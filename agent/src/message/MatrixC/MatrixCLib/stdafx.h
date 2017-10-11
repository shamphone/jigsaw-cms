// stdafx.h : 标准系统包含文件的包含文件，
// 或是常用但不常更改的项目特定的包含文件
//

#pragma once

#include "..\..\..\Common\Common\Version\Version.h"

// Windows headers
#define WIN32_LEAN_AND_MEAN		// 从 Windows 头中排除极少使用的资料
#include <winsock2.h>
#include <windows.h>

// C Runtime headers
#include <stdio.h>
#include <process.h>
#include <crtdbg.h>
#include <time.h>

// STL headers
#include <string>
#include <list>
#include <map>
using namespace std;
