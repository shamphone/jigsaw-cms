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

int ResizeDialog(WPARAM wParam,LPARAM lParam);
int InitOpenUrl(void);
int InitWindowList(void);
int InitHyperlink(void);
int InitColourPicker(void);
int InitBitmapFilter(void);
int InitPathUtils(void);

static struct CountryListEntry countries[]={
	{0   ,"Unspecified"},
	{9999,"Other"},
	{0xFFFF,"Unknown"},
	{93  ,"Afghanistan"},
	{355 ,"Albania"},
	{213 ,"Algeria"},
	{684 ,"American Samoa"},
	{376 ,"Andorra"},
	{244 ,"Angola"},
	{101 ,"Anguilla"},
	{102 ,"Antigua and Barbuda"},
	{5902,"Antilles"},
	{54  ,"Argentina"},
	{374 ,"Armenia"},
	{297 ,"Aruba"},
	{247 ,"Ascension Island"},
	{61  ,"Australia"},
	{43  ,"Austria"},
	{994 ,"Azerbaijan"},
	{103 ,"Bahamas"},
	{973 ,"Bahrain"},
	{880 ,"Bangladesh"},
	{104 ,"Barbados"},
	{120 ,"Barbuda"},
	{375 ,"Belarus"},
	{32  ,"Belgium"},
	{501 ,"Belize"},
	{229 ,"Benin"},
	{105 ,"Bermuda"},
	{975 ,"Bhutan"},
	{591 ,"Bolivia"},
	{387 ,"Bosnia and Herzegovina"},
	{267 ,"Botswana"},
	{55  ,"Brazil"},
	{106 ,"British Virgin Islands"},
	{673 ,"Brunei"},
	{359 ,"Bulgaria"},
	{226 ,"Burkina Faso"},
	{257 ,"Burundi"},
	{855 ,"Cambodia"},
	{237 ,"Cameroon"},
	{107 ,"Canada"},
	{178 ,"Canary Islands"},
	{238 ,"Cape Verde Islands"},
	{108 ,"Cayman Islands"},
	{236 ,"Central African Republic"},
	{235 ,"Chad"},
	{56  ,"Chile, Republic of"},
	{86  ,"China"},
	{672 ,"Christmas Island"},
	{6101,"Cocos-Keeling Islands"},
	{6102,"Cocos (Keeling) Islands"},
	{57  ,"Colombia"},
	{2691,"Comoros"},
	{243 ,"Congo, Democratic Republic of (Zaire)"},
	{242 ,"Congo, Republic of the"},
	{682 ,"Cook Islands"},
	{506 ,"Costa Rica"},
	{225 ,"Cote d'Ivoire (Ivory Coast)"},
	{385 ,"Croatia"},
	{53  ,"Cuba"},
	{357 ,"Cyprus"},
	{420 ,"Czech Republic"},
	{45  ,"Denmark"},
	{246 ,"Diego Garcia"},
	{253 ,"Djibouti"},
	{109 ,"Dominica"},
	{110 ,"Dominican Republic"},
	{593 ,"Ecuador"},
	{20  ,"Egypt"},
	{503 ,"El Salvador"},
	{240 ,"Equatorial Guinea"},
	{291 ,"Eritrea"},
	{372 ,"Estonia"},
	{251 ,"Ethiopia"},
	{298 ,"Faeroe Islands"},
	{500 ,"Falkland Islands"},
	{679 ,"Fiji"},
	{358 ,"Finland"},
	{33  ,"France"},
	{5901,"French Antilles"},
	{594 ,"French Guiana"},
	{689 ,"French Polynesia"},
	{241 ,"Gabon"},
	{220 ,"Gambia"},
	{995 ,"Georgia"},
	{49  ,"Germany"},
	{233 ,"Ghana"},
	{350 ,"Gibraltar"},
	{30  ,"Greece"},
	{299 ,"Greenland"},
	{111 ,"Grenada"},
	{590 ,"Guadeloupe"},
	{671 ,"Guam, US Territory of"},
	{502 ,"Guatemala"},
	{224 ,"Guinea"},
	{245 ,"Guinea-Bissau"},
	{592 ,"Guyana"},
	{509 ,"Haiti"},
	{504 ,"Honduras"},
	{852 ,"Hong Kong"},
	{36  ,"Hungary"},
	{354 ,"Iceland"},
	{91  ,"India"},
	{62  ,"Indonesia"},
	{98  ,"Iran (Islamic Republic of)"},
	{964 ,"Iraq"},
	{353 ,"Ireland"},
	{972 ,"Israel"},
	{39  ,"Italy"},
	{112 ,"Jamaica"},
	{81  ,"Japan"},
	{962 ,"Jordan"},
	{705 ,"Kazakhstan"},
	{254 ,"Kenya"},
	{686 ,"Kiribati"},
	{850 ,"Korea, North"},
	{82  ,"Korea, South"},
	{965 ,"Kuwait"},
	{706 ,"Kyrgyzstan"},
	{856 ,"Laos"},
	{371 ,"Latvia"},
	{961 ,"Lebanon"},
	{266 ,"Lesotho"},
	{231 ,"Liberia"},
	{218 ,"Libyan Arab Jamahiriya"},
	{4101,"Liechtenstein"},
	{370 ,"Lithuania"},
	{352 ,"Luxembourg"},
	{853 ,"Macau"},
	{389 ,"Macedonia (F.Y.R.O.M.)"},
	{261 ,"Madagascar"},
	{265 ,"Malawi"},
	{60  ,"Malaysia"},
	{960 ,"Maldives"},
	{223 ,"Mali"},
	{356 ,"Malta"},
	{692 ,"Marshall Islands"},
	{596 ,"Martinique"},
	{222 ,"Mauritania"},
	{230 ,"Mauritius"},
	{269 ,"Mayotte Island"},
	{52  ,"Mexico"},
	{691 ,"Micronesia, Federated States of"},
	{373 ,"Moldova, Republic of"},
	{377 ,"Monaco"},
	{976 ,"Mongolia"},
	{113 ,"Montserrat"},
	{212 ,"Morocco"},
	{258 ,"Mozambique"},
	{95  ,"Myanmar"},
	{264 ,"Namibia"},
	{674 ,"Nauru"},
	{977 ,"Nepal"},
	{31  ,"Netherlands"},
	{599 ,"Netherlands Antilles"},
	{114 ,"Nevis"},
	{687 ,"New Caledonia"},
	{64  ,"New Zealand"},
	{505 ,"Nicaragua"},
	{227 ,"Niger"},
	{234 ,"Nigeria"},
	{683 ,"Niue"},
	{6722,"Norfolk Island"},
	{47  ,"Norway"},
	{968 ,"Oman"},
	{92  ,"Pakistan"},
	{680 ,"Palau"},
	{507 ,"Panama"},
	{675 ,"Papua New Guinea"},
	{595 ,"Paraguay"},
	{51  ,"Peru"},
	{63  ,"Philippines"},
	{48  ,"Poland"},
	{351 ,"Portugal"},
	{121 ,"Puerto Rico"},
	{974 ,"Qatar"},
	{262 ,"Reunion Island"},
	{40  ,"Romania"},
	{6701,"Rota Island"},
	{7   ,"Russia"},
	{250 ,"Rwanda"},
	{290 ,"Saint Helena"},
	{115 ,"Saint Kitts"},
	{1141,"Saint Kitts and Nevis"},
	{122 ,"Saint Lucia"},
	{508 ,"Saint Pierre and Miquelon"},
	{116 ,"Saint Vincent and the Grenadines"},
	{670 ,"Saipan Island"},
	{378 ,"San Marino"},
	{239 ,"Sao Tome and Principe"},
	{966 ,"Saudi Arabia"},
	{442 ,"Scotland"},
	{221 ,"Senegal"},
	{248 ,"Seychelles"},
	{232 ,"Sierra Leone"},
	{65  ,"Singapore"},
	{421 ,"Slovakia"},
	{386 ,"Slovenia"},
	{677 ,"Solomon Islands"},
	{252 ,"Somalia"},
	{27  ,"South Africa"},
	{34  ,"Spain"},
	{94  ,"Sri Lanka"},
	{249 ,"Sudan"},
	{597 ,"Suriname"},
	{268 ,"Swaziland"},
	{46  ,"Sweden"},
	{41  ,"Switzerland"},
	{963 ,"Syrian Arab Republic"},
	{886 ,"Taiwan"},
	{708 ,"Tajikistan"},
	{255 ,"Tanzania"},
	{66  ,"Thailand"},
	{6702,"Tinian Island"},
	{228 ,"Togo"},
	{690 ,"Tokelau"},
	{676 ,"Tonga"},
	{117 ,"Trinidad and Tobago"},
	{216 ,"Tunisia"},
	{90  ,"Turkey"},
	{709 ,"Turkmenistan"},
	{118 ,"Turks and Caicos Islands"},
	{688 ,"Tuvalu"},
	{256 ,"Uganda"},
	{380 ,"Ukraine"},
	{971 ,"United Arab Emirates"},
	{44  ,"United Kingdom"},
	{598 ,"Uruguay"},
	{1   ,"USA"},
	{711 ,"Uzbekistan"},
	{678 ,"Vanuatu"},
	{379 ,"Vatican City"},
	{58  ,"Venezuela"},
	{84  ,"Vietnam"},
	{123 ,"Virgin Islands (USA)"},
	{441 ,"Wales"},
	{681 ,"Wallis and Futuna Islands"},
	{685 ,"Western Samoa"},
	{967 ,"Yemen"},
	{381 ,"Yugoslavia"},
	{3811,"Yugoslavia - Serbia"},
	{382 ,"Yugoslavia - Montenegro"},
	{260 ,"Zambia"},
	{263 ,"Zimbabwe"},
};


static int SaveWindowPosition(WPARAM wParam,LPARAM lParam)
{
	SAVEWINDOWPOS *swp=(SAVEWINDOWPOS*)lParam;
	WINDOWPLACEMENT wp;
	char szSettingName[64];

	wp.length=sizeof(wp);
	GetWindowPlacement(swp->hwnd,&wp);
	wsprintfA(szSettingName,"%sx",swp->szNamePrefix);
	DBWriteContactSettingDword(swp->hContact,swp->szModule,szSettingName,wp.rcNormalPosition.left);
	wsprintfA(szSettingName,"%sy",swp->szNamePrefix);
	DBWriteContactSettingDword(swp->hContact,swp->szModule,szSettingName,wp.rcNormalPosition.top);
	wsprintfA(szSettingName,"%swidth",swp->szNamePrefix);
	DBWriteContactSettingDword(swp->hContact,swp->szModule,szSettingName,wp.rcNormalPosition.right-wp.rcNormalPosition.left);
	wsprintfA(szSettingName,"%sheight",swp->szNamePrefix);
	DBWriteContactSettingDword(swp->hContact,swp->szModule,szSettingName,wp.rcNormalPosition.bottom-wp.rcNormalPosition.top);
	return 0;
}

static int RestoreWindowPosition(WPARAM wParam,LPARAM lParam)
{
	SAVEWINDOWPOS *swp=(SAVEWINDOWPOS*)lParam;
	WINDOWPLACEMENT wp;
	char szSettingName[64];
	int x,y;

	wp.length=sizeof(wp);
	GetWindowPlacement(swp->hwnd,&wp);
	wsprintfA(szSettingName,"%sx",swp->szNamePrefix);
	x=DBGetContactSettingDword(swp->hContact,swp->szModule,szSettingName,-1);
	wsprintfA(szSettingName,"%sy",swp->szNamePrefix);
	y=(int)DBGetContactSettingDword(swp->hContact,swp->szModule,szSettingName,-1);
	if(x==-1) return 1;
	if(wParam&RWPF_NOSIZE) {
		OffsetRect(&wp.rcNormalPosition,x-wp.rcNormalPosition.left,y-wp.rcNormalPosition.top);
	}
	else {
		wp.rcNormalPosition.left=x;
		wp.rcNormalPosition.top=y;
		wsprintfA(szSettingName,"%swidth",swp->szNamePrefix);
		wp.rcNormalPosition.right=wp.rcNormalPosition.left+DBGetContactSettingDword(swp->hContact,swp->szModule,szSettingName,-1);
		wsprintfA(szSettingName,"%sheight",swp->szNamePrefix);
		wp.rcNormalPosition.bottom=wp.rcNormalPosition.top+DBGetContactSettingDword(swp->hContact,swp->szModule,szSettingName,-1);
	}
	wp.flags=0;
	if(wParam&RWPF_NOACTIVATE)
		wp.showCmd = SW_SHOWNOACTIVATE;

	// Make sure it is inside screen
	if (IsWinVer98Plus()) {
		static BOOL loaded = FALSE;
		static HMONITOR (WINAPI *MyMonitorFromRect)(LPCRECT,DWORD) = NULL;
		static BOOL (WINAPI *MyGetMonitorInfo)(HMONITOR,LPMONITORINFO) = NULL;

		if (!loaded) {
			HMODULE hUser32 = GetModuleHandleA("user32");
			if (hUser32) {
				MyMonitorFromRect = (HMONITOR(WINAPI*)(LPCRECT,DWORD))GetProcAddress(hUser32,"MonitorFromRect");
				MyGetMonitorInfo = (BOOL(WINAPI*)(HMONITOR,LPMONITORINFO))GetProcAddress(hUser32,"GetMonitorInfoA");
				if (MyGetMonitorInfo == NULL)
					MyGetMonitorInfo = (BOOL(WINAPI*)(HMONITOR,LPMONITORINFO))GetProcAddress(hUser32,"GetMonitorInfo");
			}
			loaded = TRUE;
		}

		if (MyMonitorFromRect != NULL && MyGetMonitorInfo != NULL) {
			HMONITOR hMonitor;
			MONITORINFO mi;

			hMonitor = MyMonitorFromRect(&wp.rcNormalPosition, MONITOR_DEFAULTTONEAREST);
			mi.cbSize = sizeof(mi);
			MyGetMonitorInfo(hMonitor, &mi);

			if (wp.rcNormalPosition.bottom > mi.rcWork.bottom)
				OffsetRect(&wp.rcNormalPosition, 0, mi.rcWork.bottom - wp.rcNormalPosition.bottom);
			if (wp.rcNormalPosition.bottom < mi.rcWork.top)
				OffsetRect(&wp.rcNormalPosition, 0, mi.rcWork.top - wp.rcNormalPosition.top);
			if (wp.rcNormalPosition.top > mi.rcWork.bottom)
				OffsetRect(&wp.rcNormalPosition, 0, mi.rcWork.bottom - wp.rcNormalPosition.bottom);
			if (wp.rcNormalPosition.top < mi.rcWork.top)
				OffsetRect(&wp.rcNormalPosition, 0, mi.rcWork.top - wp.rcNormalPosition.top);
			if (wp.rcNormalPosition.right > mi.rcWork.right)
				OffsetRect(&wp.rcNormalPosition, mi.rcWork.right - wp.rcNormalPosition.right, 0);
			if (wp.rcNormalPosition.right < mi.rcWork.left)
				OffsetRect(&wp.rcNormalPosition, mi.rcWork.left - wp.rcNormalPosition.left, 0);
			if (wp.rcNormalPosition.left > mi.rcWork.right)
				OffsetRect(&wp.rcNormalPosition, mi.rcWork.right - wp.rcNormalPosition.right, 0);
			if (wp.rcNormalPosition.left < mi.rcWork.left)
				OffsetRect(&wp.rcNormalPosition, mi.rcWork.left - wp.rcNormalPosition.left, 0);
		}
	}

	SetWindowPlacement(swp->hwnd,&wp);
	return 0;
}

static int GetCountryByNumber(WPARAM wParam,LPARAM lParam)
{
	int i;

	for(i=0; i < SIZEOF(countries); i++ )
		if((int)wParam==countries[i].id) return (int)countries[i].szName;
	return (int)(char*)NULL;
}

static int GetCountryList(WPARAM wParam,LPARAM lParam)
{
	*(int*)wParam = SIZEOF(countries);
	*(struct CountryListEntry**)lParam=countries;
	return 0;
}

int LoadUtilsModule(void)
{
	CreateServiceFunction(MS_UTILS_RESIZEDIALOG,ResizeDialog);
	CreateServiceFunction(MS_UTILS_SAVEWINDOWPOSITION,SaveWindowPosition);
	CreateServiceFunction(MS_UTILS_RESTOREWINDOWPOSITION,RestoreWindowPosition);
	CreateServiceFunction(MS_UTILS_GETCOUNTRYBYNUMBER,GetCountryByNumber);
	CreateServiceFunction(MS_UTILS_GETCOUNTRYLIST,GetCountryList);
	InitOpenUrl();
	InitWindowList();
	InitHyperlink();
	InitColourPicker();
	InitBitmapFilter();
	InitPathUtils();
	return 0;
}
