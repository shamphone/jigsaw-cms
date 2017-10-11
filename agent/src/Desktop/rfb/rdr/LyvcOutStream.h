#pragma once
#include "OutStream.h"
#include "DesktopServerCallback.h"

class LyvcOutStream :
	public rdr::OutStream
{
public:
	LyvcOutStream(DESKTOPSERVER_CALLBACK pCallback, void* pCallbackParameter);
	virtual ~LyvcOutStream(void);

	virtual int length();
	virtual void flush();
    void writeBytes(const void* data, int length);

private:
	virtual int overrun(int itemSize, int nItems);

private:

    DESKTOPSERVER_CALLBACK m_pCallback;
	void* m_pCallbackParameter;

    int bufSize;
	rdr::U8* start;

};
