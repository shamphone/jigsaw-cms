#pragma once

#include "..\pub\linkbutton.h"

#define TYPE_SEND 1
#define TYPE_RECV 2

class FileTransferMgr;
class CMeetingRoomFrame;
// CFileTransferDlg 对话框

class CFileTransferDlg : public CDialog
{
	DECLARE_DYNAMIC(CFileTransferDlg)

public:
	CFileTransferDlg(CWnd* pParent = NULL, int type = TYPE_SEND);   // 标准构造函数
	virtual ~CFileTransferDlg();

// 对话框数据
	enum { IDD = IDD_FILE_TRANSFER_DLG };

	void setFileTransferMgr(FileTransferMgr* pFileTransferMgr);
	void selectFileToSend(vector<__int64> ids);
	void applySendFile( CString fileName, vector<__int64> ids );
	void setFileLength(int length);

	void notifyAgreeReceiveFile(__int64 uid, string fileName);
	void notifyRejectReceiveFile(__int64 uid, string fileName);
	void notifyStopRecvFile(__int64 uid, string fileName);
	void notifySendComplete(__int64 uid, string fileName);
	void notifySendingFile(__int64 uid, string fileName);

	void notifyApplySendFile(__int64 uid, string senderName, string fileName, __int64 fileLength);
	void notifyRecvingFile(__int64 uid, string fileName);
	void notifyStopSendFile(__int64 uid, string fileName);
	void notifyReceiveComplete(__int64 uid, string fileName);

	void notifySendFileData(__int64 uid, string fileName, int length);
	void notifyOpenFileError(__int64 uid, string fileName);

private:
	int findItem(__int64 uid, CString fileName);
	void addListItem( __int64 uid, CString userName, CString fileName, ULONGLONG fileLength, CString status );

public:
	static ULONGLONG getFileLength(CString filename);

private:
	CLinkButton			m_btnAddTask, m_btnOpenReceived, m_btnCancelSel;
	FileTransferMgr*	m_pFileTransferMgr;
	CListCtrl			m_ListCtrl;
	int					m_nType;
	CMeetingRoomFrame*  m_pRoom;
	CBrush				m_brush, m_brush1;

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnOK() {};
	virtual void OnCancel() {};

	DECLARE_MESSAGE_MAP()
	afx_msg void OnDestroy();
	afx_msg void OnClose();
	afx_msg void OnBnClickedSelectall();
	afx_msg void OnBnClickedCancelSelectedtaskBtn();
	afx_msg void OnBnClickedOpenReceived();
	afx_msg void OnBnClickedAddtaskBtn();
	afx_msg LRESULT OnSendNextFile( WPARAM wParam, LPARAM lParam );
public:
	afx_msg void OnPaint();
	afx_msg void OnSize(UINT nType, int cx, int cy);
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
