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

int __stdcall MSN_ContactJoined( ThreadData* parInfo, HANDLE hContact )
{
	for ( int i=0; i < parInfo->mJoinedCount; i++ )
		if ( parInfo->mJoinedContacts[i] == hContact )
			return i+1;

	int ret = ++parInfo->mJoinedCount;
	parInfo->mJoinedContacts = ( HANDLE* )mir_realloc( parInfo->mJoinedContacts, sizeof( HANDLE )*ret );
	parInfo->mJoinedContacts[ ret-1 ] = hContact;
	return ret;
}

int __stdcall MSN_ContactLeft( ThreadData* parInfo, HANDLE hContact )
{
	int i;

	for ( i=0; i < parInfo->mJoinedCount; i++ )
		if ( parInfo->mJoinedContacts[ i ] == hContact )
			break;

	if ( i == parInfo->mJoinedCount )
		return i;

	int ret = --parInfo->mJoinedCount;
	memmove( parInfo->mJoinedContacts + i, parInfo->mJoinedContacts+i+1, sizeof( HANDLE )*( ret-i ));
	parInfo->mJoinedContacts = ( HANDLE* )mir_realloc( parInfo->mJoinedContacts, sizeof( HANDLE )*ret );
	return ret;
}
