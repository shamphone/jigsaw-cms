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

#define COLUMNID_PROTO    0
#define COLUMNID_NICK     1
#define COLUMNID_FIRST    2
#define COLUMNID_LAST     3
#define COLUMNID_EMAIL    4
#define COLUMNID_HANDLE   5

static int handleColumnAfter=COLUMNID_EMAIL;

WCHAR* a2u( const char* );

void SaveColumnSizes(HWND hwndResults)
{
	int columnOrder[COLUMNID_HANDLE+1];
	int columnCount;
	char szSetting[32];
	int i;
	struct FindAddDlgData *dat;

	dat=(struct FindAddDlgData*)GetWindowLong(GetParent(hwndResults),GWL_USERDATA);
	columnCount=Header_GetItemCount(ListView_GetHeader(hwndResults));
	if(columnCount<=COLUMNID_EMAIL || columnCount>COLUMNID_HANDLE+1) return;
	ListView_GetColumnOrderArray(hwndResults,columnCount,columnOrder);
	if(columnCount<=COLUMNID_HANDLE) {
		if(handleColumnAfter==-1) {
			memmove(columnOrder+1,columnOrder,sizeof(columnOrder[0])*columnCount);
			columnOrder[0]=COLUMNID_HANDLE;
		}
		else {
			for(i=0;i<columnCount;i++) {
				if(handleColumnAfter==columnOrder[i]) {
					memmove(columnOrder+i+2,columnOrder+i+1,sizeof(columnOrder[0])*(columnCount-i-1));
					columnOrder[i+1]=COLUMNID_HANDLE;
					break;
				}
			}
		}
	}
	for(i=0;i<=COLUMNID_HANDLE;i++) {
		wsprintfA(szSetting,"ColOrder%d",i);
		DBWriteContactSettingByte(NULL,"FindAdd",szSetting,(BYTE)columnOrder[i]);
		if(i>=columnCount) continue;
		wsprintfA(szSetting,"ColWidth%d",i);
		DBWriteContactSettingWord(NULL,"FindAdd",szSetting,(WORD)ListView_GetColumnWidth(hwndResults,i));
	}
	DBWriteContactSettingByte(NULL,"FindAdd","SortColumn",(BYTE)dat->iLastColumnSortIndex);
	DBWriteContactSettingByte(NULL,"FindAdd","SortAscending",(BYTE)dat->bSortAscending);
}

static const TCHAR *szColumnNames[] = { NULL, _T("Nick"), _T("First Name"), _T("Last Name"), _T("E-mail"), NULL };
static int defaultColumnSizes[]={0,100,100,100,200,90};
void LoadColumnSizes(HWND hwndResults,const char *szProto)
{
	HDITEM hdi;
	int columnOrder[COLUMNID_HANDLE+1];
	int columnCount;
	char szSetting[32];
	int i;
	struct FindAddDlgData *dat;
	int colOrdersValid;

	defaultColumnSizes[COLUMNID_PROTO]=GetSystemMetrics(SM_CXSMICON)+4;
	dat=(struct FindAddDlgData*)GetWindowLong(GetParent(hwndResults),GWL_USERDATA);

	if(szProto && !lstrcmpA(szProto,"ICQ"))
		columnCount=COLUMNID_HANDLE+1;
	else
		columnCount=COLUMNID_EMAIL+1;

	colOrdersValid=1;
	for(i=0;i<=COLUMNID_HANDLE;i++) {
		LVCOLUMN lvc;
		if( i < columnCount ) {
			int bNeedsFree = FALSE;
			lvc.mask = LVCF_TEXT | LVCF_WIDTH;
			if( szColumnNames[i] != NULL )
				lvc.pszText = TranslateTS( szColumnNames[i] );
			else if( i == COLUMNID_HANDLE ) {
				#if defined( _UNICODE )
					bNeedsFree = TRUE;
					lvc.pszText = a2u((char*)CallProtoService(szProto,PS_GETCAPS,PFLAG_UNIQUEIDTEXT,0));
				#else
					lvc.pszText = (char*)CallProtoService(szProto,PS_GETCAPS,PFLAG_UNIQUEIDTEXT,0);
				#endif
			}
			else lvc.mask &= ~LVCF_TEXT;
			wsprintfA(szSetting,"ColWidth%d",i);
			lvc.cx = DBGetContactSettingWord(NULL,"FindAdd",szSetting,defaultColumnSizes[i]);
			ListView_InsertColumn( hwndResults, i, (LPARAM)&lvc );
			#if defined( _UNICODE )
				if ( bNeedsFree )
					mir_free(lvc.pszText);
			#endif
		}
		wsprintfA(szSetting,"ColOrder%d",i);
		columnOrder[i]=DBGetContactSettingByte(NULL,"FindAdd",szSetting,-1);
		if(columnOrder[i]==-1) colOrdersValid=0;
		if(columnOrder[i]==COLUMNID_HANDLE) handleColumnAfter=i?columnOrder[i-1]:-1;
	}
	if(colOrdersValid) {
		if(columnCount<=COLUMNID_HANDLE)
			for(i=0;i<columnCount;i++)
				if(columnOrder[i]==COLUMNID_HANDLE) {
					memmove(columnOrder+i,columnOrder+i+1,sizeof(columnOrder[0])*(columnCount-i));
					break;
				}
		ListView_SetColumnOrderArray(hwndResults,columnCount,columnOrder);
	}
	else handleColumnAfter=COLUMNID_EMAIL;

	dat->iLastColumnSortIndex=DBGetContactSettingByte(NULL,"FindAdd","SortColumn",COLUMNID_NICK);
	if(dat->iLastColumnSortIndex>=columnCount) dat->iLastColumnSortIndex=COLUMNID_NICK;
	dat->bSortAscending=DBGetContactSettingByte(NULL,"FindAdd","SortAscending",TRUE);

	hdi.mask=HDI_BITMAP|HDI_FORMAT;
	hdi.fmt=HDF_LEFT|HDF_BITMAP|HDF_STRING|HDF_BITMAP_ON_RIGHT;
	hdi.hbm=dat->bSortAscending?dat->hBmpSortDown:dat->hBmpSortUp;
	Header_SetItem(ListView_GetHeader(hwndResults),dat->iLastColumnSortIndex,&hdi);
}

int CALLBACK SearchResultsCompareFunc(LPARAM lParam1, LPARAM lParam2, LPARAM lParamSort)
{
	struct FindAddDlgData *dat=(struct FindAddDlgData*)lParamSort;
	int sortMultiplier;
	int sortCol;
	struct ListSearchResult *lsr1=(struct ListSearchResult*)lParam1,*lsr2=(struct ListSearchResult*)lParam2;
	
	sortMultiplier=dat->bSortAscending?1:-1;
	sortCol=dat->iLastColumnSortIndex;
	if ( lsr1 == NULL || lsr2 == NULL ) return 0;
	switch(sortCol)
	{
	case COLUMNID_PROTO:
		return lstrcmpA(lsr1->szProto, lsr2->szProto)*sortMultiplier;
	case COLUMNID_NICK:
		return lstrcmpiA(lsr1->psr.nick, lsr2->psr.nick)*sortMultiplier;
	case COLUMNID_FIRST:
		return lstrcmpiA(lsr1->psr.firstName, lsr2->psr.firstName)*sortMultiplier;
	case COLUMNID_LAST:
		return lstrcmpiA(lsr1->psr.lastName, lsr2->psr.lastName)*sortMultiplier;
	case COLUMNID_EMAIL:
		return lstrcmpiA(lsr1->psr.email, lsr2->psr.email)*sortMultiplier;
	case COLUMNID_HANDLE:
		if(!lstrcmpA(lsr1->szProto,lsr2->szProto)) {
			if(!lstrcmpA(lsr1->szProto,"ICQ")) {
				if(((ICQSEARCHRESULT*)&lsr1->psr)->uin<((ICQSEARCHRESULT*)&lsr2->psr)->uin) return -sortMultiplier;
				return sortMultiplier;
			}
			else return 0;
		}
		else return lstrcmpA(lsr1->szProto, lsr2->szProto)*sortMultiplier;
	}
	return 0;
}

void FreeSearchResults(HWND hwndResults)
{
	LV_ITEM lvi;
	struct ListSearchResult *lsr;
	for(lvi.iItem=ListView_GetItemCount(hwndResults)-1;lvi.iItem>=0;lvi.iItem--) {
		lvi.mask=LVIF_PARAM;
		ListView_GetItem(hwndResults,&lvi);
		lsr=(struct ListSearchResult*)lvi.lParam;
		if(lsr==NULL) continue;
		mir_free(lsr->psr.email);
		mir_free(lsr->psr.nick);
		mir_free(lsr->psr.firstName);
		mir_free(lsr->psr.lastName);
		mir_free(lsr);
	}
	ListView_DeleteAllItems(hwndResults);
	EnableResultButtons(GetParent(hwndResults),0);
}

// on its own thread
static void BeginSearchFailed(void * arg)
{
	TCHAR buf[128];
	if ( arg != NULL ) {
		TCHAR* protoName = LangPackPcharToTchar(( const char* )arg );
		mir_sntprintf(buf,SIZEOF(buf),TranslateT("Could not start a search on '%s', there was a problem - is %s connected?"),protoName,protoName);
		mir_free((char*)arg);
	}
	else lstrcpyn(buf,TranslateT("Could not search on any of the protocols, are you online?"),SIZEOF(buf));
	MessageBox(0,buf,TranslateT("Problem with search"),MB_OK | MB_ICONERROR);
}

int BeginSearch(HWND hwndDlg,struct FindAddDlgData *dat,const char *szProto,const char *szSearchService,DWORD requiredCapability,void *pvSearchParams)
{
	int protoCount,i;
	PROTOCOLDESCRIPTOR **protos;

	if(szProto==NULL) {
		int failures=0;
		DWORD caps;

		CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protos);
		dat->searchCount=0;
		dat->search=(struct ProtoSearchInfo*)mir_calloc(sizeof(struct ProtoSearchInfo) * protoCount);
		for(i=0;i<protoCount;i++) {
			if(protos[i]->type!=PROTOTYPE_PROTOCOL) continue;
			caps=(DWORD)CallProtoService(protos[i]->szName,PS_GETCAPS,PFLAGNUM_1,0);
			if(!(caps&requiredCapability)) continue;
			dat->search[dat->searchCount].hProcess=(HANDLE)CallProtoService(protos[i]->szName,szSearchService,0,(LPARAM)pvSearchParams);
			dat->search[dat->searchCount].szProto=protos[i]->szName;
			if(dat->search[dat->searchCount].hProcess==NULL) failures++;
			else dat->searchCount++;
		}
		if(failures) {
			//infuriatingly vague error message. fixme.
			if(dat->searchCount==0) {
				forkthread(BeginSearchFailed,0,NULL);
				mir_free(dat->search);
				dat->search=NULL;
				return 1;
		}	}
	}
	else {
		dat->search=(struct ProtoSearchInfo*)mir_alloc(sizeof(struct ProtoSearchInfo));
		dat->searchCount=1;
		dat->search[0].hProcess=(HANDLE)CallProtoService(szProto,szSearchService,0,(LPARAM)pvSearchParams);
		dat->search[0].szProto=szProto;
		if(dat->search[0].hProcess==NULL) {
			//infuriatingly vague error message. fixme.
			forkthread(BeginSearchFailed,0,(void*)mir_strdup(szProto));
			mir_free(dat->search);
			dat->search=NULL;
			dat->searchCount=0;
			return 1;
		}
	}
	return 0;
}

void SetStatusBarSearchInfo(HWND hwndStatus,struct FindAddDlgData *dat)
{
	TCHAR str[256];

	if (dat->searchCount != 0 ) {
		char szProtoName[64];
		int i;

		lstrcpy( str, TranslateT("Searching"));
		for( i=0; i <dat->searchCount; i++ ) {
			lstrcat(str, i ? _T(",") : _T( " " ));
			CallProtoService(dat->search[i].szProto,PS_GETNAME,SIZEOF(szProtoName),(LPARAM)szProtoName);
			#if !defined( _UNICODE )
				lstrcatA( str, szProtoName );
			#else
				{	TCHAR* p = a2u(szProtoName);
					lstrcat(str, p);
					mir_free(p);
				}
			#endif
	}	}
	else lstrcpy(str, TranslateT("Idle"));
		
	SendMessage( hwndStatus, SB_SETTEXT, 0, (LPARAM)str );
}	

struct ProtoResultsSummary {
	const char *szProto;
	int count;
};
void SetStatusBarResultInfo(HWND hwndDlg,struct FindAddDlgData *dat)
{
	HWND hwndStatus=GetDlgItem(hwndDlg,IDC_STATUSBAR);
	HWND hwndResults=GetDlgItem(hwndDlg,IDC_RESULTS);
	LV_ITEM lvi;
	struct ListSearchResult *lsr;
	struct ProtoResultsSummary *subtotal=NULL;
	int subtotalCount=0;
	int i,total;
	TCHAR str[256];

	total=ListView_GetItemCount(hwndResults);
	for(lvi.iItem=total-1;lvi.iItem>=0;lvi.iItem--) {
		lvi.mask=LVIF_PARAM;
		ListView_GetItem(hwndResults,&lvi);
		lsr=(struct ListSearchResult*)lvi.lParam;
		if(lsr==NULL) continue;
		for(i=0;i<subtotalCount;i++) {
			if(subtotal[i].szProto==lsr->szProto) {
				subtotal[i].count++;
				break;
			}
		}
		if(i==subtotalCount) {
			subtotal=(struct ProtoResultsSummary*)mir_realloc(subtotal,sizeof(struct ProtoResultsSummary)*(subtotalCount+1));
			subtotal[subtotalCount].szProto=lsr->szProto;
			subtotal[subtotalCount++].count=1;
		}
	}
	if ( total != 0 ) {
		char szProtoName[64];
		TCHAR substr[64];
		TCHAR* ptszProto;

		CallProtoService( subtotal[0].szProto, PS_GETNAME, SIZEOF(szProtoName), (LPARAM)szProtoName );
		#if defined( _UNICODE )
			ptszProto = a2u( szProtoName );
		#else
			ptszProto = szProtoName;
		#endif

		if ( subtotalCount == 1 ) {
			if(total==1) mir_sntprintf( str, SIZEOF(str), TranslateT("1 %s user found"), ptszProto );
			else         mir_sntprintf( str, SIZEOF(str), TranslateT("%d %s users found"), total, ptszProto );
		}
		else {
			mir_sntprintf( str, SIZEOF(str), TranslateT("%d users found ("),total);
			for( i=0; i < subtotalCount; i++ ) {
				if(i) {
					CallProtoService(subtotal[i].szProto,PS_GETNAME,SIZEOF(szProtoName),(LPARAM)szProtoName);
					#if defined( _UNICODE )
						mir_free( ptszProto );
						ptszProto = a2u( szProtoName );
					#else
						ptszProto = szProtoName;
					#endif
					lstrcat( str, _T(", "));
				}
				mir_sntprintf( substr, SIZEOF(substr), _T("%d %s"), subtotal[i].count, ptszProto );
				lstrcat( str, substr );
			}
			lstrcat( str, _T(")"));
		}
		mir_free(subtotal);
		#if defined( _UNICODE )
			mir_free( ptszProto );
		#endif
	}
	else lstrcpy(str, TranslateT("No users found"));
	SendMessage(hwndStatus, SB_SETTEXT, 2, (LPARAM)str );
}

void CreateResultsColumns(HWND hwndResults,struct FindAddDlgData *dat,char *szProto)
{
	SaveColumnSizes(hwndResults);
	while(ListView_DeleteColumn(hwndResults,0));
	ListView_SetImageList(hwndResults,dat->himlComboIcons,LVSIL_SMALL);
	LoadColumnSizes(hwndResults,szProto);
}

void ShowMoreOptionsMenu(HWND hwndDlg,int x,int y)
{
	struct FindAddDlgData *dat;
	HMENU hPopupMenu,hMenu;
	int commandId;
	struct ListSearchResult *lsr;

	dat=(struct FindAddDlgData*)GetWindowLong(hwndDlg,GWL_USERDATA);

	{	LVITEM lvi;
		if(ListView_GetSelectedCount(GetDlgItem(hwndDlg,IDC_RESULTS))!=1) return;
		lvi.mask=LVIF_PARAM;
		lvi.iItem=ListView_GetNextItem(GetDlgItem(hwndDlg,IDC_RESULTS),-1,LVNI_ALL|LVNI_SELECTED);
		ListView_GetItem(GetDlgItem(hwndDlg,IDC_RESULTS),&lvi);
		lsr=(struct ListSearchResult*)lvi.lParam;
	}

	hMenu=LoadMenu(GetModuleHandle(NULL),MAKEINTRESOURCE(IDR_CONTEXT));
	hPopupMenu=GetSubMenu(hMenu,4);
	CallService(MS_LANGPACK_TRANSLATEMENU,(WPARAM)hPopupMenu,0);
	commandId=TrackPopupMenu(hPopupMenu,TPM_RIGHTBUTTON|TPM_RETURNCMD,x,y,0,hwndDlg,NULL);
	if(commandId) {
		switch(commandId) {
			case IDC_ADD:
			{	ADDCONTACTSTRUCT acs;

				acs.handle=NULL;
				acs.handleType=HANDLE_SEARCHRESULT;
				acs.szProto=lsr->szProto;
				acs.psr=&lsr->psr;
				CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
				break;
			}
			case IDC_DETAILS:
			{	HANDLE hContact;
				hContact=(HANDLE)CallProtoService(lsr->szProto,PS_ADDTOLIST,PALF_TEMPORARY,(LPARAM)&lsr->psr);
				CallService(MS_USERINFO_SHOWDIALOG,(WPARAM)hContact,0);
				break;
			}
			case IDC_SENDMESSAGE:
			{	HANDLE hContact;
				hContact=(HANDLE)CallProtoService(lsr->szProto,PS_ADDTOLIST,PALF_TEMPORARY,(LPARAM)&lsr->psr);
				CallService(MS_MSG_SENDMESSAGE,(WPARAM)hContact,(LPARAM)(const char*)NULL);
				break;
			}
			default:
				break;
		}
	}
	DestroyMenu(hPopupMenu);
	DestroyMenu(hMenu);
}

