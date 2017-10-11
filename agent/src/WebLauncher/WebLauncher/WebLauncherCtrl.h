#pragma once

// WebLauncherCtrl.h : CWebLauncherCtrl ActiveX 控件类的声明。

// CWebLauncherCtrl : 有关实现的信息，请参阅 WebLauncherCtrl.cpp。

class CWebLauncherCtrl : public COleControl
{
	DECLARE_DYNCREATE(CWebLauncherCtrl)

// 构造函数
public:
	CWebLauncherCtrl();

// 重写
public:

// 实现
protected:
	~CWebLauncherCtrl();

	DECLARE_OLECREATE_EX(CWebLauncherCtrl)    // 类工厂和 guid
	DECLARE_OLETYPELIB(CWebLauncherCtrl)      // GetTypeInfo
	DECLARE_PROPPAGEIDS(CWebLauncherCtrl)     // 属性页 ID
	DECLARE_OLECTLTYPE(CWebLauncherCtrl)		// 类型名称和杂项状态

// 消息映射
	DECLARE_MESSAGE_MAP()

// 调度映射
	DECLARE_DISPATCH_MAP()

// 事件映射
	DECLARE_EVENT_MAP()

// 调度和事件 ID
public:
	enum {
		dispidExpertId = 8,		dispidConferenceMode = 7,		dispidPort = 6,		dispidServerIP = 5,		dispidPassword = 4,		dispidUserName = 3,		dispidConferenceId = 2,		dispidstartConference = 1L
	};

	LONG startConference(void);
protected:
	LONGLONG m_ConferenceId;
	CString m_UserName;
	CString m_Password;
	CString m_ServerIP;
	LONG m_Port;
	LONG m_ConferenceMode;
	LONGLONG m_ExpertId;
};

