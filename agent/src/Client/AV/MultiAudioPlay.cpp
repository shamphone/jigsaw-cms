#include "stdafx.h"
#include "MultiAudioPlay.h"
#include "common/common/log/log.h"

MultiAudioPlay::MultiAudioPlay(__int64 userId)
{
    this->m_userId = userId;
    m_nInstanceCount = 0;
}

MultiAudioPlay::~MultiAudioPlay()
{
    // Close every process and release memory
    AudioProcessMap::iterator itProcess = m_audioProcesses.begin();
    while( itProcess != m_audioProcesses.end())
    {
        AudioProcessPlay* pAudioProcess = itProcess->second;
        pAudioProcess->destroy();
        delete pAudioProcess;

        itProcess++;
    }
}

bool MultiAudioPlay::create()
{
    return true;
}

void MultiAudioPlay::destroy()
{
}

bool MultiAudioPlay::createProcessForUser(__int64 userId)
{
	// 查找是否已经有进程为该用户服务
	int userProcessCallCount;
	map<__int64, int>::iterator itUser = this->m_audioProcessUseCount.find(userId);
	if( itUser == this->m_audioProcessUseCount.end() )
	{
		userProcessCallCount = 1;
	}
	else
	{
		userProcessCallCount = itUser->second + 1;
		this->m_audioProcessUseCount.erase(itUser);
	}
	this->m_audioProcessUseCount.insert( map<__int64, int>::value_type(userId, userProcessCallCount));

	// 如果是引用次数为1，那么创建新进程
	if( userProcessCallCount == 1 )
	{
		// Get a new name for the audio process
		char buffer[1024];
		sprintf(buffer, "AudioProcessPlay_%I64d_%d", this->m_userId, m_nInstanceCount);
		m_nInstanceCount ++;
	    
		// Create new audio process
		AudioProcessPlay* pAudioProcess = new AudioProcessPlay();
		if(! pAudioProcess->create(buffer))
		{
			pAudioProcess->destroy();
			delete pAudioProcess;

			itUser = this->m_audioProcessUseCount.find(userId);
			this->m_audioProcessUseCount.erase(itUser);

			return false;
		}

		// Add this audio process to map
		this->m_audioProcesses.insert( AudioProcessMap::value_type( userId, pAudioProcess));
	}

    return true;
}

bool MultiAudioPlay::destroyProcessForUser(__int64 userId)
{
    // 验证这个用户在 播放列表中
	int userProcessCallCount;
	map<__int64, int>::iterator itUser = this->m_audioProcessUseCount.find(userId);
	_ASSERTE(itUser != this->m_audioProcessUseCount.end());

	userProcessCallCount = itUser->second - 1;
	this->m_audioProcessUseCount.erase(itUser);
	
	if( userProcessCallCount == 0 )
	{
		AudioProcessMap::iterator it = m_audioProcesses.find(userId);
		_ASSERTE( it != m_audioProcesses.end());

		// 关闭进程，清除数据
		AudioProcessPlay* pAudioProcess = it->second;
		pAudioProcess->destroy();
		delete pAudioProcess;
		this->m_audioProcesses.erase( userId);
	}
	else
	{
		this->m_audioProcessUseCount.insert( map<__int64, int>::value_type(userId, userProcessCallCount));
	}

    return true;
}

void MultiAudioPlay::playAudioPack(PACK_AUDIO *ppa)
{
    // 找到这个用户对应的播放进程
    AudioProcessMap::iterator it = m_audioProcesses.find(ppa->userId);
    if( it == m_audioProcesses.end())
    {
        return;
    }

    // 播放这个声音
    AudioProcessPlay* pAudioProcess = it->second;
    pAudioProcess->play(ppa);
}
