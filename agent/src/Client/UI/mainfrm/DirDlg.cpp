// ui\DirDlg.cpp : 实现文件
//

#include "stdafx.h"
#include "FLVCC.h"
#include "DirDlg.h"

// CDirDlg 对话框

IMPLEMENT_DYNAMIC(CDirDlg, CDialog)
CDirDlg::CDirDlg(CWnd* pParent /*=NULL*/)
	: CDialog(CDirDlg::IDD, pParent)
{
}

CDirDlg::~CDirDlg()
{
}

void CDirDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_DIR_TREE, m_TreeCtrl);
}


BEGIN_MESSAGE_MAP(CDirDlg, CDialog)
	ON_NOTIFY(TVN_SELCHANGED, IDC_DIR_TREE, OnTvnSelchangedDirTree)
END_MESSAGE_MAP()


// CDirDlg 消息处理程序

BOOL CDirDlg::OnInitDialog()
{
	CDialog::OnInitDialog();

	m_TreeCtrl.DisplayTree(NULL);
	m_TreeCtrl.SetSelPath(m_sPath);
	return TRUE;  // return TRUE unless you set the focus to a control
	// 异常: OCX 属性页应返回 FALSE
}

void CDirDlg::OnTvnSelchangedDirTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	m_sPath = m_TreeCtrl.GetFullPath(pNMTreeView->itemNew.hItem);

	*pResult = 0;
}
