// stdafx.h : 标准系统包含文件的包含文件，
// 或是常用但不常更改的项目特定的包含文件
//

#pragma once

#include "..\..\Common\Common\Version\Version.h"

#define WIN32_LEAN_AND_MEAN		// 从 Windows 头中排除极少使用的资料

#include <windows.h>
#include <mmsystem.h>

#include <string>
using namespace std;

#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <io.h>
#include <process.h>
