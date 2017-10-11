// TestAudioPlayback.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "..\AudioCommon\AudioCommon.h"
#include "..\AudioCapture\AudioCaptureMgr.h"
#include "..\AudioPlayback\AudioPlayback.h"


void playData(void* pObject, WAVEHDR* pWH)
{
    AudioPlayback* playback = (AudioPlayback*)pObject;
    playback->play(pWH->lpData, pWH->dwBytesRecorded);
    return;
}

int _tmain(int argc, _TCHAR* argv[])
{
    printf("Begin to record and play, press Enter to stop it...\n");

    AudioPlayback playback;
    playback.create();

    AudioCaptureMgr::PIO_CALLBACK pCallback = playData;
	if( !AudioCaptureMgr::createFromFile(pCallback, &playback, "d:\\lyvc\\src\\Client\\AVClips\\example.wav"))
	{
		printf("AudioCaputreMgr::createFromFile failed.\n");
		return -1;
	}
//	AudioCaptureMgr::create(pCallback, &playback);
//	{
//		printf("AudioCaputreMgr::create failed.\n");
//		return -1;
//	}

    AudioCaptureMgr::startCapture();
    ::getchar();
    AudioCaptureMgr::stopCapture();

    playback.destroy();
    AudioCaptureMgr::destroy();

	return 0;
}
