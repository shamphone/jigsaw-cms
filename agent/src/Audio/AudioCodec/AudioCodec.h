#pragma once

/*
 *    This class wrapper the VoiceAge Open G729 package.
 * It handles the 6 frames once, each frame, original size is 160 bytes
 * and encoded size is 10 bytes.
 */

class AudioCodec  
{
public:
	AudioCodec();
	~AudioCodec();

public:	

	BOOL DecodeAudioData(char *pin,int len,char* pout,int* lenr);
	BOOL EncodeAudioData(char *pin,int len,char* pout,int* lenr);
};
