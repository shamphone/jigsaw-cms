/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-4-9
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGE_HANDLE_TGA__2004_04_09__H__
#define __PCL_IMAGE_HANDLE_TGA__2004_04_09__H__
#include "ImageHandleBase.h"
#include "../compress/Rle.h"

//class FCImageHandle ;
    class FCImageHandle_Tga ;

//=============================================================================
/**
 *  Read/Write TGA file.
 */
class FCImageHandle_Tga : public FCImageHandle
{
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize) ;

    /**
     *  @param nFlag : 1(use RLE compress) / -1(not use), default(not use)
     */
    virtual bool SaveImage (const char* szFileName, int nFlag = -1) ;

    // Definitions for TGA image types.
    enum
    {
        TGA_NULL    = 0,
        TGA_UCPAL   = 1,
        TGA_UCRGB   = 2,
        TGA_UCMONO  = 3,
        TGA_RLEPAL  = 9,
        TGA_RLERGB  = 10,
        TGA_RLEMONO = 11,
    };

    // TGA file header (same as BMP format, pixel store from left-bottom)
#pragma pack(1)
    struct TGAHEAD
    {
        BYTE	byID_Length ;	// 图像识别信息大小
        BYTE	byPalType ;		// 00 : no-palette     01 : has-palette
        BYTE	byImageType ;	// 见上
        WORD	wPalFirstNdx ;	// 调色板起始索引
        WORD	wPalLength ;	// 调色板长度
        BYTE	byPalBits ;		// 调色板中每一颜色所占位数
        WORD	wLeft ;			// 相对于屏幕左下角X坐标
        WORD	wBottom ;		// 相对于屏幕左下角Y坐标
        WORD	wWidth ;		// width
        WORD	wHeight ;		// height
        BYTE	byColorBits ;	// bpp
        struct
        {
            BYTE	AlphaBits : 4 ;	// 每像素Alpha Channel位数
            BYTE	HorMirror : 1 ;	// 为1表示图像数据左右颠倒存储
            BYTE	VerMirror : 1 ;	// 为1表示图像数据上下颠倒存储
            BYTE	Reserved : 2 ;
        } Descriptor ;
    }; // 18 - Bytes
#pragma pack()
};

//=============================================================================
// inline Implement
//=============================================================================
inline bool FCImageHandle_Tga::LoadImageMemory (const BYTE* pStart, int nFileSize)
{
    const TGAHEAD   * pTga = (TGAHEAD*)pStart ;
    if (!pStart)
        return false ;
    if ((pTga->byPalBits == 15) || (pTga->byPalBits == 16))
        return false ; // not support 15 or 16 bit palette

    // create image
    FCObjImage   * pImg = new FCObjImage ;
    if (!pImg->Create (pTga->wWidth, pTga->wHeight, (pTga->byColorBits == 15) ? 16 : pTga->byColorBits))
    {
        delete pImg; return false ;
    }

    // set palette
    const BYTE     * pCurr = pStart + sizeof(TGAHEAD) + pTga->byID_Length ;
    if (pTga->byPalType == 1)
    {
        if (pTga->wPalFirstNdx + pTga->wPalLength > 256)
        {
            delete pImg; return false ;
        }

        RGBQUAD     pPal[256] = {0} ;
        for (int i=0 ; i < pTga->wPalLength ; i++)
        {
            PCL_B(&pPal[pTga->wPalFirstNdx + i]) = *pCurr++ ;
            PCL_G(&pPal[pTga->wPalFirstNdx + i]) = *pCurr++ ;
            PCL_R(&pPal[pTga->wPalFirstNdx + i]) = *pCurr++ ;
            if (pTga->byPalBits == 32)
                PCL_A(&pPal[pTga->wPalFirstNdx + i]) = *pCurr++ ;
        }
        pImg->SetColorTable (0, 256, pPal) ;
    }

    // start decode
    for (int i=0 ; i < pImg->Height() ; i++)
    {
        BYTE     * pDest ;
        if (pStart[17] & 0x20)
            pDest = pImg->GetBits(i) ; // top to bottom
        else
            pDest = pImg->GetBits(pImg->Height() - 1 - i) ; // bottom to top

        if ((pTga->byImageType == TGA_RLEPAL) || (pTga->byImageType == TGA_RLERGB) || (pTga->byImageType == TGA_RLEMONO)) // 压缩
        {
            pCurr = FCCompress::RLE_TGA_DecodeLine (pCurr, pImg->ColorBits(), pImg->Width(), pDest) ;
        }
        else // not-compressed
        {
            int   nPitch = pImg->Width() * pImg->ColorBits() / 8 ;
            memcpy (pDest, pCurr, nPitch) ;
            pCurr += nPitch ;
        }
    }
    PCL_PushObject (pImg) ; // pImg's ownership
    return true ;
}
//-----------------------------------------------------------------------------
inline bool FCImageHandle_Tga::SaveImage (const char* szFileName, int nFlag)
{
    if (m_SaveImg.empty())
        return false ;
    const FCObjImage   &img = *m_SaveImg[0] ;

    // validate
    if ((img.ColorBits() != 8) && (img.ColorBits() != 16) && (img.ColorBits() != 24) && (img.ColorBits() != 32))
        return false ;

    // create image file
    std::ofstream    outFile (szFileName, std::ios::out|std::ios::binary|std::ios::trunc) ;
    if (!outFile.is_open())
        return false ;

    // Initialize TGA Header
    const BYTE   fTgaInfo[] = {"PhoXo -- TGA"} ;
    TGAHEAD      TgaHead ;
    memset (&TgaHead, 0, sizeof(TgaHead)) ;
    TgaHead.byID_Length     = sizeof(fTgaInfo) - 1 ; // tga size
    TgaHead.byPalType       = ((img.ColorBits() == 8) ? 1 : 0) ;

    if (nFlag == -1)
        TgaHead.byImageType = ((img.ColorBits() == 8) ? TGA_UCPAL : TGA_UCRGB) ;
    else if (nFlag == 1)
        TgaHead.byImageType = ((img.ColorBits() == 8) ? TGA_RLEPAL : TGA_RLERGB) ;

    TgaHead.wPalFirstNdx    = 0 ;
    TgaHead.wPalLength      = 256 ;
    TgaHead.byPalBits       = 24 ; // palette's bit
    TgaHead.wWidth          = img.Width() ;
    TgaHead.wHeight         = img.Height() ;
    TgaHead.byColorBits     = (BYTE)img.ColorBits() ;
    ((BYTE*)&TgaHead)[17]   = 0x20 ; // top to bottom
    outFile.write ((char*)&TgaHead, sizeof(TGAHEAD)) ;
    outFile.write ((char*)fTgaInfo, TgaHead.byID_Length) ;

    // write palette
    if (img.ColorBits() == 8)
    {
        RGBQUAD   pPal[256] ;
        img.GetColorTable (0, 256, pPal) ;
        for (int i=0 ; i < 256 ; i++)
            outFile.write ((char*)&pPal[i], 3) ;
    }

    // write pixels
    const int     nLineByte = img.ColorBits() * img.Width() / 8 ;
    if (nFlag == -1)
    {
        // not compress
        for (int y=0 ; y < img.Height() ; y++)
            outFile.write ((char*)img.GetBits(y), nLineByte) ;
    }
    else if (nFlag == 1)
    {
        // RLE compress
        PCL_array<BYTE>   pStart (nLineByte * 2 + 4096) ;
        for (int y=0 ; y < img.Height() ; y++)
        {
            BYTE   * pEn = FCCompress::RLE_TGA_EncodeLine (img.GetBits(y), img.ColorBits(), img.Width(), pStart.get()) ;
            outFile.write ((char*)pStart.get(), (int)(pEn - pStart.get())) ;
        }
    }
    return true ;
}

#endif
