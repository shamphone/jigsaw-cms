#ifndef CONFERENCE_MGR_H
#define CONFERENCE_MGR_H

#include "message/MatrixC/MatrixCLib/MessageTarget.h"

class LServer;
class Conference;
class RunningConference;
class MultiConferenceMatrixC;
class CMeetingRoomFrame;

class ConferenceMgr : public LyvcMessage::MessageTarget
{
public:
	ConferenceMgr( LyvcMessage::MatrixC* pMatrixC, LServer* pServer );
	~ConferenceMgr();
	bool create() { return true; };
	void destroy() { };

	// 根据树结点返回会议指针
	Conference* getConferenceByItem( HTREEITEM hItem );

	// 根据会议名称返回会议指针
	Conference* getConferenceByName( string sConfName );

private:
	LServer* m_pServer;

	/**
	* 正式会议列表
	*/
	typedef std::map<__int64, Conference*> ConferenceMap;
	ConferenceMap m_conferences;

	// 增加会议
	void addConference(Conference* pConference);

	// 删除会议
	void deleteConference(__int64 conferenceID);

	// 根据会议ID取得会议指针，如果不存在，返回NULL
	Conference* getConferenceByID(__int64 conferenceID);

private:

	/*
	 * 正在进行中的会议列表
	 */
	typedef std::map<__int64, RunningConference*> RunningConferenceMap;
	RunningConferenceMap m_runningConferences;

    // 针对每一个运行的会议，都会有一个包装过的MatrixC
    // 针对会议信息进行分流
    typedef map<__int64, MultiConferenceMatrixC*> MultiConferenceMatrixCMap;
    MultiConferenceMatrixCMap m_MultiConferenceMatrixCMap;

	// 增加正在进行的会议
	void addRunningConference(RunningConference* pRunningConference);

	// 删除正在进行的会议
	void deleteRunningConference(__int64 conferenceID);

	//根据ID获取正在进行的会议指针
	RunningConference* getRunningConferenceByID(__int64 conferenceID);

public:

	//会议是否正在进行
	BOOL isConferenceGoing(__int64 conferenceId);

	// 停止所有正在运行中的会议
	void stopAllRunningConference();

	// 创建一个运行会议对象
	RunningConference* createRunningConference(
		Conference* pConference,                // 会议
		CMeetingRoomFrame* pMeetingRoomFrame);  // 会议的界面

	// 删除运行会议对象，传递会议的ID
	void destroyRunningConference(__int64 conferenceId);

private:
	//获取会议列表信息
	void cmdGetConferenceList() const;

public:
	// 发送创建临时会议
    void cmdCreateInstantConference(vector<__int64> participants);

public:
    void MediaServerAddress(LyvcMessage::BaseMessage* pMessage);
    void AddConference(LyvcMessage::BaseMessage* pMessage);
	void ConferenceFinish(LyvcMessage::BaseMessage* pMessage);
	void DeleteConference(LyvcMessage::BaseMessage* pMessage);
	void ModifyConference(LyvcMessage::BaseMessage* pMessage);
    void StartConference(LyvcMessage::BaseMessage* pMessage);
	void InviteConference(LyvcMessage::BaseMessage* pMessage);
	void KickUserFromConference(LyvcMessage::BaseMessage* pMessage);
	void DeleteConferenceUser(LyvcMessage::BaseMessage* pMessage);

};


#endif
