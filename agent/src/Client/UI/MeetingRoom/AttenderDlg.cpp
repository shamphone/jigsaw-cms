// ui\meetingroom\AttenderDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "AttenderDlg.h"
#include ".\attenderdlg.h"
#include "roommainfrm.h"
#include "AttenderListDlg.h"
#include "RoomView.h"
#include "VideoBgDlg.h"
#include "Model\room\ConferenceUser.h"
// CAttenderDlg 对话框

IMPLEMENT_DYNAMIC(CAttenderDlg, CDialog)
CAttenderDlg::CAttenderDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CAttenderDlg::IDD, pParent)
{
	m_bIfHasYuntai = false;
	m_bIsPopup = false;
	m_bIsSelf = false;
	m_bShowVideoWindow = false;
	m_pConfUser = NULL;
	m_pParent = pParent;
	m_nLeftMargin = 1;
	m_nTopMargin = 1;
	m_nIndex = -1;
	m_bIsFullScreen = false;
	m_nCurrentVideoSize = VIEW_MODE_QCIF;
}

CAttenderDlg::~CAttenderDlg()
{
}

void CAttenderDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_LOCAL_VIRTUAL_VIDEO, m_localVirtualVideoWindow);
	DDX_Control(pDX, IDC_LISTEN_BTN, m_btnListen);
}


BEGIN_MESSAGE_MAP(CAttenderDlg, CDialog)
	ON_WM_SIZE()
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
	ON_WM_RBUTTONDOWN()
	ON_WM_LBUTTONDOWN()

	ON_COMMAND(ID_LOOKVIDEO, OnLookVideo)
	ON_COMMAND(ID_STOPLOOKVIDEO, OnStopLook)
	ON_COMMAND(ID_CHAT, OnChat)
	ON_COMMAND(ID_INVITE_DESKTOP, OnInviteDesktop)
	ON_COMMAND(ID_APPLY_DESKTOP, OnApplyDesktop)
	ON_COMMAND(ID_STOP_DESKTOP_SEND, OnStopDesktopSend)
	ON_COMMAND(ID_STOP_DESKTOP_RECEIVE, OnStopDesktopReceive)
	ON_COMMAND(ID_APPLY_CONTROL, OnApplyControl)
	ON_COMMAND(ID_STOP_CONTROL, OnStopControl)
	ON_COMMAND(ID_STOP_RECEIVE_CONTROL, OnStopReceiveControl)
	ON_COMMAND(ID_RECEIVE_AUDIO, OnReceiveAudio)
	ON_COMMAND(ID_STOP_AUDIO, OnStopAudio)
	ON_COMMAND(ID_SEND_FILE, OnSendFile)

	ON_WM_LBUTTONDBLCLK()
	ON_BN_CLICKED(IDC_LISTEN_BTN, OnBnClickedListenBtn)
	ON_COMMAND(ID_VIEW_POPUP, OnViewPopup)
	ON_COMMAND(ID_TOPMOST, OnTopmost)
	ON_COMMAND(ID_FULLSCREEN, OnFullscreen)
	ON_COMMAND(ID_QCIF, OnQcif)
	ON_COMMAND(ID_CIF, OnCif)
	ON_COMMAND(ID_VGA, OnVga)
	ON_COMMAND(ID_YUNTAICONTROL, OnYuntaicontrol)
	ON_WM_MOVE()
	ON_COMMAND(ID_APPLY_CONTACT, OnApplyContact)
	ON_COMMAND(ID_VIDEOQUALITY_GOOD, OnVideoqualityGood)
	ON_COMMAND(ID_VIDEOQUALITY_NORMAL, OnVideoqualityNormal)
	ON_COMMAND(ID_VIDEOQUALITY_BAD, OnVideoqualityBad)
	ON_COMMAND(ID_SETMYPICTURE, OnSetmypicture)
	ON_COMMAND(ID_KICKUSER, OnKickuser)
	ON_COMMAND(ID_DELETECONFUSER, OnDeleteconfuser)
END_MESSAGE_MAP()


// CAttenderDlg 消息处理程序

void CAttenderDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	if(m_videoWindow.GetSafeHwnd())
	{
		m_videoWindow.MoveWindow( m_nLeftMargin, 
								  VIDEO_WINDOW_TITLE_HEIGHT + m_nTopMargin, 
								  cx - m_nLeftMargin * 2, 
								  cy - VIDEO_WINDOW_TITLE_HEIGHT - m_nTopMargin * 2);
		m_videoWindow.InvalidateRect( NULL );
		m_localVirtualVideoWindow.MoveWindow( m_nLeftMargin, 
								  VIDEO_WINDOW_TITLE_HEIGHT + m_nTopMargin, 
								  cx - m_nLeftMargin * 2, 
								  cy - VIDEO_WINDOW_TITLE_HEIGHT - m_nTopMargin * 2);
		m_localVirtualVideoWindow.InvalidateRect( NULL );
	}
	if( m_btnListen.GetSafeHwnd() )
	{
		m_btnListen.MoveWindow( cx - 40, 3, 15, 15 );
		m_btnListen.InvalidateRect( NULL );
	}
	InvalidateRect( NULL );
}

BOOL CAttenderDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_brush.CreateSolidBrush( RGB(255, 0, 255) );
	if( m_bIsSelf )
		m_btnListen.ShowWindow(FALSE);
	else
		m_btnListen.setBitmaps(IDB_NOTLISTEN, IDB_NOTLISTEN);

	m_videoWindow.Create( "", WS_VISIBLE|WS_CLIPCHILDREN, CRect(), this, IDC_VIDEO_WINDOW );

	return FALSE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CAttenderDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	// 不为绘图消息调用 CDialog::OnPaint()
	CRect rc, rcTitle, rcClient;
	GetClientRect(&rc);
	rcTitle = rc;
	rcTitle.bottom = VIDEO_WINDOW_TITLE_HEIGHT;
	rcClient = rc;
	rcClient.top = VIDEO_WINDOW_TITLE_HEIGHT;

	CDC memDC;
	memDC.CreateCompatibleDC(&dc);
	CBitmap bmp;
	bmp.CreateCompatibleBitmap(&dc, rc.Width(), rc.Height());
	memDC.SelectObject(&bmp);

	CBitmap title;
	title.LoadBitmap(IDB_ATTENDERDLG);
	DrawTitle( &title, &memDC, rcTitle);

	if( m_pConfUser )
	{
		LOGFONT lf;
		CFont* font = GetFont();
		font->GetLogFont(&lf);
		lf.lfHeight = 15;
		CFont font1;
		font1.CreateFontIndirect(&lf);
		CFont* oldFont = memDC.SelectObject(&font1);
		int nOldMode = memDC.SetBkMode(TRANSPARENT);

		CRect rc;
		m_btnListen.GetWindowRect( &rc );
		ScreenToClient( rc );
		memDC.SetTextColor( RGB( 2, 37, 131 ) );
		memDC.DrawText(m_pConfUser->getRealName().c_str(), CRect(22, 3, rc.left - 1, 20), DT_LEFT);
		memDC.SetBkMode(nOldMode);
		memDC.SelectObject(oldFont);
	}
	dc.BitBlt(0, 0, rc.Width(), rc.Height(), &memDC, 0, 0, SRCCOPY);
}

void CAttenderDlg::DrawTitle(CBitmap *pBitmap, CDC *pDC, CRect rc)
{
	CDC MemDC;
	BITMAP bm;
	pBitmap->GetBitmap(&bm);

	int li_Width = bm.bmWidth;
	int li_Height = bm.bmHeight;

	MemDC.CreateCompatibleDC(pDC);
	CBitmap* pOldBitmap = MemDC.SelectObject(pBitmap);

	int x = rc.left;
	pDC->BitBlt( x, 0, 20, li_Height, &MemDC, 0, 0, SRCCOPY);
	x += 20;
	while( x < rc.Width() ) 
	{
		pDC->BitBlt( x, 0, 150, li_Height, &MemDC, 20, 0, SRCCOPY);
		x += 150;
	}
	pDC->BitBlt( rc.right - 20, 0, 20, li_Height, &MemDC, li_Width - 20, 0, SRCCOPY);

	MemDC.SelectObject(pOldBitmap);
	MemDC.DeleteDC();
}

void CAttenderDlg::OnRButtonDown(UINT nFlags, CPoint point)
{
	CMeetingRoomFrame* pRoom = getRoomFrame();
	if( pRoom->isBeenControledUser() )
		return;

	CMenu menu;
	CMenu* popmenu;

	//判断菜单项是否可用
	if( m_pConfUser->getId() == pRoom->getSelfId() || m_pConfUser->getUserType() != ConferenceUser::NormalUser )
	{
		menu.LoadMenu(IDR_ATTENDERDLG_SELF);
		popmenu = menu.GetSubMenu(0);
		if( this->m_bShowVideoWindow )
			popmenu->EnableMenuItem(ID_LOOKVIDEO, MF_GRAYED);
		else
			popmenu->EnableMenuItem(ID_STOPLOOKVIDEO, MF_GRAYED);

		if( m_pConfUser->getUserType() != ConferenceUser::NormalUser )
		{
			popmenu->RemoveMenu( 0, MF_BYPOSITION );
			popmenu->RemoveMenu( 0, MF_BYPOSITION );
		}
	}
	else
	{
		menu.LoadMenu(IDR_ATTENDERDLG_POPUP);
		popmenu = menu.GetSubMenu(0);

		if(pRoom->isContact(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_APPLY_CONTACT, MF_GRAYED);
		if(!pRoom->canStartAudio(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_RECEIVE_AUDIO, MF_GRAYED);
		if(!pRoom->canStopAudio(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_STOP_AUDIO, MF_GRAYED);
		if(!pRoom->canStartVideo(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_LOOKVIDEO, MF_GRAYED);
		if(!pRoom->canStopVideo(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_STOPLOOKVIDEO, MF_GRAYED);
		if(!pRoom->canApplyControlDesktop(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_APPLY_CONTROL, MF_GRAYED);
		if(!pRoom->canApplyDesktop(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_APPLY_DESKTOP, MF_GRAYED);
		if(!pRoom->canInviteDesktop(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_INVITE_DESKTOP, MF_GRAYED);
		if(!pRoom->canStopReceiveDesktopFromUser(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_STOP_DESKTOP_RECEIVE, MF_GRAYED);
		if(!pRoom->canStopSendControlDesktopToUser(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_STOP_CONTROL, MF_GRAYED);
		if(!pRoom->canStopSendDesktopToUser(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_STOP_DESKTOP_SEND, MF_GRAYED);
		if(!pRoom->canStopReceiveControlDesktopFromUser(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_STOP_RECEIVE_CONTROL, MF_GRAYED);
		if(!pRoom->canSendFileToUser(m_pConfUser->getId()))
			popmenu->EnableMenuItem(ID_SEND_FILE, MF_GRAYED);
		if(!pRoom->canKickUser())
		{
			popmenu->EnableMenuItem(ID_KICKUSER, MF_GRAYED);
			popmenu->EnableMenuItem(ID_DELETECONFUSER, MF_GRAYED);
		}
	}
	if( !(this->m_bIfHasYuntai && this->m_bShowVideoWindow) )
		popmenu->EnableMenuItem(ID_YUNTAICONTROL, MF_GRAYED);
	if( m_bIsPopup )
	{
		popmenu->ModifyMenu( ID_VIEW_POPUP, MF_STRING|MF_BYCOMMAND, ID_VIEW_POPUP, "收回显示视频");
		LONG l = GetWindowLong( this->GetSafeHwnd(), GWL_EXSTYLE );
		if( l & WS_EX_TOPMOST )
			popmenu->CheckMenuItem( ID_TOPMOST, MF_CHECKED );
		if( m_bIsFullScreen )
			popmenu->CheckMenuItem( ID_FULLSCREEN, MF_CHECKED );
		else
		{
			if( m_nCurrentVideoSize == VIEW_MODE_QCIF )
				popmenu->CheckMenuItem( ID_QCIF, MF_CHECKED );
			if( m_nCurrentVideoSize == VIEW_MODE_CIF )
				popmenu->CheckMenuItem( ID_CIF, MF_CHECKED );
			if( m_nCurrentVideoSize == VIEW_MODE_VGA )
				popmenu->CheckMenuItem( ID_VGA, MF_CHECKED );
		}
		if( ((CRoomView*)(m_pParent->GetParent()))->getViewMode() != VIEW_MODE_NORMAL )
		{
			popmenu->EnableMenuItem(ID_TOPMOST, MF_GRAYED);
			popmenu->EnableMenuItem(ID_FULLSCREEN, MF_GRAYED);
			popmenu->EnableMenuItem(ID_VGA, MF_GRAYED);
			popmenu->EnableMenuItem(ID_CIF, MF_GRAYED);
			popmenu->EnableMenuItem(ID_QCIF, MF_GRAYED);
		}
	}
	else
	{
		popmenu->ModifyMenu( ID_VIEW_POPUP, MF_STRING|MF_BYCOMMAND, ID_VIEW_POPUP, "弹出显示视频");
		popmenu->EnableMenuItem(ID_TOPMOST, MF_GRAYED);
		popmenu->EnableMenuItem(ID_FULLSCREEN, MF_GRAYED);
		popmenu->EnableMenuItem(ID_VGA, MF_GRAYED);
		popmenu->EnableMenuItem(ID_CIF, MF_GRAYED);
		popmenu->EnableMenuItem(ID_QCIF, MF_GRAYED);
		if( m_pConfUser->getId() != pRoom->getSelfId() )
		{
			if(!pRoom->canStartVideo(m_pConfUser->getId()) && !m_bShowVideoWindow)
				popmenu->EnableMenuItem(ID_VIEW_POPUP, MF_GRAYED);
		}
	}
	//显示菜单
	CPoint pt1;
	GetCursorPos(&pt1);
	popmenu->TrackPopupMenu(TPM_RIGHTBUTTON|TPM_LEFTALIGN, pt1.x, pt1.y, this);
	popmenu->DestroyMenu();

	//CDialog::OnRButtonDown(nFlags, point);
}

void CAttenderDlg::OnLButtonDown(UINT nFlags, CPoint point)
{
	CRect rc;

	GetClientRect( &rc );
	rc.bottom = 18;
	rc.right = rc.right - 25;
	if( rc.PtInRect( point ) && m_bIsPopup 
		&& ((CRoomView*)(m_pParent->GetParent()))->getViewMode() == VIEW_MODE_NORMAL )
		SendMessage(WM_SYSCOMMAND, 0xF012, 0);

	GetClientRect( &rc );
	rc.bottom = 18;
	rc.left = rc.right - 23;
	if( rc.PtInRect(point) )
	{
		OnRButtonDown(nFlags, point);
		return;
	}
	CDialog::OnLButtonDown(nFlags, point);
}

void CAttenderDlg::OnLButtonDblClk(UINT nFlags, CPoint point)
{
	if( getRoomFrame()->isBeenControledUser() )
		return;

	CRect rc;
    GetClientRect( &rc );
	rc.bottom = 18;
	rc.right -= 40;
	if( rc.PtInRect(point) )
	{
		if( !m_bIsPopup )
		{
			if(!m_bShowVideoWindow)
				getRoomFrame()->startVideo(m_pConfUser->getId());
			else
				getRoomFrame()->stopVideo(m_pConfUser->getId());
		}
	}
	else
	{
		GetClientRect( &rc );
		rc.top = VIDEO_WINDOW_TITLE_HEIGHT;
		if( rc.PtInRect( point ) )
			popUp();
	}
	CDialog::OnLButtonDblClk(nFlags, point);
}

HBRUSH CAttenderDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_LOCAL_VIRTUAL_VIDEO )
	{
		pDC->SetBkColor( RGB(255, 0, 255) );
		pDC->SetBkMode(TRANSPARENT);
		return m_brush;
	}
	return hbr;
}

void CAttenderDlg::OnLookVideo()
{
	getRoomFrame()->startVideo(m_pConfUser->getId());
}

void CAttenderDlg::OnStopLook()
{
	if( m_bIsPopup )
	{
		popUp();
	}
	getRoomFrame()->stopVideo(m_pConfUser->getId());
}

void CAttenderDlg::OnChat()
{
	getRoomFrame()->addChatwith(m_pConfUser->getId());
}

void CAttenderDlg::OnInviteDesktop()
{
	getRoomFrame()->inviteDesktop(m_pConfUser->getId());
}

void CAttenderDlg::OnApplyDesktop()
{
	getRoomFrame()->applyDesktop(m_pConfUser->getId());
}

void CAttenderDlg::OnStopDesktopSend()
{
	getRoomFrame()->stopSendDesktop(m_pConfUser->getId());
}

void CAttenderDlg::OnStopDesktopReceive()
{
	getRoomFrame()->stopReceiveDesktop(m_pConfUser->getId());
}

void CAttenderDlg::OnApplyControl()
{
	getRoomFrame()->applyControlDesktop(m_pConfUser->getId());
}

void CAttenderDlg::OnStopControl()
{
	getRoomFrame()->stopSendControlDesktop(m_pConfUser->getId());
}

void CAttenderDlg::OnStopReceiveControl()
{
	getRoomFrame()->stopReceiveControlDesktop();
}

void CAttenderDlg::OnReceiveAudio()
{
	getRoomFrame()->startAudio(m_pConfUser->getId());
}

void CAttenderDlg::OnStopAudio()
{
	getRoomFrame()->stopAudio(m_pConfUser->getId());
}

void CAttenderDlg::OnSendFile()
{
	vector<__int64> ids;
	ids.push_back( getUserId() );
	getRoomFrame()->applySendFile( ids );
}

void CAttenderDlg::OnBnClickedListenBtn()
{
	if( getRoomFrame()->canStartAudio( m_pConfUser->getId() ) )
		getRoomFrame()->startAudio( m_pConfUser->getId() );
	if( getRoomFrame()->canStopAudio( m_pConfUser->getId() ) )
		getRoomFrame()->stopAudio( m_pConfUser->getId() );
}

HWND CAttenderDlg::getVideoHwnd()
{
	if( m_pConfUser != NULL && m_pConfUser->getUserType() == ConferenceUser::LocalVirtualUser )
		return m_localVirtualVideoWindow.GetSafeHwnd();
	else
		return m_videoWindow.GetSafeHwnd();
}

void CAttenderDlg::setConferenceUser(ConferenceUser* pConfUser)
{
	m_pConfUser = pConfUser;
	if( pConfUser->getUserType() == ConferenceUser::LocalVirtualUser )
	{
		m_videoWindow.ShowWindow(FALSE );
		m_localVirtualVideoWindow.ShowWindow( TRUE );
	}
	if( pConfUser->getUserType() != ConferenceUser::NormalUser )
	{
		m_btnListen.ShowWindow( FALSE );
	}
	if( m_bIsSelf )
	{
		char buf[256];
		::GetPrivateProfileString( "DISPLAY_PICTURE", "file", "", buf, 256, ((CMeetingRoomFrame*)m_pParent->GetParentOwner())->getUserConfigFile() );
		string fileName = string(buf);
		setUserPicture( fileName );
	}
}

__int64 CAttenderDlg::getUserId() 
{
	return m_pConfUser->getId(); 
}

string CAttenderDlg::getName()
{
	return m_pConfUser->getRealName();
}

CMeetingRoomFrame* CAttenderDlg::getRoomFrame()
{
	return (CMeetingRoomFrame*) (m_pParent->GetParentOwner());
}

void CAttenderDlg::setIsListenFlag( bool bIsListen )
{
	if( bIsListen )
		m_btnListen.setBitmaps(IDB_LISTEN, IDB_LISTEN);
	else
		m_btnListen.setBitmaps(IDB_NOTLISTEN, IDB_NOTLISTEN);

	m_btnListen.InvalidateRect( NULL );
}

void CAttenderDlg::resetIndex()
{
	if( m_nIndex != -1 )
		((CRoomView*)(m_pParent->GetParent()))->getVideoBgDlg()->resetIndex( m_nIndex );	
}

void CAttenderDlg::setUserPicture( string fileName )
{
	if( m_pConfUser->getUserType() == ConferenceUser::NormalUser )
	{
		if( fileName == "" )
			FCWin32::LoadImageBitmapRes( m_videoWindow.m_img, MAKEINTRESOURCE( IDB_VIDEOBG ) );
		else
            m_videoWindow.m_img.Load( fileName.c_str() );
		m_videoWindow.InvalidateRect( NULL );
	}
}

void CAttenderDlg::popUp()
{
	LONG lOld = GetWindowLong( this->GetSafeHwnd(), GWL_STYLE );
	int nMode = ((CRoomView*)(m_pParent->GetParent()))->getViewMode();
	ShowWindow(FALSE);
	CVideoBgDlg* pBg = ((CRoomView*)(m_pParent->GetParent()))->getVideoBgDlg();	
	if( !m_bIsPopup )
	{
		((CAttenderListDlg*) m_pParent)->popUp(this, true);
		m_bIsPopup = true;
		if( nMode == VIEW_MODE_NORMAL )
		{
			lOld &= ~WS_CHILD;
			SetWindowLong( this->GetSafeHwnd(), GWL_STYLE, lOld|WS_POPUP|WS_THICKFRAME );
			SetParent( NULL );
			this->OnCif();
			SetWindowPos( &CWnd::wndTopMost, 0,0,0,0, SWP_NOSIZE|SWP_NOMOVE );
			CenterWindow();
		}
		else
		{
			SetParent( pBg );
			CRect rc;
			m_nIndex = pBg->getVideoPosition( &rc, this );
			this->MoveWindow( rc );
		}
	}
	else
	{
		if( nMode == VIEW_MODE_NORMAL )
		{
			lOld &= ~WS_POPUP;
			lOld &= ~WS_THICKFRAME;
			SetWindowLong( this->GetSafeHwnd(), GWL_STYLE, lOld|WS_CHILD );
		}
		else
		{
			pBg->resetIndex( m_nIndex );
			m_nIndex = -1;
		}
		SetParent(m_pParent);
		((CAttenderListDlg*) m_pParent)->popUp(this, false);
		m_bIsPopup = false;
		m_nCurrentVideoSize = VIEW_MODE_QCIF;
		m_bIsFullScreen = false;
	}
	ShowWindow(TRUE);
}

void CAttenderDlg::OnViewPopup()
{
	if(!m_bShowVideoWindow)
		getRoomFrame()->startVideo(m_pConfUser->getId());
	popUp();
}

void CAttenderDlg::OnTopmost()
{
	CRect rc;
	GetWindowRect(&rc);
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_EXSTYLE);
	if(l&WS_EX_TOPMOST)
		SetWindowPos(&CWnd::wndNoTopMost, rc.left, rc.top, rc.Width(), rc.Height(), SWP_SHOWWINDOW);
	else
		SetWindowPos(&CWnd::wndTopMost, rc.left, rc.top, rc.Width(), rc.Height(), SWP_SHOWWINDOW);
}

void CAttenderDlg::OnFullscreen()
{
	if( m_bIsFullScreen )
	{
		OnCif();
		m_bIsFullScreen = false;
	}
	else
	{
		int nX = GetSystemMetrics( SM_CXSCREEN );
		int nY = GetSystemMetrics( SM_CYSCREEN );
		this->MoveWindow( 0, 0, nX, nY );
		m_bIsFullScreen = true;
	}
}

void CAttenderDlg::OnQcif()
{
	CRect rc;
	GetWindowRect( &rc );
	this->MoveWindow( rc.left, rc.top, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );
	m_bIsFullScreen = false;
	m_nCurrentVideoSize = VIEW_MODE_QCIF;
}

void CAttenderDlg::OnCif()
{
	CRect rc;
	GetWindowRect( &rc );
	this->MoveWindow( rc.left, rc.top, 354, 310 );
	m_bIsFullScreen = false;
	m_nCurrentVideoSize = VIEW_MODE_CIF;
}

void CAttenderDlg::OnVga()
{
	CRect rc;
	GetWindowRect( &rc );
	this->MoveWindow( rc.left, rc.top, 706, 598 );
	m_bIsFullScreen = false;
	m_nCurrentVideoSize = VIEW_MODE_VGA;
}

void CAttenderDlg::OnYuntaicontrol()
{
	getRoomFrame()->yuntaiControl( this->m_pConfUser->getId() );
}

void CAttenderDlg::OnMove(int x, int y)
{
	CDialog::OnMove(x, y);
}

void CAttenderDlg::OnApplyContact()
{
	getRoomFrame()->applyContact( this->m_pConfUser->getId() );
}

void CAttenderDlg::OnVideoqualityGood()
{
	getRoomFrame()->evaluateVideoQuality( this->m_pConfUser->getId(), 1 );
}

void CAttenderDlg::OnVideoqualityNormal()
{
	getRoomFrame()->evaluateVideoQuality( this->m_pConfUser->getId(), 2 );
}

void CAttenderDlg::OnVideoqualityBad()
{
	getRoomFrame()->evaluateVideoQuality( this->m_pConfUser->getId(), 3 );
}

void CAttenderDlg::OnSetmypicture()
{
	getRoomFrame()->setMyPicture();
}

void CAttenderDlg::OnKickuser()
{
	this->getRoomFrame()->kickUserFromRoom( this->m_pConfUser->getId() );
}

void CAttenderDlg::OnDeleteconfuser()
{
	this->getRoomFrame()->deleteUserFromConference( this->m_pConfUser->getId() );
}
