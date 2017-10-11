#pragma once

#include "..\pub\DialogEx.h"

// CAddContactGroup 对话框

class CAddContactGroup : public CDialogEx
{
	DECLARE_DYNAMIC(CAddContactGroup)

public:
	CAddContactGroup(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CAddContactGroup();

// 对话框数据
	enum { IDD = IDD_ADDCONTACTGROUP };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()

private:
	CString m_sTitle;
	CString m_sGroupName;

public:
	void setTitle(CString sTitle);
	void setDescription(CString sDescription);
	CString getGroupName();
	afx_msg void OnBnClickedOk();
	virtual BOOL OnInitDialog();
};
