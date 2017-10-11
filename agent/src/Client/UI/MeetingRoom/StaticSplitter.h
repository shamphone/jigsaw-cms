#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// StaticSplitter.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CStaticSplitter window

class CStaticSplitter : public CStatic
{
// Construction
public:
	CStaticSplitter();

// Attributes
public:

// Operations
public:
// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CStaticSplitter)
	//}}AFX_VIRTUAL

// Implementation
public:
	virtual ~CStaticSplitter();

private:
	HCURSOR m_SizeArrow;
	bool m_LButtonDown;
	void OnMouseEnter(UINT nFlags, CPoint point);
	void OnMouseLeave(UINT nFlags, CPoint point);
	int m_InitialPos;
	// Generated message map functions
protected:
	//{{AFX_MSG(CStaticSplitter)
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message);
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnPaint();
};

