#ifndef CONFERENCE_USER_MGR_H
#define CONFERENCE_USER_MGR_H

class ConferenceUser;

class ConferenceUserMgr {

private:

    // 会议人员列表
    typedef map<USERID, ConferenceUser*> ConferenceUserMap;
    ConferenceUserMap m_users;

public:
    ConferenceUserMgr();
    ~ConferenceUserMgr();

public:
	unsigned int getCount(){ return m_users.size(); };
	// 所有人员id
	void getConferenceUserIds(vector<__int64>* v, bool bIncludingVirtualUser = false);

	// 指定用户所有虚拟用户id
	static void getVirtualConferenceUserIds(USERID userId, int count, vector<__int64>* v);
	
	// 指定虚拟用户所用channel
	static LONG getVirtualConferenceUserChannel(USERID userId);

	// 根据用户id和channel得到虚拟id
	static USERID getVirtualUserId(USERID userId, LONG handler);

	// 根据虚拟id得到用户id
	static USERID getRealIdByVirtualId(USERID virtualId);

	// 增加人员
    void addConferenceUser(ConferenceUser* pUser);

    // 查找人员
    ConferenceUser* getConferenceUserByID(USERID userId);

    // 判断人员是否在本会议中
    bool isUserInConference(USERID  userId);

    // 删除人员
    void deleteConferenceUser(USERID userId);
};

#endif

