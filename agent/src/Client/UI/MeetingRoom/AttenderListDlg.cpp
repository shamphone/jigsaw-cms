// UI\meetingroom\AttenderListDlg.cpp : 实现文件


#include "stdafx.h"
#include "Flvcc.h"
#include "AttenderListDlg.h"
#include ".\attenderlistdlg.h"
#include "AttenderDlg.h"
#include "RoomView.h"
#include "sortattendersdlg.h"

// CAttenderListDlg 对话框

IMPLEMENT_DYNAMIC(CAttenderListDlg, CDialog)
CAttenderListDlg::CAttenderListDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CAttenderListDlg::IDD, pParent)
{
	m_pSelfDlg = NULL;
	m_pSortDlg = NULL;
}

CAttenderListDlg::~CAttenderListDlg()
{
	int n = m_AttenderDlgs.GetCount();
	for(int i = 0; i < n; i++)
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.RemoveHead();
		pDlg->DestroyWindow();
		delete pDlg;
	}
}

void CAttenderListDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CAttenderListDlg, CDialog)
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
	ON_BN_CLICKED(IDCANCEL, OnBnClickedCancel)
	ON_WM_SIZE()
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CAttenderListDlg 消息处理程序
bool CAttenderListDlg::hasYuntai()
{
	POSITION pos;
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( pDlg->m_bIfHasYuntai )
		{
			return true;
		}
	}
	return false;
}

void CAttenderListDlg::setYuntaiHolder( __int64 uid )
{
	CAttenderDlg* pDlg = getAttenderDlgById( uid );
	if( pDlg != NULL )
        pDlg->m_bIfHasYuntai = true;
}

void CAttenderListDlg::addConferenceUser(ConferenceUser* pConfUser, bool bSelf)
{

	if( bSelf )
	{
		m_pSelfDlg->setConferenceUser( pConfUser );
		m_pSelfDlg->SetWindowText( "我的视频" );
		m_pSelfDlg->ShowWindow(TRUE);
		m_AttenderDlgs.AddHead(m_pSelfDlg);
	}
	else
	{
		CRect rc;
		if( m_AttenderDlgs.GetCount() == 0 )
		{
			rc = CRect( 0, VIDEO_WINDOW_HEIGHT, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT + VIDEO_WINDOW_TITLE_HEIGHT );
		}
		else
		{
			rc = CRect( 0, getBottom(), VIDEO_WINDOW_WIDTH, getBottom() + VIDEO_WINDOW_TITLE_HEIGHT );
		}
		CAttenderDlg* pNewDlg = new CAttenderDlg( this );
		pNewDlg->Create(IDD_ATTENDER_DLG, this);
		pNewDlg->setConferenceUser(pConfUser);
		pNewDlg->SetWindowText( pNewDlg->getName().c_str() );
		pNewDlg->MoveWindow(rc); 
		pNewDlg->ShowWindow(TRUE);
		m_AttenderDlgs.AddTail(pNewDlg);
	}
}

void CAttenderListDlg::deleteConferenceUser(__int64 uid)
{
	POSITION pos1, pos2;
	string name;
	for( pos1 = m_AttenderDlgs.GetHeadPosition(); (pos2 = pos1) != NULL; )
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetNext(pos1);
		if (pDlg->getUserId() == uid)
		{
			name = pDlg->getName();
			if( !pDlg->m_bIsPopup )
			{
				CRect rc;
				pDlg->GetWindowRect(&rc);
				ScreenToClient(rc);
				if( rc.top < 0 )
				{
					POSITION pos = pos2;
					m_AttenderDlgs.GetPrev(pos);
					reLayout(false, pos, NULL, rc.Height());
				}
				else
					reLayout(true, pos1, NULL, rc.Height() * (-1));
			}
			pDlg->resetIndex();
			pDlg->DestroyWindow();
			bool bSelf = pDlg->m_bIsSelf;
			delete pDlg;
			pDlg = NULL;
			if( bSelf )
			{
				m_pSelfDlg = new CAttenderDlg(this);
				m_pSelfDlg->m_bShowVideoWindow = true;
				m_pSelfDlg->m_bIsSelf = true;
				m_pSelfDlg->Create( IDD_ATTENDER_DLG, this );
				m_pSelfDlg->MoveWindow( 0, 0, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );
			}
			m_AttenderDlgs.RemoveAt(pos2);
			break;
		}
	}
	if( m_pSortDlg != NULL )
	{
		m_pSortDlg->deleteUser(name, uid);
	}
}

int CAttenderListDlg::getConfUserCount() 
{
	return this->m_AttenderDlgs.GetCount();
}

void CAttenderListDlg::showVideoWindow(__int64 uid, bool bShow)
{
	CAttenderDlg* pDlg = getAttenderDlgById(uid);
	if( pDlg == NULL )
		return;
	if( pDlg->m_bShowVideoWindow == bShow )
		return;
	pDlg->m_bShowVideoWindow = bShow;
	if( pDlg->m_bIsPopup )
	{
		if( !bShow )
		{
			pDlg->popUp();
		}
		else
			return;
	}

	POSITION pos = m_AttenderDlgs.Find(pDlg);

	CRect rc;
	pDlg->GetWindowRect(&rc);
	ScreenToClient(rc);
	bool bFlag = rc.top < 0;
	if(!bShow)
	{
		if( bFlag )
		{
			rc.top += VIDEO_HEIGHT;
			pDlg->MoveWindow(rc);
			m_AttenderDlgs.GetPrev(pos);
			reLayout(false, pos, NULL, VIDEO_HEIGHT);
		}
		else
		{
			rc.bottom -= VIDEO_HEIGHT;
			pDlg->MoveWindow(rc);
        	m_AttenderDlgs.GetNext(pos);
			reLayout(true, pos, NULL, VIDEO_HEIGHT * (-1));
		}
	}
	if(bShow)
	{
		if( bFlag )
		{
			rc.top -= VIDEO_HEIGHT;
			m_AttenderDlgs.GetPrev(pos);
			reLayout(false, pos, NULL, VIDEO_HEIGHT * (-1));
		}
		else
		{
			rc.bottom += VIDEO_HEIGHT;
			reLayout(false, m_AttenderDlgs.GetTailPosition(), pos, VIDEO_HEIGHT);
		}
		pDlg->MoveWindow(rc);
	}
}

void CAttenderListDlg::reLayout(bool bFromTopToBottom, POSITION posStartAt, POSITION posEndBefore, int nOffset)
{
	CRect rc;
	CAttenderDlg* pDlg = NULL;

	for( POSITION pos = posStartAt; pos != posEndBefore; )
	{
		if(bFromTopToBottom)
			pDlg = (CAttenderDlg*) m_AttenderDlgs.GetNext(pos);
		else
			pDlg = (CAttenderDlg*) m_AttenderDlgs.GetPrev(pos);
		if( pDlg->m_bIsPopup )
			continue;
		pDlg->GetWindowRect(&rc);
		ScreenToClient(rc);
		rc.top += nOffset;
		rc.bottom += nOffset;
		pDlg->MoveWindow(rc);
	}
}

HWND CAttenderListDlg::getVideoWindowById(__int64 uid)
{
	CAttenderDlg* pDlg = getAttenderDlgById(uid);
	if( pDlg == NULL )
		return NULL;
	return pDlg->getVideoHwnd();
}

HWND CAttenderListDlg::getLocalVidwoWindow()
{
	return m_pSelfDlg->getVideoHwnd();
}

CAttenderDlg* CAttenderListDlg::getAttenderDlgById(__int64 uid)
{
	POSITION pos;
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if (pDlg->getUserId() == uid)
		{
			return pDlg;
		}
	}
	ASSERT(FALSE);
	return NULL;
}

BOOL CAttenderListDlg::canScrollDown()
{
	if( 0 > getTop() )
		return TRUE;
	return FALSE;
}

BOOL CAttenderListDlg::canScrollUp()
{
	CRect rc;
	GetClientRect(rc);
	if( rc.bottom < getBottom() )
		return TRUE;
	return FALSE;
}

void CAttenderListDlg::scrollDown()
{
	if( !canScrollDown() )
		return;

	CRect rc;
	POSITION pos = getFirstViewPosition();
	if( pos == NULL )
	{
		POSITION pos1;
		for( pos1 = m_AttenderDlgs.GetTailPosition(); pos1 != NULL; m_AttenderDlgs.GetPrev(pos1) )
		{
			CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos1);
			if( pDlg->m_bIsPopup )
				continue;
			pDlg->GetWindowRect(&rc);
			ScreenToClient(rc);
			if( rc.bottom == 0 )
				break;
		}
	}
	else
	{
		while( pos != NULL )
		{
			m_AttenderDlgs.GetPrev(pos);
			CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
			if( pDlg->m_bIsPopup )
				continue;
			pDlg->GetWindowRect(&rc);
			break;
		}
	}
	reLayout(false, m_AttenderDlgs.GetTailPosition(), NULL, rc.Height());
}

void CAttenderListDlg::scrollUp()
{
	if( !canScrollUp() )
		return;

	CRect rc;
	POSITION pos = getFirstViewPosition();
	CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
	pDlg->GetWindowRect(&rc);
	reLayout(true, m_AttenderDlgs.GetHeadPosition(), NULL, rc.Height() * (-1));
}

__int64 CAttenderListDlg::getAnotherUserId()
{
	ASSERT( getConfUserCount() == 2 );
	CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetTail();
	return pDlg->getUserId();
}

void CAttenderListDlg::swapConfUser(__int64 prevId, __int64 nextId)
{
	CAttenderDlg* pDlg1 = getAttenderDlgById( prevId );
	CAttenderDlg* pDlg2 = getAttenderDlgById( nextId );
	if( pDlg1 == NULL || pDlg2 == NULL )
		return;
	CRect rc1, rc2;
	pDlg1->GetWindowRect(&rc1);
	pDlg2->GetWindowRect(&rc2);
	ScreenToClient(rc1);
	ScreenToClient(rc2);
	int height1 = rc1.Height();
	int height2 = rc2.Height();
	POSITION pos1 = m_AttenderDlgs.Find(pDlg1);
	POSITION pos2 = m_AttenderDlgs.Find(pDlg2);
	m_AttenderDlgs.RemoveAt(pos1);
	pos1 = m_AttenderDlgs.InsertAfter(pos2, pDlg1);
	if( pos2 == this->getFirstViewPosition() )
	{
		rc2.bottom = rc2.top + height1;
		rc1.top = rc1.bottom - height2;
		if( height1 > height2 )
		{
			m_AttenderDlgs.GetPrev(pos2);
			reLayout(false, m_AttenderDlgs.GetTailPosition(), pos1, height1 - height2);
			reLayout(false, pos2, NULL, height1 - height2);
		}
		else if( height1 < height2 )
		{
			m_AttenderDlgs.GetNext(pos1);
			reLayout(true, pos1, NULL, height1 - height2);
			reLayout(false, pos2, NULL, height1 - height2);
		}
	}
	else
	{
		rc2.top = rc2.bottom - height1;
		rc1.bottom = rc1.top + height2;
	}
	pDlg1->ShowWindow(FALSE);
	pDlg2->ShowWindow(FALSE);
	pDlg1->MoveWindow(rc2);
	pDlg2->MoveWindow(rc1);
	pDlg1->ShowWindow(TRUE);
	pDlg2->ShowWindow(TRUE);
}

void CAttenderListDlg::getAttendersIdAndNameForSort( vector<string>* names, vector<__int64>* ids )
{
	POSITION pos = m_AttenderDlgs.GetHeadPosition();
	m_AttenderDlgs.GetNext(pos);
	for( ; pos != NULL; )
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetNext(pos);
		if( !pDlg->m_bIsPopup )
		{
			ids->push_back( pDlg->getUserId() );
			names->push_back( pDlg->getName() );
		}
	}
}

POSITION CAttenderListDlg::getFirstViewPosition()
{
	POSITION pos;
	CRect rc, rc1;
	GetWindowRect(&rc);
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( pDlg->m_bIsPopup )
			continue;
		pDlg->GetWindowRect(&rc1);
		if( rc1.top <= rc.top && rc1.bottom > rc.top )
			return pos;
	}
	return NULL;
}

int CAttenderListDlg::getBottom()
{
	POSITION pos;
	CRect rc;
	for( pos = m_AttenderDlgs.GetTailPosition(); pos != NULL; m_AttenderDlgs.GetPrev(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( !pDlg->m_bIsPopup )
		{
            pDlg->GetWindowRect(&rc);
			ScreenToClient( rc );
			return rc.bottom;
		}
	}
	return 0;
}

int CAttenderListDlg::getTop()
{	
	POSITION pos;
	CRect rc;
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( !pDlg->m_bIsPopup )
		{
            pDlg->GetWindowRect(&rc);
			ScreenToClient( rc );
			return rc.top;
		}
	}
	return 0;
}

void CAttenderListDlg::setIsListenFlag( bool bIsListen, __int64 uid )
{
	CAttenderDlg* pDlg = getAttenderDlgById( uid );
	if( pDlg != NULL )
		pDlg->setIsListenFlag( bIsListen );
}

void CAttenderListDlg::sortAttenders()
{
	m_pSortDlg = new CSortAttendersDlg( this->GetParent() );
	m_pSortDlg->m_pAttenderListDlg = this;
	m_pSortDlg->DoModal();
	delete m_pSortDlg;
	m_pSortDlg = NULL;
}

void CAttenderListDlg::sortAttenders( vector<__int64>& ids )
{
	CRect rc;
	CAttenderDlg* pDlg = NULL;
	for( UINT i = 0; i < ids.size(); i++ )
	{
		int count = m_AttenderDlgs.GetCount();
		for( int j = i; j < count; j++)
		{
			POSITION pos = m_AttenderDlgs.FindIndex(j);
			pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt( pos );
			if( pDlg->m_bIsPopup )
				continue;
			pDlg->ShowWindow(FALSE);
			if( (pDlg->getUserId() == ids.at(i)) && (j != i + 1) )
			{
				POSITION pos1 = m_AttenderDlgs.FindIndex( i + 1 );
				CAttenderDlg* pDlg1 = (CAttenderDlg*) m_AttenderDlgs.GetAt( pos1 );
				m_AttenderDlgs.SetAt( pos, pDlg1 );
				m_AttenderDlgs.SetAt( pos1, pDlg );
				break;
			}
		}
	}
	pDlg = (CAttenderDlg*) m_AttenderDlgs.GetHead();
	int top = 0;
	if( !pDlg->m_bIsPopup )
	{
		pDlg->GetWindowRect(&rc);
		pDlg->MoveWindow( 0, 0, rc.Width(), rc.Height() );
		pDlg->ShowWindow( TRUE );
		top = rc.Height();
	}
	int count = m_AttenderDlgs.GetCount();
	for( int j = 1; j < count; j++)
	{
		POSITION pos = m_AttenderDlgs.FindIndex(j);
		pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt( pos );
		if( pDlg->m_bIsPopup )
			continue;
		pDlg->GetWindowRect( &rc );
		pDlg->MoveWindow( 0, top, rc.Width(), rc.Height() );
		pDlg->ShowWindow( TRUE );
		top = top + rc.Height();
		TRACE1("%s\n", pDlg->getName().c_str() );
	}
}

void CAttenderListDlg::popupAttenderDlg( __int64 uid )
{
	CAttenderDlg* pDlg = getAttenderDlgById( uid );
	if( pDlg != NULL )
		pDlg->OnViewPopup();
}

void CAttenderListDlg::popUp( CAttenderDlg* pDlg, bool bPopup )
{
	if( bPopup )
	{
		CRect rc;
		pDlg->GetClientRect( &rc );
		POSITION pos = m_AttenderDlgs.Find( pDlg );
		POSITION top = getFirstViewPosition();
		if( top == NULL )
		{
			reLayout( true, m_AttenderDlgs.GetHeadPosition(), pos, rc.Height() );
		}
		else
		{
			m_AttenderDlgs.GetPrev( top );
			if( m_AttenderDlgs.Find( pDlg, top ) != NULL )
			{
				m_AttenderDlgs.GetNext( pos );
				reLayout( true, pos, NULL, rc.Height() * (-1) );
			}
			else
			{
				reLayout( true, m_AttenderDlgs.GetHeadPosition(), pos, rc.Height() );
			}
		}
	}
	else
	{
		POSITION top = getFirstViewPosition();
		if( top == NULL )
		{
			POSITION pos = m_AttenderDlgs.Find( pDlg );
			POSITION pos1 = pos;
			m_AttenderDlgs.GetNext( pos1 );
			for( ; pos1 != NULL; m_AttenderDlgs.GetNext(pos1) )
			{
				CAttenderDlg* pAttender = (CAttenderDlg*) m_AttenderDlgs.GetAt( pos1 );
				if( pAttender->m_bIsPopup )
					continue;
				CRect rc;
				pAttender->GetWindowRect( &rc );
				ScreenToClient( &rc );
				if( rc.bottom == 0 )
				{
					reLayout( true, m_AttenderDlgs.GetHeadPosition(), pos, (-1) * VIDEO_WINDOW_HEIGHT );
					for( ; ; )
					{
						CAttenderDlg* pAttender = (CAttenderDlg*) m_AttenderDlgs.GetNext( pos );
						if( pAttender->m_bIsPopup )
							continue;
						CRect rc;
						pAttender->GetWindowRect( &rc );
						ScreenToClient( &rc );
						pDlg->MoveWindow( 0, rc.top - VIDEO_WINDOW_HEIGHT, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );
						return;
					}
				}
			}
			pDlg->MoveWindow( 0, 0, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );
		}
		else
		{
			POSITION pos = m_AttenderDlgs.Find( pDlg, top );
			if( pos != NULL )
			{
                reLayout( false, m_AttenderDlgs.GetTailPosition(), pos, VIDEO_WINDOW_HEIGHT );
				for( ; ; )
				{
					CAttenderDlg* pAttender = (CAttenderDlg*) m_AttenderDlgs.GetPrev( pos );
					if( pAttender->m_bIsPopup )
						continue;
					CRect rc;
					pAttender->GetWindowRect( &rc );
					ScreenToClient( &rc );
					pDlg->MoveWindow( 0, rc.bottom, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );
					break;
				}
			}
			else
			{
				pDlg->MoveWindow( 0, 0, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );
				pos = m_AttenderDlgs.Find( pDlg );
				POSITION pos1 = pos;
				POSITION pos2 = pos;
				m_AttenderDlgs.GetNext( pos1 );
				m_AttenderDlgs.GetPrev( pos2 );
				CAttenderDlg* pPrev = pDlg;
				CAttenderDlg* pNext = pDlg;
				for( ; pos1 != NULL; )
				{
					CAttenderDlg* pAttender = (CAttenderDlg*) m_AttenderDlgs.GetNext( pos1 );
					if( pAttender->m_bIsPopup )
						continue;
					CRect rcPrev, rc;
					pPrev->GetWindowRect( &rcPrev );
					pAttender->GetWindowRect( &rc );
					ScreenToClient( &rcPrev );
					ScreenToClient( &rc );
					pAttender->MoveWindow( rc.left, rcPrev.bottom, rc.Width(), rc.Height() );
					pPrev = pAttender;
				}
				for( ; pos2 != NULL; )
				{
					CAttenderDlg* pAttender = (CAttenderDlg*) m_AttenderDlgs.GetPrev( pos2 );
					if( pAttender->m_bIsPopup )
						continue;
					CRect rcNext, rc;
					pNext->GetWindowRect( &rcNext );
					pAttender->GetWindowRect( &rc );
					ScreenToClient( &rcNext );
					ScreenToClient( &rc );
					pAttender->MoveWindow( rc.left, rcNext.top - rc.Height(), rc.Width(), rc.Height() );
					pNext = pAttender;
				}
			}
		}
	}
}

int CAttenderListDlg::getUnpopupAttenderDlg( CAttenderDlg** ppDlg, int nCount )
{
	POSITION pos;
	int n = 0; 
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( pDlg->m_bIsPopup || !pDlg->m_bShowVideoWindow )
			continue;
		if( nCount > 0 )
		{
			*ppDlg = pDlg;
			ppDlg++;
			nCount --;
			n++;
		}
		else
			break;
	}
	return n;
}

void CAttenderListDlg:: popbackAllVideoDlg()
{
	POSITION pos;
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( pDlg->m_bIsPopup )
		{
			pDlg->popUp();
		}
	}
}

BOOL CAttenderListDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_pSelfDlg = new CAttenderDlg(this);
	m_pSelfDlg->m_bShowVideoWindow = true;
	m_pSelfDlg->m_bIsSelf = true;
	m_pSelfDlg->Create( IDD_ATTENDER_DLG, this );
	m_pSelfDlg->MoveWindow( 0, 0, VIDEO_WINDOW_WIDTH, VIDEO_WINDOW_HEIGHT );

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CAttenderListDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);

	POSITION pos;
	CRect rc;
	for( pos = m_AttenderDlgs.GetHeadPosition(); pos != NULL; m_AttenderDlgs.GetNext(pos))
	{
		CAttenderDlg* pDlg = (CAttenderDlg*) m_AttenderDlgs.GetAt(pos);
		if( pDlg->m_bIsPopup )
			continue;
		pDlg->GetWindowRect(&rc);
		ScreenToClient(rc);
		rc.right = cx;
		pDlg->MoveWindow(rc);
	}
}

void CAttenderListDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect(&rc);
	dc.FillSolidRect( rc, RGB(255, 255, 255) );
	// 不为绘图消息调用 CDialog::OnPaint()
}
