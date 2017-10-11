#include "stdafx.h"
#include "resource.h"
#include "RichEditCtrlEx.h"
#include ".\richeditctrlex.h"
#include "chatdlg.h"
#include "ChatTab.h"
#include "RoomMainFrm.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

#define ID_RICH_UNDO                    101
#define ID_RICH_CUT                     102
#define ID_RICH_COPY                    103
#define ID_RICH_PASTE                   104
#define ID_RICH_CLEAR                   105
#define ID_RICH_SELECTALL               106
#define ID_RICH_SETFONT                 107

int strcchr(const char *in, int chr)
{
	int ret = 0;
	while (*in++)
		if (*(in - 1) == chr) ret++;
	return ret;
}

/////////////////////////////////////////////////////////////////////////////
// CRichEditCtrlEx
IMPLEMENT_DYNAMIC(CRichEditCtrlEx, CRichEditCtrl);

CRichEditCtrlEx::CRichEditCtrlEx() : CRichEditCtrl()
{
	memset(&m_cfDefault, 0, sizeof(m_cfDefault));
	m_cfDefault.cbSize = sizeof(CHARFORMAT);
	m_cfDefault.dwMask = CFM_BOLD | CFM_COLOR | CFM_FACE | CFM_ITALIC | CFM_SIZE | CFM_STRIKEOUT | CFM_UNDERLINE;
	m_cfDefault.crTextColor = RGB(0, 0, 0);
	_tcscpy(m_cfDefault.szFaceName, _T("宋体"));
	m_cfDefault.bPitchAndFamily = FF_SWISS;
	m_cfDefault.yHeight = 210; 

	m_bEditable = FALSE;
}

CRichEditCtrlEx::~CRichEditCtrlEx()
{
}

void CRichEditCtrlEx::AppendText(CHARFORMAT *pfmt, const char *text)
{
	int len = GetTextLengthEx(GTL_NUMCHARS,1200);

	SetSel(len, len);
	ReplaceSel(text);

	int end = GetTextLengthEx(GTL_NUMCHARS,1200);
	SetSel(len, end-2);

	if (pfmt)
	{
		SetSelectionCharFormat(*pfmt);
	}

	SetSel(end, end);

	HideSelection(TRUE, FALSE);
	LineScroll(strcchr(text, '\n'));
}

BEGIN_MESSAGE_MAP(CRichEditCtrlEx, CRichEditCtrl)
	ON_COMMAND(ID_RICH_COPY, OnCopy)
	ON_COMMAND(ID_RICH_CUT, OnCut)
	ON_COMMAND(ID_RICH_PASTE, OnPaste)
	ON_COMMAND(ID_RICH_SELECTALL, OnSelectall)
	ON_COMMAND(ID_RICH_UNDO, OnUndo)
	ON_COMMAND(ID_RICH_CLEAR, OnClear)
	ON_COMMAND(ID_RICH_SETFONT, OnSelectfont)

	ON_WM_CHAR()
	ON_WM_RBUTTONDOWN()
	ON_WM_KEYDOWN()
	ON_WM_DROPFILES()
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CRichEditCtrlEx message handlers

void CRichEditCtrlEx::OnRButtonDown(UINT nFlags, CPoint point)
{
	//设置为焦点
	SetFocus();
	//创建一个弹出式菜单
	CMenu popmenu;
	popmenu.CreatePopupMenu();
	//添加菜单项目
	if(m_bEditable)
	{
		popmenu.AppendMenu(0, ID_RICH_UNDO, "撤销");
		popmenu.AppendMenu(0, MF_SEPARATOR);
		popmenu.AppendMenu(0, ID_RICH_CUT, "剪切");
	}
	popmenu.AppendMenu(0, ID_RICH_COPY, "复制");
	if(m_bEditable)
	{
		popmenu.AppendMenu(0, ID_RICH_PASTE, "粘贴");
		popmenu.AppendMenu(0, ID_RICH_CLEAR, "清空");
		popmenu.AppendMenu(0, MF_SEPARATOR);
	}
	popmenu.AppendMenu(0, ID_RICH_SELECTALL, "全选");
	if(m_bEditable)
	{
		popmenu.AppendMenu(0, MF_SEPARATOR);
		popmenu.AppendMenu(0, ID_RICH_SETFONT, "选择字体");
	}
	//初始化菜单项
	UINT nUndo=(CanUndo() ? 0 : MF_GRAYED );
	popmenu.EnableMenuItem(ID_RICH_UNDO, MF_BYCOMMAND|nUndo);

	UINT nSel=((GetSelectionType()!=SEL_EMPTY) ? 0 : MF_GRAYED) ;
	popmenu.EnableMenuItem(ID_RICH_CUT, MF_BYCOMMAND|nSel);
	popmenu.EnableMenuItem(ID_RICH_COPY, MF_BYCOMMAND|nSel);
	popmenu.EnableMenuItem(ID_RICH_CLEAR, MF_BYCOMMAND|nSel);
	
	UINT nPaste=(CanPaste() ? 0 : MF_GRAYED) ;
	popmenu.EnableMenuItem(ID_RICH_PASTE, MF_BYCOMMAND|nPaste);

	//显示菜单
	CPoint pt;
	GetCursorPos(&pt);
	popmenu.TrackPopupMenu(TPM_RIGHTBUTTON, pt.x, pt.y, this);
	popmenu.DestroyMenu();

	//CRichEditCtrl::OnRButtonUp(nFlags, point);
}

///设置字体
void CRichEditCtrlEx::OnSelectfont() 
{
	CHARFORMAT cf;
	LOGFONT lf;
	memset(&cf, 0, sizeof(CHARFORMAT));
	memset(&lf, 0, sizeof(LOGFONT));
	//判断是否选择了内容
	BOOL m_bSelect = (GetSelectionType() != SEL_EMPTY) ? TRUE : FALSE;
	if (m_bSelect)
	{
		GetSelectionCharFormat(cf);
	}
	else
	{
		GetDefaultCharFormat(cf);
	}
	//得到相关字体属性
	BOOL bIsBold = cf.dwEffects & CFE_BOLD;
	BOOL bIsItalic = cf.dwEffects & CFE_ITALIC;
	BOOL bIsUnderline = cf.dwEffects & CFE_UNDERLINE;
	BOOL bIsStrickout = cf.dwEffects & CFE_STRIKEOUT;
	//设置属性
	lf.lfCharSet = cf.bCharSet;
	lf.lfHeight = cf.yHeight/15;
	lf.lfPitchAndFamily = cf.bPitchAndFamily;
	lf.lfItalic = bIsItalic;
	lf.lfWeight = (bIsBold ? FW_BOLD : FW_NORMAL);
	lf.lfUnderline = bIsUnderline;
	lf.lfStrikeOut = bIsStrickout;
	sprintf(lf.lfFaceName, cf.szFaceName);
	
	CFontDialog dlg(&lf,CF_SCREENFONTS,0,GetParentOwner());
	dlg.m_cf.Flags = dlg.m_cf.Flags | CF_EFFECTS;	
	dlg.m_cf.rgbColors = cf.crTextColor;
	if (dlg.DoModal() == IDOK)
	{
		dlg.GetCharFormat(cf);//获得所选字体的属性
		if (m_bSelect) 
			SetSelectionCharFormat(cf);	//为选定的内容设定所选字体
		else
			SetWordCharFormat(cf);	//为将要输入的内容设定字体
	
		SetDefaultCharFormat(cf);

		( ( CChatTab* )( m_pParent->GetParent() ) )->setCharFormat( cf );
	}
}

////返回RTF格式化字符串
CString CRichEditCtrlEx::GetRTF()
{
	CString sRTF = "";
	EDITSTREAM es;
	es.dwError = 0;
	es.pfnCallback = RTFFormatToCString;		// Set the callback
	es.dwCookie = (DWORD) &sRTF;	// so sRTF receives the string
	StreamOut(SF_RTF, es);			// Call CRichEditCtrl::StreamOut to get the string.
	return sRTF;
}

void CRichEditCtrlEx::SetRTF(CString strRTF)
{
	int begin = GetTextLengthEx(GTL_NUMCHARS, 1200);

	EDITSTREAM es;
	es.dwError = 0;
	es.pfnCallback = CStringFormatToRTF;
	es.dwCookie = (DWORD) &strRTF;
	StreamIn(SF_RTF|SFF_SELECTION, es);	// Do it.

	int end = GetTextLengthEx(GTL_NUMCHARS, 1200);
	SetSel(begin, end);
	PARAFORMAT2 pf;
	pf.cbSize = sizeof(PARAFORMAT2);
	pf.dwMask = PFM_STARTINDENT;
	pf.dxStartIndent = 210;
	this->SetParaFormat( pf );
}

////StreamOut的回调函数
DWORD CALLBACK CRichEditCtrlEx::RTFFormatToCString(DWORD dwCookie, LPBYTE pbBuff, LONG cb, LONG *pcb)
{
	CString *psEntry = (CString*) dwCookie;
	///把数据存放在一个临时变量里
	CString tmpEntry = "";
	tmpEntry = (CString) pbBuff;
	//把实际有效的数据返回
	*psEntry += tmpEntry.Left(cb);
	return 0;
}

////StreamIn的回调函数 *pcb是实际输入的字节数,cb是一次输入的最大字节数
DWORD CALLBACK CRichEditCtrlEx::CStringFormatToRTF(DWORD dwCookie, LPBYTE pbBuff, LONG cb, LONG *pcb)
{
	CString *pstr = (CString *) dwCookie;
	////若要存放到RichEdit的数据长度小于一次能存放的最大长度,则
	////一次性输入到RichEdit中
	if (pstr->GetLength() < cb)
	{
		*pcb = pstr->GetLength();
		memcpy(pbBuff, (LPCSTR) *pstr, *pcb);
		pstr->Empty();
	}
	////若输入的数据太多,则一次输入cb字节到RichEdit中
	///StreamIn回递归调用这个函数
	else
	{
		*pcb = cb;
		memcpy(pbBuff, (LPCSTR) *pstr, *pcb);
		*pstr = pstr->Right(pstr->GetLength() - cb);
	}
	return 0;
}

void CRichEditCtrlEx::OnChar(UINT nChar, UINT nRepCnt, UINT nFlags)
{
	if (nChar == 13)
	{
		int len = GetWindowTextLength();
		m_pParent->OnBnClickedSend();
	}
	else
	{
        CRichEditCtrl::OnChar(nChar, nRepCnt, nFlags);
		m_pParent->sendWritingMessageFlag();
	}
}

void CRichEditCtrlEx::Init(BOOL bEditable, CChatDlg* pParent)
{
	m_bEditable = bEditable;
	m_pParent = pParent;
	if( this->m_bEditable )
	{
		CHARFORMAT cf = ((CChatTab*)(m_pParent->GetParent()))->getCharFormat();
		if( SetDefaultCharFormat( cf ) )
		{
			SetWordCharFormat( cf );
			m_cfDefault = cf;
		}
		else
		{
			SetDefaultCharFormat( m_cfDefault );
			SetWordCharFormat( m_cfDefault );
			((CChatTab*)(m_pParent->GetParent()))->setCharFormat( m_cfDefault );
		}
	}
	else
	{
		SetDefaultCharFormat( m_cfDefault );
		SetWordCharFormat( m_cfDefault );
	}
	//this->SetBackgroundColor(FALSE, RGB(217, 235, 255));
}

BOOL CRichEditCtrlEx::PreTranslateMessage(MSG* pMsg)
{
	if( pMsg->message == WM_KEYDOWN )
	{
		if( pMsg->wParam == VK_F4 )
		{
			::PostMessage( GetParentOwner()->GetSafeHwnd(), pMsg->message, pMsg->wParam, pMsg->lParam );
			return TRUE;
		}
	}

	return CRichEditCtrl::PreTranslateMessage(pMsg);
}

void CRichEditCtrlEx::OnKeyDown(UINT nChar, UINT nRepCnt, UINT nFlags)
{
	CRichEditCtrl::OnKeyDown(nChar, nRepCnt, nFlags);
	m_pParent->sendWritingMessageFlag();
}

void CRichEditCtrlEx::OnCut() 
{ 
	Cut();
	m_pParent->sendWritingMessageFlag();
}

void CRichEditCtrlEx::OnPaste() 
{ 
	Paste(); 
	m_pParent->sendWritingMessageFlag();
}

void CRichEditCtrlEx::OnUndo() 
{ 
	Undo(); 
	m_pParent->sendWritingMessageFlag();
}

void CRichEditCtrlEx::OnClear() 
{ 
	Clear(); 
	m_pParent->sendWritingMessageFlag();
}

void CRichEditCtrlEx::OnDropFiles(HDROP hDropInfo)
{
	char buff[MAX_PATH];
	UINT nCount = DragQueryFile( hDropInfo, 0xFFFFFFFF, buff, sizeof(buff) );
	for( int i = 0; i < nCount; i++ )
	{
		DragQueryFile( hDropInfo, i, buff, MAX_PATH );
		TRACE1( "drag filename: %s\n", buff );
		((CMeetingRoomFrame*)GetParentOwner())->dragFileToSend( buff, m_pParent->m_ChatWithId );
	}

	CRichEditCtrl::OnDropFiles(hDropInfo);
}
