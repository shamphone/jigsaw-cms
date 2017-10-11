#include "miranda_dll.h"
#include "commonheaders.h"

HINSTANCE dll_instance = 0;

BOOL WINAPI DllMain(HINSTANCE hinstDLL,DWORD fdwReason,LPVOID lpvReserved)
{
	switch (fdwReason) 
	{
	case DLL_PROCESS_ATTACH:
		dll_instance = hinstDLL;
		return TRUE;
	case DLL_PROCESS_DETACH:
		return TRUE;
	case DLL_THREAD_DETACH:
		return TRUE;
	default:
		return TRUE;
	}
}

int aaa()
{
	return 0;
}

int bbb()
{
	return 0;
}
