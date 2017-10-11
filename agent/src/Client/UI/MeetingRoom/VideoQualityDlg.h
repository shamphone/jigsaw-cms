#pragma once

#include "..\pub\DialogEx.h"

// CVideoQualityDlg 对话框

class CVideoQualityDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CVideoQualityDlg)

public:
	CVideoQualityDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CVideoQualityDlg();

// 对话框数据
	enum { IDD = IDD_VIDEOQUALITY_DLG };

protected:
	BOOL apply();

	CLinkButton m_btnApply;
	int m_nFrameRate;
	int m_nKeyFrame;
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnBnClickedApply();
};
