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

int InitSkinIcons(void);
void UninitSkinIcons(void);
int InitSkinSounds(void);
void UninitSkinSounds(void);
BOOL CALLBACK DlgProcSoundOpts(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);
BOOL CALLBACK DlgProcIconsOpts(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);

static int SkinSystemShutdown(WPARAM wParam,LPARAM lParam)
{
	UninitSkinSounds();
	UninitSkinIcons();
	return 0;
}

static UINT iconsExpertOnlyControls[]={IDC_IMPORT};
static int SkinOptionsInit(WPARAM wParam,LPARAM lParam)
{
	OPTIONSDIALOGPAGE odp = { 0 };
	odp.cbSize = sizeof(odp);
	odp.position = -200000000;
	odp.hInstance = GetModuleHandle(NULL);
	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_SOUND);
	odp.pszGroup = "Customize";
	odp.pszTitle = "Sounds";
	odp.pfnDlgProc = DlgProcSoundOpts;
	odp.flags = ODPF_BOLDGROUPS;
	CallService( MS_OPT_ADDPAGE, wParam, ( LPARAM )&odp );

	odp.position = -180000000;
	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_ICONS);
	odp.pszTitle = "Icons";
	odp.pszGroup = "Customize";
	odp.pfnDlgProc = DlgProcIconsOpts;
	odp.expertOnlyControls = iconsExpertOnlyControls;
	odp.nExpertOnlyControls = SIZEOF( iconsExpertOnlyControls );
	CallService( MS_OPT_ADDPAGE, wParam, ( LPARAM )&odp );
	return 0;
}

static int SkinSystemModulesLoaded(WPARAM wParam,LPARAM lParam)
{
	HookEvent(ME_OPT_INITIALISE,SkinOptionsInit);
	return 0;
}

int LoadSkinModule(void)
{
	HookEvent(ME_SYSTEM_MODULESLOADED,SkinSystemModulesLoaded);
	HookEvent(ME_SYSTEM_SHUTDOWN,SkinSystemShutdown);
	if(InitSkinIcons()) return 1;
	if(InitSkinSounds()) return 1;
	return 0;
}
