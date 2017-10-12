package com.fulong.longcon.repository.dao.mysql;

import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.dao.PropertyDefinitionDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;
import com.fulong.longcon.repository.data.PropertyDefinitionData;
import junit.framework.Assert;
import java.sql.*;
import java.util.Date;

/**
 * 
 * @author LJY
 * 
 */

public class MysqlPropertyDefinitionDaoTest extends MysqlDaoTestCase {
	private PropertyDefinitionDao dao = null;
	private PropertyDefinitionData data = new PropertyDefinitionData();
	private String name = "test.name";
	private String definition = "test.definition";
	private String ID = "test_ID";
	private int type = 1;

	private NodeDefinitionDao def = null;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (PropertyDefinitionDao) this.factory
				.getDao(PropertyDefinitionDao.class);
		def = (NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);

		data.setType(type);
		data.setID(ID);
		data.setName(name);
		data.setNodeDefinitionID(definition);
		data.setDeletable(true);
		data.setOrderNo(1);
		try {
			dao.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}

	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(definition, ID);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	public final void testFindDefs() throws SQLException {
		PropertyDefinitionData[] rs = dao.findDefs(this.definition);
		Assert.assertEquals("passed", rs[0].getID(), ID);
		rs = dao.findByNodeDefinition(definition, ID);
		Assert.assertEquals("passed", rs[0].getID(), ID);
		data.setReferenceType(definition);
		try {
			dao.update(data);
			rs = dao.findByRefNodeDefinition(definition);
			Assert.assertEquals("passed", rs[0].getID(), ID);
		} catch (SQLException e) {
			factory.rollback();
		}

	}

	public final void testFindByRecPNodeDefinition() throws SQLException {
		NodeDefinitionData data = new NodeDefinitionData();
		data.setCreateTime(new Date());
		data.setDelete_mark(false);
		data.setDescription("test");
		data.setID("test.definitionroot");
		data.setName("test3");
		data.setSuperID("");
		data.setSystem(false);
		try {
			def.insert(data);
			data.setID(definition);
			data.setSuperID("test.definitionroot");
			def.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}
		PropertyDefinitionData[] rs = dao.findByRecPNodeDefinition(
				"test.definitionroot", ID);
		Assert.assertEquals("passed", rs[0].getID(), ID);
		this.data.setType(0);
		try {
			dao.update(this.data);
			dao.findChildsByNodeDef(definition);
			rs = dao.findByRecPNodeDefinition("test.definitionroot", ID);
			Assert.assertEquals("passed", rs[0].getID(), ID);
		} catch (SQLException e) {
			factory.rollback();
		}
		try {
			def.delete("test.definitionroot");
		} catch (SQLException e) {
			factory.rollback();
		}

	}
	public final void testUpdateOrderNum() {
		try {
			dao.updateOrderNum(definition, ID, 100);
			PropertyDefinitionData[] rs = dao.findDefs(this.definition);
			Assert.assertEquals("passed", rs[0].getOrderNo(), 100);
		} catch (SQLException e) {
			factory.rollback();
		}
		
	}

}
