#ifndef IOMANAGER_H
#define IOMANAGER_H

#include "../../../WinsockWrapper/WinsockWrapper/TCPSocket.h"

namespace LyvcMessage {

class IOManager;

struct MESSAGELENGTH_PACK
{
	IOManager* pIOManager;
	int nLength;
};

class IOManager
{
public:

    // 收到数据以后的回调函数原型定义
    typedef void (*PIO_CALLBACK)(void* pObject, const char* pBuffer, int nLength);

public:

	// 单个消息的大小限制为 64k
	static const int CMD_PACKET_SIZE = 1024*64;

    // 用于表示收到数据的窗口消息ID
    static const int DATA_RECEIVED_MESSAGE = WM_USER + 1;

public:

	IOManager(
		PIO_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter,   // Callback parameter
        const char* strHost,        // Remote host ip
		int nPort);                 // Remote port

	~IOManager();

public:

	// 启动收发线程
	BOOL start();

	// 停止收发线程
	BOOL stop();

	// 发送数据
	void sendData(const char *pBuf, DWORD len);

private:

	PIO_CALLBACK m_pCallback;           // 回调函数
	void*        m_pCallbackParameter;  // 回调函数的参数
	string       m_strHost;             // 服务器地址
	int          m_nPort;               // 服务器端口

	CTCPSocket   m_TcpSocket;	    // Socket
    HWND         m_hWindow;         // 投递消息的窗口句柄
	HANDLE       m_hSocketReadThread;  // 接收线程的句柄

private:

    // 创建用于接收Socket消息的窗口
    BOOL createMessageWindow();

    // 接收Socket消息的窗口的窗口过程
    static LRESULT CALLBACK wndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam);

	// 用于接收Socket数据的线程
	static unsigned int WINAPI socketReadThreadProc(LPVOID lpParameter);

	// 发送消息通知网络故障
	void notifyConnectionBroken();

};  // class

};  // namespace

#endif
