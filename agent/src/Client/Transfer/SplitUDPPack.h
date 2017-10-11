#ifndef _SPLIT_UDP_PACK_
#define _SPLIT_UDP_PACK_

#include "common/common/MediaPacket/udp_media_def.h"

/**
 * This file provides utility for splitting the MPEG-4 Large Key Frame
 * UDP Packet to small packet, but this is only an adhoc method,
 * the complete solution is increasing the underlying network bandwidth.
 */

// Maximum UDP packet size
// We assume the MTU is 1500 ( Like ethernet ), and Maximum UDP packet is
// 1472 bytes. but we should take PACK_VIDEO structure into account. so we
// we make some round.
const int MAX_ETHERNET_UDP = 1400;

// We have defined MAX_UDP_SIZE 60000, if split this packet to
// MAX_ETHERNET_UDP packet size, the partial block count is
// defined as following, approximately 43
const int PARTIAL_BLOCK_COUNT = (MAX_UDP_SIZE / MAX_ETHERNET_UDP + 1);

// 重新组合数据的缓冲区
struct BufferedKeyFrame
{
    PACK_VIDEO pack;
    char buffer[MAX_UDP_SIZE];
    unsigned int nReceivedBytes;

    BufferedKeyFrame()
    {
        nReceivedBytes = 0;
    }
};

//缓存的需要组合的关键帧数量
const int BUFFERED_KEYFRAME_NUMBER = 16;

// 组合得到的关键帧分片
// 如果有buffer ready, 返回其编号，否则返回 -1
int fillKeyBuffer(const PACK_VIDEO* ppv, int nLength, BufferedKeyFrame bufferedKeyFrames[]);

// 将一个关键帧编码后的数据块拆分为多个小块
int splitVideoPack(const char* buffer, int encodedLength, char partialBlocks[][MAX_ETHERNET_UDP + sizeof(PACK_VIDEO)], int partialBlockSize[]);


#endif