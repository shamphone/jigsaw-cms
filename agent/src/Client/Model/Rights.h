#pragma once

// 权限列表
struct Rights
{
    const static int VIDEO_SEND = 1;
    const static int VIDEO_RECEIVE = 2;
    const static int AUDIO_SEND = 3;
    const static int AUDIO_RECEIVE = 4;
    const static int SPEAK_APPLY = 5;
    const static int SPEAK_CONTROL = 6;
    const static int DESKTOP_SEND = 7;
    const static int DESKTOP_RECEIVE = 8;
    const static int FILE_SEND = 9;
    const static int FILE_RECEIVE = 10;
    const static int INVITE_USER = 11;
    const static int KICK_USER = 12;
	const static int CONTROL_USER = 13;
    const static int BECONTROLED_USER = 14;
};

