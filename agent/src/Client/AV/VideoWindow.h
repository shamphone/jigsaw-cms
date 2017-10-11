#pragma once

#include "../../Video/DrawDIB/DrawDIB.h"

struct VideoWindow
{
    HWND hWnd;
    CDrawDIB hDrawDIB;
};

typedef map<__int64, VideoWindow*> VideoWindowMap;

