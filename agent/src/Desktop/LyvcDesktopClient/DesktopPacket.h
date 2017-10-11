#pragma once

//桌面共享客户端到服务端数据包的标志
const int DESKTOP_KEYBOARD           = 1; // 键盘数据
const int DESKTOP_MOUSE              = 2; // 鼠标数据

struct DESKTOP_CONTROL_PACKET
{
	BYTE			flag;          // 数据包种类
	//键盘数据
	bool			downFlag;	   
	unsigned int	key;
	//鼠标数据
	BYTE			buttonMask;
	unsigned short	x;
	unsigned short	y;
};
