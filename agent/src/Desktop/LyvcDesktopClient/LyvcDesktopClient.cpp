#include "StdAfx.h"
#include ".\lyvcdesktopclient.h"
#include "CMsgReader.h"
#include "CLyvcMsgWriter.h"
#include "cview.h"
#include "../rfb/rdr/LyvcInStream.h"

using namespace rfb::win32;

LyvcDesktopClient::LyvcDesktopClient()
{
	m_pMsgReader = 0;
	m_pMsgWriter = 0;
	m_bStarted = false;
	m_is = 0;
}

LyvcDesktopClient::~LyvcDesktopClient(void)
{
	stop();

	if(m_pMsgReader)
		delete m_pMsgReader;
	m_pMsgReader = 0;
	if(m_pMsgWriter)
		delete m_pMsgWriter;
	m_pMsgWriter = 0;
	if (m_is)
		delete m_is;
}

void LyvcDesktopClient::start(LyvcMessage::MatrixC* pMatrixC)
{
	if(!m_bStarted)
	{
		m_pMsgReader = new CMsgReader(this);
		m_pMsgWriter = new CLyvcMsgWriter(pMatrixC);
		m_bStarted = true;

		m_is = new LyvcInStream();
		m_pMsgReader->setInStream(m_is);
	}
}

void LyvcDesktopClient::stop()
{
	vm.remClient();
}

void LyvcDesktopClient::addClient(__int64 uid, std::string name, ClientNotifier* pClientNotifier)
{
	vm.addClient(uid, name);
	vm.addNotifier(uid, pClientNotifier);
	vm.getView(uid)->setMsgWriter(m_pMsgWriter);
}

void LyvcDesktopClient::addNotifier(__int64 uid, ClientNotifier* pClientNotifier)
{
	vm.addNotifier(uid, pClientNotifier);
}

void LyvcDesktopClient::remClient(__int64 uid)
{
	vm.remClient(uid);
	vm.remNotifier(uid);
}

void LyvcDesktopClient::startControl(__int64 uid)
{
	CView* pView = vm.getView(uid);
	if(pView)
		pView->sendKeyMouse(true);
}

void LyvcDesktopClient::stopControl(__int64 uid)
{
	CView* pView = vm.getView(uid);
	if(pView)
		pView->sendKeyMouse(false);
}

bool LyvcDesktopClient::isWatchingUser(__int64 uid)
{
	return vm.getView(uid) != NULL;
}

void LyvcDesktopClient::receiveDesktopPacket(char* buffer, int len, __int64 uid)
{
	//传进来的uid有可能没有对应的view，这主要是可能从网络延迟引起，所以需要进行处理
	if (vm.getView(uid))
	{
        this->m_is->setData(buffer, len);
		this->m_pMsgReader->readMsg(uid);
	}
}

CMsgHandler* LyvcDesktopClient::getMsgHandler(__int64 uid)
{
	return (CMsgHandler*)(vm.getView(uid));
}

__int64 LyvcDesktopClient::getTopDesktopClientId()
{
	return vm.getTopDesktopClientId();
}
