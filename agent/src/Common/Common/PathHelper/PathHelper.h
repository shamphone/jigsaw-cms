#ifndef PATH_HELPER_H
#define PATH_HELPER_H

//
// 处理应用程序的路径部分问题
//
//    这个模块假定应用程序安装之后的文件夹布局如下：
//
//    （应用程序根目录）
//         |
//         |---- (bin)  可执行程序目录
//         |
//         |-----(conf) 公共配置文件目录
//		   |		|
//		   |        |---history.ini 登录记录
//         |
//         |-----(help) 帮助文件目录
//         |
//         |-----(data) 数据文件目录
//                  |
//                  |---(received files) 默认的收到文件保存目录
//                  |
//					|---(登录用户名@服务器地址) 每个服务器上的每个用户一个目录
//							|
//							|---Config.ini 每个用户自己的配置文件(默认服务器才有)
//							|
//							|---Server.ini 每个用户自己的服务器列表(默认服务器才有)
//							|
//							|---leaveword.xml 收到的留言
//							|
//							|---systemmsg.xml 系统公告
//							|
//							|---confnotice.xml 会议通知
//							|
//							|---(conference) 会议中的公共消息保存目录
//							|
//							|---(chat) 与联系人的聊天信息保存目录
//							|
//							|---(userface) 会议室中参会人员的显示图片目录
//							|

class PathHelper
{
public:

    // 取得应用程序根目录路径(不包括最后的\符号)
    static const char* getApplicationRoot();

    // 取得应用程序的名字
    static const char* getExeName();

    // 取得配置文件的名字
    static const char* getIniFileFullName();

    // 取得帮助文件的名字
    static const char* getHelpFileFullName();

	// 取得数据存放根目录(包括最后的\符号)
	static const char* getDataPath();

	// 取得存储留言的文件名
	static const char* getLeavewordFileFullName(const char* userName, const char* server, bool bCreateIfNotExist = false);

	// 取得存储会议通知的文件名
	static const char* getConfNoticeFileFullName(const char* userName, const char* server, bool bCreateIfNotExist = false);

	// 取得存储系统消息的文件名
	static const char* getSystemMessageFileFullName(const char* userName, const char* server, bool bCreateIfNotExist = false);

	// 取得默认的接收文件存放的目录
    static const char* getDefaultReceivedFilePath();

	// 取得聊天信息存放的目录
	static const char* getChatMsgPath( const char* userName, const char* server );

	// 取得会议文字记录存放的目录
	static const char* getConferenceMsgPath( const char* userName, const char* server );

	// 取得用户存放其他人头像的目录
	static const char* getUserFacePath( const char* userName, const char* server );

	// 取得个人配置文件
	static const char* getUserPrivateConfigFile( const char* userName, const char* server );

	// 取得个人的数据存放目录
	static const char* getUserDataPath( const char* userName, const char* server, bool bCreateIfNotExist = true );

	// 取得服务器列表文件
	static const char* getUserServerListFile( const char* userName, const char* server, bool bCreateIfNotExist = true );

	// 判断文件是否存在
	static BOOL isFileExist(const char* fileFullName);

	// 得到登录记录的文件,没有就先创建
	static const char* getLoginHistoryFile();

	// 判断目录是否存在
	static BOOL isDirectoryExist(const char* dir);
private:
	// 创建目录
	static BOOL createDirectory(const char* dir);
	// 创建文件
	static BOOL createFile(const char* fileFullName);

};

#endif
