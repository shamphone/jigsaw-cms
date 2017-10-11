#ifndef CONFERENCE_USER_H
#define CONFERENCE_USER_H

#include "model/DBUser.h"

class RunningConference;


class ConferenceUser {

protected:
	DBUser  m_baseInfo;                  // 用户基本信息
	__int64 m_nRoleID;                   // 用户在会议中的角色ID
	int m_nUserType;					 // 用户类型
	int m_nUserChannelNum;
	LONG m_nUserChannel;

public:

	// 用户类型
	static const int NormalUser = 0;
	static const int LocalVirtualUser = 1;
	static const int RemoteVirtualUser = 2;


    // 取得用户ID
	USERID getId()const {return m_baseInfo.id;};	

    // 取得用户帐户名称 
	string getName()const {return m_baseInfo.strName;};

    // 取得用户真实姓名
	string getRealName(){return m_baseInfo.getRealName();};
	
    // 取得用户Email地址
	string getEmail()const {return m_baseInfo.strEmail;};

    // 取得/设置用户角色ID
    __int64	getRoleID()const { return m_nRoleID;};
    void setRoleId(__int64 nRoleId) { m_nRoleID = nRoleId;};
	
	// 取得/设置用户类型
	int getUserType()const {return m_nUserType;};
	void setUserType(int nUserType) { m_nUserType = nUserType;};

	int getUserChannelNum()const {return m_nUserChannelNum;};
	void setUserChannelNum(int userChannelNum) { m_nUserChannelNum = userChannelNum;};

	LONG getUserChannel()const {return m_nUserChannel;};
	void setUserChannel(LONG userChannel) { m_nUserChannel = userChannel;};

public:
	ConferenceUser(const DBUser& s);
    ~ConferenceUser();

	BOOL operator == (const ConferenceUser& r) const
	{
		return m_baseInfo.id == r.getId();
	};

};
#endif //CONFUSER_H
