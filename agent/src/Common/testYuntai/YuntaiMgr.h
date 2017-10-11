#pragma once

#include "..\Common\Yuntai\cnComm.h"

class YuntaiMgr
{
public:
	YuntaiMgr();
	~YuntaiMgr(void);

	bool init();

public:
	void moveUp();
	void moveDown();
	void moveLeft();
	void moveRight();
	void focusIn();
	void focusOut();

	void stopMoveUp();
	void stopMoveDown();
	void stopMoveLeft();
	void stopMoveRight();

private:
	cnComm m_cnComm;
};
