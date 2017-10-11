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

#define HTTPGETTIMEOUT   55000  //in ms. http GETs through most proxies will give up after a while so the request needs to be re-sent

static int HttpGatewaySendGet(struct NetlibConnection *nlc)
{
	NETLIBHTTPREQUEST nlhrSend={0};
	NETLIBHTTPHEADER httpHeaders[3];
	char szUrl[512];
	struct NetlibConnection nlcSend;

	nlc->s=socket(AF_INET,SOCK_STREAM,0);
	if(nlc->s==INVALID_SOCKET) {
		Netlib_Logf(nlc->nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"socket",WSAGetLastError());
		return 0;
	}

	if(connect(nlc->s,(SOCKADDR *)&nlc->sinProxy,sizeof(nlc->sinProxy))==SOCKET_ERROR) {
		Netlib_Logf(nlc->nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"connect",WSAGetLastError());
		return 0;
	}

	nlhrSend.cbSize=sizeof(nlhrSend);
	nlhrSend.nlc=nlc;

	/*
	 * Gena01 - one small change here, just in case there is a timeout or a problem and we died while
	 *          receiving
	 */
	nlhrSend.requestType=(nlc->nlhpi.szHttpGetUrl == NULL) ? REQUEST_POST : REQUEST_GET;
	nlhrSend.flags=NLHRF_GENERATEHOST|NLHRF_DUMPPROXY|NLHRF_SMARTAUTHHEADER;
	if (nlc->nlhpi.flags & NLHPIF_HTTP11) nlhrSend.flags |= NLHRF_HTTP11;

	/*
	 * Gena01 - fixing a possible crash, can't use GET Sequence if there is no GET URL
	 */
	if ((nlc->nlhpi.flags&NLHPIF_USEGETSEQUENCE) && (nlc->nlhpi.szHttpGetUrl != NULL)) {
		EnterCriticalSection(&nlc->csHttpSequenceNums);

		mir_snprintf(szUrl,SIZEOF(szUrl),"%s%u",nlc->nlhpi.szHttpGetUrl,nlc->nlhpi.firstGetSequence);
		nlc->nlhpi.firstGetSequence++;
		if(nlc->nlhpi.flags&NLHPIF_GETPOSTSAMESEQUENCE) nlc->nlhpi.firstPostSequence++;
		LeaveCriticalSection(&nlc->csHttpSequenceNums);
		nlhrSend.szUrl=szUrl;
	}
	else nlhrSend.szUrl=(nlc->nlhpi.szHttpGetUrl == NULL) ? nlc->nlhpi.szHttpPostUrl : nlc->nlhpi.szHttpGetUrl;
	nlhrSend.headers=httpHeaders;
	nlhrSend.headersCount= 3;
	httpHeaders[0].szName="User-Agent";
	httpHeaders[0].szValue=nlc->nlu->user.szHttpGatewayUserAgent;
	httpHeaders[1].szName="Cache-Control";
	httpHeaders[1].szValue="no-store, no-cache";
	httpHeaders[2].szName="Pragma";
	httpHeaders[2].szValue="no-cache";

	nlcSend=*nlc;
	nlcSend.usingHttpGateway=0;

	if (nlc->nlhpi.szHttpGetUrl != NULL) {

		Netlib_Logf(nlc->nlu,"%s %d: Sending data.[ICQ GET] ",__FILE__,__LINE__);
		if(NetlibHttpSendRequest((WPARAM)&nlcSend,(LPARAM)&nlhrSend)==SOCKET_ERROR) {
			nlc->usingHttpGateway=1;
			return 0;
		}
		nlc->dwLastGetSentTime=GetTickCount();
		return 1;
	}

	/*
	 * Gena01 - small addition here, if we doing a POST then insert our packet here
	 */
	if (nlc->pHttpProxyPacketQueue != NULL) {
			struct NetlibHTTPProxyPacketQueue *p = nlc->pHttpProxyPacketQueue;

        	nlc->pHttpProxyPacketQueue = nlc->pHttpProxyPacketQueue->next;

			nlhrSend.dataLength=p->dataBufferLen;
	        nlhrSend.pData=(char*)p->dataBuffer;

			mir_free(p);
	}

	if(NetlibHttpSendRequest((WPARAM)&nlcSend,(LPARAM)&nlhrSend)==SOCKET_ERROR) {
		struct NetlibHTTPProxyPacketQueue *p = nlc->pHttpProxyPacketQueue;
		
		mir_free(nlhrSend.pData);

		nlc->usingHttpGateway=1;

		/*
		 * Gena01 - we need to drop ALL pending packets. Connection died!
		 */
		while (p != NULL) {
			struct NetlibHTTPProxyPacketQueue *t = p;

			p = p->next;

			mir_free(t->dataBuffer);
			mir_free(t);
		}

		nlc->pHttpProxyPacketQueue = NULL; /* empty Queue */

		return 0;
	}
	mir_free(nlhrSend.pData);
	nlc->dwLastGetSentTime=GetTickCount();
	return 1;
}

/*
 * Gena01 - this is the old POST method, I renamed it and left it intact for ICQ support. it's called
 *          when we have both GET and POST URLs specified.
 */
int NetlibHttpGatewayOLDPost(struct NetlibConnection *nlc,const char *buf,int len,int flags)
{
	NETLIBHTTPREQUEST nlhrSend={0},*nlhrReply;
	NETLIBHTTPHEADER httpHeaders[4];
	char szUrl[512];
	struct NetlibConnection nlcSend={0};

	nlcSend.handleType=NLH_CONNECTION;
	nlcSend.nlu=nlc->nlu;
	nlcSend.hNtlmSecurity=nlc->hNtlmSecurity;
	nlcSend.s=socket(AF_INET,SOCK_STREAM,0);
	if(nlcSend.s==INVALID_SOCKET) {
		Netlib_Logf(nlc->nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"socket",WSAGetLastError());
		return SOCKET_ERROR;
	}
	nlcSend.hOkToCloseEvent=CreateEvent(NULL,TRUE,TRUE,NULL);
	nlcSend.dontCloseNow=0;
	NetlibInitializeNestedCS(&nlcSend.ncsRecv);
	NetlibInitializeNestedCS(&nlcSend.ncsSend);

	if(connect(nlcSend.s,(SOCKADDR *)&nlc->sinProxy,sizeof(nlc->sinProxy))==SOCKET_ERROR) {
		Netlib_Logf(nlc->nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"connect",WSAGetLastError());
		NetlibDeleteNestedCS(&nlcSend.ncsRecv);
		NetlibDeleteNestedCS(&nlcSend.ncsSend);
		CloseHandle(nlcSend.hOkToCloseEvent);
		closesocket(nlcSend.s);
		return SOCKET_ERROR;
	}

	nlhrSend.cbSize=sizeof(nlhrSend);
	nlhrSend.nlc=nlc;
	nlhrSend.requestType=REQUEST_POST;
	nlhrSend.flags=NLHRF_GENERATEHOST|NLHRF_DUMPPROXY|NLHRF_SMARTAUTHHEADER;
	if(flags&MSG_NODUMP) nlhrSend.flags|=NLHRF_NODUMP;
	if(nlc->nlhpi.flags&NLHPIF_USEPOSTSEQUENCE) {
		EnterCriticalSection(&nlc->csHttpSequenceNums);
		mir_snprintf(szUrl,SIZEOF(szUrl),"%s%u",nlc->nlhpi.szHttpPostUrl,nlc->nlhpi.firstPostSequence);
		nlc->nlhpi.firstPostSequence++;
		if(nlc->nlhpi.flags&NLHPIF_GETPOSTSAMESEQUENCE) nlc->nlhpi.firstGetSequence++;
		LeaveCriticalSection(&nlc->csHttpSequenceNums);
		nlhrSend.szUrl=szUrl;
	}
	else nlhrSend.szUrl=nlc->nlhpi.szHttpPostUrl;
	nlhrSend.headers=httpHeaders;
	nlhrSend.headersCount=3;
	httpHeaders[0].szName="User-Agent";
	httpHeaders[0].szValue=nlc->nlu->user.szHttpGatewayUserAgent;
	httpHeaders[1].szName="Cache-Control";
	httpHeaders[1].szValue="no-store, no-cache";
	httpHeaders[2].szName="Connection";
	httpHeaders[2].szValue="close";
	httpHeaders[3].szName="Pragma";
	httpHeaders[3].szValue="no-cache";
	nlhrSend.dataLength=len;
	nlhrSend.pData=(char*)buf;
	if(NetlibHttpSendRequest((WPARAM)&nlcSend,(LPARAM)&nlhrSend)==SOCKET_ERROR) {
		NetlibDeleteNestedCS(&nlcSend.ncsRecv);
		NetlibDeleteNestedCS(&nlcSend.ncsSend);
		CloseHandle(nlcSend.hOkToCloseEvent);
		closesocket(nlcSend.s);
		return SOCKET_ERROR;
	}

	nlhrReply=(NETLIBHTTPREQUEST*)NetlibHttpRecvHeaders((WPARAM)&nlcSend,flags&MSG_NODUMP?MSG_NODUMP:MSG_DUMPPROXY);
	if(nlhrReply==NULL
	   || nlhrReply->resultCode<200 || nlhrReply->resultCode>=300) {
		NetlibDeleteNestedCS(&nlcSend.ncsRecv);
		NetlibDeleteNestedCS(&nlcSend.ncsSend);
		CloseHandle(nlcSend.hOkToCloseEvent);
		closesocket(nlcSend.s);
		if(nlhrReply) {
			NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
			NetlibHttpSetLastErrorUsingHttpResult(nlhrReply->resultCode);
		}
		return SOCKET_ERROR;
	}
	NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);

	NetlibDeleteNestedCS(&nlcSend.ncsRecv);
	NetlibDeleteNestedCS(&nlcSend.ncsSend);
	CloseHandle(nlcSend.hOkToCloseEvent);
	closesocket(nlcSend.s);
	return len;
}

int NetlibHttpGatewayPost(struct NetlibConnection *nlc,const char *buf,int len,int flags)
{
		struct NetlibHTTPProxyPacketQueue *p;

        if (nlc->nlhpi.szHttpGetUrl != NULL)
               return NetlibHttpGatewayOLDPost(nlc, buf, len, flags);

        /*
         * Gena01 - many changes here, do compare against the other version.
         *
         * Change #1: simplify to use similar code to GET
         * Change #2: we need to allow to parse POST reply if szHttpGetUrl is NULL
         * Change #3: Keep connection open if we need to.
         *
         * Impact: NONE! Since currently miranda doesn't allow szHttpGetUrl to be NULL, it will not connect
         *         with the new plugins that use this code.
         */

         p = mir_alloc(sizeof(struct NetlibHTTPProxyPacketQueue));
         p->dataBuffer = mir_alloc(len);
         memcpy(p->dataBuffer, buf, len);
         p->dataBufferLen = len;
         p->next = NULL;

         /*
          * Now check to see where to insert this in our queue
          */
        if (nlc->pHttpProxyPacketQueue == NULL) {
                nlc->pHttpProxyPacketQueue = p;
        } else {
                struct NetlibHTTPProxyPacketQueue *t = nlc->pHttpProxyPacketQueue;

                while (t->next != NULL)
                        t = t->next;

                t->next = p;
        }


        /*
         * Gena01 - fake a Send!! tell 'em all is ok. We catch errors in Recv.
         */
        return len;
}

#define NETLIBHTTP_RETRYCOUNT   3
#define NETLIBHTTP_RETRYTIMEOUT 5000

int NetlibHttpGatewayRecv(struct NetlibConnection *nlc,char *buf,int len,int flags)
{
	DWORD dwTimeNow;
	int timedout;
	NETLIBHTTPREQUEST *nlhrReply;
	int retryCount;

	/*
	 * Gena01 - we need to send packet here, since we didn't do it before.
	 */
	if ((nlc->nlhpi.szHttpGetUrl == NULL) && (nlc->s == INVALID_SOCKET) && nlc->dataBuffer == NULL )  {

			if ( nlc->pollingTimeout == 0 )
				nlc->pollingTimeout = 30;

			/* We Need to sleep/wait for the data to send before we do receive */
			for ( retryCount = 0; retryCount < nlc->pollingTimeout; retryCount++ )
			{
				if ( nlc->pHttpProxyPacketQueue != NULL )
					break;

				if ( SleepEx( 1000, TRUE ))
					return SOCKET_ERROR;
			}

/*			if ( retryCount == nlc->pollingTimeout )
			{	SetLastError( ERROR_TIMEOUT );
				return SOCKET_ERROR;
			}
*/
			if ( nlc->pHttpProxyPacketQueue == 0 && nlc->nlu->user.pfnHttpGatewayWrapSend != NULL )
				nlc->nlu->user.pfnHttpGatewayWrapSend((HANDLE)nlc,"",0,MSG_NOHTTPGATEWAYWRAP,NetlibSend);

			if(!HttpGatewaySendGet(nlc)) {
				return SOCKET_ERROR;
			}
	}
	/********************/
	if(nlc->dataBuffer) {
		if(nlc->dataBufferLen<=len) {
			int contentLength=nlc->dataBufferLen;
			CopyMemory(buf,nlc->dataBuffer,nlc->dataBufferLen);
			if(!(flags&MSG_PEEK)) {
				mir_free(nlc->dataBuffer);
				nlc->dataBuffer=NULL;
				nlc->dataBufferLen=0;
			}
			return contentLength;
		}
		CopyMemory(buf,nlc->dataBuffer,len);
		if(!(flags&MSG_PEEK)) {
			nlc->dataBufferLen-=len;
			MoveMemory(nlc->dataBuffer,nlc->dataBuffer+len,nlc->dataBufferLen);
			nlc->dataBuffer=(PBYTE)mir_realloc(nlc->dataBuffer,nlc->dataBufferLen);
		}
		return len;
	}
	for( retryCount = 0;;) {
		timedout=0;
		dwTimeNow=GetTickCount();
		if(dwTimeNow>=nlc->dwLastGetSentTime+HTTPGETTIMEOUT) timedout=1;
		else if(!WaitUntilReadable(nlc->s,nlc->dwLastGetSentTime+HTTPGETTIMEOUT-dwTimeNow)) {
			if(GetLastError()==ERROR_TIMEOUT) timedout=1;
			else return SOCKET_ERROR;
		}
		if(timedout) {
			closesocket(nlc->s);
			nlc->s=INVALID_SOCKET;
			if(!HttpGatewaySendGet(nlc)) return SOCKET_ERROR;
			retryCount = 0;
			continue;
		}
		
		nlhrReply=(NETLIBHTTPREQUEST*)NetlibHttpRecv(nlc, flags|MSG_RAW|MSG_DUMPPROXY, MSG_RAW|MSG_DUMPPROXY);
		if (nlhrReply==NULL) return SOCKET_ERROR;
        // ignore 1xx result codes
        if (nlhrReply->resultCode < 200)
		{
			NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
			continue;
		}
		// 0.3.1+
		// Attempt to retry NETLIBHTTP_RETRYCOUNT times if the result code is >300
        if (nlhrReply->resultCode >= 300)
        {
			if (retryCount < NETLIBHTTP_RETRYCOUNT) {
				NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
				Netlib_Logf(nlc->nlu, "Error received from proxy, retrying");
				retryCount++;
				closesocket(nlc->s);
				nlc->s = INVALID_SOCKET;
				Sleep(NETLIBHTTP_RETRYTIMEOUT); // wait 5 seconds
				// retry the connection
				Netlib_Logf(nlc->nlu,"%s %d: ResultCode?? Doing GET.",__FILE__,__LINE__);
				if(HttpGatewaySendGet(nlc))
					continue;
				SetLastError(ERROR_GEN_FAILURE);
				return SOCKET_ERROR;
		}	}
		retryCount = 0;

		if(nlhrReply->dataLength==0 && nlc->nlu->user.szHttpGatewayHello != NULL)
		{
			NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
			continue;
		}

		closesocket(nlc->s);
		nlc->s=INVALID_SOCKET;

		/*
		 * Gena01 - ok, ICQ does it here so that when we enter this function again we have reply
		 *			pending. This is quite clever, since GET always gets replies from ICQ server
		 *
		 */
		if (nlc->nlhpi.szHttpGetUrl != NULL)  {
			Netlib_Logf(nlc->nlu,"%s %d: Doing GET, Again????",__FILE__,__LINE__);

			if(!HttpGatewaySendGet(nlc)) {
				NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
				return SOCKET_ERROR;
			}
		}

		if(nlc->nlu->user.pfnHttpGatewayUnwrapRecv && !(flags&MSG_NOHTTPGATEWAYWRAP)) {
			PBYTE newBuffer;
			newBuffer=nlc->nlu->user.pfnHttpGatewayUnwrapRecv(nlhrReply,nlhrReply->pData,nlhrReply->dataLength,&nlhrReply->dataLength,mir_realloc);
			if(newBuffer==NULL) {
				NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
				return SOCKET_ERROR;
			}
			nlhrReply->pData=newBuffer;
		}
		if(nlhrReply->dataLength>0) break;
		if((nlhrReply->dataLength==0)&&(nlc->nlhpi.szHttpGetUrl==NULL))
			break;
		NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
	}
	if(nlhrReply->dataLength<=len) {
		int contentLength = nlhrReply->dataLength;

		CopyMemory(buf,nlhrReply->pData,nlhrReply->dataLength);
		if(flags&MSG_PEEK) {
			nlc->dataBuffer=nlhrReply->pData;
			nlc->dataBufferLen=nlhrReply->dataLength;
			nlhrReply->pData = NULL;
			nlhrReply->dataLength = 0;
		}
		NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
		return contentLength;
	}
	CopyMemory(buf,nlhrReply->pData,len);
	if(!(flags&MSG_PEEK)) {
		MoveMemory(nlhrReply->pData,nlhrReply->pData+len,nlhrReply->dataLength-len);
		nlhrReply->pData=(PBYTE)mir_realloc(nlhrReply->pData,nlhrReply->dataLength-len);
		nlc->dataBufferLen=nlhrReply->dataLength-len;
	}
	else nlc->dataBufferLen=nlhrReply->dataLength;
	nlc->dataBuffer=nlhrReply->pData;

	nlhrReply->pData = NULL;
	nlhrReply->dataLength = 0;
	NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);

	Netlib_Logf(nlc->nlu,"%s %d: NetlibHTTPGatewayRecv EXIT!",__FILE__,__LINE__);
	return len;
}

int NetlibInitHttpConnection(struct NetlibConnection *nlc,struct NetlibUser *nlu,NETLIBOPENCONNECTION *nloc)
{
	NETLIBHTTPREQUEST nlhrSend={0},*nlhrReply=NULL;
	NETLIBHTTPHEADER httpHeaders[3];

	nlc->nlhpi.firstGetSequence=nlc->nlhpi.firstPostSequence=1;

        /*
         * Gena01 - ok we set nlhrReply to be null, also if the szHttpGatewayHello is NULL, then
         *          we don't send any requests/replies. We do have a socket open though. Could we
         *          re-use it maybe?
         */
        if (nlu->user.szHttpGatewayHello != NULL) {
	nlhrSend.cbSize=sizeof(nlhrSend);
	nlhrSend.nlc=nlc;
	nlhrSend.requestType=REQUEST_GET;
	nlhrSend.flags=NLHRF_GENERATEHOST|NLHRF_DUMPPROXY|NLHRF_SMARTAUTHHEADER;
	if (nlc->nlhpi.flags & NLHPIF_HTTP11) nlhrSend.flags |= NLHRF_HTTP11;

	nlhrSend.szUrl=nlu->user.szHttpGatewayHello;
	nlhrSend.headers=httpHeaders;
    nlhrSend.headersCount=3;
	httpHeaders[0].szName="User-Agent";
	httpHeaders[0].szValue=nlu->user.szHttpGatewayUserAgent;
    httpHeaders[1].szName="Cache-Control";
    httpHeaders[1].szValue="no-store, no-cache";
    httpHeaders[2].szName="Pragma";
    httpHeaders[2].szValue="no-cache";
	if(NetlibHttpSendRequest((WPARAM)nlc,(LPARAM)&nlhrSend)==SOCKET_ERROR)
		return 0;

	nlhrReply=(NETLIBHTTPREQUEST*)NetlibHttpRecvHeaders((WPARAM)nlc,MSG_DUMPPROXY);
	if(nlhrReply==NULL) return 0;

	if(nlhrReply->resultCode<200 || nlhrReply->resultCode>=300) {
		NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
		NetlibHttpSetLastErrorUsingHttpResult(nlhrReply->resultCode);
		return 0;
	}
        }
	if(!nlu->user.pfnHttpGatewayInit(nlc,nloc,nlhrReply)) {
		NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);
		return 0;
	}
	NetlibHttpFreeRequestStruct(0,(LPARAM)nlhrReply);

	/*
	 * Gena01 - Ok, we should be able to use just POST. Needed for Yahoo, NO GET requests
	 */
	if(nlc->nlhpi.szHttpPostUrl==NULL) {
		SetLastError(ERROR_BAD_FORMAT);
		return 0;
	}
	closesocket(nlc->s);
	nlc->s=INVALID_SOCKET;

	nlc->usingHttpGateway=1;

	/* don't send anything if only using POST? */
	if(nlc->nlhpi.szHttpGetUrl!= NULL)
	if(!HttpGatewaySendGet(nlc))
		return 0;

	//now properly connected
	if(nlu->user.pfnHttpGatewayBegin)
		if(!nlu->user.pfnHttpGatewayBegin(nlc,nloc))
			return 0;
	return 1;
}

int NetlibHttpGatewaySetInfo(WPARAM wParam,LPARAM lParam)
{
	NETLIBHTTPPROXYINFO *nlhpi=(NETLIBHTTPPROXYINFO*)lParam;
	struct NetlibConnection *nlc=(struct NetlibConnection*)wParam;

	if(GetNetlibHandleType(nlc)!=NLH_CONNECTION || nlhpi==NULL || nlhpi->cbSize!=sizeof(NETLIBHTTPPROXYINFO) || nlhpi->szHttpPostUrl==NULL) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return 0;
	}
	if(nlc->nlhpi.szHttpGetUrl) mir_free(nlc->nlhpi.szHttpGetUrl);
	if(nlc->nlhpi.szHttpPostUrl) mir_free(nlc->nlhpi.szHttpPostUrl);
	nlc->nlhpi=*nlhpi;

	if (nlc->nlhpi.szHttpGetUrl)
	nlc->nlhpi.szHttpGetUrl=mir_strdup(nlc->nlhpi.szHttpGetUrl);

	nlc->nlhpi.szHttpPostUrl=mir_strdup(nlc->nlhpi.szHttpPostUrl);
	return 1;
}

int NetlibHttpSetSticky(WPARAM wParam, LPARAM lParam)
{
	struct NetlibUser * nu = (struct NetlibUser*)wParam;
	if (GetNetlibHandleType(nu)!=NLH_USER) return ERROR_INVALID_PARAMETER;
	if (nu->szStickyHeaders) { mir_free(nu->szStickyHeaders); nu->szStickyHeaders=NULL; }
	if (lParam) {
		nu->szStickyHeaders=mir_strdup((char*)lParam); // pointer is ours
	}
	return 0;
}

int NetlibHttpSetPollingTimeout(WPARAM wParam, LPARAM lParam)
{
	int oldTimeout;
	struct NetlibConnection *nlc=(struct NetlibConnection*)wParam;
	if (GetNetlibHandleType(nlc)!=NLH_CONNECTION) return -1;
	oldTimeout = nlc->pollingTimeout;
	nlc->pollingTimeout = lParam;
	return oldTimeout;
}
