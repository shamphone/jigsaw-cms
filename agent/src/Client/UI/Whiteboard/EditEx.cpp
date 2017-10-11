// WhiteboardText.cpp : 实现文件
//

#include "stdafx.h"
#include "EditEx.h"
#include ".\editex.h"


// CEditEx

IMPLEMENT_DYNAMIC(CEditEx, CEdit)
CEditEx::CEditEx()
{
}

CEditEx::~CEditEx()
{
}

BEGIN_MESSAGE_MAP(CEditEx, CEdit)
	ON_WM_CREATE()
	ON_WM_CTLCOLOR_REFLECT()
END_MESSAGE_MAP()


// CEditEx 消息处理程序

int CEditEx::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CEdit::OnCreate(lpCreateStruct) == -1)
		return -1;

	m_crTextColor = RGB( 255, 0, 0 );
	m_crBackColor = RGB( 255, 255, 255 );
	m_brushBk.CreateSolidBrush( m_crBackColor );
	return 0;
}

HBRUSH CEditEx::CtlColor(CDC* pDC, UINT nCtlColor)
{
	pDC->SetTextColor( m_crTextColor );
    pDC->SetBkColor( m_crBackColor );
	return m_brushBk;
}

void CEditEx::setFontAndColor(LOGFONT lf, COLORREF color)
{
	m_crTextColor = color;
	if( m_font.GetSafeHandle() )
	{
		m_font.DeleteObject();
	}
	m_font.CreateFontIndirect( &lf );
	this->SetFont( &m_font );
}
