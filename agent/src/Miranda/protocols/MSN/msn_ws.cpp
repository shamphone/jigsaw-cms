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

static char sttGatewayHeader[] =
	"POST %s HTTP/1.1\r\n"
	"Accept: */*\r\n"
	"Content-Type: text/xml; charset=utf-8\r\n"
	"Content-Length: %d\r\n"
	"User-Agent: %s\r\n"
	"Host: %s\r\n"
	"Connection: Keep-Alive\r\n"
	"Cache-Control: no-cache\r\n\r\n";

//=======================================================================================

int ThreadData::send( char* data, int datalen )
{
	if ( this == NULL )
		return 0;

	NETLIBBUFFER nlb = { data, datalen, 0 };

	mWaitPeriod = mJoinedCount ? 60 : 15;

	if ( MyOptions.UseGateway && !( mType == SERVER_FILETRANS || mType == SERVER_P2P_DIRECT )) {
		mGatewayTimeout = 2;

		if ( !MyOptions.UseProxy ) {
			TQueueItem* tNewItem = ( TQueueItem* )mir_alloc( datalen + sizeof( void* ) + sizeof( int ) + 1 );
			tNewItem->datalen = datalen;
			memcpy( tNewItem->data, data, datalen );
			tNewItem->data[datalen] = 0;
			tNewItem->next = NULL;

			WaitForSingleObject( hQueueMutex, INFINITE );
			TQueueItem **p = &mFirstQueueItem;
			while ( *p != NULL ) p = &(*p)->next;
			*p = tNewItem;
			++numQueueItems;
			ReleaseMutex( hQueueMutex );

			return TRUE;
		}

		MSN_CallService( MS_NETLIB_SETPOLLINGTIMEOUT, WPARAM( s ), mGatewayTimeout );
	}

	int rlen = MSN_CallService( MS_NETLIB_SEND, ( WPARAM )s, ( LPARAM )&nlb );
	if ( rlen == SOCKET_ERROR ) {
		// should really also check if sendlen is the same as datalen
		MSN_DebugLog( "Send failed: %d", WSAGetLastError() );
		return FALSE;
	}

	return TRUE;
}

bool ThreadData::isTimeout( void )
{
	bool res = false;

	if ( --mWaitPeriod > 0 ) return false;

	if ( !mIsMainThread && ( mJoinedCount <= 1 || mChatID[0] == 0 )) {
		if ( mJoinedCount == 0 )
			res = true;
		else if ( p2p_getThreadSession( mJoinedContacts[0], mType ) != NULL )
			res = false;
		else if ( mType == SERVER_SWITCHBOARD ) {
			MessageWindowInputData msgWinInData = { 
				sizeof( MessageWindowInputData ), mJoinedContacts[0], MSG_WINDOW_UFLAG_MSG_BOTH 
			};
			MessageWindowData msgWinData = {0};
			msgWinData.cbSize = sizeof( MessageWindowData );

			res = MSN_CallService( MS_MSG_GETWINDOWDATA, ( WPARAM )&msgWinInData, ( LPARAM )&msgWinData ) != 0;
			res = res || msgWinData.hwndWindow == NULL;
		}
		else
			res = true;
	}

	if ( res ) {
		bool sbsess = mType == SERVER_SWITCHBOARD;

		MSN_DebugLog( "Dropping the idle %s due to inactivity", sbsess ? "switchboard" : "p2p");
		if ( !sbsess ) return true; 

		sendPacket( "OUT", NULL );
		mWaitPeriod = 15;
	}
	else
		mWaitPeriod = 60;

	return false;
}


//=======================================================================================
// Receving data
//=======================================================================================

int ThreadData::recv_dg( char* data, long datalen )
{
	if ( mReadAheadBuffer != NULL ) {
		int tBytesToCopy = ( datalen >= mEhoughData ) ? mEhoughData : datalen;
		memcpy( data, mReadAheadBuffer, tBytesToCopy );
		mEhoughData -= tBytesToCopy;
		if ( mEhoughData == 0 ) {
			mir_free( mReadAheadBuffer );
			mReadAheadBuffer = NULL;
		}
		else memmove( mReadAheadBuffer, mReadAheadBuffer + tBytesToCopy, mEhoughData );

		return tBytesToCopy;
	}

	bool bCanPeekMsg = true;

LBL_RecvAgain:
	int ret = 0;
	{
		NETLIBSELECT tSelect = {0};
		tSelect.cbSize = sizeof( tSelect );
		tSelect.dwTimeout = 1000;
		tSelect.hReadConns[ 0 ] = ( HANDLE )s;

		for ( int i=0; i < mGatewayTimeout || !bCanPeekMsg; i++ ) {
			if ( bCanPeekMsg && numQueueItems > 0) {
				unsigned np = 0, dlen = 0;
				
				WaitForSingleObject( hQueueMutex, INFINITE );
				TQueueItem* QI = mFirstQueueItem;
				while ( QI != NULL && np < 5) { ++np; dlen += QI->datalen;  QI = QI->next;}

				if ( np == 0 ) { 
					ReleaseMutex( hQueueMutex );
					continue;
				}

				char szHttpPostUrl[300];
				getGatewayUrl( szHttpPostUrl, sizeof( szHttpPostUrl ), mFirstQueueItem->datalen == 0 );

				char* tBuffer = ( char* )alloca( 8192 );
				int cbBytes = mir_snprintf( tBuffer, 8192, sttGatewayHeader,
					szHttpPostUrl, dlen, MSN_USER_AGENT, mGatewayIP);
				
				QI = mFirstQueueItem;
				for ( unsigned i=0; i<np; ++i ) {
					memcpy( tBuffer+cbBytes, QI->data, QI->datalen );
					cbBytes += QI->datalen;
					QI = QI->next;
				}
				ReleaseMutex( hQueueMutex );

				tBuffer[ cbBytes ] = 0;

				NETLIBBUFFER nlb = { tBuffer, cbBytes, 0 };
				ret = MSN_CallService( MS_NETLIB_SEND, ( WPARAM )s, ( LPARAM )&nlb );
				if ( ret == SOCKET_ERROR ) {
					MSN_DebugLog( "Send failed: %d", WSAGetLastError() );
					return 0;
				}

				WaitForSingleObject( hQueueMutex, INFINITE );
				for ( unsigned j=0; j<np && mFirstQueueItem != NULL; ++j ) {
					QI = mFirstQueueItem;
					mFirstQueueItem = QI->next;
					mir_free( QI );
					--numQueueItems;
				}

				if ( numQueueItems < 5 )
					SetEvent( hWaitEvent );

				ReleaseMutex( hQueueMutex );

				ret = 1;
				break;
			}

			ret = MSN_CallService( MS_NETLIB_SELECT, 0, ( LPARAM )&tSelect );
			if ( ret != 0 )
				break;
			
			// Timeout switchboard session if inactive
			if ( isTimeout() ) return 0;
		}	
	}

	bCanPeekMsg = false;

	if ( ret == 0 ) {
		mGatewayTimeout += 2;
		if ( mGatewayTimeout > 8 ) 
			mGatewayTimeout = 8;

		char szHttpPostUrl[300];
		getGatewayUrl( szHttpPostUrl, sizeof( szHttpPostUrl ), true );

		char szCommand[ 400 ];
		int cbBytes = mir_snprintf( szCommand, sizeof( szCommand ),
			sttGatewayHeader, szHttpPostUrl, 0, MSN_USER_AGENT, mGatewayIP);

		NETLIBBUFFER nlb = { szCommand, cbBytes, 0 };
		MSN_CallService( MS_NETLIB_SEND, ( WPARAM )s, ( LPARAM )&nlb );
		goto LBL_RecvAgain;
	}

	NETLIBBUFFER nlb = { data, datalen, 0 };
	ret = MSN_CallService( MS_NETLIB_RECV, ( WPARAM )s, ( LPARAM )&nlb );
	if ( ret == 0 ) {
		MSN_DebugLog( "Connection closed gracefully");
		return 0;
	}

	if ( ret < 0 ) {
		MSN_DebugLog( "Connection abortively closed, error %d", WSAGetLastError() );
		return ret;
	}

	bCanPeekMsg = true;

	char* p = strstr( data, "\r\n" );
	if ( p == NULL ) {
		MSN_DebugLog( "ACHTUNG! it's not a valid header: '%s'", data );
		goto LBL_RecvAgain;
	}

	int status = 0;
	sscanf( data, "HTTP/1.1 %d", &status );
	if ( status == 100 )
		goto LBL_RecvAgain;
	
	int   tContentLength = 0, hdrLen;
	{
		MimeHeaders tHeaders;
		const char* rest = tHeaders.readFromBuffer( p+2 );
		if ( *rest == '\r' )
			rest += 2;

		for ( int i=0; i < tHeaders.mCount; i++ )
		{
			MimeHeader& H = tHeaders.mVals[i];

			if ( stricmp( H.name, "X-MSN-Messenger" ) == 0 ) {
				if ( strstr( H.value, "Session=close" ) != 0 ) {
					return 0;
				}

				processSessionData( H.value );
			}

			if ( stricmp( H.name, "Content-Length" ) == 0 )
				tContentLength = atol( H.value );
		}

		hdrLen = int( rest - data );
	}

	if ( tContentLength == 0 )
		goto LBL_RecvAgain;
	else
	{
		mGatewayTimeout = 1;
		mWaitPeriod = mJoinedCount ? 60 : 15;
	}

	ret -= hdrLen;
	if ( ret <= 0 ) {
		nlb.buf = data;
		nlb.len = datalen;
		ret = MSN_CallService( MS_NETLIB_RECV, ( WPARAM )s, ( LPARAM )&nlb );
		if ( ret <= 0 )
			return ret;
	}
	else memmove( data, data+hdrLen, ret );

	if ( tContentLength > ret ) {
		tContentLength -= ret;

		mReadAheadBuffer = ( char* )mir_calloc( tContentLength+1 );
		mReadAheadBuffer[ tContentLength ] = 0;
		mEhoughData = tContentLength;
		nlb.buf = mReadAheadBuffer;

		while ( tContentLength > 0 ) {
			nlb.len = tContentLength;
			int ret2 = MSN_CallService( MS_NETLIB_RECV, ( WPARAM )s, ( LPARAM )&nlb );
			if ( ret2 <= 0 )
			{	mir_free( mReadAheadBuffer );
				mReadAheadBuffer = NULL;
				return ret2;
			}

			tContentLength -= ret2;
			nlb.buf += ret2;
	}	}

	return ret;
}

int ThreadData::recv( char* data, long datalen )
{
	if ( MyOptions.UseGateway && !MyOptions.UseProxy )
		if ( mType != SERVER_FILETRANS && mType != SERVER_P2P_DIRECT )
			return recv_dg( data, datalen );

	NETLIBBUFFER nlb = { data, datalen, 0 };

LBL_RecvAgain:
	if ( !mIsMainThread && !MyOptions.UseGateway && !MyOptions.UseProxy ) {
		mWaitPeriod = mJoinedCount ? 60 : 15;
		for ( ;; ) {
			NETLIBSELECT nls = { 0 };
			nls.cbSize = sizeof( nls );
			nls.dwTimeout = 1000;
			nls.hReadConns[0] = s;
			if ( MSN_CallService( MS_NETLIB_SELECT, 0, ( LPARAM )&nls ) != 0 )
				break;

			if ( isTimeout() ) 
				return 0;
	}	}

	int ret = MSN_CallService( MS_NETLIB_RECV, ( WPARAM )s, ( LPARAM )&nlb );
	if ( ret == 0 ) {
		MSN_DebugLog( "Connection closed gracefully" );
		return 0;
	}

	if ( ret < 0 ) {
		MSN_DebugLog( "Connection abortively closed, error %d", WSAGetLastError() );
		return ret;
	}

	if ( MyOptions.UseGateway)
	{
		if ( ret == 1 && *data == 0 ) 
		{
			int tOldTimeout = MSN_CallService( MS_NETLIB_SETPOLLINGTIMEOUT, WPARAM( s ), 2 );
			tOldTimeout += 2;
			if ( tOldTimeout > 8 )
				tOldTimeout = 8;

			MSN_CallService( MS_NETLIB_SETPOLLINGTIMEOUT, WPARAM( s ), tOldTimeout );
			goto LBL_RecvAgain;
		}
		else MSN_CallService( MS_NETLIB_SETPOLLINGTIMEOUT, WPARAM( s ), 1 );
	}

	return ret;
}
