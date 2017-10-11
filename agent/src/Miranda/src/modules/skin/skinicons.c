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
#include <io.h>

/***********W*A*R*N*I*N*G*************

   This is quite possibly the worst
   code that has ever been written.

************W*A*R*N*I*N*G************/

BOOL CALLBACK DlgProcIconIndex(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);
static HINSTANCE hMiranda;
static HANDLE hIconsChangedEvent;
static HICON ImportIcon(const char *szProto,int n);

struct IconPreview {
	int id;
	char* description;
	int main;
};

struct IconPreview static mainIcons[] =
{
	{ SKINICON_OTHER_MIRANDA,    "Miranda IM",     1 },
	{ SKINICON_EVENT_MESSAGE,    "Message",        1 },
	{ SKINICON_EVENT_URL,        "URL",            1 },
	{ SKINICON_EVENT_FILE,       "File",           1 },
	{ SKINICON_OTHER_USERONLINE, "User Online",    1 },
	{ SKINICON_OTHER_GROUPOPEN,  "Group (Open)",   1 },
	{ SKINICON_OTHER_GROUPSHUT,  "Group (Closed)", 1 },
};

static int skinIconStatusToIdStatus[] = { ID_STATUS_OFFLINE, ID_STATUS_ONLINE, ID_STATUS_AWAY, ID_STATUS_NA, ID_STATUS_OCCUPIED, ID_STATUS_DND, ID_STATUS_FREECHAT, ID_STATUS_INVISIBLE, ID_STATUS_ONTHEPHONE, ID_STATUS_OUTTOLUNCH };
static int skinIconStatusToPf2[] = { 0xFFFFFFFF, PF2_ONLINE, PF2_SHORTAWAY, PF2_LONGAWAY, PF2_LIGHTDND, PF2_HEAVYDND, PF2_FREECHAT, PF2_INVISIBLE, PF2_ONTHEPHONE, PF2_OUTTOLUNCH };
static UINT skinIconStatusToResourceId[] = { IDI_OFFLINE, IDI_ONLINE, IDI_AWAY, IDI_NA, IDI_OCCUPIED, IDI_DND, IDI_FREE4CHAT, IDI_INVISIBLE, IDI_ONTHEPHONE, IDI_OUTTOLUNCH };
static UINT eventIconToResourceId[] = { IDI_RECVMSG, IDI_URL, IDI_FILE };
static UINT otherIconToResourceId[] = { IDI_MIRANDA, IDI_MIRANDA, IDI_MIRANDA, IDI_GROUPOPEN, IDI_USERONLINE, IDI_GROUPSHUT };

struct ProtoIcons {
	char *szProto;
	HICON hIcons[ SIZEOF(skinIconStatusToIdStatus) ];
} static *protoIcons;
static int protoIconsCount;
static HICON hEventIcons[3],hOtherIcons[6];
static HICON hStatusIcons[ SIZEOF(skinIconStatusToIdStatus) ];

static int IdStatusToSkinIconStatus(int idStatus)
{
	int i;
	for ( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ )
		if ( skinIconStatusToIdStatus[i] == idStatus )
			return i;

	return SKINICON_STATUS_OFFLINE;
}

static int LoadSkinProtoIcon(WPARAM wParam,LPARAM lParam)
{
	char *szProto=(char*)wParam;
	int i;
	if (!szProto) {
		// Only return a protocol specific icon if there is only one protocol
		// Otherwise return the builtin hStatusIcons icon.  This affects the global status menu mainly.
		PROTOCOLDESCRIPTOR **proto;
		DWORD protoCount,j;
		CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&proto);
		if (protoIconsCount==1) {
			HICON hIcon=protoIcons[0].hIcons[IdStatusToSkinIconStatus(lParam)];
			if (hIcon) {
				if (protoCount) {
					for (j=0;j<protoCount;j++) {
						if (proto[j]->type!=PROTOTYPE_PROTOCOL) continue;
						if (!_strcmpi(proto[j]->szName,protoIcons[0].szProto)) return (int)hIcon;
					} //for
				} else {
					return (int)hIcon;
				} //if
			} //if
		} // if
		return (int)hStatusIcons[IdStatusToSkinIconStatus(lParam)];
	} //if

	for(i=0;i<protoIconsCount;i++)
		if(!strcmp(szProto,protoIcons[i].szProto))
			return (int)protoIcons[i].hIcons[IdStatusToSkinIconStatus(lParam)];
	{
		int j;
		protoIcons=(struct ProtoIcons*)mir_realloc(protoIcons,sizeof(struct ProtoIcons)*(protoIconsCount+1));
		protoIcons[protoIconsCount].szProto=mir_strdup(szProto);
		for( j=0; j < SIZEOF(skinIconStatusToIdStatus); j++ )
		{
			protoIcons[protoIconsCount].hIcons[j]=ImportIcon(szProto,j);
		}//for
		protoIconsCount++;
	}
	return (int)protoIcons[protoIconsCount-1].hIcons[IdStatusToSkinIconStatus(lParam)];
}

static int LoadSkinIcon(WPARAM wParam,LPARAM lParam)
{
	if(wParam<SKINICON_EVENT_MESSAGE) {
		if(wParam>=SIZEOF(skinIconStatusToIdStatus)) return (int)(HICON)NULL;
		return LoadSkinProtoIcon((WPARAM)(char*)NULL,skinIconStatusToIdStatus[wParam]);
	}
	if(wParam<SKINICON_OTHER_MIRANDA) {
		if(wParam-SKINICON_EVENT_MESSAGE>=SIZEOF(hEventIcons)) return (int)(HICON)NULL;
		return (int)hEventIcons[wParam-SKINICON_EVENT_MESSAGE];
	}
	if(wParam-SKINICON_OTHER_MIRANDA>=SIZEOF(hOtherIcons)) return (int)(HICON)NULL;
	return (int)hOtherIcons[wParam-SKINICON_OTHER_MIRANDA];
}

static HICON ExtractIconFromPath(const char *path)
{
	char *comma;
	char file[MAX_PATH],fileFull[MAX_PATH];
	int n;
	HICON hIcon;

	if (path == NULL) return (HICON)NULL;

	lstrcpynA(file,path,SIZEOF(file));
	comma=strrchr(file,',');
	if(comma==NULL) n=0;
	else {n=atoi(comma+1); *comma=0;}
	CallService(MS_UTILS_PATHTOABSOLUTE, (WPARAM)file, (LPARAM)fileFull);
	hIcon=NULL;
	ExtractIconExA(fileFull,n,NULL,&hIcon,1);
	return hIcon;
}

static HICON ImportIcon(const char *szProto,int n)
{
	DBVARIANT dbv;
	char szSetting[64];
	HICON hIcon;

	if(szProto==NULL && n<SKINICON_EVENT_MESSAGE) {
		/* return 'all protocol status icon' */
		int i;
		for(i=0;;i++) {
			szSetting[0]='p';
			_itoa(i,szSetting+1,10);
			if(DBGetContactSetting(NULL,"Icons",szSetting,&dbv)) break;
			mir_snprintf(szSetting,SIZEOF(szSetting),"%s%d",dbv.pszVal,skinIconStatusToIdStatus[n]);
			DBFreeVariant(&dbv);
			if(!DBGetContactSetting(NULL,"Icons",szSetting,&dbv)) {
				hIcon=ExtractIconFromPath(dbv.pszVal);
				DBFreeVariant(&dbv);
				if (hIcon)
					return hIcon;
			} //if
		} //for
		return hStatusIcons[n];
	}//if

	/* if there is a protocol, build <protocol><ID> to get a path, otherwise
	use the index 'n' directly. */
	if (szProto) {
	//
		mir_snprintf(szSetting,SIZEOF(szSetting),"%s%d",szProto,skinIconStatusToIdStatus[n]);
	} else {
		_itoa(n,szSetting,10);
	} //if

	if(!DBGetContactSetting(NULL,"Icons",szSetting,&dbv)) {
		hIcon=ExtractIconFromPath(dbv.pszVal);
		DBFreeVariant(&dbv);
		if(hIcon!=NULL)
			return hIcon;
	}
	if (szProto) {
		char szPath[MAX_PATH], szFullPath[MAX_PATH],*str;
		HICON hIcon;

		GetModuleFileNameA(GetModuleHandle(NULL), szPath, MAX_PATH);
		str=strrchr(szPath,'\\');
		if(str!=NULL) *str=0;
		mir_snprintf(szFullPath, SIZEOF(szFullPath), "%s\\Icons\\proto_%s.dll,%d", szPath, szProto, -(int)skinIconStatusToResourceId[n]);
		hIcon=ExtractIconFromPath(szFullPath);
		if (hIcon) return hIcon;
		/* looking for a protocol icon and it wasn't found, use internal */
		return hStatusIcons[n];
	}
	return LoadIcon(hMiranda,MAKEINTRESOURCE(n<SKINICON_OTHER_MIRANDA?eventIconToResourceId[n-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[n-SKINICON_OTHER_MIRANDA]));
}

static void LoadAllIcons(void)
{
	DBVARIANT dbv;
	int i,j,z;
	char szSetting[128];

	for( i=0; i < SIZEOF(hEventIcons); i++ )
		hEventIcons[i]=ImportIcon(NULL,i+SKINICON_EVENT_MESSAGE);
	for( i=0; i < SIZEOF(hOtherIcons); i++ )
		hOtherIcons[i]=ImportIcon(NULL,i+SKINICON_OTHER_MIRANDA);
	ZeroMemory(hStatusIcons,sizeof(hStatusIcons));
	for( i=0; i < SIZEOF(hStatusIcons); i++ )
	{
		_itoa(skinIconStatusToIdStatus[i], szSetting, 10);
		hStatusIcons[i] = NULL;
		if(!DBGetContactSetting(NULL,"Icons",szSetting,&dbv)) {
			hStatusIcons[i] = ExtractIconFromPath(dbv.pszVal);
			DBFreeVariant(&dbv);
		}
		if (!hStatusIcons[i]) hStatusIcons[i]=LoadIcon(hMiranda,MAKEINTRESOURCE(skinIconStatusToResourceId[i]));
	}
	protoIcons=NULL;
	protoIconsCount=0;
	for(i=0;;i++) {
		z = 0;
		szSetting[0]='p';
		_itoa(i,szSetting+1,10);
		if(DBGetContactSetting(NULL,"Icons",szSetting,&dbv)) break;
		for(j=0;j<protoIconsCount;j++) {
			if (!strcmp(dbv.pszVal,protoIcons[j].szProto)) z = 1;
		}
		if (!z) {
			protoIcons=(struct ProtoIcons*)mir_realloc(protoIcons,sizeof(struct ProtoIcons)*(protoIconsCount+1));
			protoIcons[protoIconsCount].szProto=dbv.pszVal;
			for( j=0; j < SIZEOF(skinIconStatusToIdStatus); j++ )
			{
				protoIcons[protoIconsCount].hIcons[j]=ImportIcon(dbv.pszVal,j);
			}//for
			protoIconsCount++;
		}
		if (z) DBFreeVariant(&dbv); /* the proto was found, free the string */
	}//for
}

void FreeAllIcons(void)
{
	int i,j;
	for( i=0; i < SIZEOF(hOtherIcons); i++ ) DestroyIcon(hOtherIcons[i]);
	for( i=0; i < SIZEOF(hEventIcons); i++ ) DestroyIcon(hEventIcons[i]);
	for( i=0; i < protoIconsCount;i++) {
		for( j=0; j < SIZEOF(skinIconStatusToIdStatus); j++ ) {
			if(hStatusIcons[i]==protoIcons[i].hIcons[j]) continue;
			DestroyIcon(protoIcons[i].hIcons[j]);
		}
		mir_free(protoIcons[i].szProto);
	}
	mir_free(protoIcons);
	for( i=0; i < SIZEOF(hStatusIcons); i++ )
		DestroyIcon(hStatusIcons[i]);
}

int InitSkinIcons(void)
{
	hMiranda=GetModuleHandle(NULL);
	LoadAllIcons();
	CreateServiceFunction(MS_SKIN_LOADICON,LoadSkinIcon);
	CreateServiceFunction(MS_SKIN_LOADPROTOICON,LoadSkinProtoIcon);
	hIconsChangedEvent=CreateHookableEvent(ME_SKIN_ICONSCHANGED);
	return 0;
}

void UninitSkinIcons(void)
{
	FreeAllIcons();
}

struct ProtoIconsData {
	PROTOCOLDESCRIPTOR *proto;
	char **iconPath;
};
struct IconsOptsData {
	struct ProtoIconsData *protoIcons;
	int protoCount;
	char **mainIconPath;
	char **mainStatusPath;
	HWND hwndIndex;
	int originalGroupWidth;
	int shortGroupWidth;
	int groupHeight;
};
#define DM_REBUILDICONSPREVIEW   (WM_USER+10)
#define DM_CHANGEICON            (WM_USER+11)
#define DM_CHANGESPECIFICICON    (WM_USER+12)
BOOL CALLBACK DlgProcIconsOpts(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct IconsOptsData *dat;

	dat=(struct IconsOptsData*)GetWindowLong(hwndDlg,GWL_USERDATA);
	switch (msg)
	{
		case WM_INITDIALOG:
		{	PROTOCOLDESCRIPTOR *proto,**protoList;
			int protoCount,i,j;
			char szSetting[64];
			DBVARIANT dbv;

			TranslateDialogDefault(hwndDlg);
			dat=(struct IconsOptsData*)mir_alloc(sizeof(struct IconsOptsData));
			SetWindowLong(hwndDlg,GWL_USERDATA,(LONG)dat);
			dat->mainIconPath=(char**)mir_alloc(sizeof(char*) * SIZEOF(mainIcons));
			dat->mainStatusPath=(char**)mir_alloc(sizeof(char*) * SIZEOF(skinIconStatusToIdStatus));
			for( i=0; i < SIZEOF(mainIcons); i++ ) {
				_itoa(mainIcons[i].id,szSetting,10);
				if ( DBGetContactSetting( NULL, "Icons", szSetting, &dbv ))
					dat->mainIconPath[i]=NULL;
				else
					dat->mainIconPath[i]=dbv.pszVal;
			}

			for ( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
				_itoa(skinIconStatusToIdStatus[i],szSetting,10);
				if ( DBGetContactSetting( NULL, "Icons", szSetting, &dbv ))
					dat->mainStatusPath[i]=NULL;
				else
					dat->mainStatusPath[i]=dbv.pszVal;
			}

			ListView_SetImageList(GetDlgItem(hwndDlg,IDC_PREVIEW),ImageList_Create(GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),ILC_COLOR32|ILC_MASK,0,30),LVSIL_NORMAL);
			ListView_SetIconSpacing(GetDlgItem(hwndDlg,IDC_PREVIEW),56,67);
			i=SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_ADDSTRING,0,(LPARAM)TranslateT("Main Icons"));
			SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_SETITEMDATA,i,0);
			i=SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_ADDSTRING,0,(LPARAM)TranslateT("Global Status Icons"));
			SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_SETITEMDATA,i,0);
			CallService(MS_PROTO_ENUMPROTOCOLS,(WPARAM)&protoCount,(LPARAM)&protoList);
			for ( i=0; i < protoCount; i++ ) {
				TCHAR str[128];
				char protoName[96];
				if(protoList[i]->type!=PROTOTYPE_PROTOCOL || CallProtoService(protoList[i]->szName,PS_GETCAPS,PFLAGNUM_2,0)==0) continue;
				CallProtoService(protoList[i]->szName,PS_GETNAME,SIZEOF(protoName),(LPARAM)protoName);
				{	TCHAR* ptszProtoName = LangPackPcharToTchar( protoName );
					mir_sntprintf( str, SIZEOF(str), TranslateT("%s Icons"), ptszProtoName );
					mir_free( ptszProtoName );
				}
				j = SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_ADDSTRING,0,(LPARAM)str );
				SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_SETITEMDATA,j,(LPARAM)protoList[i]);
			}
			dat->protoCount=SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCOUNT,0,0)-2;
			dat->protoIcons=(struct ProtoIconsData*)mir_alloc(sizeof(struct ProtoIconsData)*dat->protoCount);
			for(i=0;i<dat->protoCount;i++) {
				proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETITEMDATA,i+2,0);
				dat->protoIcons[i].proto=proto;
				dat->protoIcons[i].iconPath=(char**)mir_alloc(sizeof(char*) * SIZEOF(skinIconStatusToIdStatus));
				for( j=0; j < SIZEOF(skinIconStatusToIdStatus); j++ ) {
					mir_snprintf(szSetting,SIZEOF(szSetting),"%s%d",proto->szName,skinIconStatusToIdStatus[j]);
					if(DBGetContactSetting(NULL,"Icons",szSetting,&dbv))
						dat->protoIcons[i].iconPath[j]=NULL;
					else
						dat->protoIcons[i].iconPath[j]=dbv.pszVal;
			}	}

			SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_SETCURSEL,0,0);
			{	RECT rc,rcReorder;
				GetWindowRect(GetDlgItem(hwndDlg,IDC_STSIMPLERIGHT),&rcReorder);
				GetWindowRect(GetDlgItem(hwndDlg,IDC_STICONSGROUP),&rc);
				dat->originalGroupWidth=rc.right-rc.left;
				dat->shortGroupWidth=rcReorder.right-rc.left;
				dat->groupHeight=rc.bottom-rc.top;
			}
			SendMessage(hwndDlg,DM_REBUILDICONSPREVIEW,0,0);
			return TRUE;
		}
		case DM_REBUILDICONSPREVIEW:
		{	LVITEM lvi;
			HIMAGELIST hIml;
			PROTOCOLDESCRIPTOR *proto;
			HICON hIcon;

			SetCursor(LoadCursor(NULL,IDC_WAIT));
			proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0),0);
			ListView_DeleteAllItems(GetDlgItem(hwndDlg,IDC_PREVIEW));
			hIml=ListView_GetImageList(GetDlgItem(hwndDlg,IDC_PREVIEW),LVSIL_NORMAL);
			ImageList_RemoveAll(hIml);

			lvi.mask=LVIF_TEXT|LVIF_IMAGE;
			lvi.iSubItem=0;
			if(proto==NULL) {
				if (SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0)==0) {
					for ( lvi.iItem=0; lvi.iItem < SIZEOF(mainIcons); lvi.iItem++ ) {
						lvi.pszText = LangPackPcharToTchar( mainIcons[lvi.iItem].description );
						hIcon = ExtractIconFromPath( dat->mainIconPath[lvi.iItem] );
						if(hIcon==NULL) hIcon=LoadIcon(hMiranda,MAKEINTRESOURCE(mainIcons[lvi.iItem].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[lvi.iItem].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[lvi.iItem].id-SKINICON_OTHER_MIRANDA]));
						lvi.iImage=ImageList_AddIcon(hIml,hIcon);
						ListView_InsertItem( GetDlgItem(hwndDlg,IDC_PREVIEW), &lvi );
						mir_free( lvi.pszText );
					}
				}
				else {
					for( lvi.iItem=0; lvi.iItem < SIZEOF(skinIconStatusToIdStatus); lvi.iItem++ ) {
						lvi.pszText = ( TCHAR* )CallService( MS_CLIST_GETSTATUSMODEDESCRIPTION, skinIconStatusToIdStatus[lvi.iItem], GCMDF_TCHAR );
						hIcon=ExtractIconFromPath(dat->mainStatusPath[lvi.iItem]);
						if(hIcon==NULL) hIcon=LoadIcon(hMiranda,MAKEINTRESOURCE(skinIconStatusToResourceId[lvi.iItem]));
						lvi.iImage=ImageList_AddIcon(hIml,hIcon);
						ListView_InsertItem( GetDlgItem(hwndDlg,IDC_PREVIEW), &lvi );
				}	}
			}
			else {
				int i;
				DWORD caps2=CallProtoService(proto->szName,PS_GETCAPS,PFLAGNUM_2,0);
				lvi.mask|=LVIF_PARAM;
				for ( i=0; i < dat->protoCount; i++ )
					if ( proto == dat->protoIcons[i].proto )
						break;

				for ( lvi.iItem=0; lvi.iItem < SIZEOF(skinIconStatusToIdStatus); lvi.iItem++ ) {
					if( !( caps2 & skinIconStatusToPf2[lvi.iItem] ))
						continue;

					lvi.pszText = ( TCHAR* )CallService( MS_CLIST_GETSTATUSMODEDESCRIPTION, skinIconStatusToIdStatus[lvi.iItem], GCMDF_TCHAR );
					lvi.lParam  = lvi.iItem;
					hIcon = ExtractIconFromPath(dat->protoIcons[i].iconPath[lvi.iItem]);
					if ( hIcon == NULL ) {
						char szPath[MAX_PATH], szFullPath[MAX_PATH],*str;
						GetModuleFileNameA(GetModuleHandle(NULL), szPath, MAX_PATH);
						str=strrchr(szPath,'\\');
						if(str!=NULL) *str=0;
						mir_snprintf(szFullPath, SIZEOF(szFullPath), "%s\\Icons\\proto_%s.dll,%d", szPath, dat->protoIcons[i].proto->szName, -(int)skinIconStatusToResourceId[lvi.iItem]);
						hIcon = ExtractIconFromPath(szFullPath);
					}
					if(hIcon==NULL) hIcon = ExtractIconFromPath(dat->mainStatusPath[lvi.iItem]);
					if(hIcon==NULL) hIcon = LoadIcon(hMiranda,MAKEINTRESOURCE(skinIconStatusToResourceId[lvi.iItem]));
					lvi.iImage = ImageList_AddIcon(hIml,hIcon);
					ListView_InsertItem( GetDlgItem(hwndDlg,IDC_PREVIEW), &lvi );
			}	}

			SetCursor(LoadCursor(NULL,IDC_ARROW));
			break;
		}
		case DM_CHANGEICON:
			{	char *path=(char*)lParam;
				PROTOCOLDESCRIPTOR *proto;

				proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0),0);
				if(proto==NULL) {
					if (SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0)==0) {
						if(dat->mainIconPath[wParam]!=NULL) mir_free(dat->mainIconPath[wParam]);
						dat->mainIconPath[wParam]=mir_strdup(path);
					}
					else {
						if(dat->mainStatusPath[wParam]!=NULL) mir_free(dat->mainStatusPath[wParam]);
						dat->mainStatusPath[wParam]=mir_strdup(path);
					}
				}
				else {
					LVITEM lvi;
					int i;
					for(i=0;i<dat->protoCount;i++)
						if(proto==dat->protoIcons[i].proto) break;
					lvi.mask=LVIF_PARAM;
					lvi.iItem=wParam;
					ListView_GetItem(GetDlgItem(hwndDlg,IDC_PREVIEW),&lvi);
					if(dat->protoIcons[i].iconPath[lvi.lParam]!=NULL) mir_free(dat->protoIcons[i].iconPath[lvi.lParam]);
					dat->protoIcons[i].iconPath[lvi.lParam]=mir_strdup(path);
				}
			}
			SendMessage(hwndDlg,DM_REBUILDICONSPREVIEW,0,0);
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		case DM_CHANGESPECIFICICON:
			{	struct IconPreview *ico=(struct IconPreview*)lParam;
				PROTOCOLDESCRIPTOR *proto=(PROTOCOLDESCRIPTOR*)wParam;
				int i;

				if(proto==NULL) {
					if (ico->main) {
						for( i=0; i < SIZEOF(mainIcons); i++ )
							if(mainIcons[i].id==ico->id) break;
						if( i < SIZEOF(mainIcons)) {
							if(dat->mainIconPath[i]!=NULL) mir_free(dat->mainIconPath[i]);
							dat->mainIconPath[i]=mir_strdup(ico->description);
						}
					}
					else {
						for( i=0; i < SIZEOF(skinIconStatusToResourceId); i++ )
							if(i==ico->id) break;
						if( i < SIZEOF(skinIconStatusToIdStatus)) {
							if(dat->mainStatusPath[i]!=NULL) mir_free(dat->mainStatusPath[i]);
							dat->mainStatusPath[i]=mir_strdup(ico->description);
						}
					}
				}
				else {
					for(i=0;i<dat->protoCount;i++)
						if(proto==dat->protoIcons[i].proto) break;
					if(dat->protoIcons[i].iconPath[ico->id]!=NULL) mir_free(dat->protoIcons[i].iconPath[ico->id]);
					dat->protoIcons[i].iconPath[ico->id]=mir_strdup(ico->description);
				}
			}
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		case WM_COMMAND:
			if(LOWORD(wParam)==IDC_IMPORT) {
				dat->hwndIndex=CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_ICONINDEX),GetParent(hwndDlg),DlgProcIconIndex,(LPARAM)hwndDlg);
				EnableWindow((HWND)lParam,FALSE);
			}
			else if(LOWORD(wParam)==IDC_LOADICONS) {
				char filetmp[MAX_PATH],filename[MAX_PATH];
				OPENFILENAMEA ofn={0};
				char filter[512],*pfilter;

				filetmp[0]=0;
				ofn.lStructSize = OPENFILENAME_SIZE_VERSION_400;
				ofn.hwndOwner = hwndDlg;
				ofn.hInstance = NULL;
				strcpy(filter,Translate("Icon Sets"));
				strcat(filter," (*.dll)");
				pfilter=filter+strlen(filter)+1;
				strcpy(pfilter,"*.DLL");
				pfilter=pfilter+strlen(pfilter)+1;
				strcpy(pfilter,Translate("All Files"));
				strcat(pfilter," (*)");
				pfilter=pfilter+strlen(pfilter)+1;
				strcpy(pfilter,"*");
				pfilter=pfilter+strlen(pfilter)+1;
				*pfilter='\0';
				ofn.lpstrFilter = filter;
				ofn.lpstrFile = filetmp;
				ofn.Flags = OFN_FILEMUSTEXIST | OFN_HIDEREADONLY;
				ofn.nMaxFile = SIZEOF(filename);
				ofn.nMaxFileTitle = MAX_PATH;
				ofn.lpstrDefExt = "dll";
				if(GetOpenFileNameA(&ofn)) {
					char path[MAX_PATH];
					int i;
					struct IconPreview ico;
					HICON hIcon;

                    CallService(MS_UTILS_PATHTORELATIVE, (WPARAM)filetmp, (LPARAM)filename);
					ico.description=path;
					if(SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0)==0) {
						for( i=0; i < SIZEOF(mainIcons); i++ ) {
							hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)(mainIcons[i].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[i].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[i].id-SKINICON_OTHER_MIRANDA]));
							if(hIcon==NULL) continue;
							ico.id=mainIcons[i].id;
							ico.main=1;
							wsprintfA(path,"%s,%d",filename,-(int)(mainIcons[i].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[i].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[i].id-SKINICON_OTHER_MIRANDA]));
							SendMessage(hwndDlg,DM_CHANGESPECIFICICON,(WPARAM)(PROTOCOLDESCRIPTOR*)NULL,(LPARAM)&ico);
						}
					}
					else if(SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0)==1) {
						for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
							hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)skinIconStatusToResourceId[i]);
							if(hIcon==NULL) continue;
							ico.id=i;
							ico.main=0;
							wsprintfA(path,"%s,%d",filename,-(int)skinIconStatusToResourceId[i]);
							SendMessage(hwndDlg,DM_CHANGESPECIFICICON,(WPARAM)(PROTOCOLDESCRIPTOR**)NULL,(LPARAM)&ico);
						}
					}
					else {
						PROTOCOLDESCRIPTOR *proto;
						proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0),0);
						for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
							hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)skinIconStatusToResourceId[i]);
							if(hIcon==NULL) continue;
							ico.id=i;
							ico.main=0;
							wsprintfA(path,"%s,%d",filename,-(int)skinIconStatusToResourceId[i]);
							SendMessage(hwndDlg,DM_CHANGESPECIFICICON,(WPARAM)proto,(LPARAM)&ico);
						}
					}
					SendMessage(hwndDlg,DM_REBUILDICONSPREVIEW,0,0);
					SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
				}
			}
			else if(LOWORD(wParam)==IDC_GETMORE) {
				char szVer[64],szUrl[256];
				CallService(MS_SYSTEM_GETVERSIONTEXT,SIZEOF(szVer),(LPARAM)szVer);
				while(strchr(szVer,' ')) *strchr(szVer,' ')='_';
				wsprintfA(szUrl,"http://addons.miranda-im.org/",szVer);
				CallService(MS_UTILS_OPENURL,1,(LPARAM)szUrl);
				break;
			}
			else if(wParam==MAKEWPARAM(IDC_CATEGORYLIST,CBN_SELCHANGE)) {
				if(dat->protoCount>1)
				{
					SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0);
				}
				SendMessage(hwndDlg,DM_REBUILDICONSPREVIEW,0,0);
			}
			break;
		case WM_CONTEXTMENU:
			{	LVHITTESTINFO lvhti;
				GetCursorPos(&lvhti.pt);
				ScreenToClient(GetDlgItem(hwndDlg,IDC_PREVIEW),&lvhti.pt);
				if(ListView_HitTest(GetDlgItem(hwndDlg,IDC_PREVIEW),&lvhti)!=-1) {
					HMENU hMenu;
					POINT pt;
					int cmd;
					GetCursorPos(&pt);
					hMenu=GetSubMenu(LoadMenu(GetModuleHandle(NULL),MAKEINTRESOURCE(IDR_CONTEXT)),3);
					CallService(MS_LANGPACK_TRANSLATEMENU,(WPARAM)hMenu,0);
					cmd=TrackPopupMenu(hMenu,TPM_RIGHTBUTTON|TPM_RETURNCMD,pt.x,pt.y,0,hwndDlg,NULL);
					DestroyMenu(hMenu);
					switch(cmd) {
						case ID_RESET:
						{
							PROTOCOLDESCRIPTOR *proto;
							proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0),0);
							if(proto==NULL) {
								if(SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCURSEL,0,0)==0) {
									if(dat->mainIconPath[lvhti.iItem]!=NULL) {
										mir_free(dat->mainIconPath[lvhti.iItem]);
										dat->mainIconPath[lvhti.iItem]=NULL;
									}
								}
								else {
									if(dat->mainStatusPath[lvhti.iItem]!=NULL) {
										mir_free(dat->mainStatusPath[lvhti.iItem]);
										dat->mainStatusPath[lvhti.iItem]=NULL;
									}
								}
							}
							else {
								LVITEM lvi;
								int i;
								for(i=0;i<dat->protoCount;i++)
									if(proto==dat->protoIcons[i].proto) break;
								lvi.mask=LVIF_PARAM;
								lvi.iItem=lvhti.iItem;
								ListView_GetItem(GetDlgItem(hwndDlg,IDC_PREVIEW),&lvi);
								if(dat->protoIcons[i].iconPath[lvi.lParam]!=NULL) {
									mir_free(dat->protoIcons[i].iconPath[lvi.lParam]);
									dat->protoIcons[i].iconPath[lvi.lParam]=NULL;
								}
							}
							SendMessage(hwndDlg,DM_REBUILDICONSPREVIEW,0,0);
							SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
							break;
						}
					}
					return TRUE;
				}
			}
			break;
		case WM_NOTIFY:
			switch(((LPNMHDR)lParam)->code) {
			case PSN_APPLY:
				{	int i,j;
					char szSetting[64];
					DWORD flags;
					PROTOCOLDESCRIPTOR *proto;
					for( i=0; i < SIZEOF(mainIcons); i++ ) {
						_itoa(mainIcons[i].id,szSetting,10);
						if(dat->mainIconPath[i]==NULL)
							DBDeleteContactSetting(NULL,"Icons",szSetting);
						else
							DBWriteContactSettingString(NULL,"Icons",szSetting,dat->mainIconPath[i]);
					}
					for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
						_itoa(skinIconStatusToIdStatus[i],szSetting,10);
						if(dat->mainStatusPath[i]==NULL)
							DBDeleteContactSetting(NULL,"Icons",szSetting);
						else
							DBWriteContactSettingString(NULL,"Icons",szSetting,dat->mainStatusPath[i]);
					}
					for(i=0;i<dat->protoCount;i++) {
						for( j=0; j < SIZEOF(skinIconStatusToIdStatus); j++ ) {
							flags=(DWORD)CallProtoService(dat->protoIcons[i].proto->szName,PS_GETCAPS,PFLAGNUM_2,0);
							mir_snprintf(szSetting,SIZEOF(szSetting),"%s%d",dat->protoIcons[i].proto->szName,skinIconStatusToIdStatus[j]);
							if(dat->protoIcons[i].iconPath[j]==NULL || !(flags&skinIconStatusToPf2[j]))
								DBDeleteContactSetting(NULL,"Icons",szSetting);
							else
								DBWriteContactSettingString(NULL,"Icons",szSetting,dat->protoIcons[i].iconPath[j]);
					}	}

					szSetting[0]='p';
					i=SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETCOUNT,0,0)-1;
					_itoa(i,szSetting+1,10);
					DBDeleteContactSetting(NULL,"Icons",szSetting);
					for(;i>=2;i--) {
						proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_CATEGORYLIST,LB_GETITEMDATA,i,0);
						_itoa(i-2,szSetting+1,10);
						DBWriteContactSettingString(NULL,"Icons",szSetting,proto->szName);
					}
					FreeAllIcons();
					LoadAllIcons();
					NotifyEventHooks(hIconsChangedEvent,0,0);
					return TRUE;
			}	}
			break;

		case WM_DESTROY:
		{	int i,j;
			DestroyWindow(dat->hwndIndex);
			for( i=0; i < SIZEOF(mainIcons); i++ )
				if(dat->mainIconPath[i]!=NULL) mir_free(dat->mainIconPath[i]);
			mir_free(dat->mainIconPath);
			for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ )
				if(dat->mainStatusPath[i]!=NULL) mir_free(dat->mainStatusPath[i]);
			mir_free(dat->mainStatusPath);
			for(i=0;i<dat->protoCount;i++) {
				for( j=0; j < SIZEOF(skinIconStatusToIdStatus); j++ ) {
					if(dat->protoIcons[i].iconPath[j]!=NULL) mir_free(dat->protoIcons[i].iconPath[j]);
				}
				mir_free(dat->protoIcons[i].iconPath);
			}
			mir_free(dat->protoIcons);
			mir_free(dat);
			break;
	}	}

	return FALSE;
}

static int IconExists(const char *filename,int id)
{
	HICON hIcon;
	hIcon=ExtractIconA(hMiranda,filename,-id);
	if(hIcon==NULL) return 0;
	if((int)hIcon==1) return 0;
	DestroyIcon(hIcon);
	return 1;
}

static UINT mirandaIconSetIds[]={IDI_RECVMSG,IDI_URL,IDI_ONLINE,IDI_OFFLINE,IDI_AWAY,IDI_NA};
static int IsMirandaIconSet(const char *filename)
{
	int i;
	if(_access(filename,0)!=0) return 0;
	for( i=0; i < SIZEOF(mirandaIconSetIds); i++ )
		if(!IconExists(filename,mirandaIconSetIds[i])) return 0;
	return 1;
}

BOOL CALLBACK DlgProcIconIndex(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	static HWND hwndParent,hwndDragOver;
	static int dragging;
	static int dragItem,dropHiLite;
	static int originalPreviewHeight;

	switch (msg) {
	case WM_INITDIALOG:
		hwndParent=(HWND)lParam;
		dragging=dragItem=0;
		TranslateDialogDefault(hwndDlg);
		ListView_SetImageList(GetDlgItem(hwndDlg,IDC_PREVIEW),ImageList_Create(GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),ILC_COLOR32|ILC_MASK,0,100),LVSIL_NORMAL);
		ListView_SetIconSpacing(GetDlgItem(hwndDlg,IDC_PREVIEW),56,67);
		{	RECT rcThis,rcParent;
			GetWindowRect(hwndDlg,&rcThis);
			GetWindowRect(hwndParent,&rcParent);
			OffsetRect(&rcThis,rcParent.right-rcThis.left,0);
			OffsetRect(&rcThis,0,rcParent.top-rcThis.top);
			GetWindowRect(GetParent(hwndParent),&rcParent);
			if(rcThis.right>GetSystemMetrics(SM_CXSCREEN)) {
				OffsetRect(&rcParent,GetSystemMetrics(SM_CXSCREEN)-rcThis.right,0);
				OffsetRect(&rcThis,GetSystemMetrics(SM_CXSCREEN)-rcThis.right,0);
				MoveWindow(GetParent(hwndParent),rcParent.left,rcParent.top,rcParent.right-rcParent.left,rcParent.bottom-rcParent.top,TRUE);
			}
			MoveWindow(hwndDlg,rcThis.left,rcThis.top,rcThis.right-rcThis.left,rcThis.bottom-rcThis.top,FALSE);
		}
		{	RECT rc;
			GetWindowRect(GetDlgItem(hwndDlg,IDC_PREVIEW),&rc);
			originalPreviewHeight=rc.bottom-rc.top;
		}
		{	int i,item;
			TCHAR text[128];
			LPARAM lData;
			for ( i = SendDlgItemMessage(hwndParent,IDC_CATEGORYLIST,LB_GETCOUNT,0,0)-1; i >= 2; i-- ) {
				SendDlgItemMessage(hwndParent,IDC_CATEGORYLIST,LB_GETTEXT,i,(LPARAM)text);
				lData=SendDlgItemMessage(hwndParent,IDC_CATEGORYLIST,LB_GETITEMDATA,i,0);
				item = SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_ADDSTRING,0,(LPARAM)text);
				SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_SETITEMDATA,item,lData);
			}
			SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_SETCURSEL,0,0);
		}
		{	HRESULT (STDAPICALLTYPE *MySHAutoComplete)(HWND,DWORD);
			MySHAutoComplete=(HRESULT (STDAPICALLTYPE*)(HWND,DWORD))GetProcAddress(GetModuleHandleA("shlwapi"),"SHAutoComplete");
			if(MySHAutoComplete) MySHAutoComplete(GetDlgItem(hwndDlg,IDC_ICONSET),1);
		}
		SetDlgItemTextA(hwndDlg,IDC_ICONSET,"icons.dll");
		return TRUE;
	case DM_REBUILDICONSPREVIEW:
	{	LVITEMA lvi;
		char filename[MAX_PATH],caption[64];
		HIMAGELIST hIml;
		int count,isMiranda,i;
		HICON hIcon;

		SetCursor(LoadCursor(NULL,IDC_WAIT));
		ListView_DeleteAllItems(GetDlgItem(hwndDlg,IDC_PREVIEW));
		hIml=ListView_GetImageList(GetDlgItem(hwndDlg,IDC_PREVIEW),LVSIL_NORMAL);
		ImageList_RemoveAll(hIml);
		GetDlgItemTextA(hwndDlg,IDC_ICONSET,filename,SIZEOF(filename));

		isMiranda=IsMirandaIconSet(filename);
		ShowWindow(GetDlgItem(hwndDlg,IDC_IMPORTMULTI),isMiranda);
		ShowWindow(GetDlgItem(hwndDlg,IDC_TOMAIN),isMiranda);
		ShowWindow(GetDlgItem(hwndDlg,IDC_TODEFICON),isMiranda);
		ShowWindow(GetDlgItem(hwndDlg,IDC_TOPROTO),isMiranda);
		ShowWindow(GetDlgItem(hwndDlg,IDC_PROTOLIST),isMiranda);
		ShowWindow(GetDlgItem(hwndDlg,IDC_IMPORT),isMiranda);
		{	RECT rcPreview,rcGroup;
			GetWindowRect(GetDlgItem(hwndDlg,IDC_PREVIEW),&rcPreview);
			GetWindowRect(GetDlgItem(hwndDlg,IDC_IMPORTMULTI),&rcGroup);
			SetWindowPos(GetDlgItem(hwndDlg,IDC_PREVIEW),0,0,0,rcPreview.right-rcPreview.left,isMiranda?originalPreviewHeight:rcGroup.bottom-rcPreview.top,SWP_NOZORDER|SWP_NOMOVE);
		}

		if(_access(filename,0)!=0) {
			SetCursor(LoadCursor(NULL,IDC_ARROW));
			break;
		}

		lvi.mask=LVIF_TEXT|LVIF_IMAGE|LVIF_PARAM;
		lvi.iSubItem=0;
		lvi.iItem=0;
		if(isMiranda) {
			for( i=0; i < SIZEOF(mainIcons); i++ ) {
				hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)(mainIcons[i].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[i].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[i].id-SKINICON_OTHER_MIRANDA]));
				if(hIcon==NULL) continue;
				lvi.iImage=ImageList_AddIcon(hIml,hIcon);
				DestroyIcon(hIcon);
				lvi.pszText=Translate(mainIcons[lvi.iItem].description);
				lvi.lParam=-(int)(mainIcons[i].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[i].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[i].id-SKINICON_OTHER_MIRANDA]);
				SendMessageA( GetDlgItem(hwndDlg,IDC_PREVIEW), LVM_INSERTITEMA, 0, (LPARAM)&lvi );
				lvi.iItem++;
			}
			for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
				hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)skinIconStatusToResourceId[i]);
				if(hIcon==NULL) continue;
				lvi.iImage=ImageList_AddIcon(hIml,hIcon);
				DestroyIcon(hIcon);
				lvi.pszText=(char*)CallService(MS_CLIST_GETSTATUSMODEDESCRIPTION,skinIconStatusToIdStatus[i],0);
				lvi.lParam=-(int)skinIconStatusToResourceId[i];
				SendMessageA( GetDlgItem(hwndDlg,IDC_PREVIEW), LVM_INSERTITEMA, 0, (LPARAM)&lvi );
				lvi.iItem++;
			}
		}
		count=(int)ExtractIconA(GetModuleHandle(NULL),filename,-1);
		for(i=0;i<count;lvi.iItem++,i++) {
			wsprintfA(caption,"%d",i+1);
			lvi.pszText=caption;
			hIcon=ExtractIconA(GetModuleHandle(NULL),filename,i);
			lvi.iImage=ImageList_AddIcon(hIml,hIcon);
			DestroyIcon(hIcon);
			lvi.lParam=i;
			SendMessageA( GetDlgItem(hwndDlg,IDC_PREVIEW), LVM_INSERTITEMA, 0, (LPARAM)&lvi );
		}
		SetCursor(LoadCursor(NULL,IDC_ARROW));
		break;
	}
	case WM_COMMAND:
		switch(LOWORD(wParam)) {
			case IDC_BROWSE:
			{	char str[MAX_PATH];
				OPENFILENAMEA ofn;
				char filter[512],*pfilter;

				GetDlgItemTextA(hwndDlg,IDC_ICONSET,str,SIZEOF(str));
				ZeroMemory(&ofn, sizeof(ofn));
				ofn.lStructSize = OPENFILENAME_SIZE_VERSION_400;
				ofn.hwndOwner = GetParent(hwndDlg);
				ofn.hInstance = NULL;
				strcpy(filter,Translate("Icon Sets"));
				strcat(filter," (*.dll;*.icl;*.exe;*.ico)");
				pfilter=filter+strlen(filter)+1;
				strcpy(pfilter,"*.DLL;*.ICL;*.EXE;*.ICO");
				pfilter=pfilter+strlen(pfilter)+1;
				strcpy(pfilter,Translate("All Files"));
				strcat(pfilter," (*)");
				pfilter=pfilter+strlen(pfilter)+1;
				strcpy(pfilter,"*");
				pfilter=pfilter+strlen(pfilter)+1;
				*pfilter='\0';
				ofn.lpstrFilter = filter;
				ofn.lpstrFile = str;
				ofn.Flags = OFN_FILEMUSTEXIST | OFN_HIDEREADONLY;
				ofn.nMaxFile = SIZEOF(str);
				ofn.nMaxFileTitle = MAX_PATH;
				ofn.lpstrDefExt = "dll";
				if(!GetOpenFileNameA(&ofn)) break;
				SetDlgItemTextA(hwndDlg,IDC_ICONSET,str);
				break;
			}
			case IDC_GETMORE:
			{	char szVer[64],szUrl[256];
				CallService(MS_SYSTEM_GETVERSIONTEXT,SIZEOF(szVer),(LPARAM)szVer);
				while(strchr(szVer,' ')) *strchr(szVer,' ')='_';
				wsprintfA(szUrl,"http://www.miranda-im.org/download/index.php",szVer);
				CallService(MS_UTILS_OPENURL,1,(LPARAM)szUrl);
				break;
			}
			case IDC_ICONSET:
				if(HIWORD(wParam)==EN_CHANGE)
					SendMessage(hwndDlg,DM_REBUILDICONSPREVIEW,0,0);
				break;
			case IDC_TOMAIN:
			case IDC_TODEFICON:
			case IDC_TOPROTO:
				EnableWindow(GetDlgItem(hwndDlg,IDC_IMPORT),IsDlgButtonChecked(hwndDlg,IDC_TOMAIN) || IsDlgButtonChecked(hwndDlg,IDC_TODEFICON) || IsDlgButtonChecked(hwndDlg,IDC_TOPROTO));
				break;
			case IDC_IMPORT:
			{	char filetmp[MAX_PATH],filename[MAX_PATH],path[MAX_PATH];
				int i;
				struct IconPreview ico;
				HICON hIcon;

				GetDlgItemTextA(hwndDlg,IDC_ICONSET,filetmp,SIZEOF(filetmp));
				CallService(MS_UTILS_PATHTORELATIVE, (WPARAM)filetmp, (LPARAM)filename);
				ico.description=path;
				if(IsDlgButtonChecked(hwndDlg,IDC_TOMAIN)) {
					for( i=0; i < SIZEOF(mainIcons); i++ ) {
						hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)(mainIcons[i].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[i].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[i].id-SKINICON_OTHER_MIRANDA]));
						if(hIcon==NULL) continue;
						ico.id=mainIcons[i].id;
						ico.main=1;
						wsprintfA(path,"%s,%d",filename,-(int)(mainIcons[i].id<SKINICON_OTHER_MIRANDA?eventIconToResourceId[mainIcons[i].id-SKINICON_EVENT_MESSAGE]:otherIconToResourceId[mainIcons[i].id-SKINICON_OTHER_MIRANDA]));
						SendMessage(hwndParent,DM_CHANGESPECIFICICON,(WPARAM)(PROTOCOLDESCRIPTOR*)NULL,(LPARAM)&ico);
					}
				}
				if(IsDlgButtonChecked(hwndDlg,IDC_TODEFICON)) {
					for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
						hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)skinIconStatusToResourceId[i]);
						if(hIcon==NULL) continue;
						ico.id=i;
						ico.main=0;
						wsprintfA(path,"%s,%d",filename,-(int)skinIconStatusToResourceId[i]);
						SendMessage(hwndParent,DM_CHANGESPECIFICICON,(WPARAM)(PROTOCOLDESCRIPTOR**)NULL,(LPARAM)&ico);
					}
				}
				if(IsDlgButtonChecked(hwndDlg,IDC_TOPROTO)) {
					PROTOCOLDESCRIPTOR *proto;
					proto=(PROTOCOLDESCRIPTOR*)SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_PROTOLIST,CB_GETCURSEL,0,0),0);
					for( i=0; i < SIZEOF(skinIconStatusToIdStatus); i++ ) {
						hIcon=ExtractIconA(GetModuleHandle(NULL),filename,-(int)skinIconStatusToResourceId[i]);
						if(hIcon==NULL) continue;
						ico.id=i;
						ico.main=0;
						wsprintfA(path,"%s,%d",filename,-(int)skinIconStatusToResourceId[i]);
						SendMessage(hwndParent,DM_CHANGESPECIFICICON,(WPARAM)proto,(LPARAM)&ico);
					}
				}
				SendMessage(hwndParent,DM_REBUILDICONSPREVIEW,0,0);
				break;
			}
		}
		break;
	case WM_MOUSEMOVE:
		if(dragging) {
			LVHITTESTINFO lvhti;
			int onItem=0;
			HWND hwndOver;
			RECT rc;
			POINT ptDrag;

			lvhti.pt.x=(short)LOWORD(lParam); lvhti.pt.y=(short)HIWORD(lParam);
			ClientToScreen(hwndDlg,&lvhti.pt);
			hwndOver=WindowFromPoint(lvhti.pt);
			GetWindowRect(hwndOver,&rc);
			ptDrag.x=lvhti.pt.x-rc.left; ptDrag.y=lvhti.pt.y-rc.top;
			if(hwndOver!=hwndDragOver) {
				ImageList_DragLeave(hwndDragOver);
				hwndDragOver=hwndOver;
				ImageList_DragEnter(hwndDragOver,ptDrag.x,ptDrag.y);
			}
			ImageList_DragMove(ptDrag.x,ptDrag.y);
			if(hwndOver==GetDlgItem(hwndParent,IDC_PREVIEW)) {
				ScreenToClient(GetDlgItem(hwndParent,IDC_PREVIEW),&lvhti.pt);
				if(ListView_HitTest(GetDlgItem(hwndParent,IDC_PREVIEW),&lvhti)!=-1) {
					if(lvhti.iItem!=dropHiLite) {
						ImageList_DragLeave(hwndDragOver);
						if(dropHiLite!=-1) ListView_SetItemState(GetDlgItem(hwndParent,IDC_PREVIEW),dropHiLite,0,LVIS_DROPHILITED);
						dropHiLite=lvhti.iItem;
						ListView_SetItemState(GetDlgItem(hwndParent,IDC_PREVIEW),dropHiLite,LVIS_DROPHILITED,LVIS_DROPHILITED);
						UpdateWindow(GetDlgItem(hwndParent,IDC_PREVIEW));
						ImageList_DragEnter(hwndDragOver,ptDrag.x,ptDrag.y);
					}
					onItem=1;
				}
			}
			if(!onItem && dropHiLite!=-1) {
				ImageList_DragLeave(hwndDragOver);
				ListView_SetItemState(GetDlgItem(hwndParent,IDC_PREVIEW),dropHiLite,0,LVIS_DROPHILITED);
				UpdateWindow(GetDlgItem(hwndParent,IDC_PREVIEW));
				ImageList_DragEnter(hwndDragOver,ptDrag.x,ptDrag.y);
				dropHiLite=-1;
			}
			SetCursor(LoadCursor(NULL,onItem?IDC_ARROW:IDC_NO));
		}
		break;
	case WM_LBUTTONUP:
		if(dragging) {
			ReleaseCapture();
			ImageList_EndDrag();
			dragging=0;
			if(dropHiLite!=-1) {
				char path[MAX_PATH],fullPath[MAX_PATH],filename[MAX_PATH];
				LVITEM lvi;
				GetDlgItemTextA(hwndDlg,IDC_ICONSET,fullPath,SIZEOF(fullPath));
				CallService(MS_UTILS_PATHTORELATIVE, (WPARAM)fullPath, (LPARAM)filename);
				lvi.mask=LVIF_PARAM;
				lvi.iItem=dragItem; lvi.iSubItem=0;
				ListView_GetItem(GetDlgItem(hwndDlg,IDC_PREVIEW),&lvi);
				wsprintfA(path,"%s,%d",filename,(int)lvi.lParam);
				SendMessage(hwndParent,DM_CHANGEICON,dropHiLite,(LPARAM)path);
				ListView_SetItemState(GetDlgItem(hwndParent,IDC_PREVIEW),dropHiLite,0,LVIS_DROPHILITED);
		}	}
		break;
	case WM_NOTIFY:
		switch(((LPNMHDR)lParam)->idFrom) {
		case IDC_PREVIEW:
			switch(((LPNMHDR)lParam)->code) {
			case LVN_BEGINDRAG:
				SetCapture(hwndDlg);
				dragging=1;
				dragItem=((LPNMLISTVIEW)lParam)->iItem;
				dropHiLite=-1;
				ImageList_BeginDrag(ListView_GetImageList(GetDlgItem(hwndDlg,IDC_PREVIEW),LVSIL_NORMAL),dragItem,GetSystemMetrics(SM_CXICON)/2,GetSystemMetrics(SM_CYICON)/2);
				{	POINT pt;
					RECT rc;
					GetCursorPos(&pt);
					GetWindowRect(hwndDlg,&rc);
					ImageList_DragEnter(hwndDlg,pt.x-rc.left,pt.y-rc.top);
					hwndDragOver=hwndDlg;
				}
				break;
			}
			break;
		}
		break;
	case WM_CLOSE:
		DestroyWindow(hwndDlg);
		EnableWindow(GetDlgItem(hwndParent,IDC_IMPORT),TRUE);
		break;
	}
	return FALSE;
}
