#pragma once

#include "../../xvid/0.9.1/divx4.h"
 
class CVideoCodecMgr  
{
public:
	virtual  ~CVideoCodecMgr();
	CVideoCodecMgr();
	
public:	
	// ”∆µ±‡Ω‚¬Î
	BOOL		m_bVEncInit;
	BOOL		m_bVDecInit;

	void DestroyDecoderV();
	void DestroyEncoderV();
	void DestroyCodecV();

	
	BOOL InitDecoderV();
	BOOL InitEncoderV(float FrameRate, float BitRate, int nKeyFrameInterval);		

	
    //protected:
	BOOL EncodeVideoData(char* pin,DWORD len,char* pout,DWORD* lenr,BOOL* pKey);
	BOOL DecodeVideoData(char *pin,DWORD len,char* pout,DWORD *lenr,DWORD flag);
	
	void SetEncVideoFormat(int nWidth,int nHeight,int nBit);
	void SetDecVideoFormat(int nWidth,int nHeight,int nBit);

	void *enchandle;		/* enchandle is a void*, written by encore */
	long dechandle;
	static long hisDecHandle;
	
	BITMAPINFO	m_BmpU;
	BITMAPINFO	m_BmpD;
};

