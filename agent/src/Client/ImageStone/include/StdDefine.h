#ifndef __PCL_STANDARD_DEFINE_HEADER_H__
#define __PCL_STANDARD_DEFINE_HEADER_H__

//=============================================================================
// compiler
//=============================================================================
#ifdef _MSC_VER
    #pragma warning (disable : 4786) // identifier was truncated to '255' characters in the browser information

    // warning during compile
    #define chSTR2(x)       #x
    #define chSTR(x)    chSTR2(x)
    #define fooWARN(desc) message(__FILE__ "(" chSTR(__LINE__) "):" #desc)
/*
    // must open RTTI switch
    #ifndef _CPPRTTI
        #error You must open RTTI switch in your VC6/7 project !!!
    #endif
*/
#else
    #define fooWARN(desc)  do{}while(false)
#endif

//=============================================================================
// namespace macro
//=============================================================================
#ifdef IMAGESTONE_USE_NAMESPACE
    #define IMAGESTONE_NAMESPACE_START   namespace IMAGESTONE{
    #define IMAGESTONE_NAMESPACE_END     } // end of namespace
#else
    #define IMAGESTONE_NAMESPACE_START
    #define IMAGESTONE_NAMESPACE_END
#endif

//=============================================================================
// basic function
//=============================================================================
template<class T> inline const T& FMax (const T& _X, const T& _Y) {return (_X < _Y ? _Y : _X);}
template<class T> inline const T& FMin (const T& _X, const T& _Y) {return (_Y < _X ? _Y : _X);}
template<class T> inline void FSwap (T& t1, T& t2) { const T tmp=t1 ; t1=t2 ; t2=tmp ;}
template<class T> inline T FSquare (const T& nValue) {return nValue*nValue ;}
// bound in [tLow, tHigh]
template<class T> inline T FClamp (const T& tValue, const T& tLow, const T& tHigh) {return FMax (tLow, FMin (tHigh, tValue)) ;}
inline int FClamp0255 (int nValue) {return FClamp (nValue, 0, 0xFF) ;}
// round double to int
inline int FRound (const double& x)
 {
    if (x > 0.0)
        return (int)(x + 0.5) ;
    else
        return (int)(x - 0.5) ;
}
#define   LIB_PI    3.1415926535897932384626433832795
#define   LIB_2PI   (2.0*LIB_PI)
/// angle ==> radian
inline double AngleToRadian (int nAngle) {return LIB_PI * nAngle / 180.0;}
/// radian ==> angle
inline int RadianToAngle (double dRadian) {return (int)(180.0 * dRadian / LIB_PI);}
// hypotenuse, c2 = a2 + b2
template<class T> inline T FHypot (const T& x, const T& y) {
    return (T)sqrt (double(x*x) + double(y*y)) ;
}

//=============================================================================
// include header
//=============================================================================
#ifdef WIN32
	#include <windows.h>
    #include <comdef.h>
    #include <TCHAR.H>

    #ifdef PCL_3RD_LIBRARY_USE_GDIPLUS
        // define for GDI+
        #if _MSC_VER > 1200
            // for VC7 and latter
            #include <GdiPlus.h>
            #pragma comment (lib, "GdiPlus.lib")
        #else
            // for VC6
            #define ULONG_PTR ULONG
            #include "../lib/gdiplus/Include/gdiplus.h"
        #endif
    #endif // PCL_3RD_LIBRARY_USE_GDIPLUS

#else
    #include "FLib_Macro.h"
#endif // WIN32

#ifdef PCL_3RD_LIBRARY_USE_FREEIMAGE
    #include "../lib/FreeImage/Dist/FreeImage.h"
#endif // PCL_3RD_LIBRARY_USE_FREEIMAGE

#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <float.h>
#include <memory.h>
#include <string.h>
#include <string>
#include <sstream>
#include <fstream>
#include "PCL_array.h"
#include "PCL_TT_Convertor.h"
#include "PCL_interface_composite.h"
#include "PCL_interface_lazyobj.h"
#include "PCL_interface_undo.h"
#include "FTimeCount.h"
#include "ObjProgress.h"

//=============================================================================
// avoid memory big/little endian
//=============================================================================
#define   PCL_R(p)   (((RGBQUAD*)(p))->rgbRed)
#define   PCL_G(p)   (((RGBQUAD*)(p))->rgbGreen)
#define   PCL_B(p)   (((RGBQUAD*)(p))->rgbBlue)
#define   PCL_A(p)   (((RGBQUAD*)(p))->rgbReserved)
template<class T> inline RGBQUAD PCL_RGBA (T r, T g, T b, T a=0xFF)
{
    RGBQUAD   cr ;
    PCL_R(&cr)=r ; PCL_G(&cr)=g ; PCL_B(&cr)=b ; PCL_A(&cr)=a ;
    return cr ;
}

//=============================================================================
//	结构
//=============================================================================
struct POINT_F // 浮点数POINT
{
    double   x ;
    double   y ;
};

//=============================================================================
inline long RECTWIDTH(const RECT & fRect) {return fRect.right - fRect.left;}
inline long RECTHEIGHT(const RECT & fRect) {return fRect.bottom - fRect.top;}
// 两点距离的平方
inline int FSquarePointDistance (const POINT& pt1, const POINT& pt2) {
    return FSquare(pt1.x - pt2.x) + FSquare(pt1.y - pt2.y) ;
}
// 三点夹角 [0，n]
inline double FPointAngle (const POINT& pt1, const POINT& pt2, const POINT& ptCenter) {
    const int   nPt1 = FSquarePointDistance (pt1, ptCenter),
                nPt2 = FSquarePointDistance (pt2, ptCenter) ;
	if ((nPt1 == 0) || (nPt2 == 0))
        return -LIB_2PI ; // pt1/pt2 == center

    const int   nTemp = nPt1 + nPt2 - FSquarePointDistance (pt1, pt2) ;
	return acos (nTemp / (2.0*sqrt((double)nPt1)*sqrt((double)nPt2))) ;
}
// 三点夹角 [0，2n]，pt1 --> pt2 的顺时针方向
inline double  FPointAngleClockwise (const POINT &pt1, const POINT &pt2, const POINT &ptCenter) {
    const double   fAngle = FPointAngle (pt1, pt2, ptCenter) ;
	if (fAngle < 0.0) // 有一点和center重合
		return fAngle ;
    if (pt1.x == ptCenter.x) // 垂直情况
        return (pt2.x >= ptCenter.x) ? fAngle : (LIB_PI - fAngle) ;
	// 判断在直线上还是下
	const double   yLine = ptCenter.y + (pt2.x - ptCenter.x) * (ptCenter.y - pt1.y) / (double)(ptCenter.x - pt1.x) ;
    if (pt2.y > yLine)
        return (pt1.x >= ptCenter.x) ? fAngle : (LIB_2PI - fAngle) ;
    return (pt1.x >= ptCenter.x) ? (LIB_2PI - fAngle) : fAngle ;
}
// pt1绕ptCenter顺时针旋转 [0, 2n] 后的坐标
inline POINT FClockwisePoint (POINT pt1, POINT_F ptCenter, double fAngle)
{
    double   dx = pt1.x - ptCenter.x, dy = -pt1.y + ptCenter.y,
             cost = cos(fAngle), sint = sin(fAngle) ;
    POINT    pt = {FRound(ptCenter.x + (dx*cost + dy*sint)),
                   FRound(ptCenter.y - (dy*cost - dx*sint))} ;
    return pt ;
}
// pt1绕ptCenter顺时针旋转 [0, 2n] 后的坐标
inline POINT FClockwisePoint (POINT pt1, POINT ptCenter, double fAngle) {

    POINT_F   ptCen = {ptCenter.x, ptCenter.y} ;
    return FClockwisePoint (pt1, ptCen, fAngle) ;
}
inline bool  IsRectInRect (const RECT & rcOut, const RECT & rcIn) {
	return (rcIn.left >= rcOut.left) && (rcIn.top >= rcOut.top) && (rcIn.right <= rcOut.right) && (rcIn.bottom <= rcOut.bottom) ;
}
//=============================================================================
//	坐标系统
//=============================================================================
enum AXIS_SYS
{
	AXIS_X,
	AXIS_Y,
	AXIS_Z,
} ;
//=============================================================================
//	平面8个方向
//=============================================================================
enum DIRECT_SYS
{
	DIRECT_TOP_LEFT,
	DIRECT_TOP,
	DIRECT_TOP_RIGHT,
	DIRECT_LEFT,
	DIRECT_RIGHT,
	DIRECT_BOTTOM_LEFT,
	DIRECT_BOTTOM,
	DIRECT_BOTTOM_RIGHT,
} ;
//=============================================================================
//	渐变过渡类型
//=============================================================================
enum REPEAT_MODE
{
	REPEAT_NONE = 0,
	REPEAT_SAWTOOTH = 1, // 锯齿波重复
	REPEAT_TRIANGULAR = 2, // 三角波重复
} ;
//=============================================================================
//	插值模式
//=============================================================================
enum INTERPOLATION_TYPE
{
    INTERPOLATION_NONE,
    INTERPOLATION_BILINEAR,
} ;
//=============================================================================
//	逻辑操作
//=============================================================================
enum LOGICAL_OP
{
    LOGI_AND,   // c = a & b
    LOGI_OR,    // c = a | b
    LOGI_XOR,   // c = a ^ b
    LOGI_ADD,   // c = a + b
    LOGI_SUB,   // c = a - b
    LOGI_MUL,   // c = a * b
    LOGI_DIV,   // c = a / b
    LOGI_LOG,   // c = log(a)
    LOGI_EXP,   // c = exp(a)
    LOGI_SQRT,  // c = sqrt(a)
    LOGI_TRIG,  // c = sin/cos/tan(a)
    LOGI_INVERT,// c = (2B - 1) - a

    LOGI_SEL_ADD, // 用在区域处理里
    LOGI_SEL_SUB, // 用在区域处理里
} ;
//=============================================================================
//	16 位色的掩码
//=============================================================================
#define		MASK16_RED_565			0xF800
#define		MASK16_GREEN_565		0x07E0
#define		MASK16_BLUE_565			0x001F
#define		MASK16_RED_555			0x7C00
#define		MASK16_GREEN_555		0x03E0
#define		MASK16_BLUE_555			0x001F
//=============================================================================
//  图像文件格式
//=============================================================================
enum IMAGE_TYPE
{
    IMG_UNKNOW,
    IMG_BMP,
    IMG_PCX,
    IMG_JPG,
    IMG_GIF,
    IMG_TGA,
    IMG_TIF,
    IMG_PNG,
    IMG_PSD,
    IMG_ICO,
    IMG_XPM,
    IMG_PHOXO,
    IMG_CUSTOM,
} ;
//=============================================================================
//	RGBA通道，这样的定义值是为了mask
//=============================================================================
enum IMAGE_CHANNEL
{
	CHANNEL_RED   = 1 << 0,// 0x01,
	CHANNEL_GREEN = 1 << 1,// 0x02,
	CHANNEL_BLUE  = 1 << 2,// 0x04,
	CHANNEL_ALPHA = 1 << 3,// 0x08,
	CHANNEL_RGB   = CHANNEL_RED|CHANNEL_GREEN|CHANNEL_BLUE,// 0x07,
	CHANNEL_RGBA  = CHANNEL_RGB|CHANNEL_ALPHA,// 0x0F,
	CHANNEL_GRAY  = 1 << 4,// 0x10
} ;
//=============================================================================
// 线样式
//=============================================================================
enum LINE_STYLE
{
    LINE_STYLE_SOLID = 0,
    LINE_STYLE_DASH = 1,
    LINE_STYLE_DOT = 2,
} ;
//=============================================================================
// 色调区域（这三个值的顺序一定不能变）
//=============================================================================
enum TONE_REGION
{
    /// shadow region of image.
    TONE_SHADOWS = 0,
    /// midtone region of image.
    TONE_MIDTONES = 1,
    /// highlight region of image.
    TONE_HIGHLIGHTS = 2,
};
//=============================================================================
// 阴影数据结构
//=============================================================================
struct SHADOWDATA
{
    int       nSmooth ; // 模糊度
    RGBQUAD   crShadow ; // 颜色/硬度
    int       nAlpha ; // 透明度
    int       nOffsetX ; // X偏移
    int       nOffsetY ; // Y偏移

    SHADOWDATA()
    {
        nOffsetX = nOffsetY = 5 ;
        crShadow = PCL_RGBA(75,75,75) ;
        nAlpha = 75 ;
        nSmooth = 5 ;
    }
};

//=============================================================================

#endif
