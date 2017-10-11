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
#include <shlobj.h>
#include <sys/types.h>
#include <sys/stat.h>
#include "file.h"

static void SetControlToUnixTime(HWND hwndDlg, UINT idCtrl, time_t unixTime)
{
	LARGE_INTEGER liFiletime;
	FILETIME filetime;
	SYSTEMTIME st;
	char szTime[64],szDate[64],szOutput[128];

	liFiletime.QuadPart=(BIGI(11644473600)+(__int64)unixTime)*10000000;
	filetime.dwHighDateTime=liFiletime.HighPart;
	filetime.dwLowDateTime=liFiletime.LowPart;
	FileTimeToSystemTime(&filetime,&st);
	GetTimeFormatA(LOCALE_USER_DEFAULT,0,&st,NULL,szTime,SIZEOF(szTime));
	GetDateFormatA(LOCALE_USER_DEFAULT,DATE_SHORTDATE,&st,NULL,szDate,SIZEOF(szDate));
	wsprintfA(szOutput,"%s %s",szDate,szTime);
	SetDlgItemTextA(hwndDlg,idCtrl,szOutput);
}

#define C_CONTEXTMENU  0
#define C_PROPERTIES   1
// not defined in VC++ 6.0 SE
#ifndef CMF_EXTENDEDVERBS
#define CMF_EXTENDEDVERBS 0x00000100
#endif
static void DoAnnoyingShellCommand(HWND hwnd,const char *szFilename,int cmd,POINT *ptCursor)
{
	IMalloc *pShellMalloc;

	OleInitialize(NULL);
	if(SHGetMalloc(&pShellMalloc)==NOERROR) {
		IShellFolder *pDesktopFolder;
		if(SHGetDesktopFolder(&pDesktopFolder)==NOERROR) {
			WCHAR wszFilename[MAX_PATH];
			ITEMIDLIST *pCurrentIdl;
			MultiByteToWideChar(CP_ACP,0,szFilename,-1,wszFilename,SIZEOF(wszFilename));
			if(pDesktopFolder->lpVtbl->ParseDisplayName(pDesktopFolder,NULL,NULL,wszFilename,NULL,&pCurrentIdl,NULL)==NOERROR) {
				if(pCurrentIdl->mkid.cb) {
					ITEMIDLIST *pidl,*pidlNext,*pidlFilename;
					IShellFolder *pFileFolder;

					for(pidl=pCurrentIdl;;) {
						pidlNext=(ITEMIDLIST*)((PBYTE)pidl+pidl->mkid.cb);
						if(pidlNext->mkid.cb==0) {
							pidlFilename=pShellMalloc->lpVtbl->Alloc(pShellMalloc,pidl->mkid.cb+sizeof(pidl->mkid.cb));
							CopyMemory(pidlFilename,pidl,pidl->mkid.cb+sizeof(pidl->mkid.cb));
							pidl->mkid.cb=0;
							break;
						}
						pidl=pidlNext;
					}
					if(pDesktopFolder->lpVtbl->BindToObject(pDesktopFolder,pCurrentIdl,NULL,&IID_IShellFolder,&pFileFolder)==NOERROR) {
						IContextMenu *pContextMenu;
						if(pFileFolder->lpVtbl->GetUIObjectOf(pFileFolder,NULL,1,&pidlFilename,&IID_IContextMenu,NULL,&pContextMenu)==NOERROR) {
							switch(cmd) {
								case C_PROPERTIES:
								{	CMINVOKECOMMANDINFO ici={0};
									ici.cbSize=sizeof(ici);
									ici.hwnd=hwnd;
									ici.lpVerb="properties";
									ici.nShow=SW_SHOW;
									pContextMenu->lpVtbl->InvokeCommand(pContextMenu,&ici);
									break;
								}
								case C_CONTEXTMENU:
								{	HMENU hMenu;
									hMenu=CreatePopupMenu();
									if(SUCCEEDED(pContextMenu->lpVtbl->QueryContextMenu(pContextMenu,hMenu,0,1000,65535,(GetKeyState(VK_SHIFT)&0x8000?CMF_EXTENDEDVERBS:0)|CMF_NORMAL))) {
										int cmd;
										cmd=TrackPopupMenu(hMenu,TPM_RETURNCMD,ptCursor->x,ptCursor->y,0,hwnd,NULL);
										if(cmd) {
											CMINVOKECOMMANDINFO ici={0};
											ici.cbSize=sizeof(ici);
											ici.hwnd=hwnd;
											ici.lpVerb=MAKEINTRESOURCEA(cmd-1000);
											ici.nShow=SW_SHOW;
											pContextMenu->lpVtbl->InvokeCommand(pContextMenu,&ici);
										}
									}
									DestroyMenu(hMenu);
									break;
								}
							}
							pContextMenu->lpVtbl->Release(pContextMenu);
						}
						pFileFolder->lpVtbl->Release(pFileFolder);
					}
					pShellMalloc->lpVtbl->Free(pShellMalloc,pidlFilename);
				}
				pShellMalloc->lpVtbl->Free(pShellMalloc,pCurrentIdl);
			}
			pDesktopFolder->lpVtbl->Release(pDesktopFolder);
		}
		pShellMalloc->lpVtbl->Release(pShellMalloc);
	}
	OleUninitialize();
}

static WNDPROC pfnIconWindowProc;
static LRESULT CALLBACK IconCtrlSubclassProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch(msg) {
		case WM_LBUTTONDBLCLK:
			ShellExecuteA(hwnd,NULL,((PROTOFILETRANSFERSTATUS*)GetWindowLong(GetParent(hwnd),GWL_USERDATA))->currentFile,NULL,NULL,SW_SHOW);
			break;
		case WM_RBUTTONUP:
		{	POINT pt;
			pt.x=(short)LOWORD(lParam); pt.y=(short)HIWORD(lParam);
			ClientToScreen(hwnd,&pt);
			DoAnnoyingShellCommand(hwnd,((PROTOFILETRANSFERSTATUS*)GetWindowLong(GetParent(hwnd),GWL_USERDATA))->currentFile,C_CONTEXTMENU,&pt);
			return 0;
		}
	}
	return CallWindowProc(pfnIconWindowProc,hwnd,msg,wParam,lParam);
}

struct loadiconsstartinfo {
	HWND hwndDlg;
	char *szFilename;
};
void __cdecl LoadIconsAndTypesThread(struct loadiconsstartinfo *info)
{
	SHFILEINFOA fileInfo;

	OleInitialize(NULL);
	if(SHGetFileInfoA(info->szFilename,0,&fileInfo,sizeof(fileInfo),SHGFI_TYPENAME|SHGFI_ICON|SHGFI_LARGEICON)) {
		char *pszExtension,*pszFilename;
		char szExtension[64];
		char szIconFile[MAX_PATH];

		pszFilename=strrchr(info->szFilename,'\\');
		if(pszFilename==NULL) pszFilename=info->szFilename;
		pszExtension=strrchr(pszFilename,'.');
		if(pszExtension) lstrcpynA(szExtension,pszExtension+1,SIZEOF(szExtension));
		else {pszExtension="."; szExtension[0]='\0';}
		CharUpperA(szExtension);
		if(fileInfo.szTypeName[0]=='\0')
			wsprintfA(fileInfo.szTypeName,Translate("%s File"),szExtension);
		SetDlgItemTextA(info->hwndDlg,IDC_EXISTINGTYPE,fileInfo.szTypeName);
		SetDlgItemTextA(info->hwndDlg,IDC_NEWTYPE,fileInfo.szTypeName);
		SendDlgItemMessage(info->hwndDlg,IDC_EXISTINGICON,STM_SETICON,(WPARAM)fileInfo.hIcon,0);
		szIconFile[0]='\0';
		if(!lstrcmpA(szExtension,"EXE")) {
			SRFile_GetRegValue(HKEY_LOCAL_MACHINE,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Icons","2",szIconFile,SIZEOF(szIconFile));
		}
		else {
			char szTypeName[MAX_PATH];
			if(SRFile_GetRegValue(HKEY_CLASSES_ROOT,pszExtension,NULL,szTypeName,SIZEOF(szTypeName))) {
				lstrcatA(szTypeName,"\\DefaultIcon");
				if(SRFile_GetRegValue(HKEY_CLASSES_ROOT,szTypeName,NULL,szIconFile,SIZEOF(szIconFile))) {
					if(strstr(szIconFile,"%1"))
						SRFile_GetRegValue(HKEY_LOCAL_MACHINE,"Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Icons","0",szIconFile,SIZEOF(szIconFile));
					else szIconFile[0]='\0';
				}
			}
		}
		if(szIconFile[0]) {
			int iconIndex;
			HICON hIcon;
			char *pszComma=strrchr(szIconFile,',');
			if(pszComma==NULL) iconIndex=0;
			else {iconIndex=atoi(pszComma+1); *pszComma='\0';}
			hIcon=ExtractIconA(GetModuleHandle(NULL),szIconFile,iconIndex);
			if(hIcon) fileInfo.hIcon=hIcon;
		}
		SendDlgItemMessage(info->hwndDlg,IDC_NEWICON,STM_SETICON,(WPARAM)fileInfo.hIcon,0);
	}
	OleUninitialize();
	mir_free(info->szFilename);
	mir_free(info);
}

BOOL CALLBACK DlgProcFileExists(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	PROTOFILETRANSFERSTATUS *fts;

	fts=(PROTOFILETRANSFERSTATUS*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch(msg) {
		case WM_INITDIALOG:
		{	TCHAR szSize[64];
			struct _stat statbuf;
			struct loadiconsstartinfo *lisi;
			HWND hwndFocus;

			SetPropA(hwndDlg,"Miranda.Preshutdown",HookEventMessage(ME_SYSTEM_PRESHUTDOWN,hwndDlg,M_PRESHUTDOWN));

			TranslateDialogDefault(hwndDlg);
			fts=(PROTOFILETRANSFERSTATUS*)mir_alloc(sizeof(PROTOFILETRANSFERSTATUS));
			CopyProtoFileTransferStatus(fts,(PROTOFILETRANSFERSTATUS*)lParam);
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)fts);
			SetDlgItemTextA(hwndDlg,IDC_FILENAME,fts->currentFile);
			SetControlToUnixTime(hwndDlg,IDC_NEWDATE,fts->currentFileTime);
			GetSensiblyFormattedSize(fts->currentFileSize,szSize,SIZEOF(szSize),0,1,NULL);
			SetDlgItemText(hwndDlg,IDC_NEWSIZE,szSize);

			pfnIconWindowProc=(WNDPROC)SetWindowLong(GetDlgItem(hwndDlg,IDC_EXISTINGICON),GWL_WNDPROC,(LONG)IconCtrlSubclassProc);

			hwndFocus=GetDlgItem(hwndDlg,IDC_RESUME);
			if(_stat(fts->currentFile,&statbuf)==0) {
				SetControlToUnixTime(hwndDlg,IDC_EXISTINGDATE,statbuf.st_mtime);
				GetSensiblyFormattedSize(statbuf.st_size,szSize,SIZEOF(szSize),0,1,NULL);
				SetDlgItemText(hwndDlg,IDC_EXISTINGSIZE,szSize);
				if(statbuf.st_size>(int)fts->currentFileSize) {
					EnableWindow(GetDlgItem(hwndDlg,IDC_RESUME),FALSE);
					hwndFocus=GetDlgItem(hwndDlg,IDC_OVERWRITE);
				}
			}
			lisi=(struct loadiconsstartinfo*)mir_alloc(sizeof(struct loadiconsstartinfo));
			lisi->hwndDlg=hwndDlg;
			lisi->szFilename=mir_strdup(fts->currentFile);
			//can be a little slow, so why not?
			forkthread(LoadIconsAndTypesThread,0,lisi);			
			SetFocus(hwndFocus);
			SetWindowLong(hwndFocus,GWL_STYLE,GetWindowLong(hwndFocus,GWL_STYLE)|BS_DEFPUSHBUTTON);
			return FALSE;
		}
		case WM_COMMAND:
		{	PROTOFILERESUME pfr={0};
			switch(LOWORD(wParam)) {
				case IDC_OPENFILE:
					ShellExecuteA(hwndDlg,NULL,fts->currentFile,NULL,NULL,SW_SHOW);
					return FALSE;
				case IDC_OPENFOLDER:
				{	char szFile[MAX_PATH];
					char *pszLastBackslash;
					lstrcpynA(szFile,fts->currentFile,SIZEOF(szFile));
					pszLastBackslash=strrchr(szFile,'\\');
					if(pszLastBackslash) *pszLastBackslash='\0';
					ShellExecuteA(hwndDlg,NULL,szFile,NULL,NULL,SW_SHOW);
					return FALSE;
				}
				case IDC_PROPERTIES:
					DoAnnoyingShellCommand(hwndDlg,fts->currentFile,C_PROPERTIES,NULL);
					return FALSE;
				case IDC_RESUME:
					pfr.action=FILERESUME_RESUME;
					break;
				case IDC_RESUMEALL:
					pfr.action=FILERESUME_RESUMEALL;
					break;
				case IDC_OVERWRITE:
					pfr.action=FILERESUME_OVERWRITE;
					break;
				case IDC_OVERWRITEALL:
					pfr.action=FILERESUME_OVERWRITEALL;
					break;
				case IDC_SAVEAS:
				{	OPENFILENAMEA ofn={0};
					char filter[512],*pfilter;
					char str[MAX_PATH];

					lstrcpynA(str,fts->currentFile,SIZEOF(str));
					ofn.lStructSize=OPENFILENAME_SIZE_VERSION_400;
					ofn.hwndOwner=hwndDlg;
					ofn.Flags=OFN_PATHMUSTEXIST|OFN_OVERWRITEPROMPT|OFN_HIDEREADONLY;
					strcpy(filter,Translate("All Files"));
					strcat(filter," (*)");
					pfilter=filter+strlen(filter)+1;
					strcpy(pfilter,"*");
					pfilter=pfilter+strlen(pfilter)+1;
					*pfilter='\0';
					ofn.lpstrFilter=filter;
					ofn.lpstrFile=str;
					ofn.nMaxFile=SIZEOF(str);
					ofn.nMaxFileTitle=MAX_PATH;
					if(!GetSaveFileNameA(&ofn)) break;
					pfr.szFilename=mir_strdup(str);
					pfr.action=FILERESUME_RENAME;
					break;
				}
				case IDC_SKIP:
					pfr.action=FILERESUME_SKIP;
					break;
				case IDCANCEL:
					pfr.action=FILERESUME_CANCEL;
					break;
				default:
					return FALSE;
			}
			{	PROTOFILERESUME *pfrCopy;
				pfrCopy=(PROTOFILERESUME*)mir_alloc(sizeof(pfr));
				CopyMemory(pfrCopy,&pfr,sizeof(pfr));
				PostMessage(GetParent(hwndDlg),M_FILEEXISTSDLGREPLY,(WPARAM)mir_strdup(fts->currentFile),(LPARAM)pfrCopy);
				DestroyWindow(hwndDlg);
			}
			break;
		}
		case WM_CLOSE:
			PostMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDCANCEL,BN_CLICKED),(LPARAM)GetDlgItem(hwndDlg,IDCANCEL));
			break;
		case M_PRESHUTDOWN:
		{
			PostMessage(hwndDlg,WM_CLOSE,0,0);
			break;
		}
		case WM_DESTROY:
			UnhookEvent(GetPropA(hwndDlg,"Miranda.Preshutdown")); // GetProp() will return NULL if it couldnt find anything
			DestroyIcon((HICON)SendDlgItemMessage(hwndDlg,IDC_EXISTINGICON,STM_GETICON,0,0));
			DestroyIcon((HICON)SendDlgItemMessage(hwndDlg,IDC_NEWICON,STM_GETICON,0,0));
			FreeProtoFileTransferStatus(fts);
			mir_free(fts);
			break;
		case WM_CTLCOLORSTATIC:
		case WM_CTLCOLOREDIT:
			SetBkMode( (HDC)wParam, TRANSPARENT );
		case WM_CTLCOLORDLG:
			{
				HBRUSH hBrush = CreateSolidBrush( RGB( 228, 240, 254 ) );
				return hBrush;
			}
	}
	return FALSE;
}
