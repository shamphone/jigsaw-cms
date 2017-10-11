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

#ifndef M_UTILS_H__
#define M_UTILS_H__ 1

#include <tchar.h>

//this entire module is v0.1.0.1+
//this module cannot be redefined by a plugin, because it's not useful for it
//to be possible
//There are some more utility services in the database for dealing with time
//and simple string scrambling, but they are very db-orientated

/* Opens a URL in the user's default web browser   v0.1.0.1+
wParam=bOpenInNewWindow
lParam=(LPARAM)(const char*)szUrl
returns 0 always
bOpenInNewWindow should be zero to open the URL in the browser window the user
last used, or nonzero to open in a new browser window. If there's no browser
running, one will be opened to show the URL.
*/
#define MS_UTILS_OPENURL    "Utils/OpenURL"

/* Resizes a dialog by calling a custom routine to move the individual
controls   v0.1.0.1+
wParam=0
lParam=(LPARAM)(UTILRESIZEDIALOG*)&urd
Returns 0 on success, or nonzero on failure
Does not support dialogtemplateex dialog boxes, and will return failure if you
try to resize one
The dialog itself should have been resized prior to calling this service
pfnResizer is called once for each control in the dialog
pfnResizer should return a combination of one rd_anchorx_ and one rd_anchory
constant
*/
typedef struct {
	int cbSize;
	UINT wId;				//control ID
	RECT rcItem;			//original control rectangle, relative to dialog
                            //modify in-place to specify the new position
	SIZE dlgOriginalSize;	//size of dialog client area in template
	SIZE dlgNewSize;		//current size of dialog client area
} UTILRESIZECONTROL;
typedef int (*DIALOGRESIZERPROC)(HWND hwndDlg,LPARAM lParam,UTILRESIZECONTROL *urc);
typedef struct {
	int cbSize;
	HWND hwndDlg;
	HINSTANCE hInstance;	//module containing the dialog template
	LPCSTR lpTemplate;		//dialog template
	LPARAM lParam;			//caller-defined
	DIALOGRESIZERPROC pfnResizer;
} UTILRESIZEDIALOG;
#define RD_ANCHORX_CUSTOM   0	//function did everything required to the x axis, do no more processing
#define RD_ANCHORX_LEFT     0	//move the control to keep it constant distance from the left edge of the dialog
#define RD_ANCHORX_RIGHT    1	//move the control to keep it constant distance from the right edge of the dialog
#define RD_ANCHORX_WIDTH    2	//size the control to keep it constant distance from both edges of the dialog
#define RD_ANCHORX_CENTRE   4	//move the control to keep it constant distance from the centre of the dialog
#define RD_ANCHORY_CUSTOM   0
#define RD_ANCHORY_TOP      0
#define RD_ANCHORY_BOTTOM   8
#define RD_ANCHORY_HEIGHT   16
#define RD_ANCHORY_CENTRE   32
#define MS_UTILS_RESIZEDIALOG	 "Utils/ResizeDialog"

/* Gets the name of a country given its number      v0.1.2.0+
wParam=countryId
lParam=0
Returns a pointer to the string containing the country name on success,
or NULL on failure
*/
#define MS_UTILS_GETCOUNTRYBYNUMBER   "Utils/GetCountryByNumber"

/* Gets the full list of country IDs     v0.1.2.0+
wParam=(WPARAM)(int*)piCount
lParam=(LPARAM)(struct CountryListEntry**)ppList
Returns 0 always
Neither wParam nor lParam can be NULL.
The list is sorted alphabetically by country name, on the assumption that it's
quicker to search numbers out of order than it is to search names out of order
*/
struct CountryListEntry {
	int id;
	const char *szName;
};
#define MS_UTILS_GETCOUNTRYLIST    "Utils/GetCountryList"

/******************************* Window lists *******************************/

//allocate a window list   v0.1.0.1+
//wParam=lParam=0
//returns a handle to the new window list
#define MS_UTILS_ALLOCWINDOWLIST "Utils/AllocWindowList"

//adds a window to the specified window list   v0.1.0.1+
//wParam=0
//lParam=(LPARAM)(WINDOWLISTENTRY*)&wle
//returns 0 on success, nonzero on failure
typedef struct {
	HANDLE hList;
	HWND hwnd;
	HANDLE hContact;
} WINDOWLISTENTRY;
#define MS_UTILS_ADDTOWINDOWLIST "Utils/AddToWindowList"
__inline static int WindowList_Add(HANDLE hList,HWND hwnd,HANDLE hContact) {
	WINDOWLISTENTRY wle;
	wle.hList=hList; wle.hwnd=hwnd; wle.hContact=hContact;
	return CallService(MS_UTILS_ADDTOWINDOWLIST,0,(LPARAM)&wle);
}
//removes a window from the specified window list  v0.1.0.1+
//wParam=(WPARAM)(HANDLE)hList
//lParam=(LPARAM)(HWND)hwnd
//returns 0 on success, nonzero on failure
#define MS_UTILS_REMOVEFROMWINDOWLIST "Utils/RemoveFromWindowList"
__inline static int WindowList_Remove(HANDLE hList,HWND hwnd) {
	return CallService(MS_UTILS_REMOVEFROMWINDOWLIST,(WPARAM)hList,(LPARAM)hwnd);
}

//finds a window given the hContact  v0.1.0.1+
//wParam=(WPARAM)(HANDLE)hList
//lParam=(WPARAM)(HANDLE)hContact
//returns the window handle on success, or NULL on failure
#define MS_UTILS_FINDWINDOWINLIST "Utils/FindWindowInList"
__inline static HWND WindowList_Find(HANDLE hList,HANDLE hContact) {
	return (HWND)CallService(MS_UTILS_FINDWINDOWINLIST,(WPARAM)hList,(LPARAM)hContact);
}

//broadcasts a message to all windows in a list  v0.1.0.1+
//wParam=(WPARAM)(HANDLE)hList
//lParam=(LPARAM)(MSG*)&msg
//returns 0 on success, nonzero on failure
//Only msg.message, msg.wParam and msg.lParam are used
#define MS_UTILS_BROADCASTTOWINDOWLIST "Utils/BroadcastToWindowList"
__inline static int WindowList_Broadcast(HANDLE hList,UINT message,WPARAM wParam,LPARAM lParam) {
	MSG msg;
	msg.message=message; msg.wParam=wParam; msg.lParam=lParam;
	return CallService(MS_UTILS_BROADCASTTOWINDOWLIST,(WPARAM)hList,(LPARAM)&msg);
}

/*
	Description: Broadcast a message to all windows in the given list using PostMessage()
	Version: 0.3.0.0+
	Inline helper: WindowList_BroadcastAsync

	wParam=(WPARAM)(HANDLE)hList
	lParam=(LPARAM)(MSG*)&msg

	Returns 0 on success, nonzero on failure, this service does not fail, even if PostMessage() fails for whatever reason

*/
#define MS_UTILS_BROADCASTTOWINDOWLIST_ASYNC "Utils/BroadcastToWindowListAsync"

__inline static int WindowList_BroadcastAsync(HANDLE hList,UINT message,WPARAM wParam,LPARAM lParam) {
	MSG msg;
	msg.message=message; msg.wParam=wParam; msg.lParam=lParam;
	return CallService(MS_UTILS_BROADCASTTOWINDOWLIST_ASYNC,(WPARAM)hList,(LPARAM)&msg);
}

/***************************** Hyperlink windows ********************************/

//there aren't any services here, because you don't need them.
#define WNDCLASS_HYPERLINK   _T("Hyperlink")
//the control will obey the SS_LEFT (0), SS_CENTER (1), and SS_RIGHT (2) styles
//the control will send STN_CLICKED via WM_COMMAND when the link itself is clicked

// Use this in a SendMessage to set the color of the url when control is enabled
// wParam=DWORD color
// lParam=not used
#define HLK_SETENABLECOLOUR	 (WM_USER+101) // added in 0.3.1
// Use this in a SendMessage to set the color of the url when control is disabled
// wParam=DWORD color
// lParam=not used
#define HLK_SETDISABLECOLOUR (WM_USER+102) // added in 0.3.1

/***************************** Window Position Saving ***************************/

//saves the position of a window in the database   v0.1.1.0+
//wParam=0
//lParam=(LPARAM)(SAVEWINDOWPOS*)&swp
//returns 0 on success, nonzero on failure
typedef struct {
	HWND hwnd;
	HANDLE hContact;
	const char *szModule;		//module name to store the setting in
	const char *szNamePrefix;	//text to prefix on "x", "width", etc, to form setting names
} SAVEWINDOWPOS;
#define MS_UTILS_SAVEWINDOWPOSITION  "Utils/SaveWindowPos"
__inline static int Utils_SaveWindowPosition(HWND hwnd,HANDLE hContact,const char *szModule,const char *szNamePrefix) {
	SAVEWINDOWPOS swp;
	swp.hwnd=hwnd; swp.hContact=hContact; swp.szModule=szModule; swp.szNamePrefix=szNamePrefix;
	return CallService(MS_UTILS_SAVEWINDOWPOSITION,0,(LPARAM)&swp);
}

//restores the position of a window from the database	 v0.1.1.0+
//wParam=flags
//lParam=(LPARAM)(SAVEWINDOWPOS*)&swp
//returns 0 on success, nonzero on failure
//if no position was found in the database, the function returns 1 and does
//nothing
//the NoSize version won't use stored size information: the window is left the
//same size.
#define RWPF_NOSIZE     1  //don't use stored size info: leave dialog same size
#define RWPF_NOMOVE     2  //don't use stored position
#define RWPF_NOACTIVATE 4  //show but don't activate v0.3.3.0+
#define MS_UTILS_RESTOREWINDOWPOSITION  "Utils/RestoreWindowPos"
__inline static int Utils_RestoreWindowPosition(HWND hwnd,HANDLE hContact,const char *szModule,const char *szNamePrefix) {
	SAVEWINDOWPOS swp;
	swp.hwnd=hwnd; swp.hContact=hContact; swp.szModule=szModule; swp.szNamePrefix=szNamePrefix;
	return CallService(MS_UTILS_RESTOREWINDOWPOSITION,0,(LPARAM)&swp);
}
__inline static int Utils_RestoreWindowPositionNoSize(HWND hwnd,HANDLE hContact,const char *szModule,const char *szNamePrefix) {
	SAVEWINDOWPOS swp;
	swp.hwnd=hwnd; swp.hContact=hContact; swp.szModule=szModule; swp.szNamePrefix=szNamePrefix;
	return CallService(MS_UTILS_RESTOREWINDOWPOSITION,RWPF_NOSIZE,(LPARAM)&swp);
}
__inline static int Utils_RestoreWindowPositionNoMove(HWND hwnd,HANDLE hContact,const char *szModule,const char *szNamePrefix) {
	SAVEWINDOWPOS swp;
	swp.hwnd=hwnd; swp.hContact=hContact; swp.szModule=szModule; swp.szNamePrefix=szNamePrefix;
	return CallService(MS_UTILS_RESTOREWINDOWPOSITION,RWPF_NOMOVE,(LPARAM)&swp);
}

/************************ Colour Picker Control (0.1.2.1+) **********************/

#define WNDCLASS_COLOURPICKER  _T("ColourPicker")

#define CPM_SETCOLOUR          0x1000	  //lParam=new colour
#define CPM_GETCOLOUR          0x1001	  //returns colour
#define CPM_SETDEFAULTCOLOUR   0x1002	  //lParam=default, used as first custom colour
#define CPM_GETDEFAULTCOLOUR   0x1003	  //returns colour
#define CPN_COLOURCHANGED      1		  //sent through WM_COMMAND

/***************************** Bitmap Filter (0.1.2.1+) *************************/

//Loads a bitmap								v0.1.2.1+
//wParam=0
//lParam=(LPARAM)(const char*)filename
//returns HBITMAP on success, NULL on failure
//This function uses OleLoadPicturePath() so supports BMP, JPEG and GIF. It may
//support PNG on future versions of Windows (or XP for that matter)
//For speed, if the file extension is .bmp or .rle it'll use LoadImage() so as
//to avoid the big lag loading OLE.
//Remember to DeleteObject() when you're done
#define MS_UTILS_LOADBITMAP   "Utils/LoadBitmap"

//Gets the filter strings for use in the open file dialog      v0.1.2.1+
//wParam=cbLengthOfBuffer
//lParam=(LPARAM)(char*)pszBuffer
//Returns 0 on success, nonzero on failure
//See the MSDN under OPENFILENAME.lpstrFilter for the formatting
//An 'All Bitmaps' item is always first and 'All Files' is last.
//The returned string is already translated.
#define MS_UTILS_GETBITMAPFILTERSTRINGS  "Utils/GetBitmapFilterStrings"

//Saves a path to a relative path (from the miranda directory)
//Only saves as a relative path if the file is in the miranda directory (or
//sub directory)
//wParam=(WPARAM)(char*)pszPath
//lParam=(LPARAM)(char*)pszNewPath
//pszPath is the path to convert and pszNewPath is the buffer that
//the new path is copied too.  pszNewPath MUST be of the size MAX_PATH.
//Returns numbers of chars copied.
#define MS_UTILS_PATHTORELATIVE "Utils/PathToRelative"

//Saves a path to a absolute path (from the miranda directory)
//wParam=(WPARAM)(char*)pszPath
//lParam=(LPARAM)(char*)pszNewPath
//pszPath is the path to convert and pszNewPath is the buffer that
//the new path is copied too.  pszNewPath MUST be of the size MAX_PATH.
//Returns numbers of chars copied.
#define MS_UTILS_PATHTOABSOLUTE "Utils/PathToAbsolute"

//Unicode versions (0.6.2+)
#ifdef _UNICODE
  #define MS_UTILS_PATHTORELATIVEW "Utils/PathToRelativeW"
  #define MS_UTILS_PATHTOABSOLUTEW "Utils/PathToAbsoluteW"

  #define MS_UTILS_PATHTORELATIVET MS_UTILS_PATHTORELATIVEW
  #define MS_UTILS_PATHTOABSOLUTET MS_UTILS_PATHTOABSOLUTEW
#else
  #define MS_UTILS_PATHTORELATIVET MS_UTILS_PATHTORELATIVE
  #define MS_UTILS_PATHTOABSOLUTET MS_UTILS_PATHTOABSOLUTE
#endif

// Added in 0.4.0.1
// Here are some string wrappers that are more safe than the win32 versions

static __inline int mir_snprintf(char *buffer, size_t count, const char* fmt, ...) {
	va_list va;
	int len;

	va_start(va, fmt);
	len = _vsnprintf(buffer, count-1, fmt, va);
	va_end(va);
	buffer[count-1] = 0;
	return len;
}

static __inline int mir_sntprintf(TCHAR *buffer, size_t count, const TCHAR* fmt, ...) {
	va_list va;
	int len;

	va_start(va, fmt);
	len = _vsntprintf(buffer, count-1, fmt, va);
	va_end(va);
	buffer[count-1] = 0;
	return len;
}

static __inline int mir_vsnprintf(char *buffer, size_t count, const char* fmt, va_list va) {
	int len;

	len = _vsnprintf(buffer, count-1, fmt, va);
	buffer[count-1] = 0;
	return len;
}

static __inline int mir_vsntprintf(TCHAR *buffer, size_t count, const TCHAR* fmt, va_list va) {
    int len;

    len = _vsntprintf(buffer, count-1, fmt, va);
    buffer[count-1] = 0;
    return len;
}

// allows to include TCHAR* strings into mir_snprintf and NetLib_Logf calls
#if defined( _UNICODE )
	#define TCHAR_STR_PARAM "%S"
#else
	#define TCHAR_STR_PARAM "%s"
#endif

#endif // M_UTILS_H__
