#include "stdafx.h"
#include "SlideVolumeConvert.h"

int SlideVolumeConvert::WaveVolumeToSlidePos(DWORD dwWaveVolume)
{
    dwWaveVolume &= 0x0000ffff;
    return SlideVolumeConvert::VOLUME_RANGE - 1 - dwWaveVolume/SlideVolumeConvert::VOLUME_UNIT;
}

int SlideVolumeConvert::SlidePosToWaveVolume(DWORD position)
{
    if( SlideVolumeConvert::VOLUME_RANGE - position -1 == 0 )
    {
        return 0;
    }
    else
    {
        return (SlideVolumeConvert::VOLUME_RANGE - position) * SlideVolumeConvert::VOLUME_UNIT -1;
    }
}

