/*
 * 声音采集的接口类
 */

#ifndef _AudioCaptureMGR_H_
#define _AudioCaptureMGR_H_

class AudioCaptureMgr
{

public:

    // 采集到声音数据以后的回调函数原型定义
    typedef void (*PIO_CALLBACK)(void* pObject, WAVEHDR* pWH); 

//
// AudioCaptureMgr 采用 Singleton模式
//
public:

    // 初始化实例, 如果没有指定设备，则使用默认设备
    static bool create(
        PIO_CALLBACK pCallback,              // Callback function
		void* pCallbackParameter,            // Callback parameter
        int nWaveInDeviceId = WAVE_MAPPER);  // WaveIn Device Id

	// 初始化实例,从文件中读取数据（在设备不足的情况下，用作测试）
    static bool createFromFile(
        PIO_CALLBACK pCallback,     // Callback function
		void* pCallbackParameter,   // Callback parameter
        char* filename);            // Audio data file

    // 销毁实例
    static void destroy();

private:
	AudioCaptureMgr();
	virtual ~AudioCaptureMgr();	

public:

    //
    //捕捉采用调用计数的方式进行
    //调用方必须保证对于 startCapture的调用和 stopCapture的调用其次数相匹配
    //

    // 开始捕捉
	static bool startCapture();

    // 停止捕捉
	static bool stopCapture();

};

#endif 
