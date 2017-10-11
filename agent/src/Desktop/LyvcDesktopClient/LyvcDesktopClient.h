#pragma once
#include "desktopclient.h"
#include "CViewManager.h"

class CMsgReader;
class CMsgHandler;
class CLyvcMsgWriter;
class ClientNotifier;
class LyvcInStream;

class LyvcDesktopClient :
	public DesktopClient
{
public:
	LyvcDesktopClient();
	~LyvcDesktopClient(void);

public:
	virtual void start(LyvcMessage::MatrixC* pMatrixC);
	virtual void stop();

	virtual void addClient(__int64 uid, std::string name, ClientNotifier* pClientNotifier);
	virtual void addNotifier(__int64 uid, ClientNotifier* notifier);
	virtual void remClient(__int64 uid);
	virtual void startControl(__int64 uid);
	virtual void stopControl(__int64 uid);

	virtual bool isWatchingUser(__int64 uid);
	virtual void receiveDesktopPacket(char* buffer, int len, __int64 uid);
	virtual __int64 getTopDesktopClientId();

public:
	CMsgHandler* getMsgHandler(__int64 uid) ;

private:
	rfb::win32::CViewManager vm;
	CMsgReader* m_pMsgReader;
	CLyvcMsgWriter* m_pMsgWriter;
	bool m_bStarted;

	LyvcInStream* m_is;
};
