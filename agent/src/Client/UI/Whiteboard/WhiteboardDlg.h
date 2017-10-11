#pragma once

#include "resource.h"
#include "..\MeetingRoom\TabItem.h"
#include "Draw.h"

#define NUMBER_OF_WHITEBOARD	10

// CWhiteboardDlg 对话框
class CWhiteboard;
class WhiteboardOutStream;
class WhiteboardInStream;
class TransModel;

class CWhiteboardDlg : public CDialog, public CTabItem
{
	DECLARE_DYNAMIC(CWhiteboardDlg)

public:
	CWhiteboardDlg(CWnd* pParent, UINT nID);   // 标准构造函数
	virtual ~CWhiteboardDlg();

    void sendWhiteboardData( char* buff, int nLen );
	void recvWhiteboardData( const char* buff, int nLen );

	void setTransmodel( TransModel* pTransModel );
	WhiteboardOutStream* getOutStream(){ return m_os; }; 

    BOOL			m_bShowWhiteboard;
	int				m_nMouseMode;			//定义鼠标的当前任务
	COLORREF		m_crCurrentColor;		//图元颜色
	int				m_nPenWidth;			//画笔宽度
	bool			m_bFill;				//是否填充
	LOGFONT			m_lf;

	CToolBar*		m_pToolBar;
	CToolBar*		m_pColorBar;
	CBrush			m_brush;
protected:
	TransModel*		m_pTransModel;
	WhiteboardOutStream* m_os;
	WhiteboardInStream*  m_is;
	CWhiteboard*	m_whiteboards[NUMBER_OF_WHITEBOARD];
	int m_nCurSel;

	int getCurrentDisplay();
	void setCurrentDisplay( int nIndex );
	void setFontAndColor();
	void hideTextWindow();

	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	virtual void OnOK() { };
	virtual void OnCancel() { };
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg BOOL OnToolTipText(UINT, NMHDR* pNMHDR, LRESULT* pResult);
	afx_msg void OnBnClickedPrevboardBtn();
	afx_msg void OnBnClickedNextboardBtn();
public:
	afx_msg void OnNewWhiteboard();
    afx_msg void OnFileOpen();
	afx_msg void OnSaveWhiteboard();
	afx_msg void OnRubber();
	afx_msg void OnUpdateRubber(CCmdUI* pCmdUI);

	afx_msg void OnSelect();
	afx_msg void OnUpdateSelect(CCmdUI* pCmdUI);
	afx_msg void OnLine();
	afx_msg void OnUpdateLine(CCmdUI* pCmdUI);
	afx_msg void OnRectangle();
	afx_msg void OnUpdateRectangle(CCmdUI* pCmdUI);
	afx_msg void OnEllipse();
	afx_msg void OnUpdateEllipse(CCmdUI* pCmdUI);
	afx_msg void OnCircle();
	afx_msg void OnUpdateCircle(CCmdUI* pCmdUI);
	afx_msg void OnPoly();
	afx_msg void OnUpdatePoly(CCmdUI* pCmdUI);
	afx_msg void OnCurve();
	afx_msg void OnUpdateCurve(CCmdUI* pCmdUI);
	afx_msg void OnText();
	afx_msg void OnUpdateText(CCmdUI* pCmdUI);
	afx_msg void OnWidthThin();
	afx_msg void OnUpdateWidthThin(CCmdUI* pCmdUI);
	afx_msg void OnWidthNormal();
	afx_msg void OnUpdateWidthNormal(CCmdUI* pCmdUI);
	afx_msg void OnWidthWide();
	afx_msg void OnUpdateWidthWide(CCmdUI* pCmdUI);
	afx_msg void OnFillFalse();
	afx_msg void OnUpdateFillFalse(CCmdUI* pCmdUI);
	afx_msg void OnFillTrue();
	afx_msg void OnUpdateFillTrue(CCmdUI* pCmdUI);

	afx_msg void OnColorBlack();
	afx_msg void OnUpdateColorBlack(CCmdUI* pCmdUI);
	afx_msg void OnColorWhite();
	afx_msg void OnUpdateColorWhite(CCmdUI* pCmdUI);
	afx_msg void OnColorGray();
	afx_msg void OnUpdateColorGray(CCmdUI* pCmdUI);
	afx_msg void OnColorRed();
	afx_msg void OnUpdateColorRed(CCmdUI* pCmdUI);
	afx_msg void OnColorGreen();
	afx_msg void OnUpdateColorGreen(CCmdUI* pCmdUI);
	afx_msg void OnColorBlue();
	afx_msg void OnUpdateColorBlue(CCmdUI* pCmdUI);
	afx_msg void OnColorPink();
	afx_msg void OnUpdateColorPink(CCmdUI* pCmdUI);
	afx_msg void OnColorYellow();
	afx_msg void OnUpdateColorYellow(CCmdUI* pCmdUI);
	afx_msg void OnColorCyan();
	afx_msg void OnUpdateColorCyan(CCmdUI* pCmdUI);

	afx_msg void OnSmallText();
	afx_msg void OnUpdateSmallText(CCmdUI* pCmdUI);
	afx_msg void OnNormalText();
	afx_msg void OnUpdateNormalText(CCmdUI* pCmdUI);
	afx_msg void OnBigText();
	afx_msg void OnUpdateBigText(CCmdUI* pCmdUI);
};
