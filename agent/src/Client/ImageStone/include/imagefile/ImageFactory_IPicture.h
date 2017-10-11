/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-9-12
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_IMAGEFACTORY_IPICTURE__2005_09_12__H__
#define __PCL_IMAGEFACTORY_IPICTURE__2005_09_12__H__
#include "ImageFactory_Mini.h"

#ifdef WIN32
#include "ImageHandle_IPicture.h"

//class FCImageHandleFactory ;
    class FCImageHandleFactory_IPicture ;

//=============================================================================
/**
 *  Read/Write image via WIN32 IPicture (<B>All WIN32</B>).
@verbatim
              BMP      TGA      Jpg      Gif
    Read       O        O        O        O
    Write      O        O        X        X
@endverbatim
 */
class FCImageHandleFactory_IPicture : public FCImageHandleFactory
{
protected:
    virtual FCImageHandleBase* CreateImageHandle (IMAGE_TYPE imgType)
    {
        switch (imgType)
        {
            case IMG_BMP : return new FCImageHandle_Bmp ;
            case IMG_TGA : return new FCImageHandle_Tga ;
            case IMG_JPG :
            case IMG_GIF : return new FCImageHandle_IPicture ;
        }
        return 0 ;
    }
    virtual ~FCImageHandleFactory_IPicture() {}
};

#endif// WIN32

inline FCImageHandleFactory* FCObjImage::__ManageImageHandleFactory (bool bGet, FCImageHandleFactory* pFactory)
{
#ifdef WIN32
    static std::auto_ptr<FCImageHandleFactory>   s_pFactory (new FCImageHandleFactory_IPicture) ;
#else
    static std::auto_ptr<FCImageHandleFactory>   s_pFactory (new FCImageHandleFactory_Mini) ;
#endif

    if (!bGet && pFactory)
        s_pFactory = std::auto_ptr<FCImageHandleFactory>(pFactory) ;
    return s_pFactory.get() ;
}

#endif // __PCL_IMAGEFACTORY_IPICTURE__2005_09_12__H__
