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
#include <crtdbg.h>
#include "rdr/OutStream.h"
#include "ImageGetter.h"
#include "ConnParams.h"
#include "../LyvcDesktopServer/SMsgWriter.h"
#include "ZRLEEncoder.h"

using namespace rfb;

rdr::MemOutStream* ZRLEEncoder::sharedMos = 0;
int ZRLEEncoder::maxLen = 513 * 1024; // enough for width 2048 32-bit pixels

int zlibLevel = -1;

#define EXTRA_ARGS ImageGetter* ig
#define GET_IMAGE_INTO_BUF(r,buf) ig->getImage(buf, r);
#define BPP 8
#include <rfb/zrleEncode.h>
#undef BPP
#define BPP 16
#include <rfb/zrleEncode.h>
#undef BPP
#define BPP 32
#include <rfb/zrleEncode.h>
#define CPIXEL 24A
#include <rfb/zrleEncode.h>
#undef CPIXEL
#define CPIXEL 24B
#include <rfb/zrleEncode.h>
#undef CPIXEL
#undef BPP

ZRLEEncoder* ZRLEEncoder::create(SMsgWriter* writer)
{
  return new ZRLEEncoder(writer);
}

ZRLEEncoder::ZRLEEncoder(SMsgWriter* writer_)
  : writer(writer_), zos(0,0,zlibLevel)
{
  if (sharedMos)
    mos = sharedMos;
  else
    mos = new rdr::MemOutStream(129*1024);
}

ZRLEEncoder::~ZRLEEncoder()
{
  if (!sharedMos)
    delete mos;
}

bool ZRLEEncoder::writeRect(const Rect& r, ImageGetter* ig, Rect* actual)
{
  rdr::U8* imageBuf = writer->getImageBuf(64 * 64 * 4 + 4);
  mos->clear();
  bool wroteAll = true;
  *actual = r;

  switch (writer->m_cp->pf().bpp) {
  case 8:
    wroteAll = zrleEncode8(r, mos, &zos, imageBuf, maxLen, actual, ig);
    break;
  case 16:
    wroteAll = zrleEncode16(r, mos, &zos, imageBuf, maxLen, actual, ig);
    break;
  case 32:
    {
	  const PixelFormat& pf = writer->m_cp->pf();

      bool fitsInLS3Bytes = ((pf.redMax   << pf.redShift)   < (1<<24) &&
                             (pf.greenMax << pf.greenShift) < (1<<24) &&
                             (pf.blueMax  << pf.blueShift)  < (1<<24));

      bool fitsInMS3Bytes = (pf.redShift   > 7  &&
                             pf.greenShift > 7  &&
                             pf.blueShift  > 7);

      if ((fitsInLS3Bytes && !pf.bigEndian) ||
          (fitsInMS3Bytes && pf.bigEndian))
      {
        wroteAll = zrleEncode24A(r, mos, &zos, imageBuf, maxLen, actual, ig);
      }
      else if ((fitsInLS3Bytes && pf.bigEndian) ||
               (fitsInMS3Bytes && !pf.bigEndian))
      {
        wroteAll = zrleEncode24B(r, mos, &zos, imageBuf, maxLen, actual, ig);
      }
      else
      {
        wroteAll = zrleEncode32(r, mos, &zos, imageBuf, maxLen, actual, ig);
      }
      break;
    }
  }

  rdr::OutStream* os = writer->getOutStream();
  writer->startMsg(rfb::msgTypeChangedRect);
  writer->startRect(*actual);
  os->writeU32(mos->length());
#ifdef _DEBUG 
	if( mos->length() > 102400 )
	{
		_RPTF4( _CRT_WARN, "big rect: (left=%d, top=%d, right=%d, bottom=%d)", r.tl.x, r.tl.y, r.br.x, r.br.y );
		_RPTF1( _CRT_WARN, ", data size: %d\n", mos->length() );
	}
#endif
  os->writeBytes(mos->data(), mos->length());
  writer->endMsg();
  return wroteAll;
}
