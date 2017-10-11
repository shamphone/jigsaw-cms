#pragma once

class VideoCodec
{
public:

    // Init the xvid library
    // Must be called before any encode/decode function
    static bool init();
};

