#include "StdAfx.h"
#include "Log.h"

// log file pointer
FILE* Log::log = stderr;

int Log::open(const char* filename)
{
    Log::log = fopen(filename, "a");
    if( Log::log == NULL )
    {
        return -1;
    }

    // Output log file title
    fputs("\n\n---------- ", Log::log);
    __time64_t ltime;
    _time64( &ltime );
    char* s = _ctime64( &ltime );
    ::fwrite(s, strlen(s) -1 , 1, Log::log);
    fputs(" ----------\n\n", Log::log);

    // Return
    return 0;
}

int Log::open()
{
    // Test if a log file has been opened.
    if( Log::log != stderr )
    {
        return 0;
    }

    // make log file name from executable
    char filename[MAX_PATH];
    ::ZeroMemory(filename, MAX_PATH);
    int len = ::GetModuleFileName(NULL, filename, MAX_PATH);
    filename[len-3] = 'l';
    filename[len-2] = 'o';
    filename[len-1] = 'g';

    // Open log file
    return open(filename);
}

void Log::close()
{
    ::fclose(Log::log);
    Log::log = stderr;
}

void Log::flush()
{
    ::fflush(Log::log);
    return;
}

void Log::printTime()
{
    __time64_t ltime;
    _time64( &ltime );
    fputs(_ctime64( &ltime ), Log::log);
}

