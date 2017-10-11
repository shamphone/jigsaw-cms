#pragma once

#include "LinkButton.h"

// CDialogEx 对话框

class CDialogEx : public CDialog
{
	DECLARE_DYNAMIC(CDialogEx)

public:
	CDialogEx(UINT nID, CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CDialogEx();
	CString m_sDescription;

protected:
	CLinkButton m_btnOK, m_btnCancel;
	CBrush m_brush;
	COLORREF m_crText;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnPaint();
};
