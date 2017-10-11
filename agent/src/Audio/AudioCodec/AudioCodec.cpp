#include "stdafx.h"
#include "AudioCodec.h"
#include "g729a.h"
#include "../AudioCommon/AudioCommon.h"

AudioCodec::AudioCodec()
{
	va_g729a_init_encoder();
	va_g729a_init_decoder();
}

AudioCodec::~AudioCodec()
{
}

BOOL AudioCodec::EncodeAudioData(char *pin, int len, char *pout, int *plen)
{
	if (!pin || len != AudioCommon::SIZE_AUDIO_FRAME || !pout || !plen)
    {
        return FALSE;
    }
	
	va_g729a_encoder((short*)pin,(BYTE*)pout);
	va_g729a_encoder((short*)(pin+160),(BYTE*)pout+10);
	va_g729a_encoder((short*)(pin+320),(BYTE*)pout+20);
	va_g729a_encoder((short*)(pin+480),(BYTE*)pout+30);
	va_g729a_encoder((short*)(pin+640),(BYTE*)pout+40);
	va_g729a_encoder((short*)(pin+800),(BYTE*)pout+50);

    *plen = 60;

	return TRUE;
}

BOOL AudioCodec::DecodeAudioData(char *pin, int len, char *pout, int *plen)
{
	if (!pin || len!=60 || !pout || !plen)
    {
        return FALSE;
    }

	va_g729a_decoder((BYTE*)pin,(short*)(pout),0);
	va_g729a_decoder((BYTE*)pin+10,(short*)(pout+160),0);
	va_g729a_decoder((BYTE*)pin+20,(short*)(pout+320),0);
	va_g729a_decoder((BYTE*)pin+30,(short*)(pout+480),0);
	va_g729a_decoder((BYTE*)pin+40,(short*)(pout+640),0);
	va_g729a_decoder((BYTE*)pin+50,(short*)(pout+800),0);

	*plen=AudioCommon::SIZE_AUDIO_FRAME;
	
    return TRUE;
}
