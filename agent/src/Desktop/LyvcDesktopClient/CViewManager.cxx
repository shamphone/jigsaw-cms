
#include "stdafx.h"
#include "CViewManager.h"
#include "CView.h"

using namespace rfb;
using namespace win32;


// -=- CViewManager


CViewManager::CViewManager() : MsgWindow(_T("CViewManager"))
{
}

CViewManager::~CViewManager() 
{
	remClient();
}

CView* CViewManager::addClient(__int64 uid, std::string name) 
{
	if(!getView(uid))
	{
		CView* pView = new CView();
		pView->setManager(this);
		pView->initialise();
		pView->setServerUserId(uid);
		string caption = name + string( "µÄ×ÀÃæ" );
		pView->setName( caption.c_str() );
		//m_pView->setVisible(true);
		//m_pView->setDesktopSize(1024, 768);
		m_viewList.push_back(pView);
		return pView;
	}
	else
		return getView(uid);
}

void CViewManager::remClient(__int64 uid)
{
	if (uid != 0)
	{
		CView* pView = getView(uid);
		if (pView == 0)
			return;

		m_viewList.remove(pView);
		delete pView;
	}
	else
	{
		int size = m_viewList.size();
		std::list<CView*>::iterator iter;
		for(int i = 0; i < size; i++)
		{
			iter = m_viewList.begin();
			CView* pView = *iter;
			m_viewList.remove(pView);
			delete pView;
		}
	}
}

LRESULT CViewManager::processMessage(UINT msg, WPARAM wParam, LPARAM lParam) 
{
	if( msg == DESKTOP_CLIENT_CLOSED)
	{
		__int64* pUid = (__int64*)wParam;
		__int64 uid = *pUid;
		delete pUid;

	    map<__int64, list<ClientNotifier*> >::iterator it;
	    it = this->m_notifierMap.find(uid); 
	    _ASSERTE( it != this->m_notifierMap.end() );

		list<ClientNotifier*> clientNotifierList = it->second;
		list<ClientNotifier*>::iterator itNotifier;
		for( itNotifier = clientNotifierList.begin(); itNotifier != clientNotifierList.end(); itNotifier++)
		{
			(*itNotifier)->clientClosed(uid);
		}
		it->second.erase(it->second.begin(), it->second.end());
	}
	return MsgWindow::processMessage(msg, wParam, lParam);
}

CView* CViewManager::getView(__int64 uid)
{
	std::list <CView*>::iterator i;
	for ( i = m_viewList.begin( ); i != m_viewList.end( ); i++ )
	{
		if ((*i)->getServerUserId() == uid)
			return *i;
	}
	return 0;
}
__int64 CViewManager::getTopDesktopClientId()
{
	HWND hWnd = ::GetForegroundWindow();
	std::list <CView*>::iterator i;
	for ( i = m_viewList.begin( ); i != m_viewList.end( ); i++ )
	{
		if ((*i)->getHandle() == hWnd)
			return (*i)->getServerUserId();
	}
	return 0;
}
void CViewManager::addNotifier(__int64 uid, ClientNotifier* pNotifier)
{
	list<ClientNotifier*> notifierList;

	map<__int64, list<ClientNotifier*> >::iterator it;
	it = this->m_notifierMap.find(uid); 
	if( it != this->m_notifierMap.end() )
	{
		notifierList = it->second;
		this->m_notifierMap.erase(it);
	}

	notifierList.push_back(pNotifier);
	this->m_notifierMap.insert( map<__int64, list<ClientNotifier*> >::value_type(uid, notifierList));
}

void CViewManager::remNotifier(__int64 uid)
{
	map<__int64, list<ClientNotifier*> >::iterator it;
	it = this->m_notifierMap.find(uid); 
	_ASSERTE( it != this->m_notifierMap.end() );
	it->second.erase(it->second.begin(), it->second.end());
}