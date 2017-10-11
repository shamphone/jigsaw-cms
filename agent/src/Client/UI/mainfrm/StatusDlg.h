#pragma once
#include "../pub/linkbutton.h"

// CStatusDlg 对话框

class CStatusDlg : public CDialog
{
	DECLARE_DYNAMIC(CStatusDlg)

public:
	CStatusDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CStatusDlg();

// 对话框数据
	enum { IDD = IDD_STATUS_DLG };


public:
	// 显示名称
	CString			m_sDisplayName;

private:
	// 添加联系人按钮
	CLinkButton		m_btnAddContact;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnOK() {};
	virtual void OnCancel() {};

	DECLARE_MESSAGE_MAP()
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnBnClickedAddcontactBtn();
	afx_msg void OnPaint();
};
