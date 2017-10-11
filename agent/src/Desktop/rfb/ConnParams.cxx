/* Copyright (C) 2002-2003 RealVNC Ltd.  All Rights Reserved.
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

#include "stdafx.h"
#include "ConnParams.h"
#include "util.h"

using namespace rfb;

ConnParams::ConnParams()
  : width(0), height(0), useCopyRect(true),
    supportsLocalCursor(true), supportsDesktopResize(true),
    name_(0)
{
  setName("");
  pf_ = PixelFormat(16,16,false,true,31,63,31,11,5,0);
}

ConnParams::~ConnParams()
{
  delete [] name_;
}

void ConnParams::setPF(const PixelFormat& pf)
{
  pf_ = pf;
}

void ConnParams::setName(const char* name)
{
  delete [] name_;
  name_ = strDup(name);
}
