#pragma once
// VolumeBar.h : header file
//
// Written by Cristina Cañero (cristina@iti.gr)
// Copyright (c) 2003.
//
// This code may be used in compiled form in any way you desire. This
// file may be redistributed unmodified by any means PROVIDING it is 
// not sold for profit without the authors written consent, and 
// providing that this notice and the authors name is included. If 
// the source code in  this file is used in any commercial application 
// then a simple email would be nice.
//
// This file is provided "as is" with no expressed or implied warranty.
// The author accepts no liability if it causes any damage whatsoever.


/////////////////////////////////////////////////////////////////////////////
// CVolumeBar window

class CVolumeBar : public CStatic
{
private:
	BOOL m_bIsInteracting;
	BOOL m_bIsMouseOnThumb;
	BOOL m_bIsVertical;
	
	int m_nVolume;
	int m_nMaxVolume;
	int m_nMinVolume;
	int m_nVolumeType;

	CRect m_rcThumbRect;
	COLORREF m_crBackColor;
	COLORREF m_crBarColor;
	
// Construction
public:
	CVolumeBar();
	virtual ~CVolumeBar();
// Operations
public:
	void SetVolumeType(int type);
	void SetRange(int nMax, int nMin);
	int GetMinVolume();
	int GetMaxVolume();
	void SetVolume(int v);
	int GetVolume();
	BOOL GetOrientation();			// true : vertical, false : horizontal
	void SetOrientation(BOOL vertical = TRUE);

protected:
	//{{AFX_MSG(CVolumeBar)
	afx_msg void OnPaint();
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()
private:
	void UpdateVolume(CPoint point);
	void DrawArrow(CDC* pDC, CPoint ArrowTip, int nDirection = 1);
	void DrawCircle(CDC* pDC, CRect rc);
};

