#ifndef MAXTRIX_C_IMP
#define MAXTRIX_C_IMP

#include "MatrixC.h"
#include "MessageHandlerItem.h"
#include "PendingMessageHandlerItem.h"

namespace LyvcMessage {

    class IOManager;
    class MatrixCImp : public MatrixC {

    private:

        // Exception Handler
        MessageHandlerItem m_exceptionHandler;

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

        // IO Manager
        IOManager* m_pIOManager;

		// Current dispatched message id
		int m_currentMessageId;

		// Remote server ip
		string m_remoteIP;

    public:
        MatrixCImp();
        ~MatrixCImp();


    public:

        // 消息系统初始化
        virtual BOOL Create(const char* hostIp, int port);

        // 消息系统停止
        virtual void Destroy();

        // 注册一个消息处理的函数
        virtual void registerMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
        virtual void removeMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);

        // 删除某个对象注册的全部消息处理函数
        virtual void removeObjectMessageHandler(MessageTarget* pMessageTarget);

        // 注册意外处理函数
        // 当消息系统发生异常，如Socket连接断开时，调用此函数
        virtual void registerExceptionHandler(MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
        virtual void removeExceptionHandler();

        // 发送消息
        virtual void sendMessage(BaseMessage* pBaseMessage);

 		virtual int getCurrentMessageId();
		virtual string getRemoteIP();

	private:
        // 注册一个消息处理的函数
        void registerMessageHandlerImpl( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);
        void removeMessageHandlerImpl( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler);

        // 删除某个对象注册的全部消息处理函数
        void removeObjectMessageHandlerImpl(MessageTarget* pMessageTarget);

    private:

        // IOManager 部分的回调函数
        static void ioManagerCallbackProc(void* pObject, const char* pBuffer, int nLength);
    };
};

#endif
