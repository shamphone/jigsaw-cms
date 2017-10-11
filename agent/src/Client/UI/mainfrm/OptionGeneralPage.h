#pragma once


// COptionGeneralPage 对话框

class COptionGeneralPage : public CDialog
{
	DECLARE_DYNAMIC(COptionGeneralPage)

public:
	COptionGeneralPage(CWnd* pParent = NULL);
	virtual ~COptionGeneralPage();

	void apply();

	// 对话框数据
	enum { IDD = IDD_OPTION_GENERAL_DLG };

private:
	bool m_bCtrl;
	bool m_bShift;
	int  m_nKey;
	CBrush m_brush;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	DECLARE_MESSAGE_MAP()
	virtual BOOL PreTranslateMessage(MSG* pMsg);
	virtual BOOL OnInitDialog();
	virtual void OnOK() { };
	virtual void OnCancel() { };
	void writeIni();
	afx_msg void OnBnClickedSelectReceivePath();
	afx_msg void OnBnClickedAutorun();
	afx_msg void OnBnClickedAutologin();
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
