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

static HCURSOR hHandCursor;

struct HyperlinkWndData {
	HFONT hFont;
	HFONT hSetFont;
	RECT rcText;
	DWORD enableColor;
	DWORD disableColor;
};
#define IM_MEASURETEXT (WM_USER+1)
#define HLK_INVALIDATE (WM_USER+2)
static LRESULT CALLBACK HyperlinkWndProc(HWND hwnd,UINT message,WPARAM wParam,LPARAM lParam)
{
	struct HyperlinkWndData *dat;
	dat=(struct HyperlinkWndData*)GetWindowLong(hwnd,0);
	switch(message) {
		case WM_CREATE:
			dat=(struct HyperlinkWndData*)mir_alloc(sizeof(struct HyperlinkWndData));
			SetWindowLong(hwnd,0,(LONG)dat);
			dat->hFont=NULL;
			dat->hSetFont=NULL;
			dat->enableColor=GetSysColor(COLOR_HOTLIGHT);
			if(dat->enableColor==0 && GetLastError()!=ERROR_SUCCESS) dat->enableColor=RGB(0,0,255);
			dat->disableColor=GetSysColor(COLOR_GRAYTEXT);
			SendMessage(hwnd,IM_MEASURETEXT,0,0);
			break;
		case WM_LBUTTONDOWN:
		{	POINT pt;
			pt.x=(short)LOWORD(lParam); pt.y=(short)HIWORD(lParam);
			if(PtInRect(&dat->rcText,pt))
				SendMessage(GetParent(hwnd),WM_COMMAND,MAKEWPARAM(GetDlgCtrlID(hwnd),STN_CLICKED),(LPARAM)hwnd);
			break;
		}
		case WM_SETFONT:
		{	LOGFONT lf;
			dat->hSetFont=(HFONT)wParam;
			GetObject(dat->hSetFont,sizeof(lf),&lf);
			lf.lfUnderline=1;
			dat->hFont=CreateFontIndirect(&lf);
			if(LOWORD(lParam)) SendMessage(hwnd,HLK_INVALIDATE,0,0);
			SendMessage(hwnd,IM_MEASURETEXT,0,0);
			break;
		}
		case WM_ERASEBKGND: return(1); break;
		case HLK_INVALIDATE: // invalidate
		{
			RECT rcWnd;
			POINT pt;
			GetWindowRect(hwnd,&rcWnd);
			pt.x = rcWnd.left;
			pt.y = rcWnd.top;
			ScreenToClient(GetParent(hwnd),&pt);
			rcWnd.right = pt.x + (rcWnd.right-rcWnd.left);
			rcWnd.bottom = pt.y + (rcWnd.bottom-rcWnd.top);
			rcWnd.left = pt.x;
			rcWnd.top = pt.y;

			InvalidateRect(GetParent(hwnd),&rcWnd,TRUE);
			break;
		}
		case WM_GETFONT:
			return (LRESULT)dat->hSetFont;
		case IM_MEASURETEXT:
		{	char text[256];
			GetWindowTextA(hwnd,text,SIZEOF(text));
			lParam=(LPARAM)text;
			//fall thru
		case WM_SETTEXT:
		{	HFONT hoFont;
			SIZE textSize;
			RECT rc;

			HDC hdc1=GetDC(hwnd);
			if(dat->hFont!=NULL) hoFont=(HFONT)SelectObject(hdc1,dat->hFont);
			GetTextExtentPoint32(hdc1,(const TCHAR*)lParam,lstrlen((const TCHAR*)lParam),&textSize);
			dat->rcText.top=0; dat->rcText.bottom=dat->rcText.top+textSize.cy;
			GetClientRect(hwnd,&rc);
			if(GetWindowLong(hwnd,GWL_STYLE)&SS_CENTER) dat->rcText.left=(rc.right-textSize.cx)/2;
			else if(GetWindowLong(hwnd,GWL_STYLE)&SS_RIGHT) dat->rcText.left=rc.right-textSize.cx;
			else dat->rcText.left=0;
			dat->rcText.right=dat->rcText.left+textSize.cx;
			if(dat->hFont!=NULL) SelectObject(hdc1,hoFont);
			ReleaseDC(hwnd,hdc1);
			SendMessage(hwnd,HLK_INVALIDATE,0,0);
			break;
		}}
		case WM_SETCURSOR:
		{	POINT pt;
			GetCursorPos(&pt);
			ScreenToClient(hwnd,&pt);
			if(PtInRect(&dat->rcText,pt)) SetCursor(hHandCursor);
			else SetCursor(LoadCursor(NULL,IDC_ARROW));
			return TRUE;
		}
		case HLK_SETENABLECOLOUR:
			if ((DWORD)wParam) dat->enableColor = (DWORD)wParam;
			break;
		case HLK_SETDISABLECOLOUR:
			if ((DWORD)wParam) dat->disableColor = (DWORD)wParam;
			break;
		case WM_NCPAINT:
		case WM_PAINT:
		{	
			HFONT hoFont;
			RECT rc;
			TCHAR text[256];
			int alignFlag;
			DWORD textColour;

			PAINTSTRUCT ps;
			HDC hdc1=BeginPaint(hwnd,&ps);
			if(IsWindowEnabled(hwnd)) {
				hoFont=(HFONT)SelectObject(hdc1,dat->hFont);
				textColour=dat->enableColor;
			}
			else {
				hoFont=(HFONT)SelectObject(hdc1,dat->hSetFont);
				textColour=dat->disableColor;
			}
			SetTextColor(hdc1,textColour);
			SetBkMode(hdc1,TRANSPARENT);
			if(GetWindowLong(hwnd,GWL_STYLE)&SS_CENTER) alignFlag=DT_CENTER;
			else if(GetWindowLong(hwnd,GWL_STYLE)&SS_RIGHT) alignFlag=DT_RIGHT;
			else alignFlag=DT_LEFT;
			GetClientRect(hwnd,&rc);
			GetWindowText(hwnd,text,SIZEOF(text));
			DrawText(hdc1,text,-1,&rc,alignFlag|DT_NOPREFIX|DT_SINGLELINE|DT_TOP);
			SelectObject(hdc1,hoFont);
			EndPaint(hwnd,&ps);
			break;
		}
		case WM_DESTROY:			
			if(dat->hFont!=NULL) DeleteObject(dat->hFont);
			mir_free(dat); dat=NULL;			
			SetWindowLong(hwnd,0,(LONG)dat);
			break;
	}
	return DefWindowProc(hwnd,message,wParam,lParam);
}

int InitHyperlink(void)
{
	WNDCLASS wcl;

	if(IsWinVer2000Plus()) hHandCursor=LoadCursor(NULL,IDC_HAND);
	else hHandCursor=LoadCursor(GetModuleHandle(NULL),MAKEINTRESOURCE(IDC_HYPERLINKHAND));
	wcl.lpfnWndProc=HyperlinkWndProc;
	wcl.cbClsExtra=0;
	wcl.cbWndExtra=sizeof(void*);
	wcl.hInstance=GetModuleHandle(NULL);
	wcl.hCursor=NULL;
	wcl.lpszClassName=WNDCLASS_HYPERLINK;
	wcl.hbrBackground=NULL;
	wcl.hIcon=NULL;
	wcl.lpszMenuName=NULL;
	wcl.style=CS_HREDRAW|CS_VREDRAW|CS_GLOBALCLASS|CS_PARENTDC;
	RegisterClass(&wcl);
	return 0;
}
