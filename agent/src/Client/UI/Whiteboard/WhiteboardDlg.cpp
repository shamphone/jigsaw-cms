// WhiteboardDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "WhiteboardDlg.h"
#include ".\whiteboarddlg.h"
#include "Whiteboard.h"
#include "structure.h"
#include "..\MeetingRoom\RoomMainFrm.h"
#include "..\..\Transfer\TransModel.h"
#include <afxpriv.h>

// CWhiteboardDlg 对话框
#pragma warning( disable : 4244 )


IMPLEMENT_DYNAMIC(CWhiteboardDlg, CDialog)
CWhiteboardDlg::CWhiteboardDlg(CWnd* pParent/*=NULL*/, UINT nID)
	: CDialog(nID, pParent), CTabItem( this )
{
	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		m_whiteboards[i] = NULL;
	}
	m_pTransModel = NULL;
	m_nCurSel = -1;
	m_bShowWhiteboard = FALSE;
	m_nMouseMode = IDT_LINE;
	m_crCurrentColor = UD_RED;
	m_nPenWidth = WIDTH_THIN;
	m_bFill = false;
	m_pToolBar = NULL;
	m_pColorBar = NULL;
	m_nType = CTabItem::WhiteboardDlg;
	this->m_ChatWithId = m_nType;

	m_os = new WhiteboardOutStream( this );
	m_is = new WhiteboardInStream();
}

CWhiteboardDlg::~CWhiteboardDlg()
{
	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		if( m_whiteboards[i] != NULL )
		{
			m_whiteboards[i]->DestroyWindow();
			delete m_whiteboards[i];
		}
	}
	delete m_os;
	delete m_is;
}

void CWhiteboardDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CWhiteboardDlg, CDialog)
	ON_WM_CTLCOLOR()
	ON_WM_SIZE()
	ON_NOTIFY_EX(TTN_NEEDTEXT, 0, OnToolTipText)

	ON_COMMAND(ID_NEW_WHITEBOARD, OnNewWhiteboard)
	ON_COMMAND(ID_FILE_OPEN, OnFileOpen)
	ON_COMMAND(ID_SAVE_WHITEBOARD, OnSaveWhiteboard)
	ON_COMMAND(ID_RUBBER, OnRubber)
	ON_UPDATE_COMMAND_UI(ID_RUBBER, OnUpdateRubber)
	ON_COMMAND(IDT_SELECT, OnSelect)
	ON_UPDATE_COMMAND_UI(IDT_SELECT, OnUpdateSelect)
	ON_COMMAND(IDT_LINE, OnLine)
	ON_UPDATE_COMMAND_UI(IDT_LINE, OnUpdateLine)
	ON_COMMAND(IDT_RECTANGLE, OnRectangle)
	ON_UPDATE_COMMAND_UI(IDT_RECTANGLE, OnUpdateRectangle)
	ON_COMMAND(IDT_ELLIPSE, OnEllipse)
	ON_UPDATE_COMMAND_UI(IDT_ELLIPSE, OnUpdateEllipse)
	ON_COMMAND(IDT_CIRCLE, OnCircle)
	ON_UPDATE_COMMAND_UI(IDT_CIRCLE, OnUpdateCircle)
	ON_COMMAND(IDT_POLY, OnPoly)
	ON_UPDATE_COMMAND_UI(IDT_POLY, OnUpdatePoly)
	ON_COMMAND(IDT_CURVE, OnCurve)
	ON_UPDATE_COMMAND_UI(IDT_CURVE, OnUpdateCurve)
	ON_COMMAND(IDT_TEXT, OnText)
	ON_UPDATE_COMMAND_UI(IDT_TEXT, OnUpdateText)
	ON_COMMAND(ID_WIDTH_THIN, OnWidthThin)
	ON_UPDATE_COMMAND_UI(ID_WIDTH_THIN, OnUpdateWidthThin)
	ON_COMMAND(ID_WIDTH_NORMAL, OnWidthNormal)
	ON_UPDATE_COMMAND_UI(ID_WIDTH_NORMAL, OnUpdateWidthNormal)
	ON_COMMAND(ID_WIDTH_WIDE, OnWidthWide)
	ON_UPDATE_COMMAND_UI(ID_WIDTH_WIDE, OnUpdateWidthWide)
	ON_COMMAND(ID_FILL_FALSE, OnFillFalse)
	ON_UPDATE_COMMAND_UI(ID_FILL_FALSE, OnUpdateFillFalse)
	ON_COMMAND(ID_FILL_TRUE, OnFillTrue)
	ON_UPDATE_COMMAND_UI(ID_FILL_TRUE, OnUpdateFillTrue)

	ON_COMMAND(ID_COLOR_BLACK, OnColorBlack)
	ON_UPDATE_COMMAND_UI(ID_COLOR_BLACK, OnUpdateColorBlack)
	ON_COMMAND(ID_COLOR_WHITE, OnColorWhite)
	ON_UPDATE_COMMAND_UI(ID_COLOR_WHITE, OnUpdateColorWhite)
	ON_COMMAND(ID_COLOR_GRAY, OnColorGray)
	ON_UPDATE_COMMAND_UI(ID_COLOR_GRAY, OnUpdateColorGray)
	ON_COMMAND(ID_COLOR_RED, OnColorRed)
	ON_UPDATE_COMMAND_UI(ID_COLOR_RED, OnUpdateColorRed)
	ON_COMMAND(ID_COLOR_GREEN, OnColorGreen)
	ON_UPDATE_COMMAND_UI(ID_COLOR_GREEN, OnUpdateColorGreen)
	ON_COMMAND(ID_COLOR_BLUE, OnColorBlue)
	ON_UPDATE_COMMAND_UI(ID_COLOR_BLUE, OnUpdateColorBlue)
	ON_COMMAND(ID_COLOR_PINK, OnColorPink)
	ON_UPDATE_COMMAND_UI(ID_COLOR_PINK, OnUpdateColorPink)
	ON_COMMAND(ID_COLOR_YELLOW, OnColorYellow)
	ON_UPDATE_COMMAND_UI(ID_COLOR_YELLOW, OnUpdateColorYellow)
	ON_COMMAND(ID_COLOR_CYAN, OnColorCyan)
	ON_UPDATE_COMMAND_UI(ID_COLOR_CYAN, OnUpdateColorCyan)

	ON_COMMAND(ID_SMALL_TEXT, OnSmallText)
	ON_UPDATE_COMMAND_UI(ID_SMALL_TEXT, OnUpdateSmallText)
	ON_COMMAND(ID_NORMAL_TEXT, OnNormalText)
	ON_UPDATE_COMMAND_UI(ID_NORMAL_TEXT, OnUpdateNormalText)
	ON_COMMAND(ID_BIG_TEXT, OnBigText)
	ON_UPDATE_COMMAND_UI(ID_BIG_TEXT, OnUpdateBigText)

	ON_BN_CLICKED(IDC_PREVBOARD_BTN, OnBnClickedPrevboardBtn)
	ON_BN_CLICKED(IDC_NEXTBOARD_BTN, OnBnClickedNextboardBtn)
END_MESSAGE_MAP()


// CWhiteboardDlg 消息处理程序

BOOL CWhiteboardDlg::OnInitDialog()
{
	CDialog::OnInitDialog();
    CFont* pFont = GetFont();
	pFont->GetLogFont( &m_lf );
	m_lf.lfHeight = 14;
	lstrcpy( m_lf.lfFaceName, "宋体" );

	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		CWhiteboard* pWhiteboard = new CWhiteboard( this, IDD_WHITEBOARD, i );
		pWhiteboard->Create( IDD_WHITEBOARD, this );
		CRect rc;
		GetClientRect( &rc );
		rc.left = 24;
		rc.bottom = rc.bottom - 24;
		pWhiteboard->MoveWindow( rc );
		m_whiteboards[i] = pWhiteboard;
	}
	m_whiteboards[0]->ShowWindow(TRUE);
	m_nCurSel = 0;
	//设置白板序号
	m_os->setPos(0);
	m_os->writeU32(m_nCurSel);

	m_brush.CreateSolidBrush( RGB(255, 255, 255) );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

HBRUSH CWhiteboardDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd == this )
	{
		hbr = m_brush;
	}
	return hbr;
}

void CWhiteboardDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		if( m_whiteboards[i] != NULL )
		{
			m_whiteboards[i]->MoveWindow( 0, 22, cx, cy - 44 );
		}
	}
	if( m_pToolBar && m_pToolBar->GetSafeHwnd() )
	{
		m_pToolBar->MoveWindow( 0, 0, cx, 22 );
	}
	if(	m_pColorBar && m_pColorBar->GetSafeHwnd() )
	{
		m_pColorBar->MoveWindow( 0, cy - 22, cx, 22 );
	}
	if( GetDlgItem(IDC_PREVBOARD_BTN)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_PREVBOARD_BTN)->MoveWindow( cx - 70, cy - 20, 25, 20 );
		GetDlgItem(IDC_NEXTBOARD_BTN)->MoveWindow( cx - 25, cy - 20, 25, 20 );
		GetDlgItem(IDC_CURRENTBOARD)->MoveWindow( cx - 45, cy - 18, 20, 18 );
	}
}

BOOL CWhiteboardDlg::OnToolTipText(UINT, NMHDR* pNMHDR, LRESULT* pResult)
{
	ASSERT(pNMHDR->code == TTN_NEEDTEXTA || pNMHDR->code == TTN_NEEDTEXTW);
	
	// UNICODE消息
	TOOLTIPTEXTA* pTTTA = (TOOLTIPTEXTA*)pNMHDR;
	TOOLTIPTEXTW* pTTTW = (TOOLTIPTEXTW*)pNMHDR;
	//TCHAR szFullText[512];
	CString strTipText;
	UINT nID = pNMHDR->idFrom;
	
	if (pNMHDR->code == TTN_NEEDTEXTA && (pTTTA->uFlags & TTF_IDISHWND) ||
		pNMHDR->code == TTN_NEEDTEXTW && (pTTTW->uFlags & TTF_IDISHWND))
	{
		// idFrom为工具条的HWND 
		nID = ::GetDlgCtrlID((HWND)nID);
	}
	
	if( nID != 0 ) //不为分隔符
	{
		strTipText.LoadString(nID);
		strTipText = strTipText.Mid(strTipText.Find('\n',0) + 1);
		
#ifndef _UNICODE
		if (pNMHDR->code == TTN_NEEDTEXTA)
		{
			lstrcpyn(pTTTA->szText, strTipText, sizeof(pTTTA->szText));
		}
		else
		{
			_mbstowcsz(pTTTW->szText, strTipText, sizeof(pTTTW->szText));
		}
#else
		if (pNMHDR->code == TTN_NEEDTEXTA)
		{
			_wcstombsz(pTTTA->szText, strTipText,sizeof(pTTTA->szText));
		}
		else
		{
			lstrcpyn(pTTTW->szText, strTipText, sizeof(pTTTW->szText));
		}
#endif
		
		// 使工具条提示窗口在最上面
		::SetWindowPos(pNMHDR->hwndFrom, HWND_TOP, 0, 0, 0, 0, SWP_NOACTIVATE|SWP_NOSIZE|SWP_NOMOVE|SWP_NOOWNERZORDER); 
	}
	*pResult = 0;
	return TRUE;
}

void CWhiteboardDlg::OnNewWhiteboard()
{
/*	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		if( m_whiteboards[i] == NULL )
		{
			CWhiteboard* pWhiteboard = new CWhiteboard( this, IDD_WHITEBOARD, i );
			pWhiteboard->Create( IDD_WHITEBOARD, this );
			CRect rc;
			GetClientRect( &rc );
			rc.left = 24;
			rc.bottom = rc.bottom - 24;
			pWhiteboard->MoveWindow( rc );
			pWhiteboard->ShowWindow( TRUE );
			m_whiteboards[i] = pWhiteboard;
			this->setCurrentDisplay( i );
			return;
		}
	}
	AfxMessageBox( "up to limit" );
*/
}

void CWhiteboardDlg::OnFileOpen()
{}

void CWhiteboardDlg::OnSaveWhiteboard()
{
	CFileDialog dlg(false,_T("BMP"),_T("*.bmp"),OFN_HIDEREADONLY | OFN_OVERWRITEPROMPT,_T("*.bmp|*.bmp|*.*|*.*|"));
	if( dlg.DoModal() != IDOK )
		return;

	CString ss=dlg.GetPathName();
	this->m_whiteboards[this->m_nCurSel]->saveToFile( ss );
}

void CWhiteboardDlg::OnRubber()
{
	m_nMouseMode = ID_RUBBER;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateRubber(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == ID_RUBBER );
}

void CWhiteboardDlg::OnSelect()
{
	m_nMouseMode = IDT_SELECT;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateSelect(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_SELECT );
}

void CWhiteboardDlg::OnLine()
{
	m_nMouseMode = IDT_LINE;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateLine(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_LINE );
}

void CWhiteboardDlg::OnRectangle()
{
	m_nMouseMode = IDT_RECTANGLE;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateRectangle(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_RECTANGLE );
}

void CWhiteboardDlg::OnEllipse()
{
	m_nMouseMode = IDT_ELLIPSE;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateEllipse(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_ELLIPSE );
}

void CWhiteboardDlg::OnCircle()
{
	m_nMouseMode = IDT_CIRCLE;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateCircle(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_CIRCLE );
}

void CWhiteboardDlg::OnPoly()
{
	m_nMouseMode = IDT_POLY;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdatePoly(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_POLY );
}

void CWhiteboardDlg::OnCurve()
{
	m_nMouseMode = IDT_CURVE;
	this->hideTextWindow();
}

void CWhiteboardDlg::OnUpdateCurve(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_CURVE );
}

void CWhiteboardDlg::OnText()
{
	m_nMouseMode = IDT_TEXT;
}

void CWhiteboardDlg::OnUpdateText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nMouseMode == IDT_TEXT );
}

void CWhiteboardDlg::OnWidthThin()
{
	m_nPenWidth = WIDTH_THIN;
}

void CWhiteboardDlg::OnUpdateWidthThin(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nPenWidth == WIDTH_THIN );
}

void CWhiteboardDlg::OnWidthNormal()
{
	m_nPenWidth = WIDTH_NORMAL;
}

void CWhiteboardDlg::OnUpdateWidthNormal(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nPenWidth == WIDTH_NORMAL );
}

void CWhiteboardDlg::OnWidthWide()
{
	m_nPenWidth = WIDTH_WIDE;
}

void CWhiteboardDlg::OnUpdateWidthWide(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_nPenWidth == WIDTH_WIDE );
}

void CWhiteboardDlg::OnFillFalse()
{
	m_bFill = false;
}

void CWhiteboardDlg::OnUpdateFillFalse(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( !m_bFill );
}

void CWhiteboardDlg::OnFillTrue()
{
	m_bFill = true;
}

void CWhiteboardDlg::OnUpdateFillTrue(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_bFill );
}

void CWhiteboardDlg::OnColorBlack()
{
	m_crCurrentColor = UD_BLACK;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorBlack(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_BLACK );
}

void CWhiteboardDlg::OnColorWhite()
{
	m_crCurrentColor = UD_WHITE;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorWhite(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_WHITE );
}

void CWhiteboardDlg::OnColorGray()
{
	m_crCurrentColor = UD_GRAY;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorGray(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_GRAY );
}

void CWhiteboardDlg::OnColorRed()
{
	m_crCurrentColor = UD_RED;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorRed(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_RED );
}

void CWhiteboardDlg::OnColorGreen()
{
	m_crCurrentColor = UD_GREEN;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorGreen(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_GREEN );
}

void CWhiteboardDlg::OnColorBlue()
{
	m_crCurrentColor = UD_BLUE;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorBlue(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_BLUE );
}

void CWhiteboardDlg::OnColorPink()
{
	m_crCurrentColor = UD_PINK;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorPink(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_PINK );
}

void CWhiteboardDlg::OnColorYellow()
{
	m_crCurrentColor = UD_YELLOW;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorYellow(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_YELLOW );
}

void CWhiteboardDlg::OnColorCyan()
{
	m_crCurrentColor = UD_CYAN;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateColorCyan(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_crCurrentColor == UD_CYAN );
}

void CWhiteboardDlg::OnSmallText()
{
	m_lf.lfHeight = 14;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateSmallText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_lf.lfHeight == 14 );
}

void CWhiteboardDlg::OnNormalText()
{
	m_lf.lfHeight = 18;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateNormalText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_lf.lfHeight == 18 );
}

void CWhiteboardDlg::OnBigText()
{
	m_lf.lfHeight = 32;
	setFontAndColor();
}

void CWhiteboardDlg::OnUpdateBigText(CCmdUI* pCmdUI)
{
	pCmdUI->SetCheck( m_lf.lfHeight == 32 );
}

int CWhiteboardDlg::getCurrentDisplay()
{
	return m_nCurSel;
}

void CWhiteboardDlg::setCurrentDisplay( int nIndex )
{
	if( nIndex < 0 || nIndex > NUMBER_OF_WHITEBOARD )
	{
		ASSERT(FALSE);
		return;
	}
	if( nIndex == m_nCurSel )
	{
		return;
	}
	m_whiteboards[m_nCurSel]->ShowWindow(FALSE);
	m_whiteboards[nIndex]->ShowWindow(TRUE);
	m_nCurSel = nIndex;
	CString str;
	str.Format( "%d", m_nCurSel + 1 );
	GetDlgItem(IDC_CURRENTBOARD)->SetWindowText( str );

	//设置白板序号
	m_os->setPos(0);
	m_os->writeU32(m_nCurSel);
}

void CWhiteboardDlg::sendWhiteboardData( char* buff, int nLen )
{
	CMeetingRoomFrame* pRoom = (CMeetingRoomFrame*)GetParentOwner();
	if( pRoom->getConfUserCount() > 1 )
	{
		m_pTransModel->sendWhiteboardData( pRoom->getConferenceId(), buff, nLen );
	}
}

void CWhiteboardDlg::recvWhiteboardData( const char* buff, int nLen )
{
	if( !m_bShowWhiteboard )
		return;

	this->m_is->setData( buff, nLen );

	int nBoardNum = m_is->readU32();
	int nType = m_is->readU32();
	COLORREF crColor = m_is->readU32();
	int nWidth = m_is->readU32();
	switch( nType )
	{
	case IDT_LINE:
	case IDT_RECTANGLE:
	case IDT_ELLIPSE:
	case IDT_CIRCLE:
		{
			BOOL bFill = m_is->readU32();
			CDrawObject* pObject = NULL;
			switch( nType )
			{
			case IDT_LINE:
				pObject = new CDrawLine( crColor, nWidth );
				break;
			case IDT_RECTANGLE:
				pObject = new CDrawRect( crColor, nWidth, bFill );
				break;
			case IDT_ELLIPSE:
				pObject = new CDrawEllipse( crColor, nWidth, bFill );
				break;
			case IDT_CIRCLE:
				pObject = new CDrawCircle( crColor, nWidth, bFill );
				break;
			default:
				break;
			}
			int x = m_is->readU16();
			int y = m_is->readU16();
			pObject->NewPoint( x, y );
			x = m_is->readU16();
			y = m_is->readU16();
			pObject->AddPoint( x, y );
			this->m_whiteboards[nBoardNum]->appendDraw( pObject );
			break;
		}
	case IDT_CURVE:
	case IDT_POLY:
		{
			int nPointNum = m_is->readU32();
			CDrawObject* pObject = NULL;
			if( nType == IDT_CURVE )
				pObject = new CDrawCurve( crColor, nWidth );
			else
				pObject = new CDrawPoly( crColor, nWidth );
			for( int i = 0; i < nPointNum; i++ )
			{
				int x = m_is->readU16();
				int y = m_is->readU16();
				pObject->AddPoint( x, y );
			}
			this->m_whiteboards[nBoardNum]->appendDraw( pObject );
			break;
		}
	case IDT_TEXT:
		{
			LOGFONT lf;
			memset( &lf, 0, sizeof(LOGFONT) );
			m_is->readBytes( &lf, sizeof(LOGFONT) );
			int x1 = m_is->readU16();
			int y1 = m_is->readU16();
			int x2 = m_is->readU16();
			int y2 = m_is->readU16();
			char* ptr = m_is->readString();
			CString str = CString( ptr );
			delete ptr;
			CDrawObject* pObject = new CDrawText( lf, str, crColor );
			pObject->NewPoint( x1, y1 );
			pObject->AddPoint( x2, y2 );
			this->m_whiteboards[nBoardNum]->appendDraw( pObject );
			break;
		}
	default:
		ASSERT(FALSE);
		break;
	}
}

void CWhiteboardDlg::setTransmodel( TransModel* pTransModel )
{
	m_pTransModel = pTransModel;
}

void CWhiteboardDlg::OnBnClickedPrevboardBtn()
{
	int nPrev = ( m_nCurSel == 0 ) ? ( NUMBER_OF_WHITEBOARD - 1 ) : ( m_nCurSel - 1 );
	this->setCurrentDisplay( nPrev );
}

void CWhiteboardDlg::OnBnClickedNextboardBtn()
{
	int nNext = ( m_nCurSel == NUMBER_OF_WHITEBOARD - 1 ) ? 0 : ( m_nCurSel + 1 );
	this->setCurrentDisplay( nNext );
}

void CWhiteboardDlg::setFontAndColor()
{
	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		m_whiteboards[i]->setTextFontAndColor(m_lf, m_crCurrentColor);
	}
}

void CWhiteboardDlg::hideTextWindow()
{
	for( int i = 0; i < NUMBER_OF_WHITEBOARD; i++ )
	{
		m_whiteboards[i]->hideTextWindow();
	}
}
