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

#ifndef WIN2K_H__
#define WIN2K_H__ 1

/*
This file was made to define the new constants normally provided by the windows
sdk you can get from http://www.microsoft.com/msdownload/platformsdk/sdkupdate/
To not need to install the whole sdk you can simply comment out the following lines.
To make myself clear, you are supposed to use the sdk, this is just a work around.

All constants are normally declared in winuser.h

File created by Christian Kästner, and tweaked a bit by Richard Hughes*/

//Windows versions in order of feature presence is:
//95, NT4, 98, ME, 2000, XP
//This is chronological order of release except for ME/2000. ME is barely an
//improvement on 98.
//These macros use the above order, not release order.
#define WinVerMajor()      LOBYTE(LOWORD(GetVersion()))
#define WinVerMinor()      HIBYTE(LOWORD(GetVersion()))
#define IsWinVerNT()       ((GetVersion()&0x80000000)==0)
// IsWinVerNT4Plus() is buggy, Windows 98 is 4.10.1998
#define IsWinVerNT4Plus()  (WinVerMajor()>=5 || WinVerMinor()>0 || IsWinVerNT())
#define IsWinVer98Plus()   (LOWORD(GetVersion())!=4)
#define IsWinVerMEPlus()   (WinVerMajor()>=5 || WinVerMinor()>10)
#define IsWinVer2000Plus() (WinVerMajor()>=5)
#define IsWinVerXPPlus()   (WinVerMajor()>=5 && LOWORD(GetVersion())!=5)

// put stuff that's not apart of any SDKs but is used nonetheless

#define SIZEOF(X) (sizeof(X)/sizeof(X[0]))

//mii was extended for NT5/Win98, so need the old length for some stuff
#define MENUITEMINFO_V4_SIZE (offsetof(MENUITEMINFO,cch)+sizeof((*((MENUITEMINFO*)0)).cch))

#if _MSC_VER >= 1300
#define NOWIN2K
#endif

#if WINVER >= 0x501
#define NOWIN2K
#endif

#ifdef _MSC_VER
#define BIGI(x) x##i64
#else
#define BIGI(x) x##LL
#endif

#if _MSC_VER
	// uxtheme.h defines
	#ifndef THEMEAPI
		#define WM_THEMECHANGED		0x031A // when windows changes themes
		#define BP_PUSHBUTTON		1  // Push Button Type
		#define PBS_NORMAL			1
		#define PBS_HOT				2
		#define PBS_PRESSED			3
		#define PBS_DISABLED		4
		#define PBS_DEFAULTED		5
		#define BP_CHECKBOX			3  // CheckBox Type
		#define TP_BUTTON           1
		#define TS_NORMAL           1
		#define TS_HOT              2
		#define TS_PRESSED          3
		#define TS_DISABLED         4
		#define TS_CHECKED          5
		#define TS_HOTCHECKED       6
		#define CBS_UNCHECKEDNORMAL 1
		#define CBS_UNCHECKEDHOT    2
		#define CBS_CHECKEDNORMAL   5
		#define CBS_CHECKEDHOT      6
	#endif
#endif

#if defined (__GNUC__)
	#define SECURITY_ENTRYPOINTA "InitSecurityInterfaceA"
	#define SECURITY_ENTRYPOINT SECURITY_ENTRYPOINTA
	#define FreeCredentialsHandle FreeCredentialsHandle
	#ifndef CDSIZEOF_STRUCT
		#define CDSIZEOF_STRUCT(structname, member)  (((int)((LPBYTE)(&((structname*)0)->member) - ((LPBYTE)((structname*)0)))) + sizeof(((structname*)0)->member))
	#endif
	#ifndef OPENFILENAME_SIZE_VERSION_400
		#define OPENFILENAME_SIZE_VERSION_400 CDSIZEOF_STRUCT(OPENFILENAME,lpTemplateName)
	#endif
	#ifndef NOTIFYICONDATAA_V1_SIZE
		#define NOTIFYICONDATAA_V1_SIZE     CDSIZEOF_STRUCT(NOTIFYICONDATAA, szTip[64])
	#endif
	#ifndef NOTIFYICONDATA_V1_SIZE
		#define NOTIFYICONDATA_V1_SIZE      CDSIZEOF_STRUCT(NOTIFYICONDATA, szTip[64])
	#endif
	typedef struct tagNMKEY {
		NMHDR hdr;
		UINT nVKey;
		UINT uFlags;
	} NMKEY, *LPNMKEY;
	#define ODS_HOTLIGHT        0x0040
	#define ODS_INACTIVE        0x0080
	#define SPI_GETFLATMENU		0x1022
	#define COLOR_HOTLIGHT		26
	#define COLOR_MENUBAR		30
	#define COLOR_MENUHILIGHT   29
	#define COLOR_HIGHLIGHT		13
	#define BP_PUSHBUTTON		1  // Push Button Type
	#define PBS_NORMAL			1
	#define PBS_HOT				2
	#define PBS_PRESSED			3
	#define PBS_DISABLED		4
	#define PBS_DEFAULTED		5
	#define BP_CHECKBOX			3  // CheckBox Type
	#define TP_BUTTON           1
	#define TS_NORMAL           1
	#define TS_HOT              2
	#define TS_PRESSED          3
	#define TS_DISABLED         4
	#define TS_CHECKED          5
	#define TS_HOTCHECKED       6
	#define CBS_UNCHECKEDNORMAL 1
	#define CBS_UNCHECKEDHOT    2
	#define CBS_CHECKEDNORMAL   5
	#define CBS_CHECKEDHOT      6
// SDK isn't present or some older VC compiler was used, include missing things.
#elif !defined(NOWIN2K) && (!defined WS_EX_LAYERED || !defined IDC_HAND)

	#pragma message("win2k.h")

	#define INVALID_FILE_ATTRIBUTES ((DWORD)-1)

	#define PSDK_WORKAROUND

	#define MONITOR_DEFAULTTONEAREST 2

	#ifndef EM_SETTEXTEX
		#define EM_SETTEXTEX	(WM_USER + 97)
		#define ST_DEFAULT		0
		#define ST_KEEPUNDO		1
		#define ST_SELECTION	2
		#define ST_NEWCHARS		4
		typedef struct _settextex
		{
			DWORD	flags;
			UINT	codepage;
		} SETTEXTEX;
	#endif

	#if(_WIN32_WINNT >= 0x0500)
		#define WS_EX_LAYERED		0x00080000
		#define MIIM_STRING			0x00000040
		#define MIIM_BITMAP			0x00000080
		#define MIIM_FTYPE			0x00000100
		#define HBMMENU_CALLBACK            ((HBITMAP) -1)
		#define ODS_HOTLIGHT        0x0040
		#define ODS_INACTIVE        0x0080
		#define IDC_HAND            MAKEINTRESOURCE(32649)
		#define COLOR_HOTLIGHT		26
		#define COLOR_MENUBAR		30
		#define COLOR_MENUHILIGHT   29
		#define COLOR_HIGHLIGHT		13
		#define SPI_GETFLATMENU		0x1022
		#define TVS_NOHSCROLL       0x8000
		#define SPI_GETLISTBOXSMOOTHSCROLLING       0x1006
		#define SPI_GETHOTTRACKING                  0x100E
		#define BIF_NEWDIALOGSTYLE	0x0040
		#define LVS_EX_LABELTIP     0x00004000
		#define DFCS_HOT 0x1000
		#define FLASHW_TRAY 0x00000002;
		typedef struct {
			UINT cbSize;
			HWND hwnd;
			DWORD dwFlags;
			UINT uCount;
			DWORD dwTimeout;
		} FLASHWINFO;
		/* for the help plugin without the SDK */
		#define SM_XVIRTUALSCREEN 76
		#define SM_YVIRTUALSCREEN 77
		#define SM_CXVIRTUALSCREEN 78
		#define SM_CYVIRTUALSCREEN 79
		#define COLOR_HOTLIGHT 26
		#define VK_OEM_PLUS		0xBB
		#define VK_OEM_MINUS	0xBD

		/* the structure only needs to be defined for VC5 or lower */
		#if _MSC_VER < 1200
			typedef struct tagLASTINPUTINFO {
			UINT cbSize;
			DWORD dwTime;
			} LASTINPUTINFO, *PLASTINPUTINFO;
		#endif /* #if _MSC_VER < 1200 */

		#ifndef OPENFILENAME_SIZE_VERSION_400
			#define OPENFILENAME_SIZE_VERSION_400 sizeof(OPENFILENAME)
		#endif

		#ifndef CCM_SETVERSION
			#define CCM_SETVERSION          (CCM_FIRST + 0x7)
		#endif

		#define SYSRGN 4
		WINGDIAPI int WINAPI GetRandomRgn(IN HDC, IN HRGN, IN INT);

	#endif /* _WIN32_WINNT >= 0x0500 */

	#define LWA_ALPHA               0x00000002
	#define AW_HIDE                 0x00010000
	#define AW_BLEND                0x00080000
	#define SPI_GETSCREENSAVERRUNNING 114
	#define SM_CMONITORS            80

	#ifndef AW_ACTIVATE
		#define AW_ACTIVATE 0x00020000
		#define AW_SLIDE 0x00040000
		#define AW_VER_NEGATIVE 0x00000008
		#define AW_HOR_POSITIVE 0x00000001
	#endif

	#ifndef DWORD_PTR
		typedef DWORD DWORD_PTR;
	#endif

	#ifndef HMONITOR
		DECLARE_HANDLE(HMONITOR);
		typedef struct tagMONITORINFO
		{
			DWORD   cbSize;
			RECT    rcMonitor;
			RECT    rcWork;
			DWORD   dwFlags;
		} MONITORINFO, *LPMONITORINFO;
	#endif


	#ifndef IDropTargetHelper
	#define INTERFACE IDropTargetHelper
		DECLARE_INTERFACE_( IDropTargetHelper, IUnknown )
		{
			// IUnknown methods
			STDMETHOD (QueryInterface)(THIS_ REFIID riid, void **ppv) PURE;
			STDMETHOD_(ULONG, AddRef) ( THIS ) PURE;
			STDMETHOD_(ULONG, Release) ( THIS ) PURE;

		    // IDropTargetHelper
			STDMETHOD (DragEnter)(THIS_ HWND hwndTarget, IDataObject* pDataObject,
                          POINT* ppt, DWORD dwEffect) PURE;
			STDMETHOD (DragLeave)(THIS) PURE;
			STDMETHOD (DragOver)(THIS_ POINT* ppt, DWORD dwEffect) PURE;
			STDMETHOD (Drop)(THIS_ IDataObject* pDataObject, POINT* ppt,
                     DWORD dwEffect) PURE;
			STDMETHOD (Show)(THIS_ BOOL fShow) PURE;

		};
	#endif /* IDropTargetHelper */

	#define WM_MENURBUTTONUP                0x0122

// tabsrmm uses these

#define SES_EXTENDBACKCOLOR	4
#define EM_SETEDITSTYLE (WM_USER + 204)
#define EM_SETSCROLLPOS (WM_USER + 222)
#define SF_USECODEPAGE 0x00000020

#define TreeView_SetItemState(hwndTV, hti, data, _mask) \
{ TVITEM _TVi; \
  _TVi.mask = TVIF_STATE; \
  _TVi.hItem = hti; \
  _TVi.stateMask = _mask; \
  _TVi.state = data; \
  SendMessage((hwndTV), TVM_SETITEM, 0, (LPARAM)(TV_ITEM *)&_TVi); \
}

#endif /* SDK check */
#endif // WIN2K_H__
