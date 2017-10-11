#include "stdafx.h"
#include ".\lyvcdesktopserver.h"
#include "rfb\ComparingUpdateTracker.h"
#include "rfb_win32\SDisplay.h"
#include "SMsgWriter.h"
#include "SMsgReader.h"
#include "rfb\ColourCube.h"
#define XK_MISCELLANY
#define XK_XKB_KEYS
#include "rfb\keysymdef.h"
#include "../rfb/rdr/LyvcOutStream.h"

using namespace rfb;
using namespace rfb::win32;

/////////////////////////////////////////////////
// ShiftPresser

class VNCSConnectionSTShiftPresser {
public:
  VNCSConnectionSTShiftPresser(SDesktop* desktop_)
    : desktop(desktop_), pressed(false) {}
  ~VNCSConnectionSTShiftPresser() {
    if (pressed) { desktop->keyEvent(XK_Shift_L, false); }
  }
  void press() {
    desktop->keyEvent(XK_Shift_L, true);
    pressed = true;
  }
  SDesktop* desktop;
  bool pressed;
};

//////////////////////////////////////////////////////////
//LyvcDesktopServer

LyvcDesktopServer::LyvcDesktopServer() 
{
	desktop = NULL;
	pb = NULL;
    comparer = NULL;
	m_pMsgWriter = NULL;
	m_pMsgReader = NULL;
	m_bInitialized = false;
	renderedCursorInvalid = false;
	m_bNeedSendInit = false;
	m_bStarted = false;
	m_os = NULL;
	m_bSendingUpdate = false;
}

LyvcDesktopServer::~LyvcDesktopServer()
{
	stop();

	if(desktop)
	{
		delete desktop;
		desktop = NULL;
	}
	if(m_pMsgWriter)
	{
		delete m_pMsgWriter;
		m_pMsgWriter = NULL;
	}
	if(m_pMsgReader)
	{
		delete m_pMsgReader;
		m_pMsgReader = NULL;
	}
	if(comparer)
	{
        delete comparer;
		comparer = NULL;
	}
	if(pb)
	{
		delete pb;
		pb = NULL;
	}
	if (m_os)
	{
		delete m_os;
		m_os = NULL;
	}
}

void LyvcDesktopServer::start(
		LyvcMessage::MatrixC* pMatrixC,
        DESKTOPSERVER_CALLBACK pCallback,
		void* pCallbackParameter)
{	
	if(!m_bInitialized)
	{
		init(pMatrixC, pCallback, pCallbackParameter);
		m_bInitialized = true;
	}
	restart();
}

void LyvcDesktopServer::stop()
{
	if (m_bStarted) 
	{
		desktop->stop();
		m_bStarted = false;
	}
}

void LyvcDesktopServer::reInitialize()
{
	restart();
}

void LyvcDesktopServer::init(LyvcMessage::MatrixC* pMatrixC, DESKTOPSERVER_CALLBACK pCallback, void* pCallbackParameter)
{
	desktop = new SDisplay();
	m_pMsgWriter = new SMsgWriter();
	m_pMsgReader = new SMsgReader(pMatrixC, this);

	m_os = new LyvcOutStream(pCallback, pCallbackParameter);
	m_pMsgWriter->setOutStream(m_os);
}

void LyvcDesktopServer::restart()
{	
	if(!m_bStarted)
	{
		desktop->start(this);
		m_bStarted = true;
	}
	else
	{
		m_bNeedSendInit = true;
		updates.add_changed(pb->getRect());
	}
}

//为减少网络占用和系统资源占用，颜色高于16位被转成16位
void LyvcDesktopServer::setPixelBuffer(PixelBuffer* pb_)
{
	pb = pb_;
	delete comparer;
	comparer = 0;

	if (pb) 
	{
		comparer = new ComparingUpdateTracker(pb);
		cursor.setPF(pb->getPF());
		renderedCursor.setPF(pb->getPF());
		m_pMsgWriter->m_cp->width = pb->width();
		m_pMsgWriter->m_cp->height = pb->height();
		if (pb->getPF().bpp <= m_pMsgWriter->m_cp->pf().bpp)//如果小于等于16位，不转
		{
			m_pMsgWriter->m_cp->setPF(pb->getPF());
			m_transImageGetter.init(pb, pb->getPF());
		}
		else//如果大于16位，转成16位的
			m_transImageGetter.init(pb, m_pMsgWriter->m_cp->pf());

		m_bNeedSendInit = true;
	}
}
void LyvcDesktopServer::setColourMapEntries(int firstColour, int nColours)
{}

void LyvcDesktopServer::tryUpdate() 
{
	if( m_bSendingUpdate )
	{
		return;
	}
	m_bSendingUpdate = true;

	//因为目前是推模式的，对客户端的情况并不关心，数据由服务器中转到各客户端，，
	//因此不考虑有的客户端只需要部分屏幕更新的数据的情况，发送的都是整个屏幕区域更新的数据
	Region requested(pb->getRect());

	//获取更新区域
	this->checkUpdate();

	// If the previous position of the rendered cursor overlaps the source of the
	// copy, then when the copy happens the corresponding rectangle in the
	// destination will be wrong, so add it to the changed region.
	if (!updates.get_copied().is_empty() && !renderedCursorRect.is_empty()) 
	{
		Rect bogusCopiedCursor = (renderedCursorRect.translate(updates.get_delta()).intersect(pb->getRect()));
		if (!updates.get_copied().intersect(bogusCopiedCursor).is_empty())
			updates.add_changed(bogusCopiedCursor);
	}

	// If we need to remove the old rendered cursor, just add the rectangle to
	// the changed region.
	if (removeRenderedCursor) 
	{
		updates.add_changed(renderedCursorRect);
		renderedCursorRect.clear();
		removeRenderedCursor = false;
	}

	// Return if there is nothing to send the client.
	if (updates.is_empty() && !drawRenderedCursor)
	{
		m_bSendingUpdate = false;
		return;
	}

	// If the client needs a server-side rendered cursor, work out the cursor
	// rectangle.  If it's empty then don't bother drawing it, but if it overlaps
	// with the update region, we need to draw the rendered cursor regardless of
	// whether it has changed.
	if (needRenderedCursor())
	{
		renderedCursorRect = (renderedCursor.getRect(renderedCursorTL).intersect(requested.get_bounding_rect()));

		if (renderedCursorRect.is_empty()) 
			drawRenderedCursor = false;
		else if (!updates.get_changed().union_(updates.get_copied()).intersect(renderedCursorRect).is_empty()) 
			drawRenderedCursor = true;

		// We could remove the new cursor rect from updates here.  It's not clear
		// whether this is worth it.  If we do remove it, then we won't draw over
		// the same bit of screen twice, but we have the overhead of a more complex
		// region.
		//if (drawRenderedCursor)
		//  updates.subtract(renderedCursorRect);
	}

	//是否需要发送初始化信息
	if(m_bNeedSendInit)
	{
		m_pMsgWriter->writeServerInit(pb->getColourMap());
		m_bNeedSendInit = false;
	}

	//发送屏幕更新数据
	UpdateInfo update;
	updates.enable_copyrect(m_pMsgWriter->m_cp->useCopyRect);
	updates.get_update(&update, requested);
	if (!update.is_empty() || drawRenderedCursor)
	{
	    Region updatedRegion;
		m_pMsgWriter->writeRects(update, &m_transImageGetter, &updatedRegion);
		updates.subtract(updatedRegion);
		if (drawRenderedCursor)
			writeCursor();
	}
	m_bSendingUpdate = false;
}
void LyvcDesktopServer::writeCursor()
{
	m_transImageGetter.setPixelBuffer(&renderedCursor);//, m_pMsgWriter->m_cp->pf());
	m_pMsgWriter->writeCursor(renderedCursor.getRect(), renderedCursorTL, &m_transImageGetter);
	drawRenderedCursor = false;

	m_transImageGetter.setPixelBuffer(pb);//, m_pMsgWriter->m_cp->pf());
}

void LyvcDesktopServer::setCursor(int width, int height, int hotspotX, int hotspotY,
                        void* cursorData, void* mask) 
{	cursor.hotspot.x = hotspotX;
	cursor.hotspot.y = hotspotY;
	cursor.setSize(width, height);
	memcpy(cursor.data, cursorData, cursor.dataLen());
	memcpy(cursor.mask.buf, mask, cursor.maskLen());

	cursor.crop();

	renderedCursorInvalid = true;

	removeRenderedCursor = true;
	if (needRenderedCursor())
		drawRenderedCursor = true;

	//tryUpdate();
}

void LyvcDesktopServer::setCursorPos(int x, int y) 
{
	if (cursorPos.x != x || cursorPos.y != y) 
	{
		cursorPos.x = x;
		cursorPos.y = y;
		renderedCursorInvalid = true;

		removeRenderedCursor = true;
		if (needRenderedCursor())
			drawRenderedCursor = true;
	}
}

void LyvcDesktopServer::add_changed(const Region& region)
{
	comparer->add_changed(region);
}

void LyvcDesktopServer::add_copied(const Region& dest, const Point& delta)
{
	comparer->add_copied(dest, delta);
}

inline bool LyvcDesktopServer::needRenderedCursor()
{
	if( !(m_pMsgWriter->m_cp->supportsLocalCursor)
		||(!(cursorPos.equals(pointerEventPos)) && (time(0) - pointerEventTime) > 0) ) 
	{
		return true;
	}
	return false;
}

void LyvcDesktopServer::checkUpdate()
{
	bool renderCursor = needRenderedCursor();

	if (comparer->is_empty() && !(renderCursor && renderedCursorInvalid))
		return;

	Region toCheck = comparer->get_changed().union_(comparer->get_copied());

	if (renderCursor) 
	{
		Rect clippedCursorRect = cursor.getRect(cursorTL()).intersect(pb->getRect());

		if (!renderedCursorInvalid 
			&& (toCheck.intersect(clippedCursorRect).is_empty())) 
		{
			renderCursor = false;
		} 
		else 
		{
			renderedCursorTL = clippedCursorRect.tl;
			renderedCursor.setSize(clippedCursorRect.width(),
									clippedCursorRect.height());
			toCheck.assign_union(clippedCursorRect);
		}
	}

	pb->grabRegion(toCheck);

	//if (rfb::Server::compareFB)
	comparer->compare();

	if (renderCursor) 
	{
		pb->getImage(renderedCursor.data, renderedCursor.getRect(renderedCursorTL));
		renderedCursor.maskRect(cursor.getRect(cursorTL().subtract(renderedCursorTL)),
								cursor.data, 
								cursor.mask.buf);
		renderedCursorInvalid = false;
	}

    updates.add_copied(comparer->get_copied(), comparer->get_delta());
    updates.add_changed(comparer->get_changed());

	comparer->clear();
}

void LyvcDesktopServer::setMouse(int mask, int x, int y)
{
	pointerEventTime = lastEventTime = time(0);
	pointerEventPos = Point(x, y);
	desktop->pointerEvent(pointerEventPos, mask);
}

// keyEvent() - record in the pressedKeys which keys were pressed.  Allow
// multiple down events (for autorepeat), but only allow a single up event.
void LyvcDesktopServer::setKey(bool downFlag, int key)
{
	lastEventTime = time(0);
	// Turn ISO_Left_Tab into shifted Tab.
	VNCSConnectionSTShiftPresser shiftPresser(desktop);
	if (key == XK_ISO_Left_Tab) {
		if (pressedKeys.find(XK_Shift_L) == pressedKeys.end() &&
			pressedKeys.find(XK_Shift_R) == pressedKeys.end())
		shiftPresser.press();
		key = XK_Tab;
	}

	if (downFlag) {
		pressedKeys.insert(key);
	} else {
		if (!pressedKeys.erase(key)) return;
	}
	desktop->keyEvent(key, downFlag);

}
