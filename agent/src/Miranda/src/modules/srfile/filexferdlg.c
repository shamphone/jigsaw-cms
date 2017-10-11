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
#include <io.h>
#include "file.h"

#define HM_RECVEVENT    (WM_USER+10)

static int CheckVirusScanned(HWND hwnd,struct FileDlgData *dat,int i)
{
	if(dat->send) return 1;
	if(dat->fileVirusScanned[i]) return 1;
	if(DBGetContactSettingByte(NULL,"SRFile","WarnBeforeOpening",1)==0) return 1;
	return IDYES==MessageBox(hwnd,TranslateT("This file has not yet been scanned for viruses. Are you certain you want to open it?"),TranslateT("File Received"),MB_YESNO|MB_DEFBUTTON2);
}

#define M_VIRUSSCANDONE  (WM_USER+100)
struct virusscanthreadstartinfo {
	char *szFile;
	int returnCode;
	HWND hwndReply;
};

static void SetOpenFileButtonStyle(HWND hwndButton,int downarrow,int enabled)
{
	if(downarrow) {
		SendMessage(hwndButton,BUTTONSETARROW,1,0);
	}
	else {
		SendMessage(hwndButton,BUTTONSETARROW,0,0);
	}
	EnableWindow(hwndButton,enabled);
}

static void __cdecl RunVirusScannerThread(struct virusscanthreadstartinfo *info)
{
	PROCESS_INFORMATION pi;
	STARTUPINFOA si={0};
	DBVARIANT dbv;
	char szCmdLine[768];

	if(!DBGetContactSetting(NULL,"SRFile","ScanCmdLine",&dbv)) {
		if(dbv.pszVal[0]) {
			char *pszReplace;
			si.cb=sizeof(si);
			pszReplace=strstr(dbv.pszVal,"%f");
			if(pszReplace) {
				if(info->szFile[lstrlenA(info->szFile)-1]=='\\') info->szFile[lstrlenA(info->szFile)-1]='\0';
				*pszReplace=0;
				mir_snprintf(szCmdLine,SIZEOF(szCmdLine),"%s\"%s\"%s",dbv.pszVal,info->szFile,pszReplace+2);
			}
			else lstrcpynA(szCmdLine,dbv.pszVal,SIZEOF(szCmdLine));
			if(CreateProcessA(NULL,szCmdLine,NULL,NULL,FALSE,0,NULL,NULL,&si,&pi)) {
				if(WaitForSingleObject(pi.hProcess,3600*1000)==WAIT_OBJECT_0)
					PostMessage(info->hwndReply,M_VIRUSSCANDONE,info->returnCode,0);
				CloseHandle(pi.hProcess);
				CloseHandle(pi.hThread);
			}
		}
		DBFreeVariant(&dbv);
	}
	mir_free(info->szFile);
	mir_free(info);
}

static void SetFilenameControls(HWND hwndDlg,PROTOFILETRANSFERSTATUS *fts)
{
	char str[MAX_PATH];
	HWND hwndFilename;

	{	TCHAR msg[MAX_PATH];
		GetDlgItemText(hwndDlg,IDC_FILENAME,msg,SIZEOF(msg));
		if(msg[0]) return;

		wsprintf(msg,TranslateT("Current file (%d of %d)"),fts->currentFileNumber+1,fts->totalFiles);
		SetDlgItemText(hwndDlg,IDC_CURRENTFILEGROUP,msg);
	}

	hwndFilename=GetDlgItem(hwndDlg,IDC_FILENAME);
	lstrcpynA(str,fts->currentFile,SIZEOF(str));
	if(strchr(str,'\\')) {
		RECT rcFilename;
		HDC hdc;
		SIZE textSize;
		HFONT hFont;
		int driveNameLen,driveAndEllipsisLen=0;
		char *pszBackslash;

		GetClientRect(hwndFilename,&rcFilename);
		hdc = GetDC(hwndFilename);
		hFont = SelectObject(hdc,(HFONT)SendMessage(hwndFilename,WM_GETFONT,0,0));
		if(str[0] && str[1]==':' && str[2]=='\\') driveNameLen=3;
		else if(str[0]=='\\' && str[1]=='\\') {
			if((pszBackslash=strchr(str+2,'\\'))!=NULL && (pszBackslash=strchr(pszBackslash+1,'\\'))!=NULL)
				driveNameLen=pszBackslash-str+1;
			else driveNameLen=2;
		}
		else driveNameLen=0;
		for(;;) {
			GetTextExtentPoint32A(hdc,str,lstrlenA(str),&textSize);
			if(textSize.cx<rcFilename.right) break;
			pszBackslash=strchr(str+(driveAndEllipsisLen?driveAndEllipsisLen:driveNameLen),'\\');
			if(pszBackslash==NULL) {
				lstrcpyA(str,strrchr(fts->currentFile,'\\')+1);
				break;
			}
			if(driveAndEllipsisLen)
				MoveMemory(str+driveAndEllipsisLen,pszBackslash+1,lstrlenA(pszBackslash));
			else {
				MoveMemory(str+driveNameLen+4,pszBackslash,lstrlenA(pszBackslash)+1);
				CopyMemory(str+driveNameLen,"...\\",4);
				driveAndEllipsisLen=driveNameLen+4;
			}
		}
		SelectObject(hdc,hFont);
		ReleaseDC(hwndFilename,hdc);
	}
	SetWindowTextA(hwndFilename,str);
}

BOOL CALLBACK DlgProcFileTransfer(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	struct FileDlgData *dat=NULL;

	dat=(struct FileDlgData*)GetWindowLong(GetParent(hwndDlg),GWL_USERDATA);
	switch (msg)
	{
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			dat->hNotifyEvent=HookEventMessage(ME_PROTO_ACK,hwndDlg,HM_RECVEVENT);
			dat->transferStatus.currentFileNumber=-1;
			if(dat->send) {
				char szMsg[450];
				GetDlgItemTextA(GetParent(hwndDlg),IDC_MSG,szMsg,SIZEOF(szMsg));
				dat->fs=(HANDLE)CallContactService(dat->hContact,PSS_FILE,(WPARAM)szMsg,(LPARAM)dat->files);
				SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Request sent, waiting for acceptance..."));
				SetOpenFileButtonStyle(GetDlgItem(hwndDlg,IDC_OPENFILE),dat->files[1]!=NULL,1);
				dat->waitingForAcceptance=1; 
			}
			else {	//recv
				char szSavePath[MAX_PATH];

				GetDlgItemTextA(GetParent(hwndDlg),IDC_FILEDIR,szSavePath,SIZEOF(szSavePath));
				CreateDirectoryTree(szSavePath);
				dat->fs=(HANDLE)CallContactService(dat->hContact,PSS_FILEALLOW,(WPARAM)dat->fs,(LPARAM)szSavePath);
				dat->transferStatus.workingDir=mir_strdup(szSavePath);
				if(DBGetContactSettingByte(dat->hContact,"CList","NotOnList",0)) dat->resumeBehaviour=FILERESUME_ASK;
				else dat->resumeBehaviour=DBGetContactSettingByte(NULL,"SRFile","IfExists",FILERESUME_ASK);
				SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Waiting for connection..."));
			}
			{
				/* check we actually got an fs handle back from the protocol */
				if (!dat->fs) {					
					SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Unable to initiate transfer."));
					dat->waitingForAcceptance=0;
				} 
			}
			{	LOGFONT lf;
				HFONT hFont;
				hFont=(HFONT)SendDlgItemMessage(hwndDlg,IDC_CURRENTFILEGROUP,WM_GETFONT,0,0);
				GetObject(hFont,sizeof(lf),&lf);
				lf.lfWeight=FW_BOLD;
				hFont=CreateFontIndirect(&lf);
				SendDlgItemMessage(hwndDlg,IDC_CURRENTFILEGROUP,WM_SETFONT,(WPARAM)hFont,0);
				SendDlgItemMessage(hwndDlg,IDC_ALLFILESGROUP,WM_SETFONT,(WPARAM)hFont,0);
			}
			{	RECT rcParentClient;
				RECT rcThisDlg;
				BOOL (WINAPI *MyAnimateWindow)(HWND hWnd,DWORD dwTime,DWORD dwFlags);

				GetClientRect(GetParent(hwndDlg),&rcParentClient);
				GetClientRect(hwndDlg,&rcThisDlg);
				SetWindowPos(hwndDlg,HWND_TOP,0,rcParentClient.bottom-rcThisDlg.bottom,0,0,SWP_NOSIZE);
				MyAnimateWindow=(BOOL (WINAPI*)(HWND,DWORD,DWORD))GetProcAddress(GetModuleHandleA("USER32"),"AnimateWindow");
				if(MyAnimateWindow)
					MyAnimateWindow(hwndDlg,200,AW_ACTIVATE|AW_SLIDE|AW_VER_NEGATIVE);
				else ShowWindow(hwndDlg,SW_SHOW);
			}
			SendDlgItemMessage(hwndDlg,IDC_OPENFILE,BUTTONSETDEFAULT,1,0);
			if(!dat->waitingForAcceptance) SetTimer(hwndDlg,1,1000,NULL);
			return TRUE;
		case WM_TIMER:
			MoveMemory(dat->bytesRecvedHistory+1,dat->bytesRecvedHistory,sizeof(dat->bytesRecvedHistory)-sizeof(dat->bytesRecvedHistory[0]));
			dat->bytesRecvedHistory[0]=dat->transferStatus.totalProgress;
			if ( dat->bytesRecvedHistorySize < SIZEOF(dat->bytesRecvedHistory))
				dat->bytesRecvedHistorySize++;

			{	TCHAR szSpeed[32], szTime[32], szDisplay[96];
				SYSTEMTIME st;
				ULARGE_INTEGER li;
				FILETIME ft;

				GetSensiblyFormattedSize((dat->bytesRecvedHistory[0]-dat->bytesRecvedHistory[dat->bytesRecvedHistorySize-1])/dat->bytesRecvedHistorySize,szSpeed,SIZEOF(szSpeed),0,1,NULL);
				if(dat->bytesRecvedHistory[0]==dat->bytesRecvedHistory[dat->bytesRecvedHistorySize-1])
					lstrcpy(szTime,_T("??:??:??"));
				else {
					li.QuadPart=BIGI(10000000)*(dat->transferStatus.currentFileSize-dat->transferStatus.currentFileProgress)*dat->bytesRecvedHistorySize/(dat->bytesRecvedHistory[0]-dat->bytesRecvedHistory[dat->bytesRecvedHistorySize-1]);
					ft.dwHighDateTime=li.HighPart; ft.dwLowDateTime=li.LowPart;
					FileTimeToSystemTime(&ft,&st);
					GetTimeFormat(LOCALE_USER_DEFAULT,TIME_FORCE24HOURFORMAT|TIME_NOTIMEMARKER,&st,NULL,szTime,SIZEOF(szTime));
				}
				mir_sntprintf(szDisplay,SIZEOF(szDisplay),_T("%s/%s  (%s %s)"),szSpeed,TranslateT("sec"),szTime,TranslateT("remaining"));
				SetDlgItemText(hwndDlg,IDC_CURRENTSPEED,szDisplay);
				if(dat->bytesRecvedHistory[0]!=dat->bytesRecvedHistory[dat->bytesRecvedHistorySize-1]) {
					li.QuadPart=BIGI(10000000)*(dat->transferStatus.totalBytes-dat->transferStatus.totalProgress)*dat->bytesRecvedHistorySize/(dat->bytesRecvedHistory[0]-dat->bytesRecvedHistory[dat->bytesRecvedHistorySize-1]);
					ft.dwHighDateTime=li.HighPart; ft.dwLowDateTime=li.LowPart;
					FileTimeToSystemTime(&ft,&st);
					GetTimeFormat(LOCALE_USER_DEFAULT,TIME_FORCE24HOURFORMAT|TIME_NOTIMEMARKER,&st,NULL,szTime,SIZEOF(szTime));
				}
				mir_sntprintf(szDisplay,SIZEOF(szDisplay),_T("%s/%s  (%s %s)"),szSpeed,TranslateT("sec"),szTime,TranslateT("remaining"));
				SetDlgItemText(hwndDlg,IDC_ALLSPEED,szDisplay);
			}
			break;
		case WM_COMMAND:
			switch(LOWORD(wParam)) {
				case IDCANCEL:
				case IDOK:
					DestroyWindow(GetParent(hwndDlg));
					break;
				case IDC_OPENFOLDER:
					ShellExecuteA(NULL,NULL,dat->transferStatus.workingDir,NULL,NULL,SW_SHOW);
					break;

				case IDC_OPENFILE:
				{
					char **files;
					
					if (dat->send)
						if (dat->files == NULL)
							files = dat->transferStatus.files;
						else
							files = dat->files;
					else
						files=dat->files;

					if (files == NULL || *files == NULL)
						break;


					// Only one single file
					if (files[1] == NULL) {
						if (!CheckVirusScanned(hwndDlg, dat, 0))
							break;
						ShellExecuteA(NULL, NULL, files[0], NULL, NULL, SW_SHOW);
					}
					// Multiple files
					else {
						RECT rc;
						int i,limit;
						char *pszFilename,*pszNewFileName;
						HMENU hMenu = CreatePopupMenu();
						
						if (dat->send)
							limit = dat->transferStatus.totalFiles;
						else
							limit = dat->transferStatus.currentFileNumber;
						
						// Loop over all transfered files and add them to the menu
						for (i = 0; i < limit; i++) {
							pszFilename = strrchr(files[i], '\\');
							if (pszFilename == NULL)
								pszFilename = files[i];
							else
								pszFilename++;
                            {
                                if (pszFilename) {
                                    int pszlen;
                                    char *p;
                                    
                                    pszNewFileName = (char*)mir_alloc(strlen(pszFilename)*2);
                                    p = pszNewFileName;
                                    for (pszlen=0; pszlen<(int)strlen(pszFilename); pszlen++) {
                                        *p++ = pszFilename[pszlen];
                                        if (pszFilename[pszlen]=='&')
                                            *p++ = '&';
                                    }
                                    *p = '\0';
                                    AppendMenuA(hMenu, MF_STRING, i+1, pszNewFileName);
                                    mir_free(pszNewFileName);
                                }
                            }
						}

						GetWindowRect((HWND)lParam, &rc);
						i = TrackPopupMenu(hMenu, TPM_RETURNCMD, rc.left, rc.bottom, 0, hwndDlg, NULL);
						DestroyMenu(hMenu);

						if (i && CheckVirusScanned(hwndDlg, dat, i))
							ShellExecuteA(NULL, NULL, files[i-1], NULL, NULL, SW_SHOW);
					}

					break;
				}

			}
			break;
		case M_FILEEXISTSDLGREPLY:
		{	PROTOFILERESUME *pfr=(PROTOFILERESUME*)lParam;
			char *szOriginalFilename=(char*)wParam;
			char *szProto=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);

			EnableWindow(hwndDlg,TRUE);
			switch(pfr->action) {
				case FILERESUME_CANCEL:
					if (dat->fs) CallContactService(dat->hContact,PSS_FILECANCEL,(WPARAM)dat->fs,0);
					dat->fs=NULL;
					DestroyWindow(GetParent(hwndDlg));
					mir_free(szOriginalFilename);
					if(pfr->szFilename) mir_free((char*)pfr->szFilename);
					mir_free(pfr);
					return 0;
				case FILERESUME_RESUMEALL:
				case FILERESUME_OVERWRITEALL:
					dat->resumeBehaviour=wParam;
					pfr->action&=~FILERESUMEF_ALL;
					break;
				case FILERESUME_RENAMEALL:
					pfr->action=FILERESUME_RENAME;
					{	char *pszExtension,*pszFilename;
						int i;
						if((pszFilename=strrchr(szOriginalFilename,'\\'))==NULL) pszFilename=szOriginalFilename;
						if((pszExtension=strrchr(pszFilename+1,'.'))==NULL) pszExtension=pszFilename+lstrlenA(pszFilename);
						if(pfr->szFilename) mir_free((char*)pfr->szFilename);
						pfr->szFilename=(char*)mir_alloc((pszExtension-szOriginalFilename)+21+lstrlenA(pszExtension));
						for(i=1;;i++) {
							sprintf((char*)pfr->szFilename,"%.*s (%u)%s",pszExtension-szOriginalFilename,szOriginalFilename,i,pszExtension);
							if(_access(pfr->szFilename,0)!=0) break;
						}
					}
					break;
			}
			mir_free(szOriginalFilename);
			CallProtoService(szProto,PS_FILERESUME,(WPARAM)dat->fs,(LPARAM)pfr);
			if(pfr->szFilename) mir_free((char*)pfr->szFilename);
			mir_free(pfr);
			break;
		}
		case HM_RECVEVENT:
		{	ACKDATA *ack=(ACKDATA*)lParam;
			if (ack->hProcess!=dat->fs) break; /* icq abuses this sometimes */
			if(ack->hContact!=dat->hContact) break;
			if(ack->type!=ACKTYPE_FILE) break;

			if(dat->waitingForAcceptance) {
				SetTimer(hwndDlg,1,1000,NULL);
				dat->waitingForAcceptance=0;
			}
			switch(ack->result) {
				case ACKRESULT_SENTREQUEST: SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Decision sent")); break;
				case ACKRESULT_CONNECTING: SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Connecting...")); break;
				case ACKRESULT_CONNECTPROXY: SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Connecting to proxy...")); break;
				case ACKRESULT_CONNECTED: SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Connected")); break;
				case ACKRESULT_LISTENING: SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Waiting for connection...")); break;
				case ACKRESULT_INITIALISING: SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Initialising...")); break;
				case ACKRESULT_NEXTFILE:
					SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Moving to next file..."));
					SetDlgItemTextA(hwndDlg,IDC_FILENAME,"");
					if(dat->transferStatus.currentFileNumber==1 && dat->transferStatus.totalFiles>1 && !dat->send)
						SetOpenFileButtonStyle(GetDlgItem(hwndDlg,IDC_OPENFILE),1,1);
					if(dat->transferStatus.currentFileNumber!=-1 && dat->files && !dat->send && DBGetContactSettingByte(NULL,"SRFile","UseScanner",VIRUSSCAN_DISABLE)==VIRUSSCAN_DURINGDL) {
						if(GetFileAttributesA(dat->files[dat->transferStatus.currentFileNumber])&FILE_ATTRIBUTE_DIRECTORY)
							PostMessage(hwndDlg,M_VIRUSSCANDONE,dat->transferStatus.currentFileNumber,0);
						else {
							struct virusscanthreadstartinfo *vstsi;
							vstsi=(struct virusscanthreadstartinfo*)mir_alloc(sizeof(struct virusscanthreadstartinfo));
							vstsi->hwndReply=hwndDlg;
							vstsi->szFile=mir_strdup(dat->files[dat->transferStatus.currentFileNumber]);
							vstsi->returnCode=dat->transferStatus.currentFileNumber;
							forkthread((void (*)(void*))RunVirusScannerThread,0,vstsi);
						}
					}
					break;
				case ACKRESULT_FILERESUME:
				{
					PROTOFILETRANSFERSTATUS *fts=(PROTOFILETRANSFERSTATUS*)ack->lParam;

					FreeProtoFileTransferStatus(&dat->transferStatus);
					CopyProtoFileTransferStatus(&dat->transferStatus,fts);
					SetFilenameControls(hwndDlg,fts);
					if(_access(fts->currentFile,0)!=0) break;
					SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("File already exists"));
					if(dat->resumeBehaviour==FILERESUME_ASK) {
						ShowWindow(hwndDlg,SW_SHOWNORMAL);
						CreateDialogParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_FILEEXISTS),hwndDlg,DlgProcFileExists,(LPARAM)fts);
						EnableWindow(hwndDlg,FALSE);
					}
					else {
						PROTOFILERESUME *pfr;
						pfr=(PROTOFILERESUME*)mir_alloc(sizeof(PROTOFILERESUME));
						pfr->action=dat->resumeBehaviour;
						pfr->szFilename=NULL;
						PostMessage(hwndDlg,M_FILEEXISTSDLGREPLY,(WPARAM)mir_strdup(fts->currentFile),(LPARAM)pfr);
					}
					SetWindowLong(hwndDlg,DWL_MSGRESULT,1);
					return TRUE;
				}
				case ACKRESULT_DATA:
				{
					PROTOFILETRANSFERSTATUS *fts=(PROTOFILETRANSFERSTATUS*)ack->lParam;
					TCHAR str[256],szSizeDone[32],szSizeTotal[32],*contactName;
					int units;

					/* HACK: for 0.3.3, limit updates to around 1.1 ack per second */
					if (fts->totalProgress!=fts->totalBytes && GetTickCount() - dat->dwTicks < 650) break; // the last update was less than a second ago!
					dat->dwTicks=GetTickCount();

/* FIXME: There is a major performance issue  with creating and freeing this list EVERY DAMN ACK! */
					FreeProtoFileTransferStatus(&dat->transferStatus);
					CopyProtoFileTransferStatus(&dat->transferStatus,fts);
					if ( dat->fileVirusScanned==NULL )
						dat->fileVirusScanned=(int*)mir_calloc(sizeof(int) * fts->totalFiles);
					if ( !dat->send ) {
						if ( dat->files == NULL )
							dat->files = ( char** )mir_calloc(( fts->totalFiles+1 )*sizeof( char* ));
						if ( fts->currentFileNumber < fts->totalFiles && dat->files[ fts->currentFileNumber ] == NULL )
							dat->files[ fts->currentFileNumber ] = mir_strdup( fts->currentFile );
					}
/* FIXME: There is a performance issue of creating this list here if it does not exist */

					SetDlgItemText(hwndDlg,IDC_STATUS,TranslateTS(fts->sending?_T("Sending..."):_T("Receiving...")));
					SetFilenameControls(hwndDlg,fts);
					SendDlgItemMessage(hwndDlg,IDC_CURRENTFILEPROGRESS, PBM_SETPOS, fts->currentFileSize?(WPARAM)(BIGI(100)*fts->currentFileProgress/fts->currentFileSize):0, 0);
					SendDlgItemMessage(hwndDlg,IDC_ALLFILESPROGRESS, PBM_SETPOS, fts->totalBytes?(WPARAM)(BIGI(100)*fts->totalProgress/fts->totalBytes):0, 0);

					GetSensiblyFormattedSize(fts->currentFileSize,szSizeTotal,SIZEOF(szSizeTotal),0,1,&units);
					GetSensiblyFormattedSize(fts->currentFileProgress,szSizeDone,SIZEOF(szSizeTotal),units,0,NULL);
					mir_sntprintf(str,SIZEOF(str),_T("%s / %s  (%d%%)"),szSizeDone,szSizeTotal,fts->currentFileSize?(int)(BIGI(100)*fts->currentFileProgress/fts->currentFileSize):0);
					SetDlgItemText(hwndDlg,IDC_CURRENTTRANSFERRED,str);

					GetSensiblyFormattedSize(fts->totalBytes,szSizeTotal,SIZEOF(szSizeTotal),0,1,&units);
					GetSensiblyFormattedSize(fts->totalProgress,szSizeDone,SIZEOF(szSizeTotal),units,0,NULL);
					mir_sntprintf(str,SIZEOF(str),_T("%s / %s  (%d%%)"),szSizeDone,szSizeTotal,fts->totalBytes?(int)(BIGI(100)*fts->totalProgress/fts->totalBytes):0);
					SetDlgItemText(hwndDlg,IDC_ALLTRANSFERRED,str);

					contactName=(TCHAR*)CallService(MS_CLIST_GETCONTACTDISPLAYNAME,(WPARAM)dat->hContact,GCDNF_TCHAR);
					mir_sntprintf(str,SIZEOF(str),_T("%d%%: %s: %s"),fts->totalBytes?(int)(BIGI(100)*fts->totalProgress/fts->totalBytes):0,contactName,TranslateTS(dat->send?(fts->totalFiles==1?_T("Sending file"):_T("Sending files")):(fts->totalFiles==1?_T("Receiving file"):_T("Receiving files"))));
					SetWindowText(GetParent(hwndDlg),str);
					break;
				}
				case ACKRESULT_SUCCESS:
				{
					dat->fs=NULL; /* protocol will free structure */
					SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Transfer completed")); 
					if (ack->result==ACKRESULT_SUCCESS) SkinPlaySound("FileDone");
					if(!dat->send) {	//receiving
						int useScanner=DBGetContactSettingByte(NULL,"SRFile","UseScanner",VIRUSSCAN_DISABLE);
						if(useScanner!=VIRUSSCAN_DISABLE) {
							struct virusscanthreadstartinfo *vstsi;
							vstsi=(struct virusscanthreadstartinfo*)mir_alloc(sizeof(struct virusscanthreadstartinfo));
							vstsi->hwndReply=hwndDlg;
							if(useScanner==VIRUSSCAN_DURINGDL) {
								vstsi->returnCode=dat->transferStatus.currentFileNumber;
								if(GetFileAttributesA(dat->files[dat->transferStatus.currentFileNumber])&FILE_ATTRIBUTE_DIRECTORY) {
									PostMessage(hwndDlg,M_VIRUSSCANDONE,vstsi->returnCode,0);
									mir_free(vstsi);
									vstsi=NULL;
								}
								else vstsi->szFile=mir_strdup(dat->files[dat->transferStatus.currentFileNumber]);
							}
							else {
								vstsi->szFile=mir_strdup(dat->transferStatus.workingDir);
								vstsi->returnCode=-1;
							}
							SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Scanning for viruses..."));
							if(vstsi) forkthread((void (*)(void*))RunVirusScannerThread,0,vstsi);
						}
						dat->transferStatus.currentFileNumber=dat->transferStatus.totalFiles;
					}
					else {	 //sending
						DBEVENTINFO dbei={0};
						char szMsg[450],szFilenames[1024],szBuffer[1024];
						int i, j=0, nCount=0;
						GetDlgItemTextA(GetParent(hwndDlg),IDC_FILE,szBuffer,SIZEOF(szBuffer));
						GetDlgItemTextA(GetParent(hwndDlg),IDC_MSG,szMsg,SIZEOF(szMsg));
						nCount = lstrlenA(szBuffer);
						for( i = 0; i < nCount; i++ )
						{
                            szFilenames[i + j] = szBuffer[i];
							if( szBuffer[i] == '\\' )
							{
								szFilenames[i + j + 1] = '\\';
								j++;
							}
						}
                        szFilenames[i+j] = '\0';
						dbei.cbSize=sizeof(dbei);
						dbei.szModule=(char*)CallService(MS_PROTO_GETCONTACTBASEPROTO,(WPARAM)dat->hContact,0);
						dbei.eventType=EVENTTYPE_FILE;
						dbei.flags=DBEF_SENT;
						dbei.timestamp=time(NULL);
						dbei.cbBlob=sizeof(DWORD)+lstrlenA(szFilenames)+lstrlenA(szMsg)+2;
						dbei.pBlob=(PBYTE)mir_alloc(dbei.cbBlob);
						*(PDWORD)dbei.pBlob=0;
						lstrcpyA(dbei.pBlob+sizeof(DWORD),szFilenames);
						lstrcpyA(dbei.pBlob+sizeof(DWORD)+lstrlenA(szFilenames)+1,szMsg);
						CallService(MS_DB_EVENT_ADD,(WPARAM)dat->hContact,(LPARAM)&dbei);
						if (dbei.pBlob)
							mir_free(dbei.pBlob);
						dat->files=NULL;   //protocol library frees this
					}
				}
					//fall through
				case ACKRESULT_FAILED:
					dat->fs=NULL; /* protocol will free structure */
					KillTimer(hwndDlg,1);
					if(!dat->send) SetOpenFileButtonStyle(GetDlgItem(hwndDlg,IDC_OPENFILE),dat->transferStatus.totalFiles>1,1);
					SetDlgItemText(hwndDlg,IDCANCEL,TranslateT("Close"));
					if (dat->hNotifyEvent) UnhookEvent(dat->hNotifyEvent);
					dat->hNotifyEvent=NULL;
					if(ack->result==ACKRESULT_FAILED) {
						SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("File transfer failed"));
						SkinPlaySound("FileFailed");
					} else 
						if(DBGetContactSettingByte(NULL,"SRFile","AutoClose",0))
							PostMessage(hwndDlg,WM_COMMAND,MAKEWPARAM(IDCANCEL,BN_CLICKED),(LPARAM)GetDlgItem(hwndDlg,IDCANCEL));
					break;

				case ACKRESULT_DENIED:
					dat->fs=NULL; /* protocol will free structure */
					SkinPlaySound("FileDenied");
					KillTimer(hwndDlg, 1);
					if (!dat->send) SetOpenFileButtonStyle(GetDlgItem(hwndDlg, IDC_OPENFILE), dat->transferStatus.totalFiles > 1, 1);
					SetDlgItemText(hwndDlg, IDCANCEL, TranslateT("Close"));
					SetDlgItemText(hwndDlg, IDC_STATUS, TranslateT("File transfer denied"));
					UnhookEvent(dat->hNotifyEvent);
					dat->hNotifyEvent = NULL;
					break;

			}
			break;
		}
		case M_VIRUSSCANDONE:
		{	int done=1,i;
			if((int)wParam==-1) {
				for(i=0;i<dat->transferStatus.totalFiles;i++) dat->fileVirusScanned[i]=1;
			}
			else {
				dat->fileVirusScanned[wParam]=1;
				for(i=0;i<dat->transferStatus.totalFiles;i++) if(!dat->fileVirusScanned[i]) {done=0; break;}
			}
			if(done) SetDlgItemText(hwndDlg,IDC_STATUS,TranslateT("Transfer and virus scan complete"));
			break;
		}
		case WM_DESTROY:
			KillTimer(hwndDlg,1);
			if(dat->fs) CallContactService(dat->hContact,PSS_FILECANCEL,(WPARAM)dat->fs,0);
			dat->fs=NULL;
			if(dat->hNotifyEvent) {UnhookEvent(dat->hNotifyEvent); dat->hNotifyEvent=NULL;}
			FreeProtoFileTransferStatus(&dat->transferStatus);
			if(!dat->send) FreeFilesMatrix(&dat->files);
			if(dat->fileVirusScanned) mir_free(dat->fileVirusScanned);
			{	HFONT hFont;
				hFont=(HFONT)SendDlgItemMessage(hwndDlg,IDC_CURRENTFILEGROUP,WM_GETFONT,0,0);
				DeleteObject(hFont);
			}
			break;
		case WM_CTLCOLORSTATIC:
			SetBkMode( (HDC)wParam, TRANSPARENT );
		case WM_CTLCOLORDLG:
			{
				HBRUSH hBrush = CreateSolidBrush( RGB( 228, 240, 254 ) );
				return hBrush;
			}
	}
	return FALSE;
}
