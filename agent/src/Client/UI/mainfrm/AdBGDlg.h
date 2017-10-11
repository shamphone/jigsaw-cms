#pragma once

#include "AdvertizeDlg.h"
// CAdBGDlg 对话框

class CAdBGDlg : public CDialog
{
	DECLARE_DYNAMIC(CAdBGDlg)

public:
	CAdBGDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CAdBGDlg();
	void navigate( CString url );
	void showAdDlg();

// 对话框数据
	enum { IDD = IDD_ADVERTIZEBG_DLG };

protected:
	CAdvertizeDlg	m_dlgAd;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual void OnCancel() { };
	virtual void OnOK() { };
	afx_msg void OnPaint();
	afx_msg void OnSize(UINT nType, int cx, int cy);
	virtual BOOL OnInitDialog();

};
