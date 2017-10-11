#pragma once

// CAdvertizeDlg 对话框

class CAdvertizeDlg : public CDHtmlDialog
{
	DECLARE_DYNCREATE(CAdvertizeDlg)

public:
	CAdvertizeDlg(CWnd* pParent = NULL);   // 标准构造函数
	virtual ~CAdvertizeDlg();
	CString m_sUrl;

// 重写
	HRESULT OnButtonOK(IHTMLElement *pElement);
	HRESULT OnButtonCancel(IHTMLElement *pElement);
	HRESULT STDMETHODCALLTYPE ShowContextMenu(DWORD dwID, POINT *ppt, IUnknown *pcmdtReserved, IDispatch *pdispReserved);

// 对话框数据
	enum { IDD = IDD_ADVERTIZE_DLG, IDH = IDR_HTML_ADVERTIZEDLG };

protected:
	virtual void DoDataExchange(CDataExchange* pDX);    // DDX/DDV 支持
	virtual BOOL OnInitDialog();
	virtual void OnCancel() { };
	virtual void OnOK() { };

	DECLARE_MESSAGE_MAP()
	DECLARE_DHTML_EVENT_MAP()
	virtual void OnDocumentComplete(LPDISPATCH pDisp, LPCTSTR szUrl);
};
