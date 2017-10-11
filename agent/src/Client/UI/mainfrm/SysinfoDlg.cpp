// SysinfoDlg.cpp : implementation file
//

#include "stdafx.h"
#include "FLVCC.h"
#include "SysinfoDlg.h"
#include ".\sysinfodlg.h"
#include "MainFrm.h"
#include "ShowXMLDlg.h"
#include "userdefinedmessage.h"
#include "..\..\..\Common\Common\PathHelper\PathHelper.h"
#include "..\..\..\Common\Common\XML\XMLParser.h"
#include "..\..\Model\LServer.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define MESSAGE_ITEM_NUMBER 100
/////////////////////////////////////////////////////////////////////////////
// CSysinfoDlg dialog


CSysinfoDlg::CSysinfoDlg(CMainFrame* pMainFrame, CWnd* pParent /*=NULL*/)
	: CDialog(CSysinfoDlg::IDD, pParent)
{
	m_pMainFrame = pMainFrame;
}

void CSysinfoDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX,IDC_SYSINFO_TREE,m_TreeCtrl);
}

BEGIN_MESSAGE_MAP(CSysinfoDlg, CDialog)
	ON_WM_SIZE()
	ON_WM_PAINT()
	ON_NOTIFY(NM_DBLCLK, IDC_SYSINFO_TREE, OnNMDblclkSysinfoTree)
	ON_NOTIFY(NM_RCLICK, IDC_SYSINFO_TREE, OnNMRclickSysinfoTree)
	ON_COMMAND(ID_LOGINSERVER, OnLoginserver)
	ON_COMMAND(ID_CANCELLOGINSERVER, OnCancelloginserver)
	ON_COMMAND(ID_LOGOUTSERVER, OnLogoutserver)
	ON_COMMAND(ID_REGISTERNEW, OnRegisternew)
	ON_COMMAND(ID_LOGINANOTHER, OnLoginanother)
	ON_COMMAND(ID_ADDSERVER, OnAddserver)
	ON_COMMAND(ID_CHECKSERVER, OnCheckserver)
	ON_COMMAND(ID_REMOVESERVER, OnRemoveserver)
	ON_NOTIFY(TVN_SELCHANGED, IDC_SYSINFO_TREE, OnTvnSelchangedSysinfoTree)
	ON_WM_SHOWWINDOW()
	ON_COMMAND(ID_LOOKMESSAGE, OnLookmessage)
	ON_COMMAND(ID_DELMESSAGE, OnDelmessage)
	ON_COMMAND(ID_DELALLMESSAGE, OnDelallmessage)
	ON_WM_SETFOCUS()
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CSysinfoDlg message handlers

BOOL CSysinfoDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_ImageList.Create(20, 20, ILC_COLOR16, 4, 1);
	CBitmap bm;
	bm.LoadBitmap(IDB_SYSMSG_TREE);
	m_ImageList.Add(&bm, RGB(0, 0, 0));
	m_TreeCtrl.SetImageList(&m_ImageList, TVSIL_NORMAL);
	m_TreeCtrl.SetBkImage(IDB_CONTACTLISTBK);

	CBitmap bmp;
	bmp.LoadBitmap( IDB_SCROLLBAR );
	m_hBmpScrollBar = (HBITMAP) bmp.Detach();
	SkinWndScroll( &m_TreeCtrl, m_hBmpScrollBar );

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CSysinfoDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rcClient;
	GetClientRect(&rcClient);

	// Create a compatible memory DC 
	CDC memDC;
	memDC.CreateCompatibleDC( &dc );
	// Select a compatible bitmap into the memory DC
	CBitmap bitmap;
	bitmap.CreateCompatibleBitmap( &dc, rcClient.Width(), rcClient.Height() );
	memDC.SelectObject( &bitmap );
	memDC.FillSolidRect( CRect(0, rcClient.bottom - 10, rcClient.right, rcClient.bottom), RGB(228, 240, 254) );
	
	CPen pen;
	pen.CreatePen( PS_SOLID, 1, RGB(186, 197, 225) );
	memDC.SelectObject( &pen );
	CRect rc = rcClient;
	rc.top = -10;
	memDC.RoundRect( rc, CPoint(10, 10) );

	CDC bkDC;
	bkDC.CreateCompatibleDC(&dc);
	CBitmap corner;
	corner.LoadBitmap( IDB_CORNER );
	bkDC.SelectObject( &corner );
	memDC.BitBlt( rc.Width() - 4, 0, 4, 9, &bkDC, 4, 0, SRCCOPY);

	dc.BitBlt( 0, 0, rcClient.right, rcClient.bottom, &memDC, 0, 0, SRCCOPY);
}

void CSysinfoDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	if(m_TreeCtrl.GetSafeHwnd())
	{
		CWnd *pFrm = m_TreeCtrl.GetParent()->GetParent();
		pFrm->MoveWindow(1, 9, cx - 2, cy - 18);
		//m_TreeCtrl.MoveWindow(1, 9, cx - 2, cy - 18);
	}
}

void CSysinfoDlg::OnShowWindow(BOOL bShow, UINT nStatus)
{
	CDialog::OnShowWindow(bShow, nStatus);
	LServer* pServer = this->getCurSelServer();
	if( bShow && pServer != NULL )
		m_pMainFrame->SetTaskIcon( pServer, FALSE );
}

void CSysinfoDlg::OnSetFocus(CWnd* pOldWnd)
{
	CDialog::OnSetFocus(pOldWnd);
	this->m_TreeCtrl.SetFocus();
}

void CSysinfoDlg::OnNMDblclkSysinfoTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( dwData & ITEM_IS_MESSAGE )
	{
		lookMessage( getCurSelServer(), HIWORD(dwData) );
	}
	*pResult = 0;
}

void CSysinfoDlg::OnNMRclickSysinfoTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	*pResult = 0;
	LPNMTVDISPINFO pTVDispInfo = reinterpret_cast<LPNMTVDISPINFO>(pNMHDR);
	TV_DISPINFO     *ptvinfo;
	ptvinfo = (TV_DISPINFO *)pNMHDR;
	
	CPoint pt;
	GetCursorPos(&pt);
	m_TreeCtrl.ScreenToClient(&pt);
	HTREEITEM hItem;
	hItem = m_TreeCtrl.HitTest(pt);

	CMenu menu;
	CMenu* submenu = NULL;

	//在空白处右键点击
	if( hItem == NULL )
	{
		menu.LoadMenu(IDR_SERVER);
		submenu = menu.GetSubMenu(0);
		for( int i = 0; i < 7; i++ )
			submenu->RemoveMenu( 0, MF_BYPOSITION );
        submenu->RemoveMenu( 1, MF_BYPOSITION );
		submenu->RemoveMenu( 1, MF_BYPOSITION );
	}
	else
	{
		m_TreeCtrl.Select(hItem,TVGN_CARET | TVGN_DROPHILITE);
		DWORD dwData = m_TreeCtrl.GetItemData(hItem);
		if( dwData & ITEM_IS_SERVER )
		{
			menu.LoadMenu(IDR_SERVER);
			submenu = menu.GetSubMenu(0);
			if( dwData & SERVER_LOGIN )
			{
				submenu->EnableMenuItem( ID_LOGINSERVER, MF_GRAYED );
				submenu->EnableMenuItem( ID_CANCELLOGINSERVER, MF_GRAYED );
				submenu->EnableMenuItem( ID_REMOVESERVER, MF_GRAYED );
			}
			if( dwData & SERVER_LOGINING )
			{
				submenu->EnableMenuItem( ID_LOGINSERVER, MF_GRAYED );
				submenu->EnableMenuItem( ID_LOGOUTSERVER, MF_GRAYED );
				submenu->EnableMenuItem( ID_REMOVESERVER, MF_GRAYED );
			}
			if( dwData & SERVER_LOGOUT )
			{
				submenu->EnableMenuItem( ID_LOGOUTSERVER, MF_GRAYED );
				submenu->EnableMenuItem( ID_CANCELLOGINSERVER, MF_GRAYED );
			}
			LServer* pServer = this->getCurSelServer();
			if( pServer != NULL )
			{
				if( pServer->isDefaultServer() )
				{
					submenu->EnableMenuItem( ID_REMOVESERVER, MF_GRAYED );
				}
			}
		}
		else if( dwData & ITEM_IS_MESSAGE )  
		{
			menu.LoadMenu(IDR_SYSMSG);
			submenu = menu.GetSubMenu(0);
		}
		else
			return;
	}
	CPoint point;
	GetCursorPos(&point);
	submenu->TrackPopupMenu(TPM_RIGHTBUTTON, point.x, point.y, this);
	submenu->DestroyMenu();
}

void CSysinfoDlg::OnTvnSelchangedSysinfoTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	*pResult = 0;
	if( this->IsWindowVisible() )
		m_pMainFrame->SetTaskIcon( this->getCurSelServer(), FALSE );
}

void CSysinfoDlg::OnLoginserver()
{
	m_pMainFrame->OnLoginServer( getCurSelServer() );
}

void CSysinfoDlg::OnCancelloginserver()
{
	m_pMainFrame->OnCancelLoginServer( getCurSelServer() );
}

void CSysinfoDlg::OnLogoutserver()
{
	m_pMainFrame->OnLogoutServer( getCurSelServer() );
}

void CSysinfoDlg::OnRegisternew()
{
	m_pMainFrame->OnRegisterNewUser( getCurSelServer() );
}

void CSysinfoDlg::OnLoginanother()
{
	m_pMainFrame->OnLoginAnother( getCurSelServer() );
}

void CSysinfoDlg::OnAddserver()
{
	m_pMainFrame->OnAddServer();
}

void CSysinfoDlg::OnCheckserver()
{
	m_pMainFrame->OnViewAndModifyServer( getCurSelServer() );
}

void CSysinfoDlg::OnRemoveserver()
{
	m_pMainFrame->OnRemoveServer( getCurSelServer() );
}

void CSysinfoDlg::notifyLeaveword(string content, string senderName, int index, string sendDate, LServer* pServer)
{
	string* msg = new string( senderName + ":" + content);
	CString sMsg = msg->c_str();
	if( sMsg.GetLength() > 100 )
	{
		sMsg = sMsg.Left(96) + "...";
	}
	// 插入到对应的服务器项下
	HTREEITEM hServer = pServer->getTreeItem( SYSMSGDLG_ITEM );
	HTREEITEM hParent = m_TreeCtrl.GetChildItem( hServer );
	ASSERT( hParent != NULL );
	if( hParent == NULL )
		return;

	setChildrenNum( hParent, 1 );
	HTREEITEM hItem = m_TreeCtrl.InsertItem( _T(sMsg), 2, 2, hParent, TVI_FIRST );
	DWORD dwData = MAKELONG( ITEM_IS_MESSAGE, LEAVEWORD );
	m_TreeCtrl.SetItemData( hItem, dwData );

	// 保存到本地文件
	string fileName = PathHelper::getLeavewordFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str(), true );
	CXMLParser xml;
	if( !xml.Load( fileName.c_str() ) )
	{
		xml.SetDoc("<?xml-stylesheet type=\"text/xsl\" href=\"../leaveword.xsl\"?><lyvc></lyvc>");
		xml.Save(fileName.c_str());
	}
	xml.InsertElem("message");
	xml.AddChildElem("index", "0");
	xml.AddChildElem("date", sendDate.c_str());
	xml.AddChildElem("sender", senderName.c_str());
	xml.AddChildElem("content", content.c_str());
	xml.Save(fileName.c_str());

	::PostMessage(GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, CONTACT_MSG, (LPARAM)msg);
}
	
void CSysinfoDlg::notifyConfNotice(string title, string content, string sendDate, __int64 confId, LServer* pServer)
{
	// 插入到对应的服务器项下
	HTREEITEM hServer = pServer->getTreeItem( SYSMSGDLG_ITEM );
	HTREEITEM hParent = m_TreeCtrl.GetChildItem( hServer );
	hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
	ASSERT( hParent != NULL );
	if( hParent == NULL )
		return;

	setChildrenNum( hParent, 1 );
	HTREEITEM hItem = m_TreeCtrl.InsertItem( _T(title.c_str()), 2, 2, hParent, TVI_FIRST );
	DWORD dwData = MAKELONG( ITEM_IS_MESSAGE, CONFNOTICE );
	m_TreeCtrl.SetItemData( hItem, dwData );

	// 保存到本地文件
	string fileName = PathHelper::getConfNoticeFileFullName( pServer->getUserName().c_str(), 
															pServer->getIP().c_str(), true );
	CXMLParser xml;
	if( !xml.Load( fileName.c_str() ) )
	{
		xml.SetDoc("<?xml-stylesheet type=\"text/xsl\" href=\"../confnotice.xsl\"?><lyvc></lyvc>");
		xml.Save(fileName.c_str());
	}
	xml.InsertElem("message");
	char buf[65];
	_i64toa( confId, buf, 10 );
	xml.AddChildElem("confId", buf );
	xml.AddChildElem("date", sendDate.c_str());
	xml.AddChildElem("title", title.c_str());
	xml.AddChildElem("content", content.c_str());
	xml.Save(fileName.c_str());

	string* msg = new string( "会议通知:" + title);
	::PostMessage(GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, CONTACT_MSG, (LPARAM)msg);
}

void CSysinfoDlg::notifySystemMsg(string title, string content, string sendDate, LServer* pServer)
{
	// 插入到对应的服务器项下
	HTREEITEM hServer = pServer->getTreeItem( SYSMSGDLG_ITEM );
	HTREEITEM hParent = m_TreeCtrl.GetChildItem( hServer );
	hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
	hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
	ASSERT( hParent != NULL );
	if( hParent == NULL )
		return;

	setChildrenNum( hParent, 1 );
	HTREEITEM hItem = m_TreeCtrl.InsertItem( _T(title.c_str()), 2, 2, hParent, TVI_FIRST );
	DWORD dwData = MAKELONG( ITEM_IS_MESSAGE, SYSTEMMSG );
	m_TreeCtrl.SetItemData( hItem, dwData );

	// 保存到本地文件
	string fileName = PathHelper::getSystemMessageFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str(), true );
	CXMLParser xml;
	if( !xml.Load( fileName.c_str() ) )
	{
		xml.SetDoc("<?xml-stylesheet type=\"text/xsl\" href=\"../systemmsg.xsl\"?><lyvc></lyvc>");
		xml.Save(fileName.c_str());
	}
	xml.InsertElem("message");
	xml.AddChildElem("date", sendDate.c_str());
	xml.AddChildElem("title", title.c_str());
	xml.AddChildElem("content", content.c_str());
	xml.Save(fileName.c_str());

	string* msg = new string( "系统公告:" + title);
	::PostMessage(GetApp()->m_pMainWnd->GetSafeHwnd(), WM_RECEIVED_SYSMSG, CONTACT_MSG, (LPARAM)msg);
}

LServer* CSysinfoDlg::getCurSelServer()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return 0;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( dwData & ITEM_IS_SERVER )
	{
		UINT serverId = dwData >> 16;
		return m_pMainFrame->getServerById( serverId );
	}
	hItem = m_TreeCtrl.GetParentItem( hItem );
	while( hItem != NULL )
	{
		DWORD dwData = m_TreeCtrl.GetItemData( hItem );
		if( dwData & ITEM_IS_SERVER )
		{
			UINT serverId = dwData >> 16;
			return m_pMainFrame->getServerById( serverId );
		}
		hItem = m_TreeCtrl.GetParentItem( hItem );
	}
    ASSERT( FALSE );
	return 0;
}

void CSysinfoDlg::addServer( LServer* pServer )
{
	CString sText = pServer->getServerName().c_str();
	sText = sText + "(未登录)"; 
	HTREEITEM hServer = m_TreeCtrl.InsertItem( sText );
	DWORD dwData = MAKELONG( ITEM_IS_SERVER|SERVER_LOGOUT, pServer->getServerId() );
	m_TreeCtrl.SetItemData( hServer, dwData );
	m_TreeCtrl.SetItemState( hServer, TVIS_BOLD, TVIS_BOLD );
	pServer->setTreeItem( hServer, SYSMSGDLG_ITEM );
	m_TreeCtrl.SelectItem( hServer );
}

void CSysinfoDlg::modifyServer( LServer* pServer )
{
	ASSERT( pServer );
	if( pServer == NULL )
		return;
	HTREEITEM hItem = pServer->getTreeItem( SYSMSGDLG_ITEM );
	if( hItem == NULL )
		return;
	CString sText = pServer->getServerName().c_str();
	if( pServer->getStatus() == SERVER_LOGOUT )
	{
		sText = sText + "(未登录)";
		HTREEITEM hChild = m_TreeCtrl.GetChildItem( hItem );
		while( hChild != NULL )
		{
			HTREEITEM hNext = m_TreeCtrl.GetNextSiblingItem( hChild );
			m_TreeCtrl.DeleteItem( hChild );
			hChild = hNext;
		}
	}
	else if( pServer->getStatus() == SERVER_LOGINING )
	{
		sText = sText + "(正在登录)";
		m_TreeCtrl.Expand( hItem, TVE_COLLAPSE );
	}
	else if( pServer->getStatus() == SERVER_LOGIN )
	{
		sText = sText + "(已登录)";
		m_TreeCtrl.InsertItem(_T("联系人留言(0)"), 1, 1, hItem);
		m_TreeCtrl.InsertItem(_T("会议通知(0)"), 1, 1, hItem);
		m_TreeCtrl.InsertItem(_T("系统公告(0)"), 1, 1, hItem);
		m_TreeCtrl.Expand( hItem, TVE_EXPAND );
		loadLeaveword( pServer );
		loadSystemMsg( pServer );
		loadConfNotice( pServer );
/*
notifyLeaveword("留言内容", "发送者", 0, "2001-01-01", pServer);
notifyConfNotice("通知标题", "通知内容", "2001-1-1", 21, pServer);
notifySystemMsg("公告标题", "公告内容", "2001-1-1", pServer);
*/	}
	m_TreeCtrl.SetItemText( hItem, sText );
	DWORD dwData = MAKELONG( ITEM_IS_SERVER|pServer->getStatus(), pServer->getServerId() );
	m_TreeCtrl.SetItemData( hItem, dwData );
}

void CSysinfoDlg::removeServer( LServer* pServer )
{
	HTREEITEM hItem = pServer->getTreeItem( SYSMSGDLG_ITEM );
	if( hItem == NULL )
		return;
	m_TreeCtrl.DeleteItem( hItem );
}

void CSysinfoDlg::lookMessage( LServer* pServer, int nType )
{
	CString path = "";
	CString type = "";
	switch (nType)
	{
	case LEAVEWORD:
		path = PathHelper::getLeavewordFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		type = "留言";
		break;
	case CONFNOTICE:
		path = PathHelper::getConfNoticeFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		type = "会议通知";
		break;
	case SYSTEMMSG:
		path = PathHelper::getSystemMessageFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		type = "系统公告";
		break;
	default:
		ASSERT( FALSE );
        return;
	}
	if( path == "" )
	{
		AfxMessageBox("您没有收到" + type + "！");
		return;
	}
	CXMLParser xml;
	if( xml.Load(path) )
	{
		CShowXMLDlg dlg( path, "查看" + type );
		dlg.DoModal();
	}
	else
	{
		AfxMessageBox("数据文件损坏！");
	}
}

void CSysinfoDlg::showContactMessage()
{
	//TODO: modify code here
	//this->m_TreeCtrl.Expand(this->m_hContactMsg, TVE_EXPAND);
	//this->m_TreeCtrl.SelectItem(m_TreeCtrl.GetChildItem(m_hContactMsg));
}

void CSysinfoDlg::setChildrenNum( HTREEITEM hItem, int nDelta )
{
	if( hItem == NULL || hItem == TVI_ROOT )
		return;
	CString s = m_TreeCtrl.GetItemText( hItem );
	int pos = s.Find( '(' );
	if( pos < 0 )
		return;
	CString sOld = s.Mid( pos + 1, s.GetLength() - pos - 2 );
	int num = atoi( sOld ) + nDelta;
	CString sNew;
	sNew.Format( "%d", num );
	s.Replace( sOld, sNew );
	m_TreeCtrl.SetItemText( hItem, s );
}

int CSysinfoDlg::getItemIndex( HTREEITEM hParent, HTREEITEM hItem )
{
	if( m_TreeCtrl.ItemHasChildren( hParent ) )
	{
		HTREEITEM hChild = m_TreeCtrl.GetChildItem( hParent );
		int n = 0;
		while( hChild != NULL )
		{
			n++;
			if( hItem != hChild )
			{
				hChild = m_TreeCtrl.GetNextSiblingItem( hChild );
			}
			else
			{
				return n;
			}
		}
	}
	return 0;
}

void CSysinfoDlg::delMessageFromFile( int nIndex, int nType )
{
	LServer* pServer = this->getCurSelServer();
	if( pServer == NULL )
		return;
	CString path = "";
	switch (nType)
	{
	case LEAVEWORD:
		path = PathHelper::getLeavewordFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		break;
	case CONFNOTICE:
		path = PathHelper::getConfNoticeFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		break;
	case SYSTEMMSG:
		path = PathHelper::getSystemMessageFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		break;
	default:
		ASSERT( FALSE );
        return;
	}
	if( path == "" )
	{
		return;
	}
	CXMLParser xml;
	if( xml.Load(path) )
	{
		if( xml.RemoveMsgNodeByIndex( nIndex ) )
			xml.Save(path);
	}
}

void CSysinfoDlg::delAllMessageFromFile( int nType )
{
	LServer* pServer = this->getCurSelServer();
	if( pServer == NULL )
		return;
	CString path = "";
	switch (nType)
	{
	case LEAVEWORD:
		path = PathHelper::getLeavewordFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		break;
	case CONFNOTICE:
		path = PathHelper::getConfNoticeFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		break;
	case SYSTEMMSG:
		path = PathHelper::getSystemMessageFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
		break;
	default:
		ASSERT( FALSE );
        return;
	}
	if( path == "" )
	{
		return;
	}
	CXMLParser xml;
	if( xml.Load(path) )
	{
		if( xml.RemoveAllMsgNode() )
			xml.Save(path);
	}
}

void CSysinfoDlg::loadLeaveword( LServer* pServer )
{
	ASSERT( pServer != NULL );
	if( pServer == NULL || pServer->getUserName() == "" )
		return;
	CString path = PathHelper::getLeavewordFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
	if( path == "" )
	{
		return;
	}
	CXMLParser xml;
	if( xml.Load(path) )
	{
		HTREEITEM hServer = pServer->getTreeItem( SYSMSGDLG_ITEM );
		HTREEITEM hParent = m_TreeCtrl.GetChildItem( hServer );
		for( int i = 1; i < MESSAGE_ITEM_NUMBER; i++ )
		{
			string sender = xml.GetMsgElemTextByIndex( "sender", i );
			if( sender == "" )
				continue;
			string content = xml.GetMsgElemTextByIndex( "content", i );
			string sText = sender+string(":")+content;
			HTREEITEM hItem = m_TreeCtrl.InsertItem( _T( sText.c_str() ), 2, 2, hParent );
			DWORD dwData = MAKELONG( ITEM_IS_MESSAGE, LEAVEWORD );
			m_TreeCtrl.SetItemData( hItem, dwData );
            this->setChildrenNum( hParent, 1 );
		}
	}
}
void CSysinfoDlg::loadSystemMsg( LServer* pServer )
{
	ASSERT( pServer != NULL );
	if( pServer == NULL || pServer->getUserName() == "" )
		return;
	CString path = PathHelper::getSystemMessageFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
	if( path == "" )
	{
		return;
	}
	CXMLParser xml;
	if( xml.Load(path) )
	{
		HTREEITEM hServer = pServer->getTreeItem( SYSMSGDLG_ITEM );
		HTREEITEM hParent = m_TreeCtrl.GetChildItem( hServer );
		hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
		hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
		for( int i = 1; i < MESSAGE_ITEM_NUMBER; i++ )
		{
			string title = xml.GetMsgElemTextByIndex( "title", i );
			if( title == "" )
				continue;
			HTREEITEM hItem = m_TreeCtrl.InsertItem( _T( title.c_str() ), 2, 2, hParent );
			DWORD dwData = MAKELONG( ITEM_IS_MESSAGE, SYSTEMMSG );
			m_TreeCtrl.SetItemData( hItem, dwData );
            this->setChildrenNum( hParent, 1 );
		}
	}
}

void CSysinfoDlg::loadConfNotice( LServer* pServer )
{
	ASSERT( pServer != NULL );
	if( pServer == NULL || pServer->getUserName() == "" )
		return;
	CString path = PathHelper::getConfNoticeFileFullName( pServer->getUserName().c_str(), pServer->getIP().c_str() );
	if( path == "" )
	{
		return;
	}
	CXMLParser xml;
	if( xml.Load(path) )
	{
		HTREEITEM hServer = pServer->getTreeItem( SYSMSGDLG_ITEM );
		HTREEITEM hParent = m_TreeCtrl.GetChildItem( hServer );
		hParent = m_TreeCtrl.GetNextSiblingItem( hParent );
		for( int i = 1; i < MESSAGE_ITEM_NUMBER; i++ )
		{
			string title = xml.GetMsgElemTextByIndex( "title", i );
			if( title == "" )
				continue;
			HTREEITEM hItem = m_TreeCtrl.InsertItem( _T( title.c_str() ), 2, 2, hParent );
			DWORD dwData = MAKELONG( ITEM_IS_MESSAGE, CONFNOTICE );
			m_TreeCtrl.SetItemData( hItem, dwData );
            this->setChildrenNum( hParent, 1 );
		}
	}
}

void CSysinfoDlg::OnLookmessage()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( dwData & ITEM_IS_MESSAGE )
	{
		lookMessage( getCurSelServer(), HIWORD(dwData) );
	}
}

void CSysinfoDlg::OnDelmessage()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( !(dwData & ITEM_IS_MESSAGE) )
		return;
	this->delMessageFromFile( this->getItemIndex( m_TreeCtrl.GetParentItem(hItem), hItem ), HIWORD(dwData) );
	HTREEITEM hParent = m_TreeCtrl.GetParentItem( hItem );
	m_TreeCtrl.DeleteItem( hItem );
	this->setChildrenNum( hParent, -1 );
}

void CSysinfoDlg::OnDelallmessage()
{
	HTREEITEM hItem = this->m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return;
	DWORD dwData = m_TreeCtrl.GetItemData( hItem );
	if( !(dwData & ITEM_IS_MESSAGE) )
		return;
	this->delAllMessageFromFile( HIWORD(dwData) );
	HTREEITEM hParent = m_TreeCtrl.GetParentItem( hItem );
	HTREEITEM hChild = m_TreeCtrl.GetChildItem( hParent );
	while( hChild != NULL )
	{
		HTREEITEM hNext = m_TreeCtrl.GetNextSiblingItem( hChild );
		m_TreeCtrl.DeleteItem( hChild );
		hChild = hNext;
		this->setChildrenNum( hParent, -1 );
	}
}
