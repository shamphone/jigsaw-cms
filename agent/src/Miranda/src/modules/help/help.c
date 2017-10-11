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

BOOL CALLBACK DlgProcAbout(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);

HWND hAboutDlg=NULL;

static int AboutCommand(WPARAM wParam,LPARAM lParam)
{
	if (IsWindow(hAboutDlg)) {
		SetForegroundWindow(hAboutDlg);
		SetFocus(hAboutDlg);
		return 0;
	}
	hAboutDlg=CreateDialog(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_ABOUT),(HWND)wParam,DlgProcAbout);
	return 0;
}

static int IndexCommand(WPARAM wParam,LPARAM lParam)
{
	CallService(MS_UTILS_OPENURL,1,(LPARAM)"http://www.miranda-im.org/support/");
	return 0;
}

static int WebsiteCommand(WPARAM wParam,LPARAM lParam)
{
	CallService(MS_UTILS_OPENURL,1,(LPARAM)"http://www.miranda-im.org");
	return 0;
}

static int BugCommand(WPARAM wParam,LPARAM lParam)
{
	CallService(MS_UTILS_OPENURL,1,(LPARAM)"http://bugs.miranda-im.org/");
	return 0;
}


int ShutdownHelpModule(WPARAM wParam, LPARAM lParam)
{
	if (IsWindow(hAboutDlg)) DestroyWindow(hAboutDlg);
	hAboutDlg=NULL;
	return 0;
}

int LoadHelpModule(void)
{
	CLISTMENUITEM mi;

	HookEvent(ME_SYSTEM_PRESHUTDOWN,ShutdownHelpModule);

	CreateServiceFunction("Help/AboutCommand",AboutCommand);
	CreateServiceFunction("Help/IndexCommand",IndexCommand);
	CreateServiceFunction("Help/WebsiteCommand",WebsiteCommand);
	CreateServiceFunction("Help/BugCommand",BugCommand);
	ZeroMemory(&mi,sizeof(mi));
	mi.cbSize=sizeof(mi);
	mi.hIcon=LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_MIRANDA));
	mi.pszPopupName=Translate("&Help");
	mi.popupPosition=2000090000;
	mi.position=2000090000;
	mi.pszName=Translate("&About...");
	mi.pszService="Help/AboutCommand";
	CallService(MS_CLIST_ADDMAINMENUITEM,0,(LPARAM)&mi);
	mi.hIcon=LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_HELP));
	mi.position=-500050000;
	mi.pszName=Translate("&Support\tF1");
	mi.hotKey=MAKELPARAM(0,VK_F1);
	mi.pszService="Help/IndexCommand";
	CallService(MS_CLIST_ADDMAINMENUITEM,0,(LPARAM)&mi);
	mi.hIcon=LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_MIRANDAWEBSITE));
	mi.position=2000050000;
	mi.pszName=Translate("&Miranda IM Homepage");
	mi.hotKey=0;
	mi.pszService="Help/WebsiteCommand";
	CallService(MS_CLIST_ADDMAINMENUITEM,0,(LPARAM)&mi);
	mi.hIcon=LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_URL));
	mi.position=2000040000;
	mi.pszName=Translate("&Report Bug");
	mi.hotKey=0;
	mi.pszService="Help/BugCommand";
	CallService(MS_CLIST_ADDMAINMENUITEM,0,(LPARAM)&mi);
	return 0;
}
