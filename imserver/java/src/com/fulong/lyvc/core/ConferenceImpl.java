package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Document;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.ModeRole;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.ConferenceDAO;
import com.fulong.lyvc.dao.ConferenceMemberDAO;
import com.fulong.lyvc.dao.DocumentDAO;
import com.fulong.lyvc.dao.ModeRoleDAO;
import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.ConferenceData;
import com.fulong.lyvc.data.DocumentData;
import com.fulong.lyvc.data.ModeRoleData;
import com.fulong.lyvc.data.UserData;

/**
 * 
 * ConferenceImpl
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-11
 */
public class ConferenceImpl implements Conference {
	private ConferenceData data;
	private Map<Long, Document> _documents;
	private DAOFactory dataSource = null;
	private ConferenceManager manager;
	private List<User> participants; //�����û�
	
	public ConferenceImpl(ConferenceManager manager, DAOFactory dataSource,
			ConferenceData data) {
		this.manager=manager;
		this._documents = null;
	}

	public boolean addMember(String modelRoleID, String userID) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ConferenceMemberDAO cmDAO = dataSource.getDAO("ConferenceMemberDAO", connection);
			cmDAO.insert(this.data.getId(), Long.parseLong(modelRoleID), Long.parseLong(userID));
			connection.commit();
			
			return true;
		} catch (SQLException ex) {
			try {
				connection.rollback();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} finally {
			if (connection != null)
				try {
					connection.close();
					return false;
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
		}
	}

	public Collection<User> getMembers() {
		ArrayList<User> users= new ArrayList<User>();
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			UserDAO dao = dataSource.getDAO("UserDAO", connection);
			UserData[] data = null;
			try {
				data = dao.findByConference(Long.parseLong(getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for(int i=0;i<data.length;i++)
				users.add(new UserImpl(this.manager, this.dataSource, data[i]));
			
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return users;
	}

	public Collection<User> getMembers(ModeRole role) {
		ArrayList<User> users= new ArrayList<User>();
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			UserDAO dao = dataSource.getDAO("UserDAO", connection);
			UserData[] data = null;
			try {
				data = dao.findByConference(Long.parseLong(getId()), Long.parseLong(role.getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for(int i=0;i<data.length;i++)
				users.add(new UserImpl(this.manager, this.dataSource, data[i]));
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return users;
	}
	public boolean isMember(String userID) {
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConferenceMemberDAO dao = dataSource.getDAO("ConferenceMemberDAO", connection);		
			try {
				return dao.exists(Long.parseLong(getId()), Long.parseLong(userID));
				
			} catch (NumberFormatException e) {
				e.printStackTrace();
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
		
		return false;
	}
	
	public ModeRole getMemberRole(String userID) {
		Connection connection = null;
		try {
			try {
				connection = dataSource.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			ModeRoleDAO dao=this.dataSource.getDAO("ModeRoleDAO", connection);
			ModeRoleData[] data = null;
			try {
				data = dao.findByConferenceAndUser(Long.parseLong(getId()), Long.parseLong(userID));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(data.length==0)
				return null;
			return new ModeRoleImpl(this.dataSource, data[0]);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public void removeMember(String userID) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ConferenceMemberDAO dao=dataSource.getDAO("ConferenceMemberDAO", connection);
			dao.delete(Long.parseLong(getId()), Long.parseLong(userID));
			connection.commit();
		} catch (SQLException ex) {
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
	}

	public Document addDocument(String name, String url, String fileName ) {
		DocumentData data= new DocumentData();
		data.setConferenceId(this.data.getId());
		data.setDocName(name);
		data.setDocURL(url);
		data.setFileName(fileName);
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			DocumentDAO dao=dataSource.getDAO("ConferenceDocumentDAO", connection);
			dao.insert(data);
			connection.commit();
			return new DocumentImpl(this.dataSource,this,data);
		} catch (SQLException ex) {
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

	public void removeDocument(String docId) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			String sql = "delete from conferencedoc where conferencedoc_id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, Long.parseLong(docId));
			ps.execute();
			ps.close();
			connection.commit();
		} catch (SQLException ex) {
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
	}

	public Mode getMode() {
		return this.manager.getMode(String.valueOf(this.data.getConferecneModelID()));
	}

	public String getDesc() {
		return this.data.getDesc();
	}

	public Date getEndTime() {
		return this.data.getEndTime();
	}

	public String getId() {
		return String.valueOf(this.data.getId());
	}

	public Date getStartTime() {
		return this.data.getStartTime();
	}

	public String getTitle() {
		return this.data.getTitle();
	}

	public long getCreatorId() {
		return this.data.getConferenceCreatorId();
	}

	public void setConferecneModelID(String conferecneModelID) {
		this.data.setConferecneModelID(Long.parseLong(conferecneModelID));
		this.save();
	}

	public void setDesc(String desc){
		this.data.setDesc(desc);
		this.save();
	}

	public void setEndTime(Date endTime) {
		this.data.setEndTime(endTime);
		this.save();
	}

	public void setStartTime(Date startTime)  {
		this.data.setStartTime(startTime);
		this.save();
	}

	public void setTitle(String title)  {
		this.data.setTitle(title);
		this.save();
	}

	public void setCreatorId(String id){
		this.data.setConferenceCreatorId(Long.parseLong(id));
		this.save();
	}
	
	public User getCreator(){
		return this.manager.getUser(String.valueOf(this.data.getConferenceCreatorId()));
	}

	public Document getDocument(String docId) {
		 return this.documents().get(Long.parseLong(docId));
	}

	public Collection<Document> getDocuments() {
		return this.documents().values();
	}

	private Map<Long, Document> documents() {
		if (this._documents == null) {
			DocumentData[] data = null;
			Connection connection = null;
			try {
				try {
					connection = dataSource.getConnection();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				DocumentDAO cmDAO = dataSource.getDAO("ConferenceDocumentDAO", connection);
				try {
					data = cmDAO.findByConference(this.data.getId());
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

			for (int i = 0; i < data.length; i++) {
				this._documents.put(new Long(data[i].getDocId()),
						new DocumentImpl(this.dataSource, this,data[i]));
			}
		}
		
		return this._documents;
	}

	private void save() {

		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			ConferenceDAO cmDAO = dataSource
					.getDAO("ConferenceDAO", connection);
			cmDAO.update(data);
			connection.commit();
		} catch (SQLException ex) {
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
	}

	public Collection<User> getParticipants() {
		return this.participants;
	}

	public boolean isHolding() {
		//��ʼʱ���ڵ�ǰʱ��֮�󣬼����黹δ��ʼ
		if((this.data.getStartTime()!=null)&& this.data.getStartTime().after(new Date()))
			return false;
		//����ʱ���ڵ�ǰʱ��֮�󣬼������Ѿ�����
		if(this.data.getEndTime()!=null && this.data.getEndTime().before(new Date()))
			return false;
		return true;
	}

	public boolean join(User user) {
		this.participants.add(user);
		return false;
	}

	public boolean leave(User user) {
		this.participants.remove(user);
		return false;
	}
	
	public void start() {
		this.participants = new Vector<User>();
	}

	public void terminate() {
		this.participants = new Vector<User>();
		
	}
	public boolean equals(Object another) {
		if(another==null)
			return false;
		if(another instanceof ConferenceImpl)
			return ((UserImpl)another).getId() == this.getId();
		return false;
	}

	@Override
	public boolean addMember(String userID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<User> getMembers(String roleId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document addDocument(String path) {
		// TODO Auto-generated method stub
		return null;
	}
}
