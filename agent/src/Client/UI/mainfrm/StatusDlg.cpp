// D:\Lyvc\src\Client\UI\mainfrm\StatusDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "StatusDlg.h"
#include ".\statusdlg.h"
#include "ContactDlg.h"

// CStatusDlg 对话框

IMPLEMENT_DYNAMIC(CStatusDlg, CDialog)
CStatusDlg::CStatusDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CStatusDlg::IDD, pParent)
{
}

CStatusDlg::~CStatusDlg()
{
}

void CStatusDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_ADDCONTACT_BTN, m_btnAddContact);
}

BEGIN_MESSAGE_MAP(CStatusDlg, CDialog)
	ON_WM_SIZE()
	ON_BN_CLICKED(IDC_ADDCONTACT_BTN, OnBnClickedAddcontactBtn)
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CStatusDlg 消息处理程序

void CStatusDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	if(GetDlgItem(IDC_ADDCONTACT_BTN)->GetSafeHwnd())
	{
		GetDlgItem(IDC_ADDCONTACT_BTN)->MoveWindow(cx - 90, 7, 84, 22);
	}
}

void CStatusDlg::OnBnClickedAddcontactBtn()
{
	((CContactDlg*)GetParent())->OnAddContact();
}

void CStatusDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect(&rc);
	
	CDC memDC;
	memDC.CreateCompatibleDC(&dc);
	CBitmap bmp;
	bmp.CreateCompatibleBitmap(&dc, rc.Width(), rc.Height());
	memDC.SelectObject(&bmp);

	// 画背景
	CDC bkDC;
	bkDC.CreateCompatibleDC(&dc);
	CBitmap back;
	back.LoadBitmap(IDB_STATUSBK);
	bkDC.SelectObject( &back );
	int x = 0;
	while( x < rc.Width() ) 
	{
		memDC.BitBlt( x, 0, 120, 36, &bkDC, 0, 0, SRCCOPY);
 		x += 120;
 	}	
	CBitmap corner;
	corner.LoadBitmap( IDB_CORNER );
	bkDC.SelectObject( &corner );
	memDC.BitBlt( rc.Width() - 4, 0, 4, 36, &bkDC, 0, 0, SRCCOPY);

	// 写当前用户
	LOGFONT lf;
	CFont* font = GetFont();
	font->GetLogFont(&lf);
	lf.lfHeight = 15;
	CFont font1;
	font1.CreateFontIndirect(&lf);
	CFont* oldFont = memDC.SelectObject(&font1);
	int nOldMode = memDC.SetBkMode(TRANSPARENT);
	memDC.DrawText( "当前用户: "+m_sDisplayName, CRect(10, 9, rc.right - 90, 29), DT_LEFT);
	memDC.SetBkMode(nOldMode);
	memDC.SelectObject(oldFont);

	dc.BitBlt(0, 0, rc.Width(), rc.Height(), &memDC, 0, 0, SRCCOPY);
	
	// 不为绘图消息调用 CDialog::OnPaint()
}

BOOL CStatusDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_btnAddContact.setBitmaps(IDB_ADDCONTACT, IDB_ADDCONTACT2, IDB_ADDCONTACT3);
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}
