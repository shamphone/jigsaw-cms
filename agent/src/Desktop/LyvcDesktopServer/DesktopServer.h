#pragma once

#include "../rfb/rdr/DesktopServerCallback.h"

namespace LyvcMessage
{
	class MatrixC;
};

class DesktopServer
{
public:
	virtual ~DesktopServer(void);

public:
	static DesktopServer* getInstance();

	virtual void start(
		LyvcMessage::MatrixC* pMatrixC,       // 消息系统
		DESKTOPSERVER_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter) = 0;        // Callback parameter

	virtual void stop() = 0;

	// 发送初始化信息到所有桌面客户端
	virtual void reInitialize() = 0;

protected:
	DesktopServer(void);
};
