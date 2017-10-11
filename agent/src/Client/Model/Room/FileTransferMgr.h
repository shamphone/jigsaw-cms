#pragma once

#include "message\matrixc\matrixclib\messagetarget.h"

class CMeetingRoomFrame;
class RunningConference;

#define WM_SEND_NEXT_FILE WM_USER + 128

class FileTransferTask
{
public:
	FileTransferTask() { m_bCanSend = false; m_uid = 0; };

	__int64 m_uid; 	       // 发送或接收者id
    string m_fileName; 	   // 发送文件名(包含路径)
	__int64 m_fileLength;  // 文件长度
	bool m_bCanSend;       // 是否可以发送
	string m_storeName;    // 保存路径

	FileTransferTask& operator=(const FileTransferTask& ftt)
	{
		m_uid = ftt.m_uid;
		m_fileName = ftt.m_fileName;
		m_fileLength = ftt.m_fileLength;
		m_bCanSend = ftt.m_bCanSend;
		m_storeName = ftt.m_storeName;
		return *this;
	}
};


class FileTransferMgr :	public LyvcMessage::MessageTarget
{
public:
	FileTransferMgr(LyvcMessage::MatrixC* pMatrixC, CMeetingRoomFrame* pRoom, RunningConference* pConferenc);
	~FileTransferMgr(void);

	bool create();
	void destroy();

	void sendNextFile();
	void recvNextFile();
	void exitSendFileThread();

	//消息发送
	void applySendFile(__int64 receiverId, string fileName, __int64 fileLength);
	void rejectReceiveFile(__int64 receiverId, string fileName);
	void agreeReceiveFile(__int64 receiverId, string fileName, __int64 fileLength);
	void stopSendFile(__int64 receiverId, string fileName);
	void stopReceiveFile(__int64 receiverId, string fileName);
	void requestSendFile(__int64 receiverId, string fileName);

	//消息处理
	void ApplySendFile(LyvcMessage::BaseMessage* pBaseMessage);
	void RejectReceiveFile(LyvcMessage::BaseMessage* pBaseMessage);
	void AgreeReceiveFile(LyvcMessage::BaseMessage* pBaseMessage);
	void StopSendFile(LyvcMessage::BaseMessage* pBaseMessage);
	void StopReceiveFile(LyvcMessage::BaseMessage* pBaseMessage);
	void SendFileData(LyvcMessage::BaseMessage* pBaseMessage);
	void RequestSendFile(LyvcMessage::BaseMessage* pBaseMessage);
	void UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage);
	void UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage);

	// 取得保存在会议中接收的文件的文件夹路径(不包括最后的\符号)
	static const string getIncomeFileSavePosition();

private:
	// 用于发送文件数据的线程
	static UINT WINAPI sendFileThreadProc(LPVOID lpParameter);

private:
	CMeetingRoomFrame* m_pRoom;
	RunningConference* m_pConference;

	//发送队列
	vector<FileTransferTask> m_sendList;
	//接收队列
	vector<FileTransferTask> m_recvList;
	//申请向自己发送文件的队列
	vector<FileTransferTask> m_applyList;

	HANDLE m_hEvent;
	unsigned int m_nThreadId;
	HANDLE m_hSendThread;

	FileTransferTask m_curSendTask;
	FileTransferTask m_curRecvTask;
	bool m_bStopCurrentSend;
	bool m_bIsReceiving;
	bool m_bExitSendThread;
	bool m_bIsSending;
};
