#pragma once
/*
 * 
 * Define Draw Objects
 *
 */

//颜色色数值定义
#define WIDTH_THIN		1
#define WIDTH_NORMAL	3
#define WIDTH_WIDE		5
#define UD_WHITE	0xffffff
#define UD_BLACK	0
#define UD_YELLOW	0x00ffff
#define UD_PINK		0xff80ff
#define UD_CYAN		0xffff00
#define UD_GRAY		0x4080
#define UD_RED		0xff 
#define UD_GREEN	0xff00 
#define UD_BLUE		0xff0000 

class WhiteboardOutStream;

class CDrawObject : public CObject
{
private:
	COLORREF m_crPenColor;	//图元颜色
	int		 m_nPenWidth;	//画笔宽度
	BOOL	 m_bFill;		//是否填充
	int		 m_nStyle;		//图元类型

public:
	CDrawObject(COLORREF crColor, int nWidth, BOOL bFill, int nStyle, WhiteboardOutStream* os);
	WhiteboardOutStream* m_os;

	COLORREF GetPenColor() { return m_crPenColor; };
	int  GetPenWidth() {return  m_nPenWidth;};
	BOOL GetFill()  {return m_bFill;};

	virtual void Draw(CDC* pDC) = 0;
	virtual void MoveAt(CDC* pDC, long x, long y) = 0;
	virtual void NewPoint(long x, long y) = 0;//图形对象第一点坐标，如果返回false则结束绘图
	virtual void AddPoint(long x, long y) = 0;
	virtual	void EndPoint(CDC* pDC) { };
	virtual void SendDraw() = 0;
};

class CDrawLine : public CDrawObject
{
private:
	long m_x1, m_y1, m_x2, m_y2;

public:
	CDrawLine(COLORREF color = UD_RED, int width = 1, WhiteboardOutStream* os = NULL);

	void Draw(CDC* pDC);
	void NewPoint(long x, long y);
	void AddPoint(long x, long y);
	void MoveAt(CDC* pDC, long x, long y);
	void SendDraw();
};

#define POINTNUMBER 150

class CDrawCurve : public CDrawObject
{
private:
	int m_nPointNumber;
	POINTS m_points[POINTNUMBER];

public:
	CDrawCurve(COLORREF color = UD_RED, int width = 1, WhiteboardOutStream* os = NULL);

	void NewPoint(long x, long y);
	void MoveAt(CDC* pDC, long x, long y);
	void Draw(CDC* pDC);
	void AddPoint(long x, long y);
	int  getPointNumber();
	void SendDraw();
};

class CDrawRect : public CDrawObject
{
private:
	long m_x1, m_y1, m_x2, m_y2;

public:
	CDrawRect(COLORREF color = UD_RED, int width = 1, BOOL bFill = FALSE, WhiteboardOutStream* os = NULL);

	void MoveAt(CDC* pDC, long x, long y);
	void NewPoint(long x, long y);
	void AddPoint(long x, long y);
	void Draw(CDC* pDC);
	void SendDraw();
};

class CDrawEllipse : public CDrawObject
{
private:
	long m_x1, m_y1, m_x2, m_y2;

public:
	CDrawEllipse(COLORREF color = UD_RED, int width = 1, BOOL bFill = FALSE, WhiteboardOutStream* os = NULL);

	void MoveAt(CDC* pDC, long x, long y);
	void NewPoint(long x, long y);
	void Draw(CDC* pDC);
	void AddPoint(long x, long y) { m_x2 = x; m_y2 = y; };
	void SendDraw();
};

class CDrawCircle : public CDrawObject
{
private:
	long m_x1, m_y1, m_x2, m_y2;

public:
	CDrawCircle(COLORREF color = UD_RED, int width = 1, BOOL bFill = FALSE, WhiteboardOutStream* os = NULL);
	void MoveAt(CDC* pDC, long x, long y);
	void NewPoint(long x, long y);
	void Draw(CDC* pDC);
	void AddPoint(long x, long y) { m_x2 = x; m_y2 = y; };
	void SendDraw();
};

#define MAXPLINEPOINT 100

class CDrawPoly : public CDrawObject
{
private:
	POINTS m_points[MAXPLINEPOINT];
	long m_oldx, m_oldy;

public:
	long m_nPointNumber;//端点总数
	CDrawPoly(COLORREF color = UD_RED, int width = 1, WhiteboardOutStream* os = NULL);

	void MoveAt(CDC* pDC, long x, long y);
	void NewPoint(long x, long y);
	void AddPoint(long x, long y);
	void EndPoint(CDC* pDC);
	void Draw(CDC* pDC);
	void SendDraw();
};

class CDrawText : public CDrawObject
{
private:
	long m_x1, m_y1, m_x2, m_y2;
	LOGFONT m_lf;
	CString m_sText;

public:
	CDrawText(LOGFONT lf, CString str, COLORREF color = UD_RED, WhiteboardOutStream* os = NULL);

	void MoveAt(CDC* pDC, long x, long y) { };
	void NewPoint(long x, long y){	m_x1 = m_x2 = x; m_y1 = m_y2 = y; };
	void AddPoint(long x, long y) { m_x2 = x; m_y2 = y; };
	void Draw(CDC* pDC);
	void SendDraw();
};