#pragma once
#include "inStream.h"

class LyvcInStream :
	public rdr::InStream
{
public:
	LyvcInStream(void);
	virtual ~LyvcInStream(void);

    int pos();
    void readBytes(void* data, int length);
	void setData(const void* data, int length);

protected:
    int overrun(int itemSize, int nItems, bool wait);

private:
    int bufSize;
	rdr::U8* start;
};
