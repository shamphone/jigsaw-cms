#pragma once

// WebLauncher.h : WebLauncher.DLL 的主头文件

#if !defined( __AFXCTL_H__ )
#error include 'afxctl.h' before including this file
#endif

#include "resource.h"       // 主符号


// CWebLauncherApp : 有关实现的信息，请参阅 WebLauncher.cpp。

class CWebLauncherApp : public COleControlModule
{
public:
	BOOL InitInstance();
	int ExitInstance();
};

extern const GUID CDECL _tlid;
extern const WORD _wVerMajor;
extern const WORD _wVerMinor;

