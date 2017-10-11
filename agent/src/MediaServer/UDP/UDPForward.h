#pragma once

#include "FvsmUser.h"
#include "WinsockWrapper/WinsockWrapper/UdpSocket.h"

class UDPForward
{
private:	

    //系统中所有的用户
    typedef		map<__int64, FvsmUser*> FvsmUserMap;	
	FvsmUserMap	m_allUsers;

    //使用一个全局关键区，保护媒体转发关系数据
	CRITICAL_SECTION	m_cs;

    // 接收媒体数据的UDP Socket
	CUDPSocket	m_udpSocket;

    // UDP 线程句柄
	HANDLE	m_hUSockThread;

    // UDP Running flag
    BOOL m_bUDPRuningFlag;

    // 媒体服务线程函数
	static unsigned int WINAPI UdpSockThreadProc(LPVOID lpParameter);

public:

    UDPForward();
    ~UDPForward();

public:

    // 开始运行
    BOOL start(int port);

    // 停止运行
    void stop();

public:

    //增加一个用户
	void addUser(__int64 userId);

    //删除一个用户
	void removeUser(__int64 userid);

    // 在用户之间增加媒体发送关系
	void addMediaRelation(__int64 from, __int64 to, int type);

    // 删除用户之间的媒体发送关系
    void removeMediaRelation(__int64 from, __int64 to, int type);	

private:

    // 获取内部的某个用户，如果没有找到则返回null
    FvsmUser* getUser(__int64 userid);

    // 根据某个用户的媒体转发关系以及媒体类型,转发UDP数据
    void forwardData(FvsmUser* pUser, char* buffer, int nLen, int dataType);
};

