// WebLauncherPropPage.cpp : CWebLauncherPropPage 属性页类的实现。

#include "stdafx.h"
#include "WebLauncher.h"
#include "WebLauncherPropPage.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


IMPLEMENT_DYNCREATE(CWebLauncherPropPage, COlePropertyPage)



// 消息映射

BEGIN_MESSAGE_MAP(CWebLauncherPropPage, COlePropertyPage)
END_MESSAGE_MAP()



// 初始化类工厂和 guid

IMPLEMENT_OLECREATE_EX(CWebLauncherPropPage, "WEBLAUNCHER.WebLauncherPropPage.1",
	0x37aea53f, 0x2c6a, 0x4a06, 0xb5, 0x91, 0x0, 0x51, 0x76, 0x98, 0xb6, 0x47)



// CWebLauncherPropPage::CWebLauncherPropPageFactory::UpdateRegistry -
// 添加或移除 CWebLauncherPropPage 的系统注册表项

BOOL CWebLauncherPropPage::CWebLauncherPropPageFactory::UpdateRegistry(BOOL bRegister)
{
	if (bRegister)
		return AfxOleRegisterPropertyPageClass(AfxGetInstanceHandle(),
			m_clsid, IDS_WEBLAUNCHER_PPG);
	else
		return AfxOleUnregisterClass(m_clsid, NULL);
}



// CWebLauncherPropPage::CWebLauncherPropPage - 构造函数

CWebLauncherPropPage::CWebLauncherPropPage() :
	COlePropertyPage(IDD, IDS_WEBLAUNCHER_PPG_CAPTION)
{
}



// CWebLauncherPropPage::DoDataExchange - 在页和属性间移动数据

void CWebLauncherPropPage::DoDataExchange(CDataExchange* pDX)
{
	DDP_PostProcessing(pDX);
}



// CWebLauncherPropPage 消息处理程序
