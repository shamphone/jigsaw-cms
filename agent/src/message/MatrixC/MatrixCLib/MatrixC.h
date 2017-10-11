#ifndef MATRIX_C_H
#define MATRIX_C_H

#include "MessageTarget.h"

namespace LyvcMessage {

    class MatrixC {

    public:

        // 获取实例
        static MatrixC* getInstance();

        // 释放实例
        static void releaseInstance(MatrixC* pMatrixC);

        // 协议头部
        static const char* LYVC_MESSAGE_HEADER;

    public:

        // 消息系统初始化
        virtual BOOL Create(const char* hostIp, int port) = 0;

        // 消息系统停止
        virtual void Destroy() = 0;

        // 注册/删除 一个消息处理的函数
        virtual void registerMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler) = 0;
        virtual void removeMessageHandler( int messageId, MessageTarget* pMessageTarget, PMSG_HANDLER pHandler) = 0;

        // 删除某个对象注册的全部消息处理函数
        virtual void removeObjectMessageHandler(MessageTarget* pMessageTarget) = 0;

        // 注册意外处理函数
        // 当消息系统发生异常，如Socket连接断开时，调用此函数
        virtual void registerExceptionHandler(MessageTarget* pMessageTarget, PMSG_HANDLER pHandler) = 0;
        virtual void removeExceptionHandler() = 0;

        // 发送消息
        virtual void sendMessage(BaseMessage* pBaseMessage) = 0;

		// 获取当前正在处理消息的ID
		// 这个方法为MultiConferenceMatrixC所加，主要是弥补C++的静态缺陷
		virtual int getCurrentMessageId() = 0;

		// 获取当前连接的服务器IP地址
		virtual string getRemoteIP() = 0;

	public:
		// 设置当前用户的id
		void setDefaultSenderId( __int64 uid ) { m_senderId = uid; };

		__int64 m_senderId;
    };

};


#endif
