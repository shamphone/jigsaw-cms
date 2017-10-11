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

#include <stdio.h>
#include <stdarg.h>

#include "msn_global.h"

#include <direct.h>
#include <io.h>
#include <fcntl.h>
#include <sys/stat.h>

#include "msn_md5.h"
#include "sha1.h"
#include "resource.h"

#include "version.h"

typedef LONG ( WINAPI pIncrementFunc )( PLONG );

static pIncrementFunc  MyInterlockedIncrement95;
static pIncrementFunc  MyInterlockedIncrementInit;

pIncrementFunc *MyInterlockedIncrement = MyInterlockedIncrementInit;

static CRITICAL_SECTION csInterlocked95;
extern HANDLE msnMainThread;
extern bool msnUseExtendedPopups;
extern char* msnPreviousUUX;

/////////////////////////////////////////////////////////////////////////////////////////
// MirandaStatusToMSN - status helper functions

char* __stdcall MirandaStatusToMSN( int status )
{
	switch(status)
	{
		case ID_STATUS_OFFLINE:		return "FLN";
		case ID_STATUS_NA:			return ( MyOptions.AwayAsBrb ) ? (char *)"AWY" : (char *)"BRB";
		case ID_STATUS_AWAY:			return ( MyOptions.AwayAsBrb ) ? (char *)"BRB" : (char *)"AWY";
		case ID_STATUS_DND:
		case ID_STATUS_OCCUPIED:	return "BSY";
		case ID_STATUS_ONTHEPHONE: return "PHN";
		case ID_STATUS_OUTTOLUNCH: return "LUN";
		case ID_STATUS_INVISIBLE:	return "HDN";
		case ID_STATUS_IDLE:			return "IDL";
		default:							return "NLN";
}	}

int __stdcall MSNStatusToMiranda(const char *status)
{
	switch((*(PDWORD)status&0x00FFFFFF)|0x20000000) {
		case ' LDI': //return ID_STATUS_IDLE;
		case ' NLN': return ID_STATUS_ONLINE;
		case ' YWA': return ( MyOptions.AwayAsBrb ) ? ID_STATUS_NA : ID_STATUS_AWAY;
		case ' BRB': return ( MyOptions.AwayAsBrb ) ? ID_STATUS_AWAY : ID_STATUS_NA;
		case ' YSB': return ID_STATUS_OCCUPIED;
		case ' NHP': return ID_STATUS_ONTHEPHONE;
		case ' NUL': return ID_STATUS_OUTTOLUNCH;
		case ' NDH': return ID_STATUS_INVISIBLE;
		default: return ID_STATUS_OFFLINE;
}	}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_AddUser - adds a e-mail address to one of the MSN server lists

int __stdcall MSN_AddUser( HANDLE hContact, const char* email, int flags )
{
	if ( flags & LIST_REMOVE )
	{
		if ( !Lists_IsInList( flags & 0xFF, email ))
			return 0;
	}
	else if ( Lists_IsInList( flags, email ))
		return 0;

	char* listName;

	switch( flags & 0xFF )
	{
		case LIST_AL: listName = "AL";	break;
		case LIST_BL: listName = "BL";	break;
		case LIST_FL: listName = "FL";	break;
		case LIST_RL: listName = "RL";	break;
		case LIST_PL: listName = "PL";	break;
		default:
			return -1;
	}

	int msgid;
	if (( flags & 0xFF ) == LIST_FL ) {
		if ( flags & LIST_REMOVE ) {
			if ( hContact == NULL )
				if (( hContact = MSN_HContactFromEmail( email, NULL, 0, 0 )) == NULL )
					return -1;

			char id[ MSN_GUID_LEN ];
			if ( !MSN_GetStaticString( "ID", hContact, id, sizeof( id )))
				msgid = msnNsThread->sendPacket( "REM", "%s %s", listName, id );
		}
		else {
			char urlNick[388];
			strcpy( urlNick, email );

			if ( !strcmp( email, MyOptions.szEmail )) {
				DBVARIANT dbv;
				if ( !DBGetContactSettingStringUtf( NULL, msnProtocolName, "Nick", &dbv )) {
					UrlEncode( dbv.pszVal, urlNick, sizeof( urlNick ));
					MSN_FreeVariant( &dbv );
			}	}
			msgid = msnNsThread->sendPacket( "ADC", "%s N=%s F=%s", listName, email, urlNick );
		}
	}
	else {
		if ( flags & LIST_REMOVE )
			msgid = msnNsThread->sendPacket( "REM", "%s %s", listName, email );
		else
			msgid = msnNsThread->sendPacket( "ADC", "%s N=%s", listName, email );
	}

	return msgid;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_AddAuthRequest - adds the authorization event to the database

void __stdcall MSN_AddAuthRequest( HANDLE hContact, const char *email, const char *nick )
{
	//blob is: UIN=0(DWORD), hContact(DWORD), nick(ASCIIZ), ""(ASCIIZ), ""(ASCIIZ), email(ASCIIZ), ""(ASCIIZ)

	DBEVENTINFO dbei = { 0 };
	dbei.cbSize    = sizeof( dbei );
	dbei.szModule  = msnProtocolName;
	dbei.timestamp = (DWORD)time(NULL);
	dbei.flags     = 0;
	dbei.eventType = EVENTTYPE_AUTHREQUEST;
	dbei.cbBlob    = sizeof(DWORD)*2+strlen(nick)+strlen(email)+5;

	PBYTE pCurBlob = dbei.pBlob = ( PBYTE )mir_alloc( dbei.cbBlob );
	*( PDWORD )pCurBlob = 0; pCurBlob+=sizeof( DWORD );
	*( PDWORD )pCurBlob = ( DWORD )hContact; pCurBlob+=sizeof( DWORD );
	strcpy(( char* )pCurBlob, nick); pCurBlob += strlen( nick )+1;
	*pCurBlob = '\0'; pCurBlob++;	   //firstName
	*pCurBlob = '\0'; pCurBlob++;	   //lastName
	strcpy(( char* )pCurBlob, email ); pCurBlob += strlen( email )+1;
	*pCurBlob = '\0';         	   //reason
	MSN_CallService( MS_DB_EVENT_ADD, NULL,( LPARAM )&dbei );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_DebugLog - writes a line of comments to the network log

void __stdcall	MSN_DebugLog( const char *fmt, ... )
{
	char		str[ 4096 ];
	va_list	vararg;

	va_start( vararg, fmt );
	int tBytes = _vsnprintf( str, sizeof(str)-1, fmt, vararg );
	if ( tBytes == 0 )
		return;

	if ( tBytes > 0 )
		str[ tBytes ] = 0;
	else
		str[ sizeof(str)-1 ] = 0;

	MSN_CallService( MS_NETLIB_LOG, ( WPARAM )hNetlibUser, ( LPARAM )str );
	va_end( vararg );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_DumpMemory - dumps a memory block to the network log

void __stdcall MSN_DumpMemory( const char* buffer, int bufSize )
{
   char TmpBuffer[ 256 ];
   long Ptr = 0;

   while ( Ptr < bufSize ) {
		char* bufferPtr = TmpBuffer + sprintf( TmpBuffer, "%04X ", Ptr );
      int i;

		for ( i=0; Ptr+i < bufSize && i < 16; i++ )
         bufferPtr += sprintf( bufferPtr, "%02X ", BYTE( buffer[Ptr+i] ));

		while ( i++ < 17 ) {
			strcat( bufferPtr, "   " );
			bufferPtr += 3;
		}

      for ( i=0; Ptr < bufSize && i < 16; i++, Ptr++ )
			*bufferPtr++ = ( BYTE( buffer[ Ptr ]) >= ' ' ) ? buffer[ Ptr ] : '.';

		*bufferPtr = 0;

		MSN_CallService( MS_NETLIB_LOG, ( WPARAM )hNetlibUser, ( LPARAM )TmpBuffer );
}	}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_GetAvatarFileName - gets a file name for an contact's avatar

void __stdcall MSN_GetAvatarFileName( HANDLE hContact, char* pszDest, int cbLen )
{
	MSN_CallService( MS_DB_GETPROFILEPATH, cbLen, LPARAM( pszDest ));

	int tPathLen = strlen( pszDest );
	tPathLen += mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "\\MSN\\"  );
	CreateDirectoryA( pszDest, NULL );

	if ( hContact != NULL ) {
		char szEmail[ MSN_MAX_EMAIL_LEN ];
		if ( MSN_GetStaticString( "e-mail", hContact, szEmail, sizeof( szEmail )))
			ltoa(( long )hContact, szEmail, 10 );

		long digest[ 4 ];
		MD5_CTX ctx;
		MD5Init( &ctx );
		MD5Update( &ctx, ( BYTE* )szEmail, strlen( szEmail ));
		MD5Final(( BYTE* )digest, &ctx );

		tPathLen += mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "%08lX%08lX%08lX%08lX",
			digest[0], digest[1], digest[2], digest[3] );

		strcat( pszDest + tPathLen, ".bmp" );
	}
	else mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "%s avatar.png", msnProtocolName );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_GetCustomSmileyFileName - gets a file name for an contact's custom smiley

void __stdcall MSN_GetCustomSmileyFileName( HANDLE hContact, char* pszDest, int cbLen, char* SmileyName, int type )
{
	MSN_CallService( MS_DB_GETPROFILEPATH, cbLen, LPARAM( pszDest ));

	int tPathLen = strlen( pszDest );

	if ( hContact != NULL ) {
		char szEmail[ MSN_MAX_EMAIL_LEN ];
		if ( MSN_GetStaticString( "e-mail", hContact, szEmail, sizeof( szEmail )))
			ltoa(( long )hContact, szEmail, 10 );
		
		tPathLen += mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "\\MSN\\CustomSmiley\\");
		CreateDirectoryA( pszDest, NULL );
		tPathLen += mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "%s\\",szEmail );
	}
	else tPathLen += mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "\\MSN\\CustomSmiley\\%s\\",msnProtocolName );
		
	CreateDirectoryA( pszDest, NULL );

	if ( type == MSN_APPID_CUSTOMSMILEY )
		mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "%s.png", SmileyName );
	else
		mir_snprintf( pszDest + tPathLen, MAX_PATH - tPathLen, "%s.gif", SmileyName );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_GoOffline - performs several actions when a server goes offline

void __stdcall	MSN_GoOffline()
{
	int msnOldStatus = msnStatusMode; msnStatusMode = ID_STATUS_OFFLINE;
	MSN_SendBroadcast( NULL, ACKTYPE_STATUS, ACKRESULT_SUCCESS, (HANDLE)msnOldStatus, ID_STATUS_OFFLINE );

	msnLoggedIn = false;

	mir_free(msnPreviousUUX);
	msnPreviousUUX = NULL;

	if ( !Miranda_Terminated() )
		MSN_EnableMenuItems( FALSE );
	MSN_CloseConnections();
	MSN_FreeGroups();
	MsgQueue_Clear();

	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
	while ( hContact != NULL )
	{
		if ( !lstrcmpA( msnProtocolName, (char*)MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO, ( WPARAM )hContact, 0 )))
			if ( ID_STATUS_OFFLINE != MSN_GetWord( hContact, "Status", ID_STATUS_OFFLINE )) {
				MSN_SetWord( hContact, "Status", ID_STATUS_OFFLINE );
				MSN_SetDword( hContact, "IdleTS", 0 );
			}

		hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 );
}	}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_SendMessage - formats and sends a MSG packet through the server

char sttHeaderStart[] = "MIME-Version: 1.0\r\n";

LONG ThreadData::sendMessage( int msgType, const char* parMsg, int parFlags )
{
	char tHeader[ 1024 ];
	strcpy( tHeader, sttHeaderStart );

	if (( parFlags & MSG_DISABLE_HDR ) == 0 ) {
		char  tFontName[ 100 ], tFontStyle[ 3 ];
		DWORD tFontColor;

		strcpy( tFontName, "Arial" );

		if ( MSN_GetByte( "SendFontInfo", 1 )) {
			char* p;

			DBVARIANT dbv;
			if ( !DBGetContactSetting( NULL, "SRMsg", "Font0", &dbv )) {
				for ( p = dbv.pszVal; *p; p++ )
					if ( BYTE( *p ) >= 128 || *p < 32 )
						break;

				if ( *p == 0 ) {
					UrlEncode( dbv.pszVal, tFontName, sizeof( tFontName ));
					MSN_FreeVariant( &dbv );
			}	}

			{	BYTE  tStyle = DBGetContactSettingByte( NULL, "SRMsg", "Font0Sty", 0 );
				p = tFontStyle;
				if ( tStyle & 1 ) *p++ = 'B';
				if ( tStyle & 2 ) *p++ = 'I';
				*p = 0;
			}

			tFontColor = DBGetContactSettingDword( NULL, "SRMsg", "Font0Col", 0 );
		}
		else {
			tFontColor = 0;
			tFontStyle[ 0 ] = 0;
		}

		mir_snprintf( tHeader + sizeof( sttHeaderStart )-1, sizeof( tHeader )-sizeof( sttHeaderStart ),
			"Content-Type: text/plain; charset=UTF-8\r\n"
			"X-MMS-IM-Format: FN=%s; EF=%s; CO=%x; CS=0; PF=31%s\r\n\r\n",
			tFontName, tFontStyle, tFontColor, (parFlags & MSG_RTL) ? ";RL=1" : "" );
	}

	return sendPacket( "MSG", "%c %d\r\n%s%s", msgType, strlen( parMsg )+strlen( tHeader ), tHeader, parMsg );
}

void ThreadData::sendCaps( void )
{
	char mversion[100], capMsg[1000];
	MSN_CallService( MS_SYSTEM_GETVERSIONTEXT, sizeof( mversion ), ( LPARAM )mversion );

	int nBytes = mir_snprintf( capMsg, sizeof( capMsg ),
		"Content-Type: text/x-clientcaps\r\n\r\n"
		"Client-Name: Miranda IM %s (MSN v.%s)\r\n",
		mversion, __VERSION_STRING );

	sendMessage( 'U', capMsg, MSG_DISABLE_HDR );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_SendRawPacket - sends a packet accordingly to the MSN protocol

LONG ThreadData::sendRawMessage( int msgType, const char* data, int datLen )
{
	if ( data == NULL )
		data = "";

	if ( datLen == -1 )
		datLen = strlen( data );

	char* buf = ( char* )alloca( datLen + 100 );

	LONG thisTrid = MyInterlockedIncrement( &mTrid );
	int nBytes = mir_snprintf( buf, 100, "MSG %d %c %d\r\n%s",
		thisTrid, msgType, datLen + sizeof(sttHeaderStart)-1, sttHeaderStart );
	memcpy( buf + nBytes, data, datLen );
	send( buf, nBytes + datLen );

	return thisTrid;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_RenameServerGroup - renames a group at the server

void MSN_RenameServerGroup( int iNumber, LPCSTR szId, const char* newName )
{
	LPCSTR oldId = MSN_GetGroupByName( newName );
	if ( oldId == NULL ) {
		char szNewName[ 256 ];
		UrlEncode( newName, szNewName, sizeof szNewName );
		msnNsThread->sendPacket( "REG", "%s %s", szId, szNewName );
	}
	else MSN_SetGroupNumber( oldId, iNumber );
}

/////////////////////////////////////////////////////////////////////////////////////////
// Msn_SendNickname - update our own nickname on the server

int __stdcall MSN_SendNickname(char *nickname)
{
	char urlNick[ 387 ];
	UrlEncode( UTF8(nickname), urlNick,  sizeof( urlNick ));
	msnNsThread->sendPacket( "PRP", "MFN %s", urlNick );
	return 0;
}

int __stdcall MSN_SendNicknameW( WCHAR* nickname)
{
	char* nickutf = mir_utf8encodeW( nickname );

	char urlNick[ 387 ];
	UrlEncode( nickutf, urlNick,  sizeof( urlNick ));
	msnNsThread->sendPacket( "PRP", "MFN %s", urlNick );

	mir_free( nickutf );
	return 0;
}

// Typing notifications support

void MSN_SendTyping( ThreadData* info  )
{
	char tCommand[ 1024 ];
	mir_snprintf( tCommand, sizeof( tCommand ),
		"Content-Type: text/x-msmsgscontrol\r\n"
		"TypingUser: %s\r\n\r\n\r\n", MyOptions.szEmail );

	info->sendMessage( 'U', tCommand, MSG_DISABLE_HDR );
}


static VOID CALLBACK TypingTimerProc(HWND hwnd, UINT uMsg, UINT_PTR idEvent, DWORD dwTime) 
{
	ThreadData* T = MSN_GetThreadByTimer( idEvent );
	if ( T != NULL )
		MSN_SendTyping( T );
	else
		KillTimer( NULL, idEvent );
}	


void __stdcall MSN_StartStopTyping( ThreadData* info, bool start )
{
	if ( start && info->mTimerId == 0 ) {
		info->mTimerId = SetTimer(NULL, NULL, 5000, TypingTimerProc);
		MSN_SendTyping( info );
	}
	else if ( !start && info->mTimerId != 0 ) {
			KillTimer( NULL, info->mTimerId );
			info->mTimerId = 0;
	}
}



/////////////////////////////////////////////////////////////////////////////////////////
// MSN_SendStatusMessage - notify a server about the status message change

// Helper to process texts
static char * HtmlEncodeUTF8T( const TCHAR *src )
{
	if (src == NULL)
		return mir_strdup("");

	TCHAR *tmp = HtmlEncodeT(src);
#if defined( _UNICODE )
	char *ret = mir_utf8encodeW(tmp);
#else
	char *ret = mir_utf8encode(tmp);
#endif
	mir_free(tmp);
	return ret;
}

void __stdcall MSN_SendStatusMessage( const char* msg )
{
	if ( !msnLoggedIn || !MyOptions.UseMSNP11 )
		return;

	char* msgEnc = HtmlEncode(( msg == NULL ) ? "" : msg );
	char  szMsg[ 1024 ];

	if ( msnCurrentMedia.cbSize == 0 ) 
	{
		mir_snprintf( szMsg, sizeof szMsg, "<Data><PSM>%s</PSM></Data>", UTF8(msgEnc));
		mir_free( msgEnc );
	}
	else 
	{
		char *szFormatEnc;
		if (ServiceExists(MS_LISTENINGTO_GETPARSEDTEXT)) {
			LISTENINGTOINFO lti = {0};
			lti.cbSize = sizeof(lti);
			if (msnCurrentMedia.ptszTitle != NULL) lti.ptszTitle = _T("{0}");
			if (msnCurrentMedia.ptszArtist != NULL) lti.ptszArtist = _T("{1}");
			if (msnCurrentMedia.ptszAlbum != NULL) lti.ptszAlbum = _T("{2}");
			if (msnCurrentMedia.ptszTrack != NULL) lti.ptszTrack = _T("{3}");
			if (msnCurrentMedia.ptszYear != NULL) lti.ptszYear = _T("{4}");
			if (msnCurrentMedia.ptszGenre != NULL) lti.ptszGenre = _T("{5}");
			if (msnCurrentMedia.ptszLength != NULL) lti.ptszLength = _T("{6}");
			if (msnCurrentMedia.ptszPlayer != NULL) lti.ptszPlayer = _T("{7}");
			if (msnCurrentMedia.ptszType != NULL) lti.ptszType = _T("{8}");

			TCHAR *tmp = (TCHAR *)CallService(MS_LISTENINGTO_GETPARSEDTEXT, (WPARAM) _T("%title% - %artist%"), (LPARAM) &lti );
			szFormatEnc = HtmlEncodeUTF8T(tmp);
			mir_free(tmp);
		} else {
			szFormatEnc = HtmlEncodeUTF8T(_T("{0} - {1}"));
		}

		char *szArtist = HtmlEncodeUTF8T( msnCurrentMedia.ptszArtist );
		char *szAlbum = HtmlEncodeUTF8T( msnCurrentMedia.ptszAlbum );
		char *szTitle = HtmlEncodeUTF8T( msnCurrentMedia.ptszTitle );
		char *szTrack = HtmlEncodeUTF8T( msnCurrentMedia.ptszTrack );
		char *szYear = HtmlEncodeUTF8T( msnCurrentMedia.ptszYear );
		char *szGenre = HtmlEncodeUTF8T( msnCurrentMedia.ptszGenre );
		char *szLength = HtmlEncodeUTF8T( msnCurrentMedia.ptszLength );
		char *szPlayer = HtmlEncodeUTF8T( msnCurrentMedia.ptszPlayer );
		char *szType = HtmlEncodeUTF8T( msnCurrentMedia.ptszType );

		mir_snprintf( szMsg, sizeof szMsg, 
			"<Data><PSM>%s</PSM><CurrentMedia>%s\\0%s\\01\\0%s\\0%s\\0%s\\0%s\\0%s\\0%s\\0%s\\0%s\\0%s\\0%s\\0\\0</CurrentMedia></Data>", 
			UTF8(msgEnc), szPlayer, szType, szFormatEnc, szTitle, szArtist, szAlbum, szTrack, szYear, szGenre, szLength, szPlayer, szType);

		mir_free( szArtist );
		mir_free( szAlbum );
		mir_free( szTitle );
		mir_free( szTrack );
		mir_free( szYear );
		mir_free( szGenre );
		mir_free( szLength );
		mir_free( szPlayer );
		mir_free( szType );
	}

	if ( !lstrcmpA( msnPreviousUUX, szMsg ))
		return;

	replaceStr( msnPreviousUUX, szMsg );
	msnNsThread->sendPacket( "UUX", "%d\r\n%s", strlen( szMsg ), szMsg );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_SendPacket - sends a packet accordingly to the MSN protocol

LONG ThreadData::sendPacket( const char* cmd, const char* fmt,...)
{
	if ( this == NULL )  // :)
		return 0;

	va_list vararg;
	va_start( vararg, fmt );

	int strsize = 512;
	char* str = ( char* )mir_alloc( strsize );

	LONG thisTrid = MyInterlockedIncrement( &mTrid );

	if ( fmt == NULL || fmt[0] == '\0' )
		sprintf( str, "%s %d", cmd, thisTrid );
	else  {
		int paramStart = sprintf( str, "%s %d ", cmd, thisTrid );
		while ( _vsnprintf( str+paramStart, strsize-paramStart-2, fmt, vararg ) == -1 )
			str = (char*)mir_realloc( str, strsize += 512 );
	}

	if ( strcmp( cmd, "MSG" ) && strcmp( cmd, "QRY" ) && strcmp( cmd, "UUX" ))
		strcat( str,"\r\n" );

	int result = send( str, strlen( str ));
	mir_free( str );
	return ( result > 0 ) ? thisTrid : -1;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_SetServerStatus - changes plugins status at the server

void __stdcall MSN_SetServerStatus( int newStatus )
{
	MSN_DebugLog( "Setting MSN server status %d, logged in = %d", newStatus, msnLoggedIn );

	if ( !msnLoggedIn )
		return;

	char* szStatusName = MirandaStatusToMSN( newStatus  );

	if ( newStatus != ID_STATUS_OFFLINE ) {
		char szMsnObject[ 1000 ];
		if ( MSN_GetStaticString( "PictObject", NULL, szMsnObject, sizeof( szMsnObject )))
			szMsnObject[ 0 ] = 0;

		//here we say what functions can be used with this plugins : http://siebe.bot2k3.net/docs/?url=clientid.html
		msnNsThread->sendPacket( "CHG", "%s 1342177280 %s", szStatusName, szMsnObject );

		if ( MyOptions.UseMSNP11 ) {
			int status = newStatus == ID_STATUS_IDLE ? ID_STATUS_ONLINE : newStatus;
			for ( int i=0; i < MSN_NUM_MODES; i++ ) { 
				if ( msnModeMsgs[ i ].m_mode == status ) {
					MSN_SendStatusMessage( msnModeMsgs[ i ].m_msg );
					break;
		}	}	}
	}
	else msnNsThread->sendPacket( "CHG", szStatusName );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_ShowError - shows an error

void __cdecl MSN_ShowError( const char* msgtext, ... )
{
	char    tBuffer[ 4096 ];
	va_list tArgs;

	va_start( tArgs, msgtext );
	mir_vsnprintf( tBuffer, sizeof( tBuffer ), MSN_Translate( msgtext ), tArgs );
	va_end( tArgs );

	if ( MyOptions.ShowErrorsAsPopups )
		MSN_ShowPopup( msnProtocolName, tBuffer, MSN_ALLOW_MSGBOX | MSN_SHOW_ERROR );
	else
		MessageBoxA( NULL, tBuffer, msnProtocolName, MB_OK );
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_ShowPopup - popup plugin support

void CALLBACK sttMainThreadCallback( ULONG dwParam )
{
	POPUPDATAEX* ppd = ( POPUPDATAEX* )dwParam;

	if ( msnUseExtendedPopups )
		MSN_CallService( MS_POPUP_ADDPOPUPEX, ( WPARAM )ppd, 0 );
	else
		MSN_CallService( MS_POPUP_ADDPOPUP, ( WPARAM )ppd, 0 );

	mir_free( ppd );
}

void __stdcall	MSN_ShowPopup( const char* nickname, const char* msg, int flags )
{
	if ( !ServiceExists( MS_POPUP_ADDPOPUP )) {
		if ( flags & MSN_ALLOW_MSGBOX ) {
			char szMsg[ 1024 ];
			mir_snprintf( szMsg, SIZEOF( szMsg ), "%s:\n%s", nickname, msg );
			MessageBoxA( NULL, szMsg, "MSN Protocol", MB_OK + ( flags & MSN_SHOW_ERROR ) ? MB_ICONERROR : MB_ICONINFORMATION );
		}
		return;
	}

	POPUPDATAEX* ppd = ( POPUPDATAEX* )mir_calloc( sizeof( POPUPDATAEX ));

	ppd->lchContact = NULL;
	ppd->lchIcon = LoadIcon( hInst, MAKEINTRESOURCE( IDI_MSN ));
	strcpy( ppd->lpzContactName, nickname );
	strcpy( ppd->lpzText, msg );

	if ( flags & MSN_SHOW_ERROR ) {
		ppd->lchIcon = LoadIcon( NULL, IDI_WARNING );
		ppd->colorBack = RGB(191,0,0); //Red
		ppd->colorText = RGB(255,245,225); //Yellow
		ppd->iSeconds  = 60;
	}
	else {
		ppd->colorBack = ( MyOptions.UseWinColors ) ? GetSysColor( COLOR_BTNFACE ) : MyOptions.BGColour;
		ppd->colorText = ( MyOptions.UseWinColors ) ? GetSysColor( COLOR_WINDOWTEXT ) : MyOptions.TextColour;
		if ( msnUseExtendedPopups )
			ppd->iSeconds = ( flags & MSN_HOTMAIL_POPUP ) ? MyOptions.PopupTimeoutHotmail : MyOptions.PopupTimeoutOther;
	}

	ppd->PluginWindowProc = ( WNDPROC )NullWindowProc;
	ppd->PluginData = ( flags & MSN_ALLOW_ENTER ) ? &ppd : NULL;

	QueueUserAPC( sttMainThreadCallback , msnMainThread, ( ULONG )ppd );
}


/////////////////////////////////////////////////////////////////////////////////////////
// UrlDecode - converts URL chars like %20 into printable characters

static int SingleHexToDecimal(char c)
{
	if ( c >= '0' && c <= '9' ) return c-'0';
	if ( c >= 'a' && c <= 'f' ) return c-'a'+10;
	if ( c >= 'A' && c <= 'F' ) return c-'A'+10;
	return -1;
}

void __stdcall UrlDecode( char* str )
{
	char* s = str, *d = str;

	while( *s )
	{
		if ( *s != '%' ) {
			*d++ = *s++;
			continue;
		}

		int digit1 = SingleHexToDecimal( s[1] ), digit2 = SingleHexToDecimal( s[2] );
		if ( digit1 == -1 || digit2 == -1 ) {
			*d++ = *s++;
			continue;
		}

		s += 3;
		*d++ = ( digit1 << 4 ) + digit2;
	}

	*d = 0;
}

void __stdcall HtmlDecode( char* str )
{
	char* p, *q;

	if ( str == NULL )
		return;

	for ( p=q=str; *p!='\0'; p++,q++ ) {
		if ( *p == '&' ) {
			if ( !strncmp( p, "&amp;", 5 )) {	*q = '&'; p += 4; }
			else if ( !strncmp( p, "&apos;", 6 )) { *q = '\''; p += 5; }
			else if ( !strncmp( p, "&gt;", 4 )) { *q = '>'; p += 3; }
			else if ( !strncmp( p, "&lt;", 4 )) { *q = '<'; p += 3; }
			else if ( !strncmp( p, "&quot;", 6 )) { *q = '"'; p += 5; }
			else { *q = *p;	}
		}
		else {
			*q = *p;
		}
	}
	*q = '\0';
}

/////////////////////////////////////////////////////////////////////////////////////////
// HtmlEncode - replaces special HTML chars

WCHAR* __stdcall HtmlEncodeW( const WCHAR* str )
{
	WCHAR* s, *p, *q;
	int c;

	if ( str == NULL )
		return NULL;

	for ( c=0,p=( WCHAR* )str; *p!=L'\0'; p++ ) {
		switch ( *p ) {
		case L'&': c += 5; break;
		case L'\'': c += 6; break;
		case L'>': c += 4; break;
		case L'<': c += 4; break;
		case L'"': c += 6; break;
		default: c++; break;
		}
	}
	if (( s=( WCHAR* )mir_alloc( (c+1) * sizeof(WCHAR) )) != NULL ) {
		for ( p=( WCHAR* )str,q=s; *p!=L'\0'; p++ ) {
			switch ( *p ) {
			case L'&': wcscpy( q, L"&amp;" ); q += 5; break;
			case L'\'': wcscpy( q, L"&apos;" ); q += 6; break;
			case L'>': wcscpy( q, L"&gt;" ); q += 4; break;
			case L'<': wcscpy( q, L"&lt;" ); q += 4; break;
			case L'"': wcscpy( q, L"&quot;" ); q += 6; break;
			default: *q = *p; q++; break;
			}
		}
		*q = L'\0';
	}

	return s;
}

char* __stdcall HtmlEncode( const char* str )
{
	char* s, *p, *q;
	int c;

	if ( str == NULL )
		return NULL;

	for ( c=0,p=( char* )str; *p!='\0'; p++ ) {
		switch ( *p ) {
		case '&': c += 5; break;
		case '\'': c += 6; break;
		case '>': c += 4; break;
		case '<': c += 4; break;
		case '"': c += 6; break;
		default: c++; break;
		}
	}
	if (( s=( char* )mir_alloc( c+1 )) != NULL ) {
		for ( p=( char* )str,q=s; *p!='\0'; p++ ) {
			switch ( *p ) {
			case '&': strcpy( q, "&amp;" ); q += 5; break;
			case '\'': strcpy( q, "&apos;" ); q += 6; break;
			case '>': strcpy( q, "&gt;" ); q += 4; break;
			case '<': strcpy( q, "&lt;" ); q += 4; break;
			case '"': strcpy( q, "&quot;" ); q += 6; break;
			default: *q = *p; q++; break;
			}
		}
		*q = '\0';
	}

	return s;
}

/////////////////////////////////////////////////////////////////////////////////////////
// UrlEncode - converts printable characters into URL chars like %20

void __stdcall UrlEncode( const char* src,char* dest, int cbDest )
{
	BYTE* d = ( BYTE* )dest;
	int   i = 0;

	for( const BYTE* s = ( const BYTE* )src; *s; s++ ) {
		if (( *s < '0' && *s != '.' && *s != '-' ) ||
			 ( *s >= ':' && *s <= '?' ) ||
			 ( *s >= '[' && *s <= '`' && *s != '_' ))
		{
			if ( i >= cbDest-4 )
				break;

			*d++ = '%';
			_itoa( *s, ( char* )d, 16 );
			d += 2;
			i += 3;
		}
		else
		{
			*d++ = *s;
			if ( i++ == cbDest-1 )
				break;
	}	}

	*d = '\0';
}

/////////////////////////////////////////////////////////////////////////////////////////
// filetransfer class members

filetransfer::filetransfer()
{
	memset( this, 0, sizeof( filetransfer ));
	fileId = -1;
	std.cbSize = sizeof( std );

	hLockHandle = CreateMutex( NULL, FALSE, NULL );
}

filetransfer::~filetransfer( void )
{
	MSN_DebugLog( "Destroying file transfer session %lu", p2p_sessionid );

	WaitForSingleObject( hLockHandle, 2000 );
	CloseHandle( hLockHandle );

	if ( !bCompleted ) {
		std.files = NULL;
		std.totalFiles = 0;
		MSN_SendBroadcast( std.hContact, ACKTYPE_FILE, ACKRESULT_FAILED, this, 0);
	}

	if ( inmemTransfer ) {
		if ( fileBuffer != NULL )
			LocalFree( fileBuffer );
	}
	else if ( fileId != -1 )
		_close( fileId );

	if ( p2p_branch != NULL ) mir_free( p2p_branch );
	if ( p2p_callID != NULL ) mir_free( p2p_callID );
	if ( p2p_dest   != NULL ) mir_free( p2p_dest );

	if ( std.currentFile != NULL ) mir_free( std.currentFile );
	if ( std.workingDir != NULL ) mir_free( std.workingDir );
	if ( std.files != NULL ) {
		for ( int i=0; i < std.totalFiles; i++ )
			mir_free( std.files[ i ] );
		mir_free( std.files );
	}

	if ( wszFileName != NULL ) mir_free(  wszFileName );
	if ( szInvcookie != NULL ) mir_free( szInvcookie );
}

void filetransfer::close( void )
{
	if ( !inmemTransfer && fileId != -1 ) {
		_close( fileId );
		fileId = -1;
}	}

void filetransfer::complete( void )
{
	close();

	bCompleted = true;
	MSN_SendBroadcast( std.hContact, ACKTYPE_FILE, ACKRESULT_SUCCESS, this, 0);
}

int filetransfer::create( void )
{
	if ( inmemTransfer ) {
		if ( fileBuffer == NULL ) {
			if ( std.totalBytes == 0 ) {
				MSN_DebugLog( "Zero buffer size was requested for avatar" );
				return -1;
			}

			if (( fileBuffer = ( char* )LocalAlloc( LPTR, DWORD( std.currentFileSize ))) == NULL ) {
				MSN_DebugLog( "Not enough memory to receive file '%s'", std.currentFile );
				return -1;
		}	}

		return ( int )fileBuffer;
	}

	#if defined( _UNICODE )	
		if ( wszFileName != NULL ) {
			WCHAR wszTemp[ MAX_PATH ];
			_snwprintf( wszTemp, sizeof wszTemp, L"%S\\%s", std.workingDir, wszFileName );
			wszTemp[ MAX_PATH-1 ] = 0;
			fileId = _wopen( wszTemp, _O_BINARY | _O_CREAT | _O_TRUNC | _O_WRONLY, _S_IREAD | _S_IWRITE);
			if ( fileId != -1 ) {
				WIN32_FIND_DATAW data;
				HANDLE hFind = FindFirstFileW( wszFileName, &data );
				if ( hFind != INVALID_HANDLE_VALUE ) {
					mir_free( std.currentFile );

					char tShortName[ 20 ];
					WideCharToMultiByte( CP_ACP, 0, 
						( data.cAlternateFileName[0] != 0 ) ? data.cAlternateFileName : data.cFileName, 
						-1, tShortName, sizeof tShortName, 0, 0 );
					char filefull[ MAX_PATH ];
					mir_snprintf( filefull, sizeof( filefull ), "%s\\%s", std.workingDir, tShortName );
					std.currentFile = mir_strdup( filefull );
					FindClose( hFind );
			}	}
		}
		else fileId = _open( std.currentFile, _O_BINARY | _O_CREAT | _O_TRUNC | _O_WRONLY, _S_IREAD | _S_IWRITE );
	#else
		fileId = _open( std.currentFile, _O_BINARY | _O_CREAT | _O_TRUNC | _O_WRONLY, _S_IREAD | _S_IWRITE );
	#endif

	if ( fileId == -1 )
		MSN_DebugLog( "Cannot create file '%s' during a file transfer", std.currentFile );
//	else if ( std.currentFileSize != 0 )
//		_chsize( fileId, std.currentFileSize );

	return fileId;
}

int filetransfer::openNext( void )
{
	if ( fileId != -1 ) {
		close();
		++std.currentFileNumber;
	}

	if ( std.currentFileNumber < std.totalFiles) {
		bCompleted = false;
		replaceStr(std.currentFile, std.files[std.currentFileNumber] );
		fileId = _open( std.currentFile, _O_BINARY | _O_RDONLY, _S_IREAD );
		if ( fileId != -1 ) {
			std.currentFileSize = filelength( fileId );
			std.currentFileProgress = 0;
			
			p2p_sendmsgid = 0;
			p2p_byemsgid = 0;
			tType = SERVER_DISPATCH;

			mir_free( p2p_branch ); p2p_branch = NULL;
			mir_free( p2p_callID ); p2p_callID = NULL;
		}
		else
			MSN_DebugLog( "Unable to open file '%s', error %d", std.currentFile, errno );
	}

	return fileId;
}

directconnection::directconnection(filetransfer* ft)
{
	memset( this, 0, sizeof( directconnection ));

	callId = mir_strdup( ft->p2p_callID );
	mNonce = ( UUID* )mir_alloc( sizeof( UUID ));
	UuidCreate( mNonce );
	ts = time( NULL );
}

directconnection::~directconnection()
{
	mir_free( callId );
	mir_free( mNonce );
	mir_free( xNonce );
}


char* directconnection::calcHashedNonce(UUID* nonce)
{
	SHA1Context sha1ctx;
	BYTE sha[ SHA1HashSize ];

	SHA1Reset( &sha1ctx );
	SHA1Input( &sha1ctx, ( BYTE* )nonce, sizeof( UUID ));
	SHA1Result( &sha1ctx, sha );

	char* p;
	UuidToStringA(( UUID* )&sha, ( BYTE** )&p );
	size_t len = strlen( p ) + 3;
	char* result = ( char* )mir_alloc( len );
	mir_snprintf(result, len, "{%s}", p );
	strupr( result );
	RpcStringFreeA(( BYTE** )&p );

	return result;
}

char* directconnection::mNonceToText( void )
{
	char* p;
	UuidToStringA( mNonce, ( BYTE** )&p );
	size_t len = strlen( p ) + 3;
	char* result = ( char* )mir_alloc( len );
	mir_snprintf(result, len, "{%s}", p );
	strupr( result );
	RpcStringFreeA(( BYTE** )&p );

	return result;
}


void directconnection::xNonceToBin( UUID* nonce )
{
	size_t len = strlen( xNonce );
	char *p = ( char* )alloca( len );
	strcpy( p, xNonce + 1 );
	p[len-2] = 0;
	UuidFromStringA(( BYTE* )p, nonce );
}

/////////////////////////////////////////////////////////////////////////////////////////
// TWinErrorCode class

TWinErrorCode::TWinErrorCode() :
	mErrorText( NULL )
{
	mErrorCode = ::GetLastError();
}

TWinErrorCode::~TWinErrorCode()
{
	if ( mErrorText != NULL )
		::LocalFree( mErrorText );
}

char* TWinErrorCode::getText()
{
	if ( mErrorText == NULL )
	{
		int tBytes = 0;

		if ( mErrorCode >= 12000 && mErrorCode < 12500 )
			tBytes = FormatMessageA(
				FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_HMODULE,
				::GetModuleHandleA( "WININET.DLL" ),
				mErrorCode, LANG_NEUTRAL, (LPSTR)&mErrorText, 0, NULL );

		if ( tBytes == 0 )
			tBytes = FormatMessageA(
				FORMAT_MESSAGE_ALLOCATE_BUFFER | FORMAT_MESSAGE_FROM_SYSTEM, NULL,
				mErrorCode, LANG_NEUTRAL, (LPSTR)&mErrorText, 0, NULL );

		if ( tBytes == 0 )
		{
			mErrorText = ( LPSTR )LocalAlloc( LMEM_FIXED, 100 );
			tBytes = mir_snprintf( mErrorText, 100, "unknown Windows error code %d", mErrorCode );
		}

		*mErrorText = tolower( *mErrorText );

		if ( mErrorText[ tBytes-1 ] == '\n' )
			mErrorText[ --tBytes ] = 0;
		if ( mErrorText[ tBytes-1 ] == '\r' )
			mErrorText[ --tBytes ] = 0;
		if ( mErrorText[ tBytes-1 ] == '.' )
			mErrorText[ tBytes-1 ] = 0;
	}

	return mErrorText;
}

/////////////////////////////////////////////////////////////////////////////////////////
// InterlockedIncrement emulation

//I hate Microsoft (c) cyreve

static LONG WINAPI MyInterlockedIncrement95(PLONG pVal)
{
	DWORD ret;
	EnterCriticalSection(&csInterlocked95);
	ret=++*pVal;
	LeaveCriticalSection(&csInterlocked95);
	return ret;
}

//there's a possible hole here if too many people call this at the same time, but that doesn't happen

static LONG WINAPI MyInterlockedIncrementInit(PLONG pVal)
{
	DWORD ver = GetVersion();
   if (( ver & 0x80000000 ) && LOWORD( ver ) == 0x0004 )
	{
		InitializeCriticalSection( &csInterlocked95 );
		MyInterlockedIncrement = MyInterlockedIncrement95;
	}
   else  MyInterlockedIncrement = ( pIncrementFunc* )InterlockedIncrement;

	return MyInterlockedIncrement( pVal );
}

// Process a string, and double all % characters, according to chat.dll's restrictions
// Returns a pointer to the new string (old one is not freed)
TCHAR* EscapeChatTags(TCHAR* pszText)
{
	int nChars = 0;
	for ( TCHAR* p = pszText; ( p = _tcschr( p, '%' )) != NULL; p++ )
		nChars++;

	if ( nChars == 0 )
		return mir_tstrdup( pszText );

	TCHAR* pszNewText = (TCHAR*)mir_alloc( sizeof(TCHAR)*(lstrlen( pszText ) + 1 + nChars)), *s, *d;
	if ( pszNewText == NULL )
		return mir_tstrdup( pszText );

	for ( s = pszText, d = pszNewText; *s; s++ ) {
		if ( *s == '%' )
			*d++ = '%';
		*d++ = *s;
	}
	*d = 0;
	return pszNewText;
}

TCHAR* UnEscapeChatTags(TCHAR* str_in)
{
	TCHAR *s = str_in, *d = str_in;
	while ( *s ) {
		if (( *s == '%' && s[1] == '%' ) || ( *s == '\n' && s[1] == '\n' ))
			s++;
		*d++ = *s++;
	}
	*d = 0;
	return str_in;
}

bool txtParseParam (const char* szData, const char* presearch, const char* start, const char* finish, char* param, const int size)
{
	const char *cp, *cp1;
	int len;
	
	if (szData == NULL) return false;

	if (presearch != NULL)
	{
		cp1 = strstr(szData, presearch);
		if (cp1 == NULL) return false;
	}
	else
		cp1 = szData;

	cp = strstr(cp1, start);
	if (cp == NULL) return false;
	cp += strlen(start);
	while (*cp == ' ') ++cp;

	cp1 = strstr(cp, finish);
	if (cp1 == NULL) return FALSE;
	while (*(cp1-1) == ' ' && cp1 > cp) --cp1;

	len = min(cp1 - cp, size - 1);
	memmove(param, cp, len);
	param[len] = 0;

	return true;
} 


void MSN_Base64Decode( const char* str, char* res, size_t reslen )
{
	if ( str == NULL ) res[0] = 0;

	char* p = const_cast< char* >( str );
	int cbLen = strlen( p );
	if ( cbLen & 3 ) { // fix for stupid Kopete's base64 encoder
		char* p1 = ( char* )alloca( cbLen+5 );
		memcpy( p1, p, cbLen );
		p = p1;
		p1 += cbLen; 
		for ( int i = 4 - (cbLen & 3); i > 0; i--, p1++, cbLen++ )
			*p1 = '=';
		*p1 = 0;
	}

	NETLIBBASE64 nlb = { p, cbLen, ( PBYTE )res, reslen };
	MSN_CallService( MS_NETLIB_BASE64DECODE, 0, LPARAM( &nlb ));
	res[nlb.cbDecoded] = 0;
}
