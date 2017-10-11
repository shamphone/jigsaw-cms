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

#if defined( _UNICODE )
	#define FLAGS LANG_UNICODE
#else
	#define FLAGS 0
#endif

static int TranslateString(WPARAM wParam,LPARAM lParam)
{
	return (int)LangPackTranslateString((const char *)lParam, (wParam & LANG_UNICODE) ? 1 : 0);
}

static int TranslateMenu(WPARAM wParam,LPARAM lParam)
{
	HMENU        hMenu = ( HMENU )wParam;
	int          i;
	MENUITEMINFO mii;
	TCHAR        str[256];

	mii.cbSize = MENUITEMINFO_V4_SIZE;
	for ( i = GetMenuItemCount( hMenu )-1; i >= 0; i--) {
		mii.fMask = MIIM_TYPE|MIIM_SUBMENU;
		mii.dwTypeData = ( TCHAR* )str;
		mii.cch = SIZEOF(str);
		GetMenuItemInfo(hMenu, i, TRUE, &mii);

		if ( mii.cch && mii.dwTypeData ) {
			TCHAR* result = ( TCHAR* )LangPackTranslateString(( const char* )mii.dwTypeData, FLAGS );
			if ( result != mii.dwTypeData ) {
				mii.dwTypeData = result;
				mii.fMask = MIIM_TYPE;
				SetMenuItemInfo( hMenu, i, TRUE, &mii );
		}	}

		if ( mii.hSubMenu != NULL ) TranslateMenu(( WPARAM )mii.hSubMenu, lParam);
	}
	return 0;
}

static void TranslateWindow( HWND hwnd, int flags )
{
	TCHAR title[2048];
	GetWindowText(hwnd, title, SIZEOF( title ));
	{
		TCHAR* result = ( TCHAR* )LangPackTranslateString(( const char* )title, FLAGS );
		if ( result != title )
			SetWindowText(hwnd, result );
}	}

static BOOL CALLBACK TranslateDialogEnumProc(HWND hwnd,LPARAM lParam)
{
	LANGPACKTRANSLATEDIALOG *lptd=(LANGPACKTRANSLATEDIALOG*)lParam;
	TCHAR szClass[32];
	int i,id = GetDlgCtrlID( hwnd );

	if(lptd->ignoreControls != NULL)
		for(i=0;lptd->ignoreControls[i];i++) if(lptd->ignoreControls[i]==id) return TRUE;

	GetClassName(hwnd,szClass,SIZEOF(szClass));
	if(!lstrcmpi(szClass,_T("static")) || !lstrcmpi(szClass,_T("hyperlink")) || !lstrcmpi(szClass,_T("button")) || !lstrcmpi(szClass,_T("MButtonClass")))
		TranslateWindow(hwnd, lptd->flags);
	else if(!lstrcmpi(szClass,_T("edit"))) {
		if(lptd->flags&LPTDF_NOIGNOREEDIT || GetWindowLong(hwnd,GWL_STYLE)&ES_READONLY)
			TranslateWindow(hwnd, lptd->flags);
	}
	return TRUE;
}

static int TranslateDialog(WPARAM wParam,LPARAM lParam)
{
	LANGPACKTRANSLATEDIALOG *lptd=(LANGPACKTRANSLATEDIALOG*)lParam;
	if(lptd==NULL||lptd->cbSize!=sizeof(LANGPACKTRANSLATEDIALOG)) return 1;
	if(!(lptd->flags&LPTDF_NOTITLE))
		TranslateWindow( lptd->hwndDlg, wParam );

	EnumChildWindows(lptd->hwndDlg,TranslateDialogEnumProc,lParam);
	return 0;
}

static int GetDefaultCodePage(WPARAM wParam,LPARAM lParam)
{
	return LangPackGetDefaultCodePage();
}

static int GetDefaultLocale(WPARAM wParam,LPARAM lParam)
{
	return LangPackGetDefaultLocale();
}

static int PcharToTchar(WPARAM wParam,LPARAM lParam)
{
	return ( int )LangPackPcharToTchar((char*)lParam );
}

int LoadLangPackServices(void)
{
	CreateServiceFunction(MS_LANGPACK_TRANSLATESTRING,TranslateString);
	CreateServiceFunction(MS_LANGPACK_TRANSLATEMENU,TranslateMenu);
	CreateServiceFunction(MS_LANGPACK_TRANSLATEDIALOG,TranslateDialog);
	CreateServiceFunction(MS_LANGPACK_GETCODEPAGE,GetDefaultCodePage);
	CreateServiceFunction(MS_LANGPACK_GETLOCALE,GetDefaultLocale);
	CreateServiceFunction(MS_LANGPACK_PCHARTOTCHAR,PcharToTchar);
	return 0;
}

