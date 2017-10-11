#pragma once

#include "..\pub\DialogEx.h"

// CContactInfoDlg 对话框

class CContactInfoDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CContactInfoDlg)

public:
	CContactInfoDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CContactInfoDlg();

// 对话框数据
	enum { IDD = IDD_CONTACT_INFO_DLG };

	CString m_sName;
	CString m_sEmail;
	CString m_sStatus;
	CString m_sGroup;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();

	DECLARE_MESSAGE_MAP()
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
