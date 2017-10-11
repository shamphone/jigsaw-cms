#pragma once

#include "..\pub\DialogEx.h"

class LServer;
// CUserInfoDlg 对话框

class CUserInfoDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CUserInfoDlg)

public:
	CUserInfoDlg(LServer* pServer, CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CUserInfoDlg();

// 对话框数据
	enum { IDD = IDD_USERINFO_DLG };

protected:
	CLinkButton m_btnApply;
	LServer* m_pServer;

	BOOL validateUserInfo( CString& message );
	BOOL sendChangeUserInfo( CString& message );

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	DECLARE_MESSAGE_MAP()
	afx_msg void OnBnClickedApplyBtn();
	virtual void OnCancel();
	virtual void OnOK();
	virtual BOOL OnInitDialog();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnBnClickedChangepassword();
};
