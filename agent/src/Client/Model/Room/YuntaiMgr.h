#pragma once

#include "..\..\..\message\matrixc\matrixclib\messagetarget.h"
#include "..\..\..\Common\Common\Yuntai\cnComm.h"

class CMeetingRoomFrame;
class RunningConference;

class YuntaiMgr : public LyvcMessage::MessageTarget
{
public:
	YuntaiMgr(LyvcMessage::MatrixC* pMatrixC, CMeetingRoomFrame* pRoom, RunningConference* pConferenc);
	~YuntaiMgr(void);

	bool create();
	void destroy();
	bool m_bIfHasYuntai;

public:
	void moveUp(int nAddress, int commPort);
	void moveDown(int nAddress, int commPort);
	void moveLeft(int nAddress, int commPort);
	void moveRight(int nAddress, int commPort);
	void zoomIn(int nAddress, int commPort);
	void zoomOut(int nAddress, int commPort);

	void stopAction(int nAddress, int commPort);

//消息处理
public:
    void JoinConferenceResponse(LyvcMessage::BaseMessage* pBaseMessage);

private:
	void getCommand(int nAddress, const unsigned char* command, unsigned char* buffer, int nLen);
    int getCommPorts();

private:
	CMeetingRoomFrame* m_pRoom;
	RunningConference* m_pConference;
	cnComm m_cnComm[4];

};
