#pragma once

#include "TabItem.h"

// CVideoBgDlg 对话框
class CRoomView;
class CAttenderDlg;

class CVideoBgDlg : public CDialog, public CTabItem
{
	DECLARE_DYNAMIC(CVideoBgDlg)

public:
	CVideoBgDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CVideoBgDlg();
	BOOL create(UINT nIDTemplate, CRoomView* pView, CWnd* pParentWnd = NULL);
	int getVideoPosition( CRect* pRect, CAttenderDlg* pAttender );
	void resetIndex( int nIndex );
	void changeViewMode( int nMode );

// 对话框数据
	enum { IDD = IDD_VIDEO_BG_DLG };
private:
	void changeView( int nVideoNumber );
	void changeToNormal();
	int getAvailableIndex( int nEndBefore );
	void DrawRangeImage(CBitmap * pBitmap, CDC* pDC, CRect rc);

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual void OnOK();
	virtual void OnCancel();

	DECLARE_MESSAGE_MAP()
	afx_msg void OnPaint();
	afx_msg void OnSize(UINT nType, int cx, int cy);

private:
	CRoomView* m_pRoomView;
	CAttenderDlg* m_attenderDlgs[16];
};
