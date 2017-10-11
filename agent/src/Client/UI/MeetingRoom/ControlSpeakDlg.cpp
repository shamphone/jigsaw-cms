// UI\MeetingRoom\ControlSpeakDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "ControlSpeakDlg.h"
#include ".\controlspeakdlg.h"
#include "RoomMainFrm.h"

// CControlSpeakDlg 对话框

IMPLEMENT_DYNAMIC(CControlSpeakDlg, CDialog)
CControlSpeakDlg::CControlSpeakDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CControlSpeakDlg::IDD, pParent)
{
}

CControlSpeakDlg::~CControlSpeakDlg()
{
}

void CControlSpeakDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control( pDX, IDC_CONTROLSPEAK_LIST, m_ListCtrl );
}


BEGIN_MESSAGE_MAP(CControlSpeakDlg, CDialog)
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDC_APPOINTSPEAKER_BTN, OnBnClickedAppointspeakerBtn)
	ON_BN_CLICKED(IDC_STOPSPEAKER_BTN, OnBnClickedStopspeakerBtn)
END_MESSAGE_MAP()


// CControlSpeakDlg 消息处理程序


void CControlSpeakDlg::addConferenceUser( __int64 uid, string name )
{
	int nIndex = m_ListCtrl.InsertItem( m_ListCtrl.GetItemCount(), name.c_str(), 0 );
	__int64* pId = new __int64( uid );
	m_ListCtrl.SetItemData( nIndex, (DWORD_PTR)pId );
}

void CControlSpeakDlg::deleteConferenceUser( __int64 uid )
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		__int64* pId = (__int64*) m_ListCtrl.GetItemData(i);
        if( *pId == uid )
		{
			delete pId;
			m_ListCtrl.DeleteItem(i);
			break;
		}
	}
}

void CControlSpeakDlg::setApplySpeak( __int64 uid )
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( uid == *(__int64*) m_ListCtrl.GetItemData(i) )
		{
			setListItemImage( i, 1 );
            break;
		}
	}
}

void CControlSpeakDlg::setSpeaker( __int64 uid, BOOL bFlag )
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( uid == *(__int64*) m_ListCtrl.GetItemData(i) )
		{
			setListItemImage( i, bFlag ? 2 : 0 );
			break;
		}
	}
}

void CControlSpeakDlg::setListItemImage( int nIndex, int nImage )
{
	LVITEM lvi;
	lvi.iItem = nIndex;
	lvi.iSubItem = 0;
	lvi.mask = LVIF_IMAGE;
	lvi.iImage = nImage;
	m_ListCtrl.SetItem( &lvi );
}

BOOL CControlSpeakDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	CRect rc;
	m_ListCtrl.GetClientRect( &rc );
    ListView_SetExtendedListViewStyle( m_ListCtrl.GetSafeHwnd(), LVS_EX_CHECKBOXES );
	m_ListCtrl.InsertColumn( 0, "", LVCFMT_LEFT, rc.Width() );

	m_ImageList.Create( 18, 18, ILC_COLOR16, 3, 1 );
	CBitmap bm;
	bm.LoadBitmap( IDB_CONTROLSPEAK );
	m_ImageList.Add( &bm, RGB( 0, 0, 255 ) );
	m_ListCtrl.SetImageList( &m_ImageList, LVSIL_SMALL );

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CControlSpeakDlg::OnDestroy()
{
	CDialog::OnDestroy();

	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		__int64* pId = (__int64*) m_ListCtrl.GetItemData(i);
		delete pId;
	}
}

void CControlSpeakDlg::OnBnClickedAppointspeakerBtn()
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( m_ListCtrl.GetCheck(i) )
		{
			((CMeetingRoomFrame*)GetParent())->appointSpeaker( *(__int64*) m_ListCtrl.GetItemData(i) );
			setListItemImage( i, 2 );
		}
	}
}

void CControlSpeakDlg::OnBnClickedStopspeakerBtn()
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( m_ListCtrl.GetCheck(i) )
		{
			((CMeetingRoomFrame*)GetParent())->stopSpeaker( *(__int64*) m_ListCtrl.GetItemData(i) );
            setListItemImage( i, 0 );
		}
	}
}
