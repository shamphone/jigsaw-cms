#ifndef _UDPSOCKET_H_
#define _UDPSOCKET_H_

class CUDPSocket
{
public:
	CUDPSocket();
	virtual ~CUDPSocket();

    // Create socket, client must call this function before use the socket
	BOOL Create(UINT uSocketPort=0, char* pszSockAddr=NULL);

    // Destory the socket.
	int Destroy();

    // Set socket buffer size
    void SetBuffer(int bufferSize);

    BOOL IsSocket();
	SOCKET GetSocket() const;
	SOCKADDR_IN GetLocalAddr() const;

    // Get/Set remote address, When no remote address specified in the Sendto function call
    // this remote address will be used.
	SOCKADDR_IN GetRemoteAddr() const;
    void SetRemoteAddr(const char *pszRemoteSockAddr,unsigned short uPort);
	
    // Send data to remote address, if pRemoteAddr is NULL, 
    // use the remoteaddr set by SetRemoteAddr
	BOOL SendTo(const char *pBuf, DWORD dwLen, SOCKADDR* pRemoteAddr=NULL);

    // Receive data, remote address will be saved in pRemoteAddr 
	DWORD RecvFrom(char *pBuf, DWORD dwLen, SOCKADDR* pRemoteAddr);

protected:	
	
	SOCKET m_Socket;
	SOCKADDR_IN m_LocalAddr;
	SOCKADDR_IN m_RemoteAddr;
};

#endif

