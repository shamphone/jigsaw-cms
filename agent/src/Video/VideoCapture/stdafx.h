// stdafx.h : 标准系统包含文件的包含文件，
// 或是常用但不常更改的项目特定的包含文件
//

#pragma once

#include "..\..\Common\Common\Version\Version.h"

#define _ATL_CSTRING_EXPLICIT_CONSTRUCTORS	// 某些 CString 构造函数将为显式的

#ifndef VC_EXTRALEAN
#define VC_EXTRALEAN		// 从 Windows 头中排除极少使用的资料
#endif

#include <afx.h>
#include <afxwin.h>         // MFC 核心组件和标准组件
#include <afxext.h>         // MFC 扩展

// DirectX Header
#include <dshow.h>
#include <initguid.h>

// C Runtime header
#include <io.h>
#include <tchar.h>

// STL Header
#include <list>
#include <algorithm>
using namespace std;
