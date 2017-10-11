// UI\MeetingRoom\FileTransferMgrDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "FileTransferMgrDlg.h"
#include ".\filetransfermgrdlg.h"
#include "FileTransferDlg.h"
#include "UI\pub\ImageTabWnd.h"


// CFileTransferMgrDlg 对话框

IMPLEMENT_DYNAMIC(CFileTransferMgrDlg, CDialog)
CFileTransferMgrDlg::CFileTransferMgrDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CFileTransferMgrDlg::IDD, pParent)
{
}

CFileTransferMgrDlg::~CFileTransferMgrDlg()
{
}

void CFileTransferMgrDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CFileTransferMgrDlg, CDialog)
	ON_WM_DESTROY()
	ON_WM_CLOSE()
END_MESSAGE_MAP()


// CFileTransferMgrDlg 消息处理程序
void CFileTransferMgrDlg::OnOK()
{
}

void CFileTransferMgrDlg::OnCancel()
{
	CDialog::OnCancel();
}

void CFileTransferMgrDlg::OnClose()
{
	CDialog::OnClose();
}

void CFileTransferMgrDlg::OnDestroy()
{
	CDialog::OnDestroy();

	if(m_pSendFileDlg)
	{
		m_pSendFileDlg->DestroyWindow();
		delete m_pSendFileDlg;
		m_pSendFileDlg = NULL;
	}
	if(m_pRecvFileDlg)
	{
		m_pRecvFileDlg->DestroyWindow();
		delete m_pRecvFileDlg;
		m_pRecvFileDlg = NULL;
	}
	if(m_pImageTab)
	{
		m_pImageTab->DestroyWindow();
		delete m_pImageTab;
		m_pImageTab = NULL;
	}
}

CFileTransferDlg* CFileTransferMgrDlg::getSendFileDlg()
{
	return m_pSendFileDlg;
}

CFileTransferDlg* CFileTransferMgrDlg::getRecvFileDlg()
{
	return m_pRecvFileDlg;
}

void CFileTransferMgrDlg::setFileTransferMgr(FileTransferMgr* pFileTransferMgr)
{
	m_pSendFileDlg->setFileTransferMgr( pFileTransferMgr );
	m_pRecvFileDlg->setFileTransferMgr( pFileTransferMgr );
}

void CFileTransferMgrDlg::selectFileToSend(vector<__int64> ids)
{
	this->ShowWindow(TRUE);
	m_pImageTab->ShowDialog(0);
	m_pSendFileDlg->selectFileToSend( ids );
}

void CFileTransferMgrDlg::applySendFile( CString fileName, vector<__int64> ids )
{
	this->ShowWindow(TRUE);
	m_pImageTab->ShowDialog(0);
	m_pSendFileDlg->applySendFile( fileName, ids );
}

void CFileTransferMgrDlg::showRecvFileDlg()
{
	this->ShowWindow(TRUE);
	m_pImageTab->ShowDialog(1);
}


BOOL CFileTransferMgrDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_pImageTab = new CImageTabWnd();
	m_pImageTab->Create(NULL, NULL, WS_CHILD|WS_CLIPCHILDREN, CRect(), this, NULL, NULL);

	m_pImageTab->SetTopImage(IDB_SENDFILETABTOP);
	m_pImageTab->SetTitleHeight(40);
	m_pImageTab->SetTabCount(2);
	m_pImageTab->SetDialogMargin(1);

	int nIndex = 0;
	m_pImageTab->SetTabImage(nIndex, IDB_SENDFILETAB);
	m_pImageTab->SetTabRect(nIndex, CRect( 2, 8, 97, 36));
	m_pImageTab->SetToolTip(nIndex, "文件发送");
	m_pSendFileDlg = new CFileTransferDlg( m_pImageTab, TYPE_SEND );
	m_pSendFileDlg->Create(IDD_FILE_TRANSFER_DLG, m_pImageTab);
	m_pImageTab->SetTabWnd(nIndex, m_pSendFileDlg);
	nIndex++;
	m_pImageTab->SetTabImage(nIndex, IDB_RECVFILETAB);
	m_pImageTab->SetTabRect(nIndex, CRect( 103, 8, 200, 36));
	m_pImageTab->SetToolTip(nIndex, "文件接收");
	m_pRecvFileDlg = new CFileTransferDlg( m_pImageTab, TYPE_RECV );
	m_pRecvFileDlg->Create(IDD_FILE_TRANSFER_DLG, m_pImageTab);
	m_pImageTab->SetTabWnd(nIndex, m_pRecvFileDlg);

	CRect rc;
	GetClientRect( &rc );
	m_pImageTab->MoveWindow( CRect(0, 0, rc.Width(), rc.Height()) );
	m_pImageTab->ShowDialog( 0 );
	m_pImageTab->ShowWindow( TRUE );

	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}
