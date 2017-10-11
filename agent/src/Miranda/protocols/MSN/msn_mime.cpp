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

/////////////////////////////////////////////////////////////////////////////////////////
// constructors and destructor

MimeHeaders::MimeHeaders() :
	mCount( 0 ),
	mVals( NULL )
{
}

MimeHeaders::MimeHeaders( int iInitCount ) :
	mCount( 0 )
{
	mVals = ( MimeHeader* )mir_alloc( iInitCount * sizeof( MimeHeader ));
}

MimeHeaders::~MimeHeaders()
{
	if ( mCount == NULL )
		return;

	for ( int i=0; i < mCount; i++ ) {
		MimeHeader& H = mVals[ i ];
		mir_free( H.name );
		mir_free( H.value );
	}

	mir_free( mVals );
}

/////////////////////////////////////////////////////////////////////////////////////////
// add various values

void MimeHeaders::addString( const char* name, const char* szValue )
{
	MimeHeader& H = mVals[ mCount++ ];
	H.name = mir_strdup( name );
	H.value = mir_strdup( szValue );
}

void MimeHeaders::addLong( const char* name, long lValue )
{
	MimeHeader& H = mVals[ mCount++ ];
	H.name = mir_strdup( name );

	char szBuffer[ 20 ];
	ltoa( lValue, szBuffer, 10 );
	H.value = mir_strdup( szBuffer );
}

/////////////////////////////////////////////////////////////////////////////////////////
// write all values to a buffer

int MimeHeaders::getLength()
{
	int iResult = 0;
	for ( int i=0; i < mCount; i++ ) {
		MimeHeader& H = mVals[ i ];
		iResult += strlen( H.name ) + strlen( H.value ) + 4;
	}

	return iResult;
}

char* MimeHeaders::writeToBuffer( char* pDest )
{
	for ( int i=0; i < mCount; i++ ) {
		MimeHeader& H = mVals[ i ];
		pDest += sprintf( pDest, "%s: %s\r\n", H.name, H.value );
	}

	return pDest;
}

/////////////////////////////////////////////////////////////////////////////////////////
// read set of values from buffer

const char* MimeHeaders::readFromBuffer( const char* parString )
{
	int        headerCount = 0;
	MimeHeader headers[ 100 ];
	char		  line[ 4096 ];

	while ( *parString ) {
		if ( parString[0] == '\r' && parString[1] == '\n' ) {
			parString += 2;
			break;
		}

		const char* peol = strchr( parString, '\r' );
		if ( peol == NULL )
			peol = parString + strlen(parString);

		int cbLen = int( peol - parString );
		if ( cbLen > sizeof( line ))
			break;

      memcpy( line, parString, cbLen );
		line[ cbLen ] = 0;

		if ( *++peol == '\n' )
			peol++;

		parString = peol;

		char* delim = strchr( line, ':' );
		if ( delim == NULL ) {
			MSN_DebugLog( "MSG: Invalid MIME header: '%s'", line );
			continue;
		}

		*delim++ = '\0';
		while ( *delim == ' ' || *delim == '\t' )
			delim++;

		MimeHeader& H = headers[ headerCount ];
		H.name = mir_strdup( line );
		H.value = mir_strdup( delim );
		headerCount++;
	}

	if (( mCount = headerCount ) != 0 ) {
		mVals = ( MimeHeader* )mir_alloc( sizeof( MimeHeader )*headerCount );
		memcpy( mVals, headers, sizeof( MimeHeader )*headerCount );
	}
	return parString;
}

const char* MimeHeaders::operator[]( const char* szFieldName )
{
	for ( int i=0; i < mCount; i++ ) {
		MimeHeader& MH = mVals[i];
		if ( !strcmp( MH.name, szFieldName ))
			return MH.value;
	}

	return NULL;
}
