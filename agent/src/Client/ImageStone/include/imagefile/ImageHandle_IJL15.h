/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2006-2-21
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGE_HANDLE_IJL15__2006_02_21__H__
#define __PCL_IMAGE_HANDLE_IJL15__2006_02_21__H__
#include "ImageHandleBase.h"

#include "../../lib/ijl15/ijl.h"

//class FCImageHandle ;
    class FCImageHandle_IJL15 ;

//=============================================================================
/**
 *  Read/Write Jpeg file via intel jpeg library.
 */
class FCImageHandle_IJL15 : public FCImageHandle
{
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize) ;
    virtual bool SaveImage (const char* szFileName, int nFlag = -1) ;
};

//=============================================================================
// inline Implement
//=============================================================================
inline bool FCImageHandle_IJL15::LoadImageMemory (const BYTE* pStart, int nFileSize)
{
    if (!pStart)
        return false ;

    JPEG_CORE_PROPERTIES     image ;
    ::ZeroMemory (&image, sizeof(image)) ;

    // Init the IJL
    if (ijlInit (&image) != IJL_OK)
        return false ;

    // read image info
    image.JPGBytes = (unsigned char*)pStart ;
    image.JPGSizeBytes = nFileSize ;
    if (ijlRead (&image, IJL_JBUFF_READPARAMS) != IJL_OK)
    {
        ijlFree (&image) ;
        return false ;
    }

    // dest image
    image.DIBChannels = 3 ;
    image.DIBColor = IJL_BGR ;
    switch (image.JPGChannels)
    {
        case 1 : image.JPGColor = IJL_G ; break ;
        case 3 : image.JPGColor = IJL_YCBCR ; break ;
        case 4 : image.JPGColor = IJL_YCBCRA_FPX ;
                 image.DIBChannels = 4 ;
                 image.DIBColor = IJL_RGBA_FPX ;
                 break ;
    }

    // create image object
    FCObjImage   * pImg = new FCObjImage ;
    if (!pImg->Create (image.JPGWidth, image.JPGHeight, image.DIBChannels * 8))
    {
        ijlFree (&image) ;
        delete pImg ;
        return false ;
    }

    image.DIBWidth    = image.JPGWidth ;
    image.DIBHeight   = -image.JPGHeight ; // DIB Format
    image.DIBBytes    = pImg->GetMemStart() ;
    image.DIBPadBytes = pImg->GetPitch() - pImg->Width() * pImg->ColorBits()/8 ;

    // read whole image
    if (ijlRead (&image, IJL_JBUFF_READWHOLEIMAGE) != IJL_OK)
    {
        ijlFree (&image) ;
        delete pImg ;
        return false ;
    }

    // 4-Channel need BGRA_to_RGBA
    if (image.DIBChannels == 4)
        for (int y=0 ; y < pImg->Height() ; y++)
            for (int x=0 ; x < pImg->Width() ; x++)
            {
                BYTE   * p = pImg->GetBits(x,y) ;
                FSwap (p[0], p[2]) ;
            }

    ijlFree (&image) ;
    PCL_PushObject (pImg) ; // pImg's ownership
    return true ;
}
//-----------------------------------------------------------------------------
inline bool FCImageHandle_IJL15::SaveImage (const char* szFileName, int nFlag)
{
    if (m_SaveImg.empty() || !m_SaveImg[0]->IsValidImage())
        return false ;
    const FCObjImage   &img = *m_SaveImg[0] ;

    // must 24bpp image's format
    if (img.ColorBits() != 24)
        {assert(false); return false;}

    JPEG_CORE_PROPERTIES     image ;
    ::ZeroMemory (&image, sizeof(image)) ;

    // Init the IJL
    if (ijlInit (&image) != IJL_OK)
        return false ;

    image.DIBWidth  = img.Width() ;
    image.DIBHeight = -img.Height() ; // DIB Format
    image.DIBChannels = 3 ;
    image.DIBPadBytes = img.GetPitch() - img.Width() * 3 ;
    image.DIBColor  = IJL_BGR ;
    image.JPGFile   = const_cast<PTSTR>(szFileName) ;
    image.JPGWidth  = img.Width() ;
    image.JPGHeight = img.Height() ;
    image.jquality  = (nFlag == -1) ? 80 : nFlag ;
    image.JPGChannels = 3 ;
    image.JPGColor = IJL_YCBCR ;
    image.JPGSubsampling = IJL_411 ;
    image.DIBBytes = img.GetMemStart() ;
    bool   bRet = (ijlWrite (&image, IJL_JFILE_WRITEWHOLEIMAGE) == IJL_OK) ;
    ijlFree (&image) ;
    return bRet ;
}

#endif
