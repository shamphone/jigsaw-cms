/*------------------------------------------------------------------------------*\
 =============================
   Ä£¿éÃû³Æ: AudioPlay.cpp
 ============================= 
\*------------------------------------------------------------------------------*/
#include "stdafx.h"
#include "WaveOut.h"
#include "AudioPlay.h"


CAudioPlay::CAudioPlay()
{

}
//Alloc new buf,and delete it when done
BOOL CAudioPlay::Play(char *buf, UINT uSize)
{

	BOOL bRet=FALSE;
	char* p;
	LPWAVEHDR pwh=new WAVEHDR;
	if(!pwh)
		goto RET;
	p=new char[uSize];
	if(!p)
		goto RET;
	CopyMemory(p,buf,uSize);
	ZeroMemory(pwh,sizeof(WAVEHDR));
	pwh->dwBufferLength=uSize;
	pwh->lpData=p;
	m_mmr=waveOutPrepareHeader(m_hOut,pwh,sizeof(WAVEHDR));
    if(m_mmr)
		goto RET;
	m_mmr=waveOutWrite(m_hOut,pwh,sizeof(WAVEHDR));
	if(m_mmr)
		goto RET;
	bRet=TRUE;
	
	
RET:
	return bRet;
}

BOOL CAudioPlay::Create(WAVEFORMATEX *pwf,DWORD dwCallBack,DWORD dwInst,DWORD fdwOpen )
{
	BOOL bRet=FALSE;
	if(m_hOut)
	{
		bRet=TRUE;
		goto RET;
	}
	
	if(!OpenDev(pwf,dwCallBack,dwInst,fdwOpen))
		goto RET;
	bRet=TRUE;
RET:
	return bRet;
}

CAudioPlay::~CAudioPlay()
{
	Destroy();
}

BOOL CAudioPlay::Destroy()
{
	TRACE0("Enter CAudioPlay::Destroy \n");
	BOOL bRet=FALSE;

	TRACE0("Before closeDeve");
	if(!CloseDev())
	{
		TRACE0("CloseDev failed. \n");
		goto RET;
	}

	TRACE0("CloseDev successful. \n");
	bRet=TRUE;
RET:
	TRACE0("Leave CAudioPlay::Destroy \n");
	return bRet;
}
