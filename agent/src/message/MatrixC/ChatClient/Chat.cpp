#include "stdafx.h"
#include "ChatClient.h"
#include "ChatClientDlg.h"
#include "Chat.h"
#include <string>
using namespace std;
#include "MatrixCLib/message/broadcast.h"

Chat::Chat(	LyvcMessage::MatrixC* pMatrixC) : LyvcMessage::MessageTarget(pMatrixC)
{
	m_pMatrixC->registerMessageHandler(LyvcMessage::Broadcast::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(Chat::broadcast));
}

void Chat::broadcast(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::Broadcast* pMessage = (LyvcMessage::Broadcast*)pBaseMessage;
    CChatClientApp* pApp = (CChatClientApp*)AfxGetApp();
    CChatClientDlg* pDlg = (CChatClientDlg*)pApp->GetMainWnd();
    pDlg->AppendText( pMessage->content.c_str());
}

