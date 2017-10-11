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

int LoadLangPackServices(void);

struct LangPackEntry {
	unsigned linePos;
	DWORD englishHash;
	char *english;	  //not currently used, the hash does everything
	char *local;
	wchar_t *wlocal;
};

struct LangPackStruct {
	TCHAR filename[MAX_PATH];
	char  language[64];
	char  lastModifiedUsing[64];
	char  authors[256];
	char  authorEmail[128];
	struct LangPackEntry *entry;
	int entryCount;
	LCID localeID;
	DWORD defaultANSICp;
} static langPack;

static void TrimString(char *str)
{
	int len,start;
	len=lstrlenA(str);
	while(str[0] && (unsigned char)str[len-1]<=' ') str[--len]=0;
	for(start=0;str[start] && (unsigned char)str[start]<=' ';start++);
	MoveMemory(str,str+start,len-start+1);
}

static void TrimStringSimple(char *str) 
{
	if (str[lstrlenA(str)-1] == '\n') str[lstrlenA(str)-1] = '\0';
	if (str[lstrlenA(str)-1] == '\r') str[lstrlenA(str)-1] = '\0';
}

static int IsEmpty(char *str)
{
	int i = 0;

	while (str[i])
	{
		if (str[i]!=' '&&str[i]!='\r'&&str[i]!='\n')
			return 0;
		i++;
	}
	return 1;
}

static void ConvertBackslashes(char *str)
{
	char *pstr;
	for(pstr=str;*pstr;pstr=CharNextA(pstr)) {
		if(*pstr=='\\') {
			switch(pstr[1]) {
case 'n': *pstr='\n'; break;
case 't': *pstr='\t'; break;
case 'r': *pstr='\r'; break;
default: *pstr=pstr[1]; break;
			}
			MoveMemory(pstr+1,pstr+2,lstrlenA(pstr+2)+1);
		}
	}
}

static DWORD LangPackHash(const char *szStr)
{
#if defined _M_IX86 && !defined _NUMEGA_BC_FINALCHECK && !defined __GNUC__
	__asm {				//this is mediocrely optimised, but I'm sure it's good enough
		xor  edx,edx
		mov  esi,szStr
		xor  cl,cl
lph_top:
		xor  eax,eax
		and  cl,31
		mov  al,[esi]
		inc  esi
		test al,al
		jz   lph_end
		rol  eax,cl
		add  cl,5
		xor  edx,eax
		jmp  lph_top
lph_end:
		mov  eax,edx
	}
#else
	DWORD hash=0;
	int i;
	int shift=0;
	for(i=0;szStr[i];i++) {
		hash^=szStr[i]<<shift;
		if(shift>24) hash^=(szStr[i]>>(32-shift))&0x7F;
		shift=(shift+5)&0x1F;
	}
	return hash;
#endif
}

static DWORD LangPackHashW(const char *szStr)
{
#if defined _M_IX86 && !defined _NUMEGA_BC_FINALCHECK && !defined __GNUC__
	__asm {				//this is mediocrely optimised, but I'm sure it's good enough
		xor  edx,edx
		mov  esi,szStr
		xor  cl,cl
lph_top:
		xor  eax,eax
		and  cl,31
		mov  al,[esi]
		inc  esi
		inc  esi
		test al,al
		jz   lph_end
		rol  eax,cl
		add  cl,5
		xor  edx,eax
		jmp  lph_top
lph_end:
		mov  eax,edx
	}
#else
	DWORD hash=0;
	int i;
	int shift=0;
	for(i=0;szStr[i];i+=2) {
		hash^=szStr[i]<<shift;
		if(shift>24) hash^=(szStr[i]>>(32-shift))&0x7F;
		shift=(shift+5)&0x1F;
	}
	return hash;
#endif
}

static int SortLangPackHashesProc(struct LangPackEntry *arg1,struct LangPackEntry *arg2)
{
	if(arg1->englishHash<arg2->englishHash) return -1;
	if(arg1->englishHash>arg2->englishHash) return 1;
	/* both source strings of the same hash (may not be the same string thou) put
	the one that was written first to be found first */
	if(arg1->linePos<arg2->linePos) return -1;
	if(arg1->linePos>arg2->linePos) return 1;
	return 0;
}


static int SortLangPackHashesProc2(struct LangPackEntry *arg1,struct LangPackEntry *arg2)
{
	if(arg1->englishHash<arg2->englishHash) return -1;
	if(arg1->englishHash>arg2->englishHash) return 1;
	return 0;
}

static int LoadLangPack(const TCHAR *szLangPack)
{
	FILE *fp;
	char line[4096];
	char *pszColon;
	char *pszLine;
	int entriesAlloced;
	int startOfLine=0;
	unsigned int linePos=1;
	USHORT langID;
	
	lstrcpy(langPack.filename,szLangPack);
	fp = _tfopen(szLangPack,_T("rt"));
	if(fp==NULL) return 1;
	fgets(line,SIZEOF(line),fp);
	TrimString(line);
	if(lstrcmpA(line,"Miranda Language Pack Version 1")) {fclose(fp); return 2;}
	//headers
	while(!feof(fp)) {
		startOfLine=ftell(fp);
		if(fgets(line,SIZEOF(line),fp)==NULL) break;
		TrimString(line);
		if(IsEmpty(line) || line[0]==';' || line[0]==0) continue;
		if(line[0]=='[') break;
		pszColon=strchr(line,':');
		if(pszColon==NULL) {fclose(fp); return 3;}
		*pszColon=0;
		if(!lstrcmpA(line,"Language")) {mir_snprintf(langPack.language,sizeof(langPack.language),"%s",pszColon+1); TrimString(langPack.language);}
		else if(!lstrcmpA(line,"Last-Modified-Using")) {mir_snprintf(langPack.lastModifiedUsing,sizeof(langPack.lastModifiedUsing),"%s",pszColon+1); TrimString(langPack.lastModifiedUsing);}
		else if(!lstrcmpA(line,"Authors")) {mir_snprintf(langPack.authors,sizeof(langPack.authors),"%s",pszColon+1); TrimString(langPack.authors);}
		else if(!lstrcmpA(line,"Author-email")) {mir_snprintf(langPack.authorEmail,sizeof(langPack.authorEmail),"%s",pszColon+1); TrimString(langPack.authorEmail);}
		else if(!lstrcmpA(line, "Locale")) {
			char szBuf[20], *stopped;

			TrimString(pszColon + 1);
			langID = (USHORT)strtol(pszColon + 1, &stopped, 16);
			langPack.localeID = MAKELCID(langID, 0);
			GetLocaleInfoA(langPack.localeID, LOCALE_IDEFAULTANSICODEPAGE, szBuf, 10);
			szBuf[5] = 0;                       // codepages have max. 5 digits
			langPack.defaultANSICp = atoi(szBuf);
		}
	}
	//body
	fseek(fp,startOfLine,SEEK_SET);
	entriesAlloced=0;
	while(!feof(fp)) {
		if(fgets(line,SIZEOF(line),fp)==NULL) break;
		if(IsEmpty(line) || line[0]==';' || line[0]==0) continue;
		TrimStringSimple(line);
		ConvertBackslashes(line);
		if(line[0]=='[' && line[lstrlenA(line)-1]==']') {
			if(langPack.entryCount && langPack.entry[langPack.entryCount-1].local==NULL) {
				if(langPack.entry[langPack.entryCount-1].english!=NULL) mir_free(langPack.entry[langPack.entryCount-1].english);
				langPack.entryCount--;
			}
			pszLine = line+1;
			line[lstrlenA(line)-1]='\0';
			TrimStringSimple(line);
			if(++langPack.entryCount>entriesAlloced) {
				entriesAlloced+=128;
				langPack.entry=(struct LangPackEntry*)mir_realloc(langPack.entry,sizeof(struct LangPackEntry)*entriesAlloced);
			}
			langPack.entry[langPack.entryCount-1].english=NULL;
			langPack.entry[langPack.entryCount-1].englishHash=LangPackHash(pszLine);
			langPack.entry[langPack.entryCount-1].local=NULL;
			langPack.entry[langPack.entryCount-1].wlocal = NULL;
			langPack.entry[langPack.entryCount-1].linePos=linePos++;
		}
		else if(langPack.entryCount) {
			struct LangPackEntry* E = &langPack.entry[langPack.entryCount-1];

			if(E->local==NULL) {
				E->local=mir_strdup(line);
				{
					int iNeeded = MultiByteToWideChar(langPack.defaultANSICp, 0, line, -1, 0, 0);
					E->wlocal = (wchar_t *)mir_alloc((iNeeded+1) * sizeof(wchar_t));
					MultiByteToWideChar(langPack.defaultANSICp, 0, line, -1, E->wlocal, iNeeded);
				}
			}
			else {
				E->local=(char*)mir_realloc(E->local,lstrlenA(E->local)+lstrlenA(line)+2);
				lstrcatA(E->local,"\n");
				lstrcatA(E->local,line);
				{
					int iNeeded = MultiByteToWideChar(langPack.defaultANSICp, 0, line, -1, 0, 0);
					int iOldLen = wcslen(E->wlocal);
					E->wlocal = (wchar_t*)mir_realloc(E->wlocal, ( sizeof(wchar_t) * ( iOldLen + iNeeded + 2)));
					wcscat(E->wlocal, L"\n");
					MultiByteToWideChar( langPack.defaultANSICp, 0, line, -1, E->wlocal + iOldLen+1, iNeeded);
				}
			}
		}
	}
	qsort(langPack.entry,langPack.entryCount,sizeof(struct LangPackEntry),(int(*)(const void*,const void*))SortLangPackHashesProc);
	fclose(fp);
	return 0;
}

char *LangPackTranslateString(const char *szEnglish, const int W)
{
	struct LangPackEntry key,*entry;

	if ( langPack.entryCount == 0 || szEnglish == NULL ) return (char*)szEnglish;

	key.englishHash = W ? LangPackHashW(szEnglish) : LangPackHash(szEnglish);
	entry=(struct LangPackEntry*)bsearch(&key,langPack.entry,langPack.entryCount,sizeof(struct LangPackEntry),(int(*)(const void*,const void*))SortLangPackHashesProc2);
	if(entry==NULL) return (char*)szEnglish;
	while(entry>langPack.entry)
	{
		entry--;
		if(entry->englishHash!=key.englishHash) {
			entry++;
			return W ? (char *)entry->wlocal : entry->local;
		}
	}
	return W ? (char *)entry->wlocal : entry->local;
}

int LangPackGetDefaultCodePage()
{
	return (langPack.defaultANSICp == 0) ? CP_ACP : langPack.defaultANSICp;
}

int LangPackGetDefaultLocale()
{
	return (langPack.localeID == 0) ? LOCALE_USER_DEFAULT : langPack.localeID;
}

TCHAR* LangPackPcharToTchar( const char* pszStr )
{
	if ( pszStr == NULL )
		return NULL;

	#if defined( _UNICODE )
	{	int len = strlen( pszStr );
		TCHAR* result = ( TCHAR* )alloca(( len+1 )*sizeof( TCHAR ));
		MultiByteToWideChar( LangPackGetDefaultCodePage(), 0, pszStr, -1, result, len );
		result[len] = 0;
		return mir_wstrdup( TranslateW( result ));
	}
	#else
		return mir_strdup( Translate( pszStr ));
	#endif
}

static int LangPackShutdown(WPARAM wParam,LPARAM lParam)
{
	int i;
	for(i=0;i<langPack.entryCount;i++) {
		if(langPack.entry[i].english!=NULL) mir_free(langPack.entry[i].english);
		if(langPack.entry[i].local!=NULL) { mir_free(langPack.entry[i].local); }
		if(langPack.entry[i].wlocal!=NULL) { mir_free(langPack.entry[i].wlocal); }
	}
	if(langPack.entryCount) {
		mir_free(langPack.entry);
		langPack.entry=0;
		langPack.entryCount=0;
	}
	return 0;
}

int LoadLangPackModule(void)
{
	HANDLE hFind;
	TCHAR szSearch[MAX_PATH],*str2,szLangPack[MAX_PATH];
	WIN32_FIND_DATA fd;

	ZeroMemory(&langPack,sizeof(langPack));
	HookEvent(ME_SYSTEM_SHUTDOWN,LangPackShutdown);
	LoadLangPackServices();
	GetModuleFileName(GetModuleHandle(NULL),szSearch,SIZEOF(szSearch));
	str2=_tcsrchr(szSearch,'\\');
	if(str2!=NULL) *str2=0;
	else str2=szSearch;
	lstrcat( szSearch, _T("\\langpack_*.txt"));
	hFind = FindFirstFile( szSearch, &fd );
	if( hFind != INVALID_HANDLE_VALUE ) {
		lstrcpy(str2+1,fd.cFileName);
		lstrcpy(szLangPack,szSearch);
		FindClose(hFind);
		LoadLangPack(szLangPack);
	}
	return 0;
}

/////////////////////////////////////////////////////////////////////////////////////////
// Langpack ANSI <-> UNICODE transformation routines

TCHAR* a2t( const char* str )
{
	if ( str == NULL )
		return NULL;

	#if defined( _UNICODE )
		return LangPackPcharToTchar( str );
	#else
		return mir_strdup( str );
	#endif
}

char* u2a( const wchar_t* src )
{
	int codepage = LangPackGetDefaultCodePage();

	int cbLen = WideCharToMultiByte( codepage, 0, src, -1, NULL, 0, NULL, NULL );
	char* result = ( char* )mir_alloc( cbLen+1 );
	if ( result == NULL )
		return NULL;

	WideCharToMultiByte( codepage, 0, src, -1, result, cbLen, NULL, NULL );
	result[ cbLen ] = 0;
	return result;
}

wchar_t* a2u( const char* src )
{
	int codepage = LangPackGetDefaultCodePage();

	int cbLen = MultiByteToWideChar( codepage, 0, src, -1, NULL, 0 );
	wchar_t* result = ( wchar_t* )mir_alloc( sizeof( wchar_t )*(cbLen+1));
	if ( result == NULL )
		return NULL;

	MultiByteToWideChar( codepage, 0, src, -1, result, cbLen );
	result[ cbLen ] = 0;
	return result;
}

