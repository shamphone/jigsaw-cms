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
#include <m_system_cpp.h>

#include <direct.h>

int MSN_HandleCommands(ThreadData *info,char *cmdString);
int MSN_HandleErrors(ThreadData *info,char *cmdString);
int MSN_HandleMSNFTP( ThreadData *info, char *cmdString );

extern LONG (WINAPI *MyInterlockedIncrement)(PLONG pVal);
extern unsigned long sl;

HANDLE hKeepAliveThreadEvt = NULL;

/////////////////////////////////////////////////////////////////////////////////////////
//	Keep-alive thread for the main connection

int msnPingTimeout = 45;

void __cdecl msn_keepAliveThread( void* )
{
	bool keepFlag = true;

	hKeepAliveThreadEvt = CreateEvent( NULL, FALSE, FALSE, NULL );

	msnPingTimeout = 45;

	while ( keepFlag )
	{
		switch ( WaitForSingleObject( hKeepAliveThreadEvt, msnPingTimeout * 1000 ))
		{
			case WAIT_TIMEOUT:
				keepFlag = msnNsThread != NULL;
				if ( MyOptions.UseGateway )
					msnPingTimeout = 45;
				else {
					msnPingTimeout = 3;
					keepFlag = keepFlag && msnNsThread->send( "PNG\r\n", 5 );
				}
				p2p_clearDormantSessions();
				break;

			case WAIT_OBJECT_0:
				keepFlag = msnPingTimeout > 0;
				break;

			default:
				keepFlag = false;
				break;
	}	}

	CloseHandle( hKeepAliveThreadEvt ); hKeepAliveThreadEvt = NULL;
	MSN_DebugLog( "Closing keep-alive thread" );
}

/////////////////////////////////////////////////////////////////////////////////////////
//	MSN redirector detection thread - refreshes the information about the redirector

static bool sttRedirectorWasChecked = false;

void __cdecl msn_RedirectorThread( ThreadData* info )
{
	extern int MSN_CheckRedirector();
	MSN_CheckRedirector();
}

/////////////////////////////////////////////////////////////////////////////////////////
//	MSN server thread - read and process commands from a server

void __cdecl MSNServerThread( ThreadData* info )
{
	if ( !sttRedirectorWasChecked ) {
		sttRedirectorWasChecked = true;
		mir_forkthread(( pThreadFunc )msn_RedirectorThread, NULL );
	}

	NETLIBOPENCONNECTION tConn = { 0 };
	tConn.cbSize = sizeof( tConn );
	tConn.flags = NLOCF_V2;

 	char* tPortDelim = strrchr( info->mServer, ':' );
	if ( tPortDelim != NULL )
		*tPortDelim = '\0';

	if ( MyOptions.UseGateway && !MyOptions.UseProxy ) {
		tConn.szHost = MSN_DEFAULT_GATEWAY;
		tConn.wPort = 80;
		info->hQueueMutex = CreateMutex( NULL, FALSE, NULL );
	}
	else {
		tConn.szHost = info->mServer;
		tConn.wPort = MSN_DEFAULT_PORT;

		if ( tPortDelim != NULL ) {
			int tPortNumber;
			if ( sscanf( tPortDelim+1, "%d", &tPortNumber ) == 1 )
				tConn.wPort = ( WORD )tPortNumber;
	}	}

	MSN_DebugLog( "Thread started: server='%s', type=%d", tConn.szHost, info->mType );

	info->s = ( HANDLE )MSN_CallService( MS_NETLIB_OPENCONNECTION, ( WPARAM )hNetlibUser, ( LPARAM )&tConn );
	if ( info->s == NULL ) {
		MSN_DebugLog( "Connection Failed (%d)", WSAGetLastError() );

		switch ( info->mType ) {
		case SERVER_NOTIFICATION:
		case SERVER_DISPATCH:
			MSN_SendBroadcast( NULL, ACKTYPE_LOGIN, ACKRESULT_FAILED, NULL, LOGINERR_NOSERVER );
			MSN_GoOffline();
			break;
		}

		return;
	}

	if ( MyOptions.UseGateway )
		MSN_CallService( MS_NETLIB_SETPOLLINGTIMEOUT, WPARAM( info->s ), 2 );

	MSN_DebugLog( "Connected with handle=%08X", info->s );

	if ( info->mType == SERVER_DISPATCH || info->mType == SERVER_NOTIFICATION ) {
		if ( MyOptions.UseMSNP11 )
			info->sendPacket( "VER", "MSNP12 MSNP11 MSNP10 CVR0" );
		else
			info->sendPacket( "VER", "MSNP10 MSNP9 CVR0" );
	}
	else if ( info->mType == SERVER_SWITCHBOARD ) {
		info->sendPacket( info->mCaller ? "USR" : "ANS", "%s %s", MyOptions.szEmail, info->mCookie );
	}
	else if ( info->mType == SERVER_FILETRANS && info->mCaller == 0 ) {
		info->send( "VER MSNFTP\r\n", 12 );
	}

	if ( info->mIsMainThread ) {
		MSN_EnableMenuItems( TRUE );
		msnNsThread = info;
	}

	if ( info->mType == SERVER_DISPATCH )
		mir_forkthread(( pThreadFunc )msn_keepAliveThread, NULL );

	MSN_DebugLog( "Entering main recv loop" );
	info->mBytesInData = 0;
	while ( TRUE ) {
		int handlerResult;

		int recvResult = info->recv( info->mData + info->mBytesInData, sizeof( info->mData ) - info->mBytesInData );
		if ( recvResult == SOCKET_ERROR ) {
			MSN_DebugLog( "Connection %08p [%08X] was abortively closed", info->s, GetCurrentThreadId());
			break;
		}

		if ( !recvResult ) {
			MSN_DebugLog( "Connection %08p [%08X] was gracefully closed", info->s, GetCurrentThreadId());
			break;
		}

		info->mBytesInData += recvResult;

		if ( info->mCaller == 1 && info->mType == SERVER_FILETRANS ) {
			handlerResult = MSN_HandleMSNFTP( info, info->mData );
			if ( handlerResult )
				break;
		}
		else {
			while( TRUE ) {
				char* peol = strchr(info->mData,'\r');
				if ( peol == NULL )
					break;

				if ( info->mBytesInData < peol-info->mData+2 )
					break;  //wait for full line end

				char msg[ sizeof(info->mData) ];
				memcpy( msg, info->mData, peol-info->mData ); msg[ peol-info->mData ] = 0;

				if ( *++peol != '\n' )
					MSN_DebugLog( "Dodgy line ending to command: ignoring" );
				else
					peol++;

				info->mBytesInData -= peol - info->mData;
				memmove( info->mData, peol, info->mBytesInData );
				MSN_DebugLog( "RECV:%s", msg );

				if ( !isalnum( msg[0] ) || !isalnum(msg[1]) || !isalnum(msg[2]) || (msg[3] && msg[3]!=' ')) {
					MSN_DebugLog( "Invalid command name" );
					continue;
				}

				if ( info->mType != SERVER_FILETRANS ) {
					if ( info->mType == SERVER_NOTIFICATION )
						SetEvent( hKeepAliveThreadEvt );

					if ( isdigit(msg[0]) && isdigit(msg[1]) && isdigit(msg[2]))   //all error messages
						handlerResult = MSN_HandleErrors( info, msg );
					else
						handlerResult = MSN_HandleCommands( info, msg );
				}
				else handlerResult = MSN_HandleMSNFTP( info, msg );

				if ( handlerResult )
					goto LBL_Exit;
		}	}

		if ( info->mBytesInData == sizeof( info->mData )) {
			MSN_DebugLog( "sizeof(data) is too small: the longest line won't fit" );
			break;
	}	}

LBL_Exit:
	if ( info->mIsMainThread ) {
		MSN_GoOffline();
		msnNsThread = NULL;
		if ( hKeepAliveThreadEvt ) {
			msnPingTimeout *= -1;
			SetEvent( hKeepAliveThreadEvt );
		}
	}

	MSN_DebugLog( "Thread [%d] ending now", GetCurrentThreadId() );
}

/////////////////////////////////////////////////////////////////////////////////////////
//  Added by George B. Hazan (ghazan@postman.ru)
//  The following code is required to abortively stop all started threads upon exit

static int CompareThreads( const ThreadData* p1, const ThreadData* p2 )
{
	return int( p1 - p2 );
}

static LIST<ThreadData> sttThreads( 10, CompareThreads );
static CRITICAL_SECTION	sttLock;

void __stdcall MSN_InitThreads()
{
	InitializeCriticalSection( &sttLock );
}

void __stdcall MSN_CloseConnections()
{
	int i;

	EnterCriticalSection( &sttLock );

	for ( i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];

		switch (T->mType) {
		case SERVER_DISPATCH :
		case SERVER_NOTIFICATION :
		case SERVER_SWITCHBOARD :
			if (T->s != NULL)
				T->sendPacket( "OUT", NULL );
			break;

		case SERVER_P2P_DIRECT :
			{
				SOCKET s = MSN_CallService( MS_NETLIB_GETSOCKET, LPARAM( T->s ), 0 );
				if ( s != INVALID_SOCKET )
					shutdown( s, 2 );
			}
			break;
	}	}

	LeaveCriticalSection( &sttLock );
}

void __stdcall MSN_CloseThreads()
{
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->s == NULL )
			continue;

		SOCKET s = MSN_CallService( MS_NETLIB_GETSOCKET, LPARAM( T->s ), 0 );
		if ( s != INVALID_SOCKET )
			shutdown( s, 2 );
	}

	LeaveCriticalSection( &sttLock );
}

void Threads_Uninit( void )
{
	DeleteCriticalSection( &sttLock );
	sttThreads.destroy();
}

ThreadData* __stdcall MSN_GetThreadByContact( HANDLE hContact, TInfoType type )
{
	ThreadData* result = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mJoinedCount == 0 || T->mJoinedContacts == NULL || T->s == NULL || T->mType != type )
			continue;

		if ( T->mJoinedContacts[0] == hContact && T->mInitialContact == NULL )
			result = T;
	}

	LeaveCriticalSection( &sttLock );
	return result;
}

ThreadData* __stdcall MSN_GetThreadByTimer( UINT timerId )
{
	ThreadData* result = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mType == SERVER_SWITCHBOARD && T->mTimerId == timerId ) {
			result = T;
			break;
	}	}

	LeaveCriticalSection( &sttLock );
	return result;
}

ThreadData* __stdcall MSN_GetP2PThreadByContact( HANDLE hContact )
{
	ThreadData *p2pT = NULL, *sbT = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; p2pT == NULL && i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mJoinedCount == 0 || T->mJoinedContacts == NULL )
			continue;

		if ( T->mJoinedContacts[0] == hContact && T->mInitialContact == NULL ) {
			switch ( T->mType ) {
			case SERVER_SWITCHBOARD:
				sbT = T;
				break;

			case SERVER_P2P_DIRECT:
				p2pT = T;
				break;
	}	}	}

	LeaveCriticalSection( &sttLock );

	return ( p2pT ? p2pT : sbT );
}


void __stdcall MSN_StartP2PTransferByContact( HANDLE hContact )
{
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mJoinedCount == 0 || T->mJoinedContacts == NULL )
			continue;

		if ( T->mJoinedContacts[0] == hContact && T->mType == SERVER_FILETRANS
			  && T->hWaitEvent != INVALID_HANDLE_VALUE )
			SetEvent( T->hWaitEvent );
	}

	LeaveCriticalSection( &sttLock );
}


ThreadData* __stdcall MSN_GetOtherContactThread( ThreadData* thread )
{
	ThreadData* result = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mJoinedCount == 0 || T->mJoinedContacts == NULL || T->s == NULL )
			continue;

		if ( T != thread && T->mJoinedContacts[0] == thread->mJoinedContacts[0] ) {
			result = T;
			break;
	}	}

	LeaveCriticalSection( &sttLock );
	return result;
}

ThreadData* __stdcall MSN_GetUnconnectedThread( HANDLE hContact )
{
	ThreadData* result = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mInitialContact == hContact && T->mType == SERVER_SWITCHBOARD ) {
			result = T;
			break;
	}	}

	LeaveCriticalSection( &sttLock );
	return result;
}

int __stdcall MSN_GetActiveThreads( ThreadData** parResult )
{
	int tCount = 0;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mType == SERVER_SWITCHBOARD && T->mJoinedCount != 0 && T->mJoinedContacts != NULL )
			parResult[ tCount++ ] = T;
	}

	LeaveCriticalSection( &sttLock );
	return tCount;
}

ThreadData* __stdcall MSN_GetThreadByConnection( HANDLE s )
{
	ThreadData* tResult = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->s == s ) {
			tResult = T;
			break;
	}	}

	LeaveCriticalSection( &sttLock );
	return tResult;
}

ThreadData* __stdcall MSN_GetThreadByPort( WORD wPort )
{
	ThreadData* result = NULL;
	EnterCriticalSection( &sttLock );

	for ( int i=0; i < sttThreads.getCount(); i++ ) {
		ThreadData* T = sttThreads[ i ];
		if ( T->mIncomingPort == wPort ) {
			result = T;
			break;
	}	}

	LeaveCriticalSection( &sttLock );
	return result;
}

/////////////////////////////////////////////////////////////////////////////////////////
// class ThreadData members

static LONG sttThreadID = 1;

ThreadData::ThreadData()
{
	memset( this, 0, sizeof( ThreadData ));
	mGatewayTimeout = 2;
	mWaitPeriod = 15;
	mIsMainThread = false;
	hWaitEvent = CreateEvent( NULL, FALSE, FALSE, NULL );
}

ThreadData::~ThreadData()
{
	if ( s != NULL ) {
		MSN_DebugLog( "Closing connection handle %08X", s );
		Netlib_CloseHandle( s );
	}

	if ( mIncomingBoundPort != NULL ) {
		Netlib_CloseHandle( mIncomingBoundPort );
	}

	if ( mMsnFtp != NULL ) {
		delete mMsnFtp;
		mMsnFtp = NULL;
	}

	if ( hWaitEvent != INVALID_HANDLE_VALUE )
		CloseHandle( hWaitEvent );

	if ( mTimerId != 0 ) 
		KillTimer( NULL, mTimerId );

	p2p_clearDormantSessions();

	mir_free( mJoinedContacts );

	if ( hQueueMutex ) WaitForSingleObject( hQueueMutex, INFINITE );
	while (mFirstQueueItem != NULL) {
		TQueueItem* QI = mFirstQueueItem;
		mFirstQueueItem = mFirstQueueItem->next;
		mir_free(QI);
		--numQueueItems;
	}
	if ( hQueueMutex )  {
		ReleaseMutex( hQueueMutex );
		CloseHandle( hQueueMutex );
	}
}

void ThreadData::applyGatewayData( HANDLE hConn, bool isPoll )
{
	char szHttpPostUrl[300];
	getGatewayUrl( szHttpPostUrl, sizeof( szHttpPostUrl ), isPoll );

	MSN_DebugLog( "applying '%s' to %08X [%08X]", szHttpPostUrl, this, GetCurrentThreadId() );

	NETLIBHTTPPROXYINFO nlhpi = {0};
	nlhpi.cbSize = sizeof(nlhpi);
	nlhpi.flags = NLHPIF_HTTP11;
	nlhpi.szHttpGetUrl = NULL;
	nlhpi.szHttpPostUrl = szHttpPostUrl;
	nlhpi.firstPostSequence = 1;
	MSN_CallService( MS_NETLIB_SETHTTPPROXYINFO, (WPARAM)hConn, (LPARAM)&nlhpi);
}

void ThreadData::getGatewayUrl( char* dest, int destlen, bool isPoll )
{
	static const char openFmtStr[] = "http://%s/gateway/gateway.dll?Action=open&Server=%s&IP=%s";
	static const char pollFmtStr[] = "http://%s/gateway/gateway.dll?Action=poll&SessionID=%s";
	static const char cmdFmtStr[]  = "http://%s/gateway/gateway.dll?SessionID=%s";

	if ( mSessionID[0] == 0 ) {
		strcpy( mGatewayIP, MSN_DEFAULT_GATEWAY );

		if ( mType == SERVER_NOTIFICATION || mType == SERVER_DISPATCH )
			mir_snprintf( dest, destlen, openFmtStr, mGatewayIP, "NS", "messenger.hotmail.com" );
		else
			mir_snprintf( dest, destlen, openFmtStr, mGatewayIP, "SB", mServer );
	}
	else
		mir_snprintf( dest, destlen, isPoll ? pollFmtStr : cmdFmtStr, mGatewayIP, mSessionID );

	if ( !MyOptions.UseProxy ) {
		char *slash = strchr(dest+7, '/');
		int len = strlen(dest) - (slash - dest) + 1;
		memmove(dest, slash, len);
	}
}

void ThreadData::processSessionData( const char* str )
{
	char tSessionID[40], tGateIP[ 40 ];

	char* tDelim = ( char* )strchr( str, ';' );
	if ( tDelim == NULL )
		return;

	*tDelim = 0; tDelim += 2;

	if ( !sscanf( str, "SessionID=%s", tSessionID ))
		return;

	char* tDelim2 = strchr( tDelim, ';' );
	if ( tDelim2 != NULL )
		*tDelim2 = '\0';

	if ( !sscanf( tDelim, "GW-IP=%s", tGateIP ))
		return;

//	MSN_DebugLog( "msn_httpGatewayUnwrapRecv printed '%s','%s' to %08X (%08X)", tSessionID, tGateIP, s, this );
	strcpy( mGatewayIP, tGateIP );
	strcpy( mSessionID, tSessionID );
}

/////////////////////////////////////////////////////////////////////////////////////////
// thread start code

static void sttRegisterThread( ThreadData* s )
{
	if ( s == NULL ) return;

	EnterCriticalSection( &sttLock );
	sttThreads.insert( s );
	LeaveCriticalSection( &sttLock );
}

static void sttUnregisterThread( ThreadData* s )
{
	EnterCriticalSection( &sttLock );
	sttThreads.remove( s );
	LeaveCriticalSection( &sttLock );
}

/////////////////////////////////////////////////////////////////////////////////////////

static void __cdecl MSN_ThreadStub( ThreadData* info )
{
	sttRegisterThread( info );
	MSN_DebugLog( "Starting thread %08X (%08X)", GetCurrentThreadId(), info->mFunc );

	__try
	{
		info->mFunc( info );
	}
	__finally
	{
		MSN_DebugLog( "Leaving thread %08X (%08X)", GetCurrentThreadId(), info->mFunc );
		sttUnregisterThread( info );
		delete info;
}	}

void ThreadData::startThread( pThreadFunc parFunc )
{
	mFunc = parFunc;
	mir_forkthread(( pThreadFunc )MSN_ThreadStub, this );
}

/////////////////////////////////////////////////////////////////////////////////////////
// HReadBuffer members

HReadBuffer::HReadBuffer( ThreadData* T, int iStart )
{
	owner = T;
	buffer = ( BYTE* )T->mData;
	totalDataSize = T->mBytesInData;
	startOffset = iStart;
}

HReadBuffer::~HReadBuffer()
{
	owner->mBytesInData = totalDataSize - startOffset;
	if ( owner->mBytesInData != 0 )
		memmove( owner->mData, owner->mData + startOffset, owner->mBytesInData );
}

BYTE* HReadBuffer::surelyRead( int parBytes )
{
	if ( startOffset + parBytes > totalDataSize )
	{
		int tNewLen = totalDataSize - startOffset;
		if ( tNewLen > 0 )
			memmove( buffer, buffer + startOffset, tNewLen );
		else
			tNewLen = 0;

		startOffset = 0;
		totalDataSize = tNewLen;
	}

	int bufferSize = sizeof( owner->mData );
	if ( parBytes > bufferSize - startOffset ) {
		MSN_DebugLog( "HReadBuffer::surelyRead: not enough memory, %d %d %d", parBytes, bufferSize, startOffset );
		return NULL;
	}

	while( totalDataSize - startOffset < parBytes )
	{
		int recvResult = owner->recv(( char* )buffer + totalDataSize, bufferSize - totalDataSize );

		if ( recvResult <= 0 )
			return NULL;

		totalDataSize += recvResult;
	}

	BYTE* result = buffer + startOffset; startOffset += parBytes;
	return result;
}
