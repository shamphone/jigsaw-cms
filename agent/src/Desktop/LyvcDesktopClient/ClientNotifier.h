#pragma once

class ClientNotifier
{
public:
	virtual void clientClosed(__int64 uid) = 0;
};
