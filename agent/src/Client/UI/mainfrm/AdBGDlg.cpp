// UI\MainFrm\AdBGDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "AdBGDlg.h"
#include ".\adbgdlg.h"


// CAdBGDlg 对话框

IMPLEMENT_DYNAMIC(CAdBGDlg, CDialog)
CAdBGDlg::CAdBGDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CAdBGDlg::IDD, pParent)
{
}

CAdBGDlg::~CAdBGDlg()
{
}

void CAdBGDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CAdBGDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_SIZE()
END_MESSAGE_MAP()


// CAdBGDlg 消息处理程序

void CAdBGDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	this->GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB( 228, 240, 254 ) );
	// 不为绘图消息调用 CDialog::OnPaint()
}

void CAdBGDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	if( GetDlgItem( IDC_STATIC )->GetSafeHwnd() )
	{
		if( cx > 250 )
		{
			GetDlgItem( IDC_STATIC )->MoveWindow( (cx - 250) / 2, 0, 242, 62 );
			::SetWindowPos( m_dlgAd.GetSafeHwnd(), NULL, (cx - 250) / 2 + 1, 1, 240, 60, SWP_NOZORDER );
		}
		else
		{
			GetDlgItem( IDC_STATIC )->MoveWindow( 4, 0, cx - 8, 62 );
			::SetWindowPos( m_dlgAd.GetSafeHwnd(), NULL, 5, 1, cx - 10, 60, SWP_NOZORDER );
		}
	}
}

BOOL CAdBGDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
	m_dlgAd.Create( IDD_ADVERTIZE_DLG, this );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CAdBGDlg::navigate( CString url )
{
	m_dlgAd.m_sUrl = url;
	m_dlgAd.Navigate( url );
}

void CAdBGDlg::showAdDlg()
{
	GetDlgItem( IDC_STATIC )->ShowWindow( FALSE );
	m_dlgAd.ShowWindow( TRUE );
	m_dlgAd.RedrawWindow();
}
