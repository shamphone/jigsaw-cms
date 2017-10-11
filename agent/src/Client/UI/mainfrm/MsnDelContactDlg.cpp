// UI\MainFrm\MsnDelContactDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "MsnDelContactDlg.h"
#include ".\msndelcontactdlg.h"


// CMsnDelContactDlg 对话框

IMPLEMENT_DYNAMIC(CMsnDelContactDlg, CDialogEx)
CMsnDelContactDlg::CMsnDelContactDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CMsnDelContactDlg::IDD, pParent)
{
	this->m_sDescription = "删除联系人";
	m_bHide = TRUE;
}

CMsnDelContactDlg::~CMsnDelContactDlg()
{
}

void CMsnDelContactDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CMsnDelContactDlg, CDialogEx)
	ON_WM_CTLCOLOR()
	ON_BN_CLICKED(IDC_MSN_HIDECONTACT, OnBnClickedMsnHidecontact)
	ON_BN_CLICKED(IDC_MSN_DELCONTACT, OnBnClickedMsnDelcontact)
END_MESSAGE_MAP()


// CMsnDelContactDlg 消息处理程序

HBRUSH CMsnDelContactDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_MSN_HIDECONTACT || pWnd->GetDlgCtrlID() == IDC_MSN_DELCONTACT )
	{
		pDC->SetBkMode( TRANSPARENT );
		hbr = this->m_brush;
	}
	return hbr;
}

BOOL CMsnDelContactDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	((CButton*)GetDlgItem(IDC_MSN_HIDECONTACT))->SetCheck(TRUE);


	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CMsnDelContactDlg::OnBnClickedMsnHidecontact()
{
	this->m_bHide = TRUE;
}

void CMsnDelContactDlg::OnBnClickedMsnDelcontact()
{
	this->m_bHide = FALSE;
}
