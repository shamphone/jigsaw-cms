#include "stdafx.h"
#include "Flvcc.h"
#include "AudioRecordPP.h"
#include ".\audiorecordpp.h"
#include "SlideVolumeConvert.h"
#include "AVConfigWizard.h"


IMPLEMENT_DYNAMIC(CAudioRecordPP, CPropertyPage)

BEGIN_MESSAGE_MAP(CAudioRecordPP, CPropertyPage)
    ON_CBN_SELCHANGE(IDC_COMBO_AUDIO_RECORD_DEVICE, OnCbnSelchangeComboAudioRecordDevice)
    ON_WM_VSCROLL()
	ON_MESSAGE(MM_MIXM_CONTROL_CHANGE, OnMixerChange)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()

CAudioRecordPP::CAudioRecordPP()
	: CPropertyPage(CAudioRecordPP::IDD)
{
}

CAudioRecordPP::~CAudioRecordPP()
{
}

void CAudioRecordPP::DoDataExchange(CDataExchange* pDX)
{
    CPropertyPage::DoDataExchange(pDX);
    DDX_Control(pDX, IDC_COMBO_AUDIO_RECORD_DEVICE, m_comboAudioRecordDevice);
    DDX_Control(pDX, IDC_SLIDER_AUDIO_RECORD_VOLUME, m_sliderVolume);
}

BOOL CAudioRecordPP::OnInitDialog()
{
    CPropertyPage::OnInitDialog();

    // Set the volume bar
    this->m_sliderVolume.SetRange(0, SlideVolumeConvert::VOLUME_RANGE-1, TRUE);

	m_brush.CreateSolidBrush( RGB(240, 245, 250) );
    return TRUE;
}

void CAudioRecordPP::EnableVolumeBar(BOOL bFlag)
{
    CWnd* pVolume = this->GetDlgItem(IDC_SLIDER_AUDIO_RECORD_VOLUME);
    pVolume->EnableWindow(bFlag);
}

void CAudioRecordPP::OnCbnSelchangeComboAudioRecordDevice()
{
    m_mixer.destroy();

    UINT nSelected = this->m_comboAudioRecordDevice.GetCurSel();
    if( nSelected == 0 )
    {
        this->EnableVolumeBar(FALSE);
    }
    else
    {
        this->EnableVolumeBar(TRUE);

        // Set volume display
        int nMixerId = Mixer::getMixerIdFromWaveInId(nSelected-1);
        m_mixer.create(nMixerId, this->m_hWnd);
        m_mixer.selectMicrophoneAsWaveInput();
        DWORD dwVolume = m_mixer.getMicrophoneVolume();
        this->m_sliderVolume.SetPos( SlideVolumeConvert::WaveVolumeToSlidePos(dwVolume));
    }
}

void CAudioRecordPP::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
    int position = this->m_sliderVolume.GetPos();
    DWORD dwVolume = SlideVolumeConvert::SlidePosToWaveVolume(position);
    m_mixer.setMicrophoneVolume(dwVolume);

    CPropertyPage::OnVScroll(nSBCode, nPos, pScrollBar);
}

BOOL CAudioRecordPP::OnSetActive()
{
    CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();   
	pWizard->setWindowRect( this );
	if( pWizard->m_btnBack.GetSafeHwnd() )
		pWizard->m_btnBack.EnableWindow(TRUE);
	if( pWizard->m_btnFinish.GetSafeHwnd() )
        pWizard->m_btnFinish.ShowWindow(FALSE);
	if( pWizard->m_btnNext.GetSafeHwnd() )
		pWizard->m_btnNext.ShowWindow(TRUE);

	// Fill the audio device combo list
	while(m_comboAudioRecordDevice.GetCount() > 0)
	{
		m_comboAudioRecordDevice.DeleteString(0);
	}
    m_comboAudioRecordDevice.AddString("无");

    // If no audio play device, disable controls on the dialog
    int audioDeviceCount = waveInGetNumDevs();
    if( audioDeviceCount == 0 )
    {
        this->EnableVolumeBar(FALSE);
        m_comboAudioRecordDevice.SetCurSel(0);
        return TRUE;
    }

    // else add them, select the first one.
    for(int i=0; i<audioDeviceCount; i++)
    {
        WAVEINCAPS waveInCaps;
        waveInGetDevCaps(i, &waveInCaps, sizeof(WAVEINCAPS));
        m_comboAudioRecordDevice.InsertString(i+1, waveInCaps.szPname);
    }

    m_comboAudioRecordDevice.SetCurSel(1);
    this->EnableVolumeBar(TRUE);

    int nMixerId = Mixer::getMixerIdFromWaveInId(0);
    m_mixer.create(nMixerId, this->m_hWnd);
    m_mixer.selectMicrophoneAsWaveInput();
    DWORD dwVolume = m_mixer.getMicrophoneVolume();
    this->m_sliderVolume.SetPos( SlideVolumeConvert::WaveVolumeToSlidePos(dwVolume));

    return CPropertyPage::OnSetActive();
}

BOOL CAudioRecordPP::OnKillActive()
{
    m_mixer.destroy();

	// 保存用户选择的设备
    int nIndex = this->m_comboAudioRecordDevice.GetCurSel();
    if( nIndex != 0)
    {
        const char* iniFileName = ::GetApp()->getIniFilename();
        char buffer[64];
        _snprintf(buffer, 64, "%d", nIndex-1);
        ::WritePrivateProfileString("MEDIA_CONFIG", "audio_record_device", buffer, iniFileName);
    }

    return CPropertyPage::OnKillActive();
}

void CAudioRecordPP::OnCancel()
{
    m_mixer.destroy();

	CPropertyPage::OnCancel();
}

LRESULT CAudioRecordPP::OnMixerChange(WPARAM wparam, LPARAM lparam)
{
	if((DWORD)lparam == m_mixer.getMicrophoneVolumeControlId())
	{
		DWORD dwVolume = m_mixer.getMicrophoneVolume();
        m_sliderVolume.SetPos( SlideVolumeConvert::WaveVolumeToSlidePos(dwVolume));
        TRACE1("Microphone Volume changed, new volume %x\n", dwVolume);
	}

	return 0;
}


void CAudioRecordPP::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();
	CString sText = "步骤2：设置麦克风：";
	pWizard->drawHead( &dc, rc, sText );
	// 不为绘图消息调用 CPropertyPage::OnPaint()
}

HBRUSH CAudioRecordPP::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CPropertyPage::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC || pWnd->GetDlgCtrlID() == IDC_SLIDER_AUDIO_RECORD_VOLUME )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}
