package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.SQLException;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.LongValueDao;
import com.fulong.longcon.repository.dao.NodeDao;
import com.fulong.longcon.repository.data.NodeData;
/**
 * 先建L_test_property表
 * @author songbo
 *
 */

public class SqlserverLongValueDaoTest extends SqlserverDaoTestCase {

	private LongValueDao dao = null;
	private NodeDao nodedao = null;
	private String contentID = "test.long";
	private String parent = "test.long.parent";
	private String property = "test_property";
	private int index = 0;
	private long value = 1000;

	protected void setUp() throws Exception {
		super.setUp();
		// 测试数据 contentID的节点
		NodeData data = new NodeData();
		data.setID(contentID);
		data.setName("test.longcon.child");
		data.setOrderNo(1);
		data.setParentID(parent);
		try {
			dao = (LongValueDao) this.factory.getDao(LongValueDao.class);
			nodedao = (NodeDao) this.factory.getDao(NodeDao.class);

			dao.insert(contentID, property, index, value);
			nodedao.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}
		long[] rs = dao.load(contentID, property);
		Assert.assertEquals("passed", rs[0], value);
		int res = dao.getSum(property, parent);
		Assert.assertEquals("passed", res, value);
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(contentID, property);
			long[] rs = dao.load(contentID, property);
			Assert.assertEquals("passed", rs.length, 0);
			dao.delete(contentID);
			nodedao.delete(contentID);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	public void testLoad() {
		//在tearDown()中测试delete()和load()
	}
}
