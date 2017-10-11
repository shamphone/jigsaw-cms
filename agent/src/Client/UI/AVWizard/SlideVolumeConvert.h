#pragma once

class SlideVolumeConvert
{
public:
    static const int VOLUME_RANGE = 16;
    static const int VOLUME_UNIT = 0x10000 / VOLUME_RANGE;

    static int WaveVolumeToSlidePos(DWORD dwWaveVolume);
    static int SlidePosToWaveVolume(DWORD position);
};

