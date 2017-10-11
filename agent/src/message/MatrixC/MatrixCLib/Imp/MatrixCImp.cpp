#include "StdAfx.h"
#include "MatrixCImp.h"
#include "Message/BaseMessage.h"
#include "Message/MessageFactory.h"
#include "IOManager/IOManager.h"

LyvcMessage::MatrixCImp::MatrixCImp()
{
    this->m_pIOManager = NULL;
    this->m_inMessageHandler = false;

	this->m_currentMessageId = 0;
}

LyvcMessage::MatrixCImp::~MatrixCImp()
{
}

BOOL LyvcMessage::MatrixCImp::Create(const char* hostIp, int port)
{
    // 创建IOManager
    this->m_pIOManager = new IOManager(LyvcMessage::MatrixCImp::ioManagerCallbackProc, this, hostIp, port);
    if( !this->m_pIOManager->start() )
    {
        delete this->m_pIOManager;
        this->m_pIOManager = NULL;
        return FALSE;
    }

	this->m_remoteIP = string(hostIp);

    return TRUE;
}

void LyvcMessage::MatrixCImp::Destroy()
{
    // 关闭IOManager
    this->m_pIOManager->stop();
    delete this->m_pIOManager;
    this->m_pIOManager = NULL;

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

    return;
}


void LyvcMessage::MatrixCImp::registerMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler)
{
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

void LyvcMessage::MatrixCImp::removeMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler)
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

void LyvcMessage::MatrixCImp::removeObjectMessageHandler(MessageTarget* pMessageTarget)
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

void LyvcMessage::MatrixCImp::registerExceptionHandler(MessageTarget* pMessageTarget, PMSG_HANDLER pHandler)
{
    this->m_exceptionHandler.pMessageTarget = pMessageTarget;
    this->m_exceptionHandler.pMessageHandler = pHandler;
    return;
}

void LyvcMessage::MatrixCImp::removeExceptionHandler()
{
    this->m_exceptionHandler.pMessageHandler = NULL;
    this->m_exceptionHandler.pMessageTarget = NULL;
    return;
}

void LyvcMessage::MatrixCImp::sendMessage(BaseMessage* pBaseMessage)
{
	pBaseMessage->_senderId = this->m_senderId;
    string message = pBaseMessage->toXML();
    this->m_pIOManager->sendData(message.c_str(), message.length());
    return;
}

int LyvcMessage::MatrixCImp::getCurrentMessageId()
{
	return this->m_currentMessageId;
}

string LyvcMessage::MatrixCImp::getRemoteIP()
{
	return string(this->m_remoteIP);
}

void LyvcMessage::MatrixCImp::ioManagerCallbackProc(void* pObject, const char* pBuffer, int nLength)
{
    LyvcMessage::MatrixCImp* _this = (LyvcMessage::MatrixCImp*)pObject;

    // Judge if it is the network broken message
    if( pBuffer == NULL && nLength == 0)
    {
        if( _this->m_exceptionHandler.pMessageTarget != NULL && _this->m_exceptionHandler.pMessageHandler != NULL)
        {
            (_this->m_exceptionHandler.pMessageTarget->*(_this->m_exceptionHandler.pMessageHandler))(NULL);
        }
        else
        {
            _RPTF0(_CRT_WARN, "Network connection broken, but no exception handler installed.");
        }
        return;
    }

    // Get message
    int messageId;
    BaseMessage* pBaseMessage = MessageFactory::createMessage(string(pBuffer, nLength), messageId);
    if( pBaseMessage == NULL )
    {
        _ASSERTE(FALSE);
        return;
    }

    // Dispatch message
    MessageMap::iterator it = _this->m_messageMap.find(messageId);
    if( it == _this->m_messageMap.end() )
    {
        // Can't find handler
        return;
    }

    _this->m_inMessageHandler = true;
	_this->m_currentMessageId = messageId;

    MessageHandlerList* pHandlerList = it->second;
    MessageHandlerList::iterator handlerIt = pHandlerList->begin();
    while( handlerIt != pHandlerList->end() ) {
        MessageHandlerItem handlerItem = *handlerIt;
        (handlerItem.pMessageTarget->*handlerItem.pMessageHandler)(pBaseMessage);
        handlerIt++;
    }

	_this->m_currentMessageId = 0;
    _this->m_inMessageHandler = false;

    // Add/Remove pending message handler.
    for( PendingMessageHandlerList::iterator it = _this->m_addList.begin();
        it != _this->m_addList.end();
        it++)
    {
        PendingMessageHandlerItem item = *it;
        _this->registerMessageHandlerImpl(item.messageId, item.pMessageTarget, item.pMessageHandler);
    }
    _this->m_addList.clear();

    for( PendingMessageHandlerList::iterator it = _this->m_removeList.begin();
        it != _this->m_removeList.end();
        it++)
    {
        PendingMessageHandlerItem item = *it;
        _this->removeMessageHandlerImpl(item.messageId, item.pMessageTarget, item.pMessageHandler);
    }
    _this->m_removeList.clear();

    for( std::list<MessageTarget*>::iterator it = _this->m_removeObjectList.begin();
        it != _this->m_removeObjectList.end();
        it++)
    {
        MessageTarget* pMessageTarget = *it;
        _this->removeObjectMessageHandlerImpl(pMessageTarget);
    }
    _this->m_removeObjectList.clear();

	delete pBaseMessage;
    return;
}

void LyvcMessage::MatrixCImp::registerMessageHandlerImpl( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler)
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

void LyvcMessage::MatrixCImp::removeMessageHandlerImpl( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler)
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
    return;
}

void LyvcMessage::MatrixCImp::removeObjectMessageHandlerImpl(MessageTarget* pMessageTarget)
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
