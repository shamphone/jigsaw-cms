#include "StdAfx.h"
#include ".\desktopserver.h"
#include "LyvcDesktopServer.h"

DesktopServer::DesktopServer(void)
{
}

DesktopServer::~DesktopServer(void)
{
}

DesktopServer* DesktopServer::getInstance()
{
	return new LyvcDesktopServer();
}