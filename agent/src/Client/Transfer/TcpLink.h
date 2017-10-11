#pragma once

#include "WinsockWrapper/WinsockWrapper/TCPSocket.h"

class TcpLink
{
public:

    // 收到数据以后的回调函数原型定义
    typedef void (*TCPLINK_CALLBACK)(void* pObject, char* pBuffer, int nLength);

public:
	TcpLink(
		TCPLINK_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter);      // Callback parameter
	~TcpLink();

	BOOL Create(string IP, int port);
	void Destroy();

    // 获得底层的SOCKET
	SOCKET getSocket();

    // 发送TCP数据包
	int send(char* buf, int dwLen);

private:

	string	m_sServerIP;                    // 服务器IP
	int		m_nPort;                        // 服务器端口

	TCPLINK_CALLBACK m_pCallback;           // 回调函数
	void*        m_pCallbackParameter;      // 回调函数的参数

    // 底层的Socket
	CTCPSocket		m_Socket;

    // 读取socket的线程句柄
	HANDLE			m_hSocketThread;
	
    // 读取Socket的线程函数
	static unsigned int WINAPI SocketThreadProc(LPVOID lpParameter);	
};

