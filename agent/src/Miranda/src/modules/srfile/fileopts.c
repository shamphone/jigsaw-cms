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
#include "file.h"

#define VSCAN_MCAFEE      1
#define VSCAN_DRSOLOMON   2
#define VSCAN_NORTON      3
#define VSCAN_CA          4

struct virusscannerinfo {
	char *szProductName;
	char *szExeRegPath;
	char *szExeRegValue;
	char *szCommandLine;
};

static struct virusscannerinfo virusScanners[]={
	{"Network Associates/McAfee VirusScan","SOFTWARE\\McAfee\\VirusScan","Scan32EXE","\"%s\" %%f /nosplash /comp /autoscan /autoexit /noboot"},
	{"Dr Solomon's VirusScan (Network Associates)","SOFTWARE\\Network Associates\\TVD\\VirusScan\\AVConsol\\General","szScannerExe","\"%s\" %%f /uinone /noboot /comp /prompt /autoexit"},
	{"Norton AntiVirus","SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\App Paths\\Navw32.exe",NULL,"\"%s\" %%f /b- /m- /s+ /noresults"},
	{"Computer Associates/Inoculate IT","Software\\Antivirus","ImageFilename","\"%s\" %%f /display=progress /exit"},
	{"Computer Associates eTrust","SOFTWARE\\ComputerAssociates\\Anti-Virus\\Resident","VetPath","\"%s\" %%f /display=progress /exit"},
	{"Kaspersky Anti-Virus","SOFTWARE\\KasperskyLab\\Components\\101","EXEName","\"%s\" /S /Q %%f"},
};

#define M_UPDATEENABLING   (WM_USER+100)
#define M_SCANCMDLINESELCHANGE  (WM_USER+101)
static BOOL CALLBACK DlgProcFileOpts(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch(msg) {
		case WM_INITDIALOG:
		{	DBVARIANT dbv;

			TranslateDialogDefault(hwndDlg);
			{
				char str[MAX_PATH];
				GetContactReceivedFilesDir(NULL,str,SIZEOF(str));
				SetDlgItemTextA(hwndDlg,IDC_FILEDIR,str);
			}
			{	HRESULT (STDAPICALLTYPE *MySHAutoComplete)(HWND,DWORD);

				MySHAutoComplete=(HRESULT (STDAPICALLTYPE*)(HWND,DWORD))GetProcAddress(GetModuleHandleA("shlwapi"),"SHAutoComplete");
				if(MySHAutoComplete) MySHAutoComplete(GetWindow(GetDlgItem(hwndDlg,IDC_FILEDIR),GW_CHILD),1);
			}
			CheckDlgButton(hwndDlg, IDC_AUTOACCEPT, DBGetContactSettingByte(NULL,"SRFile","AutoAccept",0) ? BST_CHECKED : BST_UNCHECKED);
			CheckDlgButton(hwndDlg, IDC_AUTOMIN, DBGetContactSettingByte(NULL,"SRFile","AutoMin",0) ? BST_CHECKED : BST_UNCHECKED);
			CheckDlgButton(hwndDlg, IDC_AUTOCLOSE, DBGetContactSettingByte(NULL,"SRFile","AutoClose",0) ? BST_CHECKED : BST_UNCHECKED);
			switch(DBGetContactSettingByte(NULL,"SRFile","UseScanner",VIRUSSCAN_DISABLE)) {
				case VIRUSSCAN_AFTERDL: CheckDlgButton(hwndDlg, IDC_SCANAFTERDL, BST_CHECKED); break;
				case VIRUSSCAN_DURINGDL: CheckDlgButton(hwndDlg, IDC_SCANDURINGDL, BST_CHECKED); break;
				default: CheckDlgButton(hwndDlg, IDC_NOSCANNER, BST_CHECKED); break;
			}
			CheckDlgButton(hwndDlg, IDC_WARNBEFOREOPENING, DBGetContactSettingByte(NULL,"SRFile","WarnBeforeOpening",1) ? BST_CHECKED : BST_UNCHECKED);
			{	char szScanExe[MAX_PATH];
				int i,iItem;
				for( i=0; i < SIZEOF(virusScanners); i++ ) {
					if(SRFile_GetRegValue(HKEY_LOCAL_MACHINE,virusScanners[i].szExeRegPath,virusScanners[i].szExeRegValue,szScanExe,SIZEOF(szScanExe))) {
						iItem=SendDlgItemMessageA(hwndDlg,IDC_SCANCMDLINE,CB_ADDSTRING,0,(LPARAM)virusScanners[i].szProductName);
						SendDlgItemMessage(hwndDlg,IDC_SCANCMDLINE,CB_SETITEMDATA,iItem,i);
					}
				}
			}
			if(DBGetContactSetting(NULL,"SRFile","ScanCmdLine",&dbv)==0) {
				SetDlgItemTextA(hwndDlg,IDC_SCANCMDLINE,dbv.pszVal);
				DBFreeVariant(&dbv);
			}
			else {
				if(SendDlgItemMessage(hwndDlg,IDC_SCANCMDLINE,CB_GETCOUNT,0,0)) {
					SendDlgItemMessage(hwndDlg,IDC_SCANCMDLINE,CB_SETCURSEL,0,0);
					PostMessage(hwndDlg,M_SCANCMDLINESELCHANGE,0,0);
				}
			}
			switch(DBGetContactSettingByte(NULL,"SRFile","IfExists",FILERESUME_ASK)) {
				case FILERESUME_RESUMEALL: CheckDlgButton(hwndDlg, IDC_RESUME, BST_CHECKED); break;
				case FILERESUME_OVERWRITEALL: CheckDlgButton(hwndDlg, IDC_OVERWRITE, BST_CHECKED); break;
				case FILERESUME_RENAMEALL: CheckDlgButton(hwndDlg, IDC_RENAME, BST_CHECKED); break;
				default: CheckDlgButton(hwndDlg, IDC_ASK, BST_CHECKED); break;
			}
			SendMessage(hwndDlg,M_UPDATEENABLING,0,0);
			return TRUE;
		}
		case M_UPDATEENABLING:
		{	int on=!IsDlgButtonChecked(hwndDlg,IDC_NOSCANNER);
			EnableWindow(GetDlgItem(hwndDlg,IDC_ST_CMDLINE),on);
			EnableWindow(GetDlgItem(hwndDlg,IDC_SCANCMDLINE),on);
			EnableWindow(GetDlgItem(hwndDlg,IDC_SCANCMDLINEBROWSE),on);
			EnableWindow(GetDlgItem(hwndDlg,IDC_ST_CMDLINEHELP),on);
			EnableWindow(GetDlgItem(hwndDlg,IDC_AUTOMIN),IsDlgButtonChecked(hwndDlg,IDC_AUTOACCEPT));
			break;
		}
		case M_SCANCMDLINESELCHANGE:
		{	char str[512],szScanExe[MAX_PATH];
			int iScanner=SendDlgItemMessage(hwndDlg,IDC_SCANCMDLINE,CB_GETITEMDATA,SendDlgItemMessage(hwndDlg,IDC_SCANCMDLINE,CB_GETCURSEL,0,0),0);
			if(iScanner >= SIZEOF(virusScanners) || iScanner<0) break;
			str[0]='\0';
			if(SRFile_GetRegValue(HKEY_LOCAL_MACHINE,virusScanners[iScanner].szExeRegPath,virusScanners[iScanner].szExeRegValue,szScanExe,SIZEOF(szScanExe)))
				wsprintfA(str,virusScanners[iScanner].szCommandLine,szScanExe);
			SetDlgItemTextA(hwndDlg,IDC_SCANCMDLINE,str);
			break;
		}
		case WM_COMMAND:
			switch(LOWORD(wParam)) {
				case IDC_FILEDIR:
					if((HIWORD(wParam)!=EN_CHANGE || (HWND)lParam!=GetFocus())) return 0;
					break;
				case IDC_FILEDIRBROWSE:
				{	char str[MAX_PATH];
					GetDlgItemTextA(hwndDlg,IDC_FILEDIR,str,SIZEOF(str));
					if(BrowseForFolder(hwndDlg,str))
						SetDlgItemTextA(hwndDlg,IDC_FILEDIR,str);
					break;
				}
				case IDC_AUTOACCEPT:
				case IDC_NOSCANNER:
				case IDC_SCANAFTERDL:
				case IDC_SCANDURINGDL:
					SendMessage(hwndDlg,M_UPDATEENABLING,0,0);
					break;
				case IDC_SCANCMDLINE:
					if(HIWORD(wParam)==CBN_SELCHANGE) PostMessage(hwndDlg,M_SCANCMDLINESELCHANGE,0,0);
					else if(HIWORD(wParam)!=CBN_EDITCHANGE) return 0;
					break;
				case IDC_SCANCMDLINEBROWSE:
				{	char str[MAX_PATH+2];
					OPENFILENAMEA ofn={0};
					char filter[512],*pfilter;

					GetDlgItemTextA(hwndDlg,IDC_SCANCMDLINE,str,SIZEOF(str));
					ofn.lStructSize=OPENFILENAME_SIZE_VERSION_400;
					ofn.hwndOwner=hwndDlg;
					ofn.Flags=OFN_FILEMUSTEXIST|OFN_HIDEREADONLY;
					strcpy(filter,Translate("Executable Files"));
					strcat(filter," (*.exe)");
					pfilter=filter+strlen(filter)+1;
					strcpy(pfilter,"*.exe");
					pfilter=pfilter+strlen(pfilter)+1;
					strcpy(pfilter,Translate("All Files"));
					strcat(pfilter," (*)");
					pfilter=pfilter+strlen(pfilter)+1;
					strcpy(pfilter,"*");
					pfilter=pfilter+strlen(pfilter)+1;
					*pfilter='\0';
					ofn.lpstrFilter=filter;
					ofn.lpstrFile=str;
					ofn.nMaxFile=SIZEOF(str)-2;
					if(str[0]=='"')	{
						char *pszQuote=strchr(str+1,'"');
						if(pszQuote) *pszQuote='\0';
						MoveMemory(str,str+1,lstrlenA(str));
					}
					else {
						char *pszSpace=strchr(str,' ');
						if(pszSpace) *pszSpace='\0';
					}
					ofn.nMaxFileTitle=MAX_PATH;
					if(!GetOpenFileNameA(&ofn)) break;
					if(strchr(str,' ')!=NULL) {
						MoveMemory(str+1,str,SIZEOF(str)-2);
						str[0]='"';
						lstrcatA(str,"\"");
					}
					SetDlgItemTextA(hwndDlg,IDC_SCANCMDLINE,str);
					break;
				}
			}
			SendMessage(GetParent(hwndDlg), PSM_CHANGED, 0, 0);
			break;
		case WM_NOTIFY:
			switch (((LPNMHDR)lParam)->code)
			{
				case PSN_APPLY:
				{	char str[512];
					GetDlgItemTextA(hwndDlg,IDC_FILEDIR,str,SIZEOF(str));
					DBWriteContactSettingString(NULL,"SRFile","RecvFilesDirAdv",str);
					DBWriteContactSettingByte(NULL,"SRFile","AutoAccept",(BYTE)IsDlgButtonChecked(hwndDlg,IDC_AUTOACCEPT));
					DBWriteContactSettingByte(NULL,"SRFile","AutoMin",(BYTE)IsDlgButtonChecked(hwndDlg,IDC_AUTOMIN));
					DBWriteContactSettingByte(NULL,"SRFile","AutoClose",(BYTE)IsDlgButtonChecked(hwndDlg,IDC_AUTOCLOSE));
					DBWriteContactSettingByte(NULL,"SRFile","UseScanner",(BYTE)(IsDlgButtonChecked(hwndDlg,IDC_SCANAFTERDL)?VIRUSSCAN_AFTERDL:(IsDlgButtonChecked(hwndDlg,IDC_SCANDURINGDL)?VIRUSSCAN_DURINGDL:VIRUSSCAN_DISABLE)));
					GetDlgItemTextA(hwndDlg,IDC_SCANCMDLINE,str,SIZEOF(str));
					DBWriteContactSettingString(NULL,"SRFile","ScanCmdLine",str);
					DBWriteContactSettingByte(NULL,"SRFile","WarnBeforeOpening",(BYTE)IsDlgButtonChecked(hwndDlg,IDC_WARNBEFOREOPENING));
					DBWriteContactSettingByte(NULL,"SRFile","IfExists",(BYTE)(IsDlgButtonChecked(hwndDlg,IDC_ASK)?FILERESUME_ASK:(IsDlgButtonChecked(hwndDlg,IDC_RESUME)?FILERESUME_RESUMEALL:(IsDlgButtonChecked(hwndDlg,IDC_OVERWRITE)?FILERESUME_OVERWRITEALL:FILERESUME_RENAMEALL))));
					return TRUE;
				}
			}
			break;
	}
	return FALSE;
}

int FileOptInitialise(WPARAM wParam,LPARAM lParam)
{
	OPTIONSDIALOGPAGE odp={0};
	odp.cbSize = sizeof(odp);
	odp.position = 900000000;
	odp.hInstance = GetModuleHandle(NULL);
	odp.pszTemplate = MAKEINTRESOURCEA(IDD_OPT_FILETRANSFER);
	odp.pszTitle = "File Transfers";
	odp.pszGroup = "Events";
	odp.pfnDlgProc = DlgProcFileOpts;
	odp.flags = ODPF_BOLDGROUPS;
	odp.nIDBottomSimpleControl = IDC_VIRUSSCANNERGROUP;
	CallService( MS_OPT_ADDPAGE, wParam, ( LPARAM )&odp );
	return 0;
}

