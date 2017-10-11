// UI\MeetingRoom\VideoBgDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "VideoBgDlg.h"
#include ".\videobgdlg.h"
#include "RoomView.h"
#include "AttenderDlg.h"

// CVideoBgDlg 对话框

IMPLEMENT_DYNAMIC(CVideoBgDlg, CDialog)
CVideoBgDlg::CVideoBgDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CVideoBgDlg::IDD, pParent), CTabItem( this )
{
	m_pRoomView = NULL;
	for( int i = 0; i < 16; i++ )
	{
		m_attenderDlgs[i] = NULL;
	}
	m_nType = CTabItem::VideoBg;
	this->m_ChatWithId = m_nType;
}

CVideoBgDlg::~CVideoBgDlg()
{
}

void CVideoBgDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CVideoBgDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_SIZE()
END_MESSAGE_MAP()


// CVideoBgDlg 消息处理程序

BOOL CVideoBgDlg::create(UINT nIDTemplate, CRoomView* pView, CWnd* pParentWnd)
{
    m_pRoomView = pView;
	return CDialog::Create(nIDTemplate, pParentWnd);
}

int CVideoBgDlg::getVideoPosition( CRect* pRect, CAttenderDlg* pAttender )
{
	CRect rc;
	GetClientRect(&rc);
	int nMode = m_pRoomView->getViewMode();
	if( nMode == VIEW_MODE_VGA )
	{
		int nIndex = getAvailableIndex( 1 );
		if( nIndex == -1 )
		{
			m_attenderDlgs[0]->popUp();
		}
		m_attenderDlgs[0] = pAttender;
		pRect->top = rc.top;
		pRect->bottom = rc.bottom;
		pRect->left = rc.left;
		pRect->right = rc.right;
		return 0;
	}
	else if( nMode == VIEW_MODE_CIF)
	{
		int nIndex = getAvailableIndex( 4 );
		if( nIndex == -1 )
		{
			m_attenderDlgs[0]->popUp();
			nIndex = 0;
		}
		m_attenderDlgs[nIndex] = pAttender;
		pRect->top = (nIndex / 2) * rc.Height() / 2;
		pRect->bottom = pRect->top + rc.Height() / 2;
		pRect->left = (nIndex % 2) * rc.Width() / 2;
		pRect->right = pRect->left + rc.Width() / 2;
		return nIndex;
	}
	else if( nMode == VIEW_MODE_QCIF)
	{
		int nIndex = getAvailableIndex( 16 );
		if( nIndex == -1 )
		{
			m_attenderDlgs[0]->popUp();
			nIndex = 0;
		}
		m_attenderDlgs[nIndex] = pAttender;
		pRect->top = (nIndex / 4) * rc.Height() / 4;
		pRect->bottom = pRect->top + rc.Height() / 4;
		pRect->left = (nIndex % 4) * rc.Width() / 4;
		pRect->right = pRect->left + rc.Width() / 4;
		return nIndex;
	}
	else if( nMode == VIEW_MODE_DIALOG )
	{
		int nIndex = getAvailableIndex( 2 );
		if( nIndex == -1 )
		{
			m_attenderDlgs[0]->popUp();
			nIndex = 0;
		}
		m_attenderDlgs[nIndex] = pAttender;
		pRect->top = rc.top;
		pRect->bottom = rc.bottom;
		pRect->left = (nIndex % 2) * rc.Width() / 2;
		pRect->right = pRect->left + rc.Width() / 2;
		return nIndex;
	}
	ASSERT(FALSE);
	return -1;
}

void CVideoBgDlg::resetIndex( int nIndex )
{
	ASSERT( nIndex >= 0 && nIndex < 16 );
	m_attenderDlgs[nIndex] = NULL;
}

void CVideoBgDlg::changeViewMode( int nMode )
{
	if( nMode == VIEW_MODE_NORMAL )
	{
		changeToNormal();
	}
	else
	{
		changeView( nMode );
	}
	InvalidateRect( NULL );
}

void CVideoBgDlg::changeToNormal()
{
	for( int i = 0; i < 16; i++ )
	{
		if( m_attenderDlgs[i] != NULL && m_attenderDlgs[i]->m_bIsPopup )
		{
			m_attenderDlgs[i]->popUp();
			m_attenderDlgs[i] = NULL;
		}
	}
}

void CVideoBgDlg::changeView( int nVideoNumber )
{
	CAttenderDlg* dlgs[16];
	int nRemain = nVideoNumber;
	for( int i = 0; i < nVideoNumber; i++ )
	{
		dlgs[i] = NULL;
		if( m_attenderDlgs[i] != NULL )
		{
			dlgs[ nVideoNumber - nRemain ] = m_attenderDlgs[i];
			m_attenderDlgs[i] = NULL;
			nRemain --;
		}
	}
	for( int i = nVideoNumber; i < 16; i++ )
	{
		if( m_attenderDlgs[i] != NULL )
		{
			if( nRemain > 0 )
			{
				dlgs[ nVideoNumber - nRemain ] = m_attenderDlgs[i];
				nRemain --;
			}
			else
			{
				m_attenderDlgs[i]->popUp();
			}
			m_attenderDlgs[i] = NULL;
		}
	}
	if( nRemain > 0 )
        m_pRoomView->getAttenderListDlg()->getUnpopupAttenderDlg( &dlgs[nVideoNumber-nRemain], nRemain );
	for( int i = 0; i < nVideoNumber; i++ )
	{
		if( dlgs[i] == NULL )
			break;
		else
		{
			if( dlgs[i]->m_bIsPopup )
			{
				CRect rc;
				dlgs[i]->m_nIndex = getVideoPosition( &rc, dlgs[i] );
				dlgs[i]->MoveWindow( rc );
				dlgs[i]->InvalidateRect(NULL);
			}
			else
				dlgs[i]->popUp();
		}
	}
}

int CVideoBgDlg::getAvailableIndex( int nEndBefore )
{
	for( int i = 0; i < nEndBefore; i++ )
	{
		if( m_attenderDlgs[i] == NULL )
			return i;
	}
	return -1;
}

void CVideoBgDlg::OnOK()
{
}

void CVideoBgDlg::OnCancel()
{
}

void CVideoBgDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	// 不为绘图消息调用 CDialog::OnPaint()
	CDC memDC;
	memDC.CreateCompatibleDC( &dc );

	CRect rc;
	GetClientRect( &rc );
	CBitmap bmp;
	bmp.LoadBitmap( IDB_VIDEOBG );

	int nMode = m_pRoomView->getViewMode();
	if( nMode == VIEW_MODE_VGA )
	{
		DrawRangeImage( &bmp, &dc, rc );
	}
	else if( nMode == VIEW_MODE_CIF )
	{
		DrawRangeImage( &bmp, &dc, CRect( 0, 0, rc.Width() / 2, rc.Height() / 2 ) );// 354, 310) );
	}
	else if( nMode == VIEW_MODE_QCIF )
	{
		DrawRangeImage( &bmp, &dc, CRect(0, 0, rc.Width() / 4, rc.Height() / 4 ) );//178, 166) );
	}
	else if( nMode == VIEW_MODE_DIALOG )
	{
		DrawRangeImage( &bmp, &dc, CRect(0, 0, rc.Width() / 2, rc.Height() ) );//178, 166) );
	}
	dc.BitBlt( 0, 0, rc.Width(), rc.Height(), &memDC, 0, 0, SRCCOPY );

	memDC.DeleteDC();
}

void CVideoBgDlg::DrawRangeImage(CBitmap * pBitmap, CDC* pDC, CRect rc)
{
	CRect rcClient;
	GetClientRect( &rcClient );

	CDC MemDC;
	BITMAP bm;
	pBitmap->GetBitmap(&bm);

	int li_Width = bm.bmWidth;
	int li_Height = bm.bmHeight;

	MemDC.CreateCompatibleDC(pDC);
	CBitmap* pOldBitmap = MemDC.SelectObject(pBitmap);

	int x = 0;
	int y = 0;
	while( y < rcClient.Height() ) 
 	{
 		while( x < rcClient.Width() ) 
 		{
			pDC->StretchBlt( x, y, rc.Width(), rc.Height(), &MemDC, 0, 0, li_Width, li_Height, SRCCOPY);
 			x += rc.Width();
 		}
		x = 0;
 		y += rc.Height();
 	}

	MemDC.SelectObject(pOldBitmap);
	MemDC.DeleteDC();
}
void CVideoBgDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);

	int nMode = m_pRoomView->getViewMode();
	if( nMode == VIEW_MODE_VGA )
	{
		if( m_attenderDlgs[0] != NULL )
		{
			m_attenderDlgs[0]->MoveWindow( 0, 0, cx, cy );
		}
	}
	else if( nMode == VIEW_MODE_CIF )
	{
		for( int i = 0; i < 4; i++ )
		{
			if( m_attenderDlgs[i] != NULL )
			{
				m_attenderDlgs[i]->MoveWindow( (i % 2) * cx / 2, i / 2 * cy / 2, cx / 2, cy / 2 );
			}
		}
	}
	else if( nMode == VIEW_MODE_QCIF )
	{
		for( int i = 0; i < 16; i++ )
		{
			if( m_attenderDlgs[i] != NULL )
			{
				m_attenderDlgs[i]->MoveWindow( (i % 4) * cx / 4, i / 4 * cy / 4, cx / 4, cy / 4 );
			}
		}
	}
	else if( nMode == VIEW_MODE_DIALOG )
	{
		for( int i = 0; i < 2; i++ )
		{
			if( m_attenderDlgs[i] != NULL )
			{
				m_attenderDlgs[i]->MoveWindow( i * cx / 2, 0, cx / 2, cy );
			}
		}
	}
	InvalidateRect( NULL );
}
