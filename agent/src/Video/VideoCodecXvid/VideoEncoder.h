#pragma once

//
// Wrapper for xvid encoder
// we only RGB24 format
//

class VideoEncoder
{
public:

    VideoEncoder();
    ~VideoEncoder();
    
    bool create(int nWidth, int nHeight, int nFrameRate, int bitRate, int max_key_interval);
    void destroy();

    // Encode frame
    bool encode(
            unsigned char* pSrc,   // Source buffer
            int nSrcLen,           // Source buffer length
            unsigned char* pDst,   // Destination buffer
            int* pnDstLen,         // Destination buffer length pointer
            bool* keyFrameFlag);   // Key frame flag pointer

private:

    int m_nWidth;      // image width
    int m_nHeight;     // image height
    void* m_encHandle; // encoder handle
};

