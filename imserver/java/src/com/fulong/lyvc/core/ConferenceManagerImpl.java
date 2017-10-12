package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;
import java.util.Vector;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.ConferenceManager;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Group;
import com.fulong.lyvc.Message;
import com.fulong.lyvc.Mode;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.ConferenceDAO;
import com.fulong.lyvc.dao.ConferenceMemberDAO;
import com.fulong.lyvc.dao.ContactDefaultGroupDAO;
import com.fulong.lyvc.dao.ContactGroupDAO;
import com.fulong.lyvc.dao.GroupDAO;
import com.fulong.lyvc.dao.MemberDAO;
import com.fulong.lyvc.dao.ModeDAO;
import com.fulong.lyvc.dao.ModeRoleDAO;
import com.fulong.lyvc.dao.UserDAO;
import com.fulong.lyvc.data.ConferenceData;
import com.fulong.lyvc.data.ContactGroupData;
import com.fulong.lyvc.data.GroupData;
import com.fulong.lyvc.data.ModeData;
import com.fulong.lyvc.data.UserData;

/**
 * 
 * ConferenceManagerImpl
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-15
 */
public abstract class ConferenceManagerImpl implements ConferenceManager {
	private DAOFactory daoFactory = null;
	private Map<Long, Conference> conferences;

	// private Listener listener;

	public void init() {
		this.loadConferences();
	}

	private synchronized void loadConferences() {
		Connection connection = null;
		ConferenceData[] data = null;
		
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConferenceDAO cmDAO = daoFactory.getDAO("ConferenceDAO", connection);
			try {
				data = cmDAO.findAll();
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
		
		conferences = new Hashtable<Long, Conference>();
		for (int i = 0; i < data.length; i++)
			conferences.put(new Long(data[i].getId()), new ConferenceImpl(this,
					this.daoFactory, data[i]));

	}

	public void setDaoFactory(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	public Conference getConference(String conferenceID) {
		return this.conferences.get(new Long(conferenceID));
	}

	public Collection<Conference> getConferenceByUser(String userID) {
		Connection connection = null;
		ConferenceData[] data = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ConferenceDAO cmDAO = daoFactory.getDAO("ConferenceDAO", connection);
			try {
				data = cmDAO.findByCreator(Long.parseLong(userID));
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

		ArrayList<Conference> list = new ArrayList<Conference>();
		for (int i = 0; i < data.length; i++)
			list.add(new ConferenceImpl(this, this.daoFactory, data[i]));
		
		return list;
	}

	public Collection<Conference> getAllConferences() {
		return this.conferences.values();
	}

	public Conference createNormalConference(String title, String modelId, String creatorId, Date startTime, Date endTime, String desc) {
		return this.create(title, modelId, creatorId, startTime, endTime, desc, true);
	}

	public Conference createInstantConference(String title, User creator) {
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.add(Calendar.DATE, -1);
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.add(Calendar.DATE, 1);

		return this.create(title, ""+4, creator.getId(), calendarStart.getTime(), calendarEnd.getTime(), title, true);
	}

	private Conference create(String title, String modelId, String creatorId, Date startTime, Date endTime, String desc, boolean notifyFlag) {
		ConferenceData conferenceData = new ConferenceData();
		conferenceData.setTitle(title);
		conferenceData.setDesc(desc);
		conferenceData.setStartTime(startTime);
		conferenceData.setEndTime(endTime);
		conferenceData.setConferecneModelID(Long.parseLong(modelId));
		conferenceData.setConferenceCreatorId(Long.parseLong(creatorId));

		Connection connection = null;
		try {
			connection = daoFactory.getConnection();
			ConferenceDAO cmDAO = daoFactory.getDAO("ConferenceDAO", connection);
			cmDAO.insert(conferenceData);

			if (notifyFlag) {
				// notifyListenerAddConference(this.getListener(),
				// conferenceData.getId());
			}
			connection.commit();
			Conference conference = new ConferenceImpl(this, this.daoFactory,
					conferenceData);
			this.conferences.put(new Long(conference.getId()), conference);
			return conference;
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

	public void delete(Conference conference) {
		if (!conference.getEndTime().before(new java.util.Date()))
			return;
		
		Connection connection = null;
		try {
			connection = this.daoFactory.getConnection();
			ConferenceDAO conDAO = this.daoFactory.getDAO("ConferenceDAO", connection);
			conDAO.delete(Long.parseLong(conference.getId()));
			ConferenceMemberDAO memDAO = this.daoFactory.getDAO("ConferenceMemberDAO", connection);
			memDAO.deleteByConference(Long.parseLong(conference.getId()));
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
		
		conferences.remove(conference.getId());
	}

	public void deleteInstantConferences() {
		Connection connection = null;
		try {
			connection = this.daoFactory.getConnection();
			ConferenceDAO conDAO = this.daoFactory.getDAO("ConferenceDAO",
					connection);
			conDAO.deleteByModel(4);
			ConferenceMemberDAO memDAO = this.daoFactory.getDAO(
					"ConferenceMemberDAO", connection);
			memDAO.deleteByMode(4);
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
		
		this.loadConferences();
	}

	public void deleteByUser(String userId) {
		Connection connection = null;
		try {
			connection = this.daoFactory.getConnection();
			ConferenceDAO conDAO = this.daoFactory.getDAO("ConferenceDAO", connection);
			conDAO.deleteByCreatorId(Long.parseLong(userId));
			ConferenceMemberDAO memDAO = this.daoFactory.getDAO("ConferenceMemberDAO", connection);
			memDAO.deleteByUser(Long.parseLong(userId));
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
		
		this.loadConferences();
	}

	public Collection<Mode> getPredefinedModes() {
		ArrayList<Mode> modes = new ArrayList<Mode>();

		for (int i = 0; i < PREDEFINE_CONFERENCE_MODEL.length; i++) {
			modes.add(this.getMode(String.valueOf(PREDEFINE_CONFERENCE_MODEL[i])));
		}
		
		return modes;
	}

	public Collection<Mode> getModes() {
		ArrayList<Mode> modes = new ArrayList<Mode>();
		
		Connection connection = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModeDAO dao = daoFactory.getDAO("ModeDAO", connection);
			ModeData[] data = null;
			try {
				data = dao.findAll();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (ModeData item : data)
				modes.add(new ModeImpl(this.daoFactory, item));
			
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return modes;
	}

	public Collection<Mode> getUserDefinedModes() {
		ArrayList<Mode> modes = new ArrayList<Mode>();
		
		Connection connection = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModeDAO dao = daoFactory.getDAO("ModeDAO", connection);
			ModeData[] data = null;
			try {
				data = dao.findUserDefinedModels();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (ModeData item : data)
				modes.add(new ModeImpl(this.daoFactory, item));
			
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		return modes;
	}

	public Mode addUserDefinedModel(String name, String desc) {
		ModeData data = new ModeData();
		data.setName(name);
		data.setDesc(desc);
		data.setPredefined(false);
		
		Connection connection = null;
		try {
			connection = daoFactory.getConnection();
			ModeDAO dao = daoFactory.getDAO("ModeDAO", connection);
			dao.insert(data);
			connection.commit();
			
			return new ModeImpl(this.daoFactory, data);
			
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

	public void delete(Mode model) {
		Connection connection = null;
		try {
			connection = daoFactory.getConnection();
			ModeRoleDAO cmDAO = daoFactory.getDAO("ConferenceModelRoleDAO",connection);
			cmDAO.deleteByModelId(Long.parseLong(model.getId()));
			ModeDAO dao = daoFactory.getDAO("ModeDAO", connection);
			dao.delete(Long.parseLong(model.getId()));
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

	public Mode getMode(String modelId) {
		Connection connection = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ModeDAO dao = daoFactory.getDAO("ModeDAO", connection);
			ModeData data = null;
			try {
				data = dao.findByID(Long.parseLong(modelId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (data != null)
				return new ModeImpl(this.daoFactory, data);
			
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

	/*
	 * public void setListener(String ip, int port) {
	 * if((this.listener!=null)&& this.listener.isValid()) throw new
	 * IllegalStateException("A listener is present for this library"); //
	 * ��鵱ǰ�Ƿ���Listener, �Լ���Listener�Ƿ񱣳ֻ�Ծ
	 * 
	 * // Remove old listener if there is one removeListener(); this.listener=
	 * this.createListener(ip, port); }
	 * 
	 * private void removeListener() { Connection connection
	 * = null; try { connection = this.dataSource.getConnection(); ListenerDAO
	 * dao=this.dataSource.getDAO("ListenerDAO", connection); dao.remove();
	 * this.listener=null; } finally { if (connection != null)
	 * connection.close(); } }
	 */
	public User getUserByAccountName(String accountName) {
		Connection connection = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			UserDAO dao = daoFactory.getDAO("UserDAO", connection);
			UserData data = null;
			try {
				data = dao.getByAccountName(accountName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (data != null)
				return new UserImpl(this, this.daoFactory, data);
			
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

	public User getUser(String userId) {
		// ��ֵĴ��?
		long id = 0;
		if (Long.parseLong(userId) > 100000000000000000L) {
			id = (Long.parseLong(userId) - 100000000000000000L) / 10;
		}

		Connection connection = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			UserDAO dao = daoFactory.getDAO("UserDAO", connection);
			UserData data = null;
			try {
				data = dao.getUserById(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (data != null)
				return new UserImpl(this, this.daoFactory, data);
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

	public User createUser(String accountName, String password, String firstName, String lastName, String email) {
		UserData userData = new UserData();
		userData.setAccountName(accountName);
		userData.setPassword(password);
		userData.setFirstName(firstName);
		userData.setLastName(lastName);
		userData.setEmail(email);
		
		Connection connection = null;
		try {
			connection = daoFactory.getConnection();
			UserDAO dao = daoFactory.getDAO("UserDAO", connection);
			dao.insert(userData);
			return new UserImpl(this, this.daoFactory, userData);
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
		
		/*
		 * if (userId > 0) { conferenceSession.notifyListenerAddUser(userId,
		 * accountName, password, firstName, lastName, email); }
		 */
	}

	public Collection<User> getUsers() {
		ArrayList<User> users = new ArrayList<User>();
		Connection connection = null;
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			UserDAO dao = daoFactory.getDAO("UserDAO", connection);
			UserData[] data = null;
			try {
				data = dao.getAllUsers();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			for (int i = 0; i < data.length; i++)
				users.add(new UserImpl(this, this.daoFactory, data[i]));
			
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

	public Group getGroup(String groupId) {
		Connection connection = null;
		
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			GroupDAO dao = daoFactory.getDAO("GroupDAO", connection);
			GroupData data = null;
			try {
				data = dao.findById(Long.parseLong(groupId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (data != null)
				return new GroupImpl(this, this.daoFactory, data);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ContactGroupDAO dao = daoFactory.getDAO("ContactGroupDAO", connection);
			ContactGroupData data = null;
			try {
				data = dao.findGroupsById(Long.parseLong(groupId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (data != null)
				return new ContactGroup(this, this.daoFactory, data);
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		
		try {
			try {
				connection = daoFactory.getConnection();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			ContactDefaultGroupDAO dao = daoFactory.getDAO("ContactDefaultGroupDAO", connection);
			ContactGroupData data = null;
			try {
				data = dao.findById(Long.parseLong(groupId));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if (data != null)
				return new ContactGroup(this, this.daoFactory, data);
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

	public void delete(Group group) {
		Connection connection = null;
		try {
			connection = daoFactory.getConnection();
			MemberDAO dao = daoFactory.getDAO("MemberDAO", connection);
			dao.deleteByGroup(Long.parseLong(group.getId()));
			GroupDAO gdao = daoFactory.getDAO("GroupDAO", connection);
			gdao.delete(Long.parseLong(group.getId()));
			
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

		// conferenceSession.notifyListenerRemoveGroup(groupId);
	}

	public void delete(User user) {
		// �����ڽ��еĻ�����ɾ��
		for (Conference conf : this.getAllConferences()) {
			conf.leave(user);
		}
		// ����ݿ���ɾ��
		Connection connection = null;
		try {
			connection = daoFactory.getConnection();
			UserDAO gdao = daoFactory.getDAO("UserDAO", connection);
			gdao.removeUser(Long.parseLong(user.getId()));
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

	public Group getAdministrators() {
		return new SystemGroup(this, this.daoFactory, ""+1);
	}

	public Queue<String> getMessageStore(User receiver) {
		return new MessageStore(this.daoFactory, receiver);
	}

	public Collection<Conference> getHoldingConferences() {
		Vector<Conference> holding = new Vector<Conference>();
		for (Conference conference : this.getAllConferences()) {
			if (conference.isHolding())
				holding.add(conference);
		}
		
		return holding;
	}

	public Collection<Conference> getHoldingConferences(User participant) {
		Vector<Conference> holding = new Vector<Conference>();
		for (Conference conference : this.getConferenceByUser(participant.getId())) {
			if (conference.isHolding())
				holding.add(conference);
		}
		
		return holding;
	}

	public boolean isAdministrator(User user) {
		return false;
	}

	@Override
	public Group getCommonContactGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message createMessage(String senderId, String title, String content,
			Date saveDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Conference> getNormalConferences(User participant) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
}
