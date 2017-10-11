// VideoPP.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "VideoPP.h"
#include ".\videopp.h"
#include "AVConfigWizard.h"
#include "Video\VideoCapture\VideoCaptureMgr.h"
#include "Video\VideoConfig\VideoConfig.h"
#include "UI\MainFrm\MainFrm.h"
#include "Model/CooperationManager.h"


// CVideoPP 对话框

IMPLEMENT_DYNAMIC(CVideoPP, CPropertyPage)
CVideoPP::CVideoPP() : CPropertyPage(CVideoPP::IDD)
{
    m_bIsCapturing = FALSE;
}

CVideoPP::~CVideoPP()
{
}

void CVideoPP::DoDataExchange(CDataExchange* pDX)
{
	CPropertyPage::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_COMBO_VIDEO_DEVICE, m_comboVideoDevice);
	DDX_Control(pDX, IDC_PROPERTY, m_btn );
}


BEGIN_MESSAGE_MAP(CVideoPP, CPropertyPage)
	ON_CBN_SELCHANGE(IDC_COMBO_VIDEO_DEVICE, OnCbnSelchangeComboVideoDevice)
	ON_BN_CLICKED(IDC_PROPERTY, OnBnClickedProperty)
	ON_WM_PAINT()
	ON_WM_CTLCOLOR()
END_MESSAGE_MAP()


// CVideoPP 消息处理程序

BOOL CVideoPP::OnSetActive()
{
    CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();   
	pWizard->setWindowRect( this );
	if( pWizard->m_btnBack.GetSafeHwnd() )
		pWizard->m_btnBack.EnableWindow(TRUE);
	if( pWizard->m_btnFinish.GetSafeHwnd() )
        pWizard->m_btnFinish.ShowWindow(TRUE);
	if( pWizard->m_btnNext.GetSafeHwnd() )
		pWizard->m_btnNext.ShowWindow(FALSE);

    // Fill the video device combo list
    while (m_comboVideoDevice.GetCount() > 0)
    {
       m_comboVideoDevice.DeleteString(0);
    }
    m_comboVideoDevice.AddString("无");

    list<CVideoCaptureDevice> videoDevices;
    VideoCaptureMgr::getDevices(videoDevices);
    if( videoDevices.size() == 0 )
    {
        m_comboVideoDevice.SetCurSel(0);
		CWnd* pWnd = GetDlgItem(IDC_PROPERTY);
		pWnd->EnableWindow(FALSE);
        return TRUE;
    }

    int i = 1;
    list<CVideoCaptureDevice>::iterator it = videoDevices.begin();
    while( it != videoDevices.end())
    {
        m_comboVideoDevice.InsertString( i, (*it).m_FriendlyName.c_str());
        it++;
        i++;
    }
    m_comboVideoDevice.SetCurSel(1);

	if( ((CMainFrame*)GetApp()->GetMainWnd())->hasServerLoginOrLogining())
	{
		CWnd* pWnd = GetDlgItem(IDC_VIDEO);
		pWnd->SetWindowText("在登录状态下无法调整视频属性");

		pWnd = GetDlgItem(IDC_COMBO_VIDEO_DEVICE);
		pWnd->EnableWindow(FALSE);

		pWnd = GetDlgItem(IDC_PROPERTY);
		pWnd->EnableWindow(FALSE);
	}
	else
	{
		this->startCapture();
	}

    return CPropertyPage::OnSetActive();
}

BOOL CVideoPP::OnKillActive()
{
    //CPropertySheet* psheet = (CPropertySheet*) GetParent();   
    //psheet->SetWizardButtons(PSWIZB_NEXT | PSWIZB_BACK);

    if( m_bIsCapturing )
    {
        this->stopCapture();
    }

    saveSettings();

    return CPropertyPage::OnKillActive();
}


void CVideoPP::OnCancel()
{
    if( m_bIsCapturing )
    {
        this->stopCapture();
    }

	CPropertyPage::OnCancel();
}

BOOL CVideoPP::OnWizardFinish()
{
    if( m_bIsCapturing )
    {
        this->stopCapture();
    }

    saveSettings();

	return CPropertyPage::OnWizardFinish();
}

void CVideoPP::saveSettings()
{
    int nIndex = this->m_comboVideoDevice.GetCurSel();
    if( nIndex != 0)
    {
        const char* iniFileName = ::GetApp()->getIniFilename();
		CString deviceName;
		this->m_comboVideoDevice.GetLBText(nIndex, deviceName);
        ::WritePrivateProfileString("MEDIA_CONFIG", "video_device", deviceName, iniFileName);
    }
}

void CVideoPP::startCapture()
{
    int nIndex = this->m_comboVideoDevice.GetCurSel();
	CString deviceName;
	this->m_comboVideoDevice.GetLBText(nIndex, deviceName);

	::GetApp()->getCooperationManager()->addAVCustomer(0, this);	//使用保留server id: 0,伪装成服务器请求视音频资源
	::GetApp()->getCooperationManager()->startVideoCapture();

	m_bIsCapturing = TRUE;
}

void CVideoPP::stopCapture()
{
	::GetApp()->getCooperationManager()->stopVideoCapture();
	::GetApp()->getCooperationManager()->removeAVCustomer(0);
	m_bIsCapturing = FALSE;

	// Erase the VideoWindow
	CWnd* pWnd = GetDlgItem(IDC_VIDEO);
	pWnd->Invalidate(TRUE);
	::UpdateWindow(this->m_hVideoWnd);
}

BOOL CVideoPP::OnInitDialog()
{
	CPropertyPage::OnInitDialog();

	// Adjust video window size to default.
	CWnd* pStatic = GetDlgItem(IDC_VIDEO);
	CRect rect;
	pStatic->GetWindowRect(&rect);
	rect.bottom = rect.top + VideoConfig::getHeight();
	rect.right = rect.left + VideoConfig::getWidth();
	ScreenToClient(rect);
	pStatic->MoveWindow(&rect);
	this->m_hVideoWnd = pStatic->GetSafeHwnd();

	m_btn.setBitmaps( IDB_BUTTON, IDB_BUTTON, IDB_BUTTON, IDB_BUTTONDISABLE );
	CRect rc;
	GetDlgItem(IDC_PROPERTY)->GetWindowRect( &rc );
	ScreenToClient( rc );
	m_btn.MoveWindow( rc.left, rc.top, 74, 22 );

	m_brush.CreateSolidBrush( RGB(240, 245, 250) );
	return TRUE;
}

void CVideoPP::OnCbnSelchangeComboVideoDevice()
{
	if(this->m_bIsCapturing)
	{
		this->stopCapture();
	}

	CWnd* pPropertyButton = this->GetDlgItem(IDC_PROPERTY);
	if(this->m_comboVideoDevice.GetCurSel() == 0)
	{
		pPropertyButton->EnableWindow(FALSE);
	}
	else
	{
		pPropertyButton->EnableWindow(TRUE);
		this->startCapture();
	}
}

void CVideoPP::videoDataCallback(void* pObject, LPVIDEOHDR lpVHdr)
{
	CVideoPP* _this = (CVideoPP*)pObject;
	_this->m_DrawDIB.DrawVideo(
            _this->m_hVideoWnd,
            (char*)lpVHdr->lpData,
            const_cast<BITMAPINFOHEADER*>(&(VideoConfig::getBitmapInfoHeader())));
	return;
}

void CVideoPP::OnBnClickedProperty()
{
	VideoCaptureMgr::showDialog(this->GetSafeHwnd());
}

void CVideoPP::OnPaint()
{
	CPaintDC dc(this); // device context for painting
	CRect rc;
	GetClientRect( &rc );
	CAVConfigWizard* pWizard = (CAVConfigWizard*) GetParent();
	CString sText = "步骤3：设置摄像机：";
	pWizard->drawHead( &dc, rc, sText );
	// 不为绘图消息调用 CPropertyPage::OnPaint()
}

HBRUSH CVideoPP::OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor)
{
	HBRUSH hbr = CPropertyPage::OnCtlColor(pDC, pWnd, nCtlColor);

	if( pWnd->GetDlgCtrlID() == IDC_STATIC || pWnd->GetDlgCtrlID() == IDC_VIDEO )
	{
		pDC->SetTextColor( RGB(0, 0, 0) );
		pDC->SetBkMode( TRANSPARENT );
		hbr = m_brush;
	}
	return hbr;
}
