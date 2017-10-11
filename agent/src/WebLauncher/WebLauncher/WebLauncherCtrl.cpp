// WebLauncherCtrl.cpp : CWebLauncherCtrl ActiveX 控件类的实现。

#include "stdafx.h"
#include "WebLauncher.h"
#include "WebLauncherCtrl.h"
#include "WebLauncherPropPage.h"
#include ".\weblauncherctrl.h"
#include "ConferenceInfo.h"
#include "..\..\Common\Common\RegistryConstant\RegistryConstant.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


IMPLEMENT_DYNCREATE(CWebLauncherCtrl, COleControl)



// 消息映射

BEGIN_MESSAGE_MAP(CWebLauncherCtrl, COleControl)
	ON_OLEVERB(AFX_IDS_VERB_PROPERTIES, OnProperties)
END_MESSAGE_MAP()



// 调度映射

BEGIN_DISPATCH_MAP(CWebLauncherCtrl, COleControl)
	DISP_FUNCTION_ID(CWebLauncherCtrl, "startConference", dispidstartConference, startConference, VT_I4, VTS_NONE)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "ConferenceId", dispidConferenceId, m_ConferenceId, VT_I4)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "UserName", dispidUserName, m_UserName, VT_BSTR)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "Password", dispidPassword, m_Password, VT_BSTR)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "ServerIP", dispidServerIP, m_ServerIP, VT_BSTR)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "Port", dispidPort, m_Port, VT_I4)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "ConferenceMode", dispidConferenceMode, m_ConferenceMode, VT_I4)
	DISP_PROPERTY_ID(CWebLauncherCtrl, "ExpertId", dispidExpertId, m_ExpertId, VT_I4)
END_DISPATCH_MAP()



// 事件映射

BEGIN_EVENT_MAP(CWebLauncherCtrl, COleControl)
END_EVENT_MAP()



// 属性页

// TODO: 按需要添加更多属性页。请记住增加计数！
BEGIN_PROPPAGEIDS(CWebLauncherCtrl, 1)
	PROPPAGEID(CWebLauncherPropPage::guid)
END_PROPPAGEIDS(CWebLauncherCtrl)



// 初始化类工厂和 guid

IMPLEMENT_OLECREATE_EX(CWebLauncherCtrl, "WEBLAUNCHER.WebLauncherCtrl.1",
	0xa566201e, 0xe4aa, 0x4628, 0xa6, 0x62, 0x98, 0xd2, 0x5, 0x17, 0xec, 0x47)



// 键入库 ID 和版本

IMPLEMENT_OLETYPELIB(CWebLauncherCtrl, _tlid, _wVerMajor, _wVerMinor)



// 接口 ID

const IID BASED_CODE IID_DWebLauncher =
		{ 0x65C853C0, 0x7F4C, 0x4B36, { 0xA0, 0x2E, 0x34, 0xFF, 0xCD, 0x98, 0x15, 0xA6 } };
const IID BASED_CODE IID_DWebLauncherEvents =
		{ 0xC12194B7, 0x9FFA, 0x429B, { 0xB4, 0x47, 0x6F, 0xFF, 0x1, 0x56, 0x3E, 0x3C } };



// 控件类型信息

static const DWORD BASED_CODE _dwWebLauncherOleMisc =
	OLEMISC_INVISIBLEATRUNTIME |
	OLEMISC_SETCLIENTSITEFIRST |
	OLEMISC_INSIDEOUT |
	OLEMISC_CANTLINKINSIDE |
	OLEMISC_RECOMPOSEONRESIZE;

IMPLEMENT_OLECTLTYPE(CWebLauncherCtrl, IDS_WEBLAUNCHER, _dwWebLauncherOleMisc)



// CWebLauncherCtrl::CWebLauncherCtrlFactory::UpdateRegistry -
// 添加或移除 CWebLauncherCtrl 的系统注册表项

BOOL CWebLauncherCtrl::CWebLauncherCtrlFactory::UpdateRegistry(BOOL bRegister)
{
	// TODO: 验证您的控件是否符合单元模型线程处理规则。
	// 有关更多信息，请参考 MFC 技术说明 64。
	// 如果您的控件不符合单元模型规则，则
	// 必须修改如下代码，将第六个参数从
	// afxRegApartmentThreading 改为 0。

	if (bRegister)
		return AfxOleRegisterControlClass(
			AfxGetInstanceHandle(),
			m_clsid,
			m_lpszProgID,
			IDS_WEBLAUNCHER,
			0,
			afxRegApartmentThreading,
			_dwWebLauncherOleMisc,
			_tlid,
			_wVerMajor,
			_wVerMinor);
	else
		return AfxOleUnregisterClass(m_clsid, m_lpszProgID);
}



// CWebLauncherCtrl::CWebLauncherCtrl - 构造函数

CWebLauncherCtrl::CWebLauncherCtrl()
{
	InitializeIIDs(&IID_DWebLauncher, &IID_DWebLauncherEvents);

	m_ServerIP = "";
	m_Port = 4000;
	m_UserName = "";
	m_Password = "";
	m_ConferenceId = 0;
	m_ConferenceMode = 1;
	m_ExpertId = 0;
}


// CWebLauncherCtrl::~CWebLauncherCtrl - 析构函数

CWebLauncherCtrl::~CWebLauncherCtrl()
{
}

// CWebLauncherCtrl 消息处理程序

LONG CWebLauncherCtrl::startConference(void)
{
	AFX_MANAGE_STATE(AfxGetStaticModuleState());

	//查找窗口,看客户端是否正在运行
	CWnd* pWnd = FindWindow(NULL, APP_TITLE);
	if( pWnd != NULL )
	{
		//发送消息到客户端
		COPYDATASTRUCT MyCDS;
		CONFERENCE_INFO cinfo;

		strncpy( cinfo.ip, m_ServerIP.GetString(), sizeof(cinfo.ip) );
		strncpy( cinfo.name, m_UserName.GetString(), sizeof(cinfo.name) );
		strncpy( cinfo.password, m_Password.GetString(), sizeof(cinfo.password) );
		cinfo.port = m_Port;
		cinfo.conferenceId = m_ConferenceId;
		cinfo.conferenceMode = m_ConferenceMode;
		cinfo.peerid = m_ExpertId;

		MyCDS.dwData = 100;          // function identifier
		MyCDS.cbData = sizeof( cinfo );  // size of data
		MyCDS.lpData = &cinfo;           // data structure
		::SendMessage( pWnd->GetSafeHwnd(), WM_COPYDATA, (WPARAM) GetSafeHwnd(), (LPARAM) (LPVOID) &MyCDS );
		return 0;
	}

	//查找注册表,看客户端是否安装
	HKEY hKey;
	LONG ret = RegOpenKey(HKEY_LOCAL_MACHINE, REG_KEY_ROOT, &hKey);
	if (ret != ERROR_SUCCESS )
	{
		RegCloseKey(hKey);
		return -1;
	}

	char path[_MAX_DIR];
	DWORD len;
	ret = RegQueryValueEx(hKey, REG_KEY_INSTALLDIR, 0, 0, (unsigned char*)path, &len);
	RegCloseKey(hKey);
	if (ret != ERROR_SUCCESS )
	{
		return -1;
	}

	//启动新客户端
	CString params;
	params.Format( "/server %s /port %d /user %s /password %s /confId %d /confMode %d /peerId %d",
					m_ServerIP, m_Port, m_UserName, m_Password, m_ConferenceId, m_ConferenceMode, m_ExpertId );

	ShellExecute(NULL, "open", "flvcc.exe", params, path, SW_SHOWNORMAL); 

	return 0;
}
