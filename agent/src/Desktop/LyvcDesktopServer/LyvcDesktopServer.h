#pragma once

#include "rfb\VNCServer.h"
#include "DesktopServer.h"
#include "SMsgHandler.h"
#include "rfb\SDesktop.h"
#include "rfb\Cursor.h"
#include "rfb\TransImageGetter.h"

namespace rfb
{
	class ComparingUpdateTracker;
	class PixelBuffer;
}

namespace LyvcMessage
{
	class MatrixC;
}

class SMsgWriter;
class SMsgHandler;
class SMsgReader;
class LyvcOutStream;

class LyvcDesktopServer : rfb::VNCServer, 
						public DesktopServer,
						public SMsgHandler
{
//构造，析构函数
public:
	LyvcDesktopServer();
	~LyvcDesktopServer();

//
//从DesktopServer继承，对外的接口
//
public:
	//开始
	virtual void start(
		LyvcMessage::MatrixC* pMatrixC,       // 消息系统
		DESKTOPSERVER_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter);            // Callback parameter
	//结束
	virtual void stop();
	virtual void reInitialize();
//
//从VNCServer继承
//
public:
	virtual void setPixelBuffer(rfb::PixelBuffer* pb_);
	virtual void setColourMapEntries(int firstColour=0, int nColours=0);
	virtual void serverCutText(const char* str, int len) {};
	virtual void bell() {};
	virtual bool clientsReadyForUpdate() { return true; };
	virtual void closeClients(const char* reason) {};
    virtual void tryUpdate() ;
    virtual void setCursor(int width, int height, int hotspotX, int hotspotY,
                           void* cursorData, void* mask) ;
    virtual void setCursorPos(int x, int y) ;
	virtual void setName(const char* name) {};

//
//从UpdateTracker继承
//
public:
	virtual void add_changed(const rfb::Region &region);
    virtual void add_copied(const rfb::Region &dest, const rfb::Point &delta);

//
//从SMsgHandler继承
//
	virtual void setMouse(int mask, int x, int y);
	virtual void setKey(bool downFlag, int key);
	virtual void requestUpdate() { restart(); };

//
//LyvcDesktopServer的私有成员
//
private:
	//重新发送初始化信息和整个屏幕
	void restart();
	//初始化屏幕捕捉对象和消息收发对象
	void init(LyvcMessage::MatrixC* pMatrixC, DESKTOPSERVER_CALLBACK pCallback, void* pCallbackParameter);

	rfb::Point cursorTL() { return cursorPos.subtract(cursor.hotspot); }
    bool needRenderedCursor();
	//获取屏幕更新区域的数据
    void checkUpdate();
	void writeCursor();

private:
	rfb::SDesktop* desktop;	//屏幕捕捉
    bool m_bInitialized;	//初始化标志
	rfb::PixelBuffer* pb;	//存储屏幕数据
	rfb::ComparingUpdateTracker* comparer;//比较更新区域
	rfb::SimpleUpdateTracker updates;	//更新区域
	rfb::Rect renderedCursorRect;		//鼠标区域
	rfb::Point cursorPos;				//鼠标位置
    rfb::Cursor cursor;					//鼠标
    rfb::Point renderedCursorTL;		//鼠标位置
    rfb::ManagedPixelBuffer renderedCursor;//鼠标数据
	rfb::Point pointerEventPos;				//鼠标事件位置
    time_t lastEventTime;
    time_t pointerEventTime;
    std::set<rdr::U32> pressedKeys;		//键盘按键

    bool renderedCursorInvalid;
	bool removeRenderedCursor;
	bool drawRenderedCursor;;

	bool m_bNeedSendInit;//服务端显示设置改变，需要重新发送初始化信息标志
	bool m_bStarted;
	bool m_bSendingUpdate;

private:
	//发送消息对象指针
	SMsgWriter* m_pMsgWriter;
	//接收消息对象指针
	SMsgReader* m_pMsgReader;
	//不同颜色数据转换
	rfb::TransImageGetter m_transImageGetter;

	LyvcOutStream* m_os;
};
