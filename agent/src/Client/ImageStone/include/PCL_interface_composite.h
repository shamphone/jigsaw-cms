/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-1-23
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_INTERFACE_COMPOSITE__2005_01_23__H__
#define __PCL_INTERFACE_COMPOSITE__2005_01_23__H__
#include <assert.h>
#include <deque>
#include <algorithm>

template<class T> class PCL_Interface_Composite ;

//=============================================================================
/**
    composite support.
    enable your class contain composite object, for example:
@code
class CMultiImage : public CObject,
                    private PCL_Interface_Composite<CImage>
{
    int GetImageCount() const { return PCL_GetObjectCount(); }
    CImage* GetImage (int nIndex) const { return PCL_GetObject(nIndex); }
}
@endcode
*/
template<class T>
class PCL_Interface_Composite
{
public:
    /// Deconstructor will call all PCL_DeleteAllObjects to delete all objects.
    virtual ~PCL_Interface_Composite() {PCL_DeleteAllObjects();}

    /// Get number of object.
    int PCL_GetObjectCount() const {return (int)m_objList.size();}

    /**
     *  Add an object (created by new) into list (add a NULL object is permitted).
     *  after the object be added, you can't delete it any more.
     */
    void PCL_PushObject (T* pObj) {m_objList.push_back (pObj);} // push NULL is permitted

    /**
     *  Insert an object (created by new) into list (add a NULL object is permitted).
     *  after the object be added, you can't delete it any more.
     */
    void PCL_InsertObject (int nIndex, T* pObj)
    {
        if ((nIndex >= 0) && (nIndex <= PCL_GetObjectCount()))
            m_objList.insert (m_objList.begin()+nIndex, pObj) ; // push NULL is permitted
        else
            {assert(false);}
    }

    /// Get object in list, nIndex is a zero-based index.
    T* PCL_GetObject (int nIndex) const
    {
        if ((nIndex >= 0) && (nIndex < PCL_GetObjectCount()))
            return m_objList[nIndex] ;
        else
            {assert(false); return 0;}
    }

    /// Whether the object has been added in list.
    bool PCL_HasInList (const T* pObj) const
    {
        return (std::find (m_objList.begin(), m_objList.end(), pObj) != m_objList.end()) ;
    }

    /// Throw objects' ownership. you must delete object yourself.
    void PCL_ThrowOwnership (std::deque<T*>& listObj)
    {
        listObj = m_objList ;
        m_objList.clear() ;
    }

    /// Delete all objects in list.
    void PCL_DeleteAllObjects()
    {
        while (!m_objList.empty())
        {
            T   * p = m_objList.back() ;
            m_objList.pop_back() ;
            if (p)
                delete p ;
        }
    }

    /// Remove & delete object from list.
    void PCL_DeleteObject (int nIndex)
    {
        if ((nIndex >= 0) && (nIndex < PCL_GetObjectCount()))
        {
            T   * p = m_objList[nIndex] ;
            m_objList.erase(m_objList.begin() + nIndex) ;
            if (p)
                delete p ;
        }
        else
            {assert(false);}
    }

    /// Delete object in list.
    void PCL_DeleteObject (T* pObj)
    {
        if (std::find (m_objList.begin(), m_objList.end(), pObj) != m_objList.end())
        {
            m_objList.erase (std::find (m_objList.begin(), m_objList.end(), pObj)) ;
            if (pObj)
                delete pObj ;
        }
        assert (std::find (m_objList.begin(), m_objList.end(), pObj) == m_objList.end()) ;
    }

private:
    std::deque<T*>   m_objList ;
} ;

//=============================================================================
// inline implement
//=============================================================================

#endif
