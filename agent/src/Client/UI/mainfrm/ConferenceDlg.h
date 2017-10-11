#pragma once

#include "..\pub\treectrlex.h"
#include "..\pub\SkinScroll\SkinScrollWnd.h"
#include "..\pub\SkinScroll\SkinScrollBar.h"

// ConferenceDlg.h : header file
//
class CMainFrame;
class CMeetingRoomFrame;
class LServer;
class Conference;
class ConferenceMgr;
/////////////////////////////////////////////////////////////////////////////
// CConferenceDlg dialog

class CConferenceDlg : public CDialog
{
// Construction
public:
	CConferenceDlg(CMainFrame* pMainFrame, CWnd* pParent = NULL);   // standard constructor
	enum { IDD = IDD_CONFERENCE_DLG };

	void setMyPicture( string fileName, BOOL bShow );
	void addServer( LServer* pServer );
	void modifyServer( LServer* pServer );
	void removeServer( LServer* pServer );
	LServer* getCurSelServer();
	void setSelectedServer( HTREEITEM hItem ) {	this->m_TreeCtrl.SelectItem( hItem ); };

	// 是否可以编辑，删除会议
	BOOL canEditConference();
	BOOL canDeleteConference();
	// 是否可以加入或退出会议,判断依据: 窗口是否可见，是否选中进行中的会议项，是否已经加入会议
	BOOL canJoinOrQuitConference( BOOL bCanJoin );
	// 是否可以查看会议信息
	BOOL canLookConference();
	// 服务器是否有打开的会议室
	BOOL hasRoomOpened( LServer* pServer );
	// 所有服务器是否有打开的会议室
	BOOL hasRoomOpened();
	// 判断会议室是否存在
	BOOL isRoomExist( LServer* pServer, __int64 conferenceId );
	// 显示指定会议室
	void showRoom( LServer* pServer, __int64 conferenceId );
	// 退出所有会议室
	void quitAllRoom( LServer* pServer );
	void serverDisconnect( LServer* pServer );
	void serverReconnect( LServer* pServer, Conference* pConference );


	// 添加一个会议
	void notifyAddConference( Conference* pConference, LServer* pServer );
	// 结束一个会议
	void notifyConferenceFinish( Conference* pConference, LServer* pServer );
	// 删除一个个会议
	void notifyDeleteConference( Conference* pConference, LServer* pServer );
	// 修改会议
	void notifyModifyConference( Conference* pConference, LServer* pServer );
	// 会议开始
	void notifyStartConference( Conference* pConference, LServer* pServer );
	// 被邀请加入会议
	void notifyInviteConference( Conference* pConference, LServer* pServer, int nShow );
	// 被驱逐出会议
	void notifyKickUserFromConference( __int64 confId, LServer* pServer );

protected:
	// 创建会议室界面
    CMeetingRoomFrame* createRoomFrame( LServer* pServer, string sConferenceName, int nShow );
	ConferenceMgr* getCurSelConferenceMgr();
	void setChildrenNum( HTREEITEM hItem, int nDelta );
	// 得到指定会议的会议室界面
	CMeetingRoomFrame* getRoomById( LServer* pServer, __int64 conferenceId );

	// 会议室列表
	typedef vector<CMeetingRoomFrame*> RoomList;
	typedef map<LServer*, RoomList> RoomMap;
	RoomMap			m_rooms;
	CMainFrame*		m_pMainFrame;
	CTreeCtrlEx		m_TreeCtrl;
	CImageList		m_ImageList;
	HBITMAP			m_hBmpScrollBar;

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV support
	virtual BOOL OnInitDialog();
	afx_msg void OnSize(UINT nType, int cx, int cy);
	DECLARE_MESSAGE_MAP()
	afx_msg void OnNMRclickConferenceTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnNMDblclkConferenceTree(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnTvnSelchangedConferenceTree(NMHDR *pNMHDR, LRESULT *pResult);
	virtual void OnOK() {};
	virtual void OnCancel() {};
	afx_msg void OnPaint();
	afx_msg void OnShowWindow(BOOL bShow, UINT nStatus);
	afx_msg void OnSetFocus(CWnd* pOldWnd);
	afx_msg LRESULT OnRoomClose(WPARAM wParam, LPARAM lParam);
	afx_msg LRESULT OnKickFromRoom(WPARAM wParam, LPARAM lParam);

public:
	afx_msg void OnLookConference();
	afx_msg void OnJoinConference();
	afx_msg void OnQuitConference();
	afx_msg void OnLoginserver();
	afx_msg void OnCancelloginserver();
	afx_msg void OnLogoutserver();
	afx_msg void OnRegisternew();
	afx_msg void OnLoginanother();
	afx_msg void OnAddserver();
	afx_msg void OnCheckserver();
	afx_msg void OnRemoveserver();
	afx_msg void OnEditconference();
	afx_msg void OnDeleteconference();
	afx_msg void OnEditconferencenotice();
	afx_msg void OnLookconferencenotice();
};
