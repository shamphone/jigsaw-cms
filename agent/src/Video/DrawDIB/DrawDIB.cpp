#include "stdafx.h"
#include "drawdib.h"
#include "..\VideoConfig\VideoConfig.h"


CDrawDIB::CDrawDIB(void)
{
	m_hBmp = 0;
	m_hDrawDib = ::DrawDibOpen();
}

CDrawDIB::~CDrawDIB(void)
{
	::DrawDibClose(m_hDrawDib);
    if(m_hBmp)
    {
        ::DeleteObject(m_hBmp);
    }
}

void CDrawDIB::DrawVideo(HWND hWnd,
						 char *pVideoData,						 
						 BITMAPINFOHEADER* pbih)
{
    // Create the bitmap when the DrawVideo function is called first time.
	if (!m_hBmp)
	{
		HDC hdc=::GetDC(hWnd);
		//m_hBmp=::CreateCompatibleBitmap(hdc, VideoConfig::getWidth(), VideoConfig::getHeight());
		m_hBmp=::CreateCompatibleBitmap(hdc, 704, 576);
		::ReleaseDC(hWnd,hdc);
	}

    // Get target window size
    RECT rc;
    ::GetWindowRect(hWnd, &rc);

	HDC hdc=::GetDC(hWnd);
	HDC hMemDC=::CreateCompatibleDC(hdc);
	HBITMAP hob=(HBITMAP)::SelectObject(hMemDC,m_hBmp);

	if (hob)
	{
		// If the window client area are larger than the
		// video size, we draw all the area, and then
		// stretch it.
		if (pbih->biWidth < (rc.right - rc.left))
        {
			::DrawDibDraw(m_hDrawDib, hMemDC, 0, 0,
                    pbih->biWidth,
                    pbih->biHeight,
                    pbih,
                    pVideoData,0,0,
                    pbih->biWidth,
                    pbih->biHeight,
                    DDF_NOTKEYFRAME);

			::StretchBlt(hdc, 0, 0, (rc.right - rc.left), (rc.bottom - rc.top),
                    hMemDC,0,0,
                    pbih->biWidth,
                    pbih->biHeight,
                    SRCCOPY);
        }

		// else if the window client are equal to or smaller than
		// the video size, we draw the client area size, and then
		// bitblt it
		else
        {
			::DrawDibDraw(m_hDrawDib, hMemDC, 0, 0,
					rc.right - rc.left,
					rc.bottom - rc.top,
                    pbih,
                    pVideoData,
                    0,0,pbih->biWidth,pbih->biHeight,
                    DDF_NOTKEYFRAME);

			::BitBlt(hdc, 0, 0, (rc.right-rc.left), (rc.bottom-rc.top),hMemDC,0,0,SRCCOPY);	
        }

		::SelectObject(hMemDC,hob);

	}
	::DeleteDC(hMemDC);
	::ReleaseDC(hWnd,hdc);

}
