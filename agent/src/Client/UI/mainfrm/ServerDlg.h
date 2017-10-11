#pragma once

#include "..\pub\DialogEx.h"

#define ADD_SERVER		1
#define VIEW_SERVER		2
#define MODIFY_SERVER	3

// CServerDlg 对话框
class LServer;

class CServerDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CServerDlg)

public:
	CServerDlg(int nType, LServer* pServer = NULL, CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CServerDlg();

// 对话框数据
	enum { IDD = IDD_SERVER_DLG };
	CString m_sTitle;
	CString m_sServerName;
	CString m_sServerIP;
	int m_nPort;

protected:
	LServer* m_pServer;
	int m_nType;

	BOOL validateServerInfo();
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedOk();
};
