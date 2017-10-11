/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2006-2-21
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifdef PCL_3RD_LIBRARY_USE_IJL
#ifndef __PCL_IMAGEFACTORY_IJL15__2006_02_21__H__
#define __PCL_IMAGEFACTORY_IJL15__2006_02_21__H__
#include "Interface_ImageHandleFactory.h"
#include "ImageHandle_Bmp.h"
#include "ImageHandle_Tga.h"
#include "ImageHandle_IJL15.h"

//class FCImageHandleFactory ;
    class FCImageHandleFactory_IJL15 ;

//=============================================================================
/**
 *  Read/Write jpeg image via IJL1.5.
@verbatim
              BMP      TGA      Jpg
    Read       O        O        O 
    Write      O        O        O 
@endverbatim
 */
class FCImageHandleFactory_IJL15 : public FCImageHandleFactory
{
protected:
	virtual FCImageHandleBase* CreateImageHandle (IMAGE_TYPE imgType)
    {
        switch (imgType)
        {
            case IMG_BMP : return new FCImageHandle_Bmp ;
            case IMG_TGA : return new FCImageHandle_Tga ;
            case IMG_JPG : return new FCImageHandle_IJL15 ;
        }
        return 0 ;
    }
    virtual ~FCImageHandleFactory_IJL15() {}
};

//=============================================================================
// inline Implement
//=============================================================================

#endif
#endif // PCL_3RD_LIBRARY_USE_IJL
