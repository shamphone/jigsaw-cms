/**
 * 
 */
package com.fulong.lyvc.handler.server;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;

/**
 * GetConferenceModelRoleTable
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class GetConferenceModelRoleTableHandler extends ServerBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		/*
        long modelId = ((GetConferenceModelRoleTable) message).modelId;
        ConferenceModelRoleTableItem msgToSend = new ConferenceModelRoleTableItem();
    	msgToSend.modelId = modelId;
	    List list = server.conferenceModelSession.getConferenceModel(modelId).getRoles();
	    for (int i = 0;i < list.size();i++){
	    	ConferenceModelRole item = (ConferenceModelRole)list.get(i);
	    	msgToSend.roleId = item.getId();
	    	msgToSend.roleName = item.getName();
            ArrayList rights = item.getRights();
            StringBuffer sb = new StringBuffer();
            for (int j = 0;j < rights.size();j++){
                sb.append(((Long)rights.get(j)).longValue());
                if(j < rights.size() - 1)
                    sb.append(",");
            }
            msgToSend.rights = sb.toString();
            MessageProcessor.sendMessage(channel, msgToSend);
	    }
 */

	}

}
