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
#include "../modules/database/dblists.h"

#define MMI_SIZE_V1 (4*sizeof(void*))

int InitialiseModularEngine(void);
void DestroyingModularEngine(void);
void DestroyModularEngine(void);
int UnloadNewPluginsModule(void);

HINSTANCE GetInstByAddress( void* codePtr );

DWORD (WINAPI *MyMsgWaitForMultipleObjectsEx)(DWORD,CONST HANDLE*,DWORD,DWORD,DWORD);
static DWORD MsgWaitForMultipleObjectsExWorkaround(DWORD nCount, const HANDLE *pHandles,
	DWORD dwMsecs, DWORD dwWakeMask, DWORD dwFlags);

static HANDLE hOkToExitEvent,hModulesLoadedEvent;
HANDLE hShutdownEvent,hPreShutdownEvent;
static HANDLE hWaitObjects[MAXIMUM_WAIT_OBJECTS-1];
static char *pszWaitServices[MAXIMUM_WAIT_OBJECTS-1];
static int waitObjectCount=0;
HANDLE hStackMutex,hMirandaShutdown,hThreadQueueEmpty;

struct THREAD_WAIT_ENTRY {
	DWORD dwThreadId;	// valid if hThread isn't signalled
	HANDLE hThread;
	HINSTANCE hOwner;
};

struct THREAD_WAIT_ENTRY *WaitingThreads=NULL;
int WaitingThreadsCount=0;

struct FORK_ARG {
	HANDLE hEvent;
	pThreadFunc threadcode;
	pThreadFuncEx threadcodeex;
	void *arg;
};

/////////////////////////////////////////////////////////////////////////////////////////
// forkthread - starts a new thread

void __cdecl forkthread_r(void * arg)
{
	struct FORK_ARG * fa = (struct FORK_ARG *) arg;
	void (*callercode)(void*)=fa->threadcode;
	void * cookie=fa->arg;
	CallService(MS_SYSTEM_THREAD_PUSH,0,0);
	SetEvent(fa->hEvent);
	__try {
		callercode(cookie);
	} __finally {
		SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_TIME_CRITICAL);
		CallService(MS_SYSTEM_THREAD_POP,0,0);
	}
	return;
}

unsigned long forkthread (
	void (__cdecl *threadcode)(void*),
	unsigned long stacksize,
	void *arg
)
{
	unsigned long rc;
	struct FORK_ARG fa;
	fa.hEvent=CreateEvent(NULL,FALSE,FALSE,NULL);
	fa.threadcode=threadcode;
	fa.arg=arg;
	rc=_beginthread(forkthread_r,stacksize,&fa);
	if ((unsigned long)-1L != rc)
		WaitForSingleObject(fa.hEvent,INFINITE);

	CloseHandle(fa.hEvent);
	return rc;
}

static int ForkThreadService(WPARAM wParam, LPARAM lParam)
{
	return (int)forkthread(( pThreadFunc )wParam, 0, ( void* )lParam );
}

/////////////////////////////////////////////////////////////////////////////////////////
// forkthreadex - starts a new thread with the extended info and returns the thread id

unsigned __stdcall forkthreadex_r(void * arg)
{
	struct FORK_ARG *fa=(struct FORK_ARG *)arg;
	unsigned (__stdcall * threadcode) (void *)=fa->threadcodeex;
	void *cookie=fa->arg;
	unsigned long rc;

	CallService(MS_SYSTEM_THREAD_PUSH,0,0);
	SetEvent(fa->hEvent);
	__try {
		rc=threadcode(cookie);
	} __finally {
		SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_TIME_CRITICAL);
		CallService(MS_SYSTEM_THREAD_POP,0,0);
	}
	return rc;
}

unsigned long forkthreadex(
	void *sec,
	unsigned stacksize,
	unsigned (__stdcall *threadcode)(void*),
	void *arg,
	unsigned cf,
	unsigned *thraddr
)
{
	unsigned long rc;
	struct FORK_ARG fa;
	fa.threadcodeex=threadcode;
	fa.arg=arg;
	fa.hEvent=CreateEvent(NULL,FALSE,FALSE,NULL);
	rc=_beginthreadex(sec,stacksize,forkthreadex_r,(void *)&fa,0,thraddr);
	if (rc)
		WaitForSingleObject(fa.hEvent,INFINITE);

	CloseHandle(fa.hEvent);
	return rc;
}

static int ForkThreadServiceEx(WPARAM wParam, LPARAM lParam)
{
	FORK_THREADEX_PARAMS* params = (FORK_THREADEX_PARAMS*)lParam;
	if ( params == NULL )
		return 0;

	return forkthreadex( NULL, params->iStackSize, params->pFunc, params->arg, 0, params->threadID );
}

/////////////////////////////////////////////////////////////////////////////////////////
// APC and mutex functions

static void __stdcall DummyAPCFunc(DWORD dwArg)
{
	/* called in the context of thread that cleared it's APC queue */
	return;
}

static int MirandaWaitForMutex(HANDLE hEvent)
{
	for (;;) {
		// will get WAIT_IO_COMPLETE for QueueUserAPC() which isnt a result
		DWORD rc=MsgWaitForMultipleObjectsExWorkaround(1, &hEvent, INFINITE, QS_ALLINPUT, MWMO_ALERTABLE);
		if ( rc == WAIT_OBJECT_0 + 1 ) {
			MSG msg;
			while ( PeekMessage(&msg, NULL, 0, 0, PM_REMOVE) ) {
				if ( IsDialogMessage(msg.hwnd, &msg) ) continue;
				TranslateMessage(&msg);
				DispatchMessage(&msg);
			}
		} else if ( rc==WAIT_OBJECT_0 ) {
			// got object
			return 1;
		} else if ( rc==WAIT_ABANDONED_0 || rc == WAIT_FAILED ) return 0;
	}
}

VOID CALLBACK KillAllThreads(HWND hwnd, UINT uMsg, UINT_PTR idEvent, DWORD dwTime)
{
	if ( MirandaWaitForMutex( hStackMutex )) {
		int j;
		for ( j=0; j < WaitingThreadsCount; j++ ) {
			char szModuleName[ MAX_PATH ];
			GetModuleFileNameA( WaitingThreads[j].hOwner, szModuleName, sizeof(szModuleName));
			Netlib_Logf( NULL, "Thread %08x was abnormally terminated because module '%s' didn't released it",
				WaitingThreads[j].hThread, szModuleName );
			TerminateThread( WaitingThreads[j].hThread, 9999 );
		}

		if ( WaitingThreadsCount ) {
			mir_free(WaitingThreads);
			WaitingThreads=NULL;
			WaitingThreadsCount=0;
		}

		ReleaseMutex(hStackMutex);
		SetEvent(hThreadQueueEmpty);
}	}

static void UnwindThreadWait(void)
{
	// acquire the list and wake up any alertable threads
	if ( MirandaWaitForMutex(hStackMutex) ) {
		int j;
		for (j=0;j<WaitingThreadsCount;j++)
			QueueUserAPC(DummyAPCFunc,WaitingThreads[j].hThread, 0);
		ReleaseMutex(hStackMutex);
	}

	// give all unclosed threads 5 seconds to close
	SetTimer( NULL, 0, 5000, KillAllThreads );

	// wait til the thread list is empty
	MirandaWaitForMutex(hThreadQueueEmpty);
}


/////////////////////////////////////////////////////////////////////////////////////////

typedef LONG (WINAPI *pNtQIT)(HANDLE, LONG, PVOID, ULONG, PULONG);
#define ThreadQuerySetWin32StartAddress 9

void* GetCurrentThreadEntryPoint()
{
	LONG  ntStatus;
	HANDLE hDupHandle, hCurrentProcess;
	DWORD dwStartAddress;

	pNtQIT NtQueryInformationThread = (pNtQIT)GetProcAddress(GetModuleHandle(_T("ntdll.dll")), "NtQueryInformationThread" );
	if(NtQueryInformationThread == NULL) return 0;

	hCurrentProcess = GetCurrentProcess();
	if(!DuplicateHandle(hCurrentProcess, GetCurrentThread(), hCurrentProcess, &hDupHandle, THREAD_QUERY_INFORMATION, FALSE, 0)){
		SetLastError(ERROR_ACCESS_DENIED);
		return NULL;
	}
	ntStatus = NtQueryInformationThread(hDupHandle, ThreadQuerySetWin32StartAddress, &dwStartAddress, sizeof(DWORD), NULL);
	CloseHandle(hDupHandle);

	if(ntStatus != ERROR_SUCCESS) return 0;
	return ( void* )dwStartAddress;
}

int UnwindThreadPush(WPARAM wParam,LPARAM lParam)
{
	ResetEvent(hThreadQueueEmpty); // thread list is not empty
	if (WaitForSingleObject(hStackMutex,INFINITE)==WAIT_OBJECT_0)
	{
		HANDLE hThread=0;
		DuplicateHandle(GetCurrentProcess(),GetCurrentThread(),GetCurrentProcess(),&hThread,THREAD_SET_CONTEXT,FALSE,0);
		WaitingThreads=mir_realloc(WaitingThreads,sizeof(struct THREAD_WAIT_ENTRY)*(WaitingThreadsCount+1));
		WaitingThreads[WaitingThreadsCount].hThread=hThread;
		WaitingThreads[WaitingThreadsCount].dwThreadId=GetCurrentThreadId();
		WaitingThreads[WaitingThreadsCount].hOwner=GetInstByAddress( GetCurrentThreadEntryPoint() );
		WaitingThreadsCount++;
#ifdef _DEBUG
		{
			char szBuf[64];
			mir_snprintf(szBuf,SIZEOF(szBuf),"*** pushing thread (%x)\n",GetCurrentThreadId());
			OutputDebugStringA(szBuf);
		}
#endif
		ReleaseMutex(hStackMutex);
	} //if
	return 0;
}

int UnwindThreadPop(WPARAM wParam,LPARAM lParam)
{
	if (WaitForSingleObject(hStackMutex,INFINITE)==WAIT_OBJECT_0)
	{
		DWORD dwThreadId=GetCurrentThreadId();
		int j;
		for (j=0;j<WaitingThreadsCount;j++) {
			if (WaitingThreads[j].dwThreadId == dwThreadId) {
				SetThreadPriority(GetCurrentThread(), THREAD_PRIORITY_TIME_CRITICAL);
				CloseHandle(WaitingThreads[j].hThread);
				WaitingThreadsCount--;
				if (j<WaitingThreadsCount) memmove(&WaitingThreads[j],&WaitingThreads[j+1],(WaitingThreadsCount-j) * sizeof(struct THREAD_WAIT_ENTRY));
				if (!WaitingThreadsCount)
				{
					mir_free(WaitingThreads);
					WaitingThreads=NULL;
					WaitingThreadsCount=0;
					ReleaseMutex(hStackMutex);
					SetEvent(hThreadQueueEmpty); // thread list is empty now
					return 0;
				} else {
					WaitingThreads=mir_realloc(WaitingThreads,sizeof(struct THREAD_WAIT_ENTRY)*WaitingThreadsCount);
				} //if
				ReleaseMutex(hStackMutex);
				return 0;
			} //if
		} //for
		ReleaseMutex(hStackMutex);
	} //if
	return 1;
}

int MirandaIsTerminated(WPARAM wParam,LPARAM lParam)
{
	return WaitForSingleObject(hMirandaShutdown,0)==WAIT_OBJECT_0;
}

static void __cdecl compactHeapsThread(void *dummy)
{
	while (!Miranda_Terminated())
	{
		HANDLE hHeaps[256];
		DWORD hc;
		SleepEx((1000*60)*5,TRUE); // every 5 minutes
		hc=GetProcessHeaps(255,(PHANDLE)&hHeaps);
		if (hc != 0 && hc < 256) {
			DWORD j;
			for (j=0;j<hc;j++) HeapCompact(hHeaps[j],0);
		}
	} //while
}

static void InsertRegistryKey(void)
{
	if(DBGetContactSettingByte(NULL,"_Sys","CreateRegKey",1)) {
		HKEY hKey;
		DWORD dw;
		if(RegCreateKeyExA(HKEY_LOCAL_MACHINE,"SOFTWARE\\Miranda",0,NULL,0,KEY_CREATE_SUB_KEY|KEY_SET_VALUE,NULL,&hKey,&dw)==ERROR_SUCCESS) {
			char str[MAX_PATH],*str2;
			GetModuleFileNameA(NULL,str,SIZEOF(str));
			str2=strrchr(str,'\\');
			if(str2!=NULL) *str2=0;
			RegSetValueExA(hKey,"Install_Dir",0,REG_SZ,(PBYTE)str,lstrlenA(str)+1);
			RegCloseKey(hKey);
		}
	}
}

DWORD CALLBACK APCWndProc(HWND hwnd, UINT msg, WPARAM wParam, LPARAM lParam)
{
	if (msg==WM_NULL) SleepEx(0,TRUE);
	return DefWindowProc(hwnd,msg,wParam,lParam);
}

HWND hAPCWindow=NULL;
void (*SetIdleCallback) (void)=NULL;

static int SystemSetIdleCallback(WPARAM wParam, LPARAM lParam)
{
	if (lParam && SetIdleCallback==NULL) {
		SetIdleCallback=(void (*)(void))lParam;
		return 1;
	}
	return 0;
}

static DWORD dwEventTime=0;
void checkIdle(MSG * msg)
{
	switch(msg->message) {
		case WM_MOUSEACTIVATE:
		case WM_MOUSEMOVE:
		case WM_CHAR:
		{
			dwEventTime = GetTickCount();
		}
	}
}

static int SystemGetIdle(WPARAM wParam, LPARAM lParam)
{
	if ( lParam ) *(DWORD*)lParam = dwEventTime;
	return 0;
}

static DWORD MsgWaitForMultipleObjectsExWorkaround(DWORD nCount, const HANDLE *pHandles,
	DWORD dwMsecs, DWORD dwWakeMask, DWORD dwFlags)
{
	DWORD rc;
	if ( MyMsgWaitForMultipleObjectsEx != NULL )
		return MyMsgWaitForMultipleObjectsEx(nCount, pHandles, dwMsecs, dwWakeMask, dwFlags);
	rc=MsgWaitForMultipleObjects(nCount, pHandles, FALSE, 50, QS_ALLINPUT);
	if ( rc == WAIT_TIMEOUT ) rc=WaitForMultipleObjectsEx(nCount, pHandles, FALSE, 20, TRUE);
	return rc;
}

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow)
{
	DWORD myPid=0;
	int messageloop=1;
	HANDLE hMutex = CreateMutex(NULL, TRUE, _T("Run One Copy Of Miranda") );
	if( GetLastError() == ERROR_ALREADY_EXISTS )
		return 0;

#ifdef _DEBUG
	_CrtSetDbgFlag( _CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF);
#endif

	if (InitialiseModularEngine())
	{
		NotifyEventHooks(hShutdownEvent,0,0);
		UnloadNewPluginsModule();
		DestroyModularEngine();
		return 1;
	}
	InsertRegistryKey();
	NotifyEventHooks(hModulesLoadedEvent,0,0);
	MyMsgWaitForMultipleObjectsEx=(DWORD (WINAPI *)(DWORD,CONST HANDLE*,DWORD,DWORD,DWORD))GetProcAddress(GetModuleHandleA("user32"),"MsgWaitForMultipleObjectsEx");
	forkthread(compactHeapsThread,0,NULL);
	CreateServiceFunction(MS_SYSTEM_SETIDLECALLBACK,SystemSetIdleCallback);
	CreateServiceFunction(MS_SYSTEM_GETIDLE, SystemGetIdle);
	dwEventTime=GetTickCount();
	myPid=GetCurrentProcessId();
	for(;messageloop;) {
		MSG msg;
		DWORD rc;
		BOOL dying=FALSE;
		rc=MsgWaitForMultipleObjectsExWorkaround(waitObjectCount, hWaitObjects, INFINITE, QS_ALLINPUT, MWMO_ALERTABLE);
		if ( rc >= WAIT_OBJECT_0 && rc < WAIT_OBJECT_0 + waitObjectCount) {
			rc -= WAIT_OBJECT_0;
			CallService(pszWaitServices[rc], (WPARAM) hWaitObjects[rc], 0);
		}
		//
		while ( PeekMessage(&msg, NULL, 0, 0, PM_REMOVE) ) {
			if ( msg.message != WM_QUIT ) {
				HWND h=GetForegroundWindow();
				DWORD pid = 0;
				checkIdle(&msg);
				if ( h != NULL && GetWindowThreadProcessId(h,&pid) && pid==myPid
					&& GetClassLong(h, GCW_ATOM)==32770 ) {
					if ( IsDialogMessage(h, &msg) ) continue;
				}
				TranslateMessage(&msg);
				DispatchMessage(&msg);
				if ( SetIdleCallback != NULL )
					SetIdleCallback();

			} else if ( !dying ) {
				dying++;
				SetEvent(hMirandaShutdown);
				NotifyEventHooks(hPreShutdownEvent, 0, 0);
				DestroyingModularEngine();
				// this spins and processes the msg loop, objects and APC.
				UnwindThreadWait();
				NotifyEventHooks(hShutdownEvent, 0, 0);
				// if the hooks generated any messages, it'll get processed before the second WM_QUIT
				PostQuitMessage(0);
			} else if ( dying ) {
				messageloop=0;
			}
		} // while
	}
	UnloadNewPluginsModule();
	DestroyModularEngine();
	CloseHandle(hStackMutex);
	CloseHandle(hMirandaShutdown);
	CloseHandle(hThreadQueueEmpty);
	DestroyWindow(hAPCWindow);
	return 0;
}

static int SystemShutdownProc(WPARAM wParam,LPARAM lParam)
{
	return 0;
}

static int OkToExit(WPARAM wParam,LPARAM lParam)
{
	return NotifyEventHooks(hOkToExitEvent,0,0)==0;
}

static int GetMirandaVersion(WPARAM wParam,LPARAM lParam)
{
	char filename[MAX_PATH];
	DWORD unused;
	DWORD verInfoSize;
	UINT blockSize;
	PVOID pVerInfo;
	VS_FIXEDFILEINFO *vsffi;
	DWORD ver;

	GetModuleFileNameA(NULL,filename,SIZEOF(filename));
	verInfoSize=GetFileVersionInfoSizeA(filename,&unused);
	pVerInfo=mir_alloc(verInfoSize);
	GetFileVersionInfoA(filename,0,verInfoSize,pVerInfo);
	VerQueryValueA(pVerInfo,"\\",(PVOID*)&vsffi,&blockSize);
	ver=(((vsffi->dwProductVersionMS>>16)&0xFF)<<24)|
	    ((vsffi->dwProductVersionMS&0xFF)<<16)|
		(((vsffi->dwProductVersionLS>>16)&0xFF)<<8)|
		(vsffi->dwProductVersionLS&0xFF);
	mir_free(pVerInfo);
	return (int)ver;
}

static int GetMirandaVersionText(WPARAM wParam,LPARAM lParam)
{
	char filename[MAX_PATH],*productVersion;
	DWORD unused;
	DWORD verInfoSize;
	UINT blockSize;
	PVOID pVerInfo;

	GetModuleFileNameA(NULL,filename,SIZEOF(filename));
	verInfoSize=GetFileVersionInfoSizeA(filename,&unused);
	pVerInfo=mir_alloc(verInfoSize);
	GetFileVersionInfoA(filename,0,verInfoSize,pVerInfo);
	VerQueryValueA(pVerInfo,"\\StringFileInfo\\000004b0\\ProductVersion",(void*)&productVersion,&blockSize);
	#if defined( _UNICODE )
		mir_snprintf(( char* )lParam, wParam, "%s Unicode", productVersion );
	#else
		lstrcpynA((char*)lParam,productVersion,wParam);
	#endif
	mir_free(pVerInfo);
	return 0;
}

int WaitOnHandle(WPARAM wParam,LPARAM lParam)
{
	if(waitObjectCount>=MAXIMUM_WAIT_OBJECTS-1) return 1;
	hWaitObjects[waitObjectCount]=(HANDLE)wParam;
	pszWaitServices[waitObjectCount]=(char*)lParam;
	waitObjectCount++;
	return 0;
}

static int RemoveWait(WPARAM wParam,LPARAM lParam)
{
	int i;

	for(i=0;i<waitObjectCount;i++)
		if(hWaitObjects[i]==(HANDLE)wParam) break;
	if(i==waitObjectCount) return 1;
	waitObjectCount--;
	MoveMemory(&hWaitObjects[i],&hWaitObjects[i+1],sizeof(HANDLE)*(waitObjectCount-i));
	MoveMemory(&pszWaitServices[i],&pszWaitServices[i+1],sizeof(char*)*(waitObjectCount-i));
	return 0;
}

int GetMemoryManagerInterface(WPARAM wParam, LPARAM lParam)
{
	struct MM_INTERFACE *mmi = (struct MM_INTERFACE*) lParam;
	if ( mmi == NULL )
		return 1;

	mmi->mmi_malloc = mir_alloc;
	mmi->mmi_realloc = mir_realloc;
	mmi->mmi_free = mir_free;

	switch( mmi->cbSize ) {
	case sizeof(struct MM_INTERFACE):
		mmi->mmi_calloc = mir_calloc;
		mmi->mmi_strdup = mir_strdup;
		mmi->mmi_wstrdup = mir_wstrdup;
		// fall through

	case MMI_SIZE_V1:
		break;

	default:
		#if defined( _DEBUG )
			DebugBreak();
		#endif
		return 1;
	}

	return 0;
}

int GetListInterface(WPARAM wParam, LPARAM lParam)
{
	struct LIST_INTERFACE *li = (struct LIST_INTERFACE*) lParam;
	if ( li == NULL )
		return 1;

	if (li->cbSize == LIST_INTERFACE_V2_SIZE) {
		li->List_InsertPtr = List_InsertPtr;
		li->List_RemovePtr = List_RemovePtr;
	}

	if (li->cbSize == LIST_INTERFACE_V1_SIZE || li->cbSize == LIST_INTERFACE_V2_SIZE) {
		li->List_Create   = List_Create;
		li->List_Destroy  = List_Destroy;
		li->List_Find     = List_Find;
		li->List_GetIndex = List_GetIndex;
		li->List_Insert   = List_Insert;
		li->List_Remove   = List_Remove;
		li->List_IndexOf  = List_IndexOf;
		return 0;
	}
	return 1;
}

int GetUtfInterface(WPARAM wParam, LPARAM lParam)
{
	struct UTF8_INTERFACE *utfi = (struct UTF8_INTERFACE*) lParam;
	if ( utfi == NULL )
		return 1;
	if ( utfi->cbSize != sizeof( struct UTF8_INTERFACE ))
		return 1;

	utfi->utf8_decode   = Utf8Decode;
	utfi->utf8_decodecp = Utf8DecodeCP;
	utfi->utf8_encode   = Utf8Encode;
	utfi->utf8_encodecp = Utf8EncodeCP;
	utfi->utf8_encodeW  = Utf8EncodeUcs2;
	return 0;
}

int LoadSystemModule(void)
{
	InitCommonControls();

	hAPCWindow=CreateWindowEx(0,_T("STATIC"),NULL,0, 0,0,0,0, NULL,NULL,NULL,NULL); // lame
	SetWindowLong(hAPCWindow,GWL_WNDPROC,(LONG)APCWndProc);
	hStackMutex=CreateMutex(NULL,FALSE,NULL);
	hMirandaShutdown=CreateEvent(NULL,TRUE,FALSE,NULL);
	hThreadQueueEmpty=CreateEvent(NULL,TRUE,TRUE,NULL);

	hShutdownEvent=CreateHookableEvent(ME_SYSTEM_SHUTDOWN);
	hPreShutdownEvent=CreateHookableEvent(ME_SYSTEM_PRESHUTDOWN);
	hModulesLoadedEvent=CreateHookableEvent(ME_SYSTEM_MODULESLOADED);
	hOkToExitEvent=CreateHookableEvent(ME_SYSTEM_OKTOEXIT);

	HookEvent(ME_SYSTEM_SHUTDOWN,SystemShutdownProc);

	CreateServiceFunction(MS_SYSTEM_FORK_THREAD,ForkThreadService);
	CreateServiceFunction(MS_SYSTEM_FORK_THREAD_EX,ForkThreadServiceEx);
	CreateServiceFunction(MS_SYSTEM_THREAD_PUSH,UnwindThreadPush);
	CreateServiceFunction(MS_SYSTEM_THREAD_POP,UnwindThreadPop);
	CreateServiceFunction(MS_SYSTEM_TERMINATED,MirandaIsTerminated);
	CreateServiceFunction(MS_SYSTEM_OKTOEXIT,OkToExit);
	CreateServiceFunction(MS_SYSTEM_GETVERSION,GetMirandaVersion);
	CreateServiceFunction(MS_SYSTEM_GETVERSIONTEXT,GetMirandaVersionText);
	CreateServiceFunction(MS_SYSTEM_WAITONHANDLE,WaitOnHandle);
	CreateServiceFunction(MS_SYSTEM_REMOVEWAIT,RemoveWait);
	CreateServiceFunction(MS_SYSTEM_GET_LI,GetListInterface);
	CreateServiceFunction(MS_SYSTEM_GET_MMI,GetMemoryManagerInterface);
	CreateServiceFunction(MS_SYSTEM_GET_UTFI,GetUtfInterface);
	return 0;
}
