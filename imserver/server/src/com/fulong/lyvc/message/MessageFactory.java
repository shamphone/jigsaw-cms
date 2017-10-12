package com.fulong.lyvc.message;

import org.apache.commons.logging.Log;import org.apache.commons.logging.LogFactory;

/**
 * MessageFactory
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-10
 */

public class MessageFactory {

    // Log
    static Log logger = LogFactory.getLog(MessageFactory.class);

    public static BaseMessage getMessage(String xmlString, IdWrapper messageId) throws MessageFormatException {

        BaseMessage baseMessage = null;

        // Get message id
        String tagContent = BaseMessage.getStringBetweenTag(xmlString, "id");
        int id = Integer.parseInt(tagContent);
        messageId.setId(id);

        switch(id) {

            ///////////////////////////////////////////
            // LyvcMessage Parser Generated code start
            ///////////////////////////////////////////
		case 199:
			baseMessage = new LoginSuccessfully();
			baseMessage.fromXML(xmlString);
			break;

		case 198:
			baseMessage = new KeepAlive();
			baseMessage.fromXML(xmlString);
			break;

		case 197:
			baseMessage = new NotifyMediaServerUserQuitConference();
			baseMessage.fromXML(xmlString);
			break;

		case 196:
			baseMessage = new NotifyMediaServerUserJoinConference();
			baseMessage.fromXML(xmlString);
			break;

		case 195:
			baseMessage = new RegisterUserResponse();
			baseMessage.fromXML(xmlString);
			break;

		case 194:
			baseMessage = new RegisterUser();
			baseMessage.fromXML(xmlString);
			break;

		case 193:
			baseMessage = new ChangeUserInfo();
			baseMessage.fromXML(xmlString);
			break;

		case 192:
			baseMessage = new SystemMessage();
			baseMessage.fromXML(xmlString);
			break;

		case 191:
			baseMessage = new ConferenceNotice();
			baseMessage.fromXML(xmlString);
			break;

		case 190:
			baseMessage = new DeleteConferenceUser();
			baseMessage.fromXML(xmlString);
			break;

		case 189:
			baseMessage = new KickDuplicateLogin();
			baseMessage.fromXML(xmlString);
			break;

		case 188:
			baseMessage = new ServerInfo();
			baseMessage.fromXML(xmlString);
			break;

		case 187:
			baseMessage = new Advertizement();
			baseMessage.fromXML(xmlString);
			break;

		case 186:
			baseMessage = new CopyContact();
			baseMessage.fromXML(xmlString);
			break;

		case 185:
			baseMessage = new SetContactAdmin();
			baseMessage.fromXML(xmlString);
			break;

		case 184:
			baseMessage = new SetUserPicture();
			baseMessage.fromXML(xmlString);
			break;

		case 183:
			baseMessage = new EvaluateVideoQuality();
			baseMessage.fromXML(xmlString);
			break;

		case 182:
			baseMessage = new CheckVersion();
			baseMessage.fromXML(xmlString);
			break;

		case 181:
			baseMessage = new WritingMessage();
			baseMessage.fromXML(xmlString);
			break;

		case 180:
			baseMessage = new YuntaiHolderBroadcast();
			baseMessage.fromXML(xmlString);
			break;

		case 179:
			baseMessage = new CameraZoomOut();
			baseMessage.fromXML(xmlString);
			break;

		case 178:
			baseMessage = new CameraZoomIn();
			baseMessage.fromXML(xmlString);
			break;

		case 177:
			baseMessage = new CameraMoveDown();
			baseMessage.fromXML(xmlString);
			break;

		case 176:
			baseMessage = new CameraMoveUp();
			baseMessage.fromXML(xmlString);
			break;

		case 175:
			baseMessage = new CameraMoveRight();
			baseMessage.fromXML(xmlString);
			break;

		case 174:
			baseMessage = new CameraMoveLeft();
			baseMessage.fromXML(xmlString);
			break;

		case 173:
			baseMessage = new StopSpeak();
			baseMessage.fromXML(xmlString);
			break;

		case 172:
			baseMessage = new AppointSpeaker();
			baseMessage.fromXML(xmlString);
			break;

		case 171:
			baseMessage = new ApplySpeak();
			baseMessage.fromXML(xmlString);
			break;

		case 170:
			baseMessage = new ListenToUser();
			baseMessage.fromXML(xmlString);
			break;

		case 169:
			baseMessage = new WatchUser();
			baseMessage.fromXML(xmlString);
			break;

		case 168:
			baseMessage = new RequestSendFile();
			baseMessage.fromXML(xmlString);
			break;

		case 167:
			baseMessage = new StopReceiveFile();
			baseMessage.fromXML(xmlString);
			break;

		case 166:
			baseMessage = new StopSendFile();
			baseMessage.fromXML(xmlString);
			break;

		case 165:
			baseMessage = new SendFileData();
			baseMessage.fromXML(xmlString);
			break;

		case 164:
			baseMessage = new ApplySendFile();
			baseMessage.fromXML(xmlString);
			break;

		case 163:
			baseMessage = new RejectReceiveFile();
			baseMessage.fromXML(xmlString);
			break;

		case 162:
			baseMessage = new AgreeReceiveFile();
			baseMessage.fromXML(xmlString);
			break;

		case 157:
			baseMessage = new ConferenceFinish();
			baseMessage.fromXML(xmlString);
			break;

		case 156:
			baseMessage = new ModifyConference();
			baseMessage.fromXML(xmlString);
			break;

		case 155:
			baseMessage = new GetPendingMessageList();
			baseMessage.fromXML(xmlString);
			break;

		case 154:
			baseMessage = new InstantConferenceModelId();
			baseMessage.fromXML(xmlString);
			break;

		case 153:
			baseMessage = new MediaServerTCPForwardReady();
			baseMessage.fromXML(xmlString);
			break;

		case 149:
			baseMessage = new KickUserFromConference();
			baseMessage.fromXML(xmlString);
			break;

		case 148:
			baseMessage = new NotifyMediaServerRemoveDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 147:
			baseMessage = new NotifyMediaServerAddDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 146:
			baseMessage = new SearchContactResult();
			baseMessage.fromXML(xmlString);
			break;

		case 144:
			baseMessage = new Leaveword();
			baseMessage.fromXML(xmlString);
			break;

		case 142:
			baseMessage = new StartMarkWindow();
			baseMessage.fromXML(xmlString);
			break;

		case 134:
			baseMessage = new CreateInstantConference();
			baseMessage.fromXML(xmlString);
			break;

		case 133:
			baseMessage = new NotifyMediaServerUserJoin();
			baseMessage.fromXML(xmlString);
			break;

		case 127:
			baseMessage = new InviteConference();
			baseMessage.fromXML(xmlString);
			break;

		case 126:
			baseMessage = new UserDetail();
			baseMessage.fromXML(xmlString);
			break;

		case 125:
			baseMessage = new DefaultGroupId();
			baseMessage.fromXML(xmlString);
			break;

		case 123:
			baseMessage = new ChangeGroupName();
			baseMessage.fromXML(xmlString);
			break;

		case 122:
			baseMessage = new UserNotFoundByEmail();
			baseMessage.fromXML(xmlString);
			break;

		case 121:
			baseMessage = new AddContact();
			baseMessage.fromXML(xmlString);
			break;

		case 120:
			baseMessage = new SearchContact();
			baseMessage.fromXML(xmlString);
			break;

		case 119:
			baseMessage = new RemoveGroup();
			baseMessage.fromXML(xmlString);
			break;

		case 118:
			baseMessage = new RemoveContact();
			baseMessage.fromXML(xmlString);
			break;

		case 117:
			baseMessage = new RejectContact();
			baseMessage.fromXML(xmlString);
			break;

		case 116:
			baseMessage = new MoveContactToGroup();
			baseMessage.fromXML(xmlString);
			break;

		case 115:
			baseMessage = new ApplyContact();
			baseMessage.fromXML(xmlString);
			break;

		case 114:
			baseMessage = new AgreeContact();
			baseMessage.fromXML(xmlString);
			break;

		case 113:
			baseMessage = new AddGroup();
			baseMessage.fromXML(xmlString);
			break;

		case 112:
			baseMessage = new UpdateContactStatus();
			baseMessage.fromXML(xmlString);
			break;

		case 111:
			baseMessage = new UserChannelBroken();
			baseMessage.fromXML(xmlString);
			break;

		case 110:
			baseMessage = new StopSendDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 109:
			baseMessage = new StopSendControlDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 108:
			baseMessage = new StopReceiveDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 107:
			baseMessage = new StopReceiveControlDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 106:
			baseMessage = new RejectInviteDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 105:
			baseMessage = new RejectDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 104:
			baseMessage = new RejectControlDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 103:
			baseMessage = new InviteDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 102:
			baseMessage = new ApplyDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 101:
			baseMessage = new ApplyControlDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 100:
			baseMessage = new AgreeInviteDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 99:
			baseMessage = new AgreeDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 98:
			baseMessage = new AgreeControlDesktop();
			baseMessage.fromXML(xmlString);
			break;

		case 94:
			baseMessage = new DesktopClientKeyEvent();
			baseMessage.fromXML(xmlString);
			break;

		case 88:
			baseMessage = new DesktopClientRequestUpdate();
			baseMessage.fromXML(xmlString);
			break;

		case 86:
			baseMessage = new DesktopClientMouseEvent();
			baseMessage.fromXML(xmlString);
			break;

		case 82:
			baseMessage = new ServerInternalError();
			baseMessage.fromXML(xmlString);
			break;

		case 81:
			baseMessage = new StopReceiveVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 80:
			baseMessage = new StopSendVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 79:
			baseMessage = new RejectVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 78:
			baseMessage = new AgreeVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 77:
			baseMessage = new ApplyVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 76:
			baseMessage = new StopReceiveAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 75:
			baseMessage = new StopSendAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 74:
			baseMessage = new RejectAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 73:
			baseMessage = new AgreeAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 72:
			baseMessage = new ApplyAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 71:
			baseMessage = new MediaServerAddress();
			baseMessage.fromXML(xmlString);
			break;

		case 70:
			baseMessage = new GetMediaServerAddress();
			baseMessage.fromXML(xmlString);
			break;

		case 69:
			baseMessage = new UserJoinConference();
			baseMessage.fromXML(xmlString);
			break;

		case 68:
			baseMessage = new Broadcast();
			baseMessage.fromXML(xmlString);
			break;

		case 67:
			baseMessage = new Chat();
			baseMessage.fromXML(xmlString);
			break;

		case 65:
			baseMessage = new AddConference();
			baseMessage.fromXML(xmlString);
			break;

		case 64:
			baseMessage = new JoinConferenceResponse();
			baseMessage.fromXML(xmlString);
			break;

		case 63:
			baseMessage = new JoinConference();
			baseMessage.fromXML(xmlString);
			break;

		case 62:
			baseMessage = new QuitConference();
			baseMessage.fromXML(xmlString);
			break;

		case 61:
			baseMessage = new DeleteConference();
			baseMessage.fromXML(xmlString);
			break;

		case 60:
			baseMessage = new WebDeleteConference();
			baseMessage.fromXML(xmlString);
			break;

		case 58:
			baseMessage = new WebAddConference();
			baseMessage.fromXML(xmlString);
			break;

		case 55:
			baseMessage = new GetConferenceModelRoleTable();
			baseMessage.fromXML(xmlString);
			break;

		case 54:
			baseMessage = new ConferenceModelRoleTableItem();
			baseMessage.fromXML(xmlString);
			break;

		case 53:
			baseMessage = new ConferenceModelTableItem();
			baseMessage.fromXML(xmlString);
			break;

		case 52:
			baseMessage = new GetConferenceModelTable();
			baseMessage.fromXML(xmlString);
			break;

		case 51:
			baseMessage = new GetConferenceList();
			baseMessage.fromXML(xmlString);
			break;

		case 50:
			baseMessage = new UserLoginResponse();
			baseMessage.fromXML(xmlString);
			break;

		case 48:
			baseMessage = new UserQuitConference();
			baseMessage.fromXML(xmlString);
			break;

		case 45:
			baseMessage = new NotifyMediaServerRemoveVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 44:
			baseMessage = new NotifyMediaServerAddVideo();
			baseMessage.fromXML(xmlString);
			break;

		case 43:
			baseMessage = new NotifyMediaServerRemoveAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 42:
			baseMessage = new NotifyMediaServerAddAudio();
			baseMessage.fromXML(xmlString);
			break;

		case 41:
			baseMessage = new NotifyMediaServerUserExit();
			baseMessage.fromXML(xmlString);
			break;

		case 38:
			baseMessage = new MediaServerLogin();
			baseMessage.fromXML(xmlString);
			break;

		case 37:
			baseMessage = new StartConference();
			baseMessage.fromXML(xmlString);
			break;

		case 35:
			baseMessage = new Login();
			baseMessage.fromXML(xmlString);
			break;



            ///////////////////////////////////////////
            // LyvcMessage Parser Generated code end
            ///////////////////////////////////////////

            default:
                logger.warn("Unknown id:" + id);
                break;
        }

        return baseMessage;
    }
}
