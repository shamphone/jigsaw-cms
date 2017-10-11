#pragma once


// CAddContactStep3 对话框

class CAddContactStep3 : public CPropertyPage
{
	DECLARE_DYNAMIC(CAddContactStep3)

public:
	CAddContactStep3();
	virtual ~CAddContactStep3();

// 对话框数据
	enum { IDD = IDD_ADD_CONTACT_STEP3_DLG };

protected:
	CBrush m_brush;
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	virtual BOOL OnSetActive();
	afx_msg void OnPaint();
	virtual BOOL OnInitDialog();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
