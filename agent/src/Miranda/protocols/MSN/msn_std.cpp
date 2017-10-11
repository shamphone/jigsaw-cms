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

extern HANDLE msnBlockMenuItem;

HANDLE __stdcall MSN_CreateProtoServiceFunction(
	const char* szService,
	MIRANDASERVICE serviceProc )
{
	char str[ MAXMODULELABELLENGTH ];
	strcpy( str, msnProtocolName );
	strcat( str, szService );
	return CreateServiceFunction( str, serviceProc );
}

#if !defined( _DEBUG )
int __stdcall MSN_CallService( const char* szSvcName, WPARAM wParam, LPARAM lParam )
{
	return CallService( szSvcName, wParam, lParam );
}
#endif

void __stdcall MSN_EnableMenuItems( BOOL parEnable )
{
	CLISTMENUITEM clmi;
	memset( &clmi, 0, sizeof( clmi ));
	clmi.cbSize = sizeof( clmi );
	clmi.flags = CMIM_FLAGS;
	if ( !parEnable )
		clmi.flags |= CMIF_GRAYED;

	for ( int i=0; i < MENU_ITEMS_COUNT; i++ )
	{
		if ( msnMenuItems[i] == NULL )
			continue;

		MSN_CallService( MS_CLIST_MODIFYMENUITEM, ( WPARAM )msnMenuItems[i], ( LPARAM )&clmi );
	}

	MSN_CallService( MS_CLIST_MODIFYMENUITEM, ( WPARAM )msnBlockMenuItem, ( LPARAM )&clmi );
}

DWORD __stdcall MSN_GetByte( const char* valueName, int parDefltValue )
{
	return DBGetContactSettingByte( NULL, msnProtocolName, valueName, parDefltValue );
}

char* __stdcall MSN_GetContactName( HANDLE hContact )
{
	return ( char* )MSN_CallService( MS_CLIST_GETCONTACTDISPLAYNAME, WPARAM( hContact), 0 );
}

TCHAR* __stdcall MSN_GetContactNameT( HANDLE hContact )
{
	return ( TCHAR* )MSN_CallService( MS_CLIST_GETCONTACTDISPLAYNAME, WPARAM( hContact), GCDNF_TCHAR );
}

DWORD __stdcall MSN_GetDword( HANDLE hContact, const char* valueName, DWORD parDefltValue )
{
	return DBGetContactSettingDword( hContact, msnProtocolName, valueName, parDefltValue );
}

int __stdcall MSN_GetStaticString( const char* valueName, HANDLE hContact, char* dest, int dest_len )
{
	DBVARIANT dbv;
	dbv.pszVal = dest;
	dbv.cchVal = dest_len;
	dbv.type = DBVT_ASCIIZ;

	DBCONTACTGETSETTING sVal;
	sVal.pValue = &dbv;
	sVal.szModule = msnProtocolName;
	sVal.szSetting = valueName;
	if ( MSN_CallService( MS_DB_CONTACT_GETSETTINGSTATIC, ( WPARAM )hContact, ( LPARAM )&sVal ) != 0 )
		return 1;

	return ( dbv.type != DBVT_ASCIIZ );
}

int __stdcall MSN_GetStringT( const char* valueName, HANDLE hContact, DBVARIANT* dbv )
{
	return DBGetContactSettingTString( hContact, msnProtocolName, valueName, dbv );
}

WORD __stdcall MSN_GetWord( HANDLE hContact, const char* valueName, int parDefltValue )
{
	return DBGetContactSettingWord( hContact, msnProtocolName, valueName, parDefltValue );
}

void __fastcall MSN_FreeVariant( DBVARIANT* dbv )
{
	DBFreeVariant( dbv );
}

int __stdcall MSN_SendBroadcast( HANDLE hContact, int type, int result, HANDLE hProcess, LPARAM lParam )
{
	ACKDATA ack = {0};
	ack.cbSize = sizeof( ACKDATA );
	ack.szModule = msnProtocolName;
	ack.hContact = hContact;
	ack.type = type;
	ack.result = result;
	ack.hProcess = hProcess;
	ack.lParam = lParam;
	return MSN_CallService( MS_PROTO_BROADCASTACK, 0, ( LPARAM )&ack );
}

DWORD __stdcall MSN_SetByte( const char* valueName, int parValue )
{
	return DBWriteContactSettingByte( NULL, msnProtocolName, valueName, parValue );
}

DWORD __stdcall MSN_SetDword( HANDLE hContact, const char* valueName, DWORD parValue )
{
	return DBWriteContactSettingDword( hContact, msnProtocolName, valueName, parValue );
}

DWORD __stdcall MSN_SetString( HANDLE hContact, const char* valueName, const char* parValue )
{
	return DBWriteContactSettingString( hContact, msnProtocolName, valueName, parValue );
}

DWORD __stdcall MSN_SetStringT( HANDLE hContact, const char* valueName, const TCHAR* parValue )
{
	return DBWriteContactSettingTString( hContact, msnProtocolName, valueName, parValue );
}

DWORD __stdcall MSN_SetStringUtf( HANDLE hContact, const char* valueName, char* parValue )
{
	return DBWriteContactSettingStringUtf( hContact, msnProtocolName, valueName, parValue );
}

DWORD __stdcall MSN_SetWord( HANDLE hContact, const char* valueName, int parValue )
{
	return DBWriteContactSettingWord( hContact, msnProtocolName, valueName, parValue );
}

char* __stdcall MSN_Translate( const char* str )
{
	return Translate( str );
}
