/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-6-18
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
*/
#ifndef __PCL_IMAGE_HANDLE_GDIPLUS__2004_06_18__H__
#define __PCL_IMAGE_HANDLE_GDIPLUS__2004_06_18__H__
#include "ImageHandleBase.h"
#include "../oxo_helper.h"

//class FCImageHandle ;
    class FCImageHandle_Gdiplus ;

//=============================================================================
/**
 *  Read/Write image via Gdi+
 */
class FCImageHandle_Gdiplus : public FCImageHandle
{
    virtual bool LoadImageFile (const char* szFileName) ;
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize) ;
    virtual bool SaveImage (const char* szFileName, int nFlag = -1) ;

    bool __pcl_StoreGdiplusObject (FCImageHandle* pHandler, Gdiplus::Bitmap* gp_Bmp) ;
    bool __pcl_GetEncoderClsid (const WCHAR* szFormat, CLSID* pClsid) ;
public:
    FCImageHandle_Gdiplus()
    {
        Gdiplus::GdiplusStartupInput   gpStartupInput ;
        Gdiplus::GdiplusStartup (&m_GdiplusToken, &gpStartupInput, NULL) ;
    }
    virtual ~FCImageHandle_Gdiplus()
    {
        Gdiplus::GdiplusShutdown (m_GdiplusToken) ;
    }
private:
    ULONG_PTR   m_GdiplusToken ;
} ;

//=============================================================================
// inline Implement
//=============================================================================
inline bool FCImageHandle_Gdiplus::__pcl_StoreGdiplusObject (FCImageHandle* pHandler, Gdiplus::Bitmap* gp_Bmp)
{
    if (!gp_Bmp || (gp_Bmp->GetLastStatus() != Gdiplus::Ok))
    {
        assert(false); return false;
    }

    // get image's info
    BOOL      bInitAlpha = FALSE ;
    BYTE              __bufImgInfo[sizeof(BITMAPINFOHEADER) + 16] = {0} ;
    BITMAPINFOHEADER  * pInfo = (BITMAPINFOHEADER*)__bufImgInfo ;
    DWORD             * pMask = (DWORD*)(pInfo + 1) ;
    pInfo->biWidth = gp_Bmp->GetWidth() ;
    pInfo->biHeight = gp_Bmp->GetHeight() ;
    pInfo->biCompression = BI_RGB ;
    switch (gp_Bmp->GetPixelFormat())
    {
        case PixelFormat1bppIndexed : pInfo->biBitCount = 1 ; break ;
        case PixelFormat4bppIndexed : pInfo->biBitCount = 4 ; break ;
        case PixelFormat8bppIndexed : pInfo->biBitCount = 8 ; break ;
        case PixelFormat16bppRGB555 :
        case PixelFormat16bppARGB1555 :
            pInfo->biBitCount = 16 ;
            pInfo->biCompression = BI_BITFIELDS ;
            pMask[0] = MASK16_RED_555 ;
            pMask[1] = MASK16_GREEN_555 ;
            pMask[2] = MASK16_BLUE_555 ;
            break ;
        case PixelFormat16bppRGB565 :
            pInfo->biBitCount = 16 ;
            pInfo->biCompression = BI_BITFIELDS ;
            pMask[0] = MASK16_RED_565 ;
            pMask[1] = MASK16_GREEN_565 ;
            pMask[2] = MASK16_BLUE_565 ;
            break ;
        case PixelFormat32bppRGB :
            pInfo->biBitCount = 32 ; // no alpha channel, fill 0xFF
            bInitAlpha = TRUE ;
            break ;
        case PixelFormat24bppRGB : pInfo->biBitCount = 24 ; break ;
        case PixelFormat32bppPARGB :
        case PixelFormat32bppARGB : pInfo->biBitCount = 32 ; break ;
        default :
            return false ;
    }

    // create image
    FCObjImage   * pImg = new FCObjImage ;
    if (!pImg->Create(pInfo))
    {
        delete pImg; return false ;
    }

    // set palette
    if (pImg->ColorBits() <= 8)
    {
        const int     nPalSize = gp_Bmp->GetPaletteSize() ;
        PCL_array<Gdiplus::ColorPalette>   pPal (new BYTE[nPalSize]) ;
        gp_Bmp->GetPalette (pPal.get(), nPalSize) ;
        pImg->SetColorTable (0, pPal.get()->Count, (RGBQUAD*)&pPal.get()->Entries[0]) ;
    }

    // set pixel
    Gdiplus::Rect         rcLock (0, 0, pImg->Width(), pImg->Height()) ;
    Gdiplus::BitmapData   BmpData ;

    #if _MSC_VER > 1200 // VC7
        gp_Bmp->LockBits (&rcLock, Gdiplus::ImageLockModeRead, gp_Bmp->GetPixelFormat(), &BmpData) ;
    #else
        gp_Bmp->LockBits (rcLock, Gdiplus::ImageLockModeRead, gp_Bmp->GetPixelFormat(), &BmpData) ;
    #endif

    const BYTE     * pixels = (BYTE*)BmpData.Scan0 ;
    const int      nPitch = FMin (pImg->GetPitch(), BmpData.Stride) ;
    for(int y=0 ; y < pImg->Height() ; y++)
    {
        CopyMemory (pImg->GetBits(y), &pixels[y * BmpData.Stride], nPitch) ;
    }
    gp_Bmp->UnlockBits (&BmpData) ;

    // over
    if (bInitAlpha)
        pImg->SetAlphaChannelValue(0xFF) ;
    pHandler->PCL_PushObject (pImg) ; // pImg's ownership
    return true ;
}
//-----------------------------------------------------------------------------
inline bool FCImageHandle_Gdiplus::LoadImageFile (const char* szFileName)
{
    // load image file
    bool             bRet = false ;
    Gdiplus::Bitmap  * gp_Bmp = Gdiplus::Bitmap::FromFile(_bstr_t(szFileName)) ; // ASCII --> UNICODE
    if (gp_Bmp)
    {
        bRet = __pcl_StoreGdiplusObject (this, gp_Bmp) ;
        delete gp_Bmp ;
    }
    return bRet ;
}
//-----------------------------------------------------------------------------
inline bool FCImageHandle_Gdiplus::LoadImageMemory (const BYTE* pStart, int nFileSize)
{
    if (!pStart || (nFileSize <= 0))
    {
        assert(false); return false;
    }

    bool      bRet = false ;
    HGLOBAL   hBuffer = ::GlobalAlloc (GMEM_MOVEABLE, nFileSize) ;

    // copy to HGLOBAL then load
    ::CopyMemory (::GlobalLock(hBuffer), pStart, nFileSize) ;
    ::GlobalUnlock (hBuffer) ;

    IStream     * pStream = NULL ;
    if (::CreateStreamOnHGlobal (hBuffer, TRUE, &pStream) == S_OK)
    {
        Gdiplus::Bitmap   * gp_Bmp = Gdiplus::Bitmap::FromStream(pStream) ;
        if (gp_Bmp)
        {
            bRet = __pcl_StoreGdiplusObject (this, gp_Bmp) ;
            delete gp_Bmp ;
        }
        pStream->Release() ;
    }
    return bRet ;
}
//-----------------------------------------------------------------------------
inline bool FCImageHandle_Gdiplus::__pcl_GetEncoderClsid (const WCHAR* szFormat, CLSID* pClsid)
{
    UINT     nNum=0, nSize=0 ;
    Gdiplus::GetImageEncodersSize (&nNum, &nSize) ;
    if (nSize <= 0)
        return false ;

    PCL_array<Gdiplus::ImageCodecInfo>   pICI (new BYTE[nSize]) ;
    Gdiplus::GetImageEncoders (nNum, nSize, pICI.get()) ;

    for (UINT i=0 ; i < nNum; i++)
    {
        if (wcscmp(pICI[i].MimeType, szFormat) == 0)
        {
            *pClsid = pICI[i].Clsid ;
            return true ;
        }    
    }
    return false ;
}
inline bool FCImageHandle_Gdiplus::SaveImage (const char* szFileName, int nFlag)
{
    if (m_SaveImg.empty() || !m_SaveImg[0]->IsValidImage())
        return false ;
    const FCObjImage   &img = *m_SaveImg[0] ;

    // get encoder's CLSID
    CLSID      imgClsid ;
    bstr_t     strType ;
    switch (FCObjImage::GetImageHandleFactory()->QueryImageFileType(szFileName))
    {
        case IMG_BMP : strType = "image/bmp"  ; break;
        case IMG_JPG : strType = "image/jpeg" ; break;
        case IMG_GIF : strType = "image/gif"  ; break;
        case IMG_TIF : strType = "image/tiff" ; break;
        case IMG_PNG : strType = "image/png"  ; break;
        default : return false ;
    }
    __pcl_GetEncoderClsid (strType, &imgClsid) ;

    // create a GDI+ bitmap to save
    PCL_array<BITMAPINFO>   bmfh (img.NewImgInfoWithPalette()) ;
    Gdiplus::Bitmap         gpBmp (bmfh.get(), img.GetMemStart()) ;
    return (gpBmp.Save (bstr_t(szFileName), &imgClsid, NULL) == Gdiplus::Ok) ;
}

#endif
