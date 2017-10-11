#pragma once
// SkinScrollWnd.h : header file
//
#include "SkinScrollBar.h"

/////////////////////////////////////////////////////////////////////////////
// CSkinScrollWnd window

class CSkinScrollWnd : public CWnd
{
// Construction
public:
	CSkinScrollWnd();

// Attributes
public:
	int			m_nScrollWid;
	CSkinScrollBar	m_sbHorz,m_sbVert;
	CWnd		m_wndLimit;
	HBITMAP		m_hBmpScroll;
	BOOL		m_bOp;
	int			m_nAngleType;

public:
	WNDPROC		m_funOldProc;

// Overrides
protected:
	virtual void PostNcDestroy();

// Implementation
public:
	BOOL SkinWindow(CWnd *pWnd,HBITMAP hBmpScroll);
	virtual ~CSkinScrollWnd();

	// Generated message map functions
protected:
	afx_msg LRESULT OnDestMove(WPARAM wParam,LPARAM lParam);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg void OnPaint();
	afx_msg void OnTimer(UINT nIDEvent);
	DECLARE_MESSAGE_MAP()
};

CSkinScrollWnd* SkinWndScroll(CWnd *pWnd,HBITMAP hBmpScroll);
