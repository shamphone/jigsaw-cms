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

extern int tridUrlInbox, tridUrlEdit;

int MSN_HandleErrors( ThreadData* info, char* cmdString )
{
	int errorCode, packetID = -1;
	sscanf( cmdString, "%d %d", &errorCode, &packetID );

	if ( packetID == msnSearchID )
	{
		MSN_SendBroadcast( NULL, ACKTYPE_SEARCH, ACKRESULT_SUCCESS, (HANDLE)msnSearchID, 0 );
		msnSearchID = -1;
	}

	MSN_DebugLog( "Server error:%s", cmdString );

	switch( errorCode ) {
	case ERR_INTERNAL_SERVER:
		MSN_ShowError( "MSN Services are temporarily unavailable, please try to connect later" );
		MSN_SendBroadcast( NULL, ACKTYPE_LOGIN, ACKRESULT_FAILED, NULL, LOGINERR_NOSERVER );
		return 1;

	case ERR_SERVER_UNAVAILABLE:
		MSN_ShowError( "MSN Services are too busy, please try to connect later" );
		MSN_SendBroadcast( NULL, ACKTYPE_LOGIN, ACKRESULT_FAILED, NULL, LOGINERR_NOSERVER );
		return 1;

	case ERR_NOT_ALLOWED_WHEN_OFFLINE:
		MSN_ShowError( "MSN protocol does not allow you to communicate with others when you are invisible" );
		return 0;

	case ERR_LIST_FULL:
		MSN_ShowError( "MSN plugin cannot add a new contact because the contact list is full" );
		return 0;

	case ERR_ALREADY_THERE:
		MSN_ShowError( "User is already in your contact list" );
		return 0;

	case ERR_NOT_ONLINE:
		MSN_SendBroadcast( info->mInitialContact, ACKTYPE_MESSAGE, ACKRESULT_FAILED, 
			( HANDLE )999999, ( LPARAM )Translate("User not online"));
		return 1;

	case ERR_NOT_EXPECTED:
		MSN_ShowError( "Your MSN account e-mail is unverified. Goto http://www.passport.com and verify the primary e-mail first" );
		return 0;

	case ERR_AUTHENTICATION_FAILED:
		if ( info->mType != SERVER_SWITCHBOARD ) {
			MSN_ShowError( "Your username or password is incorrect" );
			MSN_SendBroadcast( NULL, ACKTYPE_LOGIN, ACKRESULT_FAILED, NULL, LOGINERR_WRONGPASSWORD );
		}
		return 1;

	case ERR_INVALID_LOCALE:
		if ( packetID == tridUrlInbox ) {
			tridUrlInbox = -1;
			return 0;
		}

		if ( packetID == tridUrlEdit ) {
			tridUrlEdit  = msnNsThread->sendPacket( "URL", "PROFILE 0x0409" );
			return 0;
		}
		// fall through

	default:
		MSN_DebugLog( "Unprocessed error: %s", cmdString );
		if ( errorCode >= 500 ) //all these errors look fatal-ish
			MSN_ShowError( "Unrecognised error %d. The server has closed our connection", errorCode );

		break;
	}
	return 0;
}
