#pragma once

#include "ui\pub\entabctrl.h"

class CChatDlg;
class CVideoBgDlg;
class CWhiteboardDlg;
class CTabItem;

// 提示
class ChatAlerter : public CObject
{
public:
	ChatAlerter()
	{
		m_bFlag = TRUE;
		++m_nCount;
	}
	~ChatAlerter()
	{}

	BOOL m_bFlag;
private:
	ChatAlerter(const ChatAlerter &);
	ChatAlerter & operator = (const ChatAlerter &);

	USERID m_userId;
	static int m_nCount;
	int m_nTimerId;
public:
	static int GetCount()
	{
		return m_nCount;
	}
	void SetTimerId(int nTimerId)
	{
		m_nTimerId = nTimerId;
	}
	int GetTimerId()
	{
		return m_nTimerId;
	}
	USERID GetChatwithId()
	{
		return m_userId;
	}
	void SetChatwithId(USERID uid)
	{
		m_userId = uid;
	}
};

// CChatTab

class CChatTab : public CEnTabCtrl
{
	DECLARE_DYNAMIC(CChatTab)

public:
	CChatTab();
	virtual ~CChatTab();

	CHARFORMAT getCharFormat();
	void setCharFormat( CHARFORMAT cf );
	void loadCharFormat();
	void saveCharFormat();

	//设置某人正在输入消息
	void notifyWritingMessage( __int64 receiverId, __int64 senderId, bool bFlag );
	// 添加与某人的聊天对话框
	int addChatwith(USERID uid, CString strName, BOOL bShow = FALSE);
	// 关闭与某人的聊天对话框
	void closeChatwith(USERID uid);
	// 添加聊天信息
	void addChatMsg(USERID with, CString name, CString msg, BOOL bPublic);
	// 添加提示信息
	void addMessage( string msg );
	// 设置聊天窗口的分隔条位置
	int getSplitterPosition();
	void setSplitterPosition( int nPos );
	// 在标签中添加或关闭视频窗口
	void createVideoBgDlg();
	CVideoBgDlg* getVideoBgDlg();
	void removeVideoBgTab();
	void addVideoBgTab();
	// 在标签中添加或关闭白板窗口
	void createWhiteboardDlg();
	CWhiteboardDlg* getWhiteboardDlg();
	void removeWhiteboardTab();
	void addWhiteboardTab();

private:
	// 显示当前标签页对应的窗口
	void setCurSelWnd( int nIndex );
	// 删除提示对象
	void delAlert(USERID uid);
	// 设置标签项的图片
	void setItemImage(int nItem, int nImage);
	// 提示有新消息到
	void addAlert(int n, USERID with);
	// 是否已存在提示
	BOOL isAlertExist(USERID uid);
	// 根据id找到对应的标签项的序号
	int findItemIndexById(USERID uid);
	// 根据id找到对应的CTabItem
	CTabItem* findTabItemById(USERID uid, int nType);
	// 根据id找到对应的聊天窗口
	CChatDlg* findChatDlgById(USERID uid);
	// 调整序号
	void adjustTabItemIndex( int nStartAfter );
	// 添加和删除标签
	int insertItem( int nIndex, CString sText, int nImage, LPARAM lParam );
	void deleteItem( int nIndex );

private:
	// 所有的标签页子窗口
	vector<CTabItem*> m_ChildWnds;
	// 提示对象
	CPtrList m_AlerterList;
    // 要被关闭的item的序号
	int m_nToBeClosed;
	// 聊天字体
	CHARFORMAT m_cfChat;

protected:
	DECLARE_MESSAGE_MAP()
	afx_msg void OnTcnSelchange(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnNMRclick(NMHDR *pNMHDR, LRESULT *pResult);
	afx_msg void OnTimer(UINT nIDEvent);
	afx_msg void OnCloseChatDlg();
	afx_msg BOOL OnEraseBkgnd(CDC* pDC);
	afx_msg void OnPaint();
	afx_msg void OnSetFocus(CWnd* pOldWnd);
};


