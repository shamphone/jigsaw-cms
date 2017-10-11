#ifndef CONFERENCE_H
#define CONFERENCE_H


#include "model/DBConference.h"

class Conference
{
public:
	enum ConferenceStatus  // 会议状态
	{
		Active = 1,   // 正在召开
		Inactive,      // 尚未召开
		Finished		// 已结束
	};

public:
	Conference(DBConference dbConference);
	~Conference();

public:
	// 对应的树结点
	HTREEITEM m_hItem;
	DBConference m_dbConference;  // 会议数据

private:
	ConferenceStatus m_status;    // 会议状态
	int m_nUserCount;             // 会议中的人数

public:

	// 取得会议状态
	ConferenceStatus getStatus();

	// 设置会议状态
	void setStatus(ConferenceStatus status);
};

#endif
