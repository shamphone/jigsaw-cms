// UI\pub\DialogEx.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "DialogEx.h"
#include ".\dialogex.h"


// CDialogEx 对话框

IMPLEMENT_DYNAMIC(CDialogEx, CDialog)
CDialogEx::CDialogEx(UINT nID, CWnd* pParent /*=NULL*/)
	: CDialog(nID, pParent)
{
}

CDialogEx::~CDialogEx()
{
}

void CDialogEx::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control( pDX, IDOK, m_btnOK );
	DDX_Control( pDX, IDCANCEL, m_btnCancel );
}


BEGIN_MESSAGE_MAP(CDialogEx, CDialog)
	ON_WM_CTLCOLOR()
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CDialogEx 消息处理程序

BOOL CDialogEx::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_btnOK.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnCancel.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	CRect rc;
	GetClientRect( &rc );
	m_btnOK.MoveWindow( rc.right - 160, rc.bottom - 32, 74, 22 );
	m_btnCancel.MoveWindow( rc.right - 80, rc.bottom - 32, 74, 22 );
	m_brush.CreateSolidBrush( RGB(240, 245, 250) );
    m_crText = RGB( 10, 40, 140 );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

HBRUSH CDialogEx::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC )
	{
		pDC->SetTextColor( m_crText );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}

void CDialogEx::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB(228, 240, 254) );
	CRect rc1 = rc;
	rc1.bottom = rc.bottom - 40;
	CBrush brush;
	brush.CreateSolidBrush( RGB(159, 177, 225) );
	dc.FillSolidRect( rc1, RGB(240, 245, 250) );
	dc.FrameRect( rc1, &brush );
	
	CBitmap bmpBK;
	bmpBK.LoadBitmap( IDB_CONTACTLIST );
	CDC bkDC;
	bkDC.CreateCompatibleDC( &dc );
	bkDC.SelectObject( &bmpBK );
	BITMAP bmp;
	bmpBK.GetBitmap(&bmp);

	CFont* pFont = GetFont();
	LOGFONT lf;
	lf.lfWeight = 210;
	pFont->GetLogFont( &lf );
	CFont font;
	font.CreateFontIndirect( &lf );

	dc.BitBlt( 1, 1, rc.Width() - 2, 49, &bkDC, bmp.bmWidth - rc.Width(), 0, SRCCOPY );
	pFont = dc.SelectObject( &font );
	dc.SetBkMode(TRANSPARENT);
	dc.SetTextColor( m_crText );
	dc.DrawText( m_sDescription, CRect(10, 10, rc.Width() - 55, 40), DT_LEFT );

	dc.SelectObject( pFont );
	font.DeleteObject();
	// 不为绘图消息调用 CDialog::OnPaint()
}
