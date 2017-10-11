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
#include "file.h"

#define MAX_MRU_DIRS    5

static BOOL CALLBACK ClipSiblingsChildEnumProc(HWND hwnd,LPARAM lParam)
{
	SetWindowLong(hwnd,GWL_STYLE,GetWindowLong(hwnd,GWL_STYLE)|WS_CLIPSIBLINGS);
	return TRUE;
}

static void GetLowestExistingDirName(const char *szTestDir,char *szExistingDir,int cchExistingDir)
{
	DWORD dwAttributes;
	char *pszLastBackslash;

	lstrcpynA(szExistingDir,szTestDir,cchExistingDir);
	while((dwAttributes=GetFileAttributesA(szExistingDir))!=0xffffffff && !(dwAttributes&FILE_ATTRIBUTE_DIRECTORY)) {
		pszLastBackslash=strrchr(szExistingDir,'\\');
		if(pszLastBackslash==NULL) {*szExistingDir='\0'; break;}
		*pszLastBackslash='\0';
	}
	if(szExistingDir[0]=='\0') GetCurrentDirectoryA(cchExistingDir,szExistingDir);
}

static const char validFilenameChars[]="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_!&{}-=#@~,. ";
static void RemoveInvalidFilenameChars(char *szString)
{
	int i;
	for(i=strspn(szString,validFilenameChars);szString[i];i+=strspn(szString+i+1,validFilenameChars)+1)
		if(szString[i]>=0) szString[i]='%';
}

static INT CALLBACK BrowseCallbackProc(HWND hwnd, UINT uMsg, LPARAM lp, LPARAM pData)
{
	char szDir[MAX_PATH];
	switch(uMsg) {
	case BFFM_INITIALIZED:
		SendMessage(hwnd, BFFM_SETSELECTION, TRUE, pData);
		break;
	case BFFM_SELCHANGED:
		if (SHGetPathFromIDListA((LPITEMIDLIST) lp ,szDir))
			SendMessage(hwnd,BFFM_SETSTATUSTEXT,0,(LPARAM)szDir);
		break;
	}
	return 0;
}

int BrowseForFolder(HWND hwnd,char *szPath)
{
	BROWSEINFOA bi={0};
	LPMALLOC pMalloc;
	ITEMIDLIST *pidlResult;
	int result=0;

	if(SUCCEEDED(OleInitialize(NULL))) {
		if(SUCCEEDED(CoGetMalloc(1,&pMalloc))) {
			bi.hwndOwner=hwnd;
			bi.pszDisplayName=szPath;
			bi.lpszTitle=Translate("Select Folder");
			bi.ulFlags=BIF_NEWDIALOGSTYLE|BIF_EDITBOX|BIF_RETURNONLYFSDIRS;				// Use this combo instead of BIF_USENEWUI
			bi.lpfn=BrowseCallbackProc;
			bi.lParam=(LPARAM)szPath;

			pidlResult=SHBrowseForFolderA(&bi);
			if(pidlResult) {
				SHGetPathFromIDListA(pidlResult,szPath);
				lstrcatA(szPath,"\\");
				result=1;
			}
			pMalloc->lpVtbl->Free(pMalloc,pidlResult);
			pMalloc->lpVtbl->Release(pMalloc);
		}
		OleUninitialize();
	}
	return result;
}

static void ReplaceStr(char str[], int len, char *from, char *to) {
	char *tmp;

	if (tmp=strstr(str, from)) {
		int pos = tmp - str;
		int tlen = lstrlenA(from);

		tmp = mir_strdup(str);
		if (lstrlenA(to)>tlen)
			tmp = (char*)mir_realloc(tmp, lstrlenA(tmp)+1+lstrlenA(to)-tlen);

		MoveMemory(tmp+pos+lstrlenA(to), tmp+pos+tlen, lstrlenA(tmp)+1-pos-tlen);
		CopyMemory(tmp+pos, to, lstrlenA(to));
		mir_snprintf(str, len, "%s", tmp);
		mir_free(tmp);
	}
}

void GetContactReceivedFilesDir(HANDLE hContact,char *szDir,int cchDir)
{
	DBVARIANT dbv;
	char *szRecvFilesDir, szTemp[MAX_PATH];
	int len;

	if(DBGetContactSetting(NULL,"SRFile","RecvFilesDirAdv",&dbv)||lstrlenA(dbv.pszVal)==0) {
		char szDbPath[MAX_PATH];

		CallService(MS_DB_GETPROFILEPATH,(WPARAM)MAX_PATH,(LPARAM)szDbPath);
		lstrcatA(szDbPath,"\\");
		lstrcatA(szDbPath,Translate("Received Files"));
		lstrcatA(szDbPath,"\\%userid%");
		szRecvFilesDir=mir_strdup(szDbPath);
	}
	else {
		char szDrive[_MAX_DRIVE];
		_splitpath(dbv.pszVal, szDrive, NULL, NULL, NULL);
		if ( szDrive[0] == 0 && memcmp( dbv.pszVal, "\\\\", 2 ) != 0 ) {
			char szDbPath[MAX_PATH];
			CallService(MS_DB_GETPROFILEPATH,(WPARAM)MAX_PATH,(LPARAM)szDbPath);
			lstrcatA(szDbPath,"\\");
			lstrcatA(szDbPath,dbv.pszVal);
			szRecvFilesDir=mir_strdup(szDbPath);
		}
		else szRecvFilesDir=mir_strdup(dbv.pszVal);
		DBFreeVariant(&dbv);
	}
	lstrcpynA(szTemp,szRecvFilesDir,SIZEOF(szTemp));
	if (hContact) {
		CONTACTINFO ci;
		char szNick[64];
		char szUsername[64];
		char szProto[64];

		szNick[0] = '\0';
		szUsername[0] = '\0';
		szProto[0] = '\0';

		ZeroMemory(&ci, sizeof(ci));
		ci.cbSize = sizeof(ci);
		ci.hContact = hContact;
		ci.szProto = (char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)hContact,0);
		ci.dwFlag = CNF_UNIQUEID;
		mir_snprintf(szProto, SIZEOF(szProto), "%s", ci.szProto);
		if (!CallService(MS_CONTACT_GETCONTACTINFO, 0, (LPARAM) & ci)) {
			switch (ci.type) {
			case CNFT_ASCIIZ:
				mir_snprintf(szUsername, SIZEOF(szUsername), "%s", ci.pszVal);
				miranda_sys_free(ci.pszVal);
				break;
			case CNFT_DWORD:
				mir_snprintf(szUsername, SIZEOF(szUsername), "%u", ci.dVal);
				break;
		}	}

		mir_snprintf(szNick, SIZEOF(szNick), "%s", (char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)hContact,0));
		if (lstrlenA(szUsername)==0)
			mir_snprintf(szUsername, SIZEOF(szUsername), "%s", (char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)hContact,0));

		RemoveInvalidFilenameChars(szNick);
		RemoveInvalidFilenameChars(szUsername);
		RemoveInvalidFilenameChars(szProto);
		ReplaceStr(szTemp, SIZEOF(szTemp), "%nick%", szNick);
		ReplaceStr(szTemp, SIZEOF(szTemp), "%userid%", szUsername);
		ReplaceStr(szTemp, SIZEOF(szTemp), "%proto%", szProto);
	}
	lstrcpynA(szDir,szTemp,cchDir);
	mir_free(szRecvFilesDir);
	len=lstrlenA(szDir);
	if(len+1<cchDir && szDir[len-1]!='\\') lstrcpyA(szDir+len,"\\");
}

BOOL CALLBACK DlgProcRecvFile(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct FileDlgData *dat;

	dat=(struct FileDlgData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch (msg) {
	case WM_INITDIALOG: {
		TCHAR *contactName;
		char szPath[450];

		TranslateDialogDefault(hwndDlg);

		dat=(struct FileDlgData*)mir_calloc(sizeof(struct FileDlgData));
		SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
		dat->hContact=((CLISTEVENT*)lParam)->hContact;
		dat->hDbEvent=((CLISTEVENT*)lParam)->hDbEvent;
		dat->hPreshutdownEvent=HookEventMessage(ME_SYSTEM_PRESHUTDOWN,hwndDlg,M_PRESHUTDOWN);
		dat->dwTicks=GetTickCount();

		SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadSkinnedIcon(SKINICON_EVENT_FILE));
		EnumChildWindows(hwndDlg,ClipSiblingsChildEnumProc,0);
		dat->hUIIcons[0]=LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_ADDCONTACT),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
		dat->hUIIcons[1]=LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_USERDETAILS),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
		dat->hUIIcons[2]=LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_HISTORY),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
		dat->hUIIcons[3]=LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_DOWNARROW),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
		SendDlgItemMessage(hwndDlg,IDC_ADD,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hUIIcons[0]);
		SendDlgItemMessage(hwndDlg,IDC_DETAILS,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hUIIcons[1]);
		SendDlgItemMessage(hwndDlg,IDC_HISTORY,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hUIIcons[2]);
		SendDlgItemMessage(hwndDlg,IDC_USERMENU,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hUIIcons[3]);
		SendDlgItemMessage(hwndDlg,IDC_ADD,BUTTONSETASFLATBTN,0,0);
		SendDlgItemMessage(hwndDlg,IDC_DETAILS,BUTTONSETASFLATBTN,0,0);
		SendDlgItemMessage(hwndDlg,IDC_HISTORY,BUTTONSETASFLATBTN,0,0);
		SendDlgItemMessage(hwndDlg,IDC_USERMENU,BUTTONSETASFLATBTN,0,0);
		SendMessage(GetDlgItem(hwndDlg,IDC_ADD), BUTTONADDTOOLTIP, (WPARAM)Translate("Add Contact Permanently to List"), 0);
		SendMessage(GetDlgItem(hwndDlg,IDC_USERMENU), BUTTONADDTOOLTIP, (WPARAM)Translate("User Menu"), 0);
		SendMessage(GetDlgItem(hwndDlg,IDC_DETAILS), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's Details"), 0);
		SendMessage(GetDlgItem(hwndDlg,IDC_HISTORY), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's History"), 0);

		contactName=(TCHAR*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,GCDNF_TCHAR);
		SetDlgItemText(hwndDlg,IDC_FROM,contactName);
		GetContactReceivedFilesDir(dat->hContact,szPath,SIZEOF(szPath));
		SetDlgItemTextA(hwndDlg,IDC_FILEDIR,szPath);
		{	int i;
			char idstr[32];
			DBVARIANT dbv;
			HRESULT (STDAPICALLTYPE *MySHAutoComplete)(HWND,DWORD);

			MySHAutoComplete=(HRESULT (STDAPICALLTYPE*)(HWND,DWORD))GetProcAddress(GetModuleHandleA("shlwapi"),"SHAutoComplete");
			if(MySHAutoComplete) MySHAutoComplete(GetWindow(GetDlgItem(hwndDlg,IDC_FILEDIR),GW_CHILD),1);
			for(i=0;i<MAX_MRU_DIRS;i++) {
				wsprintfA(idstr,"MruDir%d",i);
				if(DBGetContactSetting(NULL,"SRFile",idstr,&dbv)) break;
				SendDlgItemMessageA(hwndDlg,IDC_FILEDIR,CB_ADDSTRING,0,(LPARAM)dbv.pszVal);
				DBFreeVariant(&dbv);
			}
		}

		CallService(MS_DB_EVENT_MARKREAD,(WPARAM)dat->hContact,(LPARAM)dat->hDbEvent);

		{	DBEVENTINFO dbei={0};
			DBTIMETOSTRINGT dbtts;
			TCHAR datetimestr[64];

			dbei.cbSize=sizeof(dbei);
			dbei.cbBlob=CallService(MS_DB_EVENT_GETBLOBSIZE,(WPARAM)dat->hDbEvent,0);
			dbei.pBlob=(PBYTE)mir_alloc(dbei.cbBlob);
			CallService(MS_DB_EVENT_GET,(WPARAM)dat->hDbEvent,(LPARAM)&dbei);
			dat->fs=(HANDLE)*(PDWORD)dbei.pBlob;
			lstrcpynA(szPath, dbei.pBlob+4, min(dbei.cbBlob+1,SIZEOF(szPath)));
			SetDlgItemTextA(hwndDlg,IDC_FILENAMES,szPath);
			lstrcpynA(szPath, dbei.pBlob+4+strlen(dbei.pBlob+4)+1, min(dbei.cbBlob-4-strlen(dbei.pBlob+4),SIZEOF(szPath)));
			SetDlgItemTextA(hwndDlg,IDC_MSG,szPath);
			mir_free(dbei.pBlob);

			dbtts.szFormat = _T("t d");
			dbtts.szDest = datetimestr;
			dbtts.cbDest = SIZEOF(datetimestr);
			CallService(MS_DB_TIME_TIMESTAMPTOSTRINGT, dbei.timestamp, ( LPARAM )&dbtts);
			SetDlgItemText(hwndDlg, IDC_DATE, datetimestr);
		}
		{	char* szProto = (char*)CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM)dat->hContact, 0);
			if (szProto) {
				CONTACTINFO ci;
				int hasName = 0;
				char buf[128];
				ZeroMemory(&ci,sizeof(ci));

				ci.cbSize = sizeof(ci);
				ci.hContact = dat->hContact;
				ci.szProto = szProto;
				ci.dwFlag = CNF_UNIQUEID;
				if (!CallService(MS_CONTACT_GETCONTACTINFO,0,(LPARAM)&ci)) {
					switch(ci.type) {
					case CNFT_ASCIIZ:
						hasName = 1;
						mir_snprintf(buf, SIZEOF(buf), "%s", ci.pszVal);
						mir_free(ci.pszVal);
						break;
					case CNFT_DWORD:
						hasName = 1;
						mir_snprintf(buf, SIZEOF(buf),"%u",ci.dVal);
						break;
				}	}
				if (hasName)
					SetDlgItemTextA(hwndDlg, IDC_NAME, buf );
				else
					SetDlgItemText(hwndDlg, IDC_NAME, contactName);
		}	}

		if(DBGetContactSettingByte(dat->hContact,"CList","NotOnList",0)) {
			RECT rcBtn1,rcBtn2,rcDateCtrl;
			GetWindowRect(GetDlgItem(hwndDlg,IDC_ADD),&rcBtn1);
			GetWindowRect(GetDlgItem(hwndDlg,IDC_USERMENU),&rcBtn2);
			GetWindowRect(GetDlgItem(hwndDlg,IDC_DATE),&rcDateCtrl);
			SetWindowPos(GetDlgItem(hwndDlg,IDC_DATE),0,0,0,rcDateCtrl.right-rcDateCtrl.left-(rcBtn2.left-rcBtn1.left),rcDateCtrl.bottom-rcDateCtrl.top,SWP_NOZORDER|SWP_NOMOVE);
		}
		else if(DBGetContactSettingByte(NULL,"SRFile","AutoAccept",0)) {
			//don't check auto-min here to fix BUG#647620
			PostMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDOK,BN_CLICKED),(LPARAM)GetDlgItem(hwndDlg,IDOK));
		}
		if(!DBGetContactSettingByte(dat->hContact,"CList","NotOnList",0))
			ShowWindow(GetDlgItem(hwndDlg, IDC_ADD),SW_HIDE);

		ShowWindow(GetDlgItem(hwndDlg,IDC_USERMENU), 0);
		ShowWindow(GetDlgItem(hwndDlg,IDC_DETAILS), 0);
		ShowWindow(GetDlgItem(hwndDlg,IDC_HISTORY), 0);
		return TRUE;
	}
	case M_FILEEXISTSDLGREPLY:
		return SendMessage(dat->hwndTransfer,msg,wParam,lParam);
	case WM_MEASUREITEM:
		return CallService(MS_CLIST_MENUMEASUREITEM,wParam,lParam);
	case WM_DRAWITEM:
		{	LPDRAWITEMSTRUCT dis=(LPDRAWITEMSTRUCT)lParam;
			if(dis->hwndItem==GetDlgItem(hwndDlg, IDC_PROTOCOL)) {
				char *szProto;

				szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);
				if (szProto) {
					HICON hIcon;

					hIcon=(HICON)CallProtoService(szProto,PS_LOADICON,PLI_PROTOCOL|PLIF_SMALL,0);
					if (hIcon) {
						DrawIconEx(dis->hDC,dis->rcItem.left,dis->rcItem.top,hIcon,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0,NULL,DI_NORMAL);
						DestroyIcon(hIcon);
			}	}	}

			return CallService(MS_CLIST_MENUDRAWITEM,wParam,lParam);
		}
	case WM_COMMAND:
		if ( CallService(MS_CLIST_MENUPROCESSCOMMAND, MAKEWPARAM(LOWORD(wParam),MPCF_CONTACTMENU), (LPARAM)dat->hContact ))
			break;

		switch ( LOWORD( wParam )) {
		case IDC_FILEDIRBROWSE:
			{
				char szDirName[MAX_PATH],szExistingDirName[MAX_PATH];

				GetDlgItemTextA(hwndDlg,IDC_FILEDIR,szDirName,SIZEOF(szDirName));
				GetLowestExistingDirName(szDirName,szExistingDirName,SIZEOF(szExistingDirName));
				if(BrowseForFolder(hwndDlg,szExistingDirName))
					SetDlgItemTextA(hwndDlg,IDC_FILEDIR,szExistingDirName);
				return TRUE;
			}
		case IDOK:
			if(dat->hwndTransfer) return SendMessage(dat->hwndTransfer,msg,wParam,lParam);
			{	//most recently used directories
				char szRecvDir[MAX_PATH],szDefaultRecvDir[MAX_PATH];
				GetDlgItemTextA(hwndDlg,IDC_FILEDIR,szRecvDir,SIZEOF(szRecvDir));
				GetContactReceivedFilesDir(NULL,szDefaultRecvDir,SIZEOF(szDefaultRecvDir));
				if(_strnicmp(szRecvDir,szDefaultRecvDir,lstrlenA(szDefaultRecvDir))) {
					char idstr[32];
					int i;
					DBVARIANT dbv;
					for(i=MAX_MRU_DIRS-2;i>=0;i--) {
						wsprintfA(idstr,"MruDir%d",i);
						if(DBGetContactSetting(NULL,"SRFile",idstr,&dbv)) continue;
						wsprintfA(idstr,"MruDir%d",i+1);
						DBWriteContactSettingString(NULL,"SRFile",idstr,dbv.pszVal);
						DBFreeVariant(&dbv);
					}
					DBWriteContactSettingString(NULL,"SRFile",idstr,szRecvDir);
				}
			}
			EnableWindow(GetDlgItem(hwndDlg,IDC_FILENAMES),FALSE);
			EnableWindow(GetDlgItem(hwndDlg,IDC_MSG),FALSE);
			EnableWindow(GetDlgItem(hwndDlg,IDC_FILEDIR),FALSE);
			EnableWindow(GetDlgItem(hwndDlg,IDC_FILEDIRBROWSE),FALSE);
			dat->hwndTransfer=CreateDialog(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_FILETRANSFERINFO),hwndDlg,DlgProcFileTransfer);
			//check for auto-minimize here to fix BUG#647620
			if(DBGetContactSettingByte(NULL,"SRFile","AutoAccept",0) && DBGetContactSettingByte(NULL,"SRFile","AutoMin",0))
				ShowWindow(hwndDlg,SW_SHOWMINIMIZED);
			return TRUE;
		case IDCANCEL:					
			if (dat->fs) CallContactService(dat->hContact,PSS_FILEDENY,(WPARAM)dat->fs,(LPARAM)Translate("Cancelled"));
			dat->fs=NULL; /* the protocol will free the handle */
			if(dat->hwndTransfer) return SendMessage(dat->hwndTransfer,msg,wParam,lParam);
			DestroyWindow(hwndDlg);
			return TRUE;
		case IDC_ADD:
			{	ADDCONTACTSTRUCT acs={0};

			acs.handle=dat->hContact;
			acs.handleType=HANDLE_CONTACT;
			acs.szProto="";
			CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
			if(!DBGetContactSettingByte(dat->hContact,"CList","NotOnList",0))
				ShowWindow(GetDlgItem(hwndDlg,IDC_ADD), SW_HIDE);
			return TRUE;
			}
		case IDC_USERMENU:
			{	RECT rc;
			HMENU hMenu=(HMENU)CallService(MS_CLIST_MENUBUILDCONTACT,(WPARAM)dat->hContact,0);
			GetWindowRect((HWND)lParam,&rc);
			TrackPopupMenu(hMenu,0,rc.left,rc.bottom,0,hwndDlg,NULL);
			DestroyMenu(hMenu);
			break;
			}
		case IDC_DETAILS:
			CallService(MS_USERINFO_SHOWDIALOG,(WPARAM)dat->hContact,0);
			return TRUE;
		case IDC_HISTORY:
			CallService(MS_HISTORY_SHOWCONTACTHISTORY,(WPARAM)dat->hContact,0);
			return TRUE;
		}
		break;

	case M_PRESHUTDOWN:
		if (IsWindow(dat->hwndTransfer)) PostMessage(dat->hwndTransfer,WM_CLOSE,0,0);
		break;

	case WM_DESTROY:
		if(dat->hPreshutdownEvent) UnhookEvent(dat->hPreshutdownEvent);
		if(dat->hwndTransfer) DestroyWindow(dat->hwndTransfer);
		DestroyIcon(dat->hUIIcons[3]);
		DestroyIcon(dat->hUIIcons[2]);
		DestroyIcon(dat->hUIIcons[1]);
		DestroyIcon(dat->hUIIcons[0]);
		mir_free(dat);
		return TRUE;
	case WM_CTLCOLORSTATIC:
		SetBkMode( (HDC)wParam, TRANSPARENT );
	case WM_CTLCOLORDLG:
		{
			HBRUSH hBrush = CreateSolidBrush( RGB( 228, 240, 254 ) );
			return hBrush;
		}
	}
	return FALSE;
}
