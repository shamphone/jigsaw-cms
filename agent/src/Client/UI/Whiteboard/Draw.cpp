#include "stdafx.h"
#include "resource.h"       // main symbols

#include "Draw.h"
#include "structure.h"
#include <math.h>

#define MAXOFFSET 2 // 定义输入点与真实点最大误差值
					// 在AtPoint和AtLine函数中使用

//白板消息格式
/*
图形							文字

字节	定义					字节	定义
1-4		白板标号
5-8		图形类型				5-8		IDT_TEXT
9-12	画笔颜色				9-12	文字颜色
13-16	画笔宽度				
17-20	是否填充(或点数)		17-76	字体设置LOGFONT
21-N	要画的坐标点			77-80	文字绘制区域左上角坐标
		每4个字节为一个点		81-84	右下角坐标
		前2个字节表示x			85-88	字节数
		后2个字节表示y			89-N	文字内容
*/

//-----------------------------
// 以下为CDrawObject类方法实现
//-----------------------------

CDrawObject::CDrawObject(COLORREF crColor, int nWidth, BOOL bFill, int nStyle, WhiteboardOutStream* os)
{
	m_crPenColor = crColor;
	m_nPenWidth = nWidth;
	m_bFill = bFill;
	m_nStyle = nStyle;
	m_os = os;
	if( m_os )
	{
		m_os->setPos(4);
		m_os->writeU32( nStyle );
		m_os->writeU32( crColor );
		m_os->writeU32( nWidth );
	}
}

//--------------------------------
//以下为CDrawLine(绘线)类方法实现
//--------------------------------

CDrawLine::CDrawLine(COLORREF color, int width, WhiteboardOutStream* os)
	:CDrawObject(color, width, FALSE, IDT_LINE, os)
{
	m_x1=m_y1=m_x2=m_y2=0;
}

void CDrawLine::NewPoint(long x, long y)
{
	m_x1 = m_x2 = x;
	m_y1 = m_y2 = y;
	TRACE2( "x=%d, y=%d\n", x, y );
}

void CDrawLine::AddPoint(long x, long y) 
{ 
	m_x2 = x; 
	m_y2 = y; 
	TRACE2( "x=%d, y=%d\n", x, y );
}

void CDrawLine::MoveAt(CDC *pDC, long x, long y)
{
	//以下设置DC
	COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
	CPen pen(PS_SOLID, GetPenWidth(), XorColor), *oldpen;
	int oldmode = pDC->GetROP2();
	oldpen = pDC->SelectObject(&pen);
	pDC->SetROP2(R2_XORPEN);
	//以下移动直线
	pDC->MoveTo(m_x1, m_y1);
	pDC->LineTo(m_x2, m_y2);//删除原图形

	m_x2 = x;
	m_y2 = y;

	pDC->MoveTo(m_x1, m_y1);
	pDC->LineTo(m_x2, m_y2);//绘新图形

	//以下恢复DC
	pDC->SelectObject(oldpen);
	pen.DeleteObject();//释放笔资源
}

void CDrawLine::Draw(CDC* pDC)
{
	//以下设置DC
	CPen pen(PS_SOLID, GetPenWidth(), GetPenColor()), *oldpen;
	oldpen = pDC->SelectObject(&pen);
	//以下移动直线
	pDC->MoveTo(m_x1, m_y1);
	pDC->LineTo(m_x2, m_y2);

	//以下恢复DC
	pDC->SelectObject(oldpen);
	pen.DeleteObject();//释放笔资源
}

void CDrawLine::SendDraw()
{
	if( m_os )
	{
		m_os->pad(4);
		m_os->writeU16( m_x1 );
		m_os->writeU16( m_y1 );
		m_os->writeU16( m_x2 );
		m_os->writeU16( m_y2 );
		m_os->flush();
	}
}

//--------------------------------
//以下为CDrawCurve(曲线)类方法实现
//--------------------------------

CDrawCurve::CDrawCurve(COLORREF color, int width, WhiteboardOutStream* os)
	:CDrawObject(color, width, FALSE, IDT_CURVE, os)
{
	m_nPointNumber = 0;
}

void CDrawCurve::NewPoint(long x, long y)
{
	m_points[0].x = x;
	m_points[0].y = y;
	m_nPointNumber = 1;
}

void CDrawCurve::AddPoint(long x, long y)
{ 
	m_points[m_nPointNumber].x = x;
	m_points[m_nPointNumber].y = y;
	m_nPointNumber++;
}

void CDrawCurve::MoveAt(CDC *pDC, long x, long y)
{
	//以下设置DC
	CPen pen(PS_DOT, GetPenWidth(), GetPenColor()), *oldpen;
	oldpen = pDC->SelectObject(&pen);

	pDC->MoveTo( m_points[m_nPointNumber-1].x, m_points[m_nPointNumber-1].y );
	pDC->LineTo(x, y);

	//以下恢复DC
	pDC->SelectObject(oldpen);
	pen.DeleteObject();//释放笔资源

	m_points[m_nPointNumber].x = x;
	m_points[m_nPointNumber].y = y;
	m_nPointNumber++;
}

void CDrawCurve::Draw(CDC* pDC)
{
	//以下设置DC
	CPen pen(PS_DOT, GetPenWidth(), GetPenColor()), *oldpen;
	oldpen = pDC->SelectObject(&pen);

    for( int i = 0; i < m_nPointNumber - 1; i++ )
	{
		pDC->MoveTo(m_points[i].x, m_points[i].y);
		pDC->LineTo(m_points[i+1].x, m_points[i+1].y);
	}
	//以下恢复DC
	pDC->SelectObject(oldpen);
	pen.DeleteObject();//释放笔资源

	m_points[0] = m_points[m_nPointNumber-1];
	m_nPointNumber = 1;
}

void CDrawCurve::SendDraw()
{
	if( m_os )
	{
		m_os->setPos(4);
		m_os->writeU32( IDT_CURVE );
		m_os->writeU32( GetPenColor() );
		m_os->writeU32( GetPenWidth() );
		m_os->writeU32( m_nPointNumber );
		for( int i = 0; i < m_nPointNumber; i++ )
		{
			m_os->writeU16( m_points[i].x );
			m_os->writeU16( m_points[i].y );
		}
		m_os->flush();
		TRACE0( "send curve\n" );
	}
}

int CDrawCurve::getPointNumber()
{
    return m_nPointNumber;
}

//-----------------------------------
// 以下为CDrawRect(绘矩形)类方法实现
//-----------------------------------

CDrawRect::CDrawRect(COLORREF color, int width, BOOL bFill, WhiteboardOutStream* os)
	:CDrawObject(color, width, bFill, IDT_RECTANGLE, os)
{
	m_x1=m_y1=m_x2=m_y2=0;
}

void CDrawRect::Draw(CDC* pDC)
{
	CPen pen(PS_SOLID, GetPenWidth(), GetPenColor());
	int oldmode = pDC->GetROP2();

	CPen* oldpen = pDC->SelectObject(&pen);
	CBrush* oldbrush;
	CBrush  brush(GetPenColor());

	if( GetFill() )	
		oldbrush = (CBrush*)pDC->SelectObject(&brush);
	else
		oldbrush=(CBrush*)pDC->SelectStockObject(NULL_BRUSH);

	pDC->Rectangle(m_x1, m_y1, m_x2, m_y2);


	//以下恢复DC
	pDC->SetROP2(oldmode);
	pDC->SelectObject(oldpen);
	pDC->SelectObject(&oldbrush);
	pen.DeleteObject();
}

void CDrawRect::SendDraw()
{
	if( m_os )
	{
		m_os->writeU32( GetFill() );
		m_os->writeU16( m_x1 );
		m_os->writeU16( m_y1 );
		m_os->writeU16( m_x2 );
		m_os->writeU16( m_y2 );
		m_os->flush();
	}
}


void CDrawRect::NewPoint(long x, long y)
{
	m_x1 = m_x2 = x;
	m_y1 = m_y2 = y;
}

void CDrawRect::AddPoint(long x, long y)
{
	m_x2 = x;
	m_y2 = y;
};

void CDrawRect::MoveAt(CDC *pDC, long x, long y)
{
	if( !GetFill() )
	{
		COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
		CPen pen(PS_SOLID, GetPenWidth(), XorColor);
		int oldmode = pDC->GetROP2();

		CPen* oldpen = pDC->SelectObject(&pen);
		//设置为“空”刷，既不填充。
		CBrush* oldbrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH);
		pDC->SetROP2(R2_XORPEN);

		//删除原图形
		pDC->Rectangle(m_x1, m_y1, m_x2, m_y2);

		//修改新图形坐标
		m_x2 = x;
		m_y2 = y;

		//绘新图形
		pDC->Rectangle(m_x1, m_y1, m_x2, m_y2);

		//以下恢复DC
		pDC->SetROP2(oldmode);
		pDC->SelectObject(oldpen);
		pen.DeleteObject();
	}
	else
	{
		COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
		CPen pen(PS_SOLID, GetPenWidth(), XorColor);
		int oldmode = pDC->GetROP2();

		CPen* oldpen = pDC->SelectObject(&pen);
		//设置刷填充。
		CBrush  brush(XorColor);
		CBrush* oldbrush = (CBrush*)pDC->SelectObject(brush);

		pDC->SetROP2(R2_XORPEN);

		//删除原图形
		pDC->Rectangle(m_x1, m_y1, m_x2, m_y2);

		//修改新图形坐标
		m_x2 = x;
		m_y2 = y;

		//绘新图形
		pDC->Rectangle(m_x1, m_y1, m_x2, m_y2);

		//以下恢复DC
		pDC->SetROP2(oldmode);
		pDC->SelectObject(oldpen);
		pDC->SelectObject(&oldbrush);
		pen.DeleteObject();
	}
}

//-------------------------------------
// 以下为CDrawEllipse(绘椭圆)类方法实现
//-------------------------------------

CDrawEllipse::CDrawEllipse(COLORREF color, int width, BOOL bFill, WhiteboardOutStream* os)
	:CDrawObject(color, width, bFill, IDT_ELLIPSE, os)
{
	m_x1=m_y1=m_x2=m_y2=0;
}

void CDrawEllipse::NewPoint(long x, long y)
{
	m_x1 = m_x2 = x;
	m_y1 = m_y2 = y;
}

void CDrawEllipse::MoveAt(CDC *pDC, long x, long y)
{
	if( !GetFill() )
	{
		COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
		CPen pen(PS_SOLID, GetPenWidth(), XorColor);
		int oldmode = pDC->GetROP2();

		CPen* oldpen = pDC->SelectObject(&pen);
		//设置为“空”刷，既不填充。
		CBrush* oldbrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH);
	
		pDC->SetROP2(R2_XORPEN);

		//删除原图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//修改新图形坐标
		m_x2 = x;
		m_y2 = y;

		//绘新图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//以下恢复DC
		pDC->SetROP2(oldmode);
		pDC->SelectObject(oldpen);
		pen.DeleteObject();
	}
	else
	{
		COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
		CPen pen(PS_SOLID, GetPenWidth(), XorColor);
		int oldmode = pDC->GetROP2();

		CPen* oldpen = pDC->SelectObject(&pen);
		//设置刷填充。
		CBrush  brush(XorColor);
		CBrush* oldbrush = (CBrush*)pDC->SelectObject(brush);
		pDC->SetROP2(R2_XORPEN);

		//删除原图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//修改新图形坐标

		m_x2 = x;
		m_y2 = y;

		//绘新图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//以下恢复DC
		pDC->SetROP2(oldmode);
		pDC->SelectObject(oldpen);
		pDC->SelectObject(&oldbrush);
		pen.DeleteObject();
	}
}

void CDrawEllipse::Draw(CDC* pDC)
{
	CPen pen(PS_SOLID, GetPenWidth(), GetPenColor());
	int oldmode = pDC->GetROP2();

	CPen* oldpen = pDC->SelectObject(&pen);
	CBrush* oldbrush;
	CBrush  brush(GetPenColor());
	if( GetFill() )	
		oldbrush = (CBrush*)pDC->SelectObject(&brush);
	else
		oldbrush=(CBrush*)pDC->SelectStockObject(NULL_BRUSH);

	pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

	//以下恢复DC
	pDC->SetROP2(oldmode);
	pDC->SelectObject(oldpen);
	pDC->SelectObject(&oldbrush);
	pen.DeleteObject();
}

void CDrawEllipse::SendDraw()
{
	if( m_os )
	{
		m_os->writeU32( GetFill() );
		m_os->writeU16( m_x1 );
		m_os->writeU16( m_y1 );
		m_os->writeU16( m_x2 );
		m_os->writeU16( m_y2 );
		m_os->flush();
	}
}

//-----------------------------------
// 以下为CDrawCircle(绘圆)类方法实现
//-----------------------------------

CDrawCircle::CDrawCircle(COLORREF color, int width, BOOL bFill, WhiteboardOutStream* os)
	:CDrawObject(color, width, bFill, IDT_CIRCLE, os)
{
	m_x1=m_y1=m_x2=m_y2=0;
}

void CDrawCircle::NewPoint(long x, long y)
{
	m_x1 = m_x2 = x;
	m_y1 = m_y2 = y;
}

void CDrawCircle::MoveAt(CDC *pDC, long x, long y)
{
	if( !GetFill() )
	{
		COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
		CPen pen(PS_SOLID, GetPenWidth(), XorColor);
		int oldmode = pDC->GetROP2();

		CPen* oldpen = pDC->SelectObject(&pen);
		//设置为“空”刷，既不填充。
		CBrush* oldbrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH);
	
		pDC->SetROP2(R2_XORPEN);

		//删除原图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//修改新图形坐标
		long min = min(abs(x - m_x1), abs(y - m_y1));
		if( x < m_x1 )
			m_x2 = m_x1 - min;
		else
			m_x2 = m_x1 + min;
		if( y < m_y1 )
			m_y2 = m_y1 - min;
		else
			m_y2 = m_y1 + min;

		//绘新图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//以下恢复DC
		pDC->SetROP2(oldmode);
		pDC->SelectObject(oldpen);
		pen.DeleteObject();
	}
	else
	{
		COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
		CPen pen(PS_SOLID, GetPenWidth(), XorColor);
		int oldmode = pDC->GetROP2();

		CPen* oldpen = pDC->SelectObject(&pen);
		//设置刷填充。
		CBrush  brush(XorColor);
		CBrush* oldbrush = (CBrush*)pDC->SelectObject(brush);


		pDC->SetROP2(R2_XORPEN);

		//删除原图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//修改新图形坐标
		long min = min(abs(x - m_x1), abs(y - m_y1));
		if( x < m_x1 )
			m_x2 = m_x1 - min;
		else
			m_x2 = m_x1 + min;
		if( y < m_y1 )
			m_y2 = m_y1 - min;
		else
			m_y2 = m_y1 + min;

		//绘新图形
		pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

		//以下恢复DC
		pDC->SetROP2(oldmode);
		pDC->SelectObject(oldpen);
		pDC->SelectObject(&oldbrush);
		pen.DeleteObject();
	}
}

void CDrawCircle::Draw(CDC* pDC)
{
	CPen pen(PS_SOLID, GetPenWidth(), GetPenColor());
	int oldmode = pDC->GetROP2();

	CPen* oldpen = pDC->SelectObject(&pen);
	CBrush* oldbrush;
	CBrush  brush(GetPenColor());
	if( GetFill() )	
		oldbrush = (CBrush*)pDC->SelectObject(&brush);
	else
		oldbrush=(CBrush*)pDC->SelectStockObject(NULL_BRUSH);

	if( GetFill() )
		oldbrush = (CBrush*)pDC->SelectObject(&brush);
	pDC->Ellipse(m_x1, m_y1, m_x2, m_y2);

	//以下恢复DC
	pDC->SetROP2(oldmode);
	pDC->SelectObject(oldpen);
	pDC->SelectObject(&oldbrush);
	pen.DeleteObject();
}

void CDrawCircle::SendDraw()
{
	if( m_os )
	{
		m_os->writeU32( GetFill() );
		m_os->writeU16( m_x1 );
		m_os->writeU16( m_y1 );
		m_os->writeU16( m_x2 );
		m_os->writeU16( m_y2 );
		m_os->flush();
	}
}

//-----------------------------------
// 以下为CDrawPoly(多边形)类方法实现
//-----------------------------------

CDrawPoly::CDrawPoly(COLORREF color, int width, WhiteboardOutStream* os)
	:CDrawObject(color, width, FALSE, IDT_POLY, os)
{
	m_nPointNumber = 0;
}

void CDrawPoly::NewPoint(long x, long y)
{
	m_nPointNumber = 1;
	m_points[0].x = x;
	m_points[0].y = y;
	m_oldx = x;
	m_oldy = y;
}

void CDrawPoly::AddPoint(long x, long y)
{
	if(m_nPointNumber < MAXPLINEPOINT)
	{
		m_points[m_nPointNumber].x = x;
		m_points[m_nPointNumber].y = y;
		m_oldx = x;
		m_oldy = y;
		m_nPointNumber++;
	}
}

void CDrawPoly::MoveAt(CDC *pDC, long x, long y)
{
	if( m_nPointNumber == MAXPLINEPOINT )
		return;
	//以下设置DC
	COLORREF XorColor = pDC->GetBkColor() ^ GetPenColor();
	CPen pen(PS_SOLID, GetPenWidth(), XorColor);
	int oldmode = pDC->GetROP2();
	CPen* oldpen = pDC->SelectObject(&pen);
	//设置为“空”刷，既不填充。
	CBrush* oldbrush = (CBrush*)pDC->SelectStockObject(NULL_BRUSH);
	pDC->SetROP2(R2_XORPEN);

	//修改新图形坐标
	pDC->MoveTo(m_points[m_nPointNumber-1].x, m_points[m_nPointNumber-1].y);
	pDC->LineTo(m_oldx, m_oldy);
	m_oldx = x;
	m_oldy = y;
	pDC->MoveTo(m_points[m_nPointNumber-1].x, m_points[m_nPointNumber-1].y);
	pDC->LineTo(m_oldx, m_oldy);

	//以下恢复DC
	pDC->SetROP2(oldmode);
	pDC->SelectObject(oldpen);
	pDC->SelectObject(oldbrush);
	pen.DeleteObject();
}

void CDrawPoly::EndPoint(CDC* pDC)
{
	CPen pen(PS_SOLID, GetPenWidth(), GetPenColor());
	CPen* oldpen = pDC->SelectObject(&pen);
	pDC->MoveTo(m_points[m_nPointNumber-1].x, m_points[m_nPointNumber-1].y);
	pDC->LineTo(m_points[0].x, m_points[0].y);
	pDC->SelectObject(oldpen);
	pen.DeleteObject();
}

void CDrawPoly::Draw(CDC* pDC)
{
	//以下设置DC
	CPen pen(PS_SOLID, GetPenWidth(), GetPenColor()), *oldpen;
	oldpen = pDC->SelectObject(&pen);

    for( int i = 0; i < m_nPointNumber - 1; i++ )
	{
		pDC->MoveTo(m_points[i].x, m_points[i].y);
		pDC->LineTo(m_points[i+1].x, m_points[i+1].y);
	}
	if( m_nPointNumber > 2 )
	{
		pDC->MoveTo(m_points[m_nPointNumber-1].x, m_points[m_nPointNumber-1].y);
		pDC->LineTo(m_points[0].x, m_points[0].y);
	}
	//以下恢复DC
	pDC->SelectObject(oldpen);
	pen.DeleteObject();//释放笔资源
}

void CDrawPoly::SendDraw()
{
	if( m_os )
	{
		m_os->writeU32(m_nPointNumber);
		for( int i = 0; i < m_nPointNumber; i++ )
		{
			m_os->writeU16( m_points[i].x );
			m_os->writeU16( m_points[i].y );
		}
		m_os->flush();
	}
}

//-----------------------------------
// 以下为CDrawText类方法实现
//-----------------------------------

CDrawText::CDrawText(LOGFONT lf, CString str, COLORREF color, WhiteboardOutStream* os)
	:CDrawObject(color, 1, FALSE, IDT_TEXT, os)
{
    m_sText = str;
	m_lf = lf;
}

void CDrawText::Draw(CDC* pDC)
{
	CFont font;
	font.CreateFontIndirect( &m_lf );
	CFont* pOldFont = pDC->SelectObject( &font );
	COLORREF crOldColor = pDC->SetTextColor( this->GetPenColor() );
	int nOldBkMode = pDC->SetBkMode( OPAQUE );
	COLORREF crOldBkColor = pDC->SetBkColor( RGB(255, 255, 255) );
	pDC->DrawText( m_sText, CRect( m_x1, m_y1, m_x2, m_y2 ), DT_LEFT|DT_WORDBREAK );
	pDC->SelectObject( pOldFont );
	pDC->SetTextColor( crOldColor );
	pDC->SetBkMode( nOldBkMode );
	pDC->SetBkColor( crOldBkColor );
	font.DeleteObject();
}

void CDrawText::SendDraw()
{
	if( m_os )
	{
		m_os->writeBytes( &m_lf, sizeof(LOGFONT) );
		m_os->writeU16( m_x1 );
		m_os->writeU16( m_y1 );
		m_os->writeU16( m_x2 );
		m_os->writeU16( m_y2 );
		m_os->writeString( m_sText );
		m_os->flush();
	}
}
