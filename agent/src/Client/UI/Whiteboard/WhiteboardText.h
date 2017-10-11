#pragma once

class CEditEx;

class CWhiteboardText : public CRectTracker
{
public:
	CWhiteboardText( CWnd* pParent );
	~CWhiteboardText(void);

	void Draw( CDC* pDC ) const;
	CString getText();
	void setText( CString str );
	void showWindow( BOOL bShow );
	void setFontAndColor(LOGFONT lf, COLORREF color);

private:
	CEditEx* m_pEdit;
	CWnd* m_pParent;
};
