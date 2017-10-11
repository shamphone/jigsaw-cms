#pragma once

// Capture C style memory allocation
#ifdef _DEBUG
#define _CRTDBG_MAP_ALLOC
#endif

// Modify the following defines if you have to target a platform prior to the ones specified below.
// Refer to MSDN for the latest info on corresponding values for different platforms.
#include "Common\Common\Version\Version.h"

#define VC_EXTRALEAN		// Exclude rarely-used stuff from Windows headers

#include <afxwin.h>         // MFC core and standard components
#include <afxext.h>         // MFC extensions
#include <afxdisp.h>        // MFC Automation classes
#include <afxdtctl.h>		// MFC support for Internet Explorer 4 Common Controls
#include <afxdhtml.h>

#ifndef _AFX_NO_AFXCMN_SUPPORT
#include <afxcmn.h>			// MFC support for Windows Common Controls
#endif // _AFX_NO_AFXCMN_SUPPORT
#include <afxrich.h>		// MFC rich edit classes
#include <afxodlgs.h>
#include <afxpriv.h>
#include <afxdlgs.h>

#include <afxtempl.h>
#include <afxcview.h>

#include <afxmt.h>          // MFC Synchronization support

// Windows Header 
#include <winsock2.h>
#include <mmsystem.h>
#include <Richole.h>
#include <vfw.h>
//using imagestone lib
#include "ImageStone\ImageStone.h"

// C-Runtime library header
#include <stdlib.h>
#include <process.h>
#include <ctype.h>
#include <memory.h>
#include <math.h>
#include <direct.h>
#include <io.h>

// STL Header.
#pragma warning(push,3)
#include <vector>
#include <string>
#include <list>
#include <deque>
#include <map>
#include <set>
#include <algorithm>
#pragma warning(pop)
using namespace std;

// UserID is 64 bit integer in this system
#define USERID __int64

// Capture C++ style memory allocation
// This may confilict with customized new operator
#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// Define safe delete operator
#define SAFE_DELETE(p)       { if(p) { delete (p);     (p)=NULL; } }

// Using Log In Debug version
#ifdef _DEBUG
#define USING_FVS_DEBUG
#endif

