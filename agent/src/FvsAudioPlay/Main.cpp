#include "stdafx.h"
#include "FileMappingFlag.h"
#include "common/common/Log/Log.h"
#include "common/common/MediaPacket/udp_media_def.h"
#include "Audio/AudioCommon/AudioCommon.h"
#include "Audio/AudioPlayback/AudioPlayback.h"
#include "Audio/AudioCodec/AudioCodec.h"

void openLog()
{
	char filename[MAX_PATH];
	::ZeroMemory(filename, MAX_PATH);
	::GetModuleFileName(NULL, filename, MAX_PATH);
    char* appNameEnd = ::strrchr(filename, '.');
    *appNameEnd = 0;

    char logFileName[1024];
    _snprintf(logFileName, 1024, "%s_%d.log", filename, time(NULL));
	Log::open(logFileName);
}

int APIENTRY WinMain(HINSTANCE hInstance,
                     HINSTANCE hPrevInstance,
                     LPSTR     lpCmdLine,
                     int       nCmdShow)
{
    // Open log
#ifdef _DEBUG
    openLog();
    FVS_DEBUG1("%s", lpCmdLine);
#endif

    if( __argc < 6 )
    {
        FVS_DEBUG("Insufficent parameters. Program exit.");
        Log::close();
        return -1;
    }

	// Enhance local thread priority
	::SetThreadPriority( GetCurrentThread(), THREAD_PRIORITY_ABOVE_NORMAL);

    // 打开文件映像
	HANDLE hFileMapT = OpenFileMapping(
            FILE_MAP_READ | FILE_MAP_WRITE,
            FALSE,
            __argv[1]);
	if (hFileMapT == NULL)
    {
        FVS_DEBUG("Can't open file mapping object");
        Log::close();
		return -1;		 
    }

	PVOID pView = MapViewOfFile(hFileMapT, FILE_MAP_READ | FILE_MAP_WRITE, 0, 0, 0);
	if (pView == NULL)
	{
        FVS_DEBUG("Can't map view for file mapping object");
		CloseHandle(hFileMapT);
        Log::close();
		return -1;
	}
    
    DWORD* pFlag      = (DWORD*)pView;
	PACK_AUDIO *pPack = (PACK_AUDIO *)(((char*)pView) + sizeof(DWORD));

	//打开事件对象
	HANDLE hCounterIn = OpenEvent(
            EVENT_ALL_ACCESS,
            true,
            __argv[2]);
	if (hCounterIn == NULL)
	{
        FVS_DEBUG("Can't open event object");
        UnmapViewOfFile(pView);
        ::CloseHandle(hFileMapT);
        Log::close();
		return -1;
	}

    // 分配数据缓冲区
    int bufferSize = atoi(__argv[3]);
    char* pSrcBuffer = new char[bufferSize];

    // 打开父进程句柄
    HANDLE hParentProcess = ::OpenProcess(
        SYNCHRONIZE,
        FALSE,
        atoi(__argv[4]));
    if( hParentProcess == NULL ) {
        FVS_DEBUG("Can't open parent process");
        delete[] pSrcBuffer;
        UnmapViewOfFile(pView);
        ::CloseHandle(hFileMapT);
        Log::close();
        return -1;
    }
    HANDLE h[2];
    h[0] = hParentProcess;
    h[1] = hCounterIn;

    // 初始化解码器
    AudioCodec codec;

    // 生成声音播放对象
    AudioPlayback playback;
    playback.create(atoi(__argv[5]));

	while(1)
	{
    	int ret = WaitForMultipleObjects(2, h, FALSE, INFINITE);

		if (ret == WAIT_ABANDONED || ret == WAIT_FAILED )
        {
            FVS_DEBUG("WaitForMultipleObject failed: ABANDONED or FAILED");
			break;
        }

        // Parent process terminated.
        if( ret == WAIT_OBJECT_0 + 0 )
        {
            FVS_DEBUG("Parent proecess exit prematurly.");
            break;
        }

		ResetEvent(hCounterIn);

		if (*pFlag == FileMappingFlag::STOP)
		{
            FVS_DEBUG("Parent proecess wants us to quit, do it.");
			break;
		}

		else if (*pFlag == FileMappingFlag::NEW_DATA)
		{
		    // 如果当前正在播放的块数超过了限制，抛掉新增加的数据
			if( playback.getPlayingBufferCount() > AudioPlayback::MAX_PLAYING_BUFFER )
			{
				FVS_DEBUG1("Skip an audio pack. Playing buffer count is %d", playback.getPlayingBufferCount());
				continue;
			}

            // Copy the data out of the file mapping in case it is overwritten by main process
            int srcLength = pPack->data_size;
            memcpy(pSrcBuffer, (char*)(pPack) + sizeof(PACK_AUDIO), pPack->data_size);

            // Get decoded data
    		char pAudioBuffer[AudioCommon::SIZE_AUDIO_FRAME];
            int nLen;
	    	BOOL result = codec.DecodeAudioData(
                    pSrcBuffer,
                    srcLength,
                    pAudioBuffer,
                    &nLen);

            // Play it
            if(result)
            {
                playback.play(pAudioBuffer, nLen);
            }
		}

        else
        {
            FVS_DEBUG1("Unknown flag: %d", *pFlag);
            Log::flush();
            _ASSERTE(FALSE);
        }

	}

    // 关闭父进程句柄
    ::CloseHandle(hParentProcess);

    // 释放缓冲区
    delete[] pSrcBuffer;

	//销毁声音播放对象
	playback.destroy();

    // 销毁事件对象
	CloseHandle(hCounterIn);

    // 取消文件映射
	UnmapViewOfFile(pView);
	CloseHandle(hFileMapT);
	
    // 关闭Log
#ifdef _DEBUG
    Log::close();
#endif

	return 0;
}

