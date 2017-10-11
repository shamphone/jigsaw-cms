#pragma once


// CIntroductionPP 对话框

class CIntroductionPP : public CPropertyPage
{
	DECLARE_DYNAMIC(CIntroductionPP)

public:
	CIntroductionPP();
	virtual ~CIntroductionPP();

    // 对话框数据
	enum { IDD = IDD_PROPPAGE_INTRO };

protected:
	CBrush m_brush;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()

    virtual BOOL OnSetActive();
    virtual BOOL OnKillActive();
	afx_msg void OnPaint();
public:
	virtual BOOL OnInitDialog();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
