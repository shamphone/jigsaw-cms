#pragma once

class Mixer
{

public:

    // Get MixerId from waveIn id, -1 for error
	static int getMixerIdFromWaveInId(int nDeviceId);

    // Get MixerId from waveOut id, -1 for error
	static int getMixerIdFromWaveOutId(int nDeviceId);

    // Print information for all mixers
    static void printInfo();

public:

	Mixer();
	~Mixer();
    bool create(int nDeviceId, HWND hWnd = 0);  // Must called before use
    void destroy();                             // Must called after use

public:

    DWORD getMasterVolume();                  // Get Master Volume 0-65535
    bool setMasterVolume(DWORD dwVolume);     // Set Master Volume, true for success
    DWORD getMicrophoneVolume();              // Get Microphone Volume 0-65535
    bool setMicrophoneVolume(DWORD dwVolume); // Set Microphone Volume, true for success

    bool selectMicrophoneAsWaveInput();       // Select Microphone as wave in
    bool setSpeakerMute(bool flag);           // mute/active the speak
    bool isSpeakerMute();                     // get the speaker mute status
    DWORD getSpeakerMuteControlId();          // get speaker mute control id
    DWORD getSpeakerVolumeControlId();        // get speaker volume control id
    DWORD getMicrophoneVolumeControlId();     // get Microphone volume control id

private:

    HMIXER m_hMixer;

private:

    DWORD getMicrophoneLineId();
    DWORD getVolumeControlValue(DWORD dwControlId);
    bool setVolumeControlValue(DWORD dwControlId, DWORD dwVolume);
};

