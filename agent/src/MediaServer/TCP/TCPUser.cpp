#include "stdafx.h"
#include "TCPUser.h"
#include "TCPForward.h"
#include "Common/Common/Log/Log.h"
#include "Common/Common/MediaPacket/tcp_media_def.h"

TCPUser::TCPUser(__int64 userId, TCPForward* pTCPForward)
{
	m_userId = userId;
	this->m_pTCPForward = pTCPForward;

	m_socket = 0;
	m_hThread = 0;
}

TCPUser::~TCPUser()
{
}

bool TCPUser::Create()
{
    return true;
}

void TCPUser::Destroy()
{
	if( m_socket != 0 )
	{
		::closesocket(m_socket);
		m_socket = 0;
	}

	if( m_hThread != 0 )
	{
		::WaitForSingleObject(m_hThread, INFINITE);
		m_hThread = 0;
	}

	return;
}

bool TCPUser::startForward(SOCKET s)
{
	// Remembe this socket
	this->m_socket = s;

    // Start the read thread
    unsigned int threadId;
    m_hThread = (HANDLE)_beginthreadex(NULL, 0, ThreadProc, this, 0, &threadId);
    if( m_hThread == 0 )
    {
		FVS_DEBUG("_beginthreadex failed.\n");
		m_hThread = 0;
        return false;
    }

	return true;
}

unsigned int TCPUser::ThreadProc(void* pObject)
{
	TCPUser* _this = (TCPUser*)pObject;
    char buffer[MAX_TCP_SIZE + sizeof(PACK_TCP)];     // Buffer

	BOOL bRunning = TRUE;
	int bytesReceived = 0;    
    int ret;
	int nHeaderSize = sizeof(PACK_TCP);

	while(bRunning)
	{
		//接收包头数据
		bytesReceived = 0;
		while( bytesReceived < nHeaderSize )
		{
			ret = ::recv(_this->m_socket, buffer + bytesReceived, nHeaderSize - bytesReceived, 0);
			if( ret == SOCKET_ERROR || ret == 0)
			{
				FVS_DEBUG2("Socket error for user %I64d, error code = %d\n", _this->m_userId, ::WSAGetLastError());
				return 0;
			}
			bytesReceived += ret;
		}

        int len = ((PACK_TCP*)buffer)->data_size;
        _ASSERTE(len < MAX_TCP_SIZE);

		//接收实际数据
		bytesReceived = 0;
		while( bytesReceived < len )
		{
			ret = ::recv(_this->m_socket, buffer + nHeaderSize + bytesReceived, len - bytesReceived, 0);
			if( ret == 0 || ret == SOCKET_ERROR)
			{
				FVS_DEBUG2("Socket error for user %I64d, error code = %d\n", _this->m_userId, ::WSAGetLastError());
				return 0;
			}
			bytesReceived += ret;
		}
		int nType = ((PACK_TCP*)buffer)->flag;

        ::EnterCriticalSection(&_this->m_pTCPForward->m_cs);

		switch( nType )
		{
		case FVS_TCP_DESKTOP:
			{
				TCPForward::TCPUserRelationMap::iterator itRelation = _this->m_pTCPForward->m_desktopRelations.find(_this->m_userId);
				if( itRelation != _this->m_pTCPForward->m_desktopRelations.end() )
				{
					// Send data to all peers
					list<__int64>* pRelation = itRelation->second;
					list<__int64>::iterator itUserId;
					for( itUserId = pRelation->begin(); itUserId != pRelation->end(); itUserId++)
					{
						TCPForward::TCPUserMap::iterator itUser = _this->m_pTCPForward->m_users.find(*itUserId);
						_ASSERTE(itUser != _this->m_pTCPForward->m_users.end());
						TCPUser* pPeer = itUser->second;
						if(!pPeer->sendData(buffer, len+nHeaderSize))
						{
							pPeer->Destroy();
						}
					}
				}
				else
				{
					_ASSERTE(FALSE);
				}
				break;
			}
		case FVS_TCP_WHITEBOARD:
			{
		/*屏蔽白板功能
				__int64 confId = ((PACK_TCP*)buffer)->confId;
				TCPForward::TCPUserRelationMap::iterator itRelation = _this->m_pTCPForward->m_whiteboardRelations.find( confId );
				if( itRelation != _this->m_pTCPForward->m_whiteboardRelations.end() )
				{
					// Send data to all peers
					list<__int64>* pRelation = itRelation->second;
					list<__int64>::iterator itUserId;
					for( itUserId = pRelation->begin(); itUserId != pRelation->end(); itUserId++)
					{
						if( *itUserId == _this->m_userId )
							continue;
						TCPForward::TCPUserMap::iterator itUser = _this->m_pTCPForward->m_users.find(*itUserId);
						if( itUser != _this->m_pTCPForward->m_users.end() )
						{
							TCPUser* pPeer = itUser->second;
							if(!pPeer->sendData(buffer, len+nHeaderSize))
							{
								pPeer->Destroy();
							}
						}
						else
						{
							_ASSERTE(FALSE);
						}
					}
				}
				else
				{
					_ASSERTE(FALSE);
				}*/
				break;
			}
		case FVS_TCP_USER:
			_this->sendData(buffer, sizeof(PACK_TCP));
			break;
		default:
			_ASSERTE(FALSE);
			break;
		}

        ::LeaveCriticalSection(&_this->m_pTCPForward->m_cs);

	}

	return 0;
}

bool TCPUser::sendData(char* buffer, int iLen)
{
	if( m_socket == 0 )
	{
		FVS_DEBUG1("Want to send data to user %I64d, but socket is not ready.", this->m_userId);
		return true;
	}

	DWORD byteSent = 0;
	DWORD ret;

	while ( iLen - byteSent > 0 )
	{
		ret = ::send(m_socket, buffer + byteSent, iLen - byteSent, 0);
		if( ret == SOCKET_ERROR )
		{
			return false;
		}
		byteSent += ret;
	}
	return true;
}

