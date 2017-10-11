#pragma once

// WebLauncherPropPage.h : CWebLauncherPropPage 属性页类的声明。


// CWebLauncherPropPage : 有关实现的信息，请参阅 WebLauncherPropPage.cpp。

class CWebLauncherPropPage : public COlePropertyPage
{
	DECLARE_DYNCREATE(CWebLauncherPropPage)
	DECLARE_OLECREATE_EX(CWebLauncherPropPage)

// 构造函数
public:
	CWebLauncherPropPage();

// 对话框数据
	enum { IDD = IDD_PROPPAGE_WEBLAUNCHER };

// 实现
protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

// 消息映射
protected:
	DECLARE_MESSAGE_MAP()
};

