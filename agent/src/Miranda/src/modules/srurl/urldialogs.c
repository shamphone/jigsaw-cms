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
#include "url.h"


BOOL CALLBACK DlgProcUrlSend(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);

extern HANDLE hUrlWindowList;


BOOL CALLBACK DlgProcUrlRecv(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct UrlRcvData *dat = NULL;

	dat=(struct UrlRcvData *)GetWindowLong(hwndDlg, GWL_USERDATA);

	switch (msg)
	{
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadSkinnedIcon(SKINICON_EVENT_URL));

			dat=(struct UrlRcvData*)mir_alloc(sizeof(struct UrlRcvData));
			SetWindowLong(hwndDlg, GWL_USERDATA, (LONG)dat);

			dat->hContact = ((CLISTEVENT*)lParam)->hContact;
			dat->hDbEvent = ((CLISTEVENT*)lParam)->hDbEvent;

			WindowList_Add(hUrlWindowList, hwndDlg, dat->hContact);

			{
				DBEVENTINFO dbei;
				DBTIMETOSTRINGT dbtts;
				TCHAR* contactName;
				TCHAR  msg[128];

				ZeroMemory(&dbei,sizeof(dbei));
				dbei.cbSize=sizeof(dbei);
				dbei.cbBlob=CallService(MS_DB_EVENT_GETBLOBSIZE,(WPARAM)dat->hDbEvent,0);
				dbei.pBlob=(PBYTE)mir_alloc(dbei.cbBlob);
				CallService(MS_DB_EVENT_GET,(WPARAM)dat->hDbEvent,(LPARAM)&dbei);
				SetDlgItemTextA(hwndDlg,IDC_URL,dbei.pBlob);
				SetDlgItemTextA(hwndDlg,IDC_MSG,dbei.pBlob+lstrlenA(dbei.pBlob)+1);
				mir_free(dbei.pBlob);

				CallService(MS_DB_EVENT_MARKREAD,(WPARAM)dat->hContact,(LPARAM)dat->hDbEvent);

				contactName=(TCHAR*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,GCDNF_TCHAR);
				mir_sntprintf(msg,SIZEOF(msg),TranslateT("URL from %s"),contactName);
				SetWindowText(hwndDlg,msg);
				SetDlgItemText(hwndDlg,IDC_FROM,contactName);
				SendDlgItemMessage(hwndDlg,IDOK,BUTTONSETARROW,1,0);
				{	TCHAR str[128];
					dbtts.szFormat = _T("t d");
					dbtts.szDest = str;
					dbtts.cbDest = SIZEOF(str);
					CallService(MS_DB_TIME_TIMESTAMPTOSTRINGT, dbei.timestamp, (LPARAM)&dbtts);
					SetDlgItemText(hwndDlg, IDC_DATE, str);
			}	}

			// From message dlg
			if (!DBGetContactSettingByte(dat->hContact, "CList", "NotOnList", 0))
				ShowWindow(GetDlgItem(hwndDlg, IDC_ADD), SW_HIDE);

			dat->hIcons[0]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_ADDCONTACT),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			dat->hIcons[1]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_USERDETAILS),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			dat->hIcons[2]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_HISTORY),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			dat->hIcons[3]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_DOWNARROW),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			SendDlgItemMessage(hwndDlg,IDC_ADD,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[0]);
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[1]);
			SendDlgItemMessage(hwndDlg,IDC_HISTORY,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[2]);
			SendDlgItemMessage(hwndDlg,IDC_USERMENU,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[3]);
			SendDlgItemMessage(hwndDlg,IDC_ADD,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_HISTORY,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_USERMENU,BUTTONSETASFLATBTN,0,0);
			SendMessage(GetDlgItem(hwndDlg,IDC_ADD), BUTTONADDTOOLTIP, (WPARAM)Translate("Add Contact Permanently to List"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_USERMENU), BUTTONADDTOOLTIP, (WPARAM)Translate("User Menu"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_DETAILS), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's Details"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_HISTORY), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's History"), 0);

			SendMessage(hwndDlg,DM_UPDATETITLE,0,0);
			// From message dlg end

			Utils_RestoreWindowPositionNoSize(hwndDlg,NULL,"SRUrl","recv");
			return TRUE;

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
					if (hIcon) DrawIconEx(dis->hDC,dis->rcItem.left,dis->rcItem.top,hIcon,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0,NULL,DI_NORMAL);
				}
			}
			return CallService(MS_CLIST_MENUDRAWITEM,wParam,lParam);
			}
		
		case DM_UPDATETITLE:
			{
				char newtitle[256],oldtitle[256];
				char *szStatus,*contactName,*pszNewTitleStart;
				char *szProto;

				pszNewTitleStart=Translate("URL from ");

				if (dat->hContact) {
					szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);
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
							}
						}

						//
						contactName=(char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,0);
						SetDlgItemTextA(hwndDlg,IDC_NAME,hasName?buf:contactName);

						szStatus=(char*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION,szProto==NULL?ID_STATUS_OFFLINE:DBGetContactSettingWord(dat->hContact,szProto,"Status",ID_STATUS_OFFLINE),0);
						mir_snprintf(newtitle,SIZEOF(newtitle),"%s %s (%s)", pszNewTitleStart, contactName, szStatus);
					}
				}
				else
					lstrcpynA(newtitle, pszNewTitleStart, SIZEOF(newtitle));

				GetWindowTextA(hwndDlg,oldtitle,SIZEOF(oldtitle));

				if(lstrcmpA(newtitle,oldtitle))	   //swt() flickers even if the title hasn't actually changed
					SetWindowTextA(hwndDlg,newtitle);

				break;
				
			}

		case WM_COMMAND:
			if (dat)
				if(CallService(MS_CLIST_MENUPROCESSCOMMAND,MAKEWPARAM(LOWORD(wParam),MPCF_CONTACTMENU),(LPARAM)dat->hContact))
					break;
			switch(LOWORD(wParam))
			{
				case IDOK:
					{	HMENU hMenu,hSubMenu;
						RECT rc;
						char url[256];

						hMenu=LoadMenu(GetModuleHandle(NULL),MAKEINTRESOURCE(IDR_CONTEXT));
						hSubMenu=GetSubMenu(hMenu,6);
						CallService(MS_LANGPACK_TRANSLATEMENU,(WPARAM)hSubMenu,0);
						GetWindowRect((HWND)lParam, &rc);
						GetDlgItemTextA(hwndDlg, IDC_URL, url, SIZEOF(url));
						switch(TrackPopupMenu(hSubMenu,TPM_RETURNCMD,rc.left,rc.bottom,0,hwndDlg,NULL)) {
							case IDM_OPENNEW:
								CallService(MS_UTILS_OPENURL,1,(LPARAM)url);
								break;
							case IDM_OPENEXISTING:
								CallService(MS_UTILS_OPENURL,0,(LPARAM)url);
								break;
							case IDM_COPYLINK:
							{	HGLOBAL hData;
								if(!OpenClipboard(hwndDlg)) break;
								EmptyClipboard();
								hData=GlobalAlloc(GMEM_MOVEABLE,lstrlenA(url)+1);
								lstrcpyA((char*)GlobalLock(hData),url);
								GlobalUnlock(hData);
								SetClipboardData(CF_TEXT,hData);
								CloseClipboard();
								break;
							}
						}
					}
					return TRUE;

				case IDC_USERMENU:
					{
						RECT rc;
						HMENU hMenu=(HMENU)CallService(MS_CLIST_MENUBUILDCONTACT,(WPARAM)dat->hContact,0);
						GetWindowRect(GetDlgItem(hwndDlg,IDC_USERMENU),&rc);
						TrackPopupMenu(hMenu,0,rc.left,rc.bottom,0,hwndDlg,NULL);
						DestroyMenu(hMenu);
					}
					break;

				case IDC_HISTORY:
					CallService(MS_HISTORY_SHOWCONTACTHISTORY,(WPARAM)dat->hContact,0);
					break;
				case IDC_DETAILS:
					CallService(MS_USERINFO_SHOWDIALOG,(WPARAM)dat->hContact,0);
					break;
				case IDC_ADD:
					{
						ADDCONTACTSTRUCT acs={0};
						
						acs.handle=dat->hContact;
						acs.handleType=HANDLE_CONTACT;
						acs.szProto=0;
						CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
					}
					if (!DBGetContactSettingByte(dat->hContact,"CList","NotOnList",0)) {
						ShowWindow(GetDlgItem(hwndDlg,IDC_ADD),FALSE);
					}
					break;
				case IDC_REPLY:
					CallService(MS_MSG_SENDMESSAGE,(WPARAM)dat->hContact,0);
					//fall through
				case IDCANCEL:
					DestroyWindow(hwndDlg);
					return TRUE;
			}
			break;
		case WM_DESTROY:
			WindowList_Remove(hUrlWindowList, hwndDlg);
			{int i;for(i=0; i < SIZEOF(dat->hIcons); i++ ) DestroyIcon(dat->hIcons[i]);}
			mir_free(dat);
			Utils_SaveWindowPosition(hwndDlg,NULL,"SRUrl","recv");
			break;
	}
	return FALSE;
}

static int ddeAcked,ddeData;
static ATOM hSzDdeData;
static HWND hwndDde;
static HGLOBAL hGlobalDdeData;
static LRESULT DdeMessage(HWND hwndDlg,UINT msg,WPARAM wParam,LPARAM lParam)
{
	ATOM hSzItem;

	switch(msg) {
		case WM_DDE_ACK:
			ddeAcked=1;
			hwndDde=(HWND)wParam;
			return 0;
		case WM_DDE_DATA:
			UnpackDDElParam(msg,lParam,(PUINT)&hGlobalDdeData,(PUINT)&hSzItem);
			ddeData=1;
			if(hGlobalDdeData) {
				DDEDATA *data;
				data=(DDEDATA*)GlobalLock(hGlobalDdeData);
				if(data->fAckReq) {
					DDEACK ack={0};
					PostMessage((HWND)wParam,WM_DDE_ACK,(WPARAM)hwndDlg,PackDDElParam(WM_DDE_ACK,*(PUINT)&ack,(UINT)hSzItem));
				}
				else GlobalDeleteAtom(hSzItem);
				GlobalUnlock(hGlobalDdeData);
			}
			else GlobalDeleteAtom(hSzItem);
			return 0;
	}
	return 0;
}

static HGLOBAL DoDdeRequest(const char *szItemName,HWND hwndDlg)
{
	ATOM hSzItemName;
	DWORD timeoutTick,thisTick;
	MSG msg;

	hSzItemName=GlobalAddAtomA(szItemName);
	if(!PostMessage(hwndDde,WM_DDE_REQUEST,(WPARAM)hwndDlg,MAKELPARAM(CF_TEXT,hSzItemName))) {
		GlobalDeleteAtom(hSzItemName);
		return NULL;
	}
	timeoutTick=GetTickCount()+5000;
	ddeData=0; ddeAcked=0;
	do {
		if(PeekMessage(&msg,NULL,0,0,PM_REMOVE)) {
			TranslateMessage(&msg);
			DispatchMessage(&msg);
		}
		if(ddeData || ddeAcked) break;
		thisTick=GetTickCount();
		if(thisTick>timeoutTick) break;
	} while(MsgWaitForMultipleObjects(0,NULL,FALSE,timeoutTick-thisTick,QS_ALLINPUT)==WAIT_OBJECT_0);

	if(!ddeData) {
		GlobalDeleteAtom(hSzItemName);
		return NULL;
	}

	return hGlobalDdeData;
}

static void FreeDdeRequestData(HGLOBAL hData)
{
	DDEDATA *data;
	data=(DDEDATA*)GlobalLock(hData);
	if(data->fRelease) {
		GlobalUnlock(hData);
		GlobalFree(hData);
	}
	else GlobalUnlock(hData);
}

static void AddBrowserPageToCombo(char *url,HWND hwndCombo)
{
	char *title,*frame,*end;

	if(url[0]!='"') return;
	url++;
	title=strchr(url,'"');
	if(title==NULL) return;
	*title='\0'; title++;
	if(*title) {
		title+=2;
		frame=strchr(title,'"');
		if(frame==NULL) return;
		*frame='\0'; frame++;
		if(*frame) {
			frame+=2;
			end=strchr(frame,'"');
			if(end==NULL) return;
			*end='\0';
		}
		else frame=NULL;
	}
	else title=frame=NULL;
	if(frame==NULL || *frame==0) {
		char *szItemData;
		int i;
		char szExistingUrl[1024];

		for(i=SendMessage(hwndCombo,CB_GETCOUNT,0,0)-1;i>=0;i--) {
			if(SendMessage(hwndCombo,CB_GETLBTEXTLEN,i,0) >= SIZEOF(szExistingUrl))
				continue;
			SendMessageA(hwndCombo,CB_GETLBTEXT,i,(LPARAM)szExistingUrl);
			if(!lstrcmpA(szExistingUrl,url)) return;
		}
		i=SendMessageA(hwndCombo,CB_ADDSTRING,0,(LPARAM)url);
		szItemData=mir_strdup(title);
		SendMessage(hwndCombo,CB_SETITEMDATA,i,(LPARAM)szItemData);
	}
}

//see Q160957 and http://developer.netscape.com/docs/manuals/communicator/DDE/index.htm
static void GetOpenBrowserUrlsForBrowser(const char *szBrowser,HWND hwndDlg,HWND hwndCombo)
{
	ATOM hSzBrowser,hSzTopic;
	int windowCount,i;
	DWORD *windowId;
	DWORD dwResult;
	HGLOBAL hData;
	DDEDATA *data;
	int dataLength;

	hSzBrowser=GlobalAddAtomA(szBrowser);

	hSzTopic=GlobalAddAtomA("WWW_ListWindows");
	ddeAcked=0;
	if(!SendMessageTimeout(HWND_BROADCAST,WM_DDE_INITIATE,(WPARAM)hwndDlg,MAKELPARAM(hSzBrowser,hSzTopic),SMTO_ABORTIFHUNG|SMTO_NORMAL,DDEMESSAGETIMEOUT,&dwResult)
	   || !ddeAcked) {
		GlobalDeleteAtom(hSzTopic);
		GlobalDeleteAtom(hSzBrowser);
		return;
	}
	hData=DoDdeRequest("WWW_ListWindows",hwndDlg);
	if(hData==NULL) {
		GlobalDeleteAtom(hSzTopic);
		GlobalDeleteAtom(hSzBrowser);
		return;
	}
	dataLength=GlobalSize(hData)-offsetof(DDEDATA,Value);
	data=(DDEDATA*)GlobalLock(hData);
	windowCount=dataLength/sizeof(DWORD);
	windowId=(PDWORD)mir_alloc(sizeof(DWORD)*windowCount);
	memcpy(windowId,data->Value,windowCount*sizeof(DWORD));
	GlobalUnlock(hData);
	FreeDdeRequestData(hData);
	PostMessage(hwndDde,WM_DDE_TERMINATE,(WPARAM)hwndDlg,0);
	GlobalDeleteAtom(hSzTopic);

	hSzTopic=GlobalAddAtomA("WWW_GetWindowInfo");
	ddeAcked=0;
	if(!SendMessageTimeout(HWND_BROADCAST,WM_DDE_INITIATE,(WPARAM)hwndDlg,MAKELPARAM(hSzBrowser,hSzTopic),SMTO_ABORTIFHUNG|SMTO_NORMAL,DDEMESSAGETIMEOUT,&dwResult)
	   || !ddeAcked) {
		GlobalDeleteAtom(hSzTopic);
		GlobalDeleteAtom(hSzBrowser);
		mir_free(windowId);
		return;
	}
	for(i=0;i<windowCount;i++) {
		if(windowId[i]==0) break;
		{	char str[16];
			wsprintfA(str,"%d",windowId[i]);
			hData=DoDdeRequest(str,hwndDlg);
		}
		if(hData!=NULL) {
			dataLength=GlobalSize(hData)-offsetof(DDEDATA,Value);
			data=(DDEDATA*)GlobalLock(hData);
			AddBrowserPageToCombo(data->Value,hwndCombo);
			GlobalUnlock(hData);
			FreeDdeRequestData(hData);
		}
	}
	PostMessage(hwndDde,WM_DDE_TERMINATE,(WPARAM)hwndDlg,0);
	GlobalDeleteAtom(hSzTopic);
	GlobalDeleteAtom(hSzBrowser);
	mir_free(windowId);
}

static void GetOpenBrowserUrls(HWND hwndDlg,HWND hwndCombo)
{
	GetOpenBrowserUrlsForBrowser("opera",hwndDlg,hwndCombo);
	GetOpenBrowserUrlsForBrowser("netscape",hwndDlg,hwndCombo);
	GetOpenBrowserUrlsForBrowser("iexplore",hwndDlg,hwndCombo);
}

static WNDPROC OldSendEditProc;
static LRESULT CALLBACK SendEditSubclassProc(HWND hwnd,UINT msg,WPARAM wParam,LPARAM lParam)
{
	switch(msg) {
		case WM_CHAR:
			if(wParam=='\n' && GetKeyState(VK_CONTROL)&0x8000) {
				PostMessage(GetParent(hwnd),WM_COMMAND,IDOK,0);
				return 0;
			}
			break;
		case WM_SYSCHAR:
			if((wParam=='s' || wParam=='S') && GetKeyState(VK_MENU)&0x8000) {
				PostMessage(GetParent(hwnd),WM_COMMAND,IDOK,0);
				return 0;
			}
			break;
	}
	return CallWindowProc(OldSendEditProc,hwnd,msg,wParam,lParam);
}

BOOL CALLBACK DlgProcUrlSend(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct UrlSendData *dat;

	dat=(struct UrlSendData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch (msg)
	{
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_LIMITTEXT, 450, 0);
			SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadSkinnedIcon(SKINICON_EVENT_URL));
			dat=(struct UrlSendData*)mir_alloc(sizeof(struct UrlSendData));
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
			dat->hContact=(HANDLE)lParam;
			dat->hAckEvent=NULL;
			dat->hSendId=NULL;
			dat->sendBuffer=NULL;

			WindowList_Add(hUrlWindowList, hwndDlg, dat->hContact);

			{	TCHAR *str = (TCHAR*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,GCDNF_TCHAR);
				SetDlgItemText(hwndDlg,IDC_NAME,str);
			}

			GetOpenBrowserUrls(hwndDlg,GetDlgItem(hwndDlg,IDC_URLS));
			SendDlgItemMessage(hwndDlg, IDC_URLS, CB_SETCURSEL, 0, 0);
			if (SendDlgItemMessage(hwndDlg,IDC_URLS,CB_GETCOUNT,0,0))SendMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDC_URLS,CBN_SELCHANGE),0);
			EnableWindow(GetDlgItem(hwndDlg, IDOK), (SendDlgItemMessage(hwndDlg, IDC_URLS, CB_GETCURSEL, 0, 0) == CB_ERR)?FALSE:TRUE);
			Utils_RestoreWindowPositionNoSize(hwndDlg,NULL,"SRUrl","send");
			OldSendEditProc=(WNDPROC)SetWindowLong(GetDlgItem(hwndDlg,IDC_MESSAGE),GWL_WNDPROC,(LONG)SendEditSubclassProc);
			OldSendEditProc=(WNDPROC)SetWindowLong(GetWindow(GetDlgItem(hwndDlg,IDC_URLS),GW_CHILD),GWL_WNDPROC,(LONG)SendEditSubclassProc);

			// From message dlg
			if (!DBGetContactSettingByte(dat->hContact, "CList", "NotOnList", 0))
				ShowWindow(GetDlgItem(hwndDlg, IDC_ADD), SW_HIDE);

			dat->hIcons[0]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_ADDCONTACT),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			dat->hIcons[1]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_USERDETAILS),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			dat->hIcons[2]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_HISTORY),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			dat->hIcons[3]=(HICON)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_DOWNARROW),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0);
			SendDlgItemMessage(hwndDlg,IDC_ADD,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[0]);
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[1]);
			SendDlgItemMessage(hwndDlg,IDC_HISTORY,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[2]);
			SendDlgItemMessage(hwndDlg,IDC_USERMENU,BM_SETIMAGE,IMAGE_ICON,(LPARAM)dat->hIcons[3]);
			SendDlgItemMessage(hwndDlg,IDC_ADD,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_HISTORY,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_USERMENU,BUTTONSETASFLATBTN,0,0);
			SendMessage(GetDlgItem(hwndDlg,IDC_ADD), BUTTONADDTOOLTIP, (WPARAM)Translate("Add Contact Permanently to List"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_USERMENU), BUTTONADDTOOLTIP, (WPARAM)Translate("User Menu"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_DETAILS), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's Details"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_HISTORY), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's History"), 0);

			SendMessage(hwndDlg,DM_UPDATETITLE,0,0);
			// From message dlg end

			return TRUE;
		case WM_DDE_DATA:
		case WM_DDE_ACK:
			return DdeMessage(hwndDlg,msg,wParam,lParam);
		case WM_TIMER:
			if (wParam==0)
			{//ICQ sendurl timed out
				KillTimer(hwndDlg,0);
				MessageBox(hwndDlg,TranslateT("Send timed out"),_T(""),MB_OK);
				EnableWindow(GetDlgItem(hwndDlg,IDOK),TRUE);
				EnableWindow(GetDlgItem(hwndDlg,IDC_URLS),TRUE);
				SendDlgItemMessage(hwndDlg,IDC_MESSAGE,EM_SETREADONLY,FALSE,0);
			}
			break;
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
					if (hIcon) DrawIconEx(dis->hDC,dis->rcItem.left,dis->rcItem.top,hIcon,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0,NULL,DI_NORMAL);
				}
			}
			return CallService(MS_CLIST_MENUDRAWITEM,wParam,lParam);
			}

		case DM_UPDATETITLE:
			{
				char newtitle[256],oldtitle[256];
				char *szStatus,*contactName,*pszNewTitleStart;
				char *szProto;

				pszNewTitleStart=Translate("Send URL to");

				if (dat->hContact) {
					szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);
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
							}
						}

						contactName=(char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,0);
						SetDlgItemTextA(hwndDlg,IDC_NAME,hasName?buf:contactName);

						szStatus=(char*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION,szProto==NULL?ID_STATUS_OFFLINE:DBGetContactSettingWord(dat->hContact,szProto,"Status",ID_STATUS_OFFLINE),0);
						mir_snprintf(newtitle,SIZEOF(newtitle),"%s %s (%s)", pszNewTitleStart, contactName, szStatus);
					}
				}
				else
					lstrcpynA(newtitle, pszNewTitleStart, SIZEOF(newtitle));

				GetWindowTextA(hwndDlg,oldtitle,SIZEOF(oldtitle));

				if(lstrcmpA(newtitle,oldtitle))	   //swt() flickers even if the title hasn't actually changed
					SetWindowTextA(hwndDlg,newtitle);

				break;
				
			}

		case WM_COMMAND:
			if(CallService(MS_CLIST_MENUPROCESSCOMMAND,MAKEWPARAM(LOWORD(wParam),MPCF_CONTACTMENU),(LPARAM)dat->hContact))
				break;
			switch (LOWORD(wParam))
			{
				case IDOK:
				{
					char *body,*url;
					int bodySize,urlSize;
					
					urlSize=GetWindowTextLength(GetDlgItem(hwndDlg,IDC_URLS))+1;
					url=(char*)mir_alloc(urlSize);
					GetDlgItemTextA(hwndDlg,IDC_URLS,url,urlSize);
					if (url[0] == 0) {
						mir_free(url);
						break;
					}
					bodySize=GetWindowTextLength(GetDlgItem(hwndDlg,IDC_MESSAGE))+1;
					body=(char*)mir_alloc(bodySize);
					GetDlgItemTextA(hwndDlg,IDC_MESSAGE,body,bodySize);

					dat->sendBuffer=(char*)mir_realloc(dat->sendBuffer,lstrlenA(url)+lstrlenA(body)+2);
					lstrcpyA(dat->sendBuffer,url);
					lstrcpyA(dat->sendBuffer+lstrlenA(url)+1,body);
					dat->hAckEvent=HookEventMessage(ME_PROTO_ACK,hwndDlg,HM_EVENTSENT);
					dat->hSendId=(HANDLE)CallContactService(dat->hContact,PSS_URL,0,(LPARAM)dat->sendBuffer);
					mir_free(url);
					mir_free(body);

					//create a timeout timer
					SetTimer(hwndDlg,0,TIMEOUT_URLSEND,NULL);
					EnableWindow(GetDlgItem(hwndDlg,IDOK),FALSE);
					EnableWindow(GetDlgItem(hwndDlg,IDC_URLS),FALSE);
					SendDlgItemMessage(hwndDlg,IDC_MESSAGE,EM_SETREADONLY,TRUE,0);

					return TRUE;
				}

				case IDCANCEL:
					DestroyWindow(hwndDlg);
					return TRUE;
				case IDC_URLS:
					if(HIWORD(wParam)==CBN_SELCHANGE) {
						int i, urlSize;
						char *title;
						i=SendDlgItemMessage(hwndDlg,IDC_URLS,CB_GETCURSEL,0,0);
						title=(char*)SendDlgItemMessage(hwndDlg,IDC_URLS,CB_GETITEMDATA,(WPARAM)i,0);
						SetDlgItemTextA(hwndDlg,IDC_MESSAGE,title);
						urlSize=SendDlgItemMessage(hwndDlg,IDC_URLS,CB_GETLBTEXTLEN,(WPARAM)i,0);
						EnableWindow(GetDlgItem(hwndDlg,IDOK), (urlSize>0));
					}
					else if(HIWORD(wParam)==CBN_EDITCHANGE) {
						int urlSize = GetWindowTextLength(GetDlgItem(hwndDlg,IDC_URLS));
						EnableWindow(GetDlgItem(hwndDlg,IDOK), (urlSize>0));
					}
					break;
				case IDC_USERMENU:
					{	RECT rc;
						HMENU hMenu=(HMENU)CallService(MS_CLIST_MENUBUILDCONTACT,(WPARAM)dat->hContact,0);
						GetWindowRect(GetDlgItem(hwndDlg,IDC_USERMENU),&rc);
						TrackPopupMenu(hMenu,0,rc.left,rc.bottom,0,hwndDlg,NULL);
						DestroyMenu(hMenu);
					}
					break;
				case IDC_HISTORY:
					CallService(MS_HISTORY_SHOWCONTACTHISTORY,(WPARAM)dat->hContact,0);
					break;
				case IDC_DETAILS:
					CallService(MS_USERINFO_SHOWDIALOG,(WPARAM)dat->hContact,0);
					break;
				case IDC_ADD:
					{	ADDCONTACTSTRUCT acs={0};

						acs.handle=dat->hContact;
						acs.handleType=HANDLE_CONTACT;
						acs.szProto=0;
						CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
					}
					if (!DBGetContactSettingByte(dat->hContact,"CList","NotOnList",0)) {
						ShowWindow(GetDlgItem(hwndDlg,IDC_ADD),FALSE);
					}
					break;
			}
			break;
		case HM_EVENTSENT:
		{	ACKDATA *ack=(ACKDATA*)lParam;
			DBEVENTINFO dbei;
			if(ack->hProcess!=dat->hSendId) break;
			if(ack->hContact!=dat->hContact) break;
			if(ack->type!=ACKTYPE_URL || ack->result!=ACKRESULT_SUCCESS) break;

			ZeroMemory(&dbei,sizeof(dbei));
			dbei.cbSize=sizeof(dbei);
			dbei.eventType=EVENTTYPE_URL;
			dbei.flags=DBEF_SENT;
			dbei.szModule=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);
			dbei.timestamp=time(NULL);
			dbei.cbBlob=strlen(dat->sendBuffer)+strlen(dat->sendBuffer+strlen(dat->sendBuffer)+1)+2;
			dbei.pBlob=dat->sendBuffer;
			CallService(MS_DB_EVENT_ADD,(WPARAM)dat->hContact,(LPARAM)&dbei);
			KillTimer(hwndDlg,0);
			DestroyWindow(hwndDlg);
			break;
		}
		case WM_DESTROY:
			WindowList_Remove(hUrlWindowList, hwndDlg);
			{int i;for(i=0; i < SIZEOF(dat->hIcons); i++ ) DestroyIcon(dat->hIcons[i]);}
			SetWindowLong(GetWindow(GetDlgItem(hwndDlg,IDC_URLS),GW_CHILD),GWL_WNDPROC,(LONG)OldSendEditProc);
			SetWindowLong(GetDlgItem(hwndDlg,IDC_MESSAGE),GWL_WNDPROC,(LONG)OldSendEditProc);
			if(dat->hAckEvent) UnhookEvent(dat->hAckEvent);
			if(dat->sendBuffer!=NULL) mir_free(dat->sendBuffer);
			mir_free(dat);
			Utils_SaveWindowPosition(hwndDlg,NULL,"SRUrl","send");
			{	int i;
				for(i=SendDlgItemMessage(hwndDlg,IDC_URLS,CB_GETCOUNT,0,0)-1;i>=0;i--)
					mir_free((char*)SendDlgItemMessage(hwndDlg,IDC_URLS,CB_GETITEMDATA,i,0));
			}
			break;
	}

	return FALSE;

}
