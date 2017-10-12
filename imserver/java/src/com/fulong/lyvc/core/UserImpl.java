/**
 * 
 */
package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;

import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.ContactDefaultGroupDAO;
import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.ContactGroupData;
import com.fulong.lyvc.data.UserData;

/**
 * UserImpl
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-18
 */
public class UserImpl implements User {
	private ConferenceManager manager;
	private DAOFactory dataSource;
	private UserData data;

	public UserImpl(ConferenceManager manager,DAOFactory factory, UserData data) {
		this.manager=manager;
		this.dataSource = factory;
		this.data = data;
		if(data == null)
			throw new NullPointerException();
	}

	public String getAccountName() {
		return this.data.getAccountName();
	}

	public String getEmail() {
		return this.data.getEmail();
	}
	public void setEmail(String email){
		this.data.setEmail(email);
		this.save();
	}

	public String getFirstName() {
		return this.data.getFirstName();
	}

	public String getId() {
		return String.valueOf(this.data.getId());
	}

	public String getLastName() {
		return this.data.getLastName();
	}

	public String getPassword() {
		return this.data.getPassword();
	}

	public boolean checkPassword(String password) {		
		if(password==null)
			return false;
		
		return this.data.getPassword().equals(password);
	}

	public void setPassword(String newPassword){
		this.data.setPassword(newPassword);
		this.save();
	}

	public String getName() {
		return this.data.getAccountName();
	}
	
	private void save() {
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			UserDAO dao = dataSource.getDAO("UserDAO", connection);
			dao.update(data);
			
		}catch(SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public Group getContactGroup() {
		Connection connection = null;
		
		try {
			connection = dataSource.getConnection();
			ContactDefaultGroupDAO dao = dataSource.getDAO("ContactDefaultGroupDAO", connection);
			ContactGroupData data = dao.findByUser(Long.parseLong(this.getId()));
			
			if (data == null) {
				data = new ContactGroupData();
				data.setUserId(Long.parseLong(this.getId()));
				data.setName(this.getName());
				dao.insert(data);
			}
			
			return new ContactGroup(manager,this.dataSource,this, data);
			
		}catch(SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return null;
	}

	public void setFirstName(String firstName) {
		this.data.setFirstName(firstName);
		this.save();
	}

	public void setLastName(String lastName) {
		this.data.setLastName(lastName);
		this.save();
	}

	public void setAccountName(String accountName) {
		this.data.setAccountName(accountName);
		this.save();
	}
	
	public Group getInContactGroup(User user) {
		Group group=this.getContactGroup();
		if(group.isMember(user))
			return group;
		
		for(Group child:group.children()) {
			if(child.isMember(user))
				return child;
		}
		
		return null;
	}
	
	public boolean equals(Object another) {
		if(another==null)
			return false;
		
		if(another instanceof UserImpl)
			return ((UserImpl)another).getId() == this.getId();
		
		return false;
	}

	@Override
	public void addMessage(String messageId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMessages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteMessage(String messageId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}
}
