#include <windows.h>
#include <streams.h>
#include <initguid.h>

#define MYLIBAPI  extern "C" __declspec(dllexport)
#include "dumpuids.h"
#include "dump.h"
#include "..\VideoConfig\VideoConfig.h"

// If we clip large picture( for example, 1/4 PAL(720*576) to QCIF 176*144 )
// we will use this buffer
static const int MAX_BUFFER_SIZE = 720*576*3; 
unsigned char m_ClipData[MAX_BUFFER_SIZE];

// Setup data
const AMOVIESETUP_MEDIATYPE sudPinTypes =
{
    &MEDIATYPE_NULL,            // Major type
    &MEDIASUBTYPE_NULL          // Minor type
};

const AMOVIESETUP_PIN sudPins =
{
    L"Input",                   // Pin string name
    FALSE,                      // Is it rendered
    FALSE,                      // Is it an output
    FALSE,                      // Allowed none
    FALSE,                      // Likewise many
    &CLSID_NULL,                // Connects to filter
    L"Output",                  // Connects to pin
    1,                          // Number of types
    &sudPinTypes                // Pin information
};

const AMOVIESETUP_FILTER sudDump =
{
    &CLSID_Dump,                // Filter CLSID
    L"Dump",                    // String name
    MERIT_DO_NOT_USE,           // Filter merit
    1,                          // Number pins
    &sudPins                    // Pin details
};


//
//  Object creation stuff
//
CFactoryTemplate g_Templates[] =
{
    L"Dump", &CLSID_Dump, CDump::CreateInstance, NULL, &sudDump
};
int g_cTemplates = 1;


// Constructor

CDumpFilter::CDumpFilter(CDump *pDump,
                         LPUNKNOWN pUnk,
                         CCritSec *pLock,
                         HRESULT *phr) :
    CBaseFilter(NAME("CDumpFilter"), pUnk, pLock, CLSID_Dump),
    m_pDump(pDump)
{
}


//
// GetPin
//
CBasePin * CDumpFilter::GetPin(int n)
{
    if (n == 0) {
        return m_pDump->m_pPin;
    } else {
        return NULL;
    }
}


//
// GetPinCount
//
int CDumpFilter::GetPinCount()
{
    return 1;
}


//
// Stop
//
// Overriden to close the dump file
//
STDMETHODIMP CDumpFilter::Stop()
{
    CAutoLock cObjectLock(m_pLock);
    return CBaseFilter::Stop();
}


//
// Pause
//
// Overriden to open the dump file
//
STDMETHODIMP CDumpFilter::Pause()
{
    CAutoLock cObjectLock(m_pLock);
    return CBaseFilter::Pause();
}


//
// Run
//
// Overriden to open the dump file
//
STDMETHODIMP CDumpFilter::Run(REFERENCE_TIME tStart)
{
    CAutoLock cObjectLock(m_pLock);
    return CBaseFilter::Run(tStart);
}


//
//  Definition of CDumpInputPin
//
CDumpInputPin::CDumpInputPin(CDump *pDump,
                             LPUNKNOWN pUnk,
                             CBaseFilter *pFilter,
                             CCritSec *pLock,
                             CCritSec *pReceiveLock,
                             HRESULT *phr) :

    CRenderedInputPin(NAME("CDumpInputPin"),
                  pFilter,                   // Filter
                  pLock,                     // Locking
                  phr,                       // Return code
                  L"Input"),                 // Pin name
    m_pReceiveLock(pReceiveLock),
    m_pDump(pDump),
    m_tLast(0)
{
}


//
// CheckMediaType
//
// Check if the pin can support this specific proposed type and format
//
HRESULT CDumpInputPin::CheckMediaType(const CMediaType* pMediaType)
{
	// We only support the Video RGB24 format
	if( *(pMediaType->Type()) == MEDIATYPE_Video &&
		*(pMediaType->Subtype()) == MEDIASUBTYPE_RGB24 &&
		*(pMediaType->FormatType()) == FORMAT_VideoInfo &&
		pMediaType->IsFixedSize())
	{
		return S_OK;
	}
	else
	{
		return S_FALSE;
	}
}


//
// BreakConnect
//
// Break a connection
//
HRESULT CDumpInputPin::BreakConnect()
{
    return CRenderedInputPin::BreakConnect();
}


//
// ReceiveCanBlock
//
// We don't hold up source threads on Receive
//
STDMETHODIMP CDumpInputPin::ReceiveCanBlock()
{
    return S_FALSE;
}


//
// Receive
//
// Do something with this media sample
//
STDMETHODIMP CDumpInputPin::Receive(IMediaSample *pSample)
{
	ASSERT(m_pDump->m_pCallback != 0);

	// Lock
    CAutoLock lock(m_pReceiveLock);

	// Get Data
	PBYTE pbData;
    HRESULT hr = pSample->GetPointer(&pbData);
    if (FAILED(hr)) {
        return hr;
    }
	// If necessary, we clip data and send it
    if( m_pDump->m_IsClip )
    {
		int nDVWidth, nDVHeight;
		if (m_pDump->m_ClipWidth > 352)
		{
			nDVWidth = 720;
			nDVHeight = 576;
		}
		else if (m_pDump->m_ClipWidth > 176)
		{
			nDVWidth = 360;
			nDVHeight = 288;
		}
		else
		{
			nDVWidth = 180;
			nDVHeight = 144;
		}
        if( pSample->GetActualDataLength() < nDVWidth*nDVHeight*3 )
        {
            ASSERT(FALSE);
            return S_OK;
        }
		int nHeight = ((m_pDump->m_ClipHeight > nDVHeight) ? nDVHeight : m_pDump->m_ClipHeight);
		int nWidth = ((m_pDump->m_ClipWidth > nDVWidth) ? nDVWidth : m_pDump->m_ClipWidth);

		for( int i = 0; i < nHeight; i++ )
        {
			memcpy( m_ClipData + m_pDump->m_ClipWidth*3*i,
                    pbData + nDVWidth*3*i,
                    nWidth*3);
        }

        (*(m_pDump->m_pCallback))(m_pDump->m_pCallbackParameter, m_ClipData, m_pDump->m_ClipWidth*m_pDump->m_ClipHeight*3);
    }
    else
    {
        (*(m_pDump->m_pCallback))(m_pDump->m_pCallbackParameter, pbData, pSample->GetActualDataLength());
    }

	return S_OK;
}

//
// EndOfStream
//
STDMETHODIMP CDumpInputPin::EndOfStream(void)
{
    CAutoLock lock(m_pReceiveLock);
    return CRenderedInputPin::EndOfStream();

} // EndOfStream


//
// NewSegment
//
// Called when we are seeked
//
STDMETHODIMP CDumpInputPin::NewSegment(REFERENCE_TIME tStart,
                                       REFERENCE_TIME tStop,
                                       double dRate)
{
    m_tLast = 0;
    return S_OK;

} // NewSegment


//
//  CDump class
//
CDump::CDump(LPUNKNOWN pUnk, HRESULT *phr) :
    CUnknown(NAME("CDump"), pUnk),
    m_pFilter(NULL),
    m_pPin(NULL),
	m_pCallback(0),
	m_pCallbackParameter(0),
	m_IsClip(false),
    m_pFileName(0)
{
    ASSERT(phr);
    
    m_pFilter = new CDumpFilter(this, GetOwner(), &m_Lock, phr);
    if (m_pFilter == NULL) {
        if (phr)
            *phr = E_OUTOFMEMORY;
        return;
    }

    m_pPin = new CDumpInputPin(this,GetOwner(),
                               m_pFilter,
                               &m_Lock,
                               &m_ReceiveLock,
                               phr);
    if (m_pPin == NULL) {
        if (phr)
            *phr = E_OUTOFMEMORY;
        return;
    }
}


//
// SetFileName
//
// Implemented for IFileSinkFilter support
//
STDMETHODIMP CDump::SetFileName(LPCOLESTR pszFileName,const AM_MEDIA_TYPE *pmt)
{
    // Is this a valid filename supplied
    CheckPointer(pszFileName,E_POINTER);
    if(wcslen(pszFileName) > MAX_PATH)
        return ERROR_FILENAME_EXCED_RANGE;

    // Take a copy of the filename
    m_pFileName = new WCHAR[1+lstrlenW(pszFileName)];
    if (m_pFileName == 0)
        return E_OUTOFMEMORY;

    lstrcpyW(m_pFileName,pszFileName);

    return S_OK;
} // SetFileName


//
// GetCurFile
//
// Implemented for IFileSinkFilter support
//
STDMETHODIMP CDump::GetCurFile(LPOLESTR * ppszFileName,AM_MEDIA_TYPE *pmt)
{
    CheckPointer(ppszFileName, E_POINTER);
    *ppszFileName = NULL;

    if (m_pFileName != NULL) 
    {
        *ppszFileName = (LPOLESTR)
        QzTaskMemAlloc(sizeof(WCHAR) * (1+lstrlenW(m_pFileName)));

        if (*ppszFileName != NULL) 
        {
            lstrcpyW(*ppszFileName, m_pFileName);
        }
    }

    if(pmt) 
    {
        ZeroMemory(pmt, sizeof(*pmt));
        pmt->majortype = MEDIATYPE_NULL;
        pmt->subtype = MEDIASUBTYPE_NULL;
    }

    return S_OK;

} // GetCurFile


//
// SetCallback
//
STDMETHODIMP CDump::SetCallback(VIDEOCAPTURE_CALLBACK pCallback, void* pCallbackParameter)
{
	ASSERT(pCallback != NULL);
	if( pCallback == NULL )
	{
		return S_FALSE;
	}

	this->m_pCallback = pCallback;
	this->m_pCallbackParameter = pCallbackParameter;
	return S_OK;

}

//
// SetClip
//
STDMETHODIMP CDump::SetClip(unsigned int width, unsigned int height)
{
	if( width < VideoConfig::DEFAULT_WIDTH ||
		height < VideoConfig::DEFAULT_HEIGHT)
	{
		return S_FALSE;
	}

	this->m_IsClip = true;
	this->m_ClipWidth = width;
	this->m_ClipHeight = height;

	return S_OK;
}

// Destructor

CDump::~CDump()
{
    delete m_pPin;
    delete m_pFilter;
    delete m_pFileName;
}


//
// CreateInstance
//
// Provide the way for COM to create a dump filter
//
CUnknown * WINAPI CDump::CreateInstance(LPUNKNOWN punk, HRESULT *phr)
{
    ASSERT(phr);

    CDump *pNewObject = new CDump(punk, phr);
    if (pNewObject == NULL) {
        if (phr)
            *phr = E_OUTOFMEMORY;
    }

    return pNewObject;

} // CreateInstance


//
// NonDelegatingQueryInterface
//
// Override this to say what interfaces we support where
//
STDMETHODIMP CDump::NonDelegatingQueryInterface(REFIID riid, void ** ppv)
{
    CheckPointer(ppv,E_POINTER);
    CAutoLock lock(&m_Lock);

    // Do we have this interface

    if (riid == IID_IFileSinkFilter) {
        return GetInterface((IFileSinkFilter *) this, ppv);
    } 
    else if (riid == IID_IBaseFilter || riid == IID_IMediaFilter || riid == IID_IPersist) {
        return m_pFilter->NonDelegatingQueryInterface(riid, ppv);
    } 
    else if (riid == IID_ISetVideoCaptureCallback) {
        return GetInterface((ISetVideoCaptureCallback*) this, ppv);
    } 

    return CUnknown::NonDelegatingQueryInterface(riid, ppv);

} // NonDelegatingQueryInterface


////////////////////////////////////////////////////////////////////////
//
// Exported entry points for registration and unregistration 
// (in this case they only call through to default implementations).
//
////////////////////////////////////////////////////////////////////////

//
// DllRegisterSever
//
// Handle the registration of this filter
//
STDAPI DllRegisterServer()
{
    return AMovieDllRegisterServer2( TRUE );

} // DllRegisterServer


//
// DllUnregisterServer
//
STDAPI DllUnregisterServer()
{
    return AMovieDllRegisterServer2( FALSE );

} // DllUnregisterServer


//
// DllEntryPoint
//
extern "C" BOOL WINAPI DllEntryPoint(HINSTANCE, ULONG, LPVOID);

BOOL APIENTRY DllMain(HANDLE hModule, 
                      DWORD  dwReason, 
                      LPVOID lpReserved)
{
	return DllEntryPoint((HINSTANCE)(hModule), dwReason, lpReserved);
}
