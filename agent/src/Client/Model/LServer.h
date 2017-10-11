#ifndef LServer_H
#define LServer_H

#include "FLVCC.h"
#include "message/MatrixC/MatrixCLib/MessageTarget.h"
#include "model/DBUser.h"

#define CONTACTDLG_ITEM		1
#define CONFERENCEDLG_ITEM	2
#define SYSMSGDLG_ITEM		3

#define NORMAL_USER							0
#define SYSTEM_MANAGER						1
#define CONFERENCE_MANAGER					2
#define GROUP_MANAGER						3
#define CONFERENCE_AND_GROUP_MANAGER		4

class Contact;
class ContactMgr;
class CMeetingRoomFrame;
class TransModel;
class ConferenceMgr;
class ConferenceModelMgr;
class RunningConference;
class MultiConferenceMatrixC;
class CMainFrame;
class CooperationManager;

//代表将要注册的会议服务器
class LServer : public LyvcMessage::MessageTarget {

public:
	LServer(CooperationManager* pCooperManager, CMainFrame* pMainFrame,	string sServerName, string IP, int port);
	~LServer();
	bool create();
	void destroy();

private:
	// 与Server对应的界面CMainFrame指针,需要界面处理的事件调用CMainFrame提供的方法
	CMainFrame*   m_pMainFrame;
    // 视音频资源请求对象
	CooperationManager* m_pCooperManager;
	// 服务器名称
	string  m_sServerName;
	// 服务器地址，端口
	string	m_sIP;
	int		m_nPort;
	// 录时使用的帐户和密码
	string	m_sUserName;
	string	m_sPassword;
	// 服务器状态
	UINT	m_nStatus;	
	// 是否是默认服务器
	BOOL	m_bDefaultServer;
	// 是否记住登录的用户和密码
	BOOL	m_bRememberUserPass;
	// 在界面对应的项
	HTREEITEM m_htiContact, m_htiConference, m_htiSysmsg;
	// 标志id
	UINT m_nServerId;
	// 会议管理服务器网址
	string m_sDeleteConferenceURL;
	string m_sCreateConferenceNoticeURL;
	string m_sCreateBulletinURL;
	string m_sEditConferenceURL;
	string m_sConferenceModeMgrURL;
	string m_sSystemRoleMgrURL;
	string m_sCommonContactMgrURL;
	string m_sCreateFromalConferenceURL;
	string m_sSelfInfoMgrURL;
	// 用户注册网址
	string m_sUserRegisterURL;
	// 客户端下载网址
	string m_sClientDownloadURL;

	__int64 m_expertId;
	__int64 m_confIdFromWeb;

public:
	void setTreeItem( HTREEITEM hItem, int nType );
	HTREEITEM getTreeItem( int nType );
	CMainFrame* getMainFrame() { return m_pMainFrame; };
	void setServerName( string sName ) { m_sServerName = sName; };
	string getServerName() { return m_sServerName; };
	void setIP( string sIP ) { m_sIP = sIP; };
	string getIP() { return m_sIP; };
	void setPort( int port ) { m_nPort = port; };
	int getPort() { return m_nPort; };
	void setStatus( UINT nStatus ) { m_nStatus = nStatus; };
	UINT getStatus() { return m_nStatus; };
	void setUserName( string sName ) { m_sUserName = sName; };
	string getUserName()const{return m_sUserName;};
	void setPassword( string sPassword ) { m_sPassword = sPassword; };
	string getPassword()const{return m_sPassword;};
	void setDefaultServer( BOOL bDefault = TRUE ) { m_bDefaultServer = bDefault; };
	BOOL isDefaultServer() { return m_bDefaultServer; };
	void setRememberUserPass( BOOL bFlag = TRUE ) { m_bRememberUserPass = bFlag; };
	BOOL isRememberUserPass() { return m_bRememberUserPass; };
	UINT getServerId() { return m_nServerId; };
	string getDeleteConferenceURL() { return m_sDeleteConferenceURL; };
	string getCreateConferenceNoticeURL() { return m_sCreateConferenceNoticeURL; };
	string getCreateBulletinURL() { return m_sCreateBulletinURL; };
	string getEditConferenceURL() { return m_sEditConferenceURL; };
	string getConferenceModeMgrURL() { return m_sConferenceModeMgrURL; };
	string getSystemRoleMgrURL() { return m_sSystemRoleMgrURL; };
	string getCommonContactMgrURL() { return m_sCommonContactMgrURL; };
	string getCreateFromalConferenceURL() { return m_sCreateFromalConferenceURL; };
	string getSelfInfoMgrURL() { return m_sSelfInfoMgrURL; };
	string getUserRegisterURL() { return m_sUserRegisterURL; };
	string getClientDownloadURL() { return m_sClientDownloadURL; };
	int getUserRole() { return m_selfInfo.userRole; };
	void setExpertId( __int64 id ) { m_expertId = id; };
	__int64 getExpertId() { return m_expertId; };
	void setConfIdFromWeb( __int64 id ) { m_confIdFromWeb = id; };
	__int64 getConfIdFromWeb() { return m_confIdFromWeb; };

protected:
	/*
	 * 用户个人信息
	 */
   	DBUser m_selfInfo;

	// 设置个人信息
	void setSelfInfo(const DBUser& dbUser) {m_selfInfo = dbUser;};

public:
	// 获取个人信息
	DBUser getSelfInfo()const {return m_selfInfo;};

	// 获得ID
	USERID getId() const {return m_selfInfo.id;};

	// 获得Email
    string getEmail()const {return m_selfInfo.strEmail;};

	// 获得全名
    string getRealName()const {return m_selfInfo.getRealName();};

	//用户登录命令
	void cmdLogin();

	void cmdKeepAlive();

	string getRemoteIP();

	// 修改个人信息
	void cmdChangeUserInfo( DBUser userInfo, string password );

protected:
	// 检查版本
    void cmdCheckVersion();

	// 获取媒体服务器地址
	void cmdGetMediaServerAddr() const;	

protected:

	// 会议模式管理器
    ConferenceModelMgr* m_pConferenceModeMgr;

public:

	ConferenceModelMgr* getConferenceModeMgr() const
	{
		return m_pConferenceModeMgr;
	}

protected:

	// 会议管理器
	ConferenceMgr* m_pConferenceMgr;

public:

    ConferenceMgr* getConferenceMgr() const
	{
		return m_pConferenceMgr;
	};

private:

	// 媒体信息传输中心
	TransModel*		m_pTransModel;

public:

    TransModel* getTransModel() const
	{
		return m_pTransModel;
	};

private:

	// 联系人管理器
    ContactMgr*			m_pContactMgr;

public:

    // 获取联系人管理器
    ContactMgr* getContactMgr() const
	{
	    return this->m_pContactMgr;
	};

public:
    // MessageHandler
    void UserLoginResponse(LyvcMessage::BaseMessage* pMessage);
    void UserDetail(LyvcMessage::BaseMessage* pMessage);
    void MediaServerAddress(LyvcMessage::BaseMessage* pMessage);
    void ServerInternalError(LyvcMessage::BaseMessage* pMessage);
	void CheckVersion(LyvcMessage::BaseMessage* pMessage);
	void Advertizement(LyvcMessage::BaseMessage* pMessage);
	void ServerInfo(LyvcMessage::BaseMessage* pMessage);
	void KickDuplicateLogin(LyvcMessage::BaseMessage* pMessage);
	void LoginSuccessfully(LyvcMessage::BaseMessage* pMessage);
	void KeepAlive(LyvcMessage::BaseMessage* pMessage);
    // Exception Hanlder
    void ExceptionHandler(LyvcMessage::BaseMessage* pMessage);

};

#endif //LSERVER_H
