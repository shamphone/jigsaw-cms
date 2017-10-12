/**
 * 
 */
package com.fulong.lyvc.dao.postgresql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.lyvc.dao.DocumentDAO;
import com.fulong.lyvc.data.DocumentData;

/**
 * ConferenceDocumentPostgresDAO
 * 
 * 龙驭视频会议系统 v3.0
 * 
 * 版权所有：北京中科辅龙计算机技术股份有限公司 2009
 * 
 * @author 李雄锋
 * 
 *         最后修改时间：2009-3-17
 */
public class DocumentPostgresDAO extends PostgresqlDAO implements
		DocumentDAO {

	public long insert(DocumentData data) throws SQLException {
		String sql = "insert into conferencedoc (conferencedoc_id,conference_id,conferencedoc_name,conferencedoc_url,conferencedoc_filename) values(?,?,?,?,?)";
		PreparedStatement ps = connection.prepareStatement(sql);
		data.setDocId(getPrimaryKey());
		ps.setLong(1, data.getDocId());
		ps.setLong(2, data.getConferenceId());
		ps.setString(3, data.getDocName());
		ps.setString(4, data.getDocURL());
		ps.setString(5, data.getFileName());
		ps.execute();
		ps.close();
		return data.getDocId();
	}

	/**
	 * getConference
	 * 
	 * @param conferenceID
	 *            long
	 * @return Conference

	 */
	public DocumentData[] findByConference(long conferenceID)
			throws SQLException {
		// Get conference doc
		ArrayList<DocumentData> conferenceDocs = new ArrayList<DocumentData>();
		String sql = "select * from conferencedoc where conference_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setLong(1, conferenceID);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			DocumentData cDoc = new DocumentData();
			cDoc.setDocId(rs.getLong("conferencedoc_id"));
			cDoc.setDocName(rs.getString("conferencedoc_name"));
			cDoc.setDocURL(rs.getString("conferencedoc_url"));
			cDoc.setFileName(rs.getString("conferencedoc_filename"));
			conferenceDocs.add(cDoc);
		}
		rs.close();
		ps.close();

		return conferenceDocs.toArray(new DocumentData[conferenceDocs
				.size()]);
	}

	public void update(DocumentData data) throws SQLException {
		String sql = "update conferencedoc set conferencedoc_name=?,conferencedoc_url=?,conferencedoc_filename=? where conferencedoc_id=?";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, data.getDocName());
		ps.setString(2, data.getDocURL());
		ps.setString(3, data.getFileName());
		ps.setLong(4, data.getDocId());
		ps.execute();
		ps.close();
	}
}
