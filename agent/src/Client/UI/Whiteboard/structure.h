#pragma once

#include "..\..\..\Desktop\rfb\rdr\outstream.h"
#include "..\..\..\Desktop\rfb\rdr\instream.h"

#pragma warning( disable : 4244 )

class CWhiteboardDlg;

class WhiteboardOutStream :	public rdr::OutStream
{
public:
	WhiteboardOutStream(CWhiteboardDlg* pDlg);
	virtual ~WhiteboardOutStream(void);
	virtual void flush();
	void setPos( int pos ) { ptr = start + pos; };
	CWhiteboardDlg* m_pWhiteboardDlg;

private:
	virtual int length() { return ptr - start; };
	virtual int overrun(int itemSize, int nItems);

    int bufSize;
	rdr::U8* start;

};

class WhiteboardInStream :	public rdr::InStream
{
public:
	WhiteboardInStream(void);
	virtual ~WhiteboardInStream(void);
	void setData(const void* data, int length);

private:
	int pos() { return ptr - start; };
    int overrun(int itemSize, int nItems, bool wait);

    int bufSize;
	rdr::U8* start;
};
