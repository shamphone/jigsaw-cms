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

//=======================================================================================
// Fake function - it does nothing but confirms successful session initialization
//=======================================================================================

int msn_httpGatewayInit(HANDLE hConn, NETLIBOPENCONNECTION* nloc, NETLIBHTTPREQUEST* nlhr)
{
	NETLIBHTTPPROXYINFO nlhpi = {0};
	nlhpi.cbSize = sizeof(nlhpi);
	nlhpi.szHttpGetUrl = NULL;
	nlhpi.szHttpPostUrl = "messenger.hotmail.com";
	nlhpi.flags = NLHPIF_HTTP11;
	return MSN_CallService(MS_NETLIB_SETHTTPPROXYINFO, (WPARAM)hConn, (LPARAM)&nlhpi);
}

//=======================================================================================
// Prepares the szHttpPostUrl. If it's the very first send (mSessionID is void), this
// function generates the initial URL depending on a thread type
//=======================================================================================

int msn_httpGatewayWrapSend(HANDLE hConn, PBYTE buf, int len, int flags, MIRANDASERVICE pfnNetlibSend)
{
	ThreadData* T = MSN_GetThreadByConnection( hConn );
	if ( T != NULL )
		T->applyGatewayData( hConn, len == 0 );

	NETLIBBUFFER tBuf = { ( char* )buf, len, flags };
	return pfnNetlibSend(( LPARAM )hConn, WPARAM( &tBuf ));
}

//=======================================================================================
// Processes the results of the command execution. Parses HTTP headers to get the next
// SessionID & gateway IP values
//=======================================================================================

PBYTE msn_httpGatewayUnwrapRecv( NETLIBHTTPREQUEST* nlhr, PBYTE buf, int len, int *outBufLen, void *(*NetlibRealloc)(void *, size_t))
{
	*outBufLen = len;

	ThreadData* T = MSN_GetThreadByConnection( nlhr->nlc );
	if ( T == NULL )
		return buf;

	bool tIsSessionClosed = false;

	for ( int i=0; i < nlhr->headersCount; i++ )
	{
		NETLIBHTTPHEADER& tHeader = nlhr->headers[ i ];
		if ( stricmp( tHeader.szName, "X-MSN-Messenger" ) != 0 )
			continue;

		if ( strstr( tHeader.szValue, "Session=close" ) != 0 )
		{	tIsSessionClosed = true;
			break;
		}

		T->processSessionData( tHeader.szValue );
		T->applyGatewayData( nlhr->nlc, false );
	}

	if ( tIsSessionClosed || nlhr->resultCode != 200)
	{	*outBufLen = 0;
		buf = ( PBYTE )mir_alloc( 1 );
		*buf = 0;
	}
	else if ( buf == NULL && len == 0 )
	{	*outBufLen = 1;
		buf = ( PBYTE )mir_alloc( 1 );
		*buf = 0;
	}

	return buf;
}
