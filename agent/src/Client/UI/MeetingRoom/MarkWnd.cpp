// D:\Lyvc\src\Client\UI\MeetingRoom\MarkWnd.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include ".\markwnd.h"


// CMarkWnd

IMPLEMENT_DYNAMIC(CMarkWnd, CWnd)
CMarkWnd::CMarkWnd()
{
	m_nPenSize = 4;          //初始化画笔大小
	MouseFlag = TRUE;  //初始化标志
	m_PenColor = RGB(0, 0, 255);
}

CMarkWnd::~CMarkWnd()
{
}


BEGIN_MESSAGE_MAP(CMarkWnd, CWnd)
	ON_WM_LBUTTONDOWN()
	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	ON_COMMAND(ID_PEN_BLUE, OnPenBlue)
	ON_COMMAND(ID_PEN_RED, OnPenRed)
	ON_COMMAND(ID_PEN_GREEN, OnPenGreen)
	ON_COMMAND(ID_PEN_L, OnPenL)
	ON_COMMAND(ID_PEN_M, OnPenM)
	ON_COMMAND(ID_PEN_S, OnPenS)
	ON_COMMAND(ID_MARK_EXIT, OnMarkExit)
	ON_WM_RBUTTONDOWN()
END_MESSAGE_MAP()



// CMarkWnd 消息处理程序

void CMarkWnd::create()
{
	CRect rc,rc1;
	this->GetDesktopWindow()->GetWindowRect(rc);
	HWND hWnd = ::FindWindow("Shell_TrayWnd",NULL);
	::GetWindowRect(hWnd, rc1);
	rc.bottom = rc1.top;

	CreateEx(WS_EX_TRANSPARENT|WS_EX_TOPMOST|WS_EX_TOOLWINDOW,
			AfxRegisterWndClass(0, AfxGetApp()->LoadStandardCursor(IDC_ARROW)),
			0,
			WS_POPUP,
			rc,
			NULL,
			NULL,
			NULL );
}

void CMarkWnd::OnRButtonDown(UINT nFlags, CPoint point)
{
	CMenu popmenu;
	popmenu.LoadMenu(IDR_MARK);

	ClientToScreen(&point);
	CMenu* pmnu = popmenu.GetSubMenu(0); 
	pmnu->TrackPopupMenu(TPM_LEFTALIGN|TPM_RIGHTBUTTON,point.x,point.y,this);
	popmenu.DestroyMenu();

	//CWnd::OnRButtonDown(nFlags, point);
}

void CMarkWnd::OnLButtonDown(UINT nFlags, CPoint point)
{
	MouseFlag = FALSE;//设置鼠标左键按下标志
	if (!MouseFlag)
	{               //取得鼠标坐标
		m_nX = point.x;
		m_nY = point.y;
	}
	
	CWnd::OnLButtonDown(nFlags, point);
}

void CMarkWnd::OnLButtonUp(UINT nFlags, CPoint point)
{
	if(!MouseFlag)
	{
		MouseFlag=TRUE;//设置鼠标左键释放标志
		ReleaseCapture();
	}

	CWnd::OnLButtonUp(nFlags, point);
}

void CMarkWnd::OnMouseMove(UINT nFlags, CPoint point)
{
	if (!MouseFlag) //取得鼠标移动的当前坐标，并在屏幕画图
	{
		CClientDC dc(this);
		CPen Pen;
		CPen *OldPen;
		Pen.CreatePen(PS_SOLID, m_nPenSize, m_PenColor);
		OldPen = dc.SelectObject(&Pen);
		dc.MoveTo(m_nX, m_nY);
		dc.LineTo(point.x, point.y);
		m_nX = point.x;
		m_nY = point.y;
		dc.SelectObject(&OldPen);
	}

	CWnd::OnMouseMove(nFlags, point);
}

void CMarkWnd::OnPenBlue()
{
	m_PenColor = RGB(0, 0, 255);
}

void CMarkWnd::OnPenRed()
{
	m_PenColor = RGB(255, 0, 0);
}

void CMarkWnd::OnPenGreen()
{
	m_PenColor = RGB(0, 255, 0);
}

void CMarkWnd::OnPenL()
{
	m_nPenSize = 6;
}

void CMarkWnd::OnPenM()
{
	m_nPenSize = 4;
}

void CMarkWnd::OnPenS()
{
	m_nPenSize = 2;
}

void CMarkWnd::OnMarkExit()
{
	this->DestroyWindow();
}

BOOL CMarkWnd::PreCreateWindow(CREATESTRUCT& cs)
{
	cs.style &= ~WS_CAPTION;
	return CWnd::PreCreateWindow(cs);
}
