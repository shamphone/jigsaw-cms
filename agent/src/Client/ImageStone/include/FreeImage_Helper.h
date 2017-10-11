/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2006-2-26
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_FREEIMAGE_HELPER__2006_02_26__H__
#define __PCL_FREEIMAGE_HELPER__2006_02_26__H__
#include "ObjImage.h"

//=============================================================================
inline bool __pcl_FreeImage_to_PCLImage (FIBITMAP* pFIimg, FCObjImage& img)
{
    if (!pFIimg)
        return false ;

    // prepare image info
    BYTE              __bufImgInfo[sizeof(BITMAPINFOHEADER) + 16] = {0} ;
    BITMAPINFOHEADER  * pInfo = (BITMAPINFOHEADER*)__bufImgInfo ;
    memcpy (pInfo, FreeImage_GetInfoHeader(pFIimg), sizeof(BITMAPINFOHEADER)) ;
    if (pInfo->biCompression == BI_BITFIELDS)
    {
        DWORD   * pMask = (DWORD*)(pInfo + 1) ;
        pMask[0] = FreeImage_GetRedMask (pFIimg) ;
        pMask[1] = FreeImage_GetGreenMask (pFIimg) ;
        pMask[2] = FreeImage_GetBlueMask (pFIimg) ;
    }

    // create image
    if (!img.Create(pInfo))
        return false ;

    // set palette
    if (img.ColorBits() <= 8)
        img.SetColorTable (0, FreeImage_GetColorsUsed(pFIimg), FreeImage_GetPalette(pFIimg)) ;

    // set pixel
    memcpy (img.GetMemStart(), FreeImage_GetBits(pFIimg), img.GetPitch()*img.Height()) ;

    // transparency
    if ((img.ColorBits() <= 8) && FreeImage_IsTransparent(pFIimg))
    {
        FCObjImage   idxImg (img) ;

        int    n = (int)FreeImage_GetTransparencyCount(pFIimg) ;
        BYTE   * p = FreeImage_GetTransparencyTable(pFIimg) ;

        img.ConvertTo32Bit() ;
        for (int y=0 ; y < img.Height() ; y++)
            for (int x=0 ; x < img.Width() ; x++)
            {
                int   nIndex = *idxImg.GetBits(x,y) ;
                if (nIndex < n)
                    PCL_A(img.GetBits(x,y)) = p[nIndex] ;
            }
    }

    // image delay time
    FITAG   * tag ;
    if (FreeImage_GetMetadata(FIMD_ANIMATION, pFIimg, "FrameTime", &tag))
    {
        img.SetNextFrameDelay (*(int*)FreeImage_GetTagValue(tag)) ;
    }
    return true ;
}
//=============================================================================
inline FIBITMAP* __pcl_AllocateFreeImage (const FCObjImage& img)
{
    if (!img.IsValidImage())
        return 0 ;

    // create FreeImage object
    DWORD     dwBitFields[3] = {0, 0, 0} ;
    if (img.ColorBits() == 16)
    {
        PCL_array<BITMAPINFOHEADER>   info (img.NewImgInfoWithPalette()) ;
        memcpy (dwBitFields, info.get() + 1, 12) ;
    }
    FIBITMAP   * pFIimg = FreeImage_AllocateT (FIT_BITMAP, img.Width(), img.Height(), img.ColorBits(), dwBitFields[0], dwBitFields[1], dwBitFields[2]) ;
    if (!pFIimg)
        return 0 ;

    // set pixel
    assert (img.GetPitch() == FreeImage_GetPitch(pFIimg)) ;
    memcpy (FreeImage_GetBits(pFIimg), img.GetMemStart(), img.GetPitch()*img.Height()) ;

    // set palette
    if (img.ColorBits() <= 8)
    {
        RGBQUAD     pPal[256] = {0} ;
        img.GetColorTable (0, 1<<img.ColorBits(), pPal) ;
        memcpy (FreeImage_GetPalette(pFIimg), pPal, FreeImage_GetColorsUsed(pFIimg)*sizeof(RGBQUAD)) ;
    }
    return pFIimg ;
}
//=============================================================================

#endif
