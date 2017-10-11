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

#define UN_MOD              "UpdateNotify"
#define UN_ENABLE           "UpdateNotifyEnable"
#define UN_ENABLE_DEF       1
#define UN_LASTCHECK        "UpdateNotifyLastCheck"
#define UN_SERVERPERIOD     "UpdateNotifyPingDelayPeriod"
#define UN_CURRENTVERSION   "UpdateNotifyCurrentVersion"
#define UN_CUSTOMURL        "UpdateNotifyCustomURL"
#define UN_URL              "http://update.miranda-im.org/update.php"
#define UN_MINCHECKTIME     60*60 /* Check no more than once an hour */
#define UN_DEFAULTCHECKTIME 60*24*60 /* Default to check once every 24 hours */
#define UN_FIRSTCHECK       15 /* First check 15 seconds after startup */

typedef struct {
    char version[64];
    char downloadUrl[256];
} UpdateNotifyData;

static HANDLE hNetlibUser = 0, hHookModules, hHookPreShutdown;
static UINT updateTimerId;
static DWORD dwUpdateThreadID = 0;
static HWND hwndUpdateDlg = 0;

static int UpdateNotifyOptInit(WPARAM wParam, LPARAM lParam);
static VOID CALLBACK UpdateNotifyTimerCheck(HWND hwnd, UINT uMsg, UINT_PTR idEvent, DWORD dwTime);
static void UpdateNotifyPerform(void *p);
static BOOL CALLBACK UpdateNotifyProc(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);
static BOOL CALLBACK UpdateNotifyOptsProc(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam);

static int UnloadUpdateNotifyModule(WPARAM wParam, LPARAM lParam) {
    UnhookEvent(hHookModules);
    UnhookEvent(hHookPreShutdown);
	return 0;
}

static int UpdateNotifyModulesLoaded(WPARAM wParam, LPARAM lParam) {
	NETLIBUSER nlu;
    
    ZeroMemory(&nlu, sizeof(nlu));
	nlu.cbSize = sizeof(nlu);
	nlu.flags =  NUF_OUTGOING|NUF_HTTPCONNS;
	nlu.szSettingsModule = UN_MOD;
	nlu.szDescriptiveName = Translate("Update notification");
    hNetlibUser = (HANDLE)CallService(MS_NETLIB_REGISTERUSER, 0, (LPARAM)&nlu);
    return 0;
}

static int UpdateNotifyPreShutdown(WPARAM wParam, LPARAM lParam) {
    if (IsWindow(hwndUpdateDlg)) {
        SendMessage(hwndUpdateDlg, WM_COMMAND, MAKELONG(IDOK, 0), 0);
    }
    return 0;
}

int LoadUpdateNotifyModule(void) {
    hHookModules = HookEvent(ME_SYSTEM_MODULESLOADED, UpdateNotifyModulesLoaded);
    hHookPreShutdown =   HookEvent(ME_SYSTEM_PRESHUTDOWN, UpdateNotifyPreShutdown);
    HookEvent(ME_SYSTEM_SHUTDOWN, UnloadUpdateNotifyModule);
    HookEvent(ME_OPT_INITIALISE, UpdateNotifyOptInit);
    updateTimerId = SetTimer(NULL, 0, 1000*UN_FIRSTCHECK, UpdateNotifyTimerCheck);
    return 0;
}

static int UpdateNotifyOptInit(WPARAM wParam, LPARAM lParam) {
    OPTIONSDIALOGPAGE odp;
    
    ZeroMemory(&odp, sizeof(odp));
    odp.cbSize = sizeof(odp);
    odp.position = 100000000;
    odp.hInstance = GetModuleHandle(NULL);
    odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_UPDATENOTIFY);
    odp.pszGroup = "Events";
    odp.pszTitle = "Update Notify";
    odp.pfnDlgProc = UpdateNotifyOptsProc;
    odp.flags = ODPF_BOLDGROUPS;
    CallService(MS_OPT_ADDPAGE, wParam, (LPARAM)&odp);
    return 0;
}

static VOID CALLBACK UpdateNotifyTimerCheck(HWND hwnd, UINT uMsg, UINT_PTR idEvent, DWORD dwTime) {
    KillTimer(NULL, updateTimerId);
    if (!DBGetContactSettingByte(NULL, UN_MOD, UN_ENABLE, UN_ENABLE_DEF)) 
        return;
    if (dwUpdateThreadID!=0) {
        Netlib_Logf(hNetlibUser, "Update notification already running, ignoring attempt");
        return;
    }
    {
        DWORD lastCheck = 0;
        
        if (!hNetlibUser)
            return;
        lastCheck = DBGetContactSettingDword(NULL, UN_MOD, UN_LASTCHECK, 0);
        if (!lastCheck) { // never checked for update before
            Netlib_Logf(hNetlibUser, "Running update notify check for the first time.");
            dwUpdateThreadID = mir_forkthread(UpdateNotifyPerform, 0);
        }
        else {
            DWORD dwNow = time(NULL), dwTimeDiff;
            DWORD dwServerPing = DBGetContactSettingDword(NULL, UN_MOD, UN_SERVERPERIOD, UN_DEFAULTCHECKTIME);
            
            if (lastCheck>dwNow) {
                 // time for last check is after the current date so reset lastcheck and quit
                 DBWriteContactSettingDword(NULL, UN_MOD, UN_LASTCHECK, dwNow);
                 return;
            }
            dwTimeDiff = dwNow - lastCheck;
            if (dwServerPing<UN_MINCHECKTIME)
                dwServerPing = UN_MINCHECKTIME;
            if (dwTimeDiff>dwServerPing)
                dwUpdateThreadID = mir_forkthread(UpdateNotifyPerform, 0);
        }
        updateTimerId = SetTimer(NULL, 0, 1000*UN_MINCHECKTIME, UpdateNotifyTimerCheck);
    }
}

static void UpdateNotifyPerform(void *manual) {
    NETLIBHTTPREQUEST req;
    NETLIBHTTPREQUEST *resp;
    NETLIBHTTPHEADER headers[1];
    DWORD timeNow = time(NULL);    
    DWORD dwVersion;
    char szVersion[32], szUrl[256], szVersionText[128];
    int isUnicode, isAlpha;
    DBVARIANT dbv;
    
    DBWriteContactSettingDword(NULL, UN_MOD, UN_LASTCHECK, timeNow);
    CallService(MS_SYSTEM_GETVERSIONTEXT, sizeof(szVersionText), (LPARAM)szVersionText);
    isUnicode = strstr(szVersionText, "Unicode") != NULL ? 1 : 0;
    isAlpha = strstr(szVersionText, "alpha") != NULL ? 1 : 0;
    dwVersion = CallService(MS_SYSTEM_GETVERSION, 0, 0);
    mir_snprintf(szVersion, sizeof(szVersion), "%d.%d.%d.%d", 
            HIBYTE(HIWORD(dwVersion)), LOBYTE(HIWORD(dwVersion)), 
            HIBYTE(LOWORD(dwVersion)), LOBYTE(LOWORD(dwVersion)));
	if (!DBGetContactSetting(NULL, UN_MOD, UN_CUSTOMURL, &dbv)) {
        mir_snprintf(szUrl, sizeof(szUrl), "%s?version=%s&unicode=%d&alpha=%d", dbv.pszVal?dbv.pszVal:UN_URL, szVersion, isUnicode, isAlpha);
		DBFreeVariant(&dbv);
	}
    else {
        mir_snprintf(szUrl, sizeof(szUrl), "%s?version=%s&unicode=%d&alpha=%d", UN_URL, szVersion, isUnicode, isAlpha);
    }
    ZeroMemory(&req, sizeof(req));
	req.cbSize = sizeof(req);
	req.requestType = REQUEST_GET;
	req.szUrl = szUrl;
	req.flags = 0;
    headers[0].szName = "User-Agent";
    headers[0].szValue = "MirandaUpdate/0.1";
    req.headersCount = 1;
    req.headers = headers;
    resp = (NETLIBHTTPREQUEST *)CallService(MS_NETLIB_HTTPTRANSACTION, (WPARAM)hNetlibUser, (LPARAM)&req);
    if (resp) {
        if (resp->resultCode==200) {
            int i;
            int resUpdate = 0;
            UpdateNotifyData und;
            
            ZeroMemory(&und, sizeof(und));
            und.version[0] = 0;
            und.downloadUrl[0] = 0;
            for (i=0; i<resp->headersCount; i++ ) {
                if (strcmp(resp->headers[i].szName, "X-Miranda-Update")==0) {
                    resUpdate = resp->headers[i].szValue&&strcmp(resp->headers[i].szValue,"true")==0?1:0;
                    if (resUpdate) {
                        Netlib_Logf(hNetlibUser, "A new update is available for %s", szVersion);
                    }
                }
                else if (strcmp(resp->headers[i].szName, "X-Miranda-Version")==0&&resp->headers[i].szValue) {
                    Netlib_Logf(hNetlibUser, "New version found (%s)", resp->headers[i].szValue);
                    mir_snprintf(und.version, sizeof(und.version), "%s", resp->headers[i].szValue);
                }
                else if (strcmp(resp->headers[i].szName, "X-Miranda-Download-URL")==0&&resp->headers[i].szValue) {
                    Netlib_Logf(hNetlibUser, "Download url found (%s)", resp->headers[i].szValue);
                    mir_snprintf(und.downloadUrl, sizeof(und.downloadUrl), "%s", resp->headers[i].szValue);
                }
                else if (strcmp(resp->headers[i].szName, "X-Miranda-UN-New-URL")==0&&resp->headers[i].szValue) {
                    Netlib_Logf(hNetlibUser, "Update URL has changed (%s)", resp->headers[i].szValue);
                    DBWriteContactSettingString(NULL, UN_MOD, UN_CUSTOMURL, resp->headers[i].szValue);
                }
                else if (strcmp(resp->headers[i].szName, "X-Miranda-Ping-Period")==0) {
                    DWORD dwPingPeriod = atoi(resp->headers[i].szValue);
                    
                    if (dwPingPeriod>UN_MINCHECKTIME) {
                        Netlib_Logf(hNetlibUser, "Next update in %d seconds", dwPingPeriod);
                        DBWriteContactSettingDword(NULL, UN_MOD, UN_SERVERPERIOD, dwPingPeriod);
                    }
                }
            }
            if (resUpdate&&und.version[0]&&und.downloadUrl[0]) {
                int notify = 1;
                
                if (!DBGetContactSetting(NULL, UN_MOD, UN_CURRENTVERSION, &dbv)) {
                    if (!strcmp(dbv.pszVal, und.version)) // already notified of this version, don't show dialog
                        notify = 0;
                    DBFreeVariant(&dbv);
                }
                if (notify) {
                    DBWriteContactSettingString(NULL, UN_MOD, UN_CURRENTVERSION, und.version);
                    DialogBoxParam(GetModuleHandle(NULL), MAKEINTRESOURCE(IDD_UPDATE_NOTIFY), 0, UpdateNotifyProc,(LPARAM)&und);
                    hwndUpdateDlg = 0;
                }
            }
        }
        CallService(MS_NETLIB_FREEHTTPREQUESTSTRUCT, 0, (LPARAM)resp);
    }
    else {
        Netlib_Logf(hNetlibUser, "No response from HTTP request");
    }
    dwUpdateThreadID = 0;
}

static BOOL CALLBACK UpdateNotifyProc(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch ( msg ) {
	case WM_INITDIALOG:
		TranslateDialogDefault(hwndDlg);
		SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_MIRANDA)));
		{
			UpdateNotifyData *und = (UpdateNotifyData*)lParam;
			char szTmp[128], *p;

			hwndUpdateDlg = hwndDlg;
			mir_snprintf(szTmp, sizeof(szTmp), Translate("Miranda IM %s Now Available"), und->version);
			SetWindowTextA(hwndDlg, szTmp);
			CallService(MS_SYSTEM_GETVERSIONTEXT, sizeof(szTmp), (LPARAM)szTmp);
			p = strstr(szTmp, " Unicode");
			if (p)
				*p = '\0';   
			SetDlgItemTextA(hwndDlg, IDC_CURRENTVERSION, szTmp);
			mir_snprintf(szTmp, sizeof(szTmp), "%s", und->version);
			SetDlgItemTextA(hwndDlg, IDC_VERSION, szTmp);
			{
				HFONT hFont;
				LOGFONT lf;

				hFont = (HFONT)SendDlgItemMessage(hwndDlg, IDC_VERSION, WM_GETFONT, 0, 0);
				GetObject(hFont, sizeof(lf), &lf);
				lf.lfWeight = FW_BOLD;
				hFont = CreateFontIndirect(&lf);
				SendDlgItemMessage(hwndDlg, IDC_VERSION, WM_SETFONT, (WPARAM)hFont, 0);
				hFont = (HFONT)SendDlgItemMessage(hwndDlg, IDC_NEWVERSIONLABEL, WM_GETFONT, 0, 0);
				GetObject(hFont, sizeof(lf), &lf);
				lf.lfWeight = FW_BOLD;
				hFont = CreateFontIndirect(&lf);
				SendDlgItemMessage(hwndDlg, IDC_NEWVERSIONLABEL, WM_SETFONT, (WPARAM)hFont, 0);  
			}
			SetWindowLong(hwndDlg, GWL_USERDATA, lParam);
		}
		break;

	case WM_COMMAND:
		switch (LOWORD(wParam)) {
		case IDC_DOWNLOAD:
			{
				UpdateNotifyData *und = (UpdateNotifyData*)GetWindowLong(hwndDlg, GWL_USERDATA);
				if (und&&und->downloadUrl) {
					CallService(MS_UTILS_OPENURL, 1, (LPARAM)und->downloadUrl);
					DestroyWindow(hwndDlg);
				}
				break;
			}
		case IDOK:
		case IDCANCEL:
			DestroyWindow(hwndDlg);
			return TRUE;
		}
		break;
	}
	return FALSE;
}

static BOOL CALLBACK UpdateNotifyOptsProc(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg) {
	case WM_INITDIALOG:
		TranslateDialogDefault(hwndDlg);
		CheckDlgButton(hwndDlg, IDC_ENABLEUPDATES, DBGetContactSettingByte(NULL, UN_MOD, UN_ENABLE, UN_ENABLE_DEF) ? BST_CHECKED : BST_UNCHECKED);
		return TRUE;

	case WM_COMMAND:
		switch (LOWORD(wParam)) {
		case IDC_ENABLEUPDATES:
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		}
		break;

	case WM_NOTIFY:
		{
			NMHDR *hdr = (NMHDR *)lParam;
			if (hdr&&hdr->code==PSN_APPLY) {
				DBWriteContactSettingByte(NULL, UN_MOD, UN_ENABLE, (BYTE)(IsDlgButtonChecked(hwndDlg, IDC_ENABLEUPDATES)));
			}
			break;
		}
	}
	return FALSE;
}
