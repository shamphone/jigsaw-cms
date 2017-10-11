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

BOOL CALLBACK AddContactDlgProc(HWND hdlg,UINT msg,WPARAM wparam,LPARAM lparam)
{
	ADDCONTACTSTRUCT *acs;

	switch(msg)
	{
		case WM_INITDIALOG:
			{
				char idstr[4],szUin[10];
				DBVARIANT dbv;
				int groupId;
				DWORD flags=0;
				
				acs=(ADDCONTACTSTRUCT *)lparam;
				SetWindowLong(hdlg,GWL_USERDATA,(LONG)acs);
				
				TranslateDialogDefault(hdlg);
				SendMessage(hdlg, WM_SETICON, ICON_BIG, (LPARAM)LoadIcon(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_ADDCONTACT)));
				if(acs->handleType==HANDLE_EVENT)
				{
					DBEVENTINFO dbei;
					DWORD dwUin;

					ZeroMemory(&dbei,sizeof(dbei));
					dbei.cbSize=sizeof(dbei);
					dbei.cbBlob=sizeof(DWORD);
					dbei.pBlob=(PBYTE)&dwUin;
					CallService(MS_DB_EVENT_GET,(WPARAM)acs->handle,(LPARAM)&dbei);
					_ltoa(dwUin,szUin,10);
					acs->szProto = dbei.szModule;
				}
				{	TCHAR* szName;
					if ( acs->handleType == HANDLE_CONTACT )
						szName = (TCHAR*)CallService( MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)acs->handle, GCDNF_TCHAR );
					else {
						char* p = (acs->handleType == HANDLE_EVENT) ? szUin : acs->psr->nick;
						#if defined( _UNICODE )
							szName =( TCHAR* )alloca( 128*sizeof( TCHAR ));
							MultiByteToWideChar( CP_ACP, 0, p, -1, szName, 128 );
						#else
							szName = p;
						#endif
					}
               
					if ( lstrlen( szName )) {
						TCHAR  szTitle[128];
						mir_sntprintf( szTitle, SIZEOF(szTitle), TranslateT("Add %s"), szName );
						SetWindowText( hdlg, szTitle );
					}
					else SetWindowText( hdlg, TranslateT("Add Contact"));
				}
				
				if ( acs->handleType == HANDLE_CONTACT && acs->handle ) {
					if ( acs->szProto == NULL || (acs->szProto != NULL && strcmp(acs->szProto,"") == 0) ) 
						acs->szProto = (char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)acs->handle,0);
				}

				for(groupId=0;groupId<999;groupId++)
				{
					_itoa(groupId,idstr,10);
					if(DBGetContactSettingTString(NULL,"CListGroups",idstr,&dbv)) break;
					SendDlgItemMessage(hdlg,IDC_GROUP,CB_ADDSTRING,0,(LPARAM)(dbv.ptszVal+1));
					DBFreeVariant(&dbv);
				}
				SendDlgItemMessage(hdlg,IDC_GROUP,CB_INSERTSTRING,0,(LPARAM)TranslateT("None"));
				SendDlgItemMessage(hdlg,IDC_GROUP,CB_SETCURSEL,0,0);
				/* acs->szProto may be NULL don't expect it */
				if (acs->szProto) flags=CallProtoService(acs->szProto,PS_GETCAPS,PFLAGNUM_4,0);
				else flags=0;
				if (flags&PF4_FORCEADDED) { // force you were added requests for this protocol
					CheckDlgButton(hdlg,IDC_ADDED,BST_CHECKED);
					EnableWindow(GetDlgItem(hdlg,IDC_ADDED),FALSE);
				}
				if (flags&PF4_FORCEAUTH) { // force auth requests for this protocol
					CheckDlgButton(hdlg,IDC_AUTH,BST_CHECKED);
					EnableWindow(GetDlgItem(hdlg,IDC_AUTH),FALSE);
				}
				if (flags&PF4_NOCUSTOMAUTH) {
					EnableWindow(GetDlgItem(hdlg,IDC_AUTHREQ),FALSE);
					EnableWindow(GetDlgItem(hdlg,IDC_AUTHGB),FALSE);
				}
				SetDlgItemText(hdlg,IDC_AUTHREQ,TranslateT("Please authorize my request and add me to your contact list."));
				EnableWindow(GetDlgItem(hdlg,IDC_AUTHREQ),IsDlgButtonChecked(hdlg,IDC_AUTH));
				EnableWindow(GetDlgItem(hdlg,IDC_AUTHGB),IsDlgButtonChecked(hdlg,IDC_AUTH));
			}
			break;

		case WM_COMMAND:
			acs=(ADDCONTACTSTRUCT *)GetWindowLong(hdlg,GWL_USERDATA);

			switch(LOWORD(wparam))
			{
				case IDC_AUTH:
				{
					DWORD flags=0;
					
					flags=CallProtoService(acs->szProto,PS_GETCAPS,PFLAGNUM_4,0);
					if (flags&PF4_NOCUSTOMAUTH) {
						EnableWindow(GetDlgItem(hdlg,IDC_AUTHREQ),FALSE);
						EnableWindow(GetDlgItem(hdlg,IDC_AUTHGB),FALSE);
					}
					else {
						EnableWindow(GetDlgItem(hdlg,IDC_AUTHREQ),IsDlgButtonChecked(hdlg,IDC_AUTH));
						EnableWindow(GetDlgItem(hdlg,IDC_AUTHGB),IsDlgButtonChecked(hdlg,IDC_AUTH));
					}
				}
					break;
				case IDOK:
					{
						HANDLE hcontact=INVALID_HANDLE_VALUE;
						if(acs->handleType==HANDLE_EVENT)
						{
							DBEVENTINFO dbei;
							ZeroMemory(&dbei,sizeof(dbei));
							dbei.cbSize=sizeof(dbei);
							dbei.cbBlob=0;
							CallService(MS_DB_EVENT_GET,(WPARAM)acs->handle,(LPARAM)&dbei);
							hcontact=(HANDLE)CallProtoService(dbei.szModule,PS_ADDTOLISTBYEVENT,0,(LPARAM)acs->handle);
						}
						else if(acs->handleType==HANDLE_SEARCHRESULT) 
							hcontact=(HANDLE)CallProtoService(acs->szProto,PS_ADDTOLIST,0,(LPARAM)acs->psr);													

						else if(acs->handleType==HANDLE_CONTACT)
							hcontact=acs->handle;
						
						if ( hcontact == NULL ) break;

						{	TCHAR szHandle[256];
							if ( GetDlgItemText( hdlg, IDC_MYHANDLE, szHandle, SIZEOF(szHandle)))
								DBWriteContactSettingTString( hcontact, "CList", "MyHandle", szHandle );

							GetDlgItemText( hdlg, IDC_GROUP, szHandle, SIZEOF(szHandle));
							if ( lstrcmp( szHandle, TranslateT( "None" )))
								DBWriteContactSettingTString( hcontact, "CList", "Group", szHandle );
						}

						if(IsDlgButtonChecked(hdlg,IDC_ADDED)) CallContactService(hcontact,PSS_ADDED,0,0);
						if(IsDlgButtonChecked(hdlg,IDC_AUTH)) {
							DWORD flags;
							flags=CallProtoService(acs->szProto,PS_GETCAPS,PFLAGNUM_4,0);
							if (flags&PF4_NOCUSTOMAUTH)	CallContactService(hcontact,PSS_AUTHREQUEST,0,(LPARAM)"");
							else {
								char szReason[256];

								GetDlgItemTextA(hdlg,IDC_AUTHREQ,szReason,256);
								CallContactService(hcontact,PSS_AUTHREQUEST,0,(LPARAM)szReason);
						}	}
						
						DBDeleteContactSetting(hcontact,"CList","NotOnList");
					}
					// fall through
				case IDCANCEL:
					if (GetParent(hdlg)==NULL) DestroyWindow(hdlg);
					else EndDialog(hdlg,0);
					break;
			}
			break;

		case WM_CLOSE:
			/* if there is no parent for the dialog, its a modeless dialog and can't be killed using EndDialog() */
			if(GetParent(hdlg)==NULL) DestroyWindow(hdlg); 
			else EndDialog(hdlg,0);
			break;

		case WM_DESTROY:			
			acs=(ADDCONTACTSTRUCT *)GetWindowLong(hdlg,GWL_USERDATA);
			if (acs) {
				if (acs->psr) {
					if (acs->psr->nick) mir_free(acs->psr->nick);
					if (acs->psr->firstName) mir_free(acs->psr->firstName);
					if (acs->psr->lastName) mir_free(acs->psr->lastName);
					if (acs->psr->email) mir_free(acs->psr->email);
					mir_free(acs->psr);
				}
				mir_free(acs);
			}
			break;
	}

	return FALSE;
}

int AddContactDialog(WPARAM wParam,LPARAM lParam)
{
	ADDCONTACTSTRUCT *acs;
	if (lParam) {	
		acs=mir_alloc(sizeof(ADDCONTACTSTRUCT));
		memmove(acs,(ADDCONTACTSTRUCT*)lParam,sizeof(ADDCONTACTSTRUCT));
		if (acs->psr) {
			PROTOSEARCHRESULT *psr;
			/* bad! structures that are bigger than psr will cause crashes if they define pointers within unreachable structural space */
			psr=mir_alloc(acs->psr->cbSize);
			memmove(psr,acs->psr,acs->psr->cbSize);
			if (psr->nick) psr->nick=mir_strdup(psr->nick);
			if (psr->firstName) psr->firstName=mir_strdup(psr->firstName);
			if (psr->lastName) psr->lastName=mir_strdup(psr->lastName);
			if (psr->email) psr->email=mir_strdup(psr->email);
			acs->psr=psr;
			/* copied the passed acs structure, the psr structure with, the pointers within that  */
		} //if
		if (wParam) {
			DialogBoxParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_ADDCONTACT),(HWND)wParam,AddContactDlgProc,(LPARAM)acs);
		} else {
			CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_ADDCONTACT),(HWND)wParam,AddContactDlgProc,(LPARAM)acs);
		} //if
		return 0;
	}
	return 1;
}

int LoadAddContactModule(void)
{
	CreateServiceFunction(MS_ADDCONTACT_SHOW,AddContactDialog);
	return 0;
}
