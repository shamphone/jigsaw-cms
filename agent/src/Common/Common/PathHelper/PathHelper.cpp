#include "stdafx.h"
#include "PathHelper.h"

static char globalBuffer[MAX_PATH];

const char* PathHelper::getApplicationRoot()
{
    ::memset(globalBuffer, 0, MAX_PATH);
	::GetModuleFileName(NULL, globalBuffer, MAX_PATH);

    char* exeFolderEnd = ::strrchr(globalBuffer, '\\');
    if( exeFolderEnd == NULL )
    {
        return NULL;
    }
    *exeFolderEnd = 0;

    char* applicationFolderEnd = ::strrchr(globalBuffer, '\\');
    if( applicationFolderEnd == NULL )
    {
        return NULL;
    }
    *applicationFolderEnd = 0;

    return globalBuffer;
}

const char* PathHelper::getExeName()
{
    ::memset(globalBuffer, 0, MAX_PATH);
	::GetModuleFileName(NULL, globalBuffer, MAX_PATH);

    char* exeFolderEnd = ::strrchr(globalBuffer, '\\');
    if( exeFolderEnd == NULL )
    {
        return NULL;
    }

    return exeFolderEnd + 1;
}

const char* PathHelper::getIniFileFullName()
{
    string exeFileName = string(PathHelper::getExeName());
    string iniFileName = string(PathHelper::getApplicationRoot()) + string("\\conf\\") + exeFileName.substr(0, exeFileName.size()-3) + string("ini");
    
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, iniFileName.c_str());
    return globalBuffer;
}

const char* PathHelper::getHelpFileFullName()
{
    string helpFileName = string(PathHelper::getApplicationRoot()) + string("\\help\\help.htm");
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, helpFileName.c_str());
    return globalBuffer;
}

const char* PathHelper::getDataPath()
{
    string dataPath = string(PathHelper::getApplicationRoot()) + string("\\data\\");
	if( !isDirectoryExist( dataPath.c_str() ) )
	{
		createDirectory( dataPath.c_str() );
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getLeavewordFileFullName(const char* userName, const char* server, bool bCreateIfNotExist)
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server, bCreateIfNotExist) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\leaveword.xml");
	if( !isFileExist( dataPath.c_str() ) )
	{
		if( bCreateIfNotExist )
		{
			createFile(dataPath.c_str());
		}
		else
		{
			return NULL;
		}
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getConfNoticeFileFullName(const char* userName, const char* server, bool bCreateIfNotExist)
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server, bCreateIfNotExist) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\confnotice.xml");
	if( !isFileExist( dataPath.c_str() ) )
	{
		if( bCreateIfNotExist )
		{
			createFile(dataPath.c_str());
		}
		else
		{
			return NULL;
		}
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
	return globalBuffer;
}

const char* PathHelper::getSystemMessageFileFullName(const char* userName, const char* server, bool bCreateIfNotExist)
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server, bCreateIfNotExist) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\systemmsg.xml");
	if( !isFileExist( dataPath.c_str() ) )
	{
		if( bCreateIfNotExist )
		{
			createFile(dataPath.c_str());
		}
		else
		{
			return NULL;
		}
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
	return globalBuffer;
}

const char* PathHelper::getDefaultReceivedFilePath()
{
    string path = string(PathHelper::getDataPath()) + string("received files");
	if( !isDirectoryExist( path.c_str() ) )
	{
		createDirectory( path.c_str() );
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, path.c_str());
    return globalBuffer;
}

const char* PathHelper::getChatMsgPath( const char* userName, const char* server )
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\chat");
	if( !isDirectoryExist( dataPath.c_str() ) )
	{
		createDirectory(dataPath.c_str());
	}
	::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getConferenceMsgPath( const char* userName, const char* server )
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server ) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\conference");
	if( !isDirectoryExist( dataPath.c_str() ) )
	{
		createDirectory(dataPath.c_str());
	}
	::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getUserFacePath( const char* userName, const char* server )
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server ) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\userface\\");
	if( !isDirectoryExist( dataPath.c_str() ) )
	{
		createDirectory(dataPath.c_str());
	}
	::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getUserPrivateConfigFile( const char* userName, const char* server )
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server ) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\Config.ini");
	if( !isFileExist( dataPath.c_str() ) )
		createFile( dataPath.c_str() );
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getUserDataPath( const char* userName, const char* server, bool bCreateIfNotExist )
{
	string dataPath = string(PathHelper::getDataPath()) + string(userName) + string("@") + string(server);
	if( !isDirectoryExist( dataPath.c_str() ) )
	{
		if( bCreateIfNotExist )
		{
			if( !createDirectory( dataPath.c_str() ) )
			{
				return NULL;
			}
		}
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getUserServerListFile( const char* userName, const char* server, bool bCreateIfNotExist )
{
	string dataPath = string( PathHelper::getUserDataPath( userName, server, bCreateIfNotExist ) );
	if( dataPath == "" )
		return NULL;
	dataPath = dataPath + string("\\Server.ini");
	if( !isFileExist( dataPath.c_str() ) )
	{
		if( bCreateIfNotExist )
		{
			createFile( dataPath.c_str() );
		}
		else
		{
			return NULL;
		}
	}
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, dataPath.c_str());
    return globalBuffer;
}

const char* PathHelper::getLoginHistoryFile()
{
	string file = string( getApplicationRoot() ) + string("\\conf\\history.ini");
	if( !isFileExist( file.c_str() ) )
		createFile( file.c_str() );
    ::memset(globalBuffer, 0, MAX_PATH);
    ::strcpy(globalBuffer, file.c_str());
    return globalBuffer;
}

BOOL PathHelper::createDirectory(const char* dir)
{
	char szDir[MAX_PATH] = "";
	strcpy(szDir, dir);
	char* p = strrchr(szDir, '\\');
	if(p == NULL)
	{
		return FALSE;
	}
	*p = '\0';

	if(!isDirectoryExist(szDir))
	{
		if(!createDirectory(szDir))
		{
			return FALSE;
		}
	}
	return CreateDirectory(dir, NULL);
}

BOOL PathHelper::createFile(const char* fileFullName)
{
	FILE* file;
	if( (file = fopen(fileFullName, "w") ) != NULL)
	{
		fclose(file);
		return TRUE;
	}
	return FALSE;
}

BOOL PathHelper::isDirectoryExist(const char* dir)
{
	DWORD dwAttr = 0L;
	dwAttr = GetFileAttributes(dir);
	if(dwAttr != 0xFFFFFFFF && dwAttr & FILE_ATTRIBUTE_DIRECTORY)
	{
		return TRUE;
	}
	return FALSE;
}

BOOL PathHelper::isFileExist(const char* fileFullName)
{
	if( (_access( fileFullName, 0 )) != -1 )
	{
		return TRUE;
	}
	return FALSE;
}
