// ChatClient.cpp : 定义应用程序的类行为。
//

#include "stdafx.h"
#include "ChatClient.h"
#include "ChatClientDlg.h"
#include "Chat.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CChatClientApp

BEGIN_MESSAGE_MAP(CChatClientApp, CWinApp)
	ON_COMMAND(ID_HELP, CWinApp::OnHelp)
END_MESSAGE_MAP()


// CChatClientApp 构造

CChatClientApp::CChatClientApp()
{
	// TODO: 在此处添加构造代码，
	// 将所有重要的初始化放置在 InitInstance 中
}


// 唯一的一个 CChatClientApp 对象

CChatClientApp theApp;


// CChatClientApp 初始化

BOOL CChatClientApp::InitInstance()
{
	// 如果一个运行在 Windows XP 上的应用程序清单指定要
	// 使用 ComCtl32.dll 版本 6 或更高版本来启用可视化方式，
	//则需要 InitCommonControls()。否则，将无法创建窗口。
	InitCommonControls();

	CWinApp::InitInstance();

	// 标准初始化
	// 如果未使用这些功能并希望减小
	// 最终可执行文件的大小，则应移除下列
	// 不需要的特定初始化例程
	// 更改用于存储设置的注册表项
	// TODO: 应适当修改该字符串，
	// 例如修改为公司或组织名
	SetRegistryKey(_T("应用程序向导生成的本地应用程序"));

    char globalBuffer[MAX_PATH];
    ::memset(globalBuffer, 0, MAX_PATH);
	::GetModuleFileName(NULL, globalBuffer, MAX_PATH);
    size_t length = strlen(globalBuffer);
    globalBuffer[length - 3 ] = 'i';
    globalBuffer[length - 2 ] = 'n';
    globalBuffer[length - 1 ] = 'i';

    char buffer[1024];
    ::GetPrivateProfileString("Server", "IP", "127.0.0.1", buffer, 1024, globalBuffer);
    int nPort = ::GetPrivateProfileInt("Server", "Port", 1000, globalBuffer);

    pMatrixC = MatrixC::getInstance();
    if(!pMatrixC->Create(buffer, nPort))
    {
        char buffer2[1024];
        _snprintf(buffer2, 1024, "Can't not connect to %s:%d", buffer, nPort);
        AfxMessageBox(buffer2);
        return FALSE;
    }

    CChatClientDlg dlg;
	m_pMainWnd = &dlg;

    Chat chat(pMatrixC);

	INT_PTR nResponse = dlg.DoModal();
	if (nResponse == IDOK)
	{
		// TODO: 在此放置处理何时用“确定”来关闭
		//对话框的代码
	}
	else if (nResponse == IDCANCEL)
	{
		// TODO: 在此放置处理何时用“取消”来关闭
		//对话框的代码
	}

	// 由于对话框已关闭，所以将返回 FALSE 以便退出应用程序，
	// 而不是启动应用程序的消息泵。
	return FALSE;
}
