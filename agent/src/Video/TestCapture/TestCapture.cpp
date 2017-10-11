// TestCapture.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "../VideoConfig/VideoConfig.h"
#include "../VideoCapture/VideoCaptureMgr.h"

DWORD startTime;
DWORD endTime;

void Callback(void* pObject, LPVIDEOHDR lpVHdr)
{
	static int i = 0;
	i++;
	if( i == 1 )
	{
		startTime = ::GetTickCount();
	}
	if( i == 100 )
	{
		endTime = ::GetTickCount();
		printf("Time is %d ms for 100 frames\n", endTime-startTime);
		printf("Acctually frame rate is %d\n", 100*1000/(endTime-startTime));
	}
	// printf("Object = %p, Data Length = %d\n", pObject, lpVHdr->dwBufferLength);
}

int _tmain(int argc, _TCHAR* argv[])
{
	int framerate;
	printf("Enter the framerate:");
	scanf("%d", &framerate);
	VideoConfig::setFrameRate(framerate);

	// Device capture
	if( !VideoCaptureMgr::create(Callback, (void*)1, NULL))
	{
		printf("Failed to create device.\n");
		return -1;
	}
	printf("Create device successfully.\n");

	// File capture
	//VideoCaptureMgr::createFromFile(Callback, (void*)2, "video.dat");
	
	printf("Press Enter to start capture...");
	::getchar();
	VideoCaptureMgr::startCapture();

	printf("Press Enter to stop capture...");
	::getchar();
	VideoCaptureMgr::stopCapture();

	VideoCaptureMgr::destroy();
    return 0;
}