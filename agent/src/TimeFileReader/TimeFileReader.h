#pragma once

class TimeFileReader
{
public:

    // 读取数据以后的回调函数原型定义
    typedef void (*TIMEFILEREADER_CALLBACK)(void* pObject, char* pBuffer, int nLength);
    
public:
	TimeFileReader(
		TIMEFILEREADER_CALLBACK pCallback,   // Callback function
        void* pCallbackParameter);           // Callback Parameter

	~TimeFileReader();

    // Start to read
	bool start(
		char* filename,  // Filename
		int headerSize,  // file header size, which will be skipped
		int frameSize,   // The size of one frame
		int interval);   // return data interval in milliseconds

    // Stop to read
	void stop();

private:

    HANDLE m_hStopEvent;     // Event to control the read thread.
    int m_nFrameSize;        // Length of one frame
    int m_nInterval;         // return data interval in milliseconds
    HANDLE m_hThread;        // Reading thread handle
    char* m_pFileData;       // File data without header
    int m_nFileDataLength;   // File data length

    TIMEFILEREADER_CALLBACK m_pCallback;  // Call back function
    void* m_pCallbackParameter;           // Call back function parameter

    // Thread function
    static unsigned int WINAPI ReadThreadProc(void* lpParameter);
};

