#pragma once

// In this version, we use fixed video size for capture and transfer.
// The video window may have other size, and this is handled by 
// display.
// 
// RGB24, 176*144, 5-15 f/s

struct VideoConfig
{
	static const int DEFAULT_WIDTH = 176;
	static const int DEFAULT_HEIGHT = 144;
	static const int DEFAULT_BIT = 24;

	static const int DEFAULT_SIZE = DEFAULT_WIDTH * DEFAULT_HEIGHT * DEFAULT_BIT / 8;

	static const BITMAPINFOHEADER DEFAULT_BITMAP_INFO_HEADER;

    static const TIME_UNIT = 10000000;
	static const int DEFAULT_FRAME_RATE = 10;

    static const MIN_FRAME_RATE = 5;
    static const MAX_FRAME_RATE = 15;

    static int framerate;

    static bool setFrameRate(int framerate);
    static int getFrameRate();

	//added by zhanglun
    static void setSize(int size);
    static int getSize();

	static void setWidth(int width);
	static int getWidth();
	static void setHeight(int height);
	static int getHeight();
	static int getMaxSize();

	static BITMAPINFOHEADER BITMAP_INFO_HEADER;
	static void setBitmapInfoHeader();
	static BITMAPINFOHEADER getBitmapInfoHeader();

private:
    static int width;
    static int height;
    static int size;
	static int maxSize;

};

struct PAL_FORMAT
{
	static const int WIDTH = 720;
	static const int HEIGHT = 576;
};

