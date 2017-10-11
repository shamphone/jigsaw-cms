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


BOOL CALLBACK DlgProcAdded(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg)
	{
		case WM_INITDIALOG:
		{	DBEVENTINFO dbei;
			DWORD *uin;
			char *nick,*first,*last,*email;
			HANDLE hDbEvent,hcontact;

			TranslateDialogDefault(hwndDlg);
			SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_MIRANDA)));
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BM_SETIMAGE,IMAGE_ICON,(LPARAM)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_USERDETAILS),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0));
			SendDlgItemMessage(hwndDlg,IDC_ADD,BM_SETIMAGE,IMAGE_ICON,(LPARAM)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_ADDCONTACT),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0));
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_ADD,BUTTONSETASFLATBTN,0,0);
			SendMessage(GetDlgItem(hwndDlg,IDC_ADD), BUTTONADDTOOLTIP, (WPARAM)Translate("Add Contact Permanently to List"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_DETAILS), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's Details"), 0);
			hDbEvent=(HANDLE)lParam;
			//blob is: uin(DWORD), hcontact(HANDLE), nick(ASCIIZ), first(ASCIIZ), last(ASCIIZ), email(ASCIIZ)
			ZeroMemory(&dbei,sizeof(dbei));
			dbei.cbSize=sizeof(dbei);
			dbei.cbBlob=CallService(MS_DB_EVENT_GETBLOBSIZE,(WPARAM)hDbEvent,0);
			dbei.pBlob=mir_alloc(dbei.cbBlob);
			CallService(MS_DB_EVENT_GET,(WPARAM)hDbEvent,(LPARAM)&dbei);
			uin=(PDWORD)dbei.pBlob;
			hcontact=*((PHANDLE)(dbei.pBlob+sizeof(DWORD)));
			if ((hcontact == INVALID_HANDLE_VALUE) || !DBGetContactSettingByte(hcontact, "CList", "NotOnList", 0))
				ShowWindow(GetDlgItem(hwndDlg,IDC_ADD),FALSE);
			nick=(char*)(dbei.pBlob+sizeof(DWORD)+sizeof(HANDLE));
			first=nick+strlen(nick)+1;
			last=first+strlen(first)+1;
			email=last+strlen(last)+1;
			if (*uin) 
				SetDlgItemInt(hwndDlg,IDC_NAME,*uin,FALSE);
			else
				SetDlgItemText(hwndDlg,IDC_NAME,TranslateT("(Unknown)"));
			SetWindowLong(hwndDlg,GWL_USERDATA,lParam);
			SetWindowLong(GetDlgItem(hwndDlg,IDC_DETAILS),GWL_USERDATA,(LONG)hcontact);
			mir_free(dbei.pBlob);
			return TRUE;
		}
		case WM_DRAWITEM:
		{	LPDRAWITEMSTRUCT dis=(LPDRAWITEMSTRUCT)lParam;
			if(dis->hwndItem==GetDlgItem(hwndDlg, IDC_PROTOCOL)) {
				DBEVENTINFO dbei;
				char *szProto;
				HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);

				ZeroMemory(&dbei,sizeof(dbei));
				dbei.cbSize=sizeof(dbei);
				dbei.cbBlob=0;
				CallService(MS_DB_EVENT_GET,(WPARAM)hDbEvent,(LPARAM)&dbei);

				szProto=dbei.szModule;
				if (szProto) {
					HICON hIcon;

					hIcon=(HICON)CallProtoService(szProto,PS_LOADICON,PLI_PROTOCOL|PLIF_SMALL,0);
					if (hIcon) DrawIconEx(dis->hDC,dis->rcItem.left,dis->rcItem.top,hIcon,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0,NULL,DI_NORMAL);
				}
				return TRUE;
			}
			break;
		}
		case WM_COMMAND:
			switch (LOWORD(wParam))
			{
				case IDC_ADD:
				{	HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);
					ADDCONTACTSTRUCT acs={0};

					acs.handle=hDbEvent;
					acs.handleType=HANDLE_EVENT;
					acs.szProto="";
					CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
					ShowWindow(GetDlgItem(hwndDlg,IDC_ADD),FALSE);
					return TRUE;
				}
				case IDC_DETAILS:
					CallService(MS_USERINFO_SHOWDIALOG,(WPARAM)(HANDLE)GetWindowLong((HWND)lParam,GWL_USERDATA),0);
					return TRUE;
				case IDOK:
				{	HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);
					ADDCONTACTSTRUCT acs={0};

					acs.handle=hDbEvent;
					acs.handleType=HANDLE_EVENT;
					acs.szProto="";
					CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
				}
					//fall through
				case IDCANCEL:
					DestroyWindow(hwndDlg);
					return TRUE;
			}
			break;
	}
	return FALSE;
}

BOOL CALLBACK DenyReasonProc(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	static char szReason[256];
	switch (msg)
	{
		case WM_INITDIALOG:
			TranslateDialogDefault(hwndDlg);
			SetWindowLong(hwndDlg,GWL_USERDATA,lParam);
			SendDlgItemMessage(hwndDlg,IDC_REASON,EM_LIMITTEXT,(WPARAM)256,0);
			return TRUE;

		case WM_COMMAND:
			if(LOWORD(wParam)!=IDOK) break;
            {
				DBEVENTINFO dbei;

				ZeroMemory(&dbei,sizeof(dbei));
				dbei.cbSize=sizeof(dbei);
				dbei.cbBlob=0;
				CallService(MS_DB_EVENT_GET,(WPARAM)GetWindowLong(hwndDlg,GWL_USERDATA),(LPARAM)&dbei);
				GetDlgItemTextA(hwndDlg,IDC_REASON,szReason,256);
				CallProtoService(dbei.szModule,PS_AUTHDENY,(WPARAM)GetWindowLong(hwndDlg,GWL_USERDATA),(LPARAM)szReason);
			}
            // fall through
		case WM_CLOSE:
			EndDialog(hwndDlg,0);
			return TRUE;
	}

	return FALSE;
}

BOOL CALLBACK DlgProcAuthReq(HWND hwndDlg, UINT msg, WPARAM wParam, LPARAM lParam)
{
	switch (msg)
	{
		case WM_INITDIALOG:
		{	DBEVENTINFO dbei;
			DWORD *uin;
			char *nick,*first,*last,*email,*reason;
			HANDLE hDbEvent,*hcontact;

			TranslateDialogDefault(hwndDlg);
			SendMessage(hwndDlg, WM_SETICON, ICON_BIG, (LPARAM)LoadIcon(GetModuleHandle(NULL), MAKEINTRESOURCE(IDI_MIRANDA)));
			SendDlgItemMessage(hwndDlg,IDC_ADD,BM_SETIMAGE,IMAGE_ICON,(LPARAM)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_ADDCONTACT),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0));
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BM_SETIMAGE,IMAGE_ICON,(LPARAM)LoadImage(GetModuleHandle(NULL),MAKEINTRESOURCE(IDI_USERDETAILS),IMAGE_ICON,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0));
			SendDlgItemMessage(hwndDlg,IDC_ADD,BUTTONSETASFLATBTN,0,0);
			SendDlgItemMessage(hwndDlg,IDC_DETAILS,BUTTONSETASFLATBTN,0,0);
			SendMessage(GetDlgItem(hwndDlg,IDC_ADD), BUTTONADDTOOLTIP, (WPARAM)Translate("Add Contact Permanently to List"), 0);
			SendMessage(GetDlgItem(hwndDlg,IDC_DETAILS), BUTTONADDTOOLTIP, (WPARAM)Translate("View User's Details"), 0);
			hDbEvent=(HANDLE)lParam;
			//blob is: uin(DWORD),hcontact(HANDLE),nick(ASCIIZ),first(ASCIIZ),last(ASCIIZ),email(ASCIIZ),reason(ASCIIZ)
			ZeroMemory(&dbei,sizeof(dbei));
			dbei.cbSize=sizeof(dbei);
			dbei.cbBlob=CallService(MS_DB_EVENT_GETBLOBSIZE,(WPARAM)hDbEvent,0);
			dbei.pBlob=mir_alloc(dbei.cbBlob);
			CallService(MS_DB_EVENT_GET,(WPARAM)hDbEvent,(LPARAM)&dbei);
			uin=(PDWORD)dbei.pBlob;
			hcontact=*(PHANDLE)(dbei.pBlob+sizeof(DWORD));
			nick=(char *)(dbei.pBlob+sizeof(DWORD)+sizeof(HANDLE));
			first=nick+strlen(nick)+1;
			last=first+strlen(first)+1;
			email=last+strlen(last)+1;
			reason=email+strlen(email)+1;
			SetDlgItemTextA(hwndDlg,IDC_NAME,nick[0]?nick:Translate("(Unknown)"));
			if (hcontact == INVALID_HANDLE_VALUE || !DBGetContactSettingByte(hcontact, "CList", "NotOnList", 0))
				ShowWindow(GetDlgItem(hwndDlg,IDC_ADD),FALSE);
			if (*uin)
				SetDlgItemInt(hwndDlg,IDC_UIN,*uin,FALSE);
			else SetDlgItemText(hwndDlg,IDC_UIN,TranslateT("(Unknown)"));
			SetDlgItemTextA(hwndDlg,IDC_MAIL,email[0]?email:Translate("(Unknown)"));
			SetDlgItemTextA(hwndDlg,IDC_REASON,reason);
			SetWindowLong(hwndDlg,GWL_USERDATA,lParam);
			SetWindowLong(GetDlgItem(hwndDlg,IDC_DETAILS),GWL_USERDATA,(LONG)hcontact);
			mir_free(dbei.pBlob);
			return TRUE;
		}
		case WM_DRAWITEM:
		{	LPDRAWITEMSTRUCT dis=(LPDRAWITEMSTRUCT)lParam;
			if(dis->hwndItem==GetDlgItem(hwndDlg, IDC_PROTOCOL)) {
				DBEVENTINFO dbei;
				char *szProto;
				HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);

				ZeroMemory(&dbei,sizeof(dbei));
				dbei.cbSize=sizeof(dbei);
				dbei.cbBlob=0;
				CallService(MS_DB_EVENT_GET,(WPARAM)hDbEvent,(LPARAM)&dbei);

				szProto=dbei.szModule;
				if (szProto) {
					HICON hIcon;

					hIcon=(HICON)CallProtoService(szProto,PS_LOADICON,PLI_PROTOCOL|PLIF_SMALL,0);
					if (hIcon) DrawIconEx(dis->hDC,dis->rcItem.left,dis->rcItem.top,hIcon,GetSystemMetrics(SM_CXSMICON),GetSystemMetrics(SM_CYSMICON),0,NULL,DI_NORMAL);
				}
				return TRUE;
			}
			break;
		}
		case WM_COMMAND:
			switch (LOWORD(wParam))
			{
				case IDC_ADD:
				{	HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);
					ADDCONTACTSTRUCT acs={0};

					acs.handle=hDbEvent;
					acs.handleType=HANDLE_EVENT;
					acs.szProto="";
					CallService(MS_ADDCONTACT_SHOW,(WPARAM)hwndDlg,(LPARAM)&acs);
					ShowWindow(GetDlgItem(hwndDlg,IDC_ADD),FALSE);
					return TRUE;
				}
				case IDC_DETAILS:
				{	HANDLE hcontact=(HANDLE)GetWindowLong((HANDLE)lParam,GWL_USERDATA);
					CallService(MS_USERINFO_SHOWDIALOG,(WPARAM)hcontact,0);
				}
					return TRUE;
				case IDOK:
				{	HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);
					DBEVENTINFO dbei;
					ZeroMemory(&dbei,sizeof(dbei));
					dbei.cbSize=sizeof(dbei);
					dbei.cbBlob=0;
					CallService(MS_DB_EVENT_GET,(WPARAM)hDbEvent,(LPARAM)&dbei);
					CallProtoService(dbei.szModule,PS_AUTHALLOW,(WPARAM)hDbEvent,0);
				}
					DestroyWindow(hwndDlg);
					return TRUE;
				case IDCANCEL:
				{	HANDLE hDbEvent=(HANDLE)GetWindowLong(hwndDlg,GWL_USERDATA);
					DialogBoxParam(GetModuleHandle(NULL),MAKEINTRESOURCE(IDD_DENYREASON),hwndDlg,DenyReasonProc,(LPARAM)hDbEvent);
				}
					DestroyWindow(hwndDlg);
					return TRUE;
			}
			break;
	}
	return FALSE;
}
		

