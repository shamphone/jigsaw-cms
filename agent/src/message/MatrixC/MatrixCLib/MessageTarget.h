#ifndef MESSAGE_TARGET_H
#define MESSAGE_TARGET_H

namespace LyvcMessage {

class MessageTarget;
class BaseMessage;
class MatrixC;

typedef void (MessageTarget::*PMSG_HANDLER)(BaseMessage* pMessage);

class MessageTarget
{
protected:
    // Pointer to underlying message facility
    MatrixC* m_pMatrixC;

public:
    MessageTarget(MatrixC* pMatrixC)
    {
        m_pMatrixC = pMatrixC;
    }

public:

    // 调用该函数完成初始化工作
	virtual bool create() = 0;

    // 调用该函数完成清理工作
	virtual void destroy() = 0;

}; // class

}; // namespace

#endif
