#pragma once

/*
 *  我们使用该类包装原始的MatrixC对象，
 *  并根据conferenceId对于会议室的各类消
 *  息处理模块进行分流.
 *
 *  通过该模块发送出去的每一个消息，将携带
 *  conferenceId信息。
 *
 *  该模块收到信息之后，首先判断该信息是否
 *  与自身的会议ID相符，如果不是，则跳过该消息
 */

#include "Message/MatrixC/MatrixCLib/MatrixC.h"
#include "Message/MatrixC/MatrixCLib/MessageHandlerItem.h"
using namespace LyvcMessage;

struct PendingMessageHandlerItem {

    int messageId;
    MessageTarget* pMessageTarget;
    PMSG_HANDLER pMessageHandler;

    PendingMessageHandlerItem() {
        messageId = 0;
        pMessageTarget = NULL;
        pMessageHandler = NULL;
    }
};

class MultiConferenceHandlerStub;

class MultiConferenceMatrixC : public LyvcMessage::MatrixC {

private:

    // Real matrix
    MatrixC* m_pMatrixC;

    // Conference id of this matrix
    __int64 m_conferenceId;

    // Stub handler
    MultiConferenceHandlerStub* m_pMultiConferenceHandlerStub;

    // 已经在实际的MatrixC中注册过消息编号的列表
    list<int> m_RegisteredMessageList;

    // Store Message Handler
    typedef std::list<MessageHandlerItem> MessageHandlerList;
    typedef std::map<int, MessageHandlerList*> MessageMap;
    MessageMap m_messageMap;

    // Indicate if we are in the message handler
    bool m_inMessageHandler;

    // Pending message handler
    typedef std::list<PendingMessageHandlerItem> PendingMessageHandlerList;
    PendingMessageHandlerList m_addList;
    PendingMessageHandlerList m_removeList;
    std::list<MessageTarget*> m_removeObjectList;

public:
    MultiConferenceMatrixC(MatrixC* pMatrixC, __int64 conferenceId);
    ~MultiConferenceMatrixC();

public:

    // 消息系统初始化
    virtual BOOL Create(const char* hostIp, int port);

    // 消息系统停止
    virtual void Destroy();

    // 注册/删除一个消息处理的函数
	virtual void registerMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
    virtual void removeMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);

    // 删除某个对象注册的全部消息处理函数
    virtual void removeObjectMessageHandler(MessageTarget* pMessageTarget);

    // 注册意外处理函数
    // 当消息系统发生异常，如Socket连接断开时，调用此函数
    // 实际上客户端的异常处理由LServer统一进行，因此我们不实现，也不允许我们的客户调用这些方法
    virtual void registerExceptionHandler(MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
    virtual void removeExceptionHandler();

    // 发送消息
    virtual void sendMessage(BaseMessage* pBaseMessage);

	// 获取当前正在处理的消息ID
	virtual int getCurrentMessageId();

	// 获取当前连接的服务器IP地址
	virtual string getRemoteIP();

	friend MultiConferenceHandlerStub;

private:
    void registerMessageHandlerImpl( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
    void removeMessageHandlerImpl( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
    void removeObjectMessageHandlerImpl(MessageTarget* pMessageTarget);
};

