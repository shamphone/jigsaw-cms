
#ifndef __RFB_MSGTYPES_H__
#define __RFB_MSGTYPES_H__

namespace rfb {
  // server to client

  const int msgTypeServerInit = 1;
  const int msgTypeChangedRect = 2;
  const int msgTypeCopiedRect = 3;
  const int msgTypeRenderCursor = 4;
  const int msgTypeFramebufferUpdate = 5;
  // client to server

  const int msgTypeFramebufferUpdateRequest = 6;
  const int msgTypeKeyEvent = 7;
  const int msgTypePointerEvent = 8;

}
#endif
