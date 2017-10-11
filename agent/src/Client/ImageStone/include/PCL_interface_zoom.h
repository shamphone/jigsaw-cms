/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-2-27
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_INTERFACE_ZOOMSCALE__2005_02_27__H__
#define __PCL_INTERFACE_ZOOMSCALE__2005_02_27__H__
#include "StdDefine.h"

class PCL_Interface_ZoomScale ;

//=============================================================================
/**
 *  Zoom scale support.
 */
class PCL_Interface_ZoomScale
{
public:
    PCL_Interface_ZoomScale() : m_nScale(1) {}
    virtual ~PCL_Interface_ZoomScale() {}

    /** Set zoom scale. */
    void  SetZoomScale (int nScale) {m_nScale = nScale; assert(nScale != 0);}
    /** Get zoom scale. */
    int   GetZoomScale() const {return m_nScale;}

    /** @name Coordinate transformation
        scaled <==> actual
    */
    //@{
    /// Convert point.
    void Scaled_to_Actual (POINT& pt) const
    {
        if (m_nScale > 1)
        {
            pt.x /= m_nScale ;
            pt.y /= m_nScale ;
        }
        else
            if (m_nScale < -1)
            {
                pt.x *= -m_nScale ;
                pt.y *= -m_nScale ;
            }
    }
    /// Convert rect.
    void Scaled_to_Actual (RECT& rc) const
    {
        Scaled_to_Actual (*(POINT*)&rc.left) ;
        Scaled_to_Actual (*(POINT*)&rc.right) ;
    }

    /// Convert point.
    void Actual_to_Scaled (POINT& pt) const
    {
        if (m_nScale > 1)
        {
            pt.x *= m_nScale ;
            pt.y *= m_nScale ;
        }
        else
            if (m_nScale < -1)
            {
                pt.x /= -m_nScale ;
                pt.y /= -m_nScale ;
            }
    }
    /// Convert rect.
    void Actual_to_Scaled (RECT& rc) const
    {
        Actual_to_Scaled (*(POINT*)&rc.left) ;
        Actual_to_Scaled (*(POINT*)&rc.right) ;
    }
    //@}

private:
    int     m_nScale ;
} ;


//=============================================================================
// inline implement
//=============================================================================

#endif
