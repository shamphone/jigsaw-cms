/*

Miranda IM: the free IM client for Microsoft* Windows*

Copyright 2000-2007 Miranda ICQ/IM project, 
all portions of this codebase are copyrighted to the people 
listed in contributors.txt.

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
*/

#ifndef M_NEWPLUGINAPI_H__
#define M_NEWPLUGINAPI_H__

#include "m_plugins.h"

#define PLUGIN_MAKE_VERSION(a,b,c,d)   (((((DWORD)(a))&0xFF)<<24)|((((DWORD)(b))&0xFF)<<16)|((((DWORD)(c))&0xFF)<<8)|(((DWORD)(d))&0xFF))
#define MAXMODULELABELLENGTH 64

#if defined( _UNICODE )
	#define UNICODE_AWARE 1
#else
	#define UNICODE_AWARE 0
#endif

typedef struct {
	int cbSize;
	char *shortName;
	DWORD version;
	char *description;
	char *author;
	char *authorEmail;
	char *copyright;
	char *homepage;
	BYTE isTransient;	   //leave this as 0 for now
	int replacesDefaultModule;		   //one of the DEFMOD_ constants in m_plugins.h or zero
	         //if non-zero, this will supress the loading of the specified built-in module
			 //with the implication that this plugin provides back-end-compatible features
} PLUGININFO;

#ifndef MODULES_H_
typedef int (*MIRANDAHOOK)(WPARAM,LPARAM);
typedef int (*MIRANDASERVICE)(WPARAM,LPARAM);
#define CALLSERVICE_NOTFOUND      ((int)0x80000000)
#endif

//see modules.h for what all this stuff is
typedef struct {
	HANDLE (*CreateHookableEvent)(const char *);
	int (*DestroyHookableEvent)(HANDLE);
	int (*NotifyEventHooks)(HANDLE,WPARAM,LPARAM);
	HANDLE (*HookEvent)(const char *,MIRANDAHOOK);
	HANDLE (*HookEventMessage)(const char *,HWND,UINT);
	int (*UnhookEvent)(HANDLE);
	HANDLE (*CreateServiceFunction)(const char *,MIRANDASERVICE);
	HANDLE (*CreateTransientServiceFunction)(const char *,MIRANDASERVICE);
	int (*DestroyServiceFunction)(HANDLE);
	int (*CallService)(const char *,WPARAM,LPARAM);
	int (*ServiceExists)(const char *);		  //v0.1.0.1+
	int (*CallServiceSync)(const char *,WPARAM,LPARAM);		//v0.3.3+
	int (*CallFunctionAsync) (void (__stdcall *)(void *), void *);	//v0.3.4+
	int (*SetHookDefaultForHookableEvent) (HANDLE, MIRANDAHOOK); // v0.3.4 (2004/09/15)
} PLUGINLINK;

#ifndef MODULES_H_
#ifndef NODEFINEDLINKFUNCTIONS
//relies on a global variable 'pluginLink' in the plugins
extern PLUGINLINK *pluginLink;
#define CreateHookableEvent(a)               pluginLink->CreateHookableEvent(a)
#define DestroyHookableEvent(a)              pluginLink->DestroyHookableEvent(a)
#define NotifyEventHooks(a,b,c)              pluginLink->NotifyEventHooks(a,b,c)
#define HookEventMessage(a,b,c)              pluginLink->HookEventMessage(a,b,c)
#define HookEvent(a,b)                       pluginLink->HookEvent(a,b)
#define UnhookEvent(a)                       pluginLink->UnhookEvent(a)
#define CreateServiceFunction(a,b)           pluginLink->CreateServiceFunction(a,b)
#define CreateTransientServiceFunction(a,b)  pluginLink->CreateTransientServiceFunction(a,b)
#define DestroyServiceFunction(a)            pluginLink->DestroyServiceFunction(a)
#define CallService(a,b,c)                   pluginLink->CallService(a,b,c)
#define ServiceExists(a)                     pluginLink->ServiceExists(a)
#define CallServiceSync(a,b,c)               pluginLink->CallServiceSync(a,b,c)
#define CallFunctionAsync(a,b)				 pluginLink->CallFunctionAsync(a,b)
#define SetHookDefaultForHookableEvent(a,b)  pluginLink->SetHookDefaultForHookableEvent(a,b)
#endif
#endif

/*
 Database plugin stuff
*/

// grokHeader() error codes
#define EGROKPRF_NOERROR	0
#define EGROKPRF_CANTREAD	1	// can't open the profile for reading
#define EGROKPRF_UNKHEADER  2	// header not supported, not a supported profile
#define EGROKPRF_VERNEWER   3	// header correct, version in profile newer than reader/writer
#define EGROKPRF_DAMAGED	4	// header/version fine, other internal data missing, damaged.

// makeDatabase() error codes
#define EMKPRF_CREATEFAILED 1   // for some reason CreateFile() didnt like something

typedef struct {
	int cbSize;
	
	/*
	returns what the driver can do given the flag
	*/
	int (*getCapability) ( int flag );

	/* 
		buf: pointer to a string buffer
		cch: length of buffer
		shortName: if true, the driver should return a short but descriptive name, e.g. "3.xx profile"
		Affect: The database plugin must return a "friendly name" into buf and not exceed cch bytes,
			e.g. "Database driver for 3.xx profiles"
		Returns: 0 on success, non zero on failure
	*/
	int (*getFriendlyName) ( char * buf, size_t cch, int shortName );

	/*
		profile: pointer to a string which contains full path + name
		Affect: The database plugin should create the profile, the filepath will not exist at
			the time of this call, profile will be C:\..\<name>.dat
		Note: Do not prompt the user in anyway about this operation.
		Note: Do not initialise internal data structures at this point!
		Returns: 0 on success, non zero on failure - error contains extended error information, see EMKPRF_*
	*/
	int (*makeDatabase) ( char * profile, int * error );

	/*
		profile: [in] a null terminated string to file path of selected profile
		error: [in/out] pointer to an int to set with error if any
		Affect: Ask the database plugin if it supports the given profile, if it does it will
			return 0, if it doesnt return 1, with the error set in error -- EGROKPRF_* can be valid error
			condition, most common error would be [EGROKPRF_UNKHEADER] 
		Note: Just because 1 is returned, doesnt mean the profile is not supported, the profile might be damaged
			etc.
		Returns: 0 on success, non zero on failure
	*/
	int (*grokHeader) ( char * profile, int * error );

	/*
	Affect: Tell the database to create all services/hooks that a 3.xx legecy database might support into link,
		which is a PLUGINLINK structure
	Returns: 0 on success, nonzero on failure
	*/
	int (*Load) ( char * profile, void * link );

	/*
	Affect: The database plugin should shutdown, unloading things from the core and freeing internal structures
	Returns: 0 on success, nonzero on failure
	Note: Unload() might be called even if Load() was never called, wasLoaded is set to 1 if Load() was ever called.
	*/
	int (*Unload) ( int wasLoaded );

} DATABASELINK;

#endif // M_NEWPLUGINAPI_H__

