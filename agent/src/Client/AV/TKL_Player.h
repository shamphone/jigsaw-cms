#ifndef  _THAKRAL_PLAYER_H_
#define  _THAKRAL_PLAYER_H_

#ifdef   _USRDLL
	#define  THAKRAL_PLAYER  __declspec(dllexport)
#else 
	#define  THAKRAL_PLAYER  __declspec(dllimport) 
#endif

#ifdef   VBCALL
	#define DLLCALL  _cdecl
#else 
	#define DLLCALL  _stdcall
#endif

//#include "PlayerInfo.h"

enum   DirectXMode{primarySurface,singleOverlay,multipleOverlay};

enum   WORKINGMODE{streamMode,fileMode};    //Á÷,ÎÄ¼þ

typedef struct 
{
   DWORD dwAudoRate;
   int   nAudoCh;
   int   nAudoFormat;
   int   nVedioRate;
   int   nVedioSeq;
   int   nGroupLen;
   int   nWidth;
   int   nHeight;
   int   nFrameType;
}FILEINFO;

enum TKPLAYERTYPE
{
   AVE1000_P,
   AVE2000_P,
   AVE3000_P,
   AVE4000_P,
   AVE5000_P,
   AVE6000_P,
   AVE7000_P,
   AVE8000_P,
   AVE9000_P
}; 


enum FRAMEFORMAT
{
   VIDEO_MODE_RGB555,
   VIDEO_MODE_RGB565,
   VIDEO_MODE_RGB24,
   VIDEO_MODE_RGB32,
   VIDEO_MODE_YV12,
   VIDEO_MODE_YUY2,
   VIDEO_MODE_UYVY
};

enum FRAMETYPE
{
	audioFrame,
	videoFrame
};

typedef struct _FrameInformation
{
   FRAMETYPE frameType; 
   int nWidth;		
   int nHeight;		
   int nFrameRate;
   int nTimeStamp;
   FRAMEFORMAT FrameType;		
}FrameInformation, *pFrameInformation;

typedef void (CALLBACK *DecoderCallBack)(ULONG lPlayer, BYTE * pBuf, ULONG uSize,
                                         FrameInformation *pInfo, void *pParams);

struct OUTPUTSTRUCT
{
   BYTE *pOutputBuf;//OUTPUT BUFFER
   int iPitch;
   char frameType;
   int nRate;
   unsigned char *pY;
   unsigned char *pU;
   unsigned char *pV;
   int iStepY;
   int iStepU;
   int iStepV;
   //* the decoderCallBack user's input parameter.
   void *pUserParam;
};

typedef void(CALLBACK *DecodeCallBack)(ULONG lPlayer, unsigned char* buffer,
                                       int size, FrameInformation* info, void *pParams);

typedef DWORD(CALLBACK *ReadFileCallBack)(LPVOID p_workaddress, DWORD dw_DataLenth,
                                          LONGLONG ll_ReadPos, void *pFileReadParams);

struct CVideoFrameInfo
{
   FRAMEFORMAT frameType;
   int iWidth;		
   int iHeight;		
   int iFrameRate;
   int iTimeStamp;
   //* all exclude VIDEO_MODE_YV12.
   unsigned char *pOutputBuf;//OUTPUT BUFFER
   int iPitch;
   //* VIDEO_MODE_YV12 will use the below members.
   unsigned char *pY;
   unsigned char *pU;
   unsigned char *pV;
   int iStepY;
   int iStepU;
   int iStepV;
   //* the videoDecoderCallBack user's input parameter.
   void *pUserParam;
};
struct CAudioFrameInfo
{
   int nTimeStamp;
   WAVEFORMATEX outWaveFormat;
   BYTE * pOutBuf;
   ULONG uDataSize;
   void *pUserParams;
};
typedef void (CALLBACK *VideoDecodeCallBack)(ULONG uPlayerId, CVideoFrameInfo *pInfo);
typedef void (CALLBACK *AudioDecodeCallBack)(ULONG uPlayerId, CAudioFrameInfo *pInfo);

//typedef void (*DecodeCallBack)(unsigned char* buffer, int size, FrameInformation* info);  
	
extern "C"
{	
	// 1 modified:para
	// 1 modified:para
	THAKRAL_PLAYER ULONG _stdcall TKLPLAYER_CreatePlayer(
		TKPLAYERTYPE  PlayerType,
		void * pParams);
	
	// 2	
	THAKRAL_PLAYER void  _stdcall TKLPLAYER_ReleasePlayer(ULONG lPlayer);
	
	// 3
	THAKRAL_PLAYER int   _stdcall TKLPLAYER_GetVideoAdapterCaps(ULONG lPlayer);
	
	// 4 modified:Name 
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_SetDisplayMode(
		ULONG lPlayer,
		DirectXMode mode,
		void * pParams);

	// 5
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_Play(ULONG lPlayer);
	
	// 6
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_Pause(ULONG lPlayer); 
	
	// 7
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_Stop(ULONG lPlayer);
	      
	// 8
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_SetVideoWindow(
		ULONG lPlayer,
		HWND hWnd,
		RECT* pRect = NULL); 
	
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_SetVideoWindowForStream(
		ULONG lPlayer,
		HWND hWnd,
		RECT* pRect = NULL); 
	// 9
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FullScreen(
		ULONG lPlayer,
		BOOL bFullScreen);
	
	// 10 
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_SetPostFilter(
		ULONG lPlayer, 
		int FilterMode);

	// 11
	THAKRAL_PLAYER void  _stdcall TKLPLAYER_Mute(
		ULONG lPlayer, 
		BOOL bMute);
		
	// 12
	THAKRAL_PLAYER void  _stdcall TKLPLAYER_SetVolume(
		ULONG lPlayer,
		LONG lVolume);
		
	// 13
	THAKRAL_PLAYER LONG  _stdcall TKLPLAYER_GetVolume(ULONG lPlayer); 

	// 14
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_StreamSetBuf(
		ULONG lPlayer,
		DWORD dwBufSize);
	
	// 15
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_StreamGetFreeBufSize(ULONG lPlayer);
	
	// 16
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_StreamEmptyBuf(ULONG lPlayer);
	
	THAKRAL_PLAYER void  _stdcall TKLPLAYER_StreamSetDelayFrames(ULONG lPlayer,
		DWORD nFrames);

	// 17
	THAKRAL_PLAYER int _stdcall TKLPLAYER_StreamWriteToBuf(
		ULONG lPlayer,
		unsigned char *pBuffer,
		DWORD dwLength,
		int nFrameType,
		DWORD dwMilliseconds,BOOL bThrown = TRUE);

	// 18  
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_GetPictureSize(
		ULONG lPlayer,		
		int &Width,
		int &Length);

	//19
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_GetBmpFileSize(
	    ULONG  lPlayer);

   // 19
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_CaptureBitmap(
		ULONG lPlayer,
		char *strFilename,
		BYTE *pBitmap = NULL);

	// 20
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_ResetPlayer(ULONG lPlayer);
	
   // 21
   THAKRAL_PLAYER void  _stdcall TKLPLAYER_EnableDataDriven(
      ULONG lPlayer,
      BOOL bEnable);
	// 21
	THAKRAL_PLAYER void  _stdcall TKLPLAYER_StreamEnableDataDriven(
		ULONG lPlayer,
		BOOL bEnable);

	// 22
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileOpen(
		ULONG lPlayer,
		char *strFileName,
      bool bFileShared=false);
   
	// 23
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_StreamOpen(
		ULONG lPlayer,
		unsigned char *pHeadBuffer,
		DWORD dwLength,
		DWORD dwMilliseconds);

	// 23
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileClose(ULONG lPlayer);

	// 24 
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileSetEndNotify(
		ULONG lPlayer, 
		HWND hWnd,
		UINT nEndMsgID,
		HANDLE hEndEvent = NULL);

	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileSetEndIndexNotify(
														ULONG lPlayer, 
														HWND hWnd,
														UINT nEndMsgID,
														HANDLE hEndEvent = NULL);

	// 25  
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_SetPlaySpeed(
		ULONG lPlayer,
		float fSpeed);

	// 26
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileStepPlay(
		ULONG lPlayer,
		BOOL bForward);

	// 27  
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileSetPosition(
		ULONG lPlayer,
		float nPercent); //0~100%

	// 29   
	THAKRAL_PLAYER float  _stdcall TKLPLAYER_FileGetPosition(ULONG lPlayer);

	// 30
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_FileGetTotalTime(ULONG lPlayer);

	// 31 
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_FileGetPlayedTime(ULONG lPlayer);

	// 32
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileSetPlayTime(
		ULONG lPlayer, 
		DWORD dwPlayTime);

	// 33 
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileFixIndex(ULONG lPlayer,BOOL bIn = TRUE);
	
	// 34 
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileCheckIndex(ULONG lPlayer);
	
	// 35
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_FileGetTotalFrame(ULONG lPlayer);

	// 36 
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_FileGetPlayedFrame(ULONG lPlayer);

	// 37
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FileSetPlayFrame(
		ULONG lPlayer, 
		DWORD dwPlayFrame);

   // 38
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_RegisterDecCallBack(
		ULONG lPlayer,
		DecoderCallBack callback7k,
		DecodeCallBack callback6k,
		void * pParams,
      BOOL bYuvNoPitch=false);

	// 39
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_UnRegisterDecCallBack(
      ULONG lPlayer,
      void* pParams);

	// 40
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_ZoomInSrcRect(
		ULONG lPlayer, 
		RECT* rect);

	// 41 
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_FilePlayKeyFrame(
		ULONG lPlayer, 
		BOOL  bForward, 
		DWORD dwSleepTime		
		);
	
	// 42
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_GetSdkVersion(ULONG lPlayer);
	
	// 43	
	THAKRAL_PLAYER DWORD _stdcall TKLPLAYER_GetLastError(ULONG lPlayer);
	
	//Add
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_SetErrorNotify(
		ULONG lPlayer, 
		HWND hWnd,
		UINT nErrorMsgID,
		HANDLE hEndEvent = NULL);
	
	//
	THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_RefreshWindow(ULONG lPlayer);	
   
	THAKRAL_PLAYER BOOL  _stdcall TKLPLAYER_ThrownFrames(
      ULONG lPlayer,
		int nFrame);

	THAKRAL_PLAYER void  _stdcall TKLPLAYER_GetWHInfo(
      ULONG lPlayer,
		char* fileName,
		DWORD& width,DWORD& height);
		
	THAKRAL_PLAYER void _stdcall TKLPLAYER_EnableAudio(
      ULONG lPlayer,
		BOOL bEnable);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_GetKeyFramePos(
      ULONG lPlayer,
		DWORD& dwPos);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_GetNextKeyFramePos(
      ULONG lPlayer,
		DWORD& dwPos);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_CetBlackScreen(ULONG lPlayer);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_SetBrightness(
      ULONG lPlayer,
      int nBrightness);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_SetContrast(
      ULONG lPlayer,
      int nContrast);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_SetSaturation(
      ULONG lPlayer,
      int nSaturation);
	
	THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_RegisterReadFileCallBack(
		ULONG lPlayer,
		ReadFileCallBack readfilecallback,
		LONGLONG FileLength,
		void * pParams) ;

	THAKRAL_PLAYER int _stdcall TKLPLAYER_GetMediaStreamType(ULONG lPlayer);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_FileBeginIndex (
      ULONG lPlayer,
      LPVOID *p_workaddress,
      LONGLONG *ll_Pos,
      LPDWORD  dw_datalength);

	THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_FileCreateIndex (
      ULONG lPlayer,
      DWORD datalength,
      LONGLONG  *ll_Pos,
      LPDWORD dw_Length);

	THAKRAL_PLAYER void _stdcall TKLPLAYER_FileBackPlay (
      ULONG lPlayer,
      int nFrame = 1);

	THAKRAL_PLAYER FILEINFO _stdcall TKLPLAYER_GetFileInfo (ULONG lPlayer) ;

   THAKRAL_PLAYER void _stdcall TKLPLAYER_SetVideoDecoderType(
      ULONG lPlayer,
      int nDecoderType,
      int iDeviceId,
      int iChannelId);

   THAKRAL_PLAYER void _stdcall TKLPLAYER_SetAudioDecoderType(
      ULONG lPlayer,
      int nDecoderType,
      int iDeviceId,
      int iChannelId);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_EnableVideoTVAnalogOutput(
      ULONG lPlayer,
      BOOL bEnable,
      int iVideoPortNumber,
      BOOL bMultiWnd,
      int iWndId);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_EnableAudioTVAnalogOutput(
      ULONG lPlayer,
      BOOL bEnable,
      int iVideoPortNumber);

   THAKRAL_PLAYER void _stdcall TKLPLAYER_EnableVideoPCOutput(
      ULONG lPlayer,
      BOOL bEnable);

   THAKRAL_PLAYER void _stdcall TKLPLAYER_EnableAudioPCOutput(
      ULONG lPlayer,
      BOOL bEnable);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_GetDecoderChannelInfo(
      ULONG lPlayer,
      ULONG *pSerielNo,
      ULONG *pFlag);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_ReadDecoderUserInfo(
      ULONG lPlayer,
      ULONG ulOffset,
      ULONG ulByteCount,
      PUSHORT pData);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_WriteDecoderUserInfo(
      ULONG lPlayer,
      ULONG ulOffset,
      ULONG ulByteCount,
      PUSHORT pData);

   THAKRAL_PLAYER void _stdcall TKLPLAYER_DspTest(ULONG lPlayer, int iTestId);

   THAKRAL_PLAYER void _stdcall TKLPLAYER_GetTotalDecoderDeviceCount(
      ULONG lPlayer,
      int &iDeviceCount);

   THAKRAL_PLAYER int _stdcall TKLPLAYER_StreamGetBufferedFrames(ULONG uPlayerId);
   
   THAKRAL_PLAYER void _stdcall TKLPLAYER_StreamEnableRemoteFilePlay(
      ULONG uPlayerId, BOOL bEnable);
   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_FileResetEndOfFile(ULONG uPlayerId);

   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_RegisterVideoDecodeCallBack(ULONG uPlayerId,
      VideoDecodeCallBack videoCallback,
      void *pParams);
   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_UnRegisterVideoDecodeCallBack(ULONG uPlayerId, void* pParams);
   
   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_RegisterAudioDecodeCallBack(ULONG uPlayerId,
      AudioDecodeCallBack audioCallback,
      int iBufferedMiniSeconds,
      void *pParams);
   THAKRAL_PLAYER BOOL _stdcall TKLPLAYER_UnRegisterAudioDecodeCallBack(ULONG uPlayerId, void* pParams);
   //
}
#endif
