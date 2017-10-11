// UI\AVWizard\AVConfigWizard.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "UI\AVWizard\AVConfigWizard.h"
#include ".\avconfigwizard.h"


// CAVConfigWizard

IMPLEMENT_DYNAMIC(CAVConfigWizard, CPropertySheet)
CAVConfigWizard::CAVConfigWizard(UINT nIDCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(nIDCaption, pParentWnd, iSelectPage)
{
}

CAVConfigWizard::CAVConfigWizard(LPCTSTR pszCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(pszCaption, pParentWnd, iSelectPage)
{
}

CAVConfigWizard::~CAVConfigWizard()
{
}


BEGIN_MESSAGE_MAP(CAVConfigWizard, CPropertySheet)
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CAVConfigWizard 消息处理程序

void CAVConfigWizard::setWindowRect( CWnd* pWnd )
{
	CRect rc;
	GetClientRect( &rc );
	rc.bottom = rc.bottom - 40;
	if( pWnd->GetSafeHwnd() )
		pWnd->MoveWindow(rc);
}

void CAVConfigWizard::drawHead( CDC* pDC, CRect rc, CString sText )
{
	pDC->FillSolidRect( rc, RGB(240, 245, 250) );
	CBrush brush;
	brush.CreateSolidBrush( RGB(159, 177, 225) );
	pDC->FrameRect( rc, &brush );
	
	CBitmap bmpBK;
	bmpBK.LoadBitmap( IDB_CONTACTLIST );
	CDC bkDC;
	bkDC.CreateCompatibleDC( pDC );
	bkDC.SelectObject( &bmpBK );
	BITMAP bmp;
	bmpBK.GetBitmap(&bmp);

	CFont* pFont = GetFont();
	LOGFONT lf;
	lf.lfWeight = 210;
	pFont->GetLogFont( &lf );
	CFont font;
	font.CreateFontIndirect( &lf );

	pDC->BitBlt( 1, 1, rc.Width() - 2, 49, &bkDC, bmp.bmWidth - rc.Width(), 0, SRCCOPY );
	pFont = pDC->SelectObject( &font );
	pDC->SetBkMode(TRANSPARENT);
	pDC->SetTextColor( RGB(4, 37, 132) );
	pDC->DrawText( sText, CRect(10, 20, rc.Width() - 60, 40), DT_LEFT );

	pDC->SelectObject( pFont );
	font.DeleteObject();
}

void CAVConfigWizard::createCustomButton( UINT nID, CLinkButton& btn )
{
	CRect rc;
	CString sText;
	CWnd* pWnd = GetDlgItem( nID );
	pWnd->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.bottom = rc.top + 22;
	rc.right = rc.left + 74;
	pWnd->GetWindowText(sText);
	CFont* pFont = this->GetFont();
	LOGFONT lf;
	pFont->GetLogFont( &lf );
	lf.lfWeight = 180;

    btn.Create( sText, pWnd->GetStyle(), rc, this, nID );
	btn.m_fNormal.CreateFontIndirect( &lf );
	lf.lfUnderline = TRUE;
	btn.fUnderline.CreateFontIndirect( &lf );
	btn.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	GetDlgItem(nID)->ShowWindow(FALSE);
}

void CAVConfigWizard::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB(228, 240, 254) );
	// 不为绘图消息调用 CPropertySheet::OnPaint()
}

BOOL CAVConfigWizard::OnInitDialog()
{
	BOOL bResult = CPropertySheet::OnInitDialog();

    this->createCustomButton( ID_WIZBACK, m_btnBack );
	this->createCustomButton( ID_WIZNEXT, m_btnNext );
	this->createCustomButton( ID_WIZFINISH, m_btnFinish );
	this->createCustomButton( IDCANCEL, m_btnCancel );
	m_btnBack.EnableWindow(FALSE);
	CWnd* pWnd = this->GetDlgItem( 0x3026 );
	if( pWnd->GetSafeHwnd() )
		pWnd->ShowWindow(FALSE);

	return bResult;
}
