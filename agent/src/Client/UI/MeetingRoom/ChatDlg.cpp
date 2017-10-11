// ChatDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "chatdlg.h"
#include "..\MainFrm\MainFrm.h"
#include "roommainfrm.h"
#include ".\chatdlg.h"
#include "ChatTab.h"
#include "..\..\Model\LServer.h"
#include "..\..\..\Common\Common\PathHelper\PathHelper.h"
#include "..\..\..\Common\Common\XML\XMLParser.h"

// CChatDlg 对话框

IMPLEMENT_DYNAMIC(CChatDlg, CDialog)
CChatDlg::CChatDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CChatDlg::IDD, pParent), CTabItem( this )
{
	m_SplitterPos = 0;
	m_cy = 0;
	m_nMinHeight = 100;
	m_bIsSendWritingMsg = false;
	m_nType = CTabItem::ChatDlg;
}

CChatDlg::~CChatDlg()
{
}

void CChatDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX,IDC_CHATMSG, m_ChatMsg);
	DDX_Control(pDX,IDC_SENDMSG, m_SendMsg);
	DDX_Control(pDX,IDC_FONT, m_btnFont);
	DDX_Control(pDX,IDC_SEND, m_btnSend);
}


BEGIN_MESSAGE_MAP(CChatDlg, CDialog)
	ON_BN_CLICKED(IDC_SEND, OnBnClickedSend)
	ON_WM_SIZE()
	ON_BN_CLICKED(IDC_FONT, OnBnClickedFont)
	ON_WM_SHOWWINDOW()
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
	ON_WM_SETFOCUS()
END_MESSAGE_MAP()


// CChatDlg 消息处理程序


BOOL CChatDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_ChatMsg.LimitText(0xFFFFFFFF);
	m_SendMsg.LimitText(1024);
	m_ChatMsg.Init( FALSE, this );
	m_SendMsg.Init( TRUE, this );

	m_Splitter.Create(NULL, WS_CHILD | SS_NOTIFY, CRect(0,0,0,0), this, IDC_STATIC);
	m_Splitter.ShowWindow(SW_SHOW);
	m_SplitterPos = m_nMinHeight;
	m_cy = m_nMinHeight;

	m_btnSend.setBitmaps(IDB_SENDMSG, IDB_SENDMSG, IDB_SENDMSG);
	m_btnSend.setTextColor( RGB(128, 128, 128), RGB(10, 100, 190) );
	m_btnSend.setTextUnderline( FALSE );
	m_btnFont.setBitmaps(IDB_FONT, IDB_FONT2, IDB_FONT3);
	m_brush.CreateSolidBrush(RGB(216, 225, 240));

	CBitmap bmp;
	bmp.LoadBitmap( IDB_SCROLLBAR );
	m_hBmpScrollBar = (HBITMAP) bmp.Detach();
	SkinWndScroll( &m_SendMsg, m_hBmpScrollBar );
	SkinWndScroll( &m_ChatMsg, m_hBmpScrollBar );

	this->GetDlgItem(IDC_SENDMSG)->SetFocus();
	return FALSE;  // return TRUE unless you set the focus to a control
}

void CChatDlg::OnOK()
{
	if(GetWindowLong(GetFocus()->m_hWnd,GWL_ID)==IDC_SENDMSG)
		OnBnClickedSend();
}

void CChatDlg::OnCancel()
{
}

void CChatDlg::OnBnClickedSend()
{
	UpdateData();
	CString strMsg;
	m_SendMsg.GetWindowText( strMsg );
	CString str = strMsg;
	str.Trim();
	if ( strMsg == "" || str == "" )
		return;

	str = strMsg;
	strMsg = m_SendMsg.GetRTF();
	CMeetingRoomFrame* room = (CMeetingRoomFrame*)GetParentOwner();

	// 发送消息
	room->sendChatMsg(strMsg, m_ChatWithId);
		
	// 在聊天信息框中显示发送的消息
	CString name = room->getSelfName().c_str();
	CTime cur = CTime::GetCurrentTime();
	CString date = cur.Format( "%Y-%m-%d" );
	CString time = cur.Format("%H:%M:%S");
	int n1 = m_ChatMsg.GetLineCount();
	CHARFORMAT cf;
	m_ChatMsg.GetDefaultCharFormat(cf);
	cf.dwMask = cf.dwMask|CFM_COLOR;
	cf.crTextColor = RGB( 0, 128, 128 );
	m_ChatMsg.AppendText( &cf, name + " (" + time + ") 说:\r\n" );
	m_ChatMsg.SetRTF(strMsg);
	int n2 = m_ChatMsg.GetLineCount();
	m_ChatMsg.LineScroll(n2-n1);
	m_SendMsg.SetWindowText("");
	m_SendMsg.SetFocus();
	UpdateData(FALSE);
    sendWritingMessageFlag();

    //保存到文件
	CString file;
	CString receiver;
	if( this->m_ChatWithId == 0 )
	{
		file = PathHelper::getConferenceMsgPath( room->getSelfUserName().c_str(), room->getServerIP().c_str() );
		file = file + "\\";
		file = file + room->getConferenceName().c_str();
		file = file + ".xml";
		receiver = "所有人";
		this->saveChatMsg( date, time, name, receiver, str, file );

		vector<__int64> v;
		room->getConferneceUsers( v );
		CString path = PathHelper::getChatMsgPath( room->getSelfUserName().c_str(), room->getServerIP().c_str() );
		path = path + "\\";
		for( unsigned int i = 0; i < v.size(); i++ )
		{
			file = path;
			file = file + room->getUserNameById( v.at(i) ).c_str();
			file = file + ".xml";
			this->saveChatMsg( date, time, name, receiver, str, file );
		}
	}
	else
	{
		file = PathHelper::getChatMsgPath( room->getSelfUserName().c_str(), room->getServerIP().c_str() );
		receiver = room->getUserNameById( this->m_ChatWithId ).c_str();
		file = file + "\\" + receiver + ".xml";
		this->saveChatMsg( date, time, name, receiver, str, file );
	}
}

void CChatDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	m_SplitterPos = cy - m_cy;
	layout();
	//UpdateWindow();
}

void CChatDlg::OnBnClickedFont()
{
	m_SendMsg.OnSelectfont();
}

void CChatDlg::SplitterMoved(int pos)
{
	POINT cur;
	GetCursorPos(&cur);
	ScreenToClient(&cur);
	SetSplitterPos(cur.y);
	m_SplitterPos = cur.y ;
}

void CChatDlg::SetSplitterPos(int pos)
{
	CRect clientRect;
	GetClientRect(&clientRect);
	m_SplitterPos = pos;
	m_Splitter.MoveWindow(0, m_SplitterPos, clientRect.Width(), 6);
}

void CChatDlg::UpdateSizes()
{
	((CChatTab*) GetParent())->setSplitterPosition( this->m_SplitterPos );
	//ShowWindow(FALSE);
	//ShowWindow(TRUE);
}

void CChatDlg::OnShowWindow(BOOL bShow, UINT nStatus)
{
	CDialog::OnShowWindow(bShow, nStatus);
	if(bShow)
		m_SendMsg.SetFocus();
}

void CChatDlg::setCharFormat( CHARFORMAT cf )
{
	this->m_SendMsg.SetWordCharFormat( cf );
	this->m_SendMsg.SetDefaultCharFormat( cf );
}

void CChatDlg::sendWritingMessageFlag()
{
	int len = m_SendMsg.GetTextLength();
	if( !m_bIsSendWritingMsg && len > 0 )
	{
		this->m_bIsSendWritingMsg = true;
		((CMeetingRoomFrame*)GetParentOwner())->sendWritingMessageFlag( m_ChatWithId, this->m_bIsSendWritingMsg );
	}
	else if( m_bIsSendWritingMsg && len == 0 )
	{
		this->m_bIsSendWritingMsg = false;
		((CMeetingRoomFrame*)GetParentOwner())->sendWritingMessageFlag( m_ChatWithId, this->m_bIsSendWritingMsg );
	}
}

void CChatDlg::setWritingMessageFlag( __int64 userId, bool bFlag )
{
	CMeetingRoomFrame* room = (CMeetingRoomFrame*)GetParentOwner();
	CString str = room->getUserNameById( userId ).c_str();
	TRACE2( "m_strWritingUser=\"%s\", %s\n", m_strWritingUser, bFlag?"true":"false" );
	if( bFlag )
	{
		if( m_strWritingUser == "" )
			m_strWritingUser = str;
		else
			m_strWritingUser = m_strWritingUser + ", " + str;
		GetDlgItem( IDC_WRITINGMSG )->SetWindowText( m_strWritingUser+" 正在输入消息......" );
	}
	else 
	{
		int pos;
		pos = m_strWritingUser.Find( str );
		if( pos != -1 )
		{
			m_strWritingUser.Delete( pos, str.GetLength() );
			if( pos > 0 )
				m_strWritingUser.Delete( pos - 2, 2 );
			else
				m_strWritingUser.Delete( pos, 2 );
			if( m_strWritingUser != "" )
				GetDlgItem( IDC_WRITINGMSG )->SetWindowText( m_strWritingUser+" 正在输入消息......" );
			else
				GetDlgItem( IDC_WRITINGMSG )->SetWindowText( "" );
		}
	}
	TRACE1( "m_strWritingUser=\"%s\"\n", m_strWritingUser );
}

void CChatDlg::appendMsg(CString name, CString msg)
{
	int n1 = m_ChatMsg.GetLineCount();
	//得到接收时间
	CTime cur = CTime::GetCurrentTime();
	CString date = cur.Format("%Y-%m-%d");
	CString time = cur.Format("%H:%M:%S");

	//显示发送者的名字和接收时间
	CHARFORMAT cf;
	m_ChatMsg.GetDefaultCharFormat(cf);
	cf.dwMask = cf.dwMask|CFM_COLOR;
	cf.crTextColor = RGB( 100, 10, 100 );
	m_ChatMsg.AppendText( &cf, name + " (" + time + ") 说:\r\n" );
	
	long nFirst = m_ChatMsg.GetTextLengthEx(GTL_NUMCHARS, 1200);
	//显示接收消息
	m_ChatMsg.SetRTF(msg);
	int n2 = m_ChatMsg.GetLineCount();
	long nLast = m_ChatMsg.GetTextLengthEx(GTL_NUMCHARS, 1200);
	m_ChatMsg.GetTextRange( nFirst, nLast, msg );
	m_ChatMsg.LineScroll(n2-n1);

	CMeetingRoomFrame* room = (CMeetingRoomFrame*)GetParentOwner();
	CString file;
	CString receiver;
	if( this->m_ChatWithId == 0 )
	{
		file = PathHelper::getConferenceMsgPath( room->getSelfUserName().c_str(), room->getServerIP().c_str() );
		file = file + "\\";
		file = file + room->getConferenceName().c_str();
		file = file + ".xml";
		receiver = "所有人";
		this->saveChatMsg( date, time, name, receiver, msg, file );
	}
	else
		receiver = room->getSelfName().c_str();

	file = PathHelper::getChatMsgPath( room->getSelfUserName().c_str(), room->getServerIP().c_str() );
	file = file + "\\" + name + ".xml";
	this->saveChatMsg( date, time, name, receiver, msg, file );
}

void CChatDlg::appendSysMsg( CString msg )
{
	int n1 = m_ChatMsg.GetLineCount();
	CHARFORMAT cf;
	m_ChatMsg.GetDefaultCharFormat(cf);
	cf.dwMask = cf.dwMask|CFM_COLOR|CFM_SIZE;
	cf.crTextColor = RGB( 0, 128, 128 );
	cf.yHeight = 180;
	m_ChatMsg.AppendText( &cf, "---"+msg+"--- " + "\r\n" );
	int n2 = m_ChatMsg.GetLineCount();
	m_ChatMsg.LineScroll(n2-n1);
}

void CChatDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect(&rc);
	CRect rcTop = rc;
	rcTop.bottom = 3;
	dc.FillSolidRect(rcTop, RGB(186, 197, 225));
	rc.top = 1;
	dc.FillSolidRect(rc, RGB(255, 255, 255));

	CRect rcMid = rc;
	CRect rcEdit;
	m_ChatMsg.GetWindowRect(&rcEdit);
	ScreenToClient(rcEdit);
	rcMid.top = rcEdit.bottom + 1;
	m_SendMsg.GetWindowRect( &rcEdit );
	ScreenToClient(rcEdit);
	rcMid.bottom = rcEdit.top - 2;
	dc.FillSolidRect( rcMid, RGB(186, 197, 225) );
	rcMid.DeflateRect(0,1,0,1);
	dc.FillSolidRect(rcMid, RGB(216, 225, 240));

	CDC bkDC;
	bkDC.CreateCompatibleDC(&dc);
	CBitmap back;
	back.LoadBitmap(IDB_CHATDLGBG);
	bkDC.SelectObject( &back );
	int x = 0;
	while( x < rc.Width() ) 
	{
			dc.BitBlt( x, rcMid.top + 6, 100, 24, &bkDC, 0, 0, SRCCOPY);
 			x += 100;
 	}

	CRect rcText = rcMid;
	rcText.DeflateRect(2, 8, 25, 2);
	LOGFONT lf;
	CFont* font = GetFont();
	font->GetLogFont(&lf);
	lf.lfHeight = 15;
	CFont font1;
	font1.CreateFontIndirect(&lf);
	CFont* oldFont = dc.SelectObject(&font1);
	int nOldMode = dc.SetBkMode(TRANSPARENT);
	dc.SetTextColor( RGB(128, 128, 128) );
	dc.DrawText( "在下面框中输入文字, 按回车发送, Ctrl+回车换行", rcText, DT_LEFT|DT_END_ELLIPSIS );
	dc.SelectObject( oldFont );
	dc.SetBkMode( nOldMode );

	CRect rcBottom = rc;
	rcBottom.top = rc.bottom - 19;
	dc.FillSolidRect( rcBottom, RGB(186, 197, 225) );
	rcBottom.top = rc.bottom - 18;
	dc.FillSolidRect( rcBottom, RGB(216, 225, 240) );
	m_SendMsg.Invalidate();
	m_SendMsg.UpdateWindow();
	m_ChatMsg.Invalidate();
	m_ChatMsg.UpdateWindow();
	this->m_Splitter.InvalidateRect( NULL );
	// 不为绘图消息调用 CDialog::OnPaint()
}

HBRUSH CChatDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_SENDTO 
		|| pWnd->GetDlgCtrlID() == IDC_WRITINGMSG )
	{
		pDC->SetTextColor(RGB(0, 128, 128));
		pDC->SetBkMode(TRANSPARENT);
		hbr = m_brush;
	}	return hbr;
}

void CChatDlg::layout()
{
	CRect rc;
	GetClientRect(&rc);
	int cx = rc.Width();
	int cy = rc.Height();

	if( m_SplitterPos < m_nMinHeight )
		m_SplitterPos = m_nMinHeight;
	if(m_SplitterPos > cy - 110)
		m_SplitterPos = cy - 110;

	if(m_ChatMsg.GetSafeHwnd())
	{
		CWnd *pFrm = m_ChatMsg.GetParent()->GetParent();
		pFrm->MoveWindow(4, 3, cx - 5, m_SplitterPos - 5);
		//m_ChatMsg.MoveWindow(4, 3, cx - 5, m_SplitterPos - 5);
	}
	if(m_SendMsg.GetSafeHwnd())
	{
		CWnd *pFrm = m_SendMsg.GetParent()->GetParent();
		pFrm->MoveWindow(4, m_SplitterPos + 33, cx - 70, cy - m_SplitterPos - 54);
		//m_SendMsg.MoveWindow(4, m_SplitterPos + 33, cx - 70, cy - m_SplitterPos - 54);
	}
	if(GetDlgItem(IDC_FONT)->GetSafeHwnd())
		GetDlgItem(IDC_FONT)->MoveWindow(cx-23, m_SplitterPos + 8, 20, 20);
	if(GetDlgItem(IDC_SENDTO)->GetSafeHwnd())
		GetDlgItem(IDC_SENDTO)->MoveWindow(30, m_SplitterPos + 10, cx - 35, 20);
	if(GetDlgItem(IDC_SEND)->GetSafeHwnd())
	{
		CRect rcSendMsg;
		m_SendMsg.GetWindowRect( &rcSendMsg );
		ScreenToClient( &rcSendMsg );
		GetDlgItem(IDC_SEND)->MoveWindow(cx - 63, rcSendMsg.top + (rcSendMsg.Height()-40)/2, 60, 40);
	}
	if(m_Splitter.GetSafeHwnd())
		m_Splitter.MoveWindow(0, m_SplitterPos, cx, 6);
	if(GetDlgItem(IDC_WRITINGMSG)->GetSafeHwnd())
		GetDlgItem(IDC_WRITINGMSG)->MoveWindow( 3, cy - 18, cx - 5, 18);
		
	m_cy = cy - m_SplitterPos;
	InvalidateRect( NULL );
	if( m_btnFont.GetSafeHwnd() )
		m_btnFont.InvalidateRect( NULL );
	if( m_btnSend.GetSafeHwnd() )
		m_btnSend.InvalidateRect( NULL );
}

void CChatDlg::saveChatMsg( CString date, CString time, CString sender, CString receiver, CString msg, CString file )
{
	CXMLParser xml;
	if( !xml.Load( file ) )
	{
		xml.SetDoc( "<?xml-stylesheet type=\"text/xsl\" href=\"..\\..\\message.xsl\"?><lyvc></lyvc>" );
		//xml.SetDoc( "<?xml version=\"1.0\" encoding=\"GB2312\"?><lyvc></lyvc>" );
		xml.Save( file );
	}
	xml.AddElem( "message" );
	xml.AddChildElem( "date", date );
	xml.AddChildElem( "time", time );
	xml.AddChildElem( "conference", ((CMeetingRoomFrame*)GetParentOwner())->getConferenceName().c_str() );
	xml.AddChildElem( "from", sender );
	xml.AddChildElem( "to", receiver );
	xml.AddChildElem( "text", msg );
	xml.Save( file );
}

void CChatDlg::OnSetFocus(CWnd* pOldWnd)
{
	CDialog::OnSetFocus(pOldWnd);

	this->m_SendMsg.SetFocus();
}
