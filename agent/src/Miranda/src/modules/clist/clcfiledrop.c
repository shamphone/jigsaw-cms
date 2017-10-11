/*

Miranda IM: the free IM client for Microsoft* Windows*

Copyright 2000-2007 Miranda ICQ/IM project, 
all portions of this codebase are copyrighted to the people 
listed in contributors.txt.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/
#include "commonheaders.h"
#include "clc.h"
#include <shlobj.h>

static IDropTargetVtbl dropTargetVtbl;

struct CDropTarget
{
	IDropTargetVtbl *lpVtbl;
	unsigned refCount;
	IDropTargetHelper *pDropTargetHelper;
}
static dropTarget;

static HWND hwndCurrentDrag = NULL;
static int originalSelection;

static STDMETHODIMP_(ULONG) CDropTarget_QueryInterface(struct CDropTarget *lpThis, REFIID riid, LPVOID * ppvObj)
{
	if (IsEqualIID(riid, &IID_IDropTarget)) {
		*ppvObj = lpThis;
		lpThis->lpVtbl->AddRef((IDropTarget *) lpThis);
		return S_OK;
	}
	*ppvObj = NULL;
	return E_NOINTERFACE;
}

static STDMETHODIMP_(ULONG) CDropTarget_AddRef(struct CDropTarget *lpThis)
{
	return ++lpThis->refCount;
}

static STDMETHODIMP_(ULONG) CDropTarget_Release(struct CDropTarget *lpThis)
{
	if (lpThis->refCount == 1) {
		if (lpThis->pDropTargetHelper)
			lpThis->pDropTargetHelper->lpVtbl->Release(lpThis->pDropTargetHelper);
	}
	return --lpThis->refCount;
}

static HANDLE HContactFromPoint(HWND hwnd, struct ClcData *dat, int x, int y, int *hitLine)
{
	int hit;
	struct ClcContact *contact;
	DWORD hitFlags;
	char *szProto;
	DWORD protoCaps;

	hit = cli.pfnHitTest(hwnd, dat, x, y, &contact, NULL, &hitFlags);
	if (hit == -1 || !(hitFlags & (CLCHT_ONITEMLABEL | CLCHT_ONITEMICON)) || contact->type != CLCIT_CONTACT)
		return NULL;
	szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) contact->hContact, 0);
	if (szProto == NULL)
		return NULL;
	protoCaps = CallProtoService(szProto, PS_GETCAPS, PFLAGNUM_1, 0);
	if (!(protoCaps & PF1_FILESEND))
		return NULL;
	if (ID_STATUS_OFFLINE == DBGetContactSettingWord(contact->hContact, szProto, "Status", ID_STATUS_OFFLINE))
		return NULL;
	if (hitLine)
		*hitLine = hit;
	return contact->hContact;
}

static STDMETHODIMP_(HRESULT) CDropTarget_DragOver(struct CDropTarget *lpThis, DWORD fKeyState, POINTL pt, DWORD * pdwEffect)
{
	POINT shortPt;
	struct ClcData *dat;
	RECT clRect;
	int hit;
	HANDLE hContact;

	if (lpThis->pDropTargetHelper && hwndCurrentDrag)
		lpThis->pDropTargetHelper->lpVtbl->DragOver(lpThis->pDropTargetHelper, (POINT *) & pt, *pdwEffect);

	*pdwEffect = 0;
	if (hwndCurrentDrag == NULL) {
		*pdwEffect = DROPEFFECT_NONE;
		return S_OK;
	}
	CallService(MS_CLIST_PAUSEAUTOHIDE, 0, 0);
	dat = (struct ClcData *) GetWindowLong(hwndCurrentDrag, 0);
	shortPt.x = pt.x;
	shortPt.y = pt.y;
	ScreenToClient(hwndCurrentDrag, &shortPt);
	GetClientRect(hwndCurrentDrag, &clRect);

	if (shortPt.y < dat->dragAutoScrollHeight || shortPt.y >= clRect.bottom - dat->dragAutoScrollHeight) {
		*pdwEffect |= DROPEFFECT_SCROLL;
		cli.pfnScrollTo(hwndCurrentDrag, dat, dat->yScroll + (shortPt.y < dat->dragAutoScrollHeight ? -1 : 1) * dat->rowHeight * 2, 0);
	}
	hContact = HContactFromPoint(hwndCurrentDrag, dat, shortPt.x, shortPt.y, &hit);
	if (hContact == NULL) {
		hit = -1;
		*pdwEffect |= DROPEFFECT_NONE;
	}
	else
		*pdwEffect |= DROPEFFECT_COPY;

	if (dat->selection != hit) {
		dat->selection = hit;
		cli.pfnInvalidateRect(hwndCurrentDrag, NULL, FALSE);
		lpThis->pDropTargetHelper->lpVtbl->Show(lpThis->pDropTargetHelper, FALSE);
		UpdateWindow(hwndCurrentDrag);
		lpThis->pDropTargetHelper->lpVtbl->Show(lpThis->pDropTargetHelper, TRUE);
	}

	return S_OK;
}

static STDMETHODIMP_(HRESULT) CDropTarget_DragEnter(struct CDropTarget *lpThis, IDataObject * pData, DWORD fKeyState, POINTL pt, DWORD * pdwEffect)
{
	HWND hwnd;
	TCHAR szWindowClass[64];
	POINT shortPt;

	shortPt.x = pt.x;
	shortPt.y = pt.y;
	hwnd = WindowFromPoint(shortPt);
	GetClassName(hwnd, szWindowClass, SIZEOF(szWindowClass));
	if (!lstrcmp(szWindowClass, CLISTCONTROL_CLASS)) {
		struct ClcData *dat;
		hwndCurrentDrag = hwnd;
		dat = (struct ClcData *) GetWindowLong(hwndCurrentDrag, 0);
		originalSelection = dat->selection;
		dat->showSelAlways = 1;
	}
	if (lpThis->pDropTargetHelper && hwndCurrentDrag)
		lpThis->pDropTargetHelper->lpVtbl->DragEnter(lpThis->pDropTargetHelper, hwndCurrentDrag, pData, (POINT *) & pt, *pdwEffect);
	return CDropTarget_DragOver(lpThis, fKeyState, pt, pdwEffect);
}

static STDMETHODIMP_(HRESULT) CDropTarget_DragLeave(struct CDropTarget *lpThis)
{
	if (hwndCurrentDrag) {
		struct ClcData *dat;
		if (lpThis->pDropTargetHelper)
			lpThis->pDropTargetHelper->lpVtbl->DragLeave(lpThis->pDropTargetHelper);
		dat = (struct ClcData *) GetWindowLong(hwndCurrentDrag, 0);
		dat->showSelAlways = 0;
		dat->selection = originalSelection;
		cli.pfnInvalidateRect(hwndCurrentDrag, NULL, FALSE);
	}
	hwndCurrentDrag = NULL;
	return S_OK;
}

static void AddToFileList(char ***pppFiles, int *totalCount, const char *szFilename)
{
	*pppFiles = (char **) mir_realloc(*pppFiles, (++*totalCount + 1) * sizeof(char *));
	(*pppFiles)[*totalCount] = NULL;
	(*pppFiles)[*totalCount - 1] = mir_strdup(szFilename);
	if (GetFileAttributesA(szFilename) & FILE_ATTRIBUTE_DIRECTORY) {
		WIN32_FIND_DATAA fd;
		HANDLE hFind;
		char szPath[MAX_PATH];
		lstrcpyA(szPath, szFilename);
		lstrcatA(szPath, "\\*");
		if (hFind = FindFirstFileA(szPath, &fd)) {
			do {
				if (!lstrcmpA(fd.cFileName, ".") || !lstrcmpA(fd.cFileName, ".."))
					continue;
				lstrcpyA(szPath, szFilename);
				lstrcatA(szPath, "\\");
				lstrcatA(szPath, fd.cFileName);
				AddToFileList(pppFiles, totalCount, szPath);
			} while (FindNextFileA(hFind, &fd));
			FindClose(hFind);
		}
	}
}

static STDMETHODIMP_(HRESULT) CDropTarget_Drop(struct CDropTarget *lpThis, IDataObject * pData, DWORD fKeyState, POINTL pt, DWORD * pdwEffect)
{
	FORMATETC fe = { CF_HDROP, NULL, DVASPECT_CONTENT, -1, TYMED_HGLOBAL };
	STGMEDIUM stg;
	HDROP hDrop;
	POINT shortPt;
	struct ClcData *dat;
	HANDLE hContact;

	if (lpThis->pDropTargetHelper && hwndCurrentDrag)
		lpThis->pDropTargetHelper->lpVtbl->Drop(lpThis->pDropTargetHelper, pData, (POINT *) & pt, *pdwEffect);

	*pdwEffect = DROPEFFECT_NONE;
	if (hwndCurrentDrag == NULL || S_OK != pData->lpVtbl->GetData(pData, &fe, &stg))
		return S_OK;
	hDrop = (HDROP) stg.hGlobal;
	dat = (struct ClcData *) GetWindowLong(hwndCurrentDrag, 0);

	shortPt.x = pt.x;
	shortPt.y = pt.y;
	ScreenToClient(hwndCurrentDrag, &shortPt);
	hContact = HContactFromPoint(hwndCurrentDrag, dat, shortPt.x, shortPt.y, NULL);
	if (hContact != NULL) {
		char **ppFiles = NULL;
		char szFilename[MAX_PATH];
		int fileCount, totalCount = 0, i;

		fileCount = DragQueryFile(hDrop, -1, NULL, 0);
		ppFiles = NULL;
		for (i = 0; i < fileCount; i++) {
			DragQueryFileA(hDrop, i, szFilename, SIZEOF(szFilename));
			AddToFileList(&ppFiles, &totalCount, szFilename);
		}

		if (!CallService(MS_CLIST_CONTACTFILESDROPPED, (WPARAM) hContact, (LPARAM) ppFiles))
			*pdwEffect = DROPEFFECT_COPY;

		for (i = 0; ppFiles[i]; i++)
			mir_free(ppFiles[i]);
		mir_free(ppFiles);
	}

	if (stg.pUnkForRelease)
		stg.pUnkForRelease->lpVtbl->Release(stg.pUnkForRelease);
	else
		GlobalFree(stg.hGlobal);

	CDropTarget_DragLeave(lpThis);
	return S_OK;
}

static VOID CALLBACK CreateDropTargetHelperTimerProc(HWND hwnd, UINT uMsg, UINT idEvent, DWORD dwTime)
{
	/* macro defines needed CLSID and IID declarations since
	they have to be referenced */
#ifndef CLSID_DragDropHelper
#define MDEF_CLSID(name, l, w1, w2, b1, b2, b3, b4, b5, b6, b7, b8) \
	const CLSID name \
	= { l, w1, w2, { b1, b2,  b3,  b4,  b5,  b6,  b7,  b8 } }

	MDEF_CLSID(IID_IDropTargetHelper, 0x4657278b, 0x411b, 0x11d2, 0x83, 0x9a, 0x0, 0xc0, 0x4f, 0xd9, 0x18, 0xd0);
	MDEF_CLSID(CLSID_DragDropHelper, 0x4657278a, 0x411b, 0x11d2, 0x83, 0x9a, 0x0, 0xc0, 0x4f, 0xd9, 0x18, 0xd0);
#endif
	KillTimer(hwnd, idEvent);
	//This is a ludicrously slow function (~200ms) so we delay load it a bit.
	if (S_OK != CoCreateInstance(&CLSID_DragDropHelper, NULL, CLSCTX_INPROC_SERVER, &IID_IDropTargetHelper, &dropTarget.pDropTargetHelper))
		dropTarget.pDropTargetHelper = NULL;
}

void InitFileDropping(void)
{
	OleInitialize(NULL);
	dropTarget.lpVtbl = &dropTargetVtbl;
	dropTarget.lpVtbl->AddRef = (ULONG(__stdcall *) (IDropTarget *)) CDropTarget_AddRef;
	dropTarget.lpVtbl->Release = (ULONG(__stdcall *) (IDropTarget *)) CDropTarget_Release;
	dropTarget.lpVtbl->QueryInterface = (ULONG(__stdcall *) (IDropTarget *, REFIID, PVOID *)) CDropTarget_QueryInterface;
	dropTarget.lpVtbl->DragEnter = (HRESULT(__stdcall *) (IDropTarget *, IDataObject *, DWORD, POINTL, PDWORD)) CDropTarget_DragEnter;
	dropTarget.lpVtbl->DragOver = (HRESULT(__stdcall *) (IDropTarget *, DWORD, POINTL, PDWORD)) CDropTarget_DragOver;
	dropTarget.lpVtbl->DragLeave = (HRESULT(__stdcall *) (IDropTarget *)) CDropTarget_DragLeave;
	dropTarget.lpVtbl->Drop = (HRESULT(__stdcall *) (IDropTarget *, IDataObject *, DWORD, POINTL, PDWORD)) CDropTarget_Drop;
	dropTarget.refCount = 0;
	dropTarget.pDropTargetHelper = NULL;
	SetTimer(NULL, 1, 1000, CreateDropTargetHelperTimerProc);
}

void FreeFileDropping(void)
{
	OleUninitialize();
}

void fnRegisterFileDropping(HWND hwnd)
{
	RegisterDragDrop(hwnd, (IDropTarget *) & dropTarget);
}

void fnUnregisterFileDropping(HWND hwnd)
{
	RevokeDragDrop(hwnd);
}
