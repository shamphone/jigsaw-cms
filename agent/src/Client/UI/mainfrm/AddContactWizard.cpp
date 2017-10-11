// D:\Lyvc\src\Client\UI\MainFrm\AddContactWizard.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "AddContactWizard.h"
#include ".\addcontactwizard.h"
#include "..\..\Model\LServer.h"
#include "..\..\Model\Contact\ContactMgr.h"
#include "..\..\Model\Contact\ContactGroup.h"
#include "ContactDlg.h"

// CAddContactWizard

IMPLEMENT_DYNAMIC(CAddContactWizard, CPropertySheet)
CAddContactWizard::CAddContactWizard(UINT nIDCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(nIDCaption, pParentWnd, iSelectPage)
{
	m_pServer = NULL;
}

CAddContactWizard::CAddContactWizard(LPCTSTR pszCaption, CWnd* pParentWnd, UINT iSelectPage)
	:CPropertySheet(pszCaption, pParentWnd, iSelectPage)
{
	m_pServer = NULL;
}

CAddContactWizard::~CAddContactWizard()
{
	m_pServer = NULL;
}


BEGIN_MESSAGE_MAP(CAddContactWizard, CPropertySheet)
	ON_COMMAND(IDCANCEL, cancel)
	ON_WM_PAINT()
END_MESSAGE_MAP()


// CAddContactWizard 消息处理程序

void CAddContactWizard::setServer(LServer* pServer)
{
	this->m_pServer = pServer;
}

void CAddContactWizard::searchContact(string email)
{
	CAddContactStep2* pPage = (CAddContactStep2*) GetPage(1);
	if( email == m_pServer->getEmail() )
	{
		pPage->setShowText("您不能将自己加为联系人。");
		return;
	}
	ContactMgr* pContactMgr = m_pServer->getContactMgr();
	if( pContactMgr != NULL )
	{
		if(!pContactMgr->isContactByEmail(email))
		{
			pContactMgr->cmdSearchContact(email);
			pPage->setShowText("正在获取该用户信息... ...");
		}
		else
			pPage->setShowText("该用户已在你的联系人中。");
	}
	else
		pPage->setShowText("此功能当前不可用。");
}

void CAddContactWizard::userNotFount(string email)
{
	CAddContactStep2* pPage = (CAddContactStep2*) GetPage(1);
	CString s = email.c_str();
	s = "无法找到Email为“" + s + "”的用户！";
	pPage->userNotFound(s);
}

void CAddContactWizard::userInfo(string realName, string email, int status, __int64 uid)
{
	CAddContactStep2* pPage = (CAddContactStep2*) GetPage(1);
	pPage->userInfo(realName, email, status, uid);
}

bool CAddContactWizard::applyContact(__int64 uid)
{
	ContactMgr* pContactMgr = m_pServer->getContactMgr();
	if( pContactMgr != NULL )
	{
		HTREEITEM hItem = pContactMgr->m_pContactDlg->m_TreeCtrl.GetSelectedItem();
		ContactGroup* pGroup = pContactMgr->getContactGroupByTreeItem( hItem );
		if(pGroup != NULL)
			pContactMgr->cmdApplyContact(uid, m_pServer->getRealName(), pGroup->m_Id);
		else
			pContactMgr->cmdApplyContact(uid, m_pServer->getRealName(), 0);
		return true;
	}
	return false;
}

void CAddContactWizard::cancel()
{
	if(this->GetActiveIndex() == 2)
		this->SetActivePage(0);
	else
		this->OnClose();
}

void CAddContactWizard::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB(228, 240, 254) );
	// 不为绘图消息调用 CPropertySheet::OnPaint()
}


BOOL CAddContactWizard::OnInitDialog()
{
	BOOL bResult = CPropertySheet::OnInitDialog();
    this->createCustomButton( ID_WIZBACK, m_btnBack );
	this->createCustomButton( ID_WIZNEXT, m_btnNext );
	this->createCustomButton( ID_WIZFINISH, m_btnFinish );
	this->createCustomButton( IDCANCEL, m_btnCancel );
	m_btnBack.EnableWindow(FALSE);
	CRect rc;
	GetWindowRect( &rc );
	//this->MoveWindow( rc );
	CWnd* pWnd = this->GetDlgItem( 0x3026 );
	if( pWnd->GetSafeHwnd() )
		pWnd->ShowWindow(FALSE);
	this->SetWindowText("添加联系人");
	return bResult;
}

void CAddContactWizard::setWindowRect( int nPage )
{
	CRect rc;
	GetClientRect( &rc );
	rc.bottom = rc.bottom - 40;
	if( this->GetPage( nPage )->GetSafeHwnd() )
		this->GetPage( nPage )->MoveWindow(rc);
}

void CAddContactWizard::drawHead( CDC* pDC, CRect rc, CString sText )
{
	pDC->FillSolidRect( rc, RGB(240, 245, 250) );
	CBrush brush;
	brush.CreateSolidBrush( RGB(159, 177, 225) );
	pDC->FrameRect( rc, &brush );
	
	CBitmap bmpBK;
	bmpBK.LoadBitmap( IDB_CONTACTLIST );
	CDC bkDC;
	bkDC.CreateCompatibleDC( pDC );
	bkDC.SelectObject( &bmpBK );
	BITMAP bmp;
	bmpBK.GetBitmap(&bmp);

	CFont* pFont = GetFont();
	LOGFONT lf;
	lf.lfWeight = 210;
	pFont->GetLogFont( &lf );
	CFont font;
	font.CreateFontIndirect( &lf );

	pDC->BitBlt( 1, 1, rc.Width() - 2, 49, &bkDC, bmp.bmWidth - rc.Width(), 0, SRCCOPY );
	pFont = pDC->SelectObject( &font );
	pDC->SetBkMode(TRANSPARENT);
	pDC->SetTextColor( RGB(4, 37, 132) );
	pDC->DrawText( sText, CRect(10, 20, rc.Width() - 60, 40), DT_LEFT );

	pDC->SelectObject( pFont );
	font.DeleteObject();
}

void CAddContactWizard::createCustomButton( UINT nID, CLinkButton& btn )
{
	CRect rc;
	CString sText;
	CWnd* pWnd = GetDlgItem( nID );
	pWnd->GetWindowRect( &rc );
	ScreenToClient( &rc );
	rc.bottom = rc.top + 22;
	rc.right = rc.left + 74;
	pWnd->GetWindowText(sText);
	CFont* pFont = this->GetFont();
	LOGFONT lf;
	pFont->GetLogFont( &lf );
	lf.lfWeight = 180;

    btn.Create( sText, pWnd->GetStyle(), rc, this, nID );
	btn.m_fNormal.CreateFontIndirect( &lf );
	lf.lfUnderline = TRUE;
	btn.fUnderline.CreateFontIndirect( &lf );
	btn.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	GetDlgItem(nID)->ShowWindow(FALSE);
}
