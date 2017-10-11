#include "stdafx.h"
#include "Flvcc.h"
#include "AudioPlayPP.h"
#include ".\audioplaypp.h"
#include "SlideVolumeConvert.h"
#include "AVConfigWizard.h"


IMPLEMENT_DYNAMIC(CAudioPlayPP, CPropertyPage)

BEGIN_MESSAGE_MAP(CAudioPlayPP, CPropertyPage)
    ON_CBN_SELCHANGE(IDC_COMBO_AUDIO_PLAY_DEVICE, OnCbnSelchangeComboAudioPlayDevice)
    ON_WM_VSCROLL()
    ON_BN_CLICKED(IDC_BUTTON_PLAY_SAMPLE, OnBnClickedButtonPlaySample)
	ON_MESSAGE(MM_MIXM_CONTROL_CHANGE, OnMixerChange)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()

CAudioPlayPP::CAudioPlayPP() : CPropertyPage(CAudioPlayPP::IDD)
{
    m_bIsPlaying = FALSE;
}

CAudioPlayPP::~CAudioPlayPP()
{
}

void CAudioPlayPP::DoDataExchange(CDataExchange* pDX)
{
    CPropertyPage::DoDataExchange(pDX);
    DDX_Control(pDX, IDC_COMBO_AUDIO_PLAY_DEVICE, m_comboAudioPlayDevice);
    DDX_Control(pDX, IDC_SLIDER_AUDIO_PLAY_VOLUME, m_sliderVolume);
	DDX_Control(pDX, IDC_BUTTON_PLAY_SAMPLE, m_btn );
}

BOOL CAudioPlayPP::OnInitDialog()
{
    CPropertyPage::OnInitDialog();

    // Set the volume bar
    this->m_sliderVolume.SetRange(0, SlideVolumeConvert::VOLUME_RANGE-1, TRUE);

    // Load the wave file
    m_waveFile.Load("..\\AVClips\\WindowsStartup.wav");
    m_waveOut.SetWave(m_waveFile);

	m_btn.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	CRect rc;
	GetDlgItem(IDC_BUTTON_PLAY_SAMPLE)->GetWindowRect( &rc );
	ScreenToClient( rc );
	m_btn.MoveWindow( rc.left, rc.top, 74, 22 );
	m_brush.CreateSolidBrush( RGB(240, 245, 250) );
    return TRUE;
}

void CAudioPlayPP::EnableButtonAndVolumeBar(BOOL bFlag)
{
    CWnd* pButton = this->GetDlgItem(IDC_BUTTON_PLAY_SAMPLE);
    pButton->EnableWindow(bFlag);

    CWnd* pVolume = this->GetDlgItem(IDC_SLIDER_AUDIO_PLAY_VOLUME);
    pVolume->EnableWindow(bFlag);
}

void CAudioPlayPP::OnCbnSelchangeComboAudioPlayDevice()
{
    if( this->m_bIsPlaying )
    {
        this->StopPlaying();
    }
    this->m_mixer.destroy();

    UINT nSelected = this->m_comboAudioPlayDevice.GetCurSel();
    if( nSelected == 0 )
    {
        this->EnableButtonAndVolumeBar(FALSE);
    }
    else
    {
        this->EnableButtonAndVolumeBar(TRUE);

        int nMixerId = Mixer::getMixerIdFromWaveOutId(nSelected-1);
        m_mixer.create(nMixerId, this->m_hWnd);
        m_mixer.setSpeakerMute(FALSE);
        DWORD dwVolume = m_mixer.getMasterVolume();
        this->m_sliderVolume.SetPos( SlideVolumeConvert::WaveVolumeToSlidePos(dwVolume));
    }
}

void CAudioPlayPP::OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar)
{
    int position = this->m_sliderVolume.GetPos();
    DWORD dwVolume = SlideVolumeConvert::SlidePosToWaveVolume(position);
    this->m_mixer.setMasterVolume(dwVolume);

    CPropertyPage::OnVScroll(nSBCode, nPos, pScrollBar);
}

void CAudioPlayPP::OnBnClickedButtonPlaySample()
{
    int nDeviceId = this->m_comboAudioPlayDevice.GetCurSel();
    if( nDeviceId == 0 )
    {
        TRACE0("Oops! Diabled control can send message!\n");
        return;
    }

    if( ! this->m_bIsPlaying )
    {
        this->StartPlaying(nDeviceId - 1);
    }
    else
    {
        this->StopPlaying();
    }
}

void CAudioPlayPP::StartPlaying(int waveOutId)
{
    m_waveDevice = CWaveDevice(waveOutId);
    m_waveOut.SetDevice(m_waveDevice);
    m_waveOut.Open();
    m_waveOut.FullPlay(INFINITE_LOOP);

    CWnd* pButton = this->GetDlgItem(IDC_BUTTON_PLAY_SAMPLE);
    pButton->SetWindowText("停止播放");
    this->m_bIsPlaying = TRUE;
}

void CAudioPlayPP::StopPlaying()
{
    m_waveOut.Stop();
    m_waveOut.Close();

    CWnd* pButton = this->GetDlgItem(IDC_BUTTON_PLAY_SAMPLE);
    pButton->SetWindowText("播放测试");
    this->m_bIsPlaying = FALSE;
}

BOOL CAudioPlayPP::OnSetActive()
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
    while (m_comboAudioPlayDevice.GetCount() > 0)
    {
       m_comboAudioPlayDevice.DeleteString(0);
    }
    m_comboAudioPlayDevice.AddString("无");

    // If no audio play device, disable controls on the dialog
    int audioDeviceCount = waveOutGetNumDevs();
    if( audioDeviceCount == 0 )
    {
        this->EnableButtonAndVolumeBar(FALSE);
        m_comboAudioPlayDevice.SetCurSel(0);
        return TRUE;
    }

    // else add them, select the first one.
    for(int i=0; i<audioDeviceCount; i++)
    {
        WAVEOUTCAPS waveOutCaps;
        waveOutGetDevCaps(i, &waveOutCaps, sizeof(WAVEOUTCAPS));
        m_comboAudioPlayDevice.InsertString(i+1, waveOutCaps.szPname);
    }

    // 默认选中第一个设备
    m_comboAudioPlayDevice.SetCurSel(1);
    this->EnableButtonAndVolumeBar(TRUE);

    int nMixerId = Mixer::getMixerIdFromWaveOutId(0);
    m_mixer.create(nMixerId, this->m_hWnd);
    m_mixer.setSpeakerMute(FALSE);
    DWORD dwVolume = m_mixer.getMasterVolume();
    this->m_sliderVolume.SetPos( SlideVolumeConvert::WaveVolumeToSlidePos(dwVolume));

    return CPropertyPage::OnSetActive();
}

void CAudioPlayPP::OnCancel()
{
    if( this->m_bIsPlaying )
    {
        this->StopPlaying();
    }

    this->m_mixer.destroy();

    CPropertyPage::OnCancel();
}

BOOL CAudioPlayPP::OnKillActive()
{
    if( this->m_bIsPlaying )
    {
        this->StopPlaying();
    }

    this->m_mixer.destroy();

    // 保存用户选择的设备
    int nIndex = this->m_comboAudioPlayDevice.GetCurSel();
    if( nIndex != 0)
    {
        const char* iniFileName = ::GetApp()->getIniFilename();
        char buffer[64];
        _snprintf(buffer, 64, "%d", nIndex-1);
        ::WritePrivateProfileString("MEDIA_CONFIG", "audio_play_device", buffer, iniFileName);
    }
    
    return CPropertyPage::OnKillActive();
}

LRESULT CAudioPlayPP::OnMixerChange(WPARAM wparam, LPARAM lparam)
{
	if((DWORD)lparam == m_mixer.getSpeakerVolumeControlId())
	{
		DWORD dwVolume = m_mixer.getMasterVolume();
        m_sliderVolume.SetPos( SlideVolumeConvert::WaveVolumeToSlidePos(dwVolume));
        TRACE1("Speaker Volume changed, new volume %x\n", dwVolume);
	}

	return 0;
}


void CAudioPlayPP::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();
	CString sText = "步骤1：设置扬声器";
	pWizard->drawHead( &dc, rc, sText );
	// 不为绘图消息调用 CPropertyPage::OnPaint()
}

HBRUSH CAudioPlayPP::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CPropertyPage::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC || pWnd->GetDlgCtrlID() == IDC_SLIDER_AUDIO_PLAY_VOLUME )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}
