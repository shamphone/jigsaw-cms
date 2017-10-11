#ifndef CONFERENCE_ROLE_H
#define CONFERENCE_ROLE_H

class ConferenceRole
{
private:
	__int64 m_nID;
	string m_strName;
    vector<__int64> m_rights;  //会议中所有的用户身份

public:
	ConferenceRole(__int64 nID, string strName);
	~ConferenceRole();

public:

	__int64 getID() const;
	string getName() const;
	void addRight(__int64 nRightID);
	vector<__int64> getRights();

};

#endif
