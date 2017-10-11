#pragma once


// CMarkWnd

class CMarkWnd : public CWnd
{
	DECLARE_DYNAMIC(CMarkWnd)

public:
	CMarkWnd();
	virtual ~CMarkWnd();

	void create();

public:
	//»­±Ê´ÖÏ¸
	int m_nPenSize;
	//»­±ÊÑÕÉ«
	COLORREF m_PenColor;
    BOOL MouseFlag;
	//×ø±ê
	int m_nY;
	int m_nX;

protected:
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnPenBlue();
	afx_msg void OnPenRed();
	afx_msg void OnPenGreen();
	afx_msg void OnPenL();
	afx_msg void OnPenM();
	afx_msg void OnPenS();
	afx_msg void OnMarkExit();
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
protected:
	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
};


