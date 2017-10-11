/*
SRMM

Copyright 2000-2005 Miranda ICQ/IM project, 
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
#ifndef SRMM_GLOBALS_H
#define SRMM_GLOBALS_H

#define SMF_SHOWINFO        0x00000001
#define SMF_SHOWBTNS        0x00000002
#define SMF_SENDBTN         0x00000004
#define SMF_SHOWTYPING      0x00000008
#define SMF_SHOWTYPINGWIN   0x00000010
#define SMF_SHOWTYPINGTRAY  0x00000020
#define SMF_SHOWTYPINGCLIST 0x00000040
#define SMF_SHOWICONS       0x00000080
#define SMF_SHOWTIME        0x00000100
#define SMF_AVATAR          0x00000200
#define SMF_SHOWDATE        0x00000400
#define SMF_HIDENAMES       0x00000800
#define SMF_SHOWSECS        0x00001000

#define SMF_ICON_ADD        0
#define SMF_ICON_USERDETAIL 1
#define SMF_ICON_HISTORY    2
#define SMF_ICON_ARROW      3
#define SMF_ICON_TYPING     4

struct GlobalMessageData
{
	unsigned int flags;
	HICON hIcons[5];
	HANDLE hMessageWindowList;
	DWORD openFlags;
};

void InitGlobals();
void FreeGlobals();
void ReloadGlobals();

extern struct GlobalMessageData *g_dat;

#endif
