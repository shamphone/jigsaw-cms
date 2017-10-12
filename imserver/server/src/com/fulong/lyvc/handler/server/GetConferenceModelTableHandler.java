package com.fulong.lyvc.handler.server;

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
/**
 * 
 * GetConferenceModelTable
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-13
 */
public class GetConferenceModelTableHandler extends ServerBaseHandler {

	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
	       // ֪ͨ���û����еĻ���ģʽ
		/*
		        ConferenceModelTableItem msgToSend = new ConferenceModelTableItem();
		        List list = server.conferenceModelSession.getAllConferenceModels();
		    	for (int i = 0;i < list.size();i++){
		    		ConferenceModel item = (ConferenceModel)list.get(i);
		    		msgToSend.modelId = item.getId();
		    		msgToSend.modelName = item.getName();
			   		MessageProcessor.sendMessage(channel, msgToSend);
		    	}
		*/
		        // ֪ͨ���û���ʱ�����ģʽID
		/*
		        InstantConferenceModelId icmiMessage = new InstantConferenceModelId();
		        icmiMessage.instantConferenceModelId = InstantConferenceModel.instantConferenceModelId;
		        MessageProcessor.sendMessage(channel, icmiMessage);
		*/

	}

}
