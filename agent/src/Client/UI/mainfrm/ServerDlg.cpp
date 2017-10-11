// UI\MainFrm\ServerDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "ServerDlg.h"
#include ".\serverdlg.h"
#include "MainFrm.h"
#include "UserDefinedMessage.h"
#include "..\..\Model\LServer.h"
// CServerDlg 对话框

IMPLEMENT_DYNAMIC(CServerDlg, CDialogEx)
CServerDlg::CServerDlg(int nType, LServer* pServer, CWnd* pParent /*=NULL*/)
	: CDialogEx(CServerDlg::IDD, pParent)
{
	m_nType = nType;
	m_pServer = pServer;
	m_sTitle = "";
	m_sServerName = "";
	m_sServerIP = "";
	m_nPort = 4000;
}

CServerDlg::~CServerDlg()
{
}

void CServerDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text( pDX, IDC_SERVERNAME, m_sServerName );
	DDX_Text( pDX, IDC_SERVERIP, m_sServerIP );
	DDX_Text( pDX, IDC_SERVERPORT, m_nPort );
}


BEGIN_MESSAGE_MAP(CServerDlg, CDialogEx)
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
END_MESSAGE_MAP()


// CServerDlg 消息处理程序

BOOL CServerDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();
    this->SetWindowText( m_sTitle );
	((CEdit*)GetDlgItem( IDC_SERVERPORT ))->LimitText( 5 );
	((CEdit*)GetDlgItem( IDC_SERVERIP ))->LimitText( 200 );
	((CEdit*)GetDlgItem( IDC_SERVERNAME ))->LimitText( 200 );
	if( m_pServer != NULL )
	{
		BOOL bReadOnly = ( m_nType == VIEW_SERVER );
		((CEdit*)GetDlgItem( IDC_SERVERPORT ))->SetReadOnly( bReadOnly );
		((CEdit*)GetDlgItem( IDC_SERVERIP ))->SetReadOnly( bReadOnly );
		((CEdit*)GetDlgItem( IDC_SERVERNAME ))->SetReadOnly( bReadOnly );
		m_sServerName = m_pServer->getServerName().c_str();
		m_sServerIP = m_pServer->getIP().c_str();
		m_nPort = m_pServer->getPort();
	}
	UpdateData(FALSE);
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CServerDlg::OnBnClickedOk()
{
	UpdateData();
	if( !validateServerInfo() )
		return;
	CMainFrame* pMain = (CMainFrame*) GetParent();
	CString str;
	switch (m_nType)
	{
	case ADD_SERVER:
		{
			if( pMain->isServerExist( m_sServerName.GetString(), m_sServerIP.GetString(), m_nPort, str ) != NULL )
			{
				AfxMessageBox(str);
				return;
			}
			OnOK();
		}
		break;
	case MODIFY_SERVER:
		{
			if( pMain->isDuplicateServer( m_pServer, m_sServerName.GetString(), m_sServerIP.GetString(), m_nPort, str ) )
			{
				AfxMessageBox(str);
				return;
			}
			OnOK();
		}
		break;
	case VIEW_SERVER:
	default:
		OnCancel();
		break;
	}
}

BOOL CServerDlg::validateServerInfo()
{
	CString sName = m_sServerName.Trim();
	CString sIP = m_sServerIP.Trim();
	if( sName == "" )
	{
		AfxMessageBox( "服务器名称不能为空!" );
		return FALSE;
	}
	if( sIP == "" )
	{
		AfxMessageBox( "服务器地址不能为空!" );
		return FALSE;
	}
	if( m_nPort <= 0 )
	{
		AfxMessageBox( "请填写正确的服务器端口!" );
		return FALSE;
	}
	if( sName.Find( '\\' ) != -1 
		|| sName.Find( '/' ) != -1 
		|| sName.Find( ':' ) != -1
		|| sName.Find( '*' ) != -1 
		|| sName.Find( '?' ) != -1 
		|| sName.Find( '\"' ) != -1 
		|| sName.Find( '<' ) != -1 
		|| sName.Find( '>' ) != -1 
		|| sName.Find( '|' ) != -1 )
	{
		AfxMessageBox( "服务器名称不能包含以下字符  \\  /  :  *  ?  \"  <  >  |" );
		return FALSE;
	}
	return TRUE;
}