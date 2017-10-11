#include "StdAfx.h"
#include "IOManager.h"
#include "../MatrixC.h"
#include "../../../Common/Common/Log/Log.h"

// #define DEBUG_PROTOCOL
#define DEBUG_PROTOCOL_MAX_SIZE 256

LyvcMessage::IOManager::IOManager(
		PIO_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter,   // Callback parameter
        const char* strHost,        // Remote host ip
		int nPort)                  // Remote port
{
    this->m_pCallback = pCallback;
    this->m_pCallbackParameter = pCallbackParameter;
    this->m_strHost = string(strHost);
	this->m_nPort   = nPort;
}

LyvcMessage::IOManager::~IOManager()
{
}

BOOL LyvcMessage::IOManager::start()
{
    // Init Winsock
    WSADATA wsaData;
    ::WSAStartup(MAKEWORD(2,2), &wsaData);

    // Create window
    if( !createMessageWindow())
    {
        return FALSE;
    }

    // Create socket
	if( !m_TcpSocket.Create() )
	{
		return FALSE;
	}

    if (!m_TcpSocket.Connect(m_nPort, m_strHost.c_str()))
	{
		m_TcpSocket.Destroy();
		return FALSE;
	}

    // Run Socket read thread.
	unsigned int threadID;
	m_hSocketReadThread=(HANDLE)::_beginthreadex(NULL, 0, socketReadThreadProc, this, 0, &threadID);
	_RPTF1(_CRT_WARN, "MatrixC read tcp thread created, id: 0x%x \n",threadID);
	if (m_hSocketReadThread == 0)
	{
		m_TcpSocket.Destroy();
		return FALSE;
	}

	return TRUE;
}

BOOL LyvcMessage::IOManager::stop()
{
	// 关闭Socket
	if (m_TcpSocket.IsSocket())
	{
		m_TcpSocket.Destroy();
	}

    // 销毁窗口
	// 这一步必须放在停止线程之前，否则线程有可能处于
	// SendMessage的状态而造成死锁。
    if( this->m_hWindow != NULL )
    {
        ::DestroyWindow(this->m_hWindow);
        this->m_hWindow = NULL;
    }

	// 停止线程
    if( m_hSocketReadThread != 0 )
    {
	    ::WaitForSingleObject(m_hSocketReadThread, INFINITE);
		::CloseHandle(m_hSocketReadThread);
        m_hSocketReadThread = 0;
    }

    // 卸载Winsock library
    ::WSACleanup();

	return TRUE;
}

// 
// findMessage 找出当前缓冲区中下一个Message。
// 参数
//    pBuf     : 缓冲区指针
//    dataSize : 缓冲区长度
//    pContentStart  : 指向Message中实际内容(序列化结果)的指针
//    nContentLength : Message中实际内容(序列化结果)的长度
// 返回值
//    找到的Message的总体大小（序列化结果+header line)，如果没有找到，返回-1
//
static int findMessage(char* pBuf, int dataSize, char*& pContent, int& nContentLength)
{
	// 1, 查找 Content-Length 行
	char* pContentLengthLineStart = ::strstr(pBuf, "Content-Length:");
	if( pContentLengthLineStart == NULL )
	{
		return -1;
	}

	// 2, 取得 实际内容大小
	char* pContentLengthLineEnd = ::strchr(pContentLengthLineStart, '\r');
	if( pContentLengthLineEnd == NULL )
	{
		return -1;
	}
	*pContentLengthLineEnd = 0;
	nContentLength = ::atoi( pContentLengthLineStart + strlen("Content-Length:"));

	// 3, 判断内容是否完整
	if( pBuf + dataSize < pContentLengthLineEnd + 4 + nContentLength )
	{
        *pContentLengthLineEnd = '\r';
		return -1;
	}

	// 4, 设置实际内容的指针
	pContent = pContentLengthLineEnd + 4;

	// 5, 返回当前消息的大小
	return pContentLengthLineEnd + 4 + nContentLength - pBuf;
}

unsigned int WINAPI LyvcMessage::IOManager::socketReadThreadProc(LPVOID lpParameter)
{
	IOManager* _this=(IOManager*)lpParameter;

	char pBuf[CMD_PACKET_SIZE + 1]; // 接收数据的缓冲区
	int dataSize = 0;           // 缓冲区内的数据长度

	BOOL bRunning = TRUE;
	while(bRunning)
	{
		int readByteCount = 0;                    // 下一次读取操作读取的字节
		int size = CMD_PACKET_SIZE - dataSize;    // 缓冲区中剩余的空间

		// 如果出现接收错误,退出线程
		if ((readByteCount=_this->m_TcpSocket.Receive(pBuf+dataSize, size)) <= 0)
		{
			_RPTF1(_CRT_WARN, "%x\n", ::GetLastError());
            _this->m_TcpSocket.Destroy();
			_this->notifyConnectionBroken();
			bRunning = FALSE;
			continue;
		}

		// 修改缓冲区
		dataSize = dataSize + readByteCount;
		pBuf[dataSize] = 0;

		//
		// 有可能一次读到多个消息, 需要在循环中进行处理
        //

		// 下一个分析的起点
		int dataPos = 0;

		while(dataSize > 0)
		{
			// 当前XML消息体的实际内容位置指针及大小
			char* pContent;
			int nContentLength;

			// 当前消息的大小
			int currentMessageSize = findMessage(pBuf+dataPos, dataSize, pContent, nContentLength);

			// 如果缓冲区内剩余的数据不是一个完整消息，
			// 将剩余数据拷贝到缓冲区开始，然后退出分析
			if (currentMessageSize < 0)
			{
				if (dataSize != 0)
				{
					char buffer[CMD_PACKET_SIZE];
					::memcpy(buffer, pBuf+dataPos, dataSize);
					::memcpy(pBuf, buffer, dataSize);
					pBuf[dataSize] = 0;
				}
				break;
			}

			// 调整剩余的数据大小
			dataSize -= currentMessageSize;

			// 将sipMessage中的实际内容拷贝到新的缓冲区中，发送给窗口
			char* pBuffer = (char*)malloc(nContentLength);
            ::memcpy(pBuffer, pContent, nContentLength);

#ifdef DEBUG_PROTOCOL
            string tmp = string(pBuffer, nContentLength);
            if( tmp.size() > DEBUG_PROTOCOL_MAX_SIZE )
            {
                FVS_DEBUG1("Receive: %s\n", tmp.substr(0, DEBUG_PROTOCOL_MAX_SIZE ).c_str());
            }
            else
            {
                FVS_DEBUG1("Receive: %s\n", tmp.c_str());
            }
#endif
            MESSAGELENGTH_PACK* mp = new MESSAGELENGTH_PACK();
			mp->pIOManager = _this;
			mp->nLength = nContentLength;
            // 通知窗口
            ::SendMessageCallback(_this->m_hWindow, IOManager::DATA_RECEIVED_MESSAGE, (WPARAM)pBuffer, (LPARAM)mp, NULL, NULL);

			// 调整分析起点
			dataPos += currentMessageSize;
		}
	}

    return 0;
}

// 发送数据
void LyvcMessage::IOManager::sendData(const char *pBuf, DWORD len)
{
    // 检查当前的Socket是否有效
    if(!this->m_TcpSocket.IsSocket() )
    {
        return;
    }

    // 发送数据的缓冲区
	char buffer[CMD_PACKET_SIZE + 1];

    // 生成头部
	_snprintf(buffer, CMD_PACKET_SIZE + 1, LyvcMessage::MatrixC::LYVC_MESSAGE_HEADER, len);
    int headerLength = strlen(buffer);
	
    // 合并消息, 如果消息尺寸过大，不发送该消息
    if(len + headerLength > CMD_PACKET_SIZE) {
		_ASSERTE(FALSE);
        return;
    }
    ::memcpy(buffer + headerLength, pBuf, len);

#ifdef DEBUG_PROTOCOL
    string tmp = string(pBuf, len);
    if( tmp.size() > DEBUG_PROTOCOL_MAX_SIZE )
    {
        FVS_DEBUG1("Send: %s\n", tmp.substr(0, DEBUG_PROTOCOL_MAX_SIZE ).c_str());
    }
    else
    {
        FVS_DEBUG1("Send: %s\n", tmp.c_str());
    }
#endif

    // 发送消息
	if( !m_TcpSocket.Send(buffer, headerLength + len))
	{
        m_TcpSocket.Destroy();
		notifyConnectionBroken();
	}
}

void LyvcMessage::IOManager::notifyConnectionBroken()
{
#pragma message ("we use postmessage for notify connection broken, differs from simple message dispather")
    MESSAGELENGTH_PACK* mp = new MESSAGELENGTH_PACK();
	mp->pIOManager = this;
	mp->nLength = 0;
	::PostMessage(m_hWindow, DATA_RECEIVED_MESSAGE, (WPARAM)NULL, (LPARAM)mp);
	return;
}

BOOL LyvcMessage::IOManager::createMessageWindow()
{
    HINSTANCE hInstance = (HINSTANCE)::GetModuleHandle(NULL);

	WNDCLASSEX wcex;
    char* szWindowClass = "LyvcMessageIOManagerWindowClass";
	wcex.cbSize = sizeof(WNDCLASSEX); 

	wcex.style			= CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc	= (WNDPROC)IOManager::wndProc;
	wcex.cbClsExtra		= 0;
	wcex.cbWndExtra		= 0;
	wcex.hInstance		= hInstance;
	wcex.hIcon			= NULL;
	wcex.hCursor		= NULL;
	wcex.hbrBackground	= NULL;
	wcex.lpszMenuName	= NULL;
	wcex.lpszClassName	= szWindowClass;
	wcex.hIconSm		= NULL;
    ::RegisterClassEx(&wcex);

    this->m_hWindow = ::CreateWindow(
        szWindowClass,
        szWindowClass,
        WS_OVERLAPPEDWINDOW,
        CW_USEDEFAULT, 0, CW_USEDEFAULT, 0,
        NULL, NULL, hInstance, this);
    if( this->m_hWindow == NULL )
    {
        return FALSE;
    }

    return TRUE;
}

LRESULT CALLBACK LyvcMessage::IOManager::wndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    //static IOManager* _this = NULL;

    if( message == IOManager::DATA_RECEIVED_MESSAGE)
	{
		MESSAGELENGTH_PACK* mp = (MESSAGELENGTH_PACK*) lParam;
		IOManager* _this = mp->pIOManager;
		(*(_this->m_pCallback))(_this->m_pCallbackParameter, (char*)wParam, mp->nLength);
		delete []((char*)wParam);
		delete mp;
		return 0;
	}

    // In WM_CREATE message, we preserve the "this" pointer
    //if( message == WM_CREATE ) {
    //    _this = (IOManager*)((CREATESTRUCT*)lParam)->lpCreateParams;
    //}

	return ::DefWindowProc(hWnd, message, wParam, lParam);
}
