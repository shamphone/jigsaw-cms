#pragma once

class CAttenderDlg;
class ConferenceUser;
class CSortAttendersDlg;
// CAttenderListDlg 对话框

#define VIDEO_HEIGHT				146
#define VIDEO_WINDOW_TITLE_HEIGHT	22
#define VIDEO_WINDOW_HEIGHT			168
#define VIDEO_WINDOW_WIDTH			180

class CAttenderListDlg : public CDialog
{
	DECLARE_DYNAMIC(CAttenderListDlg)

public:
	CAttenderListDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CAttenderListDlg();

	enum { IDD = IDD_ATTENDERLIST_DLG };

	// 判断是否有人有云台
	bool hasYuntai();
	//设置此人有云台
	void setYuntaiHolder( __int64 uid );
	// 添加参会人员
	void addConferenceUser(ConferenceUser* pConfUser, bool bSelf = false);
	// 移除参会人员
	void deleteConferenceUser(__int64 uid);
	// 获得当前参会人数
	int getConfUserCount();
	// 显示或隐藏某人视频窗口
	void showVideoWindow(__int64 uid, bool bShow = true);
	// 根据id获取视频窗口
	HWND getVideoWindowById(__int64 uid);
	// 获取本地视频窗口
	HWND getLocalVidwoWindow();
	// 上下滚动
	void scrollDown();
	void scrollUp();
	// 在只有两个参会人的情况下,获得另外一个人的id
	__int64 getAnotherUserId();
	// 交换两个参会人的位置
	void swapConfUser(__int64 prevId, __int64 nextId);
	// 不包括自己
	void getAttendersIdAndNameForSort( vector<string>* names, vector<__int64>* ids );
	void setIsListenFlag( bool bIsListen, __int64 uid );
	void sortAttenders();
	void sortAttenders( vector<__int64>& ids );
	// 窗口弹出显示
	void popupAttenderDlg( __int64 uid );
	void popUp( CAttenderDlg* pDlg, bool bPopup = true );
	int getUnpopupAttenderDlg( CAttenderDlg** ppDlg, int nCount );
	void popbackAllVideoDlg();

	// 根据id获得某人的显示窗口
	CAttenderDlg* getAttenderDlgById(__int64 uid);

private:
	// 是否可以上下滚动
	BOOL canScrollDown();
	BOOL canScrollUp();
	// 重新布局窗口
	void reLayout(bool bFromTopToBottom, POSITION posStartAt, POSITION posEndBefore, int nOffset);
	// 得到当前列表第一个显示的会议人员窗口在列表中的位置
	POSITION getFirstViewPosition();
	int getBottom();
	int getTop();

private:
	// 参会人员窗口列表
	CPtrList		m_AttenderDlgs;
	// 自己的窗口
	CAttenderDlg*	m_pSelfDlg;
	// 排序
	CSortAttendersDlg*	m_pSortDlg;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnCancel() {};
	virtual void OnOK() {};

	DECLARE_MESSAGE_MAP()
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnPaint();
	afx_msg void OnBnClickedOk() {};
	afx_msg void OnBnClickedCancel() {};
};
