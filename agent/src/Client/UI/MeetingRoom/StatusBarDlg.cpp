// UI\MeetingRoom\StatusBarDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "StatusBarDlg.h"
#include ".\statusbardlg.h"


// CStatusBarDlg 对话框

IMPLEMENT_DYNAMIC(CStatusBarDlg, CDialog)
CStatusBarDlg::CStatusBarDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CStatusBarDlg::IDD, pParent)
{
	m_adUrl = "";
}

CStatusBarDlg::~CStatusBarDlg()
{
}

void CStatusBarDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CStatusBarDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_SIZE()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// CStatusBarDlg 消息处理程序

BOOL CStatusBarDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	m_dlgAd.Create( IDD_ADVERTIZE_DLG, this );
    if( m_adUrl != "" )
	{
		m_dlgAd.Navigate( m_adUrl );
		m_dlgAd.ShowWindow( TRUE );
	}
	m_brush.CreateSolidBrush( RGB( 228, 240, 254 ) );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CStatusBarDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB(228, 240, 254) );
	// 不为绘图消息调用 CDialog::OnPaint()
}

void CStatusBarDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
    if( m_dlgAd.GetSafeHwnd() )
	{
		m_dlgAd.MoveWindow( 225, 2, cx - 230, 14 );
		GetDlgItem( IDC_STATIC )->MoveWindow( 5, 1, 60, 16 );
		GetDlgItem( IDC_STAYTIME )->MoveWindow( 65, 1, 60, 16 );
		GetDlgItem( IDC_STATIC1 )->MoveWindow( 125, 1, 60, 16 );
		GetDlgItem( IDC_CONFUSERNUM )->MoveWindow( 185, 1, 40, 16 );
	}
}

HBRUSH CStatusBarDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);
	int nID = pWnd->GetDlgCtrlID();
	if( nID == IDC_STATIC || nID == IDC_STATIC1	|| nID == IDC_CONFUSERNUM || nID == IDC_STAYTIME )
	{
		pDC->SetBkMode( TRANSPARENT );
		if( nID == IDC_STATIC || nID == IDC_STATIC1 )
			pDC->SetTextColor( RGB( 128, 128, 128 ) );
		else
			pDC->SetTextColor( RGB( 128, 0, 128 ) );
		hbr = m_brush;
	}
	return hbr;
}

void CStatusBarDlg::setStayTime( CString str )
{
	GetDlgItem( IDC_STAYTIME )->SetWindowText( str );
}

void CStatusBarDlg::setConfUserNum( CString str )
{
	GetDlgItem( IDC_CONFUSERNUM )->SetWindowText( str );
}
