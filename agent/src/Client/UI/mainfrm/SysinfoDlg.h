#pragma once

#include "..\pub\treectrlex.h"
#include "..\pub\SkinScroll\SkinScrollWnd.h"
#include "..\pub\SkinScroll\SkinScrollBar.h"

class CMainFrame;
class LServer;
// SysinfoDlg.h : header file
//
#define LEAVEWORD 1
#define CONFNOTICE 2
#define SYSTEMMSG 3
/////////////////////////////////////////////////////////////////////////////
// CSysinfoDlg dialog

class CSysinfoDlg : public CDialog
{
// Construction
public:
	CSysinfoDlg(CMainFrame* pMainFrame, CWnd* pParent = NULL);   // standard constructor
	enum { IDD = IDD_SYSINFO_DLG };

public:
	/* ContactMgr调用接口 */
	// 收到留言
	void notifyLeaveword(string content, string senderName, int index, string sendDate, LServer* pServer);
	// 收到会议通知
	void notifyConfNotice(string title, string content, string sendDate, __int64 confId, LServer* pServer);
	// 收到系统公告
	void notifySystemMsg(string title, string content, string sendDate, LServer* pServer);

	/* 界面调用 */
	// 得到当前选中的服务器
	LServer* getCurSelServer();
	// 添加服务器
	void addServer( LServer* pServer );
	// 修改服务器
	void modifyServer( LServer* pServer );
	// 删除服务器
	void removeServer( LServer* pServer );
    // 查看所有消息
	void lookMessage( LServer* pServer, int nType );
	// 显示留言
	void showContactMessage();

	void setSelectedServer( HTREEITEM hItem ) {	this->m_TreeCtrl.SelectItem( hItem ); };

private:
	void loadLeaveword( LServer* pServer );
	void loadSystemMsg( LServer* pServer );
	void loadConfNotice( LServer* pServer );
	// 显示子项的数目
	void setChildrenNum( HTREEITEM hItem, int nDelta );
	int getItemIndex( HTREEITEM hParent, HTREEITEM hItem );
	void delMessageFromFile( int nIndex, int nType );
	void delAllMessageFromFile( int nType );

protected:
	// 主界面窗口
	CMainFrame*		m_pMainFrame;
	// 树控件
	CTreeCtrlEx		m_TreeCtrl;
	// 树控件中显示的图标
	CImageList		m_ImageList;
	// 滚动条
	HBITMAP			m_hBmpScrollBar;

	DECLARE_MESSAGE_MAP()
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	virtual BOOL OnInitDialog();
	virtual void OnCancel() { };
	virtual void OnOK() { };
	afx_msg void OnPaint();
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	afx_msg void OnSetFocus(CWnd* pOldWnd);
	afx_msg void OnNMDblclkSysinfoTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnNMRclickSysinfoTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnTvnSelchangedSysinfoTree(NMHDR *pNMHDR, LRESULT *pResult);

	// 弹出菜单的处理，调用主界面窗口m_pMainFrame的方法
	afx_msg void OnLoginserver();
	afx_msg void OnCancelloginserver();
	afx_msg void OnLogoutserver();
	afx_msg void OnRegisternew();
	afx_msg void OnLoginanother();
	afx_msg void OnAddserver();
	afx_msg void OnCheckserver();
	afx_msg void OnRemoveserver();

	afx_msg void OnLookmessage();
	afx_msg void OnDelmessage();
	afx_msg void OnDelallmessage();
};

