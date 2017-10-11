// UI\MeetingRoom\VideoShowWnd.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "VideoShowWnd.h"
#include ".\videoshowwnd.h"


// CVideoShowWnd

IMPLEMENT_DYNAMIC(CVideoShowWnd, CStatic)
CVideoShowWnd::CVideoShowWnd()
{
}

CVideoShowWnd::~CVideoShowWnd()
{
}


BEGIN_MESSAGE_MAP(CVideoShowWnd, CStatic)
	ON_WM_PAINT()
	ON_WM_CREATE()
END_MESSAGE_MAP()



// CVideoShowWnd 消息处理程序


void CVideoShowWnd::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	if( m_img.IsValidImage() )
	{
		CRect rcDraw;
		GetClientRect( &rcDraw );
		FCWin32::DrawImage( m_img, dc.GetSafeHdc(), rcDraw );
	}
	// 不为绘图消息调用 CStatic::OnPaint()
}

int CVideoShowWnd::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CStatic::OnCreate(lpCreateStruct) == -1)
		return -1;

	FCWin32::LoadImageBitmapRes( m_img, MAKEINTRESOURCE( IDB_VIDEOBG ) );

	return 0;
}
