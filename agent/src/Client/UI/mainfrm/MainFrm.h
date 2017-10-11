// MainFrm.h : interface of the CMainFrame class
//
#pragma once

#include "LoginDialog.h"

class Conference;
class LServer;
class CImageTabWnd;
class CContactDlg;
class CConferenceDlg;
class CSysinfoDlg;
class CMeetingRoomFrame;
class CAdBGDlg;

class CMainFrame : public CFrameWnd
{
	DECLARE_DYNAMIC(CMainFrame)	
public:
	CMainFrame();
	virtual ~CMainFrame();

	virtual BOOL PreCreateWindow(CREATESTRUCT& cs);
	virtual BOOL OnCmdMsg(UINT nID, int nCode, void* pExtra, AFX_CMDHANDLERINFO* pHandlerInfo);

#ifdef _DEBUG
	virtual void AssertValid() const;
	virtual void Dump(CDumpContext& dc) const;
#endif

/*
 * LServer消息处理调用
 */
	// 登录成功
	void notifyLoginSuccessed( LServer* pServer );
	// 登录失败
	void notifyLoginFailed( const char* reason, LServer* pServer );
	// 检查客户端版本
	void notifyCheckVersion( BOOL bHasNewVersion, BOOL bNeedUpdate, LServer* pServer );
	// 设置广告地址
	void notifyAdvertizementAddress( CString sAdHomepage, CString sAdMainframe, CString sAdRoomframe );

	CContactDlg* getContactDlg() { return m_pContactDlg; };
	CConferenceDlg* getConferenceDlg() { return m_pConferenceDlg; };
	CSysinfoDlg* getSysinfoDlg() { return m_pSysmsgDlg; };
	LServer* getServerById( UINT nId );
	LServer* isServerExist( string sName, string sIP, int nPort, CString& str );
	BOOL isDuplicateServer( LServer* pServer, string sName, string sIP, int nPort, CString& str );
	LServer* addServer( string sName, string sIP, int nPort, string sUsername, string sPassword, BOOL bDefault = FALSE );

/*
 * 界面调用
 */
	void OnViewAndModifyServer(LServer* pServer);
	afx_msg void OnAddServer();
	void OnRemoveServer(LServer* pServer);
	BOOL OnLoginServer(LServer* pServer, BOOL bAutoLogin = FALSE);
	void OnCancelLoginServer(LServer* pServer);
	void OnLogoutServer(LServer* pServer);
	void OnRegisterNewUser(LServer* pServer);
	void OnLoginAnother(LServer* pServer);
    
	//客户端是否有服务器已经登录或正在登录
	BOOL hasServerLoginOrLogining();
	//设置系统托盘图标和提示文字
	void SetTaskIcon(LServer* pServer, BOOL bShowCurUser);
	void SetTaskIcon( CString sServer, CString sUsername, CString sRealname, int nStatus, BOOL bShowCurUser );
	LServer* getDefaultServer();
	//得到当前选中的服务器
	LServer* getCurSelServer();

private:
	// 加载服务器
	void loadServer();
	void modifyServer( LServer* pServer );
	BOOL getUsernameAndPassword( string& sUsername, string& sPassword, BOOL& bMember );
	void saveServer();
	BOOL getServerInfo( CString str, string& sName, string& sIP, int& nPort, string& sUsername, string& sPassword );
	void removeServer( LServer* pServer );
	//创建标签栏
	void CreateImgTab();	
	//删除提示框
	void removeNotifiers();
	//处理命令行参数
	BOOL processCmdline(CString sCmd);
	//升级后用来转换数据临时的方法,升级前后安装目录要相同
	void transformData( LServer* pServer );

public:
    // 生成服务器id
	static UINT getServerId() { return m_nServerIdSeed++; };
	CString getRoomAdUrl() { return m_sAdRoomframe; };
	BOOL isLoginDlgVisable() { return m_dlgLogin.IsWindowVisible(); };

private:
	// 服务器列表
	map<UINT, LServer*> m_servers;
	// 用来生成服务器id
	static UINT		m_nServerIdSeed;
	// 广告地址
	CString			m_sAdHomepage;
	CString			m_sAdMainframe;
	CString			m_sAdRoomframe;
	//位图标签窗口
	CImageTabWnd*	m_pImageTab;		
	//联系人对话框
	CContactDlg*	m_pContactDlg;		
	//会议列表对话框
	CConferenceDlg*	m_pConferenceDlg;	
	//系统消息对话框
	CSysinfoDlg*	m_pSysmsgDlg;		
	//系统托盘
	NOTIFYICONDATA	m_TaskIcon;
	//弹出提示框序号
	int				m_nNotifyId;
	//弹出提示框列表
	CPtrList		m_pTaskNotifyList;
	//登录对话框
	CLoginDialog	m_dlgLogin;		
	// 广告对话框
	CAdBGDlg*		m_pAdBG;
	//窗口最小大小
	int				m_nMinX, m_nMinY;
	BOOL			m_bShowAdDlg;
	BOOL			m_bFirstLogin;

public:
	DECLARE_MESSAGE_MAP()
	afx_msg int OnCreate(LPCREATESTRUCT lpCreateStruct);
	afx_msg void OnSetFocus(CWnd *pOldWnd);		
	afx_msg void OnClose();
	afx_msg void OnTimer(UINT nIDEvent);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnSizing(UINT fwSide, LPRECT pRect);
	afx_msg BOOL OnCopyData(CWnd* pWnd, COPYDATASTRUCT* pCopyDataStruct);

	//弹出提示框关闭的消息处理，从列表中清除
	afx_msg LRESULT OnTaskbarNotifierClosed(WPARAM wParam,LPARAM lParam);
	//需要弹出提示框的消息处理，弹出提示框
	afx_msg LRESULT OnReceivedSysMsg(WPARAM wParam,LPARAM lParam);
	//weblauncher的消息处理
	afx_msg LRESULT OnReceivedMsgFromWeblauncher(WPARAM wParam,LPARAM lParam);
	//系统托盘的消息处理
	afx_msg LRESULT OnShellTrayIcon(WPARAM wParam, LPARAM lParam);
    afx_msg LRESULT OnDisconnectServer(WPARAM wParam, LPARAM lParam);
    afx_msg LRESULT OnLoginFailed(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnCheckVersion(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnDuplicateLogin(WPARAM wParam, LPARAM lParam);

	//文件菜单项
	afx_msg void OnLogin();
	afx_msg void OnUpdateLogin(CCmdUI *pCmdUI);
	afx_msg void OnLogout();
	afx_msg void OnUpdateLogout(CCmdUI *pCmdUI);
	afx_msg void OnLookLeavewords();
	afx_msg void OnUpdateLookLeavewords(CCmdUI *pCmdUI);
	afx_msg void OnOpenReceived();
	afx_msg void OnCloseWnd();

	//联系人菜单项
	afx_msg void OnSearchContact();	
	afx_msg void OnUpdateSearchContact(CCmdUI *pCmdUI);
	afx_msg void OnSendLeaveword();
	afx_msg void OnUpdateSendLeaveword(CCmdUI *pCmdUI);
	afx_msg void OnSendEmail();
	afx_msg void OnUpdateSendEmail(CCmdUI *pCmdUI);
	afx_msg void OnDelContact();
	afx_msg void OnUpdateDelContact(CCmdUI *pCmdUI);
	afx_msg void OnPropertyContact();
	afx_msg void OnUpdatePropertyContact(CCmdUI *pCmdUI);
	afx_msg void OnCreateContactGroup();
	afx_msg void OnUpdateCreateContactGroup(CCmdUI *pCmdUI);
	afx_msg void OnDelContactGroup();
	afx_msg void OnUpdateDelContactGroup(CCmdUI *pCmdUI);
	afx_msg void OnRenameContactGroup();
	afx_msg void OnUpdateRenameContactGroup(CCmdUI *pCmdUI);
	afx_msg void OnCommoncontactmgr();
	afx_msg void OnUpdateCommoncontactmgr(CCmdUI *pCmdUI);

	//会议菜单项
	afx_msg void OnCreateInstantconf();
	afx_msg void OnUpdateCreateInstantconf(CCmdUI *pCmdUI);
	afx_msg void OnCreateformalconf();
	afx_msg void OnUpdateCreateformalconf(CCmdUI *pCmdUI);
	afx_msg void OnJoinConference();
	afx_msg void OnUpdateJoinConference(CCmdUI* pCmdUI);
	afx_msg void OnQuitConference();
	afx_msg void OnUpdateQuitConference(CCmdUI* pCmdUI);
	afx_msg void OnLookConference();
	afx_msg void OnUpdateLookConference(CCmdUI *pCmdUI);
	afx_msg void OnViewConfhistory();
	afx_msg void OnUpdateViewConfhistory(CCmdUI *pCmdUI);
//	afx_msg void OnConfmodemgr();
//	afx_msg void OnUpdateConfmodemgr(CCmdUI *pCmdUI);
//	afx_msg void OnSystemrolemgr();
//	afx_msg void OnUpdateSystemrolemgr(CCmdUI *pCmdUI);

	//工具菜单项
	afx_msg void OnAlwaysTop();
	afx_msg void OnUpdateAlwaysTop(CCmdUI *pCmdUI);
	afx_msg void OnSelfinfomgr();
	afx_msg void OnUpdateSelfinfomgr(CCmdUI *pCmdUI);
	afx_msg void OnSendsystemmsg();
	afx_msg void OnUpdateSendsystemmsg(CCmdUI *pCmdUI);
	afx_msg void OnAVWizard();
	afx_msg void OnUpdateAvWizard(CCmdUI *pCmdUI);
	afx_msg void OnSetpicture();
	afx_msg void OnOption();

	//帮助菜单项
	afx_msg void OnHelpContent();
	afx_msg void OnAbout();

	//系统托盘弹出菜单项
	afx_msg void OnMenuHide();
	afx_msg void OnUpdateMenuHide(CCmdUI *pCmdUI);
	afx_msg void OnTrayMenuOpen();
	afx_msg void OnUpdateMenuShow(CCmdUI *pCmdUI);
	afx_msg void OnTrayMenuExit();
	afx_msg void OnAddserver2();
};
