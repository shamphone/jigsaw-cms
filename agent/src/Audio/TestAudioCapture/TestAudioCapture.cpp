//
// This program record the microphone to example.wav
//
#include "stdafx.h"
#include "..\AudioCommon\AudioCommon.h"
#include "..\AudioCapture\AudioCaptureMgr.h"

const char* filedata = "filedata.dat";
const char* file = "example.wav";
static DWORD dwDataSize = 0;

void writeFileData(void* pObject, WAVEHDR* pWH)
{
    FILE* fp = (FILE*)pObject;
    fwrite(pWH->lpData, pWH->dwBytesRecorded, 1, fp);
    dwDataSize += pWH->dwBytesRecorded;
    return;
}

void writeFile()
{
    FILE* fout = fopen(file, "wb");

    fwrite("RIFF", 4, 1, fout);

    DWORD dwTotalSize = dwDataSize + 36;
    fwrite(&dwTotalSize, sizeof(dwTotalSize), 1, fout);

    fwrite("WAVEfmt ", 8, 1, fout);

    DWORD dwFmtSize = 16L;
    fwrite(&dwFmtSize, sizeof(dwFmtSize), 1, fout);

    WAVEFORMATEX m_pcmWaveFormat = AudioCommon::format;
	fwrite(&m_pcmWaveFormat.wFormatTag, sizeof(m_pcmWaveFormat.wFormatTag), 1, fout) ;
	fwrite(&m_pcmWaveFormat.nChannels, sizeof(m_pcmWaveFormat.nChannels), 1, fout) ;
	fwrite(&m_pcmWaveFormat.nSamplesPerSec, sizeof(m_pcmWaveFormat.nSamplesPerSec), 1, fout) ;
	fwrite(&m_pcmWaveFormat.nAvgBytesPerSec, sizeof(m_pcmWaveFormat.nAvgBytesPerSec), 1, fout) ;
	fwrite(&m_pcmWaveFormat.nBlockAlign, sizeof(m_pcmWaveFormat.nBlockAlign), 1, fout) ;
	fwrite(&m_pcmWaveFormat.wBitsPerSample, sizeof(m_pcmWaveFormat.wBitsPerSample), 1, fout) ;

    fwrite("data", 4, 1, fout);
    fwrite(&dwDataSize, sizeof(dwDataSize), 1, fout);
    
    FILE* fin = fopen(filedata, "rb");
	int ch;
    while( (ch = fgetc(fin)) != EOF)
    {
        fputc(ch, fout);
    }

    fclose(fin);
    fclose(fout);
}

int _tmain(int argc, _TCHAR* argv[])
{
    FILE* fp = fopen(filedata, "wb");

    AudioCaptureMgr::PIO_CALLBACK pCallback = writeFileData;
    AudioCaptureMgr::create(pCallback, fp, 1);

	printf("Press Enter to start capture...\n");
	::getchar();
    AudioCaptureMgr::startCapture();

	printf("Begin to record, press Enter to stop it...\n");
    ::getchar();
    AudioCaptureMgr::stopCapture();

    fclose(fp);

    writeFile();
    unlink(filedata);

    AudioCaptureMgr::destroy();

    printf("Audio data was stored in file %s\n", file);
	return 0;
}

