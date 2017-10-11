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

static ATOM aHide = 0;
static ATOM aRead = 0;
static ATOM aSearch = 0;
static ATOM aOpts = 0;

static void WordToModAndVk(WORD w, UINT * mod, UINT * vk)
{
	*mod = 0;
	if (HIBYTE(w) & HOTKEYF_CONTROL)
		*mod |= MOD_CONTROL;
	if (HIBYTE(w) & HOTKEYF_SHIFT)
		*mod |= MOD_SHIFT;
	if (HIBYTE(w) & HOTKEYF_ALT)
		*mod |= MOD_ALT;
	if (HIBYTE(w) & HOTKEYF_EXT)
		*mod |= MOD_WIN;
	*vk = LOBYTE(w);
}

int fnHotKeysRegister(HWND hwnd)
{
	UINT mod, vk;

	if (DBGetContactSettingByte(NULL, "CList", "HKEnShowHide", 0)) {
		if (!aHide)
			aHide = GlobalAddAtomA("HKEnShowHide");
		WordToModAndVk((WORD) DBGetContactSettingWord(NULL, "CList", "HKShowHide", 0), &mod, &vk);
		RegisterHotKey(hwnd, aHide, mod, vk);
	}
	if (DBGetContactSettingByte(NULL, "CList", "HKEnReadMsg", 0)) {
		if (!aRead)
			aRead = GlobalAddAtomA("HKEnReadMsg");
		WordToModAndVk((WORD) DBGetContactSettingWord(NULL, "CList", "HKReadMsg", 0), &mod, &vk);
		RegisterHotKey(hwnd, aRead, mod, vk);
	}
	if (DBGetContactSettingByte(NULL, "CList", "HKEnNetSearch", 0)) {
		if (!aSearch)
			aSearch = GlobalAddAtomA("HKEnNetSearch");
		WordToModAndVk((WORD) DBGetContactSettingWord(NULL, "CList", "HKNetSearch", 0), &mod, &vk);
		RegisterHotKey(hwnd, aSearch, mod, vk);
	}
	if (DBGetContactSettingByte(NULL, "CList", "HKEnShowOptions", 0)) {
		if (!aOpts)
			aOpts = GlobalAddAtomA("HKEnShowOptions");
		WordToModAndVk((WORD) DBGetContactSettingWord(NULL, "CList", "HKShowOptions", 0), &mod, &vk);
		RegisterHotKey(hwnd, aOpts, mod, vk);
	}
	return 0;
}

void fnHotKeysUnregister(HWND hwnd)
{
	if (aHide) {
		UnregisterHotKey(hwnd, aHide);
		GlobalDeleteAtom(aHide);
		aHide = 0;
	}
	if (aRead) {
		UnregisterHotKey(hwnd, aRead);
		GlobalDeleteAtom(aRead);
		aRead = 0;
	}
	if (aSearch) {
		UnregisterHotKey(hwnd, aSearch);
		GlobalDeleteAtom(aSearch);
		aSearch = 0;
	}
	if (aOpts) {
		UnregisterHotKey(hwnd, aOpts);
		GlobalDeleteAtom(aOpts);
		aOpts = 0;
}	}

int fnHotKeysProcess(HWND hwnd, WPARAM wParam, LPARAM lParam)
{
	if (wParam == aHide)
		cli.pfnShowHide(0, 0);
	else if (wParam == aSearch) {
		DBVARIANT dbv;
		if (!DBGetContactSetting(NULL, "CList", "SearchUrl", &dbv)) {
			CallService(MS_UTILS_OPENURL, DBGetContactSettingByte(NULL, "CList", "HKSearchNewWnd", 0), (LPARAM) dbv.pszVal);
			DBFreeVariant( &dbv );
		}
	}
	else if (wParam == aRead) {
		cli.pfnEventsProcessTrayDoubleClick();
	}
	else if (wParam == aOpts) {
		CallService("Options/OptionsCommand", 0, 0);
	}
	return TRUE;
}

int fnHotkeysProcessMessage(WPARAM wParam, LPARAM lParam)
{
	MSG *msg = (MSG *) wParam;
	switch (msg->message) {
	case WM_CREATE:
		cli.pfnHotKeysRegister(msg->hwnd);
		break;
	case WM_HOTKEY:
		*((LRESULT *) lParam) = cli.pfnHotKeysProcess(msg->hwnd, msg->wParam, msg->lParam);
		return TRUE;
	case WM_DESTROY:
		cli.pfnHotKeysUnregister(msg->hwnd);
		break;
	}

	return FALSE;
}
