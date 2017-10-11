// ui\pubListCtrlEx.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "ListCtrlEx.h"
#include ".\listctrlex.h"


// CListCtrlEx

IMPLEMENT_DYNAMIC(CListCtrlEx, CListCtrl)
CListCtrlEx::CListCtrlEx()
{
}

CListCtrlEx::~CListCtrlEx()
{
}


BEGIN_MESSAGE_MAP(CListCtrlEx, CListCtrl)
	ON_WM_ERASEBKGND()
	ON_WM_QUERYNEWPALETTE()
	ON_WM_PALETTECHANGED()
END_MESSAGE_MAP()



// CListCtrlEx 消息处理程序

BOOL CListCtrlEx::SetBkImage(UINT nIDResource)
{
	return SetBkImage( (LPCTSTR)nIDResource );
}

BOOL CListCtrlEx::SetBkImage(LPCTSTR lpszResourceName)
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

	int nColors = bmInfo.biClrUsed ? bmInfo.biClrUsed : 1 << bmInfo.biBitCount; // Create a halftone palette if colors> 256. 
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

		for( int i=0; i < nColors; i++ )
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

void CListCtrlEx::DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct)
{
	CDC* pDC = CDC::FromHandle(lpDrawItemStruct->hDC);
	CRect rcItem(lpDrawItemStruct->rcItem);
	int nItem = lpDrawItemStruct->itemID;
	CImageList* pImageList;

	// Save dc state
	int nSavedDC = pDC->SaveDC();


	// Get item image and state info
	LV_ITEM lvi;
	lvi.mask = LVIF_IMAGE | LVIF_STATE;
	lvi.iItem = nItem;
	lvi.iSubItem = 0;
	lvi.stateMask = 0xFFFF;		// get all state flags
	GetItem(&lvi);

	// Should the item be highlighted
	BOOL bHighlight =	((lvi.state & LVIS_DROPHILITED)
				|| ( (lvi.state & LVIS_SELECTED)
					&& ((GetFocus() == this)
						|| (GetStyle() & LVS_SHOWSELALWAYS)
						)
					)
				);


	// Get rectangles for drawing
	CRect rcBounds, rcLabel, rcIcon;
	GetItemRect(nItem, rcBounds, LVIR_BOUNDS);
	GetItemRect(nItem, rcLabel, LVIR_LABEL);
	GetItemRect(nItem, rcIcon, LVIR_ICON);
	CRect rcCol( rcBounds );


	CString sLabel = GetItemText( nItem, 0 );

	// Labels are offset by a certain amount  
	// This offset is related to the width of a space character
	int offset = pDC->GetTextExtent(_T(" "), 1 ).cx*2;

	CRect rcHighlight;
	CRect rcClient;
	int nExt;
	switch( bHighlight )
	{
	case 0:
		nExt = pDC->GetOutputTextExtent(sLabel).cx + offset;
		rcHighlight = rcLabel;
		if( rcLabel.left + nExt < rcLabel.right )
			rcHighlight.right = rcLabel.left + nExt;
		break;
	case 1:
		rcHighlight = rcBounds;
		rcHighlight.left = rcLabel.left;
		break;
	case 2:
		GetClientRect(&rcClient);
		rcHighlight = rcBounds;
		rcHighlight.left = rcLabel.left;
		rcHighlight.right = rcClient.right;
		break;
	default:
		rcHighlight = rcLabel;
	}



	// Draw bitmap in the background if one has been set
	if( m_bitmap.m_hObject != NULL )
	{
		CDC tempDC;
		tempDC.CreateCompatibleDC(pDC);
		tempDC.SelectObject( &m_bitmap );

		GetClientRect(&rcClient);

		CRgn rgnBitmap;
		CRect rcTmpBmp( rcItem );

		rcTmpBmp.right = rcClient.right;

		// We also need to check whether it is the last item
		// The update region has to be extended to the bottom if it is
		if( nItem == GetItemCount() - 1 )
			rcTmpBmp.bottom = rcClient.bottom;

		rgnBitmap.CreateRectRgnIndirect(&rcTmpBmp);
		pDC->SelectClipRgn(&rgnBitmap);
		rgnBitmap.DeleteObject();

		if( pDC->GetDeviceCaps(RASTERCAPS) & RC_PALETTE && m_pal.m_hObject != NULL )
		{
			pDC->SelectPalette( &m_pal, FALSE );
			pDC->RealizePalette();
		}

		CRect rcFirstItem;
		GetItemRect(0, rcFirstItem, LVIR_BOUNDS);
		for( int i = rcFirstItem.left; i < rcClient.right; i += m_cxBitmap )
			for( int j = rcFirstItem.top; j < rcClient.bottom; j += m_cyBitmap )
				pDC->BitBlt( i, j, m_cxBitmap, m_cyBitmap, &tempDC,
								0, 0, SRCCOPY );
	}



	// Draw the background color
	if( bHighlight )
	{
		pDC->SetTextColor(::GetSysColor(COLOR_HIGHLIGHTTEXT));
		pDC->SetBkColor(::GetSysColor(COLOR_HIGHLIGHT));

		pDC->FillRect(rcHighlight, &CBrush(::GetSysColor(COLOR_HIGHLIGHT)));
	}
	else if( m_bitmap.m_hObject == NULL )
		pDC->FillRect(rcHighlight, &CBrush(::GetSysColor(COLOR_WINDOW)));



	// Set clip region
	rcCol.right = rcCol.left + GetColumnWidth(0);
	CRgn rgn;
	rgn.CreateRectRgnIndirect(&rcCol);
	pDC->SelectClipRgn(&rgn);
	rgn.DeleteObject();

	// Draw state icon
	if (lvi.state & LVIS_STATEIMAGEMASK)
	{
		int nImage = ((lvi.state & LVIS_STATEIMAGEMASK)>>12) - 1;
		pImageList = GetImageList(LVSIL_STATE);
		if (pImageList)
		{
			pImageList->Draw(pDC, nImage,
				CPoint(rcCol.left, rcCol.top), ILD_TRANSPARENT);
		}
	}

	// Draw normal and overlay icon
	pImageList = GetImageList(LVSIL_SMALL);
	if (pImageList)
	{
		UINT nOvlImageMask=lvi.state & LVIS_OVERLAYMASK;
		pImageList->Draw(pDC, lvi.iImage,
			CPoint(rcIcon.left, rcIcon.top),
			(bHighlight?ILD_BLEND50:0) | ILD_TRANSPARENT | nOvlImageMask );
	}



	// Draw item label - Column 0
	rcLabel.left += offset/2;
	rcLabel.right -= offset;

	pDC->DrawText(sLabel,-1,rcLabel,DT_LEFT | DT_SINGLELINE | DT_NOPREFIX | DT_NOCLIP
				| DT_VCENTER | DT_END_ELLIPSIS);


	// Draw labels for remaining columns
	LV_COLUMN lvc;
	lvc.mask = LVCF_FMT | LVCF_WIDTH;

	if( bHighlight == 0 )		// Highlight only first column
	{
		pDC->SetTextColor(::GetSysColor(COLOR_WINDOWTEXT));
		pDC->SetBkColor(::GetSysColor(COLOR_WINDOW));
	}

	rcBounds.right = rcHighlight.right > rcBounds.right ? rcHighlight.right :
							rcBounds.right;
	rgn.CreateRectRgnIndirect(&rcBounds);
	pDC->SelectClipRgn(&rgn);

	for(int nColumn = 1; GetColumn(nColumn, &lvc); nColumn++)
	{
		rcCol.left = rcCol.right;
		rcCol.right += lvc.cx;

		// Draw the background if needed
		if( m_bitmap.m_hObject == NULL )//&& bHighlight == HIGHLIGHT_NORMAL )
			pDC->FillRect(rcCol, &CBrush(::GetSysColor(COLOR_WINDOW)));

		sLabel = GetItemText(nItem, nColumn);
		if (sLabel.GetLength() == 0)
			continue;


		// Get the text justification
		UINT nJustify = DT_LEFT;
		switch(lvc.fmt & LVCFMT_JUSTIFYMASK)
		{
		case LVCFMT_RIGHT:
			nJustify = DT_RIGHT;
			break;
		case LVCFMT_CENTER:
			nJustify = DT_CENTER;
			break;
		default:
			break;
		}

		rcLabel = rcCol;
		rcLabel.left += offset;
		rcLabel.right -= offset;

		pDC->DrawText(sLabel, -1, rcLabel, nJustify | DT_SINGLELINE
				| DT_NOPREFIX | DT_VCENTER | DT_END_ELLIPSIS);
	}

	// Draw focus rectangle if item has focus
	if (lvi.state & LVIS_FOCUSED && (GetFocus() == this))
		pDC->DrawFocusRect(rcHighlight);


	// Restore dc
	pDC->RestoreDC( nSavedDC );
}

BOOL CListCtrlEx::OnEraseBkgnd(CDC* pDC)
{
	//if( m_bitmap.m_hObject != NULL )
	//	return TRUE;
	return CListCtrl::OnEraseBkgnd(pDC);
}

BOOL CListCtrlEx::OnNotify(WPARAM wParam, LPARAM lParam, LRESULT* pResult)
{
	HD_NOTIFY	*pHDN = (HD_NOTIFY*)lParam;

	// This code is for using bitmap in the background
	// Invalidate the right side of the control when a column is resized
	if(pHDN->hdr.code == HDN_ITEMCHANGINGW || pHDN->hdr.code == HDN_ITEMCHANGINGA)
	{
		if( m_bitmap.m_hObject != NULL )
		{
			CRect rcClient;
			GetClientRect( &rcClient );
			DWORD dwPos = GetMessagePos();
			CPoint pt( LOWORD(dwPos), HIWORD(dwPos) );
			ScreenToClient( &pt );
			rcClient.left = pt.x;
			InvalidateRect( &rcClient );
		}
	}
	return CListCtrl::OnNotify(wParam, lParam, pResult);
}

BOOL CListCtrlEx::OnQueryNewPalette()
{
	CClientDC dc(this);
	if( dc.GetDeviceCaps(RASTERCAPS) & RC_PALETTE && m_pal.m_hObject != NULL )
	{
		dc.SelectPalette( &m_pal, FALSE );
		BOOL result = dc.RealizePalette();
		if( result )
			Invalidate();
		return result;
	}
	
	return CListCtrl::OnQueryNewPalette();
}

void CListCtrlEx::OnPaletteChanged(CWnd *pFocusWnd)
{
	CListCtrl::OnPaletteChanged(pFocusWnd);
	
	if( pFocusWnd == this )
		return;

	OnQueryNewPalette();
}