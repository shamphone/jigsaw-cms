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

static char szMirandaPath[MAX_PATH];
static char szMirandaPathLower[MAX_PATH];

static int pathIsAbsolute(char *path)
{
    if (!path||!strlen(path)>2) return 0;
    if ((path[1]==':'&&path[2]=='\\')||(path[0]=='\\'&&path[1]=='\\')) return 1;
    return 0;
}

static int pathToRelative(WPARAM wParam, LPARAM lParam)
{
    char *pSrc = (char*)wParam;
    char *pOut = (char*)lParam;
    if (!pSrc||!strlen(pSrc)||strlen(pSrc)>MAX_PATH) return 0;
    if (!pathIsAbsolute(pSrc)) {
        mir_snprintf(pOut, MAX_PATH, "%s", pSrc);
        return strlen(pOut);
    }
    else {
        char szTmp[MAX_PATH];

        mir_snprintf(szTmp, SIZEOF(szTmp), "%s", pSrc);
        _strlwr(szTmp);
        if (strstr(szTmp, szMirandaPathLower)) {
            mir_snprintf(pOut, MAX_PATH, "%s", pSrc+strlen(szMirandaPathLower));
            return strlen(pOut);
        }
        else {
            mir_snprintf(pOut, MAX_PATH, "%s", pSrc);
            return strlen(pOut);
        }
    }
}

static int pathToAbsolute(WPARAM wParam, LPARAM lParam) {
    char *pSrc = (char*)wParam;
    char *pOut = (char*)lParam;
    if (!pSrc||!strlen(pSrc)||strlen(pSrc)>MAX_PATH) return 0;
    if (pathIsAbsolute(pSrc)||(!isalnum(pSrc[0]) && pSrc[0]!='\\' )) {
        mir_snprintf(pOut, MAX_PATH, "%s", pSrc);
        return strlen(pOut);
    }
    else if (pSrc[0]!='\\') {
        mir_snprintf(pOut, MAX_PATH, "%s%s", szMirandaPath, pSrc);
        return strlen(pOut);
    }
	else {
		mir_snprintf(pOut, MAX_PATH, "%s%s", szMirandaPath, pSrc+1);
		return strlen(pOut);
	}
}

#ifdef _UNICODE
static TCHAR szMirandaPathW[MAX_PATH];
static TCHAR szMirandaPathWLower[MAX_PATH];

static int pathIsAbsoluteW(TCHAR *path)
{
    if (!path||!lstrlen(path)>2) return 0;
    if ((path[1]==':'&&path[2]=='\\')||(path[0]=='\\'&&path[1]=='\\')) return 1;
    return 0;
}

static int pathToRelativeW(WPARAM wParam, LPARAM lParam)
{
    TCHAR *pSrc = (TCHAR*)wParam;
    TCHAR *pOut = (TCHAR*)lParam;
    if (!pSrc||!lstrlen(pSrc)||lstrlen(pSrc)>MAX_PATH) return 0;
    if (!pathIsAbsoluteW(pSrc)) {
        mir_sntprintf(pOut, MAX_PATH, _T("%s"), pSrc);
        return lstrlen(pOut);
    }
    else {
        TCHAR szTmp[MAX_PATH];

        mir_sntprintf(szTmp, SIZEOF(szTmp), _T("%s"), pSrc);
        _tcslwr(szTmp);
        if (_tcsstr(szTmp, szMirandaPathWLower)) {
            mir_sntprintf(pOut, MAX_PATH, _T("%s"), pSrc+lstrlen(szMirandaPathWLower));
            return lstrlen(pOut);
        }
        else {
            mir_sntprintf(pOut, MAX_PATH, _T("%s"), pSrc);
            return lstrlen(pOut);
        }
    }
}

static int pathToAbsoluteW(WPARAM wParam, LPARAM lParam) {
    TCHAR *pSrc = (TCHAR*)wParam;
    TCHAR *pOut = (TCHAR*)lParam;
    if (!pSrc||!lstrlen(pSrc)||lstrlen(pSrc)>MAX_PATH) return 0;
    if (pathIsAbsoluteW(pSrc)||(!isalnum(pSrc[0]) && pSrc[0]!='\\' )) {
        mir_sntprintf(pOut, MAX_PATH, _T("%s"), pSrc);
        return lstrlen(pOut);
    }
    else if (pSrc[0]!='\\') {
        mir_sntprintf(pOut, MAX_PATH, _T("%s%s"), szMirandaPathW, pSrc);
        return lstrlen(pOut);
    }
	else {
		mir_sntprintf(pOut, MAX_PATH, _T("%s%s"), szMirandaPathW, pSrc+1);
		return lstrlen(pOut);
	}
}

int InitPathUtilsW(void)
{
	TCHAR *p = 0;
	GetModuleFileName(GetModuleHandle(NULL), szMirandaPathW, SIZEOF(szMirandaPathW));
	p=_tcsrchr(szMirandaPathW,'\\');
	if (p&&p+1) *(p+1)=0;
    mir_sntprintf(szMirandaPathWLower, MAX_PATH, _T("%s"), szMirandaPathW);
    _tcslwr(szMirandaPathWLower);
    CreateServiceFunction(MS_UTILS_PATHTORELATIVEW, pathToRelativeW);
    CreateServiceFunction(MS_UTILS_PATHTOABSOLUTEW, pathToAbsoluteW);
    return 0;
}
#endif

int InitPathUtils(void)
{
	char *p = 0;
	GetModuleFileNameA(GetModuleHandle(NULL), szMirandaPath, SIZEOF(szMirandaPath));
	p=strrchr(szMirandaPath,'\\');
	if (p&&p+1) *(p+1)=0;
    mir_snprintf(szMirandaPathLower, MAX_PATH, "%s", szMirandaPath);
    _strlwr(szMirandaPathLower);
    CreateServiceFunction(MS_UTILS_PATHTORELATIVE, pathToRelative);
    CreateServiceFunction(MS_UTILS_PATHTOABSOLUTE, pathToAbsolute);
#ifdef _UNICODE
    return InitPathUtilsW();
#else
    return 0;
#endif
}
