#include "stdafx.h"
#include "..\Mixer\Mixer.h"

void testConvertId()
{
    printf("waveinId=%d, mixerId=%d\n", 0, Mixer::getMixerIdFromWaveInId(0));
    printf("waveinId=%d, mixerId=%d\n", 1, Mixer::getMixerIdFromWaveInId(1));
    printf("waveinId=%d, mixerId=%d\n", 2, Mixer::getMixerIdFromWaveInId(2));
    printf("waveinId=%d, mixerId=%d\n", -1, Mixer::getMixerIdFromWaveInId(-1));

    printf("waveoutId=%d, mixerId=%d\n", 0, Mixer::getMixerIdFromWaveOutId(0));
    printf("waveoutId=%d, mixerId=%d\n", 1, Mixer::getMixerIdFromWaveOutId(1));
    printf("waveoutId=%d, mixerId=%d\n", 2, Mixer::getMixerIdFromWaveOutId(2));
    printf("waveoutId=%d, mixerId=%d\n", -1, Mixer::getMixerIdFromWaveOutId(-1));
}

void testCreateDestroy()
{
    Mixer mixer;
    mixer.create(0);
    mixer.destroy();
    mixer.create(1);
    mixer.destroy();
    mixer.create(2);
    mixer.destroy();
    mixer.create(-1);
    mixer.destroy();
}

void testGetMasterVolume()
{
    Mixer mixer;
    mixer.create(0);
    printf("Mixer 0 Master Speaker Volume %x\n", mixer.getMasterVolume());
    mixer.destroy();

    mixer.create(1);
    printf("Mixer 1 Master Speaker Volume %x\n", mixer.getMasterVolume());
    mixer.destroy();
}

void testSetMasterVolume()
{
    DWORD dwVolume1, dwVolume2;
    printf("Enter Master Speaker Volume for mixer0 and mixer1: ");
    scanf("%d %d", &dwVolume1, &dwVolume2);

    Mixer mixer;
    mixer.create(0);
    mixer.setMasterVolume(dwVolume1);
    mixer.destroy();

    mixer.create(1);
    mixer.setMasterVolume(dwVolume2);
    mixer.destroy();
}

void testGetMicrophoneVolume()
{
    Mixer mixer;
    mixer.create(0);
    printf("Mixer 0 Microphone Volume %x\n", mixer.getMicrophoneVolume());
    mixer.destroy();

    mixer.create(1);
    printf("Mixer 1 Microphone Volume %x\n", mixer.getMicrophoneVolume());
    mixer.destroy();
}

void testSetMicrophoneVolume()
{
    DWORD dwVolume1, dwVolume2;
    printf("Enter Microphone Volume for mixer0 and mixer1: ");
    scanf("%d %d", &dwVolume1, &dwVolume2);

    Mixer mixer;
    mixer.create(0);
    mixer.setMicrophoneVolume(dwVolume1);
    mixer.destroy();

    mixer.create(1);
    mixer.setMicrophoneVolume(dwVolume2);
    mixer.destroy();
}

void testSelectMicrophoneAsWaveInput()
{
    Mixer mixer;
    mixer.create(0);
    if(mixer.selectMicrophoneAsWaveInput())
    {
        printf("Mixer 0 Microphone enabled\n");
    }
    else
    {
        printf("Fail to enable Mixer 0 Microphone.\n");
    }
    mixer.destroy();

    mixer.create(1);
    if(mixer.selectMicrophoneAsWaveInput())
    {
        printf("Mixer 1 Microphone enabled\n");
    }
    else
    {
        printf("Fail to enable Mixer 1 Microphone.\n");
    }
    mixer.destroy();
}

void testMuteSpeaker()
{
    DWORD dwFlag1, dwFlag2;
    printf("Enter Mute Flag for mixer0 and mixer1: ");
    scanf("%d %d", &dwFlag1, &dwFlag2);

    Mixer mixer;
    mixer.create(0);
    mixer.setSpeakerMute(dwFlag1);
    mixer.destroy();

    mixer.create(1);
    mixer.setSpeakerMute(dwFlag2);
    mixer.destroy();
}

void testIsMute()
{
    Mixer mixer;
    mixer.create(0);
    printf("Mixer 0 speak mute: %d\n", mixer.isSpeakerMute());
    mixer.destroy();

    mixer.create(1);
    printf("Mixer 1 speak mute: %d\n", mixer.isSpeakerMute());
    mixer.destroy();
}

int _tmain(int argc, _TCHAR* argv[])
{
    // Test the device id convert functions
    // testConvertId();

    // Test Create/Destory
    // testCreateDestroy();
    
    // Test GetMasterVolume
    // testGetMasterVolume();

    // Test SetMasterVolume
    // testSetMasterVolume();

    // Test GetMicrophoneVolume
    // testGetMicrophoneVolume();

    // Test SetMicrophoneVolume
    // testSetMicrophoneVolume();

    // Test Enable Microphone
    // testSelectMicrophoneAsWaveInput();

    // Test mute speaker
    // testMuteSpeaker();

    // test is mute
    // testIsMute();

    // Print information
    Mixer::printInfo();

    printf("\nPress Enter to close the window...");
    getchar();
    return 0;
}

