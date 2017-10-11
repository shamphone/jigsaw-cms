/*------------------------------------------------------------------------------*\
 =============================
   模块名称: waveout.h
 =============================

 [目的]
 
     方便waveOutXXX函数族的使用，使其对象化     
     	  
 [描述]
		
	该模块包括CWaveOut类，这是个封装了录音操作的类。   
 
 [用法]
   
    此类是基础类，建议不要直接使用该类		
	 
 [依赖性]
	
	 Winmm.lib 

 [修改记录]
 
  版本:    1.01.01
  日期:    01-11-1         
  作者:    Brant Q
  备注:
  

                                              
\*------------------------------------------------------------------------------*/
#ifndef _WAVEOUT_H_
#define _WAVEOUT_H_

void waveOutErrorMsg(MMRESULT mmr,char* szTitle);

class CWaveOut
{
public:		
	CWaveOut();
	virtual  ~CWaveOut();
	
	void SetLastMMError(MMRESULT mmr);
	MMRESULT GetLastMMError();
	
	BOOL CloseDev();
	BOOL OpenDev(WAVEFORMATEX* pfmt,DWORD dwCallback,DWORD dwCallbackInstance,DWORD fdwOpen);
	operator HWAVEOUT() const;

	static CString GetWaveOutName(UINT nIndex);
	static UINT GetWaveOutCount();
protected:

	MMRESULT m_mmr;
	HWAVEOUT m_hOut;
};


#endif