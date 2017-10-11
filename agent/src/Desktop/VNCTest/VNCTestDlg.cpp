// VNCTestDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "VNCTest.h"
#include "VNCTestDlg.h"
#include ".\vnctestdlg.h"

#include "LyvcDesktopServer\DesktopServer.h"
#include "LyvcDesktopClient\DesktopClient.h"
#include "../../message/MatrixC/MatrixCLib/MatrixC.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

using namespace LyvcMessage ;

// CVNCTestDlg 对话框

CVNCTestDlg::CVNCTestDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CVNCTestDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	m_desktopServer = NULL;
	m_desktopClient = NULL;
	m_pMatrixC		= NULL;
}

void CVNCTestDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CVNCTestDlg, CDialog)
	ON_WM_SYSCOMMAND()
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
	ON_WM_CLOSE()
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, OnBnClickedCancel)

	ON_BN_CLICKED(IDC_STARTCLIENT, OnBnClickedStartclient)
	ON_BN_CLICKED(IDC_STOPCLIENT, OnBnClickedStopclient)
	ON_BN_CLICKED(IDC_STARTSERVER, OnBnClickedStartserver)
	ON_BN_CLICKED(IDC_STOPSERVER, OnBnClickedStopserver)
	ON_BN_CLICKED(IDC_CONTROL, OnBnClickedControl)
	ON_BN_CLICKED(IDC_UNCONTROL, OnBnClickedUncontrol)
	ON_BN_CLICKED(IDC_RESTART, OnBnClickedRestart)
END_MESSAGE_MAP()


// CVNCTestDlg 消息处理程序

BOOL CVNCTestDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// 将\“关于...\”菜单项添加到系统菜单中。

	// IDM_ABOUTBOX 必须在系统命令范围内。
	ASSERT((IDM_ABOUTBOX & 0xFFF0) == IDM_ABOUTBOX);
	ASSERT(IDM_ABOUTBOX < 0xF000);

	CMenu* pSysMenu = GetSystemMenu(FALSE);
	if (pSysMenu != NULL)
	{
		CString strAboutMenu;
		strAboutMenu.LoadString(IDS_ABOUTBOX);
		if (!strAboutMenu.IsEmpty())
		{
			pSysMenu->AppendMenu(MF_SEPARATOR);
			pSysMenu->AppendMenu(MF_STRING, IDM_ABOUTBOX, strAboutMenu);
		}
	}

	// 设置此对话框的图标。当应用程序主窗口不是对话框时，框架将自动
	//  执行此操作
	SetIcon(m_hIcon, TRUE);			// 设置大图标
	SetIcon(m_hIcon, FALSE);		// 设置小图标

	// TODO: 在此添加额外的初始化代码
	
	return TRUE;  // 除非设置了控件的焦点，否则返回 TRUE
}

void CVNCTestDlg::OnSysCommand(UINT nID, LPARAM lParam)
{
	CDialog::OnSysCommand(nID, lParam);
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CVNCTestDlg::OnPaint() 
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
HCURSOR CVNCTestDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CVNCTestDlg::OnClose()
{
	if(m_desktopServer)
	{
		m_desktopServer->stop();
		delete m_desktopServer;
		m_desktopServer = NULL;
	}
	if(m_desktopClient)
	{
		m_desktopClient->stop();
		delete m_desktopClient;
		m_desktopClient = NULL;
	}
	if(this->m_pMatrixC)
		this->releaseMsgMatrix();

	CDialog::OnClose();
}
void CVNCTestDlg::OnBnClickedOk()
{
	OnClose();
	CDialog::OnOK();
}

void CVNCTestDlg::OnBnClickedCancel()
{
	CDialog::OnCancel();
}

void CVNCTestDlg::OnBnClickedStartserver()
{
	if(!m_desktopServer)
		m_desktopServer = DesktopServer::getInstance();
	m_desktopServer->start(NULL, NULL, NULL);
}

void CVNCTestDlg::OnBnClickedStopserver()
{
	if(m_desktopServer)
	{
		m_desktopServer->stop();
		delete m_desktopServer;
		m_desktopServer = NULL;
	}
}

void CVNCTestDlg::OnBnClickedStartclient()
{
	if(m_pMatrixC)
		return;

	if(!initMsgMatrix("192.168.0.174", 2000))
	{
		AfxMessageBox("error while init MsgMatrix!");
		return;
	}
	if(!m_desktopClient)
		m_desktopClient = DesktopClient::getInstance();
	m_desktopClient->start(this->m_pMatrixC);

}

void CVNCTestDlg::OnBnClickedStopclient()
{
	if(m_desktopClient)
	{
		m_desktopClient->stop();
		delete m_desktopClient;
		m_desktopClient = NULL;
	}

	if(this->m_pMatrixC)
		this->releaseMsgMatrix();
}

BOOL CVNCTestDlg::initMsgMatrix(CString IP, int port)
{
    m_pMatrixC = MatrixC::getInstance();
    if( !m_pMatrixC->Create(IP, port))
    {
        MatrixC::releaseInstance(m_pMatrixC);
        m_pMatrixC = NULL;
		return FALSE;
    }
	return TRUE;
}

void CVNCTestDlg::releaseMsgMatrix()
{
	m_pMatrixC->Destroy();
	MatrixC::releaseInstance(m_pMatrixC);
	m_pMatrixC = NULL;
}
void CVNCTestDlg::OnBnClickedControl()
{
	if(m_desktopClient)
		m_desktopClient->startControl(0);
}

void CVNCTestDlg::OnBnClickedUncontrol()
{
	if(m_desktopClient)
		m_desktopClient->stopControl(0);

}

void CVNCTestDlg::OnBnClickedRestart()
{
//	m_desktopServer->restart();
}
