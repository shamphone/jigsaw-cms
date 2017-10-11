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

struct NetlibUser **netlibUser=NULL;
int netlibUserCount=0;
CRITICAL_SECTION csNetlibUser;
HANDLE hConnectionHeaderMutex;
DWORD g_LastConnectionTick; // protected by csNetlibUser

void NetlibFreeUserSettingsStruct(NETLIBUSERSETTINGS *settings)
{
	if(settings->szIncomingPorts) mir_free(settings->szIncomingPorts);
	if(settings->szOutgoingPorts) mir_free(settings->szOutgoingPorts);
	if(settings->szProxyAuthPassword) mir_free(settings->szProxyAuthPassword);
	if(settings->szProxyAuthUser) mir_free(settings->szProxyAuthUser);
	if(settings->szProxyServer) mir_free(settings->szProxyServer);
}

void NetlibInitializeNestedCS(struct NetlibNestedCriticalSection *nlncs)
{
	nlncs->dwOwningThreadId= 0;
	nlncs->lockCount=0;
	nlncs->hMutex=CreateMutex(NULL,FALSE,NULL);
}

void NetlibDeleteNestedCS(struct NetlibNestedCriticalSection *nlncs)
{
	CloseHandle(nlncs->hMutex);
}

int NetlibEnterNestedCS(struct NetlibConnection *nlc,int which)
{
	struct NetlibNestedCriticalSection *nlncs;
	DWORD dwCurrentThreadId=GetCurrentThreadId();

	WaitForSingleObject(hConnectionHeaderMutex,INFINITE);
	if(nlc==NULL || nlc->handleType!=NLH_CONNECTION) {
		ReleaseMutex(hConnectionHeaderMutex);
		SetLastError(ERROR_INVALID_PARAMETER);
		return 0;
	}
	nlncs=which==NLNCS_SEND?&nlc->ncsSend:&nlc->ncsRecv;
	if(nlncs->lockCount && (nlc->ncsRecv.dwOwningThreadId==dwCurrentThreadId || nlc->ncsSend.dwOwningThreadId==dwCurrentThreadId)) {
		nlncs->lockCount++;
		ReleaseMutex(hConnectionHeaderMutex);
		return 1;
	}
	InterlockedIncrement(&nlc->dontCloseNow);
	ResetEvent(nlc->hOkToCloseEvent);
	ReleaseMutex(hConnectionHeaderMutex);
	WaitForSingleObject(nlncs->hMutex,INFINITE);
	nlncs->dwOwningThreadId=dwCurrentThreadId;
	nlncs->lockCount=1;
	if(InterlockedDecrement(&nlc->dontCloseNow)==0)
		SetEvent(nlc->hOkToCloseEvent);
	return 1;
}

void NetlibLeaveNestedCS(struct NetlibNestedCriticalSection *nlncs)
{
	if(--nlncs->lockCount==0) {
		nlncs->dwOwningThreadId=0;
		ReleaseMutex(nlncs->hMutex);
	}
}

static int GetNetlibUserSettingInt(const char *szUserModule,const char *szSetting,int defValue)
{
	DBVARIANT dbv;
	if(DBGetContactSetting(NULL,szUserModule,szSetting,&dbv)
	   && DBGetContactSetting(NULL,"Netlib",szSetting,&dbv))
		return defValue;
	if(dbv.type==DBVT_BYTE) return dbv.bVal;
	if(dbv.type==DBVT_WORD) return dbv.wVal;
	return dbv.dVal;
}

static char *GetNetlibUserSettingString(const char *szUserModule,const char *szSetting,int decode)
{
	DBVARIANT dbv;
	if(DBGetContactSetting(NULL,szUserModule,szSetting,&dbv)
	   && DBGetContactSetting(NULL,"Netlib",szSetting,&dbv)) {
		return NULL;
	}
	else {
		char *szRet;
		if(decode) CallService(MS_DB_CRYPT_DECODESTRING, strlen(dbv.pszVal) + 1, (LPARAM)dbv.pszVal);
		szRet=mir_strdup(dbv.pszVal);
		DBFreeVariant(&dbv);
		if(szRet==NULL) SetLastError(ERROR_OUTOFMEMORY);
		return szRet;
	}
}

static int NetlibRegisterUser(WPARAM wParam,LPARAM lParam)
{
	NETLIBUSER *nlu=(NETLIBUSER*)lParam;
	struct NetlibUser *thisUser;
	int i;

	if(nlu==NULL || nlu->cbSize!=sizeof(NETLIBUSER) || nlu->szSettingsModule==NULL
	   || (!(nlu->flags&NUF_NOOPTIONS) && nlu->szDescriptiveName==NULL)
	   || (nlu->flags&NUF_HTTPGATEWAY && (nlu->pfnHttpGatewayInit==NULL))) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return (int)(HANDLE)NULL;
	}

	EnterCriticalSection(&csNetlibUser);
	for(i=0;i<netlibUserCount;i++)
		if(!lstrcmpA(netlibUser[i]->user.szSettingsModule,nlu->szSettingsModule)) {
			LeaveCriticalSection(&csNetlibUser);
			SetLastError(ERROR_DUP_NAME);
			return (int)(HANDLE)NULL;
		}
	LeaveCriticalSection(&csNetlibUser);

	thisUser=(struct NetlibUser*)mir_calloc(sizeof(struct NetlibUser));
	thisUser->handleType=NLH_USER;
	thisUser->user=*nlu;
	if((thisUser->user.szSettingsModule=mir_strdup(nlu->szSettingsModule))==NULL
	   || (nlu->szDescriptiveName && (thisUser->user.szDescriptiveName=mir_strdup(nlu->szDescriptiveName))==NULL)
	   || (nlu->szHttpGatewayUserAgent && (thisUser->user.szHttpGatewayUserAgent=mir_strdup(nlu->szHttpGatewayUserAgent))==NULL)) {
		SetLastError(ERROR_OUTOFMEMORY);
		return (int)(HANDLE)NULL;
	}
	if (nlu->szHttpGatewayHello)
		thisUser->user.szHttpGatewayHello=mir_strdup(nlu->szHttpGatewayHello);
	else
		thisUser->user.szHttpGatewayHello=NULL;

	thisUser->settings.cbSize=sizeof(NETLIBUSERSETTINGS);
	thisUser->settings.useProxy=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLUseProxy",0);
	thisUser->settings.proxyType=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLProxyType",PROXYTYPE_SOCKS5);
	if(thisUser->user.flags&NUF_NOHTTPSOPTION && thisUser->settings.proxyType==PROXYTYPE_HTTPS)
		thisUser->settings.proxyType=PROXYTYPE_HTTP;
	if(!(thisUser->user.flags&(NUF_HTTPCONNS|NUF_HTTPGATEWAY)) && thisUser->settings.proxyType==PROXYTYPE_HTTP) {
		thisUser->settings.useProxy=0;
		thisUser->settings.proxyType=PROXYTYPE_SOCKS5;
	}
	thisUser->settings.szProxyServer=GetNetlibUserSettingString(thisUser->user.szSettingsModule,"NLProxyServer",0);
	thisUser->settings.wProxyPort=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLProxyPort",1080);
	thisUser->settings.useProxyAuth=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLUseProxyAuth",0);
	thisUser->settings.szProxyAuthUser=GetNetlibUserSettingString(thisUser->user.szSettingsModule,"NLProxyAuthUser",0);
	thisUser->settings.szProxyAuthPassword=GetNetlibUserSettingString(thisUser->user.szSettingsModule,"NLProxyAuthPassword",1);
	thisUser->settings.useProxyAuthNtlm=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLUseProxyAuthNtlm",0);
	thisUser->settings.dnsThroughProxy=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLDnsThroughProxy",1);
	thisUser->settings.specifyIncomingPorts=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLSpecifyIncomingPorts",0);
	thisUser->settings.szIncomingPorts=GetNetlibUserSettingString(thisUser->user.szSettingsModule,"NLIncomingPorts",0);
	thisUser->settings.specifyOutgoingPorts=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLSpecifyOutgoingPorts",0);
	thisUser->settings.szOutgoingPorts=GetNetlibUserSettingString(thisUser->user.szSettingsModule,"NLOutgoingPorts",0);
	thisUser->settings.enableUPnP=GetNetlibUserSettingInt(thisUser->user.szSettingsModule,"NLEnableUPnP",1); //default to on

	EnterCriticalSection(&csNetlibUser);
	netlibUser=(struct NetlibUser**)mir_realloc(netlibUser,sizeof(struct NetlibUser*)*++netlibUserCount);
	netlibUser[netlibUserCount-1]=thisUser;
	LeaveCriticalSection(&csNetlibUser);
	return (int)thisUser;
}

static int NetlibGetUserSettings(WPARAM wParam,LPARAM lParam)
{
	NETLIBUSERSETTINGS *nlus=(NETLIBUSERSETTINGS*)lParam;
	struct NetlibUser *nlu=(struct NetlibUser*)wParam;

	if(GetNetlibHandleType(nlu)!=NLH_USER || nlus==NULL || nlus->cbSize!=sizeof(NETLIBUSERSETTINGS)) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return 0;
	}
	*nlus=nlu->settings;
	return 1;
}

static int NetlibSetUserSettings(WPARAM wParam,LPARAM lParam)
{
	NETLIBUSERSETTINGS *nlus=(NETLIBUSERSETTINGS*)lParam;
	struct NetlibUser *nlu=(struct NetlibUser*)wParam;

	if(GetNetlibHandleType(nlu)!=NLH_USER || nlus==NULL || nlus->cbSize!=sizeof(NETLIBUSERSETTINGS)) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return 0;
	}
	NetlibSaveUserSettingsStruct(nlu->user.szSettingsModule,nlus);
	return 1;
}

int NetlibCloseHandle(WPARAM wParam,LPARAM lParam)
{
	switch(GetNetlibHandleType(wParam)) {
		case NLH_USER:
		{	struct NetlibUser *nlu=(struct NetlibUser*)wParam;
			int i;
			EnterCriticalSection(&csNetlibUser);
			for(i=0;i<netlibUserCount;i++)
				if(!lstrcmpA(netlibUser[i]->user.szSettingsModule,nlu->user.szSettingsModule)) {
					netlibUserCount--;
					memmove(netlibUser+i,netlibUser+i+1,(netlibUserCount-i)*sizeof(struct NetlibUser*));
					break;
				}
			LeaveCriticalSection(&csNetlibUser);
			NetlibFreeUserSettingsStruct(&nlu->settings);
			if(nlu->user.szSettingsModule) mir_free(nlu->user.szSettingsModule);
			if(nlu->user.szDescriptiveName) mir_free(nlu->user.szDescriptiveName);
			if(nlu->user.szHttpGatewayHello) mir_free(nlu->user.szHttpGatewayHello);
			if(nlu->user.szHttpGatewayUserAgent) mir_free(nlu->user.szHttpGatewayUserAgent);
			if(nlu->szStickyHeaders) mir_free(nlu->szStickyHeaders);
			break;
		}
		case NLH_CONNECTION:
		{	struct NetlibConnection *nlc=(struct NetlibConnection*)wParam;
			HANDLE waitHandles[4];
			DWORD waitResult;

			WaitForSingleObject(hConnectionHeaderMutex,INFINITE);
			if (nlc->usingHttpGateway)
			{
				struct NetlibHTTPProxyPacketQueue *p = nlc->pHttpProxyPacketQueue;
				while (p != NULL) {
					struct NetlibHTTPProxyPacketQueue *t = p;

					p = p->next;

					mir_free(t->dataBuffer);
					mir_free(t);
				}
			}
			else
			{
				if(nlc->handleType!=NLH_CONNECTION || nlc->s==INVALID_SOCKET) {
					ReleaseMutex(hConnectionHeaderMutex);
					SetLastError(ERROR_INVALID_PARAMETER);	  //already been closed
					return 0;
				}
				closesocket(nlc->s);
				nlc->s=INVALID_SOCKET;
			}
			ReleaseMutex(hConnectionHeaderMutex);

			waitHandles[0]=hConnectionHeaderMutex;
			waitHandles[1]=nlc->hOkToCloseEvent;
			waitHandles[2]=nlc->ncsRecv.hMutex;
			waitHandles[3]=nlc->ncsSend.hMutex;
			waitResult=WaitForMultipleObjects( SIZEOF(waitHandles),waitHandles,TRUE,INFINITE);
			if(waitResult<WAIT_OBJECT_0 || waitResult >= WAIT_OBJECT_0 + SIZEOF(waitHandles)) {
				ReleaseMutex(hConnectionHeaderMutex);
				SetLastError(ERROR_INVALID_PARAMETER);	  //already been closed
				return 0;
			}
			nlc->handleType=0;
			if(nlc->nlhpi.szHttpPostUrl) mir_free(nlc->nlhpi.szHttpPostUrl);
			if(nlc->nlhpi.szHttpGetUrl) mir_free(nlc->nlhpi.szHttpGetUrl);
			if(nlc->dataBuffer) mir_free(nlc->dataBuffer);
			NetlibDestroySecurityProvider("NTLM", nlc->hNtlmSecurity);
			NetlibDeleteNestedCS(&nlc->ncsRecv);
			NetlibDeleteNestedCS(&nlc->ncsSend);
			CloseHandle(nlc->hOkToCloseEvent);
			DeleteCriticalSection(&nlc->csHttpSequenceNums);
			ReleaseMutex(hConnectionHeaderMutex);
			Netlib_Logf(nlc->nlu,"(%p:%u) Connection closed",nlc,nlc->s);
			break;
		}
		case NLH_BOUNDPORT:
			return NetlibFreeBoundPort((struct NetlibBoundPort*)wParam);
		case NLH_PACKETRECVER:
		{	struct NetlibPacketRecver *nlpr=(struct NetlibPacketRecver*)wParam;
			mir_free(nlpr->packetRecver.buffer);
			break;
		}
		default:
			SetLastError(ERROR_INVALID_PARAMETER);
			return 0;
	}
	mir_free((void*)wParam);
	return 1;
}

static int NetlibGetSocket(WPARAM wParam,LPARAM lParam)
{
	SOCKET s;
	if((void*)wParam==NULL) {
		s=INVALID_SOCKET;
		SetLastError(ERROR_INVALID_PARAMETER);
	}
	else {
		WaitForSingleObject(hConnectionHeaderMutex,INFINITE);
		switch(GetNetlibHandleType(wParam)) {
			case NLH_CONNECTION:
				s=(int)((struct NetlibConnection*)wParam)->s;
				break;
			case NLH_BOUNDPORT:
				s=(int)((struct NetlibBoundPort*)wParam)->s;
				break;
			default:
				s=INVALID_SOCKET;
				SetLastError(ERROR_INVALID_PARAMETER);
				break;
		}
		ReleaseMutex(hConnectionHeaderMutex);
	}
	return s;
}

static char szHexDigits[]="0123456789ABCDEF";
int NetlibHttpUrlEncode(WPARAM wParam,LPARAM lParam)
{
	unsigned char *szOutput,*szInput=(unsigned char*)lParam;
	unsigned char *pszIn,*pszOut;
	int outputLen;

	if(szInput==NULL) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return (int)(char*)NULL;
	}
	for(outputLen=0,pszIn=szInput;*pszIn;pszIn++) {
		if(isalnum(*pszIn) || *pszIn==' ') outputLen++;
		else outputLen+=3;
	}
	szOutput=(unsigned char*)HeapAlloc(GetProcessHeap(),0,outputLen+1);
	if(szOutput==NULL) {
		SetLastError(ERROR_OUTOFMEMORY);
		return (int)(unsigned char*)NULL;
	}
	for(pszOut=szOutput,pszIn=szInput;*pszIn;pszIn++) {
		if(isalnum(*pszIn)) *pszOut++=*pszIn;
		else if(*pszIn==' ') *pszOut++='+';
		else {
			*pszOut++='%';
			*pszOut++=szHexDigits[*pszIn>>4];
			*pszOut++=szHexDigits[*pszIn&0xF];
		}
	}
	*pszOut='\0';
	return (int)szOutput;
}

static char base64chars[]="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
int NetlibBase64Encode(WPARAM wParam,LPARAM lParam)
{
	NETLIBBASE64 *nlb64=(NETLIBBASE64*)lParam;
	int iIn;
	char *pszOut;
	PBYTE pbIn;

	if(nlb64==NULL || nlb64->pszEncoded==NULL || nlb64->pbDecoded==NULL) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return 0;
	}
	if(nlb64->cchEncoded<Netlib_GetBase64EncodedBufferSize(nlb64->cbDecoded)) {
		SetLastError(ERROR_BUFFER_OVERFLOW);
		return 0;
	}
	nlb64->cchEncoded=Netlib_GetBase64EncodedBufferSize(nlb64->cbDecoded);
	for(iIn=0,pbIn=nlb64->pbDecoded,pszOut=nlb64->pszEncoded;iIn<nlb64->cbDecoded;iIn+=3,pbIn+=3,pszOut+=4) {
		pszOut[0]=base64chars[pbIn[0]>>2];
		if(nlb64->cbDecoded-iIn==1) {
			pszOut[1]=base64chars[(pbIn[0]&3)<<4];
			pszOut[2]='=';
			pszOut[3]='=';
			pszOut+=4;
			break;
		}
		pszOut[1]=base64chars[((pbIn[0]&3)<<4)|(pbIn[1]>>4)];
		if(nlb64->cbDecoded-iIn==2) {
			pszOut[2]=base64chars[(pbIn[1]&0xF)<<2];
			pszOut[3]='=';
			pszOut+=4;
			break;
		}
		pszOut[2]=base64chars[((pbIn[1]&0xF)<<2)|(pbIn[2]>>6)];
		pszOut[3]=base64chars[pbIn[2]&0x3F];
	}
	pszOut[0]='\0';
	return 1;
}

static BYTE Base64CharToInt(char c)
{
	if(c>='A' && c<='Z') return c-'A';
	if(c>='a' && c<='z') return c-'a'+26;
	if(c>='0' && c<='9') return c-'0'+52;
	if(c=='+') return 62;
	if(c=='/') return 63;
	if(c=='=') return 64;
	return 255;
}

int NetlibBase64Decode(WPARAM wParam,LPARAM lParam)
{
	NETLIBBASE64 *nlb64=(NETLIBBASE64*)lParam;
	char *pszIn;
	PBYTE pbOut;
	BYTE b1,b2,b3,b4;
	int iIn;

	if(nlb64==NULL || nlb64->pszEncoded==NULL || nlb64->pbDecoded==NULL) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return 0;
	}
	if(nlb64->cchEncoded&3) {
		SetLastError(ERROR_INVALID_DATA);
		return 0;
	}
	if(nlb64->cbDecoded<Netlib_GetBase64DecodedBufferSize(nlb64->cchEncoded)) {
		SetLastError(ERROR_BUFFER_OVERFLOW);
		return 0;
	}
	nlb64->cbDecoded=Netlib_GetBase64DecodedBufferSize(nlb64->cchEncoded);
	for(iIn=0,pszIn=nlb64->pszEncoded,pbOut=nlb64->pbDecoded;iIn<nlb64->cchEncoded;iIn+=4,pszIn+=4,pbOut+=3) {
		b1=Base64CharToInt(pszIn[0]);
		b2=Base64CharToInt(pszIn[1]);
		b3=Base64CharToInt(pszIn[2]);
		b4=Base64CharToInt(pszIn[3]);
		if(b1==255 || b1==64 || b2==255 || b2==64 || b3==255 || b4==255) {
			SetLastError(ERROR_INVALID_DATA);
			return 0;
		}
		pbOut[0]=(b1<<2)|(b2>>4);
		if(b3==64) {nlb64->cbDecoded-=2; break;}
		pbOut[1]=(b2<<4)|(b3>>2);
		if(b4==64) {nlb64->cbDecoded--; break;}
		pbOut[2]=b4|(b3<<6);
	}
	return 1;
}

static int NetlibShutdown(WPARAM wParam,LPARAM lParam)
{
	int i;

	NetlibSecurityDestroy();
	NetlibUPnPDestroy();
	NetlibLogShutdown();
	for(i=netlibUserCount;i>0;i--)
		NetlibCloseHandle((WPARAM)netlibUser[i-1],0);
	if(netlibUser) mir_free(netlibUser);
	CloseHandle(hConnectionHeaderMutex);
	DeleteCriticalSection(&csNetlibUser);
	WSACleanup();
	return 0;
}

static int NetlibModulesLoaded(WPARAM wParam, LPARAM lParam)
{
	HookEvent(ME_SYSTEM_SHUTDOWN,NetlibShutdown); // get shutdown hook _after_ all the other plugins
	return 0;
}

int LoadNetlibModule(void)
{
	WSADATA wsadata;

	//HookEvent(ME_SYSTEM_SHUTDOWN,NetlibShutdown); // hooked later to be called last after plugins
	HookEvent(ME_SYSTEM_MODULESLOADED, NetlibModulesLoaded);
	HookEvent(ME_OPT_INITIALISE,NetlibOptInitialise);
	WSAStartup(MAKEWORD(1,1), &wsadata);
	InitializeCriticalSection(&csNetlibUser);
	hConnectionHeaderMutex=CreateMutex(NULL,FALSE,NULL);
	g_LastConnectionTick=GetTickCount();
	NetlibLogInit();
	CreateServiceFunction(MS_NETLIB_REGISTERUSER,NetlibRegisterUser);
	CreateServiceFunction(MS_NETLIB_GETUSERSETTINGS,NetlibGetUserSettings);
	CreateServiceFunction(MS_NETLIB_SETUSERSETTINGS,NetlibSetUserSettings);
	CreateServiceFunction(MS_NETLIB_CLOSEHANDLE,NetlibCloseHandle);
	CreateServiceFunction(MS_NETLIB_BINDPORT,NetlibBindPort);
	CreateServiceFunction(MS_NETLIB_OPENCONNECTION,NetlibOpenConnection);
	CreateServiceFunction(MS_NETLIB_SETHTTPPROXYINFO,NetlibHttpGatewaySetInfo);
	CreateServiceFunction(MS_NETLIB_SETSTICKYHEADERS,NetlibHttpSetSticky);
	CreateServiceFunction(MS_NETLIB_GETSOCKET,NetlibGetSocket);
	CreateServiceFunction(MS_NETLIB_URLENCODE,NetlibHttpUrlEncode);
	CreateServiceFunction(MS_NETLIB_BASE64ENCODE,NetlibBase64Encode);
	CreateServiceFunction(MS_NETLIB_BASE64DECODE,NetlibBase64Decode);
	CreateServiceFunction(MS_NETLIB_SENDHTTPREQUEST,NetlibHttpSendRequest);
	CreateServiceFunction(MS_NETLIB_RECVHTTPHEADERS,NetlibHttpRecvHeaders);
	CreateServiceFunction(MS_NETLIB_FREEHTTPREQUESTSTRUCT,NetlibHttpFreeRequestStruct);
	CreateServiceFunction(MS_NETLIB_HTTPTRANSACTION,NetlibHttpTransaction);
	CreateServiceFunction(MS_NETLIB_SEND,NetlibSend);
	CreateServiceFunction(MS_NETLIB_RECV,NetlibRecv);
	CreateServiceFunction(MS_NETLIB_SELECT,NetlibSelect);
	CreateServiceFunction(MS_NETLIB_SELECTEX,NetlibSelectEx);
	CreateServiceFunction(MS_NETLIB_CREATEPACKETRECVER,NetlibPacketRecverCreate);
	CreateServiceFunction(MS_NETLIB_GETMOREPACKETS,NetlibPacketRecverGetMore);
	CreateServiceFunction(MS_NETLIB_SETPOLLINGTIMEOUT,NetlibHttpSetPollingTimeout);

	NetlibUPnPInit();
	NetlibSecurityInit();
	return 0;
}
