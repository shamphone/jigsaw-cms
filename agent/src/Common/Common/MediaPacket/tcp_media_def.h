#pragma once

// TCP数据包的数据大小限制
#define MAX_TCP_SIZE 512*1024

//数据包的标志
const int FVS_TCP_USER    = 1; // 当前连接的用户ID
const int FVS_TCP_FILE    = 2; // 文件数据
const int FVS_TCP_DESKTOP = 3; // 桌面控制数据
const int FVS_TCP_WHITEBOARD = 4; // 白板数据

//数据包
struct PACK_TCP
{
	BYTE	flag;          // 数据包种类
	__int64 userId;        // 用户自己的id
//	__int64 confId;		   // 会议id
	DWORD   data_size;     // 后续的实际数据大小
};

