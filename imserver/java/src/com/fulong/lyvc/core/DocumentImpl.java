/**
 * 
 */
package com.fulong.lyvc.core;

import java.sql.Connection;
import java.sql.SQLException;

import com.fulong.lyvc.Conference;
import com.fulong.lyvc.DAOFactory;
import com.fulong.lyvc.Document;
import com.fulong.lyvc.dao.DocumentDAO;
import com.fulong.lyvc.data.DocumentData;

/**
 * ConferenceDocumentImpl
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-17
 */
public class DocumentImpl implements Document {
	private DocumentData data;
	private DAOFactory dataSource = null;

	public DocumentImpl(DAOFactory dataSource, Conference conference,
			DocumentData data) {
		this.data = data;
	}

	public String getDocId() {
		return String.valueOf(data.getDocId());
	}

	public String getDocName() {
		return data.getDocName();
	}

	public String getDocURL() {
		return data.getDocURL();
	}

	public String getFileName() {
		return data.getFileName();
	}

	public void setDocName(String name) {
		this.data.setDocName(name);
		this.save();
	}

	public void setDocURL(String url) {
		this.data.setDocURL(url);
		this.save();
	}

	public void setFileName(String fileName) {
		this.data.setFileName(fileName);
		this.save();
	}

	private void save() {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			DocumentDAO dao = dataSource.getDAO("ConferenceDocumentDAO", connection);
			dao.update(data);
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
}
