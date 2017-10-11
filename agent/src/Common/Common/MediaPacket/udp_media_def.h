#pragma once

// UDP数据包的数据大小限制
#define MAX_UDP_SIZE 60000

//数据包的标志
const int	FVS_MSG_AUDIO					= 1; // 声音数据包 
const int	FVS_MSG_VIDEO					= 2; // 图像数据包
const int	FVS_MSG_COMPRESSION_VIDEO       = 3; // 图像数据包
const int	FVS_MSG_ADDR					= 4; // 刷新UDP地址的数据包

//音频数据包（不包含音频数据）
struct PACK_AUDIO
{
	BYTE	flag;
	__int64	userId;	
	DWORD	no;			//序号，
	WORD	data_size;
};

//视频数据包（不包含视频数据,原因是数据的长度是变化的）
struct PACK_VIDEO
{
	BYTE	flag;	
	__int64	userId;	
	DWORD	no;			//序号
	BYTE	format;		//视频的格式
	WORD	width;
	WORD	height;
	WORD	band;		//带宽kb
	BYTE	frameRate;	//帧率
	BYTE	key;		//是否关键帧
	WORD	key_interval;//关键帧间隔
	DWORD	data_size;	//字节数
};

// 地址数据包
struct PACK_ADDR
{
	BYTE	flag;
	__int64	userId;	
};
