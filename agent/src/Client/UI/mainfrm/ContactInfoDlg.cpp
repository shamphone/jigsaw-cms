// ui\mainfrm\ContactInfoDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "ContactInfoDlg.h"
#include ".\contactinfodlg.h"


// CContactInfoDlg 对话框

IMPLEMENT_DYNAMIC(CContactInfoDlg, CDialogEx)
CContactInfoDlg::CContactInfoDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CContactInfoDlg::IDD, pParent)
{
	m_sName = "";
	m_sEmail = "";
	m_sStatus = "";
	m_sGroup = "";
}

CContactInfoDlg::~CContactInfoDlg()
{
}

void CContactInfoDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CContactInfoDlg, CDialogEx)
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// CContactInfoDlg 消息处理程序

BOOL CContactInfoDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	GetDlgItem(IDC_CONTACT_PROPERTY_NAME)->SetWindowText(m_sName);
	GetDlgItem(IDC_CONTACT_PROPERTY_EMAIL)->SetWindowText(m_sEmail);
	GetDlgItem(IDC_CONTACT_PROPERTY_STATUS)->SetWindowText(m_sStatus);
	GetDlgItem(IDC_CONTACT_PROPERTY_GROUP)->SetWindowText(m_sGroup);
    this->m_sDescription = m_sName+"的个人信息：";
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

HBRUSH CContactInfoDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_CONTACT_PROPERTY_NAME
		|| pWnd->GetDlgCtrlID() == IDC_CONTACT_PROPERTY_EMAIL
		|| pWnd->GetDlgCtrlID() == IDC_CONTACT_PROPERTY_STATUS
		|| pWnd->GetDlgCtrlID() == IDC_CONTACT_PROPERTY_GROUP )
	{
		pDC->SetTextColor( m_crText );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}
