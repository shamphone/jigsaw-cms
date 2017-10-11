#include <stdio.h>
#include <time.h>
#include <winsock2.h>
#include <windows.h>

#include "SimpleTarget.h"
#include "HelloTarget.h"
#include "ExceptionTarget.h"
#include "MatrixCLib/MatrixC.h"
#include "MatrixCLib/message/Login.h"

void testTarget()
{
    SimpleTarget* pSimpleTarget = new SimpleTarget(NULL);

    MatrixC* pMatrixC = MatrixC::getInstance();
    pMatrixC->registerMessageHandler(0, pSimpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerOne));
    pMatrixC->registerMessageHandler(1, pSimpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerTwo));
    pMatrixC->registerMessageHandler(2, pSimpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerThree));
}


void printLoginMessage(Login& ex)
{
    printf("username = %s\n", ex.username.c_str());
    printf("password = %s\n", ex.password.c_str());
}

void testMessageExample()
{
    Login login;
    login.username = "haha";
    login.password = "baba";

    printLoginMessage(login);

    string s = login.toXML();
    printf("%s\n", s.c_str());

    Login login2;
    if( !login2.fromXML(s)) {
        printf("Can't deserialize string\n");
    }
    printLoginMessage(login2);
}

MatrixC* pMatrixC = NULL;

void CALLBACK TimerProc(
	HWND hwnd,
    UINT uMsg,
    UINT_PTR idEvent,
    DWORD dwTime)
{
	static int count = 0;
    Login login;

    switch(count)
    {
    case 0:
        login.username = "bbbb";
        login.password = "2222";
        break;

    case 1:
        login.username = "cccc";
        login.password = "3333";
        break;

    case 2:
        login.username = "Hello";
        login.password = "44444";
        break;

    case 3:
    	PostQuitMessage(0);
	    return;
    }

    pMatrixC->sendMessage(&login);
    count++;
    return;
}

void testSendMessage()
{
    // Create matrixC
    pMatrixC = MatrixC::getInstance();
    pMatrixC->Create("127.0.0.1", 8081);

    // Add Message Handler
    SimpleTarget simpleTarget(pMatrixC);

    HelloTarget helloTarget(pMatrixC);

    ExceptionTarget exTarget(pMatrixC);

    // Remove message Handler
    pMatrixC->removeMessageHandler( Login::id, &simpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerThree));

    // Send default message
    Login login;
    login.username = "aaaa";
    login.password = "1111";
    pMatrixC->sendMessage(&login);

	// Create a timer
	DWORD dwInterval = 1000 * 5;
	SetTimer(NULL, 0, dwInterval, TimerProc);

	// Enter Message loop
	MSG msg;
    while(GetMessage(&msg, NULL, NULL, NULL))
	{
		TranslateMessage(&msg);
		DispatchMessage(&msg);
	}

    // quit
    pMatrixC->Destroy();
    return;
}

void testAddMessageHandler()
{
    pMatrixC = MatrixC::getInstance();
    SimpleTarget simpleTarget(pMatrixC);
    pMatrixC->registerMessageHandler( Login::id, &simpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerOne));
    pMatrixC->registerMessageHandler( Login::id, &simpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerTwo));
}

void testRemoveObjectHandler()
{
    pMatrixC = MatrixC::getInstance();
    SimpleTarget simpleTarget(pMatrixC);
    SimpleTarget simpleTarget2(pMatrixC);
    //pMatrixC->registerMessageHandler( Login::id, &simpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerOne));
    //pMatrixC->registerMessageHandler( Login::id, &simpleTarget, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerTwo));
    //pMatrixC->registerMessageHandler( Login::id, &simpleTarget2, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerOne));
    //pMatrixC->registerMessageHandler( Login::id, &simpleTarget2, static_cast<PMSG_HANDLER>(SimpleTarget::HandlerTwo));
    pMatrixC->removeObjectMessageHandler(&simpleTarget);    
}

void main(void)
{
    testSendMessage();
}
