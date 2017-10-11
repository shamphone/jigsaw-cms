/*
Miranda plugin template, originally by Richard Hughes
http://miranda-icq.sourceforge.net/

This file is placed in the public domain. Anybody is free to use or
modify it as they wish with no restriction.
There is no warranty.
*/
#include <windows.h>
#include <newpluginapi.h>
#include <m_clist.h>
#include <m_skin.h>

HINSTANCE hInst;
PLUGINLINK *pluginLink;

PLUGININFO pluginInfo={
	sizeof(PLUGININFO),
	"Plugin Template",
	PLUGIN_MAKE_VERSION(0,0,0,2),
	"The long description of your plugin, to go in the plugin options dialog",
	"J. Random Hacker",
	"noreply@sourceforge.net",
	"?2002 J. Random Hacker",
	"http://miranda-icq.sourceforge.net/",
	0,		//not transient
	0		//doesn't replace anything built-in
};

BOOL WINAPI DllMain(HINSTANCE hinstDLL,DWORD fdwReason,LPVOID lpvReserved)
{
	hInst=hinstDLL;
	return TRUE;
}

static int PluginMenuCommand(WPARAM wParam,LPARAM lParam)
{
	MessageBox(NULL,"Just groovy, baby!","Plugin-o-rama",MB_OK);
	return 0;
}

__declspec(dllexport) PLUGININFO* MirandaPluginInfo(DWORD mirandaVersion)
{
	return &pluginInfo;
}

int __declspec(dllexport) Load(PLUGINLINK *link)
{
	CLISTMENUITEM mi;

	pluginLink=link;
	CreateServiceFunction("TestPlug/MenuCommand",PluginMenuCommand);
	ZeroMemory(&mi,sizeof(mi));
	mi.cbSize=sizeof(mi);
	mi.position=-0x7FFFFFFF;
	mi.flags=0;
	mi.hIcon=LoadSkinnedIcon(SKINICON_OTHER_MIRANDA);
	mi.pszName="&Test Plugin...";
	mi.pszService="TestPlug/MenuCommand";
	CallService(MS_CLIST_ADDMAINMENUITEM,0,(LPARAM)&mi);
	return 0;
}

int __declspec(dllexport) Unload(void)
{
	return 0;
}