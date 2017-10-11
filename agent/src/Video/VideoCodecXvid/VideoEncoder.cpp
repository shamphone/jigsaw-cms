#include "stdafx.h"
#include "VideoEncoder.h"
#include "../VideoConfig/VideoConfig.h"
//#include "../../xvid/1.1.0/xvid.h"
#include "../../xvid/xvidcore-1.1.0/src/xvid.h"

VideoEncoder::VideoEncoder()
{
    m_nWidth = 0;
    m_nHeight = 0;
    m_encHandle = NULL;
}

VideoEncoder::~VideoEncoder()
{
    _ASSERTE(m_encHandle == NULL);
}

bool VideoEncoder::create(int nWidth, int nHeight, int nFrameRate, int bitRate, int max_key_interval)
{
    m_nWidth = nWidth;
    m_nHeight = nHeight;

	xvid_enc_create_t xvid_enc_create;
	memset(&xvid_enc_create, 0, sizeof(xvid_enc_create));
	xvid_enc_create.version = XVID_VERSION;
	xvid_enc_create.width = nWidth;
	xvid_enc_create.height = nHeight;
	xvid_enc_create.profile = XVID_PROFILE_AS_L4;
    xvid_enc_create.fincr = 1;
    xvid_enc_create.fbase = nFrameRate;
    xvid_enc_create.max_key_interval = max_key_interval;

    xvid_plugin_single_t single;
    memset(&single, 0, sizeof(xvid_plugin_single_t));
    single.version = XVID_VERSION;
    single.bitrate = bitRate;

	xvid_enc_plugin_t plugin;
    plugin.func = xvid_plugin_single;
    plugin.param = &single;

	xvid_enc_create.plugins = &plugin;
	xvid_enc_create.num_plugins = 1;

	int ret = xvid_encore(NULL, XVID_ENC_CREATE, &xvid_enc_create, NULL);
    if( ret < 0 )
    {
		_RPTF1(_CRT_WARN, "create encoder failed: %x.\n", ret);
        return false;
    }

    m_encHandle = xvid_enc_create.handle;
    return true;
}

void VideoEncoder::destroy()
{
	xvid_encore(m_encHandle, XVID_ENC_DESTROY, NULL, NULL);
    m_encHandle = NULL;
}

bool VideoEncoder::encode(
        unsigned char* pSrc,
        int nSrcLen,
        unsigned char* pDst,
        int* pnDstLen,
        bool* keyFrameFlag)
{
    _ASSERTE(m_encHandle != 0 );
    _ASSERTE(pSrc != 0);
    _ASSERTE(pDst != 0);
    _ASSERTE(nSrcLen == m_nHeight * m_nWidth * VideoConfig::DEFAULT_BIT / 8);

	xvid_enc_frame_t xvid_enc_frame;
	memset(&xvid_enc_frame, 0, sizeof(xvid_enc_frame));
	xvid_enc_frame.version = XVID_VERSION;

	xvid_enc_frame.bitstream = pDst;
	xvid_enc_frame.length = *pnDstLen;

    xvid_enc_frame.input.plane[0] = pSrc;
    xvid_enc_frame.input.csp = XVID_CSP_BGR;
    xvid_enc_frame.input.stride[0] = m_nWidth * VideoConfig::DEFAULT_BIT / 8;

	xvid_enc_frame.type = XVID_TYPE_AUTO;
	xvid_enc_frame.quant = 0;

	int ret = xvid_encore(m_encHandle, XVID_ENC_ENCODE, &xvid_enc_frame, NULL);
    *pnDstLen = ret;
    *keyFrameFlag = (xvid_enc_frame.out_flags & XVID_KEYFRAME) != 0;
    return ret >= 0;
}

