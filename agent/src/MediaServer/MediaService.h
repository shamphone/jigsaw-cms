#pragma once
#include "serviceframework/service.h"

class MediaService : public Service
{
private:

	// Stop Event.
	HANDLE m_hStopEvent;

public:

    // 服务启动方法
	virtual int start();

    // 服务终止方法
	virtual int stop();
};
