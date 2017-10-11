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

void replaceStr( char*& dest, const char* src )
{
	if ( src != NULL ) {
		if ( dest != NULL )
			mir_free( dest );
		dest = mir_strdup( src );
}	}

void overrideStr( TCHAR*& dest, const TCHAR* src, BOOL unicode, const TCHAR* def )
{
	if ( dest != NULL ) 
	{
		mir_free( dest );
		dest = NULL;
	}

	if ( src != NULL )
		dest = a2tf( src, unicode );
	else if ( def != NULL )
		dest = mir_tstrdup( def );
}

char* rtrim( char *string )
{
   char* p = string + strlen( string ) - 1;

   while ( p >= string )
   {  if ( *p != ' ' && *p != '\t' && *p != '\n' && *p != '\r' )
         break;

		*p-- = 0;
   }
   return string;
}

#if defined( _UNICODE )
TCHAR* rtrim( TCHAR* string )
{
   TCHAR* p = string + lstrlen( string ) - 1;

   while ( p >= string )
   {  if ( *p != ' ' && *p != '\t' && *p != '\n' && *p != '\r' )
         break;

		*p-- = 0;
   }
   return string;
}
#endif

void strdel( char* parBuffer, int len )
{
	char *p;
	for ( p = parBuffer+len; *p != 0; p++ )
		p[ -len ] = *p;

	p[ -len ] = '\0';
}

TCHAR* a2t( const char* str )
{
	if ( str == NULL )
		return NULL;

	#if defined( _UNICODE )
		return (TCHAR*)CallService( MS_LANGPACK_PCHARTOTCHAR, 0, (LPARAM)str);
	#else
		return mir_strdup( str );
	#endif
}

TCHAR* a2tf( const TCHAR* str, BOOL unicode )
{
	if ( str == NULL )
		return NULL;

	#if defined( _UNICODE )
		if ( unicode )
			return mir_tstrdup( str );
		else {
			int codepage = CallService( MS_LANGPACK_GETCODEPAGE, 0, 0 );

			int cbLen = MultiByteToWideChar( codepage, 0, (char*)str, -1, 0, 0 );
			TCHAR* result = ( TCHAR* )mir_alloc( sizeof(TCHAR)*( cbLen+1 ));
			if ( result == NULL )
				return NULL;

			MultiByteToWideChar( codepage, 0, (char*)str, -1, result, cbLen );
			result[ cbLen ] = 0;
			return result;
		}
	#else
		return mir_strdup( str );
	#endif
}

