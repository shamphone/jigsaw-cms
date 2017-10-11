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
#include "commonheaders.h"
#include "profilemanager.h"


// from the plugin loader, hate extern but the db frontend is pretty much tied
extern PLUGINLINK pluginCoreLink;
// contains the location of mirandaboot.ini
extern char mirandabootini[MAX_PATH];

// returns 1 if the profile path was returned, without trailing slash
int getProfilePath(char * buf, size_t cch)
{
	char profiledir[MAX_PATH];
	char exprofiledir[MAX_PATH];
	char * p = 0;
	// grab the base location now
	GetModuleFileNameA(NULL, buf, cch);
	p = strrchr(buf, '\\');
	if ( p != 0 ) *p=0;
	// change to this location, or "." wont expand properly
	_chdir(buf);
	GetPrivateProfileStringA("Database", "ProfileDir", ".", profiledir, SIZEOF(profiledir), mirandabootini);
	// get the string containing envars and maybe relative paths
	// get rid of the vars 
	ExpandEnvironmentStringsA(profiledir, exprofiledir, SIZEOF(exprofiledir));
	if ( _fullpath(profiledir, exprofiledir, SIZEOF(profiledir)) != 0 ) {
		/* XXX: really use CreateDirectory()? it only creates the last dir given a\b\c, SHCreateDirectory() 
		does what we want however thats 2000+ only  */
		DWORD dw = INVALID_FILE_ATTRIBUTES;
		CreateDirectoryA(profiledir, NULL);
		dw=GetFileAttributesA(profiledir);
		if ( dw != INVALID_FILE_ATTRIBUTES && dw&FILE_ATTRIBUTE_DIRECTORY )  {
			strncpy(buf, profiledir, cch);
			p = strrchr(buf, '\\');
			// if the char after '\' is null then change '\' to null
			if ( p != 0 && *(++p)==0 ) *(--p)=0;			
			return 1;
		}
	}
	// this never happens, usually C:\ is always returned	
	return 0;
}

// returns 1 if *.dat spec is matched
int isValidProfileName(char * name)
{
	char * p = strrchr(name, '.');	
	if ( p ) {
		p++;
		if ( lstrcmpiA( p, "dat" ) == 0 ) { 
			if ( p[3] == 0 ) return 1; 
		}
	}
	return 0;
}

// returns 1 if a single profile (full path) is found within the profile dir
static int getProfile1(char * szProfile, size_t cch, char * profiledir, BOOL * noProfiles)
{
	int rc = 1;
	char searchspec[MAX_PATH];
	WIN32_FIND_DATAA ffd;
	HANDLE hFind = INVALID_HANDLE_VALUE;
	unsigned int found=0;
	mir_snprintf(searchspec,SIZEOF(searchspec),"%s\\*.dat", profiledir);
	hFind = FindFirstFileA(searchspec, &ffd);
	if ( hFind != INVALID_HANDLE_VALUE ) 
	{
		// make sure the first hit is actually a *.dat file
		if ( !(ffd.dwFileAttributes&FILE_ATTRIBUTE_DIRECTORY) && isValidProfileName(ffd.cFileName) ) 
		{
			// copy the profile name early cos it might be the only one
			mir_snprintf(szProfile, cch, "%s\\%s", profiledir, ffd.cFileName);
			found++;
			// this might be the only dat but there might be a few wrong things returned before another *.dat
			while ( FindNextFileA(hFind,&ffd) ) {
				// found another *.dat, but valid?
				if ( !(ffd.dwFileAttributes&FILE_ATTRIBUTE_DIRECTORY) && isValidProfileName(ffd.cFileName) ) {
					rc=0;
					found++;
					break;
				} //if
			} // while
		} //if
		FindClose(hFind);
	}
	if ( found == 0 && noProfiles != 0 ) { 
		*noProfiles=TRUE;
		rc=0;
	}
	return rc;
}

// returns 1 if something that looks like a profile is there
static int getProfileCmdLineArgs(char * szProfile, size_t cch)
{
	char *szCmdLine=GetCommandLineA();
	char *szEndOfParam;
	char szThisParam[1024];
	int firstParam=1;

	while(szCmdLine[0]) {
		if(szCmdLine[0]=='"') {
			szEndOfParam=strchr(szCmdLine+1,'"');
			if(szEndOfParam==NULL) break;
			lstrcpynA(szThisParam,szCmdLine+1,min( SIZEOF(szThisParam),szEndOfParam-szCmdLine));
			szCmdLine=szEndOfParam+1;
		}
		else {
			szEndOfParam=szCmdLine+strcspn(szCmdLine," \t");
			lstrcpynA(szThisParam,szCmdLine,min( SIZEOF(szThisParam),szEndOfParam-szCmdLine+1));
			szCmdLine=szEndOfParam;
		}
		while(*szCmdLine && *szCmdLine<=' ') szCmdLine++;
		if(firstParam) {firstParam=0; continue;}   //first param is executable name
		if(szThisParam[0]=='/' || szThisParam[0]=='-') continue;  //no switches supported
		ExpandEnvironmentStringsA(szThisParam,szProfile,cch);
		return 1;
	}
	return 0;
}

// returns 1 if a valid filename (incl. dat) is found, includes fully qualified path
static int getProfileCmdLine(char * szProfile, size_t cch, char * profiledir)
{
	char buf[MAX_PATH];
	HANDLE hFile;
	int rc;
	if ( getProfileCmdLineArgs(buf, SIZEOF(buf)) ) {
		// have something that looks like a .dat, with or without .dat in the filename
		if ( !isValidProfileName(buf) ) mir_snprintf(buf, SIZEOF(buf)-5,"%s.dat",buf);
		// expand the relative to a full path , which might fail
		if ( _fullpath(szProfile, buf, cch) != 0 ) {
			hFile=CreateFileA(szProfile, GENERIC_READ, 0, NULL, OPEN_EXISTING, 0, NULL);
			rc=hFile != INVALID_HANDLE_VALUE;
			CloseHandle(hFile);
			return rc;
		}
		return 0;
	}
	return 0;
}

// returns 1 if the profile manager should be shown
static int showProfileManager(void)
{
	char Mgr[32];
	// is control pressed?
	if (GetAsyncKeyState(VK_CONTROL)&0x8000) return 1;
	// wanna show it?
	GetPrivateProfileStringA("Database", "ShowProfileMgr", "never", Mgr, SIZEOF(Mgr), mirandabootini);
	if ( _strcmpi(Mgr,"yes") == 0 ) return 1;
	return 0;
}

// returns 1 if a default profile should be selected instead of showing the manager.
static int getProfileAutoRun(char * szProfile, size_t cch, char * profiledir)
{
	char Mgr[32];
	char env_profile[MAX_PATH];
	char exp_profile[MAX_PATH];
	GetPrivateProfileStringA("Database", "ShowProfileMgr", "", Mgr, SIZEOF(Mgr), mirandabootini);
	if ( lstrcmpiA(Mgr,"never") ) return 0;		
	GetPrivateProfileStringA("Database", "DefaultProfile", "", env_profile, SIZEOF(env_profile), mirandabootini);
	if ( lstrlenA(env_profile) == 0 ) return 0;
	ExpandEnvironmentStringsA(env_profile, exp_profile, SIZEOF(exp_profile));
	mir_snprintf(szProfile, cch, "%s\\%s.dat", profiledir, exp_profile);
	return 1;
}



// returns 1 if a profile was selected
static int getProfile(char * szProfile, size_t cch)
{
	char profiledir[MAX_PATH];
	PROFILEMANAGERDATA pd;
	ZeroMemory(&pd,sizeof(pd));
	getProfilePath(profiledir,SIZEOF(profiledir));
	if ( getProfileCmdLine(szProfile, cch, profiledir) ) return 1;
	if ( getProfileAutoRun(szProfile, cch, profiledir) ) return 1;
	if ( !showProfileManager() && getProfile1(szProfile, cch, profiledir, &pd.noProfiles) ) return 1;
	else {		
		pd.szProfile=szProfile;
		pd.szProfileDir=profiledir;
		return getProfileManager(&pd);
	}
}

// called by the UI, return 1 on success, use link to create profile, set error if any
int makeDatabase(char * profile, DATABASELINK * link, HWND hwndDlg)
{
	char buf[256];
	int err=0;	
	// check if the file already exists
	HANDLE hFile=CreateFileA(profile, GENERIC_READ|GENERIC_WRITE, 0, NULL, OPEN_EXISTING, 0, NULL);
	char * file = strrchr(profile,'\\');
	file++;
	if ( hFile != INVALID_HANDLE_VALUE ) {		
		CloseHandle(hFile);		
		mir_snprintf(buf, SIZEOF(buf), Translate("The profile '%s' already exists. Do you want to move it to the "
			"Recycle Bin? \n\nWARNING: The profile will be deleted if Recycle Bin is disabled.\nWARNING: A profile may contain confidential information and should be properly deleted."),file);
		// file already exists!
		if ( MessageBoxA(hwndDlg, buf, Translate("The profile already exists"), MB_ICONQUESTION|MB_YESNO|MB_DEFBUTTON2) != IDYES ) return 0;
		// move the file
		{		
			char szName[MAX_PATH]; // SHFileOperation needs a "double null" 
			SHFILEOPSTRUCTA sf;
			ZeroMemory(&sf,sizeof(sf));
			sf.wFunc=FO_DELETE;
			sf.pFrom=szName;
			sf.fFlags=FOF_NOCONFIRMATION|FOF_NOERRORUI|FOF_SILENT;
			mir_snprintf(szName, SIZEOF(szName),"%s\0",profile);
			if ( SHFileOperationA(&sf) != 0 ) {
				mir_snprintf(buf, SIZEOF(buf),Translate("Couldn't move '%s' to the Recycle Bin, Please select another profile name."),file);
				MessageBoxA(0,buf,Translate("Problem moving profile"),MB_ICONINFORMATION|MB_OK);
				return 0;
			}
		}
		// now the file should be gone!
	}
	// ask the database to create the profile
	if ( link->makeDatabase(profile,&err) ) { 
		mir_snprintf(buf, SIZEOF(buf),Translate("Unable to create the profile '%s', the error was %x"),file, err);
		MessageBoxA(hwndDlg,buf,Translate("Problem creating profile"),MB_ICONERROR|MB_OK);
		return 0;
	}
	// the profile has been created! woot
	return 1;
}

// enumerate all plugins that had valid DatabasePluginInfo()
static int FindDbPluginForProfile(char * pluginname, DATABASELINK * dblink, LPARAM lParam)
{
	char * szProfile = (char *) lParam;
	if ( dblink && dblink->cbSize == sizeof(DATABASELINK) ) {
		int err=0;
		int rc=0;
		// liked the profile?
		rc=dblink->grokHeader(szProfile,&err);
		if ( rc == 0 ) { 			
			// added APIs?
			if ( dblink->Load(szProfile, &pluginCoreLink) == 0 ) return DBPE_DONE;
			return DBPE_HALT;
		} else {
			switch ( err ) {				 
				case EGROKPRF_CANTREAD:
				case EGROKPRF_UNKHEADER:
				{
					// just not supported.
					return DBPE_CONT;
				}
				case EGROKPRF_VERNEWER:
				case EGROKPRF_DAMAGED:
				{
					break;
				}
			}
			return DBPE_HALT;
		} //if
	}
	return DBPE_CONT;
}

typedef struct {
	char * profile;
	UINT msg;
	ATOM aPath;
	int found;
} ENUMMIRANDAWINDOW;

static BOOL CALLBACK EnumMirandaWindows(HWND hwnd, LPARAM lParam)
{
	TCHAR classname[256];
	ENUMMIRANDAWINDOW * x = (ENUMMIRANDAWINDOW *)lParam;
	DWORD res=0;
	if ( GetClassName(hwnd,classname,SIZEOF(classname)) && lstrcmp( _T("Miranda"),classname)==0 ) {		
		if ( SendMessageTimeout(hwnd, x->msg, (WPARAM)x->aPath, 0, SMTO_ABORTIFHUNG, 100, &res) && res ) {
			x->found++;
			return FALSE;
		}
	}
	return TRUE;
}

static int FindMirandaForProfile(char * szProfile)
{
	ENUMMIRANDAWINDOW x={0};
	x.profile=szProfile;
	x.msg=RegisterWindowMessage( _T( "Miranda::ProcessProfile" ));
	x.aPath=GlobalAddAtomA(szProfile);
	EnumWindows(EnumMirandaWindows, (LPARAM)&x);
	GlobalDeleteAtom(x.aPath);
	return x.found;
}

int LoadDatabaseModule(void)
{
	int iReturn = 0;
	char szProfile[MAX_PATH];
	szProfile[0]=0;

	// load the older basic services of the db
	InitTime();

	// find out which profile to load
	if ( getProfile(szProfile, SIZEOF(szProfile)) )
	{
		int rc;
		PLUGIN_DB_ENUM dbe;

		dbe.cbSize=sizeof(PLUGIN_DB_ENUM);
		dbe.pfnEnumCallback=( int(*) (char*,void*,LPARAM) )FindDbPluginForProfile;
		dbe.lParam=(LPARAM)szProfile;

		// find a driver to support the given profile
		rc=CallService(MS_PLUGINS_ENUMDBPLUGINS, 0, (LPARAM)&dbe);
		switch ( rc ) {
			case -1: {
				// no plugins at all
				char buf[256];
				char * p = strrchr(szProfile,'\\');
				mir_snprintf(buf,SIZEOF(buf),Translate("Miranda is unable to open '%s' because you do not have any profile plugins installed.\nYou need to install dbx_3x.dll or equivalent."), p ? ++p : szProfile );
				MessageBoxA(0,buf,Translate("No profile support installed!"),MB_OK | MB_ICONERROR);
				break;
			}
			case 1: {
				// if there were drivers but they all failed cos the file is locked, try and find the miranda which locked it
				HANDLE hFile;
				hFile=CreateFileA(szProfile,GENERIC_READ|GENERIC_WRITE,0,NULL,OPEN_EXISTING,0,NULL);
				if ( hFile == INVALID_HANDLE_VALUE ) {
					if ( !FindMirandaForProfile(szProfile) ) {
						// file is locked, tried to find miranda window, but that failed too.
					}
				} else {
					// file isn't locked, just no driver could open it.
					char buf[256];
					char * p = strrchr(szProfile,'\\');
					mir_snprintf(buf,SIZEOF(buf),Translate("Miranda was unable to open '%s', its in an unknown format.\nThis profile might also be damaged, please run DB-tool which should be installed."), p ? ++p : szProfile);
					MessageBoxA(0,buf,Translate("Miranda can't understand that profile"),MB_OK | MB_ICONERROR);
					CloseHandle(hFile);					
				}
				break;
			}
		}
		iReturn = (rc != 0);
	}
	else
	{
		iReturn = 1;
	}

	return iReturn;
}

