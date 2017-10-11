#pragma once

#include "..\pub\DialogEx.h"

// CContactListDlg 对话框

class CContactListDlg : public CDialogEx
{
	DECLARE_DYNAMIC(CContactListDlg)

public:
	CContactListDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CContactListDlg();

// 对话框数据
	enum { IDD = IDD_CONTACT_LIST_DLG };

public:
	//被选中用户的id
	vector<USERID> m_selectedUserIds;
	//要显示的用户id和用户名
	vector<USERID> m_UserIds;
	vector<string> m_UserNames;
	//对话框标题
	CString m_strTitle;
	//提示信息
	CString m_strMessage;
	CRect m_showRect;

private:
	void clearList();
	void fillList();
	
protected:
	CBrush m_brush;
	CListCtrl m_ListCtrl;
	CImageList m_ImageList;
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();

	DECLARE_MESSAGE_MAP()

	afx_msg void OnBnClickedOk();
	afx_msg void OnClose();
	afx_msg void OnBnClickedCancel();
	afx_msg void OnMove(int x, int y);
	afx_msg void OnBnClickedCheckall();
	afx_msg HBRUSH OnCtlColor(CDC* pDC, CWnd* pWnd, UINT nCtlColor);
};
