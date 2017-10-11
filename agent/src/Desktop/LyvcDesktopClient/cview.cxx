#include "stdafx.h"

#include "CView.h"
#include "rfb_win32/WMShatter.h"
#include "CLyvcMsgWriter.h"
#include "../../Common/Common/PathHelper/PathHelper.h"
using namespace rfb;
using namespace win32;
using namespace rdr;

// - Statics & consts

const int IDM_FULLSCREEN = 1;
const int IDM_SEND_MENU_KEY = 2;
const int IDM_SEND_CAD = 3;
const int IDM_ABOUT = 4;
const int IDM_OPTIONS = 5;
const int IDM_INFO = 6;
const int IDM_NEWCONN = 7;
const int IDM_REQUEST_REFRESH = 9;
const int IDM_CTRL_KEY = 10;
const int IDM_ALT_KEY = 11;

const int TIMER_BUMPSCROLL = 1;
const int TIMER_POINTER_INTERVAL = 2;
const int TIMER_POINTER_3BUTTON = 3;

//
// -=- CViewClass

//
// Window class used as the basis for all CView instances
//

class CViewClass {
public:
  CViewClass();
  ~CViewClass();
  ATOM classAtom;
  HINSTANCE instance;
};

LRESULT CALLBACK CViewProc(HWND wnd, UINT msg, WPARAM wParam, LPARAM lParam) {
  LRESULT result;

  if (msg == WM_CREATE)
    SetWindowLong(wnd, GWL_USERDATA, (long)((CREATESTRUCT*)lParam)->lpCreateParams);
  else if (msg == WM_DESTROY)
    SetWindowLong(wnd, GWL_USERDATA, 0);
  CView* _this = (CView*) GetWindowLong(wnd, GWL_USERDATA);
  if (!_this) {
    return rfb::win32::SafeDefWindowProc(wnd, msg, wParam, lParam);
  }

  result = _this->processMessage(msg, wParam, lParam);

  return result;
};

HCURSOR dotCursor = (HCURSOR)LoadImage(NULL, IDC_ARROW, IMAGE_CURSOR, 0, 0, LR_SHARED); 
//(HCURSOR)LoadImage(GetModuleHandle(0), MAKEINTRESOURCE(IDC_DOT_CURSOR), IMAGE_CURSOR, 0, 0, LR_SHARED);
HCURSOR arrowCursor = (HCURSOR)LoadImage(NULL, IDC_ARROW, IMAGE_CURSOR, 0, 0, LR_SHARED); 

CViewClass::CViewClass() : classAtom(0) {
  WNDCLASS wndClass;
  wndClass.style = 0;
  wndClass.lpfnWndProc = CViewProc;
  wndClass.cbClsExtra = 0;
  wndClass.cbWndExtra = 0;
  wndClass.hInstance = instance = GetModuleHandle(0);
  wndClass.hIcon = NULL;//(HICON)LoadImage(GetModuleHandle(0), MAKEINTRESOURCE(IDI_ICON), IMAGE_ICON, 0, 0, LR_SHARED);
  wndClass.hCursor = NULL;
  wndClass.hbrBackground = NULL;
  wndClass.lpszMenuName = 0;
  wndClass.lpszClassName = _T("rfb::win32::CViewClass");
  classAtom = RegisterClass(&wndClass);
}

CViewClass::~CViewClass() {
  if (classAtom) {
    UnregisterClass((const TCHAR*)classAtom, instance);
  }
}

CViewClass baseClass;


//
// -=- CView instance implementation
//


CView::CView() 
  : quit_on_destroy(false), buffer(0),
    client_size(0, 0, 16, 16), window_size(0, 0, 32, 32),
    cursorVisible(false), cursorAvailable(false), cursorInBuffer(false),
    systemCursorVisible(true), trackingMouseLeave(false),
    hwnd(0), has_focus(false), palette_changed(false),
    formatChange(false),
    fullScreenActive(false),
    bumpScroll(false), manager(0) {

  // Create the window
  const TCHAR* name = _T("LyvcDesktopClientTest");
  hwnd = CreateWindow((const TCHAR*)baseClass.classAtom, name, WS_OVERLAPPEDWINDOW,
    0, 0, 30, 30, 0, 0, baseClass.instance, this);

  // Initialise the CPointer pointer handler
  ptr.setHWND(getHandle());
  ptr.setIntervalTimerId(TIMER_POINTER_INTERVAL);
  ptr.set3ButtonTimerId(TIMER_POINTER_3BUTTON);
  ptr.enableEmulate3(options.emulate3);
  ptr.enableInterval(options.pointerEventInterval);

  // Initialise the bumpscroll timer
  bumpScrollTimer.setHWND(getHandle());
  bumpScrollTimer.setId(TIMER_BUMPSCROLL);

  // Create the backing buffer
  buffer = new win32::DIBSectionBuffer(getHandle());
  m_bInitialized = false;
}

CView::~CView() {
  showSystemCursor();
  if (hwnd) {
    setVisible(false);
    DestroyWindow(hwnd);
    hwnd = 0;
  }
  delete buffer;

  m_pMsgWriter = 0;
}

bool CView::initialise() 
{
	// Update the window menu
	HMENU wndmenu = GetSystemMenu(hwnd, FALSE);
	AppendMenu(wndmenu, MF_SEPARATOR, 0, 0);
	AppendMenu(wndmenu, MF_STRING, IDM_FULLSCREEN, _T("全屏显示"));
	//AppendMenu(wndmenu, MF_SEPARATOR, 0, 0);
	//AppendMenu(wndmenu, MF_STRING, IDM_CTRL_KEY, _T("Ctr&l"));
	//AppendMenu(wndmenu, MF_STRING, IDM_ALT_KEY, _T("Al&t"));
	//AppendMenu(wndmenu, MF_STRING, IDM_SEND_CAD, _T("Send Ctrl-Alt-&Del"));
	//AppendMenu(wndmenu, MF_STRING, IDM_REQUEST_REFRESH, _T("Refres&h Screen"));
	//AppendMenu(wndmenu, MF_SEPARATOR, 0, 0);
	//if (manager) AppendMenu(wndmenu, MF_STRING, IDM_NEWCONN, _T("Ne&w Connection..."));
	//AppendMenu(wndmenu, MF_STRING, IDM_OPTIONS, _T("&Options..."));
	//AppendMenu(wndmenu, MF_STRING, IDM_INFO, _T("Connection &Info..."));
	//AppendMenu(wndmenu, MF_STRING, IDM_ABOUT, _T("&About..."));

	return true;
}

void CView::setFullscreen(bool fs) {
  // Set the menu fullscreen option tick
  CheckMenuItem(GetSystemMenu(getHandle(), FALSE), IDM_FULLSCREEN,
    (options.fullScreen ? MF_CHECKED : 0) | MF_BYCOMMAND);

  // If the window is not visible then we ignore the request.
  // setVisible() will call us to correct the full-screen state when
  // the window is visible, to keep things consistent.
  if (!IsWindowVisible(getHandle()))
    return;

  if (fs && !fullScreenActive) {
    fullScreenActive = bumpScroll = true;

    // Un-minimize the window if required
    if (GetWindowLong(getHandle(), GWL_STYLE) & WS_MINIMIZE)
      ShowWindow(getHandle(), SW_RESTORE);

    // Save the non-fullscreen window position
    RECT wrect;
    GetWindowRect(getHandle(), &wrect);
    fullScreenOldRect = Rect(wrect.left, wrect.top, wrect.right, wrect.bottom);

    // Find the size of the display the window is on
    MonitorInfo mi(getHandle());

    // Set the window full-screen
    DWORD flags = GetWindowLong(getHandle(), GWL_STYLE);
    fullScreenOldFlags = flags;
    flags = flags & ~(WS_CAPTION | WS_THICKFRAME | WS_MAXIMIZE | WS_MINIMIZE);
    //vlog.debug("flags=%x", flags);

    SetWindowLong(getHandle(), GWL_STYLE, flags);
    SetWindowPos(getHandle(), HWND_TOP, mi.rcMonitor.left, mi.rcMonitor.top,
      mi.rcMonitor.right-mi.rcMonitor.left,
      mi.rcMonitor.bottom-mi.rcMonitor.top,
      SWP_FRAMECHANGED);
  } else if (!fs && fullScreenActive) {
    fullScreenActive = bumpScroll = false;

    // Set the window non-fullscreen
    SetWindowLong(getHandle(), GWL_STYLE, fullScreenOldFlags);
    SetWindowPos(getHandle(), HWND_NOTOPMOST,
      fullScreenOldRect.tl.x, fullScreenOldRect.tl.y,
      fullScreenOldRect.width(), fullScreenOldRect.height(),
      SWP_FRAMECHANGED);
  }

  // Adjust the viewport offset to cope with change in size between FS
  // and previous window state.
  setViewportOffset(scrolloffset);
}


bool CView::setViewportOffset(const Point& tl) {
/* ***
  Point np = Point(max(0, min(maxscrolloffset.x, tl.x)),
    max(0, min(maxscrolloffset.y, tl.y)));
    */
  Point np = Point(max(0, min(tl.x, buffer->width()-client_size.width())),
    max(0, min(tl.y, buffer->height()-client_size.height())));
  Point delta = np.translate(scrolloffset.negate());
  if (!np.equals(scrolloffset)) {
    scrolloffset = np;
    ScrollWindowEx(getHandle(), -delta.x, -delta.y, 0, 0, 0, 0, SW_INVALIDATE);
    UpdateWindow(getHandle());
    return true;
  }
  return false;
}


bool CView::processBumpScroll(const Point& pos)
{
  if (!bumpScroll) return false;
  int bumpScrollPixels = 20;
  bumpScrollDelta = Point();

  if (pos.x == client_size.width()-1)
    bumpScrollDelta.x = bumpScrollPixels;
  else if (pos.x == 0)
    bumpScrollDelta.x = -bumpScrollPixels;
  if (pos.y == client_size.height()-1)
    bumpScrollDelta.y = bumpScrollPixels;
  else if (pos.y == 0)
    bumpScrollDelta.y = -bumpScrollPixels;

  if (bumpScrollDelta.x || bumpScrollDelta.y) {
    if (bumpScrollTimer.isActive()) return true;
    if (setViewportOffset(scrolloffset.translate(bumpScrollDelta))) {
      bumpScrollTimer.start(25);
      return true;
    }
  }

  bumpScrollTimer.stop();
  return false;

}


LRESULT
CView::processMessage(UINT msg, WPARAM wParam, LPARAM lParam) {
  switch (msg) {

    // -=- Process standard window messages

  case WM_DISPLAYCHANGE:
    // Display has changed - use new pixel format
    calculateFullColourPF();
    break;

  case WM_PAINT:
    {
      PAINTSTRUCT ps;
      HDC paintDC = BeginPaint(getHandle(), &ps);
      if (!paintDC)
		return 0;//throw SystemException("unable to BeginPaint", GetLastError());
      Rect pr = Rect(ps.rcPaint.left, ps.rcPaint.top, ps.rcPaint.right, ps.rcPaint.bottom);

      if (!pr.is_empty()) {

        // Draw using the correct palette
        PaletteSelector pSel(paintDC, windowPalette.getHandle());

        if (buffer->bitmap) {
          // Update the bitmap's palette
          if (palette_changed) {
            palette_changed = false;
            buffer->refreshPalette();
          }

          // Get device context
          BitmapDC bitmapDC(paintDC, buffer->bitmap);

          // Blit the border if required
          Rect bufpos = bufferToClient(buffer->getRect());
          if (!pr.enclosed_by(bufpos)) {
            //vlog.debug("draw border");
            HBRUSH black = (HBRUSH) GetStockObject(BLACK_BRUSH);
            RECT r;
            SetRect(&r, 0, 0, bufpos.tl.x, client_size.height()); FillRect(paintDC, &r, black);
            SetRect(&r, bufpos.tl.x, 0, bufpos.br.x, bufpos.tl.y); FillRect(paintDC, &r, black);
            SetRect(&r, bufpos.br.x, 0, client_size.width(), client_size.height()); FillRect(paintDC, &r, black);
            SetRect(&r, bufpos.tl.x, bufpos.br.y, bufpos.br.x, client_size.height()); FillRect(paintDC, &r, black);
          }

          // Do the blit
          Point buf_pos = clientToBuffer(pr.tl);
          if (!BitBlt(paintDC, pr.tl.x, pr.tl.y, pr.width(), pr.height(),
            bitmapDC, buf_pos.x, buf_pos.y, SRCCOPY))
		  {}//throw SystemException("unable to BitBlt to window", GetLastError());

        } else {
          // Blit a load of black
          if (!BitBlt(paintDC, pr.tl.x, pr.tl.y, pr.width(), pr.height(),
            0, 0, 0, BLACKNESS))
		  {}//throw SystemException("unable to BitBlt to blank window", GetLastError());
        }
      }

      EndPaint(getHandle(), &ps);

    }
    return 0;

    // -=- Palette management

  case WM_PALETTECHANGED:
    //vlog.debug("WM_PALETTECHANGED");
    if ((HWND)wParam == getHandle()) {
      //vlog.debug("ignoring");
      break;
    }
  case WM_QUERYNEWPALETTE:
    //vlog.debug("re-selecting palette");
    {
      WindowDC wdc(getHandle());
      PaletteSelector pSel(wdc, windowPalette.getHandle());
      if (pSel.isRedrawRequired()) {
        InvalidateRect(getHandle(), 0, FALSE);
        UpdateWindow(getHandle());
      }
    }
    return TRUE;

    // -=- Window position

    // Prevent the window from being resized to be too large if in normal mode.
    // If maximized or fullscreen the allow oversized windows.

  case WM_WINDOWPOSCHANGING:
    {
      WINDOWPOS* wpos = (WINDOWPOS*)lParam;
      if (wpos->flags &  SWP_NOSIZE)
        break;

      // Work out how big the window should ideally be
      DWORD current_style = GetWindowLong(getHandle(), GWL_STYLE);
      DWORD style = current_style & ~(WS_VSCROLL | WS_HSCROLL);
      RECT r;
      SetRect(&r, 0, 0, buffer->width(), buffer->height());
      AdjustWindowRect(&r, style, FALSE);
      Rect reqd_size = Rect(r.left, r.top, r.right, r.bottom);
      if (current_style & WS_VSCROLL)
        reqd_size.br.x += GetSystemMetrics(SM_CXVSCROLL);
      if (current_style & WS_HSCROLL)
        reqd_size.br.y += GetSystemMetrics(SM_CXHSCROLL);
      RECT current;
      GetWindowRect(getHandle(), &current);

      // Ensure that the window isn't resized too large
      // If the window is maximized or full-screen then any size is allowed
      if (!(GetWindowLong(getHandle(), GWL_STYLE) & WS_MAXIMIZE) && !fullScreenActive) {
        if (wpos->cx > reqd_size.width()) {
          wpos->cx = reqd_size.width();
          wpos->x = current.left;
        }
        if (wpos->cy > reqd_size.height()) {
          wpos->cy = reqd_size.height();
          wpos->y = current.top;
        }
      }

    }
    break;

    // Add scrollbars if required and update window size info we have cached.

  case WM_SIZE:
    {
      Point old_offset = bufferToClient(Point(0, 0));

      // Update the cached sizing information
      RECT r;
      GetWindowRect(getHandle(), &r);
      window_size = Rect(r.left, r.top, r.right, r.bottom);
      GetClientRect(getHandle(), &r);
      client_size = Rect(r.left, r.top, r.right, r.bottom);

      // Determine whether scrollbars are required
      calculateScrollBars();

      // Redraw if required
      if (!old_offset.equals(bufferToClient(Point(0, 0))))
        InvalidateRect(getHandle(), 0, TRUE);
    }
    break;

  case WM_VSCROLL:
  case WM_HSCROLL: 
    {
      Point delta;
      int newpos = (msg == WM_VSCROLL) ? scrolloffset.y : scrolloffset.x;

      switch (LOWORD(wParam)) {
      case SB_PAGEUP: newpos -= 50; break;
      case SB_PAGEDOWN: newpos += 50; break;
      case SB_LINEUP: newpos -= 5; break;
      case SB_LINEDOWN: newpos += 5; break;
      case SB_THUMBTRACK:
      case SB_THUMBPOSITION: newpos = HIWORD(wParam); break;
      default: break;//vlog.info("received unknown scroll message");
      };

      if (msg == WM_HSCROLL)
        setViewportOffset(Point(newpos, scrolloffset.y));
      else
        setViewportOffset(Point(scrolloffset.x, newpos));
  
      SCROLLINFO si;
      si.cbSize = sizeof(si); 
      si.fMask  = SIF_POS; 
      si.nPos   = newpos; 
      SetScrollInfo(getHandle(), (msg == WM_VSCROLL) ? SB_VERT : SB_HORZ, &si, TRUE); 
    }
    break;

    // -=- Bump-scrolling

  case WM_TIMER:
    switch (wParam) {
    case TIMER_BUMPSCROLL:
      if (!setViewportOffset(scrolloffset.translate(bumpScrollDelta)))
        bumpScrollTimer.stop();
      break;
    case TIMER_POINTER_INTERVAL:
    case TIMER_POINTER_3BUTTON:
      ptr.handleTimer(m_pMsgWriter, wParam);
      break;
    }
    break;

    // -=- Cursor shape/visibility handling

  case WM_SETCURSOR:
    if (LOWORD(lParam) != HTCLIENT)
      break;
    SetCursor(cursorInBuffer ? dotCursor : arrowCursor);
    return TRUE;

  case WM_MOUSELEAVE:
    trackingMouseLeave = false;
    cursorOutsideBuffer();
    return 0;

    // -=- Mouse input handling

  case WM_MOUSEMOVE:
  case WM_LBUTTONUP:
  case WM_MBUTTONUP:
  case WM_RBUTTONUP:
  case WM_LBUTTONDOWN:
  case WM_MBUTTONDOWN:
  case WM_RBUTTONDOWN:
  case WM_MOUSEWHEEL:
    if (has_focus)
    {
      if (!trackingMouseLeave) {
        TRACKMOUSEEVENT tme;
        tme.cbSize = sizeof(TRACKMOUSEEVENT);
        tme.dwFlags = TME_LEAVE;
        tme.hwndTrack = hwnd;
        _TrackMouseEvent(&tme);
        trackingMouseLeave = true;
      }
      int mask = 0;
      if (LOWORD(wParam) & MK_LBUTTON) mask |= 1;
      if (LOWORD(wParam) & MK_MBUTTON) mask |= 2;
      if (LOWORD(wParam) & MK_RBUTTON) mask |= 4;

      if (msg == WM_MOUSEWHEEL) {
        int delta = (short)HIWORD(wParam);
        int repeats = (abs(delta)+119) / 120;
        int wheelMask = (delta > 0) ? 8 : 16;
        //vlog.debug("repeats %d, mask %d\n",repeats,wheelMask);
        for (int i=0; i<repeats; i++) {
          writePointerEvent(oldpos.x, oldpos.y, mask | wheelMask);
          writePointerEvent(oldpos.x, oldpos.y, mask);
        }
      } else {
        Point clientPos = Point(LOWORD(lParam), HIWORD(lParam));
        Point p = clientToBuffer(clientPos);

        // If the mouse is not within the server buffer area, do nothing
        cursorInBuffer = buffer->getRect().contains(p);
        if (!cursorInBuffer) {
          cursorOutsideBuffer();
          break;
        }

        // If we're locally rendering the cursor then redraw it
        if (cursorAvailable) {
          // - Render the cursor!
          if (!p.equals(cursorPos)) {
            hideLocalCursor();
            cursorPos = p;
            showLocalCursor();
            if (cursorVisible)
              hideSystemCursor();
          }
        }

        // If we are doing bump-scrolling then try that first...
        if (processBumpScroll(clientPos))
          break;

        // Send a pointer event to the server
        writePointerEvent(p.x, p.y, mask);
        oldpos = p;
      }
    } else {
      cursorOutsideBuffer();
    }
    break;

    // -=- Track whether or not the window has focus

  case WM_SETFOCUS:
    has_focus = true;
    break;
  case WM_KILLFOCUS:
    has_focus = false;
    cursorOutsideBuffer();
    // Restore the remote keys to consistent states
    kbd.releaseAllKeys(m_pMsgWriter);
    break;

    // -=- Handle the extra window menu items

    // Process the items added to the system menu
  case WM_SYSCOMMAND:

    // - First check whether it's one of our messages
    switch (wParam) {
    case IDM_FULLSCREEN:
      options.fullScreen = !options.fullScreen;
      setFullscreen(options.fullScreen);
      return 0;
    case IDM_CTRL_KEY:
      writeKeyEvent(VK_CONTROL, 0, !kbd.keyPressed(VK_CONTROL));
      return 0;
    case IDM_ALT_KEY:
      writeKeyEvent(VK_MENU, 0, !kbd.keyPressed(VK_MENU));
      return 0;
    case IDM_SEND_MENU_KEY:
      writeKeyEvent(options.menuKey, 0, true);
      writeKeyEvent(options.menuKey, 0, false);
      return 0;
    case IDM_SEND_CAD:
      writeKeyEvent(VK_CONTROL, 0, true);
      writeKeyEvent(VK_MENU, 0, true);
      writeKeyEvent(VK_DELETE, 0, true);
      writeKeyEvent(VK_DELETE, 0, false);
      writeKeyEvent(VK_MENU, 0, false);
      writeKeyEvent(VK_CONTROL, 0, false);
      return 0;
    case IDM_REQUEST_REFRESH:
      //writer()->writeFramebufferUpdateRequest(Rect(0,0,cp.width,cp.height), false);
      return 0;
/*    case IDM_NEWCONN:
      manager->addClient();
      return 0;
    case IDM_OPTIONS:
      // Update the monitor device name in the CViewOptions instance
      {
        MonitorInfo mi(getHandle());
        options.setMonitor(mi.szDevice);
        optionsDialog.showDialog(this);
        return 0;
      }
    case IDM_INFO:
      infoDialog.showDialog(this);
      return 0;
    case IDM_ABOUT:
      AboutDialog::instance.showDialog();
      return 0;
*/    };

    // - Not one of our messages, so process it as a system message
    switch (wParam & 0xfff0) {

      // When restored, ensure that full-screen mode is re-enabled if required.
    case SC_RESTORE:
      rfb::win32::SafeDefWindowProc(getHandle(), msg, wParam, lParam);
      setFullscreen(options.fullScreen);
      return 0;

      // If we are maximized or minimized then that cancels full-screen mode.
    case SC_MINIMIZE:
    case SC_MAXIMIZE:
      setFullscreen(false);
      break;

      // If the system menu is shown then make sure it's up to date
    case SC_KEYMENU:
    case SC_MOUSEMENU:
      updateF8Menu(false);
      break;

    };
    break;

    // Treat all menu commands as system menu commands
  case WM_COMMAND:
    SendMessage(getHandle(), WM_SYSCOMMAND, wParam, lParam);
    return 0;

  case WM_MENUCHAR:
    //vlog.debug("menuchar");
    break;

    // -=- Handle keyboard input

  case WM_KEYUP:
  case WM_KEYDOWN:
    // Hook the MenuKey to pop-up the window menu
	//如果在全屏模式下按下esc键，结束全屏显示，如果正在控制，不发送
	if (options.fullScreen )
	{
		int n = ::GetPrivateProfileInt("OPTION", "key_quit_fullscreen_desktop", 0x00010000, PathHelper::getIniFileFullName());
		if( n & 0x00000001 )
		{
			SHORT state = ::GetKeyState( VK_CONTROL ) ;
			if( !(state & 0x8000) )
				return 0;
		}
		if( n & 0x00000010 )
		{
			SHORT state = ::GetKeyState( VK_SHIFT ) ;
			if( !(state & 0x8000) )
				return 0;
		}
		n = n >> 16;
		n = n << 8;
		int n1 = lParam >> 16;
		n1 = n1 << 8;
		if( n != n1 )
			return 0;

	  options.fullScreen = false;
	  setFullscreen(false);
	  return 0;
	}

    if (options.menuKey && (wParam == options.menuKey)) {

      bool ctrlDown = (GetAsyncKeyState(VK_CONTROL) & 0x8000) != 0;
      bool altDown = (GetAsyncKeyState(VK_MENU) & 0x8000) != 0;
      bool shiftDown = (GetAsyncKeyState(VK_SHIFT) & 0x8000) != 0;
      if (!(ctrlDown || altDown || shiftDown)) {

        // If MenuKey is being released then pop-up the menu
        if ((msg == WM_KEYDOWN)) {
          // Make sure it's up to date
          updateF8Menu(true);

          // Show it under the pointer
          POINT pt;
          GetCursorPos(&pt);
          cursorInBuffer = false;
          TrackPopupMenu(GetSystemMenu(getHandle(), FALSE),
            TPM_CENTERALIGN | TPM_VCENTERALIGN, pt.x, pt.y, 0, getHandle(), 0);
        }

        // Ignore the MenuKey keypress for both press & release events
        return 0;
      }
    }
	case WM_SYSKEYDOWN:
	case WM_SYSKEYUP:
    writeKeyEvent(wParam, lParam, (msg == WM_KEYDOWN) || (msg == WM_SYSKEYDOWN));
    return 0;

    // -=- Handle the window closing

  case WM_CLOSE:
	  __int64* uid = new __int64(m_serverUserId);
	  ::PostMessage(manager->getHandle(), DESKTOP_CLIENT_CLOSED, (WPARAM)uid, 0);
	  break;
  }

  return rfb::win32::SafeDefWindowProc(getHandle(), msg, wParam, lParam);
}

void
CView::hideLocalCursor() {
  // - Blit the cursor backing store over the cursor
  // *** ALWAYS call this BEFORE changing buffer PF!!!
  if (cursorVisible) {
    cursorVisible = false;
    buffer->imageRect(cursorBackingRect, cursorBacking.data);
    invalidateBufferRect(cursorBackingRect);
  }
}

void
CView::showLocalCursor() {
  if (cursorAvailable && !cursorVisible && cursorInBuffer) {
    if (!cp.pf().equal(cursor.getPF()) ||
      cursor.getRect().is_empty()) {
      cursorAvailable = false;
      showSystemCursor();
      return;
    }
    cursorVisible = true;
    
    cursorBackingRect = cursor.getRect().translate(cursorPos).translate(cursor.hotspot.negate());
    cursorBackingRect = cursorBackingRect.intersect(buffer->getRect());
    buffer->getImage(cursorBacking.data, cursorBackingRect);

    renderLocalCursor();

    invalidateBufferRect(cursorBackingRect);
  }
}

void CView::cursorOutsideBuffer()
{
  cursorInBuffer = false;
  hideLocalCursor();
  showSystemCursor();
}

void
CView::renderLocalCursor()
{
  Rect r = cursor.getRect();
  r = r.translate(cursorPos).translate(cursor.hotspot.negate());
  buffer->maskRect(r, cursor.data, cursor.mask.buf);
}

void
CView::hideSystemCursor() {
  if (systemCursorVisible) {
    systemCursorVisible = false;
    ShowCursor(FALSE);
  }
}

void
CView::showSystemCursor() {
  if (!systemCursorVisible) {
    systemCursorVisible = true;
    ShowCursor(TRUE);
  }
}


bool
CView::invalidateBufferRect(const Rect& crect) {
  Rect rect = bufferToClient(crect);
  if (rect.intersect(client_size).is_empty()) return false;
  RECT invalid = {rect.tl.x, rect.tl.y, rect.br.x, rect.br.y};
  InvalidateRect(getHandle(), &invalid, FALSE);
  return true;
}

void
CView::setColourMapEntries(int first, int count, U16* rgbs) {
  int i;
  for (i=0;i<count;i++) {
    buffer->setColour(i+first, rgbs[i*3], rgbs[i*3+1], rgbs[i*3+2]);
  }
  // *** change to 0, 256?
  refreshWindowPalette(first, count);
  palette_changed = true;
  InvalidateRect(getHandle(), 0, FALSE);
}

void
CView::setDesktopSize(int w, int h) {

  // If the locally-rendered cursor is visible then remove it
  hideLocalCursor();

  // Resize the backing buffer
  buffer->setSize(w, h);

  // If the window is not maximised or full-screen then resize it
  if (!(GetWindowLong(getHandle(), GWL_STYLE) & WS_MAXIMIZE) && !fullScreenActive) {
    // Resize the window to the required size
    RECT r = {0, 0, w, h};
    AdjustWindowRect(&r, GetWindowLong(getHandle(), GWL_STYLE), FALSE);
    SetWindowPos(getHandle(), 0, 0, 0, r.right-r.left, r.bottom-r.top,
      SWP_NOZORDER | SWP_NOMOVE | SWP_NOACTIVATE | SWP_NOOWNERZORDER);
    centerWindow(getHandle(), 0, true);
  } else {
    // Ensure the screen contents are consistent
    InvalidateRect(getHandle(), 0, FALSE);
  }

  // Enable/disable scrollbars as appropriate
  calculateScrollBars();

}

void
CView::setCursor(const Point& hotspot, const Point& size, void* data, void* mask) {
  if (!options.useLocalCursor) return;
  hideLocalCursor();

  cursor.hotspot = hotspot;

  cursor.setSize(size.x, size.y);
  cursor.setPF(cp.pf());
  cursor.imageRect(cursor.getRect(), data);
  memcpy(cursor.mask.buf, mask, cursor.maskLen());
  cursor.crop();

  cursorBacking.setSize(size.x, size.y);
  cursorBacking.setPF(cp.pf());

  cursorAvailable = true;

  showLocalCursor();

}

PixelFormat
CView::getNativePF() const {
  return WindowDC(getHandle()).getPF();
}

void
CView::setVisible(bool visible) {
  ShowWindow(getHandle(), visible ? SW_SHOW : SW_HIDE);
  if (visible) {
    // When the window becomes visible, make it active
    SetForegroundWindow(getHandle());
    SetActiveWindow(getHandle());
    // If the window should be full-screen, then do so
    setFullscreen(options.fullScreen);
  } else {
    // Disable full-screen mode
    setFullscreen(false);
  }
}

void CView::close(const char* reason) 
{
	setVisible(false);
	SendMessage(getHandle(), WM_CLOSE, 0, 0);
}

void CView::writeKeyEvent(rdr::U8 vkey, rdr::U32 flags, bool down) 
{
	if (!options.sendKeyEvents) return;
	kbd.keyEvent(m_pMsgWriter, vkey, flags, down);
}

void CView::writePointerEvent(int x, int y, int buttonMask) 
{
	if (!options.sendPtrEvents) return;
	ptr.pointerEvent(m_pMsgWriter, x, y, buttonMask);
}

void
CView::refreshWindowPalette(int start, int count) {
  Colour colours[256];
  if (count > 256) {
    return;//throw rdr::Exception("too many palette entries");
  }

  // Copy the palette from the DIBSectionBuffer
  ColourMap* cm = buffer->getColourMap();
  if (!cm) return;
  for (int i=0; i<count; i++) {
    int r, g, b;
    cm->lookup(i, &r, &g, &b);
    colours[i].r = r;
    colours[i].g = g;
    colours[i].b = b;
  }

  // Set the window palette
  windowPalette.setEntries(start, count, colours);

  // Cause the window to be redrawn
  InvalidateRect(getHandle(), 0, 0);
}

void CView::calculateScrollBars() {
  // Calculate the required size of window
  DWORD current_style = GetWindowLong(getHandle(), GWL_STYLE);
  DWORD style = current_style & ~(WS_VSCROLL | WS_HSCROLL);
  DWORD old_style;
  RECT r;
  SetRect(&r, 0, 0, buffer->width(), buffer->height());
  AdjustWindowRect(&r, style, FALSE);
  Rect reqd_size = Rect(r.left, r.top, r.right, r.bottom);

  if (!bumpScroll) {
    // We only enable scrollbars if bump-scrolling is not active.
    // Effectively, this means if full-screen is not active,
    // but I think it's better to make these things explicit.
    
    // Work out whether scroll bars are required
    do {
      old_style = style;

      if (!(style & WS_HSCROLL) && (reqd_size.width() > window_size.width())) {
        style |= WS_HSCROLL;
        reqd_size.br.y += GetSystemMetrics(SM_CXHSCROLL);
      }
      if (!(style & WS_VSCROLL) && (reqd_size.height() > window_size.height())) {
        style |= WS_VSCROLL;
        reqd_size.br.x += GetSystemMetrics(SM_CXVSCROLL);
      }
    } while (style != old_style);
  }

  // Tell Windows to update the window style & cached settings
  if (style != current_style) {
    SetWindowLong(getHandle(), GWL_STYLE, style);
    SetWindowPos(getHandle(), NULL, 0, 0, 0, 0, SWP_NOMOVE | SWP_NOSIZE | SWP_NOZORDER | SWP_FRAMECHANGED);
  }

  // Update the scroll settings
  SCROLLINFO si;
  if (style & WS_VSCROLL) {
    si.cbSize = sizeof(si); 
    si.fMask  = SIF_RANGE | SIF_PAGE | SIF_POS; 
    si.nMin   = 0; 
    si.nMax   = buffer->height(); 
    si.nPage  = buffer->height() - (reqd_size.height() - window_size.height()); 
    maxscrolloffset.y = max(0, si.nMax-si.nPage);
    scrolloffset.y = min(maxscrolloffset.y, scrolloffset.y);
    si.nPos   = scrolloffset.y;
    SetScrollInfo(getHandle(), SB_VERT, &si, TRUE);
  }
  if (style & WS_HSCROLL) {
    si.cbSize = sizeof(si); 
    si.fMask  = SIF_RANGE | SIF_PAGE | SIF_POS; 
    si.nMin   = 0;
    si.nMax   = buffer->width(); 
    si.nPage  = buffer->width() - (reqd_size.width() - window_size.width()); 
    maxscrolloffset.x = max(0, si.nMax-si.nPage);
    scrolloffset.x = min(maxscrolloffset.x, scrolloffset.x);
    si.nPos   = scrolloffset.x;
    SetScrollInfo(getHandle(), SB_HORZ, &si, TRUE);
  }
}


void
CView::calculateFullColourPF() {
  // If the server is palette based then use palette locally
  // Also, don't bother doing bgr222
  if (!serverDefaultPF.trueColour || (serverDefaultPF.depth < 6)) {
    fullColourPF = serverDefaultPF;
	m_bNeedTranslate = false;
//    options.fullColour = true;
  } else {
    // If server is trueColour, use lowest depth PF
    PixelFormat native = getNativePF();
    if ((serverDefaultPF.bpp < native.bpp) ||
      ((serverDefaultPF.bpp == native.bpp) &&
      (serverDefaultPF.depth <= native.depth)))
	{
      fullColourPF = serverDefaultPF;
	  m_bNeedTranslate = false;
	}
    else
	{
      fullColourPF = getNativePF();
	  m_bNeedTranslate = true;
	}
  }
}


void
CView::updateF8Menu(bool hideSystemCommands) {
  HMENU menu = GetSystemMenu(getHandle(), FALSE);

  if (hideSystemCommands) {  
    // Gray out menu items that might cause a World Of Pain
    HMENU menu = GetSystemMenu(getHandle(), FALSE);
    EnableMenuItem(menu, SC_SIZE, MF_BYCOMMAND | MF_GRAYED);
    EnableMenuItem(menu, SC_MOVE, MF_BYCOMMAND | MF_GRAYED);
    EnableMenuItem(menu, SC_RESTORE, MF_BYCOMMAND | MF_ENABLED);
    EnableMenuItem(menu, SC_MINIMIZE, MF_BYCOMMAND | MF_ENABLED);
    EnableMenuItem(menu, SC_MAXIMIZE, MF_BYCOMMAND | MF_ENABLED);
  }
/*
  // Update the modifier key menu items
  UINT ctrlCheckFlags = kbd.keyPressed(VK_CONTROL) ? MF_CHECKED : MF_UNCHECKED;
  UINT altCheckFlags = kbd.keyPressed(VK_MENU) ? MF_CHECKED : MF_UNCHECKED;
  CheckMenuItem(menu, IDM_CTRL_KEY, MF_BYCOMMAND | ctrlCheckFlags);
  CheckMenuItem(menu, IDM_ALT_KEY, MF_BYCOMMAND | altCheckFlags);

  // Ensure that the Send <MenuKey> menu item has the correct text
  if (options.menuKey) {
    TCharArray menuKeyStr(options.menuKeyName());
    TCharArray tmp(_tcslen(menuKeyStr.buf) + 6);
    _stprintf(tmp.buf, _T("Send %s"), menuKeyStr.buf);
    if (!ModifyMenu(menu, IDM_SEND_MENU_KEY, MF_BYCOMMAND | MF_STRING, IDM_SEND_MENU_KEY, tmp.buf))
      InsertMenu(menu, IDM_SEND_CAD, MF_BYCOMMAND | MF_STRING, IDM_SEND_MENU_KEY, tmp.buf);
  } else {
    RemoveMenu(menu, IDM_SEND_MENU_KEY, MF_BYCOMMAND);
  }
*/
}


void
CView::setName(const char* name) {
  ::SetWindowText(getHandle(), TStr(name));
}


void CView::serverInit() 
{
	m_nativePF = getNativePF();

	setDesktopSize(cp.width, cp.height);
		
	// Save the server's current format
	serverDefaultPF = cp.pf();

	// Calculate the full-colour format to use
	calculateFullColourPF();

	buffer->setPF(fullColourPF);
	if(fullColourPF.bpp<=8)
		refreshWindowPalette(0, 256);

	// Show the window
	setVisible(true);

	m_bInitialized = true;
}

void CView::fillRect(const Rect& r, Pixel pix) {
  if (cursorBackingRect.overlaps(r)) hideLocalCursor();
  buffer->fillRect(r, pix);
  invalidateBufferRect(r);
}
void CView::imageRect(const Rect& r, void* pixels) {
  if (cursorBackingRect.overlaps(r)) hideLocalCursor();
  buffer->imageRect(r, pixels);
  invalidateBufferRect(r);
}
void CView::copyRect(const Rect& r, int srcX, int srcY) {
  if (cursorBackingRect.overlaps(r) ||
      cursorBackingRect.overlaps(Rect(srcX, srcY, srcX+r.width(), srcY+r.height())))
    hideLocalCursor();
  buffer->copyRect(r, Point(r.tl.x-srcX, r.tl.y-srcY));
  invalidateBufferRect(r);
}
void CView::serverStop()
{
	this->close(0);
}

void CView::sendKeyMouse(bool flag)
{
	options.sendPtrEvents = flag;
	options.sendKeyEvents = flag;
}
