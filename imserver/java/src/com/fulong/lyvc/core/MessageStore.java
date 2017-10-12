/**
 * 
 */
package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

import com.fulong.common.dao.DatabaseException;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.User;
import com.fulong.lyvc.dao.MessageDAO;
import com.fulong.lyvc.data.MessageData;

/**
 * MessageStore
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-19
 */
public class MessageStore extends AbstractQueue<String> implements Queue<String> {
	private DAOFactory dataSource;
	private User owner;
	private List<MessageData> _data;

	public MessageStore(DAOFactory dataSource,	User owner) {
		this.dataSource = dataSource;
		this.owner = owner;
	}

	@Override
	public Iterator<String> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		return this.data().size();
	}

	public boolean offer(String message) {
		MessageData data = new MessageData();
		data.setMessage(message);
		data.setReceiverid(Long.parseLong(owner.getId()));
		try {
			this.save(data);
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
		if (this._data != null)
			this._data.add(data);
		return true;

	}

	public String peek() {
		if (this.size() == 0)
			return null;
		return this.data().get(0).getMessage();
	}

	public String poll() {
		if (this.size() > 0) {
			try {
				this.remove(this.data().remove(0));
			} catch (SQLException e) {
				throw new DatabaseException(e);
			}
		}
		return null;
	}

	private void remove(MessageData data) throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			MessageDAO cmDAO = dataSource.getDAO("MessageDAO", connection);
			cmDAO.removeMessage(data.getId());
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	private void save(MessageData data) throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			MessageDAO cmDAO = dataSource.getDAO("MessageDAO", connection);
			cmDAO.saveMessage(data);
			connection.commit();
		} catch (SQLException ex) {
			connection.rollback();
			throw ex;
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	private MessageData[] loadData(long receiverId) throws SQLException {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			MessageDAO dao = dataSource.getDAO("MessageDAO", connection);
			return dao.getMessages(receiverId);
		} finally {
			if (connection != null)
				connection.close();
		}
	}

	private List<MessageData> data() {
		if (this._data == null) {

			this._data = new ArrayList<MessageData>();
			try {
				Collections.addAll(this._data, this.loadData(Long.parseLong(owner.getId())));
			} catch (SQLException ex) {
				throw new DatabaseException(ex);
			}
		}
		return this._data;
	}

}
