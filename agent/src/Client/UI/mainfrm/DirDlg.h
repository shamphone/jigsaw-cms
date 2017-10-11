#pragma once
#include "..\pub\dirtreectrl.h"

// CDirDlg 对话框

class CDirDlg : public CDialog
{
	DECLARE_DYNAMIC(CDirDlg)

public:
	CDirDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CDirDlg();
	CString m_sPath;
// 对话框数据
	enum { IDD = IDD_DIR_DLG };

protected:

	CDirTreeCtrl m_TreeCtrl;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	afx_msg void OnTvnSelchangedDirTree(NMHDR *pNMHDR, LRESULT *pResult);
};
