//

#include "stdafx.h"
#include "Flvcc.h"
#include "FileTransferDlg.h"
#include "FileTransferMgrDlg.h"
#include ".\filetransferdlg.h"
#include "RoomMainFrm.h"
#include "Model\Room\FileTransferMgr.h"

// CFileTransferDlg 对话框

IMPLEMENT_DYNAMIC(CFileTransferDlg, CDialog)
CFileTransferDlg::CFileTransferDlg(CWnd* pParent /*=NULL*/, int type)
	: CDialog(CFileTransferDlg::IDD, pParent)
{
	m_pRoom = (CMeetingRoomFrame*)(pParent->GetParent()->GetParent());
	m_pFileTransferMgr = NULL;
	m_nType = type;
}

CFileTransferDlg::~CFileTransferDlg()
{
}

void CFileTransferDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_SENDLIST, m_ListCtrl);
	DDX_Control( pDX, IDC_CANCEL_SELECTEDTASK_BTN, m_btnCancelSel );
	DDX_Control( pDX, IDC_OPEN_RECEIVED, m_btnOpenReceived );
	DDX_Control( pDX, IDC_ADDTASK_BTN, m_btnAddTask );
}


BEGIN_MESSAGE_MAP(CFileTransferDlg, CDialog)
	ON_WM_CLOSE()
	ON_BN_CLICKED(IDC_SELECTALL, OnBnClickedSelectall)
	ON_BN_CLICKED(IDC_CANCEL_SELECTEDTASK_BTN, OnBnClickedCancelSelectedtaskBtn)
	ON_WM_DESTROY()
	ON_BN_CLICKED(IDC_OPEN_RECEIVED, OnBnClickedOpenReceived)
	ON_BN_CLICKED(IDC_ADDTASK_BTN, OnBnClickedAddtaskBtn)
	ON_MESSAGE( WM_SEND_NEXT_FILE, OnSendNextFile)
	ON_WM_PAINT()
	ON_WM_SIZE()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// CFileTransferDlg 消息处理程序

void CFileTransferDlg::OnBnClickedSelectall()
{
	if( ((CButton*) GetDlgItem(IDC_SELECTALL))->GetCheck() )
	{
		for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
			m_ListCtrl.SetCheck(i);
	}
	else
	{
		for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
			m_ListCtrl.SetCheck(i, FALSE);
	}
}

void CFileTransferDlg::OnBnClickedCancelSelectedtaskBtn()
{
	if( !m_pFileTransferMgr )
		return;

	for( int i = m_ListCtrl.GetItemCount() - 1; i >= 0; i-- )
	{
		if( m_ListCtrl.GetCheck( i ) )
		{
			if( m_ListCtrl.GetItemData( i ) != 0 )
			{
				__int64* pId = (__int64*) m_ListCtrl.GetItemData( i );
				if( m_nType == TYPE_SEND )
				{
					m_pFileTransferMgr->stopSendFile( *pId, m_ListCtrl.GetItemText( i, 1 ).GetString() );
				}
				else
				{
					m_pFileTransferMgr->stopReceiveFile( *pId, m_ListCtrl.GetItemText( i, 1 ).GetString() );
				}
				delete pId;
			}
			m_ListCtrl.DeleteItem( i );
		}
	}
	if( m_nType == TYPE_RECV )
	{
		m_pFileTransferMgr->recvNextFile();
	}
}

void CFileTransferDlg::OnBnClickedOpenReceived()
{
	if( !m_pFileTransferMgr )
		return;
	ShellExecute(NULL, "open", FileTransferMgr::getIncomeFileSavePosition().c_str(), NULL, NULL, SW_SHOWNORMAL);
}

void CFileTransferDlg::OnBnClickedAddtaskBtn()
{
	if( !m_pFileTransferMgr )
		return;
	if( m_pRoom->canSendFile() )
        m_pRoom->sendFile();
}

void CFileTransferDlg::OnClose()
{
	ShowWindow(SW_HIDE);
}

BOOL CFileTransferDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	if( m_nType == TYPE_SEND )
	{
		SetWindowText( "发送文件" );
		GetDlgItem(IDC_ADDTASK_BTN)->ShowWindow(TRUE);
	}
	else
	{
		SetWindowText( "接收文件" );
		GetDlgItem(IDC_ADDTASK_BTN)->ShowWindow(FALSE);
	}

	CRect rc;
	m_ListCtrl.GetWindowRect( &rc );
    ListView_SetExtendedListViewStyle(m_ListCtrl.GetSafeHwnd(), LVS_EX_CHECKBOXES|LVS_EX_FULLROWSELECT);
	if( m_nType == TYPE_SEND )
	{
		m_ListCtrl.InsertColumn(0, "接收者", LVCFMT_LEFT, rc.Width() / 6, 0);
		GetDlgItem(IDC_OPEN_RECEIVED)->ShowWindow(FALSE);
	}
	else
	{
		m_ListCtrl.InsertColumn(0, "发送者", LVCFMT_LEFT, rc.Width() / 6, 0);
	}
	m_ListCtrl.InsertColumn(1, "文件", LVCFMT_LEFT, rc.Width() / 2, 1);
	m_ListCtrl.InsertColumn(2, "大小(字节)", LVCFMT_LEFT, rc.Width() / 6, 2);
	m_ListCtrl.InsertColumn(3, "状态", LVCFMT_LEFT, rc.Width() / 7, 3);

	m_btnAddTask.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnOpenReceived.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btnCancelSel.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );

    m_brush.CreateSolidBrush( RGB(240, 245, 250) );
    m_brush1.CreateSolidBrush( RGB(228, 240, 254) );
	GetDlgItem(IDC_FILETRANSFER_PROGRESS)->ShowWindow(FALSE);
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CFileTransferDlg::OnDestroy()
{
	CDialog::OnDestroy();
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( m_ListCtrl.GetItemData(i) != 0 )
		{
			__int64* pId = (__int64*) m_ListCtrl.GetItemData(i);
			delete pId;
		}
	}
}

LRESULT CFileTransferDlg::OnSendNextFile( WPARAM wParam, LPARAM lParam )
{
	if( m_pFileTransferMgr )
		this->m_pFileTransferMgr->sendNextFile();
	return 0;
}

void CFileTransferDlg::setFileTransferMgr(FileTransferMgr* pFileTransferMgr)
{
	this->m_pFileTransferMgr = pFileTransferMgr;
	if( !pFileTransferMgr )
	{
		for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
		{
			if( m_ListCtrl.GetItemData(i) != 0 )
			{
				m_ListCtrl.SetItemText( i, 3, "终止传输" );
				__int64* pId = (__int64*) m_ListCtrl.GetItemData( i );
				delete pId;
				m_ListCtrl.SetItemData( i, 0 ); 
			}
		}
	}
}

void CFileTransferDlg::selectFileToSend(vector<__int64> ids)
{
	CFileDialog fdlg(TRUE, NULL, NULL, OFN_FILEMUSTEXIST|OFN_HIDEREADONLY, "所有文件(*.*)|*.*|", this);
	int nRet = fdlg.DoModal();
	if( m_pFileTransferMgr && nRet == IDOK )
	{
		CString sFullFileName = fdlg.GetPathName();
		ULONGLONG dwLength = getFileLength( sFullFileName );
		if( dwLength*ids.size() > 40*60*15*1024 )
		{
			CString str;
			str.Format("%d", dwLength*ids.size()/40/60/1024);
			if( MessageBox("预计您发送的文件将在"+str+"分钟后全部发送完成，是否继续？","LongCon VCS", MB_YESNO) != IDYES )
			{
				return;
			}
		}
		for( UINT i = 0; i < ids.size(); i++ )
		{
			if( m_pRoom->isUserInConference( ids.at(i) ) && (findItem( ids.at(i), sFullFileName ) == -1 ) )
			{
				m_pFileTransferMgr->applySendFile( ids.at(i), sFullFileName.GetString(), dwLength );
				addListItem( ids.at(i), m_pRoom->getUserNameById(ids.at(i)).c_str(), sFullFileName, dwLength, "等待回应" );
			}
			else
				TRACE2( "the same task exist %s,%d\n", sFullFileName, ids.at(i) );
		}
	}
}

void CFileTransferDlg::applySendFile( CString fileName, vector<__int64> ids )
{
	for( UINT i = 0; i < ids.size(); i++ )
	{
		if( m_pRoom->isUserInConference( ids.at(i) ) && (findItem( ids.at(i), fileName ) == -1 ) )
		{
			ULONGLONG dwLength = getFileLength( fileName );
			m_pFileTransferMgr->applySendFile( ids.at(i), fileName.GetString(), dwLength );
			addListItem( ids.at(i), m_pRoom->getUserNameById(ids.at(i)).c_str(), fileName, dwLength, "等待回应" );
		}
		else
			TRACE2( "the same task exist %s,%d\n", fileName, ids.at(i) );
	}
}

void CFileTransferDlg::setFileLength(int length)
{
	CProgressCtrl* pProgress = (CProgressCtrl*) GetDlgItem(IDC_FILETRANSFER_PROGRESS);
	pProgress->SetPos(0);
	pProgress->SetRange32( 0, length );
}

void CFileTransferDlg::notifyAgreeReceiveFile(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
		m_ListCtrl.SetItemText( n, 3, "等待发送" );
}

void CFileTransferDlg::notifyRejectReceiveFile(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "拒绝接收" );
		__int64* pId = (__int64*) m_ListCtrl.GetItemData( n );
		delete pId;
		m_ListCtrl.SetItemData( n, 0 ); 
	}
}

void CFileTransferDlg::notifyApplySendFile(__int64 uid, string senderName, string fileName, __int64 fileLength)
{
	addListItem( uid, senderName.c_str(), fileName.c_str(), fileLength, "等待接收" );
	((CFileTransferMgrDlg*)GetParent()->GetParent())->showRecvFileDlg();
}

void CFileTransferDlg::notifyStopSendFile(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "中止发送" );
		__int64* pId = (__int64*) m_ListCtrl.GetItemData( n );
		delete pId;
		m_ListCtrl.SetItemData( n, 0 ); 
	}
	GetDlgItem(IDC_FILETRANSFER_PROGRESS)->ShowWindow(FALSE);
}

void CFileTransferDlg::notifyStopRecvFile(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "中止接收" );
		__int64* pId = (__int64*) m_ListCtrl.GetItemData( n );
		delete pId;
		m_ListCtrl.SetItemData( n, 0 ); 
	}
	GetDlgItem(IDC_FILETRANSFER_PROGRESS)->ShowWindow(FALSE);
}

void CFileTransferDlg::notifySendingFile(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "正在发送" );
		CString str = fileName.c_str();
		GetDlgItem(IDC_CURRENTTRANSFER)->SetWindowText("当前发送文件“" + str + "”");
	}
}

void CFileTransferDlg::notifyRecvingFile(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "正在接收" );
		CString str = fileName.c_str();
		GetDlgItem(IDC_CURRENTTRANSFER)->SetWindowText("当前接收文件“" + str + "”");
	}
}

void CFileTransferDlg::notifySendFileData(__int64 uid, string fileName, int length)
{
	CProgressCtrl* pProgress = (CProgressCtrl*) GetDlgItem(IDC_FILETRANSFER_PROGRESS);
	pProgress->ShowWindow(TRUE);
	int n = pProgress->GetPos();
	pProgress->SetPos( n + length );
	int nLower, nUper;
	pProgress->GetRange( nLower, nUper );
	if( pProgress->GetPos() == nUper )
		pProgress->ShowWindow( FALSE );
}

void CFileTransferDlg::notifyReceiveComplete(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "接收完成" );
		__int64* pId = (__int64*) m_ListCtrl.GetItemData( n );
		delete pId;
		m_ListCtrl.SetItemData( n, 0 ); 
	}
	GetDlgItem(IDC_FILETRANSFER_PROGRESS)->ShowWindow(FALSE);
}

void CFileTransferDlg::notifySendComplete(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "发送完成" );
		__int64* pId = (__int64*) m_ListCtrl.GetItemData( n );
		delete pId;
		m_ListCtrl.SetItemData( n, 0 ); 
	}
	GetDlgItem(IDC_FILETRANSFER_PROGRESS)->ShowWindow(FALSE);
}

void CFileTransferDlg::notifyOpenFileError(__int64 uid, string fileName)
{
	int n = findItem( uid, fileName.c_str() );
	if( n != -1 )
	{
		m_ListCtrl.SetItemText( n, 3, "文件错误" );
	}
}

ULONGLONG CFileTransferDlg::getFileLength(CString filename)
{
	CFile* pFile = NULL;
	ULONGLONG dwLength = 0;

	pFile = new CFile(_T(filename), CFile::modeRead | CFile::shareDenyNone);
	dwLength = pFile->GetLength();
	if (pFile != NULL)  
	{
		pFile->Close();
		delete pFile;
	}
	return dwLength;
}

void CFileTransferDlg::addListItem( __int64 uid, CString userName, CString fileName, ULONGLONG fileLength, CString status )
{
	if( findItem( uid, fileName ) != -1 )
		return;
	int n = m_ListCtrl.InsertItem(m_ListCtrl.GetItemCount(), userName);
	LV_ITEM lvitem;
	lvitem.mask = LVIF_TEXT;
	lvitem.iItem = n;

	lvitem.iSubItem = 1;
	lvitem.pszText = fileName.GetBuffer(fileName.GetLength());
	m_ListCtrl.SetItem(&lvitem);

	char buf[65];
	_i64toa(fileLength, buf, 10);
	CString str = buf;
	lvitem.iSubItem = 2;
	lvitem.pszText = str.GetBuffer(str.GetLength());
	m_ListCtrl.SetItem(&lvitem);

	lvitem.iSubItem = 3;
	lvitem.pszText = status.GetBuffer(status.GetLength());
	m_ListCtrl.SetItem(&lvitem);

	__int64* pId = new __int64( uid );
	m_ListCtrl.SetItemData( lvitem.iItem, (DWORD) pId ); 
}

int CFileTransferDlg::findItem(__int64 uid, CString fileName)
{
	for( int i = 0; i < m_ListCtrl.GetItemCount(); i++ )
	{
		if( m_ListCtrl.GetItemData(i) != 0 )
		{
			__int64 id = *(__int64*)m_ListCtrl.GetItemData(i);
			if( ( id == uid ) && ( fileName == m_ListCtrl.GetItemText( i, 1 ) ) )
				return i;
		}
	}
	return -1;
}


void CFileTransferDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	dc.FillSolidRect( rc, RGB(228, 240, 254) );
	CRect rc1 = rc;
	rc1.bottom = rc.bottom - 39;
	CBrush brush;
	brush.CreateSolidBrush( RGB(159, 177, 225) );
	dc.FillSolidRect( rc1, RGB(240, 245, 250) );
	rc1.InflateRect( 0, 20, 0, 0 );
	dc.FrameRect( rc1, &brush );
	
	// 不为绘图消息调用 CDialog::OnPaint()
}

void CFileTransferDlg::OnSize(UINT nType, int cx, int cy)
{
	CDialog::OnSize(nType, cx, cy);
	if( m_btnAddTask.GetSafeHwnd() )
		m_btnAddTask.MoveWindow( cx - 160, cy - 32, 74, 22 );
	if( m_btnCancelSel.GetSafeHwnd() )
		m_btnCancelSel.MoveWindow( cx - 80, cy - 32, 74, 22 );
	if( m_btnOpenReceived.GetSafeHwnd() )
        m_btnOpenReceived.MoveWindow( cx - 160, cy - 32, 74, 22 );
}

HBRUSH CFileTransferDlg::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_CURRENTTRANSFER )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	if( pWnd->GetDlgCtrlID() == IDC_SELECTALL )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush1;
	}
	return hbr;
}
