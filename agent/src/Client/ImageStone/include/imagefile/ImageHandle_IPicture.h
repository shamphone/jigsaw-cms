/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-9-12
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGE_HANDLE_IPICTURE__2005_09_12__H__
#define __PCL_IMAGE_HANDLE_IPICTURE__2005_09_12__H__
#include "ImageHandleBase.h"

//class FCImageHandle ;
    class FCImageHandle_IPicture ;

//=============================================================================
/**
 *  Use windows IPicture interface to read/write image handler.
 */
class FCImageHandle_IPicture : public FCImageHandle
{
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize)
	{
		if (!pStart || (nFileSize <= 0))
			return false ;

		HGLOBAL   hBuffer = ::GlobalAlloc (GMEM_MOVEABLE, nFileSize) ;

		// copy buffer to HGLOBAL
		::CopyMemory (::GlobalLock(hBuffer), pStart, nFileSize) ;
		::GlobalUnlock (hBuffer) ;

		IStream     * pStream = NULL ;
		IPicture    * pIPic = NULL ;
		FCObjImage  * pImg = new FCObjImage ;
		if (::CreateStreamOnHGlobal (hBuffer, TRUE, &pStream) == S_OK)
		{
			if (::OleLoadPicture (pStream, nFileSize, FALSE, IID_IPicture, (LPVOID*)&pIPic) == S_OK)
			{
				OLE_HANDLE   hDDB ;
				pIPic->get_Handle (&hDDB) ;

				BITMAP       bm ;
				GetObject ((HGDIOBJ)hDDB, sizeof(BITMAP), &bm) ;
				if (pImg->Create (bm.bmWidth, bm.bmHeight, 24))
				{
					// dest format
					PCL_array<BITMAPINFO>   bmfh (pImg->NewImgInfoWithPalette()) ;
					HDC     hdc = ::GetDC(NULL) ;
					::GetDIBits (hdc, (HBITMAP)hDDB, 0, bm.bmHeight, pImg->GetMemStart(), bmfh.get(), DIB_RGB_COLORS) ;
					::ReleaseDC (NULL, hdc) ;
				}
				pIPic->Release() ;
			}
			pStream->Release() ;
		}

		if (!pImg->IsValidImage())
		{
			delete pImg; return false;
		}
		PCL_PushObject (pImg) ; // pImg's ownership
		return true ;
	}
};

//=============================================================================
// inline Implement
//=============================================================================

#endif
