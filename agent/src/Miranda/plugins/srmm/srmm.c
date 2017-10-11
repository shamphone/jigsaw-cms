/*
SRMM

Copyright 2000-2005 Miranda ICQ/IM project, 
all portions of this codebase are copyrighted to the people 
listed in contributors.txt.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

#include "commonheaders.h"

int LoadSendRecvMessageModule(void);
int SplitmsgShutdown(void);

PLUGINLINK* pluginLink;
HINSTANCE   g_hInst;

struct MM_INTERFACE mmi;

PLUGININFO pluginInfo = {
	sizeof(PLUGININFO),
#ifdef _UNICODE
	"Send/Receive Messages (Unicode)",
#else
	"Send/Receive Messages",
#endif
	PLUGIN_MAKE_VERSION(3, 0, 0, 0),
	"Send and receive instant messages",
	"Miranda IM Development Team",
	"rainwater@miranda-im.org",
	"Copyright 2000-2006 Miranda IM project",
	"http://www.miranda-im.org",
	0,
	DEFMOD_SRMESSAGE            // replace internal version (if any)
};

BOOL WINAPI DllMain(HINSTANCE hinstDLL, DWORD fdwReason, LPVOID lpvReserved)
{
	g_hInst = hinstDLL;
	return TRUE;
}

__declspec(dllexport) PLUGININFO *MirandaPluginInfo(DWORD mirandaVersion)
{
	if (mirandaVersion < PLUGIN_MAKE_VERSION(0, 4, 0, 0))
		return NULL;
	return &pluginInfo;
}

int __declspec(dllexport) Load(PLUGINLINK * link)
{
	pluginLink = link;
	mir_getMMI( &mmi );
	return LoadSendRecvMessageModule();
}

int __declspec(dllexport) Unload(void)
{
	return SplitmsgShutdown();
}
