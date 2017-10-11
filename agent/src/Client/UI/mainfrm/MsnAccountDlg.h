#pragma once

#include "..\pub\DialogEx.h"

// CMsnAccountDlg 对话框

class CMsnAccountDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CMsnAccountDlg)

public:
	CMsnAccountDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CMsnAccountDlg();
	BOOL m_bReadOnly;
	CString m_sAccount;
	CString m_sPassword;

// 对话框数据
	enum { IDD = IDD_MSNACCOUNT_DLG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	virtual void OnOK();
};
