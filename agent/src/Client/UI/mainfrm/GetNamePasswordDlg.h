#pragma once

#include "..\pub\DialogEx.h"

// GetNamePasswordDlg 对话框

class GetNamePasswordDlg : public CDialogEx
{
	DECLARE_DYNAMIC(GetNamePasswordDlg)

public:
	GetNamePasswordDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~GetNamePasswordDlg();

// 对话框数据
	enum { IDD = IDD_GETNAMEPASSWORD_DLG };
	CString m_sUsername;
	CString m_sPassword;
	BOOL m_bRememberme;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedOk();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
