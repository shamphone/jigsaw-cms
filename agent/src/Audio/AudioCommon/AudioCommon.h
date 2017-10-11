#pragma once

namespace AudioCommon
{
    // Sound buffer size
    // G729 use 160 bit as the single buffer size.
    const int SIZE_AUDIO_FRAME = 960;

    // 16 bit PCM mono sound
    const WAVEFORMATEX format = {
	    WAVE_FORMAT_PCM,
	    1,
	    8000,
	    16000,
	    2,
	    16,
	    0
    };
};
