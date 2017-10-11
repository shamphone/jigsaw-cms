/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2003-3-30
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_OBJECT_BASE__2003_03_30__H__
#define __PCL_OBJECT_BASE__2003_03_30__H__
#include "StdDefine.h"

class FCObject ;
    class FCObjGraph ;

//=============================================================================
/**
 *  The root of all objects
 */
class FCObject
{
public:
    virtual ~FCObject() {}
    /**
     *  Save object to memory / load object from memory.
     *    @param bSave : save(true) & load(false)
     *    @return return written/loaded bytes.
     */
    virtual int Serialize (bool bSave, BYTE* pSave) {return 0;}
};

//=============================================================================
/**
 *  Basic graphic object (encapsulate object's coordinate).
 */
class FCObjGraph : public FCObject
{
public:
    FCObjGraph() {m_ptObj.x = m_ptObj.y = 0;}
    FCObjGraph& operator= (const FCObjGraph& GraphObj) {m_ptObj = GraphObj.m_ptObj; return *this;}

    /**
     *  Save graphic object to memory / load graphic object from memory.
     *  @see FCObject::Serialize
     */
    virtual int Serialize (bool bSave, BYTE* pSave)
    {
        bSave ? (*(POINT*)pSave = m_ptObj) : (m_ptObj = *(POINT*)pSave) ;
        return sizeof(m_ptObj) ;
    }

    /**
     *  @name Object's position
     *  object's position on canvas.
     */
    //@{
    /// Set position of graph.
    void SetGraphObjPos (int xPos, int yPos) {m_ptObj.x=xPos; m_ptObj.y=yPos;}
    /// Set position of graph.
    void SetGraphObjPos (const POINT& pos) {m_ptObj = pos;}
    /// Get position of graph.
    POINT GetGraphObjPos() const {return m_ptObj;}
    /// Offset position of graph.
    void OffsetGraphObj (int xOff, int yOff) {m_ptObj.x += xOff; m_ptObj.y += yOff;}
    //@}

    /**
     *  @name Coordinate transformation
     *  canvas <==> layer
     */
    //@{
    /// canvas ==> layer
    void Canvas_to_Layer (POINT& pt) const
    {
        pt.x -= m_ptObj.x ; pt.y -= m_ptObj.y ;
    }
    void Canvas_to_Layer (RECT& rc) const
    {
        rc.left -= m_ptObj.x ;
        rc.top -= m_ptObj.y ;
        rc.right -= m_ptObj.x ;
        rc.bottom -= m_ptObj.y ;
    }

    /// layer ==> canvas
    void Layer_to_Canvas (POINT& pt) const
    {
        pt.x += m_ptObj.x ; pt.y += m_ptObj.y ;
    }
    void Layer_to_Canvas (RECT& rc) const
    {
        rc.left += m_ptObj.x ;
        rc.top += m_ptObj.y ;
        rc.right += m_ptObj.x ;
        rc.bottom += m_ptObj.y ;
    }
    //@}

private:
    POINT     m_ptObj ; // object position on canvas
};

//=============================================================================
// inline implement
//=============================================================================

#endif
