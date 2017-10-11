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

#define MS_AUTH_SHOWREQUEST	"Auth/ShowRequest"
#define MS_AUTH_SHOWADDED	"Auth/ShowAdded"

BOOL CALLBACK DlgProcAuthReq(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);
BOOL CALLBACK DlgProcAdded(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);


int ShowReqWindow(WPARAM wParam,LPARAM lParam)
{
	CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_AUTHREQ),NULL,DlgProcAuthReq,(LPARAM)((CLISTEVENT *)lParam)->hDbEvent);
	return 0;
}

int ShowAddedWindow(WPARAM wParam,LPARAM lParam)
{
	CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_ADDED),NULL,DlgProcAdded,(LPARAM)((CLISTEVENT *)lParam)->hDbEvent);
	return 0;
}

static int AuthEventAdded(WPARAM wParam,LPARAM lParam)
{
	DBEVENTINFO dbei;
	CLISTEVENT cli;
	char szTooltip[256];
	HANDLE hcontact;

	ZeroMemory(&dbei,sizeof(dbei));
	dbei.cbSize=sizeof(dbei);
	dbei.cbBlob=0;
	CallService(MS_DB_EVENT_GET,(WPARAM)lParam,(LPARAM)&dbei);
	if(dbei.flags&(DBEF_SENT|DBEF_READ) || (dbei.eventType!=EVENTTYPE_AUTHREQUEST && dbei.eventType!=EVENTTYPE_ADDED)) return 0;

	dbei.cbBlob=CallService(MS_DB_EVENT_GETBLOBSIZE,(WPARAM)lParam,0);
	dbei.pBlob=mir_alloc(dbei.cbBlob);
	CallService(MS_DB_EVENT_GET,(WPARAM)(HANDLE)lParam,(LPARAM)&dbei);

	hcontact=*((PHANDLE)(dbei.pBlob+sizeof(DWORD)));

	ZeroMemory(&cli,sizeof(cli));
	cli.cbSize=sizeof(cli);
	cli.hContact=hcontact;
	cli.pszTooltip=szTooltip;
	cli.lParam=lParam;
	cli.hDbEvent=(HANDLE)lParam;

	if(dbei.eventType==EVENTTYPE_AUTHREQUEST)
	{
		mir_snprintf(szTooltip,256,Translate("%u requests authorization"),*((PDWORD)dbei.pBlob));

		cli.hIcon=LoadSkinnedIcon(SKINICON_OTHER_MIRANDA);
		cli.pszService=MS_AUTH_SHOWREQUEST;
		//CallService(MS_CLIST_ADDEVENT,0,(LPARAM)&cli);
		CallService(MS_CLUI_SENDAUTHREQUEST_TO_FLVCC, (WPARAM)hcontact, lParam);
		mir_free(dbei.pBlob);
	}
	else if(dbei.eventType==EVENTTYPE_ADDED)
	{
		mir_snprintf(szTooltip,256,Translate("%u added you to their contact list"),*((PDWORD)dbei.pBlob));

		cli.hIcon=LoadSkinnedIcon(SKINICON_OTHER_MIRANDA);
		cli.pszService=MS_AUTH_SHOWADDED;
		CallService(MS_CLIST_ADDEVENT,0,(LPARAM)&cli);
		mir_free(dbei.pBlob);
	}
	return 0;
}

int LoadSendRecvAuthModule(void)
{
	CreateServiceFunction(MS_AUTH_SHOWREQUEST,ShowReqWindow);
	CreateServiceFunction(MS_AUTH_SHOWADDED,ShowAddedWindow);
	HookEvent(ME_DB_EVENT_ADDED,AuthEventAdded);
	return 0;
} 
