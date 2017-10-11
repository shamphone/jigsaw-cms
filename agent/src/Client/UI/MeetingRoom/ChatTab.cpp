// ChatTab.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "ChatTab.h"
#include ".\chattab.h"
#include "chatdlg.h"
#include "roommainfrm.h"
#include "RoomView.h"
#include "VideoBgDlg.h"
#include "..\Whiteboard\WhiteboardDlg.h"

//ChatAlerter
int ChatAlerter::m_nCount = 0;

// CChatTab
#define ID_CLOSECHAT 1001
IMPLEMENT_DYNAMIC(CChatTab, CTabCtrl)
CChatTab::CChatTab()
{
	m_nToBeClosed = -1 ;
}

CChatTab::~CChatTab()
{
	vector<CTabItem*>::iterator iter = m_ChildWnds.begin();
	while( iter != m_ChildWnds.end() )
	{
		if( (*iter)->m_nType == CTabItem::ChatDlg )
		{
			CChatDlg* pDlg = (CChatDlg*)(*iter);
			pDlg->DestroyWindow();
			delete pDlg;
		}
		else if( (*iter)->m_nType == CTabItem::VideoBg )
		{
			CVideoBgDlg* pDlg = (CVideoBgDlg*)(*iter);
			pDlg->DestroyWindow();
			delete pDlg;
		}
		else if( (*iter)->m_nType == CTabItem::WhiteboardDlg )
		{
			CWhiteboardDlg* pDlg = (CWhiteboardDlg*)(*iter);
			pDlg->DestroyWindow();
			delete pDlg;
		}
		iter++;
	}
	while(m_AlerterList.GetCount() > 0)
	{
		ChatAlerter* pAlerter = (ChatAlerter*) m_AlerterList.RemoveHead() ;
		delete pAlerter;
	}
}

BEGIN_MESSAGE_MAP(CChatTab, CTabCtrl)
	ON_NOTIFY_REFLECT(TCN_SELCHANGE, OnTcnSelchange)
	ON_WM_SIZE()
	ON_NOTIFY_REFLECT(NM_RCLICK, OnNMRclick)
	ON_WM_TIMER()
	ON_COMMAND(ID_CLOSECHAT, OnCloseChatDlg)
	ON_WM_ERASEBKGND()
	ON_WM_PAINT()
	ON_WM_SETFOCUS()
END_MESSAGE_MAP()

void CChatTab::OnTcnSelchange(NMHDR *pNMHDR, LRESULT *pResult)
{
	int nCurSel = GetCurSel();
	int nCount = this->GetItemCount();
	for( int i = 0; i < nCount; i++ )
	{
		TCITEM item;
		item.mask = TCIF_PARAM;
		this->GetItem( i, &item );
		if( i == nCurSel )
		{
			((CTabItem*)item.lParam)->m_pWnd->ShowWindow( TRUE );
			delAlert( ((CTabItem*)item.lParam)->m_ChatWithId );
			setItemImage( nCurSel, 1 );
		}
		else
		{
			((CTabItem*)item.lParam)->m_pWnd->ShowWindow( FALSE );
		}
	}
	*pResult = 0;
}

void CChatTab::OnNMRclick(NMHDR *pNMHDR, LRESULT *pResult)
{
	// 右键单击标签项，弹出关闭菜单
	*pResult = 0;
	CPoint pt;
	GetCursorPos(&pt);
	CPoint point = pt;
	ScreenToClient(&point);
	TCHITTESTINFO tinfo;
	tinfo.pt = point;
	tinfo.flags = TCHT_ONITEM;
	int n = HitTest(&tinfo);
	if( n >= 0 )
	{
		TCITEM item;
		item.mask = TCIF_PARAM;
		GetItem( n, &item );
		CTabItem* pItem = (CTabItem*)item.lParam;
		if( pItem->m_ChatWithId < 1 )
		{
			return;
		}
	}
	else
		return;

	m_nToBeClosed = n;

	CMenu popmenu;
	popmenu.CreatePopupMenu();
	popmenu.AppendMenu(0, ID_CLOSECHAT, _T("关闭"));
	popmenu.TrackPopupMenu(TPM_RIGHTBUTTON, pt.x, pt.y, this);
	popmenu.DestroyMenu();
}

void CChatTab::OnSize(UINT nType, int cx, int cy)
{
	CTabCtrl::OnSize(nType, cx, cy);
	vector<CTabItem*>::iterator iter = m_ChildWnds.begin();
	while( iter != m_ChildWnds.end() )
	{
		if( !( (*iter)->m_nType == CTabItem::VideoBg 
				&& ((CRoomView*)GetParent())->m_bDualMoniterDisplay ) )
		{
			(*iter)->m_pWnd->MoveWindow( 0, 21, cx, cy - 21 );
		}
		iter++;
	}
}

void CChatTab::OnTimer(UINT nIDEvent)
{
	//在标签栏显示闪动的图标
	POSITION pos;
	int nItem, nImage;
	for(pos = m_AlerterList.GetHeadPosition(); pos!=NULL; m_AlerterList.GetNext(pos))
	{
		ChatAlerter* pAlerter = (ChatAlerter*) m_AlerterList.GetAt(pos);
		if(pAlerter->GetTimerId() == nIDEvent)
		{
			nItem = findItemIndexById(pAlerter->GetChatwithId());
			if(pAlerter->m_bFlag)
				nImage = 0;
			else
				nImage = 1;
			pAlerter->m_bFlag = !pAlerter->m_bFlag;
			setItemImage(nItem, nImage);
			break ;
		}
	}
	CTabCtrl::OnTimer(nIDEvent);
}

void CChatTab::OnCloseChatDlg()
{
	this->deleteItem( this->m_nToBeClosed );
}

CHARFORMAT CChatTab::getCharFormat()
{
	return this->m_cfChat;
}

void CChatTab::setCharFormat( CHARFORMAT cf )
{
	this->m_cfChat = cf;
	//将更改应用到所有聊天窗口
	vector<CTabItem*>::iterator iter = m_ChildWnds.begin();
	while( iter != m_ChildWnds.end() )
	{
		if( (*iter)->m_nType == CTabItem::ChatDlg )
		{
			CChatDlg* pChat = (CChatDlg*)(*iter)->m_pWnd;
			pChat->setCharFormat( cf );
		}
		iter++;
	}
	//写到配置文件
	saveCharFormat();
}

void CChatTab::loadCharFormat()
{
	CString iniFileName = ((CMeetingRoomFrame*)GetParentOwner())->getUserConfigFile();
	//从配置文件读取字体设置
	memset( &m_cfChat, 0, sizeof(m_cfChat) );
	m_cfChat.cbSize = sizeof(CHARFORMAT);
	m_cfChat.dwMask = CFM_BOLD | CFM_COLOR | CFM_FACE | CFM_ITALIC | CFM_SIZE | CFM_STRIKEOUT | CFM_UNDERLINE;
	m_cfChat.bPitchAndFamily = FF_SWISS;
	m_cfChat.crTextColor = ::GetPrivateProfileInt( "CHAT_FONT", "crTextColor", 0, iniFileName );
	m_cfChat.dwEffects = ::GetPrivateProfileInt( "CHAT_FONT", "dwEffects", 0, iniFileName );
	char buf[256];
	::GetPrivateProfileString( "CHAT_FONT", "szFaceName", "宋体", buf, 256, iniFileName );
	_tcscpy( m_cfChat.szFaceName, _T(buf));
	m_cfChat.yHeight = ::GetPrivateProfileInt( "CHAT_FONT", "yHeight", 210, iniFileName ); 
}

void CChatTab::saveCharFormat()
{
	CString iniFileName = ((CMeetingRoomFrame*)GetParentOwner())->getUserConfigFile();
	char buf[33];
    itoa( m_cfChat.dwEffects, buf, 10 );
	::WritePrivateProfileString( "CHAT_FONT", "dwEffects", buf, iniFileName );
	itoa( m_cfChat.yHeight, buf, 10 );
	::WritePrivateProfileString( "CHAT_FONT", "yHeight", buf, iniFileName );
	itoa( m_cfChat.crTextColor, buf, 10 );
	::WritePrivateProfileString( "CHAT_FONT", "crTextColor", buf, iniFileName );
	::WritePrivateProfileString( "CHAT_FONT", "szFaceName", m_cfChat.szFaceName, iniFileName );
}

void CChatTab::notifyWritingMessage( __int64 receiverId, __int64 senderId, bool bFlag )
{
	CChatDlg* pDlg = this->findChatDlgById( (receiverId == 0) ? receiverId : senderId );
	if( pDlg == NULL )
	{
		return;
	}
	pDlg->setWritingMessageFlag( senderId, bFlag );
}

int CChatTab::addChatwith(USERID uid, CString strName, BOOL bShow/* = FALSE*/)
{
	int nIndex = findItemIndexById(uid);
	int nCurSel = GetCurSel();
	if( nIndex >= 0 )
	{
		//如果标签栏中已经有，则设为当前选中的项，并显示窗口
		if( nIndex != nCurSel )
		{
			this->setCurSelWnd( nIndex );
		}
	}
	else
	{
		CChatDlg* pDlg = this->findChatDlgById( uid );
		if( pDlg == NULL )
		{
			// 如果聊天对话框不存在，先创建
			pDlg = new CChatDlg( this );
			pDlg->Create( IDD_CHAT_DLG, this );
			pDlg->m_ChatWithId = uid;
			pDlg->m_sChatWithName = strName;
			CRect rc;
			GetClientRect( &rc );
			rc.top = rc.top + 21;	
			pDlg->MoveWindow( &rc );
			this->m_ChildWnds.push_back( (CTabItem*)pDlg );
		}
		// 添加标签，并显示
		nIndex = GetItemCount();
		if( nIndex > 0 )
		{
			pDlg->m_SplitterPos = this->getSplitterPosition();
			pDlg->layout();
		}
		pDlg->m_nIndex = this->insertItem( nIndex, strName, 1, (LPARAM)(CTabItem*)pDlg );
		if( bShow || nCurSel < 0 )
		{
			this->setCurSelWnd( nIndex );
		}
		else
		{
			TCITEM item;
			item.mask = TCIF_PARAM;
			this->GetItem( nCurSel, &item );
			((CTabItem*)item.lParam)->m_pWnd->UpdateWindow();
		}
	}
	return nIndex;
}

void CChatTab::closeChatwith(USERID uid)
{
	int nIndex = findItemIndexById(uid);
	if( nIndex < 0 )
		return;
	//删除标签
	deleteItem( nIndex );
}

void CChatTab::addChatMsg(USERID with, CString name, CString msg, BOOL bPublic)
{
	__int64 id = bPublic ? 0 : with;
	CChatDlg* pDlg = this->findChatDlgById( id );
	int nIndex;
	if( pDlg == NULL )//聊天窗口不存在，添加聊天窗口
	{
		nIndex = addChatwith( id, name );
		pDlg = this->findChatDlgById( id );
	}
	else
	{
		if( pDlg->m_nIndex < 0 )
		{
			//窗口未在标签中，添加
			pDlg->m_nIndex = insertItem( GetItemCount(), name, 1, (LPARAM)(CTabItem*)pDlg );
			TCITEM item;
			item.mask = TCIF_PARAM;
			this->GetItem( GetCurSel(), &item );
            ((CTabItem*)item.lParam)->m_pWnd->InvalidateRect( NULL );
			((CTabItem*)item.lParam)->m_pWnd->UpdateWindow();
		}
	}
	pDlg->appendMsg(name, msg);
	addAlert(pDlg->m_nIndex, id);
}

void CChatTab::addMessage( string msg )
{
	CChatDlg* pDlg = this->findChatDlgById( 0 );
	if( pDlg == NULL )
	{
		ASSERT(FALSE);
		return;
	}
	pDlg->appendSysMsg( msg.c_str() );
	addAlert( pDlg->m_nIndex, 0 );
}

int CChatTab::getSplitterPosition()
{
	CChatDlg* pDlg = this->findChatDlgById( 0 );
	if( pDlg == NULL )
	{
		ASSERT(FALSE);
		return 0;
	}
	return pDlg->m_SplitterPos;
}

void CChatTab::setSplitterPosition( int nPos )
{
	vector<CTabItem*>::iterator iter = m_ChildWnds.begin();
	while( iter != m_ChildWnds.end() )
	{
		if( (*iter)->m_nType == CTabItem::ChatDlg )
		{
			CChatDlg* pChat = (CChatDlg*) (*iter)->m_pWnd;
			pChat->m_SplitterPos = nPos;
			pChat->layout();
		}
		iter++;
	}
}

void CChatTab::createVideoBgDlg()
{
    CVideoBgDlg* pDlg = new CVideoBgDlg(this);
	pDlg->create( IDD_VIDEO_BG_DLG, (CRoomView*)GetParent(), this );
	this->m_ChildWnds.push_back( (CTabItem*)pDlg );
}

CVideoBgDlg* CChatTab::getVideoBgDlg()
{
	CTabItem* pItem = this->findTabItemById( CTabItem::VideoBg, CTabItem::VideoBg );
	if( pItem == NULL )
	{
		ASSERT(FALSE);
		return NULL;
	}
	return (CVideoBgDlg*)pItem->m_pWnd;
}

void CChatTab::removeVideoBgTab()
{
	CVideoBgDlg* pDlg = this->getVideoBgDlg();
	if( pDlg->m_nIndex < 0 )
	{
		ASSERT(FALSE);
		return;
	}
	this->deleteItem( pDlg->m_nIndex );
}

void CChatTab::addVideoBgTab()
{
	CVideoBgDlg* pDlg = this->getVideoBgDlg();
	if( pDlg->m_nIndex < 0 )
	{
		pDlg->m_nIndex = insertItem( 0, "视频", 1, (LPARAM)(CTabItem*)pDlg );
	}
	if( this->GetCurSel() != pDlg->m_nIndex )
	{
		this->setCurSelWnd( pDlg->m_nIndex );
	}
}

void CChatTab::createWhiteboardDlg()
{
    CWhiteboardDlg* pDlg = new CWhiteboardDlg( this, IDD_WHITEBOARDDLG );
	pDlg->Create( IDD_WHITEBOARDDLG, this );
	this->m_ChildWnds.push_back( (CTabItem*)pDlg );
}

CWhiteboardDlg* CChatTab::getWhiteboardDlg()
{
	CTabItem* pItem = this->findTabItemById( CTabItem::WhiteboardDlg, CTabItem::WhiteboardDlg );
	if( pItem == NULL )
	{
		ASSERT(FALSE);
		return NULL;
	}
	return (CWhiteboardDlg*)pItem->m_pWnd;
}

void CChatTab::removeWhiteboardTab()
{
	CWhiteboardDlg* pDlg = this->getWhiteboardDlg();
	if( pDlg->m_nIndex < 0 )
	{
		ASSERT(FALSE);
		return;
	}
	this->deleteItem( pDlg->m_nIndex );
	pDlg->m_bShowWhiteboard = FALSE;
}

void CChatTab::addWhiteboardTab()
{
	CWhiteboardDlg* pDlg = this->getWhiteboardDlg();
	if( pDlg->m_bShowWhiteboard == TRUE )
	{
		this->removeWhiteboardTab();
	}
	else
	{
		if( pDlg->m_nIndex < 0 )
		{
			pDlg->m_nIndex = insertItem( 0, "白板", 1, (LPARAM)(CTabItem*)pDlg );
		}
		if( this->GetCurSel() != pDlg->m_nIndex )
		{
			this->setCurSelWnd( pDlg->m_nIndex );
		}
		pDlg->m_bShowWhiteboard = TRUE;
	}
}

void CChatTab::setCurSelWnd( int nIndex )
{
	// 隐藏当前显示
	int nCurSel = this->GetCurSel();
	TCITEM item;
	item.mask = TCIF_PARAM;
	this->GetItem( nCurSel, &item );
	((CTabItem*)item.lParam)->m_pWnd->ShowWindow(FALSE);
	// 设置新的
	this->GetItem( nIndex, &item );
	((CTabItem*)item.lParam)->m_pWnd->ShowWindow(TRUE);
	// 在消息提醒列表里删除相应的项
	delAlert( ((CTabItem*)item.lParam)->m_ChatWithId );
	setItemImage( nIndex, 1 );
	SetCurSel( nIndex );
}

int CChatTab::findItemIndexById(USERID uid)
{
	int nCount = this->GetItemCount();
	for( int i = 0; i < nCount; i++ )
	{
		TCITEM item;
		item.mask = TCIF_PARAM;
		this->GetItem( i, &item );
		if( ((CTabItem*)item.lParam)->m_ChatWithId == uid )
		{
			return i;
		}
	}
	return -1;
}

CTabItem* CChatTab::findTabItemById(USERID uid, int nType)
{
	vector<CTabItem*>::iterator iter = m_ChildWnds.begin();
	while( iter != m_ChildWnds.end() )
	{
		if( (*iter)->m_ChatWithId == uid && (*iter)->m_nType == nType )
		{
			return (*iter);
		}
		iter++;
	}
	return NULL;
}

CChatDlg* CChatTab::findChatDlgById(USERID uid)
{
	CTabItem* pItem = this->findTabItemById( uid, CTabItem::ChatDlg );
	if( pItem != NULL )
	{
		return (CChatDlg*)pItem->m_pWnd;
	}
	return NULL;
}

void CChatTab::adjustTabItemIndex( int nStartAfter )
{
	int nCount = this->GetItemCount();
	for( int i = nStartAfter + 1; i < nCount; i++ )
	{
		TCITEM item;
		item.mask = TCIF_PARAM;
		this->GetItem( i, &item );
		CTabItem* pItem = (CTabItem*)item.lParam;
		pItem->m_nIndex = i;
	}
}

int CChatTab::insertItem( int nIndex, CString sText, int nImage, LPARAM lParam )
{
	nIndex = this->InsertItem( nIndex, sText, nImage );
	TCITEM item;
	item.mask = TCIF_PARAM;
	item.lParam = lParam;
	this->SetItem( nIndex, &item );
	adjustTabItemIndex( nIndex );
	return nIndex;
}

void CChatTab::deleteItem( int nIndex )
{
	int nCurSel = GetCurSel();
	TCITEM item;
	item.mask = TCIF_PARAM;
	this->GetItem( nIndex, &item );
	((CTabItem*)item.lParam)->m_nIndex = -1;
	((CTabItem*)item.lParam)->m_pWnd->ShowWindow( FALSE );
	delAlert(((CTabItem*)item.lParam)->m_ChatWithId);
	this->DeleteItem( nIndex );
	adjustTabItemIndex( nIndex - 1 );
	//如果关闭的是当前显示的，调整显示，显示后一项
	if( nCurSel == nIndex ) 
	{
		nIndex = (nIndex > 0) ? nIndex - 1 : 0;
		SetCurSel( nIndex );
		this->GetItem( nIndex, &item );
		((CTabItem*)item.lParam)->m_pWnd->ShowWindow( TRUE );
		delAlert(((CTabItem*)item.lParam)->m_ChatWithId);
		setItemImage( nIndex, 1 );
	}
	else
	{
		this->GetItem( GetCurSel(), &item );
		((CTabItem*)item.lParam)->m_pWnd->ShowWindow( FALSE );
		((CTabItem*)item.lParam)->m_pWnd->ShowWindow( TRUE );
	}
}

void CChatTab::setItemImage(int nItem, int nImage)
{
	if( nItem >= GetItemCount() || nItem < 0 )
		return ;

	TCITEM tcItem;
	tcItem.mask = TCIF_IMAGE;
	GetItem(nItem, &tcItem);
	tcItem.iImage = nImage;
	SetItem(nItem, &tcItem);
}

void CChatTab::addAlert(int n, USERID with)
{
	//聊天窗口不可见，启动提醒
	if( n != GetCurSel() && !isAlertExist(with) )
	{
		ChatAlerter* pAlerter = new ChatAlerter();
		pAlerter->SetTimerId(ChatAlerter::GetCount());
		pAlerter->SetChatwithId(with);
		m_AlerterList.AddHead(pAlerter);
		SetTimer(pAlerter->GetTimerId(), 600, NULL);
	}
	
	//如果主窗口不活动，在任务栏闪动
	if( !GetParentOwner()->IsTopParentActive() || GetParentOwner()->IsIconic() )
		GetParentOwner()->FlashWindow(TRUE);

	//播放声音
	//PlaySound("IDR_RINGIN", AfxGetResourceHandle(), SND_RESOURCE|SND_ASYNC|SND_NODEFAULT); 
}

BOOL CChatTab::isAlertExist(USERID uid)
{
	for( POSITION pos = m_AlerterList.GetHeadPosition(); pos!=NULL; )
	{
		ChatAlerter* pAlerter = (ChatAlerter*) m_AlerterList.GetNext(pos);
		if( pAlerter->GetChatwithId() == uid )
		{
			return TRUE;
		}
	}
	return FALSE;
}

void CChatTab::delAlert(USERID uid)
{
	POSITION pos1,pos2;
	for(pos1 = m_AlerterList.GetHeadPosition(); (pos2 = pos1) != NULL;) 
	{
		ChatAlerter* pAlerter = (ChatAlerter*) m_AlerterList.GetNext(pos1);
		if(pAlerter->GetChatwithId() == uid)
		{
			KillTimer(pAlerter->GetTimerId());
			m_AlerterList.RemoveAt(pos2);
			delete pAlerter;
		}
	}
}

BOOL CChatTab::OnEraseBkgnd(CDC* pDC)
{
	return CEnTabCtrl::OnEraseBkgnd(pDC);
}

void CChatTab::OnPaint()
{
	CEnTabCtrl::OnPaint();
}

void CChatTab::OnSetFocus(CWnd* pOldWnd)
{
	CEnTabCtrl::OnSetFocus(pOldWnd);
	vector<CTabItem*>::iterator iter = m_ChildWnds.begin();
	while( iter != m_ChildWnds.end() )
	{
		if( (*iter)->m_pWnd->IsWindowVisible() && (*iter)->m_nType != CTabItem::VideoBg )
		{
			(*iter)->m_pWnd->SetFocus();
			break;
		}
		iter++;
	}
}
