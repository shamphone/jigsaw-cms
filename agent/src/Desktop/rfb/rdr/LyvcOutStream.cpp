#include "LyvcOutStream.h"

using namespace rdr;

enum { DEFAULT_BUF_SIZE = 1024000,
       MIN_BULK_SIZE = 1024 };

LyvcOutStream::LyvcOutStream(DESKTOPSERVER_CALLBACK pCallback, void* pCallbackParameter)
{
	bufSize = DEFAULT_BUF_SIZE;

	ptr = start = new U8[DEFAULT_BUF_SIZE];
	end = start + DEFAULT_BUF_SIZE;

	m_pCallback = pCallback;
	m_pCallbackParameter = pCallbackParameter;
}

LyvcOutStream::~LyvcOutStream(void)
{
	delete [] start;
}

void LyvcOutStream::writeBytes(const void* data, int length)
{
	memcpy(ptr, data, length);
	ptr += length;
}

int LyvcOutStream::length()
{
	return ptr - start;
}

void LyvcOutStream::flush()
{
	(*(m_pCallback))(m_pCallbackParameter, (char*)start, ptr - start);
	ptr = start;
}

int LyvcOutStream::overrun(int itemSize, int nItems)
{
	return nItems;
}
