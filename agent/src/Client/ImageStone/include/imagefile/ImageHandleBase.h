/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2004-4-8
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGE_HANDLE__2004_04_08__H__
#define __PCL_IMAGE_HANDLE__2004_04_08__H__
#include "Interface_ImageHandle.h"
#include "../ObjImage.h"

class FCImageHandle ;

//=============================================================================
/**
 *  It's a middleman.
 */
class FCImageHandle : public FCImageHandleBase,
                      public PCL_Interface_Composite<FCObjImage>
{
public:
    virtual std::deque<FCObjImage*> GetLoadImage (bool bWantBeOwnner)
    {
        std::deque<FCObjImage*>   imgList ;
        if (bWantBeOwnner)
            PCL_ThrowOwnership (imgList) ;
        else
        {
            for (int i=0 ; i < PCL_GetObjectCount() ; i++)
                imgList.push_back (PCL_GetObject(i)) ;
        }
        return imgList ;
    }
} ;

//=============================================================================
// inline Implement
//=============================================================================

#endif
