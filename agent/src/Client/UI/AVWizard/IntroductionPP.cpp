// IntroductionPP.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "IntroductionPP.h"
#include ".\introductionpp.h"
#include "AVConfigWizard.h"


// CIntroductionPP 对话框

IMPLEMENT_DYNAMIC(CIntroductionPP, CPropertyPage)
CIntroductionPP::CIntroductionPP()
	: CPropertyPage(CIntroductionPP::IDD)
{
}

CIntroductionPP::~CIntroductionPP()
{
}

void CIntroductionPP::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CIntroductionPP, CPropertyPage)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// CIntroductionPP 消息处理程序

BOOL CIntroductionPP::OnSetActive()
{
    CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();   
	pWizard->setWindowRect( this );
	if( pWizard->m_btnBack.GetSafeHwnd() )
		pWizard->m_btnBack.EnableWindow(FALSE);
	if( pWizard->m_btnFinish.GetSafeHwnd() )
        pWizard->m_btnFinish.ShowWindow(FALSE);
	if( pWizard->m_btnNext.GetSafeHwnd() )
		pWizard->m_btnNext.ShowWindow(TRUE);

    return CPropertyPage::OnSetActive();
}

BOOL CIntroductionPP::OnKillActive()
{
    //CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();   
    //pWizard-(PSWIZB_NEXT | PSWIZB_BACK);
    return CPropertyPage::OnKillActive();
}


void CIntroductionPP::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();
	CString sText = "视音频设置向导";
	pWizard->drawHead( &dc, rc, sText );
	// 不为绘图消息调用 CPropertyPage::OnPaint()
}

BOOL CIntroductionPP::OnInitDialog()
{
	CPropertyPage::OnInitDialog();

    m_brush.CreateSolidBrush( RGB(240, 245, 250) );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

HBRUSH CIntroductionPP::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CPropertyPage::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}
