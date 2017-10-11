#pragma once

class SMsgHandler
{
public:
	//客户端传来的鼠标和键盘事件的处理，实际由子类LyvcDesktopServer来实现
	virtual void setMouse(int mask, int x, int y) = 0;
	virtual void setKey(bool downFlag, int key) = 0;

	//有客户端请求
	virtual void requestUpdate() = 0;
};
