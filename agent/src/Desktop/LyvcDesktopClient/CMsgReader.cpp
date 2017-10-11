#include "StdAfx.h"

#include ".\cmsgreader.h"
#include "CMsgHandler.h"
#include "LyvcDesktopClient.h"
#include "rfb/ZRLEDecoder.h"
#include "rfb/Rect.h"

using namespace rfb;

CMsgReader::CMsgReader(LyvcDesktopClient* desktopClient)
{
	m_is = 0;
	m_pDesktopClient = desktopClient;

    m_imageBuf = 0;
    m_imageBufSize = 0;
}

CMsgReader::~CMsgReader(void)
{
	if(m_imageBuf)
	{
		delete [] m_imageBuf;
	}

	std::map<__int64, rfb::ZRLEDecoder*>::iterator it;
	for( it = m_decoderMap.begin(); it != this->m_decoderMap.end(); it++ )
	{
		rfb::ZRLEDecoder* pDecoder = it->second;
		delete pDecoder;
	}
}

void CMsgReader::readRect(const rfb::Rect& r, __int64 uid)
{
	CMsgHandler* pHandler = m_pDesktopClient->getMsgHandler(uid);
	if (!pHandler->m_bInitialized)
	{
		return;
	}
	this->getDecoder(uid)->readRect(r, pHandler, pHandler->cp.pf().bpp);
}

void CMsgReader::readCopyRect(const rfb::Rect& r, __int64 uid)
{
	CMsgHandler* pHandler = m_pDesktopClient->getMsgHandler(uid);
	if (!pHandler->m_bInitialized)
	{
		return;
	}
	int x = m_is->readU16();
	int y = m_is->readU16();
    pHandler->copyRect(r, x, y);
}

void CMsgReader::readServerInit(__int64 uid)
{
	int width = m_is->readU16();
	int height = m_is->readU16();
	CMsgHandler* pMsgHandler = m_pDesktopClient->getMsgHandler(uid);
	pMsgHandler->cp.width = width;
	pMsgHandler->cp.height = height;

	PixelFormat pf;
	pf.bpp = m_is->readU8();
	pf.depth = m_is->readU8();
	pf.bigEndian = m_is->readU8();
	pf.trueColour = m_is->readU8();
	pf.redMax = m_is->readU16();
	pf.greenMax = m_is->readU16();
	pf.blueMax = m_is->readU16();
	pf.redShift = m_is->readU8();
	pf.greenShift = m_is->readU8();
	pf.blueShift = m_is->readU8();
	pMsgHandler->cp.setPF(pf); 

	int firstColour = m_is->readU16();
	int nColours = m_is->readU16();
	rdr::U16Array rgbs(nColours * 3);
	for (int i = 0; i < nColours * 3; i++)
	{
		rgbs.buf[i] = m_is->readU16();
	}

	pMsgHandler->serverInit();
	if (pf.bpp <= 8)
	{
		pMsgHandler->setColourMapEntries(firstColour, nColours, rgbs.buf);
	}

	//³õÊ¼»¯½âÂëÆ÷
	this->createDecoder( uid );
}

void CMsgReader::readCursor(const rfb::Rect& r, __int64 uid)
{
	CMsgHandler* pHandler = m_pDesktopClient->getMsgHandler(uid);
	if (!pHandler->m_bInitialized)
	{
		return;
	}
	int len = r.area()*pHandler->cp.pf().bpp/8;
	rdr::U8* buf = getImageBuf(len);
	m_is->readBytes(buf, len);

    pHandler->imageRect(r, buf);
}

rdr::U8* CMsgReader::getImageBuf(int required)
{
	if (m_imageBufSize < required) 
	{
		m_imageBufSize = required;
		delete [] m_imageBuf;
		m_imageBuf = new rdr::U8[m_imageBufSize];
	}
	return m_imageBuf;
}

rfb::ZRLEDecoder* CMsgReader::getDecoder(__int64 uid)
{
	std::map<__int64, rfb::ZRLEDecoder*>::iterator it;
	it = this->m_decoderMap.find(uid);
    _ASSERTE( it != this->m_decoderMap.end() );
	return it->second;
}

void CMsgReader::createDecoder(__int64 uid)
{
	std::map<__int64, rfb::ZRLEDecoder*>::iterator it;
	it = this->m_decoderMap.find(uid);
    if( it != this->m_decoderMap.end() )
	{
		rfb::ZRLEDecoder* pDecoder = it->second;
		delete pDecoder;
	}
	rfb::ZRLEDecoder* pDecoder = rfb::ZRLEDecoder::create(this);
	this->m_decoderMap[uid] = pDecoder;
}

void CMsgReader::readMsg(__int64 uid)
{
    int x;
    int y;
    int w;
    int h;

	int msgType = m_is->readU8();
	switch (msgType)
	{
	case msgTypeServerInit:
		readServerInit(uid);
		break;
	case msgTypeChangedRect:
		x = m_is->readU16();
		y = m_is->readU16();
		w = m_is->readU16();
		h = m_is->readU16();
		readRect(Rect(x, y, x+w, y+h), uid);
		break;
	case msgTypeCopiedRect:
		x = m_is->readU16();
		y = m_is->readU16();
		w = m_is->readU16();
		h = m_is->readU16();
		readCopyRect(Rect(x, y, x+w, y+h), uid);
		break;
	case msgTypeRenderCursor:
		x = m_is->readU16();
		y = m_is->readU16();
		w = m_is->readU16();
		h = m_is->readU16();
		readCursor(Rect(x, y, x+w, y+h), uid);
		break;
	default:
		_ASSERTE(FALSE);
		break;
	}
}
