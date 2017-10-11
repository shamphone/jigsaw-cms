#include "StdAfx.h"
#include "WhiteboardText.h"
#include ".\whiteboardtext.h"
#include "EditEx.h"

CWhiteboardText::CWhiteboardText( CWnd* pParent )
{
	m_pParent = pParent;
	m_pEdit = new CEditEx();
	m_pEdit->Create( ES_AUTOVSCROLL|ES_MULTILINE|ES_WANTRETURN|ES_NOHIDESEL|WS_CLIPCHILDREN|WS_CHILD, CRect(0, 0, 0, 0), pParent, 1 );
	m_pEdit->LimitText( 500 );

	m_nStyle |= CRectTracker::resizeOutside;
	m_nStyle |= CRectTracker::hatchedBorder;
	m_rect = CRect( 0, 0, 100, 20 );
	m_sizeMin = CSize( 50, 20 );
}

CWhiteboardText::~CWhiteboardText(void)
{
	m_pEdit->DestroyWindow();
	delete m_pEdit;
}
		
void CWhiteboardText::Draw( CDC* pDC ) const
{
	CRectTracker::Draw( pDC );
	m_pEdit->MoveWindow( m_rect );
	m_pEdit->EnableWindow( TRUE );
	m_pEdit->ShowWindow( TRUE );
	m_pEdit->SetFocus();
}

CString CWhiteboardText::getText()
{
	CString str;
	int nLineCount = m_pEdit->GetLineCount();
	CString strText;
	for( int i = 0; i < nLineCount; i++ )
	{
		int len = m_pEdit->LineLength( m_pEdit->LineIndex(i) );
		if( len > 0 )
		{
			m_pEdit->GetLine( i, strText.GetBuffer(len), len );
			strText.ReleaseBuffer(len);
			if( i < nLineCount - 1 )
				str = str + strText + "\n\r";
			else
				str = str + strText;
		}
		else if( i < nLineCount - 1 )
		{
			str = str + "\n\r";
		}
	}
	return str;
}

void CWhiteboardText::setText( CString str )
{
	m_pEdit->SetWindowText( str );
}

void CWhiteboardText::showWindow( BOOL bShow )
{
	m_pEdit->ShowWindow( bShow );
	m_pEdit->EnableWindow( bShow );
}

void CWhiteboardText::setFontAndColor(LOGFONT lf, COLORREF color)
{
	m_pEdit->setFontAndColor( lf, color );
}
