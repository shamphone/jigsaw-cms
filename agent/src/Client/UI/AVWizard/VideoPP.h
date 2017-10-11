#pragma once

#include "Video/DrawDIB/DrawDIB.h"
#include "..\pub\linkbutton.h"

class CVideoPP : public CPropertyPage
{
	DECLARE_DYNAMIC(CVideoPP)

public:
	CVideoPP();
	virtual ~CVideoPP();

    // 对话框数据
	enum { IDD = IDD_PROPPAGE_VIDEO };

protected:

	DECLARE_MESSAGE_MAP()

private:
	CLinkButton m_btn;
	CComboBox m_comboVideoDevice;
	CDrawDIB m_DrawDIB;
	HWND m_hVideoWnd;
	CBrush m_brush;

    BOOL m_bIsCapturing;
    void startCapture();
    void stopCapture();

    void saveSettings();

public:

    // 采集图像数据的回调函数
	static void videoDataCallback(void* pObject, LPVIDEOHDR lpVHdr);

	virtual void DoDataExchange(CDataExchange* pDX);
	virtual BOOL OnSetActive();
    virtual BOOL OnKillActive();
	virtual void OnCancel();
	virtual BOOL OnWizardFinish();
	virtual BOOL OnInitDialog();
	afx_msg void OnCbnSelchangeComboVideoDevice();
	afx_msg void OnBnClickedProperty();
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};

