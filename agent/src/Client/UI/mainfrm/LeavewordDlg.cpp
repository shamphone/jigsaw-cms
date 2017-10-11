// LeavewordDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "Flvcc.h"
#include "LeavewordDlg.h"
#include ".\leaveworddlg.h"


// CLeavewordDlg 对话框

IMPLEMENT_DYNAMIC(CLeavewordDlg, CDialogEx)
CLeavewordDlg::CLeavewordDlg(CWnd* pParent /*=NULL*/)
	: CDialogEx(CLeavewordDlg::IDD, pParent)
{
	m_strLeaveword = "";
	m_showRect = CRect(0, 0, 0, 0);
}

CLeavewordDlg::~CLeavewordDlg()
{
}

void CLeavewordDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialogEx::DoDataExchange(pDX);
}


BEGIN_MESSAGE_MAP(CLeavewordDlg, CDialogEx)
	ON_BN_CLICKED(IDOK, OnBnClickedOk)
END_MESSAGE_MAP()


// CLeavewordDlg 消息处理程序

void CLeavewordDlg::OnBnClickedOk()
{
	this->GetDlgItem(IDC_LEAVEWORD)->GetWindowText(m_strLeaveword);
	if(m_strLeaveword.Trim().GetLength() == 0)
	{
		AfxMessageBox("输入内容不能为空！");
	}
	else
	{
        OnOK();
	}
}

BOOL CLeavewordDlg::OnInitDialog()
{
	CDialogEx::OnInitDialog();
	if(m_showRect.top > 0)
		this->MoveWindow(m_showRect);
	this->m_sDescription = "请在下面的文本框中输入留言内容，按“确定”按钮发送。";
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}
