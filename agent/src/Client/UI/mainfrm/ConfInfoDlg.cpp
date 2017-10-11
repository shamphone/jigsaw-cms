// ui\ConfInfoDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "ConfInfoDlg.h"
#include ".\confinfodlg.h"
#include "..\..\Model\Conference.h"

// CConfInfoDlg 对话框

IMPLEMENT_DYNAMIC(CConfInfoDlg, CDialogEx)
CConfInfoDlg::CConfInfoDlg(Conference* pConference, CWnd* pParent /*=NULL*/)
	: CDialogEx(CConfInfoDlg::IDD, pParent)
{
	m_pConference = pConference;
	m_strTitle = "查看会议信息";
	m_strName = "会议名称：";
	m_strDesc = "会议描述：";
}

CConfInfoDlg::~CConfInfoDlg()
{
}

void CConfInfoDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control( pDX, IDC_CONF_DOCS, m_ListCtrl );
}


BEGIN_MESSAGE_MAP(CConfInfoDlg, CDialogEx)
	ON_WM_CTLCOLOR()
	ON_NOTIFY(NM_CLICK, IDC_CONF_DOCS, OnNMClickConfDocs)
END_MESSAGE_MAP()


// CConfInfoDlg 消息处理程序

BOOL CConfInfoDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();
	if( m_pConference == NULL )
	{
		ASSERT( FALSE );
		return FALSE;
	}
	GetDlgItem(IDC_CONF_NAME)->SetWindowText( m_pConference->m_dbConference.m_sName.c_str() );
	GetDlgItem(IDC_CONF_DESC)->SetWindowText( m_pConference->m_dbConference.m_sDescription.c_str() );
	GetDlgItem(IDC_CONF_START)->SetWindowText( m_pConference->m_dbConference.m_tmStartTime.Format("%Y年%m月%d日 %H:%M") );
	GetDlgItem(IDC_CONF_END)->SetWindowText( m_pConference->m_dbConference.m_tmEndTime.Format("%Y年%m月%d日 %H:%M") );
	GetDlgItem(IDC_STATIC1)->SetWindowText(m_strName);
	GetDlgItem(IDC_STATIC2)->SetWindowText(m_strDesc);
	SetWindowText( m_strTitle );

	CRect rc;
	m_ListCtrl.GetClientRect( &rc );
	m_ListCtrl.InsertColumn( 0, "", LVCFMT_LEFT, rc.Width() - 20, 0 );
	ListView_SetExtendedListViewStyle( m_ListCtrl.GetSafeHwnd(), LVS_EX_TRACKSELECT );
	CString strDocDesc = m_pConference->m_dbConference.m_sConfFileDesc.c_str();
	CString strDocUrls = m_pConference->m_dbConference.m_sConfFileURL.c_str();
	int pos = 0;
	int pos1 = 0;
	for( ; pos < strDocDesc.GetLength() && pos1 < strDocUrls.GetLength(); )
	{
		int n = strDocDesc.Find( ',', pos );
		int m = strDocUrls.Find( ',', pos1 );
		if( n < 0 || m < 0 )
			break;		
		m_ListCtrl.InsertItem( m_ListCtrl.GetItemCount(), strDocDesc.Mid( pos, n - pos ) );
		m_confDocs.push_back( strDocUrls.Mid( pos1, m - pos1 ).GetString() );
		pos = n + 1;
		pos1 = m + 1;
	}

	this->m_sDescription = m_pConference->m_dbConference.m_sName.c_str();
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

HBRUSH CConfInfoDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);
	if( pWnd->GetDlgCtrlID() == IDC_STATIC1
		|| pWnd->GetDlgCtrlID() == IDC_STATIC2
		|| pWnd->GetDlgCtrlID() == IDC_CONF_NAME
		|| pWnd->GetDlgCtrlID() == IDC_CONF_DESC
		|| pWnd->GetDlgCtrlID() == IDC_CONF_START
		|| pWnd->GetDlgCtrlID() == IDC_CONF_END )
	{
		pDC->SetBkMode( TRANSPARENT );
		pDC->SetTextColor( m_crText );
		hbr = m_brush;	
	}
	return hbr;
}

void CConfInfoDlg::OnNMClickConfDocs(NMHDR *pNMHDR, LRESULT *pResult)
{
	*pResult = 0;
	CPoint pt;
	GetCursorPos( &pt );
	m_ListCtrl.ScreenToClient( &pt );
	UINT uFlag;
	int nItem = m_ListCtrl.HitTest( pt, &uFlag );
	if( (nItem != -1) && (uFlag & LVHT_ONITEMLABEL) )
	{
		ShellExecute(NULL, "open", m_confDocs.at(nItem).c_str(), NULL, NULL, SW_SHOWNORMAL);
        TRACE1( "%s\n", m_confDocs.at(nItem).c_str() );
	}
}
