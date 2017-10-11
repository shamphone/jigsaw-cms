#pragma once

class CDrawDIB
{
public:
	CDrawDIB(void);
	~CDrawDIB(void);

public:
	void DrawVideo(
            HWND hWnd,
            char *pVideoData,
            BITMAPINFOHEADER* pbih);

private:
	HBITMAP m_hBmp;
	HDRAWDIB m_hDrawDib;
};

