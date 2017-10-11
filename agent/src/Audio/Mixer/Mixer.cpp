#include "stdafx.h"
#include "mixer.h"

int Mixer::getMixerIdFromWaveInId(int nDeviceId)
{
    if( nDeviceId == -1 )
    {
        return 0;
    }

    int nMixerId;
    mixerGetID((HMIXEROBJ)nDeviceId, (UINT*)&nMixerId, MIXER_OBJECTF_WAVEIN);
    return nMixerId;
}

int Mixer::getMixerIdFromWaveOutId(int nDeviceId)
{
    if( nDeviceId == -1 )
    {
        return 0;
    }

    int nMixerId;
    mixerGetID((HMIXEROBJ)nDeviceId, (UINT*)&nMixerId, MIXER_OBJECTF_WAVEOUT);
    return nMixerId;
}

Mixer::Mixer()
{
    m_hMixer = 0;
}

Mixer::~Mixer()
{
    _ASSERTE(m_hMixer == 0);
}

bool Mixer::create(int nDeviceId, HWND hWnd)
{
    MMRESULT mmResult;
    if( hWnd == 0 )
    {
        mmResult = mixerOpen(&m_hMixer, nDeviceId, 0, 0, MIXER_OBJECTF_MIXER);
    }
    else
    {
        mmResult = mixerOpen(&m_hMixer, nDeviceId, (DWORD)hWnd, 0, MIXER_OBJECTF_MIXER | CALLBACK_WINDOW);
    }

    if( mmResult != MMSYSERR_NOERROR)
    {
        m_hMixer = 0;
        return false;
    }

    return true;
}

void Mixer::destroy()
{
    mixerClose(m_hMixer);
    m_hMixer = 0;
}

DWORD Mixer::getMasterVolume()
{
    // Get Speaker Volume Control ID
    DWORD dwControlID = getSpeakerVolumeControlId();
 //   if( dwControlID == 0)
//    {
 //       return 0;
//    }

    // Get control value
    return getVolumeControlValue(dwControlID);
}

bool Mixer::setMasterVolume(DWORD dwVolume)
{
    // Get Speaker Volume Control ID
    DWORD dwControlID = getSpeakerVolumeControlId();
 //   if( dwControlID == 0)
 //   {
 //       return 0;
 //   }

    // Set Control value
    return setVolumeControlValue(dwControlID, dwVolume);
}

DWORD Mixer::getMicrophoneVolume()
{
    // Get Microphone Volume Control ID
    DWORD dwControlID = getMicrophoneVolumeControlId();
//    if( dwControlID == 0)
 //   {
 //       return 0;
//    }

    // Get control value
    return getVolumeControlValue(dwControlID);
}

bool Mixer::setMicrophoneVolume(DWORD dwVolume)
{
    // Get Microphone Volume Control ID
    DWORD dwControlID = getMicrophoneVolumeControlId();
 //   if( dwControlID == 0)
 //   {
 //       return 0;
 //   }

    // Set Control value
    return setVolumeControlValue(dwControlID, dwVolume);
}

bool Mixer::selectMicrophoneAsWaveInput()
{
    // find Microphone Line id
    DWORD dwMicrophoneLineId = getMicrophoneLineId();
    if ( dwMicrophoneLineId == 0 )
    {
        // Oops! We can't find the microphone line
        return false;
    }

    // find the wavin line id
    MIXERLINE MixerLine;
    memset( &MixerLine, 0, sizeof(MIXERLINE) );
    MixerLine.cbStruct = sizeof(MIXERLINE);
    MixerLine.dwComponentType = MIXERLINE_COMPONENTTYPE_DST_WAVEIN;
    if( mixerGetLineInfo(
                (HMIXEROBJ)m_hMixer,
                &MixerLine,
                MIXER_GETLINEINFOF_COMPONENTTYPE ) != MMSYSERR_NOERROR)
    {
        return false;
    }
    DWORD dwWaveinLineId = MixerLine.dwLineID;

    // find the wavein line mux control id
    MIXERCONTROL Control;
    memset( &Control, 0, sizeof(MIXERCONTROL) );
    Control.cbStruct = sizeof(MIXERCONTROL);

    MIXERLINECONTROLS LineControls;
    memset( &LineControls, 0, sizeof(MIXERLINECONTROLS) );
    LineControls.cbStruct = sizeof(MIXERLINECONTROLS);

    LineControls.dwControlType = MIXERCONTROL_CONTROLTYPE_MUX;
    LineControls.dwLineID = dwWaveinLineId;
    LineControls.cControls = 1;
    LineControls.cbmxctrl = sizeof(MIXERCONTROL);
    LineControls.pamxctrl = &Control;
    if( mixerGetLineControls(
                (HMIXEROBJ)m_hMixer,
                &LineControls,
                MIXER_GETLINECONTROLSF_ONEBYTYPE) != MMSYSERR_NOERROR )
    {
        // Oops, can't find the mux control for wave-in line,
        // perhaps we are running on future PC :)
        return false;
    }

    // find the sequence number of microphone line in wavein line mux control
    MIXERCONTROLDETAILS mixerControlDetails;
    memset(&mixerControlDetails, 0, sizeof(MIXERCONTROLDETAILS));
    mixerControlDetails.cbStruct = sizeof(MIXERCONTROLDETAILS);
    mixerControlDetails.dwControlID = Control.dwControlID;
    mixerControlDetails.cChannels = 1;
    mixerControlDetails.cMultipleItems = Control.cMultipleItems;
    mixerControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_LISTTEXT);
    int size = mixerControlDetails.cMultipleItems * mixerControlDetails.cChannels;
    mixerControlDetails.paDetails = malloc(mixerControlDetails.cbDetails * size);

    if( mixerGetControlDetails(
            (HMIXEROBJ)m_hMixer,
            &mixerControlDetails,
            MIXER_GETCONTROLDETAILSF_LISTTEXT ) != MMSYSERR_NOERROR )
    {
        return false;
    }

    MIXERCONTROLDETAILS_LISTTEXT* pListText
        = (MIXERCONTROLDETAILS_LISTTEXT*)mixerControlDetails.paDetails;

    int dwMicrophoneLineNumber = -1;
    for(int j=0; j<size; j++)
    {
        if( pListText[j].dwParam1 == dwMicrophoneLineId )
        {
            dwMicrophoneLineNumber = j;
            break;
        }
    }
    free(mixerControlDetails.paDetails);
    if( dwMicrophoneLineNumber == -1 )
    {
        return false;
    }

    // set mux control value
    mixerControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_BOOLEAN);
    mixerControlDetails.paDetails = malloc(mixerControlDetails.cbDetails * size);
    memset(mixerControlDetails.paDetails, 0, mixerControlDetails.cbDetails * size);
    ((MIXERCONTROLDETAILS_BOOLEAN*)mixerControlDetails.paDetails)[dwMicrophoneLineNumber].fValue = 1;
    if( mixerSetControlDetails(
            (HMIXEROBJ)m_hMixer,
            &mixerControlDetails,
            MIXER_SETCONTROLDETAILSF_VALUE ) != MMSYSERR_NOERROR )
    {
        free(mixerControlDetails.paDetails);
        return false;
    }

    free(mixerControlDetails.paDetails);
    return true;
}

bool Mixer::setSpeakerMute(bool flag)
{
    // Get mute control id
    DWORD dwControlId = getSpeakerMuteControlId();
    if( dwControlId == 0 )
    {
        return false;
    }

    // Set Mute control value
    MIXERCONTROLDETAILS_BOOLEAN aDetails;
    aDetails.fValue = flag;

    MIXERCONTROLDETAILS ControlDetails;
    memset( &ControlDetails, 0, sizeof(MIXERCONTROLDETAILS) );
    ControlDetails.cbStruct = sizeof(MIXERCONTROLDETAILS);
    ControlDetails.dwControlID = dwControlId;
    ControlDetails.cChannels = 1;
    ControlDetails.cMultipleItems = 0;
    ControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_BOOLEAN);
    ControlDetails.paDetails = &aDetails;
    if( mixerSetControlDetails(
                (HMIXEROBJ)m_hMixer,
                &ControlDetails,
                MIXER_SETCONTROLDETAILSF_VALUE ) != MMSYSERR_NOERROR)
    {
        return false;
    }

    return true;
}

bool Mixer::isSpeakerMute()
{
    // Get mute control id
    DWORD dwControlId = getSpeakerMuteControlId();
    if( dwControlId == 0 )
    {
        return false;
    }

    // Get Mute control value
    MIXERCONTROLDETAILS_BOOLEAN aDetails;
    MIXERCONTROLDETAILS ControlDetails;
    memset( &ControlDetails, 0, sizeof(MIXERCONTROLDETAILS) );
    ControlDetails.cbStruct = sizeof(MIXERCONTROLDETAILS);
    ControlDetails.dwControlID = dwControlId;
    ControlDetails.cChannels = 1;
    ControlDetails.cMultipleItems = 0;
    ControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_BOOLEAN);
    ControlDetails.paDetails = &aDetails;
    if( mixerGetControlDetails(
                (HMIXEROBJ)m_hMixer,
                &ControlDetails,
                MIXER_GETCONTROLDETAILSF_VALUE ) != MMSYSERR_NOERROR)
    {
        return false;
    }

    return aDetails.fValue;
}

DWORD Mixer::getSpeakerMuteControlId()
{
    // Get speaker line
    MIXERLINE mixerLine;
    memset(&mixerLine, 0, sizeof(mixerLine));
    mixerLine.cbStruct = sizeof(mixerLine);
    mixerLine.dwComponentType = MIXERLINE_COMPONENTTYPE_DST_SPEAKERS;

    if( mixerGetLineInfo(
            (HMIXEROBJ)m_hMixer,
            &mixerLine,
            MIXER_GETLINEINFOF_COMPONENTTYPE ) != MMSYSERR_NOERROR)
    {
        return 0;
    }

    // Get Mute control
    MIXERCONTROL Control;
    memset( &Control, 0, sizeof(MIXERCONTROL) );
    Control.cbStruct = sizeof(MIXERCONTROL);

    MIXERLINECONTROLS LineControls;
    memset( &LineControls, 0, sizeof(MIXERLINECONTROLS) );
    LineControls.cbStruct = sizeof(MIXERLINECONTROLS);

    LineControls.dwControlType = MIXERCONTROL_CONTROLTYPE_MUTE;
    LineControls.dwLineID = mixerLine.dwLineID;
    LineControls.cControls = 1;
    LineControls.cbmxctrl = sizeof(MIXERCONTROL);
    LineControls.pamxctrl = &Control;
    if( mixerGetLineControls(
                (HMIXEROBJ)m_hMixer,
                &LineControls,
                MIXER_GETLINECONTROLSF_ONEBYTYPE) != MMSYSERR_NOERROR )
    {
        return 0;
    }

    return Control.dwControlID;
}

DWORD Mixer::getSpeakerVolumeControlId()
{
    // Get Line info
    MIXERLINE mixerLine;
    memset(&mixerLine, 0, sizeof(mixerLine));
    mixerLine.cbStruct = sizeof(mixerLine);
    mixerLine.dwComponentType = MIXERLINE_COMPONENTTYPE_DST_SPEAKERS;

    if( mixerGetLineInfo(
            (HMIXEROBJ)m_hMixer,
            &mixerLine,
            MIXER_GETLINEINFOF_COMPONENTTYPE | MIXER_OBJECTF_HMIXER) != MMSYSERR_NOERROR)
    {
        return 0;
    }

    // Get line control info
    MIXERCONTROL Control;
    memset( &Control, 0, sizeof(MIXERCONTROL) );
    Control.cbStruct = sizeof(MIXERCONTROL);

    MIXERLINECONTROLS LineControls;
    memset( &LineControls, 0, sizeof(MIXERLINECONTROLS) );
    LineControls.cbStruct = sizeof(MIXERLINECONTROLS);

    LineControls.dwControlType = MIXERCONTROL_CONTROLTYPE_VOLUME;
    LineControls.dwLineID = mixerLine.dwLineID;
    LineControls.cControls = 1;
    LineControls.cbmxctrl = sizeof(MIXERCONTROL);
    LineControls.pamxctrl = &Control;
    if( mixerGetLineControls(
                (HMIXEROBJ)m_hMixer,
                &LineControls,
                MIXER_GETLINECONTROLSF_ONEBYTYPE) != MMSYSERR_NOERROR )
    {
        return 0;
    }

    return Control.dwControlID;
}

DWORD Mixer::getMicrophoneLineId()
{
    MIXERLINE MixerLine;
    memset( &MixerLine, 0, sizeof(MIXERLINE) );
    MixerLine.cbStruct = sizeof(MIXERLINE);
    MixerLine.dwComponentType = MIXERLINE_COMPONENTTYPE_DST_WAVEIN;

    if( mixerGetLineInfo(
                (HMIXEROBJ)m_hMixer,
                &MixerLine,
                MIXER_GETLINEINFOF_COMPONENTTYPE ) != MMSYSERR_NOERROR)
    {
        return 0;
    }

    DWORD dwMicrophoneLineId = 0;
    for ( UINT uLine = 0; uLine < MixerLine.cConnections; uLine++ )
    {
        MIXERLINE MicrophoneLine;
        memset( &MicrophoneLine, 0, sizeof(MIXERLINE) );
        MicrophoneLine.cbStruct = sizeof(MIXERLINE);
        MicrophoneLine.dwDestination = MixerLine.dwDestination;
        MicrophoneLine.dwSource = uLine;
        MMRESULT mmResult = mixerGetLineInfo(
                (HMIXEROBJ)m_hMixer,
                &MicrophoneLine,
                MIXER_GETLINEINFOF_SOURCE );

        if ( mmResult == MMSYSERR_NOERROR )
        {
            if ( MicrophoneLine.dwComponentType == MIXERLINE_COMPONENTTYPE_SRC_MICROPHONE )
            {
                dwMicrophoneLineId = MicrophoneLine.dwLineID;
                break;
            }
        }
    }
    return dwMicrophoneLineId;
}

DWORD Mixer::getMicrophoneVolumeControlId()
{
    // Find the microphone line
    DWORD dwMicrophoneLineId = getMicrophoneLineId();
    if ( dwMicrophoneLineId == 0 )
    {
        // Oops! We can't find the microphone line
        return 0;
    }

    // Find volume control id
    MIXERCONTROL Control;
    memset( &Control, 0, sizeof(MIXERCONTROL) );
    Control.cbStruct = sizeof(MIXERCONTROL);

    MIXERLINECONTROLS LineControls;
    memset( &LineControls, 0, sizeof(MIXERLINECONTROLS) );
    LineControls.cbStruct = sizeof(MIXERLINECONTROLS);

    LineControls.dwControlType = MIXERCONTROL_CONTROLTYPE_VOLUME;
    LineControls.dwLineID = dwMicrophoneLineId;
    LineControls.cControls = 1;
    LineControls.cbmxctrl = sizeof(MIXERCONTROL);
    LineControls.pamxctrl = &Control;
    if( mixerGetLineControls(
                (HMIXEROBJ)m_hMixer,
                &LineControls,
                MIXER_GETLINECONTROLSF_ONEBYTYPE) != MMSYSERR_NOERROR )
    {
        return 0;
    }

    return Control.dwControlID;
}

DWORD Mixer::getVolumeControlValue(DWORD dwControlId)
{
    MIXERCONTROLDETAILS_UNSIGNED aDetails;
    MIXERCONTROLDETAILS ControlDetails;
    memset( &ControlDetails, 0, sizeof(MIXERCONTROLDETAILS) );
    ControlDetails.cbStruct = sizeof(MIXERCONTROLDETAILS);
    ControlDetails.dwControlID = dwControlId;
    ControlDetails.cChannels = 1;
    ControlDetails.cMultipleItems = 0;
    ControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_UNSIGNED);
    ControlDetails.paDetails = &aDetails;
    if( mixerGetControlDetails(
                (HMIXEROBJ)m_hMixer,
                &ControlDetails,
                MIXER_GETCONTROLDETAILSF_VALUE ) != MMSYSERR_NOERROR)
    {
        return 0;
    }

    return aDetails.dwValue;
}

bool Mixer::setVolumeControlValue(DWORD dwControlId, DWORD dwVolume)
{
    MIXERCONTROLDETAILS_UNSIGNED aDetails;
    aDetails.dwValue = dwVolume;

    MIXERCONTROLDETAILS ControlDetails;
    memset( &ControlDetails, 0, sizeof(MIXERCONTROLDETAILS) );
    ControlDetails.cbStruct = sizeof(MIXERCONTROLDETAILS);
    ControlDetails.dwControlID = dwControlId;
    ControlDetails.cChannels = 1;
    ControlDetails.cMultipleItems = 0;
    ControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_UNSIGNED);
    ControlDetails.paDetails = &aDetails;
    if( mixerSetControlDetails(
                (HMIXEROBJ)m_hMixer,
                &ControlDetails,
                MIXER_GETCONTROLDETAILSF_VALUE ) != MMSYSERR_NOERROR)
    {
        return false;
    }

    return true;
}

/**
 * The following codes are copied from demo program
 */
static HMIXER hMixer;
static MIXERLINE mixerLine;
static MIXERLINECONTROLS mixerLineControls;
static MIXERCONTROL mixerControl[16];
static MIXERCONTROLDETAILS mixerControlDetails;

void printLineControl(char* indent)
{
    memset(&mixerLineControls, 0, sizeof(mixerLineControls));
    mixerLineControls.cbStruct = sizeof(mixerLineControls);
    mixerLineControls.dwLineID = mixerLine.dwLineID;
    mixerLineControls.cControls = mixerLine.cControls;
    mixerLineControls.cbmxctrl = sizeof(MIXERCONTROL);
    mixerLineControls.pamxctrl = mixerControl;
    MMRESULT mmResult = mixerGetLineControls(
        (HMIXEROBJ)hMixer,
        &mixerLineControls,
        MIXER_GETLINECONTROLSF_ALL | MIXER_OBJECTF_HMIXER);

    for(unsigned int i=0; i<mixerLine.cControls; i++)
    {
        printf("%sControl %d, type %x, name %s, items %d, flag %x\n",
                indent, i,
                mixerControl[i].dwControlType,
                mixerControl[i].szName,
                mixerControl[i].cMultipleItems,
                mixerControl[i].fdwControl);

        // Get value for each control
        memset(&mixerControlDetails, 0, sizeof(MIXERCONTROLDETAILS));
        mixerControlDetails.cbStruct = sizeof(MIXERCONTROLDETAILS);
        mixerControlDetails.dwControlID = mixerControl[i].dwControlID;
        mixerControlDetails.cChannels = 1;

        int size;
        if( mixerControl[i].fdwControl & MIXERCONTROL_CONTROLF_MULTIPLE )
        {
            // Print the line_id and their text
            mixerControlDetails.cMultipleItems = mixerControl[i].cMultipleItems;
            size = mixerControlDetails.cMultipleItems * mixerControlDetails.cChannels;

            mixerControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_LISTTEXT);
            mixerControlDetails.paDetails = malloc(mixerControlDetails.cbDetails * size);

            MIXERCONTROLDETAILS_LISTTEXT* pListText
                = (MIXERCONTROLDETAILS_LISTTEXT*)mixerControlDetails.paDetails;

            MMRESULT mmResult = mixerGetControlDetails(
                    (HMIXEROBJ)hMixer,
                    &mixerControlDetails,
                    MIXER_GETCONTROLDETAILSF_LISTTEXT | MIXER_OBJECTF_HMIXER);

            printf("%sList Text ", indent);
            for(int j=0; j<size; j++)
            {
                printf("%s(%x) ", pListText[j].szName, pListText[j].dwParam1);
            }
            putchar('\n');
            free(mixerControlDetails.paDetails);
        }
        else
        {
            mixerControlDetails.cMultipleItems = 0;
            size = mixerControlDetails.cChannels;
        }

        DWORD valueTypeFlag = mixerControl[i].dwControlType & 0x000F0000L;
        if( valueTypeFlag == MIXERCONTROL_CT_UNITS_BOOLEAN)
        {
            mixerControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_BOOLEAN);
            mixerControlDetails.paDetails = malloc(mixerControlDetails.cbDetails * size);
        }
        else if ( valueTypeFlag == MIXERCONTROL_CT_UNITS_UNSIGNED)
        {
            mixerControlDetails.cbDetails = sizeof(MIXERCONTROLDETAILS_UNSIGNED);
            mixerControlDetails.paDetails = malloc(mixerControlDetails.cbDetails * size);
        }
        else
        {
            printf("Can't handle this value type now.\n");
            continue;
        }
        
        MMRESULT mmResult = mixerGetControlDetails(
                (HMIXEROBJ)hMixer,
                &mixerControlDetails,
                MIXER_GETCONTROLDETAILSF_VALUE | MIXER_OBJECTF_HMIXER);

        printf("%sValues: ", indent);
        for(int j=0; j<size; j++)
        {
            if( valueTypeFlag == MIXERCONTROL_CT_UNITS_BOOLEAN)
            {
                printf("%x ", ((MIXERCONTROLDETAILS_BOOLEAN*)mixerControlDetails.paDetails)[j].fValue);
            }
            else if ( valueTypeFlag == MIXERCONTROL_CT_UNITS_UNSIGNED)
            {
                printf("%x ", ((MIXERCONTROLDETAILS_UNSIGNED*)mixerControlDetails.paDetails)[j].dwValue);
            }
            else
            {
                assert(FALSE);
            }
        }
        putchar('\n');
        free(mixerControlDetails.paDetails);
    }
}

void printLineInfo(int destination, int source, int flag, char* indent)
{
    memset(&mixerLine, 0, sizeof(mixerLine));
    mixerLine.cbStruct = sizeof(mixerLine);
    mixerLine.dwDestination = destination;
    mixerLine.dwSource = source;

    mixerGetLineInfo(
            (HMIXEROBJ)hMixer,
            &mixerLine,
            flag | MIXER_OBJECTF_HMIXER);

    printf("%sLine %d: %s\n", indent, destination, mixerLine.szName);
    printf("%s\tLineID: %x\n", indent, mixerLine.dwLineID);
    printf("%s\tdwComponentType: %x\n", indent, mixerLine.dwComponentType);
    printf("%s\tControl Number: %d\n", indent, mixerLine.cControls);
    printf("%s\tConnections: %d\n", indent, mixerLine.cConnections);
    printf("%s\tTarget: type-%d, Name-%s\n", indent, mixerLine.Target.dwType, mixerLine.Target.szPname);

    char buffer[64];
    _snprintf(buffer, 64, "%s\t", indent);
    printLineControl(buffer);
}

void Mixer::printInfo()
{
    UINT nMixers = mixerGetNumDevs();
    printf("nMixers = %d\n", nMixers);

    MIXERCAPS mixerCaps;
    for(unsigned int i=0; i<nMixers; i++)
    {
        mixerGetDevCaps(i, &mixerCaps, sizeof(mixerCaps));
        printf("mixer %d: %s has %d lines.\n", i, mixerCaps.szPname, mixerCaps.cDestinations);

        mixerOpen(&hMixer, i, 0, 0, MIXER_OBJECTF_MIXER);

        for(unsigned int j=0; j<mixerCaps.cDestinations; j++)
        {
            // Get line Info
            printLineInfo(j, 0, MIXER_GETLINEINFOF_DESTINATION, "\t");

            // Get the source line info of this line
            unsigned int cConnections = mixerLine.cConnections;
            for(unsigned int k=0; k<cConnections; k++)
            {
                printLineInfo(j, k, MIXER_GETLINEINFOF_SOURCE, "\t\t");
            }

        }

        mixerClose(hMixer);
    }
}

