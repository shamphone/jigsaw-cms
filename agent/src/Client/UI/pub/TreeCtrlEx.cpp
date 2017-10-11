// TreeCtrlEx.cpp : 实现文件
//

#include "stdafx.h"
#include "TreeCtrlEx.h"
#include ".\treectrlex.h"


// CTreeCtrlEx

IMPLEMENT_DYNAMIC(CTreeCtrlEx, CTreeCtrl)
CTreeCtrlEx::CTreeCtrlEx()
{
	m_bIsImageTiled = false;
}

CTreeCtrlEx::~CTreeCtrlEx()
{
}


BEGIN_MESSAGE_MAP(CTreeCtrlEx, CTreeCtrl)
	ON_WM_ERASEBKGND()
	ON_WM_PAINT()
	ON_WM_HSCROLL()
	ON_WM_VSCROLL()
	ON_NOTIFY_REFLECT(TVN_ITEMEXPANDING, OnTvnItemexpanding)
	ON_WM_SIZE()
	ON_NOTIFY_REFLECT(NM_CLICK, OnNMClick)
	ON_NOTIFY_REFLECT(TVN_SELCHANGING, OnTvnSelchanging)
	ON_WM_MOUSEWHEEL()
	ON_WM_LBUTTONDOWN()
	ON_WM_RBUTTONDOWN()
END_MESSAGE_MAP()



// CTreeCtrlEx 消息处理程序


BOOL CTreeCtrlEx::OnEraseBkgnd(CDC* pDC)
{
	if( m_bitmap.m_hObject != NULL )	return TRUE;

	return CTreeCtrl::OnEraseBkgnd(pDC);
}

void CTreeCtrlEx::OnPaint()
{
	EnableScrollBar(SB_HORZ, ESB_DISABLE_BOTH);
	ShowScrollBar(SB_HORZ, FALSE);

	CPaintDC dc(this);

	CRect rcClip, rcClient;
	dc.GetClipBox( &rcClip );
	GetClientRect(&rcClient);

	// Create a compatible memory DC 
	CDC memDC;
	memDC.CreateCompatibleDC( &dc );
	
	// Select a compatible bitmap into the memory DC
	CBitmap bitmap, bmpImage;
	bitmap.CreateCompatibleBitmap( &dc, rcClient.Width(), rcClient.Height() );
	memDC.SelectObject( &bitmap );

	
	// First let the control do its default drawing.
	CWnd::DefWindowProc( WM_PAINT, (WPARAM)memDC.m_hDC, 0 );

	// Draw bitmap in the background if one has been set
	if( m_bitmap.m_hObject != NULL )
	{
		// Now create a mask
		CDC maskDC;
		maskDC.CreateCompatibleDC(&dc);
		CBitmap maskBitmap;

		// Create monochrome bitmap for the mask
		maskBitmap.CreateBitmap( rcClient.Width(), rcClient.Height(), 1, 1, NULL );
		maskDC.SelectObject( &maskBitmap );
		memDC.SetBkColor(RGB(255, 255, 255) /*::GetSysColor( COLOR_WINDOW )*/ );

		// Create the mask from the memory DC
		//maskDC.BitBlt( 0, 0, rcClient.Width(), rcClient.Height(), &memDC, rcClient.left, rcClient.top, SRCCOPY );
		
		CDC tempDC;
		tempDC.CreateCompatibleDC(&dc);
		tempDC.SelectObject( &m_bitmap );

		CDC imageDC;
		CBitmap bmpImage;
		imageDC.CreateCompatibleDC( &dc );
		bmpImage.CreateCompatibleBitmap( &dc, rcClient.Width(), 
						rcClient.Height() );
		imageDC.SelectObject( &bmpImage );

		if( dc.GetDeviceCaps(RASTERCAPS) & RC_PALETTE && m_pal.m_hObject != NULL )
		{
			dc.SelectPalette( &m_pal, FALSE );
			dc.RealizePalette();

			imageDC.SelectPalette( &m_pal, FALSE );
		}

		// Get x and y offset
		CRect rcRoot;
		BOOL b = GetItemRect( GetRootItem(), &rcRoot, TRUE );
		if (!b)
			rcRoot = CRect(0,0,0,0);
		else
			rcRoot.left = -GetScrollPos( SB_HORZ );

		if (m_bIsImageTiled)
		{
			// Draw bitmap in tiled manner to imageDC
			for( int i = rcRoot.left; i < rcClient.right; i += m_cxBitmap )
			for( int j = rcRoot.top; j < rcClient.bottom; j += m_cyBitmap )
				imageDC.BitBlt( i, j, m_cxBitmap, m_cyBitmap, &tempDC,
								0, 0, SRCCOPY );
		}
		else
		{
			int x=0,y=0 ;
			(m_cxBitmap > rcClient.right) ? x=0:x=(rcClient.right - m_cxBitmap);
			(m_cyBitmap > rcClient.bottom)? y=0:y=(rcClient.bottom - m_cyBitmap);
			imageDC.FillSolidRect( rcClient, RGB(255, 255, 255) );
//			imageDC.BitBlt( x, y, m_cxBitmap, m_cyBitmap, &tempDC, 
//							0, 0, SRCCOPY );
//			imageDC.FillSolidRect(0, 0, rcClient.Width(), y, RGB(142, 196, 255));
//			imageDC.FillSolidRect(0, y, x, rcClient.Height() - y, RGB(142, 196, 255));
		}

		// Set the background in memDC to black. Using SRCPAINT with black and any other
		// color results in the other color, thus making black the transparent color
		memDC.SetBkColor( RGB(0, 0, 0)/*memDC_bgColor_bitmap*/);        
		memDC.SetTextColor(RGB(255,255,255));
		memDC.BitBlt(rcClip.left, rcClip.top, rcClip.Width(), rcClip.Height(), &maskDC, 
				rcClip.left, rcClip.top, SRCAND);

		// Set the foreground to black. See comment above.
		imageDC.SetBkColor(RGB(255, 255, 255));
		imageDC.SetTextColor(RGB(0,0,0));
		imageDC.BitBlt(rcClip.left, rcClip.top, rcClip.Width(), rcClip.Height(), &maskDC, 
				rcClip.left, rcClip.top, SRCAND);

		// Combine the foreground with the background
		imageDC.BitBlt(rcClip.left, rcClip.top, rcClip.Width(), rcClip.Height(), &memDC, 
				rcClip.left, rcClip.top, SRCPAINT);

		HTREEITEM hVisable = this->GetFirstVisibleItem();
		while( hVisable != NULL )
		{
			if( hVisable == GetSelectedItem() )
			{
				DrawSelectedItem(&imageDC);
				break;
			}
			else
			{
				hVisable = this->GetNextVisibleItem(hVisable);
			}
		}
		// Draw the final image to the screen		
		dc.BitBlt( rcClip.left, rcClip.top, rcClip.Width(), rcClip.Height(), 
					&imageDC, rcClip.left, rcClip.top, SRCCOPY );
	}
	else
	{
		dc.BitBlt( rcClip.left, rcClip.top, rcClip.Width(), 
				rcClip.Height(), &memDC, 
				rcClip.left, rcClip.top, SRCCOPY );
	}
}

void CTreeCtrlEx::OnHScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	if( m_bitmap.m_hObject != NULL ) InvalidateRect(NULL);

	CTreeCtrl::OnHScroll(nSBCode, nPos, pScrollBar);
}

void CTreeCtrlEx::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
	if( m_bitmap.m_hObject != NULL ) InvalidateRect(NULL);

	CTreeCtrl::OnVScroll(nSBCode, nPos, pScrollBar);
}

void CTreeCtrlEx::OnTvnItemexpanding(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	if( m_bitmap.m_hObject != NULL ) Invalidate();
	*pResult = 0;
}

BOOL CTreeCtrlEx::SetBkImage(UINT nIDResource)
{
	return SetBkImage( (LPCTSTR)nIDResource );
}

BOOL CTreeCtrlEx::SetBkImage(LPCTSTR lpszResourceName)
{

	// If this is not the first call then Delete GDI objects
	if( m_bitmap.m_hObject != NULL )
		m_bitmap.DeleteObject();
	if( m_pal.m_hObject != NULL )
		m_pal.DeleteObject();
	
	HBITMAP hBmp = (HBITMAP)::LoadImage( AfxGetInstanceHandle(), 
			lpszResourceName, IMAGE_BITMAP, 0,0, LR_CREATEDIBSECTION );

	if( hBmp == NULL ) 
		return FALSE;

	m_bitmap.Attach( hBmp );
	BITMAP bm;
	m_bitmap.GetBitmap( &bm );
	m_cxBitmap = bm.bmWidth;
	m_cyBitmap = bm.bmHeight;

	// Create a logical palette for the bitmap
	DIBSECTION ds;
	BITMAPINFOHEADER &bmInfo = ds.dsBmih;
	m_bitmap.GetObject( sizeof(ds), &ds );

	int nColors = bmInfo.biClrUsed ? bmInfo.biClrUsed : 1 << bmInfo.biBitCount;

	// Create a halftone palette if colors > 256. 
	CClientDC dc(NULL);			// Desktop DC
	if( nColors > 256 )
		m_pal.CreateHalftonePalette( &dc );
	else
	{
		// Create the palette

		RGBQUAD *pRGB = new RGBQUAD[nColors];
		CDC memDC;
		memDC.CreateCompatibleDC(&dc);

		memDC.SelectObject( &m_bitmap );
		::GetDIBColorTable( memDC, 0, nColors, pRGB );

		UINT nSize = sizeof(LOGPALETTE) + (sizeof(PALETTEENTRY) * nColors);
		LOGPALETTE *pLP = (LOGPALETTE *) new BYTE[nSize];

		pLP->palVersion = 0x300;
		pLP->palNumEntries = nColors;

		for( int i=0; i < nColors; i++)
		{
			pLP->palPalEntry[i].peRed = pRGB[i].rgbRed;
			pLP->palPalEntry[i].peGreen = pRGB[i].rgbGreen;
			pLP->palPalEntry[i].peBlue = pRGB[i].rgbBlue;
			pLP->palPalEntry[i].peFlags = 0;
		}

		m_pal.CreatePalette( pLP );

		delete[] pLP;
		delete[] pRGB;
	}
	Invalidate();

	return TRUE;
}


void CTreeCtrlEx::OnSize(UINT nType, int cx, int cy)
{
	CTreeCtrl::OnSize(nType, cx, cy);
	if( m_bitmap.m_hObject != NULL ) InvalidateRect(NULL);
}

// Draw selected TreeCtrl Item
void CTreeCtrlEx::DrawSelectedItem( CDC *pDC )
{
	CRect rc_item;
	HTREEITEM hItem = this->GetSelectedItem();
	if ( GetItemRect( hItem, rc_item, TRUE ) )
	{
		//draw background
		pDC->FillSolidRect( rc_item, RGB(102, 161, 219) );
		rc_item.DeflateRect( 1, 1 );
		pDC->FillSolidRect( rc_item, RGB(212, 238, 251) );

		//draw text
		CString name = GetItemText( hItem );
		CFont* hFont = GetFont();
		ASSERT( hFont != NULL );
		CFont* hOldFont;
		UINT state = this->GetItemState(hItem, TVIS_BOLD);
		if( state & TVIS_BOLD )
		{
			LOGFONT logFont;
			hFont->GetLogFont(&logFont);
			logFont.lfWeight = FW_BOLD;
			logFont.lfHeight = 15;
			CFont hFont1;
			hFont1.CreateFontIndirect(&logFont);
            hOldFont = pDC->SelectObject(&hFont1);
		}
		else
            hOldFont = pDC->SelectObject(hFont);

		TEXTMETRIC tm;
		pDC->GetTextMetrics(&tm);
		CSize sizeExtent = pDC->GetTextExtent(name, lstrlen(name));
		CPoint pt;
		pt.x = rc_item.left + 1;
		pt.y = rc_item.top + (rc_item.Height() - tm.tmHeight)/ 2;
		
		int nMode = pDC->SetBkMode(TRANSPARENT);
		pDC->SetTextColor( RGB( 60,77,129 ) );
		pDC->DrawState(pt, sizeExtent, name, DSS_NORMAL, TRUE, 0, (HBRUSH)NULL);
		pDC->SelectObject(hOldFont);
		pDC->SetBkMode(nMode);
	}
}

void CTreeCtrlEx::OnNMClick(NMHDR *pNMHDR, LRESULT *pResult)
{
	HTREEITEM hItem;
	CPoint pt;
	GetCursorPos(&pt);
	ScreenToClient(&pt);
	hItem = HitTest(pt);

	if(hItem == NULL)
		return ;

	if(!ItemHasChildren(hItem))
		return;

	InvalidateRect(NULL);

	UINT n = GetItemState(hItem, TVIS_EXPANDED);
	int nImage;
	GetItemImage( hItem, nImage, nImage );

	if(n&TVIS_EXPANDED)
	{
		if( nImage == 0 || nImage == 1 )
			nImage = 1;
		else if( nImage == 4 || nImage == 5 )
			nImage = 5;
		Expand(hItem, TVE_COLLAPSE);
		SetItemImage(hItem, nImage, nImage);
	}
	else
	{
		if( nImage == 0 || nImage == 1 )
			nImage = 0;
		else if( nImage == 5 || nImage == 4 )
			nImage = 4; 
		Expand(hItem, TVE_EXPAND);
		SetItemImage(hItem, nImage, nImage);
	}

	*pResult = 0;
}

void CTreeCtrlEx::OnTvnSelchanging(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	if( m_bitmap.m_hObject != NULL ) InvalidateRect(NULL);
	*pResult = 0;
}

BOOL CTreeCtrlEx::OnMouseWheel(UINT nFlags, short zDelta, CPoint pt)
{
	if( m_bitmap.m_hObject != NULL ) InvalidateRect(NULL);
	return CTreeCtrl::OnMouseWheel(nFlags, zDelta, pt);
}

void CTreeCtrlEx::OnLButtonDown(UINT nFlags, CPoint point)
{
	HTREEITEM hItem;
	CPoint pt;
	GetCursorPos(&pt);
	ScreenToClient(&pt);
	hItem = HitTest(pt);
	if(hItem != NULL)
		SelectItem(hItem);

	CTreeCtrl::OnLButtonDown(nFlags, point);
}

void CTreeCtrlEx::OnRButtonDown(UINT nFlags, CPoint point)
{
	HTREEITEM hItem;
	CPoint pt;
	GetCursorPos(&pt);
	ScreenToClient(&pt);
	hItem = HitTest(pt);
	if(hItem != NULL)
		SelectItem(hItem);

	CTreeCtrl::OnRButtonDown(nFlags, point);
}
