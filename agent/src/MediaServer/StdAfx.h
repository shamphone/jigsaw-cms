#pragma once

// Version
#include "Common\Common\Version\Version.h"

// Windows Header
#include <winsock2.h>
#include <windows.h>
#include <winsvc.h>

// C Runtime library header
#include <stdio.h>
#include <time.h>

#ifdef _DEBUG
#define _CRTDBG_MAP_ALLOC
#endif
#include <stdlib.h>
#include <crtdbg.h>
#include <process.h>


// STL Header.
#include <vector>
#include <string>
#include <list>
#include <deque>
#include <map>
#include <set>
#include <algorithm>
#include <utility>
using namespace std;

// Using Log
#define USING_FVS_DEBUG
