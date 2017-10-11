#pragma once
#include <string>
#include "rfb\Rect.h"
#include "rfb\ConnParams.h"
#include "rfb\types.h"

class CMsgHandler
{
public:
	virtual void fillRect(const rfb::Rect& r, rfb::Pixel pix) = 0;
	virtual void imageRect(const rfb::Rect& r, void* pixels) = 0;
	virtual void copyRect(const rfb::Rect& r, int srcX, int srcY) = 0;
    virtual void serverInit() = 0;
    virtual void setColourMapEntries(int firstColour, int nColours, rdr::U16* rgbs) = 0;
	virtual void serverStop() = 0;
	virtual void setCursor(const rfb::Point& hotspot, const rfb::Point& size, void* data, void* mask) = 0;

	rfb::ConnParams cp;
	rfb::PixelFormat m_nativePF;
	bool m_bNeedTranslate;
	__int64 m_serverUserId;
	bool m_bInitialized;

};
