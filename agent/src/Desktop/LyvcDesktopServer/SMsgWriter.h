#pragma once
#include "rfb/types.h"
#include "rfb/Rect.h"
#include "rfb/ConnParams.h"
#include "rfb/UpdateTracker.h"
#include "rfb/msgTypes.h"
#include "rfb/ZRLEEncoder.h"

namespace rdr { class OutStream; }

class SMsgWriter
{
public:
	//构造函数
	SMsgWriter();
	~SMsgWriter(void);

public:
	void setOutStream(rdr::OutStream* os) { m_os = os; };
	rdr::OutStream* getOutStream() { return m_os; };
	void startRect(const rfb::Rect& r) ;
	void endRect() ;
	void startMsg(int type);
	void endMsg();

	//发送一些初始化信息
	void writeServerInit(rfb::ColourMap* cm=0);
	//发送变化了的矩形数据
	void writeRects(const rfb::UpdateInfo& ui, rfb::ImageGetter* ig, rfb::Region* updatedRegion);
	//鼠标数据
	void writeCursor(const rfb::Rect& r, const rfb::Point& pt, rfb::ImageGetter* ig);

	//为要发送的数据申请内存空间
    rdr::U8* getImageBuf(int required);

public:
	//客户端和服务端的一些连接参数
	rfb::ConnParams* m_cp;

private:
	void writeSetColorMapEntries(int firstColour, int nColours, rfb::ColourMap* cm);
	//拷贝矩形信息
	void writeCopyRect(const rfb::Rect& r, int srcX, int srcY);
	//实际发送变化矩形的方法
	void writeRect(const rfb::Rect& r, rfb::ImageGetter* ig);//const char* data, int uncomprLen, int comprLen);

private:
	// zrle encoder
	rfb::ZRLEEncoder* m_encoder;
	// 输出
	rdr::OutStream* m_os;
	//矩形数据缓冲区
    rdr::U8* m_imageBuf;
	//缓冲区大小
    int m_imageBufSize;

};
