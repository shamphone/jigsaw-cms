#pragma once

typedef struct tagCONFERENCE_INFO
{
	char	ip[256];
	int		port;
	char	name[32];
	char	password[32];
	int		conferenceId;
	int		conferenceMode;
	int		peerid;
} CONFERENCE_INFO;
