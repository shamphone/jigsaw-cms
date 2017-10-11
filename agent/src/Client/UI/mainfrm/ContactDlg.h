#pragma once

#include "..\pub\treectrlex.h"
#include "..\pub\SkinScroll\SkinScrollWnd.h"
#include "..\pub\SkinScroll\SkinScrollBar.h"

class MsnContact
{
public:
	HTREEITEM m_hItem;
	string m_email;
	string m_nick;
};

// ContactDlg.h : header file
//

/////////////////////////////////////////////////////////////////////////////
// CContactDlg dialog
class CMainFrame;
class LServer;
class ContactMgr;
class CAddContactWizard;
class CStatusDlg;
class Contact;
class ContactGroup;

class CContactDlg : public CDialog
{
// Construction
public:
	CContactDlg(CMainFrame* pMainFrame, CWnd* pParent = NULL);   // standard constructor
	~CContactDlg();
	// 对话框数据
	enum { IDD = IDD_CONTACT_DLG };
	
//ContactMgr调用的接口
public:
	// 添加组
	HTREEITEM notifyAddGroup( ContactGroup* pGroup );
	// 添加联系人
	HTREEITEM notifyAddContact( Contact* pContact );
	// 删除联系人
	void notifyRemoveContact( HTREEITEM hItem );
	// 有人申请加我为联系人
	void notifyApplyContact(__int64 userId, string name, __int64 groupId, LServer* pServer);
	// 申请被同意
	void notifyAgreeContact(string name);
	// 申请被拒绝
	void notifyRejectContact(string name);
	// 没有找到指定email的用户
	void notifyUserNotFound(string email);
	// 收到查询的用户信息
	void notifySearchContactResult(string realName, string email, int status, __int64 uid);
	// 得到公共联系人项
	HTREEITEM getCommonGroupItem( LServer* pServer );
	// 得到私有联系人项
	HTREEITEM getPrivateGroupItem( LServer* pServer );

public:
	void addMsnServer();
	void addMsnContact( HANDLE handle, CString sNick, CString sEmail, CString sGroup, int nStatus );
	void delMsnContact( HANDLE handle );
	void updateMsnContact( HANDLE handle, CString sNick, CString sEmail, CString sGroup, int nStatus );
	HTREEITEM addMsnGroup( CString sGroup, int nId );
	void delMsnGroup( HTREEITEM hItem );
	HTREEITEM getMsnGroup( CString sGroup );
private:
	void convertMsnStatus( int nStatus, int& nFlvccStatus, CString& sStatus );
	void convertFlvccStatus( int nStatus, int& nMsnStatus, CString& sStatus );
	void clearMsnContacts();
	void startMiranda( CString sProfile );
	CString getProfileByAccount( CString sAccount );
	bool isMsnGroupExist( CString sGroup );
	void sendCmdToMiranda( DWORD dwCmdType, const void* lParam = 0 );
	HTREEITEM getTreeItemByHandle( HANDLE handle );
	HANDLE getHandleByTreeItem( HTREEITEM hItem );
	map<HANDLE, MsnContact*> m_msnContactMap;
	vector<HTREEITEM> m_msnGroups;
	HTREEITEM	m_hItemMsn;
	UINT m_nMsnStatus;

public:
	LServer* getCurSelServer();
	void addServer( LServer* pServer );
	void modifyServer( LServer* pServer );
	void removeServer( LServer* pServer );
	void setCurrentUser(CString name);
	void setSelectedServer( HTREEITEM hItem ) {	this->m_TreeCtrl.SelectItem( hItem ); };
	//发送留言
    void sendLeaveword(__int64 uid = 0);
	//发送email
	void sendEmail(__int64 uid = 0);
	USERID getCurSelContactId();
	USERID getCurSelGroupId();

	void createInstantConference();
	BOOL canModifyCurSel();

private:
	ContactMgr* getCurSelContactMgr();
	void createStatusDlg();
	int FindMenuItem(CMenu* Menu, LPCTSTR MenuString);
	void setGroupItemText(HTREEITEM hItem, bool bAddContact, int nStatus);

public:
	CTreeCtrlEx			m_TreeCtrl;

private:
	CMainFrame*			m_pMainFrame;
	CStatusDlg*			m_pStatusDlg;
	CImageList			m_ImageList;
//	CTreeCtrlEx			m_TreeCtrl;
	HTREEITEM			m_htiDrag, m_htiDrop, m_htiOldDrop, m_hPrivateGroup;
	bool				m_bLDragging;
	UINT				m_idTimer;
	CImageList*			m_pDragImage;
	CAddContactWizard*	m_pAddContactWizard;

	HBITMAP				m_hBmpScrollBar;

	CString				m_sMoveToGroup;
	Contact*			m_pMoveContact;
	CString				m_strExpandedItem;

protected:
	virtual BOOL OnInitDialog();
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	virtual void OnCancel() { };
	virtual void OnOK() { };
	DECLARE_MESSAGE_MAP()
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnDblclkContactTree(NMHDR* pNMHDR, LRESULT* pResult);
	afx_msg void OnNMRclickContactTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnTvnBegindragContactTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnTvnSelchangedContactTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnTimer(UINT nIDEvent);
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	afx_msg void OnSetFocus(CWnd* pOldWnd);

	afx_msg void OnSendLeaveword();
	afx_msg void OnSendEmail();
	afx_msg void OnMoveContactToGroup();
	afx_msg void OnMenuSelect(UINT nItemID, UINT nFlags, HMENU hSysMenu);
	afx_msg void OnPaint();
	afx_msg void OnTvnKeydownContactTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg LRESULT OnApplyContact( WPARAM wParam, LPARAM lParam );
	afx_msg LRESULT OnMsnApplyContact( WPARAM wParam, LPARAM lParam );

public:
	afx_msg void OnAddGroup();
	afx_msg void OnAddContact();
	afx_msg void OnDelContact();
	afx_msg void OnDelGroup();
	afx_msg void OnBeginInstantConf();
	afx_msg void OnRenameGroup();
	afx_msg void OnCheckContactInfo();
	afx_msg void OnViewChatlog();
	afx_msg void OnCopycontacttouser();
	afx_msg void OnCopycontacttoall();
	afx_msg void OnCopycontacttogroup();
	afx_msg void OnAddfromcommon();
	afx_msg void OnCheckserver();
	afx_msg void OnRemoveserver();
	afx_msg void OnAddserver();
	afx_msg void OnLoginserver();
	afx_msg void OnCancelloginserver();
	afx_msg void OnLogoutserver();
	afx_msg void OnRegisternew();
	afx_msg void OnLoginanother();
	afx_msg BOOL OnCopyData(CWnd* pWnd, COPYDATASTRUCT* pCopyDataStruct);
	afx_msg void OnMsnLogin();
	afx_msg void OnMsnLogout();
	afx_msg void OnMsnAccount();
	afx_msg void OnMsnSendMsg();
	afx_msg void OnMsnFindadd();
	afx_msg void OnMsnDelcontact();
	afx_msg void OnMsnAddgroup();
	afx_msg void OnMsnDelgroup();
	afx_msg void OnMsnRenamegroup();
	afx_msg void OnMsnMovecontact();
	afx_msg void OnMsnViewhistory();
	afx_msg void OnMsnContactinfo();
	afx_msg void OnMsnStatus(UINT nID);
};
