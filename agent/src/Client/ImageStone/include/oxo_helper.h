/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-6-26
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_PHOXO_HELPER__2004_06_26__H__
#define __PCL_PHOXO_HELPER__2004_06_26__H__
#include "StdDefine.h"

//=============================================================================
/**
 *  Some helper funtion.
 */
class FCOXOHelper
{
public:
    /**
     *  @name Memory helper.
     */
    //@{
    /// Allocate memory with initialized to zero.
    static BYTE* ZeroMalloc (int nBytes) ;
    /// Free memory alloc by ZeroMalloc.
    static void ZeroFree (void* pPixel) ;
    //@}

    /**
     *  @name File helper.
     */
    //@{
    /// Save buffer to file.
    static bool SaveBufferToFile (const char* szFilename, const char* pBuffer, int nLength) ;
    /// Load file to memory (you must use <B>delete[]</B> to free returned pBuffer).
    static void LoadFileToBuffer (const char* szFilename, char*& pBuffer, int& nLength) ;
    //@}

    /**
     *  @name String convert helper.
     */
    //@{
    /// int/long/float/double ==> ASCII
    template<class T> static std::string X2A (const T& tNumber, int nWidth=0)
    {
        std::ostringstream     sOutStream ;
        sOutStream.width (nWidth) ;
        sOutStream.fill ('0') ;
        sOutStream << tNumber ;
        return sOutStream.str() ;
    }
    /// ASCII ==> int/long/float/double
    template<class T> static void A2X (const std::string& strNumber, T& tResult)
    {
        tResult = (T)0 ;
        std::stringstream     sTranslation ;
        sTranslation << strNumber ;
        sTranslation >> tResult ;
    }
    /// URL encode.
    static std::string EncodeURL (const char* pBufDummy, int nLen)
    {
        const unsigned char  * pBuf = (const unsigned char*)pBufDummy ;
        static std::string   s_HexTable = "0123456789ABCDEF",
                             s_UnsafeTable = "\"<>%\\^[]`+$,@:;/!#?=&" ;
        std::string   s ;
        for (int i=0 ; i < nLen ; i++)
            if ((pBuf[i] > 32) && (pBuf[i] < 123) && (s_UnsafeTable.find(pBuf[i]) == std::string::npos))
            {
                s += pBuf[i] ;
            }
            else
            {
                s += "%" ;
                s += s_HexTable[pBuf[i] / 16] ;
                s += s_HexTable[pBuf[i] % 16] ;
            }
        return s ;
    }
    //@}

    /// Get filename's ext name.
    static std::string GetFileExt (const char* pFile) ;

    /**
     *  @name File type.
     */
    //@{
    /// Load Photoshop's .acf file.
    static bool LoadPhotoshopACF (const char* szFileName, std::deque<int>& listElem) ;
    /// Save Photoshop's .acf file.
    static bool SavePhotoshopACF (const char* szFileName, const std::deque<int>& listElem) ;
    //@}

    /// Output is from small to big.
    static void BubbleSort (int pArray[], int iNumElement) ;

private:
    static int __PS_WORD2INT (const BYTE* pWord) ;
};

//=============================================================================
// inline Implement
//=============================================================================
inline BYTE* FCOXOHelper::ZeroMalloc (int nBytes)
{
#ifdef WIN32

    // BoundChecker can's check the memory alloc by <VirtualAlloc>
    #ifdef _DEBUG
        BYTE   * pByte = (BYTE*)malloc (nBytes) ;
        memset (pByte, 0, nBytes) ;
        return pByte ;
    #else
        return (BYTE*)VirtualAlloc (NULL, nBytes, MEM_RESERVE|MEM_COMMIT, PAGE_READWRITE) ;
    #endif

#else

        BYTE   * pByte = (BYTE*)malloc (nBytes) ;
        memset (pByte, 0, nBytes) ;
        return pByte ;

#endif
}
//-----------------------------------------------------------------------------
inline void FCOXOHelper::ZeroFree (void* pPixel)
{
    if (!pPixel)
        return ;

#ifdef WIN32

    #ifdef _DEBUG
        free (pPixel) ;
    #else
        ::VirtualFree (pPixel, 0, MEM_RELEASE) ;
    #endif

#else

        free (pPixel) ;

#endif
}
//-----------------------------------------------------------------------------
inline bool FCOXOHelper::SaveBufferToFile (const char* szFilename, const char* pBuffer, int nLength)
{
    std::ofstream   outFile (szFilename, std::ios::out|std::ios::binary|std::ios::trunc) ;
    if (!outFile.is_open())
        {assert(false); return false;}

    outFile.write (pBuffer, nLength) ;
    return true ;
}
//-----------------------------------------------------------------------------
inline void FCOXOHelper::LoadFileToBuffer (const char* szFilename, char*& pBuffer, int& nLength)
{
    pBuffer=NULL; nLength=0;
    std::ifstream     inFile (szFilename, std::ios::in|std::ios::binary) ;
    if (!inFile.is_open())
        {assert(false); return;}

    // get file length
    inFile.seekg (0, std::ios::end) ;
    nLength = inFile.tellg() ;

    // read file into memory
    pBuffer = new char[nLength + 8] ;
    memset (pBuffer, 0, nLength + 8) ;
    inFile.seekg (0, std::ios::beg) ;
    inFile.read (pBuffer, nLength) ;
}
//-----------------------------------------------------------------------------
inline std::string FCOXOHelper::GetFileExt (const char* pFile)
{
    if (!pFile) {assert(false); return "";}

    std::string     strFile(pFile), strOut ;
    const int       nPos = (int)strFile.find_last_of (".") ;
    if (nPos != std::string::npos)
        strOut = strFile.substr (nPos + 1) ;
    return strOut ;
}
//-----------------------------------------------------------------------------
inline int FCOXOHelper::__PS_WORD2INT (const BYTE* pWord)
{
    BYTE   bySwap[2] = {pWord[1], pWord[0]} ;
    return *(short*)bySwap ;
}
//-----------------------------------------------------------------------------
inline bool FCOXOHelper::LoadPhotoshopACF (const char* szFileName, std::deque<int>& listElem)
{
    listElem.clear() ;
    std::ifstream     inFile (szFileName, std::ios::in|std::ios::binary) ;
    if (!inFile.is_open())
        return false ;

    // get file's size
    inFile.seekg (0, std::ios::end) ;
    const int     nFileSize = inFile.tellg() ;
    if (nFileSize != 54) // 27.elem * 2.bytes
        return false ;

    // read file into memory.
    PCL_array<short>   pStart (new BYTE[nFileSize]) ;
    inFile.seekg (0, std::ios::beg) ;
    inFile.read ((char*)pStart.get(), nFileSize) ;

    // 5 x 5 filter & scale & offset
    for (int i=0 ; i < 25 ; i++)
    {
        listElem.push_back (__PS_WORD2INT((BYTE*)&pStart[i])) ;
    }
    listElem.push_back (__PS_WORD2INT((BYTE*)&pStart[25])) ;
    listElem.push_back (__PS_WORD2INT((BYTE*)&pStart[26])) ;
    return true ;
}
//-----------------------------------------------------------------------------
inline bool FCOXOHelper::SavePhotoshopACF (const char* szFileName, const std::deque<int> & listElem)
{
    if (listElem.size() != 27)  {assert(false); return false;}

    // create file
    std::ofstream     outFile (szFileName, std::ios::out|std::ios::binary|std::ios::trunc) ;
    if (!outFile.is_open())
        return false ;

    BYTE    pElem[2] ;
    for (int i=0 ; i < (int)listElem.size() ; i++)
    {
        *(short*)pElem = (short)listElem[i] ;
        FSwap (pElem[0], pElem[1]) ;
        outFile.write ((char*)pElem, 2) ;
    }
    return true ;
}
//-----------------------------------------------------------------------------
inline void FCOXOHelper::BubbleSort (int pArray[], int iNumElement)
{
    if (!pArray)
        {assert(false); return;}

    for (int i = iNumElement-1 ; i > 0 ; i--)
    {
        bool     bFlag = true ;
        for (int j = 0 ; j < i ; j++)
            if (pArray[j] > pArray[j + 1])
            {
                FSwap (pArray[j], pArray[j+1]) ;
                bFlag = false ;
            }
        if (bFlag)
            break ;
    }
}

#endif
