#pragma once

#include "rfb\Rect.h"
#include "rfb\types.h"
#include "rfb\msgTypes.h"
#include "rfb\ZRLEDecoder.h"
#include <map>

namespace rdr { class InStream; }

class LyvcDesktopClient;

class CMsgReader
{
public:
	CMsgReader(LyvcDesktopClient* desktopClient);
	~CMsgReader(void);

public:
	//服务端消息处理 
	void readMsg(__int64 uid);

    rdr::U8* getImageBuf(int required);
	rdr::InStream* getInStream() { return m_is; };
	void setInStream(rdr::InStream* is) { m_is = is; }

private:
	void readRect(const rfb::Rect& r, __int64 uid);
    void readCopyRect(const rfb::Rect& r, __int64 uid);
    void readServerInit(__int64 uid);
	void readCursor(const rfb::Rect& r, __int64 uid);

	rdr::U8* getTransImageBuf(int required);
	rfb::ZRLEDecoder* getDecoder(__int64 uid);
    void createDecoder(__int64 uid);

private:
	std::map<__int64, rfb::ZRLEDecoder*> m_decoderMap;
	rdr::InStream* m_is;
	LyvcDesktopClient* m_pDesktopClient;

    rdr::U8* m_imageBuf;
    int m_imageBufSize;
};
