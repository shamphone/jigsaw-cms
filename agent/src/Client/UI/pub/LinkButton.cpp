// LinkButton.cpp : implementation file
//

#include "stdafx.h"
#include "LinkButton.h"
#include "resource.h"
#include ".\linkbutton.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CLinkButton

CLinkButton::CLinkButton()
{
	bHighlight = bLBtnDown = false;
	hHand = AfxGetApp()->LoadCursor(IDC_LINK);
	m_crNormal = RGB( 0, 0, 0 );
	m_crHighlight = RGB( 0, 0, 255 );
	m_bHasUnderline = TRUE;
}

CLinkButton::~CLinkButton()
{
	if (fUnderline.GetSafeHandle()) fUnderline.DeleteObject();
}


BEGIN_MESSAGE_MAP(CLinkButton, CButton)
	//{{AFX_MSG_MAP(CLinkButton)
	ON_WM_MOUSEMOVE()
	ON_WM_SETCURSOR()
	ON_WM_TIMER()
	ON_WM_LBUTTONUP()
	ON_WM_LBUTTONDOWN()
	ON_WM_ERASEBKGND()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CLinkButton message handlers

void CLinkButton::DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct) 
{
	// 获取一个CDC指针
	CDC* pDC = CDC::FromHandle(lpDrawItemStruct->hDC);

	//定义按钮区域并初始化
	CRect rect(lpDrawItemStruct->rcItem);

	//设置背景模式
	COLORREF oc = pDC->GetTextColor();
	int iObk = pDC->SetBkMode(TRANSPARENT);

	//初始化按钮状态
	UINT state = lpDrawItemStruct->itemState;

	CFont * pOldFont = NULL;
	if( m_fNormal.GetSafeHandle() )
	{
		pOldFont = pDC->SelectObject( &m_fNormal );
	}

	int iYOffset = 0, iXOffset = 0;

	CString strText;
	GetWindowText(strText);

	rect.top  += iYOffset;
	rect.left += iXOffset;


	if (state & ODS_DISABLED)
	{		
		if(this->m_bmpDisable.GetSafeHandle())
		{
			CDC tempDC;
			tempDC.CreateCompatibleDC(pDC);
			tempDC.SelectObject(&m_bmpDisable);
			pDC->BitBlt(0, 0, rect.Width(), rect.Height(), &tempDC, 0, 0, SRCCOPY);
		}
		pDC->SetTextColor(GetSysColor(COLOR_GRAYTEXT));
		pDC->DrawText(strText, rect, DT_CENTER | DT_VCENTER | DT_SINGLELINE);
	}
	else
	{
		if (bHighlight)//光标在按钮上
		{
			//未按下按钮
			if(this->m_bmpHover.GetSafeHandle())
			{
				CDC tempDC;
				tempDC.CreateCompatibleDC(pDC);
				tempDC.SelectObject(&m_bmpHover);
				pDC->BitBlt(0, 0, rect.Width(), rect.Height(), &tempDC, 0, 0, SRCCOPY);
			}
			if (state & ODS_SELECTED)
			{
				//按下按钮
				if(this->m_bmpClicked.GetSafeHandle())
				{
					CDC tempDC;
					tempDC.CreateCompatibleDC(pDC);
					tempDC.SelectObject(&m_bmpClicked);
					pDC->BitBlt(0, 0, rect.Width(), rect.Height(), &tempDC, 0, 0, SRCCOPY);
				}
			}

			//字体颜色
			pDC->SetTextColor( m_crHighlight );

			//加下画线（也可以用其他字体）
			if (fUnderline.GetSafeHandle() == NULL)
			{
				CFont * pFont = GetFont();
				ASSERT(pFont);
				LOGFONT lf;
				pFont->GetLogFont(&lf);
				lf.lfUnderline = m_bHasUnderline;
				fUnderline.CreateFontIndirect(&lf);		
			}

			pOldFont = pDC->SelectObject(&fUnderline);
		}
		else 
		{
			if(this->m_bmpNormal.GetSafeHandle())
			{
				CDC tempDC;
                tempDC.CreateCompatibleDC(pDC);
				tempDC.SelectObject(&m_bmpNormal);
				pDC->BitBlt(0, 0, rect.Width(), rect.Height(), &tempDC, 0, 0, SRCCOPY);
			}

			pDC->SetTextColor( m_crNormal );
		}
		pDC->DrawText(strText, rect, DT_CENTER | DT_VCENTER | DT_SINGLELINE);
		if (pOldFont) pDC->SelectObject(pOldFont);
	}
}


void CLinkButton::OnMouseMove(UINT nFlags, CPoint point) 
{
	//设置一个定时器
	SetTimer(1,10,NULL);
	
	CButton::OnMouseMove(nFlags, point);
}

BOOL CLinkButton::OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message) 
{
	if (bHighlight) 
	{
		::SetCursor(hHand);
		return true;
	}
	
	return CButton::OnSetCursor(pWnd, nHitTest, message);
}

void CLinkButton::OnTimer(UINT nIDEvent) 
{
	static bool pPainted = false;
	POINT pt;
	GetCursorPos(&pt);
	CRect rect;
	GetWindowRect (rect);
	if (bLBtnDown)	
	{		
		KillTimer (1);
		if (pPainted) InvalidateRect (NULL);		
		pPainted = FALSE;		
		return;	
	}

	if (!rect.PtInRect (pt))	
	{		
		bHighlight = false;
		KillTimer (1);

		if (pPainted)			
			InvalidateRect(NULL);

		pPainted = false;
		return;	
	}
	else
	{
		bHighlight = true;
		if (!pPainted)
		{
			pPainted = true;
			InvalidateRect(NULL);
		}
	}

	CButton::OnTimer(nIDEvent);
}

void CLinkButton::OnLButtonUp(UINT nFlags, CPoint point) 
{
	bLBtnDown = false;
	if (bHighlight)	
	{
		bHighlight = false;
		InvalidateRect(NULL);
	}
	
	CButton::OnLButtonUp(nFlags, point);
}

void CLinkButton::OnLButtonDown(UINT nFlags, CPoint point) 
{
	bLBtnDown = true;
	
	CButton::OnLButtonDown(nFlags, point);
}

BOOL CLinkButton::OnEraseBkgnd(CDC* pDC) 
{
	COLORREF cr = GetSysColor(COLOR_3DFACE);
	int r = GetRValue(cr);
	int g = GetGValue(cr);
	int b = GetBValue(cr);
	if (r > 1) r -= 2;
	if (g > 1) g -= 2;
	if (r < 3 && g < 3 && b < 253) b += 2;
	COLORREF cr1 = RGB(r,g,b);
	CRect rc;
	GetClientRect(rc);
	pDC->FillSolidRect(rc, cr1);
	
	return CButton::OnEraseBkgnd(pDC);
}

void CLinkButton::setBitmaps(UINT nBitmapNormal, UINT nBitmapHover, UINT nBitmapClicked, UINT nBitmapDisable)
{
	if(nBitmapNormal)
	{
		if(m_bmpNormal.GetSafeHandle())
			m_bmpNormal.Detach();
		m_bmpNormal.LoadBitmap(nBitmapNormal);
	}
	if(nBitmapHover)
	{
		if(m_bmpHover.GetSafeHandle())
			m_bmpHover.Detach();
		m_bmpHover.LoadBitmap(nBitmapHover);
	}
	if(nBitmapClicked)
	{
		if(m_bmpClicked.GetSafeHandle())
			m_bmpClicked.Detach();
		m_bmpClicked.LoadBitmap(nBitmapClicked);
	}
	if(nBitmapDisable)
	{
		if(m_bmpDisable.GetSafeHandle())
			m_bmpDisable.Detach();
		m_bmpDisable.LoadBitmap(nBitmapDisable);
	}
}

void CLinkButton::setTextColor( COLORREF crNormal, COLORREF crHighlight )
{
	m_crNormal = crNormal;
	m_crHighlight = crHighlight;
}

void CLinkButton::PreSubclassWindow()
{
	ModifyStyle(0, BS_OWNERDRAW);

	CButton::PreSubclassWindow();
}
