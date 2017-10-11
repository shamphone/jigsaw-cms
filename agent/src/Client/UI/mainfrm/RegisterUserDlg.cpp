// UI\MainFrm\RegisterUser.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "RegisterUserDlg.h"
#include ".\registeruserdlg.h"
#include "MainFrm.h"
#include "UserDefinedMessage.h"
#include "..\..\Model\LServer.h"
#include "..\..\Model\RegisterUserMgr.h"

// CRegisterUserDlg 对话框

IMPLEMENT_DYNAMIC(CRegisterUserDlg, CDialogEx)
CRegisterUserDlg::CRegisterUserDlg( LServer* pServer, CWnd* pParent /*=NULL*/)
	: CDialogEx(CRegisterUserDlg::IDD, pParent)
{
	m_pServer = pServer;
	m_pRegisterMgr = NULL;
}

CRegisterUserDlg::~CRegisterUserDlg()
{
}

void CRegisterUserDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text( pDX, IDC_USERNAME, m_sUsername );
	DDX_Text( pDX, IDC_PASSWORD, m_sPassword );
	DDX_Text( pDX, IDC_CONFIRMPASSWORD, m_sConfirmPassword );
	DDX_Text( pDX, IDC_LASTNAME, m_sLastName );
	DDX_Text( pDX, IDC_FIRSTNAME, m_sFirstName );
	DDX_Text( pDX, IDC_EMAIL, m_sEmail );
	DDX_Check( pDX, IDC_LOGIN_AFTER_REGISTER, m_bLoginAfterRegister );
}


BEGIN_MESSAGE_MAP(CRegisterUserDlg, CDialogEx)
	ON_WM_CTLCOLOR()
	ON_MESSAGE(WM_REGISTERUSER_RESPONSE, OnRegisterUserResponse)
END_MESSAGE_MAP()


// CRegisterUserDlg 消息处理程序

BOOL CRegisterUserDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	( (CEdit*)GetDlgItem(IDC_USERNAME) )->LimitText( 16 );
	( (CEdit*)GetDlgItem(IDC_FIRSTNAME) )->LimitText( 100 );
	( (CEdit*)GetDlgItem(IDC_LASTNAME) )->LimitText( 100 );
	( (CEdit*)GetDlgItem(IDC_EMAIL) )->LimitText( 200 );
	( (CEdit*)GetDlgItem(IDC_PASSWORD) )->LimitText( 12 );
	( (CEdit*)GetDlgItem(IDC_CONFIRMPASSWORD) )->LimitText( 12 );

	this->m_sDescription = "注册新用户，请填写以下所有注册信息。";

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CRegisterUserDlg::OnOK()
{
	UpdateData();
	if( !validateRegisterInfo() )
	{
		return;
	}
	if( this->m_pRegisterMgr == NULL )
	{
		m_pRegisterMgr = new RegisterUserMgr( m_pServer, this );
		if( !m_pRegisterMgr->create() )
		{
			delete m_pRegisterMgr;
			m_pRegisterMgr = NULL;
			AfxMessageBox( "无法连接服务器，请稍后再试。" );
			return;
		}
	}
	m_pRegisterMgr->cmdRegisterUser( m_sUsername.GetString(), m_sLastName.GetString(), 
		m_sFirstName.GetString(), m_sEmail.GetString(), m_sPassword.GetString(), m_pServer->getIP() );
}

HBRUSH CRegisterUserDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_LOGIN_AFTER_REGISTER )
	{
		pDC->SetTextColor( m_crText );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}

LRESULT CRegisterUserDlg::OnRegisterUserResponse( WPARAM wParam, LPARAM lParam )
{
	string* pMsg = (string*)lParam;
	CString msg = (*pMsg).c_str();
	delete pMsg;
	if( wParam )
	{
		AfxMessageBox( "注册成功！" );
		if( this->m_bLoginAfterRegister )
		{
			if( m_pServer->getStatus() != SERVER_LOGOUT )
			{
				((CMainFrame*)GetParent())->OnLogoutServer(m_pServer);
			}
			m_pServer->setUserName( m_sUsername.GetString() );
			m_pServer->setPassword( m_sPassword.GetString() );
			((CMainFrame*)GetParent())->OnLoginServer( m_pServer );
		}
		OnCancel();
	}
	else
	{
		AfxMessageBox( "注册失败！原因是：" + msg );
	}
	return 0;
}

BOOL CRegisterUserDlg::validateRegisterInfo()
{
	for( int i = 0; i < m_sUsername.GetLength(); i++ )
	{
		char ch = m_sUsername.GetAt(i);
		if( ch < 48 || ( ch > 57 && ch < 97 ) || ch > 122 )
		{
            AfxMessageBox( "用户名应该是小写英文字母和阿拉伯数字的组合，请重新输入。" );
			return FALSE;
		}
	}
	if( m_sUsername.GetLength() < 4 || m_sUsername.GetLength() > 16 )
	{
		AfxMessageBox( "用户名长度应该在4-16个字符之间，请重新输入。" );
		return FALSE;
	}
	if( m_sPassword.GetLength() < 6 || m_sPassword.GetLength() > 16 )
	{
		AfxMessageBox( "密码长度应该在6-16个字符之间，请重新输入。" );
		return FALSE;
	}
	else if( m_sLastName.GetLength() == 0 || m_sFirstName.GetLength() == 0 
		    || m_sEmail.GetLength() == 0 || m_sPassword.GetLength() == 0 )
	{
		AfxMessageBox( "所有选项不能为空，请重新输入。" );
		return FALSE;
	}
	if( m_sPassword != m_sConfirmPassword )
	{
		AfxMessageBox( "确认密码与密码不匹配，请重新输入。" );
		return FALSE;
	}
	return TRUE;
}

void CRegisterUserDlg::OnCancel()
{
	if( this->m_pRegisterMgr )
	{
		m_pRegisterMgr->destroy();
		delete m_pRegisterMgr;
	}
	CDialogEx::OnCancel();
}
