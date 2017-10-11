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

//mask must be 8192 bytes, returns number of bits set
#define PortInMask(mask,p)  ((mask)[((p)&0xFFFF)>>3]&(1<<((p)&7)))
int StringToPortsMask(const char *szPorts,BYTE *mask)
{
	const char *psz;
	char *pszEnd;
	int portMin,portMax,port;
	int bitCount=0;

	ZeroMemory(mask,8192);
	for(psz=szPorts;*psz;) {
		while(*psz==' ' && *psz==',') psz++;
		portMin=strtol(psz,&pszEnd,0);
		if(pszEnd==psz) break;
		while(*pszEnd==' ') pszEnd++;
		if(*pszEnd=='-') {
			psz=pszEnd+1;
			portMax=strtol(psz,&pszEnd,0);
			if(pszEnd==psz) portMax=65535;
			if(portMin>portMax) {
				port=portMin;
				portMin=portMax;
				portMax=port;
			}
		}
		else portMax=portMin;
		if(portMax>=1) {
			if(portMin<=0) portMin=1;
			for(port=portMin;port<=portMax;port++) {
				if(port>65535) break;
				if((port&7)==0 && portMax-port>=7) {mask[port>>3]=0xFF; port+=7; bitCount+=8;}
				else {mask[port>>3]|=1<<(port&7); bitCount++;}
			}
		}
		psz=pszEnd;
	}
	return bitCount;
}

int NetlibFreeBoundPort(struct NetlibBoundPort *nlbp)
{
	closesocket(nlbp->s);
	WaitForSingleObject(nlbp->hThread,INFINITE);
	CloseHandle(nlbp->hThread);
	NetlibUPnPDeletePortMapping(nlbp->wExPort, "TCP");
	mir_free(nlbp);
	return 1;
}

static DWORD __stdcall NetlibBindAcceptThread(struct NetlibBoundPort *nlbp)
{
	SOCKET s;
	SOCKADDR_IN sin;
	int sinLen;
	struct NetlibConnection *nlc;

	srand((unsigned int)time(NULL));
	Netlib_Logf(nlbp->nlu,"(%d) Port %u opened for incoming connections",nlbp->s,nlbp->wPort);
	for(;;) {
		sinLen=sizeof(sin);
		s=accept(nlbp->s,(struct sockaddr*)&sin,&sinLen);
		if(s==INVALID_SOCKET) break;
		Netlib_Logf(nlbp->nlu,"New incoming connection on port %u from %s (%d)",nlbp->wPort, inet_ntoa(sin.sin_addr),s);
		nlc=(struct NetlibConnection*)mir_alloc(sizeof(struct NetlibConnection));
		memset(nlc,0,sizeof(struct NetlibConnection));
		nlc->handleType=NLH_CONNECTION;
		nlc->nlu=nlbp->nlu;
		nlc->s=s;
		InitializeCriticalSection(&nlc->csHttpSequenceNums);
		nlc->hOkToCloseEvent=CreateEvent(NULL,TRUE,TRUE,NULL);
		nlc->dontCloseNow=0;
		NetlibInitializeNestedCS(&nlc->ncsSend);
		NetlibInitializeNestedCS(&nlc->ncsRecv);
		nlbp->pfnNewConnectionV2((HANDLE)nlc,ntohl(sin.sin_addr.S_un.S_addr), nlbp->pExtra);
	}
	return 0;
}

int NetlibBindPort(WPARAM wParam,LPARAM lParam)
{
	NETLIBBIND *nlb=(NETLIBBIND*)lParam;
	struct NetlibUser *nlu=(struct NetlibUser*)wParam;
	struct NetlibBoundPort *nlbp;
	SOCKADDR_IN sin;
	int foundPort=0;
	DWORD dwThreadId;

	if(GetNetlibHandleType(nlu)!=NLH_USER || !(nlu->user.flags&NUF_INCOMING) || nlb==NULL || nlb->pfnNewConnection==NULL) {
		SetLastError(ERROR_INVALID_PARAMETER);
		return (int)(HANDLE)NULL;
	}
	if ( nlb->cbSize != sizeof(NETLIBBIND)   &&
		 nlb->cbSize != NETLIBBIND_SIZEOF_V2 &&
		 nlb->cbSize != NETLIBBIND_SIZEOF_V1 )
	{
		return (int)(HANDLE)NULL;
	}
	nlbp=(struct NetlibBoundPort*)mir_alloc(sizeof(struct NetlibBoundPort));
	nlbp->handleType=NLH_BOUNDPORT;
	nlbp->nlu=nlu;
	nlbp->pfnNewConnectionV2=nlb->pfnNewConnectionV2;
	nlbp->s=socket(AF_INET,SOCK_STREAM,0);
	nlbp->pExtra= (nlb->cbSize != NETLIBBIND_SIZEOF_V1) ? nlb->pExtra : NULL;
	if(nlbp->s==INVALID_SOCKET) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"socket",WSAGetLastError());
		mir_free(nlbp);
		return (int)(HANDLE)NULL;
	}
	sin.sin_family=AF_INET;
	sin.sin_addr.s_addr=htonl(INADDR_ANY);
	sin.sin_port=0;
	/* if the netlib user wanted a free port given in the range, then
	they better have given wPort==0, let's hope so */
	if(nlu->settings.specifyIncomingPorts && nlb->wPort==0) {
		int startPort,portNum,i,j;
		BYTE portsMask[8192];
		int portsCount;

		portsCount=StringToPortsMask(nlu->settings.szIncomingPorts,portsMask);
		if(portsCount==0) {
			closesocket(nlbp->s);
			mir_free(nlbp);
			SetLastError(WSAEADDRINUSE);
			return (int)(HANDLE)NULL;
		}
		startPort=rand()%portsCount;
		for(i=0;i<8192;i++) {
			if(portsMask[i]==0) continue;
			if(portsMask[i]==0xFF && startPort>=8) {startPort-=8; continue;}
			for(j=0;j<8;j++)
				if(portsMask[i]&(1<<j))
					if(startPort--==0) {
						portNum=(i<<3)+j;
						break;
					}
			if(startPort==-1) break;
		}
		if(i==8192) return (int)(HANDLE)NULL;   //can't happen
		startPort=portNum;
		do
		{
			sin.sin_port=htons((WORD)portNum);
			if(bind(nlbp->s,(SOCKADDR *)&sin,sizeof(sin))==0) {
				foundPort=1;
				break;
			}
			for(portNum++;!PortInMask(portsMask,portNum);portNum++)
				if(portNum==65535) portNum=0;
		} while(portNum!=startPort);
	}
	else {
		/* if ->wPort==0 then they'll get any free port, otherwise they'll
		be asking for whatever was in nlb->wPort*/
		if (nlb->wPort!=0) {
			Netlib_Logf(nlu,"%s %d: trying to bind port %d, this 'feature' can be abused, please be sure you want to allow it.",__FILE__,__LINE__,nlb->wPort);
			sin.sin_port=htons(nlb->wPort);
		}
		if(bind(nlbp->s,(SOCKADDR *)&sin,sizeof(sin))==0) foundPort=1;
	}
	if(!foundPort) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"bind",WSAGetLastError());
		closesocket(nlbp->s);
		mir_free(nlbp);
		return (int)(HANDLE)NULL;
	}

	if(listen(nlbp->s,5)) {
		Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"listen",WSAGetLastError());
		closesocket(nlbp->s);
		mir_free(nlbp);
		return (int)(HANDLE)NULL;
	}

	{	int len;
		DWORD extIP;

		ZeroMemory(&sin,sizeof(sin));
		len=sizeof(sin);
		if(getsockname(nlbp->s,(SOCKADDR *)&sin,&len)) {
			Netlib_Logf(nlu,"%s %d: %s() failed (%u)",__FILE__,__LINE__,"getsockname",WSAGetLastError());
			closesocket(nlbp->s);
			mir_free(nlbp);
			return (int)(HANDLE)NULL;
		}
		nlb->wPort=ntohs(sin.sin_port);
		nlbp->wPort=nlb->wPort;
		nlb->dwInternalIP=ntohl(sin.sin_addr.S_un.S_addr);

		if (nlb->dwInternalIP == 0)
		{
			char hostname[64];
			struct hostent *he;

			gethostname(hostname,SIZEOF(hostname));
			he=gethostbyname(hostname);
			if(he->h_addr_list[0])
				nlb->dwInternalIP=ntohl(*(PDWORD)he->h_addr_list[0]);
		}
		if (nlu->settings.enableUPnP&&NetlibUPnPAddPortMapping(nlb->wPort, "TCP", &nlbp->wExPort,
			&extIP, nlb->cbSize > NETLIBBIND_SIZEOF_V2))
		{
			Netlib_Logf(NULL, "UPnP port mapping succeeded. Internal Port: %u External Port: %u\n", 
				nlb->wPort, nlbp->wExPort); 
			if (nlb->cbSize > NETLIBBIND_SIZEOF_V2)
			{
				nlb->wExPort = nlbp->wExPort;
				nlb->dwExternalIP = extIP;
			}
		}
		else
		{
			Netlib_Logf(NULL, "UPnP port mapping failed. Internal Port: %u\n", nlb->wPort); 

			nlbp->wExPort = 0;
			if (nlb->cbSize > NETLIBBIND_SIZEOF_V2)
			{
				nlb->wExPort = nlb->wPort;
				nlb->dwExternalIP = nlb->dwInternalIP;
			}
		}

	}
	nlbp->hThread=(HANDLE)forkthreadex(NULL,0,NetlibBindAcceptThread,nlbp,0,&dwThreadId);
	return (int)nlbp;
}

