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
#include "stdafx.h"
#include "CViewOptions.h"
#include "rfb_win32/Win32Util.h"

using namespace rfb;
using namespace win32;


static bool useLocalCursor = true;//Render the mouse cursor locally

static bool useDesktopResize = true;//Support dynamic desktop resizing

static bool fullColour = true;//Use full colour (default is to use low colour 
								//unless auto select decides the link is fast enough)
static int lowColourLevel = 1;//Colour level to use on slow connections. 0 = Very Low (8 colours), 
							//1 = Low (64 colours), 2 = Medium (256 colours)
static bool fullScreen = false;//Use the whole display to show the remote desktop.
								//(Press F8 to access the viewer menu)
static bool autoSelect = true;//Auto select pixel format and encoding

static bool sharedConnection = true;//Allow existing connections to the server to continue.

static bool sendPtrEvents = false;//Send pointer (mouse) events to the server.

static bool sendKeyEvents = false;//Send key presses (and releases) to the server.

static bool clientCutText = false;//Send clipboard changes to the server.

static bool serverCutText = false;//Accept clipboard changes from the server.

static bool protocol3_3 = false;//Only use protocol version 3.3

static int ptrEventInterval = 0;//The interval to delay between sending one pointer event and the next.

static bool emulate3 = false;//Emulate middle mouse button when left and right buttons are used simulatenously.

static bool acceptBell = false;//Produce a system beep when requested to by the server.

//static string monitor = "";//("Monitor", "The monitor to open the VNC Viewer window on, if available.", "");
//static string menuKey = "F8";//("MenuKey", "The key which brings up the popup menu", "F8");


CViewOptions::CViewOptions()
: useLocalCursor(::useLocalCursor), useDesktopResize(::useDesktopResize),
autoSelect(::autoSelect), fullColour(::fullColour), fullScreen(::fullScreen),
shared(::sharedConnection), sendPtrEvents(::sendPtrEvents), sendKeyEvents(::sendKeyEvents),
clientCutText(::clientCutText), serverCutText(::serverCutText),
protocol3_3(::protocol3_3), acceptBell(::acceptBell), lowColourLevel(::lowColourLevel),
pointerEventInterval(::ptrEventInterval), emulate3(::emulate3) //, monitor(::monitor.getData())
{
  //CharArray encodingName(::preferredEncoding.getData());
  //preferredEncoding = encodingNum(encodingName.buf);
  setMenuKey("F8");//CharArray(::menuKey.getData()).buf);
  setUserName("");
}


void CViewOptions::setUserName(const char* user) {userName.replaceBuf(strDup(user));}

void CViewOptions::setMenuKey(const char* keyName) {
  if (!keyName[0]) {
    menuKey = 0;
  } else {
    menuKey = VK_F8;
    if (keyName[0] == 'F') {
      UINT fKey = atoi(&keyName[1]);
      if (fKey >= 1 && fKey <= 12)
        menuKey = fKey-1 + VK_F1;
    }
  }
}
char* CViewOptions::menuKeyName() {
  int fNum = (menuKey-VK_F1)+1;
  if (fNum<1 || fNum>12)
    return strDup("");
  CharArray menuKeyStr(4);
  sprintf(menuKeyStr.buf, "F%d", fNum);
  return menuKeyStr.takeBuf();
}


CViewOptions& CViewOptions::operator=(const CViewOptions& o) {
  useLocalCursor = o.useLocalCursor;
  useDesktopResize = o.useDesktopResize;
  fullScreen = o.fullScreen;
  fullColour = o.fullColour;
  lowColourLevel = o.lowColourLevel;
  autoSelect = o.autoSelect;
  shared = o.shared;
  sendPtrEvents = o.sendPtrEvents;
  sendKeyEvents = o.sendKeyEvents;
  clientCutText = o.clientCutText;
  serverCutText = o.serverCutText;
  emulate3 = o.emulate3;
  pointerEventInterval = o.pointerEventInterval;
  protocol3_3 = o.protocol3_3;
  acceptBell = o.acceptBell;
  setUserName(o.userName.buf);
  menuKey = o.menuKey;
  return *this;
}