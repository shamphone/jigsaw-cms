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

static DWORD protoModeMsgFlags;

static char *GetDefaultMessage(int status)
{
	switch(status) {
		case ID_STATUS_AWAY: return Translate("I've been away since %time%.");
		case ID_STATUS_NA: return Translate("Give it up, I'm not in!");
		case ID_STATUS_OCCUPIED: return Translate("Not right now.");
		case ID_STATUS_DND: return Translate("Give a guy some peace, would ya?");
		case ID_STATUS_FREECHAT: return Translate("I'm a chatbot!");
		case ID_STATUS_ONLINE: return Translate("Yep, I'm here.");
		case ID_STATUS_OFFLINE: return Translate("Nope, not here.");
		case ID_STATUS_INVISIBLE: return Translate("I'm hiding from the mafia.");
		case ID_STATUS_ONTHEPHONE: return Translate("That'll be the phone.");
		case ID_STATUS_OUTTOLUNCH: return Translate("Mmm...food.");
		case ID_STATUS_IDLE: return Translate("idleeeeeeee");
	}
	return NULL;
}

static char *StatusModeToDbSetting(int status,const char *suffix)
{
    char *prefix;
	static char str[64];

	switch(status) {
		case ID_STATUS_AWAY: prefix="Away";	break;
		case ID_STATUS_NA: prefix="Na";	break;
		case ID_STATUS_DND: prefix="Dnd"; break;
		case ID_STATUS_OCCUPIED: prefix="Occupied"; break;
		case ID_STATUS_FREECHAT: prefix="FreeChat"; break;
		case ID_STATUS_ONLINE: prefix="On"; break;
		case ID_STATUS_OFFLINE: prefix="Off"; break;
		case ID_STATUS_INVISIBLE: prefix="Inv"; break;
		case ID_STATUS_ONTHEPHONE: prefix="Otp"; break;
		case ID_STATUS_OUTTOLUNCH: prefix="Otl"; break;
		case ID_STATUS_IDLE: prefix="Idl"; break;
		default: return NULL;
	}
	lstrcpyA(str,prefix); lstrcatA(str,suffix);
	return str;
}

//remember to mir_free() the return value
static int GetAwayMessage(WPARAM wParam, LPARAM lParam)
{
	DBVARIANT dbv;
	int statusMode = (int)wParam;

	if(DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(wParam,"Ignore"),0)) {
		return (int)NULL;
	}
	if(DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(statusMode,"UsePrev"),0)) {
		if(DBGetContactSetting(NULL,"SRAway",StatusModeToDbSetting(statusMode,"Msg"),&dbv))
			dbv.pszVal=mir_strdup(GetDefaultMessage(statusMode));
	}
	else {
		int i;
		char substituteStr[128];
		if(DBGetContactSetting(NULL,"SRAway",StatusModeToDbSetting(statusMode,"Default"),&dbv))
			dbv.pszVal=mir_strdup(GetDefaultMessage(statusMode));
		for(i=0;dbv.pszVal[i];i++) {
			if(dbv.pszVal[i]!='%') continue;
			if(!_strnicmp(dbv.pszVal+i,"%time%",6))
				GetTimeFormatA(LOCALE_USER_DEFAULT,TIME_NOSECONDS,NULL,NULL,substituteStr,SIZEOF(substituteStr));
			else if(!_strnicmp(dbv.pszVal+i,"%date%",6))
				GetDateFormatA(LOCALE_USER_DEFAULT,DATE_SHORTDATE,NULL,NULL,substituteStr,SIZEOF(substituteStr));
			else continue;
			if(lstrlenA(substituteStr)>6) dbv.pszVal=(char*)mir_realloc(dbv.pszVal,lstrlenA(dbv.pszVal)+1+lstrlenA(substituteStr)-6);
			MoveMemory(dbv.pszVal+i+lstrlenA(substituteStr),dbv.pszVal+i+6,lstrlenA(dbv.pszVal)-i-5);
			CopyMemory(dbv.pszVal+i,substituteStr,lstrlenA(substituteStr));
		}
	}
	return (int)dbv.pszVal;
}

static WNDPROC OldMessageEditProc;

static LRESULT CALLBACK MessageEditSubclassProc(HWND hwnd,UINT msg,WPARAM wParam,LPARAM lParam)
{
	switch(msg) {
		case WM_CHAR:
			if(wParam=='\n' && GetKeyState(VK_CONTROL)&0x8000) {
				PostMessage(GetParent(hwnd),WM_COMMAND,IDOK,0);
				return 0;
			}
            if (wParam == 1 && GetKeyState(VK_CONTROL) & 0x8000) {      //ctrl-a
                SendMessage(hwnd, EM_SETSEL, 0, -1);
                return 0;
            }
            if (wParam == 23 && GetKeyState(VK_CONTROL) & 0x8000) {     // ctrl-w
                SendMessage(GetParent(hwnd), WM_CLOSE, 0, 0);
                return 0;
            }
            if (wParam == 127 && GetKeyState(VK_CONTROL) & 0x8000) {    //ctrl-backspace
                DWORD start, end;
                TCHAR *text;
                int textLen;
                SendMessage(hwnd, EM_GETSEL, (WPARAM) & end, (LPARAM) (PDWORD) NULL);
                SendMessage(hwnd, WM_KEYDOWN, VK_LEFT, 0);
                SendMessage(hwnd, EM_GETSEL, (WPARAM) & start, (LPARAM) (PDWORD) NULL);
                textLen = GetWindowTextLength(hwnd);
                text = (TCHAR *) mir_alloc(sizeof(TCHAR) * (textLen + 1));
                GetWindowText(hwnd, text, textLen + 1);
                MoveMemory(text + start, text + end, sizeof(TCHAR) * (textLen + 1 - end));
                SetWindowText(hwnd, text);
                mir_free(text);
                SendMessage(hwnd, EM_SETSEL, start, start);
                SendMessage(GetParent(hwnd), WM_COMMAND, MAKEWPARAM(GetDlgCtrlID(hwnd), EN_CHANGE), (LPARAM) hwnd);
                return 0;
            }
			break;
	}
	return CallWindowProc(OldMessageEditProc,hwnd,msg,wParam,lParam);
}

void ChangeAllProtoMessages(char *szProto, int statusMode,char *msg)
{
	int protoCount,i;
	PROTOCOLDESCRIPTOR **proto;

	CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&proto);
	if (szProto) CallProtoService(szProto,PS_SETAWAYMSG,statusMode,(LPARAM)msg);
	else {
		for(i=0;i<protoCount;i++) {
			if (CallProtoService(proto[i]->szName,PS_GETCAPS,PFLAGNUM_1,0)&PF1_MODEMSGSEND)
				CallProtoService(proto[i]->szName,PS_SETAWAYMSG,statusMode,(LPARAM)msg);
		}
	}
}

struct SetAwayMsgData {
	int statusMode;
	int countdown;
	char okButtonFormat[64];
	char *szProto;
	HANDLE hPreshutdown;
};
struct SetAwasMsgNewData {
	char *szProto;
	int statusMode;
};

#define DM_SRAWAY_SHUTDOWN WM_USER+10

static BOOL CALLBACK SetAwayMsgDlgProc(HWND hwndDlg,UINT message,WPARAM wParam,LPARAM lParam)
{
	struct SetAwayMsgData *dat;

	dat=(struct SetAwayMsgData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch(message) {
		case WM_INITDIALOG:
		{
			struct SetAwasMsgNewData *newdat = (struct SetAwasMsgNewData*)lParam;
			TranslateDialogDefault(hwndDlg);
			dat=(struct SetAwayMsgData*)mir_alloc(sizeof(struct SetAwayMsgData));
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
			dat->statusMode=newdat->statusMode;
			dat->szProto=newdat->szProto;
			mir_free(newdat);
			SendDlgItemMessage(hwndDlg,IDC_MSG,EM_LIMITTEXT,1024,0);
			OldMessageEditProc=(WNDPROC)SetWindowLong(GetDlgItem(hwndDlg,IDC_MSG),GWL_WNDPROC,(LONG)MessageEditSubclassProc);
			{	TCHAR str[256],format[128];
				GetWindowText( hwndDlg, format, SIZEOF( format ));
				mir_sntprintf( str, SIZEOF(str), format, CallService( MS_CLIST_GETSTATUSMODEDESCRIPTION, dat->statusMode, GCMDF_TCHAR ));
				SetWindowText( hwndDlg, str );
			}
			GetDlgItemTextA(hwndDlg,IDOK,dat->okButtonFormat,SIZEOF(dat->okButtonFormat));
			{	char *msg=(char*)GetAwayMessage((WPARAM)dat->statusMode,0);
				SetDlgItemTextA(hwndDlg,IDC_MSG,msg);
				mir_free(msg);
			}
			dat->countdown=5;
			SendMessage(hwndDlg,WM_TIMER,0,0);
			SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadSkinnedProtoIcon(dat->szProto, dat->statusMode));
			SetTimer(hwndDlg,1,1000,0);
			dat->hPreshutdown=HookEventMessage(ME_SYSTEM_PRESHUTDOWN,hwndDlg,DM_SRAWAY_SHUTDOWN);
			return TRUE;
		}
		case WM_TIMER:
			if(dat->countdown==-1) {DestroyWindow(hwndDlg); break;}
			{	char str[64];
				mir_snprintf(str,SIZEOF(str),dat->okButtonFormat,dat->countdown);
				SetDlgItemTextA(hwndDlg,IDOK,str);
			}
			dat->countdown--;
			break;
		case WM_COMMAND:
			switch(LOWORD(wParam)) {
				case IDOK:
				case IDCANCEL:
					DestroyWindow(hwndDlg);
					break;
				case IDC_MSG:
					KillTimer(hwndDlg,1);
					SetDlgItemText(hwndDlg,IDOK,TranslateT("OK"));
					break;
			}
			break;
		case DM_SRAWAY_SHUTDOWN:
			DestroyWindow(hwndDlg);
			break;
		case WM_DESTROY:
			{	char str[1024];
				GetDlgItemTextA(hwndDlg,IDC_MSG,str,SIZEOF(str));
				ChangeAllProtoMessages(dat->szProto,dat->statusMode,str);
				DBWriteContactSettingString(NULL,"SRAway",StatusModeToDbSetting(dat->statusMode,"Msg"),str);
			}
			SetWindowLong(GetDlgItem(hwndDlg,IDC_MSG),GWL_WNDPROC,(LONG)OldMessageEditProc);
			mir_free(dat);
			break;
	}
	return FALSE;
}

static int StatusModeChange(WPARAM wParam,LPARAM lParam)
{
	BOOL bScreenSaverRunning=FALSE;
	char *szProto = (char*)lParam;
	
	// If its a global change check the complete PFLAGNUM_3 flags to see if a popup might be needed
	if(!szProto) {
		if(!(protoModeMsgFlags&Proto_Status2Flag(wParam)))
			return 0;
	}
	// If its a single protocol check the PFLAGNUM_3 for the single protocol
	else if (!(CallProtoService(szProto,PS_GETCAPS,PFLAGNUM_1,0)&PF1_MODEMSGSEND)||!(CallProtoService(szProto,PS_GETCAPS,PFLAGNUM_3,0)&Proto_Status2Flag(wParam)))
		return 0;
	SystemParametersInfo(SPI_GETSCREENSAVERRUNNING,0,&bScreenSaverRunning,FALSE);
	if(DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(wParam,"Ignore"),0)) {
		ChangeAllProtoMessages((char*)lParam,wParam,NULL);
	}
	else if(bScreenSaverRunning || DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(wParam,"NoDlg"),0)) {
		char *msg=(char*)GetAwayMessage(wParam, 0);
		ChangeAllProtoMessages((char*)lParam,wParam,msg);
		mir_free(msg);
	}
	else {
		struct SetAwasMsgNewData *newdat = (struct SetAwasMsgNewData*)mir_alloc(sizeof(struct SetAwasMsgNewData));
		newdat->szProto = (char*)lParam;
		newdat->statusMode = (int)wParam;
		CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_SETAWAYMSG),NULL,SetAwayMsgDlgProc,(LPARAM)newdat);
	}
	return 0;
}

static int statusModes[]={ID_STATUS_OFFLINE,ID_STATUS_ONLINE,ID_STATUS_AWAY,ID_STATUS_NA,ID_STATUS_OCCUPIED,ID_STATUS_DND,ID_STATUS_FREECHAT,ID_STATUS_INVISIBLE,ID_STATUS_OUTTOLUNCH,ID_STATUS_ONTHEPHONE, ID_STATUS_IDLE};

struct AwayMsgInfo {
	int ignore;
	int noDialog;
	int usePrevious;
	char msg[1024];
};
struct AwayMsgDlgData {
	struct AwayMsgInfo info[ SIZEOF(statusModes) ];
	int oldPage;
};
static BOOL CALLBACK DlgProcAwayMsgOpts(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct AwayMsgDlgData *dat;

	dat=(struct AwayMsgDlgData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch (msg)
	{
		case WM_INITDIALOG:
		{	int i,j;
			DBVARIANT dbv;
			TranslateDialogDefault(hwndDlg);
			dat = (struct AwayMsgDlgData*)mir_alloc(sizeof(struct AwayMsgDlgData));
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
			dat->oldPage=-1;
			for ( i=0; i < SIZEOF(statusModes); i++ ) {
				if ( !(protoModeMsgFlags & Proto_Status2Flag( statusModes[i] )))
					continue;

				j = SendDlgItemMessage( hwndDlg, IDC_STATUS, CB_ADDSTRING, 0, 
					CallService( MS_CLIST_GETSTATUSMODEDESCRIPTION, statusModes[i], GCMDF_TCHAR ));

				SendDlgItemMessage(hwndDlg,IDC_STATUS,CB_SETITEMDATA,j,statusModes[i]);
				dat->info[j].ignore=DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(statusModes[i],"Ignore"),0);
				dat->info[j].noDialog=DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(statusModes[i],"NoDlg"),0);
				dat->info[j].usePrevious=DBGetContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(statusModes[i],"UsePrev"),0);
				if(DBGetContactSetting(NULL,"SRAway",StatusModeToDbSetting(statusModes[i],"Default"),&dbv))
					if(DBGetContactSetting(NULL,"SRAway",StatusModeToDbSetting(statusModes[i],"Msg"),&dbv))
						dbv.pszVal=mir_strdup(GetDefaultMessage(statusModes[i]));
				lstrcpyA(dat->info[j].msg,dbv.pszVal);
				mir_free(dbv.pszVal);
			}
			SendDlgItemMessage(hwndDlg,IDC_STATUS,CB_SETCURSEL,0,0);
			SendMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDC_STATUS,CBN_SELCHANGE),0);
			return TRUE;
		}
		case WM_COMMAND:
			switch(LOWORD(wParam)) {
				case IDC_STATUS:
					if(HIWORD(wParam)==CBN_SELCHANGE) {
						int i=SendDlgItemMessage(hwndDlg,IDC_STATUS,CB_GETCURSEL,0,0);
						if(dat->oldPage!=-1) {
							dat->info[dat->oldPage].ignore=IsDlgButtonChecked(hwndDlg,IDC_DONTREPLY);
							dat->info[dat->oldPage].noDialog=IsDlgButtonChecked(hwndDlg,IDC_NODIALOG);
							dat->info[dat->oldPage].usePrevious=IsDlgButtonChecked(hwndDlg,IDC_USEPREVIOUS);
							GetDlgItemTextA(hwndDlg,IDC_MSG,dat->info[dat->oldPage].msg,SIZEOF(dat->info[dat->oldPage].msg));
						}
						CheckDlgButton(hwndDlg,IDC_DONTREPLY,i<0?0:dat->info[i].ignore);
						CheckDlgButton(hwndDlg,IDC_NODIALOG,i<0?0:dat->info[i].noDialog);
						CheckDlgButton(hwndDlg,IDC_USEPREVIOUS,i<0?0:dat->info[i].usePrevious);
						CheckDlgButton(hwndDlg,IDC_USESPECIFIC,i<0?0:!dat->info[i].usePrevious);
						SetDlgItemTextA(hwndDlg,IDC_MSG,i<0?"":dat->info[i].msg);
						EnableWindow(GetDlgItem(hwndDlg,IDC_NODIALOG),i<0?0:!dat->info[i].ignore);
						EnableWindow(GetDlgItem(hwndDlg,IDC_USEPREVIOUS),i<0?0:!dat->info[i].ignore);
						EnableWindow(GetDlgItem(hwndDlg,IDC_USESPECIFIC),i<0?0:!dat->info[i].ignore);
						EnableWindow(GetDlgItem(hwndDlg,IDC_MSG),i<0?0:!(dat->info[i].ignore || dat->info[i].usePrevious));
						dat->oldPage=i;
					}
					return 0;
				case IDC_DONTREPLY:
				case IDC_USEPREVIOUS:
				case IDC_USESPECIFIC:
					SendMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDC_STATUS,CBN_SELCHANGE),0);
					break;
				case IDC_MSG:
					if(HIWORD(wParam)!=EN_CHANGE || (HWND)lParam!=GetFocus()) return 0;
					break;
			}
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		case WM_NOTIFY:
			switch(((LPNMHDR)lParam)->idFrom) {
				case 0:
					switch(((LPNMHDR)lParam)->code) {
						case PSN_APPLY:
						{	int i,status;
							SendMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDC_STATUS,CBN_SELCHANGE),0);
							for(i=SendDlgItemMessage(hwndDlg,IDC_STATUS,CB_GETCOUNT,0,0)-1;i>=0;i--) {
								status=SendDlgItemMessage(hwndDlg,IDC_STATUS,CB_GETITEMDATA,i,0);
								DBWriteContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(status,"Ignore"),(BYTE)dat->info[i].ignore);
								DBWriteContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(status,"NoDlg"),(BYTE)dat->info[i].noDialog);
								DBWriteContactSettingByte(NULL,"SRAway",StatusModeToDbSetting(status,"UsePrev"),(BYTE)dat->info[i].usePrevious);
								DBWriteContactSettingString(NULL,"SRAway",StatusModeToDbSetting(status,"Default"),dat->info[i].msg);
							}
							return TRUE;
						}
					}
					break;
			}
			break;
		case WM_DESTROY:
			mir_free(dat);
			break;
	}
	return FALSE;
}

static int AwayMsgOptInitialise(WPARAM wParam,LPARAM lParam)
{
	OPTIONSDIALOGPAGE odp = { 0 };
	odp.cbSize = sizeof(odp);
	odp.position = 870000000;
	odp.hInstance = GetModuleHandle(NULL);
	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_AWAYMSG);
	odp.pszTitle = "Status Messages";
	odp.pszGroup = "Status";
	odp.pfnDlgProc = DlgProcAwayMsgOpts;
	odp.flags = ODPF_BOLDGROUPS;
	CallService( MS_OPT_ADDPAGE, wParam, ( LPARAM )&odp );
	return 0;
}

static int AwayMsgSendModulesLoaded(WPARAM wParam,LPARAM lParam)
{
	int i,protoCount;
	PROTOCOLDESCRIPTOR **proto;

	protoModeMsgFlags=0;
	CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&proto);
	for(i=0;i<protoCount;i++)
		protoModeMsgFlags|=CallProtoService(proto[i]->szName,PS_GETCAPS,PFLAGNUM_3,0);
	if(protoModeMsgFlags) {
		HookEvent(ME_CLIST_STATUSMODECHANGE,StatusModeChange);
		HookEvent(ME_OPT_INITIALISE,AwayMsgOptInitialise);
	}
	return 0;
}

int LoadAwayMessageSending(void)
{
	HookEvent(ME_SYSTEM_MODULESLOADED,AwayMsgSendModulesLoaded);
	CreateServiceFunction(MS_AWAYMSG_GETSTATUSMSG, GetAwayMessage);
	return 0;
}
