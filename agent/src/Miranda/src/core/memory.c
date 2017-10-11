/*

Miranda IM: the free IM client for Microsoft* Windows*

Copyright 2000-2007 Miranda ICQ/IM project, 
all portions of this codebase are copyrighted to the people 
listed in contributors.txt.

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

#include "commonheaders.h"

#define BLOCK_ALLOCED 0xABBABABA
#define BLOCK_FREED   0xDEADBEEF

static int CheckBlock( void* blk )
{
	int result = FALSE;
	char* p = ( char* )blk - sizeof(DWORD)*2;
	DWORD size, *b, *e;

	__try
	{
		size = *( DWORD* )p;
		b = ( DWORD* )&p[ sizeof( DWORD ) ];
		e = ( DWORD* )&p[ sizeof( DWORD )*2 + size ];

		if ( *b != BLOCK_ALLOCED || *e != BLOCK_ALLOCED )
		{
			if ( *b == BLOCK_FREED && *e == BLOCK_FREED )
				OutputDebugStringA( "memory block is already deleted\n" );
			else
				OutputDebugStringA( "memory block is corrupted\n" );
			#if defined( _DEBUG )
				DebugBreak();
			#endif
		}
 		else result = TRUE;
	}
	__except( EXCEPTION_EXECUTE_HANDLER )
	{
		OutputDebugStringA( "access violation during checking memory block\n" );
		#if defined( _DEBUG )
			DebugBreak();
		#endif
	}

	return result;
}

/******************************************************************************/

void* mir_alloc( size_t size )
{
	if ( size == 0 )
		return NULL;
	{
		char* p = (char*)malloc( size + sizeof(DWORD)*3 );
		if ( p == NULL ) {
			OutputDebugStringA( "memory overflow\n" );
			#if defined( _DEBUG )
				DebugBreak();
			#endif
			return NULL;
		}

		*( DWORD* )p = ( DWORD )size;
		*( DWORD* )&p[ sizeof(DWORD) ] = BLOCK_ALLOCED;
		*( DWORD* )&p[ size + sizeof(DWORD)*2 ] = BLOCK_ALLOCED;
		return p + sizeof( DWORD )*2;
}	}

/******************************************************************************/

void* mir_calloc( size_t size )
{
	void* p = mir_alloc( size );
	if ( p != NULL )
		memset( p, 0, size );
	return p;
}

/******************************************************************************/

void* mir_realloc( void* ptr, size_t size )
{
	char* p;

	if ( ptr != NULL ) {
		if ( !CheckBlock( ptr ))
			return NULL;
		p = ( char* )ptr - sizeof(DWORD)*2;
	}
	else p = NULL;

	p = ( char* )realloc( p, size + sizeof(DWORD)*3 );
	if ( p == NULL ) {
		OutputDebugStringA( "memory overflow\n" );
		#if defined( _DEBUG )
			DebugBreak();
		#endif
		return NULL;
	}

	*( DWORD* )p = ( DWORD )size;
	*( DWORD* )&p[ sizeof(DWORD) ] = BLOCK_ALLOCED;
	*( DWORD* )&p[ size + sizeof(DWORD)*2 ] = BLOCK_ALLOCED;
	return p + sizeof( DWORD )*2;
}

/******************************************************************************/

void mir_free( void* ptr )
{
	char* p;
	DWORD size;

	if ( ptr == NULL )
		return;
	if ( !CheckBlock( ptr ))
		return;

	p = ( char* )ptr - sizeof(DWORD)*2;
	size = *( DWORD* )p;
	*( DWORD* )&p[ sizeof(DWORD) ] = BLOCK_FREED;
	*( DWORD* )&p[ size + sizeof(DWORD)*2 ] = BLOCK_FREED;
	free( p );
}

/******************************************************************************/

char* mir_strdup( const char* str )
{
	if ( str != NULL ) {
		char* p = mir_alloc( strlen( str )+1 );
		if ( p )
			strcpy( p, str );
		return p;
	}
	return NULL;
}

/******************************************************************************/

WCHAR* mir_wstrdup( const WCHAR* str )
{
	if ( str != NULL ) {
		WCHAR* p = mir_alloc( sizeof( WCHAR )*( wcslen( str )+1 ));
		if ( p )
			wcscpy( p, str );
		return p;
	}
	return NULL;
}
