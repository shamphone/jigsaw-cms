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

#define _USE_32BIT_TIME_T

#include "commonheaders.h"
#include "statusicon.h"
#pragma hdrstop

#include <malloc.h>

#define TIMERID_MSGSEND      0
#define TIMERID_FLASHWND     1
#define TIMERID_TYPE         2
#define TIMEOUT_FLASHWND     900
#define TIMEOUT_TYPEOFF      10000      //send type off after 10 seconds of inactivity
#define SB_CHAR_WIDTH        45
#define SB_GRIP_WIDTH        20         // pixels - buffer used to prevent sizegrip from overwriting statusbar icons
#define VALID_AVATAR(x)      (x==PA_FORMAT_PNG||x==PA_FORMAT_JPEG||x==PA_FORMAT_ICON||x==PA_FORMAT_BMP||x==PA_FORMAT_GIF)

#if defined(_UNICODE)
	#define SEND_FLAGS PREF_UNICODE
#else
	#define SEND_FLAGS 0
#endif

extern HCURSOR hCurSplitNS, hCurSplitWE, hCurHyperlinkHand;
extern HANDLE hHookWinEvt;
extern struct CREOleCallback reOleCallback;
extern HINSTANCE g_hInst;

static void UpdateReadChars(HWND hwndDlg, HWND hwndStatus);

static WNDPROC OldMessageEditProc, OldSplitterProc;
static const UINT infoLineControls[] = { IDC_PROTOCOL, IDC_NAME };
static const UINT buttonLineControls[] = { IDC_ADD, IDC_USERMENU, IDC_DETAILS, IDC_HISTORY };
static const UINT sendControls[] = { IDC_MESSAGE };

static void NotifyLocalWinEvent(HANDLE hContact, HWND hwnd, unsigned int type) {
	MessageWindowEventData mwe = { 0 };

	if (hContact==NULL || hwnd==NULL) return;
	mwe.cbSize = sizeof(mwe);
	mwe.hContact = hContact;
	mwe.hwndWindow = hwnd;
	mwe.szModule = SRMMMOD;
	mwe.uType = type;
	mwe.uFlags = MSG_WINDOW_UFLAG_MSG_BOTH;
	NotifyEventHooks(hHookWinEvt, 0, (LPARAM)&mwe);
}

static char *MsgServiceName(HANDLE hContact)
{
#ifdef _UNICODE
    char szServiceName[100];
    char *szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) hContact, 0);
    if (szProto == NULL)
        return PSS_MESSAGE;

    mir_snprintf(szServiceName, SIZEOF(szServiceName), "%s%sW", szProto, PSS_MESSAGE);
    if (ServiceExists(szServiceName))
        return PSS_MESSAGE "W";
#endif
    return PSS_MESSAGE;
}

#if defined(_UNICODE)
static int RTL_Detect(WCHAR *pszwText)
{
    WORD *infoTypeC2;
    int i;
    int iLen = lstrlenW(pszwText);

    infoTypeC2 = (WORD *)malloc(sizeof(WORD) * (iLen + 2));

    if(infoTypeC2) {
        ZeroMemory(infoTypeC2, sizeof(WORD) * (iLen + 2));

        GetStringTypeW(CT_CTYPE2, pszwText, iLen, infoTypeC2);

        for(i = 0; i < iLen; i++) {
            if(infoTypeC2[i] == C2_RIGHTTOLEFT) {
                free(infoTypeC2);
                //_DebugTraceA("RTL text found");
                return 1;
            }
        }
        free(infoTypeC2);
        //_DebugTraceA("NO RTL text detected");
    }
    return 0;
}
#endif

static void AddToFileList(char ***pppFiles,int *totalCount,const TCHAR* szFilename)
{
	*pppFiles=(char**)realloc(*pppFiles,(++*totalCount+1)*sizeof(char*));
	(*pppFiles)[*totalCount] = NULL;

	#if defined( _UNICODE )
	{
		TCHAR tszShortName[ MAX_PATH ];
		char  szShortName[ MAX_PATH ];
		if ( GetShortPathName( szFilename, tszShortName, SIZEOF( tszShortName )) == 0 )
         WideCharToMultiByte( CP_ACP, 0, szFilename, -1, szShortName, sizeof( szShortName ), NULL, NULL );
		else
         WideCharToMultiByte( CP_ACP, 0, tszShortName, -1, szShortName, sizeof( szShortName ), NULL, NULL );
		(*pppFiles)[*totalCount-1] = _strdup( szShortName );
	}
	#else
		(*pppFiles)[*totalCount-1] = _strdup( szFilename );
	#endif
	
	if ( GetFileAttributes(szFilename) & FILE_ATTRIBUTE_DIRECTORY ) {
		WIN32_FIND_DATA fd;
		HANDLE hFind;
		TCHAR szPath[MAX_PATH];
		lstrcpy(szPath,szFilename);
		lstrcat(szPath,_T("\\*"));
		if (( hFind = FindFirstFile( szPath, &fd )) != INVALID_HANDLE_VALUE ) {
			do {
				if ( !lstrcmp(fd.cFileName,_T(".")) || !lstrcmp(fd.cFileName,_T(".."))) continue;
				lstrcpy(szPath,szFilename);
				lstrcat(szPath,_T("\\"));
				lstrcat(szPath,fd.cFileName);
				AddToFileList(pppFiles,totalCount,szPath);
			} 
				while( FindNextFile( hFind,&fd ));
			FindClose( hFind );
}	}	}

static void ShowMultipleControls(HWND hwndDlg, const UINT * controls, int cControls, int state)
{
	int i;
	for (i = 0; i < cControls; i++)
		ShowWindow(GetDlgItem(hwndDlg, controls[i]), state);
}

static void SetDialogToType(HWND hwndDlg)
{
	struct MessageWindowData *dat;
	WINDOWPLACEMENT pl = { 0 };
	int icons_width;
	RECT rc;

	dat = (struct MessageWindowData *) GetWindowLong(hwndDlg, GWL_USERDATA);
	if (dat->hContact) {
		ShowMultipleControls(hwndDlg, infoLineControls, SIZEOF(infoLineControls), (g_dat->flags&SMF_SHOWINFO) ? SW_SHOW : SW_HIDE);
	}
	else
		ShowMultipleControls(hwndDlg, infoLineControls, SIZEOF(infoLineControls), SW_HIDE);
	if (dat->hContact) {
		ShowMultipleControls(hwndDlg, buttonLineControls, SIZEOF(buttonLineControls), (g_dat->flags&SMF_SHOWBTNS) ? SW_SHOW : SW_HIDE);
		if (!DBGetContactSettingByte(dat->hContact, "CList", "NotOnList", 0))
			ShowWindow(GetDlgItem(hwndDlg, IDC_ADD), SW_HIDE);
	}
	else {
		ShowMultipleControls(hwndDlg, buttonLineControls, SIZEOF(buttonLineControls), SW_HIDE);
	}
	ShowMultipleControls(hwndDlg, sendControls, SIZEOF(sendControls), SW_SHOW);
	if (!dat->hwndStatus) {
		int grip = (GetWindowLong(hwndDlg, GWL_STYLE) & WS_THICKFRAME) ? SBARS_SIZEGRIP : 0;
		dat->hwndStatus = CreateWindowEx(0, /*STATUSCLASSNAME*/_T("Static"), NULL, WS_CHILD | WS_VISIBLE /*| grip*/, 0, 0, 0, 0, hwndDlg, NULL, g_hInst, NULL);
		SendMessage(dat->hwndStatus, SB_SETMINHEIGHT, GetSystemMetrics(SM_CYSMICON), 0);
	}

	icons_width = status_icon_list_size * (GetSystemMetrics(SM_CXSMICON) + 2) + SB_GRIP_WIDTH; 
	GetWindowRect(dat->hwndStatus, &rc);
	if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CHARCOUNT, SRMSGDEFSET_CHARCOUNT)) {
		int statwidths[3];

		statwidths[0] = (rc.right - rc.left - SB_CHAR_WIDTH - icons_width);
		statwidths[1] = (rc.right - rc.left - icons_width);
		statwidths[2] = -1;
		//SendMessage(dat->hwndStatus, SB_SETPARTS, 3, (LPARAM) statwidths);
	}
	else {
		int statwidths[2];
		statwidths[0] = (rc.right - rc.left - icons_width);
		statwidths[1] = -1;

		//SendMessage(dat->hwndStatus, SB_SETPARTS, 2, (LPARAM) statwidths);
	}
	UpdateReadChars(hwndDlg, dat->hwndStatus);
	ShowWindow(GetDlgItem(hwndDlg, IDCANCEL), SW_HIDE);
	ShowWindow(GetDlgItem(hwndDlg, IDC_SPLITTER), SW_SHOW);
	ShowWindow(GetDlgItem(hwndDlg, IDOK), (g_dat->flags&SMF_SENDBTN) ? SW_SHOW : SW_HIDE);
	EnableWindow(GetDlgItem(hwndDlg, IDOK), GetWindowTextLength(GetDlgItem(hwndDlg, IDC_MESSAGE))?TRUE:FALSE);
	if (dat->avatarPic==0||!(g_dat->flags&SMF_AVATAR))
		ShowWindow(GetDlgItem(hwndDlg, IDC_AVATAR), SW_HIDE);
	SendMessage(hwndDlg, DM_UPDATETITLE, 0, 0);
	SendMessage(hwndDlg, WM_SIZE, 0, 0);
	pl.length = sizeof(pl);
	GetWindowPlacement(hwndDlg, &pl);
	if (!IsWindowVisible(hwndDlg))
		pl.showCmd = SW_HIDE;
	SetWindowPlacement(hwndDlg, &pl);   //in case size is smaller than new minimum
}

struct SavedMessageData
{
	UINT message;
	WPARAM wParam;
	LPARAM lParam;
	DWORD keyStates;            //use MOD_ defines from RegisterHotKey()
};

struct MsgEditSubclassData
{
	DWORD lastEnterTime;
	struct SavedMessageData *keyboardMsgQueue;
	int msgQueueCount;
};

static void SaveKeyboardMessage(struct MsgEditSubclassData *dat, UINT message, WPARAM wParam, LPARAM lParam)
{
	dat->keyboardMsgQueue = (struct SavedMessageData *) realloc(dat->keyboardMsgQueue, sizeof(struct SavedMessageData) * (dat->msgQueueCount + 1));
	dat->keyboardMsgQueue[dat->msgQueueCount].message = message;
	dat->keyboardMsgQueue[dat->msgQueueCount].wParam = wParam;
	dat->keyboardMsgQueue[dat->msgQueueCount].lParam = lParam;
	dat->keyboardMsgQueue[dat->msgQueueCount].keyStates = (GetKeyState(VK_SHIFT) & 0x8000 ? MOD_SHIFT : 0) | (GetKeyState(VK_CONTROL) & 0x8000 ? MOD_CONTROL : 0) | (GetKeyState(VK_MENU) & 0x8000 ? MOD_ALT : 0);
	dat->msgQueueCount++;
}

#define EM_REPLAYSAVEDKEYSTROKES  (WM_USER+0x100)
#define EM_SUBCLASSED             (WM_USER+0x101)
#define EM_UNSUBCLASSED           (WM_USER+0x102)
#define ENTERCLICKTIME   1000   //max time in ms during which a double-tap on enter will cause a send
#define EDITMSGQUEUE_PASSTHRUCLIPBOARD  //if set the typing queue won't capture ctrl-C etc because people might want to use them on the read only text
                                                  //todo: decide if this should be set or not
static LRESULT CALLBACK MessageEditSubclassProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct MsgEditSubclassData *dat;
	struct MessageWindowData *pdat;

	pdat=(struct MessageWindowData *)GetWindowLong(GetParent(hwnd),GWL_USERDATA);
	dat = (struct MsgEditSubclassData *) GetWindowLong(hwnd, GWL_USERDATA);
	switch (msg) {
	case WM_DROPFILES:
		SendMessage(GetParent(hwnd), WM_DROPFILES, (WPARAM)wParam, (LPARAM)lParam);
		break;
	case EM_SUBCLASSED:
		dat = (struct MsgEditSubclassData *) malloc(sizeof(struct MsgEditSubclassData));
		SetWindowLong(hwnd, GWL_USERDATA, (LONG) dat);
		dat->lastEnterTime = 0;
		dat->keyboardMsgQueue = NULL;
		dat->msgQueueCount = 0;
		return 0;
	case EM_SETREADONLY:
		if (wParam) {
			if (dat->keyboardMsgQueue)
				free(dat->keyboardMsgQueue);
			dat->keyboardMsgQueue = NULL;
			dat->msgQueueCount = 0;
		}
		break;
	case EM_REPLAYSAVEDKEYSTROKES:
		{
			int i;
			BYTE keyStateArray[256], originalKeyStateArray[256];
			MSG msg;

			while (PeekMessage(&msg, hwnd, 0, 0, PM_REMOVE)) {
				TranslateMessage(&msg);
				DispatchMessage(&msg);
			}
			GetKeyboardState(originalKeyStateArray);
			GetKeyboardState(keyStateArray);
			for (i = 0; i < dat->msgQueueCount; i++) {
				keyStateArray[VK_SHIFT] = dat->keyboardMsgQueue[i].keyStates & MOD_SHIFT ? 0x80 : 0;
				keyStateArray[VK_CONTROL] = dat->keyboardMsgQueue[i].keyStates & MOD_CONTROL ? 0x80 : 0;
				keyStateArray[VK_MENU] = dat->keyboardMsgQueue[i].keyStates & MOD_ALT ? 0x80 : 0;
				SetKeyboardState(keyStateArray);
				PostMessage(hwnd, dat->keyboardMsgQueue[i].message, dat->keyboardMsgQueue[i].wParam, dat->keyboardMsgQueue[i].lParam);
				while (PeekMessage(&msg, hwnd, 0, 0, PM_REMOVE)) {
					TranslateMessage(&msg);
					DispatchMessage(&msg);
				}
			}
			if (dat->keyboardMsgQueue)
				free(dat->keyboardMsgQueue);
			dat->keyboardMsgQueue = NULL;
			dat->msgQueueCount = 0;
			SetKeyboardState(originalKeyStateArray);
			return 0;
		}
	case WM_CHAR:
		if (GetWindowLong(hwnd, GWL_STYLE) & ES_READONLY)
			break;
		//for saved msg queue the keyup/keydowns generate wm_chars themselves
		if (wParam == '\n' || wParam == '\r') {
			if (((GetKeyState(VK_CONTROL) & 0x8000) != 0) ^ (0 != DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDONENTER, SRMSGDEFSET_SENDONENTER))) {
				PostMessage(GetParent(hwnd), WM_COMMAND, IDOK, 0);
				return 0;
			}
			if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SENDONDBLENTER, SRMSGDEFSET_SENDONDBLENTER)) {
				if (dat->lastEnterTime + ENTERCLICKTIME < GetTickCount())
					dat->lastEnterTime = GetTickCount();
				else {
					SendMessage(hwnd, WM_CHAR, '\b', 0);
					PostMessage(GetParent(hwnd), WM_COMMAND, IDOK, 0);
					return 0;
				}
			}
		}
		else
			dat->lastEnterTime = 0;
		if (wParam == 1 && GetKeyState(VK_CONTROL) & 0x8000) {      //ctrl-a
			SendMessage(hwnd, EM_SETSEL, 0, -1);
			return 0;
		}
		if (wParam == 23 && GetKeyState(VK_CONTROL) & 0x8000) {     // ctrl-w
			SendMessage(GetParent(hwnd), WM_CLOSE, 0, 0);
			return 0;
		}
		if (wParam == 127 && GetKeyState(VK_CONTROL) & 0x8000) {    //ctrl-backspace
			DWORD start, end;
			TCHAR *text;
			int textLen;
			SendMessage(hwnd, EM_GETSEL, (WPARAM) & end, (LPARAM) (PDWORD) NULL);
			SendMessage(hwnd, WM_KEYDOWN, VK_LEFT, 0);
			SendMessage(hwnd, EM_GETSEL, (WPARAM) & start, (LPARAM) (PDWORD) NULL);
			textLen = GetWindowTextLength(hwnd);
			text = (TCHAR *) malloc(sizeof(TCHAR) * (textLen + 1));
			GetWindowText(hwnd, text, textLen + 1);
			MoveMemory(text + start, text + end, sizeof(TCHAR) * (textLen + 1 - end));
			SetWindowText(hwnd, text);
			free(text);
			SendMessage(hwnd, EM_SETSEL, start, start);
			SendMessage(GetParent(hwnd), WM_COMMAND, MAKEWPARAM(GetDlgCtrlID(hwnd), EN_CHANGE), (LPARAM) hwnd);
			return 0;
		}
		break;
	case WM_KEYUP:
		if (GetWindowLong(hwnd, GWL_STYLE) & ES_READONLY) {
			int i;
			//mustn't allow keyups for which there is no keydown
			for (i = 0; i < dat->msgQueueCount; i++)
				if (dat->keyboardMsgQueue[i].message == WM_KEYDOWN && dat->keyboardMsgQueue[i].wParam == wParam)
					break;
			if (i == dat->msgQueueCount)
				break;
	#ifdef EDITMSGQUEUE_PASSTHRUCLIPBOARD
			if (GetKeyState(VK_CONTROL) & 0x8000) {
				if (wParam == 'X' || wParam == 'C' || wParam == 'V' || wParam == VK_INSERT)
					break;
			}
			if (GetKeyState(VK_SHIFT) & 0x8000) {
				if (wParam == VK_INSERT || wParam == VK_DELETE)
					break;
			}
	#endif
			SaveKeyboardMessage(dat, msg, wParam, lParam);
			return 0;
		}
		break;
	case WM_KEYDOWN:
		if (GetWindowLong(hwnd, GWL_STYLE) & ES_READONLY) {
	#ifdef EDITMSGQUEUE_PASSTHRUCLIPBOARD
			if (GetKeyState(VK_CONTROL) & 0x8000) {
				if (wParam == 'X' || wParam == 'C' || wParam == 'V' || wParam == VK_INSERT)
					break;
			}
			if (GetKeyState(VK_SHIFT) & 0x8000) {
				if (wParam == VK_INSERT || wParam == VK_DELETE)
					break;
			}
	#endif
			SaveKeyboardMessage(dat, msg, wParam, lParam);
			return 0;
		}
		if (wParam == VK_UP && (GetKeyState(VK_CONTROL) & 0x8000) && DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CTRLSUPPORT, SRMSGDEFSET_CTRLSUPPORT) && !DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOCLOSE, SRMSGDEFSET_AUTOCLOSE)) {
			if (pdat->cmdList) {
				if (!pdat->cmdListCurrent) {
					pdat->cmdListCurrent = tcmdlist_last(pdat->cmdList);
					SendMessage(hwnd, WM_SETREDRAW, FALSE, 0);
					SetWindowText(hwnd, pdat->cmdListCurrent->szCmd);
					SendMessage(hwnd, EM_SCROLLCARET, 0,0);
					SendMessage(hwnd, WM_SETREDRAW, TRUE, 0);
					SendMessage(hwnd, EM_SETSEL, 0, -1);
				}
				else if (pdat->cmdListCurrent->prev) {
					pdat->cmdListCurrent = pdat->cmdListCurrent->prev;
					SendMessage(hwnd, WM_SETREDRAW, FALSE, 0);
					SetWindowText(hwnd, pdat->cmdListCurrent->szCmd);
					SendMessage(hwnd, EM_SCROLLCARET, 0,0);
					SendMessage(hwnd, WM_SETREDRAW, TRUE, 0);
					SendMessage(hwnd, EM_SETSEL, 0, -1);
				}
			}
			EnableWindow(GetDlgItem(GetParent(hwnd), IDOK), GetWindowTextLength(GetDlgItem(GetParent(hwnd), IDC_MESSAGE)) != 0);
			UpdateReadChars(GetParent(hwnd), pdat->hwndStatus);
		}
		else if (wParam == VK_DOWN && (GetKeyState(VK_CONTROL) & 0x8000) && DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CTRLSUPPORT, SRMSGDEFSET_CTRLSUPPORT) && !DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOCLOSE, SRMSGDEFSET_AUTOCLOSE)) {
			if (pdat->cmdList) {
				if (!pdat->cmdListCurrent)
					pdat->cmdListCurrent = tcmdlist_last(pdat->cmdList);
				if (pdat->cmdListCurrent->next) {
					pdat->cmdListCurrent = pdat->cmdListCurrent->next;
					SendMessage(hwnd, WM_SETREDRAW, FALSE, 0);
					SetWindowText(hwnd, pdat->cmdListCurrent->szCmd);
					SendMessage(hwnd, EM_SCROLLCARET, 0,0);
					SendMessage(hwnd, WM_SETREDRAW, TRUE, 0);
					SendMessage(hwnd, EM_SETSEL, 0, -1);
				}
				else {
					pdat->cmdListCurrent = 0;
					SetWindowTextA(hwnd, "");
				}
			}
			EnableWindow(GetDlgItem(GetParent(hwnd), IDOK), GetWindowTextLength(GetDlgItem(GetParent(hwnd), IDC_MESSAGE)) != 0);
			UpdateReadChars(GetParent(hwnd), pdat->hwndStatus);
		}
		if (wParam == VK_RETURN)
			break;
		//fall through
	case WM_LBUTTONDOWN:
	case WM_RBUTTONDOWN:
	case WM_MBUTTONDOWN:
	case WM_MOUSEWHEEL:
	case WM_KILLFOCUS:
		dat->lastEnterTime = 0;
		break;
	case WM_SYSCHAR:
		dat->lastEnterTime = 0;
		if ((wParam == 's' || wParam == 'S') && GetKeyState(VK_MENU) & 0x8000) {
			if (GetWindowLong(hwnd, GWL_STYLE) & ES_READONLY)
				SaveKeyboardMessage(dat, msg, wParam, lParam);
			else
				PostMessage(GetParent(hwnd), WM_COMMAND, IDOK, 0);
			return 0;
		}
		break;
	case EM_UNSUBCLASSED:
		if (dat->keyboardMsgQueue)
			free(dat->keyboardMsgQueue);
		free(dat);
		return 0;
	}
	return CallWindowProc(OldMessageEditProc, hwnd, msg, wParam, lParam);
}

static LRESULT CALLBACK SplitterSubclassProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg) {
		case WM_NCHITTEST:
		  return HTCLIENT;
		case WM_SETCURSOR:
		{
			RECT rc;
				GetClientRect(hwnd, &rc);
				SetCursor(rc.right > rc.bottom ? hCurSplitNS : hCurSplitWE);
				return TRUE;
		}
		case WM_LBUTTONDOWN:
			SetCapture(hwnd);
			return 0;
		case WM_MOUSEMOVE:
			if (GetCapture() == hwnd) {
				RECT rc;
				GetClientRect(hwnd, &rc);
				SendMessage(GetParent(hwnd), DM_SPLITTERMOVED, rc.right > rc.bottom ? (short) HIWORD(GetMessagePos()) + rc.bottom / 2 : (short) LOWORD(GetMessagePos()) + rc.right / 2, (LPARAM) hwnd);
			}
			return 0;
		case WM_LBUTTONUP:
			ReleaseCapture();
			return 0;
	}
	return CallWindowProc(OldSplitterProc, hwnd, msg, wParam, lParam);
}

static int MessageDialogResize(HWND hwndDlg, LPARAM lParam, UTILRESIZECONTROL * urc)
{
    struct MessageWindowData *dat = (struct MessageWindowData *) lParam;

    if (!(g_dat->flags&SMF_SHOWINFO) && !(g_dat->flags&SMF_SHOWBTNS)) {
        int i;
        for (i = 0; i < SIZEOF(buttonLineControls); i++)
            if (buttonLineControls[i] == urc->wId)
                OffsetRect(&urc->rcItem, 0, -dat->lineHeight);
    }
    switch (urc->wId) {
        case IDC_NAME:
        {
            int len;
            HWND h;

            h = GetDlgItem(hwndDlg, IDC_NAME);
            len = GetWindowTextLength(h);
            if (len > 0) {
                HDC hdc;
                SIZE textSize;
                TCHAR buf[256];
					 HFONT hFont;

                GetWindowText(h, buf, SIZEOF(buf));

                hdc = GetDC(h);
                hFont = SelectObject(hdc, (HFONT) SendMessage(GetDlgItem(hwndDlg, IDOK), WM_GETFONT, 0, 0));
                GetTextExtentPoint32(hdc, buf, lstrlen(buf), &textSize);
                urc->rcItem.right = urc->rcItem.left + textSize.cx + 10;
                if ((g_dat->flags&SMF_SHOWBTNS) && urc->rcItem.right > urc->dlgNewSize.cx - dat->nLabelRight)
                    urc->rcItem.right = urc->dlgNewSize.cx - dat->nLabelRight;
					 SelectObject(hdc, hFont);
                ReleaseDC(h, hdc);
            }
        }
        case IDC_PROTOCOL:
            return RD_ANCHORX_LEFT | RD_ANCHORY_TOP;
        case IDC_ADD:
        case IDC_USERMENU:
        case IDC_DETAILS:
        case IDC_HISTORY:
            return RD_ANCHORX_RIGHT | RD_ANCHORY_TOP;
        case IDC_LOG:
            if (!(g_dat->flags&SMF_SHOWINFO) && !(g_dat->flags&SMF_SHOWBTNS))
                urc->rcItem.top -= dat->lineHeight;
            urc->rcItem.bottom -= dat->splitterPos - dat->originalSplitterPos;
            return RD_ANCHORX_WIDTH | RD_ANCHORY_HEIGHT;
        case IDC_SPLITTER:
            urc->rcItem.top -= dat->splitterPos - dat->originalSplitterPos;
            urc->rcItem.bottom -= dat->splitterPos - dat->originalSplitterPos;
            return RD_ANCHORX_WIDTH | RD_ANCHORY_BOTTOM;
        case IDC_MESSAGE:
        {
            if (!(g_dat->flags&SMF_SENDBTN))
                urc->rcItem.right = urc->dlgNewSize.cx - urc->rcItem.left;
			if ((g_dat->flags&SMF_AVATAR)&&dat->avatarPic) {
				urc->rcItem.left = dat->avatarWidth+4;
			}
            urc->rcItem.top -= dat->splitterPos - dat->originalSplitterPos;
            if (!(g_dat->flags&SMF_SENDBTN))
                return RD_ANCHORX_CUSTOM | RD_ANCHORY_BOTTOM;
            return RD_ANCHORX_WIDTH | RD_ANCHORY_BOTTOM;
        }
        case IDCANCEL:
        case IDOK:
            urc->rcItem.top -= dat->splitterPos - dat->originalSplitterPos;
            return RD_ANCHORX_RIGHT | RD_ANCHORY_BOTTOM;
		case IDC_AVATAR:
            urc->rcItem.top=urc->rcItem.bottom-(dat->avatarHeight + 2);
            urc->rcItem.right=urc->rcItem.left+(dat->avatarWidth + 2);
            return RD_ANCHORX_LEFT|RD_ANCHORY_BOTTOM;
    }
    return RD_ANCHORX_LEFT | RD_ANCHORY_TOP;
}

static void UpdateReadChars(HWND hwndDlg, HWND hwndStatus)
{
	if (hwndStatus && SendMessage(hwndStatus, SB_GETPARTS, 0, 0) == 3) {
		TCHAR buf[128];
		int len = GetWindowTextLength(GetDlgItem(hwndDlg, IDC_MESSAGE));

		mir_sntprintf(buf, SIZEOF(buf), _T("%d"), len);
		SendMessage(hwndStatus, SB_SETTEXT, 1, (LPARAM) buf);
	}
}

void ShowAvatar(HWND hwndDlg, struct MessageWindowData *dat) {
	DBVARIANT dbv;

	if (dat->avatarPic) {
		DeleteObject(dat->avatarPic);
        dat->avatarPic=0;
	}
	if (!DBGetContactSetting(dat->hContact, SRMMMOD, SRMSGSET_AVATAR, &dbv)) {
		if(dbv.pszVal) {
			HANDLE hFile;

			hFile = CreateFileA(dbv.pszVal, GENERIC_READ, FILE_SHARE_READ, NULL, OPEN_EXISTING, FILE_ATTRIBUTE_NORMAL, NULL);
			if (hFile!=INVALID_HANDLE_VALUE) {
				dat->avatarPic=(HBITMAP)CallService(MS_UTILS_LOADBITMAP,0,(LPARAM)dbv.pszVal);
				CloseHandle(hFile);
			}
		}
		DBFreeVariant(&dbv);
	}
	SendMessage(hwndDlg, DM_UPDATESIZEBAR, 0, 0);
	SendMessage(hwndDlg, DM_AVATARSIZECHANGE, 0, 0);
}

static void NotifyTyping(struct MessageWindowData *dat, int mode)
{
	DWORD protoStatus;
	DWORD protoCaps;
	DWORD typeCaps;

	if (!dat->hContact)
		return;
	// Don't send to protocols who don't support typing
	// Don't send to users who are unchecked in the typing notification options
	// Don't send to protocols that are offline
	// Don't send to users who are not visible and
	// Don't send to users who are not on the visible list when you are in invisible mode.
	if (!DBGetContactSettingByte(dat->hContact, SRMMMOD, SRMSGSET_TYPING, DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_TYPINGNEW, SRMSGDEFSET_TYPINGNEW)))
		return;
	if (!dat->szProto)
		return;
	protoStatus = CallProtoService(dat->szProto, PS_GETSTATUS, 0, 0);
	protoCaps = CallProtoService(dat->szProto, PS_GETCAPS, PFLAGNUM_1, 0);
	typeCaps = CallProtoService(dat->szProto, PS_GETCAPS, PFLAGNUM_4, 0);

	if (!(typeCaps & PF4_SUPPORTTYPING))
		return;
	if (protoStatus < ID_STATUS_ONLINE)
		return;
	if (protoCaps & PF1_VISLIST && DBGetContactSettingWord(dat->hContact, dat->szProto, "ApparentMode", 0) == ID_STATUS_OFFLINE)
		return;
	if (protoCaps & PF1_INVISLIST && protoStatus == ID_STATUS_INVISIBLE && DBGetContactSettingWord(dat->hContact, dat->szProto, "ApparentMode", 0) != ID_STATUS_ONLINE)
		return;
	if (DBGetContactSettingByte(dat->hContact, "CList", "NotOnList", 0)
		&& !DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_TYPINGUNKNOWN, SRMSGDEFSET_TYPINGUNKNOWN))
		return;
	// End user check
	dat->nTypeMode = mode;
	CallService(MS_PROTO_SELFISTYPING, (WPARAM) dat->hContact, dat->nTypeMode);
}

BOOL CALLBACK DlgProcMessage(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct MessageWindowData *dat;
	TCHAR statusMsg[256] = { 0 };

	dat = (struct MessageWindowData *) GetWindowLong(hwndDlg, GWL_USERDATA);
	switch (msg) {
	case WM_INITDIALOG:
		{
			struct NewMessageWindowLParam *newData = (struct NewMessageWindowLParam *) lParam;
			TranslateDialogDefault(hwndDlg);
			dat = (struct MessageWindowData *) calloc(sizeof(struct MessageWindowData),1);
			SetWindowLong(hwndDlg, GWL_USERDATA, (LONG) dat);
			{
				dat->hContact = newData->hContact;
				NotifyLocalWinEvent(dat->hContact, hwndDlg, MSG_WINDOW_EVT_OPENING);
				if (newData->szInitialText) {
					int len;
#if defined(_UNICODE)
                    if(newData->isWchar)
    					SetDlgItemText(hwndDlg, IDC_MESSAGE, (TCHAR *)newData->szInitialText);
    				else
    					SetDlgItemTextA(hwndDlg, IDC_MESSAGE, newData->szInitialText);
#else
					SetDlgItemTextA(hwndDlg, IDC_MESSAGE, newData->szInitialText);
#endif
					len = GetWindowTextLength(GetDlgItem(hwndDlg, IDC_MESSAGE));
					PostMessage(GetDlgItem(hwndDlg, IDC_MESSAGE), EM_SETSEL, len, len);
				}
			}
			dat->szProto = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) dat->hContact, 0);
			RichUtil_SubClass(GetDlgItem(hwndDlg, IDC_LOG));
			{ // avatar stuff
				dat->avatarPic = 0;
				dat->avatarWidth = 0;
				dat->avatarHeight = 0;
				dat->limitAvatarH = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_LIMITAVHEIGHT, SRMSGDEFSET_LIMITAVHEIGHT)?DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_AVHEIGHT, SRMSGDEFSET_AVHEIGHT):0;
			}
			if (dat->hContact && dat->szProto != NULL)
				dat->wStatus = DBGetContactSettingWord(dat->hContact, dat->szProto, "Status", ID_STATUS_OFFLINE);
			else
				dat->wStatus = ID_STATUS_OFFLINE;
			dat->wOldStatus = dat->wStatus;
			dat->hSendId = NULL;
			dat->hBkgBrush = NULL;
			dat->hDbEventFirst = NULL;
			dat->sendBuffer = NULL;
			dat->splitterPos = (int) DBGetContactSettingDword(DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SAVEPERCONTACT, SRMSGDEFSET_SAVEPERCONTACT)?dat->hContact:NULL, SRMMMOD, "splitterPos", (DWORD) - 1);
			dat->windowWasCascaded = 0;
			dat->nFlash = 0;
			dat->nTypeSecs = 0;
			dat->nLastTyping = 0;
			dat->showTyping = 0;
			dat->cmdList = 0;
			dat->cmdListCurrent = 0;
			dat->nTypeMode = PROTOTYPE_SELFTYPING_OFF;
			SetTimer(hwndDlg, TIMERID_TYPE, 1000, NULL);
			dat->lastMessage = 0;
			dat->lastEventType = -1;
			dat->nFlashMax = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_FLASHCOUNT, SRMSGDEFSET_FLASHCOUNT);
			{
				RECT rc, rc2;
				GetWindowRect(GetDlgItem(hwndDlg, IDC_USERMENU), &rc);
				GetWindowRect(hwndDlg, &rc2);
				dat->nLabelRight = rc2.right - rc.left;
			}
			{
				RECT rc;
				POINT pt;
				GetWindowRect(GetDlgItem(hwndDlg, IDC_SPLITTER), &rc);
				pt.y = (rc.top + rc.bottom) / 2;
				pt.x = 0;
				ScreenToClient(hwndDlg, &pt);
				dat->originalSplitterPos = pt.y;
				if (dat->splitterPos == -1)
					dat->splitterPos = dat->originalSplitterPos;// + 60;
				GetWindowRect(GetDlgItem(hwndDlg, IDC_ADD), &rc);
				dat->lineHeight = rc.bottom - rc.top + 3;
			}
			WindowList_Add(g_dat->hMessageWindowList, hwndDlg, dat->hContact);
			GetWindowRect(GetDlgItem(hwndDlg, IDC_MESSAGE), &dat->minEditInit);
			SendMessage(hwndDlg, DM_UPDATESIZEBAR, 0, 0);
			dat->hwndStatus = NULL;
			SendDlgItemMessage(hwndDlg, IDC_ADD, BM_SETIMAGE, IMAGE_ICON, (LPARAM) g_dat->hIcons[SMF_ICON_ADD]);
			SendDlgItemMessage(hwndDlg, IDC_DETAILS, BM_SETIMAGE, IMAGE_ICON, (LPARAM) g_dat->hIcons[SMF_ICON_USERDETAIL]);
			SendDlgItemMessage(hwndDlg, IDC_HISTORY, BM_SETIMAGE, IMAGE_ICON, (LPARAM) g_dat->hIcons[SMF_ICON_HISTORY]);
			SendDlgItemMessage(hwndDlg, IDC_USERMENU, BM_SETIMAGE, IMAGE_ICON, (LPARAM) g_dat->hIcons[SMF_ICON_ARROW]);
			// Make them flat buttons
			{
				int i;

				SendMessage(GetDlgItem(hwndDlg, IDC_NAME), BUTTONSETASFLATBTN, 0, 0);
				for (i = 0; i < SIZEOF(buttonLineControls); i++)
					SendMessage(GetDlgItem(hwndDlg, buttonLineControls[i]), BUTTONSETASFLATBTN, 0, 0);
			}
			SendMessage(GetDlgItem(hwndDlg, IDC_ADD), BUTTONADDTOOLTIP, (WPARAM) Translate("Add Contact Permanently to List"), 0);
			SendMessage(GetDlgItem(hwndDlg, IDC_USERMENU), BUTTONADDTOOLTIP, (WPARAM) Translate("User Menu"), 0);
			SendMessage(GetDlgItem(hwndDlg, IDC_DETAILS), BUTTONADDTOOLTIP, (WPARAM) Translate("View User's Details"), 0);
			SendMessage(GetDlgItem(hwndDlg, IDC_HISTORY), BUTTONADDTOOLTIP, (WPARAM) Translate("View User's History"), 0);

			EnableWindow(GetDlgItem(hwndDlg, IDC_PROTOCOL), FALSE);
			EnableWindow(GetDlgItem(hwndDlg, IDC_AVATAR), FALSE);
			SendDlgItemMessage(hwndDlg, IDC_LOG, EM_SETOLECALLBACK, 0, (LPARAM) & reOleCallback);
			SendDlgItemMessage(hwndDlg, IDC_LOG, EM_SETEVENTMASK, 0, ENM_MOUSEEVENTS | ENM_LINK);
			/* duh, how come we didnt use this from the start? */
			SendDlgItemMessage(hwndDlg, IDC_LOG, EM_AUTOURLDETECT, (WPARAM) TRUE, 0);
			if (dat->hContact) {
				if (dat->szProto) {
					int nMax;
					nMax = CallProtoService(dat->szProto, PS_GETCAPS, PFLAG_MAXLENOFMESSAGE, (LPARAM) dat->hContact);
					if (nMax)
						SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_LIMITTEXT, (WPARAM) nMax, 0);
					/* get around a lame bug in the Windows template resource code where richedits are limited to 0x7FFF */
					SendDlgItemMessage(hwndDlg, IDC_LOG, EM_LIMITTEXT, (WPARAM) sizeof(TCHAR) * 0x7FFFFFFF, 0);
				}
			}

			OldMessageEditProc = (WNDPROC) SetWindowLong(GetDlgItem(hwndDlg, IDC_MESSAGE), GWL_WNDPROC, (LONG) MessageEditSubclassProc);
			SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_SUBCLASSED, 0, 0);
			OldSplitterProc = (WNDPROC) SetWindowLong(GetDlgItem(hwndDlg, IDC_SPLITTER), GWL_WNDPROC, (LONG) SplitterSubclassProc);
			if (dat->hContact) {
				int historyMode = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_LOADHISTORY, SRMSGDEFSET_LOADHISTORY);
				// This finds the first message to display, it works like shit
				dat->hDbEventFirst = (HANDLE) CallService(MS_DB_EVENT_FINDFIRSTUNREAD, (WPARAM) dat->hContact, 0);
				switch (historyMode) {
				case LOADHISTORY_COUNT:
					{
						int i;
						HANDLE hPrevEvent;
						DBEVENTINFO dbei = { 0 };
						dbei.cbSize = sizeof(dbei);
						for (i = DBGetContactSettingWord(NULL, SRMMMOD, SRMSGSET_LOADCOUNT, SRMSGDEFSET_LOADCOUNT); i > 0; i--) {
							if (dat->hDbEventFirst == NULL)
								hPrevEvent = (HANDLE) CallService(MS_DB_EVENT_FINDLAST, (WPARAM) dat->hContact, 0);
							else
								hPrevEvent = (HANDLE) CallService(MS_DB_EVENT_FINDPREV, (WPARAM) dat->hDbEventFirst, 0);
							if (hPrevEvent == NULL)
								break;
							dbei.cbBlob = 0;
							dat->hDbEventFirst = hPrevEvent;
							CallService(MS_DB_EVENT_GET, (WPARAM) dat->hDbEventFirst, (LPARAM) & dbei);
							if (!DbEventIsShown(&dbei, dat))
								i++;
						}
						break;
					}
				case LOADHISTORY_TIME:
					{
						HANDLE hPrevEvent;
						DBEVENTINFO dbei = { 0 };
						DWORD firstTime;

						dbei.cbSize = sizeof(dbei);
						if (dat->hDbEventFirst == NULL)
							dbei.timestamp = (DWORD)time(NULL);
						else
							CallService(MS_DB_EVENT_GET, (WPARAM) dat->hDbEventFirst, (LPARAM) & dbei);
						firstTime = dbei.timestamp - 60 * DBGetContactSettingWord(NULL, SRMMMOD, SRMSGSET_LOADTIME, SRMSGDEFSET_LOADTIME);
						for (;;) {
							if (dat->hDbEventFirst == NULL)
								hPrevEvent = (HANDLE) CallService(MS_DB_EVENT_FINDLAST, (WPARAM) dat->hContact, 0);
							else
								hPrevEvent = (HANDLE) CallService(MS_DB_EVENT_FINDPREV, (WPARAM) dat->hDbEventFirst, 0);
							if (hPrevEvent == NULL)
								break;
							dbei.cbBlob = 0;
							CallService(MS_DB_EVENT_GET, (WPARAM) hPrevEvent, (LPARAM) & dbei);
							if (dbei.timestamp < firstTime)
								break;
							dat->hDbEventFirst = hPrevEvent;
						}
						break;
					}
				}
			}
			SendMessage(hwndDlg, DM_OPTIONSAPPLIED, 1, 0);
			{
				int savePerContact = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SAVEPERCONTACT, SRMSGDEFSET_SAVEPERCONTACT);
				if (Utils_RestoreWindowPosition(hwndDlg, savePerContact ? dat->hContact : NULL, SRMMMOD, "")) {
					if (savePerContact) {
						if (Utils_RestoreWindowPositionNoMove(hwndDlg, NULL, SRMMMOD, ""))
							SetWindowPos(hwndDlg, 0, 0, 0, 450, 300, SWP_NOZORDER | SWP_NOMOVE);
					}
					else
						SetWindowPos(hwndDlg, 0, 0, 0, 450, 300, SWP_NOZORDER | SWP_NOMOVE);
				}
				if (!savePerContact && DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_CASCADE, SRMSGDEFSET_CASCADE))
					WindowList_Broadcast(g_dat->hMessageWindowList, DM_CASCADENEWWINDOW, (WPARAM) hwndDlg, (LPARAM) & dat->windowWasCascaded);
			}
			{
				DBEVENTINFO dbei = { 0 };
				HANDLE hdbEvent;

				dbei.cbSize = sizeof(dbei);
				hdbEvent = (HANDLE) CallService(MS_DB_EVENT_FINDLAST, (WPARAM) dat->hContact, 0);
				if (hdbEvent) {
					do {
						ZeroMemory(&dbei, sizeof(dbei));
						dbei.cbSize = sizeof(dbei);
						CallService(MS_DB_EVENT_GET, (WPARAM) hdbEvent, (LPARAM) & dbei);
						if (dbei.eventType == EVENTTYPE_MESSAGE && !(dbei.flags & DBEF_SENT)) {
							dat->lastMessage = dbei.timestamp;
							SendMessage(hwndDlg, DM_UPDATELASTMESSAGE, 0, 0);
							break;
						}
					}
					while (hdbEvent = (HANDLE) CallService(MS_DB_EVENT_FINDPREV, (WPARAM) hdbEvent, 0));
				}

			}
			SendMessage(hwndDlg, DM_GETAVATAR, 0, 0);
			ShowWindow(hwndDlg, SW_SHOWNORMAL);
			NotifyLocalWinEvent(dat->hContact, hwndDlg, MSG_WINDOW_EVT_OPEN);
			ShowWindow(GetDlgItem( hwndDlg, IDC_PROTOCOL ), FALSE);
			ShowWindow(GetDlgItem( hwndDlg, IDC_ADD ), FALSE);
			ShowWindow(GetDlgItem( hwndDlg, IDC_NAME ), FALSE);
			ShowWindow(GetDlgItem( hwndDlg, IDC_DETAILS ), FALSE);
			ShowWindow(GetDlgItem( hwndDlg, IDC_USERMENU ), FALSE);
			ShowWindow(GetDlgItem( hwndDlg, IDC_HISTORY ), FALSE);

			{
				PARAFORMAT2 pf2;
				pf2.cbSize = sizeof(PARAFORMAT2);
				pf2.dwMask = PFM_LINESPACING;
				pf2.dyLineSpacing = 25;
				pf2.bLineSpacingRule = 5;
				SendMessage( GetDlgItem(hwndDlg, IDC_LOG), EM_SETPARAFORMAT, 0, &pf2 );
			}
            return TRUE;
		}
	case WM_CONTEXTMENU:
	{
		if (dat->hwndStatus && dat->hwndStatus == (HWND) wParam) {
			POINT pt, pt2;
			HMENU hMenu;
			RECT rc;

			GetCursorPos(&pt);
			pt2.x = pt.x; pt2.y = pt.y;
			ScreenToClient(dat->hwndStatus, &pt);

			// no popup menu for status icons - this is handled via NM_RCLICK notification and the plugins that added the icons
			SendMessage(dat->hwndStatus, SB_GETRECT, SendMessage(dat->hwndStatus, SB_GETPARTS, 0, 0) - 1, (LPARAM)&rc);
			if (pt.x >= rc.left) break;

			hMenu = (HMENU) CallService(MS_CLIST_MENUBUILDCONTACT, (WPARAM) dat->hContact, 0);

			TrackPopupMenu(hMenu, 0, pt2.x, pt2.y, 0, hwndDlg, NULL);
			DestroyMenu(hMenu);
		}
		break;
	}
	// Mod from tabsrmm
	case WM_DROPFILES:
	{
		if (dat->szProto==NULL) break;
		if (!(CallProtoService(dat->szProto, PS_GETCAPS, PFLAGNUM_1,0)&PF1_FILESEND)) break;
		if (dat->wStatus==ID_STATUS_OFFLINE) break;
		if (dat->hContact!=NULL) {
			TCHAR szFilename[MAX_PATH];
			HDROP hDrop = (HDROP)wParam;
			int fileCount = DragQueryFile(hDrop,-1,NULL,0), totalCount = 0, i;
			char** ppFiles = NULL;
			for ( i=0; i < fileCount; i++ ) {
				DragQueryFile(hDrop, i, szFilename, SIZEOF(szFilename));
				AddToFileList(&ppFiles, &totalCount, szFilename);
			}
			CallServiceSync(MS_FILE_SENDSPECIFICFILES, (WPARAM)dat->hContact, (LPARAM)ppFiles);
			for(i=0;ppFiles[i];i++) free(ppFiles[i]);
			free(ppFiles);
		}
		break;
	}
	case HM_AVATARACK:
	{
		ACKDATA *pAck = (ACKDATA *)lParam;
		PROTO_AVATAR_INFORMATION *pai = (PROTO_AVATAR_INFORMATION *)pAck->hProcess;
		HWND hwnd = 0;

		if (pAck->hContact!=dat->hContact)
			return 0;
		if (pAck->type != ACKTYPE_AVATAR)
			return 0;
		if (pAck->result == ACKRESULT_STATUS) {
			SendMessage(hwndDlg, DM_GETAVATAR, 0, 0);
			break;
		}
		if (pai==NULL)
			return 0;
		if (pAck->result == ACKRESULT_SUCCESS) {
			if (pai->filename&&strlen(pai->filename)&&VALID_AVATAR(pai->format)) {
				DBWriteContactSettingString(dat->hContact, SRMMMOD, SRMSGSET_AVATAR, pai->filename);
				ShowAvatar(hwndDlg, dat);
			}
		}
		else if (pAck->result == ACKRESULT_FAILED) {
			DBWriteContactSettingString(dat->hContact, SRMMMOD, SRMSGSET_AVATAR, "");
			ShowAvatar(hwndDlg, dat);
		}
		break;
	}
	case DM_AVATARCALCSIZE:
	{
		BITMAP bminfo;

		if (dat->avatarPic==0||!(g_dat->flags&SMF_AVATAR)) {
			dat->avatarWidth=50;
			dat->avatarHeight=50;
			ShowWindow(GetDlgItem(hwndDlg, IDC_AVATAR), SW_HIDE);
			return 0;
		}
		GetObject(dat->avatarPic, sizeof(bminfo), &bminfo);
		dat->avatarWidth=bminfo.bmWidth+2;
		dat->avatarHeight=bminfo.bmHeight+2;
		if (dat->limitAvatarH&&dat->avatarHeight>dat->limitAvatarH) {
			double aspect = 0;

			aspect = (double)dat->limitAvatarH / (double)bminfo.bmHeight;
			dat->avatarWidth = (int)(bminfo.bmWidth * aspect + 2);
			dat->avatarHeight = dat->limitAvatarH + 2;
		}
		ShowWindow(GetDlgItem(hwndDlg, IDC_AVATAR), SW_SHOW);
		break;
	}
	case DM_UPDATESIZEBAR:
	{
		dat->minEditBoxSize.cx = dat->minEditInit.right - dat->minEditInit.left;
		dat->minEditBoxSize.cy = dat->minEditInit.bottom - dat->minEditInit.top;
		if(g_dat->flags&SMF_AVATAR) {
			SendMessage(hwndDlg, DM_AVATARCALCSIZE, 0, 0);
			if(dat->avatarPic && dat->minEditBoxSize.cy<=dat->avatarHeight)
				dat->minEditBoxSize.cy = dat->avatarHeight;
		}
		break;
	}
	case DM_AVATARSIZECHANGE:
	{
		RECT rc;
		GetWindowRect(GetDlgItem(hwndDlg, IDC_MESSAGE), &rc);
		if (rc.bottom-rc.top<dat->minEditBoxSize.cy) {
			SendMessage(hwndDlg, DM_SPLITTERMOVED, rc.top-(rc.bottom-rc.top-dat->minEditBoxSize.cy-4), (LPARAM) GetDlgItem(hwndDlg, IDC_SPLITTER));
		}
		SendMessage(hwndDlg, WM_SIZE, 0, 0);
		SendMessage(hwndDlg, DM_SCROLLLOGTOBOTTOM, 0, 0);
		break;
	}
	case DM_GETAVATAR:
	{
		PROTO_AVATAR_INFORMATION pai;
		int caps = 0, result;

		SetWindowLong(hwndDlg, DWL_MSGRESULT, 0);
        if (!(g_dat->flags&SMF_AVATAR)||!(CallProtoService(dat->szProto, PS_GETCAPS, PFLAGNUM_4, 0)&PF4_AVATARS)) {
			SendMessage(hwndDlg, DM_UPDATESIZEBAR, 0, 0);
			SendMessage(hwndDlg, DM_AVATARSIZECHANGE, 0, 0);
			SetWindowLong(hwndDlg, DWL_MSGRESULT, 1);
			return 0;
		}
		if(DBGetContactSettingWord(dat->hContact, dat->szProto, "Status", ID_STATUS_OFFLINE) == ID_STATUS_OFFLINE) {
			ShowAvatar(hwndDlg, dat);
			SetWindowLong(hwndDlg, DWL_MSGRESULT, 1);
			return 0;
		}
		ZeroMemory((void *)&pai, sizeof(pai));
		pai.cbSize = sizeof(pai);
		pai.hContact = dat->hContact;
		pai.format = PA_FORMAT_UNKNOWN;
		strcpy(pai.filename, "");
		result = CallProtoService(dat->szProto, PS_GETAVATARINFO, GAIF_FORCE, (LPARAM)&pai);
		if (result==GAIR_SUCCESS) {
			if (VALID_AVATAR(pai.format))
				DBWriteContactSettingString(dat->hContact, SRMMMOD, SRMSGSET_AVATAR, pai.filename);
			else DBWriteContactSettingString(dat->hContact, SRMMMOD, SRMSGSET_AVATAR, "");
			ShowAvatar(hwndDlg, dat);
		} else if (result == GAIR_NOAVATAR) {
			DBWriteContactSettingString(dat->hContact, SRMMMOD, SRMSGSET_AVATAR, "");
			ShowAvatar(hwndDlg, dat);
		}
		SetWindowLong(hwndDlg, DWL_MSGRESULT, 1);
		break;
	}
	case DM_TYPING:
		{
			dat->nTypeSecs = (int) lParam > 0 ? (int) lParam : 0;
			break;
		}
	case DM_UPDATEWINICON:
		{
			if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_STATUSICON, SRMSGDEFSET_STATUSICON)) {
				WORD wStatus;

				if (dat->szProto) {
					wStatus = DBGetContactSettingWord(dat->hContact, dat->szProto, "Status", ID_STATUS_OFFLINE);
					SendMessage(hwndDlg, WM_SETICON, (WPARAM) ICON_BIG, (LPARAM) LoadSkinnedProtoIcon(dat->szProto, wStatus));
					break;
				}
			}
			SendMessage(hwndDlg, WM_SETICON, (WPARAM) ICON_BIG, (LPARAM) LoadSkinnedIcon(SKINICON_EVENT_MESSAGE));
			break;
		}
        case DM_USERNAMETOCLIP:
		{
			CONTACTINFO ci;
			char buf[128];
			HGLOBAL hData;

			buf[0] = 0;
			if(dat->hContact) {
				ZeroMemory(&ci, sizeof(ci));
				ci.cbSize = sizeof(ci);
				ci.hContact = dat->hContact;
				ci.szProto = dat->szProto;
				ci.dwFlag = CNF_UNIQUEID;
				if (!CallService(MS_CONTACT_GETCONTACTINFO, 0, (LPARAM) & ci)) {
					switch (ci.type) {
						case CNFT_ASCIIZ:
							mir_snprintf(buf, SIZEOF(buf), "%s", ci.pszVal);
							mir_free(ci.pszVal);
							break;
						case CNFT_DWORD:
							mir_snprintf(buf, SIZEOF(buf), "%u", ci.dVal);
							break;
					}
				}
				if (!OpenClipboard(hwndDlg) || !lstrlenA(buf)) break;
				EmptyClipboard();
				hData = GlobalAlloc(GMEM_MOVEABLE, lstrlenA(buf) + 1);
				lstrcpyA(GlobalLock(hData), buf);
				GlobalUnlock(hData);
				SetClipboardData(CF_TEXT, hData);
				CloseClipboard();
			}
			break;
		}
	case DM_UPDATELASTMESSAGE:
		{
			RECT rc;
			HFONT hfont, oldFont;
			HDC hdc;
			HBRUSH hbrush = CreateSolidBrush( RGB(228, 240, 254) );
			if (!dat->hwndStatus || dat->nTypeSecs)
				break;
			if (dat->lastMessage) {
				DBTIMETOSTRINGT dbtts;
				TCHAR date[64], time[64], fmt[128];

				dbtts.szFormat = _T("d");
				dbtts.cbDest = SIZEOF(date);
				dbtts.szDest = date;
				CallService(MS_DB_TIME_TIMESTAMPTOSTRINGT, dat->lastMessage, (LPARAM) & dbtts);
				dbtts.szFormat = _T("t");
				dbtts.cbDest = SIZEOF(time);
				dbtts.szDest = time;
				CallService(MS_DB_TIME_TIMESTAMPTOSTRINGT, dat->lastMessage, (LPARAM) & dbtts);
				mir_sntprintf(fmt, SIZEOF(fmt), TranslateT("Last message received on %s at %s."), date, time);
				SendMessage(dat->hwndStatus, SB_SETTEXT, 0, (LPARAM) fmt);
				SendMessage(dat->hwndStatus, SB_SETICON, 0, (LPARAM) NULL);
				lstrcpy(statusMsg, fmt);
			}
			else {
				SendMessage(dat->hwndStatus, SB_SETTEXT, 0, (LPARAM) _T(""));
				SendMessage(dat->hwndStatus, SB_SETICON, 0, (LPARAM) NULL);
				statusMsg[0] = '\0';
			}
			GetClientRect(hwndDlg, &rc);
			rc.left = 8;
			rc.top = rc.bottom - 22;
			rc.right = rc.right - 16;
			rc.bottom = rc.bottom - 6;
			hfont = CreateFont(-11, 0, 0, 0, 400, 0, 0, 0, 134, 0, 0, 0, 0, _T("MS Shell Dlg 2")); 
			hdc = GetDC(hwndDlg);
			oldFont = SelectObject(hdc, hfont);
			SetBkColor(hdc, RGB( 228, 240, 254 ) );
			SetTextColor(hdc, RGB( 128, 128, 128 ) );
			FillRect(hdc, &rc, hbrush);
			DrawText( hdc, statusMsg, lstrlen(statusMsg), &rc, DT_LEFT );
			SelectObject(hdc,oldFont);
			DeleteObject(hfont);
			break;
		}
	case DM_OPTIONSAPPLIED:
		SetDialogToType(hwndDlg);
		if (dat->hBkgBrush)
			DeleteObject(dat->hBkgBrush);
		{
			COLORREF colour = DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_BKGCOLOUR, SRMSGDEFSET_BKGCOLOUR);
			dat->hBkgBrush = CreateSolidBrush(colour);
			SendDlgItemMessage(hwndDlg, IDC_LOG, EM_SETBKGNDCOLOR, 0, colour);
		}
		{ // avatar stuff
			dat->avatarPic = 0;
			dat->limitAvatarH = 0;
			if (CallProtoService(dat->szProto, PS_GETCAPS, PFLAGNUM_4, 0)&PF4_AVATARS) {
				dat->limitAvatarH = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_LIMITAVHEIGHT, SRMSGDEFSET_LIMITAVHEIGHT)?DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_AVHEIGHT, SRMSGDEFSET_AVHEIGHT):0;
			}
			if (!wParam) SendMessage(hwndDlg, DM_GETAVATAR, 0, 0);
		}
		InvalidateRect(GetDlgItem(hwndDlg, IDC_MESSAGE), NULL, FALSE);
		{
			HFONT hFont;
			LOGFONT lf;
			hFont = (HFONT) SendDlgItemMessage(hwndDlg, IDC_MESSAGE, WM_GETFONT, 0, 0);
			if (hFont != NULL && hFont != (HFONT) SendDlgItemMessage(hwndDlg, IDOK, WM_GETFONT, 0, 0))
				DeleteObject(hFont);
			LoadMsgDlgFont(MSGFONTID_MESSAGEAREA, &lf, NULL);
			hFont = CreateFontIndirect(&lf);
			SendDlgItemMessage(hwndDlg, IDC_MESSAGE, WM_SETFONT, (WPARAM) hFont, MAKELPARAM(TRUE, 0));
		}

        /*
		 * configure message history for proper RTL formatting
		 */

		{
            PARAFORMAT2 pf2;
            ZeroMemory((void *)&pf2, sizeof(pf2));
            pf2.cbSize = sizeof(pf2);

            pf2.wEffects = PFE_RTLPARA;
            pf2.dwMask = PFM_RTLPARA;
            SetDlgItemText(hwndDlg, IDC_LOG, _T(""));
            SendDlgItemMessage(hwndDlg, IDC_LOG, EM_SETPARAFORMAT, 0, (LPARAM)&pf2);
            pf2.wEffects = 0;
            SendDlgItemMessage(hwndDlg, IDC_LOG, EM_SETPARAFORMAT, 0, (LPARAM)&pf2);
            SendDlgItemMessage(hwndDlg, IDC_LOG, EM_SETLANGOPTIONS, 0, (LPARAM) SendDlgItemMessage(hwndDlg, IDC_LOG, EM_GETLANGOPTIONS, 0, 0) & ~IMF_AUTOKEYBOARD);
        }
		SendMessage(hwndDlg, DM_REMAKELOG, 0, 0);
		SendMessage(hwndDlg, DM_UPDATEWINICON, 0, 0);
		break;
	case DM_UPDATETITLE:
		{
			TCHAR newtitle[256], oldtitle[256], *szStatus;
			TCHAR *contactName, *pszNewTitleEnd;
			DBCONTACTWRITESETTING *cws = (DBCONTACTWRITESETTING *) wParam;

			pszNewTitleEnd = _T("Message Session");
			if (dat->hContact) {
				if (dat->szProto) {
					CONTACTINFO ci;
					char buf[128];
					int statusIcon = DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_STATUSICON, SRMSGDEFSET_STATUSICON);

					buf[0] = 0;
					dat->wStatus = DBGetContactSettingWord(dat->hContact, dat->szProto, "Status", ID_STATUS_OFFLINE);
					contactName = ( TCHAR* )CallService(MS_CLIST_GETCONTACTDISPLAYNAME, (WPARAM) dat->hContact, GCDNF_TCHAR);
					ZeroMemory(&ci, sizeof(ci));
					ci.cbSize = sizeof(ci);
					ci.hContact = dat->hContact;
					ci.szProto = dat->szProto;
					ci.dwFlag = CNF_UNIQUEID;
					if (!CallService(MS_CONTACT_GETCONTACTINFO, 0, (LPARAM) & ci)) {
						switch (ci.type) {
						case CNFT_ASCIIZ:
							mir_snprintf(buf, SIZEOF(buf), "%s", ci.pszVal);
							mir_free(ci.pszVal);
							break;
						case CNFT_DWORD:
							mir_snprintf(buf, SIZEOF(buf), "%u", ci.dVal);
							break;
						}
					}
					if ( buf[0] )
						SetDlgItemTextA(hwndDlg, IDC_NAME, buf );
					else
						SetDlgItemText(hwndDlg, IDC_NAME, contactName);

					szStatus = (TCHAR*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION, dat->szProto == NULL ? ID_STATUS_OFFLINE : DBGetContactSettingWord(dat->hContact, dat->szProto, "Status", ID_STATUS_OFFLINE), GCMDF_TCHAR);
					if (statusIcon)
						mir_sntprintf(newtitle, SIZEOF(newtitle), _T("%s - %s"), contactName, TranslateTS(pszNewTitleEnd));
					else
						mir_sntprintf(newtitle, SIZEOF(newtitle), _T("%s (%s): %s"), contactName, szStatus, TranslateTS(pszNewTitleEnd));
					if (!cws || (!strcmp(cws->szModule, dat->szProto) && !strcmp(cws->szSetting, "Status"))) {
						InvalidateRect(GetDlgItem(hwndDlg, IDC_PROTOCOL), NULL, TRUE);
						if (statusIcon) {
							SendMessage(hwndDlg, DM_UPDATEWINICON, 0, 0);
						}
					}

					// log
					if ((dat->wStatus != dat->wOldStatus || lParam != 0)
						&& DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SHOWSTATUSCH, SRMSGDEFSET_SHOWSTATUSCH)) {
							DBEVENTINFO dbei;
							TCHAR buffer[200];
							HANDLE hNewEvent;
							int iLen;

							TCHAR *szOldStatus = (TCHAR*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION, (WPARAM) dat->wOldStatus, GCMDF_TCHAR);
							TCHAR *szNewStatus = (TCHAR*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION, (WPARAM) dat->wStatus, GCMDF_TCHAR);

							if (dat->wStatus == ID_STATUS_OFFLINE) {
								iLen = mir_sntprintf(buffer, SIZEOF(buffer), TranslateT("signed off (was %s)"), szOldStatus);
								SendMessage(hwndDlg, DM_TYPING, 0, 0);
							}
							else if (dat->wOldStatus == ID_STATUS_OFFLINE)
								iLen = mir_sntprintf(buffer, SIZEOF(buffer), TranslateT("signed on (%s)"), szNewStatus);
							else
								iLen = mir_sntprintf(buffer, SIZEOF(buffer), TranslateT("is now %s (was %s)"), szNewStatus, szOldStatus);

							{
                        char* blob = ( char* )alloca( 1000 );
								#if defined( _UNICODE )
									int ansiLen = WideCharToMultiByte(CP_ACP, 0, buffer, -1, blob, 1000, 0, 0);
									memcpy( blob+ansiLen, buffer, sizeof(TCHAR)*(iLen+1));
									dbei.cbBlob = ansiLen + sizeof(TCHAR)*(iLen+1);
								#else
									int wLen = MultiByteToWideChar(CP_ACP, 0, buffer, -1, NULL, 0 );
									memcpy( blob, buffer, iLen+1 );
									MultiByteToWideChar(CP_ACP, 0, buffer, -1, (WCHAR*)&blob[iLen+1], wLen+1 );
									dbei.cbBlob = iLen+1 + sizeof(WCHAR)*wLen;
								#endif

								dbei.cbSize = sizeof(dbei);
								dbei.pBlob = (PBYTE) blob;
								dbei.eventType = EVENTTYPE_STATUSCHANGE;
								dbei.flags = 0;
								dbei.timestamp = (DWORD)time(NULL);
								dbei.szModule = dat->szProto;
								hNewEvent = (HANDLE) CallService(MS_DB_EVENT_ADD, (WPARAM) dat->hContact, (LPARAM) & dbei);
								if (dat->hDbEventFirst == NULL) {
									dat->hDbEventFirst = hNewEvent;
									SendMessage(hwndDlg, DM_REMAKELOG, 0, 0);
								}
							}
						}
						dat->wOldStatus = dat->wStatus;
				}
			}
			else lstrcpyn(newtitle, pszNewTitleEnd, SIZEOF(newtitle));

			GetWindowText(hwndDlg, oldtitle, SIZEOF(oldtitle));
			if ( lstrcmp(newtitle, oldtitle )) { //swt() flickers even if the title hasn't actually changed
				SetWindowText(hwndDlg, newtitle);
				SendMessage(hwndDlg, WM_SIZE, 0, 0);
			}
			break;
		}
	case DM_GETWINDOWSTATE:
		{
			UINT state = 0;

			state |= MSG_WINDOW_STATE_EXISTS;
			if (IsWindowVisible(hwndDlg))
				state |= MSG_WINDOW_STATE_VISIBLE;
			if (GetForegroundWindow()==hwndDlg)
				state |= MSG_WINDOW_STATE_FOCUS;
			if (IsIconic(hwndDlg))
				state |= MSG_WINDOW_STATE_ICONIC;
			SetWindowLong(hwndDlg, DWL_MSGRESULT, state);
			return TRUE;

		}
	case DM_CASCADENEWWINDOW:
		if ((HWND) wParam == hwndDlg)
			break;
		{
			RECT rcThis, rcNew;
			GetWindowRect(hwndDlg, &rcThis);
			GetWindowRect((HWND) wParam, &rcNew);
			if (abs(rcThis.left - rcNew.left) < 3 && abs(rcThis.top - rcNew.top) < 3) {
				int offset = GetSystemMetrics(SM_CYCAPTION) + GetSystemMetrics(SM_CYFRAME);
				SetWindowPos((HWND) wParam, 0, rcNew.left + offset, rcNew.top + offset, 0, 0, SWP_NOZORDER | SWP_NOSIZE);
				*(int *) lParam = 1;
			}
		}
		break;
	case WM_ACTIVATE:
		if (LOWORD(wParam) != WA_ACTIVE)
			break;
		else
			SendMessage(hwndDlg, WM_PAINT, 0, 0 );
		//fall through
	case WM_MOUSEACTIVATE:
		if (KillTimer(hwndDlg, TIMERID_FLASHWND))
			FlashWindow(hwndDlg, FALSE);
		break;
	case WM_SETFOCUS:
		SetFocus(GetDlgItem(hwndDlg, IDC_MESSAGE));
		break;
	case WM_GETMINMAXINFO:
		{
			MINMAXINFO* mmi = (MINMAXINFO *) lParam;
			RECT rcWindow, rcLog;
			GetWindowRect(hwndDlg, &rcWindow);
			GetWindowRect(GetDlgItem(hwndDlg, IDC_LOG), &rcLog);
			mmi->ptMinTrackSize.x = rcWindow.right - rcWindow.left - ((rcLog.right - rcLog.left) - dat->minEditBoxSize.cx);
			mmi->ptMinTrackSize.y = rcWindow.bottom - rcWindow.top - ((rcLog.bottom - rcLog.top) - dat->minEditBoxSize.cy);
			return 0;
		}
	case WM_SIZE:
		{
			UTILRESIZEDIALOG urd;
			if (IsIconic(hwndDlg))
				break;
			if (dat->hwndStatus) {
				int icons_width = (GetSystemMetrics(SM_CXSMICON) + 2) * status_icon_list_size + SB_GRIP_WIDTH;
				RECT rc;
				SendMessage(dat->hwndStatus, WM_SIZE, 0, 0);
				GetWindowRect(dat->hwndStatus, &rc);
				if (SendMessage(dat->hwndStatus, SB_GETPARTS, 0, 0) == 3) {
					int statwidths[3];
					statwidths[0] = (rc.right - rc.left - SB_CHAR_WIDTH - icons_width);
					statwidths[1] = (rc.right - rc.left - icons_width);
					statwidths[2] = -1;
					SendMessage(dat->hwndStatus, SB_SETPARTS, 3, (LPARAM) statwidths);
				} else {
					int statwidths[2];
					statwidths[0] = (rc.right - rc.left - icons_width);
					statwidths[1] = -1;
					SendMessage(dat->hwndStatus, SB_SETPARTS, 2, (LPARAM) statwidths);
				}
			}
			ZeroMemory(&urd, sizeof(urd));
			urd.cbSize = sizeof(urd);
			urd.hInstance = g_hInst;
			urd.hwndDlg = hwndDlg;
			urd.lParam = (LPARAM) dat;
			urd.lpTemplate = MAKEINTRESOURCEA(IDD_MSG);
			urd.pfnResizer = MessageDialogResize;
			CallService(MS_UTILS_RESIZEDIALOG, 0, (LPARAM) & urd);
			// The statusbar sometimes draws over these 2 controls so
			// redraw them
			if (dat->hwndStatus) {
				RedrawWindow(GetDlgItem(hwndDlg, IDOK), NULL, NULL, RDW_INVALIDATE);
				RedrawWindow(GetDlgItem(hwndDlg, IDC_MESSAGE), NULL, NULL, RDW_INVALIDATE);
			}
			if ((g_dat->flags&SMF_AVATAR)&&dat->avatarPic)
				RedrawWindow(GetDlgItem(hwndDlg, IDC_AVATAR), NULL, NULL, RDW_INVALIDATE);
			break;
		}
	case DM_SPLITTERMOVED:
		{
			POINT pt;
			RECT rc;
			RECT rcLog;
			GetWindowRect(GetDlgItem(hwndDlg, IDC_LOG), &rcLog);
			if ((HWND) lParam == GetDlgItem(hwndDlg, IDC_SPLITTER)) {
				int oldSplitterY;
				GetClientRect(hwndDlg, &rc);
				pt.x = 0;
				pt.y = wParam;
				ScreenToClient(hwndDlg, &pt);

				oldSplitterY = dat->splitterPos;
				dat->splitterPos = rc.bottom - pt.y + 23;
				GetWindowRect(GetDlgItem(hwndDlg, IDC_MESSAGE), &rc);
				if (rc.bottom - rc.top + (dat->splitterPos - oldSplitterY) < dat->minEditBoxSize.cy)
					dat->splitterPos = oldSplitterY + dat->minEditBoxSize.cy - (rc.bottom - rc.top);
				if (rcLog.bottom - rcLog.top - (dat->splitterPos - oldSplitterY) < dat->minEditBoxSize.cy)
					dat->splitterPos = oldSplitterY - dat->minEditBoxSize.cy + (rcLog.bottom - rcLog.top);
			}
			SendMessage(hwndDlg, WM_SIZE, 0, 0);
			break;
		}
	case DM_REMAKELOG:
		dat->lastEventType = -1;
		StreamInEvents(hwndDlg, dat->hDbEventFirst, -1, 0);
		break;
	case DM_APPENDTOLOG:   //takes wParam=hDbEvent
		StreamInEvents(hwndDlg, (HANDLE) wParam, 1, 1);
		break;
	case DM_SCROLLLOGTOBOTTOM:
		{
			SCROLLINFO si = { 0 };
			if ((GetWindowLong(GetDlgItem(hwndDlg, IDC_LOG), GWL_STYLE) & WS_VSCROLL) == 0)
				break;
			si.cbSize = sizeof(si);
			si.fMask = SIF_PAGE | SIF_RANGE;
			GetScrollInfo(GetDlgItem(hwndDlg, IDC_LOG), SB_VERT, &si);
			si.fMask = SIF_POS;
			si.nPos = si.nMax - si.nPage + 1;
			SetScrollInfo(GetDlgItem(hwndDlg, IDC_LOG), SB_VERT, &si, TRUE);
			PostMessage(GetDlgItem(hwndDlg, IDC_LOG), WM_VSCROLL, MAKEWPARAM(SB_BOTTOM, 0), 0);
			break;
		}
	case HM_DBEVENTADDED:
		if ((HANDLE) wParam != dat->hContact)
			break;
		{
			DBEVENTINFO dbei = { 0 };

			dbei.cbSize = sizeof(dbei);
			dbei.cbBlob = 0;
			CallService(MS_DB_EVENT_GET, lParam, (LPARAM) & dbei);
			if (dat->hDbEventFirst == NULL)
				dat->hDbEventFirst = (HANDLE) lParam;
			if (dbei.eventType == EVENTTYPE_MESSAGE && (dbei.flags & DBEF_READ))
				break;
			if (DbEventIsShown(&dbei, dat)) {
				if (dbei.eventType == EVENTTYPE_MESSAGE && !(dbei.flags & (DBEF_SENT))) {
					if (GetForegroundWindow()==hwndDlg)
						SkinPlaySound("RecvMsgActive");
					else SkinPlaySound("RecvMsgInactive");
				}
				if (dbei.eventType == EVENTTYPE_MESSAGE && dat->hwndStatus && !(dbei.flags & (DBEF_SENT))) {
					dat->lastMessage = dbei.timestamp;
					SendMessage(hwndDlg, DM_UPDATELASTMESSAGE, 0, 0);
				}
				if ((HANDLE) lParam != dat->hDbEventFirst && (HANDLE) CallService(MS_DB_EVENT_FINDNEXT, lParam, 0) == NULL)
					SendMessage(hwndDlg, DM_APPENDTOLOG, lParam, 0);
				else
					SendMessage(hwndDlg, DM_REMAKELOG, 0, 0);
				if ((GetActiveWindow() != hwndDlg || GetForegroundWindow() != hwndDlg) && !(dbei.flags & DBEF_SENT)
					&& dbei.eventType != EVENTTYPE_STATUSCHANGE) {
						SetTimer(hwndDlg, TIMERID_FLASHWND, TIMEOUT_FLASHWND, NULL);
					}
			}
			break;
		}
	case WM_TIMER:
		if (wParam == TIMERID_MSGSEND) {
			KillTimer(hwndDlg, wParam);
			ShowWindow(hwndDlg, SW_SHOWNORMAL);
			EnableWindow(hwndDlg, FALSE);
			CreateDialogParam(g_hInst, MAKEINTRESOURCE(IDD_MSGSENDERROR), hwndDlg, ErrorDlgProc, (LPARAM) strdup( Translate("The message send timed out.")));
		}
		else if (wParam == TIMERID_FLASHWND) {
			FlashWindow(hwndDlg, TRUE);
			if (dat->nFlash > dat->nFlashMax) {
				KillTimer(hwndDlg, TIMERID_FLASHWND);
				FlashWindow(hwndDlg, FALSE);
				dat->nFlash = 0;
			}
			dat->nFlash++;
		}
		else if (wParam == TIMERID_TYPE) {
			if (dat->nTypeMode == PROTOTYPE_SELFTYPING_ON && GetTickCount() - dat->nLastTyping > TIMEOUT_TYPEOFF) {
				NotifyTyping(dat, PROTOTYPE_SELFTYPING_OFF);
			}
			if (dat->showTyping) {
				if (dat->nTypeSecs) {
					dat->nTypeSecs--;
					if (GetForegroundWindow() == hwndDlg)
						SendMessage(hwndDlg, DM_UPDATEWINICON, 0, 0);
				}
				else {
					SendMessage(hwndDlg, DM_UPDATELASTMESSAGE, 0, 0);
					if (g_dat->flags&SMF_SHOWTYPINGWIN)
						SendMessage(hwndDlg, DM_UPDATEWINICON, 0, 0);
					dat->showTyping = 0;
				}
			}
			else {
				if (dat->nTypeSecs) {
					RECT rc;
					HFONT hfont, oldFont;
					HDC hdc;
					HBRUSH hbrush = CreateSolidBrush( RGB(228, 240, 254) );
					TCHAR szBuf[256];
					TCHAR *szContactName = ( TCHAR* ) CallService(MS_CLIST_GETCONTACTDISPLAYNAME, (WPARAM) dat->hContact, GCDNF_TCHAR);

					mir_sntprintf(szBuf, SIZEOF(szBuf), TranslateT("%s is typing a message..."), szContactName);
					dat->nTypeSecs--;
					SendMessage(dat->hwndStatus, SB_SETTEXT, 0, (LPARAM) szBuf);
					SendMessage(dat->hwndStatus, SB_SETICON, 0, (LPARAM) g_dat->hIcons[SMF_ICON_TYPING]);
					if ((g_dat->flags&SMF_SHOWTYPINGWIN) && GetForegroundWindow() != hwndDlg)
						SendMessage(hwndDlg, WM_SETICON, (WPARAM) ICON_BIG, (LPARAM) g_dat->hIcons[SMF_ICON_TYPING]);
					dat->showTyping = 1;
					lstrcpy( statusMsg, szBuf );
					GetClientRect(hwndDlg, &rc);
					rc.left = 8;
					rc.top = rc.bottom - 22;
					rc.right = rc.right - 16;
					rc.bottom = rc.bottom - 6;
					hfont = CreateFont(-11, 0, 0, 0, 400, 0, 0, 0, 134, 0, 0, 0, 0, _T("MS Shell Dlg 2")); 
					hdc = GetDC(hwndDlg);
					oldFont = SelectObject(hdc, hfont);
					SetBkColor(hdc, RGB( 228, 240, 254 ) );
					SetTextColor(hdc, RGB( 128, 128, 128 ) );
					FillRect(hdc, &rc, hbrush);
					DrawText( hdc, statusMsg, lstrlen(statusMsg), &rc, DT_LEFT );
					SelectObject(hdc,oldFont);
					DeleteObject(hfont);
				}
			}
		}
		break;
	case DM_ERRORDECIDED:
		EnableWindow(hwndDlg, TRUE);
		switch (wParam) {
		case MSGERROR_CANCEL:
			EnableWindow(GetDlgItem(hwndDlg, IDOK), TRUE);
			SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_SETREADONLY, FALSE, 0);
			SetFocus(GetDlgItem(hwndDlg, IDC_MESSAGE));
			break;
		case MSGERROR_RETRY:
			{
				if (dat->hSendId == NULL && dat->hContact == NULL)
					return 0;
				dat->hSendId = (HANDLE) CallContactService(dat->hContact, MsgServiceName(dat->hContact), SEND_FLAGS, (LPARAM) dat->sendBuffer);
			}
			SetTimer(hwndDlg, TIMERID_MSGSEND, DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_MSGTIMEOUT, SRMSGDEFSET_MSGTIMEOUT), NULL);
			break;
			}
			break;
		case WM_CTLCOLOREDIT:
			{
				COLORREF colour;
				if ((HWND) lParam != GetDlgItem(hwndDlg, IDC_MESSAGE))
					break;
				LoadMsgDlgFont(MSGFONTID_MESSAGEAREA, NULL, &colour);
				SetTextColor((HDC) wParam, colour);
				SetBkColor((HDC) wParam, DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_BKGCOLOUR, SRMSGDEFSET_BKGCOLOUR));
				return (BOOL) dat->hBkgBrush;
			}
		case WM_MEASUREITEM:
			return CallService(MS_CLIST_MENUMEASUREITEM, wParam, lParam);
		case WM_DRAWITEM:
			{
				LPDRAWITEMSTRUCT dis = (LPDRAWITEMSTRUCT) lParam;
				if (dis->hwndItem == dat->hwndStatus) {
					DrawStatusIcons(dat->hContact, dis->hDC, dis->rcItem, 2);
					return 0;
				}
				else if (dis->hwndItem == GetDlgItem(hwndDlg, IDC_PROTOCOL)) {
					if (dat->szProto) {
						HICON hIcon;
						int dwStatus;

						dwStatus = DBGetContactSettingWord(dat->hContact, dat->szProto, "Status", ID_STATUS_OFFLINE);
						hIcon = LoadSkinnedProtoIcon(dat->szProto, dwStatus);
						if (hIcon) {
							if (DBGetContactSettingDword(dat->hContact, dat->szProto, "IdleTS", 0)) {
								HIMAGELIST hImageList;

								hImageList = ImageList_Create(GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON), IsWinVerXPPlus()? ILC_COLOR32 | ILC_MASK : ILC_COLOR16 | ILC_MASK, 1, 0);
								ImageList_AddIcon(hImageList, hIcon);
								ImageList_DrawEx(hImageList, 0, dis->hDC, dis->rcItem.left, dis->rcItem.top, 0, 0, CLR_NONE, CLR_NONE, ILD_SELECTED);
								ImageList_RemoveAll(hImageList);
								ImageList_Destroy(hImageList);
							}
							else DrawIconEx(dis->hDC, dis->rcItem.left, dis->rcItem.top, hIcon, GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON), 0, NULL, DI_NORMAL);
						}
					}
				}
				else if (dis->hwndItem == GetDlgItem(hwndDlg, IDC_AVATAR) && dat->avatarPic && (g_dat->flags&SMF_AVATAR)) {
					BITMAP bminfo;
					HPEN hPen, hOldPen;

               hPen = CreatePen(PS_SOLID, 1, RGB(0,0,0));
               hOldPen = SelectObject(dis->hDC, hPen);
               Rectangle(dis->hDC, 0, 0, dat->avatarWidth, dat->avatarHeight);
					SelectObject(dis->hDC,hOldPen);
               DeleteObject(hPen);
					GetObject(dat->avatarPic, sizeof(bminfo), &bminfo);
					{
						HDC hdcMem = CreateCompatibleDC(dis->hDC);
                  HBITMAP hbmMem = (HBITMAP)SelectObject(hdcMem, dat->avatarPic);
						{
							double aspect = 0, w = 0;

							aspect = (double)dat->limitAvatarH / (double)bminfo.bmHeight;
							w = (double)bminfo.bmWidth * aspect;
							SetStretchBltMode(dis->hDC, HALFTONE);
                     StretchBlt(dis->hDC, 1, 1, dat->avatarWidth-2, dat->avatarHeight-2, hdcMem, 0, 0, bminfo.bmWidth, bminfo.bmHeight, SRCCOPY);
						}
						SelectObject(hdcMem,hbmMem);
                  DeleteDC(hdcMem);
					}
				}
				return CallService(MS_CLIST_MENUDRAWITEM, wParam, lParam);
			}
		case WM_COMMAND:
			if (CallService(MS_CLIST_MENUPROCESSCOMMAND, MAKEWPARAM(LOWORD(wParam), MPCF_CONTACTMENU), (LPARAM) dat->hContact))
				break;
			switch (LOWORD(wParam)) {
			case IDOK:
				if (!IsWindowEnabled(GetDlgItem(hwndDlg, IDOK)))
					break;
				{
					int flags = SEND_FLAGS;
					//this is a 'send' button
					int bufSize = GetWindowTextLengthA(GetDlgItem(hwndDlg, IDC_MESSAGE)) + 1;
					dat->sendBuffer = (char *) realloc(dat->sendBuffer, bufSize * (sizeof(TCHAR) + 1));
					dat->bIsRtl = 0;
					GetDlgItemTextA(hwndDlg, IDC_MESSAGE, dat->sendBuffer, bufSize);
					#if defined( _UNICODE )
					// all that crap with temporary buffers is related to the bug #0001466 (empty messages
					// on x64 machines). GetDlgItemTextW should use the 2-byte aligned buffer
					{	WCHAR* temp = ( WCHAR* )alloca( bufSize * sizeof( TCHAR ));
						GetDlgItemTextW(hwndDlg, IDC_MESSAGE, temp, bufSize);
						memcpy(( TCHAR*)&dat->sendBuffer[bufSize], temp, bufSize * sizeof( TCHAR ));
						if ( RTL_Detect( temp )) {
							flags |= PREF_RTL;
							dat->bIsRtl = 1;
						}
					}
					#endif
					if (dat->sendBuffer[0] == 0)
						break;
					#if defined( _UNICODE )
						dat->cmdList = tcmdlist_append(dat->cmdList, (TCHAR *) & dat->sendBuffer[bufSize]);
					#else
						dat->cmdList = tcmdlist_append(dat->cmdList, dat->sendBuffer);
					#endif
					dat->cmdListCurrent = 0;
					if (dat->nTypeMode == PROTOTYPE_SELFTYPING_ON) {
						NotifyTyping(dat, PROTOTYPE_SELFTYPING_OFF);
					}

					if (dat->hContact == NULL)
						break;      //never happens
					dat->sendCount = 1;
					dat->hSendId = (HANDLE) CallContactService(dat->hContact, MsgServiceName(dat->hContact), flags, (LPARAM) dat->sendBuffer);
					EnableWindow(GetDlgItem(hwndDlg, IDOK), FALSE);
					SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_SETREADONLY, TRUE, 0);

					//create a timeout timer
					SetTimer(hwndDlg, TIMERID_MSGSEND, DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_MSGTIMEOUT, SRMSGDEFSET_MSGTIMEOUT), NULL);
					if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOMIN, SRMSGDEFSET_AUTOMIN))
						ShowWindow(hwndDlg, SW_MINIMIZE);
				}
				return TRUE;
			case IDCANCEL:
				DestroyWindow(hwndDlg);
				return TRUE;
			case IDC_USERMENU:
			case IDC_NAME:
				{
					if(GetKeyState(VK_SHIFT) & 0x8000) {    // copy user name
                            SendMessage(hwndDlg, DM_USERNAMETOCLIP, 0, 0);
                    }
					else {
						RECT rc;
						HMENU hMenu = (HMENU) CallService(MS_CLIST_MENUBUILDCONTACT, (WPARAM) dat->hContact, 0);
						GetWindowRect(GetDlgItem(hwndDlg, LOWORD(wParam)), &rc);
						TrackPopupMenu(hMenu, 0, rc.left, rc.bottom, 0, hwndDlg, NULL);
						DestroyMenu(hMenu);
					}
				}
				break;
			case IDC_HISTORY:
				CallService(MS_HISTORY_SHOWCONTACTHISTORY, (WPARAM) dat->hContact, 0);
				break;
			case IDC_DETAILS:
				CallService(MS_USERINFO_SHOWDIALOG, (WPARAM) dat->hContact, 0);
				break;
			case IDC_ADD:
				{
					ADDCONTACTSTRUCT acs = { 0 };

					acs.handle = dat->hContact;
					acs.handleType = HANDLE_CONTACT;
					acs.szProto = 0;
					CallService(MS_ADDCONTACT_SHOW, (WPARAM) hwndDlg, (LPARAM) & acs);
				}
				if (!DBGetContactSettingByte(dat->hContact, "CList", "NotOnList", 0)) {
					ShowWindow(GetDlgItem(hwndDlg, IDC_ADD), FALSE);
				}
				break;
			case IDC_MESSAGE:
				if (HIWORD(wParam) == EN_CHANGE) {
					int len = GetWindowTextLength(GetDlgItem(hwndDlg, IDC_MESSAGE));
					UpdateReadChars(hwndDlg, dat->hwndStatus);
					EnableWindow(GetDlgItem(hwndDlg, IDOK), len != 0);
					if (!(GetKeyState(VK_CONTROL) & 0x8000) && !(GetKeyState(VK_SHIFT) & 0x8000)) {
						dat->nLastTyping = GetTickCount();
						if (GetWindowTextLength(GetDlgItem(hwndDlg, IDC_MESSAGE))) {
							if (dat->nTypeMode == PROTOTYPE_SELFTYPING_OFF) {
								NotifyTyping(dat, PROTOTYPE_SELFTYPING_ON);
							}
						}
						else {
							if (dat->nTypeMode == PROTOTYPE_SELFTYPING_ON) {
								NotifyTyping(dat, PROTOTYPE_SELFTYPING_OFF);
							}
						}
					}
				}
				break;
				}
				break;
			case WM_NOTIFY:
 				if(dat && ((NMHDR *) lParam)->hwndFrom == dat->hwndStatus) {
 					if (((LPNMHDR) lParam)->code == NM_CLICK || ((LPNMHDR) lParam)->code == NM_RCLICK) {
 						NMMOUSE *nm = (NMMOUSE *) lParam;
 						RECT rc;

						SendMessage(dat->hwndStatus, SB_GETRECT, SendMessage(dat->hwndStatus, SB_GETPARTS, 0, 0) - 1, (LPARAM)&rc);
						if (nm->pt.x >= rc.left) {
							CheckIconClick(dat->hContact, dat->hwndStatus, nm->pt, rc, 2, ((LPNMHDR) lParam)->code == NM_RCLICK ? MBCF_RIGHTBUTTON : 0);
						}
 						return TRUE;
 					}
 				}
				switch (((NMHDR *) lParam)->idFrom) {
			case IDC_LOG:
				switch (((NMHDR *) lParam)->code) {
			case EN_MSGFILTER:
				switch (((MSGFILTER *) lParam)->msg) {
			case WM_LBUTTONDOWN:
				{
					HCURSOR hCur = GetCursor();
					if (hCur == LoadCursor(NULL, IDC_SIZENS) || hCur == LoadCursor(NULL, IDC_SIZEWE)
						|| hCur == LoadCursor(NULL, IDC_SIZENESW) || hCur == LoadCursor(NULL, IDC_SIZENWSE)) {
							SetWindowLong(hwndDlg, DWL_MSGRESULT, TRUE);
							return TRUE;
						}
						break;
				}
			case WM_MOUSEMOVE:
				{
					HCURSOR hCur = GetCursor();
					if (hCur == LoadCursor(NULL, IDC_SIZENS) || hCur == LoadCursor(NULL, IDC_SIZEWE)
						|| hCur == LoadCursor(NULL, IDC_SIZENESW) || hCur == LoadCursor(NULL, IDC_SIZENWSE))
						SetCursor(LoadCursor(NULL, IDC_ARROW));
					break;
				}
			case WM_RBUTTONUP:
				{
					HMENU hMenu, hSubMenu;
					POINT pt;
					CHARRANGE sel, all = { 0, -1 };

					hMenu = LoadMenu(g_hInst, MAKEINTRESOURCE(IDR_CONTEXT));
					hSubMenu = GetSubMenu(hMenu, 0);
					CallService(MS_LANGPACK_TRANSLATEMENU, (WPARAM) hSubMenu, 0);
					SendMessage(((NMHDR *) lParam)->hwndFrom, EM_EXGETSEL, 0, (LPARAM) & sel);
					if (sel.cpMin == sel.cpMax)
						EnableMenuItem(hSubMenu, IDM_COPY, MF_BYCOMMAND | MF_GRAYED);
					pt.x = (short) LOWORD(((ENLINK *) lParam)->lParam);
					pt.y = (short) HIWORD(((ENLINK *) lParam)->lParam);
					ClientToScreen(((NMHDR *) lParam)->hwndFrom, &pt);
					switch (TrackPopupMenu(hSubMenu, TPM_RETURNCMD, pt.x, pt.y, 0, hwndDlg, NULL)) {
					case IDM_COPY:
						SendMessage(((NMHDR *) lParam)->hwndFrom, WM_COPY, 0, 0);
						break;
					case IDM_COPYALL:
						SendMessage(((NMHDR *) lParam)->hwndFrom, EM_EXSETSEL, 0, (LPARAM) & all);
						SendMessage(((NMHDR *) lParam)->hwndFrom, WM_COPY, 0, 0);
						SendMessage(((NMHDR *) lParam)->hwndFrom, EM_EXSETSEL, 0, (LPARAM) & sel);
						break;
					case IDM_SELECTALL:
						SendMessage(((NMHDR *) lParam)->hwndFrom, EM_EXSETSEL, 0, (LPARAM) & all);
						break;
					case IDM_CLEAR:
						SetDlgItemText(hwndDlg, IDC_LOG, _T(""));
						dat->hDbEventFirst = NULL;
						break;
							}
							DestroyMenu(hMenu);
							SetWindowLong(hwndDlg, DWL_MSGRESULT, TRUE);
							return TRUE;
						}
				}
				break;
			case EN_LINK:
				switch (((ENLINK *) lParam)->msg) {
				case WM_SETCURSOR:
					SetCursor(hCurHyperlinkHand);
					SetWindowLong(hwndDlg, DWL_MSGRESULT, TRUE);
					return TRUE;
				case WM_RBUTTONDOWN:
				case WM_LBUTTONUP:
					{
						TEXTRANGEA tr;
						CHARRANGE sel;

						SendDlgItemMessage(hwndDlg, IDC_LOG, EM_EXGETSEL, 0, (LPARAM) & sel);
						if (sel.cpMin != sel.cpMax)
							break;
						tr.chrg = ((ENLINK *) lParam)->chrg;
						tr.lpstrText = malloc(tr.chrg.cpMax - tr.chrg.cpMin + 8);
						SendDlgItemMessageA(hwndDlg, IDC_LOG, EM_GETTEXTRANGE, 0, (LPARAM) & tr);
						if (strchr(tr.lpstrText, '@') != NULL && strchr(tr.lpstrText, ':') == NULL && strchr(tr.lpstrText, '/') == NULL) {
							MoveMemory(tr.lpstrText + 7, tr.lpstrText, tr.chrg.cpMax - tr.chrg.cpMin + 1);
							CopyMemory(tr.lpstrText, "mailto:", 7);
						}
						if (((ENLINK *) lParam)->msg == WM_RBUTTONDOWN) {
							HMENU hMenu, hSubMenu;
							POINT pt;

							hMenu = LoadMenu(g_hInst, MAKEINTRESOURCE(IDR_CONTEXT));
							hSubMenu = GetSubMenu(hMenu, 1);
							CallService(MS_LANGPACK_TRANSLATEMENU, (WPARAM) hSubMenu, 0);
							pt.x = (short) LOWORD(((ENLINK *) lParam)->lParam);
							pt.y = (short) HIWORD(((ENLINK *) lParam)->lParam);
							ClientToScreen(((NMHDR *) lParam)->hwndFrom, &pt);
							switch (TrackPopupMenu(hSubMenu, TPM_RETURNCMD, pt.x, pt.y, 0, hwndDlg, NULL)) {
							case IDM_OPENNEW:
								CallService(MS_UTILS_OPENURL, 1, (LPARAM) tr.lpstrText);
								break;
							case IDM_OPENEXISTING:
								CallService(MS_UTILS_OPENURL, 0, (LPARAM) tr.lpstrText);
								break;
							case IDM_COPYLINK:
								{
									HGLOBAL hData;
									if (!OpenClipboard(hwndDlg))
										break;
									EmptyClipboard();
									hData = GlobalAlloc(GMEM_MOVEABLE, lstrlenA(tr.lpstrText) + 1);
									lstrcpyA(GlobalLock(hData), tr.lpstrText);
									GlobalUnlock(hData);
									SetClipboardData(CF_TEXT, hData);
									CloseClipboard();
									break;
								}
							}
							free(tr.lpstrText);
							DestroyMenu(hMenu);
							SetWindowLong(hwndDlg, DWL_MSGRESULT, TRUE);
							return TRUE;
						}
						else {
							CallService(MS_UTILS_OPENURL, 1, (LPARAM) tr.lpstrText);
							SetFocus(GetDlgItem(hwndDlg, IDC_MESSAGE));
						}

						free(tr.lpstrText);
						break;
					}
				}
				break;
			}
			break;
		}
		break;
	case HM_EVENTSENT:
		{
			ACKDATA *ack = (ACKDATA *) lParam;
			DBEVENTINFO dbei = { 0 };
			HANDLE hNewEvent;

			if (ack->type != ACKTYPE_MESSAGE)
				break;
			if (dat->sendCount==0)
				break;
			switch (ack->result) {
				case ACKRESULT_FAILED:
					KillTimer(hwndDlg, TIMERID_MSGSEND);
					ShowWindow(hwndDlg, SW_SHOWNORMAL);
					EnableWindow(hwndDlg, FALSE);
					CreateDialogParam(g_hInst, MAKEINTRESOURCE(IDD_MSGSENDERROR), hwndDlg, ErrorDlgProc, (ack->lParam == 0) ? 0 : (LPARAM) strdup((char *) ack->lParam));
					return 0;
			}
			if (dat->sendBuffer==NULL) return 0;
			dbei.cbSize = sizeof(dbei);
			dbei.eventType = EVENTTYPE_MESSAGE;
			dbei.flags = DBEF_SENT + (( dat->bIsRtl ) ? DBEF_RTL : 0 );

			dbei.szModule = (char *) CallService(MS_PROTO_GETCONTACTBASEPROTO, (WPARAM) dat->hContact, 0);
			dbei.timestamp = (DWORD)time(NULL);
			dbei.cbBlob = lstrlenA(dat->sendBuffer) + 1;
	#if defined( _UNICODE )
			dbei.cbBlob *= sizeof(TCHAR) + 1;
	#endif
			dbei.pBlob = (PBYTE) dat->sendBuffer;
			hNewEvent = (HANDLE) CallService(MS_DB_EVENT_ADD, (WPARAM) dat->hContact, (LPARAM) & dbei);
			SkinPlaySound("SendMsg");
			if (dat->hDbEventFirst == NULL) {
				dat->hDbEventFirst = hNewEvent;
				SendMessage(hwndDlg, DM_REMAKELOG, 0, 0);
			}

			dat->hSendId = NULL;
			{
				int len;
				//all messages sent
				dat->sendCount = 0;
				KillTimer(hwndDlg, TIMERID_MSGSEND);
				SetDlgItemText(hwndDlg, IDC_MESSAGE, _T(""));
				EnableWindow(GetDlgItem(hwndDlg, IDOK), FALSE);
				SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_SETREADONLY, FALSE, 0);
				SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_REPLAYSAVEDKEYSTROKES, 0, 0);
				if (GetForegroundWindow() == hwndDlg)
					SetFocus(GetDlgItem(hwndDlg, IDC_MESSAGE));
				len = GetWindowTextLength(GetDlgItem(hwndDlg, IDC_MESSAGE));
				UpdateReadChars(hwndDlg, dat->hwndStatus);
				EnableWindow(GetDlgItem(hwndDlg, IDOK), len != 0);
				if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_AUTOCLOSE, SRMSGDEFSET_AUTOCLOSE))
					DestroyWindow(hwndDlg);
			}
			break;
		}

	case DM_STATUSICONCHANGE:
		SendMessage(dat->hwndStatus, SB_SETTEXT, (WPARAM)(SBT_OWNERDRAW | (SendMessage(dat->hwndStatus, SB_GETPARTS, 0, 0) - 1)), (LPARAM)0);
		return 0;
	case WM_DESTROY:
		NotifyLocalWinEvent(dat->hContact, hwndDlg, MSG_WINDOW_EVT_CLOSING);
		if (dat->nTypeMode == PROTOTYPE_SELFTYPING_ON) {
			NotifyTyping(dat, PROTOTYPE_SELFTYPING_OFF);
		}
		if (dat->hBkgBrush)
			DeleteObject(dat->hBkgBrush);
		if (dat->sendBuffer != NULL)
			free(dat->sendBuffer);
		if (dat->hwndStatus)
			DestroyWindow(dat->hwndStatus);
		tcmdlist_free(dat->cmdList);
		WindowList_Remove(g_dat->hMessageWindowList, hwndDlg);
		DBWriteContactSettingDword(DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SAVEPERCONTACT, SRMSGDEFSET_SAVEPERCONTACT)?dat->hContact:NULL, SRMMMOD, "splitterPos", dat->splitterPos);
		SetWindowLong(GetDlgItem(hwndDlg, IDC_SPLITTER), GWL_WNDPROC, (LONG) OldSplitterProc);
		SendDlgItemMessage(hwndDlg, IDC_MESSAGE, EM_UNSUBCLASSED, 0, 0);
		SetWindowLong(GetDlgItem(hwndDlg, IDC_MESSAGE), GWL_WNDPROC, (LONG) OldMessageEditProc);
		{
			HFONT hFont;
			hFont = (HFONT) SendDlgItemMessage(hwndDlg, IDC_MESSAGE, WM_GETFONT, 0, 0);
			if (hFont != NULL && hFont != (HFONT) SendDlgItemMessage(hwndDlg, IDOK, WM_GETFONT, 0, 0))
				DeleteObject(hFont);
		}
		{
			WINDOWPLACEMENT wp = { 0 };
			HANDLE hContact;

			if (DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_SAVEPERCONTACT, SRMSGDEFSET_SAVEPERCONTACT))
				hContact = dat->hContact;
			else
				hContact = NULL;
			wp.length = sizeof(wp);
			GetWindowPlacement(hwndDlg, &wp);
			if (!dat->windowWasCascaded) {
				DBWriteContactSettingDword(hContact, SRMMMOD, "x", wp.rcNormalPosition.left);
				DBWriteContactSettingDword(hContact, SRMMMOD, "y", wp.rcNormalPosition.top);
			}
			DBWriteContactSettingDword(hContact, SRMMMOD, "width", wp.rcNormalPosition.right - wp.rcNormalPosition.left);
			DBWriteContactSettingDword(hContact, SRMMMOD, "height", wp.rcNormalPosition.bottom - wp.rcNormalPosition.top);
		}
		if (dat->avatarPic)
			DeleteObject(dat->avatarPic);
		NotifyLocalWinEvent(dat->hContact, hwndDlg, MSG_WINDOW_EVT_CLOSE);
		if (dat->hContact&&DBGetContactSettingByte(NULL, SRMMMOD, SRMSGSET_DELTEMP, SRMSGDEFSET_DELTEMP)) {
			if (DBGetContactSettingByte(dat->hContact, "CList", "NotOnList", 0)) {
				CallService(MS_DB_CONTACT_DELETE, (WPARAM)dat->hContact, 0);
			}
		}
		free(dat);
		SetWindowLong(hwndDlg, GWL_USERDATA, 0);
		break;
	case WM_CTLCOLORDLG:
		{
            HBRUSH hBrush = CreateSolidBrush( RGB( 228, 240, 254 ) );
			return hBrush;
		}
	case WM_PAINT:
		{
			static LOGFONT lf;
			TCHAR hint[] = _T("在下面框中输入文字, 按回车发送, Ctrl+回车换行");
			HFONT hfont, oldFont;
			RECT rc, rcText, rcFont, rcHint;
			HDC hdc = GetDC(hwndDlg);
			HDC hdcCompatible = CreateCompatibleDC(hdc); 
			HDC hdcMem = CreateCompatibleDC(hdc);
			HDC hdcFont = CreateCompatibleDC(hdc);
			HBITMAP hbmDlg, oldhbmDlg;
			HBITMAP hbm = LoadBitmap( g_hInst, MAKEINTRESOURCE(IDB_TOP) );
			HBITMAP hbmFont = LoadBitmap( g_hInst, MAKEINTRESOURCE(IDB_FONT) );
			HPEN hpen = CreatePen(PS_SOLID, 1, RGB(186, 197, 225));
			HBRUSH hbrush = CreateSolidBrush( RGB(228, 240, 254) );
			HPEN oldPen = SelectObject( hdcMem, hpen );
			HBRUSH oldBrush = SelectObject( hdcMem, hbrush );
			HBITMAP oldbmp = SelectObject( hdcCompatible, hbm );
			HBITMAP oldbmpFont = SelectObject( hdcFont, hbmFont );
			POINT posFont;

			GetWindowRect( GetDlgItem(hwndDlg, IDOK), &rcFont );
			posFont.x = rcFont.right;
			posFont.y = rcFont.top;
			ScreenToClient( hwndDlg, &posFont );
			rcFont.left = posFont.x - 20;
			rcFont.right = posFont.x;
			rcFont.top = posFont.y - 20;
			rcFont.bottom = posFont.y;

			rcHint.left = 8;
			rcHint.top = rcFont.top + 2;
			rcHint.right = rcFont.left - 2;
			rcHint.bottom = rcFont.bottom;

			GetClientRect( hwndDlg, &rc );
			hbmDlg = CreateCompatibleBitmap( hdc, rc.right - rc.left, rc.bottom - rc.top );
			oldhbmDlg = SelectObject(hdcMem, hbmDlg);
			FillRect( hdcMem, &rc, hbrush );
			BitBlt(hdcMem, 0, 0, 350, 34, hdcCompatible, 0, 0, SRCCOPY);
			if( rc.right > 350 )
			{
				int n = 350;
				while( n < rc.right )
				{
					BitBlt(hdcMem, n, 0, 50, 34, hdcCompatible, 300, 0, SRCCOPY);
					n = n + 50;
				}
			}
			RoundRect( hdcMem, rc.left + 3, rc.top + 35, rc.right - 3, rc.bottom - 3, 10, 10 );
			BitBlt(hdcMem, rcFont.left, rcFont.top, 20, 20, hdcFont, 0, 0, SRCCOPY);
			//SendMessage(dat->hwndStatus, SB_GETTEXT, 0, (LPARAM) statusMsg);
			SetBkMode( hdcMem, TRANSPARENT );
			rcText.left = 8;
			rcText.top = rc.bottom - 22;
			rcText.right = rc.right - 16;
			rcText.bottom = rc.bottom - 6;
			SetTextColor(hdcMem, RGB(128, 128, 128) );

			hfont = CreateFont(-11, 0, 0, 0, 400, 0, 0, 0, 134, 0, 0, 0, 0, _T("MS Shell Dlg 2")); 
			oldFont = SelectObject( hdcMem, hfont );

			//TextOut( hdcMem, rcText.left, rcText.top, statusMsg, lstrlen(statusMsg) );
			DrawText(hdcMem, hint, lstrlen( hint ), &rcHint, DT_LEFT );
			DrawText(hdcMem, statusMsg, lstrlen( statusMsg ), &rcText, DT_LEFT );

			BitBlt(hdc, 0, 0, rc.right - rc.left, rc.bottom - rc.top, hdcMem, 0, 0, SRCCOPY); 

			SelectObject(hdcCompatible, hbm);
			DeleteObject(hbm);
			DeleteDC(hdcCompatible);
			SelectObject(hdcMem, oldhbmDlg);
			DeleteObject(hbmDlg);
			SelectObject(hdcMem, oldPen);
			DeleteObject(hpen);
			SelectObject(hdcMem, oldBrush);
			DeleteObject(hbrush);
			SelectObject(hdcMem, oldFont);
			DeleteObject(hfont);

			DeleteDC(hdcMem);
			return FALSE;//DefWindowProc( hwndDlg, WM_PAINT, wParam, lParam );
		}
	case WM_MOUSEMOVE:
		{
			static int bPtInRect = 0;
			static int bPtInRectFont = 0;
			RECT rc, rcFont;
			POINT pos, posFont;
			HDC hdc = GetDC(hwndDlg);
			HBRUSH hbrush = CreateSolidBrush( RGB(0, 96, 134) );
			pos.x = LOWORD(lParam);
			pos.y = HIWORD(lParam);
			rc.left = 70;
			rc.top = 0;
			rc.right = 140;
			rc.bottom = 34;
			
			GetWindowRect( GetDlgItem(hwndDlg, IDOK), &rcFont );
			posFont.x = rcFont.right;
			posFont.y = rcFont.top;
			ScreenToClient( hwndDlg, &posFont );
			rcFont.left = posFont.x - 20;
			rcFont.right = posFont.x;
			rcFont.top = posFont.y - 20;
			rcFont.bottom = posFont.y;

			if( PtInRect( &rc, pos ) )
			{
				if( !bPtInRect )
				{
					FrameRect( hdc, &rc, hbrush );
					bPtInRect = 1;
				}
			}
			else if( PtInRect( &rcFont, pos ) )
			{
				if( !bPtInRectFont )
				{
					FrameRect( hdc, &rcFont, hbrush );
					bPtInRectFont = 1;
				}
			}
			else
			{
				if( bPtInRect )
				{
					HDC hdcMem = CreateCompatibleDC(hdc);
					HBITMAP hbm = LoadBitmap( g_hInst, MAKEINTRESOURCE(IDB_TOP) );
					HBITMAP oldbmp = SelectObject( hdcMem, hbm );
					BitBlt(hdc, 70, 0, 70, 34, hdcMem, 70, 0, SRCCOPY); 
					SelectObject(hdcMem, oldbmp);
					DeleteObject(hbm);
					DeleteDC(hdcMem);
					bPtInRect = 0;
				}
				else if( bPtInRectFont )
				{
					HDC hdcMem = CreateCompatibleDC(hdc);
					HBITMAP hbm = LoadBitmap( g_hInst, MAKEINTRESOURCE(IDB_FONT) );
					HBITMAP oldbmp = SelectObject( hdcMem, hbm );
					BitBlt(hdc, rcFont.left, rcFont.top, 20, 20, hdcMem, 0, 0, SRCCOPY); 
					SelectObject(hdcMem, oldbmp);
					DeleteObject(hbm);
					DeleteDC(hdcMem);
					bPtInRectFont = 0;
				}
			}
			break;
		}
	case WM_LBUTTONDOWN:
		{
			RECT rc, rcFont;
			POINT pos, posFont;
			pos.x = LOWORD(lParam);
			pos.y = HIWORD(lParam);
			rc.left = 70;
			rc.top = 0;
			rc.right = 140;
			rc.bottom = 34;

			GetWindowRect( GetDlgItem(hwndDlg, IDOK), &rcFont );
			posFont.x = rcFont.right;
			posFont.y = rcFont.top;
			ScreenToClient( hwndDlg, &posFont );
			rcFont.left = posFont.x - 20;
			rcFont.right = posFont.x;
			rcFont.top = posFont.y - 20;
			rcFont.bottom = posFont.y;

			if( PtInRect( &rc, pos ) )
			{
				CallService(MS_FILE_SENDFILE, (WPARAM)dat->hContact, (LPARAM) (HWND) NULL);
			}
			else if( PtInRect( &rcFont, pos ) )
			{
				CHOOSEFONT cf = { 0 };
				LOGFONT lf = { 0 };
				COLORREF cr;
				LoadMsgDlgFont( 0, &lf, &cr );

				cf.lStructSize = sizeof(cf);
				cf.hwndOwner = hwndDlg;
				cf.lpLogFont = &lf;
				cf.rgbColors = cr;
				cf.Flags = CF_FORCEFONTEXIST | CF_INITTOLOGFONTSTRUCT | CF_SCREENFONTS | CF_EFFECTS;
				if (ChooseFont(&cf)) 
				{
                    char str[32];
					int i = 0;
					for( ; i < 7; i++ )
					{
						if( i == 4 )
							continue;
						wsprintfA(str, "SRMFont%d", i);
						DBWriteContactSettingTString(NULL, SRMMMOD, str, lf.lfFaceName);
						wsprintfA(str, "SRMFont%dSize", i);
						DBWriteContactSettingByte(NULL, SRMMMOD, str, (char)lf.lfHeight);
						wsprintfA(str, "SRMFont%dSty", i);
						DBWriteContactSettingByte(NULL, SRMMMOD, str, (lf.lfWeight >= FW_BOLD ? 1 : 0) | (lf.lfItalic ? 2 : 0));
						wsprintfA(str, "SRMFont%dSet", i);
						DBWriteContactSettingByte(NULL, SRMMMOD, str, lf.lfCharSet);
						wsprintfA(str, "SRMFont%dCol", i);
						if( i < 2 )
                            DBWriteContactSettingDword(NULL, SRMMMOD, str, cf.rgbColors);
						else
							DBWriteContactSettingDword(NULL, SRMMMOD, str, RGB(0, 128, 128));
					}
				}		
			}
			break;
		}
	}
	return FALSE;
}
