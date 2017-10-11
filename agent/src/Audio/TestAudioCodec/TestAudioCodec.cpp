// TestAudioCodec.cpp : 定义控制台应用程序的入口点。
//

#include "stdafx.h"

#include "..\AudioCommon\AudioCommon.h"
#include "..\AudioCapture\AudioCaptureMgr.h"
#include "..\AudioPlayback\AudioPlayback.h"
#include "..\AudioCodec\AudioCodec.h"

AudioCodec codec;

void playData(void* pObject, WAVEHDR* pWH)
{
    AudioPlayback* playback = (AudioPlayback*)pObject;

    char encoded_data[AudioCommon::SIZE_AUDIO_FRAME];
    int n_enc_len;

    char decoded_data[AudioCommon::SIZE_AUDIO_FRAME]; 
    int n_dec_len;

    codec.EncodeAudioData(pWH->lpData, pWH->dwBytesRecorded, encoded_data, &n_enc_len);
    codec.DecodeAudioData(encoded_data, n_enc_len, decoded_data, &n_dec_len);

    playback->play(decoded_data, n_dec_len);
    return;
}

int _tmain(int argc, _TCHAR* argv[])
{
    printf("Begin to record and play, press Enter to stop it...\n");

    AudioPlayback playback;
    playback.create();

    AudioCaptureMgr::PIO_CALLBACK pCallback = playData;
    AudioCaptureMgr::create(pCallback, &playback);

    AudioCaptureMgr::startCapture();
    getchar();
    AudioCaptureMgr::stopCapture();

    playback.destroy();
    AudioCaptureMgr::destroy();

	return 0;
}
