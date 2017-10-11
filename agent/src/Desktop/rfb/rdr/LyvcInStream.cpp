#include "LyvcInStream.h"

enum { DEFAULT_BUF_SIZE = 1024000,
       MIN_BULK_SIZE = 1024 };

using namespace rdr;

LyvcInStream::LyvcInStream(void) 
{
	bufSize = DEFAULT_BUF_SIZE;
	ptr = end = start = new U8[DEFAULT_BUF_SIZE];
}

LyvcInStream::~LyvcInStream(void)
{
	delete [] start;
}

int LyvcInStream::pos()
{
	return ptr - start;
}

void LyvcInStream::setData(const void* data, int length)
{
	memcpy(start, data, length);
	ptr = start;
	end = start + length;
}

void LyvcInStream::readBytes(void* data, int length)
{
	memcpy(data, ptr, length);
	ptr += length;
}

int LyvcInStream::overrun(int itemSize, int nItems, bool wait)
{
	return nItems;
}