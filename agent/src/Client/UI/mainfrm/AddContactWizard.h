#pragma once

#include "AddContactStep1.h"
#include "AddContactStep2.h"
#include "AddContactStep3.h"

#include "..\pub\linkbutton.h"

class LServer;
// CAddContactWizard

class CAddContactWizard : public CPropertySheet
{
	DECLARE_DYNAMIC(CAddContactWizard)

public:
	CAddContactWizard(UINT nIDCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	CAddContactWizard(LPCTSTR pszCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	virtual ~CAddContactWizard();

	void setServer(LServer* pServer);
	void searchContact(string email);
	void userNotFount(string email);
	void userInfo(string realName, string email, int status, __int64 uid);
	bool applyContact(__int64 uid);
	void setWindowRect( int nPage );
	void drawHead( CDC* pDC, CRect rc, CString sText );
	
	CLinkButton m_btnBack;
	CLinkButton m_btnNext;
	CLinkButton m_btnFinish;
	CLinkButton m_btnCancel;

protected:
	void createCustomButton( UINT nID, CLinkButton& btn );
	virtual BOOL OnInitDialog();
	DECLARE_MESSAGE_MAP()
	afx_msg void OnPaint();
	afx_msg void cancel();

private:
	LServer* m_pServer;
};


