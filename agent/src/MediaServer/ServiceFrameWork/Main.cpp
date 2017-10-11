#include "StdAfx.h"
#include "Service.h"
#include "common/common/Log/Log.h"
#include "Common/Common/PathHelper/PathHelper.h"

////////////////////////////////////////////
//  Global flags from command line
////////////////////////////////////////////

// Service name
// Specified on the command lines
static char* serviceName = NULL;

// Install / Uninstall / Runmode
// RunService: we are running in service.
// RunConsole: we are running in console(for debug)
bool isInstall    = false;
bool isUninstall  = false;
bool isRunService = false;
bool isRunConsole = false;

///////////////////////////////////////////
//  Global variable for the service
///////////////////////////////////////////

// Status handle
SERVICE_STATUS_HANDLE hServiceStatus;

// Status structure
SERVICE_STATUS status;

///////////////////////////////////////////
// Forward function declaration
///////////////////////////////////////////
static void WINAPI ServiceMain(DWORD argc, LPTSTR *argv);
static void WINAPI ServiceHandler(DWORD opcode);
static BOOL WINAPI ConsoleControlHandler(DWORD opcode);
static void parseCommandLine(int argc, char* argv[]);
static void printUsage();
static int installService();
static int uninstallService();

int main(int argc, char* argv[])
{
#ifdef _DEBUG
	_CrtSetDbgFlag ( _CRTDBG_ALLOC_MEM_DF | _CRTDBG_LEAK_CHECK_DF );
#endif

	// Initialize Log
	string logfile = string(PathHelper::getApplicationRoot()) + "\\log\\MediaServer.log";
	Log::open(logfile.c_str());
	FVS_DEBUG1("%s start.\n", argv[0]);

	// parseCommand
	parseCommandLine(argc, argv);

	// Are we invoked to install service?
	if( isInstall )
	{
		FVS_DEBUG1("Install service %s\n", serviceName);
		if( installService() < 0)
		{
			FVS_DEBUG("Install service failed.");
			return -1;
		}
		FVS_DEBUG("Service installed.");
		return 0;
	}
	// Are we invoked to uninstall service?
    else if( isUninstall)
	{
		FVS_DEBUG1("Uninstall service %s\n", serviceName);
		if(uninstallService() < 0 )
		{
			FVS_DEBUG("Uninstall service failed.");
			return -1;
		}
		FVS_DEBUG("Service uninstalled.");
		return 0;
	}
	else if( isRunService )
	{
		// Output log
		FVS_DEBUG("Run in service mode.");

		//Register the ServiceMain function.
		SERVICE_TABLE_ENTRY st[] =
		{
			{"JavaService", ServiceMain},
			{NULL, NULL}
		};

		//Start the service dispatcher, which will call the ServiceMain function.
		if (StartServiceCtrlDispatcher(st) == 0)
		{
			FVS_DEBUG1("StartServiceCtrlDispatcher error, %d", GetLastError());
		}

		// Return
		return 0;
	}
	else //if( isRunConsole )
	{
		// Output log
		FVS_DEBUG("Run in console mode.");

		// Register console handler
		SetConsoleCtrlHandler(ConsoleControlHandler, TRUE);

		// Call start function directly
		return Service::getApp()->start();
	}/*
	else
	{
		FVS_DEBUG("Invalid command line.");
		FVS_DEBUG1("argc = %d\n", argc);
		for(int i=0; i<argc; i++)
		{
			FVS_DEBUG1("%s\t", argv[i]);
		}
		return -1;
	}*/
}

void WINAPI ServiceMain(DWORD dwArgc, LPTSTR* lpszArgv)
{
	//Set the service name.
	serviceName = lpszArgv[0];

	//Register the control request handler
	hServiceStatus = RegisterServiceCtrlHandler(serviceName, ServiceHandler);

	//Initialize the status structure.
	status.dwServiceType = SERVICE_WIN32_OWN_PROCESS;
	status.dwCurrentState = SERVICE_STOPPED;
	status.dwControlsAccepted = SERVICE_ACCEPT_STOP | SERVICE_ACCEPT_SHUTDOWN;
	status.dwWin32ExitCode = 0;
	status.dwServiceSpecificExitCode = 0;
	status.dwCheckPoint = 0;
	status.dwWaitHint = 0;

	//Mark the service as running.
	status.dwCurrentState = SERVICE_RUNNING;
	SetServiceStatus(hServiceStatus, &status);

	//Call the service start method.
	Service::getApp()->start();

	//Tell the service manager we are stopped.
  	status.dwCurrentState = SERVICE_STOPPED;
	status.dwWaitHint = 0;
	SetServiceStatus(hServiceStatus, &status);
}

BOOL WINAPI ConsoleControlHandler(DWORD opcode)
{
	// log
	FVS_DEBUG1("ConsoleCtrlHandler called with %d.", opcode);

    switch (opcode) 
    { 
		// Handle the CTRL+C signal. 
        case CTRL_C_EVENT: 
        case CTRL_BREAK_EVENT: 
        case CTRL_LOGOFF_EVENT: 
        case CTRL_SHUTDOWN_EVENT: 
        case CTRL_CLOSE_EVENT: 
			Service::getApp()->stop();
            return TRUE; 
 
        // Pass other signals to the next handler. 
        default: 
            return FALSE; 
    } 
}

void WINAPI ServiceHandler(DWORD opcode)
{
	// log
	FVS_DEBUG1("ServiceHandler called with %d.", opcode);

	switch (opcode)
	{
		case SERVICE_CONTROL_STOP:
		case SERVICE_CONTROL_SHUTDOWN:
			
			// Call service object stop method.
			Service::getApp()->stop();
			break;

		default:
			FVS_DEBUG1("ServiceHandler:Unknown control code received. %d\n", opcode);
			break;
	}
}


void parseCommandLine(int argc, char* argv[])
{
	if( argc == 3)
	{
		serviceName = argv[2];
		if( _stricmp(argv[1], "/install") == 0 )
		{
			isInstall = true;
		}
		else if( _stricmp(argv[1], "/uninstall") == 0 )
		{
			isUninstall = true;
		}
		return;
	}
	else if( argc == 2 )
	{
		if( _stricmp(argv[1], "/console") == 0)
		{
			isRunConsole = true;
		}
		else if(_stricmp(argv[1], "/service") == 0)
		{
			isRunService = true;
		}
	}
	else
	{
		printUsage();
	}
}

int installService()
{
	// Open the Service Control Manager
	SC_HANDLE hSCM = OpenSCManager(NULL, NULL, SC_MANAGER_CREATE_SERVICE);
	if (hSCM == NULL)
	{
		return -1;
	}

	// Get the executable file path
	char filePath[MAX_PATH];
	GetModuleFileName(NULL, filePath, sizeof(filePath));

	// Make up the service path, ie, quote the executable with
	// commandline arguments /service.
	char servicePath[MAX_PATH + 16];
	_snprintf(servicePath, MAX_PATH+16, "\"%s\" /service", filePath);

	// Create the service
	SC_HANDLE hService = CreateService(hSCM,
                                       serviceName,
                                       serviceName,
                                       SERVICE_ALL_ACCESS,
                                       SERVICE_WIN32_OWN_PROCESS,
                                       SERVICE_AUTO_START,
                                       SERVICE_ERROR_NORMAL,
                                       servicePath,
                                       NULL,
                                       NULL,
                                       NULL,
                                       NULL,
                                       NULL);

	//If the function failed, return an error.
	if (hService == NULL)
	{
		CloseServiceHandle(hSCM);
		return -1;
	}

	//Close the service handles.
	CloseServiceHandle(hService);
	CloseServiceHandle(hSCM);

	// Return
	return 0;
}


int uninstallService()
{
	// Open the Service Control Manager
	SC_HANDLE hSCM = OpenSCManager(NULL, NULL, SC_MANAGER_ALL_ACCESS);
	if (hSCM == NULL)
	{
		return -1;
	}

	SC_HANDLE hService = OpenService(hSCM, serviceName, DELETE);
	if (hService == NULL)
	{
		CloseServiceHandle(hSCM);
		return -1;
	}

	if (DeleteService(hService) == FALSE)
	{
		CloseServiceHandle(hService);
		CloseServiceHandle(hSCM);
		return -1;
	}

	CloseServiceHandle(hService);
	CloseServiceHandle(hSCM);
	return 0;
}

void printUsage()
{
	printf("%s usage:\n", __argv[0]);
	printf("    /service: Run this exeutable in Windows service mode.\n");
	printf("    /console: Run this exeutable in Windows console mode.\n");
	printf("    /install ServiceName: Install this executable as Windows Service under specified name.\n");
	printf("    /uninstall ServiceName: Uninstall the specified Windows Service.\n");
}
