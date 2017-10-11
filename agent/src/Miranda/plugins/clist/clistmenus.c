/*

Miranda IM: the free IM client for Microsoft* Windows*

Copyright 2000-2003 Miranda ICQ/IM project, 
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

#define FIRSTCUSTOMMENUITEMID   30000
#define MENU_CUSTOMITEMMAIN      0x80000000
#define MENU_CUSTOMITEMCONTEXT   0x40000000
#define SEPARATORPOSITIONINTERVAL 100000

struct CListMenuItem
{
	WORD id;
	int iconId;
	CLISTMENUITEM mi;
};

static int nextMenuId;
static struct CListMenuItem *mainMenuItem, *contextMenuItem;
static int mainItemCount, contextItemCount;
static HIMAGELIST hImlMenuIcons;
static HANDLE hPreBuildContactMenuEvent, hAckHook;
static HMENU hMainMenu, hStatusMenu, hRootMenu;
int currentStatusMenuItem, currentDesiredStatusMode;
static int statusModeList[] =
{ 
	ID_STATUS_OFFLINE, ID_STATUS_ONLINE, ID_STATUS_AWAY, ID_STATUS_NA, ID_STATUS_OCCUPIED, ID_STATUS_DND, ID_STATUS_FREECHAT, ID_STATUS_INVISIBLE,
	ID_STATUS_ONTHEPHONE, ID_STATUS_OUTTOLUNCH 
};

static int skinIconStatusList[] =
{ 
	SKINICON_STATUS_OFFLINE, SKINICON_STATUS_ONLINE, SKINICON_STATUS_AWAY, SKINICON_STATUS_NA, SKINICON_STATUS_OCCUPIED, SKINICON_STATUS_DND,
	SKINICON_STATUS_FREE4CHAT, SKINICON_STATUS_INVISIBLE, SKINICON_STATUS_ONTHEPHONE, SKINICON_STATUS_OUTTOLUNCH 
};

static int statusModePf2List[] =
{ 0xFFFFFFFF, PF2_ONLINE, PF2_SHORTAWAY, PF2_LONGAWAY, PF2_LIGHTDND, PF2_HEAVYDND, PF2_FREECHAT, PF2_INVISIBLE, PF2_ONTHEPHONE, PF2_OUTTOLUNCH };

extern HANDLE hStatusModeChangeEvent;

static TCHAR* LangPackPcharToTchar( const char* pszStr )
{
	if ( pszStr == NULL )
		return NULL;

	#if defined( _UNICODE )
	{	int len = strlen( pszStr );
		TCHAR* result = ( TCHAR* )alloca(( len+1 )*sizeof( TCHAR ));
		MultiByteToWideChar( CallService( MS_LANGPACK_GETCODEPAGE, 0, 0 ), 0, pszStr, -1, result, len );
		result[len] = 0;
		return wcsdup( TranslateW( result ));
	}
	#else
		return _strdup( Translate( pszStr ));
	#endif
}

static void InsertMenuItemWithSeparators(HMENU hMenu, int uItem, BOOL fByPosition, MENUITEMINFO* lpmii)
{
	int thisItemPosition, needSeparator;
	MENUITEMINFO mii;

	if (lpmii->fMask & MIIM_SUBMENU)
		thisItemPosition = (int) lpmii->dwItemData;
	else
		thisItemPosition = mainMenuItem[lpmii->dwItemData & ~MENU_CUSTOMITEMMAIN].mi.position;
	ZeroMemory(&mii, sizeof(mii));
	mii.cbSize = MENUITEMINFO_V4_SIZE;
	//check for separator before
	if (uItem) {
		mii.fMask = MIIM_SUBMENU | MIIM_DATA | MIIM_TYPE;
		GetMenuItemInfo(hMenu, uItem - 1, TRUE, &mii);
		if (mii.fType == MFT_SEPARATOR)
			needSeparator = 0;
		else if (mii.hSubMenu == NULL && (mii.dwItemData & MENU_CUSTOMITEMMAIN) == 0)
			needSeparator = 0;
		else if (mii.hSubMenu == NULL)
			needSeparator =
			((mainMenuItem[mii.dwItemData & ~MENU_CUSTOMITEMMAIN].mi.position) / SEPARATORPOSITIONINTERVAL) !=
			thisItemPosition / SEPARATORPOSITIONINTERVAL;
		else
			needSeparator = ((int) mii.dwItemData) / SEPARATORPOSITIONINTERVAL != thisItemPosition / SEPARATORPOSITIONINTERVAL;
		if (needSeparator) {
			//but might be supposed to be after the next one instead
			mii.fType = 0;
			if (uItem < GetMenuItemCount(hMenu)) {
				mii.fMask = MIIM_SUBMENU | MIIM_DATA | MIIM_TYPE;
				GetMenuItemInfo(hMenu, uItem, TRUE, &mii);
			}
			if (mii.fType != MFT_SEPARATOR) {
				mii.fMask = MIIM_TYPE;
				mii.fType = MFT_SEPARATOR;
				InsertMenuItem(hMenu, uItem, TRUE, &mii);
			}
			uItem++;
		}
	}
	//check for separator after
	if (uItem < GetMenuItemCount(hMenu)) {
		mii.fMask = MIIM_SUBMENU | MIIM_DATA | MIIM_TYPE;
		mii.cch = 0;
		GetMenuItemInfo(hMenu, uItem, TRUE, &mii);
		if (mii.fType == MFT_SEPARATOR)
			needSeparator = 0;
		else if (mii.hSubMenu == NULL && (mii.dwItemData & MENU_CUSTOMITEMMAIN) == 0)
			needSeparator = 0;
		else if (mii.hSubMenu == NULL)
			needSeparator =
			((mainMenuItem[mii.dwItemData & ~MENU_CUSTOMITEMMAIN].mi.position) / SEPARATORPOSITIONINTERVAL) !=
			thisItemPosition / SEPARATORPOSITIONINTERVAL;
		else
			needSeparator = ((int) mii.dwItemData) / SEPARATORPOSITIONINTERVAL != thisItemPosition / SEPARATORPOSITIONINTERVAL;
		if (needSeparator) {
			mii.fMask = MIIM_TYPE;
			mii.fType = MFT_SEPARATOR;
			InsertMenuItem(hMenu, uItem, TRUE, &mii);
		}
	}
	if (uItem == GetMenuItemCount(hMenu) - 1) {
		TCHAR str[32];
		mii.fMask = MIIM_SUBMENU | MIIM_DATA | MIIM_TYPE;
		mii.dwTypeData = str;
		mii.cch = SIZEOF(str);
		GetMenuItemInfo(hMenu, uItem, TRUE, &mii);
		if (mii.fType == MFT_STRING && !_tcscmp(mii.dwTypeData, TranslateT("E&xit"))) {
			//make sure we keep the separator before the exit menu item
			mii.fMask = MIIM_TYPE;
			mii.fType = MFT_SEPARATOR;
			InsertMenuItem(hMenu, uItem, TRUE, &mii);
		}
	}
	InsertMenuItem(hMenu, uItem, TRUE, lpmii);
}

//#define PUTPOSITIONSONMENU
static int AddMainMenuItem(WPARAM wParam, LPARAM lParam)
{
	CLISTMENUITEM *mi = (CLISTMENUITEM *) lParam;
	MENUITEMINFO mii;
	TCHAR* ptszName;
	int i;
	HMENU hMenu;

	if (mi==NULL || mi->cbSize != sizeof(CLISTMENUITEM))
		return 0;
	mainMenuItem = (struct CListMenuItem *) realloc(mainMenuItem, sizeof(struct CListMenuItem) * (mainItemCount + 1));
	mainMenuItem[mainItemCount].id = nextMenuId++;
	mainMenuItem[mainItemCount].mi = *mi;
	mainMenuItem[mainItemCount].mi.pszService = strdup(mi->pszService);
	mainMenuItem[mainItemCount].mi.pszContactOwner = NULL;
	mainMenuItem[mainItemCount].mi.pszName = NULL;
	if (mi->hIcon == NULL)
		mainMenuItem[mainItemCount].iconId = -1;
	else
		mainMenuItem[mainItemCount].iconId = ImageList_AddIcon(hImlMenuIcons, mi->hIcon);
	hMenu = hMainMenu;
	ZeroMemory(&mii, sizeof(mii));
	mii.cbSize = MENUITEMINFO_V4_SIZE;
	mii.fMask = MIIM_SUBMENU | MIIM_TYPE | MIIM_DATA;
	if (mi->pszPopupName != NULL) {
		TCHAR str[80];
		TCHAR* ptszPopupName = LangPackPcharToTchar( mi->pszPopupName );
		for (i = GetMenuItemCount(hMenu) - 1; i >= 0; i--) {
			mii.cch = SIZEOF(str);
			mii.dwTypeData = str;
			GetMenuItemInfo(hMenu, i, TRUE, &mii);
#ifdef PUTPOSITIONSONMENU
			if (mii.hSubMenu != NULL && !_tcsnicmp(str, ptszPopupName, _tcslen(ptszPopupName)))
				break;
#else
			if (mii.hSubMenu != NULL && !_tcsicmp(str, ptszPopupName))
				break;
#endif
		}
		if (i < 0) {
			mii.fMask = MIIM_SUBMENU | MIIM_DATA | MIIM_TYPE;
			for (i = GetMenuItemCount(hMenu) - 1; i >= 0; i--) {
				mii.cch = SIZEOF(str);
				mii.dwTypeData = str;
				GetMenuItemInfo(hMenu, i, TRUE, &mii);
				if (mii.fType == MFT_SEPARATOR)
					continue;
				if (mii.hSubMenu == NULL && (mii.dwItemData & MENU_CUSTOMITEMMAIN) == 0)
					continue;
				if (mii.hSubMenu == NULL) {
					if (mainMenuItem[mii.dwItemData & ~MENU_CUSTOMITEMMAIN].mi.position <= mainMenuItem[mainItemCount].mi.popupPosition)
						break;
				}
				else {
					if ((int) mii.dwItemData <= mainMenuItem[mainItemCount].mi.popupPosition)
						break;
			}	}

			i++;
			mii.fMask = MIIM_DATA | MIIM_TYPE | MIIM_SUBMENU;
			mii.fType = MFT_STRING;
			mii.dwItemData = (DWORD) mi->popupPosition;
			mii.hSubMenu = CreatePopupMenu();
#ifdef PUTPOSITIONSONMENU
			{
				TCHAR str[256];
				mir_sntprintf(str, SIZEOF(str), "%s (%d)", ptszPopupName, mi->popupPosition);
				mii.dwTypeData = str;
				InsertMenuItemWithSeparators(hMenu, i, TRUE, &mii);
			}
#else
			mii.dwTypeData = ptszPopupName;
			InsertMenuItemWithSeparators(hMenu, i, TRUE, &mii);
#endif
		}
		free(ptszPopupName);
		hMenu = mii.hSubMenu;
	}
	mii.fMask = MIIM_SUBMENU | MIIM_DATA;
	for (i = GetMenuItemCount(hMenu) - 1; i >= 0; i--) {
		GetMenuItemInfo(hMenu, i, TRUE, &mii);
		if (mii.fType == MFT_SEPARATOR)
			continue;
		if (mii.hSubMenu == NULL && (mii.dwItemData & MENU_CUSTOMITEMMAIN) == 0)
			continue;
		if (mii.hSubMenu == NULL) {
			if (mainMenuItem[mii.dwItemData & ~MENU_CUSTOMITEMMAIN].mi.position <= mainMenuItem[mainItemCount].mi.position)
				break;
		}
		else {
			if ((int) mii.dwItemData <= mainMenuItem[mainItemCount].mi.position)
				break;
		}
	}
	i++;
	if (!IsWinVer98Plus()) {
		mii.cbSize = MENUITEMINFO_V4_SIZE;
		mii.fMask = MIIM_DATA | MIIM_TYPE | MIIM_ID;
	}
	else {
		mii.cbSize = sizeof(mii);
		mii.fMask = MIIM_DATA | MIIM_ID | MIIM_STRING;
		if (mainMenuItem[mainItemCount].iconId != -1)
			mii.fMask |= MIIM_BITMAP;
	}
	ptszName = LangPackPcharToTchar( mi->pszName );

	mii.fType = MFT_STRING;
	mii.wID = mainMenuItem[mainItemCount].id;
	mii.dwItemData = mainItemCount | MENU_CUSTOMITEMMAIN;
	mii.hbmpItem = HBMMENU_CALLBACK;
#ifdef PUTPOSITIONSONMENU
	{
		TCHAR str[256];
		mir_sntprintf(str, SIZEOF(str), "%s (%d)", ptszName, mi->position);
		mii.dwTypeData = str;
		InsertMenuItemWithSeparators(hMenu, i, TRUE, &mii);
	}
#else
	mii.dwTypeData = ptszName;
	InsertMenuItemWithSeparators(hMenu, i, TRUE, &mii);
#endif
	free( ptszName );
	mainItemCount++;
	return MENU_CUSTOMITEMMAIN | (mainItemCount - 1);
}

static int AddContactMenuItem(WPARAM wParam, LPARAM lParam)
{
	CLISTMENUITEM *mi = (CLISTMENUITEM *) lParam;

	if (mi==NULL || mi->cbSize != sizeof(CLISTMENUITEM))
		return 0;
	contextMenuItem = (struct CListMenuItem *) realloc(contextMenuItem, sizeof(struct CListMenuItem) * (contextItemCount + 1));
	contextMenuItem[contextItemCount].id = nextMenuId++;
	contextMenuItem[contextItemCount].mi = *mi;
	contextMenuItem[contextItemCount].mi.pszService = strdup(mi->pszService);
	contextMenuItem[contextItemCount].mi.ptszName = LangPackPcharToTchar(mi->pszName);
	if (mi->pszContactOwner != NULL)
		contextMenuItem[contextItemCount].mi.pszContactOwner = strdup(mi->pszContactOwner);
	if (mi->hIcon == NULL)
		contextMenuItem[contextItemCount].iconId = -1;
	else
		contextMenuItem[contextItemCount].iconId = ImageList_AddIcon(hImlMenuIcons, mi->hIcon);
	contextItemCount++;
	return MENU_CUSTOMITEMCONTEXT | (contextItemCount - 1);
}

static int ModifyCustomMenuItem(WPARAM wParam, LPARAM lParam)
{
	struct CListMenuItem *clmi;
	CLISTMENUITEM *mi = (CLISTMENUITEM *) lParam;
	MENUITEMINFO mii;

	if (mi==NULL || mi->cbSize != sizeof(CLISTMENUITEM))
		return 1;
	if (wParam & MENU_CUSTOMITEMMAIN) {
		if ((int) (wParam & ~MENU_CUSTOMITEMMAIN) >= mainItemCount)
			return 1;
		clmi = mainMenuItem + (wParam & ~MENU_CUSTOMITEMMAIN);
	}
	else if (wParam & MENU_CUSTOMITEMCONTEXT) {
		if ((int) (wParam & ~MENU_CUSTOMITEMCONTEXT) >= contextItemCount)
			return 1;
		clmi = contextMenuItem + (wParam & ~MENU_CUSTOMITEMCONTEXT);
	}
	else
		return 1;
	ZeroMemory(&mii, sizeof(mii));
	mii.cbSize = MENUITEMINFO_V4_SIZE;
	if (mi->flags & CMIM_NAME) {
		if (clmi->mi.pszName != NULL)
			free(clmi->mi.pszName);
		clmi->mi.ptszName = LangPackPcharToTchar(mi->pszName);
		if (wParam & MENU_CUSTOMITEMMAIN) {
			mii.fMask = IsWinVer98Plus()? MIIM_STRING : MIIM_TYPE;
			mii.fType = MFT_STRING;
			mii.dwTypeData = clmi->mi.ptszName;
			SetMenuItemInfo(hMainMenu, clmi->id, FALSE, &mii);
		}
	}
	if (mi->flags & CMIM_FLAGS) {
		clmi->mi.flags = mi->flags & ~CMIM_ALL;
		if (wParam & MENU_CUSTOMITEMMAIN) {
			mii.fMask = MIIM_STATE;
			mii.fState = ((clmi->mi.flags & CMIF_GRAYED) ? MFS_GRAYED : 0) | ((clmi->mi.flags & CMIF_CHECKED) ? MFS_CHECKED : 0);
			SetMenuItemInfo(hMainMenu, clmi->id, FALSE, &mii);
		}
	}
	if (mi->flags & CMIM_ICON) {
		clmi->mi.hIcon = mi->hIcon;
		if (mi->hIcon != NULL)
			clmi->iconId = ImageList_ReplaceIcon(hImlMenuIcons, clmi->iconId, mi->hIcon);
		else
			clmi->iconId = -1;  //fixme, should remove old icon & shuffle all iconIds
	}
	if (mi->flags & CMIM_HOTKEY) {
		clmi->mi.hotKey = mi->hotKey;
	}
	return 0;
}

int MenuProcessCommand(WPARAM wParam, LPARAM lParam)
{
	int i;
	PROTOCOLDESCRIPTOR **proto;
	int protoCount;

	if (HIWORD(wParam) & MPCF_MAINMENU) {
		int newStatus, protoIndex;
		CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & protoCount, (LPARAM) & proto);
		if (LOWORD(wParam) >= ID_STATUS_OFFLINE + SIZEOF(statusModeList)
			&& LOWORD(wParam) < ID_STATUS_OFFLINE + (protoCount + 1) * SIZEOF(statusModeList)) {
				// one of the protocol-specific status menus
				protoIndex = (LOWORD(wParam) - ID_STATUS_OFFLINE) / SIZEOF(statusModeList) - 1;
				newStatus = (LOWORD(wParam) - ID_STATUS_OFFLINE) % SIZEOF(statusModeList) + ID_STATUS_OFFLINE;
				// let the world know, this need's the translated ID_STATUS_* NOT LOWORD(wParam)
				// which is offseted by some degree depending on protocol used
				CallProtoService(proto[protoIndex]->szName, PS_SETSTATUS, newStatus, 0);
				NotifyEventHooks(hStatusModeChangeEvent, newStatus, (LPARAM) proto[protoIndex]->szName);
			}
			switch (LOWORD(wParam)) {
			case ID_STATUS_OFFLINE:
			case ID_STATUS_ONLINE:
			case ID_STATUS_AWAY:
			case ID_STATUS_DND:
			case ID_STATUS_NA:
			case ID_STATUS_OCCUPIED:
			case ID_STATUS_FREECHAT:
			case ID_STATUS_INVISIBLE:
			case ID_STATUS_OUTTOLUNCH:
			case ID_STATUS_ONTHEPHONE:
				currentDesiredStatusMode = LOWORD(wParam);
				for (i = 0; i < protoCount; i++)
					CallProtoService(proto[i]->szName, PS_SETSTATUS, LOWORD(wParam), 0);
				DBWriteContactSettingWord(NULL, "CList", "Status", (WORD) currentDesiredStatusMode);
				NotifyEventHooks(hStatusModeChangeEvent, LOWORD(wParam), 0);
				return 1;
			}
			for (i = 0; i < mainItemCount; i++) {
				if (LOWORD(wParam) == mainMenuItem[i].id) {
					CallService(mainMenuItem[i].mi.pszService, 0, (LPARAM) NULL);
					return 1;
				}
			}
	}
	if (HIWORD(wParam) & MPCF_CONTACTMENU) {
		for (i = 0; i < contextItemCount; i++) {
			if (LOWORD(wParam) == contextMenuItem[i].id) {
				if ((HANDLE) lParam != NULL)
					CallService(contextMenuItem[i].mi.pszService, lParam, (LPARAM) (HWND) NULL);
				return 1;
			}
		}
	}
	return 0;
}

static int MenuProcessHotkey(WPARAM vKey, LPARAM lParam)
{
	int i;

	if (lParam & MPCF_MAINMENU) {
		if (vKey >= '0' && vKey <= '9' && GetKeyState(VK_CONTROL) & 0x8000 && !(GetKeyState(VK_MENU) & 0x8000) && !(GetKeyState(VK_SHIFT) & 0x8000)) {
			MenuProcessCommand(MAKEWPARAM(statusModeList[vKey - '0'], MPCF_MAINMENU), 0);
			return 1;
		}
		for (i = 0; i < mainItemCount; i++) {
			if (mainMenuItem[i].mi.hotKey == 0)
				continue;
			if (HIWORD(mainMenuItem[i].mi.hotKey) != vKey)
				continue;
			if (!(LOWORD(mainMenuItem[i].mi.hotKey) & MOD_ALT) != !(GetKeyState(VK_MENU) & 0x8000))
				continue;
			if (!(LOWORD(mainMenuItem[i].mi.hotKey) & MOD_CONTROL) != !(GetKeyState(VK_CONTROL) & 0x8000))
				continue;
			if (!(LOWORD(mainMenuItem[i].mi.hotKey) & MOD_SHIFT) != !(GetKeyState(VK_SHIFT) & 0x8000))
				continue;
			CallService(mainMenuItem[i].mi.pszService, 0, (LPARAM) NULL);
			return 1;
		}
	}
	return 0;
}

//straight subtraction is not possible because I was getting overflows
static int MenuSortProc(int *item1, int *item2)
{
	if (contextMenuItem[*item2].mi.position > contextMenuItem[*item1].mi.position)
		return 1;
	if (contextMenuItem[*item2].mi.position < contextMenuItem[*item1].mi.position)
		return -1;
	return 0;
}

static int BuildContactMenu(WPARAM wParam, LPARAM lParam)
{
	HMENU hMenu;
	MENUITEMINFO mii;
	int i;
	int *itemOrder, itemCount;
	int isOnline, isOnList;
	HANDLE hContact = (HANDLE) wParam;
	int prevPosition;
	int chatRoom;
	DWORD miim_bitmap_verSpecific;
	char *szProto;

	NotifyEventHooks(hPreBuildContactMenuEvent, (WPARAM) hContact, 0);

	szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) hContact, 0);
	isOnList = 0 == DBGetContactSettingByte(hContact, "CList", "NotOnList", 0);
	isOnline = szProto != NULL && ID_STATUS_OFFLINE != DBGetContactSettingWord(hContact, szProto, "Status", ID_STATUS_OFFLINE);
	chatRoom = szProto?DBGetContactSettingByte(hContact, szProto, "ChatRoom", 0):0;
	if ( contextItemCount ) {
		itemOrder = (int *) malloc(sizeof(int) * contextItemCount);
		ZeroMemory(itemOrder, sizeof(int) * contextItemCount);
	} else {
		itemOrder = (int *) malloc(sizeof(int) * 1);
		ZeroMemory(itemOrder, sizeof(int) * 1);
	}
	itemCount = 0;
	for (i = 0; i < contextItemCount; i++) {
		if (contextMenuItem[i].id == 0)
			continue;
		if (szProto == NULL)
			continue;
		// Begin Ugly hack to hide chat room menus
		if (chatRoom) {
			if (!strcmp(contextMenuItem[i].mi.pszName,Translate("&Message")))
				continue;
			if (!strcmp(contextMenuItem[i].mi.pszName,Translate("&File")))
				continue;
			if (!strcmp(contextMenuItem[i].mi.pszName,Translate("User &Details")))
				continue;
			if (!strcmp(contextMenuItem[i].mi.pszName,Translate("View &History")))
				continue;
		}
		// End ugly hack
		if (contextMenuItem[i].mi.pszContactOwner != NULL) {
			if (strcmp(contextMenuItem[i].mi.pszContactOwner, szProto))
				continue;
		}
		if (contextMenuItem[i].mi.flags & CMIF_HIDDEN)
			continue;
		if (contextMenuItem[i].mi.flags & CMIF_NOTONLIST && isOnList)
			continue;
		if (contextMenuItem[i].mi.flags & CMIF_NOTOFFLIST && !isOnList)
			continue;
		if (contextMenuItem[i].mi.flags & CMIF_NOTONLINE && isOnline)
			continue;
		if (contextMenuItem[i].mi.flags & CMIF_NOTOFFLINE && !isOnline)
			continue;
		itemOrder[itemCount] = i;
		itemCount++;
	}
	//sorts in reverse order since it's easiest to add items bottom to top
	qsort(itemOrder, itemCount, sizeof(int), (int (*)(const void *, const void *)) MenuSortProc);
	hMenu = CreatePopupMenu();
	ZeroMemory(&mii, sizeof(mii));
	if (!IsWinVer98Plus()) {
		mii.cbSize = MENUITEMINFO_V4_SIZE;
		mii.fMask = MIIM_DATA | MIIM_ID | MIIM_STATE | MIIM_TYPE;
		miim_bitmap_verSpecific = 0;
	}
	else {
		mii.cbSize = sizeof(mii);
		mii.fMask = MIIM_DATA | MIIM_ID | MIIM_STATE | MIIM_STRING;
		miim_bitmap_verSpecific = MIIM_BITMAP;
	}
	mii.fType = MFT_STRING;
	mii.hbmpItem = HBMMENU_CALLBACK;
	prevPosition = contextMenuItem[itemOrder[0]].mi.position;
	for (i = 0; i < itemCount; i++) {
		if (prevPosition / SEPARATORPOSITIONINTERVAL != contextMenuItem[itemOrder[i]].mi.position / SEPARATORPOSITIONINTERVAL) {
			UINT oldMask = mii.fMask;
			mii.fMask = MIIM_TYPE;
			mii.fType = MFT_SEPARATOR;
			InsertMenuItem(hMenu, 0, TRUE, &mii);
			mii.fMask = oldMask;
			mii.fType = MFT_STRING;
		}
		prevPosition = contextMenuItem[itemOrder[i]].mi.position;
		if (contextMenuItem[itemOrder[i]].iconId == -1)
			mii.fMask &= ~miim_bitmap_verSpecific;
		else
			mii.fMask |= miim_bitmap_verSpecific;
		mii.dwItemData = itemOrder[i] | MENU_CUSTOMITEMCONTEXT;
		mii.fState =
			((contextMenuItem[itemOrder[i]].mi.flags & CMIF_GRAYED) ? MFS_GRAYED : MFS_ENABLED) | ((contextMenuItem[itemOrder[i]].mi.
			flags & CMIF_CHECKED) ? MFS_CHECKED :
		MFS_UNCHECKED);
		mii.wID = contextMenuItem[itemOrder[i]].id;
		mii.dwTypeData = contextMenuItem[itemOrder[i]].mi.ptszName;
#ifdef _DEBUG
		if (GetKeyState(VK_CONTROL) & 0x8000) {
			TCHAR str[256];
			wsprintf(str, _T("%s (%d)"), contextMenuItem[itemOrder[i]].mi.pszName, contextMenuItem[itemOrder[i]].mi.position);
			mii.dwTypeData = str;
			InsertMenuItem(hMenu, 0, TRUE, &mii);
		}
		else InsertMenuItem(hMenu, 0, TRUE, &mii);
#else
		InsertMenuItem(hMenu, 0, TRUE, &mii);
#endif
	}
	free(itemOrder);
	return (int) hMenu;
}

static int MenuIconsChanged(WPARAM wParam, LPARAM lParam)
{
	int i, protoCount, networkProtoCount, j;
	PROTOCOLDESCRIPTOR **proto;
	DWORD flags;
	MENUITEMINFO mii;

	ZeroMemory(&mii, sizeof(mii));
	mii.cbSize = sizeof(mii);
	mii.fMask = MIIM_DATA;

	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & protoCount, (LPARAM) & proto);
	networkProtoCount = 0;
	for (i = 0; i < protoCount; i++)
		if (proto[i]->type == PROTOTYPE_PROTOCOL)
			networkProtoCount++;
	if (networkProtoCount > 1) {
		for (i = 0; i < protoCount; i++) {
			if (proto[i]->type != PROTOTYPE_PROTOCOL)
				continue;
			flags = CallProtoService(proto[i]->szName, PS_GETCAPS, PFLAGNUM_2, 0);
			for (j = 0; j < SIZEOF(statusModeList); j++) {
				if (!(flags & statusModePf2List[j]))
					continue;
				if (!GetMenuItemInfo(hStatusMenu, (i + 1) * SIZEOF(statusModeList) + statusModeList[j], FALSE, &mii))
					continue;
				if ((mii.dwItemData & MENU_CUSTOMITEMMAIN) != MENU_CUSTOMITEMMAIN)
					continue;
				ImageList_ReplaceIcon(hImlMenuIcons, mainMenuItem[mii.dwItemData & ~MENU_CUSTOMITEMMAIN].iconId,
					LoadSkinnedProtoIcon(proto[i]->szName, statusModeList[j]));
	}	}	}

	for (i = 0; i < SIZEOF(statusModeList); i++) {
		if (!GetMenuItemInfo(hStatusMenu, statusModeList[i], FALSE, &mii))
			continue;
		if ((mii.dwItemData & MENU_CUSTOMITEMMAIN) != MENU_CUSTOMITEMMAIN)
			continue;
		ImageList_ReplaceIcon(hImlMenuIcons, mainMenuItem[mii.dwItemData & ~MENU_CUSTOMITEMMAIN].iconId,
			LoadSkinnedProtoIcon(NULL, statusModeList[i]));
	}
	return 0;
}

static void GiveExistingItemAnIcon(UINT id, HICON hIcon)
{
	MENUITEMINFO mii;

	mainMenuItem = (struct CListMenuItem *) realloc(mainMenuItem, sizeof(struct CListMenuItem) * (mainItemCount + 1));
	mii.cbSize = sizeof(mii);
	mii.fMask = MIIM_BITMAP | MIIM_DATA;
	mii.dwItemData = MENU_CUSTOMITEMMAIN | mainItemCount;
	mii.hbmpItem = HBMMENU_CALLBACK;
	mainMenuItem[mainItemCount].iconId = ImageList_AddIcon(hImlMenuIcons, hIcon);
	mainMenuItem[mainItemCount].id = 0xFFFF;
	mainMenuItem[mainItemCount].mi.hotKey = 0;
	mainMenuItem[mainItemCount].mi.pszName = NULL;
	mainMenuItem[mainItemCount].mi.pszService = NULL;
	SetMenuItemInfo(hStatusMenu, id, FALSE, &mii);
	mainItemCount++;
}

static int MeasureMenuItem(WPARAM wParam, LPARAM lParam)
{
	struct CListMenuItem *clmi = NULL;
	LPMEASUREITEMSTRUCT mis = (LPMEASUREITEMSTRUCT) lParam;
	if (mis->itemData & MENU_CUSTOMITEMCONTEXT)
		clmi = &contextMenuItem[mis->itemData & ~MENU_CUSTOMITEMCONTEXT];
	else if (mis->itemData & MENU_CUSTOMITEMMAIN)
		clmi = &mainMenuItem[mis->itemData & ~MENU_CUSTOMITEMMAIN];
	if (clmi == NULL)
		return FALSE;
	if (clmi->iconId == -1)
		return FALSE;

	mis->itemWidth = max(0, GetSystemMetrics(SM_CXSMICON) - GetSystemMetrics(SM_CXMENUCHECK) + 4);
	mis->itemHeight = GetSystemMetrics(SM_CYSMICON) + 2;
	return TRUE;
}

static int DrawMenuItem(WPARAM wParam, LPARAM lParam)
{
	struct CListMenuItem *clmi = NULL;
	int y;
	LPDRAWITEMSTRUCT dis = (LPDRAWITEMSTRUCT) lParam;

	if (dis->itemData & MENU_CUSTOMITEMCONTEXT)
		clmi = &contextMenuItem[dis->itemData & ~MENU_CUSTOMITEMCONTEXT];
	else if (dis->itemData & MENU_CUSTOMITEMMAIN)
		clmi = &mainMenuItem[dis->itemData & ~MENU_CUSTOMITEMMAIN];
	if (clmi == NULL)
		return FALSE;
	if (clmi->iconId == -1)
		return FALSE;

	y = (dis->rcItem.bottom - dis->rcItem.top - GetSystemMetrics(SM_CYSMICON)) / 2 + 1;
	if (dis->itemState & ODS_SELECTED) {
		if (dis->itemState & ODS_CHECKED) {
			RECT rc;
			rc.left = 2;
			rc.right = GetSystemMetrics(SM_CXSMICON) + 2;
			rc.top = y;
			rc.bottom = rc.top + GetSystemMetrics(SM_CYSMICON) + 2;
			FillRect(dis->hDC, &rc, GetSysColorBrush(COLOR_HIGHLIGHT));
			ImageList_DrawEx(hImlMenuIcons, clmi->iconId, dis->hDC, 2, y, 0, 0, CLR_NONE, CLR_DEFAULT, ILD_SELECTED);
		}
		else
			ImageList_DrawEx(hImlMenuIcons, clmi->iconId, dis->hDC, 2, y, 0, 0, CLR_NONE, CLR_DEFAULT, ILD_FOCUS);
	}
	else {
		if (dis->itemState & ODS_CHECKED) {
			HBRUSH hBrush;
			RECT rc;
			COLORREF menuCol, hiliteCol;
			rc.left = 0;
			rc.right = GetSystemMetrics(SM_CXSMICON) + 4;
			rc.top = y - 2;
			rc.bottom = rc.top + GetSystemMetrics(SM_CYSMICON) + 4;
			DrawEdge(dis->hDC, &rc, BDR_SUNKENOUTER, BF_RECT);
			InflateRect(&rc, -1, -1);
			menuCol = GetSysColor(COLOR_MENU);
			hiliteCol = GetSysColor(COLOR_3DHIGHLIGHT);
			hBrush =
				CreateSolidBrush(RGB
				((GetRValue(menuCol) + GetRValue(hiliteCol)) / 2, (GetGValue(menuCol) + GetGValue(hiliteCol)) / 2,
				(GetBValue(menuCol) + GetBValue(hiliteCol)) / 2));
			FillRect(dis->hDC, &rc, hBrush);
			DeleteObject(hBrush);
			ImageList_DrawEx(hImlMenuIcons, clmi->iconId, dis->hDC, 2, y, 0, 0, CLR_NONE, GetSysColor(COLOR_MENU), ILD_BLEND25);
		}
		else ImageList_DrawEx(hImlMenuIcons, clmi->iconId, dis->hDC, 2, y, 0, 0, CLR_NONE, CLR_NONE, ILD_NORMAL);
	}
	return TRUE;
}

static int MenuGetMain(WPARAM wParam, LPARAM lParam)
{
	return (int) hMainMenu;
}

static int MenuGetStatus(WPARAM wParam, LPARAM lParam)
{
	return (int) hStatusMenu;
}

static int MenuModulesLoaded(WPARAM wParam, LPARAM lParam)
{
	int i, protoCount, networkProtoCount;
	char *szProto = NULL;
	char *szLastProto = NULL;
	PROTOCOLDESCRIPTOR **proto;
	DWORD statusFlags = 0, flags, moreflags = 0;

	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & protoCount, (LPARAM) & proto);
	networkProtoCount = 0;
	for (i = 0; i < protoCount; i++)    /* look for valid protocols */
		if (proto[i]->type == PROTOTYPE_PROTOCOL && CallProtoService(proto[i]->szName, PS_GETCAPS, PFLAGNUM_2, 0) != 0) {
			networkProtoCount++;
			szLastProto = proto[i]->szName;     /* remember the protocol name, since it maybe the last */
		}                       //if

		if (IsWinVer98Plus()) {
			/* if a single network protocol is used, load the iconset for the global menu by name */
			if (networkProtoCount == 1)
				szProto = szLastProto;
			for (i = 0; i < SIZEOF(statusModeList); i++) {
				GiveExistingItemAnIcon(statusModeList[i], LoadSkinnedProtoIcon(szProto, statusModeList[i]));
			}
			HookEvent(ME_SKIN_ICONSCHANGED, MenuIconsChanged);
		}

		if (networkProtoCount > 1) {
			MENUITEMINFO mii;
			ZeroMemory(&mii, sizeof(mii));
			mii.cbSize = MENUITEMINFO_V4_SIZE;
			mii.fMask = MIIM_TYPE;
			mii.fType = MFT_SEPARATOR;
			mii.dwTypeData = _T("");
			InsertMenuItem(hStatusMenu, 0, TRUE, &mii);
		}
		for (i = 0; i < protoCount; i++) {
			if (proto[i]->type != PROTOTYPE_PROTOCOL || CallProtoService(proto[i]->szName, PS_GETCAPS, PFLAGNUM_2, 0) == 0)
				continue;
			flags = CallProtoService(proto[i]->szName, PS_GETCAPS, PFLAGNUM_2, 0);
			moreflags = CallProtoService(proto[i]->szName, PS_GETCAPS, PFLAGNUM_5, 0);
			if (networkProtoCount > 1) {
				MENUITEMINFO mii;
				char protoName[128];
				int j;
				HMENU hMenu = GetSubMenu(LoadMenu(g_hInst, MAKEINTRESOURCE(IDR_CLISTMENU)), 1);
				ZeroMemory(&mii, sizeof(mii));
				for (j = 0; j < SIZEOF(statusModeList); j++) {
					if (!(flags & statusModePf2List[j]))
						DeleteMenu(hMenu, statusModeList[j], MF_BYCOMMAND);
					else if (moreflags & statusModePf2List[j] && j > 0)
						DeleteMenu(hMenu, statusModeList[j], MF_BYCOMMAND);
					else {
						TCHAR text[128], *ptab;
						mii.cbSize = MENUITEMINFO_V4_SIZE;
						mii.fMask = MIIM_TYPE;
						mii.cch = SIZEOF(text);
						mii.dwTypeData = text;
						GetMenuItemInfo(hMenu, statusModeList[j], FALSE, &mii);
						_tcscpy(text, TranslateTS(text));
						ptab = _tcschr(text, '\t');
						if (ptab != NULL)
							*ptab = '\0';
						if (IsWinVer98Plus()) {
							mii.cbSize = sizeof(mii);
							mii.fMask = MIIM_ID | MIIM_BITMAP | MIIM_DATA | MIIM_STRING;
							mainMenuItem = (struct CListMenuItem *)realloc(mainMenuItem, sizeof(struct CListMenuItem) * (mainItemCount + 1));
							mainMenuItem[mainItemCount].iconId =
								ImageList_AddIcon(hImlMenuIcons, LoadSkinnedProtoIcon(proto[i]->szName, statusModeList[j]));
							mainMenuItem[mainItemCount].id = 0xFFFF;
							mainMenuItem[mainItemCount].mi.hotKey = 0;
							mainMenuItem[mainItemCount].mi.pszName = NULL;
							mainMenuItem[mainItemCount].mi.pszService = NULL;
							mii.dwItemData = MENU_CUSTOMITEMMAIN | mainItemCount;
							mii.hbmpItem = HBMMENU_CALLBACK;
							mainItemCount++;
						}
						else
							mii.fMask = MIIM_ID | MIIM_TYPE;
						mii.wID = statusModeList[j] + (i + 1) * SIZEOF(statusModeList);
						SetMenuItemInfo(hMenu, statusModeList[j], FALSE, &mii);
					}
				}
				if (IsWinVer98Plus()) {
					HICON hIcon;
					mii.cbSize = sizeof(mii);
					mii.fMask = MIIM_SUBMENU | MIIM_BITMAP | MIIM_DATA | MIIM_STRING;
					mainMenuItem = (struct CListMenuItem *) realloc(mainMenuItem, sizeof(struct CListMenuItem) * (mainItemCount + 1));
					hIcon = (HICON) CallProtoService(proto[i]->szName, PS_LOADICON, PLI_PROTOCOL | PLIF_SMALL, 0);
					mainMenuItem[mainItemCount].iconId = ImageList_AddIcon(hImlMenuIcons, hIcon);
					DestroyIcon(hIcon);
					mainMenuItem[mainItemCount].id = 0xFFFF;
					mainMenuItem[mainItemCount].mi.hotKey = 0;
					mainMenuItem[mainItemCount].mi.pszName = NULL;
					mainMenuItem[mainItemCount].mi.pszService = NULL;
					mii.dwItemData = MENU_CUSTOMITEMMAIN | mainItemCount;
					mii.hbmpItem = HBMMENU_CALLBACK;
					mainItemCount++;
				}
				else {
					mii.cbSize = MENUITEMINFO_V4_SIZE;
					mii.fMask = MIIM_SUBMENU | MIIM_TYPE;
				}
				mii.fType = MFT_STRING;
				mii.hSubMenu = hMenu;
				CallProtoService(proto[i]->szName, PS_GETNAME, SIZEOF(protoName), (LPARAM) protoName);
				{	TCHAR* ptszProtoName = LangPackPcharToTchar(protoName);
					mii.dwTypeData = ptszProtoName;
					InsertMenuItem(hStatusMenu, 0, TRUE, &mii);
					free(ptszProtoName);
			}	}
			statusFlags |= ( flags & ~moreflags );
		}
		for (i = 0; i < SIZEOF(statusModeList); i++)
			if (!(statusFlags & statusModePf2List[i]))
				DeleteMenu(hStatusMenu, statusModeList[i], MF_BYCOMMAND);
		return 0;
}

static int MenuProtoAck(WPARAM wParam, LPARAM lParam)
{
	int protoCount, i, networkProtoCount;
	PROTOCOLDESCRIPTOR **proto;
	ACKDATA *ack = (ACKDATA *) lParam;
	int overallStatus = 0, thisStatus;

	if (ack->type != ACKTYPE_STATUS)
		return 0;
	if (ack->result != ACKRESULT_SUCCESS)
		return 0;
	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & protoCount, (LPARAM) & proto);
	networkProtoCount = 0;
	for (i = 0; i < protoCount; i++)
		if (proto[i]->type == PROTOTYPE_PROTOCOL) {
			thisStatus = CallProtoService(proto[i]->szName, PS_GETSTATUS, 0, 0);
			if (overallStatus == 0)
				overallStatus = thisStatus;
			else if (overallStatus != thisStatus)
				overallStatus = -1;
			networkProtoCount++;
		}
		if (overallStatus > ID_STATUS_CONNECTING) {
			CheckMenuItem(hStatusMenu, currentStatusMenuItem, MF_BYCOMMAND | MF_UNCHECKED);
			currentStatusMenuItem = overallStatus;
			CheckMenuItem(hStatusMenu, currentStatusMenuItem, MF_BYCOMMAND | MF_CHECKED);
			SetMenuDefaultItem(hStatusMenu, currentStatusMenuItem, FALSE);
			currentDesiredStatusMode = currentStatusMenuItem;
		}
		else {
			CheckMenuItem(hStatusMenu, currentStatusMenuItem, MF_BYCOMMAND | MF_UNCHECKED);
			SetMenuDefaultItem(hStatusMenu, -1, FALSE);
			currentStatusMenuItem = 0;
		}
		CallService(MS_CLUI_SENDMSNSTATUS_TO_FLVCC, overallStatus, 0);
		if (networkProtoCount <= 1)
			return 0;
		for (i = 0; i < protoCount; i++)
			if (!strcmp(proto[i]->szName, ack->szModule))
				break;
		//hProcess is previous mode, lParam is new mode
		if ((int) ack->hProcess >= ID_STATUS_OFFLINE && (int) ack->hProcess < ID_STATUS_OFFLINE + SIZEOF(statusModeList))
			CheckMenuItem(hStatusMenu, (i + 1) * SIZEOF(statusModeList) + (int) ack->hProcess, MF_BYCOMMAND | MF_UNCHECKED);
		if (ack->lParam >= ID_STATUS_OFFLINE && ack->lParam < ID_STATUS_OFFLINE + SIZEOF(statusModeList))
			CheckMenuItem(hStatusMenu, (i + 1) * SIZEOF(statusModeList) + ack->lParam, MF_BYCOMMAND | MF_CHECKED);
		return 0;
}

static int MenuModulesShutdown(WPARAM wParam, LPARAM lParam)
{
	UnhookEvent(hAckHook);
	return 0;
}

int InitCustomMenus(void)
{
	CreateServiceFunction(MS_CLIST_ADDMAINMENUITEM, AddMainMenuItem);
	CreateServiceFunction(MS_CLIST_ADDCONTACTMENUITEM, AddContactMenuItem);
	CreateServiceFunction(MS_CLIST_MODIFYMENUITEM, ModifyCustomMenuItem);
	CreateServiceFunction(MS_CLIST_MENUMEASUREITEM, MeasureMenuItem);
	CreateServiceFunction(MS_CLIST_MENUDRAWITEM, DrawMenuItem);
	CreateServiceFunction(MS_CLIST_MENUBUILDCONTACT, BuildContactMenu);
	CreateServiceFunction(MS_CLIST_MENUGETMAIN, MenuGetMain);
	CreateServiceFunction(MS_CLIST_MENUGETSTATUS, MenuGetStatus);
	CreateServiceFunction(MS_CLIST_MENUPROCESSCOMMAND, MenuProcessCommand);
	CreateServiceFunction(MS_CLIST_MENUPROCESSHOTKEY, MenuProcessHotkey);
	hPreBuildContactMenuEvent = CreateHookableEvent(ME_CLIST_PREBUILDCONTACTMENU);
	hAckHook = (HANDLE) HookEvent(ME_PROTO_ACK, MenuProtoAck);
	hRootMenu = LoadMenu(g_hInst, MAKEINTRESOURCE(IDR_CLISTMENU));
	hMainMenu = GetSubMenu(hRootMenu, 0);
	hStatusMenu = GetSubMenu(hRootMenu, 1);
	CallService(MS_LANGPACK_TRANSLATEMENU, (WPARAM) hMainMenu, 0);
	CallService(MS_LANGPACK_TRANSLATEMENU, (WPARAM) hStatusMenu, 0);
	nextMenuId = FIRSTCUSTOMMENUITEMID;
	mainMenuItem = contextMenuItem = NULL;
	mainItemCount = contextItemCount = 0;
	if (IsWinVerXPPlus())       //need 32-bit icons on XP for alpha channels
		hImlMenuIcons = ImageList_Create(GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), ILC_COLOR32 | ILC_MASK, 15, 100);
	else                        //Win2k won't blend icons with imagelist_drawex when color-depth>16-bit. Don't know about WinME, but it certainly doesn't support alpha channels
		hImlMenuIcons = ImageList_Create(GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), ILC_COLOR16 | ILC_MASK, 15, 100);

	currentStatusMenuItem = ID_STATUS_OFFLINE;
	currentDesiredStatusMode = ID_STATUS_OFFLINE;
	{
		MENUITEMINFO mii;
		mii.cbSize = MENUITEMINFO_V4_SIZE;
		mii.fMask = MIIM_STATE;
		mii.fState = MFS_CHECKED | MFS_DEFAULT;
		SetMenuItemInfo(hStatusMenu, currentStatusMenuItem, FALSE, &mii);
	}

	HookEvent(ME_SYSTEM_MODULESLOADED, MenuModulesLoaded);
	HookEvent(ME_SYSTEM_SHUTDOWN, MenuModulesShutdown);
	return 0;
}

void UninitCustomMenus(void)
{
	int i;

	ImageList_Destroy(hImlMenuIcons);
	for (i = 0; i < mainItemCount; i++) {
		if (mainMenuItem[i].mi.pszName != NULL)
			free(mainMenuItem[i].mi.pszName);
		if (mainMenuItem[i].mi.pszService != NULL)
			free(mainMenuItem[i].mi.pszService);
	}
	for (i = 0; i < contextItemCount; i++) {
		if (contextMenuItem[i].mi.pszName != NULL)
			free(contextMenuItem[i].mi.pszName);
		if (contextMenuItem[i].mi.pszService != NULL)
			free(contextMenuItem[i].mi.pszService);
		if (contextMenuItem[i].mi.pszContactOwner != NULL)
			free(contextMenuItem[i].mi.pszContactOwner);
	}
	free(mainMenuItem);
	free(contextMenuItem);
	DestroyMenu(hStatusMenu);
	DestroyMenu(hMainMenu);
	DestroyMenu(hRootMenu);
}
