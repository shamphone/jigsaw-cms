#include "stdafx.h"
#include "SplitUDPPack.h"

int fillKeyBuffer(const PACK_VIDEO* ppv, int nLength, BufferedKeyFrame bufferedKeyFrames[])
{
    _ASSERTE( ppv->key);

    int who = -1;

    // Search for buffer item
    for(int i=0; i<BUFFERED_KEYFRAME_NUMBER; i++)
    {
        PACK_VIDEO currentPack = bufferedKeyFrames[i].pack;
        if(  currentPack.no == ppv->no && currentPack.userId == ppv->userId )
        {
            who = i;
            break;
        }
    }

    if( who == -1 )
    {
        // Search for an unused item
        for( int i=0; i<BUFFERED_KEYFRAME_NUMBER; i++)
        {
            if( bufferedKeyFrames[i].nReceivedBytes == 0 ) 
            {
                who = i;
                break;
            }
        }
    }

    // select the first item, so those packet is discarded.
    if( who == -1 )
    {
        who = 0;
        bufferedKeyFrames[i].nReceivedBytes = 0;
    }

    // copy the pack data to buffer
    bufferedKeyFrames[who].pack = *ppv;
    memcpy( 
        bufferedKeyFrames[who].buffer + bufferedKeyFrames[who].nReceivedBytes, 
        ((char*)ppv) + sizeof(PACK_VIDEO), 
        nLength - sizeof(PACK_VIDEO) );
    bufferedKeyFrames[who].nReceivedBytes += nLength - sizeof(PACK_VIDEO); 

    // Judge if the buffer is full
    if( bufferedKeyFrames[who].nReceivedBytes == bufferedKeyFrames[who].pack.data_size)
    {
        return who;
    }
    // not full, return -1
    else if(bufferedKeyFrames[who].nReceivedBytes < bufferedKeyFrames[who].pack.data_size) 
    {
        return -1;
    }
    // This key frame has been bad, throw it
    else
    {
        memset( (char*)&(bufferedKeyFrames[who].pack), 0, sizeof(PACK_VIDEO));
        bufferedKeyFrames[who].nReceivedBytes = 0;
        return -1;
    }
}

// 将一个关键帧编码后的数据块拆分为多个小块
int splitVideoPack(const char* buffer, int encodedLength, char partialBlocks[][MAX_ETHERNET_UDP + sizeof(PACK_VIDEO)], int partialBlockSize[])
{
	int nBlockCount = 0;
    PACK_VIDEO* ppv = (PACK_VIDEO*)buffer;
    _ASSERTE(ppv->key);

    if( encodedLength % MAX_ETHERNET_UDP == 0 )
    {
        nBlockCount = encodedLength /MAX_ETHERNET_UDP;
    }
    else
    {
        nBlockCount = encodedLength /MAX_ETHERNET_UDP + 1;
    }

    _ASSERTE(nBlockCount <= PARTIAL_BLOCK_COUNT);

    const char* copyedData = buffer + sizeof(PACK_VIDEO);
    int leftLength = encodedLength;

    for(int i=0; i<nBlockCount; i++)
    {
        int thisBlockLength = 0;

        if( leftLength > MAX_ETHERNET_UDP )
        {
            thisBlockLength = MAX_ETHERNET_UDP;
        }
        else
        {
            thisBlockLength = leftLength;
        }

        char* currentBlock = partialBlocks[i];
        ::memcpy(currentBlock, ppv, sizeof(PACK_VIDEO));
        ::memcpy(currentBlock + sizeof(PACK_VIDEO), copyedData, thisBlockLength);
        partialBlockSize[i] = thisBlockLength + sizeof(PACK_VIDEO);

        copyedData += thisBlockLength;
        leftLength -= thisBlockLength;
    }
	return nBlockCount;
}

