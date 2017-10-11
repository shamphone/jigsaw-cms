// StaticSplitter.cpp : implementation file
//

//this is the splitter in the conversation window... come to think of it, it
//probably should have been called CSplitterStatic. CStaticSplitter seems to
//have a secretly amusing ring to it

#include "stdafx.h"
#include "StaticSplitter.h"
#include "Chatdlg.h"
#include ".\staticsplitter.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CStaticSplitter

CStaticSplitter::CStaticSplitter()
{
	m_SizeArrow = LoadCursor(NULL, IDC_SIZENS);
	m_LButtonDown = false;
}

CStaticSplitter::~CStaticSplitter()
{
}


BEGIN_MESSAGE_MAP(CStaticSplitter, CStatic)
	//{{AFX_MSG_MAP(CStaticSplitter)
	ON_WM_MOUSEMOVE()
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_SETCURSOR()
	//}}AFX_MSG_MAP
	ON_WM_PAINT()
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CStaticSplitter message handlers

void CStaticSplitter::OnMouseMove(UINT nFlags, CPoint point) 
{
	CStatic::OnMouseMove(nFlags, point);

	if (m_LButtonDown)
	{
		CChatDlg *chatdlg = (CChatDlg *)GetParent();
		chatdlg->SplitterMoved(m_InitialPos);
	}
}

void CStaticSplitter::OnLButtonDown(UINT nFlags, CPoint point) 
{
	m_LButtonDown = true;
	SetCapture();
	CStatic::OnLButtonDown(nFlags, point);
}

void CStaticSplitter::OnLButtonUp(UINT nFlags, CPoint point) 
{
	m_LButtonDown = false;
	ReleaseCapture();
	CStatic::OnLButtonUp(nFlags, point);
		CChatDlg *chatdlg = (CChatDlg *)GetParent();
		chatdlg->UpdateSizes();
}

BOOL CStaticSplitter::OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message) 
{
    if (nHitTest == HTCLIENT)
    {
        ::SetCursor(m_SizeArrow);
        return TRUE;
    }
	return CStatic::OnSetCursor(pWnd, nHitTest, message);
}

void CStaticSplitter::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	// 不为绘图消息调用 CStatic::OnPaint()
	CRect rc;
	GetClientRect(&rc);
	dc.FillSolidRect(&rc, RGB(200, 215, 230));
}
