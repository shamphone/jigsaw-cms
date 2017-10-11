#pragma once

#include "../../../Audio/Mixer/Mixer.h"

class CAudioRecordPP : public CPropertyPage
{
	DECLARE_DYNAMIC(CAudioRecordPP)

public:
	CAudioRecordPP();
	virtual ~CAudioRecordPP();

    // 对话框数据
	enum { IDD = IDD_PROPPAGE_AUDIO_RECORD };

	virtual void DoDataExchange(CDataExchange* pDX);
    virtual BOOL OnInitDialog();
    virtual BOOL OnSetActive();
    virtual BOOL OnKillActive();
	virtual void OnCancel();
    afx_msg void OnCbnSelchangeComboAudioRecordDevice();
    afx_msg void OnVScroll(UINT nSBCode, UINT nPos, CScrollBar* pScrollBar);
	afx_msg LRESULT OnMixerChange(WPARAM wparam,LPARAM lparam);

private:

	DECLARE_MESSAGE_MAP()

    CComboBox m_comboAudioRecordDevice;
    CSliderCtrl m_sliderVolume;
    void EnableVolumeBar(BOOL bFlag);
    Mixer m_mixer;
	CBrush m_brush;
public:
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};

