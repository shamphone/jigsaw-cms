/*
Plugin of Miranda IM for reading/writing PNG images.
Copyright (c) 2004-6 George Hazan (ghazan@postman.ru)

Portions of this code are gotten from the libpng codebase.
Copyright 2000, Willem van Schaik.  For conditions of distribution and
use, see the copyright/license/disclaimer notice in png.h

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

File name      : $Source: /cvsroot/miranda/miranda/plugins/png2dib/png2dib.c,v $
Revision       : $Revision: 4866 $
Last change on : $Date: 2007-02-09 12:09:01 -0500 (Fri, 09 Feb 2007) $
Last change by : $Author: rainwater $

*/

#include <windows.h>
#include <commdlg.h>
#include <malloc.h>
#include <stdio.h>
#include <stdlib.h>

#include "libpng/png.h"

#include "newpluginapi.h"
#include "version.h"
#include "m_png.h"

DWORD __declspec(dllexport) getver( void )
{
	return __VERSION_DWORD;
}

// PNG image handler functions

typedef struct {
	char*		mBuffer;
	size_t	mBufSize;
	size_t	mBufPtr;
}
	HMemBufInfo;

static void png_read_data( png_structp png_ptr, png_bytep data, png_size_t length )
{
	HMemBufInfo* io = ( HMemBufInfo* )png_ptr->io_ptr;
	if ( length + io->mBufPtr > io->mBufSize )
		length = io->mBufSize - io->mBufPtr;

	if ( length > 0 ) {
		memcpy( data, io->mBuffer + io->mBufPtr, length );
		io->mBufPtr += length;
	}
	else png_error(png_ptr, "Read Error");
}

static void png_write_data( png_structp png_ptr, png_bytep data, png_size_t length )
{
	HMemBufInfo* io = ( HMemBufInfo* )png_ptr->io_ptr;
	if ( io->mBuffer != NULL )
		memcpy( io->mBuffer + io->mBufPtr, data, length );

	io->mBufPtr += length;
}

static void png_flush( png_structp png_ptr )
{
}

/*
 *		Converting a png image into a bitmap
 */

BOOL __declspec(dllexport) mempng2dib(
	BYTE*                pSource,
	DWORD                cbSourceSize,
	BITMAPINFOHEADER**   ppDibData )
{
	png_structp png_ptr	= NULL;
	png_infop info_ptr	= NULL;
	HMemBufInfo				sBuffer = { pSource, cbSourceSize, 8 };

	BOOL						bResult = FALSE;
	int						iWidth;
	int						iHeight;
	png_color				pBkgColor;
	int						iBitDepth;
	int						iColorType;
	double					dGamma;
	png_color_16*			pBackground;
	png_uint_32				ulChannels;
	png_uint_32				ulRowBytes;
	png_byte*				pbImageData;
	png_byte**				ppbRowPointers = NULL;
	int						i;
	int						wDIRowBytes;
	BYTE*                pImageData;

	*ppDibData = NULL;

	if ( pSource == NULL || cbSourceSize == 0 )
		return FALSE;

	if ( !png_check_sig( pSource, 8 ))
		return FALSE;

	// create the two png(-info) structures
	png_ptr = png_create_read_struct( PNG_LIBPNG_VER_STRING, NULL, NULL, NULL );
	if (!png_ptr)
		return FALSE;

	info_ptr = png_create_info_struct(png_ptr);
	if ( !info_ptr ) {
		png_destroy_read_struct(&png_ptr, NULL, NULL);
		return FALSE;
	}

	// initialize the png structure
	png_set_read_fn(png_ptr, (png_voidp)&sBuffer, png_read_data);
	png_set_sig_bytes(png_ptr, 8);

	// read all PNG info up to image data
	png_read_info(png_ptr, info_ptr);

	// get width, height, bit-depth and color-type

	png_get_IHDR(png_ptr, info_ptr, &iWidth, &iHeight, &iBitDepth, &iColorType, NULL, NULL, NULL);

	// expand images of all color-type and bit-depth to 3x8 bit RGB images
	// let the library process things like alpha, transparency, background

	if ( iBitDepth == 16 )
		png_set_strip_16( png_ptr );
	if ( iColorType == PNG_COLOR_TYPE_PALETTE )
		png_set_expand( png_ptr );
	if ( iBitDepth < 8 )
		png_set_expand( png_ptr );
	if (png_get_valid( png_ptr, info_ptr, PNG_INFO_tRNS ))
		png_set_expand( png_ptr );
	if ( iColorType == PNG_COLOR_TYPE_GRAY || iColorType == PNG_COLOR_TYPE_GRAY_ALPHA )
		png_set_gray_to_rgb( png_ptr );

	// set the background color to draw transparent and alpha images over.
	if (png_get_bKGD( png_ptr, info_ptr, &pBackground )) {
		png_set_background(png_ptr, pBackground, PNG_BACKGROUND_GAMMA_FILE, 1, 1.0);
		pBkgColor.red   = (byte) pBackground->red;
		pBkgColor.green = (byte) pBackground->green;
		pBkgColor.blue  = (byte) pBackground->blue;
	}

	// if required set gamma conversion
	if ( png_get_gAMA( png_ptr, info_ptr, &dGamma ))
		png_set_gamma( png_ptr, (double) 2.2, dGamma );

	// after the transformations have been registered update info_ptr data
	png_read_update_info(png_ptr, info_ptr);

	// get again width, height and the new bit-depth and color-type
	png_get_IHDR(png_ptr, info_ptr, &iWidth, &iHeight, &iBitDepth, &iColorType, NULL, NULL, NULL);

	// row_bytes is the width x number of channels
	ulRowBytes = png_get_rowbytes(png_ptr, info_ptr);
	ulChannels = png_get_channels(png_ptr, info_ptr);
	wDIRowBytes = (WORD) (( ulChannels * iWidth + 3L) >> 2) << 2;

	// now we can allocate memory to store the image
	{	DWORD cbMemSize = sizeof( BITMAPINFOHEADER );
		cbMemSize += wDIRowBytes * iHeight;
		if (( pbImageData = ( png_byte* )GlobalAlloc( LPTR, cbMemSize )) == NULL ) {
			png_destroy_read_struct( &png_ptr, &info_ptr, NULL );
			return FALSE;
	}	}

	// initialize the dib-structure
	{	BITMAPINFOHEADER* pbmih = ( BITMAPINFOHEADER* )pbImageData;
		*ppDibData = pbmih;

		pbmih->biSize = sizeof( BITMAPINFOHEADER );
		pbmih->biWidth = iWidth;
		pbmih->biHeight = iHeight;
		pbmih->biPlanes = 1;
		pbmih->biBitCount = ( WORD )( ulChannels * 8 );
		pbmih->biCompression = 0;
		pbmih->biSizeImage = iWidth * iHeight * ulChannels;

		pbImageData += sizeof( BITMAPINFOHEADER );
	}

	pImageData = (BYTE*)malloc( ulRowBytes * iHeight );
	if ( pImageData == NULL ) {
		png_destroy_read_struct(&png_ptr, NULL, NULL);
		return FALSE;
	}

	// and allocate memory for an array of row-pointers
	ppbRowPointers = ( png_bytepp )alloca( iHeight * sizeof( png_bytep ));

	// set the individual row-pointers to point at the correct offsets
	for ( i = 0; i < iHeight; i++ )
		ppbRowPointers[i] = ( png_bytep )&pImageData[ i*ulRowBytes ];

	// now we can go ahead and just read the whole image
	png_read_image( png_ptr, ppbRowPointers );
	png_read_end(png_ptr, NULL);

	// repack bytes to fill the bitmap
	for ( i = iHeight-1; i >= 0; i-- )
	{
		int j;
		png_byte a;
		png_bytep s = ppbRowPointers[i];
		BYTE* dest = pbImageData; pbImageData += wDIRowBytes;

		for ( j = 0; j < iWidth; j++ ) {
			png_byte r = *s++;
			png_byte g = *s++;
			png_byte b = *s++;
			if ( ulChannels == 4 )
				a = *s++;

			*dest++ = b;
			*dest++ = g;
			*dest++ = r;
			if ( ulChannels == 4 )
				*dest++ = a;
	}	}

	free( pImageData );
	png_destroy_read_struct( &png_ptr, &info_ptr, NULL );
	return TRUE;
}

/*
 *		Converting a bitmap into a png image
 */

static BOOL sttChechAlphaIsValid( BITMAPINFO* pbmi, png_byte* pDiData )
{
	int ciChannels = pbmi->bmiHeader.biBitCount / 8, i, j;
	int ulSrcRowBytes = ((( pbmi->bmiHeader.biWidth * ciChannels + 3 ) >> 2 ) << 2 );
	byte value = pDiData[ 3 ];

	if ( ciChannels < 4 )
		return FALSE;

	if ( value != 0 && value != 0xFF )
		return TRUE;

	for ( i=0; i < pbmi->bmiHeader.biHeight; i++ ) {
		png_byte* p = pDiData; pDiData += ulSrcRowBytes;

		for ( j=0; j < pbmi->bmiHeader.biWidth; j++, p += 4 )
			if ( p[3] != value )
				return TRUE;
	}

	return FALSE;
}

BOOL __declspec(dllexport) dib2mempng( BITMAPINFO* pbmi, png_byte* pDiData, BYTE* pResult, long* pResultLen )
{
	int ciBitDepth = 8;
	int ciChannels = pbmi->bmiHeader.biBitCount / 8;

	png_uint_32 ulSrcRowBytes, ulDstRowBytes;
	int         i;
	png_structp png_ptr = NULL;
	png_infop   info_ptr = NULL;
	png_bytepp  ppbRowPointers;
	png_bytep	pTempBuffer;
	BOOL        bIsAlphaValid;

	HMemBufInfo sBuffer = { pResult, 0, 0 };

	png_ptr = png_create_write_struct(PNG_LIBPNG_VER_STRING, NULL, (png_error_ptr)NULL, (png_error_ptr)NULL);
	if (!png_ptr)
		return FALSE;

	info_ptr = png_create_info_struct(png_ptr);
	if (!info_ptr) {
		png_destroy_write_struct(&png_ptr, (png_infopp) NULL);
		return FALSE;
	}

	// initialize the png structure
	bIsAlphaValid = sttChechAlphaIsValid( pbmi, pDiData );
	png_set_write_fn(png_ptr, (png_voidp)&sBuffer, png_write_data, png_flush);

	png_set_IHDR(png_ptr, info_ptr, pbmi->bmiHeader.biWidth, pbmi->bmiHeader.biHeight, ciBitDepth,
		( bIsAlphaValid ) ? PNG_COLOR_TYPE_RGB_ALPHA : PNG_COLOR_TYPE_RGB, 
		PNG_INTERLACE_NONE, PNG_COMPRESSION_TYPE_BASE, PNG_FILTER_TYPE_BASE);

	// write the file header information
	png_write_info(png_ptr, info_ptr);

	// swap the BGR pixels in the DiData structure to RGB
	png_set_bgr(png_ptr);

	// row_bytes is the width x number of channels
	ulSrcRowBytes = ((( pbmi->bmiHeader.biWidth * ciChannels + 3 ) >> 2 ) << 2 );
	ulDstRowBytes = ((( pbmi->bmiHeader.biWidth * ( ciChannels == 4 && bIsAlphaValid ? 4 : 3 ) + 3 ) >> 2 ) << 2 );

	ppbRowPointers = (png_bytepp)alloca( pbmi->bmiHeader.biHeight * sizeof(png_bytep));

	pTempBuffer = ( png_bytep )malloc( pbmi->bmiHeader.biHeight * ulDstRowBytes );
	if ( pTempBuffer != NULL ) {
		png_bytep pDest = pTempBuffer;
		for ( i = pbmi->bmiHeader.biHeight-1; i >= 0; i--) {
			BYTE *s, *d;
			int j;
			s = pDiData; pDiData += ulSrcRowBytes;
			d = ppbRowPointers[i] = pDest; pDest += ulDstRowBytes;

			if ( ciChannels >= 3 ) {
				for ( j = 0; j < pbmi->bmiHeader.biWidth; j++ ) {
					png_byte b = *s++;
					png_byte g = *s++;
					png_byte r = *s++;
					png_byte a = 0;

					if ( ciChannels == 4 )
						a = *s++;

					*d++ = b;
					*d++ = g;
					*d++ = r;
					if ( ciChannels == 4 && bIsAlphaValid )
						*d++ = a;
			}	}
			else {
				for ( j = 0; j < pbmi->bmiHeader.biWidth; j++ ) {
					DWORD point;
					if ( ciChannels == 1 ) {
						*d++ = ( BYTE )( point & 0x03 ) << 6;
						*d++ = ( BYTE )(( point & 0x0C ) >> 2 ) << 6;
						*d++ = ( BYTE )(( point & 0x30 ) >> 4 ) << 6;
						point = *s++;
					}
					else {
						point = *( WORD* )s;
						s += sizeof( WORD );
						*d++ = ( BYTE )(( point & 0x001F ) << 3 );
						*d++ = ( BYTE )((( point & 0x07e0 ) >> 6 ) << 3 );
						*d++ = ( BYTE )((( point & 0xF800 ) >> 11 ) << 3 );
		}	}	}	}

		png_write_image (png_ptr, ppbRowPointers);
		png_write_end(png_ptr, info_ptr);

		if ( pResultLen != NULL )
			*pResultLen = sBuffer.mBufPtr;

		free( pTempBuffer );
	}

	png_destroy_write_struct(&png_ptr, &info_ptr );
	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////////////////
// Standard Miranda structures & functions

HINSTANCE hInst = NULL;
PLUGINLINK *pluginLink;

static HANDLE hDib2mempng = NULL;
static HANDLE hMempng2Dib = NULL;

PLUGININFO pluginInfo = {
	sizeof( PLUGININFO ),
	"PNG images processor",
	__VERSION_DWORD,
	"png2dib plugin for Miranda IM ( "__DATE__" )",
	"George Hazan",
	"ghazan@miranda-im.org",
	"(c) 2004-06 George Hazan",
	"http://addons.miranda-im.org/details.php?action=viewfile&id=1420",
	0,
	0
};

__declspec( dllexport ) PLUGININFO* MirandaPluginInfo( DWORD mirandaVersion )
{
	if ( mirandaVersion < PLUGIN_MAKE_VERSION( 0,4,3,0 )) {
		MessageBox( NULL, "The png2dib plugin cannot be loaded. It requires Miranda IM 0.5 or later.", "png2dib Plugin", MB_OK|MB_ICONWARNING|MB_SETFOREGROUND|MB_TOPMOST );
		return NULL;
	}

	return &pluginInfo;
}

///////////////////////////////////////////////////////////////////////////////
// Load - initializes the plugin instance

static int serviceDib2Png( WPARAM wParam, LPARAM lParam )
{
	DIB2PNG* param = ( DIB2PNG* )lParam;
	return dib2mempng( param->pbmi, param->pDiData, param->pResult, param->pResultLen );
}

static int servicePng2Dib( WPARAM wParam, LPARAM lParam )
{
	PNG2DIB* param = ( PNG2DIB* )lParam;
	return mempng2dib( param->pSource, param->cbSourceSize, param->pResult );
}

int __declspec( dllexport ) Load( PLUGINLINK *link )
{
	pluginLink = link;

	hDib2mempng = CreateServiceFunction( MS_DIB2PNG, serviceDib2Png );
	hMempng2Dib = CreateServiceFunction( MS_PNG2DIB, servicePng2Dib );
	return 0;
}

///////////////////////////////////////////////////////////////////////////////
// Unload - destroys the plugin instance

int __declspec( dllexport ) Unload( void )
{
	DestroyServiceFunction( hDib2mempng );
	DestroyServiceFunction( hMempng2Dib );
	return 0;
}
