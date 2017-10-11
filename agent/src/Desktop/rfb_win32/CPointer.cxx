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

#include "CPointer.h"
using namespace rfb;
using namespace win32;


CPointer::CPointer() : currButtonMask(0), intervalQueued(false), threeEmulating(false) {
}

CPointer::~CPointer() {
  intervalTimer.stop();
  threeTimer.stop();
}


void CPointer::pointerEvent(CMsgWriter* writer, int x, int y, int buttonMask) {
  //
  // - Duplicate Event Filtering
  //

  bool maskChanged = buttonMask != currButtonMask;
  bool posChanged = !Point(x, y).equals(currPos);
  if (!(posChanged || maskChanged))
    return;

  // Pass on the event to the event-interval handler
  threePointerEvent(writer, x, y, buttonMask);

  // Save the position and mask
  currPos = Point(x, y);
  currButtonMask = buttonMask;
}


//inline abs(int x) {return x>0 ? x : 0;}

int emulate3Mask(int buttonMask) {
  // - Release left & right and press middle
  buttonMask &= ~5;
  buttonMask |= 2;
  return buttonMask;
}

void CPointer::threePointerEvent(CMsgWriter* writer, int x, int y, int buttonMask) {
  //
  // - 3-Button Mouse Emulation
  //

  if (emulate3) {

    bool leftChanged = (buttonMask & 1) != (currButtonMask & 1);
    bool rightChanged = (buttonMask & 4) != (currButtonMask & 4);

    if (leftChanged || rightChanged) {
      // - One of left or right have changed

      if ((buttonMask & 5) == 1 || (buttonMask & 5) == 4) {
        // - One is up, one is down.  Start a timer, so that if it
        //   expires then we know we should actually send this event
        threeTimer.start(100);
        threePos = Point(x, y);
        threeMask = buttonMask;
        return;

      } else if (threeTimer.isActive()) {
        // - Both are up or both are down, and we were timing for an emulation event
        //   Stop the timer and flush the stored event
        threeTimer.stop();
        if (threeEmulating == ((buttonMask & 5) == 5))
          intervalPointerEvent(writer, threePos.x, threePos.y, threeMask);
        else
          threeEmulating = ((buttonMask & 5) == 5);
      }

    } else {
    
      if (threeTimer.isActive()) {
        // - We are timing for an emulation event

        if (abs(threePos.x - x) <= 4 || abs(threePos.y - y) <= 4) {
          //   If the mouse has moved too far since the button-change event then flush
          threeTimer.stop();
          intervalPointerEvent(writer, threePos.x, threePos.y, threeMask);

        } else {
          //   Otherwise, we ignore the new event
          return;
        }
      }

    }

    // - If neither left nor right are down, stop emulating
    if ((buttonMask & 5) == 0)
      threeEmulating = false;

    // - If emulating, release left & right and press middle
    if (threeEmulating)
      buttonMask = emulate3Mask(buttonMask);

  }

  // - Let the event pass through to the next stage of processing
  intervalPointerEvent(writer, x, y, buttonMask);
}

void CPointer::intervalPointerEvent(CMsgWriter* writer, int x, int y, int buttonMask) {
  //
  // - Pointer Event Interval
  //

  // Send the event immediately if we haven't sent one for a while
  bool sendNow = !intervalTimer.isActive();

  if (intervalMask != buttonMask) {
    // If the buttons have changed then flush queued events and send now
    sendNow = true;
    if (intervalQueued)
      writer->writePointerEvent(intervalPos.x, intervalPos.y, intervalMask, m_uid);
    intervalQueued = false;
  }

  if (!sendNow) {
    // If we're not sending now then just queue the event
    intervalQueued = true;
    intervalPos = Point(x, y);
    intervalMask = buttonMask;
  } else {
    // Start the interval timer if required, and send the event
    intervalQueued = false;
    intervalMask = buttonMask;
    if (pointerEventInterval)
      intervalTimer.start(pointerEventInterval);
    writer->writePointerEvent(x, y, buttonMask, m_uid);
  }
}

void CPointer::handleTimer(CMsgWriter* writer, int timerId) {
  if (timerId == intervalTimer.getId()) {
    // Pointer interval has expired - send any queued events
    if (intervalQueued) {
      writer->writePointerEvent(intervalPos.x, intervalPos.y, intervalMask, m_uid);
      intervalQueued = false;
    } else {
      intervalTimer.stop();
    }

  } else if (timerId = threeTimer.getId()) {
    // 3-Button emulation timer has expired - send what we've got
    threeTimer.stop();

    // If emulating, release left & right and press middle
    if (threeEmulating)
      threeMask = emulate3Mask(threeMask);

    intervalPointerEvent(writer, threePos.x, threePos.y, threeMask);
  }
}