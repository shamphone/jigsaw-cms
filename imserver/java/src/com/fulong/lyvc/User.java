package com.fulong.lyvc;

import java.security.Principal;
import java.sql.SQLException;

/**
 * 
 * User
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 * 最后修改时间：2009-3-11
 */

public interface User extends Principal {

	/**
	 * @return Returns the user id
	 */
	public String getId();

	/**
	 * @return Returns the user email
	 */
	public String getEmail();

	/**
	 * @return Returns the user account
	 */
	public String getAccountName();

	/**
	 * @return Returns the firstName.
	 */
	public String getFirstName();

	/**
	 * @return Returns the lastName.
	 */
	public String getLastName();
	
	/**
	 * @return Returns the password.
	 */
	public String getPassword();
	
	/**
	 * 
	 * @param email
	 * @throws SQLException
	 */
	public void setEmail(String email);

	/**
	 * 
	 * @param accountName
	 */
	public void setAccountName(String accountName);
	
	/**
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName);

	/**
	 * 
	 * @param lastName
	 * @throws SQLException
	 */
	public void setLastName(String lastName);

	/**
	 * @return Returns the user password.
	 */
	public boolean checkPassword(String password);

	/**
	 * ��������
	 * 
	 * @param newPassword
	 */
	public void setPassword(String newPassword);

	/**
	 *联系人组
	 * 
	 * @return
	 */
	public Group getContactGroup();
	
	/**
	 * 指定用户在这个用户的联系人组
	 * @param user
	 * @return
	 */
	public Group getInContactGroup(User user);
	
	/**
	 * 获取用户（接收者）收到的所有留言
	 * @return
	 */
	public String[] getMessages();
	
	/**
	 * 给用户（接收者）添加一条留言
	 * @param messageId
	 */
	public void addMessage(String messageId);
	
	/**
	 * 删除用户的所有留言
	 * @param messageId
	 */
	public void deleteMessages();
	
	/**
	 * 删除用户的一条留言
	 * @param messageId
	 */
	public void deleteMessage(String messageId);
	
	/**
	 * 设置用户姓名
	 */
	public void setName(String name);
	
	/**
	 * 获取用户姓名
	 */
	public String getName();
}
