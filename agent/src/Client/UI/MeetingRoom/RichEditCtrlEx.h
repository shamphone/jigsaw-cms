#pragma once

class CChatDlg;

/////////////////////////////////////////////////////////////////////////////
// CRichEditCtrlEx window
class CRichEditCtrlEx : public CRichEditCtrl
{
	DECLARE_DYNAMIC(CRichEditCtrlEx);
public:
	CRichEditCtrlEx();
	virtual	~CRichEditCtrlEx();

	static DWORD CALLBACK CStringFormatToRTF(DWORD dwCookie, LPBYTE pbBuff, LONG cb, LONG *pcb);
	static DWORD CALLBACK RTFFormatToCString(DWORD dwCookie, LPBYTE pbBuff, LONG cb, LONG *pcb);
	void SetRTF(CString strRTF);
	CString GetRTF();
	void AppendText(CHARFORMAT *pfmt, const char *text);
	CHARFORMAT&		CharFormat()	{ return m_cfDefault; }
	void Init(BOOL bEditable, CChatDlg* pParent);

private:
	CChatDlg* m_pParent;
	BOOL m_bEditable;
	CHARFORMAT m_cfDefault;

	DECLARE_MESSAGE_MAP()
public:
	afx_msg void OnSelectfont();

protected:
	afx_msg void OnCopy() { Copy(); }
	afx_msg void OnCut();
	afx_msg void OnPaste();
	afx_msg void OnSelectall() { SetSel(0, -1); }
	afx_msg void OnUndo();
	afx_msg void OnClear();

	afx_msg void OnChar(UINT nChar, UINT nRepCnt, UINT nFlags);
	afx_msg void OnRButtonDown(UINT nFlags, CPoint point);
	virtual BOOL PreTranslateMessage(MSG* pMsg);
	afx_msg void OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags);
	afx_msg void OnDropFiles(HDROP hDropInfo);
};

