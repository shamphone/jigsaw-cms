// ContactDlg.cpp : implementation file
//

#include "stdafx.h"

#include "FLVCC.h"
#include "ContactDlg.h"
#include ".\contactdlg.h"
#include "MainFrm.h"
#include "ContactListDlg.h"
#include "ContactInfoDlg.h"
#include "UserDefinedMessage.h"
#include "AddContactGroup.h"
#include "LeavewordDlg.h"
#include "AddContactWizard.h"
#include "statusdlg.h"
#include "ShowXMLDlg.h"
#include "..\..\Model\LServer.h"
#include "..\..\Model\ConferenceMgr.h"
#include "..\..\model\contact\ContactMgr.h"
#include "..\..\model\contact\Contact.h"
#include "..\..\model\contact\ContactGroup.h"
#include "..\..\..\Common\Common\XML\XMLParser.h"
#include "..\..\..\Common\Common\PathHelper\PathHelper.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CContactDlg dialog


CContactDlg::CContactDlg(CMainFrame* pMainFrame, CWnd* pParent/* = NULL*/)
	: CDialog(CContactDlg::IDD, pParent)
{
	m_pMainFrame = pMainFrame;
	m_bLDragging = false;
	m_pAddContactWizard = NULL;
	m_pStatusDlg = NULL;
}

CContactDlg::~CContactDlg()
{
	if( m_pStatusDlg )
	{
		m_pStatusDlg->DestroyWindow();
		delete m_pStatusDlg;
	}
}

void CContactDlg::DoDataExchange(CDataExchange* pDX)
{
	CDialog::DoDataExchange(pDX);
	DDX_Control(pDX, IDC_CONTACT_TREE, m_TreeCtrl);
}

BEGIN_MESSAGE_MAP(CContactDlg, CDialog)
	ON_COMMAND(ID_ADDGROUP,OnAddGroup)
	ON_COMMAND(ID_ADDCONTACT,OnAddContact)
	ON_COMMAND(ID_DELETECONTACT,OnDelContact)
	ON_COMMAND(ID_DELGROUP,OnDelGroup)
	ON_COMMAND(ID_BEGINCONF,OnBeginInstantConf)
	ON_COMMAND(ID_RENAMEGROUP,OnRenameGroup)
	ON_COMMAND(ID_CHECKCONTACTINFO,OnCheckContactInfo)

	ON_NOTIFY(NM_DBLCLK, IDC_CONTACT_TREE, OnDblclkContactTree)
	ON_NOTIFY(NM_RCLICK, IDC_CONTACT_TREE, OnNMRclickContactTree)
	ON_NOTIFY(TVN_BEGINDRAG, IDC_CONTACT_TREE, OnTvnBegindragContactTree)

	ON_WM_LBUTTONUP()
	ON_WM_MOUSEMOVE()
	ON_WM_TIMER()
	ON_WM_CREATE()
	ON_WM_SIZE()
	ON_COMMAND(ID_SEND_LEAVEWORD, OnSendLeaveword)
	ON_COMMAND(ID_SEND_EMAIL, OnSendEmail)
	ON_COMMAND(ID_MOVE_CONTACT_TO_GROUP, OnMoveContactToGroup)
	ON_WM_MENUSELECT()
	ON_WM_PAINT()
	ON_NOTIFY(TVN_KEYDOWN, IDC_CONTACT_TREE, OnTvnKeydownContactTree)
	ON_COMMAND(ID_VIEW_CHATLOG, OnViewChatlog)
	ON_COMMAND(ID_COPYCONTACTTOUSER, OnCopycontacttouser)
	ON_COMMAND(ID_COPYCONTACTTOALL, OnCopycontacttoall)
	ON_COMMAND(ID_COPYCONTACTTOGROUP, OnCopycontacttogroup)
	ON_COMMAND(ID_ADDFROMCOMMON, OnAddfromcommon)
	ON_MESSAGE(WM_APPLY_CONTACT, OnApplyContact)
	ON_NOTIFY(TVN_SELCHANGED, IDC_CONTACT_TREE, OnTvnSelchangedContactTree)
	ON_WM_SHOWWINDOW()
	ON_WM_SETFOCUS()
END_MESSAGE_MAP()

/////////////////////////////////////////////////////////////////////////////
// CContactDlg message handlers

BOOL CContactDlg::OnInitDialog() 
{
	CDialog::OnInitDialog();
	createStatusDlg();

	m_ImageList.Create(20, 20, ILC_COLOR16, 6, 1);
	CBitmap bm;
	bm.LoadBitmap(IDB_CONTACT_TREE);
	m_ImageList.Add(&bm, RGB(0, 0, 0));
	m_TreeCtrl.SetImageList(&m_ImageList,TVSIL_NORMAL);
	m_TreeCtrl.SetBkImage(IDB_CONTACTLISTBK);
	m_TreeCtrl.SetTextColor( RGB(0, 35, 136) );
	//m_TreeCtrl.SetBkColor( RGB(250, 250, 250) );

	CBitmap bmp;
	bmp.LoadBitmap( IDB_SCROLLBAR );
	m_hBmpScrollBar = (HBITMAP) bmp.Detach();
	SkinWndScroll( &m_TreeCtrl, m_hBmpScrollBar );

	return TRUE;  // return TRUE unless you set the focus to a control
	              // EXCEPTION: OCX Property Pages should return FALSE
}

void CContactDlg::OnSize(UINT nType, int cx, int cy) 
{
	CDialog::OnSize(nType, cx, cy);
	if( m_pStatusDlg->GetSafeHwnd() )
	{
		m_pStatusDlg->MoveWindow(1, 0, cx-1, 36);
	}
	if(m_TreeCtrl.GetSafeHwnd())
	{
		CWnd *pFrm = m_TreeCtrl.GetParent()->GetParent();
		pFrm->MoveWindow(1, 36, cx - 2, cy - 46);
		//m_TreeCtrl.MoveWindow(1, 36, cx - 2, cy - 46);
	}
}

void CContactDlg::OnDblclkContactTree(NMHDR* pNMHDR, LRESULT* pResult) 
{
	*pResult = 0;
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if(hItem == NULL)
		return;

	if( m_TreeCtrl.GetItemData(hItem) & ITEM_IS_CONTACT )
	{
		LServer* pServer = this->getCurSelServer();
		if( pServer == NULL )
			return;
		ContactMgr* pContactMgr = pServer->getContactMgr();
		if( pContactMgr == NULL )
			return;
		Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
		if( pContact == NULL )
			return;
		if( pContact->getStatus() == CONTACT_ONLINE )
		{
			vector<__int64> v;
			v.push_back( pContact->getId() );
			pServer->getConferenceMgr()->cmdCreateInstantConference(v);
		}
		else
			this->sendLeaveword( pContact->getId() );
	}
}

int CContactDlg::FindMenuItem(CMenu* Menu, LPCTSTR MenuString)
{
   int count = Menu->GetMenuItemCount();
   for (int i = 0; i < count; i++)
   {
      CString str;
      if( Menu->GetMenuString(i, str, MF_BYPOSITION) && (strcmp(str, MenuString) == 0) )
         return i;
   }
   return -1;
}

void CContactDlg::OnNMRclickContactTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTVDISPINFO pTVDispInfo = reinterpret_cast<LPNMTVDISPINFO>(pNMHDR);
	TV_DISPINFO     *ptvinfo;
	ptvinfo = (TV_DISPINFO *)pNMHDR;
	
	CPoint pt;
	GetCursorPos(&pt);
	m_TreeCtrl.ScreenToClient(&pt);
	HTREEITEM hItem;
	hItem = m_TreeCtrl.HitTest(pt);

	CMenu menu;
	CMenu* submenu = NULL;
	//在空白处右键点击
	if( hItem == NULL )
	{
		return;
	}
	else
	{
		//在树的item上右键点击
		ContactMgr* pContactMgr = NULL;
		m_TreeCtrl.Select(hItem,TVGN_CARET | TVGN_DROPHILITE);
		DWORD dwData = m_TreeCtrl.GetItemData(hItem);
		if( dwData & ITEM_IS_CONTACT )
		{
			pContactMgr = this->getCurSelContactMgr();
			if( pContactMgr == NULL )
				return;
			menu.LoadMenu(IDR_CONTACTDLG);
			submenu = menu.GetSubMenu(0);
			//右键点击联系人
			if( dwData & COMMON_CONTACT_GROUP )
			{
				for(int i = 0; i < 3; i++)
				{
					submenu->RemoveMenu(12, MF_BYPOSITION);
				}
				for(int i = 0; i < 3; i++)
				{
					submenu->RemoveMenu(0, MF_BYPOSITION);
				}
				submenu->RemoveMenu(6, MF_BYPOSITION);
				submenu->RemoveMenu(6, MF_BYPOSITION);
			}
			else
			{
				for(int i = 0; i < 4; i++)
				{
					submenu->RemoveMenu(11, MF_BYPOSITION);
				}
			}
			Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
			if( pContact == NULL )
				return;
			//联系人不在线，不能开始即时会议；
			//在线，不能发送留言
			if( pContact->getStatus() == CONTACT_OFFLINE )
			{
				int n = FindMenuItem(submenu, "开始即时会议");
				if (n >= 0)
					submenu->EnableMenuItem(n, MF_GRAYED|MF_BYPOSITION);
			}
			else
			{
				int n = FindMenuItem(submenu, "发送留言");
				if (n >= 0)
					submenu->EnableMenuItem(n, MF_GRAYED|MF_BYPOSITION);
			}
			//联系人是否可以删除
			if( pContact->m_bIsCommon )
			{
				int n = FindMenuItem( submenu, "删除联系人" );
				if( n >= 0 )
					submenu->EnableMenuItem( n, MF_GRAYED|MF_BYPOSITION );
			}
			//是否可以移动联系人
			vector<string> v;
			pContactMgr->getPrivateGroupNames( v );
			if( pContact->m_bIsCommon || v.size() < 2 )
			{
				int n = FindMenuItem(submenu, "移动联系人到组");
				if (n >= 0)
					submenu->EnableMenuItem(n, MF_GRAYED|MF_BYPOSITION);
			}
			else
			{
				m_pMoveContact = pContact;
				//在菜单中添加可以移动到的组
				int n = FindMenuItem(submenu, "移动联系人到组");
				CMenu* mnu = submenu->GetSubMenu(n);
                bool bFirst = true;
				for( unsigned int i = 0; i < v.size(); i++ )
				{
					if( v.at(i) != pContact->m_pGroup->m_sName )
					{
						if( bFirst )
						{
							mnu->ModifyMenu(0, MF_STRING|MF_BYPOSITION, ID_MOVE_CONTACT_TO_GROUP, v.at(i).c_str());
							bFirst = false;
						}
						else
						{
							mnu->InsertMenu(0, MF_STRING|MF_BYPOSITION, ID_MOVE_CONTACT_TO_GROUP, v.at(i).c_str());
						}
					}
				}
			}
			if( pContact->m_bIsCommon )
			{
				m_pMoveContact = pContact;
				//在菜单中添加可以移动到的组
				int n = FindMenuItem(submenu, "添加到我的联系人组");
                if( pContactMgr->isContactById( pContact->getId() ) )
				{
					if (n >= 0)
						submenu->EnableMenuItem(n, MF_GRAYED|MF_BYPOSITION);
				}
				if( n >= 0 )
				{
					CMenu* mnu = submenu->GetSubMenu(n);
					bool bFirst = true;
					for( unsigned int i = 0; i < v.size(); i++ )
					{
						if( bFirst )
						{
							mnu->ModifyMenu(0, MF_STRING|MF_BYPOSITION, ID_ADDFROMCOMMON, v.at(i).c_str());
							bFirst = false;
						}
						else
						{
							mnu->InsertMenu(0, MF_STRING|MF_BYPOSITION, ID_ADDFROMCOMMON, v.at(i).c_str());
						}
					}
				}
			}
			submenu->EnableMenuItem( ID_COPYCONTACTTOGROUP, MF_GRAYED );
			if( !pContactMgr->isContactAdmin() )
			{
				for( int i = 0; i < 4; i++ )
				{
					submenu->RemoveMenu( submenu->GetMenuItemCount() - 1, MF_BYPOSITION);
				}
			}
		}
		else if( dwData & ITEM_IS_GROUP )
		{
			if( dwData & COMMON_CONTACT_GROUP )
				return;
			pContactMgr = this->getCurSelContactMgr();
			if( pContactMgr == NULL )
				return;
			menu.LoadMenu(IDR_CONTACTDLG);
			submenu = menu.GetSubMenu(0);
			//右键点击组
			for (int i = 0; i < 10; i++)
			{
				submenu->RemoveMenu(2, MF_BYPOSITION);
			}
			//是否可以删除和修改
			ContactGroup* pGroup = pContactMgr->getContactGroupByTreeItem( hItem );
			if( pGroup->m_Id == pContactMgr->getDefaultGroupId() )
			{
				submenu->EnableMenuItem(3, MF_GRAYED|MF_BYPOSITION);
				submenu->EnableMenuItem(4, MF_GRAYED|MF_BYPOSITION);
			}
			submenu->EnableMenuItem( ID_COPYCONTACTTOUSER, MF_GRAYED );
			if( !pContactMgr->isContactAdmin() )
			{
				for( int i = 0; i < 4; i++ )
				{
					submenu->RemoveMenu( submenu->GetMenuItemCount() - 1, MF_BYPOSITION);
				}
			}
		}
	}

	//显示菜单
	CPoint point;
	GetCursorPos(&point);
	submenu->TrackPopupMenu(TPM_RIGHTBUTTON, point.x, point.y, this);
	submenu->DestroyMenu();
}

void CContactDlg::OnAddGroup()
{
	CAddContactGroup* dlg = new CAddContactGroup();
	dlg->setTitle("添加组");
	dlg->setDescription("在下面输入组名：");
	INT_PTR nRet = dlg->DoModal();
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	if(nRet == IDOK)
	{
		CString sName = dlg->getGroupName();
		if( pContactMgr->isGroupExist( sName.GetString() ) )
			AfxMessageBox("组 " + sName + " 已存在。");
		else
			pContactMgr->cmdAddGroup( sName.GetString() );
	}
	delete dlg;
}

void CContactDlg::OnAddContact()
{
	ASSERT( this->m_pAddContactWizard == NULL );
	LServer* pServer = getCurSelServer();
	if( pServer == NULL )
	{
		AfxMessageBox( "请先选择一个服务器。");
		return;
	}
	else if( pServer->getStatus() != SERVER_LOGIN )
	{
		CString str;
		str.Format( "您还没有登录到服务器“%s”，请先登录。", pServer->getServerName().c_str() );
		AfxMessageBox( str );
		return;
	}

	this->m_pAddContactWizard = new CAddContactWizard(_T("添加联系人向导"), this, 0);
	this->m_pAddContactWizard->setServer( pServer );
	CAddContactStep1 step1;
	CAddContactStep2 step2;
	CAddContactStep3 step3;
	m_pAddContactWizard->AddPage(&step1);
	m_pAddContactWizard->AddPage(&step2);
	m_pAddContactWizard->AddPage(&step3);
	m_pAddContactWizard->SetWizardMode();
	m_pAddContactWizard->SetActivePage(&step1);

	m_pAddContactWizard->DoModal();
	delete m_pAddContactWizard;
	m_pAddContactWizard = NULL;
	return;
}

USERID CContactDlg::getCurSelGroupId()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if(hItem == NULL)
		return -1;
	if( m_TreeCtrl.GetItemData(hItem) & ITEM_IS_GROUP )
	{
		ContactMgr* pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
			return -1;
		ContactGroup* pGroup = pContactMgr->getContactGroupByTreeItem( hItem );
		if( pGroup != NULL )
			return pGroup->m_Id;
	}	
	return -1;
}

USERID CContactDlg::getCurSelContactId()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if(hItem == NULL)
		return 0;
	if( m_TreeCtrl.GetItemData(hItem) & ITEM_IS_CONTACT )
	{
		ContactMgr* pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
			return 0;
		Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
		if( pContact != NULL )
			return pContact->getId();
	}
	return 0;
}

void CContactDlg::OnDelContact()
{
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
	if( pContact == NULL )
		return;
	CString contactName = pContact->getRealName().c_str();
	int ret = AfxMessageBox("确定要删除联系人" + contactName + "？（这样也会从对方的联系人中删除自己。）", MB_YESNO|MB_ICONQUESTION);
	if( ret == IDYES )
	{
		pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
			return;
		HTREEITEM hGroup = m_TreeCtrl.GetParentItem(hItem);
		m_TreeCtrl.DeleteItem( hItem );
		this->setGroupItemText( hGroup, false, pContact->getStatus() );
		pContactMgr->cmdRemoveContact( pContact );
	}
}

void CContactDlg::OnDelGroup()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if(hItem == NULL)
		return ;
	if( m_TreeCtrl.ItemHasChildren( hItem ) )
	{
		AfxMessageBox("请先清空组！");
		return ;
	}
	else
	{
		ContactMgr* pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
			return;
		ContactGroup* pGroup = pContactMgr->getContactGroupByTreeItem( hItem );
		pContactMgr->cmdRemoveGroup( pGroup );
		m_TreeCtrl.DeleteItem( hItem );
	}
}
void CContactDlg::OnBeginInstantConf()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	LServer* pServer = this->getCurSelServer();
	if( pServer == NULL )
		return;
	ContactMgr* pContactMgr = pServer->getContactMgr();
	if( pContactMgr == NULL )
		return;
	Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
	if( !pContact )
		return;
	if( pContact->getStatus() != CONTACT_ONLINE )
		return;

    vector<__int64> participants;
    participants.push_back( pContact->getId() );
	
    pServer->getConferenceMgr()->cmdCreateInstantConference(participants);
}

void CContactDlg::OnRenameGroup()
{
	CAddContactGroup* dlg = new CAddContactGroup();
	dlg->setTitle("修改组名");
	dlg->setDescription("在下面输入组名：");
	INT_PTR nRet = dlg->DoModal();
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	if(nRet == IDOK)
	{
		CString sName = dlg->getGroupName();
		if( pContactMgr->isGroupExist( sName.GetString() ) )
			AfxMessageBox("组 " + sName + " 已存在。");
		else
		{
			HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
			CString sText = m_TreeCtrl.GetItemText( hItem );
			ContactGroup* pGroup = pContactMgr->getContactGroupByTreeItem( hItem );
			int n = sText.ReverseFind( '(' );
			sText = sText.Mid(n);
			sText = sName + sText;
			pContactMgr->cmdRenameGroup( pGroup, sName.GetString() );
			m_TreeCtrl.SetItemText( hItem, sText );
		}
	}
	delete dlg;
}
void CContactDlg::OnCheckContactInfo()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
	if( !pContact )
		return;

	CContactInfoDlg* dlginfo = new CContactInfoDlg();
	dlginfo->m_sName = pContact->getRealName().c_str();
	dlginfo->m_sEmail = pContact->getEmail().c_str();
	if (pContact->getStatus() == CONTACT_ONLINE )
		dlginfo->m_sStatus = "联机";
	else
		dlginfo->m_sStatus = "脱机";
	dlginfo->m_sGroup = pContact->m_pGroup->m_sName.c_str();
	dlginfo->DoModal();
	delete dlginfo;
}

void CContactDlg::OnViewChatlog()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return;
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
	if( pContact == NULL )
		return;
    
	LServer* pServer = getCurSelServer();
	CString path = PathHelper::getChatMsgPath( pServer->getUserName().c_str(), pServer->getIP().c_str() );
	path = path + "\\";
	path = path + pContact->getRealName().c_str();
	path = path + ".xml";
	CXMLParser xml;
	if( !xml.Load( path ) )
	{
		xml.SetDoc( "<?xml-stylesheet type=\"text/xsl\" href=\"..\\..\\message.xsl\"?><lyvc></lyvc>" );
		xml.Save( path );
	}
	CShowXMLDlg dlg( path, "查看聊天记录" );
	dlg.DoModal();
}

void CContactDlg::OnMouseMove(UINT nFlags, CPoint point)
{
	HTREEITEM hti;
	UINT      flags;

	if( m_bLDragging )
	{
		POINT pt = point;
		ClientToScreen( &pt );
		CImageList::DragMove(pt);
		
		hti = m_TreeCtrl.HitTest(CPoint(point.x,point.y-22),&flags);
		if( hti != NULL )
		{
			CImageList::DragShowNolock(FALSE);

			if( m_htiOldDrop == NULL )
				m_htiOldDrop = m_TreeCtrl.GetDropHilightItem();

			m_TreeCtrl.SelectDropTarget(hti);
			
			m_htiDrop = hti;
			
			if( m_idTimer && hti == m_htiOldDrop )
			{
				KillTimer( m_idTimer );
				m_idTimer = 0;
			}
			
			if( !m_idTimer )
				m_idTimer = SetTimer( 1000, 1000, NULL );

			CImageList::DragShowNolock(TRUE);
		}
	}
	CDialog::OnMouseMove(nFlags, point);
}

void CContactDlg::OnLButtonUp(UINT nFlags, CPoint point)
{
	CDialog::OnLButtonUp(nFlags, point);

	if( m_bLDragging )
	{
		m_bLDragging = false;

		CImageList::DragLeave(this);
		CImageList::EndDrag();
		ReleaseCapture();
		delete m_pDragImage;
		m_TreeCtrl.SelectDropTarget(NULL);
		m_htiOldDrop = NULL;

		ContactMgr* pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
			return;

		//判断是否可以移动
		TRACE1("Drop To Item: %x", m_htiDrop );
		if( m_htiDrag == m_htiDrop || m_TreeCtrl.GetParentItem(m_htiDrag) == m_htiDrop || m_htiDrop == NULL )
		{
			return;
		}
		if( m_TreeCtrl.GetItemData(m_htiDrop) & (ITEM_IS_SERVER|COMMON_CONTACT_GROUP) )
		{
			return;
		}
		//得到移动到的组item
		HTREEITEM hToGroup = m_htiDrop;
		if( m_TreeCtrl.GetItemData(m_htiDrop) & ITEM_IS_CONTACT )
		{
			//同一个组，直接返回
			if( m_TreeCtrl.GetParentItem(m_htiDrag) == m_TreeCtrl.GetParentItem(m_htiDrop) )
				return;
			hToGroup = m_TreeCtrl.GetParentItem(m_htiDrop);
		}
		
		//获取联系人和组
		Contact* pContact = pContactMgr->getContactByTreeItem( m_htiDrag );
		if( pContact == NULL )
			return;
		ContactGroup* pToGroup = pContactMgr->getContactGroupByTreeItem( hToGroup );
		//如果拖动的是公共联系人，添加联系人
		//如果不是公共联系人，移动联系人
		if( pContact->m_bIsCommon )
		{
			if( pContactMgr->isContactById( pContact->getId() ) )
			{
				CString s = pContact->getRealName().c_str();
				AfxMessageBox("用户"+s+"已经在你的联系人中。");
				return;
			}
			pContactMgr->cmdAddContact( pContact, pToGroup );
		}
		else
            pContactMgr->cmdMoveContactToGroup( pContact, pToGroup );

		if( m_idTimer )
		{
			KillTimer( m_idTimer );
			m_idTimer = 0;
		}
		m_TreeCtrl.Expand( hToGroup, TVE_EXPAND );
		m_TreeCtrl.UpdateWindow();
	}
}

void CContactDlg::OnTvnBegindragContactTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	*pResult = 0;
	m_htiDrag = pNMTreeView->itemNew.hItem;
	m_htiDrop = NULL;

	//如果拖动的不是联系人，直接返回
	if( !(m_TreeCtrl.GetItemData(m_htiDrag) & ITEM_IS_CONTACT) )
		return;

	m_pDragImage = m_TreeCtrl.CreateDragImage( m_htiDrag );
	if( !m_pDragImage )
		return;

	m_bLDragging = true;
	CPoint pt(0,0);
	IMAGEINFO ii;
	m_pDragImage->GetImageInfo( 0, &ii );
	pt.x = (ii.rcImage.right - ii.rcImage.left) / 2;
	pt.y = (ii.rcImage.bottom - ii.rcImage.top) / 2;
	TRACE2( "x = %d, y = %d \n", pt.x, pt.y );
	m_pDragImage->BeginDrag( 0, pt );
	pt = pNMTreeView->ptDrag;
	ClientToScreen( &pt );
	pt.y += 36;
	m_pDragImage->DragEnter(NULL,pt);
	TRACE2( "new x = %d, new y = %d \n", pt.x, pt.y );
	SetCapture();
}

void CContactDlg::OnTvnSelchangedContactTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTREEVIEW pNMTreeView = reinterpret_cast<LPNMTREEVIEW>(pNMHDR);
	*pResult = 0;
}

void CContactDlg::OnTimer(UINT nIDEvent) 
{
	if( nIDEvent == m_idTimer )
	{
		KillTimer(m_idTimer);
		HTREEITEM htiFloat = m_TreeCtrl.GetDropHilightItem();
		if( htiFloat && htiFloat == m_htiDrop )
		{
			if( m_TreeCtrl.ItemHasChildren( htiFloat ) )
			{
				m_TreeCtrl.Expand( htiFloat, TVE_EXPAND );
				m_TreeCtrl.InvalidateRect(NULL);
			}
		}
		return ;
	}
	CContactDlg::OnTimer(nIDEvent);
}

void CContactDlg::OnShowWindow(BOOL bShow, UINT nStatus)
{
	CDialog::OnShowWindow(bShow, nStatus);
}

void CContactDlg::OnSetFocus(CWnd* pOldWnd)
{
	CDialog::OnSetFocus(pOldWnd);
	this->m_TreeCtrl.SetFocus();
}

void CContactDlg::setGroupItemText(HTREEITEM hItem, bool bAddContact, int nStatus)
{
	ASSERT( hItem != TVI_ROOT && hItem != NULL );
	ASSERT( m_TreeCtrl.GetItemData( hItem ) & ITEM_IS_GROUP );

	//从(nOnline/nTotal)格式的文本中提取在线人数和总人数
	int nTotal = 0;
	int nOnline = 0;
	CString sText = m_TreeCtrl.GetItemText( hItem );
	int n = sText.ReverseFind( '(' );
	CString sName = sText.Left( n );
	sText = sText.Mid( n + 1, sText.GetLength() - n - 2 );
	n = sText.Find( '/' );
	CString sOnline = sText.Left( n );
	CString sTotal = sText.Mid( n + 1 );
	nTotal = atoi( sTotal );
	nOnline = atoi( sOnline );
	if( bAddContact )
	{
		nTotal++;
		if( nStatus == CONTACT_ONLINE )
			nOnline++;
	}
	else
	{
		nTotal--;
		if( nStatus == CONTACT_ONLINE )
			nOnline--;
	}
	sText.Format( sName + "(%d/%d)", nOnline, nTotal);
	m_TreeCtrl.SetItemText( hItem, sText );
	if( nOnline > 0 )
	{
		if( m_TreeCtrl.GetItemState( hItem, TVIF_STATE ) & TVIS_EXPANDED )
			m_TreeCtrl.SetItemImage( hItem, 4, 4 );
		else
			m_TreeCtrl.SetItemImage( hItem, 5, 5 );
	}
	else
	{
		if( m_TreeCtrl.GetItemState( hItem, TVIF_STATE ) & TVIS_EXPANDED )
			m_TreeCtrl.SetItemImage( hItem, 0, 0 );
		else
			m_TreeCtrl.SetItemImage( hItem, 1, 1 );
	}

	HTREEITEM hParent = m_TreeCtrl.GetParentItem( hItem );
	if( hParent == TVI_ROOT || hParent == NULL || !( m_TreeCtrl.GetItemData( hParent ) & ITEM_IS_GROUP ) )
		return;
	this->setGroupItemText( hParent, bAddContact, nStatus );
}

ContactMgr* CContactDlg::getCurSelContactMgr()
{
	return getCurSelServer()->getContactMgr();
}

BOOL CContactDlg::canModifyCurSel()
{
	//只能对除默认联系人组(我的联系人)外的私有联系人和组进行修改和删除操作
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	if( hItem == NULL )
		return FALSE;
	DWORD dwData = m_TreeCtrl.GetItemData(hItem);
	if( dwData & COMMON_CONTACT_GROUP )
		return FALSE;
	if( dwData & PRIVATE_CONTACT_GROUP )
	{
		if( dwData & ITEM_IS_GROUP )
		{
			ContactMgr* pContactMgr = this->getCurSelContactMgr();
			ContactGroup* pGroup = pContactMgr->getContactGroupByTreeItem( hItem );
			if( pGroup->m_Id == pContactMgr->getDefaultGroupId() )
				return FALSE;
		}
		return TRUE;
	}
	return FALSE;
}

void CContactDlg::createInstantConference()
{
	LServer* pServer = this->getCurSelServer();
	if( pServer == NULL )
		return;
	ContactMgr* pContactMgr = pServer->getContactMgr();
	if( pContactMgr == NULL )
		return;

	CContactListDlg* dlg = new CContactListDlg();
	dlg->m_strMessage = "请在下面的联系人列表中选择要邀请参加会议的人,按住Ctrl或Shift键可以选择多个人。";
	dlg->m_strTitle = "召开即时会议";
	vector<Contact*> v;
	pContactMgr->getContacts( v, CONTACT_ONLINE );
	for( unsigned int i = 0; i < v.size(); i++ )
	{
		dlg->m_UserIds.push_back( v.at(i)->getId() );
		dlg->m_UserNames.push_back( v.at(i)->getRealName() );
	}
	int ret = dlg->DoModal();
	pContactMgr = pServer->getContactMgr();
	if( pContactMgr == NULL )
	{
		delete dlg;
		return;
	}
	if(ret == IDOK)
	{
		unsigned int count = dlg->m_selectedUserIds.size();
		if( count > 0 )
		{
			pServer->getConferenceMgr()->cmdCreateInstantConference(dlg->m_selectedUserIds);
		}
	}
	delete dlg;
}

void CContactDlg::OnSendLeaveword()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
	if( pContact != NULL && pContact->getStatus() == CONTACT_OFFLINE )
        sendLeaveword( pContact->getId() );
}

void CContactDlg::OnSendEmail()
{
	HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
	if( pContact != NULL )
		sendEmail( pContact->getId() );
}

void CContactDlg::sendLeaveword(__int64 uid)
{
	if(uid == 0)//主界面菜单的调用
	{
		CContactListDlg* dlg = new CContactListDlg();
		dlg->m_strMessage = "请在下面的联系人列表中选择要发送留言的人,按住Ctrl或Shift键可以选择多个人。";
		dlg->m_strTitle = "发送留言";

		vector<Contact*> v;
		ContactMgr* pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
			return;
		pContactMgr->getContacts( v, CONTACT_OFFLINE );
		for( unsigned int i = 0; i < v.size(); i++ )
		{
			dlg->m_UserIds.push_back( v.at(i)->getId() );
			dlg->m_UserNames.push_back( v.at(i)->getRealName() );
		}
		int ret = dlg->DoModal();
		pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
		{
			delete dlg;
			return;
		}
		if(ret == IDOK)
		{
			if (dlg->m_selectedUserIds.size() > 0)
			{
				CLeavewordDlg* dlg1 = new CLeavewordDlg();
				dlg1->m_showRect = dlg->m_showRect;
				ret = dlg1->DoModal();
				pContactMgr = this->getCurSelContactMgr();
				if( pContactMgr == NULL )
				{
					delete dlg1;
					return;
				}
				if (ret == IDOK)
				{
					int count = dlg->m_selectedUserIds.size();
					for(int i = 0; i < count; i++)
					{
						string str = getCurSelServer()->getRealName();
						pContactMgr->cmdSendLeaveword(dlg->m_selectedUserIds.at(i), dlg1->m_strLeaveword.GetString(), str);
					}
				}
				delete dlg1;
			}
		}
		delete dlg;
	}
	else//弹出菜单的调用
	{
		CLeavewordDlg* dlg1 = new CLeavewordDlg();
		int ret = dlg1->DoModal();
		ContactMgr* pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
		{
			delete dlg1;
			return;
		}
		if (ret == IDOK)
		{
			string str = getCurSelServer()->getRealName();
			pContactMgr->cmdSendLeaveword(uid, dlg1->m_strLeaveword.GetString(), str);
		}
		delete dlg1;
	}
}

void CContactDlg::sendEmail(__int64 uid)
{
	CString mail = "";

	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;

	if( uid == 0 )
	{
		CContactListDlg* dlg = new CContactListDlg();
		dlg->m_strMessage = "请在下面的联系人列表中选择要发送邮件的人,按住Ctrl或Shift键可以选择多个人。";
		dlg->m_strTitle = "发送邮件";

		vector<Contact*> v;
		pContactMgr->getContacts( v, CONTACT_ONLINE|CONTACT_OFFLINE );
		for( unsigned int i = 0; i < v.size(); i++ )
		{
			dlg->m_UserIds.push_back( v.at(i)->getId() );
			dlg->m_UserNames.push_back( v.at(i)->getRealName() );
		}
		int ret = dlg->DoModal();
		pContactMgr = this->getCurSelContactMgr();
		if( pContactMgr == NULL )
		{
			delete dlg;	
			return;
		}
		if(ret == IDOK)
		{
			int count = dlg->m_selectedUserIds.size();
			if (count > 0)
			{
				mail = pContactMgr->getEmailById(dlg->m_selectedUserIds.at(0)).c_str();
			}
			for(int i = 1; i < count; i++)
			{
				mail = mail + ",";
				mail = mail + pContactMgr->getEmailById(dlg->m_selectedUserIds.at(i)).c_str();
			}
		}
		delete dlg;	
	}
	else
	{
		HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
		Contact* pContact = pContactMgr->getContactByTreeItem( hItem );
		if( pContact == NULL )
			return;
		mail = pContact->getEmail().c_str();
	}
	if (mail != "")
	{
		HINSTANCE h = ShellExecute(NULL, "open", "mailto:"+mail, "", "", SW_SHOW);
		int n = reinterpret_cast<int>(h);
		if (n <= 32)
		{
			AfxMessageBox("没有检测到可用的邮件客户端，请在IE的Internet选项的程序栏中配置。");
		}
	}
}

void CContactDlg::OnMoveContactToGroup()
{
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	ContactGroup* pGroup = pContactMgr->getContactGroupByName( this->m_sMoveToGroup.GetString() );
	if( pGroup == NULL )
		return;
	pContactMgr->cmdMoveContactToGroup( this->m_pMoveContact, pGroup );
	m_TreeCtrl.UpdateWindow();
}

void CContactDlg::OnMenuSelect(UINT nItemID, UINT nFlags, HMENU hSysMenu)
{
	CDialog::OnMenuSelect(nItemID, nFlags, hSysMenu);

	if( nItemID == ID_MOVE_CONTACT_TO_GROUP|| nItemID == ID_ADDFROMCOMMON )
	{
			CMenu mnu;
			mnu.Attach(hSysMenu);
			int n = mnu.GetMenuItemCount();
			for (int i = 0; i < n; i++)
			{
				CString s;
				int status = mnu.GetMenuState(i, MF_BYPOSITION);
				if (status & MF_HILITE)//MF_MOUSESELECT)
				{
					mnu.GetMenuString(i, m_sMoveToGroup, MF_BYPOSITION);
					TRACE0(m_sMoveToGroup);
				}
			}
			mnu.Detach();
	}
}

HTREEITEM CContactDlg::getCommonGroupItem( LServer* pServer )
{
	HTREEITEM hItem = m_TreeCtrl.GetChildItem(TVI_ROOT);
	if( hItem == NULL )
	{
		hItem = m_TreeCtrl.InsertItem( "公共联系人(0/0)", 0, 0 );
		m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
		m_TreeCtrl.SetItemData( hItem, ITEM_IS_GROUP|COMMON_CONTACT_GROUP );
		m_TreeCtrl.SelectItem( hItem );
	}
	return hItem;
}

HTREEITEM CContactDlg::getPrivateGroupItem( LServer* pServer )
{
	HTREEITEM hItem = m_TreeCtrl.GetChildItem(TVI_ROOT);
	hItem = m_TreeCtrl.GetNextSiblingItem( hItem );
	if( hItem == NULL )
	{
		hItem = m_TreeCtrl.InsertItem( "我的联系人(0/0)", 0, 0 );
		m_TreeCtrl.SetItemState( hItem, TVIS_BOLD, TVIS_BOLD );
		m_TreeCtrl.SetItemData( hItem, ITEM_IS_GROUP|PRIVATE_CONTACT_GROUP );
	}
	return hItem;
}

LServer* CContactDlg::getCurSelServer()
{
	return m_pMainFrame->getDefaultServer();
}

void CContactDlg::addServer( LServer* pServer )
{
}

void CContactDlg::modifyServer( LServer* pServer )
{
	if( pServer->getStatus() == SERVER_LOGOUT )
	{
		m_TreeCtrl.DeleteAllItems();
	}
}

void CContactDlg::removeServer( LServer* pServer )
{
}

void CContactDlg::setCurrentUser(CString name)
{
	m_pStatusDlg->m_sDisplayName = name;
	m_pStatusDlg->InvalidateRect( NULL );
}

void CContactDlg::createStatusDlg()
{
	m_pStatusDlg = new CStatusDlg();
	m_pStatusDlg->Create(IDD_STATUS_DLG, this);

	m_pStatusDlg->ShowWindow(TRUE);
}

void CContactDlg::OnPaint()
{
	CPaintDC dc(this); // device context for painting

	CRect rcClient;
	GetClientRect(&rcClient);

	// Create a compatible memory DC 
	CDC memDC;
	memDC.CreateCompatibleDC( &dc );
	// Select a compatible bitmap into the memory DC
	CBitmap bitmap;
	bitmap.CreateCompatibleBitmap( &dc, rcClient.Width(), rcClient.Height() );
	memDC.SelectObject( &bitmap );
	memDC.FillSolidRect( CRect(0, rcClient.bottom - 10, rcClient.right, rcClient.bottom), RGB(228, 240, 254) );
	
	CPen pen;
	pen.CreatePen( PS_SOLID, 1, RGB(186, 197, 225) );
	memDC.SelectObject( &pen );
	CRect rc = rcClient;
	rc.top = -10;
	memDC.RoundRect( rc, CPoint(10, 10) );

	dc.BitBlt( 0, 0, rcClient.right, rcClient.bottom, &memDC, 0, 0, SRCCOPY);
}

void CContactDlg::OnTvnKeydownContactTree(NMHDR *pNMHDR, LRESULT *pResult)
{
	LPNMTVKEYDOWN pTVKeyDown = reinterpret_cast<LPNMTVKEYDOWN>(pNMHDR);
	if( pTVKeyDown->wVKey == VK_DELETE )
	{
		HTREEITEM hItem = m_TreeCtrl.GetSelectedItem();
		if( hItem != NULL )
		{
			if( m_TreeCtrl.GetItemData(hItem) & PRIVATE_CONTACT_GROUP )
			{
				if( m_TreeCtrl.GetItemData(hItem) & ITEM_IS_CONTACT )
					this->OnDelContact();
				else
					this->OnDelGroup();
			}
		}
	}
	*pResult = 0;
}

LRESULT CContactDlg::OnApplyContact( WPARAM wParam, LPARAM lParam )
{
	DBUser* pUser = (DBUser*) wParam;
	CString sName = pUser->strName.c_str();
	__int64 userId = pUser->id;
	delete pUser;
	pUser = NULL;
	LServer* pServer = (LServer*) lParam;
	if( !GetParentOwner()->IsWindowVisible() )
	{
		GetParentOwner()->ShowWindow( TRUE );
		GetParentOwner()->ShowWindow( SW_RESTORE );
	}
	if( !GetParentOwner()->IsTopParentActive() )
		GetParentOwner()->FlashWindow(TRUE);

	int n = AfxMessageBox("用户" + sName + "邀请你加为联系人，是否同意？", MB_YESNO|MB_ICONQUESTION);
	ContactMgr* pContactMgr = pServer->getContactMgr();
	if( pContactMgr == NULL )
		return -1;
	string str = pServer->getRealName();
	if( n == IDYES )
	{
		if( !pContactMgr->isContactById( userId ) )
			pContactMgr->cmdAgreeContact(userId, str);
	}
	else
	{
		pContactMgr->cmdRejectContact(userId, str);
	}
	return 0;
}

void CContactDlg::OnCopycontacttouser()
{
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	pContactMgr->cmdCopyContact( this->getCurSelContactId() );
}

void CContactDlg::OnCopycontacttoall()
{
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	pContactMgr->cmdCopyContact();
}

void CContactDlg::OnCopycontacttogroup()
{
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	pContactMgr->cmdCopyContact( getCurSelGroupId(), true );
}

HTREEITEM CContactDlg::notifyAddGroup( ContactGroup* pGroup )
{
	HTREEITEM hParent = pGroup->m_pParent->m_hItem;
	ASSERT( hParent != NULL );
	if( hParent == NULL )
		return NULL;

	m_TreeCtrl.Invalidate();
	string groupName = pGroup->m_sName + string("(0/0)");
	HTREEITEM hGroup = m_TreeCtrl.InsertItem( groupName.c_str(), 0, 0, hParent );
	m_TreeCtrl.SetItemState( hGroup, TVIS_BOLD, TVIS_BOLD );
	m_TreeCtrl.SetItemData( hGroup, ITEM_IS_GROUP|(pGroup->m_bIsCommon?COMMON_CONTACT_GROUP:PRIVATE_CONTACT_GROUP) );
	CString str;
	str.Format( ":%d:", m_TreeCtrl.GetCount() + (pGroup->m_bIsCommon?-1:0) );
	if( m_strExpandedItem.Find( str ) != -1 )
		m_TreeCtrl.Expand( hParent, TVE_EXPAND );
	m_TreeCtrl.UpdateWindow();
	return hGroup;
}

HTREEITEM CContactDlg::notifyAddContact( Contact* pContact )
{
	HTREEITEM hGroup = pContact->m_pGroup->m_hItem;
	ASSERT( hGroup != NULL );
	if( hGroup == NULL )
		return NULL;

	m_TreeCtrl.Invalidate();
	HTREEITEM hContact = NULL;
	CString sText = pContact->getRealName().c_str();
	if( pContact->getStatus() == CONTACT_ONLINE )
		hContact = m_TreeCtrl.InsertItem( sText + "(联机)", 2, 2, hGroup, TVI_FIRST );
	else
	{
		HTREEITEM hInsertAfter = TVI_FIRST;
		if( m_TreeCtrl.ItemHasChildren( hGroup ) )
		{
			HTREEITEM hItem = m_TreeCtrl.GetChildItem( hGroup );
			while( hItem != NULL )
			{
                if( m_TreeCtrl.GetItemData( hItem ) & ITEM_IS_CONTACT )
				{
					hInsertAfter = hItem;
				}
				hItem = m_TreeCtrl.GetNextSiblingItem( hItem );
			}
		}
		hContact = m_TreeCtrl.InsertItem( sText + "(脱机)", 3, 3, hGroup, hInsertAfter );
	}
	m_TreeCtrl.SetItemData( hContact, 
							ITEM_IS_CONTACT|pContact->getStatus()|(pContact->m_bIsCommon?COMMON_CONTACT_GROUP:PRIVATE_CONTACT_GROUP) );
	setGroupItemText(hGroup, true, pContact->getStatus());

	CString str;
	str.Format( ":%d:", m_TreeCtrl.GetCount() + (pContact->m_bIsCommon?-1:0) );
	if( m_strExpandedItem.Find( str ) != -1 )
		m_TreeCtrl.Expand( hGroup, TVE_EXPAND );

	m_TreeCtrl.UpdateWindow();
	return hContact;
}

void CContactDlg::notifyRemoveContact( HTREEITEM hItem )
{
	HTREEITEM hGroup = m_TreeCtrl.GetParentItem( hItem );
	int nStatus = (CONTACT_ONLINE|CONTACT_OFFLINE) & m_TreeCtrl.GetItemData( hItem );
	setGroupItemText(hGroup, false, nStatus);
	m_TreeCtrl.DeleteItem( hItem );
}

void CContactDlg::notifyApplyContact(__int64 userId, string name, LServer* pServer)
{
	DBUser* pUser = new DBUser();
	pUser->id = userId;
	pUser->strName = name;
	PostMessage( WM_APPLY_CONTACT, (WPARAM)pUser, (LPARAM)pServer );
}

void CContactDlg::notifyAgreeContact(string name)
{
	string* msg = new string("用户" + name + "加您为联系人。");
	::PostMessage(GetApp()->m_pMainWnd->GetSafeHwnd(),WM_RECEIVED_SYSMSG, 0,(LPARAM)msg);
}

void CContactDlg::notifyRejectContact(string name)
{	
	string* msg = new string("用户" + name + "拒绝加您为联系人。");
	::PostMessage(GetApp()->m_pMainWnd->GetSafeHwnd(),WM_RECEIVED_SYSMSG, 0,(LPARAM)msg);
}

void CContactDlg::notifyUserNotFound(string email)
{
	if (this->m_pAddContactWizard != NULL)
	{
		this->m_pAddContactWizard->userNotFount(email);
	}
}

void CContactDlg::notifySearchContactResult(string realName, string email, int status, __int64 uid)
{
	if (this->m_pAddContactWizard != NULL)
	{
		this->m_pAddContactWizard->userInfo(realName, email, status, uid);
	}
}
void CContactDlg::OnAddfromcommon()
{
	ContactMgr* pContactMgr = this->getCurSelContactMgr();
	if( pContactMgr == NULL )
		return;
	ContactGroup* pGroup = pContactMgr->getContactGroupByName( this->m_sMoveToGroup.GetString() );
	if( pGroup == NULL )
		return;
	pContactMgr->cmdAddContact( this->m_pMoveContact, pGroup );
	m_TreeCtrl.UpdateWindow();
}

void CContactDlg::OnCheckserver()
{
}

void CContactDlg::OnRemoveserver()
{
}

void CContactDlg::OnAddserver()
{
}

void CContactDlg::OnLoginserver()
{
}

void CContactDlg::OnCancelloginserver()
{
}

void CContactDlg::OnLogoutserver()
{
}

void CContactDlg::OnRegisternew()
{
}

void CContactDlg::OnLoginanother()
{
}
