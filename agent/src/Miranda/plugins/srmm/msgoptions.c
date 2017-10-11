/*
SRMM

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
#pragma hdrstop

extern HINSTANCE g_hInst;

#define FONTF_BOLD   1
#define FONTF_ITALIC 2
struct FontOptionsList
{
	TCHAR*   szDescr;
	COLORREF defColour;
	TCHAR*   szDefFace;
	BYTE     defCharset, defStyle;
	char     defSize;
	COLORREF colour;
	TCHAR    szFace[LF_FACESIZE];
	BYTE     charset, style;
	char     size;
}
static fontOptionsList[] = {
	{_T("Outgoing messages"), RGB(106, 106, 106), _T("Arial"), DEFAULT_CHARSET, 0, -12},
	{_T("Incoming messages"), RGB(0, 0, 0), _T("Arial"), DEFAULT_CHARSET, 0, -12},
	{_T("Outgoing name"), RGB(89, 89, 89), _T("Arial"), DEFAULT_CHARSET, FONTF_BOLD, -12},
	{_T("Outgoing time"), RGB(0, 0, 0), _T("Terminal"), DEFAULT_CHARSET, FONTF_BOLD, -9},
	{_T("Outgoing colon"), RGB(89, 89, 89), _T("Arial"), DEFAULT_CHARSET, 0, -11},
	{_T("Incoming name"), RGB(215, 0, 0), _T("Arial"), DEFAULT_CHARSET, FONTF_BOLD, -12},
	{_T("Incoming time"), RGB(0, 0, 0), _T("Terminal"), DEFAULT_CHARSET, FONTF_BOLD, -9},
	{_T("Incoming colon"), RGB(215, 0, 0), _T("Arial"), DEFAULT_CHARSET, 0, -11},
	{_T("Message area"), RGB(0, 0, 0), _T("Arial"), DEFAULT_CHARSET, 0, -12},
	{_T("Notices"), RGB(90, 90, 160), _T("Arial"), DEFAULT_CHARSET, 0, -12},
};
const int msgDlgFontCount = SIZEOF(fontOptionsList);

void LoadMsgDlgFont(int i, LOGFONT* lf, COLORREF * colour)
{
	char str[32];
	int style;
	DBVARIANT dbv;

	if (colour) {
		wsprintfA(str, "SRMFont%dCol", i);
		*colour = DBGetContactSettingDword(NULL, SRMMMOD, str, fontOptionsList[i].defColour);
	}
	if (lf) {
		wsprintfA(str, "SRMFont%dSize", i);
		lf->lfHeight = (char) DBGetContactSettingByte(NULL, SRMMMOD, str, fontOptionsList[i].defSize);
		lf->lfWidth = 0;
		lf->lfEscapement = 0;
		lf->lfOrientation = 0;
		wsprintfA(str, "SRMFont%dSty", i);
		style = DBGetContactSettingByte(NULL, SRMMMOD, str, fontOptionsList[i].defStyle);
		lf->lfWeight = style & FONTF_BOLD ? FW_BOLD : FW_NORMAL;
		lf->lfItalic = style & FONTF_ITALIC ? 1 : 0;
		lf->lfUnderline = 0;
		lf->lfStrikeOut = 0;
		wsprintfA(str, "SRMFont%dSet", i);
		lf->lfCharSet = DBGetContactSettingByte(NULL, SRMMMOD, str, fontOptionsList[i].defCharset);
		lf->lfOutPrecision = OUT_DEFAULT_PRECIS;
		lf->lfClipPrecision = CLIP_DEFAULT_PRECIS;
		lf->lfQuality = DEFAULT_QUALITY;
		lf->lfPitchAndFamily = DEFAULT_PITCH | FF_DONTCARE;
		wsprintfA(str, "SRMFont%d", i);
		if (DBGetContactSettingTString(NULL, SRMMMOD, str, &dbv))
			lstrcpy(lf->lfFaceName, fontOptionsList[i].szDefFace);
		else {
			lstrcpyn(lf->lfFaceName, dbv.ptszVal, SIZEOF(lf->lfFaceName));
			DBFreeVariant(&dbv);
		}
	}
}
struct CheckBoxValues_t
{
    DWORD  style;
    TCHAR* szDescr;
}
static const statusValues[] = 
{
	{ PF2_ONLINE,     _T("Online")        },
	{ PF2_SHORTAWAY,  _T("Away")          },
	{ PF2_LONGAWAY,   _T("NA")            },
	{ PF2_LIGHTDND,   _T("Occupied")      },
	{ PF2_HEAVYDND,   _T("DND")           },
	{ PF2_FREECHAT,   _T("Free for chat") },
	{ PF2_INVISIBLE,  _T("Invisible")     },
	{ PF2_OUTTOLUNCH, _T("Out to lunch")  },
	{ PF2_ONTHEPHONE, _T("On the phone")  }
};

static void FillCheckBoxTree(HWND hwndTree, const struct CheckBoxValues_t *values, int nValues, DWORD style)
{
	TVINSERTSTRUCT tvis;
	int i;

	tvis.hParent = NULL;
	tvis.hInsertAfter = TVI_LAST;
	tvis.item.mask = TVIF_PARAM | TVIF_TEXT | TVIF_STATE;
	for (i = 0; i < nValues; i++) {
		tvis.item.lParam = values[i].style;
		tvis.item.pszText = TranslateTS(values[i].szDescr);
		tvis.item.stateMask = TVIS_STATEIMAGEMASK;
		tvis.item.state = INDEXTOSTATEIMAGEMASK((style & tvis.item.lParam) != 0 ? 2 : 1);
		TreeView_InsertItem( hwndTree, &tvis );
}	}

static DWORD MakeCheckBoxTreeFlags(HWND hwndTree)
{
    DWORD flags = 0;
    TVITEM tvi;

    tvi.mask = TVIF_HANDLE | TVIF_PARAM | TVIF_STATE;
    tvi.hItem = TreeView_GetRoot(hwndTree);
    while (tvi.hItem) {
        TreeView_GetItem(hwndTree, &tvi);
        if (((tvi.state & TVIS_STATEIMAGEMASK) >> 12 == 2))
            flags |= tvi.lParam;
        tvi.hItem = TreeView_GetNextSibling(hwndTree, tvi.hItem);
    }
    return flags;
}

static BOOL CALLBACK DlgProcOptions(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg) {
		case WM_INITDIALOG:
		{
			DWORD msgTimeout, avatarHeight;

			TranslateDialogDefault(hwndDlg);
			SetWindowLong(GetDlgItem(hwndDlg, IDC_POPLIST), GWL_STYLE, GetWindowLong(GetDlgItem(hwndDlg, IDC_POPLIST), GWL_STYLE) | TVS_NOHSCROLL | TVS_CHECKBOXES);
			FillCheckBoxTree(GetDlgItem(hwndDlg, IDC_POPLIST), statusValues, SIZEOF(statusValues),
                             DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_POPFLAGS, SRMSGDEFSET_POPFLAGS));
			CheckDlgButton(hwndDlg, IDC_SHOWBUTTONLINE, g_dat->flags&SMF_SHOWBTNS);
			CheckDlgButton(hwndDlg, IDC_SHOWINFOLINE, g_dat->flags&SMF_SHOWINFO);
			CheckDlgButton(hwndDlg, IDC_AUTOMIN, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOMIN, SRMSGDEFSET_AUTOMIN));
			CheckDlgButton(hwndDlg, IDC_AUTOCLOSE, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOCLOSE, SRMSGDEFSET_AUTOCLOSE));
			CheckDlgButton(hwndDlg, IDC_SAVEPERCONTACT, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SAVEPERCONTACT, SRMSGDEFSET_SAVEPERCONTACT));
			CheckDlgButton(hwndDlg, IDC_CASCADE, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CASCADE, SRMSGDEFSET_CASCADE));
			CheckDlgButton(hwndDlg, IDC_SENDONENTER, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDONENTER, SRMSGDEFSET_SENDONENTER));
			CheckDlgButton(hwndDlg, IDC_SENDONDBLENTER, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDONDBLENTER, SRMSGDEFSET_SENDONDBLENTER));
			CheckDlgButton(hwndDlg, IDC_STATUSWIN, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_STATUSICON, SRMSGDEFSET_STATUSICON));
			
			CheckDlgButton(hwndDlg, IDC_AVATARSUPPORT, g_dat->flags&SMF_AVATAR);
			CheckDlgButton(hwndDlg, IDC_LIMITAVATARH, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_LIMITAVHEIGHT, SRMSGDEFSET_LIMITAVHEIGHT));
			avatarHeight = DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_AVHEIGHT, SRMSGDEFSET_AVHEIGHT);
			SetDlgItemInt(hwndDlg, IDC_AVATARHEIGHT, avatarHeight, FALSE);
			EnableWindow(GetDlgItem(hwndDlg, IDC_LIMITAVATARH), IsDlgButtonChecked(hwndDlg, IDC_AVATARSUPPORT));
			if (!IsDlgButtonChecked(hwndDlg, IDC_AVATARSUPPORT)) 
				EnableWindow(GetDlgItem(hwndDlg, IDC_AVATARHEIGHT), FALSE);
			else EnableWindow(GetDlgItem(hwndDlg, IDC_AVATARHEIGHT), IsDlgButtonChecked(hwndDlg, IDC_LIMITAVATARH));
			CheckDlgButton(hwndDlg, IDC_SHOWSENDBTN, g_dat->flags&SMF_SENDBTN);
			CheckDlgButton(hwndDlg, IDC_CHARCOUNT, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CHARCOUNT, SRMSGDEFSET_CHARCOUNT));
			CheckDlgButton(hwndDlg, IDC_CTRLSUPPORT, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CTRLSUPPORT, SRMSGDEFSET_CTRLSUPPORT));
			CheckDlgButton(hwndDlg, IDC_DELTEMP, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_DELTEMP, SRMSGDEFSET_DELTEMP));
			msgTimeout = DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_MSGTIMEOUT, SRMSGDEFSET_MSGTIMEOUT);
			SetDlgItemInt(hwndDlg, IDC_SECONDS, msgTimeout >= SRMSGSET_MSGTIMEOUT_MIN ? msgTimeout / 1000 : SRMSGDEFSET_MSGTIMEOUT / 1000, FALSE);
			EnableWindow(GetDlgItem(hwndDlg, IDC_CASCADE), !IsDlgButtonChecked(hwndDlg, IDC_SAVEPERCONTACT));
			EnableWindow(GetDlgItem(hwndDlg, IDC_CTRLSUPPORT), !IsDlgButtonChecked(hwndDlg, IDC_AUTOCLOSE));
			return TRUE;
		}
		case WM_COMMAND:
			switch (LOWORD(wParam)) {
				case IDC_AUTOMIN:
					CheckDlgButton(hwndDlg, IDC_AUTOCLOSE, BST_UNCHECKED);
					break;
				case IDC_AUTOCLOSE:
					CheckDlgButton(hwndDlg, IDC_AUTOMIN, BST_UNCHECKED);
					EnableWindow(GetDlgItem(hwndDlg, IDC_CTRLSUPPORT), !IsDlgButtonChecked(hwndDlg, IDC_AUTOCLOSE));
					break;
				case IDC_SENDONENTER:
					CheckDlgButton(hwndDlg, IDC_SENDONDBLENTER, BST_UNCHECKED);
					break;
				case IDC_SENDONDBLENTER:
					CheckDlgButton(hwndDlg, IDC_SENDONENTER, BST_UNCHECKED);
					break;
				case IDC_SAVEPERCONTACT:
					EnableWindow(GetDlgItem(hwndDlg, IDC_CASCADE), !IsDlgButtonChecked(hwndDlg, IDC_SAVEPERCONTACT));
					break;
				case IDC_SECONDS:
					if (HIWORD(wParam) != EN_CHANGE || (HWND) lParam != GetFocus())
						return 0;
					break;
				case IDC_AVATARSUPPORT:
					EnableWindow(GetDlgItem(hwndDlg, IDC_LIMITAVATARH), IsDlgButtonChecked(hwndDlg, IDC_AVATARSUPPORT));
					if (!IsDlgButtonChecked(hwndDlg, IDC_AVATARSUPPORT)) 
						EnableWindow(GetDlgItem(hwndDlg, IDC_AVATARHEIGHT), FALSE);
					else EnableWindow(GetDlgItem(hwndDlg, IDC_AVATARHEIGHT), IsDlgButtonChecked(hwndDlg, IDC_LIMITAVATARH));
					break;
				case IDC_LIMITAVATARH:
					EnableWindow(GetDlgItem(hwndDlg, IDC_AVATARHEIGHT), IsDlgButtonChecked(hwndDlg, IDC_LIMITAVATARH));
					break;
				case IDC_AVATARHEIGHT:
					if (HIWORD(wParam) != EN_CHANGE || (HWND) lParam != GetFocus())
						return 0;
					break;
			}
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		case WM_NOTIFY:
			switch (((LPNMHDR) lParam)->idFrom) {
                case IDC_POPLIST:
                    if (((LPNMHDR) lParam)->code == NM_CLICK) {
                        TVHITTESTINFO hti;
                        hti.pt.x = (short) LOWORD(GetMessagePos());
                        hti.pt.y = (short) HIWORD(GetMessagePos());
                        ScreenToClient(((LPNMHDR) lParam)->hwndFrom, &hti.pt);
                        if (TreeView_HitTest(((LPNMHDR) lParam)->hwndFrom, &hti))
                            if (hti.flags & TVHT_ONITEMSTATEICON) {
                                TVITEM tvi;
                                tvi.mask = TVIF_HANDLE | TVIF_IMAGE | TVIF_SELECTEDIMAGE;
                                tvi.hItem = hti.hItem;
                                TreeView_GetItem(((LPNMHDR) lParam)->hwndFrom, &tvi);
                                tvi.iImage = tvi.iSelectedImage = tvi.iImage == 1 ? 2 : 1;
                                TreeView_SetItem(((LPNMHDR) lParam)->hwndFrom, &tvi);
                                SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
                            }
                    }
                    break;
				case 0:
					switch (((LPNMHDR) lParam)->code) {
						case PSN_APPLY:
						{
							DWORD msgTimeout, avatarHeight;
							
							DBWriteContactSettingDword(NULL, SRMMMOD, SRMSGSET_POPFLAGS, MakeCheckBoxTreeFlags(GetDlgItem(hwndDlg, IDC_POPLIST)));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWBUTTONLINE, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWBUTTONLINE));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWINFOLINE, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWINFOLINE));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOMIN, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_AUTOMIN));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOCLOSE, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_AUTOCLOSE));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SAVEPERCONTACT, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SAVEPERCONTACT));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_CASCADE, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_CASCADE));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDONENTER, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SENDONENTER));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDONDBLENTER, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SENDONDBLENTER));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_STATUSICON, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_STATUSWIN));
							
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_AVATARENABLE, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_AVATARSUPPORT));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_LIMITAVHEIGHT, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_LIMITAVATARH));
							avatarHeight = GetDlgItemInt(hwndDlg, IDC_AVATARHEIGHT, NULL, TRUE);
							DBWriteContactSettingDword(NULL, SRMMMOD, SRMSGSET_AVHEIGHT, avatarHeight<=0?SRMSGDEFSET_AVHEIGHT:avatarHeight);

							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDBUTTON, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWSENDBTN));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_CHARCOUNT, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_CHARCOUNT));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_CTRLSUPPORT, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_CTRLSUPPORT));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_DELTEMP, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_DELTEMP));
							msgTimeout = GetDlgItemInt(hwndDlg, IDC_SECONDS, NULL, TRUE) >= SRMSGSET_MSGTIMEOUT_MIN / 1000 ? GetDlgItemInt(hwndDlg, IDC_SECONDS, NULL, TRUE) * 1000 : SRMSGDEFSET_MSGTIMEOUT;
							DBWriteContactSettingDword(NULL, SRMMMOD, SRMSGSET_MSGTIMEOUT, msgTimeout);
							ReloadGlobals();
							WindowList_Broadcast(g_dat->hMessageWindowList, DM_OPTIONSAPPLIED, 0, 0);
							return TRUE;
						}
					}
					break;
			}
			break;
		case WM_DESTROY:
			break;
	}
	return FALSE;
}

static BOOL CALLBACK DlgProcLogOptions(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	static HBRUSH hBkgColourBrush;

	switch (msg) {
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			switch (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_LOADHISTORY, SRMSGDEFSET_LOADHISTORY)) {
				case LOADHISTORY_UNREAD:
					CheckDlgButton(hwndDlg, IDC_LOADUNREAD, BST_CHECKED);
					break;
				case LOADHISTORY_COUNT:
					CheckDlgButton(hwndDlg, IDC_LOADCOUNT, BST_CHECKED);
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADCOUNTN), TRUE);
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADCOUNTSPIN), TRUE);
					break;
				case LOADHISTORY_TIME:
					CheckDlgButton(hwndDlg, IDC_LOADTIME, BST_CHECKED);
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADTIMEN), TRUE);
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADTIMESPIN), TRUE);
					EnableWindow(GetDlgItem(hwndDlg, IDC_STMINSOLD), TRUE);
					break;
			}
			SendDlgItemMessage(hwndDlg, IDC_LOADCOUNTSPIN, UDM_SETRANGE, 0, MAKELONG(100, 0));
			SendDlgItemMessage(hwndDlg, IDC_LOADCOUNTSPIN, UDM_SETPOS, 0, DBGetContactSettingWord(NULL, SRMMMOD, SRMSGSET_LOADCOUNT, SRMSGDEFSET_LOADCOUNT));
			SendDlgItemMessage(hwndDlg, IDC_LOADTIMESPIN, UDM_SETRANGE, 0, MAKELONG(12 * 60, 0));
			SendDlgItemMessage(hwndDlg, IDC_LOADTIMESPIN, UDM_SETPOS, 0, DBGetContactSettingWord(NULL, SRMMMOD, SRMSGSET_LOADTIME, SRMSGDEFSET_LOADTIME));

			CheckDlgButton(hwndDlg, IDC_SHOWLOGICONS, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWLOGICONS, SRMSGDEFSET_SHOWLOGICONS));
			CheckDlgButton(hwndDlg, IDC_SHOWNAMES, !DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_HIDENAMES, SRMSGDEFSET_HIDENAMES));
			CheckDlgButton(hwndDlg, IDC_SHOWTIMES, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTIME, SRMSGDEFSET_SHOWTIME));
			EnableWindow(GetDlgItem(hwndDlg, IDC_SHOWSECS), IsDlgButtonChecked(hwndDlg, IDC_SHOWTIMES));
			CheckDlgButton(hwndDlg, IDC_SHOWSECS, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWSECS, SRMSGDEFSET_SHOWSECS));
			EnableWindow(GetDlgItem(hwndDlg, IDC_SHOWDATES), IsDlgButtonChecked(hwndDlg, IDC_SHOWTIMES));
			CheckDlgButton(hwndDlg, IDC_SHOWDATES, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWDATE, SRMSGDEFSET_SHOWDATE));
			CheckDlgButton(hwndDlg, IDC_SHOWSTATUSCHANGES, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWSTATUSCH, SRMSGDEFSET_SHOWSTATUSCH));
			SendDlgItemMessage(hwndDlg, IDC_BKGCOLOUR, CPM_SETCOLOUR, 0, DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_BKGCOLOUR, SRMSGDEFSET_BKGCOLOUR));
			SendDlgItemMessage(hwndDlg, IDC_BKGCOLOUR, CPM_SETDEFAULTCOLOUR, 0, SRMSGDEFSET_BKGCOLOUR);

			hBkgColourBrush = CreateSolidBrush(SendDlgItemMessage(hwndDlg, IDC_BKGCOLOUR, CPM_GETCOLOUR, 0, 0));

			{	int i;
				LOGFONT lf;
				for (i = 0; i < SIZEOF(fontOptionsList); i++) {
					LoadMsgDlgFont(i, &lf, &fontOptionsList[i].colour);
					lstrcpy(fontOptionsList[i].szFace, lf.lfFaceName);
					fontOptionsList[i].size = (char) lf.lfHeight;
					fontOptionsList[i].style = (lf.lfWeight >= FW_BOLD ? FONTF_BOLD : 0) | (lf.lfItalic ? FONTF_ITALIC : 0);
					fontOptionsList[i].charset = lf.lfCharSet;
					//I *think* some OSs will fail LB_ADDSTRING if lParam==0
					SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_ADDSTRING, 0, i + 1);
				}
				SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_SETSEL, TRUE, 0);
				SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_SETCOLOUR, 0, fontOptionsList[0].colour);
				SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_SETDEFAULTCOLOUR, 0, fontOptionsList[0].defColour);
			}
			return TRUE;
		case WM_CTLCOLORLISTBOX:
			SetBkColor((HDC) wParam, SendDlgItemMessage(hwndDlg, IDC_BKGCOLOUR, CPM_GETCOLOUR, 0, 0));
			return (BOOL) hBkgColourBrush;
		case WM_MEASUREITEM:
		{
			MEASUREITEMSTRUCT *mis = (MEASUREITEMSTRUCT *) lParam;
			HFONT hFont, hoFont;
			HDC hdc;
			SIZE fontSize;
			int iItem = mis->itemData - 1;
			hFont = CreateFont(fontOptionsList[iItem].size, 0, 0, 0,
								fontOptionsList[iItem].style & FONTF_BOLD ? FW_BOLD : FW_NORMAL,
								fontOptionsList[iItem].style & FONTF_ITALIC ? 1 : 0, 0, 0, fontOptionsList[iItem].charset, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH | FF_DONTCARE, fontOptionsList[iItem].szFace);
			hdc = GetDC(GetDlgItem(hwndDlg, mis->CtlID));
			hoFont = (HFONT) SelectObject(hdc, hFont);
			GetTextExtentPoint32(hdc, fontOptionsList[iItem].szDescr, lstrlen(fontOptionsList[iItem].szDescr), &fontSize);
			SelectObject(hdc, hoFont);
			ReleaseDC(GetDlgItem(hwndDlg, mis->CtlID), hdc);
			DeleteObject(hFont);
			mis->itemWidth = fontSize.cx;
			mis->itemHeight = fontSize.cy;
			return TRUE;
		}
		case WM_DRAWITEM:
		{
			DRAWITEMSTRUCT* dis = (DRAWITEMSTRUCT *) lParam;
			HFONT hFont, hoFont;
			TCHAR* pszText;
			int iItem = dis->itemData - 1;
			hFont = CreateFont(fontOptionsList[iItem].size, 0, 0, 0,
								fontOptionsList[iItem].style & FONTF_BOLD ? FW_BOLD : FW_NORMAL,
								fontOptionsList[iItem].style & FONTF_ITALIC ? 1 : 0, 0, 0, fontOptionsList[iItem].charset, OUT_DEFAULT_PRECIS, CLIP_DEFAULT_PRECIS, DEFAULT_QUALITY, DEFAULT_PITCH | FF_DONTCARE, fontOptionsList[iItem].szFace);
			hoFont = (HFONT) SelectObject(dis->hDC, hFont);
			SetBkMode(dis->hDC, TRANSPARENT);
			FillRect(dis->hDC, &dis->rcItem, hBkgColourBrush);
			if (dis->itemState & ODS_SELECTED)
				FrameRect(dis->hDC, &dis->rcItem, GetSysColorBrush(COLOR_HIGHLIGHT));
			SetTextColor(dis->hDC, fontOptionsList[iItem].colour);
			pszText = TranslateTS(fontOptionsList[iItem].szDescr);
			TextOut(dis->hDC, dis->rcItem.left, dis->rcItem.top, pszText, lstrlen(pszText));
			SelectObject(dis->hDC, hoFont);
			DeleteObject(hFont);
			return TRUE;
		}
		case WM_COMMAND:
			switch (LOWORD(wParam)) {
				case IDC_LOADUNREAD:
				case IDC_LOADCOUNT:
				case IDC_LOADTIME:
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADCOUNTN), IsDlgButtonChecked(hwndDlg, IDC_LOADCOUNT));
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADCOUNTSPIN), IsDlgButtonChecked(hwndDlg, IDC_LOADCOUNT));
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADTIMEN), IsDlgButtonChecked(hwndDlg, IDC_LOADTIME));
					EnableWindow(GetDlgItem(hwndDlg, IDC_LOADTIMESPIN), IsDlgButtonChecked(hwndDlg, IDC_LOADTIME));
					EnableWindow(GetDlgItem(hwndDlg, IDC_STMINSOLD), IsDlgButtonChecked(hwndDlg, IDC_LOADTIME));
					break;
				case IDC_SHOWTIMES:
					EnableWindow(GetDlgItem(hwndDlg, IDC_SHOWSECS), IsDlgButtonChecked(hwndDlg, IDC_SHOWTIMES));
					EnableWindow(GetDlgItem(hwndDlg, IDC_SHOWDATES), IsDlgButtonChecked(hwndDlg, IDC_SHOWTIMES));
					break;
				case IDC_BKGCOLOUR:
					DeleteObject(hBkgColourBrush);
					hBkgColourBrush = CreateSolidBrush(SendDlgItemMessage(hwndDlg, IDC_BKGCOLOUR, CPM_GETCOLOUR, 0, 0));
					InvalidateRect(GetDlgItem(hwndDlg, IDC_FONTLIST), NULL, TRUE);
					break;
				case IDC_FONTLIST:
					if (HIWORD(wParam) == LBN_SELCHANGE) {
						if (SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETSELCOUNT, 0, 0) > 1) {
							SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_SETCOLOUR, 0, GetSysColor(COLOR_3DFACE));
							SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_SETDEFAULTCOLOUR, 0, GetSysColor(COLOR_WINDOWTEXT));
						}
						else {
							int i = SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETITEMDATA,
													   SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETCURSEL, 0, 0), 0) - 1;
							SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_SETCOLOUR, 0, fontOptionsList[i].colour);
							SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_SETDEFAULTCOLOUR, 0, fontOptionsList[i].defColour);
						}
					}
					if (HIWORD(wParam) != LBN_DBLCLK)
						return TRUE;
					//fall through
				case IDC_CHOOSEFONT:
				{
					CHOOSEFONT cf = { 0 };
					LOGFONT lf = { 0 };
					int i = SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETITEMDATA, SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETCURSEL, 0, 0),
											   0) - 1;
					lf.lfHeight = fontOptionsList[i].size;
					lf.lfWeight = fontOptionsList[i].style & FONTF_BOLD ? FW_BOLD : FW_NORMAL;
					lf.lfItalic = fontOptionsList[i].style & FONTF_ITALIC ? 1 : 0;
					lf.lfCharSet = fontOptionsList[i].charset;
					lf.lfOutPrecision = OUT_DEFAULT_PRECIS;
					lf.lfClipPrecision = CLIP_DEFAULT_PRECIS;
					lf.lfQuality = DEFAULT_QUALITY;
					lf.lfPitchAndFamily = DEFAULT_PITCH | FF_DONTCARE;
					lstrcpy(lf.lfFaceName, fontOptionsList[i].szFace);
					cf.lStructSize = sizeof(cf);
					cf.hwndOwner = hwndDlg;
					cf.lpLogFont = &lf;
					cf.Flags = CF_FORCEFONTEXIST | CF_INITTOLOGFONTSTRUCT | CF_SCREENFONTS;
					if (ChooseFont(&cf)) {
						int selItems[ SIZEOF(fontOptionsList) ];
						int sel, selCount;

						selCount = SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETSELITEMS, SIZEOF(fontOptionsList), (LPARAM) selItems);
						for (sel = 0; sel < selCount; sel++) {
							i = SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETITEMDATA, selItems[sel], 0) - 1;
							fontOptionsList[i].size = (char) lf.lfHeight;
							fontOptionsList[i].style = (lf.lfWeight >= FW_BOLD ? FONTF_BOLD : 0) | (lf.lfItalic ? FONTF_ITALIC : 0);
							fontOptionsList[i].charset = lf.lfCharSet;
							lstrcpy(fontOptionsList[i].szFace, lf.lfFaceName);
							{
								MEASUREITEMSTRUCT mis = { 0 };
								mis.CtlID = IDC_FONTLIST;
								mis.itemData = i + 1;
								SendMessage(hwndDlg, WM_MEASUREITEM, 0, (LPARAM) & mis);
								SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_SETITEMHEIGHT, selItems[sel], mis.itemHeight);
							}
						}
						InvalidateRect(GetDlgItem(hwndDlg, IDC_FONTLIST), NULL, TRUE);
						break;
					}
					return TRUE;
				}
				case IDC_FONTCOLOUR:
				{
					int selItems[ SIZEOF(fontOptionsList) ];
					int sel, selCount, i;

					selCount = SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETSELITEMS, SIZEOF(fontOptionsList), (LPARAM) selItems);
					for (sel = 0; sel < selCount; sel++) {
						i = SendDlgItemMessage(hwndDlg, IDC_FONTLIST, LB_GETITEMDATA, selItems[sel], 0) - 1;
						fontOptionsList[i].colour = SendDlgItemMessage(hwndDlg, IDC_FONTCOLOUR, CPM_GETCOLOUR, 0, 0);
					}
					InvalidateRect(GetDlgItem(hwndDlg, IDC_FONTLIST), NULL, FALSE);
					break;
				}
				case IDC_LOADCOUNTN:
				case IDC_LOADTIMEN:
					if (HIWORD(wParam) != EN_CHANGE || (HWND) lParam != GetFocus())
						return TRUE;
					break;
			}
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		case WM_NOTIFY:
			switch (((LPNMHDR) lParam)->idFrom) {
				case 0:
					switch (((LPNMHDR) lParam)->code) {
						case PSN_APPLY:
							if (IsDlgButtonChecked(hwndDlg, IDC_LOADCOUNT))
								DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_LOADHISTORY, LOADHISTORY_COUNT);
							else if (IsDlgButtonChecked(hwndDlg, IDC_LOADTIME))
								DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_LOADHISTORY, LOADHISTORY_TIME);
							else
								DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_LOADHISTORY, LOADHISTORY_UNREAD);
							DBWriteContactSettingWord(NULL, SRMMMOD, SRMSGSET_LOADCOUNT, (WORD) SendDlgItemMessage(hwndDlg, IDC_LOADCOUNTSPIN, UDM_GETPOS, 0, 0));
							DBWriteContactSettingWord(NULL, SRMMMOD, SRMSGSET_LOADTIME, (WORD) SendDlgItemMessage(hwndDlg, IDC_LOADTIMESPIN, UDM_GETPOS, 0, 0));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWLOGICONS, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWLOGICONS));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_HIDENAMES, (BYTE) ! IsDlgButtonChecked(hwndDlg, IDC_SHOWNAMES));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTIME, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWTIMES));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWSECS, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWSECS));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWDATE, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWDATES));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWSTATUSCH, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWSTATUSCHANGES));
							DBWriteContactSettingDword(NULL, SRMMMOD, SRMSGSET_BKGCOLOUR, SendDlgItemMessage(hwndDlg, IDC_BKGCOLOUR, CPM_GETCOLOUR, 0, 0));

							{
								int i;
								char str[32];
								for (i = 0; i < SIZEOF(fontOptionsList); i++) {
									wsprintfA(str, "SRMFont%d", i);
									DBWriteContactSettingTString(NULL, SRMMMOD, str, fontOptionsList[i].szFace);
									wsprintfA(str, "SRMFont%dSize", i);
									DBWriteContactSettingByte(NULL, SRMMMOD, str, fontOptionsList[i].size);
									wsprintfA(str, "SRMFont%dSty", i);
									DBWriteContactSettingByte(NULL, SRMMMOD, str, fontOptionsList[i].style);
									wsprintfA(str, "SRMFont%dSet", i);
									DBWriteContactSettingByte(NULL, SRMMMOD, str, fontOptionsList[i].charset);
									wsprintfA(str, "SRMFont%dCol", i);
									DBWriteContactSettingDword(NULL, SRMMMOD, str, fontOptionsList[i].colour);
								}
							}

							FreeMsgLogIcons();
							LoadMsgLogIcons();
							ReloadGlobals();
							WindowList_Broadcast(g_dat->hMessageWindowList, DM_OPTIONSAPPLIED, 0, 0);
							return TRUE;
					}
					break;
			}
			break;
		case WM_DESTROY:
			DeleteObject(hBkgColourBrush);
			break;
	}
	return FALSE;
}

static ResetCList(HWND hwndDlg)
{
	int i;

	if (CallService(MS_CLUI_GETCAPS, 0, 0) & CLUIF_DISABLEGROUPS && !DBGetContactSettingByte(NULL, "CList", "UseGroups", SETTING_USEGROUPS_DEFAULT))
		SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETUSEGROUPS, (WPARAM) FALSE, 0);
	else
		SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETUSEGROUPS, (WPARAM) TRUE, 0);
	SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETHIDEEMPTYGROUPS, 1, 0);
	SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETGREYOUTFLAGS, 0, 0);
	SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETLEFTMARGIN, 2, 0);
	SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETBKBITMAP, 0, (LPARAM) (HBITMAP) NULL);
	SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETBKCOLOR, GetSysColor(COLOR_WINDOW), 0);
	SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETINDENT, 10, 0);
	for (i = 0; i <= FONTID_MAX; i++)
		SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETTEXTCOLOR, i, GetSysColor(COLOR_WINDOWTEXT));
}

static void RebuildList(HWND hwndDlg, HANDLE hItemNew, HANDLE hItemUnknown)
{
	HANDLE hContact, hItem;
	BYTE defType = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_TYPINGNEW, SRMSGDEFSET_TYPINGNEW);

	if (hItemNew && defType) {
		SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETCHECKMARK, (WPARAM) hItemNew, 1);
	}
	if (hItemUnknown && DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_TYPINGUNKNOWN, SRMSGDEFSET_TYPINGUNKNOWN)) {
		SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETCHECKMARK, (WPARAM) hItemUnknown, 1);
	}
	hContact = (HANDLE) CallService(MS_DB_CONTACT_FINDFIRST, 0, 0);
	do {
		hItem = (HANDLE) SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_FINDCONTACT, (WPARAM) hContact, 0);
		if (hItem && DBGetContactSettingByte(hContact, SRMMMOD, SRMSGSET_TYPING, defType)) {
			SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_SETCHECKMARK, (WPARAM) hItem, 1);
		}
	} while (hContact = (HANDLE) CallService(MS_DB_CONTACT_FINDNEXT, (WPARAM) hContact, 0));
}

static void SaveList(HWND hwndDlg, HANDLE hItemNew, HANDLE hItemUnknown)
{
	HANDLE hContact, hItem;

	if (hItemNew) {
		DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_TYPINGNEW, (BYTE) (SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_GETCHECKMARK, (WPARAM) hItemNew, 0) ? 1 : 0));
	}
	if (hItemUnknown) {
		DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_TYPINGUNKNOWN, (BYTE) (SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_GETCHECKMARK, (WPARAM) hItemUnknown, 0) ? 1 : 0));
	}
	hContact = (HANDLE) CallService(MS_DB_CONTACT_FINDFIRST, 0, 0);
	do {
		hItem = (HANDLE) SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_FINDCONTACT, (WPARAM) hContact, 0);
		if (hItem) {
			DBWriteContactSettingByte(hContact, SRMMMOD, SRMSGSET_TYPING, (BYTE) (SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_GETCHECKMARK, (WPARAM) hItem, 0) ? 1 : 0));
		}
	} while (hContact = (HANDLE) CallService(MS_DB_CONTACT_FINDNEXT, (WPARAM) hContact, 0));
}

static BOOL CALLBACK DlgProcTypeOptions(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	static HANDLE hItemNew, hItemUnknown;

	switch (msg) {
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			{
				CLCINFOITEM cii = { 0 };
				cii.cbSize = sizeof(cii);
				cii.flags = CLCIIF_GROUPFONT | CLCIIF_CHECKBOX;
				cii.pszText = TranslateT("** New contacts **");
				hItemNew = (HANDLE) SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_ADDINFOITEM, 0, (LPARAM) & cii);
				cii.pszText = TranslateT("** Unknown contacts **");
				hItemUnknown = (HANDLE) SendDlgItemMessage(hwndDlg, IDC_CLIST, CLM_ADDINFOITEM, 0, (LPARAM) & cii);
			}
			SetWindowLong(GetDlgItem(hwndDlg, IDC_CLIST), GWL_STYLE, GetWindowLong(GetDlgItem(hwndDlg, IDC_CLIST), GWL_STYLE) | (CLS_SHOWHIDDEN) | (CLS_NOHIDEOFFLINE));
			ResetCList(hwndDlg);
			RebuildList(hwndDlg, hItemNew, hItemUnknown);
			CheckDlgButton(hwndDlg, IDC_SHOWNOTIFY, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPING, SRMSGDEFSET_SHOWTYPING));
			CheckDlgButton(hwndDlg, IDC_TYPEWIN, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGWIN, SRMSGDEFSET_SHOWTYPINGWIN));
			CheckDlgButton(hwndDlg, IDC_TYPETRAY, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGNOWIN, SRMSGDEFSET_SHOWTYPINGNOWIN));
			CheckDlgButton(hwndDlg, IDC_NOTIFYTRAY, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGCLIST, SRMSGDEFSET_SHOWTYPINGCLIST));
			CheckDlgButton(hwndDlg, IDC_NOTIFYBALLOON, !DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGCLIST, SRMSGDEFSET_SHOWTYPINGCLIST));
			EnableWindow(GetDlgItem(hwndDlg, IDC_TYPEWIN), IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY));
			EnableWindow(GetDlgItem(hwndDlg, IDC_TYPETRAY), IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY));
			EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYTRAY), IsDlgButtonChecked(hwndDlg, IDC_TYPETRAY));
			EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYBALLOON), IsDlgButtonChecked(hwndDlg, IDC_TYPETRAY));
			if (!ServiceExists(MS_CLIST_SYSTRAY_NOTIFY)) {
				EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYBALLOON), FALSE);
				CheckDlgButton(hwndDlg, IDC_NOTIFYTRAY, BST_CHECKED);
				SetWindowTextA(GetDlgItem(hwndDlg, IDC_NOTIFYBALLOON), Translate("Show balloon popup (unsupported system)"));
			}
			break;
		case WM_COMMAND:
			switch (LOWORD(wParam)) {
				case IDC_TYPETRAY:
					if (IsDlgButtonChecked(hwndDlg, IDC_TYPETRAY)) {
						if (!ServiceExists(MS_CLIST_SYSTRAY_NOTIFY)) {
							EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYTRAY), TRUE);
						}
						else {
							EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYTRAY), TRUE);
							EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYBALLOON), TRUE);
						}
					}
					else {
						EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYTRAY), FALSE);
						EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYBALLOON), FALSE);
					}
					SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
					break;
				case IDC_SHOWNOTIFY:
					EnableWindow(GetDlgItem(hwndDlg, IDC_TYPEWIN), IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY));
					EnableWindow(GetDlgItem(hwndDlg, IDC_TYPETRAY), IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY));
					EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYTRAY), IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY));
					EnableWindow(GetDlgItem(hwndDlg, IDC_NOTIFYBALLOON), IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY)
								 && ServiceExists(MS_CLIST_SYSTRAY_NOTIFY));
					//fall-thru
				case IDC_TYPEWIN:
				case IDC_NOTIFYTRAY:
				case IDC_NOTIFYBALLOON:
					SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
					break;
			}
			break;
		case WM_NOTIFY:
			switch (((NMHDR *) lParam)->idFrom) {
				case IDC_CLIST:
					switch (((NMHDR *) lParam)->code) {
						case CLN_OPTIONSCHANGED:
							ResetCList(hwndDlg);
							break;
						case CLN_CHECKCHANGED:
							SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
							break;
					}
					break;
				case 0:
					switch (((LPNMHDR) lParam)->code) {
						case PSN_APPLY:
						{
							SaveList(hwndDlg, hItemNew, hItemUnknown);
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPING, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_SHOWNOTIFY));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGWIN, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_TYPEWIN));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGNOWIN, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_TYPETRAY));
							DBWriteContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWTYPINGCLIST, (BYTE) IsDlgButtonChecked(hwndDlg, IDC_NOTIFYTRAY));
							ReloadGlobals();
							WindowList_Broadcast(g_dat->hMessageWindowList, DM_OPTIONSAPPLIED, 0, 0);
						}
					}
					break;
			}
			break;
	}
	return FALSE;
}

static int OptInitialise(WPARAM wParam, LPARAM lParam)
{
	OPTIONSDIALOGPAGE odp = { 0 };

	odp.cbSize = sizeof(odp);
	odp.position = 910000000;
	odp.hInstance = g_hInst;
	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_MSGDLG);
	odp.pszTitle = "Messaging";
	odp.pszGroup = "Events";
	odp.pfnDlgProc = DlgProcOptions;
	odp.flags = ODPF_BOLDGROUPS;
	CallService(MS_OPT_ADDPAGE, wParam, (LPARAM) & odp);

	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_MSGLOG);
	odp.pszTitle = "Messaging Log";
	odp.pfnDlgProc = DlgProcLogOptions;
	odp.nIDBottomSimpleControl = IDC_STMSGLOGGROUP;
	CallService(MS_OPT_ADDPAGE, wParam, (LPARAM) & odp);

	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_MSGTYPE);
	odp.pszTitle = "Typing Notify";
	odp.pfnDlgProc = DlgProcTypeOptions;
	odp.nIDBottomSimpleControl = 0;
	CallService(MS_OPT_ADDPAGE, wParam, (LPARAM) & odp);
	return 0;
}

int InitOptions(void)
{
	HookEvent(ME_OPT_INITIALISE, OptInitialise);
	return 0;
}
