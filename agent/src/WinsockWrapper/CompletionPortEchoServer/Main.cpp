#include "stdafx.h"
#include "AcceptThread.h"
#include "IOCompletionPortModel.h"

int main(void)
{
    // Create the iocompletionport
    IOCompletionPortModel iocp;
    iocp.start();

    // Start accept thread
	AcceptThread acceptThread;
	acceptThread.start(&iocp, 5000);

	// Wait for stop
	printf("Press Another Enter to quit the server...\n");
	getchar();
	acceptThread.stop();
    iocp.stop();
	return 0;
}

