#include "stdafx.h"
#include "MultiConferenceHandlerStub.h"
#include "MultiConferenceMatrixC.h"
#include "Message/MatrixC/MatrixCLib/message/BaseMessage.h"

MultiConferenceHandlerStub::MultiConferenceHandlerStub( MultiConferenceMatrixC* pMultiConferenceMatrixC)
    : LyvcMessage::MessageTarget( NULL )
{
    this->m_pMultiConferenceMatrixC = pMultiConferenceMatrixC;
}

MultiConferenceHandlerStub::~MultiConferenceHandlerStub()
{
}

bool MultiConferenceHandlerStub::create()
{
    return true;
}

void MultiConferenceHandlerStub::destroy()
{
    return;
}

void MultiConferenceHandlerStub::StubHandler(LyvcMessage::BaseMessage* pMessage)
{
    // Test if the message is for this conference, if not skip it.
	// In some cases, such as UserChannelBroken message, it is not sent by a
	// peer conference logic module, but the conference modules wants to handle it,
	// the conferenceid of these messages are 0.
    if( pMessage->_conferenceId != this->m_pMultiConferenceMatrixC->m_conferenceId
		&& pMessage->_conferenceId != 0)
    {
        return;
    }

    // Call real handler
	int messageId = m_pMultiConferenceMatrixC->m_pMatrixC->getCurrentMessageId();
	MultiConferenceMatrixC::MessageMap::iterator it = m_pMultiConferenceMatrixC->m_messageMap.find(messageId);
    if( it == m_pMultiConferenceMatrixC->m_messageMap.end() )
    {
        // Can't find handler list, it is impossible, asserte here.
        _ASSERTE(FALSE);
        return;
    }

    m_pMultiConferenceMatrixC->m_inMessageHandler = true;
	MultiConferenceMatrixC::MessageHandlerList* pHandlerList = it->second;
	MultiConferenceMatrixC::MessageHandlerList::iterator handlerIt = pHandlerList->begin();
    while( handlerIt != pHandlerList->end() ) {
        MessageHandlerItem handlerItem = *handlerIt;
        (handlerItem.pMessageTarget->*handlerItem.pMessageHandler)(pMessage);
        handlerIt++;
    }
    m_pMultiConferenceMatrixC->m_inMessageHandler = false;

    // Add/Remove pending message handler.
    for( MultiConferenceMatrixC::PendingMessageHandlerList::iterator it = m_pMultiConferenceMatrixC->m_addList.begin();
        it != m_pMultiConferenceMatrixC->m_addList.end();
        it++)
    {
        PendingMessageHandlerItem item = *it;
        m_pMultiConferenceMatrixC->registerMessageHandlerImpl(item.messageId, item.pMessageTarget, item.pMessageHandler);
    }
    m_pMultiConferenceMatrixC->m_addList.clear();

    for( MultiConferenceMatrixC::PendingMessageHandlerList::iterator it = m_pMultiConferenceMatrixC->m_removeList.begin();
        it != m_pMultiConferenceMatrixC->m_removeList.end();
        it++)
    {
        PendingMessageHandlerItem item = *it;
        m_pMultiConferenceMatrixC->removeMessageHandlerImpl(item.messageId, item.pMessageTarget, item.pMessageHandler);
    }
    m_pMultiConferenceMatrixC->m_removeList.clear();

    for( std::list<MessageTarget*>::iterator it = m_pMultiConferenceMatrixC->m_removeObjectList.begin();
        it != m_pMultiConferenceMatrixC->m_removeObjectList.end();
        it++)
    {
        MessageTarget* pMessageTarget = *it;
        m_pMultiConferenceMatrixC->removeObjectMessageHandlerImpl(pMessageTarget);
    }
    m_pMultiConferenceMatrixC->m_removeObjectList.clear();
}

