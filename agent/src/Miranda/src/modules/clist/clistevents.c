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
#include "clc.h"
#include "../database/dblists.h"

struct CListEvent
{
	int imlIconIndex;
	int flashesDone;
	CLISTEVENT cle;
};

struct CListImlIcon
{
	int index;
	HICON hIcon;
};
static struct CListImlIcon *imlIcon;
static int imlIconCount;

extern HIMAGELIST hCListImages;

static UINT flashTimerId;
static int iconsOn;
static int disableTrayFlash;
static int disableIconFlash;

int fnGetImlIconIndex(HICON hIcon)
{
	int i;

	for (i = 0; i < imlIconCount; i++) {
		if (imlIcon[i].hIcon == hIcon)
			return imlIcon[i].index;
	}
	imlIcon = (struct CListImlIcon *) mir_realloc(imlIcon, sizeof(struct CListImlIcon) * (imlIconCount + 1));
	imlIconCount++;
	imlIcon[i].hIcon = hIcon;
	imlIcon[i].index = ImageList_AddIcon(hCListImages, hIcon);
	return imlIcon[i].index;
}

static VOID CALLBACK IconFlashTimer(HWND hwnd, UINT message, UINT idEvent, DWORD dwTime)
{
	int i, j;

	if (cli.events.count) {
		char *szProto;
		if (cli.events.items[0]->cle.hContact == NULL)
			szProto = NULL;
		else
			szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) cli.events.items[0]->cle.hContact, 0);
		cli.pfnTrayIconUpdateWithImageList((iconsOn || disableTrayFlash) ? cli.events.items[0]->imlIconIndex : 0, cli.events.items[0]->cle.ptszTooltip, szProto);
	}
	for (i = 0; i < cli.events.count; i++) {
		for (j = 0; j < i; j++)
			if (cli.events.items[j]->cle.hContact == cli.events.items[i]->cle.hContact)
				break;
		if (j < i)
			continue;
		cli.pfnChangeContactIcon(cli.events.items[i]->cle.hContact, iconsOn || disableIconFlash ? cli.events.items[i]->imlIconIndex : 0, 0);
		if (cli.events.items[i]->cle.flags & CLEF_ONLYAFEW) {
			if (0 == --cli.events.items[i]->flashesDone)
				cli.pfnRemoveEvent( cli.events.items[i]->cle.hContact, cli.events.items[i]->cle.hDbEvent);
	}	}

	iconsOn = !iconsOn;
}

struct CListEvent* fnAddEvent( CLISTEVENT *cle )
{
	int i;
	struct CListEvent* p;

	if (cle==NULL || cle->cbSize != sizeof(CLISTEVENT))
		return NULL;

	if (cle->flags & CLEF_URGENT) {
		for (i = 0; i < cli.events.count; i++)
			if (!(cli.events.items[i]->cle.flags & CLEF_URGENT))
				break;
	}
	else i = cli.events.count;

	if (( p = ( struct CListEvent* )cli.pfnCreateEvent()) == NULL )
		return NULL;

	List_Insert(( SortedList* )&cli.events, p, i );
	p->cle = *cle;
	p->imlIconIndex = fnGetImlIconIndex(cli.events.items[i]->cle.hIcon);
	p->flashesDone = 12;
	p->cle.pszService = mir_strdup(cli.events.items[i]->cle.pszService);
	#if defined( _UNICODE )
		if (p->cle.flags & CLEF_UNICODE)
			p->cle.ptszTooltip = mir_tstrdup((TCHAR*)p->cle.ptszTooltip);
		else
			p->cle.ptszTooltip = a2u((char*)p->cle.pszTooltip); //if no flag defined it handled as unicode
	#else
		p->cle.ptszTooltip = mir_tstrdup(p->cle.ptszTooltip); 
	#endif

	if (cli.events.count == 1) {
		char *szProto;
		if (cle->hContact == NULL)
			szProto = NULL;
		else
			szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM)cle->hContact, 0);
		iconsOn = 1;
		flashTimerId = SetTimer(NULL, 0, DBGetContactSettingWord(NULL, "CList", "IconFlashTime", 550), IconFlashTimer);
		cli.pfnTrayIconUpdateWithImageList( p->imlIconIndex, p->cle.ptszTooltip, szProto);
	}
	cli.pfnChangeContactIcon(cle->hContact, p->imlIconIndex, 1);
	cli.pfnSortContacts();
	return p;
}

// Removes an event from the contact list's queue
// Returns 0 if the event was successfully removed, or nonzero if the event was not found
int fnRemoveEvent( HANDLE hContact, HANDLE dbEvent )
{
	int i;
	char *szProto;

	// Find the event that should be removed
	for (i = 0; i < cli.events.count; i++)
		if ((cli.events.items[i]->cle.hContact == hContact) && (cli.events.items[i]->cle.hDbEvent == dbEvent))
			break;

	// Event was not found
	if (i == cli.events.count)
		return 1;

	// Update contact's icon
	szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM)hContact, 0);
	cli.pfnChangeContactIcon(cli.events.items[i]->cle.hContact,
		cli.pfnIconFromStatusMode(szProto,
		szProto == NULL ? ID_STATUS_OFFLINE : DBGetContactSettingWord(cli.events.items[i]->cle.hContact, szProto, "Status",
		ID_STATUS_OFFLINE), cli.events.items[i]->cle.hContact), 0);

	// Free any memory allocated to the event
	cli.pfnFreeEvent( cli.events.items[i] );
	List_Remove(( SortedList* )&cli.events, i );

	if (cli.events.count == 0) {
		KillTimer(NULL, flashTimerId);
		cli.pfnTrayIconSetToBase( hContact == NULL ? NULL : szProto);
	}
	else {
		if (cli.events.items[0]->cle.hContact == NULL)
			szProto = NULL;
		else
			szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) cli.events.items[0]->cle.hContact, 0);
		cli.pfnTrayIconUpdateWithImageList(iconsOn ? cli.events.items[0]->imlIconIndex : 0, cli.events.items[0]->cle.ptszTooltip, szProto);
	}

	return 0;
}

CLISTEVENT* fnGetEvent( HANDLE hContact, int idx )
{
	int i;

	if ( hContact == INVALID_HANDLE_VALUE) {
		if (idx >= cli.events.count)
			return NULL;
		return &cli.events.items[idx]->cle;
	}

	for (i = 0; i < cli.events.count; i++)
		if (cli.events.items[i]->cle.hContact == hContact)
			if (idx-- == 0)
				return &cli.events.items[i]->cle;
	return NULL;
}

int fnEventsProcessContactDoubleClick(HANDLE hContact)
{
	int i;
	HANDLE hDbEvent;

	for (i = 0; i < cli.events.count; i++) {
		if (cli.events.items[i]->cle.hContact == hContact) {
			hDbEvent = cli.events.items[i]->cle.hDbEvent;
			CallService(cli.events.items[i]->cle.pszService, (WPARAM) (HWND) NULL, (LPARAM) & cli.events.items[i]->cle);
			cli.pfnRemoveEvent(hContact, hDbEvent);
			return 0;
		}
	}
	return 1;
}

int fnEventsProcessTrayDoubleClick(void)
{
	if (cli.events.count) {
		HANDLE hContact, hDbEvent;
		hContact = cli.events.items[0]->cle.hContact;
		hDbEvent = cli.events.items[0]->cle.hDbEvent;
		CallService(cli.events.items[0]->cle.pszService, (WPARAM) NULL, (LPARAM) & cli.events.items[0]->cle);
		cli.pfnRemoveEvent(hContact, hDbEvent);
		return 0;
	}
	return 1;
}

static int RemoveEventsForContact(WPARAM wParam, LPARAM lParam)
{
	int j, hit;

	/*
	the for(;;) loop is used here since the cli.events.count can not be relied upon to take us
	thru the cli.events.items[] array without suffering from shortsightedness about how many unseen
	events remain, e.g. three events, we remove the first, we're left with 2, the event
	loop exits at 2 and we never see the real new 2.
	*/

	for (; cli.events.count > 0;) {
		for (hit = 0, j = 0; j < cli.events.count; j++) {
			if (cli.events.items[j]->cle.hContact == (HANDLE) wParam) {
				cli.pfnRemoveEvent((HANDLE)wParam, cli.events.items[j]->cle.hDbEvent);
				hit = 1;
			}
		}
		if (j == cli.events.count && hit == 0)
			return 0;           /* got to the end of the array and didnt remove anything */
	}

	return 0;
}

static int CListEventSettingsChanged(WPARAM wParam, LPARAM lParam)
{
	HANDLE hContact = (HANDLE) wParam;
	DBCONTACTWRITESETTING *cws = (DBCONTACTWRITESETTING *) lParam;
	if (hContact == NULL && cws && cws->szModule && cws->szSetting && strcmp(cws->szModule, "CList") == 0) {
		if (strcmp(cws->szSetting, "DisableTrayFlash") == 0)
			disableTrayFlash = (int) cws->value.bVal;
		else if (strcmp(cws->szSetting, "NoIconBlink") == 0)
			disableIconFlash = (int) cws->value.bVal;
	}
	return 0;
}

/***************************************************************************************/

int AddEventStub(WPARAM wParam, LPARAM lParam) { return cli.pfnAddEvent((CLISTEVENT*)lParam ) == NULL; }
int RemoveEventStub(WPARAM wParam, LPARAM lParam) { return cli.pfnRemoveEvent((HANDLE)wParam,(HANDLE)lParam ); }
int GetEventStub(WPARAM wParam, LPARAM lParam) { return (int)cli.pfnGetEvent((HANDLE)wParam,lParam); }

int InitCListEvents(void)
{
	memset( &cli.events, 0, sizeof(cli.events));	
	cli.events.increment = 10;
	
	disableTrayFlash = DBGetContactSettingByte(NULL, "CList", "DisableTrayFlash", 0);
	disableIconFlash = DBGetContactSettingByte(NULL, "CList", "NoIconBlink", 0);
	CreateServiceFunction(MS_CLIST_ADDEVENT, AddEventStub);
	CreateServiceFunction(MS_CLIST_REMOVEEVENT, RemoveEventStub);
	CreateServiceFunction(MS_CLIST_GETEVENT, GetEventStub);
	HookEvent(ME_DB_CONTACT_DELETED, RemoveEventsForContact);
	HookEvent(ME_DB_CONTACT_SETTINGCHANGED, CListEventSettingsChanged);
	return 0;
}

struct CListEvent* fnCreateEvent( void )
{
	return (struct CListEvent*)mir_calloc( sizeof(struct CListEvent));
}

void fnFreeEvent( struct CListEvent* p )
{
   if ( p->cle.pszService )
      mir_free( p->cle.pszService );
   if ( p->cle.pszTooltip )
      mir_free( p->cle.pszTooltip );
	mir_free( p );
}

void UninitCListEvents(void)
{
	int i;
	for (i = 0; i < cli.events.count; i++)
		cli.pfnFreeEvent(( struct CListEvent* )cli.events.items[i] );
	mir_free( cli.events.items );

	if ( imlIcon != NULL )
		mir_free( imlIcon );
}
