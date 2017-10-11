#pragma once

#include <queue>
using namespace std;

class CDrawObject;
class CWhiteboardDlg;
class CWhiteboardText;

// CWhiteboard 对话框

class CWhiteboard : public CDialog
{
	DECLARE_DYNAMIC(CWhiteboard)

public:
	CWhiteboard(CWnd* pParent, UINT nID, UINT nBoardNum);   // 标准构造函数
	virtual ~CWhiteboard();
	// 收到的在白板上画的动作
    void appendDraw( CDrawObject* pObject );
	void setTextFontAndColor(LOGFONT lf, COLORREF color);
	void hideTextWindow();
	void saveToFile( CString strFile );

private:
	CWhiteboardText*	m_pTrackText;
	BOOL			m_bShowText;
	UINT			m_nDrawTimerId;
	UINT			m_nBoardNum;
    CWhiteboardDlg* m_pParent;
	// 白板大小
	CSize			m_boardSize;
	CBrush			m_brush;
	// 绘制的内容
	HBITMAP			m_hBitmap;
	// 是否显示网格
	bool			m_bShowgrid;
	// 当前绘制的图形
	CDrawObject*	m_usrCurrentObject;
	// 将要绘制的图形队列
    queue<CDrawObject*> m_drawObjects;

    // 保存当前绘制的内容
	void SaveCurrentDraw( CDrawObject* pDrawObject );
	// 显示网格
	void ShowGrid();

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持

	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	virtual void OnOK() { };
	virtual void OnCancel() { };
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
	afx_msg void OnPaint();
	afx_msg void OnLButtonDown(UINT nFlags, CPoint point);
	afx_msg void OnMouseMove(UINT nFlags, CPoint point);
	afx_msg void OnLButtonUp(UINT nFlags, CPoint point);
	afx_msg void OnLButtonDblClk(UINT nFlags, CPoint point);
	afx_msg void OnKillFocus(CWnd* pNewWnd);
	afx_msg void OnTimer(UINT nIDEvent);
	afx_msg BOOL OnSetCursor(CWnd* pWnd, UINT nHitTest, UINT message);
};
