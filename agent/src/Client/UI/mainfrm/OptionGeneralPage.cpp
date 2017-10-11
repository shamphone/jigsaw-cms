// OptionGeneralPage.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "OptionGeneralPage.h"
#include "dirdlg.h"
#include "Common/Common/PathHelper/PathHelper.h"
#include ".\optiongeneralpage.h"

// COptionGeneralPage 对话框

IMPLEMENT_DYNAMIC(COptionGeneralPage, CDialog)
COptionGeneralPage::COptionGeneralPage(CWnd* pParent)
	: CDialog(COptionGeneralPage::IDD, pParent)
{
	m_bCtrl = false;
	m_bShift = false;
	m_nKey = -1;
}

COptionGeneralPage::~COptionGeneralPage()
{
}

void COptionGeneralPage::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(COptionGeneralPage, CDialog)
	ON_BN_CLICKED(IDC_SELECT_RECEIVE_PATH, OnBnClickedSelectReceivePath)
	ON_BN_CLICKED(IDC_AUTORUN, OnBnClickedAutorun)
	ON_BN_CLICKED(IDC_AUTOLOGIN, OnBnClickedAutologin)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// COptionGeneralPage 消息处理程序

BOOL COptionGeneralPage::OnInitDialog()
{
	CDialog::OnInitDialog();

	char buffer[1024];
	CString g_IniFileName = PathHelper::getIniFileFullName();
	int n;
	CString s;

	n = ::GetPrivateProfileInt("OPTION", "auto_login", 0, g_IniFileName);
	((CButton*) GetDlgItem(IDC_AUTOLOGIN))->SetCheck(n?BST_CHECKED:BST_UNCHECKED);

	::GetPrivateProfileString("OPTION", "recv_path", "", buffer, sizeof(buffer), g_IniFileName);
	s = buffer;
	if( s == "" )
	{
		s = PathHelper::getDefaultReceivedFilePath();
	}
	GetDlgItem(IDC_RECEIVE_PATH)->SetWindowText(s);

	//查找注册表
	HKEY hKey;
	LONG ret = RegOpenKey(HKEY_LOCAL_MACHINE, "Software\\Microsoft\\Windows\\CurrentVersion\\Run\\", &hKey);
	if (ret == ERROR_SUCCESS )
	{
		char path[MAX_PATH];
		DWORD len;
		ret = RegQueryValueEx(hKey, "LongCon VCS", 0, 0, (unsigned char*)path, &len);

		char file[MAX_PATH];
		char fileLong[MAX_PATH];
		::GetModuleFileName(NULL, fileLong, MAX_PATH);
		::GetShortPathName(fileLong, file, MAX_PATH);
		if( lstrcmp(path, file) == 0 )
			((CButton*) GetDlgItem(IDC_AUTORUN))->SetCheck(BST_CHECKED);
		else
			((CButton*) GetDlgItem(IDC_AUTORUN))->SetCheck(BST_UNCHECKED);
	}
	RegCloseKey(hKey);

	n = ::GetPrivateProfileInt("OPTION", "key_quit_fullscreen_desktop", 0x00010000, g_IniFileName);

	CString str = "";
	if( n & 0x00000001 )
	{
		str += "Ctrl + ";
		m_bCtrl = true;
	}
	if( n & 0x00000010 )
	{
		str += "Shift + ";
		m_bShift = true;
	}
	if( GetKeyNameText(n, buffer, 1024) )
	{
		m_nKey = n >> 16;
		str += buffer;
		GetDlgItem(IDC_QUITFULLSCREENDESKTOP)->SetWindowText(str);
	}
	((CEdit*)GetDlgItem(IDC_QUITFULLSCREENDESKTOP))->SetReadOnly();

	m_brush.CreateSolidBrush( RGB(240, 245, 250) );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void COptionGeneralPage::apply()
{
	writeIni();
}

void COptionGeneralPage::writeIni()
{
	char buffer[64];
	char* g_IniFileName = ::GetApp()->getIniFilename();
	int n;
	CString s;

	GetDlgItem(IDC_RECEIVE_PATH)->GetWindowText(s);
	::WritePrivateProfileString("OPTION", "recv_path", s, g_IniFileName);

	char File[MAX_PATH];
	char fileLong[MAX_PATH];
	HKEY hKey;
	::GetModuleFileName(NULL, fileLong, MAX_PATH);
	::GetShortPathName(fileLong, File, MAX_PATH);
	::RegCreateKey(HKEY_LOCAL_MACHINE, "SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", &hKey);	
	if( ((CButton*) GetDlgItem(IDC_AUTORUN))->GetCheck() )
		RegSetValueEx(hKey, "LongCon VCS", 0, REG_SZ, (unsigned char*)File, strlen(File));
    else
	    ::RegDeleteValue(hKey, "LongCon VCS");
	::RegCloseKey(hKey);

	n = ((CButton*) GetDlgItem(IDC_AUTOLOGIN))->GetCheck();
	_snprintf(buffer, 64, "%d", n);
	::WritePrivateProfileString("OPTION", "auto_login", buffer, g_IniFileName);

	if( m_nKey > 0 )
	{
		n = 0;
		if( m_bCtrl )
			n = n | 0x00000001;
		if( m_bShift )
			n = n | 0x00000010;
		n = MAKEWPARAM( n, m_nKey );
		_snprintf(buffer, 64, "%d", n);
		::WritePrivateProfileString("OPTION", "key_quit_fullscreen_desktop", buffer, g_IniFileName);
	}
	else if( m_nKey == 0 )
	{
		::WritePrivateProfileString("OPTION", "key_quit_fullscreen_desktop", "", g_IniFileName);
	}
}

void COptionGeneralPage::OnBnClickedSelectReceivePath()
{
	CDirDlg dlg(this);
	GetDlgItem(IDC_RECEIVE_PATH)->GetWindowText(dlg.m_sPath);
	int nRet = dlg.DoModal();

	if(nRet!=IDOK)
		return ;

	DWORD dwAttr = 0L;
	dwAttr = GetFileAttributes(dlg.m_sPath);
	if(dwAttr == 0xFFFFFFFF || dwAttr & FILE_ATTRIBUTE_READONLY )
	{
		AfxMessageBox("选择的路径无效或者是只读的，请重新选择！");
		return;
	}

	GetDlgItem(IDC_RECEIVE_PATH)->SetWindowText(dlg.m_sPath);

	UpdateData();
}

void COptionGeneralPage::OnBnClickedAutorun()
{
	UpdateData();
}

void COptionGeneralPage::OnBnClickedAutologin()
{
	UpdateData();
}

void COptionGeneralPage::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CBrush brush;
	brush.CreateSolidBrush( RGB(159, 177, 225) );
	dc.FillSolidRect( rc, RGB(240, 245, 250) );
	rc.InflateRect( 0, 20, 0, 0 );
	dc.FrameRect( rc, &brush );
	// 不为绘图消息调用 CDialog::OnPaint()
}

HBRUSH COptionGeneralPage::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);
	if( pWnd->GetDlgCtrlID() == IDC_STATIC
		|| pWnd->GetDlgCtrlID() == IDC_AUTORUN
		|| pWnd->GetDlgCtrlID() == IDC_AUTOLOGIN
		|| pWnd->GetDlgCtrlID() == IDC_RECEIVE_PATH )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}

BOOL COptionGeneralPage::PreTranslateMessage(MSG* pMsg)
{
	if( GetFocus() == GetDlgItem(IDC_QUITFULLSCREENDESKTOP) )
	{
		switch (pMsg->message)
		{
		case WM_CHAR:
			{
				return 1;
			}
		case WM_KEYDOWN:
			{
				switch (pMsg->wParam)
				{
				case VK_TAB:
				case VK_CONTROL:
				case VK_SHIFT:
				//case VK_RETURN:
				//case VK_ESCAPE:
					break;
				case VK_LWIN:
				case VK_RWIN:
				case VK_APPS:
				case VK_SPACE:
					return 1;
				case VK_BACK:
				case VK_DELETE:
					m_nKey = 0;
					GetDlgItem(IDC_QUITFULLSCREENDESKTOP)->SetWindowText("");
					return 1;

				default:
					{
						CString str;
						SHORT state = GetKeyState(VK_CONTROL);
						if( state & 0x8000 )
						{
							str = "Ctrl + ";
							m_bCtrl = true;
						}
						else
							m_bCtrl = false;
						state = GetKeyState(VK_SHIFT);
						if( state & 0x8000 )
						{
							str += "Shift + ";
							m_bShift = true;
						}
						else
							m_bShift = false;

						m_nKey = pMsg->lParam >> 16;
						char buf[256];
						GetKeyNameText(pMsg->lParam, buf, 256);
						str += CString(buf);
						TRACE2( "%d  %s\n", pMsg->wParam, buf);
						GetDlgItem(IDC_QUITFULLSCREENDESKTOP)->SetWindowText(str);
                        UpdateData();

						return 0;
					}
				}
			}
		}
	}
	return CDialog::PreTranslateMessage(pMsg);
}
