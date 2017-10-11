// VNCTestDlg.h : 头文件
//

#pragma once

class DesktopServer;
class DesktopClient;
using namespace std;

namespace LyvcMessage
{
    class MatrixC;
};

// CVNCTestDlg 对话框
class CVNCTestDlg : public CDialog
{
// 构造
public:
	CVNCTestDlg(CWnd* pParent = NULL);	// 标准构造函数

// 对话框数据
	enum { IDD = IDD_VNCTEST_DIALOG };

	protected:
	virtual void DoDataExchange(CDataExchange* pDX);	// DDX/DDV 支持


// 实现
protected:
	HICON m_hIcon;

	// 生成的消息映射函数
	virtual BOOL OnInitDialog();
	afx_msg void OnSysCommand(UINT nID, LPARAM lParam);
	afx_msg void OnPaint();
	afx_msg HCURSOR OnQueryDragIcon();
	DECLARE_MESSAGE_MAP()
	afx_msg void OnBnClickedCapture();

	HANDLE DDBToDIB(CBitmap &bitmap, DWORD dwCompression, CPalette *pPal);
	BOOL WriteDIB(LPTSTR szFile, HANDLE hDIB);
	void SaveRectToBmp(CRect rect);
	void OnTest(); 


private:
	//桌面共享服务端
	DesktopServer* m_desktopServer;
	//桌面共享客户端
	DesktopClient* m_desktopClient;
	//消息命令收发系统
    LyvcMessage::MatrixC* m_pMatrixC;

public:
	afx_msg void OnClose();
	afx_msg void OnBnClickedOk();
	afx_msg void OnBnClickedCancel();

	//启动桌面客户端
	afx_msg void OnBnClickedStartclient();
	//停止桌面客户端
	afx_msg void OnBnClickedStopclient();
	//启动桌面服务端
	afx_msg void OnBnClickedStartserver();
	//停止桌面服务端
	afx_msg void OnBnClickedStopserver();

	//初始化消息命令收发
	BOOL initMsgMatrix(CString IP, int port);
	void releaseMsgMatrix();

	afx_msg void OnBnClickedControl();
	afx_msg void OnBnClickedUncontrol();
	afx_msg void OnBnClickedRestart();
};
