// VolumeBar.cpp : implementation file
//

#include "stdafx.h"
#include "Flvcc.h"
#include "VolumeBar.h"
#include "memdc.h"
#include "UI\meetingroom\roomview.h"
#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CVolumeBar

CVolumeBar::CVolumeBar()
{
	m_bIsInteracting = FALSE;
	m_bIsMouseOnThumb = FALSE;
	m_bIsVertical = FALSE;
	m_nVolumeType = 0;
	m_crBackColor = RGB(238, 241, 249);
	m_crBarColor = RGB(219, 228, 227);
}

CVolumeBar::~CVolumeBar()
{
}

BEGIN_MESSAGE_MAP(CVolumeBar, CStatic)
	//{{AFX_MSG_MAP(CVolumeBar)
	ON_WM_PAINT()
	ON_WM_ERASEBKGND()
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CVolumeBar message handlers

void CVolumeBar::OnPaint() 
{
	if (GetStyle() & TBS_HORZ) {
		m_bIsVertical = FALSE;
	}
	if (GetStyle() & TBS_VERT) {
		m_bIsVertical = TRUE;
	}

	if (m_bIsVertical) {

	} else {

		CPaintDC dc0(this); // device context for painting
		CMemDC dc(&dc0);

		CBrush *pOldBrush = dc.GetCurrentBrush();

		CPen *pOldPen;
		CPen nullPen(PS_NULL, 0, RGB(0, 0, 0));
		pOldPen = dc.SelectObject( &nullPen );

		CRect rect;
		GetClientRect(&rect);

		CBrush bkBrush(m_crBackColor);
		dc.FillRect( &rect, &bkBrush);
	
		//Draw the bar 
		CRect rcBorder = rect;
		rcBorder.DeflateRect(0, 7);
		dc.RoundRect(&rcBorder, CPoint(5, 5));

		// Draw the bar (m_nVolume)
		CBrush dark_gray( this->m_crBarColor );
		dc.SelectObject(&dark_gray);
		CRect r( rcBorder.left + 1, rcBorder.top + 1, rect.Width() * m_nVolume/(m_nMaxVolume + 1), rcBorder.bottom - 1);	
		dc.RoundRect( &r, CPoint(3, 3));	

		//Draw the left and right arrow
		//if( r.right < 7 )
		//	DrawCircle(&dc, CRect(0, 3, 13, 16));
		//else if( rect.right - r.right < 6 )
        //	DrawCircle(&dc, CRect(rect.right - 13, 3, rect.right, 16));
		//else
			DrawCircle(&dc, CRect(r.right - 7, 5, r.right + 6, 18));

		dc.SelectObject(pOldPen);
		dc.SelectObject(pOldBrush);
	}
		
	// Do not call CSliderCtrl::OnPaint() for painting messages
}


BOOL CVolumeBar::OnEraseBkgnd(CDC* pDC) 
{
	//CStatic::OnEraseBkgnd(pDC);

	return TRUE;
}

int CVolumeBar::GetVolume()
{
	if (m_bIsVertical) {
		return m_nMaxVolume - m_nVolume;	
	} else {
		return m_nVolume;
	}
}

void CVolumeBar::SetVolume(int v)
{
	if (m_bIsVertical) {
		m_nVolume = m_nMaxVolume - v;	
	} else {
		m_nVolume = v;
	}
	Invalidate();
}

void CVolumeBar::OnLButtonDown(UINT nFlags, CPoint point) 
{
	if (!m_bIsInteracting) {
		m_bIsInteracting = TRUE;
		m_bIsMouseOnThumb = TRUE;
		SetCapture();
		UpdateVolume(point);
	}
	CStatic::OnLButtonDown(nFlags,point);
}

void CVolumeBar::OnLButtonUp(UINT nFlags, CPoint point) 
{
	if (m_bIsInteracting) {
		m_bIsInteracting = FALSE;
		ReleaseCapture();
		UpdateVolume(point);
		Invalidate();
	}
	CStatic::OnLButtonUp(nFlags,point);
}

void CVolumeBar::OnMouseMove(UINT nFlags, CPoint point) 
{
	if (m_bIsInteracting) {
		UpdateVolume(point);
	}

	if (m_rcThumbRect.PtInRect(point)) {
		if (!m_bIsMouseOnThumb) {
			m_bIsMouseOnThumb = TRUE;
			Invalidate();
		}
	} else {
		if (m_bIsMouseOnThumb) {
			m_bIsMouseOnThumb = FALSE;
			Invalidate();
		}
	}
	CStatic::OnMouseMove(nFlags,point);
}

void CVolumeBar::UpdateVolume(CPoint point)
{
	CRect rect;
	GetClientRect(&rect);
	float vol;
	
	if (m_bIsVertical) {
		int h = rect.Height();
		vol = ( (point.y - h/12) / (float) (rect.Height() - 2 * h / 12) * m_nMaxVolume);
	} else {
		int w = rect.Width();	
		vol = ( (point.x - w/12) / (float) (rect.Width() - 2 * w / 12) * m_nMaxVolume);
	}


	if (vol < 0) vol = 0;
	if (vol > m_nMaxVolume) vol = m_nMaxVolume;

	int oldVolume = m_nVolume;
	m_nVolume = vol;

	((CRoomView*)GetParent())->SetVolume(m_nVolume, m_nVolumeType);
	
	if (oldVolume != m_nVolume) Invalidate();
}

void CVolumeBar::SetOrientation(BOOL vertical)
{
	m_bIsVertical = vertical;
	Invalidate();
}

BOOL CVolumeBar::GetOrientation()
{
	return m_bIsVertical;
}
void CVolumeBar::SetRange(int nMax, int nMin)
{
	m_nMaxVolume = nMax;
	m_nMinVolume = nMin;
}
int CVolumeBar::GetMinVolume()
{
	return m_nMinVolume;
}
int CVolumeBar::GetMaxVolume()
{
	return m_nMaxVolume;
}
void CVolumeBar::SetVolumeType(int type)
{
	m_nVolumeType = type;
}
void CVolumeBar::DrawCircle(CDC* pDC, CRect rc)
{
	CBrush br;
	br.CreateSolidBrush(this->m_crBarColor);
	pDC->SelectObject(&br);
	pDC->Ellipse(&rc);
	rc.DeflateRect(2, 2);
	CBrush brush;
	brush.CreateSolidBrush(RGB(110, 165, 230));
	pDC->SelectObject(&brush);
	pDC->Ellipse(&rc);
}

void CVolumeBar::DrawArrow(CDC* pDC, CPoint ArrowTip, int nDirection/*1 left, 2 right, 3 up, 4 down*/)
{
	COLORREF clrOldTextColor = pDC->GetTextColor();

	pDC->SetTextColor(RGB(80,80,80));

    int nPrevBkMode = pDC->SetBkMode(TRANSPARENT);
    CFont font;
    int ppi = pDC->GetDeviceCaps(LOGPIXELSX);
    int pointsize = MulDiv(75, 96, ppi); // 6 points at 96 ppi
    font.CreatePointFont(pointsize, _T("Marlett"));
    CFont* oldfont = pDC->SelectObject(&font);

	switch(nDirection)
	{
	case (1):
		pDC->TextOut(ArrowTip.x, ArrowTip.y, CString(_T("w"))); // 
		break;
	case (2):
		pDC->TextOut(ArrowTip.x, ArrowTip.y, CString(_T("8"))); // 
		break;
	}
	pDC->SelectObject(oldfont);
    pDC->SetBkMode(nPrevBkMode);
    pDC->SetTextColor(clrOldTextColor);

/*	CPoint ptDest;
	CPen* pPen = pDC->GetCurrentPen();
	LOGPEN logPen;
	pPen->GetLogPen(&logPen);
	pDC->SetPixel(ArrowTip, logPen.lopnColor);

	switch(nDirection)
	{
	case (1):
		{
			ArrowTip -= CPoint(1,1);
			pDC->MoveTo(ArrowTip);

			ptDest = ArrowTip;
			ptDest += CPoint(0,3);
			pDC->LineTo(ptDest);

			ArrowTip -= CPoint(1,1);
			pDC->MoveTo(ArrowTip);

			ptDest = ArrowTip;
			ptDest += CPoint(0,5);
			pDC->LineTo(ptDest);

			ArrowTip -= CPoint(1,1);
			pDC->MoveTo(ArrowTip);

			ptDest = ArrowTip;
			ptDest += CPoint(0,7);
			pDC->LineTo(ptDest);
			break;
		}
	case (2):
		{
			ArrowTip -= CPoint(1,1);
			pDC->MoveTo(ArrowTip);

			ptDest = ArrowTip;
			ptDest -= CPoint(3,0);
			pDC->LineTo(ptDest);

			ArrowTip -= CPoint(1,1);
			pDC->MoveTo(ArrowTip);

			ptDest = ArrowTip;
			ptDest -= CPoint(5,0);
			pDC->LineTo(ptDest);

			ArrowTip -= CPoint(1,1);
			pDC->MoveTo(ArrowTip);

			ptDest = ArrowTip;
			ptDest -= CPoint(7,0);
			pDC->LineTo(ptDest);

			break;
		}
	}
*/
}
