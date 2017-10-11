//------------------------------------------------------------------------------
// File: iir.h
//------------------------------------------------------------------------------

typedef struct TransformFlags
{
	BOOL	bBlacken;
	BOOL	bCrop;
	BOOL	bResize;
} TRFLAGS;

#define MST_YUY2						0
#define MST_UYVY						1
#define MST_RGB32						2
#define MST_RGB24						3
#define MST_YV12						4
#define MST_AUTO						5

#ifndef __IIR__
#define __IIR__

#ifdef __cplusplus
extern "C" {
#endif
	
	DEFINE_GUID(CLSID_IImRes,
		0xA42A91C1, 0xaadb, 0x11d4, 0xae, 0x90, 0xcd, 0xac, 0x2a, 0x7c, 0x25, 0x67);

	DEFINE_GUID(IID_IImRes,
		0x81cacc01, 0xaade, 0x11d4, 0xae, 0x90, 0xcd, 0xac, 0x2a, 0x7c, 0x25, 0x67);

	DECLARE_INTERFACE_(IImRes, IUnknown)
	{
		STDMETHOD(get_CropRect) (THIS_
		RECT *CropRect,
			BOOL *State
			) PURE;

		STDMETHOD(get_AvailTypes) (THIS_
		BYTE *AvailableTypes
			) PURE;
		
		STDMETHOD(put_CropRect) (THIS_
			RECT CropRect,
			BOOL State
			) PURE;
		STDMETHOD(get_MSubType) (THIS_
			int *msubtype					// See higher defines
			) PURE;
		
		STDMETHOD(put_MSubType) (THIS_
			int msubtype
			) PURE;
		
		STDMETHOD(get_SourceSize) (THIS_
			POINT *SourceSize
			) PURE;
		
		STDMETHOD(get_Blacken) (THIS_
			int *Margin,
			BOOL *State
			) PURE;
		
		STDMETHOD(put_Blacken) (THIS_
			int Margin,
			BOOL State
			) PURE;
		
		STDMETHOD(get_Resize) (THIS_
			POINT *Dimensions,
			int *ResType,
			BOOL *State
			) PURE;
		
		STDMETHOD(put_Resize) (THIS_
			POINT Dimensions,
			int ResType,
			BOOL State
			) PURE;
		
		STDMETHOD(get_GraphState) (THIS_
			BOOL *isInputConnected,
			BOOL *isOutputConnected,
			_FilterState *State
			) PURE;
		
};
	
#ifdef __cplusplus
}
#endif

#endif // __IIR__

