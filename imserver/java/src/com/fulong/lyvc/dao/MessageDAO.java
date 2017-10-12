package com.fulong.lyvc.dao;


import java.sql.SQLException;

import com.fulong.lyvc.data.MessageData;
import com.fulong.lyvc.message.MessageFormatException;
/**
 * 
 * PendingMessageSession
 *
 * 龙驭视频会议系统 v3.0
 *
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 *
 * @author 李雄锋
 *
 * 最后修改时间：2009-3-12
 */
public interface MessageDAO extends DAO{

        // Save message
        public void saveMessage(MessageData data) throws SQLException;

        // Get messages for a user
        public MessageData[] getMessages(long userId) throws SQLException, MessageFormatException;

        // Remove pending message
        public void removeMessage(long messageId) throws SQLException;
}
