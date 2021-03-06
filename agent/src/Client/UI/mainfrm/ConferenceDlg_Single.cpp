// ConferenceDlg.cpp : implementation file
//

#include "stdafx.h"
#include "FLVCC.h"
#include "ConferenceDlg.h"
#include ".\conferencedlg.h"
#include "MainFrm.h"
#include "SysinfoDlg.h"
#include "userdefinedmessage.h"
#include "ConfInfoDlg.h" 
#include "ShowXMLDlg.h"
#include "..\MeetingRoom\RoomMainFrm.h"
#include "..\..\Model\LServer.h"
#include "..\..\Model\ConferenceMgr.h"
#include "..\..\Model\Conference.h"
#include "Common\Common\PathHelper\PathHelper.h"
#include "Common\Common\XML\XMLParser.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CConferenceDlg dialog

CConferenceDlg::CConferenceDlg(CMainFrame* pMainFrame, CWnd* pParent /*=NULL*/)
	: CDialog(CConferenceDlg::IDD, pParent)
{
	m_pMainFrame = pMainFrame;
}

void CConferenceDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX,IDC_CONFERENCE_TREE,m_TreeCtrl);
}

BEGIN_MESSAGE_MAP(CConferenceDlg, CDialog)
	ON_WM_SIZE()
	ON_NOTIFY(NM_RCLICK, IDC_CONFERENCE_TREE, OnNMRclickConferenceTree)
	ON_NOTIFY(NM_DBLCLK, IDC_CONFERENCE_TREE, OnNMDblclkConferenceTree)
	ON_COMMAND(ID_JOIN_CONFERENCE, OnJoinConference)
	ON_COMMAND(ID_QUIT_CONFERENCE, OnQuitConference)
	ON_COMMAND(ID_LOOK_CONFERENCE, OnLookConference)
	ON_WM_PAINT()
	ON_MESSAGE(WM_ROOM_CLOSE, OnRoomClose)
	ON_MESSAGE(WM_KICKFROMROOM, OnKickFromRoom)
	ON_NOTIFY(TVN_SELCHANGED, IDC_CONFERENCE_TREE, OnTvnSelchangedConferenceTree)
	ON_WM_SHOWWINDOW()
	ON_COMMAND(ID_EDITCONFERENCE, OnEditconference)
	ON_COMMAND(ID_DELETECONFERENCE, OnDeleteconference)
	ON_COMMAND(ID_EDITCONFERENCENOTICE, OnEditconferencenotice)
	ON_COMMAND(ID_LOOKCONFERENCENOTICE, OnLookconferencenotice)
	ON_WM_SETFOCUS()
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CConferenceDlg message handlers

BOOL CConferenceDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();

	m_ImageList.Create(20, 20, ILC_COLOR16, 6, 1);
	CBitmap bm;
	bm.LoadBitmap(IDB_CONF_TREE);
	m_ImageList.Add(&bm, RGB(0, 0, 0));
	m_TreeCtrl.SetImageList(&m_ImageList,TVSIL_NORMAL);
	m_TreeCtrl.SetBkImage(IDB_CONTACTLISTBK);

	CBitmap bmp;
	bmp.LoadBitmap( IDB_SCROLLBAR );
	m_hBmpScrollBar = (HBITMAP) bmp.Detach();
	SkinWndScroll( &m_TreeCtrl, m_hBmpScrollBar );

	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CConferenceDlg::OnSize(UINT nType, int cx, int cy) 
{
	CDialog::OnSize(nType, cx, cy);

	if(m_TreeCtrl.GetSafeHwnd())
	{
		CWnd *pFrm = m_TreeCtrl.GetParent()->GetParent();
		pFrm->MoveWindow(1, 9, cx - 2, cy - 18);
		//m_TreeCtrl.MoveWindow(1, 9, cx - 2, cy - 18);
	}
}

LServer* CConferenceDlg::getCurSelServer()
{
	return m_pMainFrame->getDefaultServer();
}

ConferenceMgr* CConferenceDlg::getCurSelConferenceMgr()
{
	LServer* pServer = m_pMainFrame->getCurSelServer();
	if( pServer == NULL )
		return NULL;
	ASSERT( pServer->getStatus() == SERVER_LOGIN );
	if( pServer->getStatus() != SERVER_LOGIN )
		return NULL;
	ConferenceMgr* pConferenceMgr = pServer->getConferenceMgr();
	ASSERT( pConferenceMgr != NULL );
	return pConferenceMgr;
}

void CConferenceDlg::addServer( LServer* pServer )
{
}

void CConferenceDlg::modifyServer( LServer* pServer )
{
	ASSERT( pServer );
	if( pServer == NULL )
		return;
	if( pServer->getStatus() == SERVER_LOGOUT )
	{
		m_TreeCtrl.DeleteAllItems();
	}
	else if( pServer->getStatus() == SERVER_LOGIN )
	{
		CMainFrame* pMain = (CMainFrame*) GetParentOwner();
		HTREEITEM hItem; 
		if( pMain->getCurConfMode() == TRAINING_MODE )
		{
			hItem = m_TreeCtrl.InsertItem(_T("进行中的课程(0)"), 1, 1 );
			m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
			hItem = m_TreeCtrl.InsertItem(_T("计划中的课程(0)"), 1, 1 );
			m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
		}
		else if( pMain->getCurConfMode() == CONSULTING_MODE )
		{
			hItem = m_TreeCtrl.InsertItem(_T("当前开放咨询室(0)"), 1, 1 );
			m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
		}
		else
		{
			hItem = m_TreeCtrl.InsertItem(_T("进行中的会议(0)"), 1, 1 );
			m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
			hItem = m_TreeCtrl.InsertItem(_T("计划中的会议(0)"), 1, 1 );
			m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
			hItem = m_TreeCtrl.InsertItem(_T("已结束的会议(0)"), 1, 1 );
			m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
		}
		m_TreeCtrl.SelectItem( m_TreeCtrl.GetChildItem(TVI_ROOT) );
	}
}

void CConferenceDlg::removeServer( LServer* pServer )
{
}

void CConferenceDlg::OnNMRclickConferenceTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTVDISPINFO pTVDispInfo = reinterpret_cast<LPNMTVDISPINFO>(pNMHDR);
	TV_DISPINFO     *ptvinfo;
	ptvinfo = (TV_DISPINFO *)pNMHDR;
	
	CPoint pt;
	GetCursorPos(&pt);
	m_TreeCtrl.ScreenToClient(&pt);
	HTREEITEM hItem;
	hItem = m_TreeCtrl.HitTest( pt );

	CMenu menu;
	CMenu* submenu = NULL;
	//在空白处右键点击
	if( hItem == NULL )
	{
		return;
	}
	else
	{
		m_TreeCtrl.Select(hItem,TVGN_CARET | TVGN_DROPHILITE);
		DWORD dwData = m_TreeCtrl.GetItemData( hItem );
		if( dwData & ITEM_IS_CONFERENCE )
		{	
			menu.LoadMenu(IDR_CONFERENCEDLG);
			submenu = menu.GetSubMenu(0);
			if( m_pMainFrame->getCurConfMode() == TRAINING_MODE )
			{
				submenu->ModifyMenu( 0, MF_STRING|MF_BYPOSITION, ID_JOIN_CONFERENCE, "进入教室" );
				submenu->ModifyMenu( 1, MF_STRING|MF_BYPOSITION, ID_QUIT_CONFERENCE, "退出教室" );
				submenu->ModifyMenu( 2, MF_STRING|MF_BYPOSITION, ID_LOOK_CONFERENCE, "查看课程信息" );
			}
			else if( m_pMainFrame->getCurConfMode() == CONSULTING_MODE )
			{
				submenu->ModifyMenu( 0, MF_STRING|MF_BYPOSITION, ID_JOIN_CONFERENCE, "进入咨询室" );
				submenu->ModifyMenu( 1, MF_STRING|MF_BYPOSITION, ID_QUIT_CONFERENCE, "退出咨询室" );
				submenu->RemoveMenu( 2, MF_BYPOSITION );
			}
			if( !canJoinOrQuitConference( TRUE ) )
				submenu->EnableMenuItem( ID_JOIN_CONFERENCE, MF_GRAYED );
			if( !canJoinOrQuitConference( FALSE ) )
				submenu->EnableMenuItem( ID_QUIT_CONFERENCE, MF_GRAYED );
			if( !canEditConference() )
			{
				submenu->EnableMenuItem( ID_EDITCONFERENCE, MF_GRAYED );
				submenu->EnableMenuItem( ID_EDITCONFERENCENOTICE, MF_GRAYED );
			}
			if( !canDeleteConference() )
				submenu->EnableMenuItem( ID_DELETECONFERENCE, MF_GRAYED );
		}
		else 
			return;
	}
	//显示菜单
	CPoint pt1;
	GetCursorPos(&pt1);
	submenu->TrackPopupMenu(TPM_RIGHTBUTTON, pt1.x, pt1.y, this);
	submenu->DestroyMenu();
}

void CConferenceDlg::OnLookConference()
{
	if( !canLookConference() )
		return;
	ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
	CConfInfoDlg dlg( pConference, this );
	if( m_pMainFrame->getCurConfMode() == TRAINING_MODE )
	{
		dlg.m_strDesc = "课程描述：";
		dlg.m_strName = "课程名称：";
		dlg.m_strTitle = "查看课程信息";
	}
	dlg.DoModal();
}
void CConferenceDlg::OnJoinConference()
{
	if( !canJoinOrQuitConference( TRUE ) )
		return;
	ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
	CMeetingRoomFrame* pRoom = createRoomFrame( this->getCurSelServer(), pConference->m_dbConference.m_sName, TRUE );
	RunningConference* pRunningConference  = pConferenceMgr->createRunningConference( pConference, pRoom );
	pRoom->setConference(pRunningConference);
}

void CConferenceDlg::OnQuitConference()
{
	if( !canJoinOrQuitConference( FALSE ) )
		return;
	LServer* pServer = this->getCurSelServer();
	ConferenceMgr* pConferenceMgr = pServer->getConferenceMgr();
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
    
	RoomList roomList = m_rooms[pServer];
	RoomList::iterator iter = roomList.begin();
	while( iter != roomList.end() )
	{
		CMeetingRoomFrame* pRoom = *iter;
		if( pRoom->getConferenceId() == pConference->m_dbConference.m_id )
		{
			pRoom->exit();
			pConferenceMgr->destroyRunningConference( pConference->m_dbConference.m_id );
			roomList.erase( iter );
			m_rooms[pServer] = roomList;
			return;
		}
		iter++;
	}
	ASSERT( FALSE );
}

void CConferenceDlg::setChildrenNum( HTREEITEM hItem, int nDelta )
{
	if( hItem == NULL || hItem == TVI_ROOT )
		return;
	CString s = m_TreeCtrl.GetItemText( hItem );
	int pos = s.Find( '(' );
	if( pos < 0 )
		return;
	CString sOld = s.Mid( pos + 1, s.GetLength() - pos - 2 );
	int num = atoi( sOld ) + nDelta;
	CString sNew;
	sNew.Format( "%d", num );
	s.Replace( sOld, sNew );
	m_TreeCtrl.SetItemText( hItem, s );
}

void CConferenceDlg::OnNMDblclkConferenceTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	HTREEITEM hItem;
	hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
    if( dwData & CONFERENCE_FINISHED )
	{
		return;
	}
	if( dwData & CONFERENCE_ACTIVE )
	{
		if( this->canJoinOrQuitConference( TRUE ) )
			this->OnJoinConference();
		else
		{
			LServer* pServer = this->getCurSelServer();
			if( pServer == NULL )
				return;
			ConferenceMgr* pConferenceMgr = pServer->getConferenceMgr();
			if( pConferenceMgr == NULL )
				return;
			Conference* pConference = pConferenceMgr->getConferenceByItem( hItem );
			if( pConference == NULL )
				return;
			this->showRoom( pServer, pConference->m_dbConference.m_id );
		}
	}
}

void CConferenceDlg::OnTvnSelchangedConferenceTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	*pResult = 0;
}

void CConferenceDlg::setMyPicture( string fileName, BOOL bShow )
{
	RoomMap::iterator iterMap = m_rooms.begin();
	while( iterMap != m_rooms.end() )
	{
		RoomList roomList = iterMap->second;
		RoomList::iterator iterList = roomList.begin();
		while( iterList != roomList.end() )
		{
			CMeetingRoomFrame* pRoom = *iterList;
			pRoom->setMyPicture( fileName, bShow );
			iterList++;
		}
		iterMap++;
	}
}

void CConferenceDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting

	CRect rcClient;
	GetClientRect(&rcClient);

	CMainFrame* pMain = (CMainFrame*) GetParentOwner();
	if( pMain->getCurConfMode() != CONFERENCE_MODE )
	{
		dc.FillSolidRect( rcClient, RGB(228, 240, 254) );
		CPen pen;
		pen.CreatePen( PS_SOLID, 1, RGB(186, 197, 225) );
		CPen* pOldPen = dc.SelectObject( &pen );
		CRect rc = rcClient;
		rc.top = 5;
		dc.RoundRect( rc, CPoint(10, 10) );
		dc.SelectObject( pOldPen );
		return;
	}

	// Create a compatible memory DC 
	CDC memDC;
	memDC.CreateCompatibleDC( &dc );
	// Select a compatible bitmap into the memory DC
	CBitmap bitmap;
	bitmap.CreateCompatibleBitmap( &dc, rcClient.Width(), rcClient.Height() );
	memDC.SelectObject( &bitmap );
	memDC.FillSolidRect( CRect(0, rcClient.bottom - 10, rcClient.right, rcClient.bottom), RGB(228, 240, 254) );
	
	CPen pen;
	pen.CreatePen( PS_SOLID, 1, RGB(186, 197, 225) );
	memDC.SelectObject( &pen );
	CRect rc = rcClient;
	rc.top = -10;
	memDC.RoundRect( rc, CPoint(10, 10) );

	CDC bkDC;
	bkDC.CreateCompatibleDC(&dc);
	CBitmap corner;
	corner.LoadBitmap( IDB_CORNER );
	bkDC.SelectObject( &corner );
	memDC.BitBlt( rc.Width() - 4, 0, 4, 9, &bkDC, 4, 0, SRCCOPY);

	dc.BitBlt( 0, 0, rcClient.right, rcClient.bottom, &memDC, 0, 0, SRCCOPY);
}

void CConferenceDlg::OnShowWindow(BOOL bShow, UINT nStatus)
{
	CDialog::OnShowWindow(bShow, nStatus);
}

void CConferenceDlg::OnSetFocus(CWnd* pOldWnd)
{
	CDialog::OnSetFocus(pOldWnd);
	this->m_TreeCtrl.SetFocus();
}

void CConferenceDlg::notifyAddConference( Conference* pConference, LServer* pServer )
{
	// 正在召开的会议
	HTREEITEM hParent = m_TreeCtrl.GetChildItem( TVI_ROOT );
	// 显示图标
	int nImage = 2;
	UINT nStatus = CONFERENCE_ACTIVE;
	if( pConference->getStatus() != Conference::Active )
	{
		// 计划中的会议
        hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
		nImage = 3;
		nStatus = CONFERENCE_INACTIVE;
		if( pConference->getStatus() != Conference::Inactive )
		{
			// 已结束的会议
	        hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
			nImage = 4;
			nStatus = CONFERENCE_FINISHED;
		}
	}
	ASSERT( hParent != NULL );
	if( hParent == NULL )
		return;
	HTREEITEM hItem = m_TreeCtrl.InsertItem( _T(pConference->m_dbConference.m_sName.c_str()), nImage, nImage, hParent );
	pConference->m_hItem = hItem;
	m_TreeCtrl.SetItemData( hItem, ITEM_IS_CONFERENCE|nStatus );
	setChildrenNum( hParent, 1 );
}

void CConferenceDlg::notifyConferenceFinish( Conference* pConference, LServer* pServer )
{
	// 从正在召开的会议加到已结束的会议
	ASSERT( pConference != NULL );
	HTREEITEM hParent = m_TreeCtrl.GetParentItem( pConference->m_hItem );
	HTREEITEM hParentNew = m_TreeCtrl.GetNextSiblingItem( hParent );
	hParentNew = m_TreeCtrl.GetNextSiblingItem( hParentNew );
	ASSERT( hParentNew != NULL );
	if( hParentNew == NULL )
		return;
	HTREEITEM hItemNew = m_TreeCtrl.InsertItem( m_TreeCtrl.GetItemText(pConference->m_hItem), 4, 4, hParentNew, TVI_FIRST);
	m_TreeCtrl.SetItemData( hItemNew, ITEM_IS_CONFERENCE|CONFERENCE_FINISHED );
    setChildrenNum( hParentNew, 1 );

	m_TreeCtrl.DeleteItem( pConference->m_hItem );
	setChildrenNum( hParent, -1 );
}

void CConferenceDlg::notifyDeleteConference( Conference* pConference, LServer* pServer )
{
	ASSERT( pConference != NULL );
	HTREEITEM hParent = m_TreeCtrl.GetParentItem( pConference->m_hItem );
	m_TreeCtrl.DeleteItem( pConference->m_hItem );
	setChildrenNum( hParent, -1 );
}

void CConferenceDlg::notifyModifyConference( Conference* pConference, LServer* pServer )
{
	ASSERT( pConference != NULL );
    m_TreeCtrl.SetItemText( pConference->m_hItem, pConference->m_dbConference.m_sName.c_str() );
}

void CConferenceDlg::notifyStartConference( Conference* pConference, LServer* pServer )
{
	// 从即将召开的会议加到正在召开的会议
	ASSERT( pConference != NULL );
	HTREEITEM hParent = m_TreeCtrl.GetParentItem( pConference->m_hItem );
	HTREEITEM hParentNew = m_TreeCtrl.GetPrevSiblingItem( hParent );
    HTREEITEM hItem = pConference->m_hItem;
	pConference->m_hItem = m_TreeCtrl.InsertItem( m_TreeCtrl.GetItemText( hItem ), 2, 2, hParentNew );
	m_TreeCtrl.SetItemData( pConference->m_hItem, ITEM_IS_CONFERENCE|CONFERENCE_ACTIVE );
	m_TreeCtrl.DeleteItem( hItem );
	setChildrenNum( hParent, -1 );
	setChildrenNum( hParentNew, 1 );
}

void CConferenceDlg::notifyInviteConference( Conference* pConference, LServer* pServer, int nShow )
{
	ConferenceMgr* pConferenceMgr = pServer->getConferenceMgr();
	CMeetingRoomFrame* pRoom = createRoomFrame( pServer, pConference->m_dbConference.m_sName, nShow );
	RunningConference* pRunningConference  = pConferenceMgr->createRunningConference( pConference, pRoom );
	pRoom->setConference(pRunningConference);
}

void CConferenceDlg::notifyKickUserFromConference( __int64 confId, LServer* pServer )
{
	__int64* pId = new __int64(confId);
	PostMessage( WM_KICKFROMROOM, (WPARAM)pId, (LPARAM)pServer );
}

BOOL CConferenceDlg::canEditConference()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( dwData & ITEM_IS_CONFERENCE )
	{
		ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
		Conference* pConference = pConferenceMgr->getConferenceByItem( hItem );
		if( pConference->m_dbConference.m_creatorId == this->getCurSelServer()->getId() )
			return TRUE;
	}
	return FALSE;
}

BOOL CConferenceDlg::canDeleteConference()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( (dwData & ITEM_IS_CONFERENCE) && !(dwData & CONFERENCE_ACTIVE) )
	{
		ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
		Conference* pConference = pConferenceMgr->getConferenceByItem( hItem );
		if( pConference->m_dbConference.m_creatorId == this->getCurSelServer()->getId() )
			return TRUE;
	}
	return FALSE;
}

BOOL CConferenceDlg::canJoinOrQuitConference( BOOL bCanJoin )
{
	if( !IsWindowVisible() )
		return FALSE;
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return FALSE;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( (dwData & ITEM_IS_CONFERENCE) && (dwData & CONFERENCE_ACTIVE) )
	{
		ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
		if( pConferenceMgr == NULL )
			return FALSE;
		Conference* pConference = pConferenceMgr->getConferenceByItem( hItem );
		if( pConference == NULL )
			return FALSE;
		BOOL b = pConferenceMgr->isConferenceGoing( pConference->m_dbConference.m_id );
		if( bCanJoin )
			return !b;
		return b;
	}
	return FALSE;
}

BOOL CConferenceDlg::canLookConference()
{
	if( !IsWindowVisible() )
		return FALSE;
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return FALSE;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( dwData & ITEM_IS_CONFERENCE )
	{
		ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
		if( pConferenceMgr == NULL )
			return FALSE;
		Conference* pConference = pConferenceMgr->getConferenceByItem( hItem );
		if( pConference == NULL )
			return FALSE;
		return TRUE;
	}
	return FALSE;
}

BOOL CConferenceDlg::hasRoomOpened( LServer* pServer )
{
	if( m_rooms.find( pServer ) == m_rooms.end() )
		return FALSE;
	RoomList roomList = m_rooms[pServer];
	RoomList::iterator iter = roomList.begin();
	int n = 0;
	while( iter != roomList.end() )
	{
		if( (*iter)->IsWindowVisible() )
			n++;
		iter++;
	}
	return n > 0;
}

BOOL CConferenceDlg::hasRoomOpened()
{
	RoomMap::iterator iter = m_rooms.begin();
	while( iter != m_rooms.end() )
	{
		LServer* pServer = iter->first;
		if( hasRoomOpened( pServer ) )
			return TRUE;
		iter++;
	}
	return FALSE;
}

BOOL CConferenceDlg::isRoomExist( LServer* pServer, __int64 conferenceId )
{
	return getRoomById( pServer, conferenceId ) != NULL;
}

CMeetingRoomFrame* CConferenceDlg::getRoomById( LServer* pServer, __int64 conferenceId )
{
	if( m_rooms.find( pServer ) == m_rooms.end() )
		return NULL;
	RoomList roomList = m_rooms[pServer];
	RoomList::iterator iter = roomList.begin();
	while( iter != roomList.end() )
	{
		CMeetingRoomFrame* pRoom = *iter;
		if( pRoom->getConferenceId() == conferenceId )
		{
			return pRoom;
		}
		iter++;
	}
	return NULL;
}

void CConferenceDlg::showRoom( LServer* pServer, __int64 conferenceId )
{
	CMeetingRoomFrame* pRoom = this->getRoomById( pServer, conferenceId );
	ASSERT( pRoom != NULL );
	if( pRoom == NULL )
		return;

	CWnd* pWndChild = pRoom->GetLastActivePopup();
	if( !pRoom->IsWindowVisible() )
		pRoom->ShowWindow( SW_SHOW );
    if( pRoom->IsIconic() )
        pRoom->ShowWindow( SW_RESTORE );
    pWndChild->SetForegroundWindow();
}

void CConferenceDlg::quitAllRoom( LServer* pServer )
{
	ConferenceMgr* pConferenceMgr = pServer->getConferenceMgr();
    
	RoomList roomList = m_rooms[pServer];
	RoomList::iterator iter = roomList.begin();
	while( iter != roomList.end() )
	{
		CMeetingRoomFrame* pRoom = *iter;
		__int64 conferenceId = pRoom->getConferenceId();
		pRoom->exit();
		pConferenceMgr->destroyRunningConference( conferenceId );
		roomList.erase( iter );
		iter = roomList.begin();
	}
	m_rooms[pServer] = roomList;
}

CMeetingRoomFrame* CConferenceDlg::createRoomFrame( LServer* pServer, string sConferenceName, int nShow )
{
	CMeetingRoomFrame* pRoom = new CMeetingRoomFrame( pServer, this, m_pMainFrame->getCurConfMode() );
	pRoom->m_adUrl = m_pMainFrame->getRoomAdUrl();
	pRoom->m_sUserConfigFile = PathHelper::getUserPrivateConfigFile( m_pMainFrame->getDefaultServer()->getUserName().c_str(), 
																	m_pMainFrame->getDefaultServer()->getIP().c_str() );
	if( m_pMainFrame->getCurConfMode() == TRAINING_MODE )
		pRoom->LoadFrame(IDR_TRAININGROOM, WS_OVERLAPPEDWINDOW | FWS_ADDTOTITLE , NULL, NULL);
	else if( m_pMainFrame->getCurConfMode() == CONSULTING_MODE )
		pRoom->LoadFrame(IDR_CONSULTINGROOM, WS_OVERLAPPEDWINDOW | FWS_ADDTOTITLE , NULL, NULL);
	else
		pRoom->LoadFrame(IDR_MAINFRAME_MEET, WS_OVERLAPPEDWINDOW | FWS_ADDTOTITLE , NULL, NULL);
	string sCaption = sConferenceName + string( " - " ) + pServer->getRealName();
	pRoom->SetWindowText( sCaption.c_str() );
	pRoom->ShowWindow( nShow );
	pRoom->SetFocus();
	pRoom->setSplitterPosition();
	
	RoomList roomList = m_rooms[pServer];
	roomList.push_back( pRoom );
	m_rooms[pServer] = roomList;
	return pRoom;
}

LRESULT CConferenceDlg::OnRoomClose(WPARAM wParam, LPARAM lParam)
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*) wParam;
	LServer* pServer = (LServer*) lParam;
	RoomList roomList = m_rooms[pServer];
	RoomList::iterator iter = roomList.begin();
	while( iter != roomList.end() )
	{
		if( pRoom == *iter )
		{
			pServer->getConferenceMgr()->destroyRunningConference( pRoom->getConferenceId() );
			roomList.erase( iter );
			m_rooms[pServer] = roomList;
			return 0;
		}
		iter++;
	}
	ASSERT( FALSE );
	return 0;
}

LRESULT CConferenceDlg::OnKickFromRoom(WPARAM wParam, LPARAM lParam)
{
	CString str;
	__int64* confId = (__int64*) wParam;
	LServer* pServer = (LServer*) lParam;
	ConferenceMgr* pConferenceMgr = pServer->getConferenceMgr();
   	RoomList roomList = m_rooms[pServer];
	RoomList::iterator iter = roomList.begin();
	while( iter != roomList.end() )
	{
		CMeetingRoomFrame* pRoom = *iter;
		if( pRoom->getConferenceId() == *confId )
		{
			str = pRoom->getConferenceName().c_str();
			pRoom->exit();
			pConferenceMgr->destroyRunningConference( *confId );
			roomList.erase( iter );
			m_rooms[pServer] = roomList;
			break;
		}
		iter++;
	}
	delete confId;
	AfxMessageBox("你被赶出了会议[" + str + "]。");
	return 0;
}

void CConferenceDlg::OnLoginserver()
{
}

void CConferenceDlg::OnCancelloginserver()
{
}

void CConferenceDlg::OnLogoutserver()
{
}

void CConferenceDlg::OnRegisternew()
{
}

void CConferenceDlg::OnLoginanother()
{
}

void CConferenceDlg::OnAddserver()
{
}

void CConferenceDlg::OnCheckserver()
{
}

void CConferenceDlg::OnRemoveserver()
{
}

void CConferenceDlg::OnEditconference()
{
	if( !canEditConference() )
		return;
	ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
	CString url;
	url.Format( "%s?userId=%d", 
				m_pMainFrame->getCurSelServer()->getEditConferenceURL().c_str(), 
                m_pMainFrame->getCurSelServer()->getId() ); 
    url.Format( "%s&conId=%d&isEdit=true", url, pConference->m_dbConference.m_id );
	CShowXMLDlg dlg( url, "编辑会议" );
	dlg.setWindowSize( 588, 513 );
	dlg.DoModal();
}

void CConferenceDlg::OnDeleteconference()
{
	if( !canDeleteConference() )
		return;
	ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
	CString url;
	url.Format( "%s?conferenceId=%d", m_pMainFrame->getCurSelServer()->getDeleteConferenceURL().c_str(), 
									 pConference->m_dbConference.m_id );
	CShowXMLDlg dlg( url, "删除会议" );
	dlg.Create( IDD_SHOWXML_DLG, this );
}

void CConferenceDlg::OnEditconferencenotice()
{
	if( !canEditConference() )
		return;
	ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
	CString url;
	url.Format( "%s?conferenceId=%d", m_pMainFrame->getCurSelServer()->getCreateConferenceNoticeURL().c_str(), 
									 pConference->m_dbConference.m_id );
	CShowXMLDlg dlg( url, "发送会议通知" );
	dlg.setWindowSize( 507, 270 );
	dlg.DoModal();
}

void CConferenceDlg::OnLookconferencenotice()
{
	ConferenceMgr* pConferenceMgr = this->getCurSelConferenceMgr();
	if( pConferenceMgr == NULL )
		return;
	Conference* pConference = pConferenceMgr->getConferenceByItem( m_TreeCtrl.GetSelectedItem() );
	if( pConference == NULL )
		return;
	m_pMainFrame->getSysinfoDlg()->lookMessage( getCurSelServer(), CONFNOTICE );
}
