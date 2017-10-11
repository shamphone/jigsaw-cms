#include "stdafx.h"
#include "VideoDecoder.h"
#include "../VideoConfig/VideoConfig.h"
//#include "../../xvid/1.1.0/xvid.h"
#include "../../xvid/xvidcore-1.1.0/src/xvid.h"

VideoDecoder::VideoDecoder()
{
    m_nWidth = 0;
    m_nHeight = 0;
    m_decHandle = NULL;
}

VideoDecoder::~VideoDecoder()
{
    _ASSERTE(m_decHandle == NULL);
}

bool VideoDecoder::create(int nWidth, int nHeight)
{
    m_nWidth = nWidth;
    m_nHeight = nHeight;

	xvid_dec_create_t xvid_dec_create;
    memset(&xvid_dec_create, 0, sizeof(xvid_dec_create));

    xvid_dec_create.version = XVID_VERSION;
	xvid_dec_create.width = m_nWidth;
	xvid_dec_create.height = m_nHeight;

	int ret = xvid_decore(NULL, XVID_DEC_CREATE, &xvid_dec_create, NULL);
    if( ret < 0 )
    {
		_RPTF1(_CRT_WARN, "create decoder failed: %x.\n", ret);
        return false;
    }

    m_decHandle = xvid_dec_create.handle;
    return true;
}

void VideoDecoder::destroy()
{
	xvid_decore(m_decHandle, XVID_DEC_DESTROY, NULL, NULL);
    m_decHandle = NULL;
}

bool VideoDecoder::decode(unsigned char* pSrc, int nSrcLen, unsigned char* pDst)
{
    _ASSERTE(m_decHandle != 0 );
    _ASSERTE(pSrc != 0);
    _ASSERTE(pDst != 0);

	xvid_dec_frame_t xvid_dec_frame;
	memset(&xvid_dec_frame, 0, sizeof(xvid_dec_frame_t));
	xvid_dec_frame.version = XVID_VERSION;
	xvid_dec_frame.general = 0;
	xvid_dec_frame.bitstream = pSrc;
	xvid_dec_frame.length = nSrcLen;

    xvid_dec_frame.output.csp = XVID_CSP_BGR;
	xvid_dec_frame.output.plane[0]  = pDst;
	xvid_dec_frame.output.stride[0] = m_nWidth * VideoConfig::DEFAULT_BIT / 8;

	int ret = xvid_decore(m_decHandle, XVID_DEC_DECODE, &xvid_dec_frame, NULL);
	return ret >= 0;
}

