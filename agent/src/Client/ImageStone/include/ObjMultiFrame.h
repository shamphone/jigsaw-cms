/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-8-31
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef	__PCL_OBJECT_MULTI_IMAGE__2004_08_31__H__
#define	__PCL_OBJECT_MULTI_IMAGE__2004_08_31__H__
#include "ObjImage.h"

//=============================================================================
/**
 *  Image container.
 */
class FCObjMultiFrame : public FCObject,
                        public PCL_Interface_Composite<FCObjImage>
{
public:
    /// Get image's number.
    int GetFrameCount() const {return PCL_GetObjectCount();}
    /// Get image.
    FCObjImage* GetFrame (int nFrame) const {return PCL_GetObject(nFrame);}

    /**
     *  @name Read/Write image file
     *  determine image type by file's ext name.
     */
    //@{
    bool Load (FCImageHandleBase& rHandler) ;
    bool Load (const char* szFileName) ;
    bool Load (BYTE* pStart, int iFileSize, IMAGE_TYPE imgType) ;
    //@}
};

//=============================================================================
// inline Implement
//=============================================================================
inline bool FCObjMultiFrame::Load (FCImageHandleBase& rHandler)
{
    std::deque<FCObjImage*>   imgList = rHandler.GetLoadImage(true) ;
    if (imgList.empty())
        return false ;

    for (int i=0 ; i < (int)imgList.size() ; i++)
        PCL_PushObject (imgList[i]) ;
    return true ;
}
//-----------------------------------------------------------------------------
inline bool FCObjMultiFrame::Load (BYTE* pStart, int iFileSize, IMAGE_TYPE imgType)
{
    std::auto_ptr<FCImageHandleBase>  pHandler (FCObjImage::GetImageHandleFactory()->CreateImageHandle(imgType)) ;
    if (!pHandler.get())
        return false ;

    bool     bRet = pHandler->LoadImageMemory (pStart, iFileSize) ;
    if (bRet)
        bRet = this->Load (*pHandler) ;
    assert (bRet) ;
    return bRet ;
}
//-----------------------------------------------------------------------------
inline bool FCObjMultiFrame::Load (const char* szFileName)
{
    IMAGE_TYPE     imgType = FCObjImage::GetImageHandleFactory()->QueryImageFileType(szFileName) ;
    std::auto_ptr<FCImageHandleBase>  pHandler (FCObjImage::GetImageHandleFactory()->CreateImageHandle(imgType)) ;
    if (!pHandler.get())
        return false ;

    bool     bRet = pHandler->LoadImageFile (szFileName) ;
    if (bRet)
        bRet = this->Load (*pHandler) ;
    assert (bRet) ;
    return bRet ;
}

#endif
