/**
 * 
 */
package com.fulong.lyvc.jcr;

import java.util.ArrayList;
import java.util.Collection;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeWrapper;
import com.fulong.longcon.repository.Property;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.util.SchemeConstant;

/**
 * UserNode
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author lifx
 *
 * 最后修改时间：2010-8-6
 */

public class UserNode extends NodeWrapper implements User {
	
	private static final String ACCOUNTNAME = "user-username";		//用户名
	private static final String NAME = "user-commonname";			//用户姓名 
	private static final String FIRSTNAME = "firstName";			//名字
	private static final String LASTNAME = "lastName";				//姓
	private static final String PASSWORD = "user-password";		//密码
	private static final String EMAIL = "mail";					//邮箱
	private static final String MESSAGE = "message";				//留言
	private static final String CONTACTGROUP = "contactGroup";		//联系人组名称
	
	public UserNode(Node node) {
		super(node);
	}
	
	/**
	 * 获取唯一标识
	 * 
	 * @return
	 */
	public String getId() {
		return this.getNode().getID();
	}

	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getAccountName() {
		String accountName = null;
		
		Property property = this.getNode().getProperty(ACCOUNTNAME);
		if(property != null)
			accountName = property.getString();
		
		return accountName;
	}

	/**
	 * 获取邮箱
	 * 
	 * @return
	 */
	public String getEmail() {
		String email = null;
		
		Property property = this.getNode().getProperty(EMAIL);
		if(property != null)
			email = property.getString();
		
		return email;
	}
	
	/**
	 * 获取用户的名字
	 * 
	 * @return
	 */
	public String getFirstName() {
		return this.getName();
		
//		String firstName = null;
//		
//		Property property = this.getNode().getProperty(FIRSTNAME);
//		if(property != null)
//			firstName = property.getString();
//		
//		return firstName;
	}

	/**
	 * 获取用户的姓氏
	 * 
	 * @return
	 */
	public String getLastName() {
		return "";
		
//		String lastName = null;
//		
//		Property property = this.getNode().getProperty(LASTNAME);
//		if(property != null)
//			lastName = property.getString();
//		
//		return lastName;
	}

	/**
	 * 获取密码
	 * 
	 * @return
	 */
	public String getPassword() {
		String password = null;
		
		Property property = this.getNode().getProperty(PASSWORD);
		if(property != null) {
			password = property.getString();
			DesEncrypter des = new DesEncrypter("fulong");
			password = des.decrypt(password);
		}
		
		return password;
	}
	
	/**
	 * 设置用户名
	 * 
	 * @return
	 */
	public void setAccountName(String accountName) {
		Property property = this.getNode().getProperty(ACCOUNTNAME);
		if(property != null)
			property.setValue(accountName);
	}

	/**
	 * 设置邮箱
	 * 
	 * @return
	 */
	public void setEmail(String email) {
		Property property = this.getNode().getProperty(EMAIL);
		if(property != null)
			property.setValue(email);
	}
	
	/**
	 * 设置用户的名字
	 * 
	 * @return
	 */
	public void setFirstName(String firstName) {
		Property property = this.getNode().getProperty(NAME);
		if(property != null)
			property.setValue(firstName);
	}

	/**
	 * 设置用户的姓氏
	 * 
	 * @return
	 */
	public void setLastName(String lastName) {
//		Property property = this.getNode().getProperty(LASTNAME);
//		if(property != null)
//			property.setValue(lastName);
	}

	/**
	 * 设置密码
	 * 
	 * @return
	 */
	public void setPassword(String newPassword) {
		Property property = this.getNode().getProperty(PASSWORD);
		if(property != null) {
			DesEncrypter des = new DesEncrypter("fulong");
			String password = des.encrypt(newPassword);
			property.setValue(password);
		}
	}
	
	/**
	 * 获取用户名
	 * 
	 * @return
	 */
	public String getName() {
		String name = null;
		
		Property property = this.getNode().getProperty(NAME);
		if(property != null)
			name = property.getString();
		
		return name;
	}
	
	/**
	 * 设置用户姓名
	 */
	public void setName(String name) {
		Property property = this.getNode().getProperty(NAME);
		if(property != null)
			property.setValue(name);
	}
	
	/**
	 * 获取所有的留言
	 */
	public String[] getMessages() {
		String[] messages = null;
		
		Property property = this.getNode().getProperty(MESSAGE);
		if(property != null)
			messages = property.getArray();
		
		return messages;
	}
	
	/**
	 * 添加一条留言
	 */
	public void addMessage(String messageId) {
		String[] messages = null;
		
		Property property = this.getNode().getProperty(MESSAGE);
		if(property != null) {
			messages = property.getArray();
			
			String[] newMessages = new String[messages.length+1];
			System.arraycopy(messages, 0, newMessages, 0, messages.length);
			newMessages[newMessages.length-1] = messageId;
			property.setValue(newMessages);
		}
	}

	/**
	 * 检验密码
	 * 
	 * @return
	 */
	public boolean checkPassword(String password) {	
		boolean flag;
		
		if(password == null) 
			flag = false;
		else
			flag = this.getPassword().equals(password);
		
		return flag;
	}
	
	/**
	 * 该方法用来得到一个用户的联系人组（第一层的）
	 *
	 */
	public Group getContactGroup() {
		Node Group = this.getNode().getNode(CONTACTGROUP);
		
		//得到联系人组的节点定义（节点类型）
		if(Group == null) {
			NodeDefinition userDefinition = this.getNode().getRepository().getDefinitionManager().getDefinition(SchemeConstant.contactGroupScheme);
			Group = this.getNode().addNode(userDefinition, CONTACTGROUP);	//创建一个联系人组类型的节点
		}
		
		return new ContactGroupNode(Group);
	}

	/**
	 * 该方法用来得到一个用户所在的联系人组
	 *
	 */
	public Group getInContactGroup(User user) {
		Group group = this.getContactGroup();
		if(group.isMember(user))
			return group;
		
		Collection<Group> groupList = new ArrayList<Group>();
		groupList.add(group);
		groupList.addAll(getAllChildren(group));
		
		for(Group temp : groupList) {
			if(temp.isMember(user)) {
				return temp;
			}
		}

		return null;
	}
	
	/**
	 * 该方法用来得到一个用户的所有联系人组（包括每一层的）
	 *
	 */
	public ArrayList<Group> getAllChildren(ArrayList<Group> groups, Group group) {
		Collection<? extends Group> collection = group.children();
		for(Group temp : collection) {
			ArrayList<Group> temps = getAllChildren(groups, temp);
			groups.addAll(temps);
		}
		
		return groups;
	}
	
	public boolean equals(Object another) {
		if(another == null)
			return false; 
		
		if(another instanceof UserNode)
			return ((UserNode)another).getId() == this.getId();
		
		return false;
	}

	/**
	 * 删除所有的留言（包括用户留言、系统公告等）
	 */
	public void deleteMessages() {
		Property property = this.getNode().getProperty(MESSAGE);
		if(property != null) {
			property.setValue((String)null);
		}
	}

	/**
	 * 删除一条留言
	 */
	public void deleteMessage(String messageId) {
	String[] messages = null;
		
		Property property = this.getNode().getProperty(MESSAGE);
		if(property != null) {
			messages = property.getArray();
			
			String[] newMessages = new String[messages.length-1];
			int k = 0;
			for(int i=0; i<messages.length; i++) {
				if(!messages[i].equals(messageId))
					newMessages[k++] = messages[i];
			}
			property.setValue(newMessages);
		}
	}

	/**
	 * 
	 * 获取一个组下的所有子组（包括每一层的子组）
	 */
	private static Collection<Group> getAllChildren(Group group) {
		ArrayList<Group> groups = new ArrayList<Group>();
		groups.addAll(group.children());
		
		for(int i=0; i<groups.size(); i++) {
			groups.addAll(groups.get(i).children());
		}
		
		return groups;
	}

	@Override
	public void setMaxOrderNo(int orsderNo) {
		// TODO Auto-generated method stub   
		
	}
}
