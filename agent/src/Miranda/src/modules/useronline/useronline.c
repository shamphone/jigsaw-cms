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

static int uniqueEventId=0;

static int UserOnlineSettingChanged(WPARAM wParam,LPARAM lParam)
{
	DBCONTACTWRITESETTING *cws=(DBCONTACTWRITESETTING*)lParam;
	int newStatus,oldStatus;

	if((HANDLE)wParam==NULL || strcmp(cws->szSetting,"Status")) return 0;
	newStatus=cws->value.wVal;
	oldStatus=DBGetContactSettingWord((HANDLE)wParam,"UserOnline","OldStatus",ID_STATUS_OFFLINE);
	DBWriteContactSettingWord((HANDLE)wParam,"UserOnline","OldStatus",(WORD)newStatus);
	if(CallService(MS_IGNORE_ISIGNORED,wParam,IGNOREEVENT_USERONLINE)) return 0;
	if(DBGetContactSettingByte((HANDLE)wParam,"CList","Hidden",0)) return 0;
	if((newStatus==ID_STATUS_ONLINE || newStatus==ID_STATUS_FREECHAT) &&
	   oldStatus!=ID_STATUS_ONLINE && oldStatus!=ID_STATUS_FREECHAT) {
		{
			DWORD ticked = db_dword_get(NULL, "UserOnline", cws->szModule, GetTickCount());
			// only play the sound (or show event) if this event happens at least 10 secs after the proto went from offline
			if ( GetTickCount() - ticked > (1000*10) ) { 
				CLISTEVENT cle;
				char tooltip[256];

				ZeroMemory(&cle,sizeof(cle));
				cle.cbSize=sizeof(cle);
				cle.flags=CLEF_ONLYAFEW;
				cle.hContact=(HANDLE)wParam;
				cle.hDbEvent=(HANDLE)(uniqueEventId++);
				cle.hIcon=LoadSkinnedIcon(SKINICON_OTHER_USERONLINE);
				cle.pszService="UserOnline/Description";
				mir_snprintf(tooltip,SIZEOF(tooltip),Translate("%s is Online"),(char*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,wParam,0));
				cle.pszTooltip=tooltip;
				CallService(MS_CLIST_ADDEVENT,0,(LPARAM)&cle);

				SkinPlaySound("UserOnline");
			}
		}
	}
    if( oldStatus != newStatus )
	{
		CallService(MS_CLUI_UPDATECONTACT_TO_FLVCC, wParam, newStatus);
	}
	return 0;
}

static int UserOnlineAck(WPARAM wParam, LPARAM lParam)
{
	ACKDATA * ack = (ACKDATA*) lParam;
	if ( ack != 0 && ack->szModule && ack->type == ACKTYPE_STATUS && ack->result == ACKRESULT_SUCCESS && ack->hProcess == (HANDLE)ID_STATUS_OFFLINE) {
		// if going from offline to any other mode, remember when it happened.
		db_dword_set(NULL, "UserOnline", ack->szModule, GetTickCount());
	}
	return 0;
}

static int UserOnlineModulesLoaded(WPARAM wParam, LPARAM lParam)
{
	int protoCount=0, j;
	PROTOCOLDESCRIPTOR **protos = 0;

	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM)&protoCount, (LPARAM)&protos);
	// reset the counter
	for (j=0 ; j < protoCount; j++) 
		if (protos[j]->type == PROTOTYPE_PROTOCOL) {
			db_dword_set(NULL, "UserOnline", protos[j]->szName, GetTickCount());
		}
	return 0;
}

int LoadUserOnlineModule(void)
{
	HookEvent(ME_DB_CONTACT_SETTINGCHANGED,UserOnlineSettingChanged);
	HookEvent(ME_PROTO_ACK, UserOnlineAck);
	HookEvent(ME_SYSTEM_MODULESLOADED, UserOnlineModulesLoaded);
	SkinAddNewSoundEx("UserOnline",Translate("Alerts"),Translate("Online"));
	return 0;
}
