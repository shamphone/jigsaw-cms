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
#include <m_url.h>
#include "url.h"

HANDLE hUrlWindowList = NULL;
static HANDLE hEventContactSettingChange = NULL;
HANDLE hContactDeleted=NULL;
HANDLE *hUrlContactMenu=NULL;
int hUrlContactMenuCount=0;

BOOL CALLBACK DlgProcUrlSend(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);
BOOL CALLBACK DlgProcUrlRecv(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);

static int ReadUrlCommand(WPARAM wParam,LPARAM lParam)
{
	CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_URLRECV),NULL,DlgProcUrlRecv,lParam);
	return 0;
}

static int UrlEventAdded(WPARAM wParam,LPARAM lParam)
{
	CLISTEVENT cle;
	DBEVENTINFO dbei;
	char *contactName;
	char szTooltip[256];

	ZeroMemory(&dbei,sizeof(dbei));
	dbei.cbSize=sizeof(dbei);
	dbei.cbBlob=0;
	CallService(MS_DB_EVENT_GET,lParam,(LPARAM)&dbei);
	if(dbei.flags&(DBEF_SENT|DBEF_READ) || dbei.eventType!=EVENTTYPE_URL) return 0;

	SkinPlaySound("RecvUrl");
	ZeroMemory(&cle,sizeof(cle));
	cle.cbSize=sizeof(cle);
	cle.hContact=(HANDLE)wParam;
	cle.hDbEvent=(HANDLE)lParam;
	cle.hIcon=LoadSkinnedIcon(SKINICON_EVENT_URL);
	cle.pszService="SRUrl/ReadUrl";
	contactName=(char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,wParam,0);
	mir_snprintf(szTooltip,SIZEOF(szTooltip),Translate("URL from %s"),contactName);
	cle.pszTooltip=szTooltip;
	CallService(MS_CLIST_ADDEVENT,0,(LPARAM)&cle);
	return 0;
}

static int SendUrlCommand(WPARAM wParam,LPARAM lParam)
{
	CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_URLSEND),NULL,DlgProcUrlSend,wParam);
	return 0;
}

static void RestoreUnreadUrlAlerts(void)
{
	CLISTEVENT cle={0};
	DBEVENTINFO dbei={0};
	char toolTip[256];
	HANDLE hDbEvent,hContact;

	dbei.cbSize=sizeof(dbei);
	cle.cbSize=sizeof(cle);
	cle.hIcon=LoadSkinnedIcon(SKINICON_EVENT_URL);
	cle.pszService="SRUrl/ReadUrl";

	hContact=(HANDLE)CallService(MS_DB_CONTACT_FINDFIRST,0,0);
	while(hContact) {
		hDbEvent=(HANDLE)CallService(MS_DB_EVENT_FINDFIRSTUNREAD,(WPARAM)hContact,0);
		while(hDbEvent) {
			dbei.cbBlob=0;
			CallService(MS_DB_EVENT_GET,(WPARAM)hDbEvent,(LPARAM)&dbei);
			if(!(dbei.flags&(DBEF_SENT|DBEF_READ)) && dbei.eventType==EVENTTYPE_URL) {
				cle.hContact=hContact;
				cle.hDbEvent=hDbEvent;
				mir_snprintf(toolTip,SIZEOF(toolTip),Translate("URL from %s"),(char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)hContact,0));
				cle.pszTooltip=toolTip;
				CallService(MS_CLIST_ADDEVENT,0,(LPARAM)&cle);
			}
			hDbEvent=(HANDLE)CallService(MS_DB_EVENT_FINDNEXT,(WPARAM)hDbEvent,0);
		}
		hContact=(HANDLE)CallService(MS_DB_CONTACT_FINDNEXT,(WPARAM)hContact,0);
	}
}

static int ContactSettingChanged(WPARAM wParam, LPARAM lParam)
{
	DBCONTACTWRITESETTING *cws=(DBCONTACTWRITESETTING*)lParam;
	char *szProto;

	szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,wParam,0);
	if(lstrcmpA(cws->szModule,"CList") && (szProto==NULL || lstrcmpA(cws->szModule,szProto))) return 0;
	WindowList_Broadcast(hUrlWindowList,DM_UPDATETITLE,0,0);
	return 0;
}

static int SRUrlModulesLoaded(WPARAM wParam,LPARAM lParam)
{
	CLISTMENUITEM mi;
	PROTOCOLDESCRIPTOR **protocol;
	int protoCount,i;

	ZeroMemory(&mi,sizeof(mi));
	mi.cbSize=sizeof(mi);
	mi.position=-2000040000;
	mi.flags=0;
	mi.hIcon=LoadSkinnedIcon(SKINICON_EVENT_URL);
	mi.pszName=Translate("Web Page Address (&URL)");
	mi.pszService=MS_URL_SENDURL;
	CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protocol);
	for(i=0;i<protoCount;i++) {
		if(protocol[i]->type!=PROTOTYPE_PROTOCOL) continue;
		if(CallProtoService(protocol[i]->szName,PS_GETCAPS,PFLAGNUM_1,0)&PF1_URLSEND) {
			mi.pszContactOwner=protocol[i]->szName;
			hUrlContactMenu=mir_realloc(hUrlContactMenu,(hUrlContactMenuCount+1)*sizeof(HANDLE));
			hUrlContactMenu[hUrlContactMenuCount++]=(HANDLE)CallService(MS_CLIST_ADDCONTACTMENUITEM,0,(LPARAM)&mi);
		}
	}
	RestoreUnreadUrlAlerts();
	return 0;
}

static int UrlMenuIconChanged(WPARAM wParam, LPARAM lParam)
{

	if (hUrlContactMenu) {
		
		int j; 
		CLISTMENUITEM mi;

		mi.cbSize=sizeof(mi);
		mi.flags=CMIM_ICON;
		mi.hIcon=LoadSkinnedIcon(SKINICON_EVENT_URL);

		for (j=0; j<hUrlContactMenuCount; j++) {		
			CallService(MS_CLIST_MODIFYMENUITEM,(WPARAM)hUrlContactMenu[j],(LPARAM)&mi);
		}

	}
	return 0;
}

static int SRUrlShutdown(WPARAM wParam,LPARAM lParam)
{

	if (hEventContactSettingChange)	UnhookEvent(hEventContactSettingChange);
	if (hContactDeleted) UnhookEvent(hContactDeleted);
	if (hUrlWindowList) {		
		WindowList_BroadcastAsync(hUrlWindowList,WM_CLOSE,0,0);
	}
	if (hUrlContactMenu) {
		mir_free(hUrlContactMenu); hUrlContactMenu=NULL;
		hUrlContactMenuCount=0;
	}
	return 0;

}

int UrlContactDeleted(WPARAM wParam, LPARAM lParam)
{
	HWND h;
	h=WindowList_Find(hUrlWindowList,(HANDLE)wParam);
	if (h) {
		SendMessage(h,WM_CLOSE,0,0);
	}
	return 0;
}

int LoadSendRecvUrlModule(void)
{
	hUrlWindowList=(HANDLE)CallService(MS_UTILS_ALLOCWINDOWLIST,0,0);
	HookEvent(ME_SYSTEM_MODULESLOADED,SRUrlModulesLoaded);
	HookEvent(ME_DB_EVENT_ADDED,UrlEventAdded);
	HookEvent(ME_SKIN_ICONSCHANGED,UrlMenuIconChanged);
	hEventContactSettingChange = HookEvent(ME_DB_CONTACT_SETTINGCHANGED, ContactSettingChanged);
	hContactDeleted = HookEvent(ME_DB_CONTACT_DELETED, UrlContactDeleted);
	HookEvent(ME_SYSTEM_PRESHUTDOWN,SRUrlShutdown);
	CreateServiceFunction(MS_URL_SENDURL,SendUrlCommand);
	CreateServiceFunction("SRUrl/ReadUrl",ReadUrlCommand);
	SkinAddNewSoundEx("RecvUrl",Translate("URL"),Translate("Incoming"));
	return 0;
}

