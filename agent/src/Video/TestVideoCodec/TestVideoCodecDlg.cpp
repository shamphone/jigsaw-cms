// TestVideoCodecDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "TestVideoCodec.h"
#include "TestVideoCodecDlg.h"
#include "../VideoConfig/VideoConfig.h"
#include "../VideoCapture/VideoCaptureMgr.h"
#include "../VideoCodec/VideoCodecMgr.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#endif


// CTestVideoCodecDlg 对话框



CTestVideoCodecDlg::CTestVideoCodecDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CTestVideoCodecDlg::IDD, pParent)
{
	m_hIcon = AfxGetApp()->LoadIcon(IDR_MAINFRAME);
}

void CTestVideoCodecDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
}

BEGIN_MESSAGE_MAP(CTestVideoCodecDlg, CDialog)
	ON_WM_PAINT()
	ON_WM_QUERYDRAGICON()
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()


HWND CTestVideoCodecDlg::AdjustVideoWindow(int ControlId)
{
	CRect rect;
	CWnd* pStatic = GetDlgItem(ControlId);
	pStatic->GetWindowRect(&rect);
	rect.bottom = rect.top + VideoConfig::getHeight();
	rect.right = rect.left + VideoConfig::getWidth();
	ScreenToClient(rect);
	pStatic->MoveWindow(&rect);
	return pStatic->GetSafeHwnd();
}

// CTestVideoCodecDlg 消息处理程序

BOOL CTestVideoCodecDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	// 设置此对话框的图标。当应用程序主窗口不是对话框时，框架将自动
	//  执行此操作
	SetIcon(m_hIcon, TRUE);			// 设置大图标
	SetIcon(m_hIcon, FALSE);		// 设置小图标

    // Move video window
	this->m_hVideoWndOriginal = AdjustVideoWindow(IDC_VIDEO_ORIGINAL);
	this->m_hVideoWndEncode = AdjustVideoWindow(IDC_VIDEO_ENCODE);

    //
    // Init old code
    //
	m_pVideoEncoder = new CVideoCodecMgr();

	m_pVideoEncoder->SetEncVideoFormat(VideoConfig::getWidth(), VideoConfig::getHeight(), VideoConfig::DEFAULT_BIT);
	m_pVideoEncoder->SetDecVideoFormat(VideoConfig::getWidth(), VideoConfig::getHeight(), VideoConfig::DEFAULT_BIT);

    int frameRate = VideoConfig::getFrameRate(); 
	int nKeyMax = frameRate * 10;
	int nCurVideoBitRate = 60000;
    if (!m_pVideoEncoder->InitEncoderV((float)frameRate, (float)nCurVideoBitRate, nKeyMax))
    {
        TRACE("Fail to init  0.9.1 encoder\n");
        return -1;
    }
    if (!m_pVideoEncoder->InitDecoderV())
    {
        TRACE("Fail to init  0.9.1 decoder\n");
        return -1;
    }
	TRACE("Initialize 0.9.1 codec successfully.");
		
	// Start capture
	if( !VideoCaptureMgr::create(CTestVideoCodecDlg::Callback, (void*)this, NULL))
	{
		TRACE("Failed to create device.\n");
		return -1;
	}
	
	VideoCaptureMgr::startCapture();
	TRACE("Start capture successfully.\n");

	return TRUE;
}

// 如果向对话框添加最小化按钮，则需要下面的代码
//  来绘制该图标。对于使用文档/视图模型的 MFC 应用程序，
//  这将由框架自动完成。

void CTestVideoCodecDlg::OnPaint() 
{
	if (IsIconic())
	{
		CPaintDC dc(this); // 用于绘制的设备上下文

		SendMessage(WM_ICONERASEBKGND, reinterpret_cast<WPARAM>(dc.GetSafeHdc()), 0);

		// 使图标在工作矩形中居中
		int cxIcon = GetSystemMetrics(SM_CXICON);
		int cyIcon = GetSystemMetrics(SM_CYICON);
		CRect rect;
		GetClientRect(&rect);
		int x = (rect.Width() - cxIcon + 1) / 2;
		int y = (rect.Height() - cyIcon + 1) / 2;

		// 绘制图标
		dc.DrawIcon(x, y, m_hIcon);
	}
	else
	{
		CDialog::OnPaint();
	}
}

//当用户拖动最小化窗口时系统调用此函数取得光标显示。
HCURSOR CTestVideoCodecDlg::OnQueryDragIcon()
{
	return static_cast<HCURSOR>(m_hIcon);
}

void CTestVideoCodecDlg::Callback(void* pObject, LPVIDEOHDR lpVHdr)
{
	CTestVideoCodecDlg* _this = (CTestVideoCodecDlg*)pObject;

    // Draw original
	_this->m_DrawDIBOriginal.DrawVideo(
            _this->m_hVideoWndOriginal,
            (char*)lpVHdr->lpData,
            const_cast<BITMAPINFOHEADER*>(&VideoConfig::DEFAULT_BITMAP_INFO_HEADER));

    // Encode the data
    DWORD nLength = ENCODED_VIDEO_BUFFER_LENGTH;
    int key;
    if(!_this->m_pVideoEncoder->EncodeVideoData(
            (char*)lpVHdr->lpData,
            lpVHdr->dwBytesUsed,
            (char*)_this->m_videoData,
            &nLength,
            &key))
	{
		TRACE("Encode failed.\n");
		return;
	}
    if( key )
    {
        TRACE1("Key Frame length = %d\n", nLength);
    }
    else
    {
        TRACE1("Frame length = %d\n", nLength);
    }

    memset(lpVHdr->lpData, 0, lpVHdr->dwBytesUsed);

    // Decode the data
	_this->m_pVideoEncoder->DecodeVideoData(
            (char*)_this->m_videoData,
            nLength,
            (char*)lpVHdr->lpData,
            0, 0);

    // Draw encoded data
	_this->m_DrawDIBEncode.DrawVideo(
            _this->m_hVideoWndEncode,
            (char*)lpVHdr->lpData,
            const_cast<BITMAPINFOHEADER*>(&VideoConfig::DEFAULT_BITMAP_INFO_HEADER));
}


void CTestVideoCodecDlg::OnOK()
{
	VideoCaptureMgr::stopCapture();
	VideoCaptureMgr::destroy();

	m_pVideoEncoder->DestroyCodecV();
	delete m_pVideoEncoder;

	CDialog::OnOK();
}

