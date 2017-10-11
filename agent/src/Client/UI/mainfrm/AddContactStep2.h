#pragma once


// CAddContactStep2 对话框

class CAddContactStep2 : public CPropertyPage
{
	DECLARE_DYNAMIC(CAddContactStep2)

public:
	CAddContactStep2();
	virtual ~CAddContactStep2();

// 对话框数据
	enum { IDD = IDD_ADD_CONTACT_STEP2_DLG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
public:
	virtual LRESULT OnWizardBack();
	virtual BOOL OnSetActive();

	void userNotFound(CString s);
	void userInfo(string realName, string email, int status, __int64 uid);
	void setShowText(CString s) { m_sText = s; };

private:
	CBrush m_brush;
	CString m_sText;
	__int64 m_uid;
public:
	virtual LRESULT OnWizardNext();
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	virtual BOOL OnInitDialog();
};
