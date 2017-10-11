#pragma once
#include "..\pub\linkbutton.h"

class LServer;
// CLoginDialog 对话框

class CLoginDialog : public CDialog
{
	DECLARE_DYNAMIC(CLoginDialog)

public:
	CLoginDialog(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CLoginDialog();
// 对话框数据
	enum { IDD = IDD_LOGIN_DIALOG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	virtual void OnCancel();

public:
	void saveLoginHistory();
	static CString EncodePassword(CString strPass);
	static CString DecodePassword(CString strPass);

	// 得到会议服务器地址
	CString getServerName();
	CString getServerIP();
	int getServerPort();
	void addServer(CString sServerName, CString sIP, int nPort);

private:
	void getLoginHistory();
	void saveLoginServer();
	static unsigned char m_fixedkey[];

protected:

	DECLARE_MESSAGE_MAP()

	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnBnClickedRegisterBtn();
	afx_msg void OnCbnSelchangeUsername();
	afx_msg void OnBnClickedRememberpassword();
	afx_msg void OnBnClickedRememberusername();
public:
	afx_msg void OnBnClickedLoginBtn();

private:
	LServer*	m_pDefaultServer;
	BOOL		m_bMemPwd;	
	BOOL		m_bMemName;
	CLinkButton m_btnRegister;
	CLinkButton m_btnLogin;
	CBrush		m_brush;
	COLORREF	m_crBackGround;
	COLORREF	m_crText;
};
