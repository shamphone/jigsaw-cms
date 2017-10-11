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

#define TRAYICON_ID_BASE    100
#define TIM_CALLBACK   (WM_USER+1857)
#define TIM_CREATE     (WM_USER+1858)

static VOID CALLBACK TrayCycleTimerProc(HWND hwnd, UINT message, UINT idEvent, DWORD dwTime);
void fnTrayIconUpdateBase(const char *szChangedProto);

extern HIMAGELIST hCListImages;
extern int currentStatusMenuItem, currentDesiredStatusMode;
extern BOOL(WINAPI * MySetProcessWorkingSetSize) (HANDLE, SIZE_T, SIZE_T);

static UINT WM_TASKBARCREATED;
static int cycleTimerId = 0, cycleStep = 0;
static int RefreshTimerId=0;   /////by FYR

struct trayIconInfo_t
{
	int id;
	char *szProto;
	HICON hBaseIcon;
	int isBase;
};
static struct trayIconInfo_t *trayIcon = NULL;
static int trayIconCount;

// don't move to win2k.h, need new and old versions to work on 9x/2000/XP
#define NIF_STATE       0x00000008
#define NIF_INFO        0x00000010

typedef struct _DllVersionInfo
{
	DWORD cbSize;
	DWORD dwMajorVersion;       // Major version
	DWORD dwMinorVersion;       // Minor version
	DWORD dwBuildNumber;        // Build number
	DWORD dwPlatformID;         // DLLVER_PLATFORM_*
}
	DLLVERSIONINFO;

#define DLLVER_PLATFORM_WINDOWS         0x00000001      // Windows 95
#define DLLVER_PLATFORM_NT              0x00000002      // Windows NT
typedef HRESULT(CALLBACK * DLLGETVERSIONPROC) (DLLVERSIONINFO *);

static DLLVERSIONINFO dviShell;

static TCHAR *TrayIconMakeTooltip(const TCHAR *szPrefix, const char *szProto)
{
	static TCHAR szTip[128];
	char szProtoName[32];
	TCHAR *szStatus, *szSeparator, *sztProto;

	szSeparator = (IsWinVerMEPlus())? szSeparator = _T("\n") : _T(" | ");

	if (szProto == NULL) {
		PROTOCOLDESCRIPTOR **protos;
		int count, netProtoCount, i;
		CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & count, (LPARAM) & protos);
		for (i = 0, netProtoCount = 0; i < count; i++)
			if (protos[i]->type == PROTOTYPE_PROTOCOL)
				netProtoCount++;
		if (netProtoCount == 1)
			for (i = 0; i < count; i++)
				if (protos[i]->type == PROTOTYPE_PROTOCOL)
					return TrayIconMakeTooltip(szPrefix, protos[i]->szName);
		if (szPrefix && szPrefix[0]) {
			lstrcpyn(szTip, szPrefix, SIZEOF(szTip));
			if (!DBGetContactSettingByte(NULL, "CList", "AlwaysStatus", SETTING_ALWAYSSTATUS_DEFAULT))
				return szTip;
		}
		else
			szTip[0] = '\0';
		szTip[ SIZEOF(szTip) - 1] = '\0';
		for (i = count - 1; i >= 0; i--) {
			if (protos[i]->type != PROTOTYPE_PROTOCOL)
				continue;
			CallProtoService(protos[i]->szName, PS_GETNAME, SIZEOF(szProtoName), (LPARAM) szProtoName);
			szStatus = cli.pfnGetStatusModeDescription( CallProtoService(protos[i]->szName, PS_GETSTATUS, 0, 0), 0);
			if (szTip[0])
				_tcsncat(szTip, szSeparator, SIZEOF(szTip) - 1 - _tcslen(szTip));
			#if defined( _UNICODE )
			{	TCHAR* p = a2u( szProtoName );
				_tcsncat(szTip, p, SIZEOF(szTip) - 1 - _tcslen(szTip));
				mir_free( p );
			}
			#else
				_tcsncat(szTip, szProtoName, SIZEOF(szTip) - 1 - _tcslen(szTip));
			#endif
			_tcsncat(szTip, _T(" "), SIZEOF(szTip) - 1 - _tcslen(szTip));
			_tcsncat(szTip, szStatus, SIZEOF(szTip) - 1 - _tcslen(szTip));
		}
	}
	else {
		CallProtoService(szProto, PS_GETNAME, SIZEOF(szProtoName), (LPARAM) szProtoName);
		#if defined( _UNICODE )
			sztProto = a2u( szProtoName );
		#else
			sztProto = szProtoName;
		#endif

		szStatus = cli.pfnGetStatusModeDescription(CallProtoService(szProto, PS_GETSTATUS, 0, 0), 0);
		if (szPrefix && szPrefix[0]) {
			if (DBGetContactSettingByte(NULL, "CList", "AlwaysStatus", SETTING_ALWAYSSTATUS_DEFAULT))
				mir_sntprintf(szTip, SIZEOF(szTip), _T("%s%s%s %s"), szPrefix, szSeparator, sztProto, szStatus);
			else
				lstrcpyn(szTip, szPrefix, SIZEOF(szTip));
		}
		else mir_sntprintf(szTip, SIZEOF(szTip), _T("%s %s"), sztProto, szStatus);

		#if defined( _UNICODE )
			mir_free(sztProto);
		#endif
	}
	return szTip;
}

static int TrayIconAdd(HWND hwnd, const char *szProto, const char *szIconProto, int status)
{
#pragma message ("modify by wxl")
	return 0;
/*	NOTIFYICONDATA nid = { 0 };
	int i;

	for (i = 0; i < trayIconCount; i++)
		if (trayIcon[i].id == 0)
			break;

	trayIcon[i].id = TRAYICON_ID_BASE + i;
	trayIcon[i].szProto = (char *) szProto;

	nid.cbSize = ( dviShell.dwMajorVersion >= 5 ) ? sizeof(nid) : NOTIFYICONDATA_V1_SIZE;
	nid.hWnd = hwnd;
	nid.uID = trayIcon[i].id;
	nid.uFlags = NIF_ICON | NIF_MESSAGE | NIF_TIP;
	nid.uCallbackMessage = TIM_CALLBACK;
	trayIcon[i].hBaseIcon = ImageList_GetIcon(hCListImages, cli.pfnIconFromStatusMode(szIconProto ? szIconProto : trayIcon[i].szProto, status, NULL), ILD_NORMAL);
	nid.hIcon = trayIcon[i].hBaseIcon;

	if (dviShell.dwMajorVersion >= 5)
		nid.uFlags |= NIF_INFO;

	lstrcpyn(nid.szTip, TrayIconMakeTooltip(NULL, trayIcon[i].szProto), SIZEOF(nid.szTip));
	Shell_NotifyIcon(NIM_ADD, &nid);
	trayIcon[i].isBase = 1;
	return i;*/
}

static void TrayIconRemove(HWND hwnd, const char *szProto)
{
	int i;
	NOTIFYICONDATA nid = { 0 };

	nid.cbSize = ( dviShell.dwMajorVersion >= 5 ) ? sizeof(nid) : NOTIFYICONDATA_V1_SIZE;
	nid.hWnd = hwnd;
	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		if (lstrcmpA(szProto, trayIcon[i].szProto))
			continue;
		nid.uID = trayIcon[i].id;
		Shell_NotifyIcon(NIM_DELETE, &nid);

		DestroyIcon(trayIcon[i].hBaseIcon);
		trayIcon[i].id = 0;
		break;
	}
}

static int TrayIconInit(HWND hwnd)
{
	int count, netProtoCount, i;
	int averageMode = 0;
	PROTOCOLDESCRIPTOR **protos;

	if (cycleTimerId) {
		KillTimer(NULL, cycleTimerId);
		cycleTimerId = 0;
	}
	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & count, (LPARAM) & protos);
	for (i = 0, netProtoCount = 0; i < count; i++) {
		if (protos[i]->type != PROTOTYPE_PROTOCOL)
			continue;
		cycleStep = i;
		netProtoCount++;
		if (averageMode == 0)
			averageMode = CallProtoService(protos[i]->szName, PS_GETSTATUS, 0, 0);
		else if (averageMode != CallProtoService(protos[i]->szName, PS_GETSTATUS, 0, 0)) {
			averageMode = -1;
			break;
		}
	}
	trayIconCount = count;
	trayIcon = (struct trayIconInfo_t *) mir_alloc(sizeof(struct trayIconInfo_t) * count);
	ZeroMemory(trayIcon, sizeof(struct trayIconInfo_t) * count);
	if (DBGetContactSettingByte(NULL, "CList", "TrayIcon", SETTING_TRAYICON_DEFAULT) == SETTING_TRAYICON_MULTI &&
		(averageMode <= 0 || DBGetContactSettingByte(NULL, "CList", "AlwaysMulti", SETTING_ALWAYSMULTI_DEFAULT))) {
			int i;
			for (i = count - 1; i >= 0; i--)
				if (protos[i]->type == PROTOTYPE_PROTOCOL)
					TrayIconAdd(hwnd, protos[i]->szName, NULL, CallProtoService(protos[i]->szName, PS_GETSTATUS, 0, 0));
		}
	else if (averageMode <= 0 && DBGetContactSettingByte(NULL, "CList", "TrayIcon", SETTING_TRAYICON_DEFAULT) == SETTING_TRAYICON_SINGLE) {
		DBVARIANT dbv = { DBVT_DELETED };
		char *szProto;
		if (DBGetContactSetting(NULL, "CList", "PrimaryStatus", &dbv))
			szProto = NULL;
		else
			szProto = dbv.pszVal;
		TrayIconAdd(hwnd, NULL, szProto, szProto ? CallProtoService(szProto, PS_GETSTATUS, 0, 0) : CallService(MS_CLIST_GETSTATUSMODE, 0, 0));
		DBFreeVariant(&dbv);
	}
	else
		TrayIconAdd(hwnd, NULL, NULL, averageMode);
	if (averageMode <= 0 && DBGetContactSettingByte(NULL, "CList", "TrayIcon", SETTING_TRAYICON_DEFAULT) == SETTING_TRAYICON_CYCLE)
		cycleTimerId = SetTimer(NULL, 0, DBGetContactSettingWord(NULL, "CList", "CycleTime", SETTING_CYCLETIME_DEFAULT) * 1000, TrayCycleTimerProc);
	return 0;
}

static void TrayIconDestroy(HWND hwnd)
{
	NOTIFYICONDATA nid = { 0 };
	int i;

	nid.cbSize = ( dviShell.dwMajorVersion >= 5 ) ? sizeof(nid) : NOTIFYICONDATA_V1_SIZE;
	nid.hWnd = hwnd;
	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		nid.uID = trayIcon[i].id;
		Shell_NotifyIcon(NIM_DELETE, &nid);
		DestroyIcon(trayIcon[i].hBaseIcon);
	}
	if (trayIcon)
		mir_free(trayIcon);
	trayIcon = NULL;
	trayIconCount = 0;
}

//called when Explorer crashes and the taskbar is remade
static void TrayIconTaskbarCreated(HWND hwnd)
{
	TrayIconDestroy(hwnd);
	TrayIconInit(hwnd);
}

static int TrayIconUpdate(HICON hNewIcon, const TCHAR *szNewTip, const char *szPreferredProto, int isBase)
{
	NOTIFYICONDATA nid = { 0 };
	int i;

	nid.cbSize = ( dviShell.dwMajorVersion >= 5 ) ? sizeof(nid) : NOTIFYICONDATA_V1_SIZE;
	nid.hWnd = (HWND) CallService(MS_CLUI_GETHWND, 0, 0);
	nid.uFlags = NIF_ICON | NIF_TIP;
	nid.hIcon = hNewIcon;

	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		if (lstrcmpA(trayIcon[i].szProto, szPreferredProto))
			continue;
		nid.uID = trayIcon[i].id;
		lstrcpyn(nid.szTip, TrayIconMakeTooltip(szNewTip, trayIcon[i].szProto), SIZEOF(nid.szTip));
		Shell_NotifyIcon(NIM_MODIFY, &nid);

		trayIcon[i].isBase = isBase;
		return i;
	}

	//if there wasn't a suitable icon, change all the icons
	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		nid.uID = trayIcon[i].id;
		lstrcpyn(nid.szTip, TrayIconMakeTooltip(szNewTip, trayIcon[i].szProto), SIZEOF(nid.szTip));
		Shell_NotifyIcon(NIM_MODIFY, &nid);

		trayIcon[i].isBase = isBase;
	}
	return -1;
}

static int TrayIconSetBaseInfo(HICON hIcon, char *szPreferredProto)
{
	int i;

	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		if (lstrcmpA(trayIcon[i].szProto, szPreferredProto))
			continue;
		DestroyIcon(trayIcon[i].hBaseIcon);
		trayIcon[i].hBaseIcon = hIcon;
		return i;
	}
	//if there wasn't a specific icon, there will only be one suitable
	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		DestroyIcon(trayIcon[i].hBaseIcon);
		trayIcon[i].hBaseIcon = hIcon;
		return i;
	}
	DestroyIcon(hIcon);
	return -1;
}

void fnTrayIconUpdateWithImageList(int iImage, const TCHAR *szNewTip, char *szPreferredProto)
{
	HICON hIcon = ImageList_GetIcon(hCListImages, iImage, ILD_NORMAL);
	TrayIconUpdate(hIcon, szNewTip, szPreferredProto, 0);
	DestroyIcon(hIcon);
}

static VOID CALLBACK TrayCycleTimerProc(HWND hwnd, UINT message, UINT idEvent, DWORD dwTime)
{
	int count;
	PROTOCOLDESCRIPTOR **protos;

	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & count, (LPARAM) & protos);
	for (cycleStep++;; cycleStep++) {
		if (cycleStep >= count)
			cycleStep = 0;
		if (protos[cycleStep]->type == PROTOTYPE_PROTOCOL)
			break;
	}
	DestroyIcon(trayIcon[0].hBaseIcon);
	trayIcon[0].hBaseIcon =
		ImageList_GetIcon(hCListImages,
		cli.pfnIconFromStatusMode(protos[cycleStep]->szName, CallProtoService(protos[cycleStep]->szName, PS_GETSTATUS, 0, 0), NULL), ILD_NORMAL);
	if (trayIcon[0].isBase)
		TrayIconUpdate(trayIcon[0].hBaseIcon, NULL, NULL, 1);
}

void fnTrayIconUpdateBase(const char *szChangedProto)
{
	int i, count, netProtoCount, changed = -1;
	PROTOCOLDESCRIPTOR **protos;
	int averageMode = 0;
	HWND hwnd = (HWND) CallService(MS_CLUI_GETHWND, 0, 0);

	if (cycleTimerId) {
		KillTimer(NULL, cycleTimerId);
		cycleTimerId = 0;
	}
	CallService(MS_PROTO_ENUMPROTOCOLS, (WPARAM) & count, (LPARAM) & protos);
	for (i = 0, netProtoCount = 0; i < count; i++) {
		if (protos[i]->type != PROTOTYPE_PROTOCOL)
			continue;
		netProtoCount++;
		if (!lstrcmpA(szChangedProto, protos[i]->szName))
			cycleStep = i;
		if (averageMode == 0)
			averageMode = CallProtoService(protos[i]->szName, PS_GETSTATUS, 0, 0);
		else if (averageMode != CallProtoService(protos[i]->szName, PS_GETSTATUS, 0, 0)) {
			averageMode = -1;
			break;
		}
	}
	if (netProtoCount > 1) {
		if (averageMode > 0) {
			if (DBGetContactSettingByte(NULL, "CList", "TrayIcon", SETTING_TRAYICON_DEFAULT) == SETTING_TRAYICON_MULTI) {
				if (DBGetContactSettingByte(NULL, "CList", "AlwaysMulti", SETTING_ALWAYSMULTI_DEFAULT))
					changed = TrayIconSetBaseInfo(ImageList_GetIcon(hCListImages, cli.pfnIconFromStatusMode(szChangedProto, averageMode, NULL), ILD_NORMAL), (char*)szChangedProto);
				else if (trayIcon && trayIcon[0].szProto != NULL) {
					TrayIconDestroy(hwnd);
					TrayIconInit(hwnd);
				}
				else
					changed = TrayIconSetBaseInfo(ImageList_GetIcon(hCListImages, cli.pfnIconFromStatusMode(NULL, averageMode, NULL), ILD_NORMAL), NULL);
			}
			else
				changed = TrayIconSetBaseInfo(ImageList_GetIcon(hCListImages, cli.pfnIconFromStatusMode(NULL, averageMode, NULL), ILD_NORMAL), NULL);
		}
		else {
			switch (DBGetContactSettingByte(NULL, "CList", "TrayIcon", SETTING_TRAYICON_DEFAULT)) {
			case SETTING_TRAYICON_SINGLE:
				{
					DBVARIANT dbv = { DBVT_DELETED };
					char *szProto;
					if (DBGetContactSetting(NULL, "CList", "PrimaryStatus", &dbv))
						szProto = NULL;
					else
						szProto = dbv.pszVal;
					changed = TrayIconSetBaseInfo(ImageList_GetIcon(hCListImages,cli.pfnIconFromStatusMode(szProto,szProto ? CallProtoService(szProto, PS_GETSTATUS, 0,0) : CallService(MS_CLIST_GETSTATUSMODE, 0, 0), NULL),ILD_NORMAL), NULL);
					DBFreeVariant(&dbv);
					break;
				}
			case SETTING_TRAYICON_CYCLE:
				cycleTimerId =
					SetTimer(NULL, 0, DBGetContactSettingWord(NULL, "CList", "CycleTime", SETTING_CYCLETIME_DEFAULT) * 1000, TrayCycleTimerProc);
				changed =
					TrayIconSetBaseInfo(ImageList_GetIcon
					(hCListImages, cli.pfnIconFromStatusMode(szChangedProto, CallProtoService(szChangedProto, PS_GETSTATUS, 0, 0), NULL),
					ILD_NORMAL), NULL);
				break;
			case SETTING_TRAYICON_MULTI:
				if (!trayIcon) {
					TrayIconRemove(NULL, NULL);
				}
				else if (DBGetContactSettingByte(NULL, "CList", "AlwaysMulti", SETTING_ALWAYSMULTI_DEFAULT))
					changed = TrayIconSetBaseInfo(ImageList_GetIcon(hCListImages, cli.pfnIconFromStatusMode(szChangedProto, CallProtoService(szChangedProto, PS_GETSTATUS, 0, 0), NULL), ILD_NORMAL), (char*)szChangedProto);
				else {
					TrayIconDestroy(hwnd);
					TrayIconInit(hwnd);
				}
				break;
			}
		}
	}
	else
		changed = TrayIconSetBaseInfo(ImageList_GetIcon(hCListImages, cli.pfnIconFromStatusMode(NULL, averageMode, NULL), ILD_NORMAL), NULL);
	if (changed != -1 && trayIcon[changed].isBase)
		TrayIconUpdate(trayIcon[changed].hBaseIcon, NULL, trayIcon[changed].szProto, 1);
}

void fnTrayIconSetToBase(char *szPreferredProto)
{
	int i;

	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		if (lstrcmpA(trayIcon[i].szProto, szPreferredProto))
			continue;
		TrayIconUpdate(trayIcon[i].hBaseIcon, NULL, szPreferredProto, 1);
		return;
	}
	//if there wasn't a specific icon, there will only be one suitable
	for (i = 0; i < trayIconCount; i++) {
		if (trayIcon[i].id == 0)
			continue;
		TrayIconUpdate(trayIcon[i].hBaseIcon, NULL, szPreferredProto, 1);
		return;
	}
}

void fnTrayIconIconsChanged(void)
{
	HWND hwnd = (HWND) CallService(MS_CLUI_GETHWND, 0, 0);
	TrayIconDestroy(hwnd);
	TrayIconInit(hwnd);
}

static int autoHideTimerId;
static VOID CALLBACK TrayIconAutoHideTimer(HWND hwnd, UINT message, UINT idEvent, DWORD dwTime)
{
	HWND hwndClui;
	KillTimer(hwnd, idEvent);
	hwndClui = (HWND) CallService(MS_CLUI_GETHWND, 0, 0);
	if (GetActiveWindow() == hwndClui)
		return;
	ShowWindow(hwndClui, SW_HIDE);
	if (MySetProcessWorkingSetSize != NULL)
		MySetProcessWorkingSetSize(GetCurrentProcess(), -1, -1);
}

int fnTrayIconPauseAutoHide(WPARAM wParam, LPARAM lParam)
{
	if (DBGetContactSettingByte(NULL, "CList", "AutoHide", SETTING_AUTOHIDE_DEFAULT)) {
		if (GetActiveWindow() != (HWND) CallService(MS_CLUI_GETHWND, 0, 0)) {
			KillTimer(NULL, autoHideTimerId);
			autoHideTimerId = SetTimer(NULL, 0, 1000 * DBGetContactSettingWord(NULL, "CList", "HideTime", SETTING_HIDETIME_DEFAULT), TrayIconAutoHideTimer);
		}
	}
	return 0;
}

int fnTrayIconProcessMessage(WPARAM wParam, LPARAM lParam)
{
	MSG *msg = (MSG *) wParam;
	switch (msg->message) {
	case WM_CREATE: {
		WM_TASKBARCREATED = RegisterWindowMessageA("TaskbarCreated");
		PostMessage(msg->hwnd, TIM_CREATE, 0, 0);
		break;
	}
	case TIM_CREATE:
		TrayIconInit(msg->hwnd);
		break;
	case WM_ACTIVATE:
		if (DBGetContactSettingByte(NULL, "CList", "AutoHide", SETTING_AUTOHIDE_DEFAULT)) {
			if (LOWORD(msg->wParam) == WA_INACTIVE)
				autoHideTimerId = SetTimer(NULL, 0, 1000 * DBGetContactSettingWord(NULL, "CList", "HideTime", SETTING_HIDETIME_DEFAULT), TrayIconAutoHideTimer);
			else
				KillTimer(NULL, autoHideTimerId);
		}
		break;
	case WM_DESTROY:
		TrayIconDestroy(msg->hwnd);
		break;
	case TIM_CALLBACK:
		if (msg->lParam == WM_MBUTTONUP) {
			cli.pfnShowHide(0, 0);
		}
		else if (msg->lParam ==
			(DBGetContactSettingByte(NULL, "CList", "Tray1Click", SETTING_TRAY1CLICK_DEFAULT) ? WM_LBUTTONUP : WM_LBUTTONDBLCLK)) {
				if ((GetAsyncKeyState(VK_CONTROL) & 0x8000))
					cli.pfnShowHide(0, 0);
				else {
					if (cli.pfnEventsProcessTrayDoubleClick())
						cli.pfnShowHide(0, 0);
				}
			}
		else if (msg->lParam == WM_RBUTTONUP) {
			MENUITEMINFO mi;
			POINT pt;
			HMENU hMainMenu = LoadMenu(cli.hInst, MAKEINTRESOURCE(IDR_CONTEXT));
			HMENU hMenu = GetSubMenu(hMainMenu, 0);
			CallService(MS_LANGPACK_TRANSLATEMENU, (WPARAM) hMenu, 0);

			ZeroMemory(&mi, sizeof(mi));
			mi.cbSize = MENUITEMINFO_V4_SIZE;
			mi.fMask = MIIM_SUBMENU | MIIM_TYPE;
			mi.fType = MFT_STRING;
			mi.hSubMenu = (HMENU) CallService(MS_CLIST_MENUGETMAIN, 0, 0);
			mi.dwTypeData = TranslateT("&Main Menu");
			InsertMenuItem(hMenu, 1, TRUE, &mi);
			mi.hSubMenu = (HMENU) CallService(MS_CLIST_MENUGETSTATUS, 0, 0);
			mi.dwTypeData = TranslateT("&Status");
			InsertMenuItem(hMenu, 2, TRUE, &mi);
			SetMenuDefaultItem(hMenu, ID_TRAY_HIDE, FALSE);

			SetForegroundWindow(msg->hwnd);
			SetFocus(msg->hwnd);
			GetCursorPos(&pt);
			TrackPopupMenu(hMenu, TPM_TOPALIGN | TPM_LEFTALIGN, pt.x, pt.y, 0, msg->hwnd, NULL);

			RemoveMenu(hMenu, 1, MF_BYPOSITION);
			RemoveMenu(hMenu, 1, MF_BYPOSITION);
			DestroyMenu(hMainMenu);
		}
		*((LRESULT *) lParam) = 0;
		return TRUE;
	default:
		if (msg->message == WM_TASKBARCREATED) {
			TrayIconTaskbarCreated(msg->hwnd);
			*((LRESULT *) lParam) = 0;
			return TRUE;
		}
	}

	return FALSE;
}

int fnCListTrayNotify(MIRANDASYSTRAYNOTIFY *msn)
{
	if (msn && msn->cbSize == sizeof(MIRANDASYSTRAYNOTIFY) && msn->szInfo && msn->szInfoTitle) {
		if (trayIcon) {
			NOTIFYICONDATAA nid = { 0 };
			nid.cbSize = ( dviShell.dwMajorVersion >= 5 ) ? sizeof(nid) : NOTIFYICONDATAA_V1_SIZE;
			nid.hWnd = (HWND) CallService(MS_CLUI_GETHWND, 0, 0);
			if (msn->szProto) {
				int j;
				for (j = 0; j < trayIconCount; j++) {
					if (trayIcon[j].szProto != NULL) {
						if (strcmp(msn->szProto, trayIcon[j].szProto) == 0) {
							nid.uID = trayIcon[j].id;
							j = trayIconCount;
							continue;
						}
					}
					else {
						if (trayIcon[j].isBase) {
							nid.uID = trayIcon[j].id;
							j = trayIconCount;
							continue;
						}
					}
				}               //for
			}
			else {
				nid.uID = trayIcon[0].id;
			}
			nid.uFlags = NIF_INFO;
			lstrcpynA(nid.szInfo, msn->szInfo, sizeof(nid.szInfo));
			lstrcpynA(nid.szInfoTitle, msn->szInfoTitle, sizeof(nid.szInfoTitle));
			nid.uTimeout = msn->uTimeout;
			nid.dwInfoFlags = msn->dwInfoFlags;
			return Shell_NotifyIconA(NIM_MODIFY, (void *) &nid) == 0;
		}
		return 2;
	}
	return 1;
}

/////////////////////////////////////////////////////////////////////////////////////////

static int pfnCListTrayNotifyStub(WPARAM wParam, LPARAM lParam )
{	return cli.pfnCListTrayNotify(( MIRANDASYSTRAYNOTIFY* )lParam );
}

void InitTray(void)
{
	HINSTANCE hLib;

	hLib = LoadLibraryA("shell32.dll");
	if (hLib) {
		DLLGETVERSIONPROC proc;
		dviShell.cbSize = sizeof(dviShell);
		proc = (DLLGETVERSIONPROC) GetProcAddress(hLib, "DllGetVersion");
		if (proc) {
			proc(&dviShell);
		}
		FreeLibrary(hLib);
	}
	if (dviShell.dwMajorVersion >= 5)
		CreateServiceFunction(MS_CLIST_SYSTRAY_NOTIFY, pfnCListTrayNotifyStub );

	return;
}
