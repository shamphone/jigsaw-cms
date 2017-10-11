#include "StdAfx.h"
#include "Flvcc.h"
#include ".\aboutdlg.h"
#include "Common/Common/PathHelper/PathHelper.h"

CAboutDlg::CAboutDlg() : CDialog(CAboutDlg::IDD)
{
}

void CAboutDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control( pDX, IDOK, m_btnOK );
}

BEGIN_MESSAGE_MAP(CAboutDlg, CDialog)
	ON_BN_CLICKED(IDC_BTN_OK, OnBnClickedBtnOk)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
END_MESSAGE_MAP()

// App command to run the dialog
void CFLVCCApp::OnAppAbout()
{
	CAboutDlg aboutDlg;
	aboutDlg.DoModal();
}
/////////////////////////////////////////////////////////////////////////////
// CFLVCCApp message handlers

void CAboutDlg::OnBnClickedBtnOk()
{
	CDialog::OnOK();
}

BOOL CAboutDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_brush.CreateSolidBrush( RGB( 228, 240, 254 ) );
	// Read version number
	char buffer[64] = "0";
	string version_dat = string(PathHelper::getApplicationRoot()) + "\\version.dat";
	FILE *fp = fopen(version_dat.c_str(), "r");
	if( fp )
	{
		fgets(buffer, 64, fp);
		fclose(fp);
	}

	// Set the version number on the about dialog
	char buffer2[128];
	_snprintf(buffer2, 128, "Build: %s", buffer);
	CWnd* pWnd = this->GetDlgItem(IDC_STATIC_REVISION);
	pWnd->SetWindowText(buffer2);

    CRect rc;
	GetClientRect( &rc );
	m_btnOK.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnOK.MoveWindow( rc.right - 80, rc.bottom - 32, 74, 22 );
	return TRUE;
}

void CAboutDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect(&rc);
	dc.FillSolidRect( rc, RGB(228, 240, 254 ) );
	// 不为绘图消息调用 CDialog::OnPaint()
}

HBRUSH CAboutDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC_REVISION || pWnd->GetDlgCtrlID() == IDC_STATIC )
	{
		pDC->SetBkMode( TRANSPARENT );
		pDC->SetTextColor( RGB(1, 37, 131) );
		hbr = m_brush;
	}
	return hbr;
}

void CAboutDlg::OnBnClickedOk()
{
	OnOK();
}
