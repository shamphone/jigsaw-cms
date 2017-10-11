// FvsAudioCodec.h: interface for the FvsAudioCodec class.
//
//////////////////////////////////////////////////////////////////////

#if !defined(AFX_FVSAUDIOCODEC_H__6C030BCE_58E7_4511_A5CF_D851046CEBAC__INCLUDED_)
#define AFX_FVSAUDIOCODEC_H__6C030BCE_58E7_4511_A5CF_D851046CEBAC__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

class FvsAudioCodec  
{
public:
	FvsAudioCodec();
	~FvsAudioCodec();

public:	

	BOOL DecodeAudioData(char *pin,int len,char* pout,int* lenr);
	BOOL EncodeAudioData(char *pin,int len,char* pout,int* lenr);
};

#endif // !defined(AFX_FVSAUDIOCODEC_H__6C030BCE_58E7_4511_A5CF_D851046CEBAC__INCLUDED_)
