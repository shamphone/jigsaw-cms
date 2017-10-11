#ifndef _TKL_SDK_H_
#define _TKL_SDK_H_

#ifdef _USRDLL
#define TKLSDK_API  __declspec(dllexport)
#else
#define TKLSDK_API  __declspec(dllimport)
#endif

#define IN
#define OUT

#ifdef __cplusplus
extern "C"{
#endif

typedef enum _TKLSDK_CHANNEL_TYPE
{    	
	AVEDEFAULT = 0,
	AVE1000    =1,
	AVE2000    =2,
	AVE3000    =3,
	AVE4000    =4,
	AVE5000    =5,
	AVE6000    =6,
	AVE7000    =7,
	AVE8000    =8,
	AVE9000    =9,
	AVE9200    =10,
	AVE6800    =11   
}TKLSDK_CHANNEL_TYPE, *PTKLSDK_CHANNEL_TYPE; 

TKLSDK_API	BOOL	__stdcall	
TKLSDK_OpenChannel(
	IN		int			nChannelNo,
	IN	OUT int		 	*pChannelType,		// TKLSDK_CHANNEL_TYPE
	OUT		long		*plHandle
	);

TKLSDK_API	BOOL	__stdcall
TKLSDK_CloseChannel(
	IN	long 		lHandle
);

/****  Preview functions *****/
typedef enum _TKLSDK_PREVIEW_MODE
{
	TKLSDK_PREVIEW_UNKNOWN=0,
	TKLSDK_PREVIEW_PRIMARY,
	TKLSDK_PREVIEW_OVERLAY
}TKLSDK_PREVIEW_MODE, *PTKLSDK_PREVIEW_MODE;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_InitPreview(
	IN OUT int *pMode,	//TKLSDK_PREVIEW_MODE
	IN BOOL bSingleField=true
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetPreviewWindow(
	IN	long	 		lHandle, 
	IN	HWND			hWnd,
	IN	RECT 		 	rect,
	IN	BOOL 			bEnable
	);

TKLSDK_API	 BOOL	__stdcall
TKLSDK_ReleasePreview();

TKLSDK_API	 BOOL 	__stdcall
TKLSDK_UpdatePreview();

TKLSDK_API	 BOOL 	__stdcall
TKLSDK_SetPreviewClip(
	IN	long		lHandle,
	IN	int			nClipNum,
	IN	RECT		rect
);

/****  Compression functions *****/
TKLSDK_API	BOOL	__stdcall
TKLSDK_RegisterDataRcvWindow(
	IN	long 		lHandle,
	IN	HWND 		hWnd, 
	IN	UINT		Msg
	);

TKLSDK_API  BOOL 	__stdcall
TKLSDK_RegisterDataRcvEvent(
	IN	long 		lHandle,
	IN	HANDLE 		hEvent
	);

typedef enum _TKLSDK_FRAME_TYPE
{
	TKLSDK_FRAME_UNKNOWN = -1,
	TKLSDK_FRAME_TYPE_I	=	0,
	TKLSDK_FRAME_TYPE_P,
	TKLSDK_FRAME_TYPE_B
}TKLSDK_FRAME_TYPE, *PTKLSDK_FRAME_TYPE;

typedef struct _TKLSDK_FRAME_INFO
{
	unsigned int     nFrameType;	// TKLSDK_FRAME_TYPE
	unsigned int     nFrameNum;
	unsigned int     reserved[2];
} TKLSDK_FRAME_INFO,*PTKLSDK_FRAME_INFO;

typedef void  (__stdcall * COMPRESSCALLBACK)(
	long 			lHandle,
	PTKLSDK_FRAME_INFO 	pFrameInfo,
	PUCHAR 			pBuf,
	int 			nLength,
	void  			*pUsrParams
 );

TKLSDK_API	BOOL 	__stdcall
TKLSDK_RegisterDataRcvCallback(
	IN	long 				lHandle,
	IN	COMPRESSCALLBACK	CompressCallBack, 
	IN	void				*pParams
);

typedef enum _TKLSDK_RATE_MODE
{
	TKLSDK_RATEMODE_VBR = 0,
	TKLSDK_RATEMODE_CBR
}TKLSDK_RATE_MODE, *PTKLSDK_RATE_MODE; 

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetRateMode(
	IN	long 	lHandle, 
	IN	int 	nRateMode	// TKLSDK_RATE_MODE
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoDataRate(
	IN	long 	lHandle, 
	IN	ULONG 	ulVideoDataRate
	);

typedef enum _TKLSDK_AUDIO_DATARATE
{
	TKLSDK_ARATE_8=8,
	TKLSDK_ARATE_16=16,
	TKLSDK_ARATE_24=24,
	TKLSDK_ARATE_32=32,
	TKLSDK_ARATE_40=40,
	TKLSDK_ARATE_48=48,
	TKLSDK_ARATE_56=56,
	TKLSDK_ARATE_64=64,
	TKLSDK_ARATE_80=80,
	TKLSDK_ARATE_96=96,
	TKLSDK_ARATE_112=112,
	TKLSDK_ARATE_128=128,
	TKLSDK_ARATE_160=160,
	TKLSDK_ARATE_192=192,
	TKLSDK_ARATE_224=224,
	TKLSDK_ARATE_256=256,
	TKLSDK_ARATE_320=320,
	TKLSDK_ARATE_384=384
}TKLSDK_AUDIO_DATARATE, *PTKLSDK_AUDIO_DATARATE;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetAudioDataRate(
	IN	long		lHandle, 
	IN	ULONG 		ulAudioDataRate //TKLSDK_AUDIO_DATARATE
	);

typedef enum _TKLSDK_STREAM_TYPE
{
	TKLSDK_STREAM_MULTIP	=	0,
	TKLSDK_STREAM_VIDEO,
	TKLSDK_STREAM_AUDIO
}TKLSDK_STREAM_TYPE, *PTKLSDK_STREAM_TYPE;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetStreamType(
	IN	long		lHandle, 
	IN	ULONG 		ulStreamType		// TKLSDK_STREAM_TYPE
	);

typedef enum _TKLSDK_COMPRESSION_STANDARD
{
	TKLSDK_COMPRESSION_STANDARD_MPEG1	=	0,
	TKLSDK_COMPRESSION_STANDARD_MPEG2,
	TKLSDK_COMPRESSION_STANDARD_MPEG4,
	TKLSDK_COMPRESSION_STANDARD_MJPEG,
	TKLSDK_COMPRESSION_STANDARD_H263,
	TKLSDK_COMPRESSION_STANDARD_H264
	
}TKLSDK_COMPRESSION_STANDARD, *PTKLSDK_COMPRESSION_STANDARD;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetCompressionStandard(
	IN	long		lHandle, 
	IN	ULONG 		ulCompressionMode		//TKLSDK_COMPRESSION_STANDARD
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetCompressionSize(
	IN	long		lHandle, 
	IN	int 		nWidth,
	IN	int 		nHeight
	);

TKLSDK_API	BOOL	__stdcall
TKLSDK_StartCompress(
	IN	long lHandle
	);

TKLSDK_API	BOOL	__stdcall
TKLSDK_StopCompress(
	IN	long lHandle
	);
	
TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetFileHeader(
	IN		long 		lHandle,
	IN	OUT	PUCHAR 		pUserBuf,
	OUT 	PUCHAR		*ppSDKBuf,
	IN	OUT	long  		*pBufLen
);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetData(
	IN		long 		lHandle, 
	IN	OUT	PUCHAR 		pUserBuf,
	OUT		PUCHAR		*ppSDKBuf,
	IN OUT	long 		*pBufLen
	);

//compression auxiliaries function
TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetKeyFramePeriod(
	IN	long 		lHandle,
	IN	int	 		nKeyFramePeriod
	);
#define  TKLSDK_FAST_FRAMERATE_SET 0x01000000
TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetFrameRate(
	IN	long 		lHandle,
	IN	int			nFrameRate
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_InsertKeyFrame(
	IN	long lHandle
	);


TKLSDK_API	BOOL	__stdcall
TKLSDK_CheckFrameType(
	IN	long	 	lHandle,
	IN	PUCHAR 		pFrameBuf,
	IN	long		*pFrameLength,
	OUT	int			*pFameType //TKLSDK_FRAME_TYPE
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetCompressQuality(
	IN	long lHandle,
	IN  int  nQI,
	IN  int  nQP,
	IN  int  nQB
	);

typedef enum _TKLSDK_FRAME_SEQUENCE_MODE
{
	TKLSDK_FRAME_SEQUENCE_MODE_III=1,
	TKLSDK_FRAME_SEQUENCE_MODE_IPP,
	TKLSDK_FRAME_SEQUENCE_MODE_IBBP	
	
}TKLSDK_FRAME_SEQUENCE_MODE, *PTKLSDK_FRAME_SEQUENCE_MODE;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetFrameSequenceMode(
	IN	long lHandle,
	IN  int  nFrameSequenceMode	// TKLSDK_FRAME_SEQUENCE_MODE
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_EnableWatermark(
	IN	long 		lHandle, 
	IN	BOOL 		bEnable
	);


TKLSDK_API	BOOL 	__stdcall
TKLSDK_ProcessStream(
	IN		long 		lHandle,
	IN	OUT PUCHAR 		pBuf1,
	IN	OUT	long 		*pBufLen1,
	OUT 	PUCHAR 		pBuf2,
	OUT		long 		*pBufLen2
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_BreakStream(
	IN	long lHandle
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_PauseStream(
	IN	long lHandle
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_ResumeStream(
	IN	long lHandle
	);

//*****Motion detection functions*********
TKLSDK_API	BOOL		__stdcall
TKLSDK_EnableMotionDetect(
	IN	long lHandle,
	IN  BOOL bEnable
	);

TKLSDK_API	BOOL		__stdcall
TKLSDK_GetLastMotionResult(
	IN 	long 		lHandle,
	OUT int*		pMotResult
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetDetectParams(
	IN	long 		lHandle,
	IN	int			nAreaNum,
	IN	RECT		rectArea,
	IN	int			nThreshold,
	IN  int         nNoiseThreshold
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetDetectInterval(
	IN	long 		lHandle, 
	OUT	int 		nInterval
	);
	
typedef union _TKLSDK_USER_MOTION_INFO
{
	struct 
	{
		short x;
		short y;
	}MOTION_VECTOR;

	int n;
}TKLSDK_USER_MOTION_INFO, *PTKLSDK_USER_MOTION_INFO;

typedef struct _TKLSDK_USER_MOTION_MAP 
{  
	TKLSDK_USER_MOTION_INFO  	MotionInfo[36][44];
} TKLSDK_USER_MOTION_MAP, *PTKLSDK_USER_MOTION_MAP;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetMotionMap(
	IN	long 		lHandle, 
	OUT	PTKLSDK_USER_MOTION_MAP pMotionMap
);

//*****OSD and mask functions********
typedef enum _TKLSDK_OSD_MODE
{
	TKLSDK_OSD_YY_MM_DD=1,
	TKLSDK_OSD_YY_DD_MM,
	TKLSDK_OSD_DD_MM_YY,
	TKLSDK_OSD_MMDD_YY,
	TKLSDK_OSD_YY_MMDD,
	TKLSDK_OSD_MM_DD_YY,
	TKLSDK_OSD_MM_DD_YYYY	
}TKLSDK_OSD_MODE, *PTKLSDK_OSD_MODE;

//#define TKLSDK_OSD_BMP	0x80000000
#define TKLSDK_OSD_OWN	0x10000000
#define TKLSDK_OSD_LEFT	0x20000000
#define TKLSDK_OSD_TOP	0x40000000

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetOSD(
	IN	long 			lHandle, 
	IN	int 			nMode, //TKLSDK_OSD_xx
	IN	char 			cSpaceChar,
	IN	const char		*pName,
	IN	PUCHAR			pBitmap,
	IN	BOOL			bEnable
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetOSDEx(
	IN	long 		lHandle, 
	IN	int			nMode, //TKLSDK_OSD_LEFT, TKLSDK_OSD_TOP
	IN	const char 	*pLines,
	IN	PUCHAR   	pBitmap,
	IN	BOOL		bEnable,
	IN  DWORD		dwFrontColor = 0xFFFFFFFF,	// white
	IN  DWORD		dwBorderColor = 0x00000000	// black
);

TKLSDK_API	BOOL	__stdcall
TKLSDK_SetMaskWindow(
	IN	long 	lHandle,
	IN	RECT	rect,
	IN	BOOL	bEnable, 
	IN	DWORD	dwMaskColor = 0x00000000	// black
);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetLogo(
	IN	long 		lHandle,
	IN	POINT		dstStartPoint,
	IN	PUCHAR 		pBitmap,
	IN	BOOL		bEnable,
	IN   DWORD		dwColorKey = 0x00000000,	// black 
	IN   DWORD		dwTransparency = 0		// No  dwTransparency
);

//******Analog video functions************
TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoBrightness(
	IN	long 		lHandle, 
	IN	int			nBrightness
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoContrast(
	IN	long 		lHandle, 
	IN	int			nContrast
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoSaturation(
	IN	long 		lHandle, 
	IN	int			nSaturation
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoHue(
	IN	long 		lHandle, 
	IN	int			nHue
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoOffset(
	IN	long 		lHandle, 
	IN	int			nHorizonal,
	IN	int			nVertical
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetVideoBrightness(
	IN	long 		lHandle, 
	OUT	int			*pBrightness
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetVideoContrast(
	IN	long 		lHandle, 
	OUT	int			*pContrast
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetVideoSaturation(
	IN	long 		lHandle, 
	OUT	int			*pSaturation
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetVideoHue(
	IN	long 		lHandle, 
	OUT	int			*pHue
	);


typedef enum _TKLSDK_VIDEO_SOURCE
{
	TKLSDK_VIDEO_SRC_CVBS=0,
	TKLSDK_VIDEO_SRC_SVIDEO
}TKLSDK_VIDEO_SOURCE, *PTKLSDK_VIDEO_SOURCE;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_SetVideoSource(
	IN	long		lHandle, 
	IN	int			nSource		// TKLSDK_VIDEO_SOURCE
	);

typedef enum _TKLSDK_VIDEO_FORMATS
{
	TKLSDK_VIDEO_FORMAT_NONE=0,
	TKLSDK_VIDEO_FORMAT_PAL,
	TKLSDK_VIDEO_FORMAT_NTSC
}TKLSDK_VIDEO_FORMATS, *PTKLSDK_VIDEO_FORMATS;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetVideoFormat(
	IN	long		lHandle, 
	OUT	int 		*pFormat		// TKLSDK_VIDEO_FORMATS
	);


//******Analog audio functions**********
TKLSDK_API	BOOL	__stdcall
TKLSDK_SetVolume(
	IN	long		lHandle, 
	IN	int			nVolume
	);

typedef enum _TKLSDK_AUDIO_SOURCE
{
	TKLSDK_AUDIO_SRC_LINE=0,
	TKLSDK_AUDIO_SRC_MIC
}TKLSDK_AUDIO_SOURCE, *PTKLSDK_AUDIO_SOURCE;


TKLSDK_API	BOOL	__stdcall
TKLSDK_SetAudioSource(
	IN	long 		lHandle, 
	IN	int 		nSource		// TKLSDK_AUDIO_SOURCE
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_EnableAudioMonitor(
	IN	long		lHandle, 
	IN	BOOL 		bEnable
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_EnableRawAudioDectect(
	IN	long		lHandle, 
	IN	BOOL 		bEnable
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetRawAudioDetectReport(
	IN	long		lHandle, 
	OUT	int 		*pResult
	);

typedef void (__stdcall * PAUDIOCALLBACK)(
	UINT lHandle,
	BYTE * pBuf,
	int nLength,
	void * pUsrParam
	);


typedef struct _AUDIOPARAMS{
	BOOL      		bEnableCallback;//true:enable callback;false:disable callback,then both pUserCallBackParam and pAudioCallback should be set to NULL.
	void          	*pUserCallBackParam;//callback params
	PAUDIOCALLBACK	pAudioCallback;//callback function adress
	int            	Frequency;//11 or 11025;22 or 22050;44 or 44100
	int             nSampleBits;//8 or 16:sample bit rate
	BOOL            bEnableRecord;//true: record;false:no record.
    char          * FileName;//recording file name
} TKLSDK_AUDIOPARAMS, *TKLSDK_PAUDIOPARAMS;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_StartRawAudioCapture(
	IN	long				lHandle, 
	OUT	TKLSDK_PAUDIOPARAMS 	pParams
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_StopRawAudioCapture(
	IN	long 		lHandle
	);

//*******other***********
typedef enum _TKLSDK_ALM_STATUS
{
	TKLSDK_ALM_STATUS_OPEN=0,
	TKLSDK_ALM_STATUS_SHORT,
	TKLSDK_ALM_STATUS_NORMAL
}TKLSDK_ALM_STATUS, *PTKLSDK_ALM_STATUS;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetAlarmStatus(
	IN	long 		lHandle,
	OUT	ULONG		*pStatus	// TKLSDK_ALM_STATUS
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_EnableAlarmOutput(
	IN	long		lHandle, 
	IN	BOOL 		bEnable
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_WriteUserInfo(
	IN	long 		lHandle, 
	IN	int			nOffset,
	IN	int			nLength,
	IN	PUCHAR 		pBuf
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_ReadUserInfo (
	IN	long 		lHandle, 
	IN	int			nOffset,
	IN	int			nLength,
	OUT	PUCHAR 		pBuf
	);

#define TKLSDK_CHANNEL_COMPRESS 			0x80000000
#define TKLSDK_CHANNEL_VOLUME_ADJ			0x40000000
#define TKLSDK_CHANNEL_VIDSRC_SELECT		0x20000000
#define TKLSDK_CHANNEL_AUDSRC_SELECT		0x10000000
#define TKLSDK_CHANNEL_ALARM_INTERFACE	    0x08000000
#define TKLSDK_CHANNEL_OSD				    0x04000000
#define TKLSDK_CHANNEL_FRAME_RATE_ADJ		0x02000000

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetChannelInfo(
	IN	long 		lHandle, 
	OUT	ULONG 		*pSerialNo,
	OUT	ULONG 		*pFlags
	);

typedef union _TKLSDK_STATISTICS
{
	ULONG reserved[8];
	struct
	{
		ULONG ulPacketLost;
		ULONG ulOutOfSync;
		ULONG ulVideoPacket;
		ULONG ulAudioPacket;
		ULONG ulStuffPacket;
		ULONG ulUnknownPacket;
	}AVE2K_STATISTICS;
	
	struct
	{	// todo...
		ULONG ulPacketLost;
	}AVE6K_STATISTICS;
	
	struct{
		ULONG ulFrameLost;
	}TKLSDK_STATISTICS;
}TKLSDK_STATISTICS, *PTKLSDK_STATISTICS;

TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetStatistics(
	IN	long	lHandle, 
	OUT	PTKLSDK_STATISTICS 	pStatistics
	);


TKLSDK_API	BOOL		__stdcall
TKLSDK_GetLastError(
	IN	long		lHandle,
	OUT	ULONG 		*pError
);

typedef enum _TKLSDK_BITMAP_BIT
{
	TKLSDK_BITMAP_RGB24=0,
	TKLSDK_BITMAP_RGB32,
	TKLSDK_BITMAP_YUYV=2,
	TKLSDK_BITMAP_Y8
}TKLSDK_BITMAP_BIT, *PTKLSDK_BITMAP_BIT;

typedef struct _TKLSDK_YUV_BITMAP_HEADER
{
	ULONG ulFlag;//"YUYV" or "Y8"
	ULONG ulWidth;
	ULONG ulHeight;
	ULONG Reserved;

}TKLSDK_YUV_BITMAP_HEADER, *PTKLSDK_YUV_BITMAP_HEADER;


TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetBitmapSize(
	IN	int 		nWidth,
	IN	int 		nHeight,
	IN	int 		nFormat,
	OUT	int			*pBitmapSize
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_CaptureBitmap(
	IN	long	lHandle, 
	IN	int		nWidth,
	IN	int 	nHeight,
	IN	int 	nFormat,
	IN	OUT	PUCHAR 	pUserBuf,
	OUT	PUCHAR 	*ppSDKBuf,
	OUT	int		*pBufSize
	);


TKLSDK_API	BOOL 	__stdcall
TKLSDK_WritePort(
	IN	long		lHandle, 
	IN	ULONG 		ulPort, 
	IN	UCHAR 		nByte
	);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_ReadPort(
	IN	long 		lHandle, 
	IN	ULONG 		ulPort,
	OUT	PUCHAR		pByte
	);
TKLSDK_API	BOOL 	__stdcall
TKLSDK_CorrectTime(
	 IN long lHandle,
	 IN OUT PUCHAR pBuf, 
	 IN long nBufLen);

TKLSDK_API	BOOL 	__stdcall
TKLSDK_ResetTime(
	 IN long lHandle
     );


TKLSDK_API	BOOL 	__stdcall
TKLSDK_GetTotalChannelNumber(
	 IN int nChannelType = AVEDEFAULT,
	 OUT int * pChannelNumber=NULL );


TKLSDK_API	BOOL 	__stdcall 
TKLSDK_ActivateDynParams(
				 IN long lHandle
				 );


TKLSDK_API	BOOL 	__stdcall 
TKLSDK_SaveDbgMsg(
				 IN long lHandle,
				 IN char * msg
				 );
enum ErrorCodes{
	ERR_NONE=0,
	ERR_WAIT_TIMEOUT,
	ERR_INVALID_HANDLE, 
	ERR_INVALID_ARGUMENT,
	ERR_DDRAW_CREATE_FAILED,
	ERR_DDRAW_CAPS_FAULT, 
	ERR_SET_COOPERATIVELEVEL_FAILED, 
	ERR_PRIMARY_SURFACE_CREATE_FAILED,
	ERR_GET_OVERLAY_ADDRESS_FAILED, 
	ERR_OVERLAY_SURFACE_CREATE_FAILED, 
	ERR_OVERLAY_UPDATE_FAILED, 
	ERR_TMMAN_FAILURE, 
	ERR_CHANNELMAGIC_MISMATCH,
	ERR_QUEUE_OVERFLOW,
	ERR_STREAM_THREAD_FAILURE,
	ERR_THREAD_STOP_ERROR, 
	ERR_NOT_SUPPORT, 
	ERR_OUTOF_MEMORY, 
	ERR_DSP_BUSY, 
	ERR_DATA_ERROR 

};
//Functions below is not implemented so far.
TKLSDK_API	BOOL 	__stdcall TKLSDK_LockChannel(
				   IN long lHandle,
				   IN ULONG ulKey
				   );
TKLSDK_API	BOOL 	__stdcall TKLSDK_UnLockChannel(
				   IN long lHandle,
				   IN ULONG ulKey
				   );
TKLSDK_API	BOOL __stdcall TKLSDK_GetOverlaySurface(PULONG pSurface);
TKLSDK_API	BOOL __stdcall TKLSDK_DeclareChannels(IN ULONG nBaseNum,IN ULONG nChannelNumber, IN BOOL bForce=FALSE);
TKLSDK_API	BOOL __stdcall TKLSDK_ReadRegister(IN long lHandle, IN ULONG nRegister, OUT ULONG* pByte);
TKLSDK_API	BOOL __stdcall TKLSDK_EnhanceImage(IN long lHandle, IN BOOL bEnhanceImage);
						   
#ifdef __cplusplus
}
#endif

#endif
