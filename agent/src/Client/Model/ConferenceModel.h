#ifndef CONFERENCE_MODE_H
#define CONFERENCE_MODE_H

#include "ConferenceRole.h"

class ConferenceModel {

private:
    string  m_strName;               //会议模式名称
    __int64 m_nID;                   //会议模式ID
    vector<ConferenceRole> m_roles;  //会议中所有的用户身份

public:

	ConferenceModel(__int64 nID, string strName);
	~ConferenceModel();

public:

	// 获得ID
	__int64 getID();

	// 获得名字
	string getName();

	// 增加用户身份
	void addRole(ConferenceRole& conferenceRole);

	// 根据ID获取用户身份名称
	string getRoleName(__int64 nRoleID);

	// 根据ID获取用户身份
	ConferenceRole* getRole(__int64 nRoleID);
};

#endif
