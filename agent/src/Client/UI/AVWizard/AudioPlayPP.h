#pragma once

#include "../../../Audio/WavePlay/Wave.h"
#include "../../../Audio/WavePlay/WaveDevice.h"
#include "../../../Audio/WavePlay/WaveOut.h"
#include "../../../Audio/Mixer/Mixer.h"

#include "..\pub\linkbutton.h"

class CAudioPlayPP : public CPropertyPage
{
    DECLARE_DYNAMIC(CAudioPlayPP)

public:
    CAudioPlayPP();
    virtual ~CAudioPlayPP();

    // 对话框数据
    enum { IDD = IDD_PROPPAGE_AUDIO_PLAY };

    afx_msg void OnCbnSelchangeComboAudioPlayDevice();
    afx_msg void OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
    afx_msg void OnBnClickedButtonPlaySample();
    virtual void DoDataExchange(CDataExchange* pDX);
    virtual BOOL OnInitDialog();
    virtual BOOL OnKillActive();
    virtual BOOL OnSetActive();
    virtual void OnCancel();
	afx_msg LRESULT OnMixerChange(WPARAM wparam,LPARAM lparam);

private:
	CLinkButton m_btn;
	CBrush m_brush;

    DECLARE_MESSAGE_MAP()

    CComboBox m_comboAudioPlayDevice;   // Combo box for waveout devices
    CSliderCtrl m_sliderVolume;         // Volume bar
    CWave m_waveFile;                   // Wave file
    CWaveDevice m_waveDevice;           // Wave Device for playing
    CWaveOut m_waveOut;                 // Wave Out for playing
    Mixer m_mixer;                      // Mixer for volume control
    BOOL m_bIsPlaying;                  // whether we are playing

    void EnableButtonAndVolumeBar(BOOL bFlag);
    void StartPlaying(int waveOutId);
    void StopPlaying();
public:
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};

