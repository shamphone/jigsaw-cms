/*
 *   Copyright (C) =USTC= Fu Li
 *
 *   Author   :  Fu Li
 *   Create   :  2005-3-3
 *   Home     :  http://www.crazy-bit.com/
 *   Mail     :  crazybit@263.net
 *   History  :  
 */
#ifndef __PCL_TIME_COUNT__2005_03_03__H__
#define __PCL_TIME_COUNT__2005_03_03__H__
#include <time.h>

class FCTimeCount ;

//=============================================================================
/**
 *  Time counter. unit : millisecond
 */
class FCTimeCount
{
public:
    /// Constructor (will set start tag).
    FCTimeCount() {SetStartTag();}

    /// Set start tag.
    void SetStartTag() {m_nTick = ::clock();}
    /// Get passed millisecond from start tag.
    int GetPassMillisecond() const {return tick_to_ms(::clock() - m_nTick);}

    static int tick_to_ms (const clock_t& nTick)
    {
        return (int)(nTick * 1000 / CLOCKS_PER_SEC) ;
    }

private:
    clock_t   m_nTick ;
};

#endif
