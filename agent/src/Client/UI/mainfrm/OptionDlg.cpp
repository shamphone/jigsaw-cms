// UI\MainFrm\OptionDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "OptionDlg.h"
#include ".\optiondlg.h"
#include "optiongeneralpage.h"
#include "optionvideoconfigpage.h"
#include "..\pub\ImageTabWnd.h"

// COptionDlg 对话框

IMPLEMENT_DYNAMIC(COptionDlg, CDialog)
COptionDlg::COptionDlg(CWnd* pParent /*=NULL*/)
	: CDialog(COptionDlg::IDD, pParent)
{
	m_pImageTab = NULL;
	m_pDlgVideoConfig = NULL;
	m_pDlgGeneral = NULL;
}

COptionDlg::~COptionDlg()
{

}

void COptionDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control( pDX, IDC_APPLY_BTN, m_btnApply );
	DDX_Control( pDX, IDC_CLOSE_BTN, m_btnClose );
	DDX_Control( pDX, IDOK, m_btnOK );
}


BEGIN_MESSAGE_MAP(COptionDlg, CDialog)
	ON_BN_CLICKED(IDC_APPLY_BTN, OnBnClickedApplyBtn)
	ON_BN_CLICKED(IDC_CLOSE_BTN, OnBnClickedCloseBtn)
	ON_WM_DESTROY()
	ON_WM_SIZE()
	ON_WM_PAINT()
	ON_WM_CLOSE()
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
END_MESSAGE_MAP()


// COptionDlg 消息处理程序

void COptionDlg::OnBnClickedApplyBtn()
{
	this->m_pDlgGeneral->apply();
	this->m_pDlgVideoConfig->apply();
}

void COptionDlg::OnBnClickedCloseBtn()
{
	CDialog::OnOK();
}

BOOL COptionDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_pImageTab = new CImageTabWnd();
	m_pImageTab->Create(NULL, NULL, WS_CHILD|WS_CLIPCHILDREN, CRect(), this, NULL, NULL);

	m_pImageTab->SetTopImage(IDB_SENDFILETABTOP);
	m_pImageTab->SetTitleHeight(40);
	m_pImageTab->SetTabCount(2);
	m_pImageTab->SetDialogMargin(1);

	int nIndex = 0;
	m_pImageTab->SetTabImage(nIndex, IDB_GENERALOPTION);
	m_pImageTab->SetTabRect(nIndex, CRect( 2, 8, 97, 36));
	m_pImageTab->SetToolTip(nIndex, "常规选项");
	m_pDlgGeneral = new COptionGeneralPage( m_pImageTab );
	m_pDlgGeneral->Create(IDD_OPTION_GENERAL_DLG, m_pImageTab);
	m_pImageTab->SetTabWnd(nIndex, m_pDlgGeneral);
	nIndex++;
	m_pImageTab->SetTabImage(nIndex, IDB_VIDEOOPTION);
	m_pImageTab->SetTabRect(nIndex, CRect( 103, 8, 200, 36));
	m_pImageTab->SetToolTip(nIndex, "视频参数");
	m_pDlgVideoConfig = new COptionVideoConfigPage( m_pImageTab );
	m_pDlgVideoConfig->setReadOnly( this->m_bReadOnly );
	m_pDlgVideoConfig->Create(IDD_OPTION_VIDEOCONFIG_DLG, m_pImageTab);
	m_pImageTab->SetTabWnd(nIndex, m_pDlgVideoConfig);

	CRect rc;
	GetClientRect( &rc );
	m_pImageTab->MoveWindow( CRect(0, 0, rc.Width(), rc.Height() - 42) );
	m_pImageTab->ShowDialog( 0 );
	m_pImageTab->ShowWindow( TRUE );

	m_btnApply.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnClose.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnOK.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnApply.MoveWindow( rc.right - 80, rc.bottom - 32, 74, 22 );
	m_btnClose.MoveWindow( rc.right - 160, rc.bottom - 32, 74, 22 );
	m_btnOK.MoveWindow( rc.right - 240, rc.bottom - 32, 74, 22 );

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void COptionDlg::OnDestroy()
{
	CDialog::OnDestroy();

	if(m_pDlgGeneral)
	{
		m_pDlgGeneral->DestroyWindow();
		delete m_pDlgGeneral;
		m_pDlgGeneral = NULL;
	}
	if(m_pDlgVideoConfig)
	{
		m_pDlgVideoConfig->DestroyWindow();
		delete m_pDlgVideoConfig;
		m_pDlgVideoConfig = NULL;
	}
	if(m_pImageTab)
	{
		m_pImageTab->DestroyWindow();
		delete m_pImageTab;
		m_pImageTab = NULL;
	}
}

void COptionDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);

	if( this->m_pImageTab->GetSafeHwnd() )
	{
		m_pImageTab->MoveWindow( 0, 0, cx, cy - 42 );
	}
}

void COptionDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB(228, 240, 254) );
	// 不为绘图消息调用 CDialog::OnPaint()
}

void COptionDlg::OnClose()
{
	CDialog::OnCancel();
}

void COptionDlg::OnBnClickedOk()
{
	m_pDlgGeneral->apply();
	if( m_pDlgVideoConfig->apply() )
        CDialog::OnOK();
}
