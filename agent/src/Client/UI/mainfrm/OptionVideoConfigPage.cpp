// UI\MainFrm\OptionVideoConfigPage.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "OptionVideoConfigPage.h"
#include ".\optionvideoconfigpage.h"
#include "..\..\model\CooperationManager.h"

// COptionVideoConfigPage 对话框

IMPLEMENT_DYNAMIC(COptionVideoConfigPage, CDialog)
COptionVideoConfigPage::COptionVideoConfigPage(CWnd* pParent)
	: CDialog(COptionVideoConfigPage::IDD, pParent)
{
}

COptionVideoConfigPage::~COptionVideoConfigPage()
{
}

void COptionVideoConfigPage::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control( pDX, IDC_DEFAULT_BTN, m_btn );
}


BEGIN_MESSAGE_MAP(COptionVideoConfigPage, CDialog)
	ON_BN_CLICKED(IDC_DEFAULT_BTN, OnBnClickedDefaultBtn)
	ON_EN_CHANGE(IDC_FPS, OnEnChangeFps)
	ON_EN_CHANGE(IDC_KEYFRAME, OnEnChangeKeyframe)
	ON_CBN_SELCHANGE(IDC_COMBO_VIDEOSIZE, OnCbnSelchangeComboVideosize)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// COptionVideoConfigPage 消息处理程序

BOOL COptionVideoConfigPage::OnInitDialog()
{
	CDialog::OnInitDialog();

	GetDlgItem( IDC_DEFAULT_BTN )->EnableWindow( !m_bReadOnly );
	((CEdit*)GetDlgItem( IDC_FPS ))->LimitText( 2 );
	((CEdit*)GetDlgItem( IDC_KEYFRAME ))->LimitText( 3 );

	((CEdit*)GetDlgItem( IDC_FPS ))->SetReadOnly( m_bReadOnly );
	((CEdit*)GetDlgItem( IDC_KEYFRAME ))->SetReadOnly( m_bReadOnly );

	CComboBox* pBox = (CComboBox*)GetDlgItem( IDC_COMBO_VIDEOSIZE );
	pBox->AddString("176 * 144");
	pBox->AddString("352 * 288");
	pBox->AddString("640 * 480");
	int width = ::GetPrivateProfileInt( "MEDIA_CONFIG", "video_width", 176, GetApp()->getIniFilename() );
	int height = ::GetPrivateProfileInt( "MEDIA_CONFIG", "video_height", 144, GetApp()->getIniFilename() );
	if( width == 176 && height == 144 )
		pBox->SetCurSel( 0 );
	else if( width == 352 && height == 288 )
		pBox->SetCurSel( 1 );
	else if( width == 640 && height == 480 )
		pBox->SetCurSel( 2 );
	else
   		pBox->SetCurSel( -1 );

	pBox->EnableWindow( !m_bReadOnly );

	int fps = ::GetPrivateProfileInt( "MEDIA_CONFIG", "video_quality", 10, GetApp()->getIniFilename() );
	int key_interval = ::GetPrivateProfileInt( "MEDIA_CONFIG", "max_key_interval", 20, GetApp()->getIniFilename() );

	if( fps <= 0 || fps > 25 )
		fps = 10;
	if( key_interval <= 0 || key_interval > 500 )
		key_interval = 10;

	char buf[33];
	CString str;
	itoa( fps, buf, 10 );
	str = buf;
	GetDlgItem( IDC_FPS )->SetWindowText( str );
	itoa( key_interval, buf, 10 );
	str = buf;
	GetDlgItem( IDC_KEYFRAME )->SetWindowText( str );

	CRect rc;
	GetClientRect( &rc );
	m_btn.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	m_btn.MoveWindow( rc.right - 85, 140, 74, 22 );
	m_brush.CreateSolidBrush( RGB(240, 245, 250) );
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

BOOL COptionVideoConfigPage::apply()
{
    if( this->checkValidateData() )
	{
		writeIni();
		return TRUE;
	}
	AfxMessageBox( "视频参数数据输入有误，请重新输入。" );
    return FALSE;
}

void COptionVideoConfigPage::setReadOnly( BOOL bReadOnly )
{
	m_bReadOnly = bReadOnly;
}

void COptionVideoConfigPage::writeIni()
{
	if( this->m_bReadOnly )
		return;

	CString s;
	GetDlgItem(IDC_FPS)->GetWindowText(s);
	::WritePrivateProfileString("MEDIA_CONFIG", "video_quality", s, GetApp()->getIniFilename());
	GetDlgItem(IDC_KEYFRAME)->GetWindowText(s);
	::WritePrivateProfileString("MEDIA_CONFIG", "max_key_interval", s, GetApp()->getIniFilename());

	CComboBox* pBox = (CComboBox*)GetDlgItem( IDC_COMBO_VIDEOSIZE );
	int nCurSel = pBox->GetCurSel();
	CString strWidth, strHeight, strBitrate;
	if( nCurSel == 0 )
	{	
		strWidth = "176";
		strHeight = "144";
		strBitrate = "50000";
	}
	else if( nCurSel == 1 )
	{
		strWidth = "352";
		strHeight = "288";
		strBitrate = "100000";
	}
	else if( nCurSel == 2 )
	{
		strWidth = "640";
		strHeight = "480";
		strBitrate = "200000";
	}
	if( nCurSel >= 0 )
	{
		::WritePrivateProfileString( "MEDIA_CONFIG", "video_width", strWidth, GetApp()->getIniFilename() );
		::WritePrivateProfileString( "MEDIA_CONFIG", "video_height", strHeight, GetApp()->getIniFilename() );
		::WritePrivateProfileString( "TRANSMODEL", "bitrate", strBitrate, GetApp()->getIniFilename() );
		::GetApp()->getCooperationManager()->destroy();
		::GetApp()->getCooperationManager()->create();
	}
}

BOOL COptionVideoConfigPage::checkValidateData()
{
	CString str;
	GetDlgItem(IDC_FPS)->GetWindowText(str);
	int fps = atoi( str );
	if( fps <= 0 || fps > 25 )
		return FALSE;

	GetDlgItem(IDC_KEYFRAME)->GetWindowText(str);
	int interval = atoi( str );
	if( interval <= 0 || interval > 500 )
		return FALSE;
	return TRUE;
}

void COptionVideoConfigPage::OnBnClickedDefaultBtn()
{
	GetDlgItem( IDC_FPS )->SetWindowText( "10" );
	GetDlgItem( IDC_KEYFRAME )->SetWindowText( "20" );
	((CComboBox*)GetDlgItem( IDC_COMBO_VIDEOSIZE ))->SetCurSel( 0 );
}

void COptionVideoConfigPage::OnEnChangeFps()
{
}

void COptionVideoConfigPage::OnEnChangeKeyframe()
{
}

void COptionVideoConfigPage::OnCbnSelchangeComboVideosize()
{
}

void COptionVideoConfigPage::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CBrush brush;
	brush.CreateSolidBrush( RGB(159, 177, 225) );
	dc.FillSolidRect( rc, RGB(240, 245, 250) );
	rc.InflateRect( 0, 20, 0, 0 );
	dc.FrameRect( rc, &brush );
	// 不为绘图消息调用 CDialog::OnPaint()
}

HBRUSH COptionVideoConfigPage::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CDialog::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}
