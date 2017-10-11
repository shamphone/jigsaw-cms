#pragma once

#include "VideoCaptureDevice.h"

class CVideoCapture
{
public:

    // 读取数据以后的回调函数原型定义
    typedef void (*VIDEOCAPTURE_CALLBACK)(void* pObject, char* pBuffer, int nLength);
    
private:

    IBaseFilter* m_pDevice;           // Video Capture Filter
    IGraphBuilder* m_pGraph;          // Graph Manager
	ICaptureGraphBuilder2* m_pBuild;  // Graph Builder
    IMediaControl* m_pControl;        // Media Control
    IBaseFilter* m_pDump;             // Dump file object(Call back)

	IBaseFilter* m_pDecoder;          // DV Decoder
	bool m_IsDV;                      // 是否在使用DV设备进行采集

    VIDEOCAPTURE_CALLBACK m_pCallback;  // Call back function
    void* m_pCallbackParameter;         // Call back function parameter

public:
	CVideoCapture(VIDEOCAPTURE_CALLBACK m_pCallback, void* m_pCallbackParameter);
	virtual ~CVideoCapture(void);

public:
    BOOL Create(const char* devicename);
    void Destroy();

    BOOL Start();
    void Stop();
	void ShowDialog(HWND hParentWnd);

	// Get Device list.
	static bool getDevices(list<CVideoCaptureDevice>& deviceList);

private:

	// Get Capture Filter with specified name, NULL for first available filter
    bool getFilter(const char* strFilterName, IBaseFilter** ppVideoCaptureFilter);

    // Get Default filter
    bool getDefaultFilter(IBaseFilter** ppVideoCaptureFilter);

    // Init graph builder
    bool InitCaptureGraphBuilder( IGraphBuilder **ppGraph, ICaptureGraphBuilder2 **ppBuild);

    // Set format according to the videoconst configuration
    bool SetFormat(ICaptureGraphBuilder2* pBuild, IBaseFilter* pVideoCaptureFilter);

public:

	// Dump Filter call back
	static void __stdcall CallbackStub(void* pObject, unsigned char* pData, int nLength);
};

