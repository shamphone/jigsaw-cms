/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-4-2
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_INTERFACE_IMAGEHANDLE__2005_04_02__H__
#define __PCL_INTERFACE_IMAGEHANDLE__2005_04_02__H__
#include "../StdDefine.h"
class FCObjImage ; // external class

class FCImageHandleBase ;

//=============================================================================
/**
 *  Interface of image handle.
 *  Used by FCObjImage::Load function, to implement read/write image file.
 */
class FCImageHandleBase
{
public:
    /**
     *  Read image from file.
     *  provide a default implement : call LoadImageMemory after load file into memory
     */
    virtual bool LoadImageFile (const char* szFileName) ;

    /// Read image from memory.
    virtual bool LoadImageMemory (const BYTE* pStart, int nFileSize) {return false;}

    /// Save image to file.
    /// User implement save function stored in m_SaveImg.
    virtual bool SaveImage (const char* szFileName, int nFlag = -1) {return false;}

    /// Get loaded image list (such as .gif).
    /// @param bWantBeOwnner : true - the handle will throw the ownership of these image, you must delete youself.
    virtual std::deque<FCObjImage*> GetLoadImage (bool bWantBeOwnner) =0 ;

    /// Add image object to save list.
    void PushSaveList (const FCObjImage* pImg) {m_SaveImg.push_back(pImg);}

    virtual ~FCImageHandleBase() {}
protected:
    /// Image list to be saved.
    std::deque<const FCObjImage*>   m_SaveImg ;
} ;

//=============================================================================
// inline Implement
//=============================================================================
inline bool FCImageHandleBase::LoadImageFile (const char* szFileName)
{
    std::ifstream     inFile (szFileName, std::ios::in|std::ios::binary) ;
    if (!inFile.is_open())
        return false ;

    // get file length
    inFile.seekg (0, std::ios::end) ;
    const int     nFileSize = inFile.tellg() ;
    if (nFileSize <= 0)
        return false ;

    // read file into memory
    PCL_array<BYTE>   pStart (nFileSize) ;
    inFile.seekg (0, std::ios::beg) ;
    inFile.read ((char*)pStart.get(), nFileSize) ;
    return LoadImageMemory (pStart.get(), nFileSize) ;
}

#endif
