#pragma once

#include "..\pub\linkbutton.h"
#include "..\pub\dialogex.h"

class YuntaiMgr;
// CYuntaiDlg 对话框

class CYuntaiDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CYuntaiDlg)

public:
	CYuntaiDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CYuntaiDlg();

	void setYuntaiMgr(YuntaiMgr* pMgr);
	void addYuntaiHolder( __int64 uid, string name, int nCommPort );

private:
	void stopControl();
	int getCurrentCommPort();
	int getCurentControlAddress();

	YuntaiMgr* m_pYuntaiMgr;
	BOOL m_bMovingUp;
	BOOL m_bMovingDown;
	BOOL m_bMovingLeft;
	BOOL m_bMovingRight;
	BOOL m_bZoomIn;
	BOOL m_bZoomOut;

    int m_nCurrentCommPort;
	int m_nCurrentAddress;
	// 对话框数据
	enum { IDD = IDD_YUNTAI_DLG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual void OnOK();
	virtual void OnCancel();

	DECLARE_MESSAGE_MAP()

	afx_msg void OnBnClickedBtnUp();
	afx_msg void OnBnClickedBtnDown();
	afx_msg void OnBnClickedBtnLeft();
	afx_msg void OnBnClickedBtnRight();
	afx_msg void OnBnClickedBtnZoomin();
	afx_msg void OnBnClickedBtnZoomout();
	afx_msg void OnCbnSelchangeComboControllist();
	afx_msg void OnCbnSelchangeComboSerialport();
};
