/*
SRMM

Copyright 2000-2005 Miranda ICQ/IM project, 
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

struct GlobalMessageData *g_dat=NULL;
extern HINSTANCE g_hInst;
static HANDLE g_hDbEvent = 0, g_hAck = 0;
static int dbaddedevent(WPARAM wParam, LPARAM lParam);
static int ackevent(WPARAM wParam, LPARAM lParam);

void InitGlobals() {
	g_dat = (struct GlobalMessageData *)malloc(sizeof(struct GlobalMessageData));
	g_dat->hMessageWindowList = (HANDLE) CallService(MS_UTILS_ALLOCWINDOWLIST, 0, 0);
	g_hDbEvent = HookEvent(ME_DB_EVENT_ADDED, dbaddedevent);
	g_hAck = HookEvent(ME_PROTO_ACK, ackevent);
	ReloadGlobals();
	g_dat->hIcons[SMF_ICON_ADD] = (HICON) LoadImage(g_hInst, MAKEINTRESOURCE(IDI_ADDCONTACT), IMAGE_ICON, GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), 0);
	g_dat->hIcons[SMF_ICON_USERDETAIL] = (HICON) LoadImage(g_hInst, MAKEINTRESOURCE(IDI_USERDETAILS), IMAGE_ICON, GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), 0);
	g_dat->hIcons[SMF_ICON_HISTORY] = (HICON) LoadImage(g_hInst, MAKEINTRESOURCE(IDI_HISTORY), IMAGE_ICON, GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), 0);
	g_dat->hIcons[SMF_ICON_ARROW] = (HICON) LoadImage(g_hInst, MAKEINTRESOURCE(IDI_DOWNARROW), IMAGE_ICON, GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), 0);
	g_dat->hIcons[SMF_ICON_TYPING] = (HICON) LoadImage(g_hInst, MAKEINTRESOURCE(IDI_TYPING), IMAGE_ICON, GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), 0);
}

void FreeGlobals() {
	int i;

	if (g_dat) {
		for (i=0; i < SIZEOF(g_dat->hIcons); i++)
			DestroyIcon(g_dat->hIcons[i]);
		free(g_dat);
	}
	if (g_hDbEvent) UnhookEvent(g_hDbEvent);
	if (g_hAck) UnhookEvent(g_hAck);
}

void ReloadGlobals() {
	g_dat->flags = 0;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWINFOLINE, SRMSGDEFSET_SHOWINFOLINE))
		g_dat->flags |= SMF_SHOWINFO;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWBUTTONLINE, SRMSGDEFSET_SHOWBUTTONLINE))
		g_dat->flags |= SMF_SHOWBTNS;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDBUTTON, SRMSGDEFSET_SENDBUTTON))
		g_dat->flags |= SMF_SENDBTN;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPING, SRMSGDEFSET_SHOWTYPING))
		g_dat->flags |= SMF_SHOWTYPING;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGWIN, SRMSGDEFSET_SHOWTYPINGWIN))
		g_dat->flags |= SMF_SHOWTYPINGWIN;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGNOWIN, SRMSGDEFSET_SHOWTYPINGNOWIN))
		g_dat->flags |= SMF_SHOWTYPINGTRAY;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGCLIST, SRMSGDEFSET_SHOWTYPINGCLIST))
		g_dat->flags |= SMF_SHOWTYPINGCLIST;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWLOGICONS, SRMSGDEFSET_SHOWLOGICONS))
		g_dat->flags |= SMF_SHOWICONS;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTIME, SRMSGDEFSET_SHOWTIME))
		g_dat->flags |= SMF_SHOWTIME;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AVATARENABLE, SRMSGDEFSET_AVATARENABLE))
		g_dat->flags |= SMF_AVATAR;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWDATE, SRMSGDEFSET_SHOWDATE))
		g_dat->flags |= SMF_SHOWDATE;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWSECS, SRMSGDEFSET_SHOWSECS))
		g_dat->flags |= SMF_SHOWSECS;
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_HIDENAMES, SRMSGDEFSET_HIDENAMES))
		g_dat->flags |= SMF_HIDENAMES;
	g_dat->openFlags = DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_POPFLAGS, SRMSGDEFSET_POPFLAGS);
}

static int dbaddedevent(WPARAM wParam, LPARAM lParam) {
	if (wParam) {
		HWND h = WindowList_Find(g_dat->hMessageWindowList, (HANDLE)wParam);
		if(h) SendMessage(h, HM_DBEVENTADDED, wParam, lParam);
	}
	return 0;
}

static int ackevent(WPARAM wParam, LPARAM lParam) {
	ACKDATA *pAck = (ACKDATA *)lParam;
	
	if (!pAck) return 0;
	else if (pAck->type==ACKTYPE_AVATAR) {
		HWND h = WindowList_Find(g_dat->hMessageWindowList, (HANDLE)pAck->hContact);
		if(h) SendMessage(h, HM_AVATARACK, wParam, lParam);
	}
	else if (pAck->type==ACKTYPE_MESSAGE) {
		HWND h = WindowList_Find(g_dat->hMessageWindowList, (HANDLE)pAck->hContact);
		if(h) SendMessage(h, HM_EVENTSENT, wParam, lParam);
	}
	return 0;
}
