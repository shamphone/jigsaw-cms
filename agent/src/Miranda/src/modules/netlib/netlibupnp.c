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

extern struct NetlibUser **netlibUser;
extern int netlibUserCount;
extern CRITICAL_SECTION csNetlibUser;

static char search_request_msg[] = 
	"M-SEARCH * HTTP/1.1\r\n"
	"MX: 1\r\n"
	"HOST: 239.255.255.250:1900\r\n"
	"MAN: \"ssdp:discover\"\r\n"
	"ST: urn:schemas-upnp-org:service:%s\r\n"
	"\r\n";

static char xml_get_hdr[] =
	"GET %s HTTP/1.1\r\n"
	"Connection: close\r\n"
	"Host: %s:%u\r\n\r\n";

static char soap_post_hdr[] =
	"POST %s HTTP/1.1\r\n"
	"HOST: %s:%u\r\n"
	"CONTENT-LENGTH: %u\r\n"
	"CONTENT-TYPE: text/xml; charset=\"utf-8\"\r\n"
	"SOAPACTION: \"%s#%s\"\r\n\r\n"
	"%s";

static char soap_post_hdr_m[] =
	"M-POST %s URL HTTP/1.1\r\n"
	"HOST: %s:%u\r\n"
	"CONTENT-LENGTH: %u\r\n"
	"CONTENT-TYPE: text/xml; charset=\"utf-8\"\r\n"
	"MAN: \"http://schemas.xmlsoap.org/soap/envelope/\"; ns=01\r\n"
	"01-SOAPACTION: \"%s#%s\"\r\n\r\n"
	"%s";

static char search_device[] = 
	"<serviceType>%s</serviceType>";

static char soap_action[] =
	"<s:Envelope\r\n"
	"    xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\"\r\n"
	"    s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\r\n"
	"  <s:Body>\r\n"
	"    <u:%s xmlns:u=\"%s\">\r\n"
	"%s"
	"    </u:%s>\r\n"
	"  </s:Body>\r\n"
	"</s:Envelope>\r\n";

static char add_port_mapping[] =
	"      <NewRemoteHost></NewRemoteHost>\r\n"
	"      <NewExternalPort>%i</NewExternalPort>\r\n"
	"      <NewProtocol>%s</NewProtocol>\r\n"
	"      <NewInternalPort>%i</NewInternalPort>\r\n"
	"      <NewInternalClient>%s</NewInternalClient>\r\n"
	"      <NewEnabled>1</NewEnabled>\r\n"
	"      <NewPortMappingDescription>Miranda</NewPortMappingDescription>\r\n"
	"      <NewLeaseDuration>0</NewLeaseDuration>\r\n";

static char delete_port_mapping[] =
	"     <NewRemoteHost></NewRemoteHost>\r\n"
	"     <NewExternalPort>%i</NewExternalPort>\r\n"
	"     <NewProtocol>%s</NewProtocol>\r\n";

static char get_port_mapping[] =
	"     <NewPortMappingIndex>%i</NewPortMappingIndex>\r\n";

static BOOL gatewayFound = FALSE;
static SOCKADDR_IN locIP;
static time_t lastDiscTime = 0;
static int expireTime = 120;

static WORD *portList;
static unsigned numports, numportsAlloc;
HANDLE portListMutex;

static char szCtlUrl[256], szDev[256];


static BOOL txtParseParam(char* szData, char* presearch, 
						  char* start, char* finish, char* param, int size)
{
	char *cp, *cp1;
	int len;
	
	*param = 0;

	if (presearch != NULL)
	{
		cp1 = strstr(szData, presearch);
		if (cp1 == NULL) return FALSE;
	}
	else
		cp1 = szData;

	cp = strstr(cp1, start);
	if (cp == NULL) return FALSE;
	cp += strlen(start);
	while (*cp == ' ') ++cp;

	cp1 = strstr(cp, finish);
	if (cp1 == NULL) return FALSE;
	while (*(cp1-1) == ' ' && cp1 > cp) --cp1;

	len = min(cp1 - cp, size);
	strncpy(param, cp, len);
	param[len] = 0;

	return TRUE;
}

void parseURL(char* szUrl, char* szHost, unsigned short* sPort, char* szPath)
{
	char *ppath, *phost, *pport;
	int sz;

	phost = strstr(szUrl,"://");
	if (phost == NULL) phost = szUrl;
	else phost += 3;
	
	ppath = strchr(phost,'/');
	if (ppath == NULL) ppath = phost + strlen(phost);
	
	pport = strchr(phost,':');
	if (pport == NULL) pport = ppath;

	if (szHost != NULL)
	{
		sz = pport - phost + 1;
		if (sz>256) sz = 256;
		strncpy(szHost, phost, sz);
		szHost[sz-1] = 0;
	}

	if (sPort != NULL)
	{
		if (pport < ppath)
		{
			long prt = atol(pport+1);
			*sPort = prt != 0 ? (unsigned short)prt : 80;
		}
		else
			*sPort = 80;
	}

	if (szPath != NULL)
	{
		strncpy(szPath, ppath, 256);
		szPath[255] = 0;
	}
}


static LongLog(char* szData)
{
	char* buf = szData;
	int sz = strlen(szData);

	while ( sz > 1000)
	{
		char* nbuf = buf + 1000;
		char t = *nbuf;
		*nbuf = 0;
		Netlib_Logf(NULL, buf);
		*nbuf = t;
		buf = nbuf;
		sz -= 1000;
	}
	Netlib_Logf(NULL, buf);
}


static void discoverUPnP(char* szUrl, int sizeUrl)
{
	char* buf;
	int buflen;
	unsigned i, j, nip = 0;
	char* szData = NULL;
	unsigned* ips = NULL;

	static const unsigned any = INADDR_ANY;
	fd_set readfd;
	TIMEVAL tv = { 1, 500000 };

	char hostname[256];
	PHOSTENT he;

	SOCKET sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP);

	SOCKADDR_IN enetaddr;
	enetaddr.sin_family = AF_INET;
	enetaddr.sin_port = htons(1900);
	enetaddr.sin_addr.s_addr = inet_addr("239.255.255.250");

	FD_ZERO(&readfd);
	FD_SET(sock, &readfd);

	szUrl[0] = 0;

	gethostname( hostname, sizeof( hostname ));
	he = gethostbyname( hostname );

	if (he)
	{
		while(he->h_addr_list[nip]) ++nip;

		ips = mir_alloc(nip * sizeof(unsigned));

		for (j=0; j<nip; ++j)
			ips[j] = *(unsigned*)he->h_addr_list[j];
	}

	buf = ( char* )alloca(1500);

	for(i = 3;  --i && szUrl[0] == 0;) 
	{
		for (j=0; j<nip; ++j)
		{
			if (ips)
				setsockopt(sock, IPPROTO_IP, IP_MULTICAST_IF, (char *)&ips[j], sizeof(unsigned));

			buflen = mir_snprintf(buf, 1500, search_request_msg, "WANIPConnection:1");
			sendto(sock, buf, buflen, 0, (SOCKADDR*)&enetaddr, sizeof(enetaddr)); 
			LongLog(buf);

			buflen = mir_snprintf(buf, 1500, search_request_msg, "WANPPPConnection:1");
			sendto(sock, buf, buflen, 0, (SOCKADDR*)&enetaddr, sizeof(enetaddr)); 
			LongLog(buf);
		}

		while (select(0, &readfd, NULL, NULL, &tv) == 1) 
		{
			buflen = recv(sock, buf, 1500, 0);
			if (buflen != SOCKET_ERROR) 
			{
				buf[buflen] = 0;
				LongLog(buf);

				if (txtParseParam(buf, NULL, "LOCATION:", "\r", szUrl, sizeUrl) ||
					txtParseParam(buf, NULL, "Location:", "\r", szUrl, sizeUrl))
				{
					char age[30];
					txtParseParam(buf, NULL, "ST:", "\r", szDev, sizeof(szDev));
					txtParseParam(buf, "max-age", "=", "\r", age, sizeof(age));
					expireTime = atoi(age);
					break;
	}	}	}	}

	mir_free(ips);
	setsockopt(sock, IPPROTO_IP, IP_MULTICAST_IF, (char *)&any, sizeof(unsigned));
	closesocket(sock);
}

static int httpTransact(char* szUrl, char* szResult, int resSize, char* szActionName)
{
	// Parse URL
	char szHost[256], szPath[256], szRes[16];
	int sz, res = 0;
	unsigned short sPort;
	SOCKET sock = INVALID_SOCKET;

	char* szPostHdr = soap_post_hdr;
	char* szData = mir_alloc(4096);
	char* szReq = szActionName ? mir_strdup(szResult) : NULL;
	szResult[0] = 0;

	parseURL(szUrl, szHost, &sPort, szPath);

	for (;;)
	{
		if (szActionName == NULL) 
			sz = mir_snprintf (szData, 4096, xml_get_hdr, szPath, szHost, sPort);
		else
		{
			char szData1[1024];
			
			sz = mir_snprintf (szData1, sizeof(szData1),
				soap_action, szActionName, szDev, szReq, szActionName);

			sz = mir_snprintf (szData, 4096,
				szPostHdr, szPath, szHost, sPort, 
				sz, szDev, szActionName, szData1);
		}

		{
			static TIMEVAL tv = { 5, 0 };
			static unsigned ttl = 4;
			static u_long mode = 1; 
			fd_set readfd;
			SOCKADDR_IN enetaddr;

			sock = socket(AF_INET, SOCK_STREAM, IPPROTO_TCP);

			enetaddr.sin_family = AF_INET;
			enetaddr.sin_port = htons(sPort);
			enetaddr.sin_addr.s_addr = inet_addr(szHost);

			// Resolve host name if needed
			if (enetaddr.sin_addr.s_addr == INADDR_NONE)
			{
				PHOSTENT he = gethostbyname(szHost);
				if (he) 
					enetaddr.sin_addr.s_addr = *(unsigned*)he->h_addr_list[0];
			}

			Netlib_Logf(NULL, "UPnP HTTP connection Host: %s Port: %u\n", szHost, sPort); 

			FD_ZERO(&readfd);
			FD_SET(sock, &readfd);

			// Limit the scope of the connection (does not work for 
			setsockopt(sock, IPPROTO_IP, IP_TTL, (char *)&ttl, sizeof(unsigned));

			// Put socket into non-blocking mode for timeout on connect
			ioctlsocket(sock, FIONBIO, &mode);

			// Connect to the remote host
			if (connect(sock, (SOCKADDR*)&enetaddr, sizeof(enetaddr)) == SOCKET_ERROR)
			{
				int err = WSAGetLastError();
				
				// Socket connection failed
				if (err != WSAEWOULDBLOCK)
				{
					Netlib_Logf(NULL, "UPnP connect failed %d", err);
					break;
				}
				// Wait for socket to connect 
				else if (select(-1, NULL, &readfd, NULL, &tv) != 1) 
				{
					Netlib_Logf(NULL, "UPnP connect timeout");
					break;
				}
			}

			if (send( sock, szData, sz, 0 ) != SOCKET_ERROR)
			{
				char *hdrend = NULL;
				int acksz = 0, pktsz = 0;

				LongLog(szData);
				sz = 0;
				for(;;) 
				{
					int bytesRecv;

					// Wait for the next packet
					if (select(0, &readfd, NULL, NULL, &tv) != 1) 
					{
						Netlib_Logf(NULL, "UPnP recieve timeout"); 
						break;
					}

					// 
					bytesRecv = recv( sock, &szResult[sz], resSize-sz, 0 );
					// Connection closed or aborted, all data received
					if ( bytesRecv == 0 || bytesRecv == SOCKET_ERROR) 
						break;
					else
						sz += bytesRecv;

					// Insert null terminator to use string functions
					if (sz >= (resSize-1)) 
					{
						szResult[resSize-1] = 0;
						break;
					}
					else
						szResult[sz] = 0;
					
					// HTTP header found?
					if (hdrend == NULL)
					{
						// Find HTTP header end
						hdrend = strstr(szResult, "\r\n\r\n");
						if (hdrend != NULL)
						{
							hdrend += 4;

							// Get packet size if provided
							if (txtParseParam(szResult, NULL, "Content-Length:", "\r", szRes, sizeof(szRes)) ||
								txtParseParam(szResult, NULL, "CONTENT-LENGTH:", "\r", szRes, sizeof(szRes)))
							{
								// Add size of HTTP header to the packet size to compute full transmission size
								pktsz = atol(szRes) + (hdrend - szResult);
							}
							// Get encoding type if provided
							if (txtParseParam(szResult, NULL, "Transfer-Encoding:", "\r", szRes, sizeof(szRes)) &&
								stricmp(szRes, "Chunked") == 0)
							{
								acksz = hdrend - szResult;
							}
						}
					}

					// Content-Length bytes reached, all data received
					if (sz >= pktsz && pktsz != 0)
					{
						szResult[pktsz] = 0;
						break;
					}
 
					// Chunked encoding processing
					if (sz > acksz && acksz != 0)
					{
						// Parse out chunk size
						char* data = szResult + acksz;
						char* peol1 = data == hdrend ? data - 2 : strstr(data, "\r\n");
						if (peol1 != NULL)
						{
							char *peol2;
							peol1 += 2;
							
							peol2 = strstr(peol1, "\r\n");
							if (peol2 != NULL)
							{
								// Get chunk size
								int chunkBytes = strtol(peol1, NULL, 16);
								acksz += chunkBytes;
								peol2 += 2;

								memmove(data, peol2, strlen(peol2)+1);
								sz -= peol2 - data;

								// Last chunk, all data received
								if (chunkBytes == 0) break;
							}
						}
					}
				}
				LongLog(szResult);
			}
			else
				Netlib_Logf(NULL, "UPnP send failed %d", WSAGetLastError()); 

			if (szActionName == NULL) 
			{
				int len = sizeof(locIP);
				getsockname(sock, (SOCKADDR*)&locIP, &len);
				if (locIP.sin_addr.S_un.S_addr == 0x0100007f)
				{
					struct hostent *he;

					gethostname(szPath, sizeof(szPath));
					he = gethostbyname(szPath);
					if (he != NULL)
						locIP.sin_addr.S_un.S_addr = *(PDWORD)he->h_addr_list[0];
				}
			}

			shutdown(sock, 2);
			closesocket(sock);
			sock = INVALID_SOCKET;
		}
		txtParseParam(szResult, "HTTP", " ", " ", szRes, sizeof(szRes));
		res = atol(szRes);
		if (szActionName != NULL && res == 405 && szPostHdr == soap_post_hdr)
			szPostHdr = soap_post_hdr_m;
		else
			break;
	}

	if (sock != INVALID_SOCKET)
	{
		shutdown(sock, 2);
		closesocket(sock);
	}

	mir_free(szData);
	mir_free(szReq);
	return res;
}

static void findUPnPGateway(void)
{
	time_t curTime = time(NULL);

	if ((curTime - lastDiscTime) >= expireTime)
	{
		char szUrl[256];
		char szHostNew[256], szHostExist[256];
		char* szData = (char*)alloca(8192);

		lastDiscTime = curTime;

		discoverUPnP(szUrl, sizeof(szUrl));
		
		gatewayFound = szUrl[0] != 0;
		if ( !gatewayFound )
			return;

		parseURL(szUrl, szHostNew, NULL, NULL);
		parseURL(szCtlUrl, szHostExist, NULL, NULL);
		if (strcmp(szHostNew, szHostExist) == 0)
			return;

		txtParseParam(szUrl, NULL, "http://", "/", szCtlUrl, sizeof(szCtlUrl));
		
		gatewayFound = httpTransact(szUrl, szData, 8192, NULL) == 200;
		if (gatewayFound)
		{
			char szTemp[256];
			size_t ctlLen;

			txtParseParam(szData, NULL, "<URLBase>", "</URLBase>", szTemp, sizeof(szTemp));
			if (szTemp[0] != 0) strcpy(szCtlUrl, szTemp);
			ctlLen = strlen(szCtlUrl);
			if (ctlLen > 0 && szCtlUrl[ctlLen-1] == '/')
				szCtlUrl[--ctlLen] = 0;

			mir_snprintf(szTemp, sizeof(szTemp), search_device, szDev);
			txtParseParam(szData, szTemp, "<controlURL>", "</controlURL>", szUrl, sizeof(szUrl));
			switch (szUrl[0])
			{
				case 0:
					gatewayFound = FALSE;
					break;

				case '/': 
					strncat(szCtlUrl, szUrl, sizeof(szCtlUrl) - ctlLen);
					szCtlUrl[sizeof(szCtlUrl)-1] = 0;
					break;

				default: 
					strncpy(szCtlUrl, szUrl, sizeof(szCtlUrl));
					szCtlUrl[sizeof(szCtlUrl)-1] = 0;
					break;
		}	}

		Netlib_Logf(NULL, "UPnP Gateway detected %d, Control URL: %s\n", gatewayFound, szCtlUrl); 
}	}

BOOL NetlibUPnPAddPortMapping(WORD intport, char *proto, WORD *extport, DWORD *extip, BOOL search)
{
	int res = 0;

	findUPnPGateway();

	if ( gatewayFound ) {
		char* szData = mir_alloc(4096);
		char szExtIP[30];

		*extport = intport - 1;
		*extip = ntohl(locIP.sin_addr.S_un.S_addr);

		WaitForSingleObject(portListMutex, INFINITE);

		do {
			++*extport;
			mir_snprintf(szData, 4096, add_port_mapping, 
				*extport, proto, intport, inet_ntoa(locIP.sin_addr));
			res = httpTransact(szCtlUrl, szData, 4096, "AddPortMapping");
			txtParseParam(szData, NULL, "<errorCode>", "</errorCode>", szExtIP, sizeof(szExtIP));

		}
		while (search && res == 500 && atol(szExtIP) == 718);
		
		if (res == 200) {
			szData[0] = 0;
			res = httpTransact(szCtlUrl, szData, 4096, "GetExternalIPAddress");
			if (res == 200 && txtParseParam(szData, "<NewExternalIPAddress", ">", "<", szExtIP, sizeof(szExtIP)))
				*extip = ntohl(inet_addr(szExtIP));

			if (numports >= numportsAlloc)
				mir_realloc(portList, sizeof(WORD)*(numportsAlloc += 10));
			portList[numports++] = *extport;
		}

		mir_free(szData);
		ReleaseMutex(portListMutex);
	}

	return res == 200;
}

void NetlibUPnPDeletePortMapping(WORD extport, char* proto)
{
	if (extport == 0)
		return;

//	findUPnPGateway();

	if (gatewayFound) {
		unsigned i;
		char* szData = (char*)alloca(4096);
		
		WaitForSingleObject(portListMutex, INFINITE);
		mir_snprintf(szData, 4096, delete_port_mapping, extport, proto);
		httpTransact(szCtlUrl, szData, 4096, "DeletePortMapping");

		for ( i=0; i < numports; ++i )
			if ( portList[i] == extport && --numports > 0)
				memmove(&portList[i], &portList[i+1], (numports - i)*sizeof(WORD));

		ReleaseMutex(portListMutex);
}	}

void NetlibUPnPCleanup(void* extra)
{
    if (DBGetContactSettingByte(NULL,"Netlib","NLEnableUPnP",1)==0) {
        // upnp is disabled globally, no need for a cleanup
        return;
    }
    {
        int i, incoming = 0;
        EnterCriticalSection(&csNetlibUser);
        for(i=netlibUserCount; i--; ) {
            if (netlibUser[i]->user.flags&NUF_INCOMING)
                incoming = 1;
        }
		LeaveCriticalSection(&csNetlibUser);
        if (!incoming) return;
    }
	findUPnPGateway();

	if ( gatewayFound ) {
		char* szData = ( char* )alloca(4096);
		char buf[50], lip[50];
		unsigned i, j = 0, k;
		
		WORD ports[30];

		strcpy(lip, inet_ntoa(locIP.sin_addr));

		for (i=0; !Miranda_Terminated(); ++i)  {
			mir_snprintf(szData, 4096, get_port_mapping, i);

			ReleaseMutex(portListMutex);
			WaitForSingleObject(portListMutex, INFINITE);

			if (httpTransact(szCtlUrl, szData, 4096, "GetGenericPortMappingEntry") != 200)
				break;

			if (!txtParseParam(szData, "<NewPortMappingDescription", ">", "<", buf, sizeof(buf)) || strcmp(buf, "Miranda") != 0)
				continue;

			if (!txtParseParam(szData, "<NewInternalClient", ">", "<", buf, sizeof(buf)) || strcmp(buf, lip) != 0)
				continue;

			if (txtParseParam(szData, "<NewExternalPort", ">", "<", buf, sizeof(buf))) {
				WORD mport = (WORD)atol(buf);

				for (k=0; k<numports; ++k)
					if ( portList[k] == mport)
						break;

				if (k >= numports && j < 30)
					ports[j++] = mport;
		}	}

		ReleaseMutex(portListMutex);

		for (i=0; i<j && !Miranda_Terminated(); ++i) { 
			WaitForSingleObject(portListMutex, INFINITE);
			NetlibUPnPDeletePortMapping(ports[i], "TCP");
			ReleaseMutex(portListMutex);
	}	}
}

void NetlibUPnPInit(void)
{
	numports = 0;
	numportsAlloc = 10;
	portList = mir_alloc(sizeof(WORD)*numportsAlloc);
	
	portListMutex = CreateMutex(NULL, FALSE, NULL);
}

void NetlibUPnPDestroy(void)
{
	mir_free(portList);
	CloseHandle(portListMutex);
}
