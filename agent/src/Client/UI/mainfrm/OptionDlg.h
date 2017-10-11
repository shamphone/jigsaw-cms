#pragma once

#include "..\pub\linkbutton.h"

class CImageTabWnd;
class COptionVideoConfigPage;
class COptionGeneralPage;

// COptionDlg 对话框

class COptionDlg : public CDialog
{
	DECLARE_DYNAMIC(COptionDlg)

public:
	COptionDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~COptionDlg();

	void setReadOnly( BOOL bReadOnly ) { m_bReadOnly = bReadOnly; };

// 对话框数据
	enum { IDD = IDD_OPTION_DLG };

protected:
	BOOL m_bReadOnly;
	CImageTabWnd*  m_pImageTab;
	COptionVideoConfigPage* m_pDlgVideoConfig;
	COptionGeneralPage*     m_pDlgGeneral;

	CLinkButton m_btnApply;
	CLinkButton m_btnClose;
	CLinkButton m_btnOK;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnCancel() { } ;
	virtual void OnOK() { } ;

	DECLARE_MESSAGE_MAP()
	afx_msg void OnBnClickedApplyBtn();
	afx_msg void OnBnClickedCloseBtn();
	afx_msg void OnDestroy();
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnPaint();
	afx_msg void OnClose();
	afx_msg void OnBnClickedOk();
};
