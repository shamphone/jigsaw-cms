/*
Plugin of Miranda IM for communicating with users of the MSN Messenger protocol.
Copyright (c) 2003-5 George Hazan.
Copyright (c) 2002-3 Richard Hughes (original version).

Miranda IM: the free icq client for MS Windows
Copyright (C) 2000-2002 Richard Hughes, Roland Rabien & Tristan Van de Vreede

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

#include "msn_global.h"

//a few little functions to manage queuing send message requests until the
//connection is established

static MsgQueueEntry* msgQueue;
static int msgQueueCount;
static CRITICAL_SECTION csMsgQueue;
static int msgQueueSeq;

void MsgQueue_Init( void )
{
	msgQueueCount = 0;
	msgQueue = NULL;
	msgQueueSeq = 1;
	InitializeCriticalSection( &csMsgQueue );
}

void MsgQueue_Uninit( void )
{
	MsgQueue_Clear( NULL );
	DeleteCriticalSection( &csMsgQueue );
}

int __stdcall MsgQueue_Add( HANDLE hContact, int msgType, const char* msg, int msgSize, filetransfer* ft, int flags )
{
	EnterCriticalSection( &csMsgQueue );
	msgQueue = ( MsgQueueEntry* )mir_realloc( msgQueue, sizeof( MsgQueueEntry )*( msgQueueCount+1 ));

	int seq = msgQueueSeq++;

	MsgQueueEntry& E = msgQueue[ msgQueueCount++ ];
	E.hContact = hContact;
	E.msgSize = msgSize;
	E.msgType = msgType;
	if ( msgSize <= 0 )
		E.message = mir_strdup( msg );
	else
		memcpy( E.message = ( char* )mir_alloc( msgSize ), msg, msgSize );
	E.ft = ft;
	E.seq = seq;
	E.flags = flags;
	E.allocatedToThread = 0;
	E.timeout = DBGetContactSettingDword(NULL, "SRMM", "MessageTimeout", 10000)/1000;

	LeaveCriticalSection( &csMsgQueue );
	return seq;
}

// shall we create another session?
HANDLE __stdcall MsgQueue_CheckContact( HANDLE hContact )
{
	EnterCriticalSection( &csMsgQueue );

	HANDLE ret = NULL;
	for( int i=0; i < msgQueueCount; i++ )
	{
		if ( msgQueue[ i ].hContact == hContact )
		{	ret = hContact;
			break;
	}	}

	LeaveCriticalSection( &csMsgQueue );
	return ret;
}

//for threads to determine who they should connect to
HANDLE __stdcall MsgQueue_GetNextRecipient(void)
{
	EnterCriticalSection( &csMsgQueue );

	HANDLE ret = NULL;
	for( int i=0; i < msgQueueCount; i++ )
	{
		MsgQueueEntry& E = msgQueue[ i ];
		if ( !E.allocatedToThread )
		{
			E.allocatedToThread = 1;
			ret = E.hContact;

			while( ++i < msgQueueCount )
				if ( msgQueue[i].hContact == ret )
					msgQueue[i].allocatedToThread = 1;

			break;
	}	}

	LeaveCriticalSection( &csMsgQueue );
	return ret;
}

//deletes from list. Must mir_free() return value
int __stdcall MsgQueue_GetNext( HANDLE hContact, MsgQueueEntry& retVal )
{
	int i;

	EnterCriticalSection( &csMsgQueue );
	for( i=0; i < msgQueueCount; i++ )
		if ( msgQueue[ i ].hContact == hContact )
			break;

	if ( i == msgQueueCount )
	{	LeaveCriticalSection(&csMsgQueue);
		return 0;
	}

	retVal = msgQueue[ i ];

	msgQueueCount--;
	memmove( msgQueue+i, msgQueue+i+1, sizeof( MsgQueueEntry )*( msgQueueCount-i ));
	msgQueue = ( MsgQueueEntry* )mir_realloc( msgQueue, sizeof( MsgQueueEntry )*msgQueueCount );
	LeaveCriticalSection( &csMsgQueue );
	return i+1;
}

void __stdcall MsgQueue_Clear( HANDLE hContact )
{
	int i;

	if (hContact == NULL)
	{
		EnterCriticalSection( &csMsgQueue );

		for( i=0; i < msgQueueCount; i++ )
			mir_free( msgQueue[ i ].message );
		mir_free( msgQueue );

		msgQueueCount = 0;
		msgQueue = NULL;
		msgQueueSeq = 1;
		LeaveCriticalSection( &csMsgQueue );
	}
	else
	{
		MsgQueueEntry E;
		while (MsgQueue_GetNext(hContact, E) != 0)
			mir_free( E.message );
	}
}
