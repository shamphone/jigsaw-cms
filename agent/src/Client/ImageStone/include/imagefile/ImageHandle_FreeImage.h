/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-4-9
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGE_HANDLE_FREEIMAGE__2004_04_09__H__
#define __PCL_IMAGE_HANDLE_FREEIMAGE__2004_04_09__H__
#include "ImageHandleBase.h"
#include "../FreeImage_Helper.h"

//class FCImageHandle ;
    class FCImageHandle_FreeImage ;

//=============================================================================
/**
 *  Read/Write image via FreeImage library.
 */
class FCImageHandle_FreeImage : public FCImageHandle
{
public:
    FCImageHandle_FreeImage()
    {
        FreeImage_Initialise() ;
    }
    virtual ~FCImageHandle_FreeImage()
    {
        FreeImage_DeInitialise() ;
    }

    // read from file routine
    virtual bool LoadImageFile (const char* szFileName)
    {
        // get image format
        FREE_IMAGE_FORMAT     imgType = FreeImage_GetFileType (szFileName) ;
        if (imgType == FIF_UNKNOWN)
            return false ;

        // read image file via FreeImage library
        if ((imgType == FIF_GIF) || (imgType == FIF_TIFF))
        {
            FIMULTIBITMAP   * FIMul = FreeImage_OpenMultiBitmap (imgType, szFileName, FALSE, TRUE) ;
            if (!FIMul)
                return false ;
            for (int i=0 ; i < FreeImage_GetPageCount(FIMul) ; i++)
            {
                FIBITMAP   * FIimg = FreeImage_LockPage (FIMul, i) ;
                if (FIimg)
                {
                    StoreFreeImageObject (FIimg) ;
                    FreeImage_UnlockPage (FIMul, FIimg, FALSE) ;
                }
            }
            FreeImage_CloseMultiBitmap (FIMul) ;
        }
        else
        {
            FIBITMAP   * FIimg = FreeImage_Load (imgType, szFileName) ;
            if (!FIimg)
                return false ;
            StoreFreeImageObject (FIimg) ;
            FreeImage_Unload (FIimg) ;
        }
        return true ;
    }

    // read from memory routine
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize)
    {
        FIMEMORY   * pFIM = FreeImage_OpenMemory (const_cast<BYTE*>(pStart), nFileSize) ;
        if (!pFIM)
            return false ;

        // get image format
        FREE_IMAGE_FORMAT   imgType = FreeImage_GetFileTypeFromMemory (pFIM) ;
        if (imgType == FIF_UNKNOWN)
        {
            FreeImage_CloseMemory (pFIM) ;
            return false ;
        }

        // read image file via FreeImage library
        FIBITMAP     * FIimg = FreeImage_LoadFromMemory (imgType, pFIM) ;
        if (!FIimg)
        {
            FreeImage_CloseMemory (pFIM) ;
            return false ;
        }

        StoreFreeImageObject (FIimg) ;
        FreeImage_Unload (FIimg) ;
        FreeImage_CloseMemory (pFIM) ;
        return true ;
    }

    // save to file routine
    virtual bool SaveImage (const char* szFileName, int nFlag = -1)
    {
        if (m_SaveImg.empty() || !m_SaveImg[0]->IsValidImage())
            return false ;
        const FCObjImage   &img = *m_SaveImg[0] ;

        // is the FreeImage library support
        FREE_IMAGE_FORMAT     imgFormat = FreeImage_GetFIFFromFilename (szFileName) ;
        if (imgFormat == FIF_UNKNOWN)
            return false ;
        // jpeg default quality
        if (imgFormat == FIF_JPEG)
        {
            if ((nFlag < 0) || (nFlag > 100))
                nFlag = 82 ;
        }
        if (!FreeImage_FIFSupportsWriting (imgFormat) || !FreeImage_FIFSupportsExportBPP (imgFormat, img.ColorBits()))
            return false ;

        // create FreeImage object
        FIBITMAP   * pFIimg = __pcl_AllocateFreeImage (img) ;
        if (!pFIimg)
            return false ;

        // gif transparency
        if ((imgFormat == FIF_GIF) && (img.ColorBits() <= 8) && (nFlag != -1))
        {
            const int         nCount = 1 << img.ColorBits(),
                              nIndex = FClamp (nFlag, 0, nCount-1) ;
            PCL_array<BYTE>   aTab(nCount) ;
            memset (aTab.get(), 0xFF, nCount) ;
            aTab[nIndex] = 0 ;
            FreeImage_SetTransparent (pFIimg, true) ;
            FreeImage_SetTransparencyTable (pFIimg, aTab.get(), nCount) ;
        }

        // save image file
        bool   bRet = FreeImage_Save (imgFormat, pFIimg, szFileName, nFlag) ? true : false ;
        FreeImage_Unload (pFIimg) ;
        return bRet ;
    }

private:
    void StoreFreeImageObject (FIBITMAP* pFIimg)
    {
        // create image
        FCObjImage     * pImg = new FCObjImage ;
        if (!__pcl_FreeImage_to_PCLImage (pFIimg, *pImg))
        {
            delete pImg; return;
        }

        // pImg's ownership
        PCL_PushObject (pImg) ;
    }
};

//=============================================================================
// inline Implement
//=============================================================================

#endif
