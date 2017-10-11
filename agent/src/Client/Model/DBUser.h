#ifndef DBUSER_H
#define DBUSER_H

// 用户的基本信息
struct DBUser
{
	__int64 id;         // Num_id
	__int32 userRole;
	string  strName;      // login account name
	string  strFirstName; // first name
	string  strLastName;  // last name
	string  strEmail;     // Email
	__int64 groupId;	//groupId

	DBUser()
	{
		id = 0;
	}

	BOOL operator == (DBUser a)
	{
		return this->id == a.id;
	}

	string getRealName() const
	{
		return strLastName + strFirstName;
	};
};

#endif
