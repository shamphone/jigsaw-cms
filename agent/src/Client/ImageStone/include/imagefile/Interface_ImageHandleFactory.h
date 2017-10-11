/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-6-21
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_INTERFACE_IMAGEHANDLE_FACTORY__2005_06_21__H__
#define __PCL_INTERFACE_IMAGEHANDLE_FACTORY__2005_06_21__H__
#include "../StdDefine.h"
class FCImageHandleBase ; // external class

class FCImageHandleFactory ;

//=============================================================================
/**
 *  Interface of image handler's factory.
 *  See FCObjImage::SetImageHandleFactory.
 */
class FCImageHandleFactory
{
public:
    /// Get image file's format by ext name.
    virtual IMAGE_TYPE QueryImageFileType (const char* szFileName)
    {
        if (!szFileName)  {assert(false); return IMG_UNKNOW;}

        std::string       strExt (FCOXOHelper::GetFileExt(szFileName)) ;
        PCL_array<char>   szConvert (new char[strExt.size() + 8]) ;
        memset (szConvert.get(), 0, strExt.size() + 8) ;
        memcpy (szConvert.get(), strExt.c_str(), strExt.length()) ;

        // convert to lowercase
        for (int i=0 ; i < (int)strExt.size() ; i++)
            szConvert[i] = tolower(szConvert[i]) ;

        PCL_TT_Convertor<IMAGE_TYPE, std::string>   imgTab ;
        imgTab.AddElement (IMG_JPG, "jpg") ;
        imgTab.AddElement (IMG_JPG, "jpeg") ;
        imgTab.AddElement (IMG_GIF, "gif") ;
        imgTab.AddElement (IMG_PNG, "png") ;
        imgTab.AddElement (IMG_BMP, "bmp") ;
        imgTab.AddElement (IMG_PCX, "pcx") ;
        imgTab.AddElement (IMG_TIF, "tif") ;
        imgTab.AddElement (IMG_TIF, "tiff") ;
        imgTab.AddElement (IMG_TGA, "tga") ;
        imgTab.AddElement (IMG_ICO, "ico") ;
        imgTab.AddElement (IMG_PSD, "psd") ;
        imgTab.AddElement (IMG_XPM, "xpm") ;
        imgTab.AddElement (IMG_PHOXO, "oxo") ;
        return imgTab.Second_to_First (szConvert.get(), IMG_UNKNOW) ;
    }

    /**
     *  Create image handler by image type.
     *  User must use <B>delete</B> to delete returned handler.
@verbatim
     you are high recommended to use std::auto_ptr wrap this returned pointer.
sample:
    IMAGE_TYPE     imgType = FCObjImage::GetImageHandleFactory()->QueryImageFileType(szFileName) ;
    std::auto_ptr<FCImageHandleBase>  pHandler (FCObjImage::GetImageHandleFactory()->CreateImageHandle(imgType)) ;
@endverbatim
     */
    virtual FCImageHandleBase* CreateImageHandle (IMAGE_TYPE imgType) =0 ;
    virtual ~FCImageHandleFactory() {}
};

//=============================================================================
// inline Implement
//=============================================================================

#endif
