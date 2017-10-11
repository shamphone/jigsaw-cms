// D:\lyvc\src\Client\UI\MeetingRoom\YuntaiDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "YuntaiDlg.h"
#include ".\yuntaidlg.h"

#include "..\..\Model\Room\YuntaiMgr.h"

// CYuntaiDlg 对话框

IMPLEMENT_DYNAMIC(CYuntaiDlg, CDialogEx)
CYuntaiDlg::CYuntaiDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CYuntaiDlg::IDD, pParent)
{
	m_bMovingUp = FALSE;
	m_bMovingDown = FALSE;
	m_bMovingLeft = FALSE;
	m_bMovingRight = FALSE;
	m_bZoomIn = FALSE;
	m_bZoomOut = FALSE;

	m_pYuntaiMgr = NULL;
	this->m_sDescription = "一体化智能球云台控制界面，摄像头的方向和焦距调整。";
}

CYuntaiDlg::~CYuntaiDlg()
{
}

void CYuntaiDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CYuntaiDlg, CDialogEx)
	ON_BN_CLICKED(IDC_BTN_UP, OnBnClickedBtnUp)
	ON_BN_CLICKED(IDC_BTN_DOWN, OnBnClickedBtnDown)
	ON_BN_CLICKED(IDC_BTN_LEFT, OnBnClickedBtnLeft)
	ON_BN_CLICKED(IDC_BTN_RIGHT, OnBnClickedBtnRight)
	ON_BN_CLICKED(IDC_BTN_ZOOMIN, OnBnClickedBtnZoomin)
	ON_BN_CLICKED(IDC_BTN_ZOOMOUT, OnBnClickedBtnZoomout)
	ON_CBN_SELCHANGE(IDC_COMBO_CONTROLLIST, OnCbnSelchangeComboControllist)
	ON_CBN_SELCHANGE(IDC_COMBO_SERIALPORT, OnCbnSelchangeComboSerialport)
END_MESSAGE_MAP()


// CYuntaiDlg 消息处理程序

void CYuntaiDlg::OnBnClickedBtnUp()
{
	if( !m_pYuntaiMgr )
		return;

	if( !m_bMovingUp )
	{
		this->m_pYuntaiMgr->moveUp( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_UP)->SetWindowText("停止");
		GetDlgItem(IDC_BTN_DOWN)->SetWindowText("向下");
		GetDlgItem(IDC_BTN_LEFT)->SetWindowText("向左");
		GetDlgItem(IDC_BTN_RIGHT)->SetWindowText("向右");
		m_bMovingUp = TRUE;
		m_bMovingDown = FALSE;
		m_bMovingLeft = FALSE;
		m_bMovingRight = FALSE;
	}
	else
	{
		this->m_pYuntaiMgr->stopAction( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_UP)->SetWindowText("向上");
		m_bMovingUp = FALSE;
	}
}

void CYuntaiDlg::OnBnClickedBtnDown()
{
	if( !m_pYuntaiMgr )
		return;

	if( !m_bMovingDown )
	{
		this->m_pYuntaiMgr->moveDown( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_DOWN)->SetWindowText("停止");
		GetDlgItem(IDC_BTN_UP)->SetWindowText("向上");
		GetDlgItem(IDC_BTN_LEFT)->SetWindowText("向左");
		GetDlgItem(IDC_BTN_RIGHT)->SetWindowText("向右");
		m_bMovingUp = FALSE;
		m_bMovingDown = TRUE;
		m_bMovingLeft = FALSE;
		m_bMovingRight = FALSE;
	}
	else
	{
		this->m_pYuntaiMgr->stopAction( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_DOWN)->SetWindowText("向下");
		m_bMovingDown = FALSE;
	}
}

void CYuntaiDlg::OnBnClickedBtnLeft()
{
	if( !m_pYuntaiMgr )
		return;

	if( !m_bMovingLeft )
	{
		this->m_pYuntaiMgr->moveLeft( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_LEFT)->SetWindowText("停止");
		GetDlgItem(IDC_BTN_UP)->SetWindowText("向上");
		GetDlgItem(IDC_BTN_DOWN)->SetWindowText("向下");
		GetDlgItem(IDC_BTN_RIGHT)->SetWindowText("向右");
		m_bMovingUp = FALSE;
		m_bMovingDown = FALSE;
		m_bMovingLeft = TRUE;
		m_bMovingRight = FALSE;
	}
	else
	{
		this->m_pYuntaiMgr->stopAction( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_LEFT)->SetWindowText("向左");
		m_bMovingLeft = FALSE;
	}
}

void CYuntaiDlg::OnBnClickedBtnRight()
{
	if( !m_pYuntaiMgr )
		return;

	if( !m_bMovingRight )
	{
		this->m_pYuntaiMgr->moveRight( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_RIGHT)->SetWindowText("停止");
		GetDlgItem(IDC_BTN_UP)->SetWindowText("向上");
		GetDlgItem(IDC_BTN_DOWN)->SetWindowText("向下");
		GetDlgItem(IDC_BTN_LEFT)->SetWindowText("向左");
		m_bMovingUp = FALSE;
		m_bMovingDown = FALSE;
		m_bMovingLeft = FALSE;
		m_bMovingRight = TRUE;
	}
	else
	{
		this->m_pYuntaiMgr->stopAction( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_RIGHT)->SetWindowText("向右");
		m_bMovingRight = FALSE;
	}
}

void CYuntaiDlg::OnBnClickedBtnZoomin()
{
	if( !m_pYuntaiMgr )
		return;

	if( !m_bZoomIn )
	{
		this->m_pYuntaiMgr->zoomIn( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_ZOOMIN)->SetWindowText("停止");
		GetDlgItem(IDC_BTN_ZOOMOUT)->SetWindowText("变大");
		m_bZoomOut = FALSE;
		m_bZoomIn = TRUE;
	}
	else
	{
		this->m_pYuntaiMgr->stopAction( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_ZOOMIN)->SetWindowText("变小");
		m_bZoomIn = FALSE;
	}
}

void CYuntaiDlg::OnBnClickedBtnZoomout()
{
	if( !m_pYuntaiMgr )
		return;

	if( !m_bZoomOut )
	{
		this->m_pYuntaiMgr->zoomOut( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_ZOOMOUT)->SetWindowText("停止");
		GetDlgItem(IDC_BTN_ZOOMIN)->SetWindowText("变小");
		m_bZoomOut = TRUE;
		m_bZoomIn = FALSE;
	}
	else
	{
		this->m_pYuntaiMgr->stopAction( this->m_nCurrentAddress, this->m_nCurrentCommPort );
		GetDlgItem(IDC_BTN_ZOOMOUT)->SetWindowText("变大");
		m_bZoomOut = FALSE;
	}
}

void CYuntaiDlg::OnOK()
{
	stopControl();

	CDialogEx::OnOK();
}

void CYuntaiDlg::OnCancel()
{
	OnOK();
}

void CYuntaiDlg::setYuntaiMgr(YuntaiMgr* pMgr)
{
	m_pYuntaiMgr = pMgr;
}

void CYuntaiDlg::addYuntaiHolder(  __int64 uid, string name, int nCommPort )
{
	// 初始化云台地址列表
	CComboBox* pCB = ( CComboBox* ) GetDlgItem( IDC_COMBO_CONTROLLIST );
	for( int i = 1; i < 255; i++ )
	{
		char buf[33];
		itoa(i, buf, 10);
		pCB->InsertString( pCB->GetCount(), buf );
	}
	pCB->SetCurSel( 0 );
	this->m_nCurrentAddress = 1;
	// 初始化com端口列表
	pCB = ( CComboBox* ) GetDlgItem( IDC_COMBO_SERIALPORT );
	pCB->ResetContent();
	for( int i = 1; i < 5; i++ )
	{
		if( ( 1 << (i-1) ) & nCommPort )
		{
			char buf[33];
			itoa(i, buf, 10);
			pCB->SetItemData( pCB->InsertString( pCB->GetCount(), buf ), 1<<(i-1) );
		}
	}
	pCB->SetCurSel( 0 );
	this->m_nCurrentCommPort = pCB->GetItemData( 0 );
}

void CYuntaiDlg::stopControl()
{
	if( !m_pYuntaiMgr )
		return;
	if(	m_bMovingUp )
	{
		this->m_pYuntaiMgr->stopAction(this->m_nCurrentAddress, this->m_nCurrentCommPort);
		GetDlgItem(IDC_BTN_UP)->SetWindowText("向上");
		m_bMovingUp = FALSE;
	}
	else if( m_bMovingDown )
	{
		this->m_pYuntaiMgr->stopAction(this->m_nCurrentAddress, this->m_nCurrentCommPort);
		GetDlgItem(IDC_BTN_DOWN)->SetWindowText("向下");
		m_bMovingDown = FALSE;
	}
	else if( m_bMovingLeft )
	{
		this->m_pYuntaiMgr->stopAction(this->m_nCurrentAddress, this->m_nCurrentCommPort);
		GetDlgItem(IDC_BTN_LEFT)->SetWindowText("向左");
		m_bMovingLeft = FALSE;
	}
	else if( m_bMovingRight )
	{
		this->m_pYuntaiMgr->stopAction(this->m_nCurrentAddress, this->m_nCurrentCommPort);
		GetDlgItem(IDC_BTN_RIGHT)->SetWindowText("向右");
		m_bMovingRight = FALSE;
	}
	else if( m_bZoomIn )
	{
		this->m_pYuntaiMgr->stopAction(this->m_nCurrentAddress, this->m_nCurrentCommPort);
		GetDlgItem(IDC_BTN_ZOOMIN)->SetWindowText("变小");
		m_bZoomIn = FALSE;
	}
	else if( m_bZoomOut )
	{
		this->m_pYuntaiMgr->stopAction(this->m_nCurrentAddress, this->m_nCurrentCommPort);
		GetDlgItem(IDC_BTN_ZOOMOUT)->SetWindowText("变大");
		m_bZoomOut = FALSE;
	}
}

int CYuntaiDlg::getCurentControlAddress()
{
	CComboBox* pCB = ( CComboBox* ) GetDlgItem( IDC_COMBO_CONTROLLIST );
	return pCB->GetCurSel() + 1;
}

int CYuntaiDlg::getCurrentCommPort()
{
	CComboBox* pCB = ( CComboBox* ) GetDlgItem( IDC_COMBO_SERIALPORT );
	return pCB->GetItemData( pCB->GetCurSel() );
}

void CYuntaiDlg::OnCbnSelchangeComboControllist()
{
	int nAddress = this->getCurentControlAddress();
	if( this->m_nCurrentAddress == nAddress )
		return;
	stopControl();
	m_nCurrentAddress = nAddress;
}

void CYuntaiDlg::OnCbnSelchangeComboSerialport()
{
	int nPort = this->getCurrentCommPort();
	if( this->m_nCurrentCommPort == nPort )
		return;
	stopControl();
	m_nCurrentCommPort = nPort;
}
