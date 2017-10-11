#pragma once


class ClientNotifier;

namespace LyvcMessage
{
	class MatrixC;
};

class DesktopClient
{
public:
	virtual ~DesktopClient(void);

public:
	static DesktopClient* getInstance();

	virtual void start(LyvcMessage::MatrixC* pMatrixC) = 0;
	virtual void stop() = 0;

	virtual void addClient(__int64 uid, std::string name, ClientNotifier* pClientNotifier) = 0;
	virtual void addNotifier(__int64 uid, ClientNotifier* notifier) = 0;
	virtual void remClient(__int64 uid) = 0;
	virtual void startControl(__int64 uid) = 0;
	virtual void stopControl(__int64 uid) = 0;

	// 是否在观看某个用户的桌面
	virtual bool isWatchingUser(__int64 uid) = 0;

	// 接收对方传递过来的桌面信号数据
	virtual void receiveDesktopPacket(char* buffer, int len, __int64 uid) = 0;

	// 找出当前观看的桌面中最上层的那个
	virtual __int64 getTopDesktopClientId() = 0;


protected:
	DesktopClient(void);
};
