#ifndef SERVICE_H
#define SERVICE_H

class Service
{
private:
	static Service* theAppPointer;

public:
	static Service* getApp()
	{
		return theAppPointer;
	}

public:
	Service()
	{
		theAppPointer = this;
	}
	virtual int start();
	virtual int stop();
};

#endif

