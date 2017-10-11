// MainFrm.cpp : CMeetingRoomFrame 类的实现
//

#include "stdafx.h"

#include "Flvcc.h"
#include "RoomMainFrm.h"
#include ".\roommainfrm.h"
#include "AttenderDlg.h"
#include "YuntaiDlg.h"
#include "ControlSpeakDlg.h"
#include "ControlDlg.h"
#include "RoomView.h"
#include "AttenderListDlg.h"
#include "FileTransferMgrDlg.h"
#include "MarkWnd.h"
#include "ChatTab.h"
#include "VideoQualityDlg.h"
#include "SetmypictureDlg.h"
#include "StatusBarDlg.h"
#include "..\Whiteboard\WhiteboardDlg.h"
#include "..\MainFrm\ContactDlg.h"
#include "..\MainFrm\UserDefinedMessage.h"
#include "..\MainFrm\ContactListDlg.h"
#include "..\MainFrm\ConfInfoDlg.h"
#include "..\MainFrm\ShowXMLDlg.h"
#include "..\..\Model\CooperationManager.h"
#include "..\..\Model\LServer.h"
#include "..\..\Model\Contact\Contact.h"
#include "..\..\Model\Contact\ContactMgr.h"
#include "..\..\model\Room\RunningConference.h"
#include "..\..\model\Conference.h"
#include "..\..\model\Room\AVManager.h"
#include "..\..\Model\room\ConferenceUser.h"
#include "..\..\Model\room\DesktopManager.h"
#include "..\..\model\room\ConferenceUserMgr.h"
#include "..\..\Model\room\filetransfermgr.h"
#include "..\..\..\Common\Common\RegistryConstant\RegistryConstant.h"
#include "..\..\..\Common\Common\PathHelper\PathHelper.h"
#include "Transfer/TransModel.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif

// CMeetingRoomFrame

IMPLEMENT_DYNAMIC(CMeetingRoomFrame, CFrameWnd)

BEGIN_MESSAGE_MAP(CMeetingRoomFrame, CFrameWnd)
	ON_WM_CREATE()
	ON_WM_SETFOCUS()
	ON_WM_CLOSE()
	ON_WM_SIZING()
	ON_WM_TIMER()

	ON_COMMAND(ID_EXIT, OnExit)
	ON_COMMAND(ID_APPLY_VIDEO, OnApplyVideo)
	ON_UPDATE_COMMAND_UI(ID_APPLY_VIDEO, OnUpdateApplyVideo)
	ON_COMMAND(ID_STOP_RECEIVE_VIDEO, OnStopReceiveVideo)
	ON_UPDATE_COMMAND_UI(ID_STOP_RECEIVE_VIDEO, OnUpdateStopReceiveVideo)

	ON_COMMAND(ID_VIEW_CONFUSERLIST, OnViewConfuserlist)
	ON_UPDATE_COMMAND_UI(ID_VIEW_CONFUSERLIST, OnUpdateViewConfuserlist)

	ON_COMMAND(ID_HELP_CONTENT, OnHelpContent)
	ON_COMMAND(ID_START_DESKTOP_SERVER, OnStartDesktopServer)
	ON_COMMAND(ID_STOP_DESKTOP_SERVER, OnStopDesktopServer)
	ON_COMMAND(ID_APPLY_DESKTOP_SHARE, OnApplyDesktopShare)
	ON_COMMAND(ID_STOP_DESKTOP_SHARE, OnStopDesktopShare)
	ON_COMMAND(ID_APPLY_DESKTOP_CONTROL, OnApplyDesktopControl)
	ON_COMMAND(ID_STOP_DESKTOP_CONTROL, OnStopDesktopControl)
	ON_COMMAND(ID_STOP_RECEIVE_DESKTOP_CONTROL, OnStopReceiveDesktopControl)
	
	ON_UPDATE_COMMAND_UI(ID_STOP_DESKTOP_SERVER, OnUpdateStopDesktopServer)
	ON_UPDATE_COMMAND_UI(ID_APPLY_DESKTOP_SHARE, OnUpdateApplyDesktopShare)
	ON_UPDATE_COMMAND_UI(ID_STOP_DESKTOP_SHARE, OnUpdateStopDesktopShare)
	ON_UPDATE_COMMAND_UI(ID_APPLY_DESKTOP_CONTROL, OnUpdateApplyDesktopControl)
	ON_UPDATE_COMMAND_UI(ID_STOP_DESKTOP_CONTROL, OnUpdateStopDesktopControl)
	ON_UPDATE_COMMAND_UI(ID_STOP_RECEIVE_DESKTOP_CONTROL, OnUpdateStopReceiveDesktopControl)
	ON_UPDATE_COMMAND_UI(ID_START_DESKTOP_SERVER, OnUpdateStartDesktopServer)
	ON_COMMAND(ID_START_LISTEN, OnStartListen)
	ON_COMMAND(ID_STOP_LISTEN, OnStopListen)
    ON_UPDATE_COMMAND_UI(ID_START_LISTEN, OnUpdateStartListen)
    ON_UPDATE_COMMAND_UI(ID_STOP_LISTEN, OnUpdateStopListen)
	ON_COMMAND(ID_SEND_FILE, OnSendFile)
	ON_COMMAND(ID_OPEN_RECEIVED, OnOpenReceived)
	ON_COMMAND(ID_INVITE_CONFERENCE, OnInviteConference)
	ON_COMMAND(ID_CONFERENCE_INFO, OnConferenceInfo)
	ON_COMMAND(ID_MARK, OnMark)
	ON_UPDATE_COMMAND_UI(ID_SEND_FILE, OnUpdateSendFile)
	ON_COMMAND(ID_KICK_USER, OnKickUser)
	ON_UPDATE_COMMAND_UI(ID_KICK_USER, OnUpdateKickUser)
	ON_COMMAND(ID_SORTATTENDERS, OnSortattenders)

	ON_MESSAGE(WM_APPLY_DESKTOP, OnReceiveApplyDesktopMsg)
	ON_MESSAGE(WM_INVITE_DESKTOP, OnReceiveInviteDesktopMsg)
	ON_MESSAGE(WM_APPLY_CONTROL_DESKTOP, OnReceiveApplyControlDesktopMsg)
	ON_MESSAGE(WM_APPLY_SENDFILE, OnReceiveApplySendfileMsg)

	ON_COMMAND(ID_VIEW_NORMAL, OnViewNormal)
	ON_UPDATE_COMMAND_UI(ID_VIEW_NORMAL, OnUpdateViewNormal)
	ON_COMMAND(ID_VIEW_ONE_VGA, OnViewOneVga)
	ON_UPDATE_COMMAND_UI(ID_VIEW_ONE_VGA, OnUpdateViewOneVga)
	ON_COMMAND(ID_VIEW_FOUR_CIF, OnViewFourCif)
	ON_UPDATE_COMMAND_UI(ID_VIEW_FOUR_CIF, OnUpdateViewFourCif)
	ON_COMMAND(ID_VIEW_SIXTEEN_QCIF, OnViewSixteenQcif)
	ON_UPDATE_COMMAND_UI(ID_VIEW_SIXTEEN_QCIF, OnUpdateViewSixteenQcif)
	ON_COMMAND(ID_CENTER_CONTROL, OnCenterControl)
	ON_COMMAND(ID_VIEW_DIALOG, OnViewDialog)
	ON_UPDATE_COMMAND_UI(ID_VIEW_DIALOG, OnUpdateViewDialog)
	ON_UPDATE_COMMAND_UI(ID_CENTER_CONTROL, OnUpdateCenterControl)
	ON_UPDATE_COMMAND_UI(ID_INVITE_CONFERENCE, OnUpdateInviteConference)
	ON_UPDATE_COMMAND_UI(ID_MARK, OnUpdateMark)
	ON_COMMAND(ID_CONTROLSPEAK, OnControlspeak)
	ON_COMMAND(ID_APPLYSPEAK, OnApplyspeak)
	ON_UPDATE_COMMAND_UI(ID_APPLYSPEAK, OnUpdateApplyspeak)
	ON_UPDATE_COMMAND_UI(ID_CONTROLSPEAK, OnUpdateControlspeak)
	ON_WM_DESTROY()
	ON_COMMAND(ID_SEPRATE_VIDEO, OnSeprateVideo)
	ON_UPDATE_COMMAND_UI(ID_SEPRATE_VIDEO, OnUpdateSeprateVideo)
	ON_COMMAND(ID_YUNTAICONTROL, OnYuntaicontrol)
	ON_UPDATE_COMMAND_UI(ID_YUNTAICONTROL, OnUpdateYuntaicontrol)
	ON_COMMAND(ID_CHANGEVIDEOQUALITY, OnChangevideoquality)
	ON_COMMAND(ID_VIEWCONFHISTORY, OnViewconfhistory)
	ON_WM_SIZE()


	ON_COMMAND(ID_NEW_WHITEBOARD, OnNewWhiteboard)
	ON_COMMAND(ID_FILE_OPEN, OnFileOpen)
	ON_COMMAND(ID_SAVE_WHITEBOARD, OnSaveWhiteboard)
	ON_COMMAND(ID_RUBBER, OnRubber)
	ON_UPDATE_COMMAND_UI(ID_RUBBER, OnUpdateRubber)
	ON_COMMAND(IDT_SELECT, OnSelect)
	ON_UPDATE_COMMAND_UI(IDT_SELECT, OnUpdateSelect)
	ON_COMMAND(IDT_LINE, OnLine)
	ON_UPDATE_COMMAND_UI(IDT_LINE, OnUpdateLine)
	ON_COMMAND(IDT_RECTANGLE, OnRectangle)
	ON_UPDATE_COMMAND_UI(IDT_RECTANGLE, OnUpdateRectangle)
	ON_COMMAND(IDT_ELLIPSE, OnEllipse)
	ON_UPDATE_COMMAND_UI(IDT_ELLIPSE, OnUpdateEllipse)
	ON_COMMAND(IDT_CIRCLE, OnCircle)
	ON_UPDATE_COMMAND_UI(IDT_CIRCLE, OnUpdateCircle)
	ON_COMMAND(IDT_POLY, OnPoly)
	ON_UPDATE_COMMAND_UI(IDT_POLY, OnUpdatePoly)
	ON_COMMAND(IDT_CURVE, OnCurve)
	ON_UPDATE_COMMAND_UI(IDT_CURVE, OnUpdateCurve)
	ON_COMMAND(IDT_TEXT, OnText)
	ON_UPDATE_COMMAND_UI(IDT_TEXT, OnUpdateText)
	ON_COMMAND(ID_WIDTH_THIN, OnWidthThin)
	ON_UPDATE_COMMAND_UI(ID_WIDTH_THIN, OnUpdateWidthThin)
	ON_COMMAND(ID_WIDTH_NORMAL, OnWidthNormal)
	ON_UPDATE_COMMAND_UI(ID_WIDTH_NORMAL, OnUpdateWidthNormal)
	ON_COMMAND(ID_WIDTH_WIDE, OnWidthWide)
	ON_UPDATE_COMMAND_UI(ID_WIDTH_WIDE, OnUpdateWidthWide)
	ON_COMMAND(ID_FILL_FALSE, OnFillFalse)
	ON_UPDATE_COMMAND_UI(ID_FILL_FALSE, OnUpdateFillFalse)
	ON_COMMAND(ID_FILL_TRUE, OnFillTrue)
	ON_UPDATE_COMMAND_UI(ID_FILL_TRUE, OnUpdateFillTrue)

	ON_COMMAND(ID_COLOR_BLACK, OnColorBlack)
	ON_UPDATE_COMMAND_UI(ID_COLOR_BLACK, OnUpdateColorBlack)
	ON_COMMAND(ID_COLOR_WHITE, OnColorWhite)
	ON_UPDATE_COMMAND_UI(ID_COLOR_WHITE, OnUpdateColorWhite)
	ON_COMMAND(ID_COLOR_GRAY, OnColorGray)
	ON_UPDATE_COMMAND_UI(ID_COLOR_GRAY, OnUpdateColorGray)
	ON_COMMAND(ID_COLOR_RED, OnColorRed)
	ON_UPDATE_COMMAND_UI(ID_COLOR_RED, OnUpdateColorRed)
	ON_COMMAND(ID_COLOR_GREEN, OnColorGreen)
	ON_UPDATE_COMMAND_UI(ID_COLOR_GREEN, OnUpdateColorGreen)
	ON_COMMAND(ID_COLOR_BLUE, OnColorBlue)
	ON_UPDATE_COMMAND_UI(ID_COLOR_BLUE, OnUpdateColorBlue)
	ON_COMMAND(ID_COLOR_PINK, OnColorPink)
	ON_UPDATE_COMMAND_UI(ID_COLOR_PINK, OnUpdateColorPink)
	ON_COMMAND(ID_COLOR_YELLOW, OnColorYellow)
	ON_UPDATE_COMMAND_UI(ID_COLOR_YELLOW, OnUpdateColorYellow)
	ON_COMMAND(ID_COLOR_CYAN, OnColorCyan)
	ON_UPDATE_COMMAND_UI(ID_COLOR_CYAN, OnUpdateColorCyan)

	ON_COMMAND(ID_SMALL_TEXT, OnSmallText)
	ON_UPDATE_COMMAND_UI(ID_SMALL_TEXT, OnUpdateSmallText)
	ON_COMMAND(ID_NORMAL_TEXT, OnNormalText)
	ON_UPDATE_COMMAND_UI(ID_NORMAL_TEXT, OnUpdateNormalText)
	ON_COMMAND(ID_BIG_TEXT, OnBigText)
	ON_UPDATE_COMMAND_UI(ID_BIG_TEXT, OnUpdateBigText)
END_MESSAGE_MAP()

// CMeetingRoomFrame 构造/析构
CMeetingRoomFrame::CMeetingRoomFrame(LServer* pServer, CWnd* pWnd)
{
	m_pServer = pServer;
	m_pParentWnd = pWnd;

	m_pStatusBarDlg = NULL;
	m_pYuntaiDlg = NULL;
	m_pFileTransferMgrDlg = NULL;
	m_pRoomView = NULL;
	m_pMarkWnd = NULL;
	m_pConference = NULL;
	m_pControlDlg = NULL;
	m_bIsSpeaking = FALSE;
	m_bIsApplyingSpeak = FALSE;
	m_bIsInitChatDlgSplitterPos = FALSE;
}

CMeetingRoomFrame::~CMeetingRoomFrame()
{
}

BOOL CMeetingRoomFrame::PreTranslateMessage(MSG* pMsg)
{
	if( pMsg->message == WM_KEYDOWN )
	{
		TRACE0("trap key down message\n");
		if( pMsg->wParam == VK_F4 )
		{
			if( m_pConference && !m_pConference->isBeenControledUser() )
				this->switchViewmode();
		}
	}
	if(pMsg->message == WM_MOUSEWHEEL)
	{
		CPoint pt = CPoint(pMsg->lParam);
        CRect rc;
		m_pRoomView->getAttenderListDlg()->GetWindowRect(&rc);
		int zDelta = GET_WHEEL_DELTA_WPARAM(pMsg->wParam);
		int delta = abs(zDelta);
		if( rc.PtInRect(pt) )
		{
			if( zDelta < 0 )
			{
				for( int i = 0; i < delta / 120; i++ )
				{
					m_pRoomView->getAttenderListDlg()->scrollUp();
				}
			}
			else if( zDelta > 0 )
			{
				for( int i = 0; i < delta / 120; i++ )
				{
					m_pRoomView->getAttenderListDlg()->scrollDown();
				}
			}
			return TRUE;
		}
	}
	return CFrameWnd::PreTranslateMessage(pMsg);
}

// CMeetingRoomFrame 诊断
#ifdef _DEBUG
void CMeetingRoomFrame::AssertValid() const
{
	CFrameWnd::AssertValid();
}
void CMeetingRoomFrame::Dump(CDumpContext& dc) const
{
	CFrameWnd::Dump(dc);
}
#endif //_DEBUG


int CMeetingRoomFrame::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CFrameWnd::OnCreate(lpCreateStruct) == -1)
		return -1;

	// 创建一个视图以占用框架的工作区
	m_pRoomView = new CRoomView();
	m_pRoomView->Create( IDD_ROOM_VIEW, this );
	m_pRoomView->ShowWindow( TRUE );

	m_pStatusBarDlg = new CStatusBarDlg( this );
	m_pStatusBarDlg->m_adUrl = this->m_adUrl;
	m_pStatusBarDlg->Create( IDD_STATUSBAR_DLG, this );
	m_pStatusBarDlg->ShowWindow( TRUE );

	m_pFileTransferMgrDlg = new CFileTransferMgrDlg( this );
	m_pFileTransferMgrDlg->Create( IDD_FILETRANSFERMGRDLG, this );

	m_pControlDlg = new CControlDlg();
	m_pControlDlg->Create( IDD_CONTROL_DLG, this );

	m_pControlSpeakDlg = new CControlSpeakDlg( this );
	m_pControlSpeakDlg->Create( IDD_CONTROLSPEAK_DLG, this );

	m_pYuntaiDlg = new CYuntaiDlg( this );
	m_pYuntaiDlg->Create( IDD_YUNTAI_DLG, this );

	HICON hIcon = AfxGetApp()->LoadIcon(IDI_MEETING);
	SetIcon(hIcon, TRUE);
	SetIcon(hIcon, FALSE);

	m_timeEnterRoom = CTime::GetCurrentTime();
	SetTimer(1, 1000, NULL);

	if( !m_wndToolBar.CreateEx( (CWnd*)(m_pRoomView->getWhiteboardDlg()), TBSTYLE_FLAT, WS_CHILD|WS_VISIBLE|CBRS_ALIGN_TOP|/*CBRS_GRIPPER|*/CBRS_TOOLTIPS,
		CRect(0, 0, 0, 0) ) ||	!m_wndToolBar.LoadToolBar(IDR_WB_TOOLBAR) )
	{
		TRACE0("failed to create toolbar\n");
		return FALSE;
	}
	m_wndToolBar.ShowWindow(SW_SHOW);

	if( !m_wndColorBar.CreateEx( (CWnd*)(m_pRoomView->getWhiteboardDlg()), TBSTYLE_FLAT, WS_CHILD|WS_VISIBLE|CBRS_ALIGN_BOTTOM|/*CBRS_GRIPPER|*/CBRS_TOOLTIPS,
		CRect(0, 0, 0, 0) ) ||	!m_wndColorBar.LoadToolBar(IDR_COLORTOOLBAR) )
	{
		TRACE0("failed to create toolbar\n");
		return FALSE;
	}
	m_wndColorBar.ShowWindow(SW_SHOW);
	RepositionBars( AFX_IDW_CONTROLBAR_FIRST, AFX_IDW_CONTROLBAR_LAST, 0 );

	m_pRoomView->getWhiteboardDlg()->m_pToolBar = &m_wndToolBar;
	m_pRoomView->getWhiteboardDlg()->m_pColorBar = &m_wndColorBar;

	return 0;
}

void CMeetingRoomFrame::OnDestroy()
{
	CFrameWnd::OnDestroy();

	delete m_pMarkWnd;
	if( m_pFileTransferMgrDlg )
	{
		m_pFileTransferMgrDlg->DestroyWindow();
		delete m_pFileTransferMgrDlg;
	}
	if( m_pControlDlg )
	{
		m_pControlDlg->DestroyWindow();
		delete m_pControlDlg;
	}
	if( m_pControlSpeakDlg )
	{
		m_pControlSpeakDlg->DestroyWindow();
		delete m_pControlSpeakDlg;
	}
	if( m_pYuntaiDlg )
	{
		m_pYuntaiDlg->DestroyWindow();
		delete m_pYuntaiDlg;
	}
	if( m_pRoomView )
	{
		m_pRoomView->DestroyWindow();
		delete m_pRoomView;
	}
	if( m_pStatusBarDlg )
	{
		m_pStatusBarDlg->DestroyWindow();
		delete m_pStatusBarDlg;
	}
}

BOOL CMeetingRoomFrame::PreCreateWindow(CREATESTRUCT& cs)
{
	if( !CFrameWnd::PreCreateWindow(cs) )
		return FALSE;

	cs.x = ::GetPrivateProfileInt("ROOM_POSITION", "left", 0, m_sUserConfigFile);
	if( cs.x < 0) cs.x = 0;
	cs.y = ::GetPrivateProfileInt("ROOM_POSITION", "top", 0, m_sUserConfigFile);
	if( cs.y < 0) cs.y = 0;
	cs.cx = ::GetPrivateProfileInt("ROOM_POSITION", "width", 0, m_sUserConfigFile);
	if( cs.cx == 0 ) cs.cx = 640;
	if( cs.cx < 450) cs.cx = 450;
	cs.cy = ::GetPrivateProfileInt("ROOM_POSITION", "height", 0, m_sUserConfigFile);
	if( cs.cy == 0 ) cs.cy = 480;
	if( cs.cy < 400 ) cs.cy = 400;

	cs.dwExStyle &= ~WS_EX_CLIENTEDGE;
	cs.lpszClass = AfxRegisterWndClass(0);
	return TRUE;
}

void CMeetingRoomFrame::OnSize(UINT nType, int cx, int cy)
{
	CFrameWnd::OnSize(nType, cx, cy);
	if( cx <= 0 || cy <= 0 )
		return;
	this->m_pRoomView->MoveWindow( 0, 0, cx, cy - 18 );
	m_pStatusBarDlg->MoveWindow( 0, cy - 18, cx, 18 );
	if( !m_bIsInitChatDlgSplitterPos )
	{
		m_bIsInitChatDlgSplitterPos = TRUE;
		this->setSplitterPosition();
	}
}

void CMeetingRoomFrame::OnSizing(UINT fwSide, LPRECT pRect)
{
	int nMinX, nMinY;
	nMinX = 450;
	nMinY = 400;
	if(pRect->right - pRect->left > nMinX )
		CFrameWnd::OnSizing(fwSide, pRect);
	else
	{
		if(fwSide==1 || fwSide==4 || fwSide==7)
			pRect->left = pRect->right - nMinX;	
		else
			pRect->right = pRect->left + nMinX;
	}
	if(pRect->bottom - pRect->top > nMinY)
		CFrameWnd::OnSizing(fwSide, pRect);
	else
	{
		if(fwSide==3 || fwSide ==5 || fwSide ==4)
			pRect->top = pRect->bottom - nMinY;
		else
			pRect->bottom = pRect->top + nMinY;
	}
}

void CMeetingRoomFrame::OnClose()
{
	if( m_pConference )
	{
		// 发送文件的线程会更新界面的显示，所以先停止文件发送线程
		m_pConference->getFileTransferMgr()->exitSendFileThread();
	}
	writeIni();
	::SendMessage( this->m_pParentWnd->GetSafeHwnd(), WM_ROOM_CLOSE, (WPARAM)this, (LPARAM)m_pServer );
	CFrameWnd::OnClose();
}

void CMeetingRoomFrame::OnSetFocus(CWnd* /*pOldWnd*/)
{
	// 将焦点前移到视图窗口
	m_pRoomView->SetFocus();
}

BOOL CMeetingRoomFrame::OnCmdMsg(UINT nID, int nCode, void* pExtra, AFX_CMDHANDLERINFO* pHandlerInfo)
{
	// 否则，执行默认处理
	return CFrameWnd::OnCmdMsg(nID, nCode, pExtra, pHandlerInfo);
}

void CMeetingRoomFrame::OnTimer(UINT nIDEvent)
{
	if(nIDEvent == 1)
	{
		CTime t = CTime::GetCurrentTime();
		CTimeSpan ts;
		ts = t - m_timeEnterRoom;
		CString s;
		s = ts.Format( "%H::%M::%S" );

		if( m_pStatusBarDlg->GetSafeHwnd() )
			m_pStatusBarDlg->setStayTime( s );	
	}

	CFrameWnd::OnTimer(nIDEvent);
}

void CMeetingRoomFrame::serverDisconnect()
{
	if( m_pConference )
	{
		m_pConference->getFileTransferMgr()->exitSendFileThread();
		vector<__int64> confUserIds;
		m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds, true);
		for (unsigned int i = 0; i < confUserIds.size(); i++)
		{
			__int64 id = confUserIds.at(i);
			this->deleteConferenceUser( id );
		}
		this->deleteConferenceUser( this->getSelfId() );
		this->m_pRoomView->EnableWindow( FALSE );
		this->m_pConference = NULL;
		this->m_pFileTransferMgrDlg->setFileTransferMgr( NULL );
		this->m_pYuntaiDlg->setYuntaiMgr( NULL );
	}
}

void CMeetingRoomFrame::serverReconnect()
{
	this->m_pRoomView->EnableWindow( TRUE );
	m_pFileTransferMgrDlg->setFileTransferMgr( m_pConference->getFileTransferMgr() );
	m_pYuntaiDlg->setYuntaiMgr( m_pConference->getYuntaiMgr() );
}

void CMeetingRoomFrame::setSplitterPosition()
{
	int nSplitterPos = ::GetPrivateProfileInt( "ROOM_POSITION", "splitterPosition", 200, this->m_sUserConfigFile );
	m_pRoomView->getChatTab()->setSplitterPosition( nSplitterPos );
}

void CMeetingRoomFrame::notifyConferenceMsg( string msg )
{
	m_pRoomView->getChatTab()->addMessage( msg );
}

void CMeetingRoomFrame::notifyShowUserPicture( __int64 uid, string fileName )
{
	CAttenderDlg* pDlg = m_pRoomView->getAttenderListDlg()->getAttenderDlgById( uid );
	if( pDlg != NULL )
        pDlg->setUserPicture( fileName );
}

void CMeetingRoomFrame::notifyEvaluateVideoQuality( __int64 userId, int quality )
{
	string s = "认为您的视频质量";
	if( quality == 1 )
		s = s + "好";
	else if( quality == 2 )
		s = s + "一般";
	else if( quality == 3 )
		s = s + "差";
	string* str = new string( m_pConference->getUserRealName( userId ) + s );
	::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
}

void CMeetingRoomFrame::notifyWritingMessage( __int64 receiverId, __int64 senderId, bool bFlag )
{
	m_pRoomView->getChatTab()->notifyWritingMessage( receiverId, senderId, bFlag );
}

void CMeetingRoomFrame::notifyApplySpeak( __int64 applyerId )
{
	if( m_pConference->canControlSpeak() )
	{
		m_pControlSpeakDlg->setApplySpeak( applyerId );
		string* str = new string( m_pConference->getUserRealName( applyerId ) + "申请发言。" );
		::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
}

void CMeetingRoomFrame::notifyAppointSpeaker( __int64 senderId, __int64 speakerId )
{
	if( speakerId == getSelfId() )
	{
		m_bIsSpeaking = TRUE;
		m_bIsApplyingSpeak = FALSE;
		string* str = new string( m_pConference->getUserRealName( senderId ) + "指定由您发言。" );
		::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
	}
	if( m_pConference->canControlSpeak() )
		m_pControlSpeakDlg->setSpeaker( speakerId );
}

void CMeetingRoomFrame::notifyStopSpeak( __int64 senderId, __int64 speakerId )
{
	if( speakerId == getSelfId() )
	{
		if( m_bIsSpeaking )
		{
			string* str = new string( m_pConference->getUserRealName( senderId ) + "停止了您的发言。" );
			::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
		}
		else if( m_bIsApplyingSpeak )
		{
			string* str = new string( m_pConference->getUserRealName( senderId ) + "拒绝了您的发言申请。" );
			::PostMessage( GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, 0, (LPARAM)str );
		}
		m_bIsApplyingSpeak = FALSE;
		m_bIsSpeaking = FALSE;
	}
	if( m_pConference->canControlSpeak() )
		m_pControlSpeakDlg->setSpeaker( speakerId, FALSE );
}

void CMeetingRoomFrame::setSendMyVideoFlag( BOOL bSend )
{
	this->m_pRoomView->setSendMyVideoFlag( bSend );
}

void CMeetingRoomFrame::popupAttenderDlg( __int64 uid )
{
	if( m_pRoomView->getViewMode() == VIEW_MODE_NORMAL )
	{
		startVideo( uid );
	}
	else
	{
		m_pRoomView->getAttenderListDlg()->popupAttenderDlg( uid );
	}
}

void CMeetingRoomFrame::popbackAllVideoDlg( int nMode)
{
	if( nMode == m_pRoomView->getViewMode() )
		return;
	m_pRoomView->getAttenderListDlg()->popbackAllVideoDlg();
}

void CMeetingRoomFrame::setViewMode( int nMode )
{
	m_pRoomView->setViewMode( nMode );
}

void CMeetingRoomFrame::addConferenceUser(ConferenceUser* pConfUser)
{
	this->m_pRoomView->getAttenderListDlg()->addConferenceUser(pConfUser, pConfUser->getId() == getSelfId());
	if( pConfUser->getId() != getSelfId() )
		m_pControlDlg->addConferenceUser( pConfUser->getId(), pConfUser->getRealName(), pConfUser->getUserType() != ConferenceUser::NormalUser );
	if( pConfUser->getId() != getSelfId() )
		m_pControlSpeakDlg->addConferenceUser( pConfUser->getId(), pConfUser->getRealName() );

	if( pConfUser->getUserType() == ConferenceUser::NormalUser )
	{
		int n = m_pRoomView->getAttenderListDlg()->getConfUserCount();
		CString s;
		s.Format("%d 人", n);
		m_pStatusBarDlg->setConfUserNum( s );	
	}
	if( pConfUser->getId() == getSelfId() )
		m_pRoomView->setButtonStatus();
	else
	{
		if( pConfUser->getUserType() == ConferenceUser::NormalUser )
		{
			string msg = string("用户[") + pConfUser->getRealName() + string("]进入了会议室");
			this->notifyConferenceMsg( msg );
		}
	}
}

void CMeetingRoomFrame::deleteConferenceUser(USERID uid, bool bVirtualUser)
{
	m_pRoomView->getAttenderListDlg()->deleteConferenceUser(uid);
	if( !bVirtualUser )
	{
        string msg = string("用户[") + m_pConference->getUserRealName( uid ) + string("]退出了会议室");
		this->notifyConferenceMsg( msg );
		m_pRoomView->getChatTab()->closeChatwith(uid);
		if( m_pControlSpeakDlg )
			m_pControlSpeakDlg->deleteConferenceUser( uid );
		this->m_pRoomView->getChatTab()->notifyWritingMessage( 0, uid, false );

		int n = m_pRoomView->getAttenderListDlg()->getConfUserCount();
		CString s;
		s.Format("%d 人", n);
		m_pStatusBarDlg->setConfUserNum( s );	
	}
	if( m_pControlDlg )
		m_pControlDlg->deleteConferenceUser( uid );
}

int CMeetingRoomFrame::getConfUserCount()
{
	return m_pRoomView->getAttenderListDlg()->getConfUserCount();
}

void CMeetingRoomFrame::receivedChatMessage(USERID senderId, string msg, BOOL bPublic /*= FALSE*/)
{
	m_pRoomView->getChatTab()->addChatMsg(senderId, m_pConference->getUserRealName(senderId).c_str(), msg.c_str(), bPublic);
}

void CMeetingRoomFrame::closeVideoWindow(USERID uid)
{
	m_pRoomView->getAttenderListDlg()->showVideoWindow(uid, false);
}

void CMeetingRoomFrame::showVideoWindow(USERID uid)
{
	m_pRoomView->getAttenderListDlg()->showVideoWindow(uid);
}

HWND CMeetingRoomFrame::getLocalVideoWindow()
{
	return m_pRoomView->getAttenderListDlg()->getLocalVidwoWindow();
}

HWND CMeetingRoomFrame::getLocalCompressionVideoWindow(USERID uid)
{
	return this->m_pRoomView->getAttenderListDlg()->getVideoWindowById(uid);
}

CFileTransferDlg* CMeetingRoomFrame::getSendFileDlg()
{
	return m_pFileTransferMgrDlg->getSendFileDlg();
}

CFileTransferDlg* CMeetingRoomFrame::getRecvFileDlg()
{
	return m_pFileTransferMgrDlg->getRecvFileDlg();
}

CYuntaiDlg* CMeetingRoomFrame::getYuntaiDlg()
{
	return m_pYuntaiDlg;
}

void CMeetingRoomFrame::addYuntaiHolder( __int64 uid, int nCommPort )
{
	this->m_pYuntaiDlg->addYuntaiHolder( uid, this->getUserNameById(uid), nCommPort );
	this->m_pRoomView->getAttenderListDlg()->setYuntaiHolder( uid );
}

CWhiteboardDlg* CMeetingRoomFrame::getWhiteboardDlg()
{
	return this->m_pRoomView->getWhiteboardDlg();
}

void CMeetingRoomFrame::setMyPicture()
{
	CSetmypictureDlg dlg(this);
	dlg.m_sIniFileName = this->m_sUserConfigFile;
	int ret = dlg.DoModal();
	if( m_pConference && ret == IDOK )
	{
		this->setMyPicture( dlg.m_fileName.GetString(), dlg.m_bDisplayNow );
	}
}

void CMeetingRoomFrame::setMyPicture( string fileName, BOOL bShow )
{
	if( !m_pConference )
		return;
	if( bShow )
	{
		m_pRoomView->getAttenderListDlg()->getAttenderDlgById( getSelfId() )->setUserPicture( fileName );
		m_pConference->cmdSetUserPicture( fileName );
	}
	else
	{
		m_pRoomView->getAttenderListDlg()->getAttenderDlgById( getSelfId() )->setUserPicture( "" );
		m_pConference->cmdSetUserPicture( "" );
	}
	this->sendMyVideo( !bShow );
	m_pRoomView->setSendMyVideoFlag( !bShow );
}

void CMeetingRoomFrame::evaluateVideoQuality( __int64 userId, int quality )
{
	if( m_pConference )
		m_pConference->evaluateVideoQuality( userId, quality );
}

string CMeetingRoomFrame::getConferenceName()
{
	return this->m_strConfName;
}

string CMeetingRoomFrame::getServerIP()
{
	return this->m_pServer->getIP();
}

CString CMeetingRoomFrame::getUserConfigFile()
{
	return m_sUserConfigFile;
}

BOOL CMeetingRoomFrame::isContact( __int64 uid )
{
	return m_pServer->getContactMgr()->isContactById( uid );
}

void CMeetingRoomFrame::applyContact( __int64 uid )
{
    if( m_pServer->getContactMgr() )
		m_pServer->getContactMgr()->cmdApplyContact( uid, this->getSelfName(), -1);
}

void CMeetingRoomFrame::sendWritingMessageFlag( __int64 receiverId, bool bFlag )
{
	this->m_pConference->cmdWritingMessage( receiverId, bFlag );
}

void CMeetingRoomFrame::yuntaiControl( __int64 uid )
{
	if( uid == this->getSelfId() )
	{
		this->OnYuntaicontrol();
	}
}

void CMeetingRoomFrame::sendFile()
{
	if( m_pRoomView->getAttenderListDlg()->getConfUserCount() > 2 )
	{
		CContactListDlg* dlg = new CContactListDlg(this);
		dlg->m_strTitle = "发送文件";
		dlg->m_strMessage = "请在下面的参会人员列表中选择要发送文件的人。";
		vector<__int64> v;
		this->m_pConference->getConferenceUserMgr()->getConferenceUserIds(&v);
		for(unsigned int i = 0; i < v.size(); i++)
		{
			if( m_pConference->canReceiveFile(v.at(i)) )
			{
                dlg->m_UserIds.push_back(v.at(i));
				dlg->m_UserNames.push_back(this->m_pConference->getUserRealName(v.at(i)));
			}
		}

		int ret = dlg->DoModal();
		if( m_pConference && ret == IDOK)
		{
			applySendFile( dlg->m_selectedUserIds );
		}
		delete dlg;
	}
	else if( m_pRoomView->getAttenderListDlg()->getConfUserCount() == 2 )
	{
		__int64 id = m_pRoomView->getAttenderListDlg()->getAnotherUserId();
		if( m_pConference->canReceiveFile( id ) )
		{
			vector<__int64> ids;
			ids.push_back(id);
			applySendFile(ids);
		}
	}
}

void CMeetingRoomFrame::dragFileToSend( CString fileName, __int64 uid )
{
	if( !m_pConference )
		return;
	vector<__int64> v;
	if( uid == 0 )
        this->m_pConference->getConferenceUserMgr()->getConferenceUserIds(&v);
	else
		v.push_back( uid );
	for( UINT i = 0; i < v.size(); )
	{
		if( !m_pConference->canReceiveFile( v.at(i) ) )
			v.erase( v.begin() + i );
		else
			i++;
	}
	if( v.size() == 0 )
	{
		return;
	}
	this->m_pFileTransferMgrDlg->applySendFile( fileName, v );
}

void CMeetingRoomFrame::applySendFile( vector<__int64> ids )
{
	this->m_pFileTransferMgrDlg->selectFileToSend( ids );
}

void CMeetingRoomFrame::setIsListenFlag(bool bIsListen, __int64 uid)
{
	this->m_pRoomView->getAttenderListDlg()->setIsListenFlag( bIsListen, uid );
}

void CMeetingRoomFrame::addChatwith(USERID uid)
{
	m_pRoomView->getChatTab()->addChatwith(uid, m_pConference->getUserRealName(uid).c_str(), TRUE);
}

void CMeetingRoomFrame::exit()
{
	if( m_pConference )
		m_pConference->getFileTransferMgr()->exitSendFileThread();
	writeIni();
	CFrameWnd::OnClose();
}

void CMeetingRoomFrame::setConference(RunningConference* pConf)
{
	ASSERT(pConf != NULL);
	m_pConference = pConf;
	m_pFileTransferMgrDlg->setFileTransferMgr(pConf->getFileTransferMgr());
	m_pYuntaiDlg->setYuntaiMgr( pConf->getYuntaiMgr() );
	m_nConfId = m_pConference->getConferenceId();
	m_strConfName = m_pConference->getConferenceName();
}

void CMeetingRoomFrame::setFrameRateAndKeyFrameInterval(int frameRate, int keyFrameInterval)
{
	if( m_pConference )
		m_pConference->m_pTransModel->setFrameRateAndKeyFrameInterval(frameRate, keyFrameInterval);
}

void CMeetingRoomFrame::startVideo(__int64 uid)
{
	if(uid == getSelfId())
	{
		this->showVideoWindow(uid);
		return;
	}
	
	HWND hWnd = this->m_pRoomView->getAttenderListDlg()->getVideoWindowById(uid);
	if(this->m_pConference->getUser(uid)->getUserType() == ConferenceUser::LocalVirtualUser)
	{
		this->showVideoWindow(uid);
		m_pConference->getAVManager()->StartVideoRecv(uid, hWnd, 0, 0);
	}
	else
	{
		m_pConference->getAVManager()->StartReceiveVideoFromUser(uid, hWnd);
	}
}

void CMeetingRoomFrame::stopVideo(__int64 uid)
{
	if( this->m_pConference->getUser(uid)->getUserType() == ConferenceUser::LocalVirtualUser)
	{
		m_pConference->getAVManager()->EndVideoRecv(uid);
		this->closeVideoWindow(uid);
		return;
	}
	this->closeVideoWindow(uid);
	if(uid == getSelfId())
	{
		return;
	}
    m_pConference->getAVManager()->StopReceiveVideoFromUser(uid);
}

void CMeetingRoomFrame::startAudio(__int64 uid)
{
	if( uid == getSelfId() )
		return;
	m_pConference->getAVManager()->StartReceiveAudioFromUser(uid);
}

void CMeetingRoomFrame:: stopAudio(__int64 uid)
{
	if( uid == getSelfId() )
		return;
	m_pConference->getAVManager()->StopReceiveAudioFromUser(uid);
	setIsListenFlag( false, uid );
}

BOOL CMeetingRoomFrame::canStartVideo(__int64 uid)
{
	return m_pConference->canReceiveVideo() 
		   && m_pConference->canSendVideo(uid) && !(m_pConference->getAVManager()->isReceiveVideo(uid));
}

BOOL CMeetingRoomFrame::canStopVideo(__int64 uid)
{
	return !isBeenControledUser() && m_pConference->getAVManager()->isReceiveVideo(uid);
}

BOOL CMeetingRoomFrame::canStartAudio(__int64 uid)
{
	return m_pConference->canReceiveAudio() 
		   && m_pConference->canSendAudio(uid) && !(m_pConference->getAVManager()->isReceiveAudio(uid));
}

BOOL CMeetingRoomFrame::canStopAudio(__int64 uid)
{
	return !isBeenControledUser() && m_pConference->getAVManager()->isReceiveAudio(uid);
}

BOOL CMeetingRoomFrame::isUserInConference( __int64 uid )
{
	return m_pConference->isUserInConference( uid );
}

void CMeetingRoomFrame::kickUserFromRoom( __int64 userId )
{
	this->m_pConference->cmdKickUser( userId );
}

void CMeetingRoomFrame::deleteUserFromConference( __int64 userId )
{
	this->m_pConference->cmdDeleteUserFromConference( userId );
}

void CMeetingRoomFrame::getConferneceUsers( vector<__int64>& v )
{
	m_pConference->getConferenceUserMgr()->getConferenceUserIds( &v );
}

BOOL CMeetingRoomFrame::isInstantConference()
{
	return m_pConference->isInstantConference();
}

__int64 CMeetingRoomFrame::getConferenceId()
{
	return m_nConfId;
}

__int64 CMeetingRoomFrame::getSelfId()
{
	return m_pServer->getId();
}

string CMeetingRoomFrame::getSelfName()
{
	return m_pConference->getSelf()->getRealName();
}

string CMeetingRoomFrame::getSelfUserName()
{
	return m_pServer->getUserName();
}

string CMeetingRoomFrame::getUserNameById(__int64 uid)
{
	return m_pConference->getUserRealName( uid );
}

BOOL CMeetingRoomFrame::isBeenControledUser()
{
	return m_pConference->isBeenControledUser();
}

void CMeetingRoomFrame::sendChatMsg(CString msg, USERID uid)
{
	if( uid == 0 )
		m_pConference->cmdChatPublic(msg.GetString());
	else
		m_pConference->cmdChat(uid, msg.GetString());
}

void CMeetingRoomFrame::watchUser( __int64 uid, string beWatched, int nViewMode )
{
	m_pConference->cmdWatchUser( uid, beWatched, nViewMode );
}

void CMeetingRoomFrame::listenToUser( __int64 uid, string beListened )
{
	m_pConference->cmdListenToUser( uid, beListened );
}

void CMeetingRoomFrame::sendMyVideo(BOOL bSend)
{
	m_pConference->sendMyVideo(bSend);
	CWnd* pWnd = CWnd::FromHandle( this->getLocalVideoWindow() );
	pWnd->InvalidateRect( NULL );
}

void CMeetingRoomFrame::appointSpeaker( __int64 uid )
{
	m_pConference->cmdAppointSpeaker( uid );
}

void CMeetingRoomFrame::stopSpeaker( __int64 uid )
{
	m_pConference->cmdStopSpeak( uid );
}

BOOL CMeetingRoomFrame::canKickUser()
{
	return m_pConference->canKickUser();
}

BOOL CMeetingRoomFrame::canSendFile()
{
    return m_pConference && m_pConference->canSendFile();
}

BOOL CMeetingRoomFrame::canSendFileToUser( __int64 uid )
{
	return m_pConference && m_pConference->canSendFile() && m_pConference->canReceiveFile( uid );
}

BOOL CMeetingRoomFrame::canInviteUser()
{
	return m_pConference->canInviteUser();
}

void CMeetingRoomFrame::inviteDesktop(__int64 uid)
{
	m_pConference->getDesktopManager()->cmdInviteDesktop(uid);
	string msg = string("发送桌面共享邀请到用户") + m_pConference->getUserRealName( uid );
	this->notifyConferenceMsg( msg );
}

void CMeetingRoomFrame::stopSendDesktop(__int64 uid)
{
	m_pConference->getDesktopManager()->cmdStopSendDesktop(uid);
}

void CMeetingRoomFrame::applyDesktop(__int64 uid)
{
	m_pConference->getDesktopManager()->cmdApplyDesktop(uid);	
	string msg = string("发送桌面共享申请到用户") + m_pConference->getUserRealName( uid );
	this->notifyConferenceMsg( msg );
}

void CMeetingRoomFrame::stopReceiveDesktop(__int64 uid)
{
	m_pConference->getDesktopManager()->cmdStopReceiveDesktop(uid);
}

void CMeetingRoomFrame::applyControlDesktop(__int64 uid)
{
	m_pConference->getDesktopManager()->cmdApplyControlDesktop(uid);
	string msg = string("发送远程控制申请到用户") + m_pConference->getUserRealName( uid );
	this->notifyConferenceMsg( msg );
}

void CMeetingRoomFrame::stopSendControlDesktop(__int64 uid)
{
	m_pConference->getDesktopManager()->cmdStopSendControlDesktop(uid);
}

void CMeetingRoomFrame::stopReceiveControlDesktop()
{
	m_pConference->getDesktopManager()->cmdStopReceiveControlDesktop();
}

BOOL CMeetingRoomFrame::canInviteDesktop(__int64 uid)
{
	return m_pConference->getDesktopManager()->canInviteDesktop(uid);
}

BOOL CMeetingRoomFrame::canStopSendDesktopToUser(__int64 uid)
{
	return m_pConference->getDesktopManager()->canStopSendDesktopToUser(uid);
}

BOOL CMeetingRoomFrame::canApplyDesktop(__int64 uid)
{
	if( uid == 0 )
		return m_pConference->canReceiveDesktop();
	return m_pConference->getDesktopManager()->canApplyDesktop(uid);
}

BOOL CMeetingRoomFrame::canStopReceiveDesktopFromUser(__int64 uid)
{
	return m_pConference->getDesktopManager()->canStopReceiveDesktopFromUser(uid);
}

BOOL CMeetingRoomFrame::canApplyControlDesktop(__int64 uid)
{
	return m_pConference->getDesktopManager()->canApplyControlDesktop(uid);
}

BOOL CMeetingRoomFrame::canStopSendControlDesktopToUser(__int64 uid)
{
	return m_pConference->getDesktopManager()->canStopSendControlDesktopToUser(uid);
}

BOOL CMeetingRoomFrame::canStopReceiveControlDesktopFromUser(__int64 uid)
{
	return m_pConference->getDesktopManager()->canStopReceiveControlDesktopFromUser(uid);
}

void CMeetingRoomFrame::writeIni()
{
	if( !IsWindowVisible() )
		return;

	// Save position
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_STYLE);
	if((l&WS_MINIMIZE))
		this->ShowWindow( SW_RESTORE );
	CRect rc;
	GetWindowRect(&rc);
	char buffer[64];
	_snprintf(buffer, 64, "%d", rc.top);
	::WritePrivateProfileString( "ROOM_POSITION", "top", buffer, m_sUserConfigFile );
	_snprintf(buffer, 64, "%d", rc.left);
	::WritePrivateProfileString( "ROOM_POSITION", "left", buffer, m_sUserConfigFile );
	_snprintf(buffer, 64, "%d", rc.Width());
	::WritePrivateProfileString( "ROOM_POSITION", "width", buffer, m_sUserConfigFile );
	_snprintf(buffer, 64, "%d", rc.Height());
	::WritePrivateProfileString( "ROOM_POSITION", "height", buffer, m_sUserConfigFile );	

	CString s = m_pRoomView->getAttenderListDlg()->IsWindowVisible() ? "1" : "0";
    ::WritePrivateProfileString( "ROOM_POSITION", "showUserList", s, m_sUserConfigFile );
	itoa( m_pRoomView->getChatTab()->getSplitterPosition(), buffer, 10 );
	::WritePrivateProfileString( "ROOM_POSITION", "splitterPosition", buffer, m_sUserConfigFile );
}

void CMeetingRoomFrame::switchViewmode()
{
	int nMode = m_pRoomView->getViewMode();
	if( nMode == VIEW_MODE_NORMAL )
		m_pRoomView->setViewMode( VIEW_MODE_VGA );
	else if( nMode == VIEW_MODE_VGA )
		m_pRoomView->setViewMode( VIEW_MODE_DIALOG );
	else if( nMode == VIEW_MODE_DIALOG )
		m_pRoomView->setViewMode( VIEW_MODE_CIF );
	else if( nMode == VIEW_MODE_CIF )
		m_pRoomView->setViewMode( VIEW_MODE_QCIF );
	else if( nMode == VIEW_MODE_QCIF )
		m_pRoomView->setViewMode( VIEW_MODE_NORMAL );
}

void CMeetingRoomFrame::OnConferenceInfo()
{
	if( m_pConference )
	{
		Conference* pConference = this->m_pConference->getConference();
		CConfInfoDlg dlg( pConference, this );
		dlg.DoModal();
	}
}

void CMeetingRoomFrame::OnViewconfhistory()
{
	CString path = PathHelper::getConferenceMsgPath( m_pServer->getUserName().c_str(), m_pServer->getIP().c_str() );
	path = path + "\\";
	path = path + this->m_strConfName.c_str();
	path = path + ".xml";
	if( !PathHelper::isFileExist( path ) )
	{
		AfxMessageBox( "对不起，还没有这个会议的文字记录。" );
		return;
	}
	CShowXMLDlg dlg( path, "查看会议记录", this );
	dlg.DoModal();
}

void CMeetingRoomFrame::OnInviteConference()
{
	CContactListDlg* dlg = new CContactListDlg(this);
	dlg->m_strTitle = "邀请联系人加入会议";
	dlg->m_strMessage = "请在下面的联系人列表中选择要邀请参加会议的人。";
	vector<Contact*> v;
	m_pServer->getContactMgr()->getContacts( v, CONTACT_ONLINE );
	for( unsigned int i = 0; i < v.size(); i++ )
	{
		__int64 id =  v.at(i)->getId();
		if( !m_pConference->isUserInConference( id ) )
		{
			dlg->m_UserIds.push_back(id);
			dlg->m_UserNames.push_back( v.at(i)->getRealName() );
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for( unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++ )
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( !m_pConference->isUserInConference(uid) )
				m_pConference->cmdInviteConference( uid, m_pConference->getConferenceId() );
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateInviteConference(CCmdUI *pCmdUI)
{
    pCmdUI->Enable( m_pConference && m_pConference->canInviteUser() );
}

void CMeetingRoomFrame::OnKickUser()
{
	CContactListDlg* dlg = new CContactListDlg();

	dlg->m_strTitle = "驱逐人员";
	dlg->m_strMessage = "请在下面的参会人员列表中选择要驱逐出会议的人。";
	vector<__int64> v;
	this->m_pConference->getConferenceUserMgr()->getConferenceUserIds(&v);
	for( unsigned int i = 0; i < v.size(); i++ )
	{
		dlg->m_UserIds.push_back(v.at(i));
		dlg->m_UserNames.push_back(this->m_pConference->getUserRealName(v.at(i)));
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			if( isUserInConference(dlg->m_selectedUserIds.at(i) ) )
				m_pConference->cmdKickUser(dlg->m_selectedUserIds.at(i));
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateKickUser(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canKickUser() );
}

void CMeetingRoomFrame::OnCenterControl()
{
	m_pControlDlg->ShowWindow( TRUE );
}

void CMeetingRoomFrame::OnUpdateCenterControl(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canControlUser() );
}

void CMeetingRoomFrame::OnSendFile()
{
	m_pFileTransferMgrDlg->ShowWindow(SW_SHOW);
}

void CMeetingRoomFrame::OnUpdateSendFile(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canSendFile() );
}

void CMeetingRoomFrame::OnOpenReceived()
{
	ShellExecute(NULL, "open", FileTransferMgr::getIncomeFileSavePosition().c_str(), NULL, NULL, SW_SHOWNORMAL);
}

void CMeetingRoomFrame::OnExit()
{
	OnClose();
}

void CMeetingRoomFrame::OnApplyspeak()
{
	if( m_bIsSpeaking )
	{
		m_bIsSpeaking = FALSE;
		m_pConference->cmdStopSpeak( getSelfId() );
	}
	else
	{
		m_pConference->cmdApplySpeak();
		m_bIsApplyingSpeak = TRUE;
	}
}

void CMeetingRoomFrame::OnUpdateApplyspeak(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canApplySpeak() );
	pCmdUI->SetText( m_bIsSpeaking ? "停止发言" : "申请发言" );
}

void CMeetingRoomFrame::OnControlspeak()
{
	m_pControlSpeakDlg->ShowWindow( TRUE );
}

void CMeetingRoomFrame::OnUpdateControlspeak(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canControlSpeak() );
}

void CMeetingRoomFrame::OnViewConfuserlist()
{
	CRect rc;
	GetWindowRect( &rc );
	m_pRoomView->m_bShowUserList = !m_pRoomView->m_bShowUserList;
	if( m_pRoomView->m_bShowUserList )
		rc.right = rc.right + VIDEO_WINDOW_WIDTH + 11;
	else
		rc.right = rc.right - VIDEO_WINDOW_WIDTH - 11;
	MoveWindow( rc );
}

void CMeetingRoomFrame::OnUpdateViewConfuserlist(CCmdUI *pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->m_bShowUserList );
}

void CMeetingRoomFrame::OnSortattenders()
{
	if( m_pConference )
		this->m_pRoomView->getAttenderListDlg()->sortAttenders();
}

void CMeetingRoomFrame::OnViewNormal()
{
	m_pRoomView->setViewMode( VIEW_MODE_NORMAL );
}

void CMeetingRoomFrame::OnUpdateViewNormal(CCmdUI *pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getViewMode() == VIEW_MODE_NORMAL );
	pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnViewOneVga()
{
	m_pRoomView->setViewMode( VIEW_MODE_VGA );
}

void CMeetingRoomFrame::OnUpdateViewOneVga(CCmdUI *pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getViewMode() == VIEW_MODE_VGA );
	pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnViewDialog()
{
	m_pRoomView->setViewMode( VIEW_MODE_DIALOG );
}

void CMeetingRoomFrame::OnUpdateViewDialog(CCmdUI *pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getViewMode() == VIEW_MODE_DIALOG );
	pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnViewFourCif()
{
	m_pRoomView->setViewMode( VIEW_MODE_CIF );
}

void CMeetingRoomFrame::OnUpdateViewFourCif(CCmdUI *pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getViewMode() == VIEW_MODE_CIF );
	pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnViewSixteenQcif()
{
	m_pRoomView->setViewMode( VIEW_MODE_QCIF );
}

void CMeetingRoomFrame::OnUpdateViewSixteenQcif(CCmdUI *pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getViewMode() == VIEW_MODE_QCIF );
	pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnSeprateVideo()
{
	if( m_pRoomView->getViewMode() == VIEW_MODE_NORMAL )
	{
		m_pRoomView->setViewMode( VIEW_MODE_VGA );
	}
	m_pRoomView->setDualMoniterDisplay();
}

void CMeetingRoomFrame::OnUpdateSeprateVideo(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( ::GetSystemMetrics( SM_CMONITORS ) == 2 && m_pConference && !isBeenControledUser() );
	pCmdUI->SetCheck( m_pRoomView->m_bDualMoniterDisplay ); 
}

void CMeetingRoomFrame::OnApplyVideo()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "观看视频";
	dlg->m_strMessage = "请在下面的联系人列表中选择要观看视频的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds, true);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//如果该用户有发送视频的权限，且当前没有观看该用户的视频，则加到申请列表中
		if( m_pConference->canSendVideo(id) && !m_pConference->getAVManager()->isReceiveVideo(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			// 由于消息的异步关系，这里再作一次判断
			if( m_pConference->isUserInConference( uid ) )
				startVideo(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateApplyVideo(CCmdUI* pCmdUI) 
{
	pCmdUI->Enable( m_pConference && m_pConference->canReceiveVideo() );
}

void CMeetingRoomFrame::OnStopReceiveVideo()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "停止观看视频";
	dlg->m_strMessage = "请在下面的联系人列表中选择要停止观看的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds, true);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		// 如果正在接收该用户的视频，则将该用户加到停止列表中
		if( m_pConference->getAVManager()->isReceiveVideo(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			// 由于消息的异步关系，这里再作一次判断
			if( m_pConference->getAVManager()->isReceiveVideo(uid) )
				stopVideo( uid );
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStopReceiveVideo(CCmdUI* pCmdUI) 
{	
	pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnStartListen()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "收听声音";
	dlg->m_strMessage = "请在下面的联系人列表中选择要收听的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//如果该用户有发送声音的权限，且当前没有收听该用户，则加到申请列表中
		if( m_pConference->canSendAudio(id) && !m_pConference->getAVManager()->isReceiveAudio(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			// 由于消息的异步关系，这里再作一次判断
			if( m_pConference->isUserInConference( uid ) )
				startAudio(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStartListen(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canReceiveAudio());
}

void CMeetingRoomFrame::OnStopListen()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "停止收听";
	dlg->m_strMessage = "请在下面的联系人列表中选择要停止收听的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		// 如果正在收听该用户，则将该用户加到停止列表中
		if( m_pConference->getAVManager()->isReceiveAudio(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			// 由于消息的异步关系，这里再作一次判断
			if( m_pConference->getAVManager()->isReceiveAudio(uid) )
				stopAudio(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStopListen(CCmdUI *pCmdUI)
{
    pCmdUI->Enable( m_pConference && !m_pConference->isBeenControledUser() );
}

void CMeetingRoomFrame::OnYuntaicontrol()
{
	if( !m_pYuntaiDlg->IsWindowVisible() )
	{
		m_pYuntaiDlg->ShowWindow(TRUE);
	}
}

void CMeetingRoomFrame::OnUpdateYuntaicontrol(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && this->m_pRoomView->getAttenderListDlg()->hasYuntai() );
}

void CMeetingRoomFrame::OnChangevideoquality()
{
	CVideoQualityDlg dlg;
	dlg.DoModal();
}

void CMeetingRoomFrame::OnStartDesktopServer()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "邀请观看我的桌面";
	dlg->m_strMessage = "请在下面的联系人列表中选择要邀请观看我的桌面的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//将可以邀请观看我的桌面的用户加入列表
		if( canInviteDesktop(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( m_pConference->isUserInConference(uid) && canInviteDesktop(uid) )
				inviteDesktop(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStartDesktopServer(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canSendDesktop() );
}

void CMeetingRoomFrame::OnStopDesktopServer()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "停止桌面共享";
	dlg->m_strMessage = "请在下面的联系人列表中选择要停止共享的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//将可以停止观看我的桌面的用户加入列表
		if( canStopSendDesktopToUser(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( canStopSendDesktopToUser(uid) )
				stopSendDesktop(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStopDesktopServer(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->getDesktopManager()->canStopSendDesktopToUser() );
}

void CMeetingRoomFrame::OnApplyDesktopShare()
{
	CContactListDlg* dlg = new CContactListDlg(this);
	dlg->m_strTitle = "申请观看桌面";
	dlg->m_strMessage = "请在下面的联系人列表中选择要观看桌面的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//将可以申请观看桌面的用户加入列表
		if( canApplyDesktop(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( m_pConference->isUserInConference(uid) && canApplyDesktop(uid) )
				applyDesktop(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateApplyDesktopShare(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->canReceiveDesktop() );
}

void CMeetingRoomFrame::OnStopDesktopShare()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "停止观看桌面";
	dlg->m_strMessage = "请在下面的联系人列表中选择要停止观看的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//将可以停止观看桌面的用户加入列表
		if( canStopReceiveDesktopFromUser(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( canStopReceiveDesktopFromUser(uid) )
				stopReceiveDesktop(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStopDesktopShare(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->getDesktopManager()->canStopReceiveDesktopFromUser() );
}

void CMeetingRoomFrame::OnApplyDesktopControl()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "申请桌面控制";
	dlg->m_strMessage = "请在下面的联系人列表中选择要控制的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//将可以申请控制桌面的用户加入列表
		if( canApplyControlDesktop(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( m_pConference->isUserInConference(uid) && canApplyControlDesktop(uid) )
				applyControlDesktop(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateApplyDesktopControl(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->getDesktopManager()->canApplyControlDesktop() );
}

void CMeetingRoomFrame::OnStopDesktopControl()
{
	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strTitle = "停止桌面控制";
	dlg->m_strMessage = "请在下面的联系人列表中选择要停止控制的人。";
	vector<__int64> confUserIds;
	m_pConference->getConferenceUserMgr()->getConferenceUserIds(&confUserIds);
	
	for (unsigned int i = 0; i < confUserIds.size(); i++)
	{
		__int64 id = confUserIds.at(i);
		//将可以停止控制桌面的用户加入列表
		if( canStopSendControlDesktopToUser(id) )
		{
			dlg->m_UserIds.push_back(id);
			string name = m_pConference->getConferenceUserMgr()->getConferenceUserByID(id)->getRealName();
			dlg->m_UserNames.push_back(name);
		}
	}
	int ret = dlg->DoModal();
	if( m_pConference && ret == IDOK )
	{
		for(unsigned int i = 0; i < dlg->m_selectedUserIds.size(); i++)
		{
			__int64 uid = dlg->m_selectedUserIds.at(i);
			if( canStopSendControlDesktopToUser(uid) )
				stopSendControlDesktop(uid);
		}
	}
	delete dlg;
}

void CMeetingRoomFrame::OnUpdateStopDesktopControl(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->getDesktopManager()->canStopSendControlDesktopToUser() );
}

void CMeetingRoomFrame::OnStopReceiveDesktopControl()
{
	stopReceiveControlDesktop();
}

void CMeetingRoomFrame::OnUpdateStopReceiveDesktopControl(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->getDesktopManager()->canStopReceiveControlDesktopFromUser() );
}

void CMeetingRoomFrame::OnMark()
{
	if(m_pMarkWnd->GetSafeHwnd())
		m_pMarkWnd->DestroyWindow();

	delete m_pMarkWnd;

	m_pMarkWnd = new CMarkWnd();
	m_pMarkWnd->create();
	m_pMarkWnd->ShowWindow(TRUE);
}

void CMeetingRoomFrame::OnUpdateMark(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pConference && m_pConference->getDesktopManager()->canStopSendDesktopToUser() );
}

void CMeetingRoomFrame::OnHelpContent()
{
    CString path = PathHelper::getHelpFileFullName();
	ShellExecute(NULL, "open", path, NULL, NULL, SW_SHOWNORMAL);
}

LRESULT CMeetingRoomFrame::OnReceiveApplyDesktopMsg(WPARAM wParam, LPARAM lParam)
{
	if( !IsWindowVisible() )
		ShowWindow( SW_SHOWNORMAL );
	if( !IsTopParentActive() )
		FlashWindow(TRUE);
	__int64* pId = (__int64*) wParam;
    string str = m_pConference->getUserRealName( *pId );
	str = str + "申请共享您的桌面，是否同意？";
	int ret = MessageBox( str.c_str(), APP_TITLE, MB_YESNO|MB_ICONQUESTION );
	if( !m_pConference )
	{
		delete pId;
		return 0;
	}
	if( ret == IDYES )
	{
		m_pConference->getDesktopManager()->cmdAgreeDesktop( *pId );
	}
	else if( ret == IDNO )
	{
		m_pConference->getDesktopManager()->cmdRejectDesktop( *pId );
	}
	delete pId;
	return 0;
}

LRESULT CMeetingRoomFrame::OnReceiveInviteDesktopMsg(WPARAM wParam, LPARAM lParam)
{
	if( !IsWindowVisible() )
		ShowWindow( SW_SHOWNORMAL );
	__int64* pId = (__int64*) wParam;

		if(!GetParentOwner()->IsTopParentActive())
			GetParentOwner()->FlashWindow(TRUE);
		string str = m_pConference->getUserRealName( *pId );
		str = str + "邀请您观看对方桌面，是否同意？";
		int ret = MessageBox( str.c_str(), APP_TITLE, MB_YESNO|MB_ICONQUESTION );
		if( !m_pConference )
		{
			delete pId;
			return 0;
		}
		if( ret == IDYES )
		{
			m_pConference->getDesktopManager()->cmdAgreeInviteDesktop( *pId );
		}
		else if( ret == IDNO )
		{
			m_pConference->getDesktopManager()->cmdRejectInviteDesktop( *pId );
		}

	delete pId;
	return 0;
}

LRESULT CMeetingRoomFrame::OnReceiveApplyControlDesktopMsg(WPARAM wParam, LPARAM lParam)
{
	if(!IsTopParentActive())
		FlashWindow(TRUE);
	__int64* pId = (__int64*) wParam;
    string str = m_pConference->getUserRealName( *pId );
	str = str + "申请控制您的桌面，是否同意？";
	int ret = MessageBox( str.c_str(), APP_TITLE, MB_YESNO|MB_ICONQUESTION );
	if( !m_pConference )
	{
		delete pId;
		return 0;
	}
	if( ret == IDYES )
	{
		m_pConference->getDesktopManager()->cmdAgreeControlDesktop( *pId );
	}
	else if( ret == IDNO )
	{
		m_pConference->getDesktopManager()->cmdRejectControlDesktop( *pId );
	}
	delete pId;
	return 0;
}

LRESULT CMeetingRoomFrame::OnReceiveApplySendfileMsg(WPARAM wParam, LPARAM lParam)
{
	if( !IsWindowVisible() )
		ShowWindow( SW_SHOWNORMAL );

	if(!IsTopParentActive())
		FlashWindow(TRUE);

	FileTransferTask* pFtt = (FileTransferTask *) wParam;
	CString s = m_pConference->getUserRealName( pFtt->m_uid ).c_str();
	CString fileName = pFtt->m_fileName.c_str();
	fileName = fileName.Right(fileName.GetLength() - fileName.ReverseFind('\\') - 1);
	s = s + "向您发送文件“ " + fileName + " (";

	char buf[65];
	_i64toa(pFtt->m_fileLength, buf, 10);
	if( pFtt->m_fileLength < 1024 )
	{
		s = s + buf;
		s = s + "字节";
	}
	else if( pFtt->m_fileLength < 1024*1024 )
	{
		_snprintf(buf, 65, "%.2f", (float(pFtt->m_fileLength))/1024);
		s = s + buf;
		s = s + "KB";
	}
	else
	{
		_snprintf(buf, 65, "%.2f", (float(pFtt->m_fileLength))/(1024*1024));
		s = s + buf;
		s = s + "MB";
	}
	s = s + ") ”, 是否同意接收？";

	int ret = MessageBox( s, "文件传送", MB_YESNO );
	if( !m_pConference )
	{
		delete pFtt;
		return 0;
	}
	if( ret == IDYES )
	{
		m_pConference->getFileTransferMgr()->agreeReceiveFile( pFtt->m_uid, pFtt->m_fileName, pFtt->m_fileLength );
	}
	else if( ret == IDNO )
	{
		m_pConference->getFileTransferMgr()->rejectReceiveFile( pFtt->m_uid, pFtt->m_fileName );
	}
	delete pFtt;
	return 0;
}


void CMeetingRoomFrame::OnNewWhiteboard()
{
	m_pRoomView->getWhiteboardDlg()->OnNewWhiteboard();
}

void CMeetingRoomFrame::OnFileOpen()
{}

void CMeetingRoomFrame::OnSaveWhiteboard()
{
    m_pRoomView->getWhiteboardDlg()->OnSaveWhiteboard();
}

void CMeetingRoomFrame::OnRubber()
{
	m_pRoomView->getWhiteboardDlg()->OnRubber();
}

void CMeetingRoomFrame::OnUpdateRubber(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( this->m_pRoomView->getWhiteboardDlg()->m_nMouseMode == ID_RUBBER );
}

void CMeetingRoomFrame::OnSelect()
{
	m_pRoomView->getWhiteboardDlg()->OnSelect();
}

void CMeetingRoomFrame::OnUpdateSelect(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( this->m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_SELECT );
}

void CMeetingRoomFrame::OnLine()
{
	m_pRoomView->getWhiteboardDlg()->OnLine();
}

void CMeetingRoomFrame::OnUpdateLine(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( this->m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_LINE );
}

void CMeetingRoomFrame::OnRectangle()
{
	m_pRoomView->getWhiteboardDlg()->OnRectangle();
}

void CMeetingRoomFrame::OnUpdateRectangle(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_RECTANGLE );
}

void CMeetingRoomFrame::OnEllipse()
{
	m_pRoomView->getWhiteboardDlg()->OnEllipse();
}

void CMeetingRoomFrame::OnUpdateEllipse(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_ELLIPSE );
}

void CMeetingRoomFrame::OnCircle()
{
	m_pRoomView->getWhiteboardDlg()->OnCircle();
}

void CMeetingRoomFrame::OnUpdateCircle(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_CIRCLE );
}

void CMeetingRoomFrame::OnPoly()
{
	m_pRoomView->getWhiteboardDlg()->OnPoly();
}

void CMeetingRoomFrame::OnUpdatePoly(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_POLY );
}

void CMeetingRoomFrame::OnCurve()
{
	m_pRoomView->getWhiteboardDlg()->OnCurve();
}

void CMeetingRoomFrame::OnUpdateCurve(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_CURVE );
}

void CMeetingRoomFrame::OnText()
{
	m_pRoomView->getWhiteboardDlg()->OnText();
}

void CMeetingRoomFrame::OnUpdateText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nMouseMode == IDT_TEXT );
}

void CMeetingRoomFrame::OnWidthThin()
{
	m_pRoomView->getWhiteboardDlg()->OnWidthThin();
}

void CMeetingRoomFrame::OnUpdateWidthThin(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nPenWidth == WIDTH_THIN );
}

void CMeetingRoomFrame::OnWidthNormal()
{
	m_pRoomView->getWhiteboardDlg()->OnWidthNormal();
}

void CMeetingRoomFrame::OnUpdateWidthNormal(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nPenWidth == WIDTH_NORMAL );
}

void CMeetingRoomFrame::OnWidthWide()
{
	m_pRoomView->getWhiteboardDlg()->OnWidthWide();
}

void CMeetingRoomFrame::OnUpdateWidthWide(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_nPenWidth == WIDTH_WIDE );
}

void CMeetingRoomFrame::OnFillFalse()
{
	m_pRoomView->getWhiteboardDlg()->OnFillFalse();
}

void CMeetingRoomFrame::OnUpdateFillFalse(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( !m_pRoomView->getWhiteboardDlg()->m_bFill );
}

void CMeetingRoomFrame::OnFillTrue()
{
	m_pRoomView->getWhiteboardDlg()->OnFillTrue();
}

void CMeetingRoomFrame::OnUpdateFillTrue(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_bFill );
}

void CMeetingRoomFrame::OnColorBlack()
{
	m_pRoomView->getWhiteboardDlg()->OnColorBlack();
}

void CMeetingRoomFrame::OnUpdateColorBlack(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_BLACK );
}

void CMeetingRoomFrame::OnColorWhite()
{
	m_pRoomView->getWhiteboardDlg()->OnColorWhite();
}

void CMeetingRoomFrame::OnUpdateColorWhite(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_WHITE );
}

void CMeetingRoomFrame::OnColorGray()
{
	m_pRoomView->getWhiteboardDlg()->OnColorGray();
}

void CMeetingRoomFrame::OnUpdateColorGray(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_GRAY );
}

void CMeetingRoomFrame::OnColorRed()
{
	m_pRoomView->getWhiteboardDlg()->OnColorRed();
}

void CMeetingRoomFrame::OnUpdateColorRed(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_RED );
}

void CMeetingRoomFrame::OnColorGreen()
{
	m_pRoomView->getWhiteboardDlg()->OnColorGreen();
}

void CMeetingRoomFrame::OnUpdateColorGreen(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_GREEN );
}

void CMeetingRoomFrame::OnColorBlue()
{
	m_pRoomView->getWhiteboardDlg()->OnColorBlue();
}

void CMeetingRoomFrame::OnUpdateColorBlue(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_BLUE );
}

void CMeetingRoomFrame::OnColorPink()
{
	m_pRoomView->getWhiteboardDlg()->OnColorPink();
}

void CMeetingRoomFrame::OnUpdateColorPink(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_PINK );
}

void CMeetingRoomFrame::OnColorYellow()
{
	m_pRoomView->getWhiteboardDlg()->OnColorYellow();
}

void CMeetingRoomFrame::OnUpdateColorYellow(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_YELLOW );
}

void CMeetingRoomFrame::OnColorCyan()
{
	m_pRoomView->getWhiteboardDlg()->OnColorCyan();
}

void CMeetingRoomFrame::OnUpdateColorCyan(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_crCurrentColor == UD_CYAN );
}

void CMeetingRoomFrame::OnSmallText()
{
	m_pRoomView->getWhiteboardDlg()->OnSmallText();
}

void CMeetingRoomFrame::OnUpdateSmallText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_lf.lfHeight == 14 );
}

void CMeetingRoomFrame::OnNormalText()
{
	m_pRoomView->getWhiteboardDlg()->OnNormalText();
}

void CMeetingRoomFrame::OnUpdateNormalText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_lf.lfHeight == 18 );
}

void CMeetingRoomFrame::OnBigText()
{
	m_pRoomView->getWhiteboardDlg()->OnBigText();
}

void CMeetingRoomFrame::OnUpdateBigText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_pRoomView->getWhiteboardDlg()->m_lf.lfHeight == 32 );
}
