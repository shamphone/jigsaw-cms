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

//this module was created in v0.1.1.0

#ifndef M_PROTOCOLS_H__
#define M_PROTOCOLS_H__ 1

#include "statusmodes.h"

//call a specific protocol service. See the PS_ constants in m_protosvc.h
__inline static int CallProtoService(const char *szModule,const char *szService,WPARAM wParam,LPARAM lParam)
{
	char str[MAXMODULELABELLENGTH];
	strcpy(str,szModule);
	strcat(str,szService);
	return CallService(str,wParam,lParam);
}

//send a general request through the protocol chain for a contact
//wParam=0
//lParam=(LPARAM)(CCSDATA*)&ccs
//returns the value as documented in the PS_ definition (m_protosvc.h)
typedef struct {
	HANDLE hContact;
	const char *szProtoService;   //a PS_ constant
	WPARAM wParam;
	LPARAM lParam;
} CCSDATA;
#define MS_PROTO_CALLCONTACTSERVICE    "Proto/CallContactService"
__inline static int CallContactService(HANDLE hContact,const char *szProtoService,WPARAM wParam,LPARAM lParam)
{
	CCSDATA ccs;
	ccs.hContact=hContact;
	ccs.szProtoService=szProtoService;
	ccs.wParam=wParam;
	ccs.lParam=lParam;
	return CallService(MS_PROTO_CALLCONTACTSERVICE,0,(LPARAM)&ccs);
}

//a general network 'ack'
//wParam=0
//lParam=(LPARAM)(ACKDATA*)&ack
//Note that just because definitions are here doesn't mean they will be sent.
//Read the documentation for the function you are calling to see what replies
//you will receive.
typedef struct {
	int cbSize;
	const char *szModule;  //the name of the protocol module which initiated this ack
	HANDLE hContact;
	int type;     //an ACKTYPE_ constant
	int result; 	//an ACKRESULT_ constant
	HANDLE hProcess;   //a caller-defined process code
	LPARAM lParam;	   //caller-defined extra info
} ACKDATA;
#define ACKTYPE_MESSAGE    0
#define ACKTYPE_URL        1
#define ACKTYPE_FILE       2
#define ACKTYPE_CHAT       3
#define ACKTYPE_AWAYMSG    4
#define ACKTYPE_AUTHREQ    5
#define ACKTYPE_ADDED      6
#define ACKTYPE_GETINFO    7
#define ACKTYPE_SETINFO    8
#define ACKTYPE_LOGIN      9
#define ACKTYPE_SEARCH     10
#define ACKTYPE_NEWUSER    11
#define ACKTYPE_STATUS     12
#define ACKTYPE_CONTACTS   13	//send/recv of contacts
#define ACKTYPE_AVATAR	   14   //send/recv of avatars from a protocol
#define ACKRESULT_SUCCESS     0
#define ACKRESULT_FAILED      1
//'in progress' result codes:
#define ACKRESULT_CONNECTING  100
#define ACKRESULT_CONNECTED   101
#define ACKRESULT_INITIALISING 102
#define ACKRESULT_SENTREQUEST 103  //waiting for reply...
#define ACKRESULT_DATA        104  //blob of file data sent/recved, or search result
#define ACKRESULT_NEXTFILE    105  //file transfer went to next file
#define ACKRESULT_FILERESUME  106  //a file is about to be received, see PS_FILERESUME
#define ACKRESULT_DENIED      107  //a file send has been denied
#define ACKRESULT_STATUS	  108  //an ack or a series of acks to do with a task have a status change
#define ACKRESULT_LISTENING   109  //waiting for connection
#define ACKRESULT_CONNECTPROXY 110 //connecting to file proxy
#define ME_PROTO_ACK       "Proto/Ack"

// v0.3.2+: When result is ACKRESULT_FAILED or ACKRESULT_DENIED, lParam can point to
// a human readable string with an explanation. For example: "The message was too
// long to be delivered". If no error message is specified, lParam must be NULL.
// Right now only explanations from ACKTYPE_MESSAGE is shown.

//when type==ACKTYPE_FILE && (result==ACKRESULT_DATA || result==ACKRESULT_FILERESUME),
//lParam points to this
typedef struct {
	int cbSize;
	HANDLE hContact;
	int sending;	//true if sending, false if receiving
	char **files;
	int totalFiles;
	int currentFileNumber;
	unsigned long totalBytes;
	unsigned long totalProgress;
	char *workingDir;
	char *currentFile;
	unsigned long currentFileSize;
	unsigned long currentFileProgress;
	unsigned long currentFileTime;  //as seconds since 1970
} PROTOFILETRANSFERSTATUS;

//Enumerate the currently running protocols
//wParam=(WPARAM)(int*)&numberOfProtocols
//lParam=(LPARAM)(PROTOCOLDESCRIPTOR***)&ppProtocolDescriptors
//Returns 0 on success, nonzero on failure
//Neither wParam nor lParam may be NULL
//The list returned by this service is the protocol modules currently installed
//and running. It is not the complete list of all protocols that have ever been
//installed.
//Note that a protocol module need not be an interface to an Internet server,
//they can be encryption and loads of other things, too.
//And yes, before you ask, that is triple indirection. Deal with it.
//Access members using ppProtocolDescriptors[index]->element
typedef struct {
	int cbSize;
	char *szName;    //unique name of the module
	int type;      //module type, see PROTOTYPE_ constants
} PROTOCOLDESCRIPTOR;
// v0.3.3+: 
//
// For recv, it will go from lower to higher, so in this case:
// check ignore, decrypt (encryption), translate
//
// For send, it will go translate, encrypt, ignore(??), send
//
// The DB will store higher numbers here, LOWER in the protocol chain, and lower numbers
// here HIGHER in the protocol chain
//
#define PROTOTYPE_IGNORE	  50    // added during v0.3.3
#define PROTOTYPE_PROTOCOL    1000
#define PROTOTYPE_ENCRYPTION  2000
#define PROTOTYPE_FILTER      3000
#define PROTOTYPE_TRANSLATION 4000
#define PROTOTYPE_OTHER       10000   //avoid using this if at all possible
#define MS_PROTO_ENUMPROTOCOLS     "Proto/EnumProtocols"

//determines if a protocol module is loaded or not
//wParam=0
//lParam=(LPARAM)(const char*)szName
//Returns a pointer to the PROTOCOLDESCRIPTOR if the protocol is loaded, or
//NULL if it isn't.
#define MS_PROTO_ISPROTOCOLLOADED  "Proto/IsProtocolLoaded"

//gets the network-level protocol associated with a contact
//wParam=(WPARAM)(HANDLE)hContact
//lParam=0
//Returns a char* pointing to the asciiz name of the protocol or NULL if the
//contact has no protocol. There is no need to free() it or anything.
//This is the name of the module that actually accesses the network for that
//contact.
#define MS_PROTO_GETCONTACTBASEPROTO  "Proto/GetContactBaseProto"

//determines whether the specified contact has the given protocol in its chain
//wParam=(WPARAM)(HANDLE)hContact
//lParam=(LPARAM)(const char*)szName
//Returns nonzero if it does and 0 if it doesn't
#define MS_PROTO_ISPROTOONCONTACT  "Proto/IsProtoOnContact"

#define PROTOTYPE_SELFTYPING_OFF      0
#define PROTOTYPE_SELFTYPING_ON       1
//This service is for notifying protocols that the user is typing a message v0.3.3+
//in a message dialog.
//This is typically sent by a message dialog when a user in the clist is typing.
//wParam=(WPARAM)(HANDLE)hContact
//lParam=(LPARAM)(int)typing state
//NOTE: Only protocols should generally call this service
#define MS_PROTO_SELFISTYPING "Proto/SelfIsTyping"

#define PROTOTYPE_CONTACTTYPING_OFF      0
#define PROTOTYPE_CONTACTTYPING_INFINITE 2147483647
//This service is for notifying message dialogs/other plugins of a user typing. v0.3.3+
//This is typically sent by a protocol when a user in the clist is typing.
//wParam=(WPARAM)(HANDLE)hContact
//lParam=(LPARAM)(int)time (secs)
//NOTE: The time in seconds is used to tell a message dialog (or other plugin)
//how long to display its notification.  If time is 0, then notification
//of typing ends.
//NOTE: Only protocols should generally call this service
#define MS_PROTO_CONTACTISTYPING "Proto/ContactIsTyping"

//This hook notifies when a user is typing.  If a message dialog supports sending v0.3.3+
//typing notifications it should hook this event and fire the 
//ProtoService PSS_USERISTYPING to the contacts protocol *after* verifying
//that the hContact is not NULL and the the user wishes to send notifications 
//to this user (checked visibility, individual typing blocking, etc).
//wParam=(WPARAM)(HANDLE)hContact
//lParam=(LPARAM)(int)typing state
#define ME_PROTO_CONTACTISTYPING "Proto/ContactIsTypingEvent"


/* -------------- avatar support ---------------------

First a protocol must report it supports PF4_AVATARS via PS_GETCAPS, secondly
it will return the following acks for certain events, the protocol must use
ProtoBroadcastAck(), listeners must hook ME_PROTO_ACK, note that lParam = ACKDATA*

*/

/*

  The following ACKs MUST be sent in order of .result via ProtoBroadcastAck()

	.szModule = protocol module
	.hContact = contact the avatar is for, or 0 if its for the user
	.type = ACKTYPE_AVATAR
	.result = ACKRESULT_CONNECTING, ACKRESULT_CONNECTED, ACKRESULT_SENTREQUEST, ACKRESULT_DATA
	.hProcess = protocol specific
	.lParam = 0

  Once the transfer is complete the following ACKs MUST be sent by the protocol

  .result = ACKRESULT_SUCCESS or ACKRESULT_FAILED
  .hProcess = (HANDLE) &PROTO_AVATAR_INFORMATION
  .lParam = 0;

  Anytime before or during the ack or series of acks ACKRESULT_STATUS maybe sent, this might
  be sent a long while before anything else happens (or after anything happening) For avatars
  it allows callers to identify status information to do with the avatar, including the time
  before background transport has been created, so that you know an avatar will be exchanged
  sometime in the future.

  When ACKRESULT_STATUS, hProcess is the result code - the value of this is protocol dependent,
  See protocol documentation to do with avatars for what codes are defined and what they mean.

*/

#define PA_FORMAT_UNKNOWN	0	// the protocol can not determine much about the "bitmap"
#define PA_FORMAT_PNG		1	// the image is PNG
#define PA_FORMAT_JPEG		2
#define PA_FORMAT_ICON		3	
#define PA_FORMAT_BMP		4
#define PA_FORMAT_GIF       5
#define PA_FORMAT_SWF       6
#define PA_FORMAT_XML       7

typedef struct {
	int cbSize;					// sizeof()
	HANDLE hContact;			// this might have to be set by the caller too
	int format;					// PA_FORMAT_*
	char filename[MAX_PATH];	// full path to filename which contains the avatar
} PROTO_AVATAR_INFORMATION;

#define GAIF_FORCE 1			// force an update of the avatar if there is none

#define GAIR_SUCCESS  0			// information about the avatar has been returned
#define GAIR_WAITFOR  1			// you must hook ME_PROTO_ACK and wait for replies about avatar status
#define GAIR_NOAVATAR 2			// sorry, this contact has no avatars

/*
	wParam : GAIF_*
	lParam : (LPARAM) &PROTO_AVATAR_INFORMATION
	Affect : Make a request to the protocol to return information about a hContact's avatar (or main user)
			 it can return information, tell you there is none, or if forced start requesting an avatar.
	Note:
	Version: 0.3.4+ (2004/09/13)
*/
#define PS_GETAVATARINFO "/GetAvatarInformation"


#endif // M_PROTOCOLS_H


