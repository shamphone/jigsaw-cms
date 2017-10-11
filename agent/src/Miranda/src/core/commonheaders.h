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

#if defined( UNICODE ) && !defined( _UNICODE )
#  define _UNICODE
#endif

// to enable all 0.6 core functions
#define MIRANDA_VER 0x600

#include <tchar.h>
#include <malloc.h>

#define _ALPHA_BASE_ 1	// defined for CVS builds
#define _ALPHA_FUSE_ 1	// defined for fuse powered core

#ifdef _DEBUG
#	define _CRTDBG_MAP_ALLOC
#	include <stdlib.h>
#	include <crtdbg.h>
#endif

#define _WIN32_WINNT 0x0501
#define _WIN32_IE 0x0500
#include <windows.h>
#include <commctrl.h>
#include <stdio.h>
#include <time.h>
#include <stddef.h>
#include <process.h>
#include <io.h>
#include <string.h>
#include <direct.h>
#include "../resource.h"
#include <win2k.h>
#include "modules.h"
#include "miranda.h"
#include "forkthread.h"
#include <m_system.h>
#include <newpluginapi.h>
#include <m_database.h>
#include <m_clc.h>
#include <m_clui.h>
#include <m_langpack.h>
#include <m_clist.h>
#include <m_clistint.h>
#include <m_netlib.h>
#include <m_button.h>
#include <m_protosvc.h>
#include <m_protomod.h>
#include <m_protocols.h>
#include <m_plugins.h>
#include <m_options.h>
#include <m_utils.h>
#include <m_skin.h>
#include <m_contacts.h>
#include <m_message.h>
#include <m_userinfo.h>
#include <m_history.h>
#include <m_addcontact.h>
#include <m_findadd.h>
#include <m_file.h>
#include <m_email.h>
#include <m_awaymsg.h>
#include <m_idle.h>
#include <m_ignore.h>
#include <m_utils.h>
