#pragma once

#include "..\pub\DialogEx.h"
#include "VideoShowWnd.h"

// CSetmypictureDlg 对话框

class CSetmypictureDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CSetmypictureDlg)

public:
	CSetmypictureDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CSetmypictureDlg();

// 对话框数据
	enum { IDD = IDD_SETMYPICTURE_DLG };

	CString m_fileName;
	bool    m_bDisplayNow;
	CString m_sIniFileName;

protected:
	CLinkButton m_btnReset;
	CVideoShowWnd m_videoWindow;
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	afx_msg void OnBnClickedSelectfileBtn();
	afx_msg void OnPaint();
	virtual BOOL OnInitDialog();
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedReset();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
