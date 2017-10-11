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

#include <fcntl.h>
#include <io.h>
#include <sys/stat.h>

#include "resource.h"

void __cdecl MSNServerThread( ThreadData* info );

void MSN_ChatStart(ThreadData* info);
void MSN_KillChatSession(TCHAR* id);

void msnftp_sendAcceptReject( filetransfer *ft, bool acc );

HANDLE msnBlockMenuItem = NULL;
extern char* profileURL;

/////////////////////////////////////////////////////////////////////////////////////////
// MsnAddToList - adds contact to the server list

static HANDLE AddToListByEmail( const char *email, DWORD flags )
{
	char urlEmail[ 255 ], *szProto;
	UrlEncode( email, urlEmail, sizeof( urlEmail ));
	HANDLE hContact;

	//check not already on list
	for ( hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
		   hContact != NULL;
			hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 ))
	{
		szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM )hContact, 0 );
		if ( szProto != NULL && !strcmp( szProto, msnProtocolName )) {
			char tEmail[ MSN_MAX_EMAIL_LEN ];
			if ( MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail )))
				continue;

			if ( strcmp( tEmail, email ))
				continue;

			if ( !( flags & PALF_TEMPORARY ) && DBGetContactSettingByte( hContact, "CList", "NotOnList", 1 )) {
				DBDeleteContactSetting( hContact, "CList", "NotOnList" );
				DBDeleteContactSetting( hContact, "CList", "Hidden" );
LBL_AddContact:
				if ( msnLoggedIn ) {
					MSN_AddUser( hContact, urlEmail, LIST_FL );
					MSN_AddUser( hContact, urlEmail, LIST_BL + LIST_REMOVE );
					MSN_AddUser( hContact, urlEmail, LIST_AL );
				}
				else hContact = NULL;
			}

			return hContact;
	}	}

	//not already there: add
	hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_ADD, 0, 0 );
	MSN_CallService( MS_PROTO_ADDTOCONTACT, ( WPARAM )hContact,( LPARAM )msnProtocolName );
	MSN_SetString( hContact, "e-mail", email );

	if ( !( flags & PALF_TEMPORARY ))
		goto LBL_AddContact;

	DBWriteContactSettingByte( hContact, "CList", "NotOnList", 1 );
	DBWriteContactSettingByte( hContact, "CList", "Hidden", 1 );
	return hContact;
}

static int MsnAddToList(WPARAM wParam,LPARAM lParam)
{
	PROTOSEARCHRESULT *psr=(PROTOSEARCHRESULT*)lParam;

	if ( psr->cbSize != sizeof( PROTOSEARCHRESULT ))
		return NULL;

	return ( int )AddToListByEmail( psr->email, wParam );
}

static int MsnAddToListByEvent(WPARAM wParam, LPARAM lParam)
{
	DBEVENTINFO dbei = {0};
	dbei.cbSize = sizeof(dbei);
	if (( dbei.cbBlob = MSN_CallService( MS_DB_EVENT_GETBLOBSIZE, lParam, 0 )) == (DWORD)(-1))
		return 0;

	dbei.pBlob=(PBYTE) alloca(dbei.cbBlob);
	if ( MSN_CallService(MS_DB_EVENT_GET, lParam, ( LPARAM )&dbei))	return 0;
	if ( strcmp(dbei.szModule, msnProtocolName))						      return 0;
	if ( dbei.eventType != EVENTTYPE_AUTHREQUEST)					      return 0;

	char* nick = (char *) (dbei.pBlob + sizeof(DWORD) + sizeof(HANDLE));
	char* firstName = nick + strlen(nick) + 1;
	char* lastName = firstName + strlen(firstName) + 1;
	char* email = lastName + strlen(lastName) + 1;

	return ( int ) AddToListByEmail( email, 0 );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnAuthAllow - called after successful authorization

static int MsnAuthAllow(WPARAM wParam,LPARAM lParam)
{
	if ( !msnLoggedIn )
		return 1;

	DBEVENTINFO dbei;
	memset( &dbei, 0, sizeof( dbei ));
	dbei.cbSize = sizeof( dbei );

	if (( dbei.cbBlob = MSN_CallService( MS_DB_EVENT_GETBLOBSIZE, wParam, 0 )) == -1 )
		return 1;

	dbei.pBlob = ( PBYTE )alloca( dbei.cbBlob );
	if ( MSN_CallService( MS_DB_EVENT_GET, wParam, ( LPARAM )&dbei ))
		return 1;

	if ( dbei.eventType != EVENTTYPE_AUTHREQUEST )
		return 1;

	if ( strcmp( dbei.szModule, msnProtocolName ))
		return 1;

	char* nick = ( char* )( dbei.pBlob + sizeof( DWORD )*2 );
	char* firstName = nick + strlen( nick ) + 1;
	char* lastName = firstName + strlen( firstName ) + 1;
	char* email = lastName + strlen( lastName ) + 1;

	char urlNick[388],urlEmail[130];

	UrlEncode( UTF8( nick ), urlNick, sizeof( urlNick ));
	UrlEncode( email, urlEmail, sizeof( urlEmail ));

	AddToListByEmail( email, 0 );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnAuthDeny - called after unsuccessful authorization

static int MsnAuthDeny(WPARAM wParam,LPARAM lParam)
{
	if ( !msnLoggedIn )
		return 1;

	DBEVENTINFO dbei;
	memset( &dbei, 0, sizeof( dbei ));
	dbei.cbSize = sizeof( dbei );

	if (( dbei.cbBlob = MSN_CallService( MS_DB_EVENT_GETBLOBSIZE, wParam, 0 )) == -1 )
		return 1;

	dbei.pBlob = ( PBYTE )alloca( dbei.cbBlob );
	if ( MSN_CallService( MS_DB_EVENT_GET, wParam, ( LPARAM )&dbei ))
		return 1;

	if ( dbei.eventType != EVENTTYPE_AUTHREQUEST )
		return 1;

	if ( strcmp( dbei.szModule, msnProtocolName ))
		return 1;

	char* nick = ( char* )( dbei.pBlob + sizeof( DWORD )*2 );
	char* firstName = nick + strlen( nick ) + 1;
	char* lastName = firstName + strlen( firstName ) + 1;
	char* email = lastName + strlen( lastName ) + 1;

	char urlNick[388],urlEmail[130];

	UrlEncode( UTF8(nick), urlNick, sizeof( urlNick ));
	UrlEncode( email, urlEmail, sizeof( urlEmail ));

	MSN_AddUser( NULL, urlEmail, LIST_BL );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnBasicSearch - search contacts by e-mail

static int MsnBasicSearch(WPARAM wParam,LPARAM lParam)
{
	if ( !msnLoggedIn || msnSearchID != -1 )
		return 0;

	char tEmail[ 256 ];
	UrlEncode(( char* )lParam, tEmail, sizeof( tEmail ));
	return msnSearchID = msnNsThread->sendPacket( "ADC", "BL N=%s", tEmail );
}

/////////////////////////////////////////////////////////////////////////////////////////
// Block command callback function

static int MsnBlockCommand( WPARAM wParam, LPARAM lParam )
{
	if ( msnLoggedIn ) {
		char tEmail[ MSN_MAX_EMAIL_LEN ];
		if ( !MSN_GetStaticString( "e-mail", ( HANDLE )wParam, tEmail, sizeof( tEmail )))
			MSN_SetWord(( HANDLE )wParam, "ApparentMode", ( Lists_IsInList( LIST_BL, tEmail )) ? 0 : ID_STATUS_OFFLINE );
	}
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnContactDeleted - called when a contact is deleted from list

static int MsnContactDeleted( WPARAM wParam, LPARAM lParam )
{
	if ( !msnLoggedIn )  //should never happen for MSN contacts
		return 0;

	char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, wParam, 0 );
	if ( szProto == NULL || strcmp( szProto, msnProtocolName ))
		return 0;

	HANDLE hContact = ( HANDLE )wParam;
	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail ))) {
		MSN_AddUser( hContact, tEmail, LIST_FL | LIST_REMOVE );
		MSN_AddUser( hContact, tEmail, LIST_AL | LIST_REMOVE );

		if ( !Lists_IsInList( LIST_RL, tEmail )) {
			MSN_AddUser( hContact, tEmail, LIST_BL | LIST_REMOVE );
			Lists_Remove( 0xFF, tEmail );
		}
		else {
			MSN_AddUser( hContact, tEmail, LIST_BL );
			Lists_Remove( LIST_FL, tEmail );
		}	
	}

	int type = DBGetContactSettingByte( hContact, msnProtocolName, "ChatRoom", 0 );
	if ( type != 0 ) {
		DBVARIANT dbv;
		if ( !MSN_GetStringT( "ChatRoomID", hContact, &dbv )) {
			MSN_KillChatSession( dbv.ptszVal );
			MSN_FreeVariant( &dbv );
	}	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnDbSettingChanged - look for contact's settings changes

static int MsnDbSettingChanged(WPARAM wParam,LPARAM lParam)
{
	HANDLE hContact = ( HANDLE )wParam;
	DBCONTACTWRITESETTING* cws = ( DBCONTACTWRITESETTING* )lParam;

	if ( !msnLoggedIn )
		return 0;

	if ( hContact == NULL && MyOptions.ManageServer && !strcmp( cws->szModule, "CListGroups" )) {
		int iNumber = atol( cws->szSetting );
		LPCSTR szId = MSN_GetGroupByNumber( iNumber );
		if ( szId == NULL ) {
			if ( cws->value.type == DBVT_ASCIIZ )
				MSN_AddServerGroup( cws->value.pszVal+1, hContact );
			return 0;
		}

		switch ( cws->value.type ) {
			case DBVT_DELETED:	msnNsThread->sendPacket( "RMG", szId );										break;
			case DBVT_UTF8:		MSN_RenameServerGroup( iNumber, szId, cws->value.pszVal+1 );			break;
			case DBVT_ASCIIZ:		MSN_RenameServerGroup( iNumber, szId, UTF8( cws->value.pszVal+1 ));	break;
		}
		return 0;
	}

	if ( !strcmp( cws->szSetting, "ApparentMode" )) {
		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM ) hContact, 0 );
		if ( szProto == NULL || strcmp( szProto, msnProtocolName ))
			return 0;

		char tEmail[ MSN_MAX_EMAIL_LEN ];
		if ( !MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail ))) {
			int isBlocked = Lists_IsInList( LIST_BL, tEmail );

			if ( !isBlocked && cws->value.wVal == ID_STATUS_OFFLINE ) {
				MSN_AddUser( hContact, tEmail, LIST_AL + LIST_REMOVE );
				MSN_AddUser( hContact, tEmail, LIST_BL );
			}
			else if ( isBlocked && cws->value.wVal == 0 ) {
				MSN_AddUser( hContact, tEmail, LIST_BL + LIST_REMOVE );
				MSN_AddUser( hContact, tEmail, LIST_AL );
	}	}	}

	if ( !strcmp( cws->szModule, "CList" )) {
		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM ) hContact, 0 );
		if ( szProto == NULL || strcmp( szProto, msnProtocolName ))
			return 0;

		if ( !strcmp( cws->szSetting, "Group" )) {
			switch( cws->value.type ) {
				case DBVT_DELETED:	MSN_MoveContactToGroup( hContact, NULL );								break;
				case DBVT_ASCIIZ:    MSN_MoveContactToGroup( hContact, UTF8(cws->value.pszVal));		break;
				case DBVT_UTF8:		MSN_MoveContactToGroup( hContact, cws->value.pszVal );			break;
			}
			return 0;
		}

		if ( !strcmp( cws->szSetting, "MyHandle" )) {
			char szContactID[ 100 ], szNewNick[ 387 ];
			if ( !MSN_GetStaticString( "ID", hContact, szContactID, sizeof( szContactID ))) {
				MSN_GetStaticString( "e-mail", hContact, szNewNick, sizeof( szNewNick ));
				if ( strcmp( szNewNick, MyOptions.szEmail )) {
					if ( cws->value.type != DBVT_DELETED ) {
						if ( cws->value.type == DBVT_UTF8 )
							UrlEncode( cws->value.pszVal, szNewNick, sizeof( szNewNick ));
						else
							UrlEncode( UTF8(cws->value.pszVal), szNewNick, sizeof( szNewNick ));
					}
					msnNsThread->sendPacket( "SBP", "%s MFN %s", szContactID, szNewNick );
				}
				return 0;
	}	}	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnWindowEvent - goes to the Profile section at the Hotmail.com

int MsnIdleChanged(WPARAM wParam, LPARAM lParam)
{
	if ( !msnLoggedIn || msnDesiredStatus != ID_STATUS_ONLINE )
		return 0;

    if ( lParam & IDF_PRIVACY ) {
		if ( msnStatusMode == ID_STATUS_IDLE )
			MSN_SetServerStatus( ID_STATUS_ONLINE );
	}
    else 
		MSN_SetServerStatus( lParam & IDF_ISIDLE ? ID_STATUS_IDLE : ID_STATUS_ONLINE );

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnWindowEvent - goes to the Profile section at the Hotmail.com

int MsnWindowEvent(WPARAM wParam, LPARAM lParam)
{
	MessageWindowEventData* msgEvData  = (MessageWindowEventData*)lParam;

	char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM )msgEvData->hContact, 0 );
	if ( lstrcmpA( msnProtocolName, szProto )) return 0;

	switch( msgEvData->uType ) {
		case MSG_WINDOW_EVT_OPENING:
			if ( MSN_GetThreadByContact( msgEvData->hContact )   == NULL &&
				 MSN_GetUnconnectedThread( msgEvData->hContact ) == NULL ) 
			{
				msnNsThread->sendPacket( "XFR", "SB" );
				MsgQueue_Add( msgEvData->hContact, 'X', "None", 0, NULL );
			}
			break;
/*
		case MSG_WINDOW_EVT_CLOSING:
			{
				ThreadData* info = MSN_GetThreadByContact( msgEvData->hContact );
				if ( info != NULL ) {
					if ( p2p_getThreadSession( msgEvData->hContact, SERVER_SWITCHBOARD ) == NULL )
						info->sendPacket( "OUT", NULL );
				}
				else {
					info = MSN_GetUnconnectedThread( msgEvData->hContact );
					if ( info != NULL ) 
						info->sendPacket( "OUT", NULL );
				}
				break;
			}
*/
	}
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnEditProfile - goes to the Profile section at the Hotmail.com

static int MsnEditProfile( WPARAM, LPARAM )
{
	char tUrl[ 4096 ];
	mir_snprintf( tUrl, sizeof( tUrl ), "%s&did=1&t=%s&js=yes", profileURL, MSPAuth );
	MSN_CallService( MS_UTILS_OPENURL, TRUE, ( LPARAM )tUrl );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnFileAllow - starts the file transfer

static void __cdecl MsnFileAckThread( void* arg )
{
	filetransfer* ft = (filetransfer*)arg;
	if ( !ft->inmemTransfer ) {
		char filefull[ MAX_PATH ];
		mir_snprintf( filefull, sizeof filefull, "%s\\%s", ft->std.workingDir, ft->std.currentFile );
		replaceStr( ft->std.currentFile, filefull );

		if ( MSN_SendBroadcast( ft->std.hContact, ACKTYPE_FILE, ACKRESULT_FILERESUME, ft, ( LPARAM )&ft->std ))
			return;

		if ( ft->wszFileName != NULL ) {
			mir_free( ft->wszFileName );
			ft->wszFileName = NULL;
	}	}

	bool fcrt = ft->create() != -1;

	if ( ft->p2p_appID != 0) {
		p2p_sendStatus( ft, MSN_GetP2PThreadByContact( ft->std.hContact ), fcrt ? 200 : 603 );
		if ( fcrt )
			p2p_sendFeedStart( ft );
	}
	else
		msnftp_sendAcceptReject (ft, fcrt);

	MSN_SendBroadcast( ft->std.hContact, ACKTYPE_FILE, ACKRESULT_INITIALISING, ft, 0);
}

int MsnFileAllow(WPARAM wParam, LPARAM lParam)
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	filetransfer* ft = ( filetransfer* )ccs->wParam;

	if ( !msnLoggedIn || !p2p_sessionRegistered( ft ))
		return 0;

	if (( ft->std.workingDir = mir_strdup(( char* )ccs->lParam )) == NULL ) {
		char szCurrDir[ MAX_PATH ];
		GetCurrentDirectoryA( sizeof( szCurrDir ), szCurrDir );
		ft->std.workingDir = mir_strdup( szCurrDir );
	}
	else {
		int len = strlen( ft->std.workingDir )-1;
		if ( ft->std.workingDir[ len ] == '\\' )
			ft->std.workingDir[ len ] = 0;
	}

	mir_forkthread( MsnFileAckThread, ft );

	return int( ft );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnFileCancel - cancels the active file transfer

int MsnFileCancel(WPARAM wParam, LPARAM lParam)
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	filetransfer* ft = ( filetransfer* )ccs->wParam;

	if ( !msnLoggedIn || !p2p_sessionRegistered( ft ))
		return 0;

	ThreadData* thread = MSN_GetP2PThreadByContact( ft->std.hContact );
	if  ( !ft->std.sending && ft->fileId == -1 ) {
		if ( ft->p2p_appID != 0 )
			p2p_sendStatus(ft, thread, 603);
		else
			msnftp_sendAcceptReject (ft, false);
	}
	else {
		ft->bCanceled = true;
		if ( ft->p2p_appID != 0 )
			p2p_sendCancel( thread, ft );
	}

	ft->std.files = NULL;
	ft->std.totalFiles = 0;
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnFileDeny - rejects the file transfer request

int MsnFileDeny( WPARAM wParam, LPARAM lParam )
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	filetransfer* ft = ( filetransfer* )ccs->wParam;

	if ( !msnLoggedIn || !p2p_sessionRegistered( ft ))
		return 1;

	ThreadData* thread = MSN_GetP2PThreadByContact( ft->std.hContact );
	if ( !ft->std.sending && ft->fileId == -1 ) {
		if ( ft->p2p_appID != 0 )
			p2p_sendStatus(ft, thread, 603);
		else
			msnftp_sendAcceptReject (ft, false);
	}
	else {
		if ( ft->p2p_appID != 0 )
			p2p_sendCancel( thread, ft );
		else
			ft->bCanceled = true;
	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnFileResume - renames a file

int MsnFileResume( WPARAM wParam, LPARAM lParam )
{
	filetransfer* ft = ( filetransfer* )wParam;
	
	if ( !msnLoggedIn || !p2p_sessionRegistered( ft ))
		return 1;

	PROTOFILERESUME *pfr = (PROTOFILERESUME*)lParam;
	switch (pfr->action) {
	case FILERESUME_SKIP:
		if ( ft->p2p_appID != 0 )
			p2p_sendStatus( ft, MSN_GetP2PThreadByContact( ft->std.hContact ), 603 );
		else 
			msnftp_sendAcceptReject (ft, false);
		break;

	case FILERESUME_RENAME:
		if ( ft->wszFileName != NULL ) {
			mir_free( ft->wszFileName );
			ft->wszFileName = NULL;
		}
		replaceStr( ft->std.currentFile, pfr->szFilename );
	
	default:
		bool fcrt = ft->create() != -1;
		if ( ft->p2p_appID != 0 ) {
			p2p_sendStatus( ft, MSN_GetP2PThreadByContact( ft->std.hContact ), fcrt ? 200 : 603 );
			if ( fcrt )
				p2p_sendFeedStart( ft );
		}
		else 
			msnftp_sendAcceptReject (ft, fcrt);

		MSN_SendBroadcast( ft->std.hContact, ACKTYPE_FILE, ACKRESULT_INITIALISING, ft, 0);
		break;
	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetAvatar - retrieves the file name of my own avatar

int MsnGetAvatar(WPARAM wParam, LPARAM lParam)
{
	char* buf = ( char* )wParam;
	int  size = ( int )lParam;

	if ( buf == NULL || size <= 0 )
		return -1;

	if ( !MyOptions.EnableAvatars )
		return -2;

	MSN_GetAvatarFileName( NULL, buf, size );
	return 0;
}	

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetAvatarFormatSupported - Msn supports avatars of virtually all formats

int MsnGetAvatarFormatSupported(WPARAM wParam, LPARAM lParam)
{
	return (lParam == PA_FORMAT_PNG) ? 1 : 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetAvatarInfo - retrieve the avatar info

static int MsnGetAvatarInfo(WPARAM wParam,LPARAM lParam)
{
	PROTO_AVATAR_INFORMATION* AI = ( PROTO_AVATAR_INFORMATION* )lParam;

	if ( !MyOptions.EnableAvatars || ( MSN_GetDword( AI->hContact, "FlagBits", 0 ) & 0x70000000 ) == 0 )
		return GAIR_NOAVATAR;

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", AI->hContact, tEmail, sizeof( tEmail )) && !strcmp( tEmail, MyOptions.szEmail )) 
		return GAIR_NOAVATAR;

	char szContext[ MAX_PATH ];
	if ( MSN_GetStaticString(( AI->hContact == NULL ) ? "PictObject" : "PictContext", AI->hContact, szContext, sizeof szContext ))
		return GAIR_NOAVATAR;

	MSN_GetAvatarFileName( AI->hContact, AI->filename, sizeof AI->filename );
	AI->format = ( AI->hContact == NULL ) ? PA_FORMAT_PNG : PA_FORMAT_BMP;

	if ( ::access( AI->filename, 0 ) == 0 ) {
		char szSavedContext[ 256 ];
		if ( !MSN_GetStaticString( "PictSavedContext", AI->hContact, szSavedContext, sizeof( szSavedContext )))
			if ( !strcmp( szSavedContext, szContext ))
				return GAIR_SUCCESS;
	}

	if (( wParam & GAIF_FORCE ) != 0 && AI->hContact != NULL ) {
		p2p_invite( AI->hContact, MSN_APPID_AVATAR );
		return GAIR_WAITFOR;
	}

	return GAIR_NOAVATAR;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetAvatarMaxSize - retrieves the optimal avatar size

int MsnGetAvatarMaxSize(WPARAM wParam, LPARAM lParam)
{
	if (wParam != 0) *((int*) wParam) = 96;
	if (lParam != 0) *((int*) lParam) = 96;
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetAwayMsg - reads the current status message for a user

static void __cdecl MsnGetAwayMsgThread( HANDLE hContact )
{
	DBVARIANT dbv;
	if ( !DBGetContactSetting( hContact, "CList", "StatusMsg", &dbv )) {
		MSN_SendBroadcast( hContact, ACKTYPE_AWAYMSG, ACKRESULT_SUCCESS, ( HANDLE )1, ( LPARAM )dbv.pszVal );
		MSN_FreeVariant( &dbv );
	}
	else MSN_SendBroadcast( hContact, ACKTYPE_AWAYMSG, ACKRESULT_SUCCESS, ( HANDLE )1, ( LPARAM )0 );
}

static int MsnGetAwayMsg(WPARAM wParam,LPARAM lParam)
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	if ( ccs == NULL )
		return 0;

	mir_forkthread( MsnGetAwayMsgThread, ccs->hContact );
	return 1;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetCaps - obtain the protocol capabilities

static int MsnGetCaps(WPARAM wParam,LPARAM lParam)
{
	switch( wParam ) {
	case PFLAGNUM_1:
	{	int result = PF1_IM | PF1_SERVERCLIST | PF1_AUTHREQ | PF1_BASICSEARCH |
				 PF1_ADDSEARCHRES | PF1_SEARCHBYEMAIL | PF1_USERIDISEMAIL |
				 PF1_FILESEND | PF1_FILERECV | PF1_URLRECV | PF1_VISLIST;
		if ( MyOptions.UseMSNP11 )
			result |= PF1_MODEMSG;
		return result;
	}
	case PFLAGNUM_2:
		return PF2_ONLINE | PF2_SHORTAWAY | PF2_LONGAWAY | PF2_LIGHTDND |
				 PF2_ONTHEPHONE | PF2_OUTTOLUNCH | PF2_INVISIBLE;

	case PFLAGNUM_3:
		return PF2_ONLINE | PF2_SHORTAWAY | PF2_LONGAWAY | PF2_LIGHTDND |
				 PF2_ONTHEPHONE | PF2_OUTTOLUNCH;

	case PFLAGNUM_4:
		return PF4_SUPPORTTYPING | PF4_AVATARS;

	case PFLAG_UNIQUEIDTEXT:
		return ( int )MSN_Translate( "E-mail address" );

	case PFLAG_UNIQUEIDSETTING:
		return ( int )"e-mail";

	case PFLAG_MAXLENOFMESSAGE:
		return 1202;
	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetInfo - nothing to do, cause we cannot obtain information from the server

static void sttInfoAck( HANDLE hContact )
{
	Sleep( 150 );
	MSN_SendBroadcast( hContact, ACKTYPE_GETINFO, ACKRESULT_SUCCESS, ( HANDLE )1, 0 );
}

static int MsnGetInfo( WPARAM wParam, LPARAM lParam )
{
	CCSDATA *ccs = ( CCSDATA* )lParam;
	mir_forkthread( sttInfoAck, ccs->hContact );
	return 1;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetName - obtain the protocol name

static int MsnGetName( WPARAM wParam, LPARAM lParam )
{
	lstrcpynA(( char* )lParam, msnProtocolName, wParam );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGetStatus - obtain the protocol status

static int MsnGetStatus(WPARAM wParam,LPARAM lParam)
{
	return msnStatusMode;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnGotoInbox - goes to the Inbox folder at the Hotmail.com

static int MsnGotoInbox( WPARAM, LPARAM )
{
	MsnShowMailThread( NULL );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnInviteCommand - invite command callback function

static int MsnInviteCommand( WPARAM wParam, LPARAM lParam )
{
	ThreadData* tActiveThreads[ 64 ];
	int tThreads = MSN_GetActiveThreads( tActiveThreads ), tChosenThread;
// modified for chat


	switch( tThreads ) {
	case 0:
		MessageBoxA(NULL, Translate("No active chat session is found."), Translate("MSN Chat"), MB_OK|MB_ICONINFORMATION);
		return 0;

	case 1:
		tChosenThread = 0;
		break;

	default:
		HMENU tMenu = ::CreatePopupMenu();

		for ( int i=0; i < tThreads; i++ ) {
			if (( long )tActiveThreads[i]->mJoinedContacts[0] < 0 ) {
				char sessionName[ 255 ];
				mir_snprintf( sessionName, sizeof( sessionName ), "%s %s%s",
					msnProtocolName, Translate( "Chat #" ), tActiveThreads[i]->mChatID );
				::AppendMenuA( tMenu, MF_STRING, ( UINT_PTR )( i+1 ), sessionName );
			}
			else ::AppendMenu( tMenu, MF_STRING, ( UINT_PTR )( i+1 ), MSN_GetContactNameT( *tActiveThreads[i]->mJoinedContacts ));
		}

		HWND tWindow = CreateWindow(_T("EDIT"),_T(""),0,1,1,1,1,NULL,NULL,hInst,NULL);

		POINT pt;
		::GetCursorPos ( &pt );
		tChosenThread = ::TrackPopupMenu( tMenu, TPM_NONOTIFY | TPM_LEFTALIGN | TPM_TOPALIGN | TPM_RETURNCMD, pt.x, pt.y, 0, tWindow, NULL );
		::DestroyMenu( tMenu );
		::DestroyWindow( tWindow );
		if ( !tChosenThread )
			return 0;

		tChosenThread--;
	}

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", ( HANDLE )wParam, tEmail, sizeof( tEmail ))) {
		if ( !strcmp( tEmail, MyOptions.szEmail ))
			return 0;

		for ( int j=0; j < tActiveThreads[ tChosenThread ]->mJoinedCount; j++ ) {
			// if the user is already in the chat session
			if ( tActiveThreads[ tChosenThread ]->mJoinedContacts[j] == ( HANDLE )wParam ) {
				MessageBoxA(NULL, Translate("User is already in the chat session."), Translate("MSN Chat"), MB_OK|MB_ICONINFORMATION);
				return 0;
		}	}

		tActiveThreads[ tChosenThread ]->sendPacket( "CAL", tEmail );

		if ( msnHaveChatDll )
			MSN_ChatStart(tActiveThreads[ tChosenThread ]);
	}
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnLoadIcon - obtain the protocol icon

static int MsnLoadIcon(WPARAM wParam,LPARAM lParam)
{
	UINT id;

	switch(wParam&0xFFFF) {
		case PLI_PROTOCOL: id=IDI_MSN; break;
		default: return (int)(HICON)NULL;
	}

	bool tIsSmall = ( wParam & PLIF_SMALL ) != 0;
	return (int)LoadImage( hInst, MAKEINTRESOURCE(id), IMAGE_ICON,
									GetSystemMetrics(tIsSmall ? SM_CXSMICON : SM_CXICON),
									GetSystemMetrics(tIsSmall ? SM_CYSMICON : SM_CYICON), 0 );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnRebuildContactMenu - gray or ungray the block menus according to contact's status

static int MsnRebuildContactMenu( WPARAM wParam, LPARAM lParam )
{
	char szEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", ( HANDLE )wParam, szEmail, sizeof( szEmail ))) {
		CLISTMENUITEM clmi = { 0 };
		clmi.cbSize = sizeof( clmi );
		clmi.pszName = MSN_Translate( ( Lists_IsInList( LIST_BL, szEmail ) ? "&Unblock" : "&Block" ));
		clmi.flags = CMIM_NAME;
		MSN_CallService( MS_CLIST_MODIFYMENUITEM, ( WPARAM )msnBlockMenuItem, ( LPARAM )&clmi );
	}
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnRecvFile - creates a database event from the file request been received

int MsnRecvFile( WPARAM wParam, LPARAM lParam )
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	DBDeleteContactSetting( ccs->hContact, "CList", "Hidden" );

	ThreadData* thread = MSN_GetP2PThreadByContact( ccs->hContact );
	if ( thread == NULL ) {	
		if ( MSN_GetUnconnectedThread( ccs->hContact ) == NULL )
			msnNsThread->sendPacket( "XFR", "SB" );
		return 0;
	}

	PROTORECVEVENT* pre = ( PROTORECVEVENT* )ccs->lParam;
	char* szFile = pre->szMessage + sizeof( DWORD );
	char* szDescr = szFile + strlen( szFile ) + 1;

	DBEVENTINFO dbei;
	memset( &dbei, 0, sizeof( dbei ));
	dbei.cbSize = sizeof( dbei );
	dbei.szModule = msnProtocolName;
	dbei.timestamp = pre->timestamp;
	dbei.flags = ( pre->flags & PREF_CREATEREAD ) ? DBEF_READ : 0;
	dbei.eventType = EVENTTYPE_FILE;
	dbei.cbBlob = sizeof( DWORD ) + strlen( szFile ) + strlen( szDescr ) + 2;
	dbei.pBlob = ( PBYTE )pre->szMessage;
	MSN_CallService( MS_DB_EVENT_ADD, ( WPARAM )ccs->hContact, ( LPARAM )&dbei );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnRecvMessage - creates a database event from the message been received

static int MsnRecvMessage(WPARAM wParam,LPARAM lParam)
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	PROTORECVEVENT* pre = ( PROTORECVEVENT* )ccs->lParam;

	DBDeleteContactSetting( ccs->hContact, "CList", "Hidden" );

	DBEVENTINFO dbei = { 0 };
	dbei.cbSize = sizeof( dbei );
	dbei.szModule = msnProtocolName;
	dbei.timestamp = pre->timestamp;
	dbei.flags = ( pre->flags & PREF_CREATEREAD ) ? DBEF_READ : 0;
	if ( pre->flags & PREF_RTL )
		dbei.flags |= DBEF_RTL;
	dbei.eventType = EVENTTYPE_MESSAGE;
	dbei.cbBlob = strlen( pre->szMessage )+1;
	if ( pre->flags & PREF_UNICODE )
		dbei.cbBlob *= ( sizeof( wchar_t )+1 );

	dbei.pBlob = ( PBYTE )pre->szMessage;
	MSN_CallService( MS_DB_EVENT_ADD, ( WPARAM )ccs->hContact, ( LPARAM )&dbei );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnSendFile - initiates a file transfer

static int MsnSendFile( WPARAM wParam, LPARAM lParam )
{
	if ( !msnLoggedIn )
		return 0;

	CCSDATA *ccs = ( CCSDATA* )lParam;
	if ( MSN_GetWord( ccs->hContact, "Status", ID_STATUS_OFFLINE ) == ID_STATUS_OFFLINE )
		return 0;

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", ccs->hContact, tEmail, sizeof( tEmail )) && !strcmp( tEmail, MyOptions.szEmail )) 
		return NULL;

	char** files = ( char** )ccs->lParam;

	filetransfer* sft = new filetransfer();
	sft->std.files = files;
	sft->std.hContact = ccs->hContact;
	sft->std.sending = true;

	while ( files[sft->std.totalFiles] != NULL ) {
		struct _stat statbuf;
		if ( _stat( files[sft->std.totalFiles], &statbuf ) == 0 )
			sft->std.totalBytes += statbuf.st_size;

		++sft->std.totalFiles; 
	}

	if ( sft->openNext() == -1 ) {
		delete sft;
		return NULL;
	}

	DWORD dwFlags = MSN_GetDword( ccs->hContact, "FlagBits", 0 );
	if ( dwFlags & 0x70000000 )
		p2p_invite( ccs->hContact, MSN_APPID_FILE, sft );
	else {
		ThreadData* thread = MSN_GetThreadByContact( ccs->hContact );
		if ( thread != NULL ) {
			thread->mMsnFtp = sft;
		}
		else {
			if ( MSN_GetUnconnectedThread( ccs->hContact ) == NULL )
				msnNsThread->sendPacket( "XFR", "SB" );
		}

		char* pszFiles = strrchr( *files, '\\' ), msg[ 1024 ];
		if ( pszFiles )
			pszFiles++;
		else
			pszFiles = *files;

		mir_snprintf( msg, sizeof( msg ),
			"Content-Type: text/x-msmsgsinvite; charset=UTF-8\r\n\r\n"
			"Application-Name: File Transfer\r\n"
			"Application-GUID: {5D3E02AB-6190-11d3-BBBB-00C04F795683}\r\n"
			"Invitation-Command: INVITE\r\n"
			"Invitation-Cookie: %i\r\n"
			"Application-File: %s\r\n"
			"Application-FileSize: %i\r\n\r\n",
			( WORD )(((double)rand()/(double)RAND_MAX)*4294967295), UTF8(pszFiles), sft->std.currentFileSize );

		if ( thread == NULL )
			MsgQueue_Add( ccs->hContact, 'S', msg, -1, sft );
		else
			thread->sendMessage( 'S', msg, MSG_DISABLE_HDR );
	}

	MSN_SendBroadcast( ccs->hContact, ACKTYPE_FILE, ACKRESULT_SENTREQUEST, sft, 0 );
	return (int)(HANDLE)sft;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnSendMessage - sends the message to a server

struct TFakeAckParams
{
	inline TFakeAckParams( HANDLE p2, LONG p3, LPCSTR p4 ) :
		hContact( p2 ),
		id( p3 ),
		msg( p4 )
		{}

	HANDLE	hContact;
	LONG		id;
	LPCSTR	msg;
};

static void sttFakeAck( LPVOID param )
{
	TFakeAckParams* tParam = ( TFakeAckParams* )param;

	Sleep( 100 );
	MSN_SendBroadcast( tParam->hContact, ACKTYPE_MESSAGE, tParam->msg ? ACKRESULT_FAILED : ACKRESULT_SUCCESS, 
		( HANDLE )tParam->id, LPARAM( tParam->msg ));

	delete tParam;
}

static int MsnSendMessage( WPARAM wParam, LPARAM lParam )
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	char *msg, *errMsg = NULL;

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", ccs->hContact, tEmail, sizeof( tEmail )) && !strcmp( tEmail, MyOptions.szEmail )) {
		errMsg = MSN_Translate( "You cannot send message to yourself" );
		mir_forkthread( sttFakeAck, new TFakeAckParams( ccs->hContact, 999999, errMsg ));
		return 999999;
	}

	if ( ccs->wParam & PREF_UNICODE ) {
		char* p = ( char* )ccs->lParam;
		msg = mir_utf8encodeW(( wchar_t* )&p[ strlen(p)+1 ] );
	}
	else msg = mir_utf8encode(( char* )ccs->lParam );

	if ( strlen( msg ) > 1202 ) {
		errMsg = MSN_Translate( "Message is too long: MSN messages are limited by 1202 UTF8 chars" );
LBL_Error:
		mir_free( msg );

		mir_forkthread( sttFakeAck, new TFakeAckParams( ccs->hContact, 999999, errMsg ));
		return 999999;
	}

	int seq, msgType = ( MyOptions.SlowSend ) ? 'A' : 'N';
	int rtlFlag = ( ccs->wParam & PREF_RTL ) ? MSG_RTL : 0;
	ThreadData* thread = MSN_GetThreadByContact( ccs->hContact );
	if ( thread == NULL || thread->mJoinedCount == 0 )
	{
		WORD wStatus = MSN_GetWord( ccs->hContact, "Status", ID_STATUS_OFFLINE );
		if ( wStatus == ID_STATUS_OFFLINE || msnStatusMode == ID_STATUS_INVISIBLE ) {
			errMsg = MSN_Translate( "MSN protocol does not support offline messages" );
			goto LBL_Error;
		}

		if ( MSN_GetUnconnectedThread( ccs->hContact ) == NULL )
			msnNsThread->sendPacket( "XFR", "SB" );

		seq = MsgQueue_Add( ccs->hContact, msgType, msg, 0, 0, rtlFlag );
	}
	else
	{	
		seq = thread->sendMessage( msgType, msg, rtlFlag );

		if ( seq == -1 ) {
			seq = MsgQueue_Add( ccs->hContact, msgType, msg, 0, 0, rtlFlag );
			msnNsThread->sendPacket( "XFR", "SB" );
		}
		else if ( !MyOptions.SlowSend )
			mir_forkthread( sttFakeAck, new TFakeAckParams( ccs->hContact, seq, 0 ));
	}

	mir_free( msg );
	return seq;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnSendNudge - Sending a nudge

static int MsnSendNudge( WPARAM wParam, LPARAM lParam )
{
	if ( !msnLoggedIn ) return 0;
	
	HANDLE hContact = ( HANDLE )wParam;
	char msg[ 1024 ];
	
	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail )) && !strcmp( tEmail, MyOptions.szEmail ))
		return 0;

	mir_snprintf( msg, sizeof( msg ),"N 69\r\nMIME-Version: 1.0\r\n"
				"Content-Type: text/x-msnmsgr-datacast\r\n\r\n"
				"ID: 1\r\n\r\n");

	ThreadData* thread = MSN_GetThreadByContact( hContact );

	if ( thread == NULL ) {
		if ( MSN_GetUnconnectedThread( hContact ) == NULL ) 
			msnNsThread->sendPacket( "XFR", "SB" );
		MsgQueue_Add( hContact, 'N', msg, -1 );
	}
	else
		thread->sendPacket( "MSG",msg);
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnSendNetMeeting - Netmeeting callback function

static int MsnSendNetMeeting( WPARAM wParam, LPARAM lParam )
{
	if ( !msnLoggedIn ) return 0;

	HANDLE hContact = HANDLE(wParam);

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail )) && !strcmp( tEmail, MyOptions.szEmail ))
		return 0;

	ThreadData* thread = MSN_GetThreadByContact( hContact );

	if ( thread == NULL ) {
		MessageBoxA( NULL, MSN_Translate( "You must be talking to start Netmeeting" ), "MSN Protocol", MB_OK | MB_ICONERROR );
		return 0;
	}

	char msg[ 1024 ];

	mir_snprintf( msg, sizeof( msg ),
		"Content-Type: text/x-msmsgsinvite; charset=UTF-8\r\n\r\n"
		"Application-Name: NetMeeting\r\n"
		"Application-GUID: {44BBA842-CC51-11CF-AAFA-00AA00B6015C}\r\n"
		"Session-Protocol: SM1\r\n"
		"Invitation-Command: INVITE\r\n"
		"Invitation-Cookie: %i\r\n"
		"Session-ID: {1A879604-D1B8-11D7-9066-0003FF431510}\r\n\r\n",
		( WORD )(((double)rand()/(double)RAND_MAX)*4294967295));

	thread->sendMessage( 'N', msg, MSG_DISABLE_HDR );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	MsnSetApparentMode - controls contact visibility

static int MsnSetApparentMode( WPARAM wParam, LPARAM lParam )
{
	CCSDATA* ccs = ( CCSDATA* )lParam;
	if ( ccs->wParam && ccs->wParam != ID_STATUS_OFFLINE )
	  return 1;

	WORD oldMode = MSN_GetWord( ccs->hContact, "ApparentMode", 0 );
	if ( ccs->wParam != oldMode )
		MSN_SetWord( ccs->hContact, "ApparentMode", ( WORD )ccs->wParam );

	return 1;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	MsnSetAvatar - sets an avatar without UI

static int MsnSetAvatar( WPARAM wParam, LPARAM lParam )
{
	HBITMAP hBitmap = ( HBITMAP )MSN_CallService( MS_UTILS_LOADBITMAP, 0, lParam );
	if ( hBitmap == NULL )
		return 1;

	if (( hBitmap = MSN_StretchBitmap( hBitmap )) == NULL )
		return 2;

	MSN_SaveBitmapAsAvatar( hBitmap, (char *) lParam );
	DeleteObject( hBitmap );
	if ( msnLoggedIn )
		MSN_SetServerStatus( msnStatusMode );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	MsnSetAvatar - sets an avatar without UI

static int MsnSetAvatarUI( WPARAM wParam, LPARAM lParam )
{
	char szFileName[ MAX_PATH ];
	if ( MSN_EnterBitmapFileName( szFileName ) != ERROR_SUCCESS )
		return 1;

	return MsnSetAvatar( 0, ( LPARAM )szFileName );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnSetAwayMsg - sets the current status message for a user

static int MsnSetAwayMsg(WPARAM wParam,LPARAM lParam)
{
	int i;

	for ( i=0; i < MSN_NUM_MODES; i++ )
		if ( msnModeMsgs[i].m_mode == (int)wParam )
			break;

	if ( i == MSN_NUM_MODES )
		return 1;

	replaceStr( msnModeMsgs[i].m_msg, ( char* )lParam );

	if ( (int)wParam == msnDesiredStatus )
		MSN_SendStatusMessage(( char* )lParam );

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	SetNickname - sets a nick name without UI

static int MsnSetNickName( WPARAM wParam, LPARAM lParam )
{
	MSN_SendNickname(( char* )lParam );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	GetCurrentMedia - get current media

static int MsnGetCurrentMedia(WPARAM wParam, LPARAM lParam) 
{
	LISTENINGTOINFO *cm = (LISTENINGTOINFO *)lParam;

	if (cm == NULL || cm->cbSize != sizeof(LISTENINGTOINFO))
		return -1;

	cm->ptszArtist = mir_tstrdup( msnCurrentMedia.ptszArtist );
	cm->ptszAlbum = mir_tstrdup( msnCurrentMedia.ptszAlbum );
	cm->ptszTitle = mir_tstrdup( msnCurrentMedia.ptszTitle );
	cm->ptszTrack = mir_tstrdup( msnCurrentMedia.ptszTrack );
	cm->ptszYear = mir_tstrdup( msnCurrentMedia.ptszYear );
	cm->ptszGenre = mir_tstrdup( msnCurrentMedia.ptszGenre );
	cm->ptszLength = mir_tstrdup( msnCurrentMedia.ptszLength );
	cm->ptszPlayer = mir_tstrdup( msnCurrentMedia.ptszPlayer );
	cm->ptszType = mir_tstrdup( msnCurrentMedia.ptszType );
	cm->dwFlags = msnCurrentMedia.dwFlags;

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	SetCurrentMedia - set current media

static int MsnSetCurrentMedia(WPARAM wParam, LPARAM lParam) 
{
	// Clear old info
	if ( msnCurrentMedia.ptszArtist ) mir_free( msnCurrentMedia.ptszArtist );
	if ( msnCurrentMedia.ptszAlbum ) mir_free( msnCurrentMedia.ptszAlbum );
	if ( msnCurrentMedia.ptszTitle ) mir_free( msnCurrentMedia.ptszTitle );
	if ( msnCurrentMedia.ptszTrack ) mir_free( msnCurrentMedia.ptszTrack );
	if ( msnCurrentMedia.ptszYear ) mir_free( msnCurrentMedia.ptszYear );
	if ( msnCurrentMedia.ptszGenre ) mir_free( msnCurrentMedia.ptszGenre );
	if ( msnCurrentMedia.ptszLength ) mir_free( msnCurrentMedia.ptszLength );
	if ( msnCurrentMedia.ptszPlayer ) mir_free( msnCurrentMedia.ptszPlayer );
	if ( msnCurrentMedia.ptszType ) mir_free( msnCurrentMedia.ptszType );
	ZeroMemory(&msnCurrentMedia, sizeof(msnCurrentMedia));

	// Copy new info
	LISTENINGTOINFO *cm = (LISTENINGTOINFO *)lParam;
	if (cm != NULL && cm->cbSize == sizeof(LISTENINGTOINFO) && (cm->ptszArtist != NULL || cm->ptszTitle != NULL)) 
	{
		BOOL unicode = cm->dwFlags & LTI_UNICODE;

		msnCurrentMedia.cbSize = sizeof(msnCurrentMedia);	// Marks that there is info set
		msnCurrentMedia.dwFlags = LTI_TCHAR;

		overrideStr( msnCurrentMedia.ptszType, cm->ptszType, unicode, _T("Music") );
		overrideStr( msnCurrentMedia.ptszArtist, cm->ptszArtist, unicode );
		overrideStr( msnCurrentMedia.ptszAlbum, cm->ptszAlbum, unicode );
		overrideStr( msnCurrentMedia.ptszTitle, cm->ptszTitle, unicode, _T("No Title") );
		overrideStr( msnCurrentMedia.ptszTrack, cm->ptszTrack, unicode );
		overrideStr( msnCurrentMedia.ptszYear, cm->ptszYear, unicode );
		overrideStr( msnCurrentMedia.ptszGenre, cm->ptszGenre, unicode );
		overrideStr( msnCurrentMedia.ptszLength, cm->ptszLength, unicode );
		overrideStr( msnCurrentMedia.ptszPlayer, cm->ptszPlayer, unicode );
	}

	// Set user text
	if (msnCurrentMedia.cbSize == 0)
		DBDeleteContactSetting( NULL, msnProtocolName, "ListeningTo" );
	else 
	{
		TCHAR *text;
		if (ServiceExists(MS_LISTENINGTO_GETPARSEDTEXT)) 
			text = (TCHAR *) CallService(MS_LISTENINGTO_GETPARSEDTEXT, (WPARAM) _T("%title% - %artist%"), (LPARAM) &msnCurrentMedia );
		else 
		{
			text = (TCHAR *) mir_alloc( 128 * sizeof(TCHAR) );
			mir_sntprintf( text, 128, _T("%s - %s"), ( msnCurrentMedia.ptszTitle ? msnCurrentMedia.ptszTitle : _T("") ), 
													 ( msnCurrentMedia.ptszArtist ? msnCurrentMedia.ptszArtist : _T("") ) );
		}
		MSN_SetStringT(NULL, "ListeningTo", text);
		mir_free(text);
	}

	// Send it
	int i;
	for ( i=0; i < MSN_NUM_MODES; i++ ) {
		if ( msnModeMsgs[i].m_mode == msnDesiredStatus ) {
			MSN_SendStatusMessage( msnModeMsgs[i].m_msg );
			break;
	}	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	SetNicknameCommand - sets nick name

static BOOL CALLBACK DlgProcSetNickname(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch ( msg )
	{
		case WM_INITDIALOG:
		{
			TranslateDialogDefault( hwndDlg );
			SendMessage( hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadIcon( hInst, MAKEINTRESOURCE( IDI_MSN )));
			SendMessage( GetDlgItem( hwndDlg, IDC_NICKNAME ), EM_LIMITTEXT, 129, 0 );

			DBVARIANT dbv;
			if ( !MSN_GetStringT( "Nick", NULL, &dbv )) {
				SetDlgItemText( hwndDlg, IDC_NICKNAME, dbv.ptszVal );
				MSN_FreeVariant( &dbv );
			}
			return TRUE;
		}
		case WM_COMMAND:
			switch(wParam)
			{
				case IDOK:
					if ( msnLoggedIn ) {
						TCHAR str[ 130 ];
						GetDlgItemText( hwndDlg, IDC_NICKNAME, str, SIZEOF( str ));
						MSN_SendNicknameT( str );
					}

				case IDCANCEL:
 					DestroyWindow( hwndDlg );
					break;
			}
			break;

		case WM_CLOSE:
			DestroyWindow( hwndDlg );
			break;
	}
	return FALSE;
}

static int SetNicknameUI( WPARAM wParam, LPARAM lParam )
{
	HWND hwndSetNickname = CreateDialog(hInst, MAKEINTRESOURCE( IDD_SETNICKNAME ), NULL, DlgProcSetNickname );

	SetForegroundWindow( hwndSetNickname );
	SetFocus( hwndSetNickname );
 	ShowWindow( hwndSetNickname, SW_SHOW );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnSetStatus - set the plugin's connection status

static int MsnSetStatus( WPARAM wParam, LPARAM lParam )
{
	msnDesiredStatus = wParam;
	MSN_DebugLog( "PS_SETSTATUS(%d,0)", wParam );

	if ( msnDesiredStatus == ID_STATUS_OFFLINE )
	{
		MSN_GoOffline();
	}
	else if (!msnLoggedIn && !(msnStatusMode>=ID_STATUS_CONNECTING && msnStatusMode<ID_STATUS_CONNECTING+MAX_CONNECT_RETRIES))
	{
		LoadOptions();
		MyOptions.UseProxy = MSN_GetByte( "NLUseProxy", FALSE );

		ThreadData* newThread = new ThreadData;

		WORD tServerPort = MSN_GetWord( NULL, "MSNMPort", 1863 );

		char tServer[ sizeof( newThread->mServer ) ];
		if ( MSN_GetStaticString( "LoginServer", NULL, tServer, sizeof( tServer )))
			strcpy( tServer, MSN_DEFAULT_LOGIN_SERVER );

		mir_snprintf( newThread->mServer, sizeof( newThread->mServer ), "%s:%i", tServer, tServerPort );
		newThread->mServer[ sizeof(newThread->mServer)-1 ] = 0;

		newThread->mType = SERVER_DISPATCH;
		newThread->mIsMainThread = true;
		{	int oldMode = msnStatusMode;
			msnStatusMode = ID_STATUS_CONNECTING;
			MSN_SendBroadcast( NULL, ACKTYPE_STATUS, ACKRESULT_SUCCESS, ( HANDLE )oldMode, msnStatusMode );
		}
		newThread->startThread(( pThreadFunc )MSNServerThread );
	}
	else MSN_SetServerStatus( msnDesiredStatus );

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnUserIsTyping - notify another contact that we're typing a message

extern char sttHeaderStart[];

static int MsnUserIsTyping(WPARAM wParam, LPARAM lParam)
{
	if ( !msnLoggedIn )
		return 0;

	HANDLE hContact = ( HANDLE )wParam;

	char tEmail[ MSN_MAX_EMAIL_LEN ];
	if ( !MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail )) && !strcmp( tEmail, MyOptions.szEmail ))
		return 0;

	ThreadData* T = MSN_GetThreadByContact( hContact );

	bool typing = lParam == PROTOTYPE_SELFTYPING_ON;

	if ( T == NULL ) {
		WORD wStatus = MSN_GetWord( hContact, "Status", ID_STATUS_OFFLINE );
		if ( wStatus == ID_STATUS_OFFLINE || msnStatusMode == ID_STATUS_INVISIBLE )
			return 0;

		if ( typing && MsgQueue_CheckContact( hContact ) == NULL )
			msnNsThread->sendPacket( "XFR", "SB" );

		MsgQueue_Add( hContact, 2571, NULL, 0, NULL, typing );
	}
	else 
		MSN_StartStopTyping( T, typing );

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnViewProfile - view a contact's profile at http://members.msn.com

static char sttUrlPrefix[] = "http://members.msn.com/";

static int MsnViewProfile( WPARAM wParam, LPARAM lParam )
{
	char tUrl[ MSN_MAX_EMAIL_LEN + sizeof sttUrlPrefix ];
	strcpy( tUrl, sttUrlPrefix );

	if ( !MSN_GetStaticString( "e-mail", ( HANDLE )wParam, tUrl + sizeof sttUrlPrefix - 1, MSN_MAX_EMAIL_LEN ))
		MSN_CallService( MS_UTILS_OPENURL, 1, ( LPARAM )tUrl );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MsnViewServiceStatus - display MSN services status

static int MsnViewServiceStatus( WPARAM wParam, LPARAM lParam )
{
	MSN_CallService( MS_UTILS_OPENURL, 1, ( LPARAM )"http://messenger.msn.com/Status.aspx" );
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// Services initialization and destruction

static HANDLE hHookHandle[3]; 
static HANDLE hServiceHandle[50]; 

int LoadMsnServices( void )
{
	//////////////////////////////////////////////////////////////////////////////////////
	// Main menu initialization

	char servicefunction[ 100 ];
	strcpy( servicefunction, msnProtocolName );
	char* tDest = servicefunction + strlen( servicefunction );
	CLISTMENUITEM mi;

	if ( !MSN_GetByte( "DisableSetNickname", 0 ))
	{
		strcpy( tDest, MS_SET_NICKNAME_UI );
		hServiceHandle[0] = CreateServiceFunction( servicefunction, SetNicknameUI );

		memset( &mi, 0, sizeof( mi ));
		mi.popupPosition = 500085000;
		mi.pszPopupName = msnProtocolName;
		mi.cbSize = sizeof( mi );
		mi.position = 2000060000;
		mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_MSN ));
		mi.pszName = MSN_Translate( "Set &Nickname" );
		mi.pszService = servicefunction;
		msnMenuItems[ 0 ] = ( HANDLE )MSN_CallService( MS_CLIST_ADDMAINMENUITEM, 0, (LPARAM)&mi );

		strcpy( tDest, MS_GOTO_INBOX );
		hServiceHandle[1] = CreateServiceFunction( servicefunction, MsnGotoInbox );

		mi.position = 2000060001;
		mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_INBOX ));
		mi.pszName = MSN_Translate( "Display Hotmail &Inbox" );
		mi.pszService = servicefunction;
		MSN_CallService( MS_CLIST_ADDMAINMENUITEM, 0, (LPARAM)&mi );

		strcpy( tDest, MS_EDIT_PROFILE );
		hServiceHandle[2] = CreateServiceFunction( servicefunction, MsnEditProfile );

		mi.position = 2000060002;
		mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_PROFILE ));
		mi.pszName = MSN_Translate( "Edit MSN &Profile" );
		mi.pszService = servicefunction;
		msnMenuItems[ 1 ] = ( HANDLE )MSN_CallService( MS_CLIST_ADDMAINMENUITEM, 0, (LPARAM)&mi );

		strcpy( tDest, MS_VIEW_STATUS );
		hServiceHandle[3] = CreateServiceFunction( servicefunction, MsnViewServiceStatus );

		mi.position = 2000060003;
		mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_SERVICES ));
		mi.pszName = MSN_Translate( "View MSN Services &Status" );
		mi.pszService = servicefunction;
		MSN_CallService( MS_CLIST_ADDMAINMENUITEM, 0, (LPARAM)&mi );

		strcpy( tDest, MS_SET_AVATAR_UI );
		hServiceHandle[4] = CreateServiceFunction( servicefunction, MsnSetAvatarUI );

		mi.position = 2000060004;
		mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_AVATAR ));
		mi.pszName = MSN_Translate( "Set &Avatar" );
		mi.pszService = servicefunction;
		MSN_CallService( MS_CLIST_ADDMAINMENUITEM, 0, (LPARAM)&mi );
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Contact menu initialization

	strcpy( tDest, MSN_BLOCK );
	hServiceHandle[5] = CreateServiceFunction( servicefunction, MsnBlockCommand );

	memset( &mi, 0, sizeof( mi ));
	mi.cbSize = sizeof( mi );
	mi.position = -500050000;
	mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_MSNBLOCK ));
	mi.pszContactOwner = msnProtocolName;
	mi.pszName = MSN_Translate( "&Block" );
	mi.pszService = servicefunction;
	msnBlockMenuItem = ( HANDLE )MSN_CallService( MS_CLIST_ADDCONTACTMENUITEM, 0, ( LPARAM )&mi );

	strcpy( tDest, MSN_INVITE );
	hServiceHandle[6] = CreateServiceFunction( servicefunction, MsnInviteCommand );

	mi.flags = CMIF_NOTOFFLINE;
	mi.position = -500050001;
	mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_INVITE ));
	mi.pszName = MSN_Translate( "&Invite to chat" );
	mi.pszService = servicefunction;
	MSN_CallService( MS_CLIST_ADDCONTACTMENUITEM, 0, ( LPARAM )&mi );

	strcpy( tDest, MSN_NETMEETING );
	hServiceHandle[7] = CreateServiceFunction( servicefunction, MsnSendNetMeeting );

	mi.flags = CMIF_NOTOFFLINE;
	mi.position = -500050002;
	mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_NETMEETING ));
	mi.pszName = MSN_Translate( "&Start Netmeeting" );
	mi.pszService = servicefunction;
	MSN_CallService( MS_CLIST_ADDCONTACTMENUITEM, 0, ( LPARAM )&mi );

	strcpy( tDest, MSN_VIEW_PROFILE );
	hServiceHandle[8] = CreateServiceFunction( servicefunction, MsnViewProfile );

	mi.flags = 0;
	mi.position = -500050003;
	mi.hIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_PROFILE ));
	mi.pszName = MSN_Translate( "&View Profile" );
	mi.pszService = servicefunction;
	MSN_CallService( MS_CLIST_ADDCONTACTMENUITEM, 0, ( LPARAM )&mi );

	MSN_EnableMenuItems( FALSE );

	//////////////////////////////////////////////////////////////////////////////////////
	// Service creation

	hHookHandle[0] = HookEvent( ME_DB_CONTACT_DELETED, MsnContactDeleted );
	hHookHandle[1] = HookEvent( ME_DB_CONTACT_SETTINGCHANGED, MsnDbSettingChanged );
	hHookHandle[2] = HookEvent( ME_CLIST_PREBUILDCONTACTMENU, MsnRebuildContactMenu );

	hServiceHandle[9] = MSN_CreateProtoServiceFunction( PS_ADDTOLIST,			MsnAddToList );
	hServiceHandle[10] = MSN_CreateProtoServiceFunction( PS_ADDTOLISTBYEVENT,	MsnAddToListByEvent );
	hServiceHandle[11] = MSN_CreateProtoServiceFunction( PS_AUTHALLOW,			MsnAuthAllow );
	hServiceHandle[12] = MSN_CreateProtoServiceFunction( PS_AUTHDENY,				MsnAuthDeny );
	hServiceHandle[13] = MSN_CreateProtoServiceFunction( PS_BASICSEARCH,			MsnBasicSearch );
	hServiceHandle[14] = MSN_CreateProtoServiceFunction( PS_FILERESUME,			MsnFileResume );
	hServiceHandle[15] = MSN_CreateProtoServiceFunction( PS_GETAVATARINFO,		MsnGetAvatarInfo );
	hServiceHandle[16] = MSN_CreateProtoServiceFunction( PS_GETCAPS,				MsnGetCaps );
	hServiceHandle[17] = MSN_CreateProtoServiceFunction( PS_GETNAME,				MsnGetName );
	hServiceHandle[18] = MSN_CreateProtoServiceFunction( PS_GETSTATUS,			MsnGetStatus );
	hServiceHandle[19] = MSN_CreateProtoServiceFunction( PS_LOADICON,				MsnLoadIcon );
	hServiceHandle[20] = MSN_CreateProtoServiceFunction( PS_SEARCHBYEMAIL,		MsnBasicSearch );
	hServiceHandle[21] = MSN_CreateProtoServiceFunction( PS_SETSTATUS,			MsnSetStatus );

	hServiceHandle[22] = MSN_CreateProtoServiceFunction( PSR_FILE,					MsnRecvFile );
	hServiceHandle[23] = MSN_CreateProtoServiceFunction( PSR_MESSAGE,				MsnRecvMessage );

	hServiceHandle[24] = MSN_CreateProtoServiceFunction( PSS_FILE,					MsnSendFile );
	hServiceHandle[25] = MSN_CreateProtoServiceFunction( PSS_FILEALLOW,			MsnFileAllow );
	hServiceHandle[26] = MSN_CreateProtoServiceFunction( PSS_FILECANCEL,			MsnFileCancel );
	hServiceHandle[27] = MSN_CreateProtoServiceFunction( PSS_FILEDENY,			MsnFileDeny );
	hServiceHandle[28] = MSN_CreateProtoServiceFunction( PSS_GETINFO,				MsnGetInfo );
	hServiceHandle[29] = MSN_CreateProtoServiceFunction( PSS_MESSAGE,				MsnSendMessage );
	hServiceHandle[30] = MSN_CreateProtoServiceFunction( PSS_SETAPPARENTMODE,  MsnSetApparentMode );
	hServiceHandle[31] = MSN_CreateProtoServiceFunction( PSS_USERISTYPING,     MsnUserIsTyping );

	if ( MyOptions.UseMSNP11 ) {
		hServiceHandle[32] = MSN_CreateProtoServiceFunction( PSS_GETAWAYMSG,		MsnGetAwayMsg );
		hServiceHandle[33] = MSN_CreateProtoServiceFunction( PS_SETAWAYMSG,		MsnSetAwayMsg );
	}

	hServiceHandle[34] = MSN_CreateProtoServiceFunction( MSN_ISAVATARFORMATSUPPORTED, MsnGetAvatarFormatSupported );
	hServiceHandle[35] = MSN_CreateProtoServiceFunction( MSN_GETMYAVATARMAXSIZE, MsnGetAvatarMaxSize );
	hServiceHandle[36] = MSN_CreateProtoServiceFunction( PS_SET_LISTENINGTO,   MsnSetCurrentMedia );
	hServiceHandle[37] = MSN_CreateProtoServiceFunction( PS_GET_LISTENINGTO,   MsnGetCurrentMedia );
	hServiceHandle[38] = MSN_CreateProtoServiceFunction( MSN_GETMYAVATAR,      MsnGetAvatar );
	hServiceHandle[39] = MSN_CreateProtoServiceFunction( MSN_SETMYAVATAR,      MsnSetAvatar );

	hServiceHandle[40] = MSN_CreateProtoServiceFunction( MSN_SET_NICKNAME,     MsnSetNickName );
	hServiceHandle[41] = MSN_CreateProtoServiceFunction( MSN_SEND_NUDGE,       MsnSendNudge );
	return 0;
}

void UnloadMsnServices( void )
{
	int i;

	for ( i=0; i<sizeof(hHookHandle)/sizeof(HANDLE); i++ )
		if ( hHookHandle[i] != NULL )
			UnhookEvent( hHookHandle[i] );
	
	for ( i=0; i<sizeof(hServiceHandle)/sizeof(HANDLE); i++ )
		if ( hServiceHandle[i] != NULL )
			DestroyServiceFunction( hServiceHandle[i] );
}
