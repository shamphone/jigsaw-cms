/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-7-29
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifdef PCL_3RD_LIBRARY_USE_FREEIMAGE
#ifndef __PCL_IMAGEFACTORY_FREEIMAGE__2005_07_29__H__
#define __PCL_IMAGEFACTORY_FREEIMAGE__2005_07_29__H__
#include "Interface_ImageHandleFactory.h"
#include "ImageHandle_Bmp.h"
#include "ImageHandle_Tga.h"
#include "ImageHandle_FreeImage.h"

//class FCImageHandleFactory ;
    class FCImageHandleFactory_FreeImage ;

//=============================================================================
/**
 *  Read/Write image via FreeImage lib (<B>Need FreeImage lib</B>).
@verbatim
              BMP      TGA      Jpg      Gif      Tif      Png      Pcx      Ico      Xpm      Psd
    Read       O        O        O        O        O        O        O        O        O        O
    Write      O        O        O        O        O        O        O        O        O        X
@endverbatim
 */
class FCImageHandleFactory_FreeImage : public FCImageHandleFactory
{
protected:
	virtual FCImageHandleBase* CreateImageHandle (IMAGE_TYPE imgType)
    {
        switch (imgType)
        {
            case IMG_BMP : return new FCImageHandle_Bmp ;
            case IMG_TGA : return new FCImageHandle_Tga ;
            case IMG_GIF : return new FCImageHandle_FreeImage ;
            case IMG_PCX : return new FCImageHandle_FreeImage ;
            case IMG_PNG : return new FCImageHandle_FreeImage ;
            case IMG_TIF : return new FCImageHandle_FreeImage ;
            case IMG_JPG : return new FCImageHandle_FreeImage ;
            case IMG_ICO : return new FCImageHandle_FreeImage ;
            case IMG_XPM : return new FCImageHandle_FreeImage ;
            case IMG_PSD : return new FCImageHandle_FreeImage ;
        }
        return 0 ;
    }
    virtual ~FCImageHandleFactory_FreeImage() {}
};

//=============================================================================
// inline Implement
//=============================================================================

#endif
#endif // PCL_3RD_LIBRARY_USE_FREEIMAGE
