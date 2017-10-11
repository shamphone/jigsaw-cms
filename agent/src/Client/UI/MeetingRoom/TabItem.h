#pragma once

class CTabItem
{
public:
	CTabItem( CWnd* pWnd )
	{
		m_pWnd = pWnd;
		m_nIndex = -1;
	};
	// 窗口类型
	enum Type{ VideoBg = -2, WhiteboardDlg, ChatDlg };
	int		m_nType;
	// 窗口指针
	CWnd*	m_pWnd;
	// 用户id，类型为chatdlg的时候，为聊天对方的id，其他类型时为类型id
	__int64 m_ChatWithId;
	// 标签页序号
	int		m_nIndex;
};