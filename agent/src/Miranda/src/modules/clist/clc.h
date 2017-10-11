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

struct ClcContact {
	BYTE type;
	BYTE flags;
	union {
		struct {
			WORD iImage;
			HANDLE hContact;
		};
		struct {
			WORD groupId;
			struct ClcGroup *group;
		};
	};
	BYTE  iExtraImage[MAXEXTRACOLUMNS];
	TCHAR szText[120-MAXEXTRACOLUMNS];
	char * proto;	// MS_PROTO_GETBASEPROTO
};

struct ClcData {
	struct ClcGroup list;
	int rowHeight;
	int yScroll;
	int selection;
	struct ClcFontInfo fontInfo[FONTID_MAX + 1];
	int scrollTime;
	HIMAGELIST himlHighlight;
	int groupIndent;
	TCHAR szQuickSearch[128];
	int iconXSpace;
	HWND hwndRenameEdit;
	COLORREF bkColour, selBkColour, selTextColour, hotTextColour, quickSearchColour;
	int iDragItem, iInsertionMark;
	int dragStage;
	POINT ptDragStart;
	int dragAutoScrolling;
	int dragAutoScrollHeight;
	int leftMargin;
	int insertionMarkHitHeight;
	HBITMAP hBmpBackground;
	int backgroundBmpUse, bkChanged;
	int iHotTrack;
	int gammaCorrection;
	DWORD greyoutFlags;			  //see m_clc.h
	DWORD offlineModes;
	DWORD exStyle;
	POINT ptInfoTip;
	int infoTipTimeout;
	HANDLE hInfoTipItem;
	HIMAGELIST himlExtraColumns;
	int extraColumnsCount;
	int extraColumnSpacing;
	int checkboxSize;
	int showSelAlways;
	int showIdle;
	int noVScrollbar;
	int useWindowsColours;
	int needsResort;
};

//clc.c
extern int g_IconWidth, g_IconHeight;

//clcidents.c
int GetRowsPriorTo(struct ClcGroup *group,struct ClcGroup *subgroup,int contactIndex);
int FindItem(HWND hwnd,struct ClcData *dat,HANDLE hItem,struct ClcContact **contact,struct ClcGroup **subgroup,int *isVisible);
int GetRowByIndex(struct ClcData *dat,int testindex,struct ClcContact **contact,struct ClcGroup **subgroup);
HANDLE ContactToHItem(struct ClcContact *contact);
HANDLE ContactToItemHandle(struct ClcContact *contact,DWORD *nmFlags);

//clcitems.c
struct ClcGroup *AddGroup(HWND hwnd,struct ClcData *dat,const TCHAR *szName,DWORD flags,int groupId,int calcTotalMembers);
void FreeGroup(struct ClcGroup *group);
int AddInfoItemToGroup(struct ClcGroup *group,int flags,const TCHAR *pszText);
void RebuildEntireList(HWND hwnd,struct ClcData *dat);
struct ClcGroup *RemoveItemFromGroup(HWND hwnd,struct ClcGroup *group,struct ClcContact *contact,int updateTotalCount);
void DeleteItemFromTree(HWND hwnd,HANDLE hItem);
void AddContactToTree(HWND hwnd,struct ClcData *dat,HANDLE hContact,int updateTotalCount,int checkHideOffline);
void SortCLC(HWND hwnd,struct ClcData *dat,int useInsertionSort);
int GetGroupContentsCount(struct ClcGroup *group,int visibleOnly);
void SaveStateAndRebuildList(HWND hwnd,struct ClcData *dat);

//clcmsgs.c
LRESULT ProcessExternalMessages(HWND hwnd,struct ClcData *dat,UINT msg,WPARAM wParam,LPARAM lParam);

//clcutils.c
void EnsureVisible(HWND hwnd,struct ClcData *dat,int iItem,int partialOk);
void RecalcScrollBar(HWND hwnd,struct ClcData *dat);
void SetGroupExpand(HWND hwnd,struct ClcData *dat,struct ClcGroup *group,int newState);
void DoSelectionDefaultAction(HWND hwnd,struct ClcData *dat);
int FindRowByText(HWND hwnd,struct ClcData *dat,const TCHAR *text,int prefixOk);
void EndRename(HWND hwnd,struct ClcData *dat,int save);
void DeleteFromContactList(HWND hwnd,struct ClcData *dat);
void BeginRenameSelection(HWND hwnd,struct ClcData *dat);
char *GetGroupCountsText(struct ClcData *dat,struct ClcContact *contact);
int HitTest(HWND hwnd,struct ClcData *dat,int testx,int testy,struct ClcContact **contact,struct ClcGroup **group,DWORD *flags);
void ScrollTo(HWND hwnd,struct ClcData *dat,int desty,int noSmooth);

int GetDropTargetInformation(HWND hwnd,struct ClcData *dat,POINT pt);
int ClcStatusToPf2(int status);
int IsHiddenMode(struct ClcData *dat,int status);
void HideInfoTip(HWND hwnd,struct ClcData *dat);
void NotifyNewContact(HWND hwnd,HANDLE hContact);
void LoadClcOptions(HWND hwnd,struct ClcData *dat);
void RecalculateGroupCheckboxes(HWND hwnd,struct ClcData *dat);
void SetGroupChildCheckboxes(struct ClcGroup *group,int checked);
void InvalidateItem(HWND hwnd,struct ClcData *dat,int iItem);

int fnGetRowBottomY(struct ClcData *dat, int item);
int fnGetRowHeight(struct ClcData *dat, int item);
int fnGetRowTopY(struct ClcData *dat, int item);
int fnGetRowTotalHeight(struct ClcData *dat);
int fnRowHitTest(struct ClcData *dat, int y);

//clcpaint.c
void PaintClc(HWND hwnd,struct ClcData *dat,HDC hdc,RECT *rcPaint);

//clcopts.c
int ClcOptInit(WPARAM wParam,LPARAM lParam);
DWORD GetDefaultExStyle(void);
void GetFontSetting(int i,LOGFONTA *lf,COLORREF *colour);

//clistsettings.c
TCHAR* GetContactDisplayNameW( HANDLE hContact, int mode );

//clcfiledrop.c
void InitFileDropping(void);
void FreeFileDropping(void);
void RegisterFileDropping(HWND hwnd);
void UnregisterFileDropping(HWND hwnd);
