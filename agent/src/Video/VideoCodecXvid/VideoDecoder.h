#pragma once

//
// Wrapper for xvid decoder
// we only RGB24 format
//

class VideoDecoder
{
public:

    VideoDecoder();
    ~VideoDecoder();
    
    bool create(int nWidth, int nHeight);
    void destroy();

    bool decode(unsigned char* pSrc, int nSrcLen, unsigned char* pDst);

private:

    int m_nWidth;      // image width
    int m_nHeight;     // image height
    void* m_decHandle; // decoder handle
};

