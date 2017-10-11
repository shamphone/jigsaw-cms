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
//
// ConnParams - structure containing the connection parameters.
//

#ifndef __RFB_CONNPARAMS_H__
#define __RFB_CONNPARAMS_H__

#include "types.h"
#include "PixelFormat.h"

namespace rfb {

  class ConnParams {
  public:
    ConnParams();
    ~ConnParams();

    int width;
    int height;

    const PixelFormat& pf() { return pf_; }
    void setPF(const PixelFormat& pf);

    const char* name() { return name_; }
    void setName(const char* name);

    bool useCopyRect;
    bool supportsLocalCursor;
    bool supportsDesktopResize;

  private:
    PixelFormat pf_;
    char* name_;
  };
}
#endif
