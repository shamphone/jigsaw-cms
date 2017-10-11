#pragma once

#include "..\pub\DialogEx.h"

// CLeavewordDlg 对话框

class CLeavewordDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CLeavewordDlg)

public:
	CLeavewordDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CLeavewordDlg();

// 对话框数据
	enum { IDD = IDD_LEAVEWORD_DLG };

	CString m_strLeaveword;
	CRect   m_showRect;

protected:

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	afx_msg void OnBnClickedOk();
	virtual BOOL OnInitDialog();
};
