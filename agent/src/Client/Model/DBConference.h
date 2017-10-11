#ifndef DBCONFERENCE_H
#define DBCONFERENCE_H

//反映存放在数据库中，客户端需要使用的属性
class DBConference {
public:    

	__int64			m_id;               //会议的ID
	string			m_sName;            //会议名称
	string			m_sDescription;     //会议描述
	CTime			m_tmStartTime;      //会议开始时间
	CTime			m_tmEndTime;        //会议结束时间
	__int64         m_modelId;          //会议模式ID
	__int64         m_creatorId;        //会议创建者ID
	string			m_sConfFileDesc;    //会议资料描述
	string			m_sConfFileURL;     //会议资料地址

	// operator.
	BOOL operator == (const DBConference& a)
	{
		return this->m_id == a.m_id;
	}
};

#endif
