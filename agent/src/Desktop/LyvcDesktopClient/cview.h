// -=- CView.h

#ifndef __RFB_WIN32_CVIEW_H__
#define __RFB_WIN32_CVIEW_H__

#define DESKTOP_CLIENT_CLOSED WM_USER + 103

#include "rfb/Cursor.h"
#include "rfb_win32/IntervalTimer.h"
#include "rfb_win32/CPointer.h"
#include "rfb_win32/CKeyBoard.h"
#include "rfb_win32/DIBSectionBuffer.h"
#include "rfb_win32/Win32Util.h"
#include "CViewOptions.h"
#include "CViewManager.h"
#include <list>

#include "CMsgHandler.h"

namespace rfb {

  class CMsgWriter;

  namespace win32 {

    class CView : public ::CMsgHandler
    {
    public:
      CView();
      virtual ~CView();

      bool initialise();
	  void setMsgWriter(CMsgWriter* pMsgWriter) { m_pMsgWriter = pMsgWriter; };
      void setManager(CViewManager* m) {manager = m;}
	  void sendKeyMouse(bool flag = true);
      HWND getHandle() const {return hwnd;}

      // -=- Window Message handling
      virtual LRESULT processMessage(UINT msg, WPARAM wParam, LPARAM lParam);

	protected:
      // -=- Window interface
      void postQuitOnDestroy(bool qod) {quit_on_destroy = qod;}
      PixelFormat getNativePF() const;
      void setVisible(bool visible);
      void close(const char* reason=0);

      // -=- Coordinate conversions
      inline Point bufferToClient(const Point& p) {
        Point pos = p;
        if (client_size.width() > buffer->width())
          pos.x += (client_size.width() - buffer->width()) / 2;
        else if (client_size.width() < buffer->width())
          pos.x -= scrolloffset.x;
        if (client_size.height() > buffer->height())
          pos.y += (client_size.height() - buffer->height()) / 2;
        else if (client_size.height() < buffer->height())
          pos.y -= scrolloffset.y;
        return pos;
      }
      inline Rect bufferToClient(const Rect& r) {
        return Rect(bufferToClient(r.tl), bufferToClient(r.br));
      }

      inline Point clientToBuffer(const Point& p) {
        Point pos = p;
        if (client_size.width() > buffer->width())
          pos.x -= (client_size.width() - buffer->width()) / 2;
        else if (client_size.width() < buffer->width())
          pos.x += scrolloffset.x;
        if (client_size.height() > buffer->height())
          pos.y -= (client_size.height() - buffer->height()) / 2;
        else if (client_size.height() < buffer->height())
          pos.y += scrolloffset.y;
        return pos;
      }
      inline Rect clientToBuffer(const Rect& r) {
        return Rect(clientToBuffer(r.tl), clientToBuffer(r.br));
      }

	public:
//CMsgHandler 接口实现////////
      virtual void fillRect(const Rect& r, Pixel pix);
      virtual void copyRect(const Rect& r, int srcX, int srcY);
	  virtual void serverStop();
      virtual void serverInit();
      virtual void setColourMapEntries(int firstColour, int nColours, rdr::U16* rgbs);
      virtual void imageRect(const Rect& r, void* pixels);
      virtual void setCursor(const Point& hotspot, const Point& size, void* data, void* mask);
/////////////////////////////
      void setName(const char* name);

    protected:
	  rdr::U8* getTransImageBuf(int required);
      void setFullscreen(bool fs);
      bool setViewportOffset(const Point& tl);
      bool processBumpScroll(const Point& cursorPos);
      void setBumpScroll(bool on);
      void setDesktopSize(int w, int h);

      // Locally-rendered VNC cursor
      void hideLocalCursor();
      void showLocalCursor();
      void renderLocalCursor();

      // The system-rendered cursor
      void hideSystemCursor();
      void showSystemCursor();

      // cursorOutsideBuffer() is called whenever we detect that the mouse has
      // moved outside the desktop.  It restores the system arrow cursor.
      void cursorOutsideBuffer();

      // Returns true if part of the supplied rect is visible, false otherwise
      bool invalidateBufferRect(const Rect& crect);

      // Update the window palette if the display is palette-based.
      // Colours are pulled from the DIBSectionBuffer's ColourMap.
      // Only the specified range of indexes is dealt with.
      // After the update, the entire window is redrawn.
      void refreshWindowPalette(int start, int count);

      // Determine whether or not we need to enable/disable scrollbars and set the
      // window style accordingly
      void calculateScrollBars();

      // Recalculate the most suitable full-colour pixel format
      void calculateFullColourPF();

      // Enable/disable/check/uncheck the F8 menu items as appropriate.
      void updateF8Menu(bool hideSystemCommands);

      // VNCviewer options

      CViewOptions options;

      // Input handling
      void writeKeyEvent(rdr::U8 vkey, rdr::U32 flags, bool down);
      void writePointerEvent(int x, int y, int buttonMask);
      rfb::win32::CKeyboard kbd;
      rfb::win32::CPointer ptr;
      Point oldpos;

      // Pixel format and encoding
      PixelFormat serverDefaultPF;
      PixelFormat fullColourPF;
      bool formatChange;

      // Palette handling
      LogicalPalette windowPalette;
      bool palette_changed;

      // - Full-screen mode
      Rect fullScreenOldRect;
      DWORD fullScreenOldFlags;
      bool fullScreenActive;

      // Bump-scrolling (used in full-screen mode)
      bool bumpScroll;
      Point bumpScrollDelta;
      IntervalTimer bumpScrollTimer;

      // Cursor handling
      Cursor cursor;
      bool systemCursorVisible;  // Should system-cursor be drawn?
      bool trackingMouseLeave;
      bool cursorInBuffer;    // Is cursor position within server buffer? (ONLY for LocalCursor)
      bool cursorVisible;     // Is cursor currently rendered?
      bool cursorAvailable;   // Is cursor available for rendering?
      Point cursorPos;
      ManagedPixelBuffer cursorBacking;
      Rect cursorBackingRect;

      // Local window state
      win32::DIBSectionBuffer* buffer;
      bool has_focus;
      bool quit_on_destroy;
      Rect window_size;
      Rect client_size;
      Point scrolloffset;
      Point maxscrolloffset;
      HWND hwnd;

      // Handle back to CViewManager instance, if any
      CViewManager* manager;

	  CMsgWriter* m_pMsgWriter;

	  //正在观看用户的id
	  public:
		  void setServerUserId(__int64 uid) { m_serverUserId = uid; ptr.setUserId(uid); kbd.setUserId(uid); };
		  __int64 getServerUserId() { return m_serverUserId; };

    };

  };

};

#endif


