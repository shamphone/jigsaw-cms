#include "stdafx.h"
#include "MultiConferenceMatrixC.h"
#include "MultiConferenceHandlerStub.h"
#include "Message/MatrixC/MatrixCLib/message/BaseMessage.h"

MultiConferenceMatrixC::MultiConferenceMatrixC(MatrixC* pMatrixC, __int64 conferenceId)
{
    m_pMatrixC = pMatrixC;
    m_conferenceId = conferenceId;

    m_pMultiConferenceHandlerStub = new MultiConferenceHandlerStub(this);
    m_inMessageHandler = false;
}

MultiConferenceMatrixC::~MultiConferenceMatrixC()
{
    // 删除本身注册的所有Message Handler
    this->m_pMatrixC->removeObjectMessageHandler(this->m_pMultiConferenceHandlerStub);

    // 删除所有的Message Handler
    MessageMap::iterator it = this->m_messageMap.begin();
    while( it != this->m_messageMap.end() )
    {
        MessageHandlerList* pHandlerList = it->second;
        _ASSERTE(pHandlerList->empty());
        delete pHandlerList;
        it++;
    }
    this->m_messageMap.clear();

    // 删除stub 对象
    delete m_pMultiConferenceHandlerStub;
    m_pMultiConferenceHandlerStub = NULL;
}

BOOL MultiConferenceMatrixC::Create(const char* hostIp, int port)
{
	return TRUE;
}

void MultiConferenceMatrixC::Destroy()
{
	return;
}

void MultiConferenceMatrixC::registerMessageHandler( 
        int messageId,
        MessageTarget* pMessageTarget,
        PMSG_HANDLER pHandler)
{
    // 判断是否已经注册过这个消息,如果没有，向真正的MatrixC注册该消息
    if( find(this->m_RegisteredMessageList.begin(), this->m_RegisteredMessageList.end(), messageId)
            == this->m_RegisteredMessageList.end())
    {
        this->m_pMatrixC->registerMessageHandler(
                messageId,
                this->m_pMultiConferenceHandlerStub, 
                static_cast<LyvcMessage::PMSG_HANDLER>(MultiConferenceHandlerStub::StubHandler));
		this->m_RegisteredMessageList.push_back(messageId);
    }

    if(!this->m_inMessageHandler)
    {
        this->registerMessageHandlerImpl(messageId, pMessageTarget, pHandler);
    }
    else
    {
        PendingMessageHandlerItem item;
        item.messageId = messageId;
        item.pMessageTarget = pMessageTarget;
        item.pMessageHandler = pHandler;
        this->m_addList.push_back(item);
    }
}

void MultiConferenceMatrixC::removeMessageHandler(
        int messageId,
        MessageTarget* pMessageTarget,
        PMSG_HANDLER pHandler)
{
    if(!this->m_inMessageHandler)
    {
        this->removeMessageHandlerImpl(messageId, pMessageTarget, pHandler);
    }
    else
    {
        PendingMessageHandlerItem item;
        item.messageId = messageId;
        item.pMessageTarget = pMessageTarget;
        item.pMessageHandler = pHandler;
        this->m_removeList.push_back(item);
    }
}

void MultiConferenceMatrixC::removeObjectMessageHandler(MessageTarget* pMessageTarget)
{
    if(!this->m_inMessageHandler)
    {
        this->removeObjectMessageHandlerImpl(pMessageTarget);
    }
    else
    {
        this->m_removeObjectList.push_back(pMessageTarget);
    }
}

void MultiConferenceMatrixC::registerExceptionHandler(MessageTarget* pMessageTarget, PMSG_HANDLER pHandler)
{
    _ASSERTE(FALSE);
}

void MultiConferenceMatrixC::removeExceptionHandler()
{
    _ASSERTE(FALSE);
}

void MultiConferenceMatrixC::sendMessage(BaseMessage* pBaseMessage)
{
    pBaseMessage->_conferenceId = this->m_conferenceId;
    this->m_pMatrixC->sendMessage(pBaseMessage);
}

int MultiConferenceMatrixC::getCurrentMessageId()
{
	return this->m_pMatrixC->getCurrentMessageId();
}

string MultiConferenceMatrixC::getRemoteIP()
{
	return this->m_pMatrixC->getRemoteIP();
}

void MultiConferenceMatrixC::registerMessageHandlerImpl(
        int messageId,
        MessageTarget* pMessageTarget,
        PMSG_HANDLER pHandler)
{
    MessageHandlerItem handlerItem;
    handlerItem.pMessageTarget = pMessageTarget;
    handlerItem.pMessageHandler = pHandler;

    // Find the handler list of the message id
    MessageHandlerList* pHandlerList = NULL;
    MessageMap::iterator it = this->m_messageMap.find(messageId);
    if( it != m_messageMap.end() )
    {
        pHandlerList = it->second;
        pHandlerList->push_back(handlerItem);
    }
    else
    {
        pHandlerList = new MessageHandlerList();
        pHandlerList->push_back(handlerItem);
        this->m_messageMap.insert( MessageMap::value_type(messageId, pHandlerList));
    }
    return;
}

void MultiConferenceMatrixC::removeMessageHandlerImpl(
        int messageId,
        MessageTarget* pMessageTarget,
        PMSG_HANDLER pHandler)
{
    MessageHandlerItem handlerItem;
    handlerItem.pMessageTarget = pMessageTarget;
    handlerItem.pMessageHandler = pHandler;

    // Find the handler list of the message id
    MessageHandlerList* pHandlerList = NULL;
    MessageMap::iterator it = this->m_messageMap.find(messageId);
    if( it != m_messageMap.end() )
    {
        pHandlerList = it->second;
        pHandlerList->remove(handlerItem);
    }
    else
    {
        // Can't find the specified item
        _ASSERTE(FALSE);
    }

    // We won't bother to remove the stub handler when the list is empty, it
    // simpley doesn't hurt. The destructor will clear everything.
    return;
}

void MultiConferenceMatrixC::removeObjectMessageHandlerImpl(MessageTarget* pMessageTarget)
{
    for(MessageMap::iterator it= this->m_messageMap.begin();
        it != this->m_messageMap.end();
        ++it)
    {
        MessageHandlerList* pHandlerList = it->second;
        MessageHandlerList::iterator it2 = pHandlerList->begin();
        while( it2 != pHandlerList->end())
        {
            MessageHandlerItem handlerItem = *it2;
            if( handlerItem.pMessageTarget == pMessageTarget )
            {
                it2 = pHandlerList->erase(it2);
            }
            else
            {
                ++it2;
            }
        }
    }
}

