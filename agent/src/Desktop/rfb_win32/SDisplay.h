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

// -=- SDisplay.h
//
// The SDisplay class encapsulates a system display.

// *** THIS INTERFACE NEEDS TIDYING TO SEPARATE COORDINATE SYSTEMS BETTER ***

#ifndef __RFB_SDISPLAY_H__
#define __RFB_SDISPLAY_H__

#include "../rfb/SDesktop.h"
#include "../rfb/UpdateTracker.h"
#include "../rfb/util.h"

#include "Win32Util.h"
#include "DeviceFrameBuffer.h"
#include "SInput.h"
#include "MsgWindow.h"
#include "WMCursor.h"
#include "WMHooks.h"
#include "WMNotifier.h"
#include "WMWindowCopyRect.h"
#include "WMPoller.h"

namespace rfb {

  namespace win32 {

    //
    // -=- SDisplay
    //

    class SDisplayCore;

    class SDisplay : public SDesktop,
      WMMonitor::Notifier,
      UpdateTracker
    {
    public:
      SDisplay(const TCHAR* device=0);
      virtual ~SDisplay();

      // -=- SDesktop interface

      virtual void start(VNCServer* vs);
      virtual void stop();
      virtual void pointerEvent(const Point& pos, rdr::U8 buttonmask);
      virtual void keyEvent(rdr::U32 key, bool down);
      virtual Point getFbSize();

      // -=- UpdateTracker

      virtual void add_changed(const Region& rgn);
      virtual void add_copied(const Region& dest, const Point& delta);

      // -=- Display events
      
      virtual void notifyDisplayEvent(WMMonitor::Notifier::DisplayEventType evt);

      // -=- Notification of whether or not SDisplay is started

      void setStatusLocation(bool* status) {statusLocation = status;}

      void restart();

	  friend class SDisplayCore;

	  static bool use_hooks;
	  static bool disableLocalInputs;
	  static bool removeWallpaper;
	  static bool removePattern;
	  static bool disableEffects;

    protected:
      void processUpdate();
      void recreatePixelBuffer();
      bool flushChangeTracker();  // true if flushed, false if empty

      VNCServer* server;

      // -=- Display pixel buffer
      DeviceFrameBuffer* pb;
      TCharArray deviceName;
      HDC device;
      bool releaseDevice;

      // -=- The coordinates of Window's entire virtual Screen
      Rect screenRect;

      // -=- All changes are collected in Display coords and merged
      SimpleUpdateTracker change_tracker;

      // -=- Internal SDisplay implementation
      SDisplayCore* core;

      // -=- Cursor
      WMCursor::Info old_cursor;
      Region old_cursor_region;
      Point cursor_renderpos;

      // -=- Where to write the active/inactive indicator to
      bool* statusLocation;
    };

  }
}

#endif // __RFB_SDISPLAY_H__
