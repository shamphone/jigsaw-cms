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

HANDLE __stdcall MSN_HContactFromEmail( const char* msnEmail, const char* msnNick, int addIfNeeded, int temporary )
{
	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
	while ( hContact != NULL )
	{
		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO,( WPARAM )hContact, 0 );
		if ( szProto != NULL && !strcmp( msnProtocolName, szProto )) {
			char tEmail[ MSN_MAX_EMAIL_LEN ];
			if ( !MSN_GetStaticString( "e-mail", hContact, tEmail, sizeof( tEmail )))
				if ( !strcmpi( msnEmail, tEmail ))
					return hContact;
		}

		hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 );
	}

	if ( addIfNeeded )
	{
		hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_ADD, 0, 0 );
		MSN_CallService( MS_PROTO_ADDTOCONTACT, ( WPARAM )hContact, ( LPARAM )msnProtocolName );
		MSN_SetString( hContact, "e-mail", msnEmail );
		MSN_SetStringUtf( hContact, "Nick", ( char* )msnNick );
		if ( temporary )
			DBWriteContactSettingByte( hContact, "CList", "NotOnList", 1 );

		return hContact;
	}

	return NULL;
}

HANDLE __stdcall MSN_HContactFromEmailT( const TCHAR* msnEmail )
{
	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
	while ( hContact != NULL )
	{
		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO,( WPARAM )hContact, 0 );
		if ( szProto != NULL && !strcmp( msnProtocolName, szProto )) {
			DBVARIANT dbv;
			if ( !MSN_GetStringT( "e-mail", hContact, &dbv ))
				if ( !lstrcmpi( msnEmail, dbv.ptszVal ))
					return hContact;
		}

		hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 );
	}

	return NULL;
}

HANDLE __stdcall MSN_HContactById( const char* szGuid )
{
	HANDLE hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDFIRST, 0, 0 );
	while ( hContact != NULL )
	{
		char* szProto = ( char* )MSN_CallService( MS_PROTO_GETCONTACTBASEPROTO,( WPARAM )hContact, 0 );
		if ( szProto != NULL && !strcmp( msnProtocolName, szProto )) {
			char tId[ 100 ];
			if ( !MSN_GetStaticString( "ID", hContact, tId, sizeof tId ))
				if ( !strcmpi( szGuid, tId ))
					return hContact;
		}

		hContact = ( HANDLE )MSN_CallService( MS_DB_CONTACT_FINDNEXT, ( WPARAM )hContact, 0 );
	}

	return NULL;
}
