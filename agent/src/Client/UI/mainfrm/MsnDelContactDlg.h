#pragma once

#include "..\pub\DialogEx.h"

// CMsnDelContactDlg 对话框

class CMsnDelContactDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CMsnDelContactDlg)

public:
	CMsnDelContactDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CMsnDelContactDlg();

// 对话框数据
	enum { IDD = IDD_MSN_DELCONTACT_DLG };
	BOOL m_bHide;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedMsnHidecontact();
	afx_msg void OnBnClickedMsnDelcontact();
};
