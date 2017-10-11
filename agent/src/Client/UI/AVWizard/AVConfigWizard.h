#pragma once

#include "AudioPlayPP.h"
#include "AudioRecordPP.h"
#include "IntroductionPP.h"
#include "VideoPP.h"

#include "..\pub\linkbutton.h"

// CAVConfigWizard

class CAVConfigWizard : public CPropertySheet
{
	DECLARE_DYNAMIC(CAVConfigWizard)

public:
	CAVConfigWizard(UINT nIDCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	CAVConfigWizard(LPCTSTR pszCaption, CWnd* pParentWnd = NULL, UINT iSelectPage = 0);
	virtual ~CAVConfigWizard();

	void setWindowRect( CWnd* pWnd );
	void drawHead( CDC* pDC, CRect rc, CString sText );

public:
	CLinkButton m_btnBack;
	CLinkButton m_btnNext;
	CLinkButton m_btnFinish;
	CLinkButton m_btnCancel;

protected:
	void createCustomButton( UINT nID, CLinkButton& btn );
	DECLARE_MESSAGE_MAP()
	virtual BOOL OnInitDialog();
	afx_msg void OnPaint();
};


