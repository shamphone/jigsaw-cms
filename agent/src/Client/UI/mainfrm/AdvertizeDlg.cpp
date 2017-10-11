// UI\MainFrm\AdvertizeDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "AdvertizeDlg.h"
#include ".\advertizedlg.h"
#include "AdBGDlg.h"

// CAdvertizeDlg 对话框

IMPLEMENT_DYNCREATE(CAdvertizeDlg, CDHtmlDialog)

CAdvertizeDlg::CAdvertizeDlg(CWnd* pParent /*=NULL*/)
	: CDHtmlDialog(CAdvertizeDlg::IDD, CAdvertizeDlg::IDH, pParent)
{
	m_sUrl = "";
}

CAdvertizeDlg::~CAdvertizeDlg()
{
}

void CAdvertizeDlg::DoDataExchange(CDataExchange* pDX)
{
	CDHtmlDialog::DoDataExchange(pDX);
}

BOOL CAdvertizeDlg::OnInitDialog()
{
	CDHtmlDialog::OnInitDialog();
	return TRUE;  // 除非将焦点设置到控件，否则返回 TRUE
}

BEGIN_MESSAGE_MAP(CAdvertizeDlg, CDHtmlDialog)
END_MESSAGE_MAP()

BEGIN_DHTML_EVENT_MAP(CAdvertizeDlg)
	DHTML_EVENT_ONCLICK(_T("ButtonOK"), OnButtonOK)
	DHTML_EVENT_ONCLICK(_T("ButtonCancel"), OnButtonCancel)
END_DHTML_EVENT_MAP()



// CAdvertizeDlg 消息处理程序

HRESULT CAdvertizeDlg::OnButtonOK(IHTMLElement* /*pElement*/)
{
	OnOK();
	return S_OK;  // 除非将焦点设置到控件，否则返回 TRUE
}

HRESULT CAdvertizeDlg::OnButtonCancel(IHTMLElement* /*pElement*/)
{
	OnCancel();
	return S_OK;  // 除非将焦点设置到控件，否则返回 TRUE
}

HRESULT STDMETHODCALLTYPE CAdvertizeDlg::ShowContextMenu(DWORD /*dwID*/, POINT *ppt, IUnknown* /*pcmdtReserved*/, IDispatch* /*pdispReserved*/)
{
	return S_OK;
}

void CAdvertizeDlg::OnDocumentComplete(LPDISPATCH pDisp, LPCTSTR szUrl)
{
	CDHtmlDialog::OnDocumentComplete(pDisp, szUrl);
	if( m_sUrl != "" )
	{
		CAdBGDlg* pDlg = (CAdBGDlg*) GetParent();
		pDlg->showAdDlg();
	}
}
