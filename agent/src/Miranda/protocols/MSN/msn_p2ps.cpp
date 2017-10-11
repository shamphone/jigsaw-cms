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

static CRITICAL_SECTION sessionLock;

static int CompareFT( const filetransfer* p1, const filetransfer* p2 )
{
	return int( p1 - p2 );
}


static int CompareDC( const directconnection* p1, const directconnection* p2 )
{
	return int( p1 - p2 );
}

static LIST<filetransfer> sessionList( 10, CompareFT );
static LIST<directconnection> dcList( 10, CompareDC );


/////////////////////////////////////////////////////////////////////////////////////////
// add file session to a list

void __stdcall p2p_registerSession( filetransfer* ft )
{
	EnterCriticalSection( &sessionLock );
	sessionList.insert( ft );
	LeaveCriticalSection( &sessionLock );
}

/////////////////////////////////////////////////////////////////////////////////////////
// remove file session from a list

void __stdcall p2p_unregisterSession( filetransfer* ft )
{
	EnterCriticalSection( &sessionLock );
	sessionList.remove( ft );
	delete ft; 
	LeaveCriticalSection( &sessionLock );
}

/////////////////////////////////////////////////////////////////////////////////////////
// get session by some parameter

filetransfer* __stdcall p2p_getSessionByID( unsigned id )
{
	if ( id == 0 )
		return NULL;

	filetransfer* ft = NULL;
	EnterCriticalSection( &sessionLock );

	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( FT->p2p_sessionid == id ) {
			ft = FT;
			break;
	}	}

	LeaveCriticalSection( &sessionLock );
	if ( ft == NULL )
		MSN_DebugLog( "Ignoring unknown session id %lu", id );

	return ft;
}

filetransfer* __stdcall p2p_getSessionByMsgID( unsigned id )
{
	if ( id == 0 )
		return NULL;

	filetransfer* ft = NULL;
	EnterCriticalSection( &sessionLock );

	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( FT->p2p_msgid == id || FT->p2p_msgid == (id + 1) ) {
			ft = FT;
			break;
	}	}

	LeaveCriticalSection( &sessionLock );
	if ( ft == NULL )
		MSN_DebugLog( "Ignoring unknown message id %lu", id );

	return ft;
}

BOOL __stdcall p2p_sessionRegistered( filetransfer* ft )
{
    BOOL result = FALSE;
	EnterCriticalSection( &sessionLock );

	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( sessionList[i] == ft ) {
			result = TRUE;
			break;
	}	}

	LeaveCriticalSection( &sessionLock );
	return result;
}

filetransfer* __stdcall p2p_getThreadSession( HANDLE hContact, TInfoType mType )
{
	EnterCriticalSection( &sessionLock );

	filetransfer* result = NULL;
	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( FT->std.hContact == hContact && FT->tType == mType ) {
			result = FT;
			break;
	}	}

	LeaveCriticalSection( &sessionLock );
	return result;
}

void __stdcall p2p_clearDormantSessions( void )
{
	EnterCriticalSection( &sessionLock );

	time_t ts = time( NULL );
	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( FT->p2p_waitack && ( ts - FT->ts ) > 10 ) 
		{
			LeaveCriticalSection( &sessionLock );
			p2p_unregisterSession( FT );
			EnterCriticalSection( &sessionLock );
			i = 0;
	}	}

	for ( int j=0; j < dcList.getCount(); j++ ) {
		directconnection* DC = dcList[j];
		if (( ts - DC->ts ) > 120 ) {
			LeaveCriticalSection( &sessionLock );
			p2p_unregisterDC( DC );
			EnterCriticalSection( &sessionLock );
			j = 0;
	}	}

	LeaveCriticalSection( &sessionLock );
}

void __stdcall p2p_redirectSessions( HANDLE hContact )
{
	EnterCriticalSection( &sessionLock );

	ThreadData* T = MSN_GetP2PThreadByContact( hContact );
	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( FT->std.hContact == hContact && !FT->std.sending && 
			( T == NULL || ( FT->tType != T->mType && FT->tType != 0 ))) 
			p2p_sendRedirect( T, FT );
	}

	LeaveCriticalSection( &sessionLock );
}

void __stdcall p2p_cancelAllSessions( void )
{
	EnterCriticalSection( &sessionLock );

	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		p2p_sendCancel( MSN_GetP2PThreadByContact( FT->std.hContact ), FT );
	}

	for ( int j=0; j < dcList.getCount(); j++ ) 
		delete dcList[j];

	dcList.destroy();

	LeaveCriticalSection( &sessionLock );
}

filetransfer* __stdcall p2p_getSessionByCallID( const char* CallID )
{
	if ( CallID == NULL )
		return NULL;

	EnterCriticalSection( &sessionLock );

	filetransfer* ft = NULL;
	for ( int i=0; i < sessionList.getCount(); i++ ) {
		filetransfer* FT = sessionList[i];
		if ( FT->p2p_callID != NULL && !strcmp( FT->p2p_callID, CallID )) {
			ft = FT;
			break;
	}	}

	LeaveCriticalSection( &sessionLock );
	if ( ft == NULL )
		MSN_DebugLog( "Ignoring unknown session call id %s", CallID );

	return ft;
}


void __stdcall p2p_registerDC( directconnection* dc )
{
	EnterCriticalSection( &sessionLock );
	dcList.insert( dc );
	LeaveCriticalSection( &sessionLock );
}

void __stdcall p2p_unregisterDC( directconnection* dc )
{
	EnterCriticalSection( &sessionLock );
	delete dc; 
	dcList.remove( dc );
	LeaveCriticalSection( &sessionLock );
}

directconnection* __stdcall p2p_getDCByCallID( const char* CallID )
{
	if ( CallID == NULL )
		return NULL;

	EnterCriticalSection( &sessionLock );

	directconnection* dc = NULL;
	for ( int i=0; i < dcList.getCount(); i++ ) {
		directconnection* DC = dcList[i];
		if ( DC->callId != NULL && !strcmp( DC->callId, CallID )) {
			dc = DC;
			break;
	}	}

	LeaveCriticalSection( &sessionLock );

	return dc;
}

/////////////////////////////////////////////////////////////////////////////////////////
// external functions

void P2pSessions_Init()
{
	InitializeCriticalSection( &sessionLock );
}

void P2pSessions_Uninit()
{
	EnterCriticalSection( &sessionLock );

	for ( int i=0; i < sessionList.getCount(); i++ ) 
		delete sessionList[i];

	sessionList.destroy();

	for ( int j=0; j < dcList.getCount(); j++ ) 
		delete dcList[j];

	dcList.destroy();

	LeaveCriticalSection( &sessionLock );
	DeleteCriticalSection( &sessionLock );
}
