package com.fulong.lyvc.handler.server;

/**
 * CreateInstantConference
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-9-10
 */

import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.ChangeUserInfo;

/**
 * 
 * ChangeUserInfoHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public class ChangeUserInfoHandler extends ServerBaseHandler {

	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		this.setConferenceManager(channel.getDomain());
		
		ChangeUserInfo msg = (ChangeUserInfo) message;
		User user = this.getConferenceManager().getUser(""+msg.userId);
		user.setEmail(msg.email);
		user.setFirstName(msg.firstName);
		user.setLastName(msg.lastName);
		user.setPassword(msg.password);
	}

}
