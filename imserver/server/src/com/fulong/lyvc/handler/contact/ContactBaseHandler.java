/**
 * 
 */
package com.fulong.lyvc.handler.contact;

import com.fulong.lyvc.EventHandler;

/**
 * ContactBaseHandler
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-13
 */
public abstract class ContactBaseHandler extends EventHandler {
	protected final static int CONTACT_OFFLINE = 1;
	protected final static int CONTACT_ONLINE = 2;

//	protected void removeMember(String userId, String groupId) throws Exception {
//		User member = this.getConferenceManager().getUser(userId);
//		Group group = this.getConferenceManager().getGroup(groupId);
//		if ((member != null) && (group != null))
//			group.removeMember(member);
//	}
}
