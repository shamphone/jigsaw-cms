#pragma once


// CEditEx

class CEditEx : public CEdit
{
	DECLARE_DYNAMIC(CEditEx)

public:
	CEditEx();
	virtual ~CEditEx();
	void setFontAndColor(LOGFONT lf, COLORREF color);

protected:
	CFont		m_font;
	COLORREF	m_crTextColor;
    COLORREF	m_crBackColor;
	CBrush		m_brushBk;

	DECLARE_MESSAGE_MAP()
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg HBRUSH CtlColor(CDC* pDC, UINT nCtlColor);
};


