// UI\MainFrm\MsnAccountDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "MsnAccountDlg.h"
#include ".\msnaccountdlg.h"


// CMsnAccountDlg 对话框

IMPLEMENT_DYNAMIC(CMsnAccountDlg, CDialogEx)
CMsnAccountDlg::CMsnAccountDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CMsnAccountDlg::IDD, pParent)
{
	this->m_sDescription = "设置的帐号和密码在本次登录注销后下次登录时有效。"; 
	m_bReadOnly = FALSE;
}

CMsnAccountDlg::~CMsnAccountDlg()
{
}

void CMsnAccountDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Text( pDX, IDC_MSNACCOUNT, m_sAccount );
	DDX_Text( pDX, IDC_MSNPASSWORD, m_sPassword );
}


BEGIN_MESSAGE_MAP(CMsnAccountDlg, CDialogEx)
END_MESSAGE_MAP()


// CMsnAccountDlg 消息处理程序

BOOL CMsnAccountDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	UpdateData( FALSE );
	((CEdit*)GetDlgItem(IDC_MSNACCOUNT))->SetReadOnly( m_bReadOnly );
	((CEdit*)GetDlgItem(IDC_MSNPASSWORD))->SetReadOnly( m_bReadOnly );

	((CEdit*)GetDlgItem(IDC_MSNACCOUNT))->LimitText( 126 );
	((CEdit*)GetDlgItem(IDC_MSNPASSWORD))->LimitText( 126 );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CMsnAccountDlg::OnOK()
{
	UpdateData();
	CDialogEx::OnOK();
}
