// testMirandaDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "testMiranda.h"
#include "testMirandaDlg.h"
#include ".\testmirandadlg.h"

#include "..\include\m_commands.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CtestMirandaDlg 对话框


CtestMirandaDlg::CtestMirandaDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CtestMirandaDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CtestMirandaDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CtestMirandaDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
	ON_BN_CLICKED(IDC_LOGOUT_BTN, OnBnClickedLogoutBtn)
	ON_BN_CLICKED(IDC_EXIT_BTN, OnBnClickedExitBtn)
	ON_BN_CLICKED(IDC_SETACCOUNTANDPASSWORD_BTN, OnBnClickedSetaccountandpasswordBtn)
END_MESSAGE_MAP()


// CtestMirandaDlg 消息处理程序

BOOL CtestMirandaDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// 设置此对话框的图标。当应用程序主窗口不是对话框时，框架将自动
	//  执行此操作
	SetIcon(m_hIcon, TRUE);			// 设置大图标
	SetIcon(m_hIcon, FALSE);		// 设置小图标

	return TRUE;  // 除非设置了控件的焦点，否则返回 TRUE
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CtestMirandaDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作矩形中居中
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标显示。
HCURSOR CtestMirandaDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CtestMirandaDlg::OnBnClickedOk()
{
	sendCmdToMiranda( MIRANDA_MSN_LOGIN );
	return;
    STARTUPINFO si = { sizeof(si) };            
    PROCESS_INFORMATION processInfo;
    //m_hEvent = CreateEvent( NULL, TRUE, FALSE, _T("miranda_event"));
	TCHAR szCommandline[] = _T( "D:\\miranda-im-v0.6.8-src\\miranda\\bin7\\Debug Unicode\\miranda32.exe miranda_event" );
    if( CreateProcess(NULL, szCommandline, NULL, NULL, FALSE, 0, NULL, NULL, &si, &processInfo) ) 
    {
        CloseHandle( processInfo.hProcess );
        CloseHandle( processInfo.hThread );            
    }

}

void CtestMirandaDlg::OnBnClickedLogoutBtn()
{
	sendCmdToMiranda( MIRANDA_MSN_LOGOUT );
}

void CtestMirandaDlg::OnBnClickedExitBtn()
{
	sendCmdToMiranda( MIRANDA_EXIT );
}

void CtestMirandaDlg::OnBnClickedSetaccountandpasswordBtn()
{
	sendCmdToMiranda( MIRANDA_MSN_SET_ACCOUNT_AND_PASSWORD );
}

void CtestMirandaDlg::sendCmdToMiranda( DWORD dwCmdType )
{
	CWnd* pWnd = FindWindow(NULL, "Miranda IM");
	COPYDATASTRUCT MyCDS;
	MyCDS.dwData = dwCmdType;          // function identifier
	MyCDS.cbData = sizeof( SET_ACCOUNT_AND_PASSWORD );  // size of data
	SET_ACCOUNT_AND_PASSWORD cmd;
	switch ( dwCmdType )
	{
	case MIRANDA_EXIT:
	case MIRANDA_MSN_LOGIN:
	case MIRANDA_MSN_LOGOUT:
	case MIRANDA_QQ_LOGIN:
	case MIRANDA_QQ_LOGOUT:
		break;
	case MIRANDA_MSN_SET_ACCOUNT_AND_PASSWORD:
	case MIRANDA_QQ_SET_ACCOUNT_AND_PASSWORD:
		{
			lstrcpy( cmd.szAccount, "johnwang__1977@hotmail.com" );
			lstrcpy( cmd.szPassword, "123456" );
			break;
		}
	default:
		return;
	}

	MyCDS.lpData = &cmd;           // data structure
	BOOL b = ::SendMessage( pWnd->GetSafeHwnd(), WM_COPYDATA, (WPARAM) GetSafeHwnd(), (LPARAM) (LPVOID) &MyCDS );
	if( !b )
	{
		TRACE1( "send command %d to miranda failed!\n", dwCmdType );
	}
}
