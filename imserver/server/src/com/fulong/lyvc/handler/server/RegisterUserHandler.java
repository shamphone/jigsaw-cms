/**
 * 
 */
package com.fulong.lyvc.handler.server;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.TCPChannel;
import com.fulong.lyvc.TCPMessage;
import com.fulong.lyvc.User;
import com.fulong.lyvc.message.RegisterUser;
import com.fulong.lyvc.message.RegisterUserResponse;

/**
 * 
 * RegisterUserHandler
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2010-11-22
 */

public class RegisterUserHandler extends ServerBaseHandler {

	/* (non-Javadoc)
	 * @see com.fulong.lyvc.net.Handler#execute(com.fulong.lyvc.net.TCPChannel, com.fulong.lyvc.message.BaseMessage)
	 */
	@Override
	public void execute(TCPChannel channel, TCPMessage message) throws Exception {
		RegisterUser msg = (RegisterUser)message;
		
		this.setConferenceManager(msg.domain);
		ConferenceManager manager = this.getConferenceManager();
		
        RegisterUserResponse msgToSend = new RegisterUserResponse();
        msgToSend.bSuccessful = false;
        
        User user = manager.getUserByAccountName(msg.userName);
        if( user != null ) {
        	 msgToSend.message = "用户名["+msg.userName+"]已经存在！";
        	 sendMessage(channel, msgToSend);
        	 
        	 return;
        }
        
        user = manager.getUserByEmail(msg.email);
    	if(user != null) {
    		msgToSend.message = "邮箱["+msg.email+"]已经存在！";
    		sendMessage(channel, msgToSend);
        	 
        	return;
    	}
    	
    	user = manager.createUser(msg.userName, msg.password, msg.firstName, msg.lastName, msg.email);
    	String userId = user.getId();
        if(userId.equals("0")) {
            msgToSend.message = "注册新用户失败！";
        } 
        else {
            msgToSend.bSuccessful = true;
            msgToSend.message = "注册新用户成功！";
            
            //TODO 下面的实现以后可能需要修改（修改该操作要实现的功能）
            
            //将该用户添加到公共联系人根组下
    		Group commonGroup = manager.getCommonContactGroup();
    		commonGroup.addMember(user);
            
//            //获取所有的组
//    		Collection<Group> groupList = new ArrayList<Group>();
//    		groupList.add(commonGroup);
//    		groupList.addAll(getAllChildren(commonGroup));
//    		
//    		//得到管理员id
//    		User creator = manager.getUser(manager.getLeaserId());
//    		
//    		//获取所有的成员
//    		Collection<User> userList= new ArrayList<User>();
//    		userList.add(creator);
//    		for(Group temp : groupList) 
//    			userList.addAll(temp.users());
//    		
//    		//发送创建公共联系人的消息给所有在线用户
//    		AddContact contactMsg = new AddContact();
//    		contactMsg.setId("121");
//    		contactMsg.isCommon = true;
//    		contactMsg.groupId = commonGroup.getId();
//    		contactMsg.contactId = userId;
//    		contactMsg.name = msg.userName;
//    		contactMsg.firstName = msg.firstName;
//    		contactMsg.lastName = msg.lastName;
//    		contactMsg.email = msg.email;
//    		
//    		for(User temp : userList) {
//    			//找到在线用户
//    			TCPChannel chan = getChannel(temp.getId());
//    			if (chan != null) {
//    				sendMessage(chan, contactMsg);
//    			}
//    		}
        }
        
        sendMessage(channel, msgToSend);
	}
	
	/**
	 * 获取一个组下的所有子组（包括每一层的子组）
	 */
//	private static Collection<Group> getAllChildren(Group group) {
//		ArrayList<Group> groups = new ArrayList<Group>();
//		groups.addAll(group.children());
//		
//		for(int i=0; i<groups.size(); i++) {
//			groups.addAll(groups.get(i).children());
//		}
//		
//		return groups;
//	}

}
