#pragma once

///////////////////////////////////
// 声音播放进程的通讯标志
///////////////////////////////////

class FileMappingFlag
{
public:

    // 新数据到达
    static const DWORD NEW_DATA       = 0;

    // 要求停止播放程序
    static const DWORD STOP           = 1;

};

