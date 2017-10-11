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
// TODO: Remove this
#include <m_icq.h>
#include "findadd.h"

#define TIMERID_THROBBER   111

#define HM_SEARCHACK  (WM_USER+10)
#define M_SETGROUPVISIBILITIES  (WM_USER+11)

static HWND hwndFindAdd=NULL;
static HANDLE hHookModulesLoaded = 0;
static int OnSystemModulesLoaded(WPARAM wParam,LPARAM lParam);

void ListView_SetItemTextA( HWND hwndLV, int i, int iSubItem, char* pszText )
{
	LV_ITEMA _ms_lvi;
	_ms_lvi.iSubItem = iSubItem;
	_ms_lvi.pszText = pszText;
	SendMessageA( hwndLV, LVM_SETITEMTEXTA, i, (LPARAM)&_ms_lvi);
}

// from msn_libstr.cpp
static char* FindAddTrimR(char *s) {
	char* p = s+strlen(s)-1;

	while (p>=s) {  
		if (*p!=' '&&*p!='\t'&&*p!='\n'&&*p!='\r')
			break;
		*p--=0;
   }
   return s;
}

static int FindAddDlgResizer(HWND hwndDlg,LPARAM lParam,UTILRESIZECONTROL *urc)
{
	static int y,nextY,oldTop;
	struct FindAddDlgData *dat;

	dat=(struct FindAddDlgData*)lParam;
	switch(urc->wId) {
		case IDC_RESULTS:
			return RD_ANCHORX_WIDTH|RD_ANCHORY_HEIGHT;
		case IDOK:
			dat->minDlgHeight=nextY+urc->rcItem.bottom-urc->rcItem.top;
			return RD_ANCHORX_LEFT|RD_ANCHORY_BOTTOM;
		case IDC_ADD:
		case IDC_MOREOPTIONS:
			return RD_ANCHORX_RIGHT|RD_ANCHORY_BOTTOM;
		case IDC_STATUSBAR:
			return RD_ANCHORX_WIDTH|RD_ANCHORY_BOTTOM;
		case IDC_PROTOIDGROUP:	//the resize is always processed in template order
			nextY=y=urc->rcItem.top;
			if(dat->showProtoId) nextY=y+urc->rcItem.bottom-urc->rcItem.top+7;
			break;
		case IDC_EMAILGROUP:
			oldTop=urc->rcItem.top;
			y=nextY;
			if(dat->showEmail) nextY=y+urc->rcItem.bottom-urc->rcItem.top+7;
			OffsetRect(&urc->rcItem,0,y-oldTop);
			return RD_ANCHORX_LEFT|RD_ANCHORY_CUSTOM;
		case IDC_NAMEGROUP:
			oldTop=urc->rcItem.top;
			y=nextY;
			if(dat->showName) nextY=y+urc->rcItem.bottom-urc->rcItem.top+7;
			OffsetRect(&urc->rcItem,0,y-oldTop);
			return RD_ANCHORX_LEFT|RD_ANCHORY_CUSTOM;
		case IDC_ADVANCEDGROUP:
			oldTop=urc->rcItem.top;
			y=nextY;
			if(dat->showAdvanced) nextY=y+urc->rcItem.bottom-urc->rcItem.top+7;
			OffsetRect(&urc->rcItem,0,y-oldTop);
			return RD_ANCHORX_LEFT|RD_ANCHORY_CUSTOM;
		case IDC_BYEMAIL:
		case IDC_EMAIL:
		case IDC_BYNAME:
		case IDC_STNAMENICK:
		case IDC_STNAMEFIRST:
		case IDC_STNAMELAST:
		case IDC_NAMENICK:
		case IDC_NAMEFIRST:
		case IDC_NAMELAST:
		case IDC_BYADVANCED:
		case IDC_ADVANCED:
			OffsetRect(&urc->rcItem,0,y-oldTop);
			return RD_ANCHORX_LEFT|RD_ANCHORY_CUSTOM;
	}
	return RD_ANCHORX_LEFT|RD_ANCHORY_TOP;
}

static void RenderThrobber(HDC hdc,RECT *rcItem,int *throbbing,int *pivot)
{
	HBRUSH hBr;
	HDC hMemDC;
	HBITMAP hBitmap;
	HPEN hPen;
	RECT rc;
	int x,width,height,height2;

	InflateRect(rcItem,-1,0);
	width=rcItem->right-rcItem->left;
	height=rcItem->bottom-rcItem->top;
	height2=height/2;

	if (*throbbing) 
	{
		/* create memdc */
		hMemDC=CreateCompatibleDC(0);
		hBitmap=SelectObject(hMemDC, CreateCompatibleBitmap(hdc,width,height));
		/* flush it */
		rc.left=rc.top=0;
		rc.right=width;
		rc.bottom=height;
		hBr=GetSysColorBrush(COLOR_BTNFACE);
		FillRect(hMemDC,&rc,hBr);
		DeleteObject(hBr);
		/* set up the pen */
		hPen=SelectObject(hMemDC,CreatePen(PS_SOLID,4,GetSysColor(COLOR_BTNSHADOW)));
		/* draw everything before the pivot */
		x=*pivot;
		while (x>(-height))
		{
			MoveToEx(hMemDC,x+height2,0,NULL);
			LineTo(hMemDC,x-height2,height);
			x-=12;
		} //while
		/* draw everything after the pivot */
		x=*pivot;
		while (x < width+height)
		{
			MoveToEx(hMemDC,x+height2,0,NULL);
			LineTo(hMemDC,x-height2,height);
			x+=12;
		} //while
		/* move the pivot */
		*pivot+=2;
		/* reset the pivot point if it gets past the rect */
		if (*pivot>width) *pivot=0;
		/* put back the old pen and delete the new one */
		DeleteObject(SelectObject(hMemDC,hPen));
		/* cap the top and bottom */
		hPen=SelectObject(hMemDC,CreatePen(PS_SOLID,1,GetSysColor(COLOR_BTNFACE)));
		MoveToEx(hMemDC,0,0,NULL);
		LineTo(hMemDC,width,0);
		MoveToEx(hMemDC,0,height-1,NULL);
		LineTo(hMemDC,width,height-1);
		/* select in the old pen and delete the new pen */
		DeleteObject(SelectObject(hMemDC,hPen));
		/* paint to screen */
		BitBlt(hdc,rcItem->left,rcItem->top,width,height,hMemDC,0,0,SRCCOPY);
		/* select back in the old bitmap and delete the created one, as well as freeing the mem dc. */
		hBitmap=SelectObject(hMemDC,hBitmap);
		DeleteObject(hBitmap);
		DeleteDC(hMemDC);
	} else {
		/* just flush the DC */
		hBr=GetSysColorBrush(COLOR_BTNFACE);
		FillRect(hdc,rcItem,hBr);
		DeleteObject(hBr);
	} //if
}

static void StartThrobber(HWND hwndDlg,struct FindAddDlgData *dat)
{
	dat->throbbing=1;
	SetTimer(hwndDlg,TIMERID_THROBBER,25,NULL);
}

static void StopThrobber(HWND hwndDlg,struct FindAddDlgData *dat)
{
	KillTimer(hwndDlg,TIMERID_THROBBER);
	dat->throbbing=0;
	dat->pivot=0;
	InvalidateRect(GetDlgItem(hwndDlg,IDC_STATUSBAR),NULL,FALSE);
}

static void ShowAdvancedSearchDlg(HWND hwndDlg,struct FindAddDlgData *dat)
{
	char *szProto=(char*)SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCURSEL,0,0),0);
	BOOL (WINAPI *MyAnimateWindow)(HWND hWnd,DWORD dwTime,DWORD dwFlags);

	if(szProto==NULL) return;
	if(dat->hwndAdvSearch==NULL) {
		RECT rc;
		dat->hwndAdvSearch=(HWND)CallProtoService(szProto,PS_CREATEADVSEARCHUI,0,(LPARAM)hwndDlg);
		GetWindowRect(GetDlgItem(hwndDlg,IDC_RESULTS),&rc);
		SetWindowPos(dat->hwndAdvSearch,0,rc.left,rc.top,0,0,SWP_NOZORDER|SWP_NOSIZE);
	}
	MyAnimateWindow=(BOOL (WINAPI*)(HWND,DWORD,DWORD))GetProcAddress(GetModuleHandleA("USER32"),"AnimateWindow");
	if(MyAnimateWindow) {
		MyAnimateWindow(dat->hwndAdvSearch,150,AW_ACTIVATE|AW_SLIDE|AW_HOR_POSITIVE);
		RedrawWindow(dat->hwndAdvSearch,NULL,NULL,RDW_INVALIDATE|RDW_UPDATENOW|RDW_ALLCHILDREN);
	} else ShowWindow(dat->hwndAdvSearch,SW_SHOW);
	CheckDlgButton(hwndDlg,IDC_ADVANCED,BST_CHECKED);
}

static void HideAdvancedSearchDlg(HWND hwndDlg,struct FindAddDlgData *dat)
{
	BOOL (WINAPI *MyAnimateWindow)(HWND hWnd,DWORD dwTime,DWORD dwFlags);

	if(dat->hwndAdvSearch==NULL) return;
	MyAnimateWindow=(BOOL (WINAPI*)(HWND,DWORD,DWORD))GetProcAddress(GetModuleHandleA("USER32"),"AnimateWindow");
	if(MyAnimateWindow && IsWinVerXPPlus())  //blending is quite slow on win2k
		MyAnimateWindow(dat->hwndAdvSearch,150,AW_HIDE|AW_BLEND);
	else ShowWindow(dat->hwndAdvSearch,SW_HIDE);
	CheckDlgButton(hwndDlg,IDC_ADVANCED,BST_UNCHECKED);
}

void EnableResultButtons(HWND hwndDlg,int enable)
{
	struct FindAddDlgData *dat;

	dat=(struct FindAddDlgData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	EnableWindow(GetDlgItem(hwndDlg,IDC_ADD),enable);
	EnableWindow(GetDlgItem(hwndDlg,IDC_MOREOPTIONS),enable);
}

static void CheckSearchTypeRadioButton(HWND hwndDlg,int idControl)
{
	int i;
	int controls[]={IDC_BYPROTOID,IDC_BYEMAIL,IDC_BYNAME,IDC_BYADVANCED};
	for( i=0; i < SIZEOF(controls); i++ )
		CheckDlgButton(hwndDlg,controls[i],idControl==controls[i]?BST_CHECKED:BST_UNCHECKED);
}

#define sttErrMsg TranslateT("You haven't filled in the search field. Please enter a search term and try again.")
#define sttErrTitle TranslateT("Search")

static void SetListItemText( HWND hwndDlg, int idx, int col, char* szText )
{
	if ( lstrlenA( szText ))
		ListView_SetItemTextA( GetDlgItem(hwndDlg,IDC_RESULTS), idx, col, szText );
	else
		ListView_SetItemText( GetDlgItem(hwndDlg,IDC_RESULTS), idx, col, TranslateT("<not specified>"));
}

static BOOL CALLBACK DlgProcFindAdd(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct FindAddDlgData *dat;

	dat=(struct FindAddDlgData*)GetWindowLong(hwndDlg,GWL_USERDATA);

	switch (msg)
	{
		case WM_INITDIALOG:
		{	int protoCount,i,netProtoCount;
			PROTOCOLDESCRIPTOR **protos;
			COMBOBOXEXITEM cbei;
			char szProtoName[64];
			HICON hIcon;

			TranslateDialogDefault(hwndDlg);
			SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_FINDUSER)));
			ListView_SetExtendedListViewStyle(GetDlgItem(hwndDlg,IDC_RESULTS),LVS_EX_FULLROWSELECT|LVS_EX_HEADERDRAGDROP);
			dat=(struct FindAddDlgData*)mir_alloc(sizeof(struct FindAddDlgData));
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
			dat->hResultHook=NULL;
			dat->notSearchedYet=1;
			dat->search=NULL;
			dat->searchCount=0;
			dat->iLastColumnSortIndex=1;
			dat->bSortAscending=1;
			dat->hBmpSortUp=(HBITMAP)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDB_SORTCOLUP),IMAGE_BITMAP,0,0,LR_LOADMAP3DCOLORS);
			dat->hBmpSortDown=(HBITMAP)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDB_SORTCOLDOWN),IMAGE_BITMAP,0,0,LR_LOADMAP3DCOLORS);
			dat->throbbing=0;
			dat->pivot=0;
			dat->hwndAdvSearch=NULL;
			SendDlgItemMessage(hwndDlg,IDC_MOREOPTIONS,BUTTONSETARROW,1,0);

			{	LVCOLUMN lvc;
				RECT rc;
				LVITEM lvi;

				GetClientRect(GetDlgItem(hwndDlg,IDC_RESULTS),&rc);
				lvc.mask = LVCF_TEXT | LVCF_WIDTH;
				lvc.pszText = TranslateT("Results");
				lvc.cx = rc.right-1;
				ListView_InsertColumn(GetDlgItem(hwndDlg,IDC_RESULTS), 0, &lvc);
				lvi.mask=LVIF_TEXT;
				lvi.iItem=0;
				lvi.iSubItem=0;
				lvi.pszText=TranslateT("There are no results to display.");
				ListView_InsertItem(GetDlgItem(hwndDlg,IDC_RESULTS), &lvi);
			}

			// Allocate a reasonable amount of space in the status bar
			{	int partWidth[3];
				SIZE textSize;
				HDC hdc;

				hdc=GetDC(GetDlgItem(hwndDlg,IDC_STATUSBAR));
				SelectObject(hdc,(HFONT)SendDlgItemMessage(hwndDlg,IDC_STATUSBAR,WM_GETFONT,0,0));
				GetTextExtentPoint32(hdc,TranslateT("Searching"),lstrlen(TranslateT("Searching")),&textSize);
				partWidth[0]=textSize.cx;
				GetTextExtentPoint32(hdc,_T("01234567890123456789"), 20, &textSize );
				partWidth[0]+=textSize.cx;
				ReleaseDC(GetDlgItem(hwndDlg,IDC_STATUSBAR),hdc);
				partWidth[1]=partWidth[0]+150;
				partWidth[2]=-1;
				SendDlgItemMessage(hwndDlg,IDC_STATUSBAR,SB_SETPARTS,SIZEOF(partWidth),(LPARAM)partWidth);
				SendDlgItemMessage(hwndDlg,IDC_STATUSBAR,SB_SETTEXT,1|SBT_OWNERDRAW,0);
				SetStatusBarSearchInfo(GetDlgItem(hwndDlg,IDC_STATUSBAR),dat);
			}
			{
				char *szProto = NULL;
				int index = 0;
				DBVARIANT dbv={0};
				HDC hdc;
				SIZE textSize;
				RECT rect;
				int cbwidth = 0;
				DWORD caps;

				if(!DBGetContactSetting(NULL, "FindAdd", "LastSearched", &dbv))
					szProto=(char*)dbv.pszVal;
				CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protos);
				for(i=0,netProtoCount=0;i<protoCount;i++) {
					if(protos[i]->type!=PROTOTYPE_PROTOCOL) continue;
					caps=(DWORD)CallProtoService(protos[i]->szName,PS_GETCAPS,PFLAGNUM_1,0);
					if (caps&PF1_BASICSEARCH || caps&PF1_EXTSEARCH || caps&PF1_SEARCHBYEMAIL || caps&PF1_SEARCHBYNAME)
						netProtoCount++;
				}
				dat->himlComboIcons=ImageList_Create(GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),(IsWinVerXPPlus()?ILC_COLOR32:ILC_COLOR16)|ILC_MASK,netProtoCount+1,netProtoCount+1);
				SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CBEM_SETIMAGELIST,0,(LPARAM)dat->himlComboIcons);
				cbei.mask=CBEIF_IMAGE|CBEIF_SELECTEDIMAGE|CBEIF_TEXT|CBEIF_LPARAM;
				cbei.iItem=0;
				hdc=GetDC(hwndDlg);
				SelectObject(hdc,(HFONT)SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,WM_GETFONT,0,0));
				if(netProtoCount>1) {
					cbei.pszText=TranslateT("All Networks");
					GetTextExtentPoint32(hdc,cbei.pszText,lstrlen(cbei.pszText),&textSize);
					if (textSize.cx>cbwidth) cbwidth = textSize.cx;
					cbei.iImage=cbei.iSelectedImage=ImageList_AddIcon(dat->himlComboIcons,LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_SEARCHALL)));
					cbei.lParam=0;
					SendDlgItemMessageA(hwndDlg,IDC_PROTOLIST,CBEM_INSERTITEM,0,(LPARAM)&cbei);
					cbei.iItem++;
				}
				for(i=0;i<protoCount;i++) {
					if(protos[i]->type!=PROTOTYPE_PROTOCOL) continue;
					caps=(DWORD)CallProtoService(protos[i]->szName,PS_GETCAPS,PFLAGNUM_1,0);
					if (!(caps&PF1_BASICSEARCH) && !(caps&PF1_EXTSEARCH) && !(caps&PF1_SEARCHBYEMAIL) && !(caps&PF1_SEARCHBYNAME))
						continue;
					CallProtoService(protos[i]->szName,PS_GETNAME,SIZEOF(szProtoName),(LPARAM)szProtoName);
					#if !defined( _UNICODE )
						cbei.pszText=(char*)szProtoName;
					#else
					{	TCHAR wszProtoName[ 64 ];
						MultiByteToWideChar( CP_ACP, 0, szProtoName, 64, wszProtoName, 64 );
						cbei.pszText = wszProtoName;
					}
					#endif
					GetTextExtentPoint32(hdc,cbei.pszText,lstrlen(cbei.pszText),&textSize);
					if (textSize.cx>cbwidth) cbwidth = textSize.cx;
					hIcon=(HICON)CallProtoService(protos[i]->szName,PS_LOADICON,PLI_PROTOCOL|PLIF_SMALL,0);
					cbei.iImage=cbei.iSelectedImage=ImageList_AddIcon(dat->himlComboIcons,hIcon);
					DestroyIcon(hIcon);
					cbei.lParam=(LPARAM)protos[i]->szName;
					SendDlgItemMessageA(hwndDlg,IDC_PROTOLIST,CBEM_INSERTITEM,0,(LPARAM)&cbei);
					if (szProto && cbei.pszText && !lstrcmpA(szProto,szProtoName)) index=cbei.iItem;
					cbei.iItem++;
				}
				cbwidth+=32;
				SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETDROPPEDCONTROLRECT,0,(LPARAM)&rect);
				if ((rect.right-rect.left)<cbwidth)
					SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_SETDROPPEDWIDTH,cbwidth,0);
				SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_SETCURSEL,index,0);
				DBFreeVariant(&dbv); /* free string szProto was fetched with */
			}
			SendMessage(hwndDlg,M_SETGROUPVISIBILITIES,0,0);
			Utils_RestoreWindowPosition(hwndDlg,NULL,"FindAdd","");			
			return TRUE;
		}
		case WM_SIZE:
		{	UTILRESIZEDIALOG urd={0};
			urd.cbSize=sizeof(urd);
			urd.hwndDlg=hwndDlg;
			urd.hInstance=GetModuleHandle(NULL);
			urd.lpTemplate=MAKEINTRESOURCEA(IDD_FINDADD);
			urd.lParam=(LPARAM)dat;
			urd.pfnResizer=FindAddDlgResizer;
			CallService(MS_UTILS_RESIZEDIALOG,0,(LPARAM)&urd);
			SendDlgItemMessage(hwndDlg,IDC_STATUSBAR,WM_SIZE,0,0);
			if(dat->notSearchedYet) {
				RECT rc;
				GetClientRect(GetDlgItem(hwndDlg,IDC_RESULTS),&rc);
				ListView_SetColumnWidth(GetDlgItem(hwndDlg,IDC_RESULTS),0,rc.right);
			}
		}
			//fall through
		case WM_MOVE:
		{	RECT rc;
			if(dat->hwndAdvSearch==NULL) break;
			GetWindowRect(GetDlgItem(hwndDlg,IDC_RESULTS),&rc);
			SetWindowPos(dat->hwndAdvSearch,0,rc.left,rc.top,0,0,SWP_NOZORDER|SWP_NOSIZE);
			break;
		}
		case WM_GETMINMAXINFO:
		{	MINMAXINFO *mmi=(MINMAXINFO*)lParam;
			RECT rc,rc2;
			GetWindowRect(GetDlgItem(hwndDlg,IDC_RESULTS),&rc);
			GetWindowRect(hwndDlg,&rc2);
			mmi->ptMinTrackSize.x=rc.left-rc2.left+10+GetSystemMetrics(SM_CXFRAME);
			GetClientRect(GetDlgItem(hwndDlg,IDC_MOREOPTIONS),&rc);
			mmi->ptMinTrackSize.x+=rc.right+5;
			GetClientRect(GetDlgItem(hwndDlg,IDC_ADD),&rc);
			mmi->ptMinTrackSize.x+=rc.right+5;
			GetClientRect(GetDlgItem(hwndDlg,IDC_STATUSBAR),&rc);
			mmi->ptMinTrackSize.y=dat->minDlgHeight+20+GetSystemMetrics(SM_CYCAPTION)+2*GetSystemMetrics(SM_CYFRAME);
			GetClientRect(GetDlgItem(hwndDlg,IDC_STATUSBAR),&rc);
			mmi->ptMinTrackSize.y+=rc.bottom;
			return 0;
		}
		case M_SETGROUPVISIBILITIES:
		{	char *szProto;
			int protoCount,i;
			PROTOCOLDESCRIPTOR **protos;
			DWORD protoCaps;
			MINMAXINFO mmi;
			RECT rc;
			int checkmarkVisible;

			dat->showAdvanced=dat->showEmail=dat->showName=dat->showProtoId=0;
			szProto=(char*)SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCURSEL,0,0),0);
			if ( szProto == (char *)CB_ERR ) break;
			if(szProto==NULL) {
				CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protos);
				for(i=0;i<protoCount;i++) {
					if(protos[i]->type!=PROTOTYPE_PROTOCOL) continue;
					protoCaps=(DWORD)CallProtoService(protos[i]->szName,PS_GETCAPS,PFLAGNUM_1,0);
					if(protoCaps&PF1_SEARCHBYEMAIL) dat->showEmail=1;
					if(protoCaps&PF1_SEARCHBYNAME) dat->showName=1;
				}
			}
			else {
				protoCaps=(DWORD)CallProtoService(szProto,PS_GETCAPS,PFLAGNUM_1,0);
				if(protoCaps&PF1_BASICSEARCH) dat->showProtoId=1;
				if(protoCaps&PF1_SEARCHBYEMAIL) dat->showEmail=1;
				if(protoCaps&PF1_SEARCHBYNAME) dat->showName=1;
				if(protoCaps&PF1_EXTSEARCHUI) dat->showAdvanced=1;
				if(protoCaps&PF1_USERIDISEMAIL && dat->showProtoId) {dat->showProtoId=0; dat->showEmail=1;}
				if(dat->showProtoId) {
					char *szUniqueId;
					szUniqueId=(char*)CallProtoService(szProto,PS_GETCAPS,PFLAG_UNIQUEIDTEXT,0);
					if(szUniqueId) {
						#if defined( _UNICODE )
							TCHAR* p = a2u(szUniqueId);
							SetDlgItemText(hwndDlg,IDC_BYPROTOID,p);
							mir_free(p);
						#else
							SetDlgItemTextA(hwndDlg,IDC_BYPROTOID,szUniqueId);
						#endif
					}
					else SetDlgItemText(hwndDlg,IDC_BYPROTOID,TranslateT("Handle"));
					if(protoCaps&PF1_NUMERICUSERID) SetWindowLong(GetDlgItem(hwndDlg,IDC_PROTOID),GWL_STYLE,GetWindowLong(GetDlgItem(hwndDlg,IDC_PROTOID),GWL_STYLE)|ES_NUMBER);
					else SetWindowLong(GetDlgItem(hwndDlg,IDC_PROTOID),GWL_STYLE,GetWindowLong(GetDlgItem(hwndDlg,IDC_PROTOID),GWL_STYLE)&~ES_NUMBER);
				}
			}
#define en(id,t) ShowWindow(GetDlgItem(hwndDlg,IDC_##id),dat->show##t?SW_SHOW:SW_HIDE)
			en(PROTOIDGROUP,ProtoId); en(BYPROTOID,ProtoId); en(PROTOID,ProtoId);
			en(EMAILGROUP,Email); en(BYEMAIL,Email); en(EMAIL,Email);
			en(NAMEGROUP,Name);  en(BYNAME,Name);
			en(STNAMENICK,Name); en(NAMENICK,Name);
			en(STNAMEFIRST,Name); en(NAMEFIRST,Name);
			en(STNAMELAST,Name); en(NAMELAST,Name);
			en(ADVANCEDGROUP,Advanced); en(BYADVANCED,Advanced); en(ADVANCED,Advanced);
#undef en
			checkmarkVisible=(dat->showAdvanced && IsDlgButtonChecked(hwndDlg,IDC_BYADVANCED)) ||
			                 (dat->showEmail && IsDlgButtonChecked(hwndDlg,IDC_BYEMAIL)) ||
							 (dat->showName && IsDlgButtonChecked(hwndDlg,IDC_BYNAME)) ||
							 (dat->showProtoId && IsDlgButtonChecked(hwndDlg,IDC_BYPROTOID));
			if(!checkmarkVisible) {
				if(dat->showProtoId) CheckSearchTypeRadioButton(hwndDlg,IDC_BYPROTOID);
				else if(dat->showEmail) CheckSearchTypeRadioButton(hwndDlg,IDC_BYEMAIL);
				else if(dat->showName) CheckSearchTypeRadioButton(hwndDlg,IDC_BYNAME);
				else if(dat->showAdvanced) CheckSearchTypeRadioButton(hwndDlg,IDC_BYADVANCED);
			}
			SendMessage(hwndDlg,WM_SIZE,0,0);
			SendMessage(hwndDlg,WM_GETMINMAXINFO,0,(LPARAM)&mmi);
			GetWindowRect(hwndDlg,&rc);
			if(rc.bottom-rc.top<mmi.ptMinTrackSize.y) SetWindowPos(hwndDlg,0,0,0,rc.right-rc.left,mmi.ptMinTrackSize.y,SWP_NOZORDER|SWP_NOMOVE);
			break;
		}
		case WM_TIMER:
			if(wParam==TIMERID_THROBBER) {
				RECT rc;
				HDC hdc;
				int borders[3];
				SendDlgItemMessage(hwndDlg,IDC_STATUSBAR,SB_GETBORDERS,0,(LPARAM)borders);
				SendDlgItemMessage(hwndDlg,IDC_STATUSBAR,SB_GETRECT,1,(LPARAM)&rc);
				InflateRect(&rc,-borders[2]/2,-borders[1]/2);
				hdc=GetDC(GetDlgItem(hwndDlg,IDC_STATUSBAR));
				RenderThrobber(hdc,&rc,&dat->throbbing,&dat->pivot);
				ReleaseDC(GetDlgItem(hwndDlg,IDC_STATUSBAR),hdc);
			}
			break;
		case WM_DRAWITEM:
		{	DRAWITEMSTRUCT *dis=(DRAWITEMSTRUCT*)lParam;
			if(dis->CtlID==IDC_STATUSBAR && dis->itemID==1) {
				RenderThrobber(dis->hDC,&dis->rcItem,&dat->throbbing,&dat->pivot);
				return TRUE;
			}
			break;
		}
		case WM_NOTIFY:
			switch(wParam) 
			{
				case IDC_RESULTS:
					switch(((LPNMHDR)lParam)->code) {
						case LVN_ITEMCHANGED:
						{	int count=ListView_GetSelectedCount(GetDlgItem(hwndDlg,IDC_RESULTS));
							if(dat->notSearchedYet) count=0;
							EnableResultButtons(hwndDlg,count);
							break;
						}
						case LVN_COLUMNCLICK:
						{
							LPNMLISTVIEW nmlv=(LPNMLISTVIEW)lParam;
							HDITEM hdi;

							hdi.mask=HDI_BITMAP|HDI_FORMAT;
							hdi.fmt=HDF_LEFT|HDF_STRING;
							Header_SetItem(ListView_GetHeader(GetDlgItem(hwndDlg,IDC_RESULTS)),dat->iLastColumnSortIndex,&hdi);

							if(nmlv->iSubItem!=dat->iLastColumnSortIndex)
							{
								dat->bSortAscending=TRUE;
								dat->iLastColumnSortIndex=nmlv->iSubItem;
							}
							else dat->bSortAscending=!dat->bSortAscending;

							hdi.fmt=HDF_LEFT|HDF_BITMAP|HDF_STRING|HDF_BITMAP_ON_RIGHT;
							hdi.hbm=dat->bSortAscending?dat->hBmpSortDown:dat->hBmpSortUp;
							Header_SetItem(ListView_GetHeader(GetDlgItem(hwndDlg,IDC_RESULTS)),dat->iLastColumnSortIndex,&hdi);

							ListView_SortItems(GetDlgItem(hwndDlg, IDC_RESULTS), SearchResultsCompareFunc, (LPARAM)dat);
							break;
						}
					}
					break;
			}
			break;
		case WM_COMMAND:
			switch(LOWORD(wParam)) {
				case IDC_PROTOLIST:
					if(HIWORD(wParam)==CBN_SELCHANGE) {
						HideAdvancedSearchDlg(hwndDlg,dat);
						if(dat->hwndAdvSearch) {
							DestroyWindow(dat->hwndAdvSearch);
							dat->hwndAdvSearch=NULL;
						}
						SendMessage(hwndDlg,M_SETGROUPVISIBILITIES,0,0);
					}
					break;
				case IDC_BYPROTOID:
				case IDC_BYEMAIL:
				case IDC_BYNAME:
					HideAdvancedSearchDlg(hwndDlg,dat);
					break;
				case IDC_PROTOID:
					if(HIWORD(wParam)==EN_CHANGE) {
						HideAdvancedSearchDlg(hwndDlg,dat);
						CheckSearchTypeRadioButton(hwndDlg,IDC_BYPROTOID);
					}
					break;
				case IDC_EMAIL:
					if(HIWORD(wParam)==EN_CHANGE) {
						HideAdvancedSearchDlg(hwndDlg,dat);
						CheckSearchTypeRadioButton(hwndDlg,IDC_BYEMAIL);
					}
					break;
				case IDC_NAMENICK:
				case IDC_NAMEFIRST:
				case IDC_NAMELAST:
					if(HIWORD(wParam)==EN_CHANGE) {
						HideAdvancedSearchDlg(hwndDlg,dat);
						CheckSearchTypeRadioButton(hwndDlg,IDC_BYNAME);
					}
					break;
				case IDC_ADVANCED:
					if(IsDlgButtonChecked(hwndDlg,IDC_ADVANCED))
						ShowAdvancedSearchDlg(hwndDlg,dat);
					else	
						HideAdvancedSearchDlg(hwndDlg,dat);
					CheckSearchTypeRadioButton(hwndDlg,IDC_BYADVANCED);
					break;
				case IDCANCEL:
					DestroyWindow(hwndDlg);
					break;
				case IDOK:
				{	char *szProto;

					HideAdvancedSearchDlg(hwndDlg,dat);
					if(dat->searchCount) {	 //cancel search
						SetDlgItemText(hwndDlg,IDOK,TranslateT("&Search"));
						if(dat->hResultHook) {UnhookEvent(dat->hResultHook); dat->hResultHook=NULL;}
						if(dat->search) {mir_free(dat->search); dat->search=NULL;}
						dat->searchCount=0;
						StopThrobber(hwndDlg,dat);
						SetStatusBarSearchInfo(GetDlgItem(hwndDlg,IDC_STATUSBAR),dat);
						break;
					}
					szProto=(char*)SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCURSEL,0,0),0);
					if(dat->search) {mir_free(dat->search); dat->search=NULL;}
					dat->searchCount=0;
					dat->hResultHook=HookEventMessage(ME_PROTO_ACK,hwndDlg,HM_SEARCHACK);
					if(IsDlgButtonChecked(hwndDlg,IDC_BYPROTOID)) {
						char str[256];
						GetDlgItemTextA(hwndDlg,IDC_PROTOID,str,SIZEOF(str));
						FindAddTrimR(str);
						if(str[0]==0)
							MessageBox(hwndDlg,sttErrMsg,sttErrTitle,MB_OK);
						else
							BeginSearch(hwndDlg,dat,szProto,PS_BASICSEARCH,PF1_BASICSEARCH,str);
					}
					else if(IsDlgButtonChecked(hwndDlg,IDC_BYEMAIL)) {
						char str[256];
						GetDlgItemTextA(hwndDlg,IDC_EMAIL,str,SIZEOF(str));
						FindAddTrimR(str);
						if(str[0]==0)
							MessageBox(hwndDlg,sttErrMsg,sttErrTitle,MB_OK);
						else
							BeginSearch(hwndDlg,dat,szProto,PS_SEARCHBYEMAIL,PF1_SEARCHBYEMAIL,str);
					}
					else if(IsDlgButtonChecked(hwndDlg,IDC_BYNAME)) {
						char nick[256],first[256],last[256];
						PROTOSEARCHBYNAME psbn;
						GetDlgItemTextA(hwndDlg,IDC_NAMENICK,nick,SIZEOF(nick));
						GetDlgItemTextA(hwndDlg,IDC_NAMEFIRST,first,SIZEOF(first));
						GetDlgItemTextA(hwndDlg,IDC_NAMELAST,last,SIZEOF(last));
						psbn.pszFirstName=first;
						psbn.pszLastName=last;
						psbn.pszNick=nick;
						if(nick[0]==0 && first[0]==0 && last[0]==0)
							MessageBox(hwndDlg,sttErrMsg,sttErrTitle,MB_OK);
						else
							BeginSearch(hwndDlg,dat,szProto,PS_SEARCHBYNAME,PF1_SEARCHBYNAME,&psbn);
					}
					else if(IsDlgButtonChecked(hwndDlg,IDC_BYADVANCED)) {
						if(dat->hwndAdvSearch==NULL)
							MessageBox(hwndDlg,sttErrMsg,sttErrTitle,MB_OK);
						else
							BeginSearch(hwndDlg,dat,szProto,PS_SEARCHBYADVANCED,PF1_EXTSEARCHUI,dat->hwndAdvSearch);
					}

					if(dat->searchCount==0) {
						if(dat->hResultHook) {UnhookEvent(dat->hResultHook); dat->hResultHook=NULL;}
						break;
					}

					dat->notSearchedYet=0;
					FreeSearchResults(GetDlgItem(hwndDlg,IDC_RESULTS));

					CreateResultsColumns(GetDlgItem(hwndDlg,IDC_RESULTS),dat,szProto);
					SetStatusBarSearchInfo(GetDlgItem(hwndDlg,IDC_STATUSBAR),dat);
					SetStatusBarResultInfo(hwndDlg,dat);
					StartThrobber(hwndDlg,dat);
					SetDlgItemText(hwndDlg, IDOK, TranslateT("Cancel"));
					break;
				}
				case IDC_ADD:
				{	LVITEM lvi;
					struct ListSearchResult *lsr;
					ADDCONTACTSTRUCT acs;

					if(ListView_GetSelectedCount(GetDlgItem(hwndDlg,IDC_RESULTS))!=1) break;
					lvi.mask=LVIF_PARAM;
					lvi.iItem=ListView_GetNextItem(GetDlgItem(hwndDlg,IDC_RESULTS),-1,LVNI_ALL|LVNI_SELECTED);
					ListView_GetItem(GetDlgItem(hwndDlg,IDC_RESULTS),&lvi);
					lsr=(struct ListSearchResult*)lvi.lParam;

					acs.handle=NULL;
					acs.handleType=HANDLE_SEARCHRESULT;
					acs.szProto=lsr->szProto;
					acs.psr=&lsr->psr;
					CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
					break;
				}
				case IDC_MOREOPTIONS:
				{	RECT rc;
					GetWindowRect(GetDlgItem(hwndDlg,IDC_MOREOPTIONS),&rc);
					ShowMoreOptionsMenu(hwndDlg,rc.left,rc.bottom);
					break;
				}
			}
			break;
		case WM_CONTEXTMENU:
		{	POINT pt;
			LVHITTESTINFO lvhti;

			pt.x=(short)LOWORD(lParam); pt.y=(short)HIWORD(lParam);
			lvhti.pt=pt;
			ScreenToClient(hwndDlg,&pt);
			switch(GetDlgCtrlID(ChildWindowFromPoint(hwndDlg,pt))) {
				case IDC_RESULTS:
					if(dat->notSearchedYet) return TRUE;
					ScreenToClient(GetDlgItem(hwndDlg,IDC_RESULTS),&lvhti.pt);
					if(ListView_HitTest(GetDlgItem(hwndDlg,IDC_RESULTS),&lvhti)==-1) break;
					ShowMoreOptionsMenu(hwndDlg,(short)LOWORD(lParam),(short)HIWORD(lParam));
					return TRUE;
			}
			break;
		}
		case HM_SEARCHACK:
		{	ACKDATA *ack=(ACKDATA*)lParam;
			int i;

			if(ack->type!=ACKTYPE_SEARCH) break;
			for(i=0;i<dat->searchCount;i++)
				if(dat->search[i].hProcess==ack->hProcess && dat->search[i].hProcess != NULL && !lstrcmpA(dat->search[i].szProto,ack->szModule)) break;
			if(i==dat->searchCount) break;
			if(ack->result==ACKRESULT_SUCCESS) {
				dat->searchCount--;
				memmove(dat->search+i,dat->search+i+1,sizeof(struct ProtoSearchInfo)*(dat->searchCount-i));
				if(dat->searchCount==0) {
					mir_free(dat->search);
					dat->search=NULL;
					UnhookEvent(dat->hResultHook);
					dat->hResultHook=NULL;
					SetDlgItemText(hwndDlg, IDOK, TranslateT("&Search"));
					StopThrobber(hwndDlg,dat);
				}
				SetStatusBarSearchInfo(GetDlgItem(hwndDlg,IDC_STATUSBAR),dat);
			}
			else if(ack->result==ACKRESULT_DATA) {
				LVITEM lvi={0};
				int i,col;
				PROTOSEARCHRESULT *psr=(PROTOSEARCHRESULT*)ack->lParam;
				struct ListSearchResult *lsr;
				char *szComboProto;
				COMBOBOXEXITEM cbei={0};

				lsr=(struct ListSearchResult*)mir_alloc(offsetof(struct ListSearchResult,psr)+psr->cbSize);
				lsr->szProto=ack->szModule;
				CopyMemory(&lsr->psr,psr,psr->cbSize);
				lsr->psr.email=psr->email==NULL?NULL:mir_strdup(psr->email);
				lsr->psr.nick=psr->nick==NULL?NULL:mir_strdup(psr->nick);
				lsr->psr.firstName=psr->firstName==NULL?NULL:mir_strdup(psr->firstName);
				lsr->psr.lastName=psr->lastName==NULL?NULL:mir_strdup(psr->lastName);
				lvi.mask = LVIF_PARAM|LVIF_IMAGE;
				lvi.lParam=(LPARAM)lsr;
				for(i=SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCOUNT,0,0)-1;i>=0;i--) {
					szComboProto=(char*)SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETITEMDATA,i,0);
					if(szComboProto==NULL) continue;
					if(!lstrcmpA(szComboProto,ack->szModule)) {
						cbei.mask=CBEIF_IMAGE;
						cbei.iItem=i;
						SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CBEM_GETITEM,0,(LPARAM)&cbei);
						lvi.iImage=cbei.iImage;
					}
				}
				i=ListView_InsertItem(GetDlgItem(hwndDlg,IDC_RESULTS), &lvi);
				col=1;
				SetListItemText(hwndDlg, i, col++, psr->nick );
				SetListItemText(hwndDlg, i, col++, psr->firstName );
				SetListItemText(hwndDlg, i, col++, psr->lastName );
				SetListItemText(hwndDlg, i, col++, psr->email );
				if(!lstrcmpA(ack->szModule,"ICQ")) {
					char str[15];
					ICQSEARCHRESULT *isr=(ICQSEARCHRESULT*)psr;
					wsprintfA(str, "%u", isr->uin);
					ListView_SetItemTextA(GetDlgItem(hwndDlg,IDC_RESULTS),i,col++,str);
				}
				ListView_SortItems(GetDlgItem(hwndDlg, IDC_RESULTS), SearchResultsCompareFunc, (LPARAM)dat);
				SetStatusBarResultInfo(hwndDlg,dat);
			}
			break;
		}
		case WM_CLOSE:
			DestroyWindow(hwndDlg);
			break;
		case WM_DESTROY:		
			{
				TCHAR *szProto;
				int len = SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETLBTEXTLEN,SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCURSEL,0,0),0);
				szProto = ( TCHAR* )alloca( sizeof(TCHAR)*( len+1 ));
				*szProto='\0';
				SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETLBTEXT,SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCURSEL,0,0),(LPARAM)szProto);
				DBWriteContactSettingTString(NULL, "FindAdd", "LastSearched", szProto?szProto:_T(""));
			}			
			SaveColumnSizes(GetDlgItem(hwndDlg,IDC_RESULTS));
			if(dat->hResultHook!=NULL) UnhookEvent(dat->hResultHook);
			FreeSearchResults(GetDlgItem(hwndDlg,IDC_RESULTS));
			ImageList_Destroy(dat->himlComboIcons);
			if(dat->search) mir_free(dat->search);
			if(dat->hwndAdvSearch) {
				DestroyWindow(dat->hwndAdvSearch);
				dat->hwndAdvSearch=NULL;
			}
			DeleteObject(dat->hBmpSortDown);
			DeleteObject(dat->hBmpSortUp);
			mir_free(dat);			
			Utils_SaveWindowPosition(hwndDlg,NULL,"FindAdd","");
			break;
	}
	return FALSE;
}

static int FindAddCommand(WPARAM wParam,LPARAM lParam)
{
	if(IsWindow(hwndFindAdd)) {
		ShowWindow(hwndFindAdd,SW_SHOWNORMAL);
		SetForegroundWindow(hwndFindAdd);
		SetFocus(hwndFindAdd);
	}
	else {
		INITCOMMONCONTROLSEX icce={0};
		int netProtoCount, protoCount, i;
		PROTOCOLDESCRIPTOR **protos;

		// Make sure we have some networks to search on. This is not ideal since
		// this check will be repeated every time the dialog is requested, but it
		// must be done since this service can be called from other places than the menu.
		// One alternative would be to only create the service if we have network
		// protocols loaded but that would delay the creation until MODULE_LOADED and
		// that is not good either...
		CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protos);
		for(i=0,netProtoCount=0;i<protoCount;i++)
			if(protos[i]->type==PROTOTYPE_PROTOCOL) { 
				int protoCaps=CallProtoService(protos[i]->szName, PS_GETCAPS, PFLAGNUM_1, 0);
				if ( protoCaps&PF1_BASICSEARCH || protoCaps&PF1_SEARCHBYEMAIL || protoCaps&PF1_SEARCHBYNAME 
					|| protoCaps&PF1_EXTSEARCHUI ) netProtoCount++;
			}
		if (netProtoCount > 0) {
			icce.dwSize=sizeof(icce);
			icce.dwICC=ICC_USEREX_CLASSES;
			InitCommonControlsEx(&icce);
			hwndFindAdd=CreateDialog(GetModuleHandle(NULL), MAKEINTRESOURCE(IDD_FINDADD), NULL, DlgProcFindAdd);
		}
	}
	return 0;
}

int FindAddPreShutdown(WPARAM wParam, LPARAM lParam)
{
	if (IsWindow(hwndFindAdd)) DestroyWindow(hwndFindAdd);
	hwndFindAdd=NULL;
	return 0;
}

int LoadFindAddModule(void)
{

	CreateServiceFunction(MS_FINDADD_FINDADD,FindAddCommand);
	HookEvent(ME_SYSTEM_MODULESLOADED, OnSystemModulesLoaded);
	HookEvent(ME_SYSTEM_PRESHUTDOWN,FindAddPreShutdown);

	return 0;
}

static int OnSystemModulesLoaded(WPARAM wParam,LPARAM lParam)
{
	CLISTMENUITEM mi;
	int netProtoCount, protoCount, i;
	PROTOCOLDESCRIPTOR **protos;

	// Make sure we have some networks to search on.
	CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protos);
	for(i=0,netProtoCount=0;i<protoCount;i++)
		if(protos[i]->type==PROTOTYPE_PROTOCOL) {
			int protoCaps=CallProtoService(protos[i]->szName, PS_GETCAPS, PFLAGNUM_1, 0);
			if ( protoCaps&PF1_BASICSEARCH || protoCaps&PF1_SEARCHBYEMAIL || protoCaps&PF1_SEARCHBYNAME 
				|| protoCaps&PF1_EXTSEARCHUI ) netProtoCount++;
		}

	if (netProtoCount > 0) {
		ZeroMemory(&mi, sizeof(mi));
		mi.cbSize = sizeof(mi);
		mi.position = 500020000;
		mi.hIcon = LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_FINDUSER));
		mi.pszName = Translate("&Find/Add Contacts...");
		mi.pszService = MS_FINDADD_FINDADD;
		CallService(MS_CLIST_ADDMAINMENUITEM, 0, (LPARAM)&mi);
	}

	return 0;

}

