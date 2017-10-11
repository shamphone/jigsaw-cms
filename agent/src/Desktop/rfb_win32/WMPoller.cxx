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

// -=- WMPoller.cxx
#include "stdafx.h"
#include "WMPoller.h"

using namespace rfb;
using namespace win32;


bool rfb::win32::WMPoller::poll_console_windows = true;

// -=- WMPoller class

rfb::win32::WMPoller::WMPoller() : clipper(0) {
}

rfb::win32::WMPoller::~WMPoller() {
  if (clipper) delete clipper;
}

bool
rfb::win32::WMPoller::processEvent() {
  PollInfo info;
  if (clipper && poll_console_windows) {
    ::EnumWindows(WMPoller::enumWindowProc, (LPARAM) &info);
    clipper->add_changed(info.poll_include);
  }
  return false;
}

bool
rfb::win32::WMPoller::setClipRect(const Rect& r) {
  clip_region = r;
  if (clipper) clipper->set_clip_region(clip_region);
  return true;
}

bool
rfb::win32::WMPoller::setUpdateTracker(UpdateTracker* ut) {
  if (clipper) delete clipper;
  clipper = new ClippedUpdateTracker(*ut);
  clipper->set_clip_region(clip_region);
  return true;
}

bool
rfb::win32::WMPoller::checkPollWindow(HWND w) {
  TCHAR buffer[128];
  if (!GetClassName(w, buffer, 128))
    printf("unable to get window class:%u", GetLastError());
  if ((_tcscmp(buffer, _T("tty")) != 0) &&
    (_tcscmp(buffer, _T("ConsoleWindowClass")) != 0)) {
    return false;
  }
  return true;
}

void
rfb::win32::WMPoller::pollWindow(HWND w, PollInfo* i) {
  RECT r;
  if (IsWindowVisible(w) && GetWindowRect(w, &r)) {
    if (IsRectEmpty(&r)) return;
    Region wrgn(Rect(r.left, r.top, r.right, r.bottom));
    if (checkPollWindow(w)) {
      wrgn.assign_subtract(i->poll_exclude);
      i->poll_include.assign_union(wrgn);
    } else {
      i->poll_exclude.assign_union(wrgn);
    }
  }
}

BOOL CALLBACK
rfb::win32::WMPoller::enumWindowProc(HWND w, LPARAM lp) {
  pollWindow(w, (PollInfo*)lp);
  return TRUE;
}