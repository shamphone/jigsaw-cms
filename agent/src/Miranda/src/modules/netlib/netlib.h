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

#define GetNetlibHandleType(h)  (h?*(int*)h:NLH_INVALID)
#define NLH_INVALID      0
#define NLH_USER         'USER'
#define NLH_CONNECTION   'CONN'
#define NLH_BOUNDPORT    'BIND'
#define NLH_PACKETRECVER 'PCKT'

struct NetlibUser {
	int handleType;
	NETLIBUSER user;
	NETLIBUSERSETTINGS settings;
	char * szStickyHeaders;
};

struct NetlibNestedCriticalSection {
	HANDLE hMutex;
	DWORD dwOwningThreadId;
	int lockCount;
};

struct NetlibHTTPProxyPacketQueue {
	struct NetlibHTTPProxyPacketQueue *next;
	PBYTE dataBuffer;
	int dataBufferLen;        
};

struct NetlibConnection {
	int handleType;
	SOCKET s;
	int usingHttpGateway;
	struct NetlibUser *nlu;
	SOCKADDR_IN sinProxy;
	NETLIBHTTPPROXYINFO nlhpi;
	PBYTE dataBuffer;
	int dataBufferLen;
	DWORD dwLastGetSentTime;
	CRITICAL_SECTION csHttpSequenceNums;
	HANDLE hOkToCloseEvent;
	LONG dontCloseNow;
	struct NetlibNestedCriticalSection ncsSend,ncsRecv;
	HANDLE hNtlmSecurity;
	struct NetlibHTTPProxyPacketQueue * pHttpProxyPacketQueue;
	int pollingTimeout;
};

struct NetlibBoundPort {
	int handleType;
	SOCKET s;
	WORD wPort;
	WORD wExPort;
	struct NetlibUser *nlu;
	NETLIBNEWCONNECTIONPROC_V2 pfnNewConnectionV2;
	HANDLE hThread;
	void *pExtra;
};

struct NetlibPacketRecver {
	int handleType;
	struct NetlibConnection *nlc;
	NETLIBPACKETRECVER packetRecver;
};

//netlib.c
void NetlibFreeUserSettingsStruct(NETLIBUSERSETTINGS *settings);
int NetlibCloseHandle(WPARAM wParam,LPARAM lParam);
void NetlibInitializeNestedCS(struct NetlibNestedCriticalSection *nlncs);
void NetlibDeleteNestedCS(struct NetlibNestedCriticalSection *nlncs);
#define NLNCS_SEND  0
#define NLNCS_RECV  1
int NetlibEnterNestedCS(struct NetlibConnection *nlc,int which);
void NetlibLeaveNestedCS(struct NetlibNestedCriticalSection *nlncs);
int NetlibBase64Encode(WPARAM wParam,LPARAM lParam);
int NetlibBase64Decode(WPARAM wParam,LPARAM lParam);
int NetlibHttpUrlEncode(WPARAM wParam,LPARAM lParam);

//netlibbind.c
int NetlibFreeBoundPort(struct NetlibBoundPort *nlbp);
int NetlibBindPort(WPARAM wParam,LPARAM lParam);
int StringToPortsMask(const char *szPorts,BYTE *mask);

//netlibhttp.c
int NetlibHttpSendRequest(WPARAM wParam,LPARAM lParam);
int NetlibHttpRecvHeaders(WPARAM wParam,LPARAM lParam);
int NetlibHttpFreeRequestStruct(WPARAM wParam,LPARAM lParam);
int NetlibHttpTransaction(WPARAM wParam,LPARAM lParam);
void NetlibHttpSetLastErrorUsingHttpResult(int result);
NETLIBHTTPREQUEST* NetlibHttpRecv(HANDLE hConnection, DWORD hflags, DWORD dflags);

//netlibhttpproxy.c
int NetlibInitHttpConnection(struct NetlibConnection *nlc,struct NetlibUser *nlu,NETLIBOPENCONNECTION *nloc);
int NetlibHttpGatewaySetInfo(WPARAM wParam,LPARAM lParam);
int NetlibHttpSetPollingTimeout(WPARAM wParam,LPARAM lParam);
int NetlibHttpGatewayRecv(struct NetlibConnection *nlc,char *buf,int len,int flags);
int NetlibHttpGatewayPost(struct NetlibConnection *nlc,const char *buf,int len,int flags);
int NetlibHttpSetSticky(WPARAM wParam, LPARAM lParam);

//netliblog.c
void NetlibLogShowOptions(void);
void NetlibDumpData(struct NetlibConnection *nlc,PBYTE buf,int len,int sent,int flags);
void NetlibLogInit(void);
void NetlibLogShutdown(void);

//netlibopenconn.c
DWORD DnsLookup(struct NetlibUser *nlu,const char *szHost);
int WaitUntilReadable(SOCKET s,DWORD dwTimeout);
int NetlibOpenConnection(WPARAM wParam,LPARAM lParam);

//netlibopts.c
int NetlibOptInitialise(WPARAM wParam,LPARAM lParam);
void NetlibSaveUserSettingsStruct(const char *szSettingsModule,NETLIBUSERSETTINGS *settings);

//netlibpktrecver.c
int NetlibPacketRecverCreate(WPARAM wParam,LPARAM lParam);
int NetlibPacketRecverGetMore(WPARAM wParam,LPARAM lParam);

//netlibsock.c
int NetlibSend(WPARAM wParam,LPARAM lParam);
int NetlibRecv(WPARAM wParam,LPARAM lParam);
int NetlibSelect(WPARAM wParam,LPARAM lParam);
int NetlibSelectEx(WPARAM wParam,LPARAM lParam);

//netlibupnp.c
BOOL NetlibUPnPAddPortMapping(WORD intport, char *proto, 
							  WORD *extport, DWORD *extip, BOOL search);
void NetlibUPnPDeletePortMapping(WORD extport, char* proto);
void NetlibUPnPCleanup(void* extra);
void NetlibUPnPInit(void);
void NetlibUPnPDestroy(void);

//netlibsecurity.c
void   NetlibSecurityInit(void);
void   NetlibSecurityDestroy(void);
void   NetlibDestroySecurityProvider(char* provider, HANDLE hSecurity);
HANDLE NetlibInitSecurityProvider(char* provider);
char*  NtlmCreateResponseFromChallenge(HANDLE hSecurity, char *szChallenge);


static __inline int NLSend(struct NetlibConnection *nlc,const char *buf,int len,int flags) {
	NETLIBBUFFER nlb={(char*)buf,len,flags};
	return NetlibSend((WPARAM)nlc,(LPARAM)&nlb);
}
static __inline int NLRecv(struct NetlibConnection *nlc,char *buf,int len,int flags) {
	NETLIBBUFFER nlb={buf,len,flags};
	return NetlibRecv((WPARAM)nlc,(LPARAM)&nlb);
}

