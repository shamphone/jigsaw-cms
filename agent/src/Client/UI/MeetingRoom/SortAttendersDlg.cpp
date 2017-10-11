// UI\MeetingRoom\SortAttendersDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "SortAttendersDlg.h"
#include ".\sortattendersdlg.h"
#include "AttenderListDlg.h"

// CSortAttendersDlg 对话框

IMPLEMENT_DYNAMIC(CSortAttendersDlg, CDialogEx)
CSortAttendersDlg::CSortAttendersDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CSortAttendersDlg::IDD, pParent)
{
	m_pAttenderListDlg = NULL;
}

CSortAttendersDlg::~CSortAttendersDlg()
{
}

void CSortAttendersDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
	DDX_Control( pDX, IDC_SORTBYNAME_BTN, this->m_btnSortByName );
	DDX_Control( pDX, IDC_DOWN_BTN, this->m_btnDown );
	DDX_Control( pDX, IDC_UP_BTN, this->m_btnUp );
}


BEGIN_MESSAGE_MAP(CSortAttendersDlg, CDialogEx)
	ON_BN_CLICKED(IDC_UP_BTN, OnBnClickedUpBtn)
	ON_BN_CLICKED(IDC_DOWN_BTN, OnBnClickedDownBtn)
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDC_SORTBYNAME_BTN, OnBnClickedSortbynameBtn)
END_MESSAGE_MAP()


// CSortAttendersDlg 消息处理程序

void CSortAttendersDlg::OnBnClickedUpBtn()
{
	CListBox* pListBox = (CListBox*) GetDlgItem(IDC_ATTENDERS);
	int n = pListBox->GetCurSel();
	if( n == LB_ERR || n == 0)
		return;
	CString str;
	__int64* pId1 = (__int64*) pListBox->GetItemData( n );
	__int64* pId2 = (__int64*) pListBox->GetItemData( n - 1 );
	pListBox->GetText(n, str);
	pListBox->DeleteString(n);
	n = pListBox->InsertString(n - 1, str);
	pListBox->SetItemData( n, (DWORD) pId1 );
	pListBox->SetCurSel(n);

	m_pAttenderListDlg->swapConfUser( *pId2, *pId1 );
}

void CSortAttendersDlg::OnBnClickedDownBtn()
{
	CListBox* pListBox = (CListBox*) GetDlgItem(IDC_ATTENDERS);
	int n = pListBox->GetCurSel();
	if( n == LB_ERR || n == pListBox->GetCount() - 1)
		return;
	CString str;
	__int64* pId1 = (__int64*) pListBox->GetItemData( n );
	__int64* pId2 = (__int64*) pListBox->GetItemData( n + 1 );
	pListBox->GetText(n, str);
	pListBox->DeleteString(n);
	n = pListBox->InsertString(n + 1, str);
	pListBox->SetItemData( n, (DWORD) pId1 );
	pListBox->SetCurSel( n );

	m_pAttenderListDlg->swapConfUser( *pId1, *pId2 );
}

BOOL CSortAttendersDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();
	m_pAttenderListDlg->getAttendersIdAndNameForSort( &m_attenderNames, &m_attenderIds );

	this->m_btnSortByName.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE);
	this->m_btnUp.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE);
	this->m_btnDown.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE);

	CRect rc;
	GetClientRect( &rc );
	this->m_btnSortByName.MoveWindow( 3, rc.bottom - 32, 74, 22 );
	this->m_btnUp.MoveWindow( 80, rc.bottom - 32, 74, 22 );
	this->m_btnDown.MoveWindow( 160, rc.bottom - 32, 74, 22 );
	fillListBox();

	this->m_sDescription = "选中参会人员，按“上移”，“下移”按钮进行排序。";
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CSortAttendersDlg::fillListBox(bool bSortByName)
{
	CListBox* pListBox = (CListBox*) GetDlgItem(IDC_ATTENDERS);
	pListBox->SetItemHeight( 0, 18 );
	int n;
	for( UINT i = 0; i < m_attenderNames.size(); i++ )
	{
		if( bSortByName )
			n = pListBox->AddString( m_attenderNames.at(i).c_str() );
		else
        	n = pListBox->InsertString( i, m_attenderNames.at(i).c_str() );
		__int64* pId = new __int64(m_attenderIds.at(i));
		pListBox->SetItemData( n, (DWORD)pId );
	}
}

void CSortAttendersDlg::deleteUser(string name, __int64 uid)
{
	CListBox* pListBox = (CListBox*) GetDlgItem(IDC_ATTENDERS);
	int ret = pListBox->FindString( -1, name.c_str() );
	while( ret != LB_ERR )
	{
		__int64* pId = (__int64*) pListBox->GetItemData( ret );
		if( *pId == uid )
		{
			pListBox->DeleteString( ret );
			delete pId;
			break;
		}
		ret = pListBox->FindString( ret, name.c_str() );
	}

	vector<string>::iterator it;
	if( (it = find( m_attenderNames.begin(), m_attenderNames.end(), name )) != m_attenderNames.end() ) 
		m_attenderNames.erase( it );
	vector<__int64>::iterator it1;
	if( (it1 = find( m_attenderIds.begin(), m_attenderIds.end(), uid )) != m_attenderIds.end() )
		m_attenderIds.erase( it1 );
}

void CSortAttendersDlg::OnDestroy()
{
	CDialogEx::OnDestroy();

	CListBox* pListBox = (CListBox*) GetDlgItem(IDC_ATTENDERS);
	int count = pListBox->GetCount();
	for( int i = 0; i < count; i++ )
	{
		__int64* pId = (__int64*) pListBox->GetItemData(i);
		delete pId;
	}
}

void CSortAttendersDlg::OnBnClickedSortbynameBtn()
{
	CListBox* pListBox = (CListBox*) GetDlgItem(IDC_ATTENDERS);
	int count = pListBox->GetCount();
	if( count < 2 )
		return;
	for( int i = 0; i < count; i++ )
	{
		__int64* pId = (__int64*) pListBox->GetItemData(i);
		delete pId;
	}
	pListBox->ResetContent();
	fillListBox(true);
	count = pListBox->GetCount();
	vector<__int64> ids;
	for( int i = 0; i < count; i++ )
	{
		__int64* pId = (__int64*) pListBox->GetItemData(i);
		ids.push_back( *pId );
	}
	this->m_pAttenderListDlg->sortAttenders( ids );
}
