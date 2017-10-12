package com.fulong.lyvc.handler.server;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;

/**
 * 
 * GetConferenceListHandler
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-12
 */
public class GetConferenceListHandler extends ServerBaseHandler{

	@Override
	public void execute(TCPChannel channel, TCPMessage message)	throws Exception {

        // ֪ͨ���û��������ܹ��μӵĻ���
/*
        long userId = message._senderId;
        Collection lyvcConferences = ConferenceTable.getConTable().values();
        Iterator itLyvcConferences = lyvcConferences.iterator();
        while( itLyvcConferences.hasNext()) {
            ConferenceInServer lyvcConference = (ConferenceInServer)itLyvcConferences.next();
            Conference dbConference = LyvcServer.conferenceSession.getConference(lyvcConference.getId());
            if(dbConference != null){
                List userIds = dbConference.getMemberIDs();
                for (int i = 0; i < userIds.size(); i++) {
                    if (userId == ((Long) userIds.get(i)).longValue()) {
                        sendConferenceToUser(channel, dbConference);
                        break;
                    }
                }
            }
        }
*/
	}

}
