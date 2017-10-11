#pragma once

struct Log
{
    static FILE* log;
    static int open();
    static int open(const char* filename);
    static void close();
    static void flush();
    static void printTime();
};


#ifdef USING_FVS_DEBUG

#define FVS_DEBUG0(str)   _RPTF0(_CRT_WARN, str); \
                          Log::printTime(); \
                          fprintf(Log::log, "%s:%d\n", __FILE__, __LINE__); \
                          fprintf(Log::log, str); \
                          fputs("\n\n", Log::log);

#define FVS_DEBUG(str)    FVS_DEBUG0(str)

#define FVS_DEBUG1(str,arg1)  _RPTF1(_CRT_WARN, str, arg1); \
                          Log::printTime(); \
                          fprintf(Log::log, "%s:%d\n", __FILE__, __LINE__); \
                          fprintf(Log::log, str, arg1); \
                          fputs("\n\n", Log::log);

#define FVS_DEBUG2(str,arg1,arg2)    _RPTF2(_CRT_WARN, str, arg1, arg2); \
                          Log::printTime(); \
                          fprintf(Log::log, "%s:%d\n", __FILE__, __LINE__); \
                          fprintf(Log::log, str, arg1, arg2); \
                          fputs("\n\n", Log::log);

#define FVS_DEBUG3(str,arg1,arg2,arg3)  _RPTF3(_CRT_WARN, str, arg1, arg2, arg3); \
                          Log::printTime(); \
                          fprintf(Log::log, "%s:%d\n", __FILE__, __LINE__); \
                          fprintf(Log::log, str, arg1, arg2, arg3); \
                          fputs("\n\n", Log::log);

#else

#define FVS_DEBUG0(str)
#define FVS_DEBUG(str)
#define FVS_DEBUG1(str,arg1)
#define FVS_DEBUG2(str,arg1,arg2)
#define FVS_DEBUG3(str,arg1,arg2,arg3)

#endif

