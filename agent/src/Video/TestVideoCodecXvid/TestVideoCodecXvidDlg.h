// TestVideoCodecXvidDlg.h : 头文件
//

#pragma once

#include "..\DrawDIB\DrawDIB.h"
#include "../VideoCodecXvid/VideoEncoder.h"
#include "../VideoCodecXvid/VideoDecoder.h"

// CTestVideoCodecXvidDlg 对话框
class CTestVideoCodecXvidDlg : public CDialog
{
public:

    // 构造
	CTestVideoCodecXvidDlg(CWnd* pParent = NULL);

    // 对话框数据
	enum { IDD = IDD_TESTVIDEOCODECXVID_DIALOG };

// 实现
protected:
	HICON m_hIcon;

	// 生成的消息映射函数
	virtual BOOL OnInitDialog();
	virtual void OnOK();
	afx_msg void OnPaint();
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 支持
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()

    // Video Call back
	static void Callback(void* pObject, LPVIDEOHDR lpVHdr);

private:

	CDrawDIB m_DrawDIBOriginal;
	HWND m_hVideoWndOriginal;

	CDrawDIB m_DrawDIBEncode;
	HWND m_hVideoWndEncode;
    VideoEncoder m_encoder;
    VideoDecoder m_decoder;

    static const int ENCODED_VIDEO_BUFFER_LENGTH = 60000;
    unsigned char m_videoData[ENCODED_VIDEO_BUFFER_LENGTH];

    HWND AdjustVideoWindow(int ControlId);
};

