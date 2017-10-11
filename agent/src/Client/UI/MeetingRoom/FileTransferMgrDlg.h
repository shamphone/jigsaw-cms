#pragma once

// CFileTransferMgrDlg 对话框
class CFileTransferDlg;
class CImageTabWnd;
class FileTransferMgr;

class CFileTransferMgrDlg : public CDialog
{
	DECLARE_DYNAMIC(CFileTransferMgrDlg)

public:
	CFileTransferMgrDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CFileTransferMgrDlg();

// 对话框数据
	enum { IDD = IDD_FILETRANSFERMGRDLG };

	//获取文件传输对话框
	CFileTransferDlg* getSendFileDlg();
	CFileTransferDlg* getRecvFileDlg();
	void setFileTransferMgr(FileTransferMgr* pFileTransferMgr);
	void selectFileToSend(vector<__int64> ids);
	void applySendFile( CString fileName, vector<__int64> ids );
	void showRecvFileDlg();

private:
	CFileTransferDlg*	m_pSendFileDlg;//文件发送对话框
	CFileTransferDlg*	m_pRecvFileDlg;
	CImageTabWnd*		m_pImageTab;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual void OnOK();
	virtual void OnCancel();
	virtual BOOL OnInitDialog();

	DECLARE_MESSAGE_MAP()
	afx_msg void OnDestroy();
	afx_msg void OnClose();
};
