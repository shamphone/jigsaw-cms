#pragma once

#include "..\pub\DialogEx.h"

class Conference;

// CConfInfoDlg 对话框

class CConfInfoDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CConfInfoDlg)

public:
	CConfInfoDlg(Conference* pConference, CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CConfInfoDlg();
	// 对话框数据
	enum { IDD = IDD_CONF_INFO_DLG };

	CString m_strTitle;
	CString m_strName;
	CString m_strDesc;

protected:
	Conference* m_pConference;
	vector<string> m_confDocs;

	CListCtrl m_ListCtrl;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	DECLARE_MESSAGE_MAP()
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnNMClickConfDocs(NMHDR *pNMHDR, LRESULT *pResult);
};
