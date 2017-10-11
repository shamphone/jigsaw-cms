// RoomView.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "RoomView.h"
#include ".\roomview.h"
#include "RoomMainFrm.h"
#include "VideoBgDlg.h"

// CRoomView

IMPLEMENT_DYNCREATE(CRoomView, CDialog)

CRoomView::CRoomView(CWnd* pParent /*=NULL*/)
	: CDialog(CRoomView::IDD, pParent)
{
	m_bMicMute = false;
	m_bSendMyVideo = TRUE;
	m_nViewMode = VIEW_MODE_NORMAL;
	m_bDualMoniterDisplay = FALSE;
	m_bShowUserList = TRUE;
}

CRoomView::~CRoomView()
{
	m_mixerWaveOut.destroy();
	m_mixerWaveIn.destroy();
}

void CRoomView::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_CHAT_TAB, m_ChatTab);
	DDX_Control(pDX, IDC_SHOWATTENDERLIST_BTN, m_btnShowAttenderList);
	DDX_Control(pDX, IDC_SCROLLUP_BTN, m_btnScrollUp);
	DDX_Control(pDX, IDC_SCROLLDOWN_BTN, m_btnScrollDown);
	DDX_Control(pDX, IDC_MIC_MUTE, m_btnMicMute);
	DDX_Control(pDX, IDC_WAVEOUT_MUTE, m_btnWaveOutMute);
	DDX_Control(pDX, IDC_VOLUME_MIC, m_VolumeMic);
	DDX_Control(pDX, IDC_VOLUME_WAVEOUT, m_VolumeWave);
	DDX_Control(pDX, IDC_INVITE, m_btnInvite);
	DDX_Control(pDX, IDC_SENDFILE, m_btnSendFile);
	DDX_Control(pDX, IDC_SENDVIDEO, m_btnSendVideo);
	DDX_Control(pDX, IDC_APPLYDESKTOP, m_btnApplyDesktop);
	DDX_Control(pDX, IDC_WHITEBOARD_BTN, m_btnWhiteboard);
	DDX_Control(pDX, IDC_SORT_BTN, m_btnSort);
}

BEGIN_MESSAGE_MAP(CRoomView, CDialog)
	ON_WM_SIZE()
	ON_BN_CLICKED(IDC_SHOWATTENDERLIST_BTN, OnBnClickedShowattenderlistBtn)
	ON_WM_PAINT()
	ON_BN_CLICKED(IDC_SCROLLDOWN_BTN, OnBnClickedScrolldownBtn)
	ON_BN_CLICKED(IDC_SCROLLUP_BTN, OnBnClickedScrollupBtn)
	ON_BN_CLICKED(IDC_MIC_MUTE, OnBnClickedMicMute)
	ON_BN_CLICKED(IDC_WAVEOUT_MUTE, OnBnClickedWaveoutMute)
	ON_MESSAGE(MM_MIXM_CONTROL_CHANGE, OnMixerChange)
	ON_WM_HSCROLL()
	ON_WM_CTLCOLOR()
	ON_BN_CLICKED(IDC_INVITE, OnBnClickedInvite)
	ON_BN_CLICKED(IDC_SENDFILE, OnBnClickedSendfile)
	ON_BN_CLICKED(IDC_SENDVIDEO, OnBnClickedSendvideo)
	ON_BN_CLICKED(IDC_APPLYDESKTOP, OnBnClickedApplydesktop)
	ON_BN_CLICKED(IDC_WHITEBOARD_BTN, OnBnClickedWhiteboard)
	ON_BN_CLICKED(IDC_SORT_BTN, OnBnClickedSortBtn)
	ON_WM_SETFOCUS()
END_MESSAGE_MAP()


// CRoomView 消息处理程序

void CRoomView::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);

	if( m_AttenderListDlg.GetSafeHwnd() )
	{
		m_AttenderListDlg.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7, 51, VIDEO_WINDOW_WIDTH, cy - 99);

		showAttenderListDlg( m_bShowUserList );
		if( m_bShowUserList )
		{
			m_btnShowAttenderList.InvalidateRect(NULL);
			//GetDlgItem(IDC_ATTENDERS)->InvalidateRect(NULL);
			m_btnScrollUp.InvalidateRect(NULL);
			m_btnScrollDown.InvalidateRect(NULL);
			m_btnWaveOutMute.InvalidateRect(NULL);
			m_VolumeWave.InvalidateRect(NULL);
			m_btnMicMute.InvalidateRect(NULL);
			m_VolumeMic.InvalidateRect(NULL);
			m_btnSort.InvalidateRect(NULL);
		}
	}

	this->ShowScrollBar(SB_BOTH, FALSE);
}

BOOL CRoomView::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_ImageList.Create(IDB_MSG, 16, 16, RGB(255, 255, 255));
	m_ChatTab.SetImageList(&m_ImageList);
	m_ChatTab.SetBkgndColor( RGB(225, 235, 245) );
	DWORD dwFlags = ETC_FLAT | ETC_SELECTION | ETC_GRADIENT | ETC_BACKTABS;
	m_ChatTab.EnableDraw(); 
	CEnTabCtrl::EnableCustomLook(TRUE, dwFlags);
	m_ChatTab.loadCharFormat();
	m_ChatTab.addChatwith(0, "公共消息", TRUE);
	m_ChatTab.createVideoBgDlg();
	m_ChatTab.createWhiteboardDlg();

	m_AttenderListDlg.Create(IDD_ATTENDERLIST_DLG, this);
	m_bShowUserList = ::GetPrivateProfileInt( "ROOM_POSITION", "showUserList", 1, ((CMeetingRoomFrame*)GetParentOwner())->getUserConfigFile() );
	m_AttenderListDlg.ShowWindow( m_bShowUserList );
	m_btnShowAttenderList.setBitmaps(IDB_HIDEATTENDERLIST, IDB_HIDEATTENDERLIST2);
	m_btnScrollUp.setBitmaps(IDB_SCROLLUP, IDB_SCROLLUP2);
	m_btnScrollDown.setBitmaps(IDB_SCROLLDOWN, IDB_SCROLLDOWN2);
	m_btnInvite.setBitmaps(IDB_INVITE, IDB_INVITE2, IDB_INVITE3, IDB_INVITE4);
	m_btnSendFile.setBitmaps(IDB_SENDFILE, IDB_SENDFILE2, IDB_SENDFILE3, IDB_SENDFILE4);
	m_btnSendVideo.setBitmaps(IDB_SENDVIDEO, IDB_SENDVIDEO2, IDB_SENDVIDEO3, IDB_SENDVIDEO4);
	m_btnApplyDesktop.setBitmaps(IDB_APPLYDESKTOP, IDB_APPLYDESKTOP2, IDB_APPLYDESKTOP3, IDB_APPLYDESKTOP4);
	m_btnWhiteboard.setBitmaps(IDB_WHITEBOARD, IDB_WHITEBOARD2, IDB_WHITEBOARD3);
	m_btnWhiteboard.ShowWindow(FALSE);
	m_btnMicMute.setBitmaps(IDB_MIC, IDB_MIC);

	m_brush.CreateSolidBrush(RGB(216, 225, 240));

	/*初始化音量控制*/
	DWORD dwVolume;
	CString iniFilename = ::GetApp()->getIniFilename();

	// Create Microphone mixer
    int nWaveInId = ::GetPrivateProfileInt("MEDIA_CONFIG", "audio_record_device", WAVE_MAPPER, iniFilename);
    if( nWaveInId >= waveInGetNumDevs())
    {
        nWaveInId = WAVE_MAPPER;
    }
    int nMixerId = Mixer::getMixerIdFromWaveInId(nWaveInId);
    m_mixerWaveIn.create(nMixerId, this->m_hWnd);

    // Display Microphone Volume
    dwVolume = m_mixerWaveIn.getMicrophoneVolume();
	if( dwVolume == 0 )
	{
		m_dwMicV = 0xffff;
		m_bMicMute = true;
		m_btnMicMute.setBitmaps(IDB_MICMUTE, IDB_MICMUTE);
	}
	m_VolumeMic.SetRange(0xffff, 0);
	m_VolumeMic.SetVolume(dwVolume);
	m_VolumeMic.SetParent(this);
	m_VolumeMic.SetVolumeType(0);

	// Create speaker mixer
	int nWaveOutId = ::GetPrivateProfileInt("MEDIA_CONFIG", "audio_play_device", WAVE_MAPPER, iniFilename);
	if( nWaveOutId >= waveOutGetNumDevs())
	{
		nWaveOutId = WAVE_MAPPER;
	}
    nMixerId = Mixer::getMixerIdFromWaveOutId(nWaveOutId);
	m_mixerWaveOut.create(nMixerId, this->m_hWnd);

    // Display Speaker Volume
    dwVolume = m_mixerWaveOut.getMasterVolume();
	m_VolumeWave.SetRange(0xffff, 0);
	m_VolumeWave.SetVolume(dwVolume); 
	m_VolumeWave.SetParent(this);
	m_VolumeWave.SetVolumeType(1);

    // Display Speaker Mute status
    bool isMute = m_mixerWaveOut.isSpeakerMute();
	m_btnWaveOutMute.setBitmaps(isMute ? IDB_HEADPHONEMUTE : IDB_HEADPHONE, 
								isMute ? IDB_HEADPHONEMUTE : IDB_HEADPHONE);
	return TRUE;
}

LRESULT CRoomView::OnMixerChange(WPARAM wparam, LPARAM lparam)
{
	if((DWORD)lparam == m_mixerWaveOut.getSpeakerVolumeControlId())
	{
		DWORD dwVolume = m_mixerWaveOut.getMasterVolume();
		m_VolumeWave.SetVolume(dwVolume);
        TRACE1("Speaker Volume changed, new volume %x\n", dwVolume);
	}
	else if((DWORD)lparam == m_mixerWaveOut.getSpeakerMuteControlId())
	{
		bool isMute = m_mixerWaveOut.isSpeakerMute();
		m_btnWaveOutMute.setBitmaps(isMute ? IDB_HEADPHONEMUTE : IDB_HEADPHONE, 
									isMute ? IDB_HEADPHONEMUTE : IDB_HEADPHONE);
		m_btnWaveOutMute.InvalidateRect( NULL );
        TRACE1("Speaker mute status changed, new status %x\n", isMute);
	}
	else if((DWORD)lparam == m_mixerWaveIn.getMicrophoneVolumeControlId())
	{
		DWORD dwVolume = m_mixerWaveIn.getMicrophoneVolume();
		m_VolumeMic.SetVolume(dwVolume);
		m_btnMicMute.setBitmaps( (dwVolume > 0) ? IDB_MIC : IDB_MICMUTE, (dwVolume > 0) ? IDB_MIC : IDB_MICMUTE );
		m_btnMicMute.InvalidateRect( NULL );
        TRACE1("Microphone Volume changed, new volume %x\n", dwVolume);
	}

	return 0;
}

void CRoomView::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	if (pScrollBar->GetSafeHwnd() == m_VolumeMic.GetSafeHwnd())
	{
		DWORD dwVolume = m_VolumeMic.GetMinVolume() + m_VolumeMic.GetVolume();
		m_mixerWaveIn.setMicrophoneVolume(dwVolume);

		if (dwVolume == 0)
		{
			m_btnMicMute.SetCheck(BST_CHECKED);
		}
		else
		{
			m_btnMicMute.SetCheck(BST_UNCHECKED);
		}
	}
	else if(pScrollBar->GetSafeHwnd() == m_VolumeWave.GetSafeHwnd())
	{
		DWORD dwVolume = m_VolumeWave.GetMinVolume() + m_VolumeWave.GetVolume();
		m_mixerWaveOut.setMasterVolume(dwVolume);
	}
}

void CRoomView::OnBnClickedMicMute()
{
	m_bMicMute = !m_bMicMute;
	if( m_bMicMute )
	{
		m_dwMicV = m_mixerWaveIn.getMicrophoneVolume();
		m_mixerWaveIn.setMicrophoneVolume(0);
		m_btnMicMute.setBitmaps(IDB_MICMUTE, IDB_MICMUTE);
	}
	else
	{
		m_mixerWaveIn.setMicrophoneVolume(m_dwMicV);
		m_btnMicMute.setBitmaps(IDB_MIC, IDB_MIC);
	}
}

void CRoomView::OnBnClickedWaveoutMute()
{
	bool isMute = m_mixerWaveOut.isSpeakerMute();
	m_mixerWaveOut.setSpeakerMute( !isMute );
}

CAttenderListDlg* CRoomView::getAttenderListDlg()
{
	return &m_AttenderListDlg;
}

CChatTab* CRoomView::getChatTab()
{
	return &m_ChatTab;
}

CVideoBgDlg* CRoomView::getVideoBgDlg()
{
	return m_ChatTab.getVideoBgDlg();
}

CWhiteboardDlg* CRoomView::getWhiteboardDlg()
{
	return m_ChatTab.getWhiteboardDlg();
}

void CRoomView::showAttenderListDlg(BOOL bShow)
{
	m_AttenderListDlg.ShowWindow(bShow);
	CRect rc;
	GetClientRect(&rc);
	int cx = rc.Width();
	int cy = rc.Height();

	m_btnInvite.MoveWindow(5, 0, 75, 34);
	m_btnSendFile.MoveWindow(80, 0, 70, 34);
	m_btnSendVideo.MoveWindow(150, 0, 75, 34);
	m_btnApplyDesktop.MoveWindow(225, 0, 96, 34);
	m_btnWhiteboard.MoveWindow(321, 0, 70, 34);
	if(!bShow)
	{
		m_ChatTab.MoveWindow(4, 42, cx - 11, cy - 49);
		m_btnShowAttenderList.MoveWindow(cx - 6, cy/2, 6, 27);
		m_btnShowAttenderList.setBitmaps(IDB_SHOWATTENDERLIST, IDB_SHOWATTENDERLIST2);
	}
	else
	{
		m_btnShowAttenderList.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 17, cy/2, 6, 27);
		m_btnShowAttenderList.setBitmaps(IDB_HIDEATTENDERLIST, IDB_HIDEATTENDERLIST2);
		m_ChatTab.MoveWindow(4, 42, cx - VIDEO_WINDOW_WIDTH - 22, cy - 49);
		//GetDlgItem(IDC_ATTENDERS)->MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7, 42, 144, 18);
		m_btnSort.MoveWindow(cx - 37, 42, 30, 18);
		m_btnScrollUp.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7, 42, VIDEO_WINDOW_WIDTH, 8);
		m_btnScrollDown.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7, cy - 46, VIDEO_WINDOW_WIDTH, 8);
		m_btnWaveOutMute.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7, cy - 29, 24, 22);
		m_VolumeWave.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7 + 24, cy - 29, 66, 22);
		m_btnMicMute.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7 + 24 + 66, cy - 29, 24, 22);
		m_VolumeMic.MoveWindow(cx - VIDEO_WINDOW_WIDTH - 7 + 24 + 66 + 24, cy - 29, 66, 22);
	}
	m_VolumeMic.ShowWindow( bShow );
	m_VolumeWave.ShowWindow( bShow );
	m_btnWaveOutMute.ShowWindow( bShow );
	m_btnMicMute.ShowWindow( bShow );
	//GetDlgItem(IDC_ATTENDERS)->ShowWindow(bShow);
	//m_btnSort.ShowWindow(bShow);
	m_btnScrollUp.ShowWindow(bShow);
	m_btnScrollDown.ShowWindow(bShow);

	InvalidateRect( CRect( 0, 38, cx, 42 ) );
	InvalidateRect( CRect( 0, 38, 4, cy ) );
	InvalidateRect( CRect( 0, cy - 7, cx, cy ) );
	InvalidateRect( CRect( cx - 7, 38, cx, cy ) );
	if( bShow )
	{
		InvalidateRect( CRect( cx - VIDEO_WINDOW_WIDTH - 18, 38, cx - VIDEO_WINDOW_WIDTH - 7, cy ) );
		InvalidateRect( CRect( cx - VIDEO_WINDOW_WIDTH - 14, cy - 29, cx, cy - 39 ) );
	}
}

void CRoomView::setDualMoniterDisplay()
{
	if( !m_bDualMoniterDisplay )
	{
		int x = GetSystemMetrics( SM_XVIRTUALSCREEN );
		int y = GetSystemMetrics( SM_YVIRTUALSCREEN );

		int cx = GetSystemMetrics( SM_CXSCREEN );
		int cy = GetSystemMetrics( SM_CYSCREEN );

		int cx1 = GetSystemMetrics( SM_CXVIRTUALSCREEN );
		int cy1 = GetSystemMetrics( SM_CYVIRTUALSCREEN );

		int x1, y1;
		if( x < 0 )
			x1 = x;
		else
			x1 = cx1 - cx;

		if( y < 0 )
			y1 = y;
		else
			y1 = cy1 - cy;

		LONG lOld = GetWindowLong( getVideoBgDlg()->GetSafeHwnd(), GWL_STYLE );
		lOld &= ~WS_CHILD;
		SetWindowLong( getVideoBgDlg()->GetSafeHwnd(), GWL_STYLE, lOld|WS_POPUP );
		getVideoBgDlg()->SetParent( NULL );
		getVideoBgDlg()->SetWindowText( _T("视频窗口") );

		CRect rc;
		getVideoBgDlg()->GetWindowRect( &rc );
		getVideoBgDlg()->MoveWindow( x1, y1, rc.Width(), rc.Height() );
		HMONITOR hMonitor = ::MonitorFromWindow( getVideoBgDlg()->GetSafeHwnd(), MONITOR_DEFAULTTONEAREST );
		MONITORINFO mi;
		mi.cbSize = sizeof( MONITORINFO );
		::GetMonitorInfo( hMonitor, &mi );
		getVideoBgDlg()->MoveWindow( mi.rcMonitor.left + (mi.rcMonitor.right - mi.rcMonitor.left - rc.Width())/2,
								mi.rcMonitor.top + (mi.rcMonitor.bottom - mi.rcMonitor.top - rc.Height())/2, 
								rc.Width(), rc.Height() );
		getVideoBgDlg()->ShowWindow( TRUE );

		m_btnInvite.ShowWindow( TRUE );
		m_btnSendFile.ShowWindow( TRUE );
		m_btnSendVideo.ShowWindow( TRUE );
		m_btnApplyDesktop.ShowWindow( TRUE );
		//m_btnWhiteboard.ShowWindow( TRUE );
		m_ChatTab.ShowWindow( TRUE );

		m_bDualMoniterDisplay = TRUE;
		m_ChatTab.removeVideoBgTab();
	}
	else
	{
		LONG lOld = GetWindowLong( getVideoBgDlg()->GetSafeHwnd(), GWL_STYLE );
		lOld &= ~WS_POPUP;
		SetWindowLong( getVideoBgDlg()->GetSafeHwnd(), GWL_STYLE, lOld|WS_CHILD );
		getVideoBgDlg()->SetParent( &m_ChatTab );

		CRect rc;
		m_ChatTab.GetWindowRect( &rc );
		getVideoBgDlg()->MoveWindow( 0, 21, rc.Width(), rc.Height() - 21 );
		if( m_nViewMode == VIEW_MODE_NORMAL )
			getVideoBgDlg()->ShowWindow( FALSE );
		else
			m_ChatTab.addVideoBgTab();

		m_bDualMoniterDisplay = FALSE;
	}
}

void CRoomView::setSendMyVideoFlag( BOOL bSend )
{
	m_bSendMyVideo = bSend;
	if( m_bSendMyVideo )
		m_btnSendVideo.setBitmaps(IDB_SENDVIDEO, IDB_SENDVIDEO2, IDB_SENDVIDEO3);
	else
		m_btnSendVideo.setBitmaps(IDB_NOTSENDVIDEO, IDB_NOTSENDVIDEO2, IDB_NOTSENDVIDEO3);

	m_btnSendVideo.InvalidateRect( NULL );
}

void CRoomView::setButtonStatus()
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParent();
	if( !pRoom->canInviteUser() )
		m_btnInvite.EnableWindow( FALSE );
	if( !pRoom->canSendFile() )
		m_btnSendFile.EnableWindow( FALSE );
	if( pRoom->isBeenControledUser() )
		m_btnSendVideo.EnableWindow( FALSE );
	if( !pRoom->canApplyDesktop() )
		m_btnApplyDesktop.EnableWindow( FALSE );
}

void CRoomView::SetVolume(int v, int type)
{
	switch(type)
	{
	case (0):
		m_mixerWaveIn.setMicrophoneVolume(v);
		if( v > 0 )
		{
			this->m_bMicMute = false;
			m_btnMicMute.setBitmaps(IDB_MIC, IDB_MIC);
		}
		else
		{
			this->m_bMicMute = true;
			m_btnMicMute.setBitmaps(IDB_MICMUTE, IDB_MICMUTE);
		}
		m_btnMicMute.InvalidateRect( NULL );
		break;
	case (1):
		m_mixerWaveOut.setMasterVolume(v);
		break;
	}
}

void CRoomView::OnBnClickedShowattenderlistBtn()
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParent();
	pRoom->OnViewConfuserlist();
}

void CRoomView::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	// 不为绘图消息调用 CDialog::OnPaint()
	CRect rc;
	GetClientRect(&rc);

	CDC memDC;
	memDC.CreateCompatibleDC( &dc );
	CBitmap bmp;
	bmp.CreateCompatibleBitmap( &dc, rc.Width(), rc.Height() );
	memDC.SelectObject( &bmp );
	memDC.FillSolidRect( &rc, RGB(228, 240, 254) );

	CDC bkDC;
	bkDC.CreateCompatibleDC(&dc);
	CBitmap back;
	back.LoadBitmap(IDB_ROOMTOPBG);
	bkDC.SelectObject( &back );
	int x = 0;
	while( x < rc.Width() ) 
	{
		memDC.BitBlt( x, 0, 50, 34, &bkDC, 0, 0, SRCCOPY);
 		x += 50;
 	}

	CPen pen;
	pen.CreatePen(PS_SOLID, 1, RGB(186, 197, 225));
	memDC.SelectObject(&pen);
	CBrush brushLeft, brushRight;
	brushLeft.CreateSolidBrush( RGB(216, 225, 240) );
	brushRight.CreateSolidBrush( RGB(238, 241, 249) );
	memDC.SelectObject( &brushRight );

	CRect rcRightTop, rcRightBottom, rcLeft;
	if( m_AttenderListDlg.IsWindowVisible() )
	{
		rcRightTop.left = rc.right - VIDEO_WINDOW_WIDTH - 11;
		rcRightTop.right = rc.right - 3;
		rcRightTop.top = 38;
		rcRightTop.bottom = rc.bottom - 35;
		memDC.RoundRect( rcRightTop, CPoint(10, 10) );

		rcRightBottom.left =  rcRightTop.left;
		rcRightBottom.right = rcRightTop.right;
		rcRightBottom.top = rc.bottom - 33;
		rcRightBottom.bottom = rc.bottom - 3;
		memDC.RoundRect( rcRightBottom, CPoint(10, 10) );

		rcLeft.right = rcRightTop.left - 6;
	}
	else
	{
		rcLeft.right = rc.right - 6;
	}
	rcLeft.top = 38;
	rcLeft.bottom = rc.bottom - 3;
	rcLeft.left = 3;
	memDC.SelectObject( &brushLeft );
	memDC.RoundRect( rcLeft, CPoint(10, 10) );

	dc.BitBlt( 0, 0, rc.Width(), rc.Height(), &memDC, 0, 0, SRCCOPY );
}

void CRoomView::OnBnClickedScrolldownBtn()
{
	m_AttenderListDlg.scrollDown();
}

void CRoomView::OnBnClickedScrollupBtn()
{
	m_AttenderListDlg.scrollUp();
}

HBRUSH CRoomView::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if(	pWnd->GetDlgCtrlID() == IDC_ATTENDERS
		|| pWnd->GetDlgCtrlID() == IDC_SORT_BTN )
	{
		pDC->SetTextColor(RGB(128, 128, 128));
		pDC->SetBkMode(TRANSPARENT);
		hbr = m_brush;
	}
	return hbr;
}

void CRoomView::OnBnClickedInvite()
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParent();
	if( pRoom->canInviteUser() )
		pRoom->OnInviteConference();
}

void CRoomView::OnBnClickedSendfile()
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParent();
	if( pRoom->canSendFile() )
		pRoom->sendFile();
}

void CRoomView::OnBnClickedSendvideo()
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParent();
	if( pRoom->isBeenControledUser() )
		return;

	setSendMyVideoFlag( !m_bSendMyVideo );
	pRoom->sendMyVideo(m_bSendMyVideo);
}

void CRoomView::OnBnClickedApplydesktop()
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParent();
	if( !pRoom->canApplyDesktop() )
		return;
	if( m_AttenderListDlg.getConfUserCount() > 2 )
		pRoom->OnApplyDesktopShare();
	else if( m_AttenderListDlg.getConfUserCount() == 2 )
	{
		__int64 uid = m_AttenderListDlg.getAnotherUserId();
		if( pRoom->canApplyDesktop(uid) )
		{
			pRoom->applyDesktop(m_AttenderListDlg.getAnotherUserId());
		}
	}
}

void CRoomView::OnBnClickedWhiteboard()
{
	this->m_ChatTab.addWhiteboardTab();
}

void CRoomView::OnBnClickedSortBtn()
{
	m_AttenderListDlg.sortAttenders();
}

void CRoomView::setViewMode( int nMode )
{
	if( nMode == getViewMode() )
		return;
	if( getViewMode() == VIEW_MODE_NORMAL )
	{
		m_AttenderListDlg.popbackAllVideoDlg();
	}
	m_nViewMode = nMode;

	getVideoBgDlg()->changeViewMode( nMode );

	if( nMode == VIEW_MODE_NORMAL )
	{
		this->getVideoBgDlg()->ShowWindow( FALSE );
		m_btnInvite.ShowWindow( TRUE );
		m_btnSendFile.ShowWindow( TRUE );
		m_btnSendVideo.ShowWindow( TRUE );
		m_btnApplyDesktop.ShowWindow( TRUE );
		//m_btnWhiteboard.ShowWindow( TRUE );
		this->m_ChatTab.ShowWindow( TRUE );
		if( m_bDualMoniterDisplay )
		{
			this->setDualMoniterDisplay();
		}
		else
		{
			this->m_ChatTab.removeVideoBgTab();
		}
		return;
	}

	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*) GetParent();
	pRoom->ShowWindow( SW_RESTORE );
	CRect rcWindow, rcClient;
	pRoom->GetWindowRect( &rcWindow );
	GetClientRect( &rcClient );
	int ncHeight = rcWindow.Height() - rcClient.Height();
	int ncWidth = rcWindow.Width() - rcClient.Width();
	int nY, nX;

	if( nMode == VIEW_MODE_VGA )
	{
		nX = 704 + 2;
		nY = 576 + VIDEO_WINDOW_TITLE_HEIGHT + 2 + 63;
	}
	else if( nMode == VIEW_MODE_CIF )
	{
		nX = ( 352 + 2 ) * 2;
		nY = ( 288 + VIDEO_WINDOW_TITLE_HEIGHT + 2) * 2 + 63;
	}
	else if( nMode == VIEW_MODE_QCIF )
	{
		nX = ( 176 + 2 ) * 4;
		nY = ( 144 + VIDEO_WINDOW_TITLE_HEIGHT + 2 ) * 4 + 63;
	}
	else if( nMode == VIEW_MODE_DIALOG )
	{
		nX = ( 396 + 2 ) * 2;
		nY = 324 + VIDEO_WINDOW_TITLE_HEIGHT + 2 + 63;
	}

	if( !m_bDualMoniterDisplay )
	{
		int nRight = 0;
		if( m_AttenderListDlg.IsWindowVisible() )
			nRight = VIDEO_WINDOW_WIDTH + 17;
		pRoom->ShowWindow( SW_NORMAL );
		pRoom->MoveWindow( rcWindow.left, rcWindow.top, nX + nRight + ncWidth, nY + ncHeight );
		pRoom->CenterWindow();
		m_ChatTab.addVideoBgTab();
	}
	else
	{
		HMONITOR hMonitor = ::MonitorFromWindow( getVideoBgDlg()->GetSafeHwnd(), MONITOR_DEFAULTTONEAREST );
		MONITORINFO mi;
		mi.cbSize = sizeof( MONITORINFO );
		::GetMonitorInfo( hMonitor, &mi );
		getVideoBgDlg()->MoveWindow( mi.rcMonitor.left + (mi.rcMonitor.right - mi.rcMonitor.left - nX)/2,
								mi.rcMonitor.top + (mi.rcMonitor.bottom - mi.rcMonitor.top - nY)/2, 
								nX, nY );
	}
}

int CRoomView::getViewMode()
{
	return m_nViewMode;
}

void CRoomView::OnSetFocus(CWnd* pOldWnd)
{
	CDialog::OnSetFocus(pOldWnd);
	if( this->m_ChatTab.IsWindowVisible() )
	{
		this->m_ChatTab.SetFocus();
	}
}
