// ui\LoginDialog.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "LoginDialog.h"
#include ".\logindialog.h"
#include "MainFrm.h"
#include "ShowXMLDlg.h"
#include "UserDefinedMessage.h"
#include "ServerDlg.h"
#include "..\..\Model\LServer.h"
#include "..\..\..\common\common\des\d3des.h"
#include "..\..\..\common\common\PathHelper\PathHelper.h"

unsigned char CLoginDialog::m_fixedkey[] = {23,82,107,6,35,78,88,7};
#define HISTORY_NUMBER 10
// CLoginDialog 对话框

IMPLEMENT_DYNAMIC(CLoginDialog, CDialog)
CLoginDialog::CLoginDialog(CWnd* pParent /*=NULL*/)
	: CDialog(CLoginDialog::IDD, pParent)
{
	m_bMemPwd = FALSE;
	m_bMemName = FALSE;
	m_crBackGround = RGB( 228, 240, 254 );
	m_crText = RGB( 1, 37, 134 );
	m_pDefaultServer = NULL;
}

CLoginDialog::~CLoginDialog()
{
}

void CLoginDialog::DoDataExchange(CDataExchange* pDX)
{
//	DDX_Control(pDX, IDC_REGISTER_BTN, m_btnRegister);
	DDX_Control(pDX, IDC_LOGIN_BTN, m_btnLogin);

	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CLoginDialog, CDialog)
//	ON_BN_CLICKED(IDC_REGISTER_BTN, OnBnClickedRegisterBtn)
	ON_BN_CLICKED(IDC_LOGIN_BTN, OnBnClickedLoginBtn)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
	ON_WM_SIZE()
	ON_WM_MOUSEMOVE()
	ON_WM_ERASEBKGND()
	ON_CBN_SELCHANGE(IDC_USERNAME, OnCbnSelchangeUsername)
	ON_BN_CLICKED(IDC_REMEMBERPASSWORD, OnBnClickedRememberpassword)
	ON_BN_CLICKED(IDC_REMEMBERUSERNAME, OnBnClickedRememberusername)
END_MESSAGE_MAP()


// CLoginDialog 消息处理程序

void CLoginDialog::OnBnClickedRegisterBtn()
{
	CString sIP = getServerIP();
	if( sIP == "" )
	{
		AfxMessageBox( "请先添加要注册的服务器地址。");
		return;
	}
	CString sName = getServerName();
	int nPort = getServerPort();
	CMainFrame* pMain = (CMainFrame*)GetParentOwner();
	if( m_pDefaultServer == NULL )
	{
		m_pDefaultServer = pMain->addServer( sName.GetString(), sIP.GetString(), nPort, "", "", TRUE );
	}
	else
	{
		m_pDefaultServer->setIP( sIP.GetString() );
		m_pDefaultServer->setPort( nPort );
	}
	pMain->OnRegisterNewUser( m_pDefaultServer );
}

void CLoginDialog::OnBnClickedLoginBtn()
{
	CString sName = getServerName();
	CString sIP = getServerIP();
	int nPort = getServerPort();
	CString sUsername, sPassword;
	GetDlgItem(IDC_USERNAME)->GetWindowText( sUsername );
	GetDlgItem(IDC_PASSWORD)->GetWindowText( sPassword );
	if( sIP == "" || sUsername == "" || sPassword == "" )
	{
		AfxMessageBox( "服务器地址或用户名或密码不能为空。");
		return;
	}

		CMainFrame* pFrame = (CMainFrame*)GetParentOwner();
		if( m_pDefaultServer == NULL )
		{
			m_pDefaultServer = pFrame->addServer( sName.GetString(), sIP.GetString(), nPort, 
												  sUsername.GetString(), sPassword.GetString(), TRUE );
		}
		else
		{
			m_pDefaultServer->setServerName( sName.GetString() );
			m_pDefaultServer->setIP( sIP.GetString() );
			m_pDefaultServer->setPort( nPort );
			m_pDefaultServer->setUserName( sUsername.GetString() );
			m_pDefaultServer->setPassword( sPassword.GetString() );
		}
		if( !m_bMemPwd )
			GetDlgItem(IDC_PASSWORD)->SetWindowText("");
		m_pDefaultServer->setRememberUserPass(m_bMemPwd);
		pFrame->OnLoginServer( m_pDefaultServer );
}

void CLoginDialog::OnCbnSelchangeUsername()
{
	CComboBox* pcb = (CComboBox*) GetDlgItem(IDC_USERNAME);
	CString name;
	pcb->GetLBText( pcb->GetCurSel(), name );
	((CButton*) GetDlgItem(IDC_REMEMBERUSERNAME))->SetCheck( BST_CHECKED );
	m_bMemName = TRUE;
	GetDlgItem(IDC_REMEMBERPASSWORD)->EnableWindow();

	CString iniFileName = PathHelper::getLoginHistoryFile();
	char buffer[255];
	CString str1,str2;
	for( int i = 1; i <= HISTORY_NUMBER; i++ )
	{
		_snprintf(buffer, 64, "h%d", i);
		::GetPrivateProfileString("HISTORY", buffer, "", buffer, sizeof(buffer), iniFileName);
		str1 = buffer;
		if( str1 != "" )
		{
			int pos1;
			//登录名
			pos1 = str1.Find(":");
			str2 = str1.Left(pos1);
			if( str2 != name )
				continue;
			//密码
			CButton* pbtn = (CButton*) GetDlgItem(IDC_REMEMBERPASSWORD);
			str2 = str1.Mid(pos1 + 1);
			if(str2 != "")
			{
				str2 = DecodePassword(str2);
				pbtn->SetCheck(BST_CHECKED);
				m_bMemPwd = TRUE;
			}
			else
			{
				m_bMemPwd = FALSE;
				pbtn->SetCheck(BST_UNCHECKED);
			}
			GetDlgItem(IDC_PASSWORD)->SetWindowText(str2);
			break;
		}
	}
}

void CLoginDialog::OnBnClickedRememberpassword()
{
	CButton* pbtn = (CButton*) GetDlgItem(IDC_REMEMBERPASSWORD);
	m_bMemPwd = pbtn->GetCheck();
}

void CLoginDialog::OnBnClickedRememberusername()
{
	CButton* pbtn = (CButton*) GetDlgItem(IDC_REMEMBERUSERNAME);
	m_bMemName = pbtn->GetCheck();
	GetDlgItem(IDC_REMEMBERPASSWORD)->EnableWindow( m_bMemName );
}

BOOL CLoginDialog::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_btnLogin.setBitmaps(IDB_LOGIN1, IDB_LOGIN2, IDB_LOGIN3);
	m_btnRegister.setTextColor( RGB(255, 0, 0), RGB(0, 0, 255) );
	
	m_brush.CreateSolidBrush( m_crBackGround );

	getLoginHistory();

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CLoginDialog::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	// 不为绘图消息调用 CDialog::OnPaint()
	CRect rc;
	GetClientRect(&rc);

	CDC memDC;
	memDC.CreateCompatibleDC(&dc);
	CBitmap bmp;
	bmp.CreateCompatibleBitmap(&dc, rc.Width(), rc.Height());
	memDC.SelectObject(&bmp);
	memDC.FillSolidRect(&rc, m_crBackGround);

	CDC bkDC;
	bkDC.CreateCompatibleDC(&dc);
	CBitmap back;
	back.LoadBitmap(IDB_LOGINBK);
	bkDC.SelectObject( &back );
	int x = 0;
	while( x < rc.Width() ) 
	{
		memDC.BitBlt( x, 0, 676, 110, &bkDC, 0, 0, SRCCOPY);
 		x += 676;
 	}

	//CBitmap logo;
	//logo.LoadBitmap(IDB_LOGINLOGO);
	//bkDC.SelectObject( &logo );
	//memDC.BitBlt(0, 0, 201, 110, &bkDC, 0, 0, SRCCOPY);

	// 画到显示器上
	dc.BitBlt(0, 0, rc.Width(), rc.Height(), &memDC, 0, 0, SRCCOPY);
}

HBRUSH CLoginDialog::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_ENTERUSERNAME 
		|| pWnd->GetDlgCtrlID() == IDC_ENTERPASSWORD
		|| pWnd->GetDlgCtrlID() == IDC_SELECTSERVER
		|| pWnd->GetDlgCtrlID() == IDC_REMEMBERUSERNAME
		|| pWnd->GetDlgCtrlID() == IDC_REMEMBERPASSWORD )
//		|| pWnd->GetDlgCtrlID() == IDC_REGISTER_BTN)
	{
		pDC->SetTextColor( m_crText );
		pDC->SetBkMode(TRANSPARENT);
		hbr = m_brush;
	}
	return hbr;
}


void CLoginDialog::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	Invalidate();
	int nWidth = 160;
	if( GetDlgItem(IDC_SELECTSERVER)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_SELECTSERVER)->MoveWindow( (cx-nWidth)/2, 120, nWidth, 20 );
	}
	if( GetDlgItem(IDC_SERVERLIST)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_SERVERLIST)->MoveWindow( (cx-nWidth)/2, 140, nWidth, 20 );
	}
	if( GetDlgItem(IDC_ENTERUSERNAME)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_ENTERUSERNAME)->MoveWindow( (cx-nWidth)/2, 170, nWidth, 20 );
	}
	if( GetDlgItem(IDC_USERNAME)->GetSafeHwnd() )
	{
        GetDlgItem(IDC_USERNAME)->MoveWindow( (cx-nWidth)/2, 190, nWidth, 20 );
	}
	if( GetDlgItem(IDC_ENTERPASSWORD)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_ENTERPASSWORD)->MoveWindow( (cx-nWidth)/2, 220, nWidth, 20 );
	}
	if( GetDlgItem(IDC_PASSWORD)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_PASSWORD)->MoveWindow( (cx-nWidth)/2, 240, nWidth, 20 );
	}
	if( GetDlgItem(IDC_REMEMBERUSERNAME)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_REMEMBERUSERNAME)->MoveWindow( (cx-nWidth)/2, 270, nWidth, 20 );
	}
	if( GetDlgItem(IDC_REMEMBERPASSWORD)->GetSafeHwnd() )
	{
		GetDlgItem(IDC_REMEMBERPASSWORD)->MoveWindow( (cx-nWidth)/2, 290, nWidth, 20 );
	}
	if( m_btnRegister.GetSafeHwnd() )
	{
		m_btnRegister.MoveWindow( (cx-nWidth)/2+10, 310, 100, 22 );
	}
	if( m_btnLogin.GetSafeHwnd() )
	{
		m_btnLogin.MoveWindow( (cx-70)/2, 360, 70, 24 );
	}
}

BOOL CLoginDialog::OnEraseBkgnd(CDC* pDC)
{
	//return CDialog::OnEraseBkgnd(pDC);
	return TRUE;
}

void CLoginDialog::OnOK()
{
	if( this->m_pDefaultServer == NULL || this->m_pDefaultServer->getStatus() == SERVER_LOGOUT )	
		this->OnBnClickedLoginBtn();
}

void CLoginDialog::OnCancel()
{
}

CString CLoginDialog::getServerName()
{
	CString sServerName;
	GetDlgItem(IDC_SERVERLIST)->GetWindowText( sServerName );
	return sServerName;
}

CString CLoginDialog::getServerIP()
{
	CComboBox* pcb = (CComboBox*) GetDlgItem(IDC_SERVERLIST);
	int nIndex = pcb->GetCurSel();
    char buffer[512];
	_snprintf(buffer, 64, "s%d", nIndex+1);
	CString iniFileName = PathHelper::getLoginHistoryFile();
	::GetPrivateProfileString("CONFERENCE_SERVER", buffer, "", buffer, sizeof(buffer), iniFileName);
	CString sServerIP = buffer;
	sServerIP = sServerIP.Mid(sServerIP.Find(':') + 1, sServerIP.ReverseFind(':') - sServerIP.Find(':') - 1);
	return sServerIP;
}

int CLoginDialog::getServerPort()
{
	CComboBox* pcb = (CComboBox*) GetDlgItem(IDC_SERVERLIST);
	int nIndex = pcb->GetCurSel();
    char buffer[512];
	_snprintf(buffer, 64, "s%d", nIndex+1);
	CString iniFileName = PathHelper::getLoginHistoryFile();
	::GetPrivateProfileString("CONFERENCE_SERVER", buffer, "", buffer, sizeof(buffer), iniFileName);
	CString sPort = buffer;
	sPort = sPort.Right(sPort.GetLength() - sPort.ReverseFind(':') - 1);
	return atoi(sPort);
}

void CLoginDialog::addServer(CString sServerName, CString sIP, int nPort)
{
	char buffer[512];
	CString sIniFileName = PathHelper::getLoginHistoryFile();
	CString history = sServerName + CString(":") + sIP + CString(":") + CString(itoa(nPort, buffer, 10));
	CString str;

	for(int j = 1; j <= HISTORY_NUMBER; ++j)
	{
		_snprintf(buffer, 64, "s%d", j);
		::GetPrivateProfileString("CONFERENCE_SERVER", buffer, "", buffer, sizeof(buffer), sIniFileName);
		str = buffer;
		if(str == "")
		{
			_snprintf(buffer, 64, "s%d", j);
			::WritePrivateProfileString("CONFERENCE_SERVER", buffer, history, sIniFileName);
			break;
		}
	}
	_snprintf(buffer, 64, "s%d", j+1);
	::WritePrivateProfileString("CONFERENCE_SERVER", buffer, "", sIniFileName);

	CComboBox* pcb = (CComboBox*) GetDlgItem(IDC_SERVERLIST);
	pcb->InsertString(pcb->GetCount(), sServerName);
	pcb->SetCurSel(pcb->GetCount());
}

void CLoginDialog::getLoginHistory()
{
	CString iniFileName = PathHelper::getLoginHistoryFile();
	char buffer[255];
	CString s;
	CComboBox* pcb = NULL;

	// 得到登录用户记录
	pcb = (CComboBox*) GetDlgItem(IDC_USERNAME);
	for(int i = 1; i <= HISTORY_NUMBER; i++)
	{
		_snprintf(buffer, 64, "h%d", i);
		::GetPrivateProfileString("HISTORY", buffer, "", buffer, sizeof(buffer), iniFileName);
		s = buffer;
		if (s == "")
			continue;
		pcb->AddString(s.Left(s.Find(":")));
	}
	if( pcb->GetCount() > 0 )
	{
		pcb->SetCurSel(0);
		this->OnCbnSelchangeUsername();
	}

	//得到登录服务器记录
	pcb = (CComboBox*) GetDlgItem(IDC_SERVERLIST);
	for(int i = 1; i <= HISTORY_NUMBER; i++)
	{
		_snprintf(buffer, 64, "s%d", i);
		::GetPrivateProfileString("CONFERENCE_SERVER", buffer, "", buffer, sizeof(buffer), iniFileName);
		s = buffer;
		if (s == "")
			break;
		pcb->AddString(s.Left(s.Find(":")));
	}
	if( pcb->GetCount() > 0 )
	{
		pcb->SetCurSel(0);
	}
	else
	{
		CServerDlg dlg( ADD_SERVER, NULL, this );
		dlg.m_sTitle = "添加服务器";
		dlg.m_sDescription = "您还没有设置登录服务器，请先输入服务器信息。";
		if( dlg.DoModal() == IDOK )
		{
			addServer(dlg.m_sServerName.GetString(), dlg.m_sServerIP.GetString(), dlg.m_nPort);
		}
	}
}

void CLoginDialog::saveLoginHistory()
{
	this->saveLoginServer();

	if( !( m_bMemName || m_pDefaultServer->isRememberUserPass() ) )
		return;

	char buffer[255];
	CString g_IniFileName = PathHelper::getLoginHistoryFile();
	CString history = "";
	CString sEncodedPwd = "";
	CString sUsername = this->m_pDefaultServer->getUserName().c_str();
	CString sPassword = this->m_pDefaultServer->getPassword().c_str();
	
	history += sUsername + ":";
	if( this->m_pDefaultServer->isRememberUserPass() )
	{
		sEncodedPwd = EncodePassword( sPassword );
		history += sEncodedPwd;
	}

	int i;
	for(i = 1; i <= HISTORY_NUMBER; i++)
	{
		_snprintf(buffer, 64, "h%d", i);
		::GetPrivateProfileString("HISTORY", buffer, "", buffer, sizeof(buffer), g_IniFileName);
		CString s = buffer;
		if (s != "")
		{
			CString sName = s.Left(s.Find(":"));
			if(sName == sUsername)
			{
				break;
			}
		}
	}

	int j = i > HISTORY_NUMBER - 1 ? HISTORY_NUMBER - 1 : i-1;
	for(; j > 0; j--)
	{
		_snprintf(buffer, 64, "h%d", j);
		::GetPrivateProfileString("HISTORY", buffer, "", buffer, sizeof(buffer), g_IniFileName);
		CString str = buffer;
		if (str == "")
			continue;
		_snprintf(buffer, 64, "h%d", j+1);
		::WritePrivateProfileString("HISTORY", buffer, str, g_IniFileName);	
	}
	::WritePrivateProfileString("HISTORY", "h1", history, g_IniFileName);	
}

void CLoginDialog::saveLoginServer()
{
	char buffer[512];
	CString sIniFileName = PathHelper::getLoginHistoryFile();
	CString history;
	history.Format( "%s:%s:%d", m_pDefaultServer->getServerName().c_str(), m_pDefaultServer->getIP().c_str(), m_pDefaultServer->getPort() );
	CString str;

	int i;
	for(i = 1; i <= HISTORY_NUMBER; i++)
	{
		_snprintf(buffer, 64, "s%d", i);
		::GetPrivateProfileString("CONFERENCE_SERVER", buffer, "", buffer, sizeof(buffer), sIniFileName);
		str = buffer;
		if (str == history)
		{
			break;
		}
	}

	int j = i > HISTORY_NUMBER - 1 ? HISTORY_NUMBER - 1 : i-1;
	for(; j > 0; j--)
	{
		_snprintf(buffer, 64, "s%d", j);
		::GetPrivateProfileString("CONFERENCE_SERVER", buffer, "", buffer, sizeof(buffer), sIniFileName);
		str = buffer;
		if (str == "")
			continue;
		_snprintf(buffer, 64, "s%d", j+1);
		::WritePrivateProfileString("CONFERENCE_SERVER", buffer, str, sIniFileName);	
	}
	::WritePrivateProfileString("CONFERENCE_SERVER", "s1", history, sIniFileName);	
}

//加密写到配置文件的密码
CString CLoginDialog::EncodePassword(CString strPass)
{
	// Calculate padding length.
	int nLen = strPass.GetLength();
	nLen += (8- nLen % 8) + 8;
	
	// Fill heading 8 bytes with password length
	unsigned char* pPlain = new unsigned char[nLen];
	memset(pPlain, 0, nLen);
	_snprintf( (char*)pPlain, 8, "%d", strPass.GetLength() );

	// Copy other bytes.
	strcpy((char*)pPlain + 8, strPass);

	// Allocate encrypted length.
	unsigned char* pEncrypted;
	pEncrypted = new unsigned char[ nLen ];
	memset(pEncrypted, 0, nLen);

		// Encrypt
	unsigned char* pEncryptedBak = pEncrypted;
	unsigned char* pPlainBak     = pPlain;
	deskey(m_fixedkey, EN0);
	for(int i=0; i<nLen/8; i++)
	{
		des(pPlain, pEncrypted);
		pPlain += 8;
		pEncrypted += 8;
	}

	char buffer[1024];

	char* pBuffer = (char*)buffer;
	for(i=0; i<nLen; i++ )
	{
		sprintf(pBuffer, "%02x", pEncryptedBak[i]);
		pBuffer += 2;
	}

	// free.
	delete[] pPlainBak;
	delete[] pEncryptedBak;
	return buffer;
}
//解密
CString CLoginDialog::DecodePassword(CString strPass)
{
	char buffer[1024];

	strcpy(buffer, strPass);
	// If buffer is null, return it.
	if (strlen(buffer) == 0)
		return "";

	// Judge if the size is multiple size of 8
	if (strlen(buffer) % 8 != 0 )
		return "";

	// allocate text buffer
	int nLen = strlen(buffer) / 2;
	unsigned char* pPlain     = new unsigned char[nLen];
	unsigned char* pEncrypted = new unsigned char[nLen];
	ZeroMemory(pPlain, nLen);
	ZeroMemory(pEncrypted, nLen);

	// Convert string to memory bytes
	char number[3];
	number[2] = 0;
	for(int i=0; i<nLen; i++)
	{
		number[0] = buffer[i*2];
		number[1] = buffer[i*2+1];
	#pragma warning(push)
	#pragma warning(disable:4244)
		pEncrypted[i] = strtol(number, NULL, 16);
	#pragma warning(pop)
	}

	// Decrpt it
	unsigned char* pEncryptedBak = pEncrypted;
	unsigned char* pPlainBak     = pPlain;
	deskey(m_fixedkey, DE1);
	for(i=0; i<nLen/8; i++)
	{
		des(pEncrypted, pPlain);
		pPlain += 8;
		pEncrypted += 8;
	}

	// Send text to window
	char aPassLen[9];
	ZeroMemory(aPassLen, 9);
	memcpy(aPassLen, pPlainBak, 8);
	int nPassLen = atoi(aPassLen);
	string str = string( (char*)pPlainBak+8, nPassLen );

	// delete memory
	delete[] pPlainBak;
	delete[] pEncryptedBak;

	return str.c_str();
}
