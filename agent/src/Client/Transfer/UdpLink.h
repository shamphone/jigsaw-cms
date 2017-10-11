#ifndef UDPLINK_H
#define UDPLINK_H

#include "WinsockWrapper/WinsockWrapper/UDPSocket.h"

class UdpLink
{
public:

    // 收到数据以后的回调函数原型定义
    typedef void (*UDPLINK_CALLBACK)(void* pObject, char* pBuffer, int nLength);

public:
	UdpLink(
		UDPLINK_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter);      // Callback parameter

	~UdpLink();

	BOOL Create(string IP, int port);
	void Destroy();

    // 获得底层的SOCKET
	SOCKET getSocket();

    // 发送UDP数据包
	int send(char* buf, int dwLen);

private:

	string	m_sServerIP;                    // 服务器IP
	int		m_nPort;                        // 服务器端口(UDP)

	UDPLINK_CALLBACK m_pCallback;           // 回调函数
	void*        m_pCallbackParameter;      // 回调函数的参数

    // 底层的Socket
	CUDPSocket		m_MediaUdpSocket;

    // 读取socket的线程句柄
	HANDLE			m_hUSockThread;
	
    // 读取Socket的线程函数
	static unsigned int WINAPI USockThreadProc(LPVOID lpParameter);	
};

#endif //UDPLINK_H
