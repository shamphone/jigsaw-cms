/*

Miranda IM: the free IM client for Microsoft* Windows*

Copyright 2000-2007 Miranda ICQ/IM project,
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

int LoadProtoChains(void);

static HANDLE hAckEvent,hTypeEvent;
static int protocolModuleCount;
static PROTOCOLDESCRIPTOR *protocolModule;
static PROTOCOLDESCRIPTOR **pProtocolModules;

void InitContactDir(void);
void UninitContactDir(void);

static int Proto_BroadcastAck(WPARAM wParam,LPARAM lParam)
{
	return NotifyEventHooks(hAckEvent,wParam,lParam);
}

int Proto_IsProtocolLoaded(WPARAM wParam,LPARAM lParam)
{
	int i;
	for(i=0;i<protocolModuleCount;i++)
		if(!strcmp(protocolModule[i].szName,(char*)lParam)) return (int)&protocolModule[i];
	return (int)(PROTOCOLDESCRIPTOR*)NULL;
}

static int Proto_EnumProtocols(WPARAM wParam,LPARAM lParam)
{
	*(int*)wParam=protocolModuleCount;
	*(PROTOCOLDESCRIPTOR***)lParam=pProtocolModules;
	return 0;
}

static int Proto_RegisterModule(WPARAM wParam,LPARAM lParam)
{
	PROTOCOLDESCRIPTOR *pd=(PROTOCOLDESCRIPTOR*)lParam;
	int i;

	if(pd->cbSize!=sizeof(PROTOCOLDESCRIPTOR)) return 1;
	protocolModule=(PROTOCOLDESCRIPTOR*)mir_realloc(protocolModule,sizeof(PROTOCOLDESCRIPTOR)*(protocolModuleCount+1));
	protocolModule[protocolModuleCount]=*pd;
	protocolModule[protocolModuleCount++].szName=mir_strdup(pd->szName);
	pProtocolModules=(PROTOCOLDESCRIPTOR**)mir_realloc(pProtocolModules,sizeof(PROTOCOLDESCRIPTOR*)*(protocolModuleCount+1));
	for(i=0;i<protocolModuleCount;i++)
		pProtocolModules[i]=&protocolModule[i];
	return 0;
}

static int Proto_ValidTypingContact(HANDLE hContact, char *szProto) {
	DWORD protoCaps;

	if (!hContact || !szProto) return 0;
	protoCaps=CallProtoService(szProto,PS_GETCAPS,PFLAGNUM_4,0);
	if (!(protoCaps&PF4_SUPPORTTYPING)) return 0;
	return 1;
}

static int Proto_SelfIsTyping(WPARAM wParam,LPARAM lParam)
{
	int type = (int)lParam;
	char *szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,wParam,0);
	if (type!=PROTOTYPE_SELFTYPING_OFF && type!=PROTOTYPE_SELFTYPING_ON) return 0;

	if (!szProto) return 0;
	if (Proto_ValidTypingContact((HANDLE)wParam, szProto)) {
		CallProtoService(szProto, PSS_USERISTYPING, wParam, lParam);
	}
	return 0;
}

static int Proto_ContactIsTyping(WPARAM wParam,LPARAM lParam)
{
	int type = (int)lParam;
	char *szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,wParam,0);

	if (!szProto) return 0;
	if (type<PROTOTYPE_CONTACTTYPING_OFF) return 0;
	if (Proto_ValidTypingContact((HANDLE)wParam, szProto))
		NotifyEventHooks(hTypeEvent, wParam, lParam);
	return 0;
}

static int ProtoShutdown(WPARAM wParam,LPARAM lParam)
{
	if (hAckEvent) DestroyHookableEvent(hAckEvent);
	hAckEvent=NULL;
	if(protocolModule!=NULL) {
		int i;
		for(i=0;i<protocolModuleCount;i++)
			mir_free(protocolModule[i].szName);
		mir_free(protocolModule);
	}
	if(pProtocolModules!=NULL) mir_free(pProtocolModules);
	protocolModuleCount=0;
	UninitContactDir();
	return 0;
}

int LoadProtocolsModule(void)
{
	HookEvent(ME_SYSTEM_SHUTDOWN,ProtoShutdown);
	if(LoadProtoChains()) return 1;
	hAckEvent=CreateHookableEvent(ME_PROTO_ACK);
	hTypeEvent=CreateHookableEvent(ME_PROTO_CONTACTISTYPING);
	CreateServiceFunction(MS_PROTO_BROADCASTACK,Proto_BroadcastAck);
	CreateServiceFunction(MS_PROTO_ISPROTOCOLLOADED,Proto_IsProtocolLoaded);
	CreateServiceFunction(MS_PROTO_ENUMPROTOCOLS,Proto_EnumProtocols);
	CreateServiceFunction(MS_PROTO_REGISTERMODULE,Proto_RegisterModule);
	CreateServiceFunction(MS_PROTO_SELFISTYPING,Proto_SelfIsTyping);
	CreateServiceFunction(MS_PROTO_CONTACTISTYPING,Proto_ContactIsTyping);
	InitContactDir();
	return 0;
}
