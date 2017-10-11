// ui\mainfrm\ContactListDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "ContactListDlg.h"
#include ".\contactlistdlg.h"

// CContactListDlg 对话框

IMPLEMENT_DYNAMIC(CContactListDlg, CDialogEx)
CContactListDlg::CContactListDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CContactListDlg::IDD, pParent)
{
	m_strTitle = "";
	m_strMessage = "";
}

CContactListDlg::~CContactListDlg()
{
}

void CContactListDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_CONTACT_LIST, m_ListCtrl);
}


BEGIN_MESSAGE_MAP(CContactListDlg, CDialogEx)
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
	ON_WM_CLOSE()
	ON_BN_CLICKED(IDCANCEL, OnBnClickedCancel)
	ON_WM_MOVE()
	ON_BN_CLICKED(IDC_CHECKALL, OnBnClickedCheckall)
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// CContactListDlg 消息处理程序

void CContactListDlg::OnBnClickedOk()
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( m_ListCtrl.GetCheck(i) )
		{
			m_selectedUserIds.push_back(*((USERID*)(m_ListCtrl.GetItemData(i))));
		}
	}
	clearList();
	if( m_selectedUserIds.size() > 0 )
		OnOK();
	else
		OnCancel();
}

BOOL CContactListDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();

	this->SetWindowText(this->m_strTitle);

	m_ImageList.Create(20, 18, ILC_COLOR16, 4, 1);
	CBitmap bm;
	bm.LoadBitmap(IDB_CONTACT_TREE);
	m_ImageList.Add(&bm, RGB(0, 0, 0));
	m_ListCtrl.SetImageList(&m_ImageList,LVSIL_SMALL);
    ListView_SetExtendedListViewStyle( m_ListCtrl.GetSafeHwnd(), LVS_EX_CHECKBOXES );
	fillList();

	GetWindowRect(&m_showRect);
	m_brush.CreateSolidBrush( RGB( 230, 240, 250 ) );
    this->m_sDescription = this->m_strMessage;
	return TRUE;  // return TRUE unless you set the focus to a control
}

void CContactListDlg::OnClose()
{
	clearList();
	CDialogEx::OnClose();
}

void CContactListDlg::OnBnClickedCancel()
{
	clearList();
	OnCancel();
}

void CContactListDlg::fillList()
{
	int count = this->m_UserIds.size();
	for (int i = 0; i < count; i++)
	{
		__int64 id = this->m_UserIds.at(i);
		string name = this->m_UserNames.at(i);

		LV_ITEM lvitem;
		lvitem.mask = LVIF_TEXT | LVIF_IMAGE;
		lvitem.iItem = 0;
		lvitem.iSubItem = 0;
		CString str = name.c_str();
		lvitem.pszText = str.GetBuffer(str.GetLength());
		lvitem.iImage = 2;
		m_ListCtrl.InsertItem(&lvitem);

		__int64* itemData = new __int64(id);
		m_ListCtrl.SetItemData(0, (DWORD) itemData);
	}
}

void CContactListDlg::clearList()
{
	int count = this->m_ListCtrl.GetItemCount();
	for (int i = 0; i < count; i++)
	{
		__int64* id = (__int64*)this->m_ListCtrl.GetItemData(0);
		delete id;
		this->m_ListCtrl.DeleteItem(0);
	}
}

void CContactListDlg::OnMove(int x, int y)
{
	CDialogEx::OnMove(x, y);
	GetWindowRect(&m_showRect);
}

void CContactListDlg::OnBnClickedCheckall()
{
	if( ((CButton*) GetDlgItem(IDC_CHECKALL))->GetCheck() )
	{
		for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
		{
			m_ListCtrl.SetCheck(i);
		}
	}
	else
	{
		for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
		{
			m_ListCtrl.SetCheck(i, FALSE);
		}
	}
}

HBRUSH CContactListDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialogEx::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_CHECKALL )
	{
		pDC->SetTextColor( m_crText );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}	return hbr;
}
