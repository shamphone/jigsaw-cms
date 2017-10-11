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

#include <io.h>

int MSN_HandleErrors(ThreadData *info,char *cmdString);

void msnftp_sendAcceptReject( filetransfer *ft, bool acc )
{
	ThreadData* thread = MSN_GetThreadByContact( ft->std.hContact );
	if ( thread == NULL ) return;

	if ( acc )
	{
		thread->sendPacket( "MSG",
			"U %d\r\nMIME-Version: 1.0\r\n"
			"Content-Type: text/x-msmsgsinvite; charset=UTF-8\r\n\r\n"
			"Invitation-Command: ACCEPT\r\n"
			"Invitation-Cookie: %s\r\n"
			"Launch-Application: FALSE\r\n"
			"Request-Data: IP-Address:\r\n\r\n",
			172+4+strlen( ft->szInvcookie ), ft->szInvcookie );
	}
	else
	{
		thread->sendPacket( "MSG",
			"U %d\r\nMIME-Version: 1.0\r\n"
			"Content-Type: text/x-msmsgsinvite; charset=UTF-8\r\n\r\n"
			"Invitation-Command: CANCEL\r\n"
			"Invitation-Cookie: %s\r\n"
			"Cancel-Code: REJECT\r\n\r\n",
			172-33+4+strlen( ft->szInvcookie ), ft->szInvcookie );
	}
}


/////////////////////////////////////////////////////////////////////////////////////////
//	MSN File Transfer Protocol commands processing

int MSN_HandleMSNFTP( ThreadData *info, char *cmdString )
{
	char* params = "";
	filetransfer* ft = info->mMsnFtp;

	if ( cmdString[ 3 ] )
		params = cmdString+4;

	switch((*(PDWORD)cmdString&0x00FFFFFF)|0x20000000)
	{
		case ' EYB':    //********* BYE
		{
			ft->complete();
			return 1;
		}
		case ' LIF':    //********* FIL
		{
			char filesize[ 30 ];
			if ( sscanf( params, "%s", filesize ) < 1 )
				goto LBL_InvalidCommand;

			info->mCaller = 1;
			info->send( "TFR\r\n", 5 );
			break;
		}
		case ' RFT':    //********* TFR
		{
			char* sendpacket = ( char* )alloca( 2048 );
			filetransfer* ft = info->mMsnFtp;

			info->mCaller = 3;

			while( ft->std.currentFileProgress < ft->std.currentFileSize )
			{
				if ( ft->bCanceled ) {
					sendpacket[0] = 0x01;
					sendpacket[1] = 0x00;
					sendpacket[2] = 0x00;
					info->send( sendpacket, 3 );
					return 0;
				}

				int wPlace = 0;
				sendpacket[ wPlace++ ] = 0x00;
				int packetLen = ft->std.currentFileSize - ft->std.currentFileProgress;
				if ( packetLen > 2045 )
					packetLen = 2045;

				sendpacket[ wPlace++ ] = packetLen & 0x00ff;
				sendpacket[ wPlace++ ] = ( packetLen & 0xff00 ) >> 8;
				_read( ft->fileId, &sendpacket[wPlace], packetLen );

				info->send( &sendpacket[0], packetLen+3 );

				ft->std.totalProgress += packetLen;
				ft->std.currentFileProgress += packetLen;

				MSN_SendBroadcast( ft->std.hContact, ACKTYPE_FILE, ACKRESULT_DATA, ft, ( LPARAM )&ft->std );
			}

			ft->complete();
			break;
		}
		case ' RSU':    //********* USR
		{
			char email[130],authcookie[14];
			if ( sscanf(params,"%129s %13s",email,authcookie) < 2 )
			{
				MSN_DebugLog( "Invalid USR OK command, ignoring" );
				break;
			}

			char tCommand[ 30 ];
			mir_snprintf( tCommand, sizeof( tCommand ), "FIL %i\r\n", info->mMsnFtp->std.totalBytes );
			info->send( tCommand, strlen( tCommand ));
			break;
		}
		case ' REV':    //********* VER
		{
			char protocol1[ 7 ];
			if ( sscanf( params, "%6s", protocol1 ) < 1 )
			{
LBL_InvalidCommand:
				MSN_DebugLog( "Invalid %.3s command, ignoring", cmdString );
				break;
			}

			if ( strcmp( protocol1, "MSNFTP" ) != 0 )
			{
				int tempInt;
				int tFieldCount = sscanf( params, "%d %6s", &tempInt, protocol1 );
				if ( tFieldCount != 2 || strcmp( protocol1, "MSNFTP" ) != 0 )
				{
					MSN_DebugLog( "Another side requested the unknown protocol (%s), closing thread", params );
					return 1;
			}	}

			if ( info->mCaller == 0 )  //receive
			{
				char tCommand[ MSN_MAX_EMAIL_LEN + 50 ];
				mir_snprintf( tCommand, sizeof( tCommand ), "USR %s %s\r\n", MyOptions.szEmail, info->mCookie );
				info->send( tCommand, strlen( tCommand ));
			}
			else if ( info->mCaller == 2 )  //send
			{
				static char sttCommand[] = "VER MSNFTP\r\n";
				info->send( sttCommand, strlen( sttCommand ));
			}
			break;
		}
		default:		// receiving file
		{
			HReadBuffer tBuf( info, int( cmdString - info->mData ));

			while ( true )
			{
				if ( ft->bCanceled )
				{	info->send( "CCL\r\n", 5 );
					ft->close();
					return 1;
				}

				BYTE* p = tBuf.surelyRead( 3 );
				if ( p == NULL ) {
LBL_Error:		ft->close();
					MSN_ShowError( "file transfer is canceled by remote host" );
					return 1;
				}

				BYTE tIsTransitionFinished = *p++;
				WORD dataLen = *p++;
				dataLen |= (*p++ << 8);

				if ( tIsTransitionFinished ) {
LBL_Success:
					static char sttCommand[] = "BYE 16777989\r\n";
					info->send( sttCommand, strlen( sttCommand ));
					return 1;
				}

				p = tBuf.surelyRead( dataLen );
				if ( p == NULL )
					goto LBL_Error;

				_write( ft->fileId, p, dataLen );
				ft->std.totalProgress += dataLen;
				ft->std.currentFileProgress += dataLen;

				MSN_SendBroadcast( ft->std.hContact, ACKTYPE_FILE, ACKRESULT_DATA, ft, ( LPARAM )&ft->std );

				if ( ft->std.currentFileProgress == ft->std.totalBytes ) {
					ft->complete();
					goto LBL_Success;
	}	}	}	}

	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
//	ft_startFileSend - sends a file using the old f/t protocol

static void __cdecl sttSendFileThread( ThreadData* info )
{
	MSN_DebugLog( "Waiting for an incoming connection to '%s'...", info->mServer );

	filetransfer* ft = info->mMsnFtp;

	switch( WaitForSingleObject( info->hWaitEvent, 60000 )) {
	case WAIT_TIMEOUT:
	case WAIT_FAILED:
		MSN_DebugLog( "Incoming connection timed out, closing file transfer" );
		return;
	}

	info->mBytesInData = 0;

	while ( TRUE ) {
		int recvResult = info->recv( info->mData+info->mBytesInData, 1000 - info->mBytesInData );
		if ( recvResult == SOCKET_ERROR || !recvResult )
			break;

		info->mBytesInData += recvResult;

		//pull off each line for parsing
		if ( info->mCaller == 3 && info->mType == SERVER_FILETRANS ) {
			if ( MSN_HandleMSNFTP( info, info->mData ))
				break;
		}
		else {  // info->mType!=SERVER_FILETRANS
			while ( TRUE ) {
				char* peol = strchr(info->mData,'\r');
				if ( peol == NULL )
					break;

				if ( info->mBytesInData < peol - info->mData + 2 )
					break;  //wait for full line end

				char msg[ sizeof(info->mData) ];
				memcpy( msg, info->mData, peol - info->mData ); msg[ peol - info->mData ] = 0;
				if ( *++peol != '\n' )
					MSN_DebugLog( "Dodgy line ending to command: ignoring" );
				else
					peol++;

				info->mBytesInData -= peol - info->mData;
				memmove( info->mData, peol, info->mBytesInData );

				MSN_DebugLog( "RECV:%s", msg );

				if ( !isalnum(msg[0]) || !isalnum(msg[1]) || !isalnum(msg[2]) || (msg[3] && msg[3]!=' ')) {
					MSN_DebugLog( "Invalid command name");
					continue;
				}

				if ( MSN_HandleMSNFTP( info, msg ))
					break;
		}	}

		if ( info->mBytesInData == sizeof( info->mData )) {
			MSN_DebugLog( "sizeof(data) is too small: the longest line won't fit" );
			break;
	}	}

	MSN_DebugLog( "Closing file transfer thread" );
}

void ft_startFileSend( ThreadData* info, const char* Invcommand, const char* Invcookie )
{
	if ( strcmpi( Invcommand,"ACCEPT" ))
		return;

	bool bHasError = false;
	NETLIBBIND nlb = {0};
	char ipaddr[256];
	HANDLE sb;

	filetransfer* ft = info->mMsnFtp; info->mMsnFtp = NULL;
	if ( ft != NULL ) {
		if ( MSN_GetMyHostAsString( ipaddr, sizeof ipaddr ))
			bHasError = true;
		else {
			nlb.cbSize = sizeof( nlb );
			nlb.pfnNewConnectionV2 = MSN_ConnectionProc;
			nlb.wPort = 0;	// Use user-specified incoming port ranges, if available
			sb = ( HANDLE )MSN_CallService( MS_NETLIB_BINDPORT, ( WPARAM )hNetlibUser, ( LPARAM )&nlb);
			if ( sb == NULL ) {
				MSN_DebugLog( "Unable to bind the port for incoming transfers" );
				bHasError = true;
	}	}	}
	else bHasError = true;

	char command[ 1024 ];
	int  nBytes = mir_snprintf( command, sizeof( command ),
		"MIME-Version: 1.0\r\n"
		"Content-Type: text/x-msmsgsinvite; charset=UTF-8\r\n\r\n"
		"Invitation-Command: %s\r\n"
		"Invitation-Cookie: %s\r\n"
		"IP-Address: %s\r\n"
		"Port: %i\r\n"
		"AuthCookie: %i\r\n"
		"Launch-Application: FALSE\r\n"
		"Request-Data: IP-Address:\r\n\r\n",
		( bHasError ) ? "CANCEL" : "ACCEPT",
		Invcookie, ipaddr, nlb.wExPort, WORD((( double )rand() / ( double )RAND_MAX ) * 4294967295 ));
	info->sendPacket( "MSG", "N %d\r\n%s", nBytes, command );

	if ( bHasError ) {
		delete ft;
		return;
	}
	
	ThreadData* newThread = new ThreadData;
	newThread->mType = SERVER_FILETRANS;
	newThread->mCaller = 2;
	newThread->mMsnFtp = ft;
	newThread->mIncomingBoundPort = sb;
	newThread->mIncomingPort = nlb.wPort;
	newThread->startThread(( pThreadFunc )sttSendFileThread );
}
