/*
 * This program update the specified file last-write time to current time
 */

#include <windows.h>
#include <stdio.h>

void main(int argc, char** argv)
{
    if( argc != 2 ) {
        printf("touch [filename]\n");
        return;
    }

    // Open file
    HANDLE hFile = CreateFile(
            argv[1],
            GENERIC_WRITE,
            FILE_SHARE_READ,
            NULL,
            OPEN_EXISTING,
            FILE_ATTRIBUTE_NORMAL,
            NULL);

    // Change date
    FILETIME ft;
    SYSTEMTIME st;

    GetSystemTime(&st);
    SystemTimeToFileTime(&st, &ft);
    SetFileTime(
            hFile,
            (LPFILETIME) NULL,
            (LPFILETIME) NULL,
            &ft);

    // Close file
    ::CloseHandle(hFile);
}

