///////////////////////////////////////////
// Define Interface of the VideoCapture Dll
///////////////////////////////////////////

#ifndef MYLIBAPI
#define MYLIBAPI extern "C" __declspec(dllimport)
#endif
#undef lstrcpyW
// {390976F4-5B8E-4df3-B97D-0DB3D288DE52}
DEFINE_GUID(CLSID_Dump, 
0x390976f4, 0x5b8e, 0x4df3, 0xb9, 0x7d, 0xd, 0xb3, 0xd2, 0x88, 0xde, 0x52);

// {1035BDDC-C3A7-4006-9D7E-68252936E246}
DEFINE_GUID(IID_ISetVideoCaptureCallback, 
0x1035bddc, 0xc3a7, 0x4006, 0x9d, 0x7e, 0x68, 0x25, 0x29, 0x36, 0xe2, 0x46);

// Callback function prototype
extern "C" typedef void (__stdcall *VIDEOCAPTURE_CALLBACK) (void* pObject, unsigned char* pData, int nLength);

struct __declspec(novtable)
ISetVideoCaptureCallback : public IUnknown
{
public:
    virtual HRESULT STDMETHODCALLTYPE SetCallback( 
        /* [in] */ VIDEOCAPTURE_CALLBACK pCallback,
        /* [in] */ void* pCallbackParameter) = 0;

	virtual HRESULT STDMETHODCALLTYPE SetClip(
        /* [in] */ unsigned int width,
        /* [in] */ unsigned int height) = 0;
};

