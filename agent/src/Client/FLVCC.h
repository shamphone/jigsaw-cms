#pragma once

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif
#include "resource.h"       // main symbols

class CooperationManager;

class CFLVCCApp : public CWinApp
{
public:
	virtual BOOL InitInstance();
	virtual int ExitInstance();

	// 获取配置文件的文件名
	char* getIniFilename();

	// 获取客户端数据模型的对象指针
	CooperationManager* getCooperationManager();
	
	afx_msg  void OnAppAbout();
	DECLARE_MESSAGE_MAP()

private:
	// 配置文件的文件名
	char m_IniFileName[MAX_PATH];

	// 客户端的数据模型
    CooperationManager*  m_cooperationManager;

};

inline CFLVCCApp *GetApp()
{
	return (CFLVCCApp *)AfxGetApp();
}
