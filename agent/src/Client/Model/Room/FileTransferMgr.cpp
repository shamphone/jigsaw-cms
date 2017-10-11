
#include "stdafx.h"
#include "Flvcc.h"
#include "FileTransferMgr.h"
#include ".\filetransfermgr.h"
#include "UI\meetingroom\roommainfrm.h"
#include "UI\meetingroom\FileTransferDlg.h"
#include "UI\Mainfrm\UserDefinedMessage.h"
#include "Common\common\Base64coder\Base64Coder.h"
#include "Common\Common\PathHelper\PathHelper.h"
#include "Model\Room\RunningConference.h"
#include "Desktop\zlib\zlib.h"

#include "message/MatrixC/MatrixCLib/MatrixC.h"
#include "message/MatrixC/MatrixCLib/message/ApplySendFile.h"
#include "message/MatrixC/MatrixCLib/message/RejectReceiveFile.h"
#include "message/MatrixC/MatrixCLib/message/AgreeReceiveFile.h"
#include "message/MatrixC/MatrixCLib/message/StopSendFile.h"
#include "message/MatrixC/MatrixCLib/message/StopReceiveFile.h"
#include "message/MatrixC/MatrixCLib/message/SendFileData.h"
#include "message/MatrixC/MatrixCLib/message/RequestSendFile.h"
#include "message/MatrixC/MatrixCLib/message/UserChannelBroken.h"
#include "message/MatrixC/MatrixCLib/message/UserQuitConference.h"

#define FILE_DATA_BUFFER_SIZE	4096


FileTransferMgr::FileTransferMgr(LyvcMessage::MatrixC* pMatrixC, CMeetingRoomFrame* pRoom, RunningConference* pConference) : LyvcMessage::MessageTarget(pMatrixC)
{
	m_pConference = pConference;
	m_pRoom = pRoom;
	m_bIsReceiving = false;
	m_bIsSending = false;
	m_bStopCurrentSend = false;
	m_nThreadId = 0;
	m_bExitSendThread = false;
	m_hEvent = CreateEvent( NULL, FALSE, FALSE, NULL );

    m_pMatrixC->registerMessageHandler(LyvcMessage::ApplySendFile::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::ApplySendFile));
	m_pMatrixC->registerMessageHandler(LyvcMessage::RejectReceiveFile::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::RejectReceiveFile));
	m_pMatrixC->registerMessageHandler(LyvcMessage::AgreeReceiveFile::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::AgreeReceiveFile));
	m_pMatrixC->registerMessageHandler(LyvcMessage::StopSendFile::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::StopSendFile));
	m_pMatrixC->registerMessageHandler(LyvcMessage::StopReceiveFile::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::StopReceiveFile));
	m_pMatrixC->registerMessageHandler(LyvcMessage::SendFileData::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::SendFileData));
	m_pMatrixC->registerMessageHandler(LyvcMessage::RequestSendFile::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::RequestSendFile));
	m_pMatrixC->registerMessageHandler(LyvcMessage::UserChannelBroken::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::UserChannelBroken));
	m_pMatrixC->registerMessageHandler(LyvcMessage::UserQuitConference::id, this, static_cast<LyvcMessage::PMSG_HANDLER>(FileTransferMgr::UserQuitConference));
}

FileTransferMgr::~FileTransferMgr(void)
{
	CloseHandle( m_hEvent );
	m_pMatrixC->removeObjectMessageHandler(this);
}

bool FileTransferMgr::create()
{
	return true;
}

void FileTransferMgr::destroy()
{
    for( UINT i = 0; i < m_sendList.size(); i++ )
	{
		stopSendFile( m_sendList.at(i).m_uid, m_sendList.at(i).m_fileName );
	}
	if( this->m_curRecvTask.m_uid != 0 )
	{
		stopReceiveFile( m_curRecvTask.m_uid, m_curRecvTask.m_storeName );
	}
	for( UINT i = 0; i < m_recvList.size(); i++ )
	{
		stopReceiveFile( m_recvList.at(i).m_uid, m_recvList.at(i).m_storeName );
	}
}

void FileTransferMgr::applySendFile(__int64 receiverId, string fileName, __int64 fileLength)
{
	LyvcMessage::ApplySendFile msg;
	msg.receiverId = receiverId;
	msg.fullFileName = fileName;
	msg.fileLength = fileLength;
	this->m_pMatrixC->sendMessage(&msg);

	FileTransferTask ftt;
	ftt.m_fileName = fileName;
	ftt.m_uid = receiverId;
	ftt.m_fileLength = fileLength;

	m_sendList.push_back( ftt );
}

void FileTransferMgr::rejectReceiveFile(__int64 receiverId, string fileName)
{
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( receiverId == m_applyList.at(i).m_uid 
			&& fileName == m_applyList.at(i).m_fileName
			&& m_applyList.at(i).m_bCanSend == true )
		{
			LyvcMessage::RejectReceiveFile msg;
			msg.fullFileName = fileName;
			msg.receiverId = receiverId;
			this->m_pMatrixC->sendMessage(&msg);
			break;
		}
	}
    // 从申请队列中清除
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( receiverId == m_applyList.at(i).m_uid && fileName == m_applyList.at(i).m_fileName )
		{
			m_applyList.erase( m_applyList.begin() + i );
			break;
		}
	}
}

void FileTransferMgr::agreeReceiveFile(__int64 receiverId, string fileName, __int64 fileLength)
{
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( receiverId == m_applyList.at(i).m_uid 
			&& fileName == m_applyList.at(i).m_fileName
			&& m_applyList.at(i).m_bCanSend == true )
		{
			LyvcMessage::AgreeReceiveFile msg;
			msg.receiverId = receiverId;
			msg.fullFileName = fileName;
			this->m_pMatrixC->sendMessage(&msg);

			CString shortName = fileName.c_str();
			shortName = shortName.Right( shortName.GetLength() - shortName.ReverseFind('\\') - 1 );
			string storeName = this->getIncomeFileSavePosition() + "\\" + shortName.GetString();
			m_pRoom->getRecvFileDlg()->notifyApplySendFile( receiverId, m_pConference->getUserRealName( receiverId ), storeName, fileLength );

			FileTransferTask ftt;
			ftt.m_fileName = fileName;
			ftt.m_uid = receiverId;
			ftt.m_fileLength = fileLength;
			ftt.m_storeName = storeName;
			m_recvList.push_back( ftt );
			this->recvNextFile();
		}
	}
    // 从申请队列中清除
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( receiverId == m_applyList.at(i).m_uid && fileName == m_applyList.at(i).m_fileName )
		{
			m_applyList.erase( m_applyList.begin() + i );
			break;
		}
	}
}

void FileTransferMgr::stopSendFile(__int64 receiverId, string fileName)
{
	if( this->m_curSendTask.m_uid == receiverId && this->m_curSendTask.m_fileName == fileName )
	{
		this->m_bStopCurrentSend = true;
		LyvcMessage::StopSendFile msg;
		msg.receiverId = receiverId;
		msg.fullFileName = fileName;
		this->m_pMatrixC->sendMessage(&msg);
	}
	else
	{
		for( UINT i = 0; i < m_sendList.size(); i++ )
		{
			if( receiverId == m_sendList.at(i).m_uid && fileName == m_sendList.at(i).m_fileName )
			{
				m_sendList.erase( m_sendList.begin() + i );
				LyvcMessage::StopSendFile msg;
				msg.receiverId = receiverId;
				msg.fullFileName = fileName;
				this->m_pMatrixC->sendMessage(&msg);
				break;
			}
		}
	}

}

void FileTransferMgr::stopReceiveFile(__int64 receiverId, string fileName)
{
	LyvcMessage::StopReceiveFile msg;
	msg.receiverId = receiverId;
	if( receiverId == m_curRecvTask.m_uid && fileName == m_curRecvTask.m_storeName )
	{
		msg.fullFileName = m_curRecvTask.m_fileName;
		m_bIsReceiving = false;
		m_curRecvTask.m_uid = 0;
	}
	else
	{
		for( UINT i = 0; i < m_recvList.size(); i++ )
		{
			if( receiverId == m_recvList.at(i).m_uid && fileName == m_recvList.at(i).m_storeName )
			{
				msg.fullFileName = m_recvList.at(i).m_fileName;
				m_recvList.erase( m_recvList.begin() + i );
				break;
			}
		}
	}
	this->m_pMatrixC->sendMessage(&msg);
}

void FileTransferMgr::requestSendFile(__int64 receiverId, string fileName)
{
	LyvcMessage::RequestSendFile msg;
	msg.receiverId = receiverId;
	msg.fullFileName = fileName;
	this->m_pMatrixC->sendMessage( &msg );
}

void FileTransferMgr::ApplySendFile(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::ApplySendFile* pMsg = (LyvcMessage::ApplySendFile*) pBaseMessage;

	// 判断是否已在申请队列中
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( pMsg->_senderId == m_applyList.at(i).m_uid && pMsg->fullFileName == m_applyList.at(i).m_fileName )
		{
			//是的话不再弹出提示框,
			m_applyList.at(i).m_bCanSend = true;
			return;
		}
	}
	//否则加入队列
	FileTransferTask ftt;
	ftt.m_fileName = pMsg->fullFileName;
	ftt.m_uid = pMsg->_senderId;
	ftt.m_fileLength = pMsg->fileLength;
	ftt.m_bCanSend = true;
	m_applyList.push_back( ftt );

	FileTransferTask* pFtt = new FileTransferTask();
	*pFtt = ftt;
	::PostMessage( m_pConference->getRoom()->GetSafeHwnd(), WM_APPLY_SENDFILE, (WPARAM) pFtt, 0 );
}

void FileTransferMgr::RejectReceiveFile(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::RejectReceiveFile* pMsg = (LyvcMessage::RejectReceiveFile*) pBaseMessage;
	for( UINT i = 0; i < m_sendList.size(); i++ )
	{
		if( pMsg->_senderId == m_sendList.at(i).m_uid && pMsg->fullFileName == m_sendList.at(i).m_fileName )
		{
			m_sendList.erase( m_sendList.begin() + i );
			m_pRoom->getSendFileDlg()->notifyRejectReceiveFile(pMsg->_senderId, pMsg->fullFileName);
			break;
		}
	}
}

void FileTransferMgr::AgreeReceiveFile(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::AgreeReceiveFile* pMsg = (LyvcMessage::AgreeReceiveFile*) pBaseMessage;
	m_pRoom->getSendFileDlg()->notifyAgreeReceiveFile(pMsg->_senderId, pMsg->fullFileName);
}

void FileTransferMgr::StopSendFile(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::StopSendFile* pMsg = (LyvcMessage::StopSendFile*) pBaseMessage;
	CString fileName = pMsg->fullFileName.c_str();
	fileName = fileName.Right(fileName.GetLength() - fileName.ReverseFind('\\') - 1);
	if( (pMsg->_senderId == m_curRecvTask.m_uid) && (pMsg->fullFileName == m_curRecvTask.m_fileName) )
	{
		m_pRoom->getRecvFileDlg()->notifyStopSendFile( pMsg->_senderId, m_curRecvTask.m_storeName );
		m_curRecvTask.m_uid = 0;
		m_bIsReceiving = false;
		this->recvNextFile();
	}
	else
	{
		for( UINT i = 0; i < m_recvList.size(); i++ )
		{
			if( (pMsg->_senderId == m_recvList.at(i).m_uid) && (pMsg->fullFileName == m_recvList.at(i).m_fileName) )
			{
				m_recvList.erase( m_recvList.begin() + i );
				m_pRoom->getRecvFileDlg()->notifyStopSendFile( pMsg->_senderId, m_recvList.at(i).m_storeName );
				break;
			}
		}
	}
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( pMsg->_senderId == m_applyList.at(i).m_uid && pMsg->fullFileName == m_applyList.at(i).m_fileName )
		{
			m_applyList.at(i).m_bCanSend = false;
			break;
		}
	}
}

void FileTransferMgr::StopReceiveFile(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::StopReceiveFile* pMsg = (LyvcMessage::StopReceiveFile*) pBaseMessage;
	if( pMsg->_senderId == m_curSendTask.m_uid && pMsg->fullFileName == m_curSendTask.m_fileName )
	{
		this->m_bStopCurrentSend = true;
	}
	else
	{
		for( UINT i = 0; i < m_sendList.size(); i++ )
		{
			if( (pMsg->_senderId == m_sendList.at(i).m_uid) && (pMsg->fullFileName == m_sendList.at(i).m_fileName) )
			{
				m_sendList.erase( m_sendList.begin() + i );
				break;
			}
		}
	}
	m_pRoom->getSendFileDlg()->notifyStopRecvFile(pMsg->_senderId, pMsg->fullFileName);
}

void FileTransferMgr::SendFileData(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::SendFileData* pMsg = (LyvcMessage::SendFileData*) pBaseMessage;

	if( (pMsg->_senderId == m_curRecvTask.m_uid) && (pMsg->fullFileName == m_curRecvTask.m_fileName) )
	{
		FILE* file;
		if( pMsg->isFirstSend )
		{
			m_pRoom->getRecvFileDlg()->notifyRecvingFile( pMsg->_senderId, m_curRecvTask.m_storeName );
			file = fopen( m_curRecvTask.m_storeName.c_str(), "wb");
		}
		else
		{
			file = fopen( m_curRecvTask.m_storeName.c_str(), "a+b");
		}
		//Base64解码
		Base64Coder decoder;
		int len = pMsg->fileData.length();
		decoder.Decode((const PBYTE)pMsg->fileData.c_str(), len);
		string fileData;
		fileData.append(decoder.DecodedMessage(), pMsg->compressedLength);
		//解压缩
		unsigned char* pUncomprBuff = new unsigned char[FILE_DATA_BUFFER_SIZE];
		ULONG uncomprLen = FILE_DATA_BUFFER_SIZE;
		int err = uncompress( (Byte*)pUncomprBuff, &uncomprLen, (const Bytef*)fileData.data(), fileData.length() );
		if( err != Z_OK )
		{
			TRACE1("uncompress error, error code %d\n", err);
		}

		//写入文件
		if( file != NULL )
		{
            fwrite( pUncomprBuff, sizeof(char), uncomprLen, file);
			fclose(file);
		}
		delete pUncomprBuff;
		//更新界面进度条
		m_pRoom->getRecvFileDlg()->notifySendFileData( pMsg->_senderId, m_curRecvTask.m_storeName, pMsg->dataLength);
		if( pMsg->isFinished )
		{
			m_pRoom->getRecvFileDlg()->notifyReceiveComplete( pMsg->_senderId, m_curRecvTask.m_storeName );
			m_curRecvTask.m_uid = 0;
			m_bIsReceiving = false;
			this->recvNextFile();
		}
	}
}

void FileTransferMgr::RequestSendFile(LyvcMessage::BaseMessage* pBaseMessage)
{
    LyvcMessage::RequestSendFile* pMsg = (LyvcMessage::RequestSendFile*) pBaseMessage;
	for( UINT i = 0; i < m_sendList.size(); i++ )
	{
		if( (pMsg->_senderId == m_sendList.at(i).m_uid) && (pMsg->fullFileName == m_sendList.at(i).m_fileName) )
		{
			m_sendList.at(i).m_bCanSend = true;
			if( m_nThreadId == 0 )
			{
				m_hSendThread = (HANDLE)::_beginthreadex(NULL, 0, sendFileThreadProc, this, 0, &m_nThreadId); 
			}
			if( !m_bIsSending )
			{
				sendNextFile();
			}
			break;
		}
	}
}

void FileTransferMgr::UserChannelBroken(LyvcMessage::BaseMessage* pBaseMessage)
{
	UINT i;
	LyvcMessage::UserChannelBroken* pMsg = (LyvcMessage::UserChannelBroken*) pBaseMessage;
	
	for( i = 0; i < m_sendList.size(); )
	{
		if( m_sendList[i].m_uid == pMsg->userId )
		{
			m_pRoom->getSendFileDlg()->notifyStopRecvFile( m_sendList[i].m_uid, m_sendList[i].m_fileName );
			m_sendList.erase( m_sendList.begin() + i );
		}
		else
			i++;
	}
	if( m_curSendTask.m_uid == pMsg->userId )
	{
		m_bStopCurrentSend = true;
		m_pRoom->getSendFileDlg()->notifyStopRecvFile( m_curSendTask.m_uid, m_curSendTask.m_fileName );
	}
	for( i = 0; i < m_recvList.size(); )
	{
		if( m_recvList[i].m_uid == pMsg->userId )
		{
			m_pRoom->getRecvFileDlg()->notifyStopSendFile( m_recvList[i].m_uid, m_recvList[i].m_storeName );
			m_recvList.erase( m_recvList.begin() + i );
		}
		else
			i++;
	}
	if( m_curRecvTask.m_uid == pMsg->userId )
	{
		m_bIsReceiving = false;
		m_pRoom->getRecvFileDlg()->notifyStopSendFile( m_curRecvTask.m_uid, m_curRecvTask.m_storeName );
		recvNextFile();
	}
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( pMsg->userId == m_applyList.at(i).m_uid )
		{
			m_applyList.at(i).m_bCanSend = false;			
		}
	}
}

void FileTransferMgr::UserQuitConference(LyvcMessage::BaseMessage* pBaseMessage)
{
	LyvcMessage::UserQuitConference* pMsg = (LyvcMessage::UserQuitConference*) pBaseMessage;
	unsigned int i;
	for( i = 0; i < m_sendList.size(); )
	{
		if( m_sendList[i].m_uid == pMsg->userId )
		{
			m_pRoom->getSendFileDlg()->notifyStopRecvFile( m_sendList[i].m_uid, m_sendList[i].m_fileName );
			m_sendList.erase( m_sendList.begin() + i );
		}
		else
			i++;
	}
	for( UINT i = 0; i < m_applyList.size(); i++ )
	{
		if( pMsg->userId == m_applyList.at(i).m_uid )
		{
			m_applyList.at(i).m_bCanSend = false;			
		}
	}
}

const string FileTransferMgr::getIncomeFileSavePosition()
{
	char buffer[1024];
	char* g_IniFileName = ::GetApp()->getIniFilename();
	string s;
	::GetPrivateProfileString("OPTION", "recv_path", "", buffer, sizeof(buffer), g_IniFileName);
	if( strlen(buffer) == 0 )
	{
		s.append(PathHelper::getDefaultReceivedFilePath());
	}
	else
	{
		s.append(buffer, strlen(buffer));
	}
	return s;
}

void FileTransferMgr::sendNextFile()
{
	for( UINT i = 0; i < m_sendList.size(); i++ )
	{
		if( m_sendList.at(i).m_bCanSend == true )
		{
			m_curSendTask = m_sendList.at( i );
			m_sendList.erase( m_sendList.begin() + i );
			m_bStopCurrentSend = false;
			m_bIsSending = true;
			SetEvent( m_hEvent );
			return;
		}
	}
	m_bIsSending = false;
}

void FileTransferMgr::recvNextFile()
{
	if( !m_bIsReceiving )
	{
		if( m_recvList.size() > 0 )
		{
			m_curRecvTask = m_recvList.at(0);
			m_recvList.erase( m_recvList.begin() );
			requestSendFile( m_curRecvTask.m_uid, m_curRecvTask.m_fileName );
			m_pRoom->getRecvFileDlg()->setFileLength( m_curRecvTask.m_fileLength );
			m_bIsReceiving = true;
		}
		else
		{
			m_pRoom->getRecvFileDlg()->setFileLength( 0 );
		}
	}
}

void FileTransferMgr::exitSendFileThread()
{
	if( m_nThreadId != 0 )
	{
		m_bExitSendThread = true;
		m_bStopCurrentSend = true;
		SetEvent( m_hEvent );
		DWORD ret = ::WaitForSingleObject( m_hSendThread, INFINITE );
		if( ret == WAIT_OBJECT_0 )
		{
			CloseHandle( m_hSendThread );
		}
	}
}

UINT WINAPI FileTransferMgr::sendFileThreadProc(LPVOID lpParameter)
{
	TRACE0("send file thread created.\n");
	FileTransferMgr* _this = (FileTransferMgr*) lpParameter;
	unsigned char* pComprBuff = new unsigned char[FILE_DATA_BUFFER_SIZE*2];

	while( true )
	{
		WaitForSingleObject( _this->m_hEvent, INFINITE );
		if( _this->m_bExitSendThread )
			break;

		FILE* file = NULL;
		string str = "";
		string strEncoded = "";
		bool bFirst = true;
		LyvcMessage::SendFileData msg;
		msg.isFinished = false;
		msg.receiverId = _this->m_curSendTask.m_uid;
		msg.fullFileName = _this->m_curSendTask.m_fileName;

		//打开文件
		file = fopen( msg.fullFileName.c_str(), "rb" );
		if( file == NULL ) 
		{
			_this->stopSendFile( msg.receiverId, msg.fullFileName );
			_this->m_pRoom->getSendFileDlg()->notifyOpenFileError( msg.receiverId, msg.fullFileName );
			PostMessage( _this->m_pRoom->getSendFileDlg()->GetSafeHwnd(), WM_SEND_NEXT_FILE, 0, 0 );
			continue;
		}
		char buffer[FILE_DATA_BUFFER_SIZE];

		Base64Coder encoder;
		//读取并发送文件
		size_t count = 0;
		while( !feof(file) )
		{
			if( _this->m_bStopCurrentSend )
			{
				_this->stopSendFile( msg.receiverId, msg.fullFileName );
				break;
			}
	
			count = fread( buffer, sizeof(char), FILE_DATA_BUFFER_SIZE, file );
			if( ferror(file) )
			{
				_this->stopSendFile( msg.receiverId, msg.fullFileName );
				if( !_this->m_bExitSendThread )
					_this->m_pRoom->getSendFileDlg()->notifyOpenFileError( msg.receiverId, msg.fullFileName );
				break;
			}
			//压缩
			ULONG comprLen = FILE_DATA_BUFFER_SIZE*2;
			int err = compress( (Bytef*)pComprBuff, &comprLen, (const Bytef*)buffer, count );
			if( err != Z_OK )
			{
				TRACE1("compress error, error code %d\n", err);
			}
			str.append( (const char*)pComprBuff, comprLen );
			msg.compressedLength = comprLen;

			//Base64编码
			int len = str.length();
			encoder.Encode( (const PBYTE)str.c_str(), len );
			strEncoded = encoder.EncodedMessage();
			str = "";
			//发送
			if( bFirst )
			{
				bFirst = false;
				msg.isFirstSend = true;
				if( !_this->m_bExitSendThread )
				{
					_this->m_pRoom->getSendFileDlg()->setFileLength( _this->m_curSendTask.m_fileLength );
					_this->m_pRoom->getSendFileDlg()->notifySendingFile( msg.receiverId, msg.fullFileName );
				}
			}
			else
			{
				msg.isFirstSend = false;	
			}
			if( count < FILE_DATA_BUFFER_SIZE )
			{
				msg.isFinished = true;
				if( !_this->m_bExitSendThread )
					_this->m_pRoom->getSendFileDlg()->notifySendComplete( msg.receiverId, msg.fullFileName );
			}
			msg.fileData = strEncoded;
			msg.dataLength = count;
			if( !_this->m_bExitSendThread )
			{
				_this->m_pMatrixC->sendMessage( &msg );
				_this->m_pRoom->getSendFileDlg()->notifySendFileData( msg.receiverId, msg.fullFileName, count );
				Sleep(100);
			}
		}
		//关闭文件
		fclose(file);
		if( !_this->m_bExitSendThread )
			PostMessage( _this->m_pRoom->getSendFileDlg()->GetSafeHwnd(), WM_SEND_NEXT_FILE, 0, 0 );
	}
	delete pComprBuff;
	TRACE0("exit send file thread.\n");
	return 0;
}
