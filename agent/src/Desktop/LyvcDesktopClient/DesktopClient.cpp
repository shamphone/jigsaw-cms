#include "StdAfx.h"
#include ".\desktopclient.h"
#include "LyvcDesktopClient.h"

DesktopClient::DesktopClient(void)
{
}

DesktopClient::~DesktopClient(void)
{
}

DesktopClient* DesktopClient::getInstance()
{
	return new LyvcDesktopClient();
}