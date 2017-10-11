#pragma once


// CVideoShowWnd

class CVideoShowWnd : public CStatic
{
	DECLARE_DYNAMIC(CVideoShowWnd)

public:
	CVideoShowWnd();
	virtual ~CVideoShowWnd();

	FCObjImage		m_img;

protected:
	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnPaint();
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
};


