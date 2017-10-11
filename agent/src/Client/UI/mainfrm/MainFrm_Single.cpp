// MainFrm.cpp : implementation of the CMainFrame class
//

#include "stdafx.h"

#include "FLVCC.h"
#include "MainFrm.h"
#include ".\mainfrm.h"
#include "ContactDlg.h"
#include "ConferenceDlg.h"
#include "SysinfoDlg.h"
#include "ContactListDlg.h"
#include "ServerDlg.h"
#include "GetNamePasswordDlg.h"
#include "AdBGDlg.h"
#include "RegisterUserDlg.h"
#include "UserInfoDlg.h"
#include "..\pub\ImageTabWnd.h"
#include "..\MeetingRoom\RoomMainFrm.h"
#include "OptionDlg.h"
#include "TaskbarNotifier.h"
#include "UserDefinedMessage.h"
#include "WebLauncher\WebLauncher\ConferenceInfo.h"
#include "Common\Common\RegistryConstant\RegistryConstant.h"
#include "Common\Common\PathHelper\PathHelper.h"
#include "Common\Common\XML\XMLParser.h"
#include "ShowXMLDlg.h"
#include "..\AVWizard\AVConfigWizard.h"
#include "Video/VideoCapture/TklSdk.h"
#include "Video\VideoCapture\VideoCaptureMgr.h"
#include "../../../xvid/xvidcore-1.1.0/src/xvid.h"

#include "model\LServer.h"
#include "model\CooperationManager.h"

#include "..\MeetingRoom\SetmypictureDlg.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define WM_TKLSDK_DATA1	WM_USER+0x101
#define WM_TKLSDK_DATA2	WM_USER+0x102
#define SERVER_NUMBER   256
/////////////////////////////////////////////////////////////////////////////
// CMainFrame
UINT CMainFrame::m_nServerIdSeed = 1;

IMPLEMENT_DYNAMIC(CMainFrame, CFrameWnd)

BEGIN_MESSAGE_MAP(CMainFrame, CFrameWnd)
	ON_WM_CREATE()
	ON_WM_SETFOCUS()
	ON_WM_CLOSE()
	ON_WM_SYSCOMMAND()
	ON_WM_TIMER()
	ON_WM_SIZE()
	ON_WM_SIZING()
	ON_MESSAGE(WM_DISCONNECT_SERVER, OnDisconnectServer)
	ON_MESSAGE(WM_LOGIN_FAILED, OnLoginFailed)
	ON_MESSAGE(WM_TASKBARNOTIFIER_CLOSED, OnTaskbarNotifierClosed)
	ON_MESSAGE(WM_RECEIVED_SYSMSG, OnReceivedSysMsg)
	ON_MESSAGE(WM_TRAY_NOTIFICATION, OnShellTrayIcon)
	ON_MESSAGE(WM_MESSAGE_FROM_WEBLAUNCHER, OnReceivedMsgFromWeblauncher)
	ON_MESSAGE(WM_CHECK_VERSION, OnCheckVersion)
	ON_MESSAGE(WM_DUPLICATE_LOGIN, OnDuplicateLogin)

	ON_COMMAND(ID_LOGIN, OnLogin)
	ON_COMMAND(ID_LOGOUT, OnLogout)
	ON_UPDATE_COMMAND_UI(ID_LOGIN, OnUpdateLogin)
	ON_UPDATE_COMMAND_UI(ID_LOGOUT, OnUpdateLogout)

	ON_COMMAND(ID_SEARCH_CONTACT, OnSearchContact)
	ON_COMMAND(ID_DEL_CONTACT, OnDelContact)
	ON_COMMAND(ID_PROPERTY_CONTACT, OnPropertyContact)
	ON_COMMAND(ID_CREATE_CONTACT_GROUP, OnCreateContactGroup)
	ON_COMMAND(ID_DEL_CONTACT_GROUP, OnDelContactGroup)
	ON_COMMAND(ID_RENAME_CONTACT_GROUP, OnRenameContactGroup)
	ON_UPDATE_COMMAND_UI(ID_DEL_CONTACT, OnUpdateDelContact)
	ON_UPDATE_COMMAND_UI(ID_PROPERTY_CONTACT, OnUpdatePropertyContact)	
	ON_UPDATE_COMMAND_UI(ID_SEARCH_CONTACT, OnUpdateSearchContact)
	ON_UPDATE_COMMAND_UI(ID_CREATE_CONTACT_GROUP, OnUpdateCreateContactGroup)
	ON_UPDATE_COMMAND_UI(ID_DEL_CONTACT_GROUP, OnUpdateDelContactGroup)
	ON_UPDATE_COMMAND_UI(ID_RENAME_CONTACT_GROUP, OnUpdateRenameContactGroup)

	ON_COMMAND(ID_CREATE_INSTANTCONF, OnCreateInstantconf)
	ON_COMMAND(ID_JOIN_CONFERENCE, OnJoinConference)
	ON_COMMAND(ID_QUIT_CONFERENCE, OnQuitConference)
	ON_COMMAND(ID_LOOK_CONFERENCE, OnLookConference)
	ON_UPDATE_COMMAND_UI(ID_JOIN_CONFERENCE, OnUpdateJoinConference)
	ON_UPDATE_COMMAND_UI(ID_QUIT_CONFERENCE, OnUpdateQuitConference)
	ON_UPDATE_COMMAND_UI(ID_LOOK_CONFERENCE, OnUpdateLookConference)
	ON_UPDATE_COMMAND_UI(ID_CREATE_INSTANTCONF, OnUpdateCreateInstantconf)

	ON_COMMAND(ID_ALWAYS_TOP, OnAlwaysTop)
	ON_COMMAND(ID_OPTION, OnOption)
	ON_UPDATE_COMMAND_UI(ID_ALWAYS_TOP, OnUpdateAlwaysTop)

	ON_UPDATE_COMMAND_UI(ID_MENU_SHOW, OnUpdateMenuShow)
	ON_UPDATE_COMMAND_UI(ID_MENU_HIDE, OnUpdateMenuHide)
	ON_COMMAND(ID_MENU_HIDE, OnMenuHide)
	ON_COMMAND(ID_HELP_CONTENT, OnHelpContent)
	ON_COMMAND(ID_ABOUT, OnAbout)
	ON_COMMAND(ID_CLOSE, OnCloseWnd)
	ON_COMMAND(ID_MENU_SHOW, OnTrayMenuOpen)
	ON_COMMAND(ID_EXIT, OnTrayMenuExit)

	ON_UPDATE_COMMAND_UI(ID_SEND_LEAVEWORD, OnUpdateSendLeaveword)
	ON_UPDATE_COMMAND_UI(ID_SEND_EMAIL, OnUpdateSendEmail)
	ON_COMMAND(ID_LOOK_LEAVEWORDS, OnLookLeavewords)
	ON_COMMAND(ID_OPEN_RECEIVED, OnOpenReceived)
	ON_COMMAND(ID_AV_WIZARD, OnAVWizard)
	ON_COMMAND(ID_SEND_LEAVEWORD, OnSendLeaveword)
	ON_COMMAND(ID_SEND_EMAIL, OnSendEmail)
	ON_UPDATE_COMMAND_UI(ID_LOOK_LEAVEWORDS, OnUpdateLookLeavewords)
	ON_UPDATE_COMMAND_UI(ID_AV_WIZARD, OnUpdateAvWizard)
	ON_WM_COPYDATA()
	ON_MESSAGE(WM_TKLSDK_DATA1, OnReceiveData1)
	ON_MESSAGE(WM_TKLSDK_DATA2, OnReceiveData2)
	ON_COMMAND(ID_VIEW_CONFHISTORY, OnViewConfhistory)
	ON_COMMAND(ID_SETPICTURE, OnSetpicture)
	ON_UPDATE_COMMAND_UI(ID_VIEW_CONFHISTORY, OnUpdateViewConfhistory)
	ON_COMMAND(ID_COMMONCONTACTMGR, OnCommoncontactmgr)
	ON_UPDATE_COMMAND_UI(ID_COMMONCONTACTMGR, OnUpdateCommoncontactmgr)
	ON_COMMAND(ID_CREATEFORMALCONF, OnCreateformalconf)
	ON_UPDATE_COMMAND_UI(ID_CREATEFORMALCONF, OnUpdateCreateformalconf)
	ON_COMMAND(ID_CONFMODEMGR, OnConfmodemgr)
	ON_UPDATE_COMMAND_UI(ID_CONFMODEMGR, OnUpdateConfmodemgr)
	ON_COMMAND(ID_SYSTEMROLEMGR, OnSystemrolemgr)
	ON_UPDATE_COMMAND_UI(ID_SYSTEMROLEMGR, OnUpdateSystemrolemgr)
	ON_COMMAND(ID_SELFINFOMGR, OnSelfinfomgr)
	ON_UPDATE_COMMAND_UI(ID_SELFINFOMGR, OnUpdateSelfinfomgr)
	ON_COMMAND(ID_SENDSYSTEMMSG, OnSendsystemmsg)
	ON_UPDATE_COMMAND_UI(ID_SENDSYSTEMMSG, OnUpdateSendsystemmsg)
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CMainFrame construction/destruction

CMainFrame::CMainFrame()
{
	m_pImageTab = NULL;
	m_pContactDlg = NULL;
	m_pConferenceDlg = NULL;
	m_pSysmsgDlg = NULL;
	m_nMinX = 200;
	m_nMinY = 440;
	m_nNotifyId = 0;
	m_conferenceIdFromWeb = 0;
	m_bShowAdDlg = FALSE;
	m_bFirstLogin = TRUE;

	m_pDataBuffer1 = NULL;
	m_pDataBuffer2 = NULL;
	m_decodeBuffer = NULL;
	m_decodeBuffer2 = NULL;
	m_decHandle = NULL;
	m_decHandle2 = NULL;
}

CMainFrame::~CMainFrame()
{
	m_pAdBG->DestroyWindow();
	delete m_pAdBG;

	m_pContactDlg->DestroyWindow();
	delete m_pContactDlg;

	m_pConferenceDlg->DestroyWindow();
	delete m_pConferenceDlg;

	m_pSysmsgDlg->DestroyWindow();
	delete m_pSysmsgDlg;

	m_pImageTab->DestroyWindow();
	delete m_pImageTab;

	map<UINT, LServer*>::iterator iter = m_servers.begin();
	while( iter != m_servers.end() )
	{
		LServer* pServer = iter->second;
		if( pServer->getStatus() != SERVER_LOGOUT )
			pServer->destroy();
		delete pServer;
		pServer = NULL;
		iter++;
	}
	m_servers.clear();
	Shell_NotifyIcon(NIM_DELETE,&m_TaskIcon);
	destroy();
}

void CMainFrame::destroy()
{
	delete m_pDataBuffer1;
	delete m_pDataBuffer2;
	delete m_decodeBuffer;
	delete m_decodeBuffer2;

	if(m_decHandle != NULL){
		xvid_decore(m_decHandle, XVID_DEC_DESTROY, NULL, NULL);
		m_decHandle = NULL;
		::DeleteCriticalSection(&m_videoCaptureCs);
	}

	if(m_decHandle2 != NULL){
		xvid_decore(m_decHandle2, XVID_DEC_DESTROY, NULL, NULL);
		m_decHandle2 = NULL;
	}
}

int CMainFrame::OnCreate(LPCREATESTRUCT lpCreateStruct)
{
	if (CFrameWnd::OnCreate(lpCreateStruct) == -1)
		return -1;

	m_dlgLogin.Create(IDD_LOGIN_DIALOG, this);
	m_dlgLogin.ShowWindow(TRUE);
	CreateImgTab();
	m_pAdBG = new CAdBGDlg();
	m_pAdBG->Create( IDD_ADVERTIZEBG_DLG, this );

	//设置系统托盘图标
	m_TaskIcon.hIcon = AfxGetApp()->LoadIcon(IDI_LOGOUT);
	m_TaskIcon.hWnd = this->GetSafeHwnd();
	m_TaskIcon.uFlags = 0x7;
	m_TaskIcon.uID = 1000;
	lstrcpy(m_TaskIcon.szTip,_T(APP_TITLE + " - 未登录"));
	m_TaskIcon.uCallbackMessage = WM_TRAY_NOTIFICATION;
	Shell_NotifyIcon(NIM_ADD,&m_TaskIcon);

	HICON hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
	SendMessage(WM_SETICON,TRUE,(LPARAM)hIcon);
	SetWindowText(APP_TITLE);

	CString s = GetCommandLine();
	if(processCmdline(s))
		return 0;
	
	if( ::GetPrivateProfileInt("OPTION", "auto_login", 0, GetApp()->getIniFilename()) )
		m_dlgLogin.OnBnClickedLoginBtn();

	//SetTimer( 1, 20000, NULL );
	return 0;
}

void CMainFrame::CreateImgTab()
{
	m_pImageTab = new CImageTabWnd();
	m_pImageTab->Create(NULL, NULL, WS_CHILD|WS_CLIPCHILDREN, CRect(), this, NULL, NULL);	
	m_pContactDlg = new CContactDlg(this);
	m_pContactDlg->Create(IDD_CONTACT_DLG, m_pImageTab);
	m_pConferenceDlg = new CConferenceDlg(this);
	m_pConferenceDlg->Create(IDD_CONFERENCE_DLG, m_pImageTab);
	m_pSysmsgDlg = new CSysinfoDlg(this);
	m_pSysmsgDlg->Create(IDD_SYSINFO_DLG, m_pImageTab);

	m_pImageTab->SetTabCount(3);
	m_pImageTab->SetDialogMargin(5);

	m_pImageTab->SetTabWnd(0, m_pContactDlg);
	m_pImageTab->SetTabWnd(1, m_pConferenceDlg);
	m_pImageTab->SetTabWnd(2, m_pSysmsgDlg);
}

BOOL CMainFrame::PreCreateWindow(CREATESTRUCT& cs)
{
	if( !CFrameWnd::PreCreateWindow(cs) )
		return FALSE;

	// Show the window in last size and location
	char* IniFileName = ::GetApp()->getIniFilename();

	cs.x = ::GetPrivateProfileInt("WINDOW", "left", 0, IniFileName);
	if( cs.x < 0) cs.x = 0;
	cs.y = ::GetPrivateProfileInt("WINDOW", "top", 0, IniFileName);
	if( cs.y < 0) cs.y = 0;
	cs.cx = ::GetPrivateProfileInt("WINDOW", "width", 0, IniFileName);
	if( cs.cx == 0 ) cs.cx = 400;
	if( cs.cx < m_nMinX) cs.cx = m_nMinX;
	cs.cy = ::GetPrivateProfileInt("WINDOW", "height", 0, IniFileName);
	if( cs.cy == 0 ) cs.cy = 580;
	if( cs.cy < m_nMinY) cs.cy = m_nMinY;

	cs.dwExStyle &= ~WS_EX_CLIENTEDGE;
	cs.lpszClass = AfxRegisterWndClass(0);
	return TRUE;
}

/////////////////////////////////////////////////////////////////////////////
// CMainFrame diagnostics

#ifdef _DEBUG
void CMainFrame::AssertValid() const
{
	CFrameWnd::AssertValid();
}

void CMainFrame::Dump(CDumpContext& dc) const
{
	CFrameWnd::Dump(dc);
}

#endif //_DEBUG

/////////////////////////////////////////////////////////////////////////////
// CMainFrame message handlers
void CMainFrame::OnSetFocus(CWnd* pOldWnd)
{
	if( m_dlgLogin.GetSafeHwnd() && m_dlgLogin.IsWindowVisible() )
		m_dlgLogin.SetFocus();
	else if( m_pImageTab->GetSafeHwnd() && m_pImageTab->IsWindowVisible() )
		m_pImageTab->SetFocus();
}

BOOL CMainFrame::OnCmdMsg(UINT nID, int nCode, void* pExtra, AFX_CMDHANDLERINFO* pHandlerInfo)
{
	// otherwise, do default handling
	return CFrameWnd::OnCmdMsg(nID, nCode, pExtra, pHandlerInfo);
}

void CMainFrame::OnClose()
{
	ShowWindow(SW_MINIMIZE);
	ShowWindow(SW_HIDE);	
}

void CMainFrame::OnTimer(UINT nIDEvent)
{
	LServer* pServer = this->getDefaultServer();
	if( pServer != NULL && pServer->getStatus() == SERVER_LOGOUT )
	{
		this->OnLoginServer( pServer, TRUE );
		TRACE1( "auto login server: %s\n", pServer->getIP().c_str() );
	}
	CFrameWnd::OnTimer(nIDEvent);
}

void CMainFrame::OnSize(UINT nType, int cx, int cy)
{
	CFrameWnd::OnSize(nType, cx, cy);
	if( m_dlgLogin.GetSafeHwnd() )
		m_dlgLogin.MoveWindow( 0, 0, cx, cy );
	if( m_pImageTab->GetSafeHwnd() )
		m_pImageTab->MoveWindow( 0, 0, cx, m_bShowAdDlg?cy - 65:cy );
	if( m_pAdBG->GetSafeHwnd() )
		m_pAdBG->MoveWindow( 0, cy - 65, cx, 65 );
}

void CMainFrame::OnSizing(UINT fwSide, LPRECT pRect)
{
	if(pRect->right - pRect->left > m_nMinX )
		CFrameWnd::OnSizing(fwSide, pRect);
	else
	{
		if(fwSide==1 || fwSide==4 || fwSide==7)
			pRect->left = pRect->right - m_nMinX;	
		else
			pRect->right = pRect->left + m_nMinX;
	}
	if(pRect->bottom - pRect->top > m_nMinY)
		CFrameWnd::OnSizing(fwSide, pRect);
	else
	{
		if(fwSide==3 || fwSide ==5 || fwSide ==4)
			pRect->top = pRect->bottom - m_nMinY;
		else
			pRect->bottom = pRect->top + m_nMinY;
	}
}

BOOL CMainFrame::OnCopyData(CWnd* pWnd, COPYDATASTRUCT* pCopyDataStruct)
{
 	if( pCopyDataStruct->dwData == 100 )
	{
		OnTrayMenuOpen();
		CONFERENCE_INFO* pConferenceInfo = (CONFERENCE_INFO*)pCopyDataStruct->lpData;  

		CONFERENCE_INFO* pInfo = new CONFERENCE_INFO;
		strcpy(pInfo->ip,(LPSTR) ((CONFERENCE_INFO *)(pCopyDataStruct->lpData))->ip);
		strcpy(pInfo->name, (LPSTR) ((CONFERENCE_INFO *)(pCopyDataStruct->lpData))->name);
		pInfo->port = (LONG) ((CONFERENCE_INFO *)(pCopyDataStruct->lpData))->port;
		pInfo->conferenceId = (LONGLONG) ((CONFERENCE_INFO *)(pCopyDataStruct->lpData))->conferenceId;
		strcpy(pInfo->password, (LPSTR) ((CONFERENCE_INFO *)(pCopyDataStruct->lpData))->password);
		pInfo->conferenceMode = (LONG) ((CONFERENCE_INFO *)(pCopyDataStruct->lpData))->conferenceMode;
		PostMessage(WM_MESSAGE_FROM_WEBLAUNCHER, 0, (DWORD)pInfo);
	}        

	return CFrameWnd::OnCopyData(pWnd, pCopyDataStruct);
}

LRESULT CMainFrame::OnTaskbarNotifierClosed(WPARAM wParam,LPARAM lParam)
{
	POSITION pos1,pos2;
	for( pos1 = m_pTaskNotifyList.GetHeadPosition(); (pos2 = pos1) != NULL; )
	{
		CTaskbarNotifier* tn = (CTaskbarNotifier*)m_pTaskNotifyList.GetNext(pos1);
		if (tn->m_nId == wParam)
		{
			m_pTaskNotifyList.RemoveAt(pos2);
			if( m_pTaskNotifyList.GetCount() == 0 )
			{
				m_nNotifyId = 0;
			}
			if( lParam == 1)
			{
				if (tn->m_nType == CONTACT_MSG)
				{
					this->m_pImageTab->ShowDialog(2);
					this->m_pSysmsgDlg->showContactMessage();
				}
			}
			tn->DestroyWindow();
			delete tn;
			return 0;
		}
	}
	return -1;	
}
LRESULT CMainFrame::OnReceivedSysMsg(WPARAM wParam,LPARAM lParam)
{
	string* str = (string*) lParam ;
	if(!str || str->empty())
		return -1;

	if (m_pTaskNotifyList.GetCount() > 0)
	{
		CTaskbarNotifier* tnPrev = (CTaskbarNotifier*) m_pTaskNotifyList.GetTail();
		m_nNotifyId = tnPrev->m_nId + 1;
	}
	else
	{
		m_nNotifyId++;
	}
	CTaskbarNotifier* tn = new CTaskbarNotifier(m_nNotifyId);
	tn->m_nType = wParam;
	tn->Create(this);
	tn->SetSkin(IDB_TASKBARNOTIFIER, 255, 0, 255);
	tn->SetTextFont("Arial",90,TN_TEXT_NORMAL,TN_TEXT_NORMAL);
	tn->SetTextColor(RGB(0,120,120),RGB(0,10,200));
	tn->SetTextRect(CRect(10,28,tn->m_nSkinWidth-10,tn->m_nSkinHeight-5));
	tn->SetTitleRect( CRect(30, 3, tn->m_nSkinWidth-5, 20) );
	tn->m_nStartPosOffsetY = (m_nNotifyId-1) % 5 * tn->m_nSkinHeight;
	tn->m_nStartPosOffsetX = (m_nNotifyId-1) / 5 * tn->m_nSkinWidth;
	CString strText = str->c_str();
	if( wParam == CONTACT_MSG )
	{
		int pos =  strText.Find( ":" );
		if( pos != -1 )
		{
			tn->m_strTitle = strText.Left( pos );
			strText = strText.Mid( pos+1 );
		}
	}
	else
	{
		tn->m_strTitle = "系统消息";
	}
	tn->Show( strText );
	m_pTaskNotifyList.AddTail(tn);

	delete str;
	return 0;
}

LRESULT CMainFrame::OnReceivedMsgFromWeblauncher(WPARAM wParam,LPARAM lParam)
{
	CONFERENCE_INFO* pInfo = (CONFERENCE_INFO*)lParam;
	CString name = pInfo->name;
	CString password = pInfo->password;
	__int64 conferenceId = pInfo->conferenceId;

	m_confMode = pInfo->conferenceMode;

	//TODO: modify code here
/*
	//已经登录
	if( m_bLogin )
	{
		//是否已在会议室
		if(m_pClientFrame->isRoomExist(conferenceId))
			m_pClientFrame->showRoom(conferenceId);
		else if( m_pClientFrame->m_conferenceIdFromWeb != conferenceId )
			m_pClientFrame->joinConference(conferenceId);
	}
	else
	{
		if( m_bLogining )//正在登录
			cancelLogin();
		m_conferenceIdFromWeb = conferenceId;
		m_dlgLogin.startLoginFromWeb( name, password, m_confMode );
	}
*/
	delete pInfo;
	return 0;
}

LRESULT CMainFrame::OnShellTrayIcon(WPARAM wParam,LPARAM lParam)
{
	CMenu  menu;
	CMenu* pPopup;
	POINT pos;

	if (wParam == 1000 )
	{
		switch( lParam )
		{
		case WM_LBUTTONDOWN:
			break;

		case WM_RBUTTONDOWN:
			if (this->IsWindowEnabled() )
			{
				// Show the menu
				menu.LoadMenu(IDR_MENU_TRAY);
				pPopup = menu.GetSubMenu(0);
				GetCursorPos(&pos);
				this->SetForegroundWindow();
				pPopup->TrackPopupMenu(TPM_RIGHTBUTTON | TPM_LEFTALIGN, pos.x, pos.y, this, NULL);
				::PostMessage(this->m_hWnd, WM_NULL, 0, 0);
			}
			break;

		case WM_LBUTTONDBLCLK:
			// Show this window
			this->ShowWindow(SW_NORMAL);
			this->SetForegroundWindow();
			break;
		}
	}
	return 0;
}

LRESULT CMainFrame::OnDisconnectServer(WPARAM wParam, LPARAM lParam)
{
	LServer* pServer = (LServer*) wParam;
	CString* message = (CString *) lParam;
	CString str;
	str.Format( "到服务器（地址[%s] 端口[%d]）的连接已断开，原因是:“%s”。", 
				pServer->getIP().c_str(), pServer->getPort(), *message );
	AfxMessageBox( str, MB_OK | MB_ICONEXCLAMATION );
	delete message;
	return 0;
}

LRESULT CMainFrame::OnLoginFailed(WPARAM wParam, LPARAM lParam)
{
	LServer* pServer = (LServer*) wParam;
	CString* str = (CString*) lParam;
	CString message;
	message.Format( "登录服务器[%s]失败，原因是“%s”。", pServer->getServerName().c_str(), *str );
	UINT nType = MB_OK;
	if( *str == "错误的用户名或者密码" )
	{
		message = message + "是否重新输入用户名和密码？";
		nType = MB_YESNO | MB_ICONQUESTION;
	}
	delete str;
	int ret = AfxMessageBox(message, nType);
	if( ret == IDYES )
	{
		string sUsername, sPassword;
		BOOL bMember = pServer->isRememberUserPass();
		if( getUsernameAndPassword( sUsername, sPassword, bMember ) )
		{
			pServer->setUserName( sUsername );
			pServer->setPassword( sPassword );
			pServer->setRememberUserPass( bMember );
			if( pServer->getStatus() == SERVER_LOGINING )
				pServer->cmdLogin();
			return 0;
		}
	}
	pServer->destroy();
	m_dlgLogin.showAsLogout();
	return 0;
}

LRESULT CMainFrame::OnCheckVersion(WPARAM wParam, LPARAM lParam)
{
	LServer* pServer = (LServer*) lParam;
	BOOL bHasNewVersion = LOWORD( wParam );
	BOOL bNeedUpdate = HIWORD( wParam );
	if( bNeedUpdate )
	{
		pServer->destroy();
		CString str;
		if( pServer->isDefaultServer() )
		{
			str.Format( "你需要更新到新的客户端以登录服务器，现在是否更新？" );
			int ret = AfxMessageBox( str, MB_YESNO|MB_ICONQUESTION );
			if( ret == IDYES )
			{
				ShellExecute(NULL, "open", pServer->getClientDownloadURL().c_str(), NULL, NULL, SW_SHOWNORMAL);
				OnTrayMenuExit();
				return 0;
			}
		}
		else
		{
			str.Format( "你需要更新到新的客户端以登录服务器" );
			AfxMessageBox( str );
		}
		m_dlgLogin.showAsLogout();
		return 0;
	}
	if( bHasNewVersion )
	{
		if( pServer->isDefaultServer() )
		{
			int ret = AfxMessageBox( "有新的客户端可以使用，现在是否更新？", MB_YESNO|MB_ICONQUESTION );
			if( ret == IDYES )
			{
				pServer->destroy();
				ShellExecute(NULL, "open", pServer->getClientDownloadURL().c_str(), NULL, NULL, SW_SHOWNORMAL);
				OnTrayMenuExit();
				return 0;
			}
		}
	}
	if( pServer->getUserName() == "" )
	{
		string sUsername, sPassword;
		BOOL bMember = FALSE;
		if( getUsernameAndPassword( sUsername, sPassword, bMember ) )
		{
			pServer->setUserName( sUsername );
			pServer->setPassword( sPassword );
			pServer->setRememberUserPass( bMember );
			if( pServer->getStatus() == SERVER_LOGOUT )
				return 0;
		}
		else
		{
			pServer->destroy();
			m_dlgLogin.showAsLogout();
			return 0;
		}
	}
	pServer->cmdLogin();
	//SetTimer( pServer->getServerId(), 30000, NULL );
	return 0;
}

LRESULT CMainFrame::OnDuplicateLogin(WPARAM wParam, LPARAM lParam)
{
	LServer* pServer = (LServer*) wParam;
	CString* message = (CString *) lParam;
	m_pConferenceDlg->quitAllRoom( pServer );
	pServer->destroy();
	this->modifyServer( pServer );
	this->SetTaskIcon( pServer, FALSE );
    ::PostMessage(this->m_hWnd, WM_DISCONNECT_SERVER, (WPARAM)pServer, (LPARAM)message);
	return 0;
}

void CMainFrame::OnLogin()
{
	this->m_dlgLogin.OnBnClickedLoginBtn();
}

void CMainFrame::OnUpdateLogin(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() == NULL || this->getCurSelServer()->getStatus() == SERVER_LOGOUT );
}

void CMainFrame::OnLogout()
{
	this->OnLogoutServer( this->getCurSelServer() );
}

void CMainFrame::OnUpdateLogout(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() != NULL && this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnLookLeavewords()
{
	this->m_pSysmsgDlg->lookMessage( this->getCurSelServer(), LEAVEWORD );
}

void CMainFrame::OnUpdateLookLeavewords(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL && pServer->getUserName() != "" );
}

void CMainFrame::OnOpenReceived()
{
	char buffer[1024];
	CString path;
	char* g_IniFileName = ::GetApp()->getIniFilename();
	::GetPrivateProfileString("OPTION", "recv_path", "", buffer, sizeof(buffer), g_IniFileName);
	path = buffer;
	if( path == "" )
		path = PathHelper::getDefaultReceivedFilePath();

	ShellExecute(NULL, "open", "", NULL, path, SW_SHOWNORMAL);
}

void CMainFrame::OnCloseWnd()
{
	OnClose();
}

void CMainFrame::OnSearchContact()
{
	m_pContactDlg->OnAddContact();
}

void CMainFrame::OnUpdateSearchContact(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() != NULL && this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnSendLeaveword()
{
	this->m_pContactDlg->sendLeaveword();
}

void CMainFrame::OnUpdateSendLeaveword(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() != NULL && this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnSendEmail()
{
	this->m_pContactDlg->sendEmail();
}

void CMainFrame::OnUpdateSendEmail(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() != NULL && this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnDelContact()
{
	m_pContactDlg->OnDelContact();
}

void CMainFrame::OnUpdateDelContact(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pContactDlg->IsWindowVisible() 
					&& m_pContactDlg->getCurSelContactId() 
					&& m_pContactDlg->canModifyCurSel() );
}

void CMainFrame::OnPropertyContact()
{
	m_pContactDlg->OnCheckContactInfo();
}

void CMainFrame::OnUpdatePropertyContact(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pContactDlg->IsWindowVisible() && m_pContactDlg->getCurSelContactId() );
}

void CMainFrame::OnCreateContactGroup()
{
	m_pContactDlg->OnAddGroup();
}

void CMainFrame::OnUpdateCreateContactGroup(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pContactDlg->IsWindowVisible()
					&& this->getCurSelServer() != NULL && this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnDelContactGroup()
{
	m_pContactDlg->OnDelGroup();
}

void CMainFrame::OnUpdateDelContactGroup(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pContactDlg->IsWindowVisible()
					&& m_pContactDlg->getCurSelGroupId() != -1
					&& m_pContactDlg->canModifyCurSel() );
}

void CMainFrame::OnRenameContactGroup()
{
	m_pContactDlg->OnRenameGroup();
}

void CMainFrame::OnUpdateRenameContactGroup(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( m_pContactDlg->IsWindowVisible()
					&& m_pContactDlg->getCurSelGroupId() != -1
					&& m_pContactDlg->canModifyCurSel() );
}

void CMainFrame::OnCommoncontactmgr()
{
	CString url;
	url.Format( "%s?userId=%d", getCurSelServer()->getCommonContactMgrURL().c_str(), this->getCurSelServer()->getId() );
	CShowXMLDlg dlg( url, "公共联系人管理" );
	dlg.setWindowSize( 723, 568 );
	dlg.DoModal();
}

void CMainFrame::OnUpdateCommoncontactmgr(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL 
					&& pServer->getStatus() == SERVER_LOGIN
					&& pServer->getCommonContactMgrURL() != "" 
					&& (pServer->getUserRole() == GROUP_MANAGER 
						|| pServer->getUserRole() == SYSTEM_MANAGER
						|| pServer->getUserRole() == CONFERENCE_AND_GROUP_MANAGER) );
}

void CMainFrame::OnCreateInstantconf()
{
	m_pContactDlg->createInstantConference();
}

void CMainFrame::OnUpdateCreateInstantconf(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() != NULL && this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnCreateformalconf()
{
    CString url;
	url.Format( "%s?userId=%d&isEdit=false", 
				getCurSelServer()->getCreateFromalConferenceURL().c_str(), this->getCurSelServer()->getId() );
	CShowXMLDlg dlg( url, "创建正式会议" );
	dlg.setWindowSize( 588, 513 );
	dlg.DoModal();
}

void CMainFrame::OnUpdateCreateformalconf(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL 
					&& pServer->getStatus() == SERVER_LOGIN
					&& pServer->getCreateFromalConferenceURL() != "" 
					&& (pServer->getUserRole() == CONFERENCE_MANAGER 
						|| pServer->getUserRole() == SYSTEM_MANAGER
						|| pServer->getUserRole() == CONFERENCE_AND_GROUP_MANAGER) );
}

void CMainFrame::OnJoinConference() 
{
    m_pConferenceDlg->OnJoinConference();
}

void CMainFrame::OnUpdateJoinConference(CCmdUI* pCmdUI) 
{
	pCmdUI->Enable( this->m_pConferenceDlg->canJoinOrQuitConference( TRUE ) );
}

void CMainFrame::OnQuitConference() 
{
	m_pConferenceDlg->OnQuitConference();
}

void CMainFrame::OnUpdateQuitConference(CCmdUI* pCmdUI) 
{	
	pCmdUI->Enable( this->m_pConferenceDlg->canJoinOrQuitConference( FALSE ) );
}

void CMainFrame::OnLookConference()
{
	m_pConferenceDlg->OnLookConference();
}

void CMainFrame::OnUpdateLookConference(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->m_pConferenceDlg->canLookConference() );
}

void CMainFrame::OnViewConfhistory()
{
	CString file = "<html><head><style type='text/css'>.f12{font-size:12px;}</style></head>";
	file = file + "<body><TABLE bgColor=#D5DAED cellSpacing=1 cellPadding=2 width=100% style=\"WORD-BREAK: break-all\" class='f12'>";
	file = file + "<TR align='left'><TH bgColor=#e7ecf4>会议列表</TH></TR>";

	CFileFind finder;
	// build a string with wildcards
	CString path;
	LServer* pServer = this->getCurSelServer();
	path = PathHelper::getConferenceMsgPath( pServer->getUserName().c_str(), pServer->getIP().c_str() );
	// start working for files
	BOOL bWorking = finder.FindFile( path + _T("\\*.xml") );
	int n = 0;
	while (bWorking)
	{
		bWorking = finder.FindNextFile();
		// skip . and .. files; otherwise, we'd	recur infinitely!
		if (finder.IsDots())
			continue;
		// if it's a directory, skip search it
		if (finder.IsDirectory())
			continue;
		CString bg;
		bg = "bgColor=#e7ecf4";
		if( n % 2 == 0 )
			bg = "bgColor=#f3f7fd";
		file = file + "<tr><td "+bg+"><a href='"+finder.GetFileName()+"' target='mainFrame'><li>"+finder.GetFileTitle()+"</a></td></tr>";
		n++;
	}
	finder.Close();
	file = file + "</table></body></html>";

	FILE* fd = fopen( path+"\\conflist.temp", "wt" );
	if( fd != NULL )
	{
		fwrite( file, sizeof(char), file.GetLength(), fd );
		fclose( fd );
	}
	file = "<html><frameset cols='200,*' frameborder='NO' border='0' framespacing='0'>";
	file = file + "<frame src='conflist.temp' name='leftFrame' scrolling='auto' noresize>";
	file = file + "<frame src='' name='mainFrame'></frameset><noframes><body></body></noframes></html>";
	fd = fopen( path+"\\confhistory.temp", "wt" );
	if( fd != NULL )
	{
		fwrite( file, sizeof(char), file.GetLength(), fd );
		fclose( fd );
	}
	CShowXMLDlg dlg( path + "\\confhistory.temp", "查看会议记录" );
	dlg.DoModal();
}

void CMainFrame::OnUpdateViewConfhistory(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL && pServer->getUserName() != "" );
}

void CMainFrame::OnConfmodemgr()
{
	CShowXMLDlg dlg( getCurSelServer()->getConferenceModeMgrURL().c_str(), "会议模式管理" );
	dlg.setWindowSize( 611, 347 );
	dlg.DoModal();
}

void CMainFrame::OnUpdateConfmodemgr(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL 
					&& pServer->getStatus() == SERVER_LOGIN
					&& pServer->getConferenceModeMgrURL() != "" 
					&& pServer->getUserRole() == SYSTEM_MANAGER );
}

void CMainFrame::OnSystemrolemgr()
{
	CShowXMLDlg dlg( getCurSelServer()->getSystemRoleMgrURL().c_str(), "用户角色管理" );
	dlg.setWindowSize( 598, 388 );
	dlg.DoModal();
}

void CMainFrame::OnUpdateSystemrolemgr(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL 
					&& pServer->getStatus() == SERVER_LOGIN
					&& pServer->getSystemRoleMgrURL() != "" 
					&& pServer->getUserRole() == SYSTEM_MANAGER );
}

void CMainFrame::OnAlwaysTop()
{
	CRect rc;
	GetWindowRect(&rc);
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_EXSTYLE);
	SetWindowPos( (l & WS_EX_TOPMOST) ? &CWnd::wndNoTopMost : &CWnd::wndTopMost, rc.left, rc.top, rc.Width(), rc.Height(), SWP_SHOWWINDOW);
}

void CMainFrame::OnUpdateAlwaysTop(CCmdUI *pCmdUI)
{
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_EXSTYLE);
	pCmdUI->SetCheck( l & WS_EX_TOPMOST );
}

void CMainFrame::OnSelfinfomgr()
{
	CUserInfoDlg dlg( getCurSelServer() );
	dlg.DoModal();
}

void CMainFrame::OnUpdateSelfinfomgr(CCmdUI *pCmdUI)
{
	pCmdUI->Enable( this->getCurSelServer() != NULL 
					&& this->getCurSelServer()->getStatus() == SERVER_LOGIN );
}

void CMainFrame::OnSendsystemmsg()
{
	CShowXMLDlg dlg( getCurSelServer()->getCreateBulletinURL().c_str(), "发送系统公告" );
	dlg.setWindowSize( 507, 270 );
	dlg.DoModal();
}

void CMainFrame::OnUpdateSendsystemmsg(CCmdUI *pCmdUI)
{
	LServer* pServer = this->getCurSelServer();
	pCmdUI->Enable( pServer != NULL 
					&& pServer->getStatus() == SERVER_LOGIN
					&& pServer->getCreateBulletinURL() != "" 
					&& pServer->getUserRole() == SYSTEM_MANAGER );
}

void CMainFrame::OnAVWizard()
{
	CIntroductionPP p1;
	CAudioPlayPP p2;
	CAudioRecordPP p3;
	CVideoPP p4;
	CAVConfigWizard sheet( "视音频设置向导", this );

	sheet.AddPage(&p1);
	sheet.AddPage(&p2);
	sheet.AddPage(&p3);
	sheet.AddPage(&p4);

	sheet.SetWizardMode();
	sheet.DoModal();
}

void CMainFrame::OnUpdateAvWizard(CCmdUI *pCmdUI)
{
    // 只能在未登录的状态下使用视音频向导,具体解释请参见 Bug 1460
	pCmdUI->Enable( !hasServerLoginOrLogining() );
}

void CMainFrame::OnSetpicture()
{
	CSetmypictureDlg dlg(this);
	dlg.m_bShowCheckBox = FALSE;
	LServer* pDefaultServer = this->m_servers.begin()->second;
	dlg.m_sIniFileName = PathHelper::getUserPrivateConfigFile( pDefaultServer->getUserName().c_str(), pDefaultServer->getIP().c_str() );
	if( dlg.DoModal() == IDOK )
	{
		m_pConferenceDlg->setMyPicture( dlg.m_fileName.GetString(), dlg.m_bDisplayNow );
	}
}

void CMainFrame::OnOption() 
{
	COptionDlg optDlg( this );
	optDlg.setReadOnly( hasServerLoginOrLogining() );
	optDlg.DoModal();
}

void CMainFrame::OnHelpContent()
{
    CString path = PathHelper::getHelpFileFullName();
	ShellExecute(NULL, "open", path, NULL, NULL, SW_SHOWNORMAL);
}

void CMainFrame::OnAbout()
{
	GetApp()->OnAppAbout();
}

void CMainFrame::OnTrayMenuOpen()
{
	ShowWindow(SW_SHOW);
	ShowWindow(SW_RESTORE);
}

void CMainFrame::OnUpdateMenuShow(CCmdUI *pCmdUI)
{
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_STYLE);
	pCmdUI->Enable(  l & WS_MINIMIZE );
}

void CMainFrame::OnMenuHide()
{
	OnClose();
}

void CMainFrame::OnUpdateMenuHide(CCmdUI *pCmdUI)
{
	pCmdUI->Enable();
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_STYLE);
	if(l&WS_MINIMIZE)
		pCmdUI->Enable(FALSE);
}

void CMainFrame::OnTrayMenuExit()
{
	if( m_pConferenceDlg->hasRoomOpened() )
	{
		if( AfxMessageBox( "您现在正在会议室中，确定要退出，关闭所有会议室么？", MB_YESNO ) == IDNO )
			return;
	}
	map<UINT, LServer*>::iterator iter = m_servers.begin();
	while( iter != m_servers.end() )
	{
		LServer* pServer = iter->second;
		m_pConferenceDlg->quitAllRoom( pServer );
		iter++;
	}

	// Save position
	LONG l = GetWindowLong(this->GetSafeHwnd(), GWL_STYLE);
	if((l&WS_MINIMIZE))
		this->ShowWindow( SW_RESTORE );
	CRect rc;
	GetWindowRect(&rc);
	char* pIniFile = GetApp()->getIniFilename();
	char buffer[64];
	_snprintf(buffer, 64, "%d", rc.top);
	::WritePrivateProfileString( "WINDOW", "top", buffer, pIniFile );
	_snprintf(buffer, 64, "%d", rc.left);
	::WritePrivateProfileString( "WINDOW", "left", buffer, pIniFile );
	_snprintf(buffer, 64, "%d", rc.Width());
	::WritePrivateProfileString( "WINDOW", "width", buffer, pIniFile );
	_snprintf(buffer, 64, "%d", rc.Height());
	::WritePrivateProfileString( "WINDOW", "height", buffer, pIniFile );	

	// Close window
	CFrameWnd::OnClose();
}

void CMainFrame::SetTaskIcon(LServer* pServer, BOOL bShowCurUser)
{
	ASSERT( pServer != NULL );
	if( pServer == NULL )
		return;
	if( bShowCurUser )
	{
		CString sUsername = pServer->getUserName().c_str();
		if( sUsername != "" )
		{
			CString sRealname = pServer->getRealName().c_str();
			if( sRealname == "" )
				sRealname = "未登录";
			sUsername = sUsername + "(" + sRealname + ")";
		}
		m_pContactDlg->setCurrentUser( sUsername );
	}
	CString sTaskTip = pServer->getIP().c_str();
	int nStatus = pServer->getStatus();
	UINT nID;
	if( nStatus == SERVER_LOGOUT )
	{
        sTaskTip = sTaskTip + " - 未登录";
		nID = IDI_LOGOUT;
	}
	else if( nStatus == SERVER_LOGINING )
	{
		sTaskTip = sTaskTip + " - 正在登录";
		nID = IDI_LOGOUT;
	}
	else 
	{
		sTaskTip = sTaskTip + " - 已登录";
		nID = IDR_MAINFRAME;
	}
	lstrcpy( m_TaskIcon.szTip, sTaskTip );
	m_TaskIcon.hIcon = AfxGetApp()->LoadIcon( nID );
	Shell_NotifyIcon( NIM_MODIFY, &m_TaskIcon );
}

LServer* CMainFrame::getDefaultServer()
{
	return m_servers.begin()->second;
}

LServer* CMainFrame::getCurSelServer()
{
	if( m_servers.size() > 0 )
		return this->getDefaultServer();
	else
		return NULL;
}

LServer* CMainFrame::getServerById( UINT nId )
{
	return NULL;
}

LServer* CMainFrame::isServerExist( string sName, string sIP, int nPort, CString& str )
{
	map<UINT, LServer*>::iterator iter = m_servers.begin();
	while( iter != m_servers.end() )
	{
		LServer* pServer = iter->second;
		if( pServer->getServerName() == sName )
		{
			str.Format( "已存在名称为[%s]的服务器。", sName.c_str() );
			return pServer;
		}
		if( pServer->getIP() == sIP && pServer->getPort() == nPort )
		{
			str.Format( "已存在地址为[%s]，端口为[%d]的服务器。", sIP.c_str(), nPort );
			return pServer;
		}
		iter++;
	}
	return NULL;
}

BOOL CMainFrame::isDuplicateServer( LServer* pServer, string sName, string sIP, int nPort, CString& str )
{
	return FALSE;
}

LServer* CMainFrame::addServer( string sName, string sIP, int nPort, string sUsername, string sPassword, BOOL bDefault )
{
	CString str;
	LServer* pServer = isServerExist( sName, sIP, nPort, str );
	if( pServer != NULL )
		return pServer;
	pServer = new LServer( GetApp()->getCooperationManager(), this, sName, sIP, nPort );
	pServer->setDefaultServer( bDefault );
	pServer->setUserName( sUsername );
	pServer->setPassword( sPassword );
	m_servers[pServer->getServerId()] = pServer;
	this->m_pContactDlg->addServer( pServer );
	this->m_pConferenceDlg->addServer( pServer );
	this->m_pSysmsgDlg->addServer( pServer );
	return pServer;
}

void CMainFrame::removeServer( LServer* pServer )
{
}

void CMainFrame::modifyServer( LServer* pServer )
{
	ASSERT( pServer != NULL );
	if( pServer == NULL )
		return;
	this->m_pContactDlg->modifyServer( pServer );
	this->m_pConferenceDlg->modifyServer( pServer );
	this->m_pSysmsgDlg->modifyServer( pServer );
	if( pServer->getStatus() == SERVER_LOGOUT )
	{
		m_pImageTab->ShowWindow( FALSE );
		m_pAdBG->ShowWindow( FALSE );
		m_dlgLogin.showAsLogout();
		m_dlgLogin.ShowWindow( TRUE );
		CMenu menu;
		menu.LoadMenu( IDR_LOGOUT );
		SetMenu( &menu );
	}
	else if( pServer->getStatus() == SERVER_LOGIN )
	{
		m_pImageTab->ShowWindow( TRUE );
		m_dlgLogin.ShowWindow( FALSE );
		CMenu menu;
		if( m_confMode == TRAINING_MODE )
			menu.LoadMenu( IDR_TRAINING );
		else if( m_confMode == CONSULTING_MODE )
			menu.LoadMenu( IDR_CONSULTING );
		else
			menu.LoadMenu( IDR_MAINFRAME );
		SetMenu( &menu );
	}
}

void CMainFrame::OnViewAndModifyServer(LServer* pServer)
{
}

void CMainFrame::OnAddServer()
{
}

void CMainFrame::OnRemoveServer(LServer* pServer)
{
}

BOOL CMainFrame::OnLoginServer(LServer* pServer, BOOL bAutoLogin)
{
	ASSERT( pServer != NULL );
	if( pServer == NULL )
		return FALSE;
	ASSERT( pServer->getStatus() == SERVER_LOGOUT );
	if( pServer->getStatus() != SERVER_LOGOUT )
		return FALSE;
	if( !bAutoLogin )
		KillTimer( pServer->getServerId() );
	if( !pServer->create() && !bAutoLogin )
	{
		CString s;
		s.Format("无法连接服务器[%s]\n地址: %s\n端口: %d", 
			pServer->getServerName().c_str(), 
			pServer->getIP().c_str(), 
			pServer->getPort() );
		AfxMessageBox( s );
		m_dlgLogin.showAsLogout();
		return FALSE;
	}
	return TRUE;
}

void CMainFrame::OnCancelLoginServer(LServer* pServer)
{
}

void CMainFrame::OnLogoutServer(LServer* pServer)
{
	ASSERT( pServer != NULL );
	if( pServer == NULL )
		return;
	ASSERT( pServer->getStatus() == SERVER_LOGIN );
	if( pServer->getStatus() != SERVER_LOGIN )
		return;
	if( m_pConferenceDlg->hasRoomOpened(pServer) )
	{
		if( AfxMessageBox( "您现在正在该服务器的会议室中，确定要注销，关闭会议室么？", MB_YESNO ) == IDNO )
			return;
	}
	m_pConferenceDlg->quitAllRoom( pServer );
	pServer->destroy();
	this->modifyServer( pServer );
	this->SetTaskIcon( pServer, FALSE );
}

void CMainFrame::OnRegisterNewUser(LServer* pServer)
{
	ASSERT( pServer != NULL );
	if( pServer == NULL )
		return;
	CRegisterUserDlg dlg( pServer, this );
	dlg.DoModal();
}

void CMainFrame::OnLoginAnother(LServer* pServer)
{
}

BOOL CMainFrame::hasServerLoginOrLogining()
{
	map<UINT, LServer*>::iterator iter =  m_servers.begin();
	while( iter != m_servers.end() )
	{
		LServer* pServer = iter->second;
		if( pServer->getStatus() != SERVER_LOGOUT )
			return TRUE;
		iter++;
	}
	return FALSE;
}

void CMainFrame::loadServer( int nConfMode )
{
}

void CMainFrame::saveServer()
{
}

BOOL CMainFrame::getServerInfo( CString str, string& sName, string& sIP, int& nPort, string& sUsername, string& sPassword )
{
	return FALSE;
}

BOOL CMainFrame::getUsernameAndPassword( string& sUsername, string& sPassword, BOOL& bMember )
{
	GetNamePasswordDlg dlg( this );
	dlg.m_bRememberme = bMember;
	int ret = dlg.DoModal();
	sUsername = dlg.m_sUsername;
	sPassword = dlg.m_sPassword;
	bMember = dlg.m_bRememberme;
	return ret == IDOK;
}

void CMainFrame::removeNotifiers()
{
	POSITION pos1, pos2;
	for(pos1 = m_pTaskNotifyList.GetHeadPosition(); (pos2 = pos1) != NULL; )
	{
		CTaskbarNotifier* tn = (CTaskbarNotifier*)m_pTaskNotifyList.GetNext(pos1);
		tn->DestroyWindow();
		delete tn;
		m_pTaskNotifyList.RemoveAt(pos2);
	}
}

BOOL CMainFrame::processCmdline(CString sCmd)
{
	CString sip;
	int port;
	CString sPort;
	CString name;
	CString password;
	__int64 conferenceId;
	CString sCid;
	CString sMode;

	int pos1, pos2;
	pos1 = sCmd.Find("/s ");
	if (pos1 == -1)
		return FALSE;
	pos2 = sCmd.Find("/p ");
	if (pos2 == -1)
		return FALSE;
	sip = sCmd.Mid(pos1 + 3, pos2 - pos1 - 4);
	
	pos1 = pos2;
	pos2 = sCmd.Find("/usr ");
	if (pos2 == -1)
		return FALSE;
	sPort = sCmd.Mid(pos1 + 3, pos2 - pos1 - 4);
	port = atoi(sPort);

	pos1 = pos2;
	pos2 = sCmd.Find("/pwd ");
	if (pos2 == -1)
		return FALSE;
	name = sCmd.Mid(pos1 + 5, pos2 - pos1 - 6);

	pos1 = pos2;
	pos2 = sCmd.Find("/cid ");
	if (pos2 == -1)
		return FALSE;
	password = sCmd.Mid(pos1 + 5, pos2 - pos1 - 6);

	pos1 = pos2;
	pos2 = sCmd.Find("/mode ");
	if( pos2 == -1 )
		return FALSE;
	sCid = sCmd.Mid(pos1 + 5, pos2 - pos1 - 6);
	conferenceId = atoi(sCid);

	pos1 = pos2;
	sMode = sCmd.Mid( pos1 + 6 );
	m_confMode = atoi(sMode);

	m_conferenceIdFromWeb = conferenceId;
	m_dlgLogin.startLoginFromWeb( name, password, m_confMode );

	return TRUE;
}

void CMainFrame::transformData( LServer* pServer )
{
	__int64 defaultUserId = getDefaultServer()->getId();
	CString oldDataPath;
	oldDataPath.Format( "%s%I64d", PathHelper::getDataPath(), defaultUserId );
	if( !PathHelper::isDirectoryExist( oldDataPath ) )
		return;
	CString newServerPath;
	newServerPath.Format( "%s%s@%s", PathHelper::getDataPath(), pServer->getUserName().c_str(), pServer->getIP().c_str() );

	CString oldServerPath;
	oldServerPath.Format( "%s\\%s", oldDataPath, pServer->getServerName().c_str() );
	BOOL nRet;
	if( PathHelper::isDirectoryExist( oldServerPath ) )
	{
		nRet = MoveFile( oldServerPath, newServerPath );
		if( nRet == 0 )
			return;
		CString filePath = newServerPath + "\\chat\\";
		for( int i = 0; i < 2; i++ )
		{
			CFileFind finder;
			BOOL bWorking = finder.FindFile( filePath + _T("*.xml") );
			while( bWorking )
			{
				bWorking = finder.FindNextFile();
				// skip . and .. files; otherwise, we'd	recur infinitely!
				if (finder.IsDots())
					continue;
				// if it's a directory, skip search it
				if (finder.IsDirectory())
					continue;
				CString fileName = filePath + finder.GetFileName();
				CXMLParser xml;
				if( xml.Load( fileName ) )
				{
					if( xml.SetStyleSheetAttrib( "type=\"text/xsl\" href=\"../../message.xsl\"" ) )
					{
						xml.Save( fileName );
					}
				}
			}
			finder.Close();
			filePath = newServerPath + "\\conference\\";
		}
		CString leavewordFile = newServerPath + "\\leaveword.xml";
		if( PathHelper::isFileExist( leavewordFile ) )
		{
			CXMLParser xml;
			if( xml.Load( leavewordFile ) )
			{
				if( xml.SetStyleSheetAttrib( "type=\"text/xsl\" href=\"../leaveword.xsl\"" ) )
				{
					xml.Save( leavewordFile );
				}
			}
		}
	}
	if( pServer->isDefaultServer() )
	{
		if( PathHelper::isFileExist( oldDataPath + "\\Server.ini" ) )
		{
			nRet = MoveFile( oldDataPath + "\\Server.ini", newServerPath + "\\Server.ini" );
			this->loadServer( this->m_confMode );
		}
		if( PathHelper::isFileExist( oldDataPath + "\\Config.ini" ) )
		{
			nRet = MoveFile( oldDataPath + "\\Config.ini", newServerPath + "\\Config.ini" );
		}
	}
}

LRESULT CMainFrame::OnReceiveData1(WPARAM wParam,LPARAM lParam)
{
	long nDataLen;
	BOOL bResult;
	while(1){
		nDataLen=300000;//用户缓冲区的大小
		bResult = TKLSDK_GetData(this->m_lHandler[0], m_pDataBuffer1, NULL, &nDataLen);
		if(bResult){
			::EnterCriticalSection(&m_videoCaptureCs);
			decode(m_decHandle, m_pDataBuffer1, nDataLen,m_decodeBuffer,176*144*24/8);
			m_VideoHeader1.dwBytesUsed = 176*144*24/8;
			m_VideoHeader1.lpData = m_decodeBuffer;
			(*m_pCompressCallback)(m_pCallbackParameter, &m_VideoHeader1, this->m_lHandler[0]);
		    ::LeaveCriticalSection(&m_videoCaptureCs);
		}else{
			break;
		}
	}
	return 0;
}

LRESULT CMainFrame::OnReceiveData2(WPARAM wParam,LPARAM lParam)
{
	long nDataLen;
	BOOL bResult;
	while(1){
		nDataLen=300000;//用户缓冲区的大小
		bResult = TKLSDK_GetData(this->m_lHandler[1], m_pDataBuffer2, NULL, &nDataLen);
		if(bResult){//读到了数据
			::EnterCriticalSection(&m_videoCaptureCs);
			decode(m_decHandle2, m_pDataBuffer2, nDataLen,m_decodeBuffer2,176*144*24/8);
			m_VideoHeader2.dwBytesUsed = 176*144*24/8;
			m_VideoHeader2.lpData = m_decodeBuffer2;
			(*m_pCompressCallback)(m_pCallbackParameter, &m_VideoHeader2, this->m_lHandler[1]);
		    ::LeaveCriticalSection(&m_videoCaptureCs);
		}else{
			break;
		}
	}
	return 0;
}

void CMainFrame::RegisterDataRcv(LONG* handler, PIO_COMPRESS_CALLBACK pCompressCallback, void* pCallbackParameter)
{
	m_pDataBuffer1 = new unsigned char[300000];
	m_pDataBuffer2 = new unsigned char[300000];
	m_decodeBuffer = new unsigned char[300000];
	m_decodeBuffer2 = new unsigned char[300000];

	::InitializeCriticalSection(&m_videoCaptureCs);

	this->m_lHandler = handler;
	TKLSDK_RegisterDataRcvWindow(this->m_lHandler[0], this->m_hWnd, WM_TKLSDK_DATA1);
	TKLSDK_RegisterDataRcvWindow(this->m_lHandler[1], this->m_hWnd, WM_TKLSDK_DATA2);
	//TKLSDK_RegisterDataRcvWindow(this->m_lHandler[2], this->m_hWnd, WM_TKLSDK_DATA3);
	//TKLSDK_RegisterDataRcvWindow(this->m_lHandler[3], this->m_hWnd, WM_TKLSDK_DATA4);

	m_pCompressCallback = pCompressCallback;
	m_pCallbackParameter = pCallbackParameter;
	
	xvid_create(&m_decHandle, 176, 144);
	xvid_create(&m_decHandle2, 176, 144);
}

bool CMainFrame::xvid_create(void** decHandle, int nWidth, int nHeight)
{
	xvid_dec_create_t xvid_dec_create;
    memset(&xvid_dec_create, 0, sizeof(xvid_dec_create));

    xvid_dec_create.version = XVID_VERSION;
	xvid_dec_create.width = nWidth;
	xvid_dec_create.height = nHeight;

	int ret = xvid_decore(NULL, XVID_DEC_CREATE, &xvid_dec_create, NULL);
    if( ret < 0 )
    {
		_RPTF1(_CRT_WARN, "create decoder failed: %x.\n", ret);
        return false;
    }

    *decHandle = xvid_dec_create.handle;
    return true;
}

bool CMainFrame::decode(void* decHandle, unsigned char* pSrc, int nSrcLen, unsigned char* pDst, int nDstLen)
{
    _ASSERTE(decHandle != 0 );
    _ASSERTE(pSrc != 0);
    _ASSERTE(pDst != 0);

	xvid_dec_frame_t xvid_dec_frame;
	memset(&xvid_dec_frame, 0, sizeof(xvid_dec_frame_t));
	xvid_dec_frame.version = XVID_VERSION;
	xvid_dec_frame.general = 0;
	xvid_dec_frame.bitstream = pSrc;
	xvid_dec_frame.length = nSrcLen;

    xvid_dec_frame.output.csp = XVID_CSP_BGR;
	xvid_dec_frame.output.plane[0]  = pDst;
	xvid_dec_frame.output.stride[0] = 176 * 24 / 8;

	int ret = xvid_decore(decHandle, XVID_DEC_DECODE, &xvid_dec_frame, NULL);
	return ret >= 0;
}

void CMainFrame::notifyLoginSuccessed( LServer* pServer )
{
	this->transformData( pServer );
	this->modifyServer( pServer );
	m_dlgLogin.saveLoginHistory();
	m_dlgLogin.showAsLogout();
	this->SetTaskIcon( pServer, TRUE );

	if( m_confMode == TRAINING_MODE || m_confMode == CONSULTING_MODE )
	{
		m_pImageTab->ShowDialog( 1 );
	}
	else
	{
		m_pImageTab->SetTopImage(IDB_TOP);
		m_pImageTab->SetTitleHeight(48);
		m_pImageTab->SetTabImage(0, IDB_CONTACT);
		m_pImageTab->SetTabRect(0, CRect(5, 10, 52, 40));
		m_pImageTab->SetToolTip(0, "我的联系人");
		m_pImageTab->SetTabImage(1, IDB_CONFERENCE);
		m_pImageTab->SetTabRect(1, CRect(55, 10, 102, 40));
		m_pImageTab->SetToolTip(1, "会议中心");

		m_pImageTab->SetTabImage(2, IDB_SYSMSG);
		m_pImageTab->SetTabRect(2, CRect(105, 10, 152, 40));
		m_pImageTab->SetToolTip(2, "消息列表");
	}
	CRect rc;
	GetClientRect( &rc );
	rc.bottom = rc.bottom + 1;
	m_pImageTab->MoveWindow( rc );
	m_pImageTab->ShowWindow( TRUE );
}

void CMainFrame::notifyLoginFailed( const char* reason, LServer* pServer )
{
	CString* str = new CString( reason );
    ::PostMessage(this->m_hWnd, WM_LOGIN_FAILED, (WPARAM)pServer, (LPARAM)str );
}

void CMainFrame::notifyConnectionBroken( const char* message, LServer* pServer, BOOL bAlert )
{
	m_pConferenceDlg->quitAllRoom( pServer );
	pServer->destroy();
	this->modifyServer( pServer );
	this->SetTaskIcon( pServer, FALSE );
	if( bAlert )
	{
		CString* msg = new CString(message);
	    ::PostMessage(this->m_hWnd, WM_DISCONNECT_SERVER, (WPARAM)pServer, (LPARAM)msg);
	}
}

void CMainFrame::notifyCheckVersion( BOOL bHasNewVersion, BOOL bNeedUpdate, LServer* pServer )
{
	WPARAM wParam = MAKEWPARAM( bHasNewVersion, bNeedUpdate );
	PostMessage( WM_CHECK_VERSION, wParam, (LPARAM)pServer );
}

void CMainFrame::notifyAdvertizementAddress( CString sAdHomepage, CString sAdMainframe, CString sAdRoomframe )
{
	m_sAdHomepage = sAdHomepage;
	m_sAdMainframe = sAdMainframe;
	m_sAdRoomframe = sAdRoomframe;
	if( m_sAdHomepage != "" )
		ShellExecute(NULL, "open", m_sAdHomepage, NULL, NULL, SW_SHOWNORMAL);
	this->SetForegroundWindow();
	if( m_sAdMainframe != "" )
	{
		m_bShowAdDlg = TRUE;
		CRect rc;
		this->GetClientRect( &rc );
		rc.bottom = rc.bottom - 65;
		this->m_pImageTab->MoveWindow( rc );
		m_pAdBG->ShowWindow( TRUE );
		this->m_pAdBG->navigate( m_sAdMainframe );
	}
}
