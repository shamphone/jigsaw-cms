#pragma once

class CTCPSocket  
{
public:
	CTCPSocket();
	virtual ~CTCPSocket();

	// 以阻塞的方式发送数据，除非出现错误，否则保证数据全部发送
	BOOL Send(const char *pBuf,int iLen)const;

	// 以阻塞的方式接受数据，一旦接收到数据，将该数据返回给调用者，
	// 不保证接受到的数据等于缓冲区的长度
	int Receive(char *pBuf, int iLen);

	// 以阻塞的方式接受数据，直到读取的数据填满缓冲区，然后返回
	BOOL ReceiveBlock(char* pBuf, int iLen);

	SOCKET GetSocket();
	BOOL Create();
	BOOL Destroy();
	BOOL IsSocket();
	BOOL Connect(UINT uSocketPort, const char *pszSockAddr);

protected:
	SOCKET m_Socket;
};
