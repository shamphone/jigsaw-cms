#pragma once

// video data block header
// Comes from the VFW header file
#ifndef _INC_VFW
typedef struct videohdr_tag {
    LPBYTE      lpData;                 /* pointer to locked data buffer */
    DWORD       dwBufferLength;         /* Length of data buffer */
    DWORD       dwBytesUsed;            /* Bytes actually used */
    DWORD       dwTimeCaptured;         /* Milliseconds from start of stream */
    DWORD_PTR   dwUser;                 /* for client's use */
    DWORD       dwFlags;                /* assorted flags (see defines) */
    DWORD_PTR   dwReserved[4];          /* reserved for driver */
} VIDEOHDR, NEAR *PVIDEOHDR, FAR * LPVIDEOHDR;
#endif

#include "VideoCaptureDevice.h"

class CVideoCapture;

class VideoCaptureMgr
{
public:

    // 采集到图像数据以后的回调函数原型定义
    typedef void (*PIO_CALLBACK)(void* pObject, LPVIDEOHDR lpVHdr);

private:

	static CVideoCapture *m_pVideoDevice; // Video device
	static PIO_CALLBACK m_pCallback;      // Callback routine

	// If we run two instances of the program under debug
	// The DirectX library will failed at RenderStream call
	// of the second instance, and then popup dialog to say
	// that "debugger detection", This is annoying, and prevent
	// us running two debugger.
	// To avoid this situation, we use an Event to ensure 
	// that only one instance of VideoCaptureMgr is running.
	static const char* m_eventName;
	static HANDLE m_hEvent;

    static void initCommon(); // Common init task for file and device capture

public:
    static BOOL createFromFile(
        PIO_CALLBACK pCallback,      // Callback function
		void* pCallbackParameter,    // Callback parameter
        const char* filename);       // Video data file

    static BOOL create(
        PIO_CALLBACK pCallback,					 // Callback function
		void* pCallbackParameter,				 // Callback parameter
        const char* deviceName = NULL);			 // Device name, null for default

	static void destroy();

    static BOOL startCapture();
    static void stopCapture();
	static void showDialog(HWND hParentWnd);

	// Get Device list.
	static bool getDevices(list<CVideoCaptureDevice>& deviceList);

public:

    // 视频流的回调函数
    static void CallbackStub(void* pObject, char* pBuffer, int nLength);

};

