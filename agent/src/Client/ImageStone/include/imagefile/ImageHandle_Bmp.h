/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-4-9
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGE_HANDLE_BMP__2004_04_09__H__
#define __PCL_IMAGE_HANDLE_BMP__2004_04_09__H__
#include "ImageHandleBase.h"

//class FCImageHandle ;
    class FCImageHandle_Bmp ;

//=============================================================================
/**
 *  Read/Write BMP file.
 */
class FCImageHandle_Bmp : public FCImageHandle
{
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize) ;
    virtual bool SaveImage (const char* szFileName, int nFlag = -1) ;

#pragma pack(2)
    struct PCL_BMPHEADER
    {
        WORD    bfType;
        DWORD   bfSize;
        WORD    bfReserved1;
        WORD    bfReserved2;
        DWORD   bfOffBits;
    };
#pragma pack()
};

//=============================================================================
// inline Implement
//=============================================================================
inline bool FCImageHandle_Bmp::LoadImageMemory (const BYTE* pStart, int nFileSize)
{
    if (!pStart || (nFileSize <= sizeof(PCL_BMPHEADER)))
        return false ;
    if (((PCL_BMPHEADER*)pStart)->bfType != 0x4D42)
        return false ;

    // size check
    const int   nOffset = (int)((PCL_BMPHEADER*)pStart)->bfOffBits ;
    if (nFileSize <= nOffset)
        return false ;

    // create image
    BITMAPINFOHEADER   * pBmih = (BITMAPINFOHEADER*)(pStart + sizeof(PCL_BMPHEADER)) ;
    FCObjImage         * pImg = new FCObjImage ;
    if (!pImg->Create(pBmih))
    {
        delete pImg; return false;
    }

    // set palette
    if (pImg->ColorBits() <= 8)
        pImg->SetColorTable(0, 1<<pImg->ColorBits(), (RGBQUAD*)((BYTE*)pBmih + pBmih->biSize)) ;

    // set pixel
    int     nCopyByte = pImg->GetPitch()*pImg->Height() ;
    assert (nFileSize - nOffset >= nCopyByte) ;
    nCopyByte = FMin (nCopyByte, nFileSize - nOffset) ;
    memcpy (pImg->GetMemStart(), pStart + nOffset, nCopyByte) ;
    PCL_PushObject (pImg) ; // pImg's ownership
    return true ;
}
//-----------------------------------------------------------------------------
inline bool FCImageHandle_Bmp::SaveImage (const char* szFileName, int nFlag)
{
    if (m_SaveImg.empty() || !m_SaveImg[0]->IsValidImage())
        return false ;
    const FCObjImage   &img = *m_SaveImg[0] ;

    // create image file, if the file already exists, its contents are discarded.
    std::ofstream    outFile (szFileName, std::ios::out|std::ios::binary|std::ios::trunc) ;
    if (!outFile.is_open())
        return false ;

    // calculate total size of bmp file.
    const int   nPixs = img.GetPitch() * img.Height() ;
          int   nTotalSize = sizeof(PCL_BMPHEADER) + sizeof(BITMAPINFOHEADER) + nPixs ;

    PCL_array<BITMAPINFOHEADER>   info (img.NewImgInfoWithPalette()) ;
    if (img.ColorBits() <= 8)
        nTotalSize += (4 * (1<<img.ColorBits())) ;
    else if (info.get()->biCompression == BI_BITFIELDS)
        nTotalSize += 12 ;

    // write bmp file header
    PCL_BMPHEADER     bmpFileHeader ;
    bmpFileHeader.bfType      = 0x4D42 ; // "BM"
    bmpFileHeader.bfSize      = nTotalSize ;
    bmpFileHeader.bfReserved1 =
    bmpFileHeader.bfReserved2 = 0 ;
    bmpFileHeader.bfOffBits   = nTotalSize - nPixs ;
    outFile.write ((char*)&bmpFileHeader, sizeof(bmpFileHeader)) ;

    // write BMP file info / bitfields / palette
    outFile.write ((char*)info.get(), nTotalSize - sizeof(PCL_BMPHEADER) - nPixs) ;

    // write pixels value
    outFile.write ((char*)img.GetMemStart(), nPixs) ;
    return true ;
}

#endif
