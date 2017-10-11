#pragma once

#include "resource.h"
#include "RichEditCtrlEx.h"
#include "staticsplitter.h"
#include "..\pub\linkbutton.h"
#include "..\pub\SkinScroll\SkinScrollWnd.h"
#include "..\pub\SkinScroll\SkinScrollBar.h"
#include "TabItem.h"

// CChatDlg 对话框
class CChatDlg : public CDialog, public CTabItem
{
	DECLARE_DYNAMIC(CChatDlg)

public:
	CChatDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CChatDlg();
// 对话框数据
	enum { IDD = IDD_CHAT_DLG };

	void setCharFormat( CHARFORMAT cf );
	void sendWritingMessageFlag();
	void setWritingMessageFlag( __int64 userId, bool bFlag );
	// 添加聊天信息
	void appendMsg(CString name, CString msg);
	void appendSysMsg( CString msg );
	// 分割条移动
	void SplitterMoved(int pos);
	// 设置分割条位置
	void SetSplitterPos(int pos);
	// 更新窗口控件
	void UpdateSizes();
	void layout();

private:
	void saveChatMsg( CString date, CString time, CString sender, CString receiver, CString msg, CString file );

public:
	// 聊天人的名字
	CString				m_sChatWithName;
	// 分割条位置
	int					m_SplitterPos;

private:
	// 显示聊天信息
	CRichEditCtrlEx		m_ChatMsg;
	// 输入聊天信息
	CRichEditCtrlEx		m_SendMsg;
	// 可拖动的分割条
	CStaticSplitter		m_Splitter;
	int					m_cy;
	int					m_nMinHeight;
	CBrush				m_brush;
	CLinkButton			m_btnFont;
	CLinkButton			m_btnSend;
	bool				m_bIsSendWritingMsg;
	CString				m_strWritingUser;
	HBITMAP				m_hBmpScrollBar;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	virtual void OnCancel();
	
	DECLARE_MESSAGE_MAP()
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnBnClickedFont();
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);

public:
	afx_msg void OnBnClickedSend();
	afx_msg void OnSetFocus(CWnd* pOldWnd);
};
