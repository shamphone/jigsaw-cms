#pragma once

#include "..\pub\linkbutton.h"
#include "VideoShowWnd.h"

class ConferenceUser;
class CMeetingRoomFrame;

class CAttenderDlg : public CDialog
{
	DECLARE_DYNAMIC(CAttenderDlg)

public:
	CAttenderDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CAttenderDlg();

	//设置此人的显示图片
	void setUserPicture( string fileName );
	// 弹出/收回显示
    void popUp();
	// 获得画视频信号的窗口句柄
	HWND getVideoHwnd();
	// 设置参会人信息
	void setConferenceUser(ConferenceUser* pConfUser);
	// 获得用户id和名字
	__int64 getUserId();
    string getName();
	// 设置是否正在听的标志
	void setIsListenFlag( bool bIsListen );
	void resetIndex();

// 对话框数据
	enum { IDD = IDD_ATTENDER_DLG };

private:
	CMeetingRoomFrame* getRoomFrame();
	void DrawTitle(CBitmap *pBitmap, CDC *pDC, CRect rc);

public:
	bool			m_bIfHasYuntai;
	// 是否显示视频窗口
	bool			m_bShowVideoWindow;
	// 是否是自己
	bool			m_bIsSelf;
	// 是否弹出
	bool			m_bIsPopup;
	int				m_nIndex;
    
private:
	// 收听/停止收听此人的按钮
	CLinkButton			m_btnListen;
	// 参会人信息
	ConferenceUser*		m_pConfUser;
	// 视频窗口
	CVideoShowWnd		m_videoWindow;
	// 有压缩卡的时候本地画视频的窗口
	CStatic				m_localVirtualVideoWindow;
	CBrush				m_brush;
	CWnd*				m_pParent;
	int					m_nLeftMargin;
	int					m_nTopMargin;
	bool				m_bIsFullScreen;
	int					m_nCurrentVideoSize;

private:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnOK() {};
	virtual void OnCancel() {};

	DECLARE_MESSAGE_MAP()
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg void OnPaint();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnLButtonDblClk(UINT nFlags, CPoint point);

	afx_msg void OnLookVideo();
	afx_msg void OnStopLook();
	afx_msg void OnChat();
	afx_msg void OnInviteDesktop();
	afx_msg void OnApplyDesktop();
	afx_msg void OnStopDesktopSend();
	afx_msg void OnStopDesktopReceive();
	afx_msg void OnApplyControl();
	afx_msg void OnStopControl();
	afx_msg void OnStopReceiveControl();
	afx_msg void OnReceiveAudio();
	afx_msg void OnStopAudio();
	afx_msg void OnSendFile();
	afx_msg void OnBnClickedListenBtn();
	afx_msg void OnTopmost();
	afx_msg void OnFullscreen();
	afx_msg void OnQcif();
	afx_msg void OnCif();
	afx_msg void OnVga();
	afx_msg void OnYuntaicontrol();
public:
	afx_msg void OnViewPopup();
	afx_msg void OnMove(int x, int y);
	afx_msg void OnApplyContact();
	afx_msg void OnVideoqualityGood();
	afx_msg void OnVideoqualityNormal();
	afx_msg void OnVideoqualityBad();
	afx_msg void OnSetmypicture();
	afx_msg void OnKickuser();
	afx_msg void OnDeleteconfuser();
};
