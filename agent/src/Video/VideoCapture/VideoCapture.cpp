#include "StdAfx.h"
#include ".\videocapture.h"
#include ".\Iir.h"
#include "..\Dump\Dumpuids.h"
#include "..\VideoConfig\VideoConfig.h"

// Default filter name for DV
static const char* m_DVFilterName = "Microsoft DV Camera and VCR";

//
// We copy these functions from the BaseClasses
// project to avoid to much project relationships
//
static void WINAPI FreeMediaType(AM_MEDIA_TYPE& mt)
{
    if (mt.cbFormat != 0) {
        CoTaskMemFree((PVOID)mt.pbFormat);

        // Strictly unnecessary but tidier
        mt.cbFormat = 0;
        mt.pbFormat = NULL;
    }
    if (mt.pUnk != NULL) {
        mt.pUnk->Release();
        mt.pUnk = NULL;
    }
}

static void WINAPI DeleteMediaType(AM_MEDIA_TYPE *pmt)
{
    // allow NULL pointers for coding simplicity

    if (pmt == NULL) {
        return;
    }

    FreeMediaType(*pmt);
    CoTaskMemFree((PVOID)pmt);
}

static void GetFormattedGuid(CString& rString, GUID m_guid)
{
	// load appropriate formatting string
	CString strFormat = "{%08lX-%04X-%04x-%02X%02X-%02X%02X%02X%02X%02X%02X}";

	// then format into destination
	rString.Format(strFormat,
		// first copy...
		m_guid.Data1, m_guid.Data2, m_guid.Data3,
		m_guid.Data4[0], m_guid.Data4[1], m_guid.Data4[2], m_guid.Data4[3],
		m_guid.Data4[4], m_guid.Data4[5], m_guid.Data4[6], m_guid.Data4[7],
		// second copy...
		m_guid.Data1, m_guid.Data2, m_guid.Data3,
		m_guid.Data4[0], m_guid.Data4[1], m_guid.Data4[2], m_guid.Data4[3],
		m_guid.Data4[4], m_guid.Data4[5], m_guid.Data4[6], m_guid.Data4[7]);
}

void __stdcall CVideoCapture::CallbackStub(void* pObject, unsigned char* pData, int nLength)
{
	CVideoCapture* _this = (CVideoCapture*)pObject;
	(*(_this->m_pCallback))(_this->m_pCallbackParameter, (char*)pData, nLength);
}

CVideoCapture::CVideoCapture(VIDEOCAPTURE_CALLBACK pCallback, void* pCallbackParameter)
{
    _ASSERTE(m_pCallback != NULL);
    m_pCallback = pCallback;
    m_pCallbackParameter = pCallbackParameter;

    m_pDevice = NULL;
    m_pGraph = NULL;
    m_pBuild = NULL;
    m_pControl = NULL;
    m_pDump = NULL;
    m_pDecoder = NULL;
}

CVideoCapture::~CVideoCapture(void)
{
    _ASSERTE(m_pDevice == NULL);
    _ASSERTE(m_pGraph == NULL);
    _ASSERTE(m_pBuild == NULL);
    _ASSERTE(m_pControl == NULL);
    _ASSERTE(m_pDump == NULL);

    _ASSERTE(m_pDecoder == NULL);
}

BOOL CVideoCapture::Create(const char* devicename)
{
	::CoInitialize(0);
    BOOL bRet = FALSE;
    HRESULT hr;

	if(! CVideoCapture::InitCaptureGraphBuilder(&m_pGraph, &m_pBuild))
	{
        goto end;
	}

	hr = m_pBuild->SetOutputFileName(
			&CLSID_Dump,
			L"Null.dat",
			&m_pDump,
			NULL);
	if( FAILED(hr))
	{
        goto end;
	}

    // Set callback
	ISetVideoCaptureCallback* pSetVideoCaptureCallback = NULL;
	hr = m_pDump->QueryInterface(IID_ISetVideoCaptureCallback, (void**)&pSetVideoCaptureCallback);
	if( FAILED(hr))
	{
		goto end;
	}
	pSetVideoCaptureCallback->SetCallback(CallbackStub, this);
	pSetVideoCaptureCallback->Release();

	// Get the Video Capture Filter
    bool result;
    if( devicename == NULL )
    {
        result = CVideoCapture::getDefaultFilter(&m_pDevice);
        if( !result )
        {
            goto end;
        }
    }
    else
    {
		// get named video capture filter
        result = CVideoCapture::getFilter(devicename, &m_pDevice);
        if( !result )
        {
			// Try to get the default Video Capture Filter
	        result = CVideoCapture::getDefaultFilter(&m_pDevice);
			if( !result )
			{
				goto end;
			}
        }
    }

    // Set the format for this Filter
    result = CVideoCapture::SetFormat(m_pBuild, m_pDevice);
    if( !result)
    {
        goto end;
    }

	// Add this filter
	hr = m_pGraph->AddFilter(m_pDevice, L"Capture Filter");
	if( FAILED(hr))
	{
        goto end;
	}

	// If the device is a DV, we add a DV Transform filter, and the set clip
    if( this->m_IsDV)
    {
        // Create the DVDecoder filter
        hr = CoCreateInstance(
            CLSID_DVVideoCodec,
            NULL, 
            CLSCTX_INPROC_SERVER, 
            IID_IBaseFilter, 
            (void**)&m_pDecoder);
        if( FAILED(hr))
        {
            goto end;
        }

        // Query the IIPDVDec interface
        IIPDVDec* pIPDVDec = NULL;
        hr = m_pDecoder->QueryInterface(IID_IIPDVDec, (void**)&pIPDVDec);
        if( FAILED(hr))
        {
            goto end;
        }

        // Set it to right DV RESOLUTION according configuration, default to DVRESOLUTION_QUARTER
		int dvResolution;
		int width = VideoConfig::getWidth();
		if (width > 352)
		{
			dvResolution = DVRESOLUTION_FULL;//720*576
		}
		else if (width > 176)
		{
			dvResolution = DVRESOLUTION_HALF;//360*288
		}
		else
		{
			dvResolution = DVRESOLUTION_QUARTER;//180*144
		}
        hr = pIPDVDec->put_IPDisplay(dvResolution);
        if( FAILED(hr))
        {
            goto end;
        }
        pIPDVDec->Release();
        pIPDVDec = NULL;

        // Add dv decoder filter
        hr = m_pGraph->AddFilter(m_pDecoder, L"DV Decoder Filter");
        if( FAILED(hr))
        {
            goto end;
        }

        // Set clip
        pSetVideoCaptureCallback = NULL;
        hr = m_pDump->QueryInterface(IID_ISetVideoCaptureCallback, (void**)&pSetVideoCaptureCallback);
        if( FAILED(hr))
        {
            goto end;
        }
		pSetVideoCaptureCallback->SetClip(VideoConfig::getWidth(), VideoConfig::getHeight());
        pSetVideoCaptureCallback->Release();
    }

    // Render Stream
	__try
	{
		hr = m_pBuild->RenderStream(
				&PIN_CATEGORY_CAPTURE,
				&MEDIATYPE_Video,
				m_pDevice,
				NULL,
				m_pDump);
		if( FAILED(hr))
		{
			goto end;
		}
	}
	__except(EXCEPTION_EXECUTE_HANDLER)
	{
		goto end;
	}

    // Get control
    hr = m_pGraph->QueryInterface(IID_IMediaControl, (void**)&m_pControl);
	if(FAILED(hr))
	{
        goto end;
	}

    bRet = TRUE;

end:
    if( !bRet )
    {
        if(m_pDump)
        {
            m_pDump->Release();
            m_pDump = NULL;
        }
        if(m_pControl)
        {
            m_pControl->Release();
            m_pControl = NULL;
        }
        if(m_pDevice)
        {
            m_pDevice->Release();
            m_pDevice = NULL;
        }
        if(m_pGraph)
        {
            m_pGraph->Release();
            m_pGraph = NULL;
        }
        if(m_pBuild)
        {
            m_pBuild->Release();
            m_pBuild = NULL;
        }

        ::CoUninitialize();
    }
    return bRet;
}

void CVideoCapture::Destroy()
{
    _ASSERTE(m_pDevice != NULL);
    _ASSERTE(m_pGraph != NULL);
    _ASSERTE(m_pBuild != NULL);
    _ASSERTE(m_pControl != NULL);
    _ASSERTE(m_pDump != NULL);

	if( this->m_IsDV)
	{
	    _ASSERTE(m_pDecoder != NULL);
		m_pDecoder->Release();
		m_pDecoder = NULL;
	}

    m_pDump->Release();
    m_pDump = NULL;

    m_pControl->Release();
    m_pControl = NULL;

    m_pDevice->Release();
    m_pDevice = NULL;

    m_pGraph->Release();
    m_pGraph = NULL;

    m_pBuild->Release();
    m_pBuild = NULL;

	::CoUninitialize();
}

BOOL CVideoCapture::Start()
{
    HRESULT hr = m_pControl->Run();
    return SUCCEEDED(hr);
}

void CVideoCapture::Stop()
{
    m_pControl->Stop();
}

void CVideoCapture::ShowDialog(HWND hParentWnd)
{
	if( m_pDevice == NULL )
	{
		return;
	}

    ISpecifyPropertyPages *pProp;
    HRESULT hr = m_pDevice->QueryInterface(IID_ISpecifyPropertyPages, (void **)&pProp);
    if (SUCCEEDED(hr)) 
    {
        // Get the filter's name and IUnknown pointer.
        FILTER_INFO FilterInfo;
        hr = m_pDevice->QueryFilterInfo(&FilterInfo); 
        IUnknown *pFilterUnk;
        m_pDevice->QueryInterface(IID_IUnknown, (void **)&pFilterUnk);

        // Show the page. 
        CAUUID caGUID;
        pProp->GetPages(&caGUID);
        pProp->Release();
        OleCreatePropertyFrame(
            hParentWnd,             // Parent window
            0, 0,                   // Reserved
            FilterInfo.achName,     // Caption for the dialog box
            1,                      // Number of objects (just the filter)
            &pFilterUnk,            // Array of object pointers. 
            caGUID.cElems,          // Number of property pages
            caGUID.pElems,          // Array of property page CLSIDs
            0,                      // Locale identifier
            0, NULL                 // Reserved
        );

        // Clean up.
        pFilterUnk->Release();
        FilterInfo.pGraph->Release(); 
        CoTaskMemFree(caGUID.pElems);
    }

}

bool CVideoCapture::getDevices(list<CVideoCaptureDevice>& deviceList)
{
	USES_CONVERSION;

	HRESULT hr;
	ICreateDevEnum* pSysDevEnum = NULL;
	hr = CoCreateInstance(
		CLSID_SystemDeviceEnum, 
		NULL, 
		CLSCTX_INPROC_SERVER, 
		IID_ICreateDevEnum, 
		(void**)&pSysDevEnum);
	if( FAILED(hr))
	{
		return false;
	}

	// 枚举指定的目录类型
	IEnumMoniker *pEnumCat = NULL;
	hr = pSysDevEnum->CreateClassEnumerator(CLSID_VideoInputDeviceCategory, &pEnumCat, 0);
	if( hr == S_OK )
	{
		IMoniker *pMoniker = NULL;
		ULONG cFetched;

		while( pEnumCat->Next(1, &pMoniker, &cFetched) == S_OK)
		{
			IPropertyBag* pPropBag = NULL;
			hr = pMoniker->BindToStorage(0, 0, IID_IPropertyBag, (void**)&pPropBag);
			if( SUCCEEDED(hr) )
			{
				VARIANT varFriendlyName;
				VARIANT varDevicePath;
				VariantInit(&varFriendlyName);
				VariantInit(&varDevicePath);

				pPropBag->Read(L"FriendlyName", &varFriendlyName, 0);
				pPropBag->Read(L"DevicePath", &varDevicePath, 0);

                CVideoCaptureDevice device;
                device.m_DevicePath = OLE2T(varDevicePath.bstrVal);
                device.m_FriendlyName = OLE2T(varFriendlyName.bstrVal);
                deviceList.push_back(device);

				VariantClear(&varFriendlyName);
				VariantClear(&varDevicePath);

				pPropBag->Release();
			}
			pMoniker->Release();
		}

		pEnumCat->Release();
	}

	pSysDevEnum->Release();

	return true;
}

bool CVideoCapture::getFilter(const char* strFilterName, IBaseFilter** ppVideoCaptureFilter)
{
	USES_CONVERSION;

    if( !ppVideoCaptureFilter)
    {
        return false;
    }
    *ppVideoCaptureFilter = NULL;

	HRESULT hr;
	ICreateDevEnum* pSysDevEnum = NULL;
	hr = CoCreateInstance(
		CLSID_SystemDeviceEnum, 
		NULL, 
		CLSCTX_INPROC_SERVER, 
		IID_ICreateDevEnum, 
		(void**)&pSysDevEnum);
	if( FAILED(hr))
	{
		return false;
	}

	// 枚举指定的目录类型
    bool bFound = false;
	IEnumMoniker *pEnumCat = NULL;
	hr = pSysDevEnum->CreateClassEnumerator(CLSID_VideoInputDeviceCategory, &pEnumCat, 0);
	if( hr == S_OK )
	{
		pEnumCat->Reset();

		IMoniker *pMoniker = NULL;
		ULONG cFetched = 0;

		while( pEnumCat->Next(1, &pMoniker, &cFetched) == S_OK && (!bFound))
		{
			IPropertyBag* pPropBag = NULL;
			hr = pMoniker->BindToStorage(0, 0, IID_IPropertyBag, (void**)&pPropBag);
			if( SUCCEEDED(hr) )
			{
				VARIANT varFriendlyName;
				VARIANT varDevicePath;
				VariantInit(&varFriendlyName);
				VariantInit(&varDevicePath);

				pPropBag->Read(L"FriendlyName", &varFriendlyName, 0);
				pPropBag->Read(L"DevicePath", &varDevicePath, 0);

                if( strFilterName == NULL ||
					strcmp(OLE2T(varFriendlyName.bstrVal), strFilterName) == 0)
                {
                    pMoniker->BindToObject(0,0, IID_IBaseFilter, (void**)ppVideoCaptureFilter);
                    bFound = true;

					if( strcmp(OLE2T(varFriendlyName.bstrVal), m_DVFilterName) == 0 )
					{
						this->m_IsDV = true;
					}
					else
					{
						this->m_IsDV = false;
					}
                }

				VariantClear(&varFriendlyName);
				VariantClear(&varDevicePath);

				pPropBag->Release();
			}
			pMoniker->Release();
		}

		pEnumCat->Release();
	}

	pSysDevEnum->Release();
	return bFound;

}

bool CVideoCapture::getDefaultFilter(IBaseFilter** ppVideoCaptureFilter)
{
    return CVideoCapture::getFilter(NULL, ppVideoCaptureFilter);
}

bool CVideoCapture::InitCaptureGraphBuilder(
        IGraphBuilder **ppGraph,
        ICaptureGraphBuilder2 **ppBuild)
{
    if( !ppGraph || !ppBuild )
    {
        return false;
    }

    IGraphBuilder *pGraph = NULL;
    ICaptureGraphBuilder2 *pBuild = NULL;

    HRESULT hr = CoCreateInstance(
            CLSID_CaptureGraphBuilder2,
            NULL,
            CLSCTX_INPROC_SERVER,
            IID_ICaptureGraphBuilder2,
            (void**)&pBuild);
    if( SUCCEEDED(hr))
    {
        // Create Manager
        hr = CoCreateInstance(
                CLSID_FilterGraph,
                0,
                CLSCTX_INPROC_SERVER,
                IID_IGraphBuilder, (void**)&pGraph);
        if( SUCCEEDED(hr))
        {
            pBuild->SetFiltergraph(pGraph);

            // Return
            *ppBuild = pBuild;
            *ppGraph = pGraph;
            return true;
        }
        else
        {
            pBuild->Release();
        }
    }

    return false;
}

bool CVideoCapture::SetFormat(ICaptureGraphBuilder2* pBuild, IBaseFilter* pVideoCaptureFilter)
{
    HRESULT hr;
    bool bRet = false;
    IAMStreamConfig *pConfig = NULL;

    if( !pBuild || !pVideoCaptureFilter)
    {
        return false;
    }

    hr = pBuild->FindInterface(
            &PIN_CATEGORY_CAPTURE,
            &MEDIATYPE_Video,
            pVideoCaptureFilter,
            IID_IAMStreamConfig,
            (void**)&pConfig);
    if( FAILED(hr))
    {
        goto end;
    }

	int iCount = 0;
	int iSize = 0;
	hr = pConfig->GetNumberOfCapabilities(&iCount, &iSize);
	if( FAILED(hr))
	{
		goto end;
	}

	int diffWidth = 5000;
	int diffHeight = 0;
	int userWidth = VideoConfig::getWidth();
	int userHeight = VideoConfig::getHeight();

    for(int iFormat = 0; iFormat < iCount; iFormat++)
    {
        VIDEO_STREAM_CONFIG_CAPS scc;
        AM_MEDIA_TYPE* pmtConfig;
        hr = pConfig->GetStreamCaps(iFormat, &pmtConfig, (BYTE*)&scc);
        if( SUCCEEDED(hr))
        {
#ifdef _DEBUG
			CString rString;
			GetFormattedGuid(rString, pmtConfig->majortype);
			TRACE1("%s\n", rString);
			GetFormattedGuid(rString, pmtConfig->subtype);
			TRACE1("%s\n", rString);
			GetFormattedGuid(rString, pmtConfig->formattype);
			TRACE1("%s\n", rString);
#endif

            if(!this->m_IsDV )
            {
                if( (pmtConfig->majortype == MEDIATYPE_Video) &&
                    //(pmtConfig->subtype == MEDIASUBTYPE_RGB24) &&
                    //(pmtConfig->formattype == FORMAT_VideoInfo) &&
                    (pmtConfig->cbFormat >= sizeof(VIDEOINFOHEADER)) &&
                    (pmtConfig->pbFormat != NULL))
                {
                    VIDEOINFOHEADER *pVih = (VIDEOINFOHEADER*)pmtConfig->pbFormat;

					TRACE3("width=%d, height=%d, frametime=%d\n", pVih->bmiHeader.biWidth, pVih->bmiHeader.biHeight, pVih->AvgTimePerFrame);

                    // QCIF 176*144
                    if(pVih->bmiHeader.biWidth == userWidth &&
                       pVih->bmiHeader.biHeight == userHeight )
                    {
                        int frametime = VideoConfig::TIME_UNIT / VideoConfig::framerate;
                        pVih->AvgTimePerFrame = frametime;
                        if( SUCCEEDED( pConfig->SetFormat(pmtConfig)))
                        {
							VideoConfig::setWidth(pVih->bmiHeader.biWidth);
							VideoConfig::setHeight(pVih->bmiHeader.biHeight);
							VideoConfig::setSize(VideoConfig::getWidth() * VideoConfig::getHeight() * VideoConfig::DEFAULT_BIT / 8);
							VideoConfig::setBitmapInfoHeader();
                            bRet = true;
                            DeleteMediaType(pmtConfig);
                            TRACE0("SetFormat for USB Camera OK\n");
                            break;
                        }
					}else{
						if(abs(pVih->bmiHeader.biWidth - userWidth) < diffWidth){
							diffWidth = abs(pVih->bmiHeader.biWidth - userWidth);
							int frametime = VideoConfig::TIME_UNIT / VideoConfig::framerate;
							pVih->AvgTimePerFrame = frametime;
							if( SUCCEEDED( pConfig->SetFormat(pmtConfig)))
							{
								VideoConfig::setWidth(pVih->bmiHeader.biWidth);
								VideoConfig::setHeight(pVih->bmiHeader.biHeight);
								VideoConfig::setSize(VideoConfig::getWidth() * VideoConfig::getHeight() * VideoConfig::DEFAULT_BIT / 8);
								VideoConfig::setBitmapInfoHeader();
								bRet = true;
								TRACE0("Temporarily SetFormat for USB Camera OK\n");
							}
						}
					}
				}
            }

            if(this->m_IsDV )
            {
                if( (pmtConfig->majortype == MEDIATYPE_Video) &&
                    //(pmtConfig->subtype == MEDIASUBTYPE_dvsd) &&
                    //(pmtConfig->formattype == FORMAT_VideoInfo) &&
                    (pmtConfig->cbFormat >= sizeof(VIDEOINFOHEADER)) &&
                    (pmtConfig->pbFormat != NULL))
                {
                    VIDEOINFOHEADER *pVih = (VIDEOINFOHEADER*)pmtConfig->pbFormat;
					TRACE1("%d\n", pVih->bmiHeader.biWidth);
                    // PAL 720*576
                    if(pVih->bmiHeader.biWidth == PAL_FORMAT::WIDTH &&
                       pVih->bmiHeader.biHeight == PAL_FORMAT::HEIGHT )
                    {
                        int frametime = VideoConfig::TIME_UNIT / VideoConfig::framerate;
                        pVih->AvgTimePerFrame = frametime;
                        if( SUCCEEDED( pConfig->SetFormat(pmtConfig)))
                        {
                            bRet = true;
                            DeleteMediaType(pmtConfig);
                            TRACE0("SetFormat for DV OK\n");
                            break;
                        }
                    }
                }
            }

            // Delete it
			DeleteMediaType(pmtConfig);
        }
    }

end:
    if(pConfig) pConfig->Release();
    return bRet;
}

