#ifndef CONFERENCE_MODE_MGR_H
#define CONFERENCE_MODE_MGR_H

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

class ConferenceModel;

class ConferenceModelMgr : public LyvcMessage::MessageTarget {

private:
	typedef std::map<__int64, ConferenceModel*> ConferenceModelMap;
	ConferenceModelMap m_modes;  // 服务器所有的会议模式列表

	// 即时会议的模式ID
	__int64 m_instantConferenceModelId;

public:
	ConferenceModelMgr( LyvcMessage::MatrixC* pMatrixC );
	~ConferenceModelMgr();
	bool create() { return true; };
	void destroy() { };

public:

	__int64 getInstantConferenceModelId();
	void setInstantConferenceModelId(__int64 id);

public:

	// 增加一种会议模式
	void addConferenceMode(__int64 nID, string strName);

	// 给特定的会议模式增加一种用户身份
	void addConferenceModeRole(__int64 nModeID, __int64 nRoleID, string strName, string rights);

	// 根据ID取得会议模式名称
	string getConferenceModeNameByID(__int64 nID);

	// 根据会议模式ID，用户身份ID取得用户身份名称
	string getConferenceRoleNameByID(__int64 nConferenceModeID, __int64 nRoleID);

	ConferenceModel* getConferenceModeByID(__int64 nID);

private:
	//获取会议模式列表
	void cmdGetConferenceModeTable() const;

	//获取某个会议模式的用户身份列表
	void cmdGetConferenceModeRoleTable(__int64 nModeID) const;

public:
    void UserDetail(LyvcMessage::BaseMessage* pMessage);
    void ConferenceModelTableItem(LyvcMessage::BaseMessage* pMessage);
    void ConferenceModelRoleTableItem(LyvcMessage::BaseMessage* pMessage);
	void InstantConferenceModelId(LyvcMessage::BaseMessage* pMessage);

};

#endif
