// CodecMgr.cpp: implementation of the CVideoCodecMgr class.
//
//////////////////////////////////////////////////////////////////////

#include "stdafx.h"
#include "VideoCodecMgr.h"


#ifdef _DEBUG
#undef THIS_FILE
static char THIS_FILE[]=__FILE__;
#define new DEBUG_NEW
#endif

static int QUALITY =5;
static int QUANTI = 0;		/* used for fixed-quantizer encoding  */

//const long dechandle = 0x0815;	/* dechandle is a unique constant!!!  */
long CVideoCodecMgr::hisDecHandle = 0x0815;	

CVideoCodecMgr::CVideoCodecMgr()
{	
	m_bVEncInit = FALSE;
	m_bVDecInit = FALSE;

	enchandle = NULL;
	dechandle = hisDecHandle++;	//应该使用不同的值
}

CVideoCodecMgr::~CVideoCodecMgr()
{
	if (m_bVDecInit)
		DestroyDecoderV();

	if (m_bVEncInit)
		DestroyEncoderV();
}

void CVideoCodecMgr::SetEncVideoFormat(int nWidth, int nHeight, int nBit)
{	
	m_BmpU.bmiHeader.biBitCount=nBit;
	m_BmpU.bmiHeader.biClrImportant=m_BmpU.bmiHeader.biClrUsed=0;
	m_BmpU.bmiHeader.biCompression=BI_RGB;
	m_BmpU.bmiHeader.biHeight=nHeight;
	m_BmpU.bmiHeader.biPlanes=1;
	m_BmpU.bmiHeader.biSize=sizeof(BITMAPINFOHEADER);
	m_BmpU.bmiHeader.biSizeImage= (nWidth * nHeight *nBit)/8;
	m_BmpU.bmiHeader.biWidth=nWidth;
	m_BmpU.bmiHeader.biXPelsPerMeter=m_BmpU.bmiHeader.biYPelsPerMeter=0;
} 

void CVideoCodecMgr::SetDecVideoFormat(int nWidth, int nHeight, int nBit)
{	
	m_BmpD.bmiHeader.biBitCount=nBit;
	m_BmpD.bmiHeader.biClrImportant=m_BmpD.bmiHeader.biClrUsed=0;
	m_BmpD.bmiHeader.biCompression=BI_RGB;
	m_BmpD.bmiHeader.biHeight=nHeight;
	m_BmpD.bmiHeader.biPlanes=1;	
	m_BmpD.bmiHeader.biSize=sizeof(BITMAPINFOHEADER);
	m_BmpD.bmiHeader.biSizeImage= (nWidth * nHeight *nBit)/8;
	m_BmpD.bmiHeader.biWidth=nWidth;
	m_BmpD.bmiHeader.biXPelsPerMeter=m_BmpD.bmiHeader.biYPelsPerMeter=0;
}


//解压缩视频帧
BOOL CVideoCodecMgr::DecodeVideoData(char *pin,DWORD len,char* pout,DWORD *lenr,DWORD flag)
{	
	if (!m_bVDecInit)
		return FALSE;

	int status;
	
	static DEC_FRAME dec_frame;
	static DEC_FRAME_INFO dec_frame_info;

	dec_frame.length = len;
	dec_frame.bitstream = pin;
	dec_frame.bmp = pout;
	dec_frame.render_flag = 1;		/* 0 means: skip this frame */
	dec_frame.stride = m_BmpD.bmiHeader.biWidth;

	status = decore(dechandle, DEC_OPT_FRAME, &dec_frame, &dec_frame_info);

	if (lenr)
		*lenr=m_BmpD.bmiHeader.biSizeImage;

	return TRUE;	
}

//压缩视频帧
BOOL CVideoCodecMgr::EncodeVideoData(char* pin,DWORD len,char* pout,DWORD* lenr,BOOL* pKey)
{

	int status;

	ENC_FRAME enc_frame;
	ENC_RESULT enc_result;
	
	enc_frame.image = (void *) pin; 
	enc_frame.bitstream = (void *) pout;
	enc_frame.length = 0;			/* filled by encore */
	enc_frame.colorspace = ENC_CSP_RGB24;
	enc_frame.mvs = NULL;		/* unsupported */	

	enc_frame.quant = 0;
	enc_frame.intra = -1;		/* let encoder decide if frame is INTER/INTRA */
	if (pKey)
	{
		if (*pKey == TRUE)
		{
			enc_frame.intra = 1;
		}
	}

	
	status = encore(enchandle, ENC_OPT_ENCODE_VBR, &enc_frame, &enc_result);

	if (lenr)
		*lenr=enc_frame.length;
	
	if (pKey)
	{
		if (enc_result.is_key_frame) 
		{
			*pKey = 1;
		}
		else
		{
			*pKey = 0;
		}
	}
	
	return TRUE;
}


BOOL CVideoCodecMgr::InitEncoderV(float FrameRate, float BitRate, int nKeyFrameInterval)
{
	BOOL bRet=FALSE;

	ENC_PARAM enc_param;

	enc_param.x_dim = m_BmpU.bmiHeader.biWidth;
	enc_param.y_dim = m_BmpU.bmiHeader.biHeight;

	enc_param.framerate = FrameRate;		/* a float */
	enc_param.bitrate = (int)BitRate;

	enc_param.rc_period = 2000;
	enc_param.rc_reaction_period = 10;
	enc_param.rc_reaction_ratio = 20;

	enc_param.max_quantizer = 80;
	enc_param.min_quantizer = 1;

	enc_param.quality = QUALITY;

	enc_param.use_bidirect = 0;	/* use bidirectional coding */
	enc_param.deinterlace = 0;	/* fast deinterlace */
	enc_param.obmc = 0;		/* flag to enable overlapped block motion compensation mode */

	enc_param.max_key_interval = (int)nKeyFrameInterval;
	enc_param.handle = NULL;	/*will be filled by encore */

	int status = encore(enchandle, ENC_OPT_INIT, &enc_param, NULL);
	enchandle = enc_param.handle;

	if (status == ENC_OK)
		m_bVEncInit = TRUE;

	return m_bVEncInit;
}

BOOL CVideoCodecMgr::InitDecoderV()
{
	DEC_PARAM dec_param;
	
	dec_param.x_dim = m_BmpD.bmiHeader.biWidth;
	dec_param.y_dim = m_BmpD.bmiHeader.biHeight;
	dec_param.output_format = DEC_RGB24;	/*   output color format, , see <decore.h> */
	
	dec_param.time_incr = 20;

	decore(dechandle, DEC_OPT_INIT, &dec_param, NULL);
	m_bVDecInit = TRUE;

	return TRUE;

}

void CVideoCodecMgr::DestroyCodecV()
{
	DestroyEncoderV();
	DestroyDecoderV();
}

void CVideoCodecMgr::DestroyEncoderV()
{
	if (m_bVEncInit)
	{
		encore(enchandle, ENC_OPT_RELEASE, NULL, NULL);
		m_bVEncInit = FALSE;
	}
}

void CVideoCodecMgr::DestroyDecoderV()
{
	if (m_bVDecInit)
	{
		decore(dechandle, DEC_OPT_RELEASE, NULL, NULL);
		m_bVDecInit = FALSE;
	}
}

