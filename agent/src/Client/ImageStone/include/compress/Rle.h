/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2000-9-6
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_COMPRESS__2000_09_06__H__
#define __PCL_COMPRESS__2000_09_06__H__
#include "../FColor.h"

//=============================================================================
/**
 *  Compress helper class.
 */
class FCCompress
{
public:
    /**
     *  pOutBuffer must at least double size than pInBuffer.
     *  @return return bytes of written to pOutBuffer.
     */
    static int RLE_PCX_Encode (const BYTE* pInBuffer, int nInSize, BYTE* pOutBuffer) ;
    /**
     *  pOutBuffer must large enough.
     *  @return return bytes of written to pOutBuffer.
     */
    static int RLE_PCX_Decode (const BYTE* pInBuffer, int nInSize, BYTE* pOutBuffer) ;

    /**
     *  pOutBuffer must at least double size than pInBuffer.
     *  @param iColorBit : bpp - 8, 16, 24, 32
     *  @param iNumPixel : pixel width of image line
     *  @return return current pOutBuffer position.
     */
    static BYTE* RLE_TGA_EncodeLine (const BYTE* pInBuffer, int iColorBit, int iNumPixel, BYTE* pOutBuffer) ;
    /**
     *  pOutBuffer must large enough.
     *  @param iColorBit : bpp - 8, 16, 24, 32
     *  @param iNumPixel : pixel width of image line
     *  @return return current pInBuffer position.
     */
    static BYTE* RLE_TGA_DecodeLine (const BYTE* pInBuffer, int iColorBit, int iNumPixel, BYTE* pOutBuffer) ;
};

//=============================================================================
// inline Implement
//=============================================================================
inline int FCCompress::RLE_PCX_Encode (const BYTE* pInBuffer, int nInSize, BYTE* pOutBuffer)
{
    const BYTE   * pOutStart = pOutBuffer ;
    while (nInSize-- > 0)
    {
        const BYTE   byData = *pInBuffer++ ;
        BYTE         cCount = 1 ;
        while ( (cCount < 0x3F) && (nInSize != 0) )
            if (*pInBuffer != byData) // Stat. the repeat BYTE
                break ;
            else
            {
                cCount++ ; pInBuffer++ ; nInSize-- ;
            }

        if (cCount == 1) // unique
        {
            if ( byData >= 0xC0 ) // Data >= 0xC0
            {
                *pOutBuffer++ = 0xC1 ;
                *pOutBuffer++ = byData ;
            }
            else
                *pOutBuffer++ = byData ; // Data < 0xC0, write directly
        }
        else // repeat
        {
            *pOutBuffer++ = 0xC0 | cCount ;
            *pOutBuffer++ = byData ;
        }
    }
    return (int)(pOutBuffer - pOutStart) ;
}
//-----------------------------------------------------------------------------
inline int FCCompress::RLE_PCX_Decode (const BYTE* pInBuffer, int nInSize, BYTE* pOutBuffer)
{
    const BYTE   * pOutStart = pOutBuffer ;
    while (nInSize-- > 0)
    {
        const BYTE   byData = *pInBuffer++ ; // read byte and move ptr to next
        if ( byData >= 0xC0 )
        {
            // error : the inbuffer has been exhausted.
            if (nInSize <= 0)
            {
                assert(false) ; goto rleOver;
            }

            BYTE     cNum = byData & 0x3F ; // repeat current byte Num
            ::memset (pOutBuffer, *pInBuffer++, cNum) ; // memset func will check "Num" =? 0
            pOutBuffer += cNum ;
            nInSize-- ;
        }
        else
            *pOutBuffer++ = byData ;
    }
rleOver:
    return (int)(pOutBuffer - pOutStart) ;
}
//-----------------------------------------------------------------------------
inline BYTE* FCCompress::RLE_TGA_EncodeLine (const BYTE* InBuffer, int iColorBit, int iNumPixel, BYTE* OutBuffer)
{
    iColorBit /= 8 ; // convert to bytes : 1,2,3,4
    while (iNumPixel > 0)
    {
        DWORD      Data = 0, Next = 0, Count = 1 ;
        FCColor::CopyPixel (&Data, InBuffer, iColorBit) ; // first pixel
        InBuffer += iColorBit ; iNumPixel-- ;
        while ((Count < 0x80) && (iNumPixel > 0)) // Stat. the repeat pixel
        {
            FCColor::CopyPixel (&Next, InBuffer, iColorBit) ; // next pixel
            if (Next != Data)
                break ;
            InBuffer += iColorBit ; iNumPixel-- ; Count++ ;
        }

        *OutBuffer++ = 0x80 | (BYTE)--Count ;
        FCColor::CopyPixel (OutBuffer, &Data, iColorBit) ;
        OutBuffer += iColorBit ;
    }
    return OutBuffer ;
}
//-----------------------------------------------------------------------------
inline BYTE* FCCompress::RLE_TGA_DecodeLine (const BYTE* InBuffer, int iColorBit, int iNumPixel, BYTE* OutBuffer)
{
    iColorBit /= 8 ; // convert to bytes : 1,2,3,4
    while (iNumPixel > 0)
    {
        const BYTE    byData = *InBuffer++ ; // Next Byte
        if (byData & 0x80) // Data >= 0x80
        {
            const int    nNum = (byData & 0x7F) + 1 ; // number of repeat pixel
            iNumPixel -= nNum ;
            for (int i=0 ; i < nNum ; i++, OutBuffer += iColorBit)
                FCColor::CopyPixel (OutBuffer, InBuffer, iColorBit) ;
            InBuffer += iColorBit ;
        }
        else
        {
            // copy directly
            const int   n = byData + 1, // non-repeat pixel
                        nByte = n * iColorBit ; // calculate copy bytes
            iNumPixel -= n ;
            ::memcpy (OutBuffer, InBuffer, nByte) ;
            OutBuffer += nByte ;
            InBuffer += nByte ;
        }
    }
    assert (iNumPixel == 0) ;
    return const_cast<BYTE*>(InBuffer) ;
}

#endif
