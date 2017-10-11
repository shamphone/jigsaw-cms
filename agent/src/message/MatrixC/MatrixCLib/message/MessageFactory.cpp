#include "StdAfx.h"
#include "MessageFactory.h"
#include "BaseMessage.h"

///////////////////////////////////////////
// LyvcMessage Parser Generated header start
///////////////////////////////////////////
#include "LoginSuccessfully.h"
#include "KeepAlive.h"
#include "NotifyMediaServerUserQuitConference.h"
#include "NotifyMediaServerUserJoinConference.h"
#include "RegisterUserResponse.h"
#include "RegisterUser.h"
#include "ChangeUserInfo.h"
#include "SystemMessage.h"
#include "ConferenceNotice.h"
#include "DeleteConferenceUser.h"
#include "KickDuplicateLogin.h"
#include "ServerInfo.h"
#include "Advertizement.h"
#include "CopyContact.h"
#include "SetContactAdmin.h"
#include "SetUserPicture.h"
#include "EvaluateVideoQuality.h"
#include "CheckVersion.h"
#include "WritingMessage.h"
#include "YuntaiHolderBroadcast.h"
#include "CameraZoomOut.h"
#include "CameraZoomIn.h"
#include "CameraMoveDown.h"
#include "CameraMoveUp.h"
#include "CameraMoveRight.h"
#include "CameraMoveLeft.h"
#include "StopSpeak.h"
#include "AppointSpeaker.h"
#include "ApplySpeak.h"
#include "ListenToUser.h"
#include "WatchUser.h"
#include "RequestSendFile.h"
#include "StopReceiveFile.h"
#include "StopSendFile.h"
#include "SendFileData.h"
#include "ApplySendFile.h"
#include "RejectReceiveFile.h"
#include "AgreeReceiveFile.h"
#include "ConferenceFinish.h"
#include "ModifyConference.h"
#include "GetPendingMessageList.h"
#include "InstantConferenceModelId.h"
#include "MediaServerTCPForwardReady.h"
#include "KickUserFromConference.h"
#include "NotifyMediaServerRemoveDesktop.h"
#include "NotifyMediaServerAddDesktop.h"
#include "SearchContactResult.h"
#include "Leaveword.h"
#include "StartMarkWindow.h"
#include "CreateInstantConference.h"
#include "NotifyMediaServerUserJoin.h"
#include "InviteConference.h"
#include "UserDetail.h"
#include "DefaultGroupId.h"
#include "ChangeGroupName.h"
#include "UserNotFoundByEmail.h"
#include "AddContact.h"
#include "SearchContact.h"
#include "RemoveGroup.h"
#include "RemoveContact.h"
#include "RejectContact.h"
#include "MoveContactToGroup.h"
#include "ApplyContact.h"
#include "AgreeContact.h"
#include "AddGroup.h"
#include "UpdateContactStatus.h"
#include "UserChannelBroken.h"
#include "StopSendDesktop.h"
#include "StopSendControlDesktop.h"
#include "StopReceiveDesktop.h"
#include "StopReceiveControlDesktop.h"
#include "RejectInviteDesktop.h"
#include "RejectDesktop.h"
#include "RejectControlDesktop.h"
#include "InviteDesktop.h"
#include "ApplyDesktop.h"
#include "ApplyControlDesktop.h"
#include "AgreeInviteDesktop.h"
#include "AgreeDesktop.h"
#include "AgreeControlDesktop.h"
#include "DesktopClientKeyEvent.h"
#include "DesktopClientRequestUpdate.h"
#include "DesktopClientMouseEvent.h"
#include "ServerInternalError.h"
#include "StopReceiveVideo.h"
#include "StopSendVideo.h"
#include "RejectVideo.h"
#include "AgreeVideo.h"
#include "ApplyVideo.h"
#include "StopReceiveAudio.h"
#include "StopSendAudio.h"
#include "RejectAudio.h"
#include "AgreeAudio.h"
#include "ApplyAudio.h"
#include "MediaServerAddress.h"
#include "GetMediaServerAddress.h"
#include "UserJoinConference.h"
#include "Broadcast.h"
#include "Chat.h"
#include "AddConference.h"
#include "JoinConferenceResponse.h"
#include "JoinConference.h"
#include "QuitConference.h"
#include "DeleteConference.h"
#include "WebDeleteConference.h"
#include "WebAddConference.h"
#include "GetConferenceModelRoleTable.h"
#include "ConferenceModelRoleTableItem.h"
#include "ConferenceModelTableItem.h"
#include "GetConferenceModelTable.h"
#include "GetConferenceList.h"
#include "UserLoginResponse.h"
#include "UserQuitConference.h"
#include "NotifyMediaServerRemoveVideo.h"
#include "NotifyMediaServerAddVideo.h"
#include "NotifyMediaServerRemoveAudio.h"
#include "NotifyMediaServerAddAudio.h"
#include "NotifyMediaServerUserExit.h"
#include "MediaServerLogin.h"
#include "StartConference.h"
#include "Login.h"

///////////////////////////////////////////
// LyvcMessage Parser Generated header end
///////////////////////////////////////////

LyvcMessage::BaseMessage* LyvcMessage::MessageFactory::createMessage(string& message, int& messageId) {

    LyvcMessage::BaseMessage* pBaseMessage = NULL;

    // Get message id
    string tagContent = BaseMessage::getStringBetweenTag(message, "id");
    int id = atoi(tagContent.c_str());
    messageId = id;

    switch(id) {

        ///////////////////////////////////////////
        // LyvcMessage Parser Generated code start
        ///////////////////////////////////////////
		case 199:
			pBaseMessage = new LoginSuccessfully();
			pBaseMessage->fromXML(message);
			break;

		case 198:
			pBaseMessage = new KeepAlive();
			pBaseMessage->fromXML(message);
			break;

		case 197:
			pBaseMessage = new NotifyMediaServerUserQuitConference();
			pBaseMessage->fromXML(message);
			break;

		case 196:
			pBaseMessage = new NotifyMediaServerUserJoinConference();
			pBaseMessage->fromXML(message);
			break;

		case 195:
			pBaseMessage = new RegisterUserResponse();
			pBaseMessage->fromXML(message);
			break;

		case 194:
			pBaseMessage = new RegisterUser();
			pBaseMessage->fromXML(message);
			break;

		case 193:
			pBaseMessage = new ChangeUserInfo();
			pBaseMessage->fromXML(message);
			break;

		case 192:
			pBaseMessage = new SystemMessage();
			pBaseMessage->fromXML(message);
			break;

		case 191:
			pBaseMessage = new ConferenceNotice();
			pBaseMessage->fromXML(message);
			break;

		case 190:
			pBaseMessage = new DeleteConferenceUser();
			pBaseMessage->fromXML(message);
			break;

		case 189:
			pBaseMessage = new KickDuplicateLogin();
			pBaseMessage->fromXML(message);
			break;

		case 188:
			pBaseMessage = new ServerInfo();
			pBaseMessage->fromXML(message);
			break;

		case 187:
			pBaseMessage = new Advertizement();
			pBaseMessage->fromXML(message);
			break;

		case 186:
			pBaseMessage = new CopyContact();
			pBaseMessage->fromXML(message);
			break;

		case 185:
			pBaseMessage = new SetContactAdmin();
			pBaseMessage->fromXML(message);
			break;

		case 184:
			pBaseMessage = new SetUserPicture();
			pBaseMessage->fromXML(message);
			break;

		case 183:
			pBaseMessage = new EvaluateVideoQuality();
			pBaseMessage->fromXML(message);
			break;

		case 182:
			pBaseMessage = new CheckVersion();
			pBaseMessage->fromXML(message);
			break;

		case 181:
			pBaseMessage = new WritingMessage();
			pBaseMessage->fromXML(message);
			break;

		case 180:
			pBaseMessage = new YuntaiHolderBroadcast();
			pBaseMessage->fromXML(message);
			break;

		case 179:
			pBaseMessage = new CameraZoomOut();
			pBaseMessage->fromXML(message);
			break;

		case 178:
			pBaseMessage = new CameraZoomIn();
			pBaseMessage->fromXML(message);
			break;

		case 177:
			pBaseMessage = new CameraMoveDown();
			pBaseMessage->fromXML(message);
			break;

		case 176:
			pBaseMessage = new CameraMoveUp();
			pBaseMessage->fromXML(message);
			break;

		case 175:
			pBaseMessage = new CameraMoveRight();
			pBaseMessage->fromXML(message);
			break;

		case 174:
			pBaseMessage = new CameraMoveLeft();
			pBaseMessage->fromXML(message);
			break;

		case 173:
			pBaseMessage = new StopSpeak();
			pBaseMessage->fromXML(message);
			break;

		case 172:
			pBaseMessage = new AppointSpeaker();
			pBaseMessage->fromXML(message);
			break;

		case 171:
			pBaseMessage = new ApplySpeak();
			pBaseMessage->fromXML(message);
			break;

		case 170:
			pBaseMessage = new ListenToUser();
			pBaseMessage->fromXML(message);
			break;

		case 169:
			pBaseMessage = new WatchUser();
			pBaseMessage->fromXML(message);
			break;

		case 168:
			pBaseMessage = new RequestSendFile();
			pBaseMessage->fromXML(message);
			break;

		case 167:
			pBaseMessage = new StopReceiveFile();
			pBaseMessage->fromXML(message);
			break;

		case 166:
			pBaseMessage = new StopSendFile();
			pBaseMessage->fromXML(message);
			break;

		case 165:
			pBaseMessage = new SendFileData();
			pBaseMessage->fromXML(message);
			break;

		case 164:
			pBaseMessage = new ApplySendFile();
			pBaseMessage->fromXML(message);
			break;

		case 163:
			pBaseMessage = new RejectReceiveFile();
			pBaseMessage->fromXML(message);
			break;

		case 162:
			pBaseMessage = new AgreeReceiveFile();
			pBaseMessage->fromXML(message);
			break;

		case 157:
			pBaseMessage = new ConferenceFinish();
			pBaseMessage->fromXML(message);
			break;

		case 156:
			pBaseMessage = new ModifyConference();
			pBaseMessage->fromXML(message);
			break;

		case 155:
			pBaseMessage = new GetPendingMessageList();
			pBaseMessage->fromXML(message);
			break;

		case 154:
			pBaseMessage = new InstantConferenceModelId();
			pBaseMessage->fromXML(message);
			break;

		case 153:
			pBaseMessage = new MediaServerTCPForwardReady();
			pBaseMessage->fromXML(message);
			break;

		case 149:
			pBaseMessage = new KickUserFromConference();
			pBaseMessage->fromXML(message);
			break;

		case 148:
			pBaseMessage = new NotifyMediaServerRemoveDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 147:
			pBaseMessage = new NotifyMediaServerAddDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 146:
			pBaseMessage = new SearchContactResult();
			pBaseMessage->fromXML(message);
			break;

		case 144:
			pBaseMessage = new Leaveword();
			pBaseMessage->fromXML(message);
			break;

		case 142:
			pBaseMessage = new StartMarkWindow();
			pBaseMessage->fromXML(message);
			break;

		case 134:
			pBaseMessage = new CreateInstantConference();
			pBaseMessage->fromXML(message);
			break;

		case 133:
			pBaseMessage = new NotifyMediaServerUserJoin();
			pBaseMessage->fromXML(message);
			break;

		case 127:
			pBaseMessage = new InviteConference();
			pBaseMessage->fromXML(message);
			break;

		case 126:
			pBaseMessage = new UserDetail();
			pBaseMessage->fromXML(message);
			break;

		case 125:
			pBaseMessage = new DefaultGroupId();
			pBaseMessage->fromXML(message);
			break;

		case 123:
			pBaseMessage = new ChangeGroupName();
			pBaseMessage->fromXML(message);
			break;

		case 122:
			pBaseMessage = new UserNotFoundByEmail();
			pBaseMessage->fromXML(message);
			break;

		case 121:
			pBaseMessage = new AddContact();
			pBaseMessage->fromXML(message);
			break;

		case 120:
			pBaseMessage = new SearchContact();
			pBaseMessage->fromXML(message);
			break;

		case 119:
			pBaseMessage = new RemoveGroup();
			pBaseMessage->fromXML(message);
			break;

		case 118:
			pBaseMessage = new RemoveContact();
			pBaseMessage->fromXML(message);
			break;

		case 117:
			pBaseMessage = new RejectContact();
			pBaseMessage->fromXML(message);
			break;

		case 116:
			pBaseMessage = new MoveContactToGroup();
			pBaseMessage->fromXML(message);
			break;

		case 115:
			pBaseMessage = new ApplyContact();
			pBaseMessage->fromXML(message);
			break;

		case 114:
			pBaseMessage = new AgreeContact();
			pBaseMessage->fromXML(message);
			break;

		case 113:
			pBaseMessage = new AddGroup();
			pBaseMessage->fromXML(message);
			break;

		case 112:
			pBaseMessage = new UpdateContactStatus();
			pBaseMessage->fromXML(message);
			break;

		case 111:
			pBaseMessage = new UserChannelBroken();
			pBaseMessage->fromXML(message);
			break;

		case 110:
			pBaseMessage = new StopSendDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 109:
			pBaseMessage = new StopSendControlDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 108:
			pBaseMessage = new StopReceiveDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 107:
			pBaseMessage = new StopReceiveControlDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 106:
			pBaseMessage = new RejectInviteDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 105:
			pBaseMessage = new RejectDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 104:
			pBaseMessage = new RejectControlDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 103:
			pBaseMessage = new InviteDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 102:
			pBaseMessage = new ApplyDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 101:
			pBaseMessage = new ApplyControlDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 100:
			pBaseMessage = new AgreeInviteDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 99:
			pBaseMessage = new AgreeDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 98:
			pBaseMessage = new AgreeControlDesktop();
			pBaseMessage->fromXML(message);
			break;

		case 94:
			pBaseMessage = new DesktopClientKeyEvent();
			pBaseMessage->fromXML(message);
			break;

		case 88:
			pBaseMessage = new DesktopClientRequestUpdate();
			pBaseMessage->fromXML(message);
			break;

		case 86:
			pBaseMessage = new DesktopClientMouseEvent();
			pBaseMessage->fromXML(message);
			break;

		case 82:
			pBaseMessage = new ServerInternalError();
			pBaseMessage->fromXML(message);
			break;

		case 81:
			pBaseMessage = new StopReceiveVideo();
			pBaseMessage->fromXML(message);
			break;

		case 80:
			pBaseMessage = new StopSendVideo();
			pBaseMessage->fromXML(message);
			break;

		case 79:
			pBaseMessage = new RejectVideo();
			pBaseMessage->fromXML(message);
			break;

		case 78:
			pBaseMessage = new AgreeVideo();
			pBaseMessage->fromXML(message);
			break;

		case 77:
			pBaseMessage = new ApplyVideo();
			pBaseMessage->fromXML(message);
			break;

		case 76:
			pBaseMessage = new StopReceiveAudio();
			pBaseMessage->fromXML(message);
			break;

		case 75:
			pBaseMessage = new StopSendAudio();
			pBaseMessage->fromXML(message);
			break;

		case 74:
			pBaseMessage = new RejectAudio();
			pBaseMessage->fromXML(message);
			break;

		case 73:
			pBaseMessage = new AgreeAudio();
			pBaseMessage->fromXML(message);
			break;

		case 72:
			pBaseMessage = new ApplyAudio();
			pBaseMessage->fromXML(message);
			break;

		case 71:
			pBaseMessage = new MediaServerAddress();
			pBaseMessage->fromXML(message);
			break;

		case 70:
			pBaseMessage = new GetMediaServerAddress();
			pBaseMessage->fromXML(message);
			break;

		case 69:
			pBaseMessage = new UserJoinConference();
			pBaseMessage->fromXML(message);
			break;

		case 68:
			pBaseMessage = new Broadcast();
			pBaseMessage->fromXML(message);
			break;

		case 67:
			pBaseMessage = new Chat();
			pBaseMessage->fromXML(message);
			break;

		case 65:
			pBaseMessage = new AddConference();
			pBaseMessage->fromXML(message);
			break;

		case 64:
			pBaseMessage = new JoinConferenceResponse();
			pBaseMessage->fromXML(message);
			break;

		case 63:
			pBaseMessage = new JoinConference();
			pBaseMessage->fromXML(message);
			break;

		case 62:
			pBaseMessage = new QuitConference();
			pBaseMessage->fromXML(message);
			break;

		case 61:
			pBaseMessage = new DeleteConference();
			pBaseMessage->fromXML(message);
			break;

		case 60:
			pBaseMessage = new WebDeleteConference();
			pBaseMessage->fromXML(message);
			break;

		case 58:
			pBaseMessage = new WebAddConference();
			pBaseMessage->fromXML(message);
			break;

		case 55:
			pBaseMessage = new GetConferenceModelRoleTable();
			pBaseMessage->fromXML(message);
			break;

		case 54:
			pBaseMessage = new ConferenceModelRoleTableItem();
			pBaseMessage->fromXML(message);
			break;

		case 53:
			pBaseMessage = new ConferenceModelTableItem();
			pBaseMessage->fromXML(message);
			break;

		case 52:
			pBaseMessage = new GetConferenceModelTable();
			pBaseMessage->fromXML(message);
			break;

		case 51:
			pBaseMessage = new GetConferenceList();
			pBaseMessage->fromXML(message);
			break;

		case 50:
			pBaseMessage = new UserLoginResponse();
			pBaseMessage->fromXML(message);
			break;

		case 48:
			pBaseMessage = new UserQuitConference();
			pBaseMessage->fromXML(message);
			break;

		case 45:
			pBaseMessage = new NotifyMediaServerRemoveVideo();
			pBaseMessage->fromXML(message);
			break;

		case 44:
			pBaseMessage = new NotifyMediaServerAddVideo();
			pBaseMessage->fromXML(message);
			break;

		case 43:
			pBaseMessage = new NotifyMediaServerRemoveAudio();
			pBaseMessage->fromXML(message);
			break;

		case 42:
			pBaseMessage = new NotifyMediaServerAddAudio();
			pBaseMessage->fromXML(message);
			break;

		case 41:
			pBaseMessage = new NotifyMediaServerUserExit();
			pBaseMessage->fromXML(message);
			break;

		case 38:
			pBaseMessage = new MediaServerLogin();
			pBaseMessage->fromXML(message);
			break;

		case 37:
			pBaseMessage = new StartConference();
			pBaseMessage->fromXML(message);
			break;

		case 35:
			pBaseMessage = new Login();
			pBaseMessage->fromXML(message);
			break;



        ///////////////////////////////////////////
        // LyvcMessage Parser Generated code end
        ///////////////////////////////////////////

        default:
            return NULL;
    }

    return pBaseMessage;
}
