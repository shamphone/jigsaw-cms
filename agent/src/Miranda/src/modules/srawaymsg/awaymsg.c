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

int LoadAwayMessageSending(void);

static HANDLE hAwayMsgMenuItem;
static HANDLE hWindowList;

struct AwayMsgDlgData {
	HANDLE hContact;
	HANDLE hSeq;
	HANDLE hAwayMsgEvent;
};
#define HM_AWAYMSG  (WM_USER+10)
static BOOL CALLBACK ReadAwayMsgDlgProc(HWND hwndDlg,UINT message,WPARAM wParam,LPARAM lParam)
{
	struct AwayMsgDlgData *dat;
	dat=(struct AwayMsgDlgData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch(message) {
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			dat=(struct AwayMsgDlgData*)mir_alloc(sizeof(struct AwayMsgDlgData));
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
			dat->hContact=(HANDLE)lParam;
			dat->hAwayMsgEvent=HookEventMessage(ME_PROTO_ACK,hwndDlg,HM_AWAYMSG);
			dat->hSeq=(HANDLE)CallContactService(dat->hContact,PSS_GETAWAYMSG,0,0);
			WindowList_Add(hWindowList,hwndDlg,dat->hContact);
			{	TCHAR  str[256],format[128];
				TCHAR* contactName=(TCHAR*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,GCDNF_TCHAR);
				char*  szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);
				WORD   dwStatus = DBGetContactSettingWord(dat->hContact,szProto,"Status",ID_STATUS_OFFLINE);
				TCHAR* status=(TCHAR*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION,dwStatus,GCMDF_TCHAR);
				GetWindowText(hwndDlg,format,SIZEOF(format));
				mir_sntprintf(str,SIZEOF(str),format,status,contactName);
				SetWindowText(hwndDlg,str);
				GetDlgItemText(hwndDlg,IDC_RETRIEVING,format,SIZEOF(format));
				mir_sntprintf(str,SIZEOF(str),format,status);
				SetDlgItemText(hwndDlg,IDC_RETRIEVING,str);
				SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadSkinnedProtoIcon(szProto, dwStatus));
			}
			return TRUE;
		case HM_AWAYMSG:
		{	ACKDATA *ack=(ACKDATA*)lParam;
			if(ack->hContact!=dat->hContact) break;
			if(ack->type!=ACKTYPE_AWAYMSG) break;
			if(ack->hProcess!=dat->hSeq) break;
			if(ack->result!=ACKRESULT_SUCCESS) break;
			if(dat->hAwayMsgEvent!=NULL) {UnhookEvent(dat->hAwayMsgEvent); dat->hAwayMsgEvent=NULL;}
			SetDlgItemTextA(hwndDlg,IDC_MSG,(const char*)ack->lParam);
			ShowWindow(GetDlgItem(hwndDlg,IDC_RETRIEVING),SW_HIDE);
			ShowWindow(GetDlgItem(hwndDlg,IDC_MSG),SW_SHOW);
			SetDlgItemText(hwndDlg,IDOK,TranslateT("&Close"));
			break;
		}
		case WM_COMMAND:
			switch(LOWORD(wParam)) {
				case IDCANCEL:
				case IDOK:
					DestroyWindow(hwndDlg);
					break;
			}
			break;
		case WM_CLOSE:
			DestroyWindow(hwndDlg);
			break;
		case WM_DESTROY:
			if(dat->hAwayMsgEvent!=NULL) UnhookEvent(dat->hAwayMsgEvent);
			WindowList_Remove(hWindowList,hwndDlg);
			mir_free(dat);
			break;
	}
	return FALSE;
}

static int GetMessageCommand(WPARAM wParam,LPARAM lParam)
{
	HWND hwnd;
	if(hwnd=WindowList_Find(hWindowList,(HANDLE)wParam)) {
		SetForegroundWindow(hwnd);
		SetFocus(hwnd);
	}
	else CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_READAWAYMSG),NULL,ReadAwayMsgDlgProc,wParam);
	return 0;
}

static int AwayMsgPreBuildMenu(WPARAM wParam,LPARAM lParam)
{
	CLISTMENUITEM clmi;
	char str[128];
	int status;
	char *szProto;

	szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,wParam,0);
	ZeroMemory(&clmi,sizeof(clmi));
	clmi.cbSize=sizeof(clmi);
	clmi.flags=CMIM_FLAGS|CMIF_NOTOFFLINE|CMIF_HIDDEN;

	if(szProto!=NULL) {
	   int chatRoom = szProto?DBGetContactSettingByte((HANDLE)wParam, szProto, "ChatRoom", 0):0;
	   if ( !chatRoom ) {
			status=DBGetContactSettingWord((HANDLE)wParam,szProto,"Status",ID_STATUS_OFFLINE);
			wsprintfA(str,Translate("Re&ad %s Message"),(char*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION,status,0));
			clmi.pszName=str;
			if(CallProtoService(szProto,PS_GETCAPS,PFLAGNUM_1,0)&PF1_MODEMSGRECV) {
				if(CallProtoService(szProto,PS_GETCAPS,PFLAGNUM_3,0)&Proto_Status2Flag(status)){
					clmi.flags=CMIM_FLAGS|CMIM_NAME|CMIF_NOTOFFLINE|CMIM_ICON;
					clmi.hIcon = LoadSkinnedProtoIcon(szProto, status);
				}
			}
		}
	}
	CallService(MS_CLIST_MODIFYMENUITEM,(WPARAM)hAwayMsgMenuItem,(LPARAM)&clmi);
	return 0;
}

static int AwayMsgPreShutdown(WPARAM wParam, LPARAM lParam)
{
	if (hWindowList) WindowList_BroadcastAsync(hWindowList,WM_CLOSE,0,0);
	return 0;
}

int LoadAwayMsgModule(void)
{
	CLISTMENUITEM mi;

	hWindowList=(HANDLE)CallService(MS_UTILS_ALLOCWINDOWLIST,0,0);
	CreateServiceFunction(MS_AWAYMSG_SHOWAWAYMSG,GetMessageCommand);
	ZeroMemory(&mi,sizeof(mi));
	mi.cbSize=sizeof(mi);
	mi.position=-2000005000;
	mi.flags=CMIF_NOTOFFLINE;
	mi.hIcon=NULL;
	mi.pszContactOwner=NULL;
	mi.pszName=Translate("Re&ad Away Message");
	mi.pszService=MS_AWAYMSG_SHOWAWAYMSG;
	hAwayMsgMenuItem=(HANDLE)CallService(MS_CLIST_ADDCONTACTMENUITEM,0,(LPARAM)&mi);
	HookEvent(ME_CLIST_PREBUILDCONTACTMENU,AwayMsgPreBuildMenu);
	HookEvent(ME_SYSTEM_PRESHUTDOWN,AwayMsgPreShutdown);
	return LoadAwayMessageSending();
}
