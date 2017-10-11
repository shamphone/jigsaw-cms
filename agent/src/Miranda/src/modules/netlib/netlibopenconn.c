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
#include "netlib.h"

extern CRITICAL_SECTION csNetlibUser;
extern DWORD g_LastConnectionTick; // protected by csNetlibUser
static int iUPnPCleanup = 0;

//returns in network byte order
DWORD DnsLookup(struct NetlibUser *nlu,const char *szHost)
{
	DWORD ip;
	HOSTENT *host;

	ip=inet_addr(szHost);
	if(ip!=INADDR_NONE) return ip;
	host=gethostbyname(szHost);
	if(host) return *(u_long *)host->h_addr_list[0];
	Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"gethostbyname",WSAGetLastError());
	return 0;
}

int WaitUntilReadable(SOCKET s,DWORD dwTimeout)
{
	fd_set readfd;
	TIMEVAL tv;

	tv.tv_sec=dwTimeout/1000;
	tv.tv_usec=(dwTimeout%1000)*1000;
	FD_ZERO(&readfd);
	FD_SET(s,&readfd);
	switch(select(0,&readfd,0,0,&tv)) {
		case 0:
			SetLastError(ERROR_TIMEOUT);
		case SOCKET_ERROR:
			return 0;
	}
	return 1;
}

static int WaitUntilWritable(SOCKET s,DWORD dwTimeout)
{
	fd_set writefd;
	TIMEVAL tv;

	tv.tv_sec=dwTimeout/1000;
	tv.tv_usec=(dwTimeout%1000)*1000;
	FD_ZERO(&writefd);
	FD_SET(s,&writefd);
	switch(select(0,0,&writefd,0,&tv)) {
		case 0:
			SetLastError(ERROR_TIMEOUT);
		case SOCKET_ERROR:
			return 0;
	}
	return 1;
}

static int NetlibInitSocks4Connection(struct NetlibConnection *nlc,struct NetlibUser *nlu,NETLIBOPENCONNECTION *nloc)
{	//http://www.socks.nec.com/protocol/socks4.protocol and http://www.socks.nec.com/protocol/socks4a.protocol
	PBYTE pInit;
	int nUserLen,nHostLen,len;
	BYTE reply[8];

	nUserLen=lstrlenA(nlu->settings.szProxyAuthUser);
	nHostLen=lstrlenA(nloc->szHost);
	pInit=(PBYTE)mir_alloc(10+nUserLen+nHostLen);
	pInit[0]=4;   //SOCKS4
	pInit[1]=1;   //connect
	*(PWORD)(pInit+2)=htons(nloc->wPort);
	if(nlu->settings.szProxyAuthUser==NULL) pInit[8]=0;
	else lstrcpyA(pInit+8,nlu->settings.szProxyAuthUser);
	if(nlu->settings.dnsThroughProxy) {
		if((*(PDWORD)(pInit+4)=inet_addr(nloc->szHost))==INADDR_NONE) {
			*(PDWORD)(pInit+4)=0x01000000;
			lstrcpyA(pInit+9+nUserLen,nloc->szHost);
			len=10+nUserLen+nHostLen;
		}
		else len=9+nUserLen;
	}
	else {
		*(PDWORD)(pInit+4)=DnsLookup(nlu,nloc->szHost);
		if(*(PDWORD)(pInit+4)==0) {
			mir_free(pInit);
			return 0;
		}
		len=9+nUserLen;
	}
	if(NLSend(nlc,pInit,len,MSG_DUMPPROXY)==SOCKET_ERROR) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLSend",GetLastError());
		mir_free(pInit);
		return 0;
	}
	mir_free(pInit);

	if(!WaitUntilReadable(nlc->s,30000)) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"WaitUntilReadable",GetLastError());
		return 0;
	}

	len=NLRecv(nlc,reply,SIZEOF(reply),MSG_DUMPPROXY);
	if(len < sizeof(reply) || reply[1]!=90) {
		if(len != SOCKET_ERROR) {
			if (len < SIZEOF(reply)) SetLastError(ERROR_BAD_FORMAT);
			else switch(reply[1]) {
				case 91: SetLastError(ERROR_ACCESS_DENIED); break;
				case 92: SetLastError(ERROR_CONNECTION_UNAVAIL); break;
				case 93: SetLastError(ERROR_INVALID_ACCESS); break;
				default: SetLastError(ERROR_INVALID_DATA); break;
			}
		}
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLRecv",GetLastError());
		return 0;
	}
	//connected
	return 1;
}

static int NetlibInitSocks5Connection(struct NetlibConnection *nlc,struct NetlibUser *nlu,NETLIBOPENCONNECTION *nloc)
{	//rfc1928
	int len;
	BYTE buf[256];

	buf[0]=5;  //yep, socks5
	buf[1]=1;  //one auth method
	buf[2]=nlu->settings.useProxyAuth?2:0;
	if(NLSend(nlc,buf,3,MSG_DUMPPROXY)==SOCKET_ERROR) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLSend",GetLastError());
		return 0;
	}

	if(!WaitUntilReadable(nlc->s,10000)) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"WaitUntilReadable",GetLastError());
		return 0;
	}

	len=NLRecv(nlc,buf,2,MSG_DUMPPROXY);	   //confirmation of auth method
	if(len<2 || (buf[1]!=0 && buf[1]!=2)) {
		if(len!=SOCKET_ERROR) {
			if(len<2) SetLastError(ERROR_BAD_FORMAT);
			else SetLastError(ERROR_INVALID_ID_AUTHORITY);
		}
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLRecv",GetLastError());
		return 0;
	}

	if(buf[1]==2) {		//rfc1929
		int nUserLen,nPassLen;
		PBYTE pAuthBuf;

		nUserLen=lstrlenA(nlu->settings.szProxyAuthUser);
		nPassLen=lstrlenA(nlu->settings.szProxyAuthPassword);
		pAuthBuf=(PBYTE)mir_alloc(3+nUserLen+nPassLen);
		pAuthBuf[0]=1;		//auth version
		pAuthBuf[1]=nUserLen;
		memcpy(pAuthBuf+2,nlu->settings.szProxyAuthUser,nUserLen);
		pAuthBuf[2+nUserLen]=nPassLen;
		memcpy(pAuthBuf+3+nUserLen,nlu->settings.szProxyAuthPassword,nPassLen);
		if(NLSend(nlc,pAuthBuf,3+nUserLen+nPassLen,MSG_DUMPPROXY)==SOCKET_ERROR) {
			Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLSend",GetLastError());
			mir_free(pAuthBuf);
			return 0;
		}
		mir_free(pAuthBuf);

		if(!WaitUntilReadable(nlc->s,10000)) {
			Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"WaitUntilReadable",GetLastError());
			return 0;
		}

		len=NLRecv(nlc,buf,SIZEOF(buf),MSG_DUMPPROXY);
		if(len<2 || buf[1]) {
			if(len!=SOCKET_ERROR) {
				if(len<2) SetLastError(ERROR_BAD_FORMAT);
				else SetLastError(ERROR_ACCESS_DENIED);
			}
			Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLRecv",GetLastError());
			return 0;
		}
	}

	{	PBYTE pInit;
		int nHostLen;
		DWORD hostIP;

		if(nlu->settings.dnsThroughProxy) {
			if((hostIP=inet_addr(nloc->szHost))==INADDR_NONE)
				nHostLen=lstrlenA(nloc->szHost)+1;
			else nHostLen=4;
		}
		else {
			if((hostIP=DnsLookup(nlu,nloc->szHost))==0)
				return 0;
			nHostLen=4;
		}
		pInit=(PBYTE)mir_alloc(6+nHostLen);
		pInit[0]=5;   //SOCKS5
		pInit[1]=1;   //connect
		pInit[2]=0;   //reserved
		if(hostIP==INADDR_NONE) {		 //DNS lookup through proxy
			pInit[3]=3;
			pInit[4]=nHostLen-1;
			memcpy(pInit+5,nloc->szHost,nHostLen-1);
		}
		else {
			pInit[3]=1;
			*(PDWORD)(pInit+4)=hostIP;
		}
		*(PWORD)(pInit+4+nHostLen)=htons(nloc->wPort);
		if(NLSend(nlc,pInit,6+nHostLen,MSG_DUMPPROXY)==SOCKET_ERROR) {
			Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLSend",GetLastError());
			mir_free(pInit);
			return 0;
		}
		mir_free(pInit);
	}

	if(!WaitUntilReadable(nlc->s,30000)) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"WaitUntilReadable",GetLastError());
		return 0;
	}

	len=NLRecv(nlc,buf,SIZEOF(buf),MSG_DUMPPROXY);
	if(len<7 || buf[0]!=5 || buf[1]) {
		if(len!=SOCKET_ERROR) {
			if(len<7 || buf[0]!=5) SetLastError(ERROR_BAD_FORMAT);
			else switch(buf[1]) {
				case 1: SetLastError(ERROR_GEN_FAILURE); break;
				case 2: SetLastError(ERROR_ACCESS_DENIED); break;
				case 3: SetLastError(WSAENETUNREACH); break;
				case 4: SetLastError(WSAEHOSTUNREACH); break;
				case 5: SetLastError(WSAECONNREFUSED); break;
				case 6: SetLastError(WSAETIMEDOUT); break;
				case 7: SetLastError(ERROR_CALL_NOT_IMPLEMENTED); break;
				case 8: SetLastError(ERROR_INVALID_ADDRESS); break;
				default: SetLastError(ERROR_INVALID_DATA); break;
			}
		}
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"NLRecv",GetLastError());
		return 0;
	}
	//connected
	return 1;
}

static int NetlibInitHttpsConnection(struct NetlibConnection *nlc,struct NetlibUser *nlu,NETLIBOPENCONNECTION *nloc)
{	//rfc2817
	NETLIBHTTPHEADER httpHeaders[3];
	NETLIBHTTPREQUEST nlhrSend={0},*nlhrReply;
	char szUrl[512];

	memset(httpHeaders,0,sizeof(httpHeaders));

	nlhrSend.cbSize=sizeof(nlhrSend);
	nlhrSend.requestType=REQUEST_CONNECT;
	nlhrSend.flags=NLHRF_DUMPPROXY|NLHRF_SMARTAUTHHEADER|NLHRF_HTTP11;
	if(nlu->settings.dnsThroughProxy) {
		mir_snprintf(szUrl,SIZEOF(szUrl),"%s:%u",nloc->szHost,nloc->wPort);
		if(inet_addr(nloc->szHost)==INADDR_NONE) {
			httpHeaders[0].szName="Host";
			httpHeaders[0].szValue=szUrl;
			nlhrSend.headersCount++;
		}
	}
	else {
		struct in_addr addr;
		DWORD ip=DnsLookup(nlu,nloc->szHost);
		if(ip==0) return 0;
		addr.S_un.S_addr=ip;
		mir_snprintf(szUrl,SIZEOF(szUrl),"%s:%u",inet_ntoa(addr),nloc->wPort);
	}
	nlhrSend.szUrl=szUrl;
	nlhrSend.headers=httpHeaders;
	nlhrSend.headersCount=0;
	if(NetlibHttpSendRequest((WPARAM)nlc,(LPARAM)&nlhrSend)==SOCKET_ERROR)
		return 0;
	nlhrReply=(NETLIBHTTPREQUEST*)NetlibHttpRecvHeaders((WPARAM)nlc,MSG_DUMPPROXY);
	if(nlhrReply==NULL) return 0;
	if(nlhrReply->resultCode<200 || nlhrReply->resultCode>=300) {
		NetlibHttpSetLastErrorUsingHttpResult(nlhrReply->resultCode);
		Netlib_Logf(nlu,"%s %d: %s request failed (%u %s)",__FILE__,__LINE__,nlu->settings.proxyType==PROXYTYPE_HTTP?"HTTP":"HTTPS",nlhrReply->resultCode,nlhrReply->szResultDescr);
		NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
		return 0;
	}
	NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
	//connected
	return 1;
}

static void FreePartiallyInitedConnection(struct NetlibConnection *nlc)
{
	DWORD dwOriginalLastError=GetLastError();

	if(nlc->s!=INVALID_SOCKET) closesocket(nlc->s);
	if(nlc->nlhpi.szHttpPostUrl) mir_free(nlc->nlhpi.szHttpPostUrl);
	if(nlc->nlhpi.szHttpGetUrl) mir_free(nlc->nlhpi.szHttpGetUrl);
	NetlibDestroySecurityProvider("NTLM", nlc->hNtlmSecurity);
	NetlibDeleteNestedCS(&nlc->ncsSend);
	NetlibDeleteNestedCS(&nlc->ncsRecv);
	CloseHandle(nlc->hOkToCloseEvent);
	DeleteCriticalSection(&nlc->csHttpSequenceNums);
	mir_free(nlc);
	SetLastError(dwOriginalLastError);
}

#define PortInMask(mask,p)  ((mask)[((p)&0xFFFF)>>3]&(1<<((p)&7)))

static int my_connect(SOCKET s, const struct sockaddr * name, int namelen, NETLIBOPENCONNECTION * nloc)
{
	int rc=0;
	unsigned int dwTimeout=( nloc->cbSize==sizeof(NETLIBOPENCONNECTION) && nloc->flags&NLOCF_V2 ) ? nloc->timeout : 0;
	u_long notblocking=1;	
	TIMEVAL tv;
	DWORD lasterr = 0;	
	int waitdiff;
	// if dwTimeout is zero then its an old style connection or new with a 0 timeout, select() will error quicker anyway
	if ( dwTimeout == 0 )
		dwTimeout += 60;
	// return the socket to non blocking
	if ( ioctlsocket(s, FIONBIO, &notblocking) != 0 ) {
		return SOCKET_ERROR;
	}
	// this is for XP SP2 where there is a default connection attempt limit of 10/second
	EnterCriticalSection(&csNetlibUser);
	waitdiff=GetTickCount() - g_LastConnectionTick;
	g_LastConnectionTick=GetTickCount();
	LeaveCriticalSection(&csNetlibUser);
	if ( waitdiff < 1000 ) {
		// last connection was less than 1 second ago, wait 1.2 seconds
		SleepEx(1200,TRUE);
	}
	// might of died in between the wait
	if ( Miranda_Terminated() )  {
		rc=SOCKET_ERROR;
		lasterr=ERROR_TIMEOUT;
		goto unblock;
	}
	// try a connect
	if ( connect(s, name, namelen) == 0 ) {
		goto unblock;
	}
	// didn't work, was it cos of nonblocking?
	if ( WSAGetLastError() != WSAEWOULDBLOCK ) {
		rc=SOCKET_ERROR;
		lasterr=WSAGetLastError();
		goto unblock;
	}
	// setup select()
	tv.tv_sec=1;
	tv.tv_usec=0;
	for (;;) {		
		fd_set r, w, e;
		FD_ZERO(&r); FD_ZERO(&w); FD_ZERO(&e);
		FD_SET(s, &r);
		FD_SET(s, &w);
		FD_SET(s, &e);		
		if ( (rc=select(0, &r, &w, &e, &tv)) == SOCKET_ERROR ) {
			break;
		}			
		if ( rc > 0 ) {			
			if ( FD_ISSET(s, &r) ) {
				// connection was closed
				rc=SOCKET_ERROR;
				lasterr=WSAECONNRESET;
			}
			if ( FD_ISSET(s, &w) ) {
				// connection was successful
				rc=0;
			}
			if ( FD_ISSET(s, &e) ) {
				// connection failed.
				int len=sizeof(lasterr);
				rc=SOCKET_ERROR;
				getsockopt(s,SOL_SOCKET,SO_ERROR,(char*)&lasterr,&len);
			}
			goto unblock;
		} else if ( Miranda_Terminated() ) {
			rc=SOCKET_ERROR;
			lasterr=ERROR_TIMEOUT;
			goto unblock;
		} else if ( nloc->cbSize==sizeof(NETLIBOPENCONNECTION) && nloc->flags&NLOCF_V2 && nloc->waitcallback != NULL 
			&& nloc->waitcallback(&dwTimeout) == 0) {
			rc=SOCKET_ERROR;
			lasterr=ERROR_TIMEOUT;
			goto unblock;
		}
		if ( --dwTimeout == 0 ) {
			rc=SOCKET_ERROR;
			lasterr=ERROR_TIMEOUT;
			goto unblock;
		}
	}
unblock:	
	notblocking=0;
	ioctlsocket(s, FIONBIO, &notblocking);
	SetLastError(lasterr);
	return rc;
}

int NetlibOpenConnection(WPARAM wParam,LPARAM lParam)
{
	NETLIBOPENCONNECTION *nloc=(NETLIBOPENCONNECTION*)lParam;
	struct NetlibUser *nlu=(struct NetlibUser*)wParam;
	struct NetlibConnection *nlc;
	SOCKADDR_IN sin;

    EnterCriticalSection(&csNetlibUser);
    if(iUPnPCleanup==0) {
        forkthread(NetlibUPnPCleanup, 0, NULL);
        iUPnPCleanup = 1;
    }
    LeaveCriticalSection(&csNetlibUser);
	if(GetNetlibHandleType(nlu)!=NLH_USER || !(nlu->user.flags&NUF_OUTGOING) || nloc==NULL 
		|| !(nloc->cbSize==NETLIBOPENCONNECTION_V1_SIZE||nloc->cbSize==sizeof(NETLIBOPENCONNECTION)) || nloc->szHost==NULL || nloc->wPort==0) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return (int)(HANDLE)NULL;
	}
	nlc=(struct NetlibConnection*)mir_calloc(sizeof(struct NetlibConnection));
	nlc->handleType=NLH_CONNECTION;
	nlc->nlu=nlu;
	nlc->s=socket(AF_INET,SOCK_STREAM,0);
	if(nlc->s==INVALID_SOCKET) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"socket",WSAGetLastError());
		mir_free(nlc);
		return (int)(HANDLE)NULL;
	}
	if (nlu->settings.specifyOutgoingPorts && nlu->settings.szOutgoingPorts) 
	{
		BYTE portsMask[0x2000];
		int startPort,portNum,i,j,portsCount;

		sin.sin_family=AF_INET;
		sin.sin_addr.s_addr=htonl(INADDR_ANY);
		sin.sin_port=0;
		
		portsCount=StringToPortsMask(nlu->settings.szOutgoingPorts,portsMask);
		if (portsCount) {
			startPort=rand() % portsCount;
			for (i=0;i<0x02000;i++) {
				if(portsMask[i]==0) continue;
				if(portsMask[i]==0xFF && startPort>=8) {startPort-=8; continue;}
				for(j=0;j<8;j++)
					if(portsMask[i]&(1<<j))
						if(startPort--==0) {
							portNum=(i<<3)+j;
							break;
						}
				if(startPort==-1) break;
			} //for
			if (i!=0x2000) {
				startPort=portNum;
				do {
					sin.sin_port=htons((WORD)portNum);					
					if(bind(nlc->s,(SOCKADDR*)&sin,sizeof(sin))==0) break;
					for(portNum++;!PortInMask(portsMask,portNum);portNum++)
					if(portNum==0xFFFF) portNum=0;
				} while (portNum!=startPort);				
			} //if
		} //if
	} 
	InitializeCriticalSection(&nlc->csHttpSequenceNums);
	nlc->hOkToCloseEvent=CreateEvent(NULL,TRUE,TRUE,NULL);
	nlc->dontCloseNow=0;
	NetlibInitializeNestedCS(&nlc->ncsSend);
	NetlibInitializeNestedCS(&nlc->ncsRecv);
	if(nlu->settings.useProxy && (nlu->settings.proxyType==PROXYTYPE_HTTP || nlu->settings.proxyType==PROXYTYPE_HTTPS) && nlu->settings.useProxyAuth && nlu->settings.useProxyAuthNtlm)
		nlc->hNtlmSecurity = NetlibInitSecurityProvider("NTLM");

	nlc->sinProxy.sin_family=AF_INET;
	if(nlu->settings.useProxy) {
		nlc->sinProxy.sin_port=htons((short)nlu->settings.wProxyPort);
		nlc->sinProxy.sin_addr.S_un.S_addr=DnsLookup(nlu,nlu->settings.szProxyServer);
	}
	else {
		nlc->sinProxy.sin_port=htons((short)nloc->wPort);
		nlc->sinProxy.sin_addr.S_un.S_addr=DnsLookup(nlu,nloc->szHost);
	}
	if(nlc->sinProxy.sin_addr.S_un.S_addr==0
	   || my_connect(nlc->s,(SOCKADDR *)&nlc->sinProxy,sizeof(nlc->sinProxy), nloc)==SOCKET_ERROR) {
		if(nlc->sinProxy.sin_addr.S_un.S_addr)
			Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"connect",WSAGetLastError());
		FreePartiallyInitedConnection(nlc);		
		return (int)(HANDLE)NULL;
	}

	if(nlu->settings.useProxy
	   && !(nloc->flags&NLOCF_HTTP
	        && (nlu->settings.proxyType==PROXYTYPE_HTTP || nlu->settings.proxyType==PROXYTYPE_HTTPS)))
	{
		if(!WaitUntilWritable(nlc->s,30000)) {
			FreePartiallyInitedConnection(nlc);
			return (int)(HANDLE)NULL;
		}

		switch(nlu->settings.proxyType) {
			case PROXYTYPE_SOCKS4:
				if(!NetlibInitSocks4Connection(nlc,nlu,nloc)) {
					FreePartiallyInitedConnection(nlc);
					return (int)(HANDLE)NULL;
				}
				break;

			case PROXYTYPE_SOCKS5:
				if(!NetlibInitSocks5Connection(nlc,nlu,nloc)) {
					FreePartiallyInitedConnection(nlc);
					return (int)(HANDLE)NULL;
				}
				break;

			case PROXYTYPE_HTTPS:
				if(!NetlibInitHttpsConnection(nlc,nlu,nloc)) {
					FreePartiallyInitedConnection(nlc);
					return (int)(HANDLE)NULL;
				}
				break;

			case PROXYTYPE_HTTP:
				if(!(nlu->user.flags&NUF_HTTPGATEWAY)) {
					//NLOCF_HTTP not specified and no HTTP gateway available: try HTTPS
					if(!NetlibInitHttpsConnection(nlc,nlu,nloc)) {
						//can't do HTTPS: try direct
						if(nlc->s!=INVALID_SOCKET) closesocket(nlc->s);

						nlc->s=socket(AF_INET,SOCK_STREAM,0);
						if(nlc->s==INVALID_SOCKET) {
							FreePartiallyInitedConnection(nlc);
							return (int)(HANDLE)NULL;
						}
						nlc->sinProxy.sin_family=AF_INET;
						nlc->sinProxy.sin_port=htons((short)nloc->wPort);
						nlc->sinProxy.sin_addr.S_un.S_addr=DnsLookup(nlu,nloc->szHost);
						if(nlc->sinProxy.sin_addr.S_un.S_addr==0
						   || my_connect(nlc->s,(SOCKADDR *)&nlc->sinProxy,sizeof(nlc->sinProxy), nloc)==SOCKET_ERROR) {
							if(nlc->sinProxy.sin_addr.S_un.S_addr)
								Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"connect",WSAGetLastError());
							FreePartiallyInitedConnection(nlc);
							return (int)(HANDLE)NULL;
						}
					}
				}
				else if(!NetlibInitHttpConnection(nlc,nlu,nloc)) {
					FreePartiallyInitedConnection(nlc);
					return (int)(HANDLE)NULL;
				}
				break;

			default:
				SetLastError(ERROR_INVALID_PARAMETER);
				FreePartiallyInitedConnection(nlc);
				return (int)(HANDLE)NULL;
		}
	}
	Netlib_Logf(nlu,"(%d) Connected to %s:%d",nlc->s,nloc->szHost,nloc->wPort);
	return (int)nlc;
}
