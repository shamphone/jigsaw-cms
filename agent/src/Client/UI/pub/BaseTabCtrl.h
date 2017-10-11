
#pragma once

// BaseTabCtrl.h : header file
//

enum
{
	BTC_NONE = 0,
	BTC_TABS = 1,
	BTC_ALL = 2,
};

class CBaseTabCtrl : public CTabCtrl
{
// Construction
public:
	CBaseTabCtrl(int nType = BTC_NONE);
	BOOL EnableDraw(int nType = BTC_ALL);
	void SetBkgndColor(COLORREF color);

protected:
	COLORREF m_crBack;
	int m_nDrawType;

// Implementation
public:
	virtual ~CBaseTabCtrl();

	// Generated message map functions
protected:
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnPaint();
	DECLARE_MESSAGE_MAP()

protected:
	virtual void DrawMainBorder(LPDRAWITEMSTRUCT lpDrawItemStruct);
	virtual void DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct);
	virtual void DrawItemBorder(LPDRAWITEMSTRUCT lpDrawItemStruct);
	virtual COLORREF GetTabColor(BOOL bSelected = FALSE);
	virtual COLORREF GetTabTextColor(BOOL bSelected = FALSE);
	virtual void PreSubclassWindow();
};
