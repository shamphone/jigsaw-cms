// TestAudioPlayback2.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"
#include "TestAudioPlayback2.h"
#ifdef _DEBUG
#define new DEBUG_NEW
#endif

/*
 * 新版的声音播放代码有 dedede 的声音，估计是录音间隔引起的
 * 为了验证这一点，用原来的声音播放代码作了一个exe
 */

// 唯一的应用程序对象

CWinApp theApp;

using namespace std;

#include "..\AudioCapture\AudioCaptureMgr.h"
#include "FvscAudioPlayer.h"


void playData(void* pObject, WAVEHDR* pWH)
{
    FvscAudioPlayer* pPlayer = (FvscAudioPlayer*)pObject;
    pPlayer->playDecodeData(pWH->lpData, pWH->dwBytesRecorded);
    return;
}


int _tmain(int argc, TCHAR* argv[], TCHAR* envp[])
{
	int nRetCode = 0;

	// 初始化 MFC 并在失败时显示错误
	if (!AfxWinInit(::GetModuleHandle(NULL), NULL, ::GetCommandLine(), 0))
	{
		// TODO: 更改错误代码以符合您的需要
		_tprintf(_T("致命错误: MFC 初始化失败\n"));
		nRetCode = 1;
	}
	else
	{
		// TODO: 在此处为应用程序的行为编写代码。
	}

    printf("Begin to record and play, press Enter to stop it...\n");

	FvscAudioPlayer audioPlay;
	audioPlay.init();

    AudioCaptureMgr::PIO_CALLBACK pCallback = playData;
    AudioCaptureMgr::create(pCallback, &audioPlay);

    AudioCaptureMgr::startCapture();
    ::getchar();
    AudioCaptureMgr::stopCapture();

	audioPlay.destroy();

    AudioCaptureMgr::destroy();

	return nRetCode;
}
