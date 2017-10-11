// UI\MeetingRoom\ControlDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "ControlDlg.h"
#include ".\controldlg.h"
#include "RoomMainFrm.h"
#include "RoomView.h"

// CControlDlg 对话框

IMPLEMENT_DYNAMIC(CControlDlg, CDialogEx)
CControlDlg::CControlDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CControlDlg::IDD, pParent)
{
}

CControlDlg::~CControlDlg()
{
}

void CControlDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control( pDX, IDC_USER_LIST, m_ListUser );
	DDX_Control( pDX, IDC_LOOKVIDEO_LIST, m_ListVideo );
	DDX_Control( pDX, IDC_LISTEN_LIST, m_ListListen );
	DDX_Control( pDX, IDC_BTN_APPLY, m_btnApply );
	DDX_Control( pDX, IDC_BTN_STOPANSWER, m_btnStopAnswer );
	DDX_Control( pDX, IDC_BTN_ANSWER, m_btnAnswer );
}


BEGIN_MESSAGE_MAP(CControlDlg, CDialogEx)
	ON_BN_CLICKED(IDC_SELECT_ALL_CHK, OnBnClickedSelectAllChk)
	ON_BN_CLICKED(IDC_SELECT_ALL_CHK2, OnBnClickedSelectAllChk2)
	ON_BN_CLICKED(IDC_SELECT_ALL_CHK3, OnBnClickedSelectAllChk3)
	ON_WM_DESTROY()
	ON_NOTIFY(NM_CLICK, IDC_LOOKVIDEO_LIST, OnNMClickLookvideoList)
	ON_NOTIFY(NM_DBLCLK, IDC_LOOKVIDEO_LIST, OnNMDblclkLookvideoList)
	ON_NOTIFY(NM_RCLICK, IDC_LOOKVIDEO_LIST, OnNMRclickLookvideoList)
	ON_BN_CLICKED(IDC_VIEW_VGA, OnBnClickedViewVga)
	ON_BN_CLICKED(IDC_VIEW_TWO, OnBnClickedViewTwo)
	ON_BN_CLICKED(IDC_VIEW_CIF, OnBnClickedViewCif)
	ON_BN_CLICKED(IDC_VIEW_QCIF, OnBnClickedViewQcif)
	ON_BN_CLICKED(IDC_VIEW_NORMAL, OnBnClickedViewNormal)
	ON_BN_CLICKED(IDC_BTN_ANSWER, OnBnClickedBtnAnswer)
	ON_BN_CLICKED(IDC_BTN_STOPANSWER, OnBnClickedBtnStopanswer)
	ON_BN_CLICKED(IDC_BTN_APPLY, OnBnClickedBtnApply)
	ON_WM_CTLCOLOR()
	ON_WM_PAINT()
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
END_MESSAGE_MAP()


void CControlDlg::addConferenceUser( __int64 uid, string name, bool bVirtualUser )
{
	__int64* pId = new __int64( uid );
	if( bVirtualUser )
	{
		int n = m_ListVideo.InsertItem( m_ListVideo.GetItemCount(), name.c_str() );
		m_ListVideo.SetItemData( n, (DWORD_PTR)pId );
	}
	else
	{
		m_ListVideo.InsertItem( 0, name.c_str() );
		m_ListVideo.SetItemData( 0, (DWORD_PTR)pId );

		m_ListUser.InsertItem( 0, name.c_str() );
		m_ListUser.SetItemData( 0, (DWORD_PTR)pId );

		m_ListListen.InsertItem( 0, name.c_str() );
		m_ListListen.SetItemData( 0, (DWORD_PTR)pId );
	}
}

void CControlDlg::deleteConferenceUser( __int64 uid )
{
	for( int i = 0; i < m_ListUser.GetItemCount(); i++ )
	{
		__int64* pId = (__int64*) m_ListUser.GetItemData(i);
        if( *pId == uid )
		{
			m_ListUser.DeleteItem(i);
			m_ListVideo.DeleteItem(i);
			m_ListListen.DeleteItem(i);
			delete pId;
			break;
		}
	}
}

void CControlDlg::setViewAsConsultingMode()
{
	setVideoNumber( VIEW_MODE_NORMAL );
	SetWindowText( "回答问题" );
	GetDlgItem( IDC_NUMBEROFVIDEO )->ShowWindow( FALSE );
    GetDlgItem( IDC_VIEW_NORMAL )->ShowWindow( FALSE );
    GetDlgItem( IDC_VIEW_VGA )->ShowWindow( FALSE );
    GetDlgItem( IDC_VIEW_TWO )->ShowWindow( FALSE );
    GetDlgItem( IDC_VIEW_CIF )->ShowWindow( FALSE );
    GetDlgItem( IDC_VIEW_QCIF )->ShowWindow( FALSE );
    GetDlgItem( IDC_STATIC_VIEWMODE )->ShowWindow( FALSE );
    GetDlgItem( IDC_STATIC_FRAME )->ShowWindow( FALSE );
    GetDlgItem( IDC_BTN_APPLY )->ShowWindow( FALSE );
    GetDlgItem( IDCANCEL )->ShowWindow( FALSE );
    GetDlgItem( IDOK )->ShowWindow( FALSE );
	GetDlgItem( IDC_STATIC_AUDIO )->ShowWindow( FALSE );
	GetDlgItem( IDC_LISTEN_LIST )->ShowWindow( FALSE );
	GetDlgItem( IDC_SELECT_ALL_CHK3 )->ShowWindow( FALSE );
    GetDlgItem( IDC_BTN_ANSWER )->ShowWindow( TRUE );
    GetDlgItem( IDC_BTN_STOPANSWER )->ShowWindow( TRUE );

	GetDlgItem( IDC_STATIC_VIDEO )->SetWindowText( "我要收看收听的用户" );
	GetDlgItem( IDC_STATIC_USERLIST)->SetWindowText( "接收我视音频的用户" );

	CRect rc;
	GetDlgItem( IDC_STATIC_USERLIST )->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.DeflateRect( 0, 0 , -20, 0 );
	GetDlgItem( IDC_STATIC_USERLIST )->MoveWindow( rc );

	GetDlgItem( IDC_STATIC_VIDEO )->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.DeflateRect( -100, 0 , 70, 0 );
	GetDlgItem( IDC_STATIC_VIDEO )->MoveWindow( rc );

	GetDlgItem( IDC_LOOKVIDEO_LIST )->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.DeflateRect( -100, 0 , 100, 0 );
	GetDlgItem( IDC_LOOKVIDEO_LIST )->MoveWindow( rc );
	
	GetDlgItem( IDC_SELECT_ALL_CHK2 )->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.DeflateRect( -100, 0 , 100, 0 );
	GetDlgItem( IDC_SELECT_ALL_CHK2 )->MoveWindow( rc );
	
	GetDlgItem( IDC_STATIC )->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.DeflateRect( 0, 0 , 240, 0 );
	GetDlgItem( IDC_STATIC )->MoveWindow( rc );

	GetWindowRect( &rc );
	rc.right = rc.right - 240;
	MoveWindow( rc );
}

void CControlDlg::setVideoNumber( int nVideo )
{
	m_nNumberOfVideo = nVideo;
	int n = 0;
	for( int i = 0; i < m_ListVideo.GetItemCount(); i++ )
	{
		if( m_ListVideo.GetCheck(i) )
		{
			n++;
			if( n > nVideo )
			{
				m_ListVideo.SetCheck( i, FALSE );
				m_ListListen.SetCheck( i, FALSE );
			}
		}
	}
}

// CControlDlg 消息处理程序

BOOL CControlDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();
    ( (CButton*) GetDlgItem( IDC_VIEW_NORMAL ) )->SetCheck( BST_CHECKED );
	m_nNumberOfVideo = VIEW_MODE_NORMAL;

	CRect rc;
	m_ListUser.GetClientRect( &rc );
    ListView_SetExtendedListViewStyle( m_ListUser.GetSafeHwnd(), LVS_EX_CHECKBOXES );
	m_ListUser.InsertColumn(0, "", LVCFMT_LEFT, rc.Width(), 0);
	m_ListVideo.GetClientRect( &rc );
    ListView_SetExtendedListViewStyle( m_ListVideo.GetSafeHwnd(), LVS_EX_CHECKBOXES );
	m_ListVideo.InsertColumn(0, "", LVCFMT_LEFT, rc.Width(), 0);
	m_ListListen.GetClientRect( &rc );
    ListView_SetExtendedListViewStyle( m_ListListen.GetSafeHwnd(), LVS_EX_CHECKBOXES );
	m_ListListen.InsertColumn(0, "", LVCFMT_LEFT, rc.Width(), 0);

	GetClientRect( &rc );
	m_btnApply.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnStopAnswer.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnAnswer.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnOK.MoveWindow( rc.right - 240, rc.bottom - 32, 74, 22 );
	m_btnApply.MoveWindow( rc.right - 80, rc.bottom - 32, 74, 22 );
	m_btnCancel.MoveWindow( rc.right - 160, rc.bottom - 32, 74, 22 );
	m_btnStopAnswer.MoveWindow( rc.right - 80, rc.bottom - 32, 74, 22 );
	m_btnAnswer.MoveWindow( rc.right - 160, rc.bottom - 32, 74, 22 );

	this->m_sDescription = "选择要控制的用户，设置这些用户的窗口布局、要观看的视频和收听的声音。";
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CControlDlg::OnBnClickedSelectAllChk()
{
	BOOL fCheck = ((CButton*) GetDlgItem(IDC_SELECT_ALL_CHK))->GetCheck();
	for( int i = 0; i < m_ListUser.GetItemCount(); i++ )
	{
		m_ListUser.SetCheck( i, fCheck );
	}
}

void CControlDlg::OnBnClickedSelectAllChk2()
{
	BOOL fCheck = ((CButton*) GetDlgItem(IDC_SELECT_ALL_CHK2))->GetCheck();
	for( int i = 0; i < m_ListVideo.GetItemCount(); i++ )
	{
		if( i < m_nNumberOfVideo )
		{
			m_ListVideo.SetCheck( i, fCheck );
			m_ListListen.SetCheck( i, fCheck );
		}
		else
		{
			m_ListVideo.SetCheck( i, FALSE );
			m_ListListen.SetCheck( i, FALSE );
		}
	}
}

void CControlDlg::OnBnClickedSelectAllChk3()
{
	BOOL fCheck = ((CButton*) GetDlgItem(IDC_SELECT_ALL_CHK3))->GetCheck();
	for( int i = 0; i < m_ListListen.GetItemCount(); i++ )
	{
		m_ListListen.SetCheck( i, fCheck );
	}
}

void CControlDlg::OnDestroy()
{
	CDialogEx::OnDestroy();

	for( int i = 0; i < m_ListVideo.GetItemCount(); i++ )
	{
		__int64* pId = (__int64*) m_ListVideo.GetItemData(i);
		delete pId;
	}
}

void CControlDlg::OnNMClickLookvideoList(NMHDR *pNMHDR, LRESULT *pResult)
{
	CPoint pt;
	GetCursorPos( &pt );
	m_ListVideo.ScreenToClient( &pt );
	UINT uFlag;
	int nItem = m_ListVideo.HitTest( pt, &uFlag );
	if( (nItem != -1) && (uFlag & LVHT_ONITEMSTATEICON) )
	{
		if( m_ListVideo.GetCheck( nItem ) )
		{
			m_ListListen.SetCheck( nItem, FALSE );
		}
		else
		{
			int nCount = 0;
			int nPrevItem = 0;
			for( int i = 0; i < m_ListVideo.GetItemCount(); i++ )
			{
				if( m_ListVideo.GetCheck(i) )
				{
					nCount++;
					nPrevItem = i;
				}
			}
			if( nCount >= m_nNumberOfVideo )
			{
				m_ListVideo.SetCheck( nPrevItem, FALSE );
				m_ListListen.SetCheck( nPrevItem, FALSE );
			}
			m_ListListen.SetCheck( nItem );
		}
	}
	*pResult = 0;
}

void CControlDlg::OnNMDblclkLookvideoList(NMHDR *pNMHDR, LRESULT *pResult)
{
	OnNMClickLookvideoList(pNMHDR, pResult);
	*pResult = 0;
}

void CControlDlg::OnNMRclickLookvideoList(NMHDR *pNMHDR, LRESULT *pResult)
{
	OnNMClickLookvideoList(pNMHDR, pResult);
	*pResult = 0;
}

void CControlDlg::OnBnClickedViewVga()
{
	GetDlgItem( IDC_NUMBEROFVIDEO )->SetWindowText("(最多1路)");
    setVideoNumber( VIEW_MODE_VGA );
}

void CControlDlg::OnBnClickedViewTwo()
{
    setVideoNumber( VIEW_MODE_DIALOG );
	GetDlgItem( IDC_NUMBEROFVIDEO )->SetWindowText("(最多2路)");
}

void CControlDlg::OnBnClickedViewCif()
{
    setVideoNumber( VIEW_MODE_CIF );
	GetDlgItem( IDC_NUMBEROFVIDEO )->SetWindowText("(最多4路)");
}

void CControlDlg::OnBnClickedViewQcif()
{
    setVideoNumber( VIEW_MODE_QCIF );
	GetDlgItem( IDC_NUMBEROFVIDEO )->SetWindowText("(最多16路)");
}

void CControlDlg::OnBnClickedViewNormal()
{
	m_nNumberOfVideo = VIEW_MODE_NORMAL;
	GetDlgItem( IDC_NUMBEROFVIDEO )->SetWindowText("");
}

void CControlDlg::OnBnClickedBtnAnswer()
{
	char buf[65];
	__int64 myId = ((CMeetingRoomFrame*)GetParent())->getSelfId();
	for( int i = 0; i < m_ListUser.GetItemCount(); i++ )
	{
		if( m_ListUser.GetCheck( i ) )
		{
			_i64toa( myId, buf, 10 );
			string str = buf;
			str.append( "," );
			__int64 uid = *(__int64*) m_ListUser.GetItemData( i );
			_i64toa( uid, buf, 10 );
			str.append( buf );
			str.append( "," );
			((CMeetingRoomFrame*)GetParent())->watchUser( uid, str, m_nNumberOfVideo );
			((CMeetingRoomFrame*)GetParent())->listenToUser( uid, str );
		}
	}

	_i64toa( myId, buf, 10 );
	string str1 = buf;
	str1.append( "," );
	for( int i = 0; i < m_ListVideo.GetItemCount(); i++ )
	{
		if( m_ListVideo.GetCheck(i) )
		{
			__int64 uid = *(__int64*) m_ListVideo.GetItemData( i );
			_i64toa( uid, buf, 10 );
			str1.append( buf );
			str1.append( "," );
		}
	}
	((CMeetingRoomFrame*)GetParent())->watchUser( myId, str1, m_nNumberOfVideo );
	((CMeetingRoomFrame*)GetParent())->listenToUser( myId, str1 );
}

void CControlDlg::OnBnClickedBtnStopanswer()
{
	char buf[65];
	for( int i = 0; i < m_ListUser.GetItemCount(); i++ )
	{
		__int64 uid = *(__int64*) m_ListUser.GetItemData( i );
		_i64toa( uid, buf, 10 );
		string str = buf;
		str.append( "," );
		((CMeetingRoomFrame*)GetParent())->watchUser( uid, str, m_nNumberOfVideo );
		((CMeetingRoomFrame*)GetParent())->listenToUser( uid, "" );
	}

	__int64 myId = ((CMeetingRoomFrame*)GetParent())->getSelfId();
	_i64toa( myId, buf, 10 );
	string str1 = buf;
	str1.append( "," );
	((CMeetingRoomFrame*)GetParent())->watchUser( myId, str1, m_nNumberOfVideo );
	((CMeetingRoomFrame*)GetParent())->listenToUser( myId, "" );
}

void CControlDlg::OnBnClickedBtnApply()
{
	__int64 uid;
	string strVideo = "";
	string strListen = "";
	for( int i = 0; i < m_ListVideo.GetItemCount(); i++ )
	{
		if( m_ListVideo.GetCheck(i) )
		{
			char buf[65];
			__int64 id = *(__int64*) m_ListVideo.GetItemData( i );
			_i64toa( id, buf, 10 );
			strVideo.append( buf );
			strVideo.append( "," );
		}
	}
	for( int i = 0; i < m_ListListen.GetItemCount(); i++ )
	{
		if( m_ListListen.GetCheck(i) )
		{
			char buf[65];
			__int64 id = *(__int64*) m_ListListen.GetItemData( i );
			_i64toa( id, buf, 10 );
			strListen.append( buf );
			strListen.append( "," );
		}
	}
	BOOL bHasChecked = FALSE;
	for( int i = 0; i < m_ListUser.GetItemCount(); i++ )
	{
		if( m_ListUser.GetCheck( i ) )
		{
			bHasChecked = TRUE;
			uid = *(__int64*) m_ListUser.GetItemData( i );
			((CMeetingRoomFrame*)GetParent())->watchUser( uid, strVideo, m_nNumberOfVideo );
			((CMeetingRoomFrame*)GetParent())->listenToUser( uid, strListen );
		}
	}
	if( !bHasChecked )
	{
		MessageBox( "没有要控制的用户被选中，此操作不会生效！" );
	}
}

HBRUSH CControlDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC_USERLIST
		|| pWnd->GetDlgCtrlID() == IDC_STATIC_VIEWMODE
		|| pWnd->GetDlgCtrlID() == IDC_STATIC_VIDEO
		|| pWnd->GetDlgCtrlID() == IDC_NUMBEROFVIDEO
		|| pWnd->GetDlgCtrlID() == IDC_STATIC_AUDIO
		|| pWnd->GetDlgCtrlID() == IDC_VIEW_NORMAL
		|| pWnd->GetDlgCtrlID() == IDC_VIEW_VGA
		|| pWnd->GetDlgCtrlID() == IDC_VIEW_TWO
		|| pWnd->GetDlgCtrlID() == IDC_VIEW_CIF
		|| pWnd->GetDlgCtrlID() == IDC_VIEW_QCIF
		|| pWnd->GetDlgCtrlID() == IDC_SELECT_ALL_CHK
		|| pWnd->GetDlgCtrlID() == IDC_SELECT_ALL_CHK2
		|| pWnd->GetDlgCtrlID() == IDC_SELECT_ALL_CHK3 )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}

void CControlDlg::OnBnClickedOk()
{
	OnBnClickedBtnApply();
	CDialogEx::OnOK();
}
