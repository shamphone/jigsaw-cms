// FLVCC.cpp : Defines the class behaviors for the application.
//

#include "stdafx.h"
#include "FLVCC.h"
#include "UI\mainfrm\MainFrm.h"
#include "Model\CooperationManager.h"
#include "common/common/PathHelper/PathHelper.h"
#include "common/common/log/log.h"
#include "common\common\registryconstant\registryconstant.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CFLVCCApp

BEGIN_MESSAGE_MAP(CFLVCCApp, CWinApp)
	//{{AFX_MSG_MAP(CFLVCCApp)
	ON_COMMAND(ID_APP_ABOUT, OnAppAbout)
		// NOTE - the ClassWizard will add and remove mapping macros here.
		//    DO NOT EDIT what you see in these blocks of generated code!
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


/////////////////////////////////////////////////////////////////////////////
// The one and only CFLVCCApp object

CFLVCCApp theApp;

/////////////////////////////////////////////////////////////////////////////
// CFLVCCApp initialization

BOOL CFLVCCApp::InitInstance()
{
#ifndef _DEBUG
	HANDLE hMutex = CreateMutex(NULL, TRUE, _T("Run One Copy Of Flvcc") );
	if( GetLastError() == ERROR_ALREADY_EXISTS )
	{
		CWnd *pWndPrev, *pWndChild;
		// Determine if a window with the class name exists...
		if (pWndPrev = CWnd::FindWindow(NULL, APP_TITLE))
		{
			// If so, does it have any popups?
			pWndChild = pWndPrev->GetLastActivePopup();
			// If iconic, restore the main window
			if (pWndPrev->IsIconic())
				pWndPrev->ShowWindow(SW_RESTORE);
			// Bring the main window or its popup to the foreground
			pWndChild->SetForegroundWindow();
		}
		return FALSE;
	}
#endif

	// 初始化COM
	::CoInitialize(0);

	// 将工作目录转到exe文件所在的目录
	char buffer[MAX_PATH];
    ::memset(buffer, 0, MAX_PATH);
	::GetModuleFileName(NULL, buffer, MAX_PATH);
    char* exeFolderEnd = ::strrchr(buffer, '\\');
    *exeFolderEnd = 0;
	_chdir(buffer);

    /*
	 *	生成配置文件的名字
	 */
    ::strcpy(this->m_IniFileName, PathHelper::getIniFileFullName());

	/*
	 *	初始化日志
	 */
#ifdef _DEBUG
    Log::open();
    FVS_DEBUG0("Flvcc start.");
#endif

	/*
	 *	初始化 WinSock
	 */
	WSADATA wsaData;
	WORD wVersionRequested=MAKEWORD(2,2);
	if( ::WSAStartup(wVersionRequested, &wsaData) != 0)
	{
		::AfxMessageBox("WSAStartup Failed!");
		return FALSE;
	}

	/*
	 *  初始化客户端管理系统
	 */
	m_cooperationManager = new CooperationManager();

	//初始化richeditctrl
	AfxInitRichEdit2();

	/*
	 *	创建并显示主窗口
	 */
	CMainFrame* pFrame = new CMainFrame();
	m_pMainWnd = pFrame;
	pFrame->LoadFrame(IDR_LOGOUT, WS_OVERLAPPEDWINDOW | FWS_ADDTOTITLE, NULL, NULL);
	pFrame->ShowWindow(SW_SHOW);
	pFrame->UpdateWindow();
	pFrame->SetFocus();

	m_cooperationManager->create();

	return TRUE;
}

int CFLVCCApp::ExitInstance()
{
	/*
     *  关闭客户端管理系统
     */
	if (m_cooperationManager)
	{
		m_cooperationManager->destroy();
		delete m_cooperationManager;
	}

    /*
	 *	清除WinSock库
	 */
	::WSACleanup();

	/*
	 *	关闭日志
	 */
#ifdef _DEBUG
    FVS_DEBUG0("Flvcc exit.");
    Log::close();
#endif

	// 清理COM
	::CoUninitialize();

	return CWinApp::ExitInstance();
}

char* CFLVCCApp::getIniFilename()
{
	return m_IniFileName;
}

CooperationManager* CFLVCCApp::getCooperationManager()
{
	return m_cooperationManager;
}
