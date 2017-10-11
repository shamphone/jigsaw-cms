#pragma once

class TCPUser;
class AcceptThread;

class TCPForward
{
public:

    TCPForward();
    ~TCPForward();

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

    // 在用户之间增加桌面数据发送关系
	void addDesktopRelation(__int64 from, __int64 to);

    // 删除用户之间的桌面数据发送关系
    void removeDesktopRelation(__int64 from, __int64 to);

    // 在用户之间增加白板数据发送关系
	void addWhiteboardRelation(__int64 userId, __int64 confId);

    // 删除用户之间的白板数据发送关系
    void removeWhiteboardRelation(__int64 userId, __int64 confId);

private:

    // Accept connection thread
    AcceptThread* m_pAcceptThread;

    // Route table
	CRITICAL_SECTION m_cs;

	// User id and socket map.
	typedef std::map<__int64, TCPUser*> TCPUserMap;
	TCPUserMap m_users;

	// User id and all peer who share his desktop.
	typedef std::map<__int64, list<__int64>*> TCPUserRelationMap;
	TCPUserRelationMap m_desktopRelations;
	TCPUserRelationMap m_whiteboardRelations;

	friend AcceptThread;
	friend TCPUser;
};
