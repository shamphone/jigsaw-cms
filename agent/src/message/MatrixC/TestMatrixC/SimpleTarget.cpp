#include <windows.h>
#include "simpletarget.h"
#include "MatrixCLib/message/Login.h"
#include "MatrixCLib/MatrixC.h"


SimpleTarget::SimpleTarget(MatrixC* pMatrixC) : MessageTarget(pMatrixC) 
{
    pMatrixC->registerMessageHandler( Login::id, this, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerOne));
    pMatrixC->registerMessageHandler( Login::id, this, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerTwo));
    pMatrixC->registerMessageHandler( Login::id, this, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerThree));
}

SimpleTarget::~SimpleTarget(void)
{
}

void SimpleTarget::HandlerOne(BaseMessage* pMessage) {
    //m_pMatrixC->removeMessageHandler(Login::id, this, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerOne));
    printf("Handler one\n");
    return;
}

void SimpleTarget::HandlerTwo(BaseMessage* pMessage) {
    m_pMatrixC->removeObjectMessageHandler(this);
    printf("Handler two\n");
    return;
}

void SimpleTarget::HandlerThree(BaseMessage* pMessage) {
    printf("Handler three\n");
    return;
}
