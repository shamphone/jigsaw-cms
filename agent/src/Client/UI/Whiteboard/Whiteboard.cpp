// Whiteboard.cpp : 实现文件
//

#include "stdafx.h"
#include "Whiteboard.h"
#include ".\whiteboard.h"
#include "Draw.h"
#include "structure.h"
#include "WhiteboardDlg.h"
#include "WhiteboardText.h"

#define WHITEBOARD_BUFFER_SIZE 1000
// CWhiteboard 对话框

IMPLEMENT_DYNAMIC(CWhiteboard, CDialog)
CWhiteboard::CWhiteboard(CWnd* pParent /*=NULL*/, UINT nID, UINT nBoardNum)
	: CDialog(nID, pParent)
{
	m_pParent = (CWhiteboardDlg*) pParent;
	m_nBoardNum = nBoardNum;
	m_usrCurrentObject = NULL;
	m_bShowgrid = false;
	m_boardSize = CSize( 1280, 1024 );
	m_nDrawTimerId = 1;
	m_hBitmap = NULL;
	m_pTrackText = NULL;
	m_bShowText = FALSE;
}

CWhiteboard::~CWhiteboard()
{
	if(m_usrCurrentObject)
	{
		delete m_usrCurrentObject;
		m_usrCurrentObject = NULL;
	}
	while( m_drawObjects.size() > 0 )
	{
		CDrawObject* pObject = m_drawObjects.front();
		m_drawObjects.pop();
		delete pObject;
		pObject = NULL;
	}
	delete m_pTrackText;
}

void CWhiteboard::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CWhiteboard, CDialog)
	ON_WM_CTLCOLOR()
	ON_WM_PAINT()
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	ON_WM_KILLFOCUS()
	ON_WM_LBUTTONDBLCLK()
	ON_WM_TIMER()
	ON_WM_SETCURSOR()
END_MESSAGE_MAP()


// CWhiteboard 消息处理程序

BOOL CWhiteboard::OnInitDialog()
{
	CDialog::OnInitDialog();
	SetTimer( m_nDrawTimerId, 1000, NULL );

	// 初始化画板背景
    CDC* pDC = GetDC();
	CBitmap cb;
	cb.CreateCompatibleBitmap( pDC, this->m_boardSize.cx, this->m_boardSize.cy );
	CDC memDC;
	memDC.CreateCompatibleDC( pDC );
	memDC.SelectObject( &cb );
	memDC.FillSolidRect( 0, 0, this->m_boardSize.cx, this->m_boardSize.cy, RGB( 255, 255, 255 ) );
	m_hBitmap = (HBITMAP)cb;
	cb.Detach();
	memDC.DeleteDC();

    m_pTrackText = new CWhiteboardText( this );
	m_pTrackText->setFontAndColor( m_pParent->m_lf, m_pParent->m_crCurrentColor );
	m_brush.CreateSolidBrush( RGB( 255, 255, 255 ) );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

HBRUSH CWhiteboard::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);
	if( pWnd == this )
	{
		hbr = m_brush;
	}
	return hbr;
}

void CWhiteboard::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	if( m_hBitmap )
	{
		CDC memDC;
		memDC.CreateCompatibleDC( &dc );
		CBitmap cb;
		cb.Attach( m_hBitmap );
		memDC.SelectObject( &cb );
		CRect rc;
		GetClientRect( &rc );
		dc.BitBlt( 0,0, rc.Width(), rc.Height(), &memDC, 0, 0, SRCCOPY );
		cb.Detach();
	}
	if( m_pParent->m_nMouseMode == IDT_TEXT && m_bShowText )
		this->m_pTrackText->Draw( &dc );
	/////////////////////////////////////////
	if( m_bShowgrid ) 
		ShowGrid();
	// 不为绘图消息调用 CDialog::OnPaint()
}

void CWhiteboard::OnLButtonDown(UINT nFlags, CPoint point)
{
	int nMouseMode = m_pParent->m_nMouseMode;
	if( nMouseMode == IDT_SELECT )
	{
		return CDialog::OnLButtonDown(nFlags, point);
	}
	if( nMouseMode == IDT_TEXT )
	{
		if( m_bShowText )
		{
			if( m_pTrackText->Track(this, point) )
			{
				InvalidateRect(NULL);
				return CDialog::OnLButtonDown(nFlags, point);
			}
		}
		CDC* pDC = GetDC();
		if( m_bShowText )
		{
			m_bShowText = FALSE;
			CString str = m_pTrackText->getText();
			m_pTrackText->showWindow( FALSE );
			if( str != "" )
			{
				m_pTrackText->setText( "" );
				CDrawText* pDraw = new CDrawText(m_pParent->m_lf, str, m_pParent->m_crCurrentColor, m_pParent->getOutStream() );
				pDraw->NewPoint( m_pTrackText->m_rect.left, m_pTrackText->m_rect.top );
				pDraw->AddPoint( m_pTrackText->m_rect.right, m_pTrackText->m_rect.bottom );
				this->SaveCurrentDraw( pDraw );
				delete pDraw;
			}
		}
		else
		{
			int nWidth = m_pTrackText->m_rect.Width();
			int nHeight = m_pTrackText->m_rect.Height();
			m_pTrackText->m_rect = CRect( point.x, point.y, point.x + nWidth, point.y + nHeight );
			m_pTrackText->Draw( pDC );
			m_bShowText = TRUE;
		}
		ReleaseDC( pDC );
		CRect rc = m_pTrackText->m_rect;
		rc.InflateRect( 4, 4 );
		InvalidateRect( rc );
		return CDialog::OnLButtonDown(nFlags, point);
	}

	CRect client;
	GetClientRect( &client );
	ClientToScreen( &client );
	//::ClipCursor( &client );

	int crColor = m_pParent->m_crCurrentColor;
	int nPenWidth = m_pParent->m_nPenWidth;
	bool bFill = m_pParent->m_bFill;
	switch( nMouseMode )
	{
	case IDT_LINE:
		m_usrCurrentObject = new CDrawLine(crColor, nPenWidth, m_pParent->getOutStream());
		m_usrCurrentObject->NewPoint(point.x, point.y);
		break;
	case IDT_CURVE:
		m_usrCurrentObject = new CDrawCurve(crColor, nPenWidth, m_pParent->getOutStream());
		m_usrCurrentObject->NewPoint(point.x, point.y);
		break;
	case IDT_RECTANGLE:
		m_usrCurrentObject = new CDrawRect(crColor, nPenWidth, bFill, m_pParent->getOutStream());
		m_usrCurrentObject->NewPoint(point.x, point.y);
		break;
	case IDT_ELLIPSE:
		m_usrCurrentObject = new CDrawEllipse(crColor, nPenWidth, bFill, m_pParent->getOutStream());
		m_usrCurrentObject->NewPoint(point.x, point.y);
		break;
	case IDT_CIRCLE:
		m_usrCurrentObject = new CDrawCircle(crColor, nPenWidth, bFill, m_pParent->getOutStream());
		m_usrCurrentObject->NewPoint(point.x, point.y);
		break;
	case IDT_POLY:
		if(m_usrCurrentObject)
		{
			m_usrCurrentObject->AddPoint(point.x, point.y);
		}
		else
		{
			m_usrCurrentObject = new CDrawPoly(crColor, nPenWidth, m_pParent->getOutStream());
			m_usrCurrentObject->NewPoint(point.x, point.y);
		}
		break;
	case ID_RUBBER:
		m_usrCurrentObject = new CDrawCurve( RGB( 255, 255, 255 ), 12, m_pParent->getOutStream() );
		m_usrCurrentObject->NewPoint(point.x, point.y);
		break;
	default:
		break;
	}
	CDialog::OnLButtonDown(nFlags, point);
}

void CWhiteboard::OnMouseMove(UINT nFlags, CPoint point)
{
	if( m_usrCurrentObject )
	{
		if( m_pParent->m_nMouseMode == IDT_CURVE || m_pParent->m_nMouseMode == ID_RUBBER )
		{
			if( ((CDrawCurve*)m_usrCurrentObject)->getPointNumber() == POINTNUMBER )
			{
				this->SaveCurrentDraw( m_usrCurrentObject );
			}
		}
		CDC* pDC = GetDC();
        m_usrCurrentObject->MoveAt(pDC, point.x, point.y);	
		ReleaseDC(pDC);
	}
	CDialog::OnMouseMove(nFlags, point);
}

void CWhiteboard::OnLButtonUp(UINT nFlags, CPoint point)
{
	if( m_usrCurrentObject )
	{
		int nMouseMode = m_pParent->m_nMouseMode;
		if( nMouseMode == IDT_LINE 
			|| nMouseMode == IDT_RECTANGLE 
			|| nMouseMode == IDT_ELLIPSE 
			|| nMouseMode == IDT_CIRCLE 
			|| nMouseMode == IDT_CURVE
			|| nMouseMode == ID_RUBBER ) 
		{
			this->SaveCurrentDraw( m_usrCurrentObject );
			//::ClipCursor(NULL);
			delete m_usrCurrentObject;
			m_usrCurrentObject = NULL;
			if( nMouseMode != IDT_CURVE && nMouseMode != ID_RUBBER )
				InvalidateRect( NULL );
		}
    }
	CDialog::OnLButtonUp(nFlags, point);
}

void CWhiteboard::OnLButtonDblClk(UINT nFlags, CPoint point)
{
	if( m_pParent->m_nMouseMode == IDT_POLY && m_usrCurrentObject )
	{
		CDC* pDC = GetDC();
		m_usrCurrentObject->EndPoint( pDC );
        this->SaveCurrentDraw( m_usrCurrentObject );
		//::ClipCursor(NULL);
		delete m_usrCurrentObject;
		m_usrCurrentObject = NULL;
		ReleaseDC(pDC);

		InvalidateRect( NULL );
	}
	CDialog::OnLButtonDblClk(nFlags, point);
}

void CWhiteboard::OnKillFocus(CWnd* pNewWnd)
{
	CDialog::OnKillFocus(pNewWnd);
	//::ClipCursor(NULL);
	if( m_usrCurrentObject )
	{
		delete m_usrCurrentObject;
		m_usrCurrentObject = NULL;
	}
	InvalidateRect(NULL);
}

void CWhiteboard::OnTimer(UINT nIDEvent)
{
	if( nIDEvent == m_nDrawTimerId )
	{
		if( !m_usrCurrentObject && m_drawObjects.size() > 0 )
		{
			while( m_drawObjects.size() > 0 )
			{
				CDrawObject* pObject = m_drawObjects.front();
				m_drawObjects.pop();
				this->SaveCurrentDraw( pObject );
				delete pObject;
				pObject = NULL;
			}
			InvalidateRect( NULL );
		}
	}
	CDialog::OnTimer(nIDEvent);
}

BOOL CWhiteboard::OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message)
{
	if( pWnd == this && this->m_bShowText )
	{
		CPoint point;
		::GetCursorPos( &point );
		ScreenToClient( &point );
		CRect rc;
		m_pTrackText->GetTrueRect( &rc );
		if( rc.PtInRect( point ) )
            return m_pTrackText->SetCursor(this, nHitTest);
	}
	if( pWnd == this && m_pParent->m_nMouseMode == ID_RUBBER )
	{
		SetCursor( AfxGetApp()->LoadCursor( IDC_RUBBER ) );
		return TRUE;
	}
	return CDialog::OnSetCursor(pWnd, nHitTest, message);
}

void CWhiteboard::SaveCurrentDraw( CDrawObject* pDrawObject )
{
	if( pDrawObject == NULL )
	{
		ASSERT(FALSE);
		return;
	}
	pDrawObject->SendDraw();

	CDC* pDC = GetDC();
	CBitmap cb;
	cb.Attach( m_hBitmap );
	CDC memDC;
	memDC.CreateCompatibleDC( pDC );
	memDC.SelectObject( &cb );
	pDrawObject->Draw( &memDC );
	m_hBitmap = (HBITMAP)cb.GetSafeHandle();
	cb.Detach();
	memDC.DeleteDC();
	ReleaseDC( pDC );
}

void CWhiteboard::ShowGrid()
{
	CDC *pdc = GetDC();
	CRect		rect;
	GetWindowRect(rect);
	int w=rect.Width();
	int h=rect.Height();

	COLORREF XorColor = pdc->GetBkColor()^RGB(128,128,128) ;
	CPen pen(PS_SOLID, 1, XorColor), *oldpen;
	int oldmode = pdc->GetROP2();
	oldpen = pdc->SelectObject(&pen);
	pdc->SetROP2(R2_XORPEN);

	for(int i=0;i<12;i++)
	{
		pdc->MoveTo(0,(i*w)/12);
		pdc->LineTo(w,i*w/12);
    	pdc->MoveTo(w*i/12,0);
		pdc->LineTo(w*i/12,h);
	}

	pdc->SetROP2(oldmode);
	pdc->SelectObject(oldpen);
	pen.DeleteObject();
}

void CWhiteboard::appendDraw( CDrawObject* pObject )
{
    m_drawObjects.push( pObject );
}

void CWhiteboard::setTextFontAndColor(LOGFONT lf, COLORREF color)
{
	this->m_pTrackText->setFontAndColor(lf, color);
}

void CWhiteboard::hideTextWindow()
{
	if( m_bShowText )
	{
		m_bShowText = FALSE;
		this->m_pTrackText->showWindow(FALSE);
		CRect rc = m_pTrackText->m_rect;
		rc.InflateRect( 4, 4 );
		InvalidateRect( rc );
	}
}

void CWhiteboard::saveToFile( CString strFile )
{
	CDC* pDC = GetDC();
	CBitmap cbm;
	cbm.Attach( m_hBitmap );

	CDC memDC;
	memDC.CreateCompatibleDC( pDC );
	memDC.SelectObject( &cbm );
	BITMAP  bmp;
	cbm.GetBitmap( &bmp );
	DWORD size = bmp.bmWidthBytes * bmp.bmHeight;
	LPSTR lpData = (LPSTR)GlobalAlloc( GMEM_FIXED|GMEM_ZEROINIT, size );

	BITMAPINFOHEADER  bih;
	bih.biBitCount = bmp.bmBitsPixel;
	bih.biClrImportant = 0;
	bih.biClrUsed = 0;
	bih.biCompression = 0;
	bih.biHeight = bmp.bmHeight;
	bih.biPlanes = 1;
	bih.biSize = sizeof(BITMAPINFOHEADER);
	bih.biSizeImage = size;
	bih.biWidth = bmp.bmWidth;
	bih.biXPelsPerMeter = 0;
	bih.biYPelsPerMeter = 0;
	GetDIBits( memDC.GetSafeHdc(), cbm, 0, bih.biHeight, lpData, (BITMAPINFO*)&bih, DIB_RGB_COLORS );

	BITMAPFILEHEADER   bfh;
	bfh.bfReserved1 = bfh.bfReserved2 = 0;
	bfh.bfType = ((WORD)('M'<< 8)|'B');
	bfh.bfSize = 54 + size;
	bfh.bfOffBits = 54;

	CFile file;
	if( file.Open( strFile, CFile::modeCreate | CFile::modeWrite ) )
	{
		file.Write(&bfh, sizeof(BITMAPFILEHEADER));
		file.Write(&bih, sizeof(BITMAPINFOHEADER));
		file.Write(lpData, size);
		file.Close();
		AfxMessageBox( "保存成功" );
	}
	else
	{
		AfxMessageBox( "保存失败" );
	}
	GlobalFree(lpData);
	cbm.Detach();
	memDC.DeleteDC();
	ReleaseDC( pDC );
}
