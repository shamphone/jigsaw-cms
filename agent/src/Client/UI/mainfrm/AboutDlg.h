/////////////////////////////////////////////////////////////////////////////
// CAboutDlg dialog used for App About
#pragma once

#include "..\pub\linkbutton.h"

class CAboutDlg : public CDialog
{
public:
	CAboutDlg();
	// Dialog Data
	enum { IDD = IDD_ABOUTBOX };

	// ClassWizard generated virtual function overrides
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support

	// Implementation
protected:
	CLinkButton m_btnOK;
	CBrush m_brush;

	DECLARE_MESSAGE_MAP()
	afx_msg void OnBnClickedBtnOk();
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnBnClickedOk();
};
