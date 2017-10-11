/*

Miranda IM: the free IM client for Microsoft* Windows*

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

HINSTANCE g_hInst = 0;
PLUGINLINK *pluginLink;
CLIST_INTERFACE* pcli = NULL;
HIMAGELIST himlCListClc = NULL;
HANDLE hStatusModeChangeEvent;

extern int currentDesiredStatusMode;

struct MM_INTERFACE memoryManagerInterface;
BOOL(WINAPI * MySetLayeredWindowAttributes) (HWND, COLORREF, BYTE, DWORD) = NULL;

/////////////////////////////////////////////////////////////////////////////////////////
// external functions

int MenuProcessCommand(WPARAM wParam, LPARAM lParam);
int InitCustomMenus(void);
void UninitCustomMenus(void);

void PaintClc(HWND hwnd, struct ClcData *dat, HDC hdc, RECT * rcPaint);

int ClcOptInit(WPARAM wParam, LPARAM lParam);
int CluiOptInit(WPARAM wParam, LPARAM lParam);
int CListOptInit(WPARAM wParam, LPARAM lParam);

/////////////////////////////////////////////////////////////////////////////////////////
// dll stub

BOOL WINAPI DllMain(HINSTANCE hInstDLL, DWORD dwReason, LPVOID reserved)
{
	g_hInst = hInstDLL;
	DisableThreadLibraryCalls(g_hInst);
	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////////////////
// returns the plugin information

PLUGININFO pluginInfo = {
	sizeof(PLUGININFO),
	#if defined( _UNICODE )
		"Classic contact list (Unicode)",
	#else
		"Classic contact list",
	#endif
	PLUGIN_MAKE_VERSION(0, 5, 1, 1),
		
	"Display contacts, event notifications, protocol status",
	"Miranda IM project",
	"ghazan@miranda-im.org",
	"Copyright 2000-2006 Miranda IM project",
	"http://www.miranda-im.org",
	UNICODE_AWARE,
	DEFMOD_CLISTALL
};

__declspec(dllexport) PLUGININFO *MirandaPluginInfo(DWORD mirandaVersion)
{
	if (mirandaVersion < PLUGIN_MAKE_VERSION(0, 4, 3, 0))
		return NULL;
	return &pluginInfo;
}

/////////////////////////////////////////////////////////////////////////////////////////
// called when all modules got loaded

static int OnModulesLoaded( WPARAM wParam, LPARAM lParam )
{
	himlCListClc = (HIMAGELIST) CallService(MS_CLIST_GETICONSIMAGELIST, 0, 0);
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// options iniatialization

static int OnOptsInit(WPARAM wParam, LPARAM lParam)
{
	ClcOptInit(wParam, lParam);
	CluiOptInit(wParam, lParam);
	CListOptInit(wParam, lParam);
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// menu status services

static int SetStatusMode(WPARAM wParam, LPARAM lParam)
{
	//todo: check wParam is valid so people can't use this to run random menu items
	MenuProcessCommand(MAKEWPARAM(LOWORD(wParam), MPCF_MAINMENU), 0);
	return 0;
}

static int GetStatusMode(WPARAM wParam, LPARAM lParam)
{
	return currentDesiredStatusMode;
}

/////////////////////////////////////////////////////////////////////////////////////////
// main clist initialization routine

int __declspec(dllexport) CListInitialise(PLUGINLINK * link)
{
	int rc = 0;
	pluginLink = link;
	#ifdef _DEBUG
		_CrtSetDbgFlag(_CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
	#endif

	// get the internal malloc/free()
	memset(&memoryManagerInterface, 0, sizeof(memoryManagerInterface));
	memoryManagerInterface.cbSize = sizeof(memoryManagerInterface);
	CallService(MS_SYSTEM_GET_MMI, 0, (LPARAM) & memoryManagerInterface);

	pcli = ( CLIST_INTERFACE* )CallService(MS_CLIST_RETRIEVE_INTERFACE, 0, (LPARAM)g_hInst);
	if ( (int)pcli == CALLSERVICE_NOTFOUND ) {
		MessageBoxA( NULL, "This version of plugin requires Miranda IM 0.5 or later", "Fatal error", MB_OK );
		return 1;
	}
	pcli->pfnPaintClc = PaintClc;

	MySetLayeredWindowAttributes = (BOOL(WINAPI *) (HWND, COLORREF, BYTE, DWORD)) GetProcAddress(
		LoadLibraryA("user32.dll"), "SetLayeredWindowAttributes");

	HookEvent(ME_SYSTEM_MODULESLOADED, OnModulesLoaded);
	HookEvent(ME_OPT_INITIALISE, OnOptsInit);

	hStatusModeChangeEvent = CreateHookableEvent(ME_CLIST_STATUSMODECHANGE);

	InitCustomMenus();
	CreateServiceFunction(MS_CLIST_SETSTATUSMODE, SetStatusMode);
	CreateServiceFunction(MS_CLIST_GETSTATUSMODE, GetStatusMode);
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// a plugin loader aware of CList exports will never call this.

int __declspec(dllexport) Load(PLUGINLINK * link)
{
	return 1;
}

/////////////////////////////////////////////////////////////////////////////////////////
// a plugin unloader

int __declspec(dllexport) Unload(void)
{
	UninitCustomMenus();
	return 0;
}
