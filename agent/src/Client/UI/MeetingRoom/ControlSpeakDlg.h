#pragma once


// CControlSpeakDlg 对话框

class CControlSpeakDlg : public CDialog
{
	DECLARE_DYNAMIC(CControlSpeakDlg)

public:
	CControlSpeakDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CControlSpeakDlg();

	// 添加人员
	void addConferenceUser( __int64 uid, string name );

	// 删除人员
	void deleteConferenceUser( __int64 uid );

	// 设置申请发言的标志
	void setApplySpeak( __int64 uid );

	// 设置是否正在发言的标志
	void setSpeaker( __int64 uid, BOOL bFlag = TRUE );

	// 对话框数据
	enum { IDD = IDD_CONTROLSPEAK_DLG };

private:
    void setListItemImage( int nIndex, int nImage );

private:
	CImageList	m_ImageList;
	CListCtrl m_ListCtrl;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();

	DECLARE_MESSAGE_MAP()

	afx_msg void OnDestroy();
	afx_msg void OnBnClickedAppointspeakerBtn();
	afx_msg void OnBnClickedStopspeakerBtn();
};
