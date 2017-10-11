#pragma once

// LinkButton.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CLinkButton window

class CLinkButton : public CButton
{
// Construction
public:
	CLinkButton();
	virtual ~CLinkButton();

// Operations
public:
	void setBitmaps(UINT nBitmapNormal, UINT nBitmapHover = 0, UINT nBitmapClicked = 0, UINT nBitmapDisable = 0);
	void setTextColor( COLORREF crNormal, COLORREF crHighlight );
	void setTextUnderline( BOOL bFlag ) { m_bHasUnderline = bFlag; };

// Overrides
public:
	virtual void DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct);
	virtual void PreSubclassWindow();
	CFont fUnderline;
	CFont m_fNormal;

protected:
	BOOL m_bHasUnderline;
	HCURSOR hHand;
	bool bLBtnDown;
	bool bHighlight;
	CBitmap m_bmpNormal, m_bmpHover, m_bmpClicked, m_bmpDisable;
	COLORREF m_crNormal, m_crHighlight;

// Generated message map functions
protected:
	//{{AFX_MSG(CLinkButton)
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message);
	afx_msg void OnTimer(UINT nIDEvent);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()
};

