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
#include "../../include/m_history.h"

static LONG sttChatID = 0;
extern HANDLE hInitChat;

int MSN_ChatInit( WPARAM wParam, LPARAM lParam )
{
	ThreadData *info = (ThreadData*)wParam;
	InterlockedIncrement( &sttChatID );
	_ltot( sttChatID, info->mChatID, 10 );

	info->mJoinedContacts = ( HANDLE* )mir_realloc(info->mJoinedContacts, sizeof(HANDLE)*(++info->mJoinedCount));
	info->mJoinedContacts[info->mJoinedCount - 1] = info->mJoinedContacts[0];
	info->mJoinedContacts[0] = ( HANDLE )-sttChatID;

	TCHAR szName[ 512 ];
	mir_sntprintf( szName, SIZEOF( szName ), _T(TCHAR_STR_PARAM) _T(" %s%s"), 
		msnProtocolName, TranslateT( "Chat #" ), info->mChatID );

	GCSESSION gcw = {0};
	gcw.cbSize = sizeof(GCSESSION);
	gcw.dwFlags = GC_TCHAR;
	gcw.iType = GCW_CHATROOM;
	gcw.pszModule = msnProtocolName;
	gcw.ptszName = szName;
	gcw.ptszID = info->mChatID;
	MSN_CallService(MS_GC_NEWSESSION, NULL, (LPARAM)&gcw);

	GCDEST gcd = { msnProtocolName, NULL, GC_EVENT_ADDGROUP };
	gcd.ptszID = info->mChatID;
	GCEVENT gce = {0};
	gce.cbSize = sizeof(GCEVENT);
	gce.dwFlags = GC_TCHAR;
	gce.pDest = &gcd;
	gce.ptszStatus = TranslateT("Me");
	MSN_CallService(MS_GC_EVENT, NULL, (LPARAM)&gce);

	DBVARIANT dbv;
	int bError = MSN_GetStringT( "Nick", NULL, &dbv );
	if ( bError )
		dbv.ptszVal = _T("");

	gcd.iType = GC_EVENT_JOIN;
	gce.ptszNick = dbv.ptszVal;
	gce.ptszUID = a2t(MyOptions.szEmail);
	gce.time = 0;
	gce.bIsMe = TRUE;
	MSN_CallService(MS_GC_EVENT, NULL, (LPARAM)&gce);

	gcd.iType = GC_EVENT_ADDGROUP;
	gce.ptszStatus = TranslateT("Others");
	MSN_CallService(MS_GC_EVENT, NULL, (LPARAM)&gce);

	gcd.iType = GC_EVENT_CONTROL;
	MSN_CallService(MS_GC_EVENT, SESSION_INITDONE, (LPARAM)&gce);
	MSN_CallService(MS_GC_EVENT, SESSION_ONLINE, (LPARAM)&gce);
	MSN_CallService(MS_GC_EVENT, WINDOW_VISIBLE, (LPARAM)&gce);

	if ( !bError )
		MSN_FreeVariant( &dbv );
	mir_free((void*)gce.ptszUID );
	return 0;
}

void MSN_ChatStart(ThreadData* info)
{
	if ( info->mChatID[0] != 0 )
		return;

	NotifyEventHooks( hInitChat, (WPARAM)info, 0 );

	// add all participants onto the list
	GCDEST gcd = { msnProtocolName, NULL, GC_EVENT_JOIN };
	gcd.ptszID = info->mChatID;

	GCEVENT gce = {0};
	gce.cbSize = sizeof(GCEVENT);
	gce.dwFlags = GC_TCHAR | GCEF_ADDTOLOG;
	gce.pDest = &gcd;
	gce.ptszStatus = TranslateT("Others");
	gce.time = time(NULL);
	gce.bIsMe = FALSE;

	for ( int j=0; j < info->mJoinedCount; j++ ) {
		if (( long )info->mJoinedContacts[j] <= 0 )
			continue;

		gce.ptszNick = MSN_GetContactNameT( info->mJoinedContacts[j] );

		DBVARIANT dbv;
		if ( !MSN_GetStringT( "e-mail", info->mJoinedContacts[j], &dbv )) {
			gce.ptszUID = dbv.ptszVal;
			MSN_CallService( MS_GC_EVENT, NULL, ( LPARAM )&gce );
}	}	}

void MSN_KillChatSession( TCHAR* id )
{
	GCDEST gcd = { msnProtocolName, NULL, GC_EVENT_CONTROL };
	gcd.ptszID = id;
	GCEVENT gce = {0};
	gce.cbSize = sizeof(GCEVENT);
	gce.dwFlags = GC_TCHAR;
	gce.pDest = &gcd;
	MSN_CallService(MS_GC_EVENT, SESSION_OFFLINE, (LPARAM)&gce);
	MSN_CallService(MS_GC_EVENT, SESSION_TERMINATE, (LPARAM)&gce);
}

void InviteUser(ThreadData* info) {
	HMENU tMenu = ::CreatePopupMenu();
	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );

	// add the heading
	::AppendMenu(tMenu, MF_STRING|MF_GRAYED|MF_DISABLED, (UINT_PTR)0, TranslateT("&Invite user..."));
	::AppendMenu(tMenu, MF_SEPARATOR, (UINT_PTR)1, NULL);

	// generate a list of contact
	while ( hContact != NULL ) {
		if ( !lstrcmpA( msnProtocolName, ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM )hContact,0 ))) {
			if (DBGetContactSettingByte(hContact, msnProtocolName, "ChatRoom", 0) == 0) {
				if (MSN_GetWord(hContact, "Status", ID_STATUS_OFFLINE) != ID_STATUS_OFFLINE) {
					BOOL alreadyInSession = FALSE;
					for ( int j=0; j < info->mJoinedCount; j++ ) {
						if (info->mJoinedContacts[j] == hContact) {
							alreadyInSession = TRUE;
							break;
						}
					}
					if (!alreadyInSession)
						::AppendMenu(tMenu, MF_STRING, (UINT_PTR)hContact, MSN_GetContactNameT(hContact));
				}
		}	}
		hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT,( WPARAM )hContact, 0 );
	}

	HWND tWindow = CreateWindow(_T("EDIT"),_T(""),0,1,1,1,1,NULL,NULL,hInst,NULL);

	POINT pt;
	::GetCursorPos ( &pt );
	HANDLE hInvitedUser = (HANDLE)::TrackPopupMenu( tMenu, TPM_NONOTIFY | TPM_LEFTALIGN | TPM_TOPALIGN | TPM_RETURNCMD, pt.x, pt.y, 0, tWindow, NULL );
	::DestroyMenu( tMenu );
	::DestroyWindow( tWindow );

	if ( !hInvitedUser )
		return;

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", ( HANDLE )hInvitedUser, tEmail, sizeof( tEmail ))) {
		info->sendPacket( "CAL", tEmail );
		MSN_ChatStart(info);
}	}

int MSN_GCEventHook(WPARAM wParam,LPARAM lParam) {
	GCHOOK *gch = (GCHOOK*) lParam;
	if ( !gch )
		return 1;

	if ( !lstrcmpiA(gch->pDest->pszModule, msnProtocolName )) {
		switch (gch->pDest->iType) {
		case GC_SESSION_TERMINATE: {
			int chatID = _ttoi( gch->pDest->ptszID );
			ThreadData* thread = MSN_GetThreadByContact((HANDLE)-chatID);
			if ( thread != NULL ) {
				// open up srmm dialog when quit while 1 person left
				if ( thread->mJoinedCount == 1 ) {
					// switch back to normal session
					thread->mJoinedContacts[0] = thread->mJoinedContacts[1];
					thread->mJoinedContacts = ( HANDLE* )mir_realloc( thread->mJoinedContacts, sizeof( HANDLE ) );
					MSN_CallService(MS_MSG_SENDMESSAGE, (WPARAM)thread->mJoinedContacts[0], 0);
					thread->mChatID[0] = 0;
				}
				else thread->sendPacket( "OUT", NULL );
			}
			break;
		}
		case GC_USER_MESSAGE:
			if ( gch && gch->pszText && lstrlenA( gch->pszText ) > 0 ) {
				rtrim( gch->ptszText ); // remove the ending linebreak
				{	
					TCHAR* pszMsg = UnEscapeChatTags( NEWTSTR_ALLOCA( gch->ptszText ));

					CCSDATA ccs = {0};
					ccs.hContact = (HANDLE)-_ttoi(gch->pDest->ptszID);
					ccs.wParam = PREF_TCHAR;

					#if defined( _UNICODE )
						int cbLen = WideCharToMultiByte( CP_ACP, 0, pszMsg, -1, NULL, 0, 0, 0 );
						int cbLenW = lstrlen( pszMsg );
						char* msgBuf = ( char* )alloca( --cbLen+1 + (cbLenW+1)*sizeof( TCHAR ));
						WideCharToMultiByte( CP_ACP, 0, pszMsg, -1, msgBuf, cbLen+1, 0, 0 );
						msgBuf[ cbLen ] = 0;
						memcpy( msgBuf+cbLen+1, pszMsg, (cbLenW+1)*sizeof( TCHAR ));
						ccs.lParam = (LPARAM)msgBuf;
					#else
						ccs.lParam = (LPARAM)pszMsg;
					#endif

					CallProtoService(msnProtocolName, PSS_MESSAGE, 0, (LPARAM)&ccs);
				}

				DBVARIANT dbv;
				int bError = DBGetContactSettingTString( NULL, msnProtocolName, "Nick", &dbv );
				if ( bError )
					dbv.ptszVal = _T("");

				GCDEST gcd = { msnProtocolName, 0, GC_EVENT_MESSAGE };
				gcd.ptszID = gch->pDest->ptszID;

				GCEVENT gce = {0};
				gce.cbSize = sizeof(GCEVENT);
				gce.dwFlags = GC_TCHAR | GCEF_ADDTOLOG;
				gce.pDest = &gcd;
				gce.ptszNick = dbv.ptszVal;
				gce.ptszUID = a2t(MyOptions.szEmail);
				gce.time = time(NULL);
				gce.ptszText = gch->ptszText;
				gce.bIsMe = TRUE;
				MSN_CallService(MS_GC_EVENT, NULL, (LPARAM)&gce);

				if ( !bError )
					MSN_FreeVariant( &dbv );
			}
			break;
		case GC_USER_CHANMGR: {
			int chatID = _ttoi( gch->pDest->ptszID );
			ThreadData* thread = MSN_GetThreadByContact((HANDLE)-chatID);
			if ( thread != NULL ) {
				InviteUser(thread);
			}
			break;
		}
		case GC_USER_PRIVMESS: {
			HANDLE hContact = MSN_HContactFromEmail((char*)gch->pszUID, NULL, 0, 0);
			MSN_CallService(MS_MSG_SENDMESSAGE, (WPARAM)hContact, 0);
			break;
		}
		case GC_USER_LOGMENU:
			switch(gch->dwData) {
			case 10: {
				int chatID = _ttoi( gch->pDest->ptszID );
				ThreadData* thread = MSN_GetThreadByContact((HANDLE)-chatID);
				if ( thread != NULL )
					InviteUser( thread );

				break;
			}
			case 20:
				MSN_KillChatSession( gch->pDest->ptszID );
				break;
			}
			break;
		case GC_USER_NICKLISTMENU: {
			HANDLE hContact = MSN_HContactFromEmailT( gch->ptszUID );

			switch(gch->dwData) {
			case 10:
				MSN_CallService(MS_USERINFO_SHOWDIALOG, (WPARAM)hContact, 0);
				break;
			case 20:
				MSN_CallService(MS_HISTORY_SHOWCONTACTHISTORY, (WPARAM)hContact, 0);
				break;
			case 110:
				MSN_KillChatSession( gch->pDest->ptszID );
				break;
			}
			break;
		}
/*	haven't implemented in chat.dll
		case GC_USER_TYPNOTIFY: {
			int chatID = atoi(p);
			ThreadData* thread = MSN_GetThreadByContact((HANDLE)-chatID);
			for ( int j=0; j < thread->mJoinedCount; j++ ) {
				if (( long )thread->mJoinedContacts[j] > 0 )
					CallService(MS_PROTO_SELFISTYPING, (WPARAM) thread->mJoinedContacts[j], (LPARAM) PROTOTYPE_SELFTYPING_ON);
			}
				break;
			}
*/
	}	}

	return 0;
}

int MSN_GCMenuHook(WPARAM wParam,LPARAM lParam) {
	GCMENUITEMS *gcmi= (GCMENUITEMS*) lParam;

	if ( gcmi ) {
		if ( !lstrcmpiA(gcmi->pszModule, msnProtocolName )) {
			if ( gcmi->Type == MENU_ON_LOG ) {
				static struct gc_item Items[] = {
					{ TranslateT("&Invite user..."), 10, MENU_ITEM, FALSE },
					{ TranslateT("&Leave chat session"), 20, MENU_ITEM, FALSE }
				};
				gcmi->nItems = SIZEOF(Items);
				gcmi->Item = Items;
			}
			if ( gcmi->Type == MENU_ON_NICKLIST ) {
				if ( !lstrcmpA(MyOptions.szEmail, (char *)gcmi->pszUID)) {
					static struct gc_item Items[] = {
						{ TranslateT("User &details"), 10, MENU_ITEM, FALSE },
						{ TranslateT("User &history"), 20, MENU_ITEM, FALSE },
						{ TranslateT(""), 100, MENU_SEPARATOR, FALSE },
						{ TranslateT("&Leave chat session"), 110, MENU_ITEM, FALSE }
					};
					gcmi->nItems = SIZEOF(Items);
					gcmi->Item = Items;
				}
				else {
					static struct gc_item Items[] = {
						{ TranslateT("User &details"), 10, MENU_ITEM, FALSE },
						{ TranslateT("User &history"), 20, MENU_ITEM, FALSE }
					};
					gcmi->nItems = SIZEOF(Items);
					gcmi->Item = Items;
	}	}	}	}

	return 0;
}
