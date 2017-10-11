/*
Plugin of Miranda IM for communicating with users of the MSN Messenger protocol.
Copyright (c) 2003-5 George Hazan.
Copyright (c) 2002-3 Richard Hughes (original version).

Miranda IM: the free icq client for MS Windows
Copyright (C) 2000-2002 Richard Hughes, Roland Rabien & Tristan Van de Vreede

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

#include "msn_global.h"

#include <mbstring.h>

#include "resource.h"

struct MsnContact
{
	int list;
	char *email,*nick;
};

static int count;
static MsnContact* lists;
static CRITICAL_SECTION csLists;

void Lists_Init(void)
{
	lists = NULL;
	count = 0;
	InitializeCriticalSection( &csLists );
}

void Lists_Uninit(void)
{
	for ( int i=0; i < count; i++ ) {
		mir_free( lists[i].email );
		mir_free( lists[i].nick );
	}

	if ( lists != NULL )
		mir_free( lists );

	DeleteCriticalSection( &csLists );
}

int __stdcall Lists_NameToCode( const char *name )
{
	if ( name[2] )
		return 0;

	switch( *( PWORD )name )  {
		case 'LA': return LIST_AL;
		case 'LB': return LIST_BL;
		case 'LR': return LIST_RL;
		case 'LF': return LIST_FL;
		case 'LP': return LIST_PL;
	}

	return 0;
}

void __stdcall Lists_Wipe( void )
{
	EnterCriticalSection( &csLists );
	for ( int i=0; i < count; i++ ) {
		mir_free( lists[i].email );
		mir_free( lists[i].nick );
	}

	if ( lists != NULL ) {
		mir_free( lists );
		lists = NULL;
	}

	count = 0;
	LeaveCriticalSection( &csLists );
}

int __stdcall Lists_IsInList( int list, const char* email )
{
	int i;
	EnterCriticalSection( &csLists );
	for ( i=0; i < count; i++ )
		if ( !strcmp( lists[i].email, email ))
			break;

	if ( list != -1 && i != count )
		if (( lists[ i ].list & list ) != list )
			i = count;

	LeaveCriticalSection( &csLists );
	return ( i == count ) ? 0 : i+1;
}

int __stdcall Lists_GetMask( const char* email )
{
	int i;
	EnterCriticalSection( &csLists );
	for ( i=0; i < count; i++ )
		if ( !strcmp( lists[i].email, email )) {
			LeaveCriticalSection( &csLists );
			return lists[i].list;
		}

	LeaveCriticalSection( &csLists );
	return 0;
}

int __stdcall Lists_Add( int list, const char* email, const char* nick )
{
	EnterCriticalSection( &csLists );

	MsnContact* C;
	int idx = Lists_IsInList( -1, email );
	if ( idx == 0 )
	{
		lists = ( MsnContact* )mir_realloc( lists, sizeof( MsnContact )*( count+1 ));
		C = &lists[ count++ ];
		C->list = 0;
		C->email = mir_strdup( email );
		C->nick  = ( char* )mir_strdup( nick );
	}
	else C = &lists[ idx-1 ];

	int result = ( C->list |= list );
	LeaveCriticalSection( &csLists );
	return result;
}

void __stdcall Lists_Remove( int list, const char* email )
{
	EnterCriticalSection( &csLists );
	int i = Lists_IsInList( -1, email );
	if ( i != 0 ) {
		MsnContact* C = &lists[ --i ];

		C->list &= ~list;
		if ( C->list == 0 ) {
			mir_free( C->email );
			mir_free( C->nick );
			count--;
			memmove( lists+i, lists+i+1, sizeof( MsnContact )*( count-i ));
			lists = ( MsnContact* )mir_realloc( lists, sizeof( MsnContact )*count );
	}	}

	LeaveCriticalSection( &csLists );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN Server List Manager dialog procedure

static void ResetListOptions(HWND hwndList)
{
	int i;

	SendMessage(hwndList,CLM_SETBKBITMAP,0,(LPARAM)(HBITMAP)NULL);
	SendMessage(hwndList,CLM_SETBKCOLOR,GetSysColor(COLOR_WINDOW),0);
	SendMessage(hwndList,CLM_SETGREYOUTFLAGS,0,0);
	SendMessage(hwndList,CLM_SETLEFTMARGIN,2,0);
	SendMessage(hwndList,CLM_SETINDENT,10,0);
	for(i=0;i<=FONTID_MAX;i++)
		SendMessage(hwndList,CLM_SETTEXTCOLOR,i,GetSysColor(COLOR_WINDOWTEXT));
	SetWindowLong(hwndList,GWL_STYLE,GetWindowLong(hwndList,GWL_STYLE)|CLS_SHOWHIDDEN);
}

static void SetAllContactIcons( HWND hwndList )
{
	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
	do {
		HANDLE hItem = ( HANDLE )SendMessage( hwndList, CLM_FINDCONTACT, ( WPARAM )hContact, 0 );
		if ( hItem == NULL )
			continue;

		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM )hContact, 0 );
		if ( szProto == NULL ) {
LBL_Bad:	SendMessage( hwndList, CLM_DELETEITEM, ( WPARAM )hItem, 0 );
			continue;
		}

		if ( strcmp( szProto, msnProtocolName ))
			goto LBL_Bad;

		char szEmail[ MSN_MAX_EMAIL_LEN ];
		if ( MSN_GetStaticString( "e-mail", hContact, szEmail, sizeof szEmail ))
			goto LBL_Bad;

		DWORD dwMask = Lists_GetMask( szEmail );
		if ( SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(0,0)) == 0xFF )
			SendMessage( hwndList, CLM_SETEXTRAIMAGE,( WPARAM )hItem, MAKELPARAM(0,( dwMask & LIST_FL )?1:0));
		if ( SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(1,0)) == 0xFF )
			SendMessage( hwndList, CLM_SETEXTRAIMAGE,( WPARAM )hItem, MAKELPARAM(1,( dwMask & LIST_AL )?2:0));
		if ( SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(2,0)) == 0xFF )
			SendMessage( hwndList, CLM_SETEXTRAIMAGE,( WPARAM )hItem, MAKELPARAM(2,( dwMask & LIST_BL )?3:0));
		if ( SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(3,0)) == 0xFF )
			SendMessage( hwndList, CLM_SETEXTRAIMAGE,( WPARAM )hItem, MAKELPARAM(3,( dwMask & LIST_RL )?4:0));
	}
		while( hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 ));
}

static void SaveListItem( HANDLE hContact, const char* szEmail, int list, int iPrevValue, int iNewValue )
{
	if ( iPrevValue == iNewValue )
		return;

	if ( iNewValue == 0 )
		list += LIST_REMOVE;
	MSN_AddUser( hContact, szEmail, list );
}

static void SaveSettings( HWND hwndList )
{
	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
	do {
		HANDLE hItem = ( HANDLE )SendMessage( hwndList, CLM_FINDCONTACT, ( WPARAM )hContact, 0 );
		if ( hItem == NULL )
			continue;

		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM )hContact, 0 );
		if ( szProto == NULL ) continue;
		if ( strcmp( szProto, msnProtocolName )) continue;

		char szEmail[ MSN_MAX_EMAIL_LEN ];
		if ( MSN_GetStaticString( "e-mail", hContact, szEmail, sizeof szEmail ))
			continue;

		DWORD dwMask = Lists_GetMask( szEmail );
		SaveListItem( hContact, szEmail, LIST_FL, ( dwMask & LIST_FL ) != 0, SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(0,0)));
		SaveListItem( hContact, szEmail, LIST_AL, ( dwMask & LIST_AL ) != 0, SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(1,0)));
		SaveListItem( hContact, szEmail, LIST_BL, ( dwMask & LIST_BL ) != 0, SendMessage( hwndList, CLM_GETEXTRAIMAGE, ( WPARAM )hItem, MAKELPARAM(2,0)));
	}
		while( hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 ));
}

BOOL CALLBACK DlgProcMsnServLists(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	static HANDLE hItemAll;

	switch ( msg ) {
	case WM_INITDIALOG:
		TranslateDialogDefault( hwndDlg );
		{	
			HIMAGELIST hIml = ImageList_Create(
				GetSystemMetrics(SM_CXSMICON), GetSystemMetrics(SM_CYSMICON),
				ILC_MASK | (IsWinVerXPPlus() ? ILC_COLOR32 : ILC_COLOR16 ), 5, 5 );
			ImageList_AddIcon( hIml,LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(211)));
			ImageList_AddIcon( hIml, LoadIcon( hInst, MAKEINTRESOURCE( IDI_LIST_FL )));
			ImageList_AddIcon( hIml, LoadIcon( hInst, MAKEINTRESOURCE( IDI_LIST_AL )));
			ImageList_AddIcon( hIml, LoadIcon( hInst, MAKEINTRESOURCE( IDI_LIST_BL )));
			ImageList_AddIcon( hIml, LoadIcon( hInst, MAKEINTRESOURCE( IDI_LIST_RL )));
			SendDlgItemMessage( hwndDlg, IDC_LIST, CLM_SETEXTRAIMAGELIST, 0, (LPARAM)hIml );
		}
		ResetListOptions( GetDlgItem( hwndDlg, IDC_LIST ));
		SendDlgItemMessage(hwndDlg,IDC_LIST,CLM_SETEXTRACOLUMNS,4,0);
		SetAllContactIcons(GetDlgItem(hwndDlg,IDC_LIST));
		return TRUE;

	case WM_SETFOCUS:
		SetFocus(GetDlgItem(hwndDlg,IDC_LIST));
		break;

	case WM_COMMAND:
		break;

	case WM_NOTIFY:
		if (((LPNMHDR)lParam)->idFrom == 0 && ((LPNMHDR)lParam)->code == PSN_APPLY ) {
			SaveSettings(GetDlgItem(hwndDlg,IDC_LIST));
			break;
		}

		if (((LPNMHDR)lParam)->idFrom != IDC_LIST )
			break;

		switch (((LPNMHDR)lParam)->code) {
		case CLN_NEWCONTACT:
		case CLN_LISTREBUILT:
			SetAllContactIcons(GetDlgItem(hwndDlg,IDC_LIST));
			//fall through
		case CLN_OPTIONSCHANGED:
			ResetListOptions(GetDlgItem(hwndDlg,IDC_LIST));
			break;

		case NM_CLICK:
			HANDLE hItem;
			NMCLISTCONTROL *nm=(NMCLISTCONTROL*)lParam;
			DWORD hitFlags;
			int iImage;
			int itemType;

			// Make sure we have an extra column, also we can't change RL list
			if ( nm->iColumn == -1 || nm->iColumn == 3 )
				break;

			// Find clicked item
			hItem = (HANDLE)SendDlgItemMessage(hwndDlg, IDC_LIST, CLM_HITTEST, (WPARAM)&hitFlags, MAKELPARAM(nm->pt.x,nm->pt.y));
			// Nothing was clicked
			if ( hItem == NULL )
				break;

			// It was not our extended icon
			if ( !( hitFlags & CLCHT_ONITEMEXTRA ))
				break;

			// Get image in clicked column (0=none, 1=FL, 2=AL, 3=BL, 4=RL)
			iImage = SendDlgItemMessage(hwndDlg, IDC_LIST, CLM_GETEXTRAIMAGE, (WPARAM)hItem, MAKELPARAM(nm->iColumn, 0));
			if ( iImage == 0 )
				iImage = nm->iColumn + 1;
			else
				iImage = 0;

			// Get item type (contact, group, etc...)
			itemType = SendDlgItemMessage(hwndDlg, IDC_LIST, CLM_GETITEMTYPE, (WPARAM)hItem, 0);
			if ( itemType == CLCIT_CONTACT ) { // A contact
				SendDlgItemMessage(hwndDlg, IDC_LIST, CLM_SETEXTRAIMAGE, (WPARAM)hItem, MAKELPARAM(nm->iColumn, iImage));
				if (iImage && SendDlgItemMessage(hwndDlg,IDC_LIST,CLM_GETEXTRAIMAGE,(WPARAM)hItem,MAKELPARAM( 3-nm->iColumn, 0)) != 0xFF )
					if ( nm->iColumn == 1 || nm->iColumn == 2 )
						SendDlgItemMessage(hwndDlg, IDC_LIST, CLM_SETEXTRAIMAGE, (WPARAM)hItem, MAKELPARAM( 3-nm->iColumn, 0));
			}

			// Activate Apply button
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		}
		break;

	case WM_DESTROY:
		HIMAGELIST hIml=(HIMAGELIST)SendDlgItemMessage(hwndDlg,IDC_LIST,CLM_GETEXTRAIMAGELIST,0,0);
		ImageList_Destroy(hIml);
		break;
	}

	return FALSE;
}
