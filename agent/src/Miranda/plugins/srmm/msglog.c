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
#include <ctype.h>
#include <malloc.h>
#include <mbstring.h>

extern HINSTANCE g_hInst;

static int logPixelSY;
#define LOGICON_MSG_IN      0
#define LOGICON_MSG_OUT     1
#define LOGICON_MSG_NOTICE  2
static PBYTE pLogIconBmpBits[3];
static int logIconBmpSize[ SIZEOF(pLogIconBmpBits) ];
static HIMAGELIST g_hImageList;

#define STREAMSTAGE_HEADER  0
#define STREAMSTAGE_EVENTS  1
#define STREAMSTAGE_TAIL    2
#define STREAMSTAGE_STOP    3
struct LogStreamData
{
	int stage;
	HANDLE hContact;
	HANDLE hDbEvent, hDbEventLast;
	char *buffer;
	int bufferOffset, bufferLen;
	int eventsToInsert;
	int isEmpty;
	struct MessageWindowData *dlgDat;
};

static char szSep2[40], szSep2_RTL[50];

static void AppendToBuffer(char **buffer, int *cbBufferEnd, int *cbBufferAlloced, const char *fmt, ...)
{
	va_list va;
	int charsDone;

	va_start(va, fmt);
	for (;;) {
		charsDone = mir_vsnprintf(*buffer + *cbBufferEnd, *cbBufferAlloced - *cbBufferEnd, fmt, va);
		if (charsDone >= 0)
			break;
		*cbBufferAlloced += 1024;
		*buffer = (char *) realloc(*buffer, *cbBufferAlloced);
	}
	va_end(va);
	*cbBufferEnd += charsDone;
}

static int AppendToBufferWithRTF(char **buffer, int *cbBufferEnd, int *cbBufferAlloced, TCHAR* line)
{
	DWORD textCharsCount = 0;
	char *d;

	int lineLen = _tcslen(line) * 9 + 8;
	if (*cbBufferEnd + lineLen > *cbBufferAlloced) {
		cbBufferAlloced[0] += (lineLen + 1024 - lineLen % 1024);
		*buffer = (char *) realloc(*buffer, *cbBufferAlloced);
	}

	d = *buffer + *cbBufferEnd;
	strcpy(d, "{\\uc1 ");
	d += 6;

	for (; *line; line++, textCharsCount++) {
		if (*line == '\r' && line[1] == '\n') {
			CopyMemory(d, "\\par ", 5);
			line++;
			d += 5;
		}
		else if (*line == '\n') {
			CopyMemory(d, "\\par ", 5);
			d += 5;
		}
		else if (*line == '\t') {
			CopyMemory(d, "\\tab ", 5);
			d += 5;
		}
		else if (*line == '\\' || *line == '{' || *line == '}') {
			*d++ = '\\';
			*d++ = (char) *line;
		}
		else if (*line < 128) {
			*d++ = (char) *line;
		}
		else d += sprintf(d, "\\u%d ?", *line);
	}

	strcpy(d, "}");
	d++;

	*cbBufferEnd = (int) (d - *buffer);
	return textCharsCount;
}

#if defined( _UNICODE )
	#define FONT_FORMAT "{\\f%u\\fnil\\fcharset%u %S;}"
#else
	#define FONT_FORMAT "{\\f%u\\fnil\\fcharset%u %s;}"
#endif

static char *CreateRTFHeader(struct MessageWindowData *dat)
{
	char *buffer;
	int bufferAlloced, bufferEnd;
	int i;
	LOGFONT lf;
	COLORREF colour;
	HDC hdc;

	hdc = GetDC(NULL);
	logPixelSY = GetDeviceCaps(hdc, LOGPIXELSY);
	ReleaseDC(NULL, hdc);
	bufferEnd = 0;
	bufferAlloced = 1024;
	buffer = (char *) malloc(bufferAlloced);
	buffer[0] = '\0';
	AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "{\\rtf1\\ansi\\deff0{\\fonttbl");

	for (i = 0; i < msgDlgFontCount; i++) {
		LoadMsgDlgFont(i, &lf, NULL);
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, FONT_FORMAT, i, lf.lfCharSet, lf.lfFaceName);
	}
	AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "}{\\colortbl ");
	for (i = 0; i < msgDlgFontCount; i++) {
		LoadMsgDlgFont(i, NULL, &colour);
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\red%u\\green%u\\blue%u;", GetRValue(colour), GetGValue(colour), GetBValue(colour));
	}
	if (GetSysColorBrush(COLOR_HOTLIGHT) == NULL)
		colour = RGB(0, 0, 255);
	else
		colour = GetSysColor(COLOR_HOTLIGHT);
	AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\red%u\\green%u\\blue%u;", GetRValue(colour), GetGValue(colour), GetBValue(colour));
	AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "}");
	//AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "}\\pard");
	return buffer;
}

//free() the return value
static char *CreateRTFTail(struct MessageWindowData *dat)
{
	char *buffer;
	int bufferAlloced, bufferEnd;

	bufferEnd = 0;
	bufferAlloced = 1024;
	buffer = (char *) malloc(bufferAlloced);
	buffer[0] = '\0';
	AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "}");
	return buffer;
}

//return value is static
static char *SetToStyle(int style)
{
	static char szStyle[128];
	LOGFONT lf;

	LoadMsgDlgFont(style, &lf, NULL);
	wsprintfA(szStyle, "\\f%u\\cf%u\\b%d\\i%d\\fs%u", style, style, lf.lfWeight >= FW_BOLD ? 1 : 0, lf.lfItalic, 2 * abs(lf.lfHeight) * 74 / logPixelSY);
	return szStyle;
}

int DbEventIsShown(DBEVENTINFO * dbei, struct MessageWindowData *dat)
{
	switch (dbei->eventType) {
		case EVENTTYPE_MESSAGE:
			return 1;
		case EVENTTYPE_STATUSCHANGE:
		case EVENTTYPE_FILE:
			if (dbei->flags & DBEF_READ)
				return 0;
			return 1;
	}
	return 0;
}

//free() the return value
static char *CreateRTFFromDbEvent(struct MessageWindowData *dat, HANDLE hContact, HANDLE hDbEvent, struct LogStreamData *streamData)
{
	char *buffer;
	int bufferAlloced, bufferEnd;
	DBEVENTINFO dbei = { 0 };
	int showColon = 0;

	dbei.cbSize = sizeof(dbei);
	dbei.cbBlob = CallService(MS_DB_EVENT_GETBLOBSIZE, (WPARAM) hDbEvent, 0);
	if (dbei.cbBlob == -1)
		return NULL;
	dbei.pBlob = (PBYTE) malloc(dbei.cbBlob);
	CallService(MS_DB_EVENT_GET, (WPARAM) hDbEvent, (LPARAM) & dbei);
	if (!DbEventIsShown(&dbei, dat)) {
		free(dbei.pBlob);
		return NULL;
	}
	if (!(dbei.flags & DBEF_SENT) && dbei.eventType == EVENTTYPE_MESSAGE) {
		CallService(MS_DB_EVENT_MARKREAD, (WPARAM) hContact, (LPARAM) hDbEvent);
		CallService(MS_CLIST_REMOVEEVENT, (WPARAM) hContact, (LPARAM) hDbEvent);
	}
	else if (dbei.eventType == EVENTTYPE_STATUSCHANGE) {
		CallService(MS_DB_EVENT_MARKREAD, (WPARAM) hContact, (LPARAM) hDbEvent);
	}
	bufferEnd = 0;
	bufferAlloced = 1024;
	buffer = (char *) malloc(bufferAlloced);
	buffer[0] = '\0';

	if (!dat->bIsAutoRTL && !streamData->isEmpty)
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\par");

	if (dbei.flags & DBEF_RTL) {
        AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\rtlpar");
		dat->bIsAutoRTL = TRUE;
	}
    else
        AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\ltrpar");

	streamData->isEmpty = 0;

	if (dat->bIsAutoRTL) {
		if(dbei.flags & DBEF_RTL) {
			AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\ltrch\\rtlch");
		}else{
			AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\rtlch\\ltrch");
		}
	}

	if (g_dat->flags&SMF_SHOWICONS) {
		int i;

		switch (dbei.eventType) {
			case EVENTTYPE_MESSAGE:
				if (dbei.flags & DBEF_SENT) {
					i = LOGICON_MSG_OUT;
				}
				else {
					i = LOGICON_MSG_IN;
				}
				break;
			case EVENTTYPE_STATUSCHANGE:
			case EVENTTYPE_FILE:
				i = LOGICON_MSG_NOTICE;
				break;
		}
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\f0\\fs14");
		while (bufferAlloced - bufferEnd < logIconBmpSize[i])
			bufferAlloced += 1024;
		buffer = (char *) realloc(buffer, bufferAlloced);
		CopyMemory(buffer + bufferEnd, pLogIconBmpBits[i], logIconBmpSize[i]);
		bufferEnd += logIconBmpSize[i];
	}
	if (g_dat->flags&SMF_SHOWTIME) {
		DBTIMETOSTRINGT dbtts;
		TCHAR str[64];
		
		if (g_dat->flags&SMF_SHOWSECS) 
			dbtts.szFormat = g_dat->flags&SMF_SHOWDATE ? _T("d s") : _T("s");
		else
			dbtts.szFormat = g_dat->flags&SMF_SHOWDATE ? _T("d t") : _T("t");
		dbtts.cbDest = SIZEOF(str);
		dbtts.szDest = str;
		CallService(MS_DB_TIME_TIMESTAMPTOSTRINGT, dbei.timestamp, (LPARAM)&dbtts);
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, " %s ", SetToStyle(dbei.flags & DBEF_SENT ? MSGFONTID_MYTIME : MSGFONTID_YOURTIME));
		AppendToBufferWithRTF(&buffer, &bufferEnd, &bufferAlloced, str);
		showColon = 1;
	}
	if (!(g_dat->flags&SMF_HIDENAMES) && dbei.eventType != EVENTTYPE_STATUSCHANGE) {
		TCHAR* szName;
		CONTACTINFO ci;
		ZeroMemory(&ci, sizeof(ci));

		if (dbei.flags & DBEF_SENT) {
			ci.cbSize = sizeof(ci);
			ci.hContact = NULL;
			ci.szProto = dbei.szModule;
			ci.dwFlag = CNF_DISPLAY;
			#if defined( _UNICODE )
				ci.dwFlag += CNF_UNICODE;
			#endif
			if (!CallService(MS_CONTACT_GETCONTACTINFO, 0, (LPARAM) & ci)) {
				// CNF_DISPLAY always returns a string type
				szName = ( TCHAR* )ci.pszVal;
			}
		}
		else szName = ( TCHAR* ) CallService(MS_CLIST_GETCONTACTDISPLAYNAME, (WPARAM) hContact, GCDNF_TCHAR);

		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, " %s ", SetToStyle(dbei.flags & DBEF_SENT ? MSGFONTID_MYNAME : MSGFONTID_YOURNAME));
		AppendToBufferWithRTF(&buffer, &bufferEnd, &bufferAlloced, szName);
		showColon = 1;
		if (ci.pszVal)
			mir_free(ci.pszVal);
	}

	if (showColon)
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "%s :", SetToStyle(dbei.flags & DBEF_SENT ? MSGFONTID_MYCOLON : MSGFONTID_YOURCOLON));

	switch (dbei.eventType) {
		case EVENTTYPE_MESSAGE:
		{
			TCHAR* msg;

			AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, " %s ", SetToStyle(dbei.flags & DBEF_SENT ? MSGFONTID_MYMSG : MSGFONTID_YOURMSG));

			#if defined( _UNICODE )
			{
				int msglen = strlen((char *) dbei.pBlob) + 1;
				if (msglen != (int) dbei.cbBlob)
					msg = (TCHAR *) & dbei.pBlob[msglen];
				else {
					msg = (TCHAR *) alloca(sizeof(TCHAR) * msglen);
					MultiByteToWideChar(CP_ACP, 0, (char *) dbei.pBlob, -1, msg, msglen);
				}
			}
			#else
				msg = (BYTE *) dbei.pBlob;
			#endif
			AppendToBufferWithRTF(&buffer, &bufferEnd, &bufferAlloced, msg);
			break;
		}
		case EVENTTYPE_STATUSCHANGE:
		{
			TCHAR *msg, *szName;
			CONTACTINFO ci;
			ZeroMemory(&ci, sizeof(ci));

			if (dbei.flags & DBEF_SENT) {
				ci.cbSize = sizeof(ci);
				ci.hContact = NULL;
				ci.szProto = dbei.szModule;
				ci.dwFlag = CNF_DISPLAY;
				#if defined( _UNICODE )
					ci.dwFlag += CNF_UNICODE;
				#endif
				if (!CallService(MS_CONTACT_GETCONTACTINFO, 0, (LPARAM) & ci)) {
					// CNF_DISPLAY always returns a string type
					szName = ( TCHAR* )ci.pszVal;
				}
			}
			else szName = ( TCHAR* )CallService(MS_CLIST_GETCONTACTDISPLAYNAME, (WPARAM) hContact, GCDNF_TCHAR);

			AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, " %s ", SetToStyle(MSGFONTID_NOTICE));
			AppendToBufferWithRTF(&buffer, &bufferEnd, &bufferAlloced, szName);
			AppendToBufferWithRTF(&buffer, &bufferEnd, &bufferAlloced, _T(" "));
			#if defined( _UNICODE )
			{
				int msglen = strlen((char *) dbei.pBlob) + 1;
				msg = ( TCHAR* )alloca(sizeof(TCHAR) * msglen);
				MultiByteToWideChar(CP_ACP, 0, (char *) dbei.pBlob, -1, msg, msglen);
			}
			#else
				msg = (BYTE *) dbei.pBlob;
			#endif
			AppendToBufferWithRTF(&buffer, &bufferEnd, &bufferAlloced, msg);
			if (ci.pszVal)
				mir_free(ci.pszVal);
			break;
		}
		case EVENTTYPE_FILE:
		{
			char* filename = dbei.pBlob + sizeof(DWORD);
			char* descr = filename + lstrlenA( filename ) + 1;
			AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, " %s ", SetToStyle(MSGFONTID_NOTICE));
         if ( *descr != 0 )
				AppendToBuffer( &buffer, &bufferEnd, &bufferAlloced, "%s (%s)", filename, descr );
			else
				AppendToBuffer( &buffer, &bufferEnd, &bufferAlloced, "%s", filename );
			break;
		}
	}
	if(dat->bIsAutoRTL)
		AppendToBuffer(&buffer, &bufferEnd, &bufferAlloced, "\\par");

	dat->lastEventType = dbei.flags;
	free(dbei.pBlob);
	return buffer;
}

static DWORD CALLBACK LogStreamInEvents(DWORD_PTR dwCookie, LPBYTE pbBuff, LONG cb, LONG * pcb)
{
	struct LogStreamData *dat = (struct LogStreamData *) dwCookie;

	if (dat->buffer == NULL) {
		dat->bufferOffset = 0;
		switch (dat->stage) {
			case STREAMSTAGE_HEADER:
				dat->buffer = CreateRTFHeader(dat->dlgDat);
				dat->stage = STREAMSTAGE_EVENTS;
				break;
			case STREAMSTAGE_EVENTS:
				if (dat->eventsToInsert) {
					do {
						dat->buffer = CreateRTFFromDbEvent(dat->dlgDat, dat->hContact, dat->hDbEvent, dat);
						if (dat->buffer)
							dat->hDbEventLast = dat->hDbEvent;
						dat->hDbEvent = (HANDLE) CallService(MS_DB_EVENT_FINDNEXT, (WPARAM) dat->hDbEvent, 0);
						if (--dat->eventsToInsert == 0)
							break;
					} while (dat->buffer == NULL && dat->hDbEvent);
					if (dat->buffer) {
						dat->isEmpty = 0;
						break;
					}
				}
				dat->stage = STREAMSTAGE_TAIL;
				//fall through
			case STREAMSTAGE_TAIL:
				dat->buffer = CreateRTFTail(dat->dlgDat);
				dat->stage = STREAMSTAGE_STOP;
				break;
			case STREAMSTAGE_STOP:
				*pcb = 0;
				return 0;
		}
		dat->bufferLen = lstrlenA(dat->buffer);
	}
	*pcb = min(cb, dat->bufferLen - dat->bufferOffset);
	CopyMemory(pbBuff, dat->buffer + dat->bufferOffset, *pcb);
	dat->bufferOffset += *pcb;
	if (dat->bufferOffset == dat->bufferLen) {
		free(dat->buffer);
		dat->buffer = NULL;
	}
	return 0;
}

void StreamInEvents(HWND hwndDlg, HANDLE hDbEventFirst, int count, int fAppend)
{
	EDITSTREAM stream = { 0 };
	struct LogStreamData streamData = { 0 };
	struct MessageWindowData *dat = (struct MessageWindowData *) GetWindowLong(hwndDlg, GWL_USERDATA);
	CHARRANGE oldSel, sel;

	SendDlgItemMessage(hwndDlg, IDC_LOG, EM_HIDESELECTION, TRUE, 0);
	SendDlgItemMessage(hwndDlg, IDC_LOG, EM_EXGETSEL, 0, (LPARAM) & oldSel);
	streamData.hContact = dat->hContact;
	streamData.hDbEvent = hDbEventFirst;
	streamData.dlgDat = dat;
	streamData.eventsToInsert = count;
	streamData.isEmpty = fAppend ? GetWindowTextLength(GetDlgItem(hwndDlg, IDC_LOG)) == 0 : 1;
	stream.pfnCallback = LogStreamInEvents;
	stream.dwCookie = (DWORD_PTR) & streamData;
	if (fAppend) {
		sel.cpMin = sel.cpMax = GetWindowTextLength(GetDlgItem(hwndDlg, IDC_LOG));
		SendDlgItemMessage(hwndDlg, IDC_LOG, EM_EXSETSEL, 0, (LPARAM) & sel);
	}
	else dat->bIsFirstAppend = TRUE;

	strcpy(szSep2, fAppend ? "\\par\\sl0" : "\\sl1000");
	strcpy(szSep2_RTL, fAppend ? "\\rtlpar\\rtlmark\\par\\sl1000" : "\\sl1000");

	SendDlgItemMessage(hwndDlg, IDC_LOG, EM_STREAMIN, fAppend ? SFF_SELECTION | SF_RTF : SFF_SELECTION | SF_RTF, (LPARAM) & stream);
	SendDlgItemMessage(hwndDlg, IDC_LOG, EM_EXSETSEL, 0, (LPARAM) & oldSel);
	SendDlgItemMessage(hwndDlg, IDC_LOG, EM_HIDESELECTION, FALSE, 0);
	dat->hDbEventLast = streamData.hDbEventLast;
	if (GetWindowLong(GetDlgItem(hwndDlg, IDC_LOG), GWL_STYLE) & WS_VSCROLL)
		PostMessage(hwndDlg, DM_SCROLLLOGTOBOTTOM, 0, 0);
}

#define RTFPICTHEADERMAXSIZE   78
void LoadMsgLogIcons(void)
{
	HICON hIcon;
	HBITMAP hBmp, hoBmp;
	HDC hdc, hdcMem;
	BITMAPINFOHEADER bih = { 0 };
	int widthBytes, i;
	RECT rc;
	HBRUSH hBkgBrush;
	int rtfHeaderSize;
	PBYTE pBmpBits;

	g_hImageList = ImageList_Create(10, 10, IsWinVerXPPlus()? ILC_COLOR32 | ILC_MASK : ILC_COLOR8 | ILC_MASK, SIZEOF(pLogIconBmpBits), 0);
	hBkgBrush = CreateSolidBrush(DBGetContactSettingDword(NULL, SRMMMOD, SRMSGSET_BKGCOLOUR, SRMSGDEFSET_BKGCOLOUR));
	bih.biSize = sizeof(bih);
	bih.biBitCount = 24;
	bih.biCompression = BI_RGB;
	bih.biHeight = 10;
	bih.biPlanes = 1;
	bih.biWidth = 10;
	widthBytes = ((bih.biWidth * bih.biBitCount + 31) >> 5) * 4;
	rc.top = rc.left = 0;
	rc.right = bih.biWidth;
	rc.bottom = bih.biHeight;
	hdc = GetDC(NULL);
	hBmp = CreateCompatibleBitmap(hdc, bih.biWidth, bih.biHeight);
	hdcMem = CreateCompatibleDC(hdc);
	pBmpBits = (PBYTE) malloc(widthBytes * bih.biHeight);
	for (i = 0; i < SIZEOF(pLogIconBmpBits); i++) {
		switch (i) {
			case LOGICON_MSG_IN:
				hIcon = LoadIcon(g_hInst, MAKEINTRESOURCE(IDI_INCOMING));
				ImageList_AddIcon(g_hImageList, hIcon);
				hIcon = ImageList_GetIcon(g_hImageList, LOGICON_MSG_IN, ILD_NORMAL);
				break;
			case LOGICON_MSG_OUT:
				hIcon = LoadIcon(g_hInst, MAKEINTRESOURCE(IDI_OUTGOING));
				ImageList_AddIcon(g_hImageList, hIcon);
				hIcon = ImageList_GetIcon(g_hImageList, LOGICON_MSG_OUT, ILD_NORMAL);
				break;
			case LOGICON_MSG_NOTICE:
				hIcon = LoadIcon(g_hInst, MAKEINTRESOURCE(IDI_NOTICE));
				ImageList_AddIcon(g_hImageList, hIcon);
				hIcon = ImageList_GetIcon(g_hImageList, LOGICON_MSG_NOTICE, ILD_NORMAL);
				break;
		}
		pLogIconBmpBits[i] = (PBYTE) malloc(RTFPICTHEADERMAXSIZE + (bih.biSize + widthBytes * bih.biHeight) * 2);
		//I can't seem to get binary mode working. No matter.
		rtfHeaderSize = sprintf(pLogIconBmpBits[i], "{\\pict\\dibitmap0\\wbmbitspixel%u\\wbmplanes1\\wbmwidthbytes%u\\picw%u\\pich%u ", bih.biBitCount, widthBytes, bih.biWidth, bih.biHeight);
		hoBmp = (HBITMAP) SelectObject(hdcMem, hBmp);
		FillRect(hdcMem, &rc, hBkgBrush);
		DrawIconEx(hdcMem, 0, 0, hIcon, bih.biWidth, bih.biHeight, 0, NULL, DI_NORMAL);
		SelectObject(hdcMem, hoBmp);
		GetDIBits(hdc, hBmp, 0, bih.biHeight, pBmpBits, (BITMAPINFO *) & bih, DIB_RGB_COLORS);
		DestroyIcon(hIcon);
		{
			int n;
			for (n = 0; n < sizeof(BITMAPINFOHEADER); n++)
				sprintf(pLogIconBmpBits[i] + rtfHeaderSize + n * 2, "%02X", ((PBYTE) & bih)[n]);
			for (n = 0; n < widthBytes * bih.biHeight; n += 4)
				sprintf(pLogIconBmpBits[i] + rtfHeaderSize + (bih.biSize + n) * 2, "%02X%02X%02X%02X", pBmpBits[n], pBmpBits[n + 1], pBmpBits[n + 2], pBmpBits[n + 3]);
		}
		logIconBmpSize[i] = rtfHeaderSize + (bih.biSize + widthBytes * bih.biHeight) * 2 + 1;
		pLogIconBmpBits[i][logIconBmpSize[i] - 1] = '}';
	}
	free(pBmpBits);
	DeleteDC(hdcMem);
	DeleteObject(hBmp);
	ReleaseDC(NULL, hdc);
	DeleteObject(hBkgBrush);
}

void FreeMsgLogIcons(void)
{
	int i;
	for (i = 0; i < SIZEOF(pLogIconBmpBits); i++)
		free(pLogIconBmpBits[i]);
	ImageList_RemoveAll(g_hImageList);
	ImageList_Destroy(g_hImageList);
}
