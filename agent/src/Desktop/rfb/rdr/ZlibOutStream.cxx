/* Copyright (C) 2002-2004 RealVNC Ltd.  All Rights Reserved.
 *    
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this software; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
 * USA.
 */
#include <assert.h>
#include "ZlibOutStream.h"
#include "..\..\zlib\zlib.h"

using namespace rdr;

enum { DEFAULT_BUF_SIZE = 163840 };

ZlibOutStream::ZlibOutStream(OutStream* os, int bufSize_, int compressLevel)
  : underlying(os), bufSize(bufSize_ ? bufSize_ : DEFAULT_BUF_SIZE), offset(0)
{
  zs = new z_stream;
  zs->zalloc    = Z_NULL;
  zs->zfree     = Z_NULL;
  zs->opaque    = Z_NULL;
  if (deflateInit(zs, compressLevel) != Z_OK) {
    delete zs;
  }
  ptr = start = new U8[bufSize];
  end = start + bufSize;
}

ZlibOutStream::~ZlibOutStream()
{
  flush();
  delete [] start;
  deflateEnd(zs);
  delete zs;
}

void ZlibOutStream::setUnderlying(OutStream* os)
{
  underlying = os;
}

int ZlibOutStream::length()
{
  return offset + ptr - start;
}

void ZlibOutStream::flush()
{
  zs->next_in = start;
  zs->avail_in = ptr - start;

  while (zs->avail_in != 0) {

    do {
      underlying->check(1);
      zs->next_out = underlying->getptr();
      zs->avail_out = underlying->getend() - underlying->getptr();

      int rc = deflate(zs, Z_SYNC_FLUSH);
	  if (rc != Z_OK) 
	  {
		  assert(false);
	  }
      underlying->setptr(zs->next_out);
    } while (zs->avail_out == 0);
  }

  offset += ptr - start;
  ptr = start;
}

int ZlibOutStream::overrun(int itemSize, int nItems)
{
	return nItems;
}
