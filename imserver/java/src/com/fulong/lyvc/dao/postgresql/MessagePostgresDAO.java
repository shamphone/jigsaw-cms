package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.fulong.lyvc.dao.MessageDAO;
import com.fulong.lyvc.data.MessageData;
import com.fulong.lyvc.message.MessageFormatException;
/**
 * 
 * PostgreSqlPendingMessageSession
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-12
 */
public class MessagePostgresDAO extends PostgresqlDAO implements MessageDAO {

	/**
	 * getMessages
	 * 
	 * @param userId
	 *            long
	 * @return PendingMessage[]
	 * @throws SQLException 
	 * @throws MessageFormatException 
	 */
	public MessageData[] getMessages(long userId) throws SQLException{
			String sql = " select * from pendingmessage where receiverid = ?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, userId);
			ResultSet rs = ps.executeQuery();

			List<MessageData> messageList = new ArrayList<MessageData>();
			while (rs.next()) {
				MessageData pendingMessage = new MessageData();
				pendingMessage.setId(rs.getLong("id"));
				pendingMessage.setReceiverid(rs.getLong("senderid"));
				pendingMessage.setMessage(rs.getString("message"));
				pendingMessage.setSaveDate(rs.getTimestamp("savedate"));
				messageList.add(pendingMessage);
			}
			rs.close();
			ps.close();
			return (MessageData[]) messageList.toArray(new MessageData[0]);
	}


	/**
	 * removeMessage
	 * 
	 * @param messageId
	 *            long
	 * @throws SQLException 
	 */
	public void removeMessage(long messageId) throws SQLException {
			String sql = " delete from pendingmessage where id=?";
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setLong(1, messageId);
			ps.executeUpdate();
			ps.close();
	}

	/**
	 * saveMessage
	 * 
	 * @param receiverId
	 *            long
	 * @param message
	 *            BaseMessage
	 */
	public void saveMessage(MessageData data) throws SQLException {		
			String sql = " select nextval('pendingmessageid')";
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			long id = rs.getInt(1);
			rs.close();
			ps.close();
			data.setId(id);

			// insert record
			sql = "insert into pendingmessage(id, receiverId, message, saveDate) values(?, ?, ?, ?)";
			ps = connection.prepareStatement(sql);
			ps.setLong(1, data.getId());
			ps.setLong(2, data.getReceiverid());
			ps.setString(3, data.getMessage());
			ps.setTimestamp(4, new Timestamp(data.getSaveDate().getTime()));
			ps.executeUpdate();
			ps.close();
	}
}
