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
#include <m_plugins.h>

typedef struct {
	MIRANDAHOOK pfnHook;
	HINSTANCE hOwner;
	HWND hwnd;
	UINT message;
} THookSubscriber;

typedef struct {
	char name[MAXMODULELABELLENGTH];
	DWORD hookHash;
	int subscriberCount;
	THookSubscriber *subscriber;
	MIRANDAHOOK pfnHook;
} THookList;

typedef struct {
	char name[MAXMODULELABELLENGTH];
	DWORD nameHash;
	HINSTANCE hOwner;
	MIRANDASERVICE pfnService;
} TServiceList;

typedef struct {
	int hookId;
	HANDLE hDoneEvent;
	WPARAM wParam;
	LPARAM lParam;
	int result;
} THookToMainThreadItem;

typedef struct {
	HANDLE hDoneEvent;
	WPARAM wParam;
	LPARAM lParam;
	int result;
	const char *name;
} TServiceToMainThreadItem;

static THookList *hook;
static TServiceList *service;
static int hookCount,serviceCount;
static CRITICAL_SECTION csHooks,csServices;
static DWORD mainThreadId;
static HANDLE hMainThread;
static HANDLE hMissingService;

HINSTANCE GetInstByAddress( void* codePtr );

int LoadSystemModule(void);		// core: m_system.h services
int LoadNewPluginsModuleInfos(void); // core: preloading plugins
int LoadNewPluginsModule(void);	// core: N.O. plugins
int LoadNetlibModule(void);		// core: network
int LoadLangPackModule(void);	// core: translation
int LoadProtocolsModule(void);	// core: protocol manager
int LoadIgnoreModule(void);		// protocol filter: ignore

int LoadSendRecvUrlModule(void);	//send/recv
int LoadSendRecvEMailModule(void);	//send/recv
int LoadSendRecvAuthModule(void);	//send/recv
int LoadSendRecvFileModule(void);	//send/recv

int LoadContactListModule(void);// ui: clist
int LoadOptionsModule(void);	// ui: options dialog
int LoadFindAddModule(void);	// ui: search/add users
int LoadSkinModule(void);		// ui: skin
int LoadHelpModule(void);		// ui: help stuff
int LoadUserInfoModule(void);	// ui: user info
int LoadHistoryModule(void);	// ui: history viewer
int LoadAwayMsgModule(void);	// ui: setting away messages
int LoadVisibilityModule(void);	// ui: visibility control
int LoadCLUIModule(void);		// ui: CList UI
int LoadPluginOptionsModule(void);	// ui: plugin viewer
int LoadAddContactModule(void);	// ui: authcontrol contacts
int LoadIdleModule(void);		// rnd: report idle information
int LoadAutoAwayModule(void);	// ui: away
int LoadUserOnlineModule(void);	// ui: online alert
int LoadUtilsModule(void);		// ui: utils (has a few window classes, like HyperLink)
int LoadCLCModule(void);		// window class: CLC control
int LoadButtonModule(void);		// window class: button class
int LoadContactsModule(void);    // random: contact
int LoadUpdateNotifyModule(void); // random: update notification

static int LoadDefaultModules(void)
{
	int *disableDefaultModule = 0;

    //load order is very important for these
	if(LoadSystemModule()) return 1;	
	if(LoadLangPackModule()) return 1; // langpack will be a system module in the new order so this is moved 'ere
	if(LoadUtilsModule()) return 1;		//order not important for this, but no dependencies and no point in pluginising	
	if(LoadNewPluginsModuleInfos()) return 1; 
	if(LoadProtocolsModule()) return 1;
	if(LoadSkinModule()) return 1;
	if(LoadButtonModule()) return 1;
	if(LoadOptionsModule()) return 1;	
	if(LoadContactsModule()) return 1;
	if(LoadContactListModule()) return 1;
	if(LoadAddContactModule()) return 1;
	if(LoadNewPluginsModule()) return 1; // will call Load() on everything, clist will load first

	//this info will be available at LoadNewPluginsModule()
	disableDefaultModule=(int*)CallService(MS_PLUGINS_GETDISABLEDEFAULTARRAY,0,0);
	if(!disableDefaultModule[DEFMOD_PROTOCOLNETLIB]) if(LoadNetlibModule()) return 1;		
	//order becomes less important below here	
	if(!disableDefaultModule[DEFMOD_UIFINDADD]) if(LoadFindAddModule()) return 1;
	if(!disableDefaultModule[DEFMOD_UIUSERINFO]) if(LoadUserInfoModule()) return 1;	
	if(!disableDefaultModule[DEFMOD_SRURL]) if(LoadSendRecvUrlModule()) return 1;
	if(!disableDefaultModule[DEFMOD_SREMAIL]) if(LoadSendRecvEMailModule()) return 1;
	if(!disableDefaultModule[DEFMOD_SRAUTH]) if(LoadSendRecvAuthModule()) return 1;
	if(!disableDefaultModule[DEFMOD_SRFILE]) if(LoadSendRecvFileModule()) return 1;
	if(!disableDefaultModule[DEFMOD_UIHELP]) if(LoadHelpModule()) return 1;
	if(!disableDefaultModule[DEFMOD_UIHISTORY]) if(LoadHistoryModule()) return 1;
	if(!disableDefaultModule[DEFMOD_RNDIDLE]) if(LoadIdleModule()) return 1;
	if(!disableDefaultModule[DEFMOD_RNDAUTOAWAY]) if(LoadAutoAwayModule()) return 1;
	if(!disableDefaultModule[DEFMOD_RNDUSERONLINE]) if(LoadUserOnlineModule()) return 1;
	if(!disableDefaultModule[DEFMOD_SRAWAY]) if(LoadAwayMsgModule()) return 1;
	if(!disableDefaultModule[DEFMOD_RNDIGNORE]) if(LoadIgnoreModule()) return 1;
	if(!disableDefaultModule[DEFMOD_UIVISIBILITY]) if(LoadVisibilityModule()) return 1;	
	if(!disableDefaultModule[DEFMOD_UPDATENOTIFY]) if (LoadUpdateNotifyModule()) return 1;	
	return 0;
}

int InitialiseModularEngine(void)
{
	hookCount=serviceCount=0;
	hook=NULL;
	service=NULL;
	InitializeCriticalSection(&csHooks);
	InitializeCriticalSection(&csServices);

	mainThreadId=GetCurrentThreadId();
	DuplicateHandle(GetCurrentProcess(),GetCurrentThread(),GetCurrentProcess(),&hMainThread,THREAD_SET_CONTEXT,FALSE,0);

	hMissingService = CreateHookableEvent(ME_SYSTEM_MISSINGSERVICE);
	return LoadDefaultModules();
}

void DestroyingModularEngine(void)
{
	return;
}

void DestroyModularEngine(void)
{
	int i;
	for(i=0;i<hookCount;i++)
		if(hook[i].subscriberCount) mir_free(hook[i].subscriber);
	if(hookCount) mir_free(hook);
	if(serviceCount) mir_free(service);
	DeleteCriticalSection(&csHooks);
	DeleteCriticalSection(&csServices);
	CloseHandle(hMainThread);
}


#if __GNUC__
#define NOINLINEASM
#endif

DWORD NameHashFunction(const char *szStr)
{
#if defined _M_IX86 && !defined _NUMEGA_BC_FINALCHECK && !defined NOINLINEASM
	__asm {
		xor   edx,edx
		xor   eax,eax
		mov   esi,szStr
		mov   al,[esi]
		dec   esi
		xor   cl,cl
	lph_top:	 //only 4 of 9 instructions in here don't use AL, so optimal pipe use is impossible
		xor   edx,eax
		inc   esi
		and   cl,31
		movzx eax,byte ptr [esi]
		add   cl,5
		test  al,al
		rol   eax,cl		 //rol is u-pipe only, but pairable
		                 //rol doesn't touch z-flag
		jnz   lph_top  //5 clock tick loop. not bad.

		xor   eax,edx
	}
#else
	DWORD hash=0;
	int i;
	int shift=0;
	for(i=0;szStr[i];i++) {
		hash^=szStr[i]<<shift;
		if (shift>24) hash^=(szStr[i]>>(32-shift))&0x7F;
		shift=(shift+5)&0x1F;
	}
	return hash;
#endif
}

///////////////////////////////HOOKS

static int FindHookByName(const char *name)
{
	int i;

	for(i=0;i<hookCount;i++)
		if(!strcmp(hook[i].name,name)) return i;
	return -1;
}

static int FindHookByHashAndName(DWORD Hash, const char *name)
{
	int i;
	for (i=0; i<hookCount; i++)
		if (hook[i].hookHash==Hash) 
		{
			if (!strcmp(hook[i].name, name)) return i;
		}
	return -1;
}

HANDLE CreateHookableEvent(const char *name)
{
	DWORD Hash = NameHashFunction(name);
	HANDLE ret;

	EnterCriticalSection(&csHooks);
	//if(FindHookByName(name)!=-1) {LeaveCriticalSection(&csHooks); return NULL;}
	if (FindHookByHashAndName(Hash, name) != -1) 
	{
		LeaveCriticalSection(&csHooks);
		return NULL;
	}
	hook=(THookList*)mir_realloc(hook,sizeof(THookList)*(hookCount+1));
	strcpy(hook[hookCount].name,name);
	hook[hookCount].hookHash=Hash;
	hook[hookCount].subscriberCount=0;
	hook[hookCount].subscriber=NULL;
	hook[hookCount].pfnHook=NULL;
	hookCount++;
	ret=(HANDLE)hookCount;
	LeaveCriticalSection(&csHooks);
	return ret;
}

int DestroyHookableEvent(HANDLE hEvent)
{
	int hookId=(int)hEvent-1;
	EnterCriticalSection(&csHooks);
	if(hookId>=hookCount || hookId<0) {LeaveCriticalSection(&csHooks); return 1;}
	if(hook[hookId].name[0]==0) {LeaveCriticalSection(&csHooks); return 1;}
	hook[hookId].name[0]=0;
	if(hook[hookId].subscriberCount) {
		mir_free(hook[hookId].subscriber);
		hook[hookId].subscriber=NULL;
		hook[hookId].subscriberCount=0;
	}
	LeaveCriticalSection(&csHooks);
	return 0;
}

int SetHookDefaultForHookableEvent(HANDLE hEvent, MIRANDAHOOK pfnHook)
{
	int hookId = (int) hEvent - 1;
	if ( hookId < 0 || hookId >= hookCount ) return 1;
	EnterCriticalSection(&csHooks);
	hook[hookId].pfnHook = pfnHook;
	LeaveCriticalSection(&csHooks);
	return 0;
}

static int CallHookSubscribers(int hookId,WPARAM wParam,LPARAM lParam)
{
	int i,returnVal=0;

	EnterCriticalSection(&csHooks);
	if(hookId>=hookCount || hookId<0 || hook[hookId].name[0]==0) returnVal=-1;
	else {		
		//NOTE: We've got the critical section while all this lot are called. That's mostly safe, though.
		for(i=0;i<hook[hookId].subscriberCount;i++) {
			if(hook[hookId].subscriber[i].pfnHook!=NULL) {
				returnVal=hook[hookId].subscriber[i].pfnHook(wParam,lParam);
				if( returnVal ) break;
			}
			else if(hook[hookId].subscriber[i].hwnd!=NULL) {
				returnVal=SendMessage(hook[hookId].subscriber[i].hwnd,hook[hookId].subscriber[i].message,wParam,lParam);
				if( returnVal ) break;
			}//if
		}//for
		// check for no hooks and call the default hook if any
		if ( hook[hookId].subscriberCount == 0 && hook[hookId].pfnHook != 0 ) returnVal = hook[hookId].pfnHook(wParam,lParam);
	}
	LeaveCriticalSection(&csHooks);
	return returnVal;
}

static void CALLBACK HookToMainAPCFunc(DWORD dwParam)
{
	THookToMainThreadItem *item=(THookToMainThreadItem*)dwParam;

	item->result=CallHookSubscribers(item->hookId,item->wParam,item->lParam);
	SetEvent(item->hDoneEvent);
	return;
}

int NotifyEventHooks(HANDLE hEvent,WPARAM wParam,LPARAM lParam)
{
	extern HWND hAPCWindow;

	if(GetCurrentThreadId()!=mainThreadId) {
		THookToMainThreadItem item;

		item.hDoneEvent=CreateEvent(NULL,FALSE,FALSE,NULL);
		item.hookId=(int)hEvent-1;
		item.wParam=wParam;
		item.lParam=lParam;

		QueueUserAPC(HookToMainAPCFunc,hMainThread,(DWORD)&item);
		PostMessage(hAPCWindow,WM_NULL,0,0); // let it process APC even if we're in a common dialog
		WaitForSingleObject(item.hDoneEvent,INFINITE);
		CloseHandle(item.hDoneEvent);
		return item.result;
	}
	else
		return CallHookSubscribers((int)hEvent-1,wParam,lParam);
}

HANDLE HookEvent(const char *name,MIRANDAHOOK hookProc)
{
	int hookId;
	HANDLE ret;

	EnterCriticalSection(&csHooks);
	hookId=FindHookByName(name);
	if(hookId==-1) {
#ifdef _DEBUG
		OutputDebugStringA("Attempt to hook: \t");
		OutputDebugStringA(name);
		OutputDebugStringA("\n");
#endif
		LeaveCriticalSection(&csHooks);
		return NULL;
	}
	hook[hookId].subscriber=(THookSubscriber*)mir_realloc(hook[hookId].subscriber,sizeof(THookSubscriber)*(hook[hookId].subscriberCount+1));
	hook[hookId].subscriber[hook[hookId].subscriberCount].pfnHook = hookProc;
	hook[hookId].subscriber[hook[hookId].subscriberCount].hOwner  = GetInstByAddress(hookProc);
	hook[hookId].subscriber[hook[hookId].subscriberCount].hwnd    = NULL;
	hook[hookId].subscriberCount++;

	ret=(HANDLE)((hookId<<16)|hook[hookId].subscriberCount);
	LeaveCriticalSection(&csHooks);
	return ret;
}

HANDLE HookEventMessage(const char *name,HWND hwnd,UINT message)
{
	int hookId;
	HANDLE ret;

	EnterCriticalSection(&csHooks);
	hookId=FindHookByName(name);
	if(hookId==-1) {
#ifdef _DEBUG
		MessageBoxA(NULL,"Attempt to hook non-existant event",name,MB_OK);
#endif
		LeaveCriticalSection(&csHooks);
		return NULL;
	}
	hook[hookId].subscriber=(THookSubscriber*)mir_realloc(hook[hookId].subscriber,sizeof(THookSubscriber)*(hook[hookId].subscriberCount+1));
	hook[hookId].subscriber[hook[hookId].subscriberCount].pfnHook=NULL;
	hook[hookId].subscriber[hook[hookId].subscriberCount].hwnd=hwnd;
	hook[hookId].subscriber[hook[hookId].subscriberCount].message=message;
	hook[hookId].subscriberCount++;
	ret=(HANDLE)((hookId<<16)|hook[hookId].subscriberCount);
	LeaveCriticalSection(&csHooks);
	return ret;
}

int UnhookEvent(HANDLE hHook)
{
	int hookId=(int)hHook>>16;
	int subscriberId=((int)hHook&0xFFFF)-1;

	EnterCriticalSection(&csHooks);
	if(hookId>=hookCount || hookId<0) {LeaveCriticalSection(&csHooks); return 1;}
	if(hook[hookId].name[0]==0) {LeaveCriticalSection(&csHooks); return 1;}
	if(subscriberId>=hook[hookId].subscriberCount || subscriberId<0) {LeaveCriticalSection(&csHooks); return 1;}
	hook[hookId].subscriber[subscriberId].pfnHook=NULL;
	hook[hookId].subscriber[subscriberId].hwnd=NULL;
	hook[hookId].subscriber[subscriberId].hOwner=NULL;
	while(hook[hookId].subscriberCount && hook[hookId].subscriber[hook[hookId].subscriberCount-1].pfnHook==NULL && hook[hookId].subscriber[hook[hookId].subscriberCount-1].hwnd==NULL)
		hook[hookId].subscriberCount--;
	if (hook[hookId].subscriberCount==0) {
		if(hook[hookId].subscriber) mir_free(hook[hookId].subscriber);	
		hook[hookId].subscriber=NULL;
	}
	LeaveCriticalSection(&csHooks);
	return 0;
}

void KillModuleEventHooks( HINSTANCE hInst )
{
	int i, j;

	EnterCriticalSection(&csHooks);
	for ( i = hookCount-1; i >= 0; i-- ) {
		if ( hook[i].subscriberCount == 0 )
			continue;

		for ( j = hook[i].subscriberCount-1; j >= 0; j-- ) {
			if ( hook[i].subscriber[j].hOwner == hInst ) {
				char szModuleName[ MAX_PATH ];
				GetModuleFileNameA( hook[i].subscriber[j].hOwner, szModuleName, sizeof(szModuleName));
				Netlib_Logf( NULL, "A hook %08x for event '%s' was abnormally deleted because module '%s' didn't released it",
					hook[i].subscriber[j].pfnHook, hook[i].name, szModuleName );
				UnhookEvent(( HANDLE )(( i << 16 ) + j + 1 ));
				if ( hook[i].subscriberCount == 0 )
					break;
	}	}	}

	LeaveCriticalSection(&csHooks);
}

/////////////////////SERVICES

static __inline TServiceList *FindServiceByHash(DWORD hash)
{
	int first,last,mid;

	if(serviceCount==0) return NULL;
	first=0; last=serviceCount-1;
	if(hash>=service[last].nameHash) {
		if(hash==service[last].nameHash) return &service[last];
		return NULL;
	}
	for(;;) {
	  mid=(first+last)>>1;
	  if(hash>service[mid].nameHash) {
		  if(last-first<=1) break;
		  first=mid;
	  }
	  else if(hash<service[mid].nameHash) {
		  if(last-first<=1) break;
		  last=mid;
	  }
	  else return &service[mid];
	}
	return NULL;
}

static __inline TServiceList *FindServiceByName(const char *name)
{
	return FindServiceByHash(NameHashFunction(name));
}

/* assume critical section csServices is owned */
static int FindHashForService(DWORD hash, int * shift)
{	
	int i=0;
	unsigned int mid = 0;
	unsigned int min = 0;
	unsigned int max = serviceCount - 1;
	if (serviceCount==0) {
		if (shift) *shift=0;
		return 0;
	}
	if ( serviceCount > 0 && hash > service[serviceCount - 1].nameHash) {
		if (shift) *shift = 0;
		return serviceCount;
	}
	if ( serviceCount > 2 )
		{
			for ( ; (max - min) > 1 ; )
				{
					mid = ( min + max ) >> 1;
					if ( hash > service[mid].nameHash ) min = mid;
					else if ( hash < service[mid].nameHash ) max = mid;
					else return -1;
				} // for
			i = mid - 1;
		}
	/* its O(N) but we might reduce it to M=(log2 N), then looking for the hash is only O(2) */
	for(; i < serviceCount ; i++) {
		if ( hash <= service[i].nameHash ) break;
	}
	return i;
}

HANDLE CreateServiceFunction(const char *name,MIRANDASERVICE serviceProc)
{
	DWORD hash;
	int i;
	int shift = 1;
#ifdef _DEBUG
	if (name==NULL) { 
		MessageBoxA(0,"Someone tried to create a NULL'd service, see call stack for more info","",0);
		DebugBreak();
		return NULL;
	}
#else
	if (name==NULL) return NULL;
#endif
	hash=NameHashFunction(name);	
	EnterCriticalSection(&csServices);
	i=FindHashForService(hash,&shift);
	if (i==-1) {
		LeaveCriticalSection(&csServices);
		return NULL;
	}
	service=(TServiceList*)mir_realloc(service,sizeof(TServiceList)*(serviceCount+1));
	if ( serviceCount > 0 && shift) MoveMemory(service+i+1,service+i,sizeof(TServiceList)*(serviceCount-i));
	strncpy(service[i].name,name,sizeof(service[i].name));
	service[i].nameHash   = hash;
	service[i].pfnService = serviceProc;
	service[i].hOwner     = GetInstByAddress( serviceProc );
	serviceCount++;
	LeaveCriticalSection(&csServices);
	return (HANDLE)hash;
}

int DestroyServiceFunction(HANDLE hService)
{
	TServiceList *pService;
	int i;

	EnterCriticalSection(&csServices);
	pService=FindServiceByHash((DWORD)hService);
	if(pService==NULL) {LeaveCriticalSection(&csServices); return 1;}
	i=(int)(pService-service);
	MoveMemory(service+i,service+i+1,sizeof(TServiceList)*(--serviceCount-i));
	LeaveCriticalSection(&csServices);
	return 0;
}

int ServiceExists(const char *name)
{
	int ret;
	EnterCriticalSection(&csServices);
	ret=FindServiceByName(name)!=NULL;
	LeaveCriticalSection(&csServices);
	return ret;
}

int CallService(const char *name,WPARAM wParam,LPARAM lParam)
{
	TServiceList *pService;
	MIRANDASERVICE pfnService;

#ifdef _DEBUG
	if (name==NULL) {
		MessageBoxA(0,"Someone tried to CallService(NULL,..) see stack trace for details","",0);
		DebugBreak();
		return CALLSERVICE_NOTFOUND;
	}
#else
	if (name==NULL) return CALLSERVICE_NOTFOUND;
#endif

	EnterCriticalSection(&csServices);
	pService=FindServiceByName(name);
	if(pService==NULL) {
		LeaveCriticalSection(&csServices);
#ifdef _DEBUG
		//MessageBoxA(NULL,"Attempt to call non-existant service",name,MB_OK);
		OutputDebugStringA("Missing service called: \t");
		OutputDebugStringA(name);
		OutputDebugStringA("\n");
#endif
/*		{	MISSING_SERVICE_PARAMS params = { name, wParam, lParam };
			int result = NotifyEventHooks(hMissingService,0,(LPARAM)&params);
			if (result != 0)
				return params.lParam;
		} */
		return CALLSERVICE_NOTFOUND;
	}
	pfnService=pService->pfnService;
	LeaveCriticalSection(&csServices);
	return ((int (*)(WPARAM,LPARAM))pfnService)(wParam,lParam);
}

static void CALLBACK CallServiceToMainAPCFunc(DWORD dwParam)
{
	TServiceToMainThreadItem *item = (TServiceToMainThreadItem*) dwParam;
	item->result = CallService(item->name, item->wParam, item->lParam);
	SetEvent(item->hDoneEvent);
}

int CallServiceSync(const char *name, WPARAM wParam, LPARAM lParam)
{

	extern HWND hAPCWindow;

	if (name==NULL) return CALLSERVICE_NOTFOUND;
	// the service is looked up within the main thread, since the time it takes
	// for the APC queue to clear the service being called maybe removed.
	// even thou it may exists before the call, the critsec can't be locked between calls.
	if (GetCurrentThreadId() != mainThreadId) {
		TServiceToMainThreadItem item;
		item.wParam = wParam;
		item.lParam = lParam;
		item.name = name;
		item.hDoneEvent = CreateEvent(NULL, FALSE, FALSE, NULL);
		QueueUserAPC(CallServiceToMainAPCFunc, hMainThread, (DWORD) &item);
		PostMessage(hAPCWindow,WM_NULL,0,0); // let this get processed in its own time
		WaitForSingleObject(item.hDoneEvent, INFINITE);
		CloseHandle(item.hDoneEvent);
		return item.result;
	}

   return CallService(name, wParam, lParam);
}

int CallFunctionAsync( void (__stdcall *func)(void *), void *arg)
{
	extern HWND hAPCWindow;
	int r = 0;
	r=QueueUserAPC((void (__stdcall *)(DWORD))func,hMainThread,(DWORD)arg);
	PostMessage(hAPCWindow,WM_NULL,0,0);
	return r;
}

void KillModuleServices( HINSTANCE hInst )
{
	int i;

	EnterCriticalSection(&csServices);
	for ( i = serviceCount-1; i >= 0; i-- ) {
		if ( service[i].hOwner == hInst ) {
			char szModuleName[ MAX_PATH ];
			GetModuleFileNameA( service[i].hOwner, szModuleName, sizeof(szModuleName));
			Netlib_Logf( NULL, "A service function '%s' was abnormally deleted because module '%s' didn't released it",
				service[i].name, szModuleName );
			DestroyServiceFunction(( HANDLE )service[i].nameHash );
	}	}

	LeaveCriticalSection(&csServices);
}
