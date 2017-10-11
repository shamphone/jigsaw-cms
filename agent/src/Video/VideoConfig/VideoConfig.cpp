#include "stdafx.h"
#include "VideoConfig.h"

int VideoConfig::width = VideoConfig::DEFAULT_WIDTH;
int VideoConfig::height = VideoConfig::DEFAULT_HEIGHT;
int VideoConfig::framerate = VideoConfig::DEFAULT_FRAME_RATE;

int VideoConfig::size = VideoConfig::DEFAULT_SIZE;
int VideoConfig::maxSize = 704*576*24/8;

BITMAPINFOHEADER VideoConfig::BITMAP_INFO_HEADER = 
{
    sizeof(BITMAPINFOHEADER),     // biSize
    DEFAULT_WIDTH,                // biWidth
    DEFAULT_HEIGHT,               // biHeight
    1,                            // biPlanes
    DEFAULT_BIT,                  // bitCount
    BI_RGB,                       // biCompression
    DEFAULT_SIZE,                 // biSizeImage
    0,                            // biXPelsPerMeter
    0,                            // biYPelsPerMeter
    0,                            // biClrUsed
    0,                            // biImportant
};

const BITMAPINFOHEADER VideoConfig::DEFAULT_BITMAP_INFO_HEADER = 
{
    sizeof(BITMAPINFOHEADER),     // biSize
    DEFAULT_WIDTH,                // biWidth
    DEFAULT_HEIGHT,               // biHeight
    1,                            // biPlanes
    DEFAULT_BIT,                  // bitCount
    BI_RGB,                       // biCompression
    DEFAULT_SIZE,                 // biSizeImage
    0,                            // biXPelsPerMeter
    0,                            // biYPelsPerMeter
    0,                            // biClrUsed
    0,                            // biImportant
};

bool VideoConfig::setFrameRate(int framerate)
{
    if( framerate < VideoConfig::MIN_FRAME_RATE )
    {
        VideoConfig::framerate = VideoConfig::MIN_FRAME_RATE;
        return false;
    }

    if( framerate > VideoConfig::MAX_FRAME_RATE )
    {
        VideoConfig::framerate = VideoConfig::MAX_FRAME_RATE;
        return false;
    }

    VideoConfig::framerate = framerate;
    return true;
}

int VideoConfig::getFrameRate()
{
    return VideoConfig::framerate;
}

void VideoConfig::setSize(int size)
{
	VideoConfig::size = size;
}

int VideoConfig::getSize()
{
    return VideoConfig::size;
}

int VideoConfig::getMaxSize()
{
	return VideoConfig::maxSize;
}

void VideoConfig::setWidth(int width)
{
	VideoConfig::width = width;
}

int VideoConfig::getWidth()
{
    return VideoConfig::width;
}

void VideoConfig::setHeight(int height)
{
	VideoConfig::height = height;
}

int VideoConfig::getHeight()
{
    return VideoConfig::height;
}

void VideoConfig::setBitmapInfoHeader()
{
	VideoConfig::BITMAP_INFO_HEADER.biWidth = VideoConfig::width;
	VideoConfig::BITMAP_INFO_HEADER.biHeight = VideoConfig::height;
	VideoConfig::BITMAP_INFO_HEADER.biSizeImage = VideoConfig::size;
}

BITMAPINFOHEADER VideoConfig::getBitmapInfoHeader()
{
	return VideoConfig::BITMAP_INFO_HEADER;
}

