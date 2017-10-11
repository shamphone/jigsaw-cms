#include <windows.h>
#include <string>
using namespace std;

#include ".\hellotarget.h"
#include "MatrixCLib/message/Login.h"
#include "MatrixCLib/MatrixC.h"

HelloTarget::HelloTarget(MatrixC* pMatrixC) : MessageTarget(pMatrixC) 
{
    pMatrixC->registerMessageHandler( Login::id, this, static_cast<PMSG_HANDLER>(HelloTarget::LoginHandler));
}

HelloTarget::~HelloTarget(void)
{
}

void HelloTarget::LoginHandler(BaseMessage* pMessage)
{
    m_pMatrixC->registerMessageHandler( Login::id, this, static_cast<PMSG_HANDLER>(HelloTarget::LoginHandler));
    Login* pLogin = (Login*)pMessage;
    printf("%s %s\n", pLogin->username.c_str(), pLogin->password.c_str());
}
