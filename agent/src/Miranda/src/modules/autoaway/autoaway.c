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

#define AA_MODULE "AutoAway"

static void AutoAwaySetProtocol(const char * proto, unsigned status)
{
	char * awayMsg = (char *) CallService(MS_AWAYMSG_GETSTATUSMSG, (WPARAM) status, 0);								
	CallProtoService(proto, PS_SETSTATUS, status, 0);
	if ( awayMsg != NULL )  {
		if (CallProtoService(proto,PS_GETCAPS,PFLAGNUM_1,0)&PF1_MODEMSGSEND)
		CallProtoService(proto, PS_SETAWAYMSG, status, (LPARAM) awayMsg);
		miranda_sys_free(awayMsg);
	}
}

static int AutoAwayEvent(WPARAM wParam, LPARAM lParam)
{
	PROTOCOLDESCRIPTOR **proto=0;
	int protoCount=0;
	int j;
	MIRANDA_IDLE_INFO mii;
	int status;

	mii.cbSize = sizeof(mii);
	CallService(MS_IDLE_GETIDLEINFO, 0, (LPARAM)&mii);
	if (mii.aaStatus==0) return 0;
	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM)&protoCount, (LPARAM)&proto);
	for (j=0; j<protoCount; j++) {
		if ( proto[j]->type == PROTOTYPE_PROTOCOL )  {
			int statusbits = CallProtoService(proto[j]->szName, PS_GETCAPS, PFLAGNUM_2, 0);
			int currentstatus = CallProtoService(proto[j]->szName, PS_GETSTATUS, 0, 0);
			status = mii.aaStatus;
			if ( !(statusbits & Proto_Status2Flag(status)) ) {
				// the protocol doesnt support the given status
				if ( statusbits & Proto_Status2Flag(ID_STATUS_AWAY) ) status=ID_STATUS_AWAY;
				else {
					// the proto doesnt support user mode or even away, bail.
					continue;
				}
			}
			if ( currentstatus >= ID_STATUS_ONLINE && currentstatus != ID_STATUS_INVISIBLE ) {			
				if ( (lParam&IDF_ISIDLE) && ( currentstatus == ID_STATUS_ONLINE || currentstatus == ID_STATUS_FREECHAT ))  {
					DBWriteContactSettingByte(NULL,AA_MODULE,proto[j]->szName,1);
					AutoAwaySetProtocol(proto[j]->szName, status);
				} else if ( !(lParam&IDF_ISIDLE) && DBGetContactSettingByte(NULL,AA_MODULE,proto[j]->szName,0) ) {
					// returning from idle and this proto was set away, set it back
					DBWriteContactSettingByte(NULL,AA_MODULE,proto[j]->szName,0);
					if ( !mii.aaLock ) AutoAwaySetProtocol(proto[j]->szName, ID_STATUS_ONLINE);
	}	}	}	}

	return 0;
}

int LoadAutoAwayModule(void)
{
	HookEvent(ME_IDLE_CHANGED, AutoAwayEvent);
	return 0;
}


