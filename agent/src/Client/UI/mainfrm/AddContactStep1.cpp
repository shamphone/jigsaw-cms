// D:\Lyvc\src\Client\UI\MainFrm\AddContactStep1.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "AddContactStep1.h"
#include "AddContactWizard.h"
#include ".\addcontactstep1.h"


// CAddContactStep1 对话框

IMPLEMENT_DYNAMIC(CAddContactStep1, CPropertyPage)
CAddContactStep1::CAddContactStep1()
	: CPropertyPage(CAddContactStep1::IDD)
{
}

CAddContactStep1::~CAddContactStep1()
{
}

void CAddContactStep1::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CAddContactStep1, CPropertyPage)
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CAddContactStep1 消息处理程序

BOOL CAddContactStep1::OnSetActive()
{
	CAddContactWizard* pWizard = (CAddContactWizard*) GetParent();
	pWizard->setWindowRect( 0 );
	if( pWizard->m_btnNext.GetSafeHwnd() )
	{
		pWizard->m_btnNext.ShowWindow(TRUE);
		pWizard->m_btnNext.EnableWindow();
	}
	if( pWizard->m_btnCancel.GetSafeHwnd() )
		pWizard->m_btnCancel.SetWindowText("取消");
	if( pWizard->m_btnBack.GetSafeHwnd() )
		pWizard->m_btnBack.EnableWindow(FALSE);
	if( pWizard->m_btnFinish.GetSafeHwnd() )
		pWizard->m_btnFinish.ShowWindow(FALSE);

	return CPropertyPage::OnSetActive();
}

LRESULT CAddContactStep1::OnWizardNext()
{
	CString email;
	GetDlgItem(IDC_EMAIL)->GetWindowText(email);
	if(email.Trim() == "")
	{
		AfxMessageBox("请输入有效的电子邮件地址.");
		return -1;
	}
	CAddContactWizard* pWizard = (CAddContactWizard*) GetParent();
	pWizard->searchContact(email.GetString());

	return CPropertyPage::OnWizardNext();
}

void CAddContactStep1::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CAddContactWizard* pWizard = (CAddContactWizard*) GetParent();
	CString sText = "请在下面的输入框中输入您要添加的联系人的电子邮件：";
	pWizard->drawHead( &dc, rc, sText );

	// 不为绘图消息调用 CPropertyPage::OnPaint()
}
