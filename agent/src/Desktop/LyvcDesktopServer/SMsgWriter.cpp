#include "StdAfx.h"
#include ".\smsgwriter.h"

#include "rfb/ZRLEEncoder.h"

using namespace rfb;
using namespace rdr;

SMsgWriter::SMsgWriter()
{
	m_cp = new ConnParams();
	m_imageBuf = 0;
	m_imageBufSize = 0;
	m_encoder = 0;
	m_os = 0;
}

SMsgWriter::~SMsgWriter(void)
{
	delete [] m_imageBuf;
	if (m_encoder)
		delete m_encoder;
	if (m_cp)
		delete m_cp;
}

void SMsgWriter::writeCopyRect(const rfb::Rect& r, int srcX, int srcY)
{
	startMsg(msgTypeCopiedRect);
	startRect(r);
	m_os->writeU16(srcX);
	m_os->writeU16(srcY);
	endMsg();
}

void SMsgWriter::writeRect(const rfb::Rect& r, ImageGetter* ig)
{
	if (r.area() == 0)
		return;

	Rect actual;
	m_encoder->writeRect(r, ig, &actual);	
}

void SMsgWriter::writeRects(const UpdateInfo& ui, ImageGetter* ig, Region* updatedRegion)
{
	std::vector<Rect> rects;
	std::vector<Rect>::const_iterator i;
	updatedRegion->copyFrom(ui.changed);
	updatedRegion->assign_union(ui.copied);

	ui.copied.get_rects(&rects, ui.copy_delta.x <= 0, ui.copy_delta.y <= 0);
	for (i = rects.begin(); i != rects.end(); i++)
		writeCopyRect(*i, i->tl.x - ui.copy_delta.x, i->tl.y - ui.copy_delta.y);

	ui.changed.get_rects(&rects);
	for (i = rects.begin(); i != rects.end(); i++)
	{
		writeRect(*i, ig);
	}
}

void SMsgWriter::writeSetColorMapEntries(int firstColour, int nColours, ColourMap* cm)
{
	m_os->writeU16(firstColour);
	m_os->writeU16(nColours);
	for (int i = firstColour; i < firstColour+nColours; i++) 
	{
		int r, g, b;
		cm->lookup(i, &r, &g, &b);
		m_os->writeU16(r);
		m_os->writeU16(g);
		m_os->writeU16(b);
	}
}

void SMsgWriter::writeServerInit(ColourMap* cm)
{
	//初始化编码器
	if(m_encoder)
	{
		delete m_encoder;
	}
	m_encoder = ZRLEEncoder::create(this);

	//发送初始化信息
	startMsg(msgTypeServerInit);

	m_os->writeU16(m_cp->width);
	m_os->writeU16(m_cp->height);
	m_os->writeU8(m_cp->pf().bpp);
	m_os->writeU8(m_cp->pf().depth);
	m_os->writeU8(m_cp->pf().bigEndian);
	m_os->writeU8(m_cp->pf().trueColour);
	m_os->writeU16(m_cp->pf().redMax);
	m_os->writeU16(m_cp->pf().greenMax);
	m_os->writeU16(m_cp->pf().blueMax);
	m_os->writeU8(m_cp->pf().redShift);
	m_os->writeU8(m_cp->pf().greenShift);
	m_os->writeU8(m_cp->pf().blueShift);

	this->writeSetColorMapEntries(0, 256, cm);

	endMsg();
}

void SMsgWriter::writeCursor(const rfb::Rect& r, const rfb::Point& pt, rfb::ImageGetter* ig)
{
	if (r.area() == 0)
		return;
	Rect rc;
	rc.tl.x = r.tl.x + pt.x;
	rc.tl.y = r.tl.y + pt.y;
	rc.br.x = r.br.x + pt.x;
	rc.br.y = r.br.y + pt.y;
	int len = r.area() * m_cp->pf().bpp/8;
	rdr::U8* buf = getImageBuf(len);
	ig->getImage(buf, r);

	startMsg(msgTypeRenderCursor);
	startRect(rc);
	m_os->writeBytes(buf, len);
	endMsg();
}

rdr::U8* SMsgWriter::getImageBuf(int required)
{
	int size = required * (m_cp->pf().bpp / 8);

	if (m_imageBufSize < size) 
	{
		m_imageBufSize = size;
		delete [] m_imageBuf;
		m_imageBuf = new rdr::U8[m_imageBufSize];
	}
	return m_imageBuf;
}

void SMsgWriter::startMsg(int type)
{
	m_os->writeU8(type);
}

void SMsgWriter::endMsg()
{
	m_os->flush();
}

void SMsgWriter::startRect(const rfb::Rect& r) 
{
	m_os->writeU16(r.tl.x);
	m_os->writeU16(r.tl.y);
	m_os->writeU16(r.width());
	m_os->writeU16(r.height());
};

void SMsgWriter::endRect()
{
}
