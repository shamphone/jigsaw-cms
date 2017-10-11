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

#include "sha1.h"

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_BitmapToAvatarDibBits - rescales a bitmap to 96x96 pixels and creates a DIB from it

HBITMAP __stdcall MSN_StretchBitmap( HBITMAP hBitmap )
{
	BITMAPINFO bmStretch = { 0 }; 
	bmStretch.bmiHeader.biSize = sizeof(BITMAPINFOHEADER);
	bmStretch.bmiHeader.biWidth = 96;
	bmStretch.bmiHeader.biHeight = 96;
	bmStretch.bmiHeader.biPlanes = 1;
	bmStretch.bmiHeader.biBitCount = 32;

	UINT* ptPixels;
	HBITMAP hStretchedBitmap = CreateDIBSection( NULL, &bmStretch, DIB_RGB_COLORS, ( void** )&ptPixels, NULL, 0);
	if ( hStretchedBitmap == NULL ) {
		MSN_DebugLog( "Bitmap creation failed with error %d", GetLastError() );
		return NULL;
	}

	BITMAP bmp;
	HDC hDC = CreateCompatibleDC( NULL );
	HBITMAP hOldBitmap1 = ( HBITMAP )SelectObject( hDC, hBitmap );
	GetObject( hBitmap, sizeof( BITMAP ), &bmp );

	HDC hBmpDC = CreateCompatibleDC( hDC );
	HBITMAP hOldBitmap2 = ( HBITMAP )SelectObject( hBmpDC, hStretchedBitmap );
	int side, dx, dy;

	if ( bmp.bmWidth > bmp.bmHeight ) {
		side = bmp.bmHeight;
		dx = ( bmp.bmWidth - bmp.bmHeight )/2;
		dy = 0;
	}
	else {
		side = bmp.bmWidth;
		dx = 0;
		dy = ( bmp.bmHeight - bmp.bmWidth )/2;
	}

	SetStretchBltMode( hBmpDC, HALFTONE );
	StretchBlt( hBmpDC, 0, 0, 96, 96, hDC, dx, dy, side, side, SRCCOPY );

	SelectObject( hDC, hOldBitmap1 );
	DeleteObject( hBitmap );
	DeleteDC( hDC );

	SelectObject( hBmpDC, hOldBitmap2 );
	DeleteDC( hBmpDC );
	return hStretchedBitmap;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_SaveBitmapAsAvatar - updates the avatar database settins and file from a bitmap

int __stdcall MSN_SaveBitmapAsAvatar( HBITMAP hBitmap, const char* szFileName ) 
{
	if ( !MSN_LoadPngModule())
		return 1;

	HDC hdc = CreateCompatibleDC( NULL );
	HBITMAP hOldBitmap = ( HBITMAP )SelectObject( hdc, hBitmap );

	BITMAPINFO* bmi = ( BITMAPINFO* )alloca( sizeof( BITMAPINFO ) + sizeof( RGBQUAD )*256 );
	memset( bmi, 0, sizeof( BITMAPINFO ));
	bmi->bmiHeader.biSize = 0x28;
	if ( GetDIBits( hdc, hBitmap, 0, 96, NULL, bmi, DIB_RGB_COLORS ) == 0 ) {
		TWinErrorCode errCode;
		MSN_ShowError( "Unable to get the bitmap: error %d (%s)", errCode.mErrorCode, errCode.getText() );
		return 2;
	}

	BITMAPINFOHEADER* pDib;
	BYTE* pDibBits;
	pDib = ( BITMAPINFOHEADER* )GlobalAlloc( LPTR, sizeof( BITMAPINFO ) + sizeof( RGBQUAD )*256 + bmi->bmiHeader.biSizeImage );
	if ( pDib == NULL )
		return 3;

	memcpy( pDib, bmi, sizeof( BITMAPINFO ) + sizeof( RGBQUAD )*256 );
	pDibBits = (( BYTE* )pDib ) + sizeof( BITMAPINFO ) + sizeof( RGBQUAD )*256;

	GetDIBits( hdc, hBitmap, 0, pDib->biHeight, pDibBits, ( BITMAPINFO* )pDib, DIB_RGB_COLORS );
	SelectObject( hdc, hOldBitmap );
	DeleteDC( hdc );

	long dwPngSize = 0;
	DIB2PNG convertor;
	convertor.pbmi = ( BITMAPINFO* )pDib;
	convertor.pDiData = pDibBits;
	convertor.pResult = NULL;
	convertor.pResultLen = &dwPngSize;
	if ( !CallService( MS_DIB2PNG, 0, (LPARAM)&convertor )) {
		GlobalFree( pDib );
		return 2;
	}

	convertor.pResult = new BYTE[ dwPngSize ];
	CallService( MS_DIB2PNG, 0, (LPARAM)&convertor );
	GlobalFree( pDib );

	SHA1Context sha1ctx;
	BYTE sha1c[ SHA1HashSize ], sha1d[ SHA1HashSize ];
	char szSha1c[ 40 ], szSha1d[ 40 ];
	SHA1Reset( &sha1ctx );
	SHA1Input( &sha1ctx, convertor.pResult, dwPngSize );
	SHA1Result( &sha1ctx, sha1d );
	{	NETLIBBASE64 nlb = { szSha1d, sizeof( szSha1d ), ( PBYTE )sha1d, sizeof( sha1d ) };
		MSN_CallService( MS_NETLIB_BASE64ENCODE, 0, LPARAM( &nlb ));
	}
	char drive[_MAX_DRIVE];
	char dir[_MAX_DIR];
	char fname[_MAX_FNAME];
	char ext[_MAX_EXT];
	_splitpath(szFileName, drive, dir, fname, ext );
	SHA1Reset( &sha1ctx );

	SHA1Input( &sha1ctx, ( PBYTE )"Creator", 7 );
	SHA1Input( &sha1ctx, ( PBYTE )MyOptions.szEmail, strlen( MyOptions.szEmail ));

	char szFileSize[ 20 ];
	ltoa( dwPngSize, szFileSize, 10 );
	SHA1Input( &sha1ctx, ( PBYTE )"Size", 4 );
	SHA1Input( &sha1ctx, ( PBYTE )szFileSize, strlen( szFileSize ));

	SHA1Input( &sha1ctx, ( PBYTE )"Type", 4 );
	SHA1Input( &sha1ctx, ( PBYTE )"3", 1 );

	SHA1Input( &sha1ctx, ( PBYTE )"Location", 8 );
	SHA1Input( &sha1ctx, ( PBYTE )fname, sizeof( fname ));

	SHA1Input( &sha1ctx, ( PBYTE )"Friendly", 8 );
	SHA1Input( &sha1ctx, ( PBYTE )"AAA=", 4 );

	SHA1Input( &sha1ctx, ( PBYTE )"SHA1D", 5 );
	SHA1Input( &sha1ctx, ( PBYTE )szSha1d, strlen( szSha1d ));
	SHA1Result( &sha1ctx, sha1c );
	{	NETLIBBASE64 nlb = { szSha1c, sizeof( szSha1c ), ( PBYTE )sha1c, sizeof( sha1c ) };
		MSN_CallService( MS_NETLIB_BASE64ENCODE, 0, LPARAM( &nlb ));
	}
	{
		char* szBuffer = ( char* )alloca( 1000 );
		mir_snprintf( szBuffer, 1000,
			"<msnobj Creator=\"%s\" Size=\"%ld\" Type=\"3\" Location=\"%s\" Friendly=\"AAA=\" SHA1D=\"%s\" SHA1C=\"%s\"/>",
			MyOptions.szEmail, dwPngSize,fname, szSha1d, szSha1c );

		char* szEncodedBuffer = ( char* )alloca( 1000 );
		UrlEncode( szBuffer, szEncodedBuffer, 1000 );

		MSN_SetString( NULL, "PictObject", szEncodedBuffer );
	}
	{	char tFileName[ MAX_PATH ];
		MSN_GetAvatarFileName( NULL, tFileName, sizeof( tFileName ));
		FILE* out = fopen( tFileName, "wb" );
		if ( out != NULL ) {
			fwrite( convertor.pResult, dwPngSize, 1, out );
			fclose( out );
	}	}
	delete convertor.pResult;
	return ERROR_SUCCESS;
}

/////////////////////////////////////////////////////////////////////////////////////////
// MSN_EnterBitmapFileName - enters a bitmap filename

int __stdcall MSN_EnterBitmapFileName( char* szDest )
{
	*szDest = 0;

	char szFilter[ 512 ];
	MSN_CallService( MS_UTILS_GETBITMAPFILTERSTRINGS, sizeof szFilter, ( LPARAM )szFilter );

	char str[ MAX_PATH ]; str[0] = 0;
	OPENFILENAMEA ofn = {0};
	ofn.lStructSize = sizeof( OPENFILENAME );
	ofn.lpstrFilter = szFilter;
	ofn.lpstrFile = szDest;
	ofn.Flags = OFN_FILEMUSTEXIST | OFN_HIDEREADONLY | OFN_NOCHANGEDIR;
	ofn.nMaxFile = MAX_PATH;
	ofn.nMaxFileTitle = MAX_PATH;
	ofn.lpstrDefExt = "bmp";
	if ( !GetOpenFileNameA( &ofn ))
		return 1;

	return ERROR_SUCCESS;
}
