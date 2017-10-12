package com.fulong.longcon.repository.dao.mysql;

import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;

import java.sql.*;
import java.util.Date;

import junit.framework.Assert;;
;
/**
 * 
 * @author LJY
 * 构造nodedefinition树
 * test.definition.1
 *   |___test.definition.2
 *     |___test.definition.3
 * 
 */
public class MysqlNodeDefinitionDaoTest extends MysqlDaoTestCase {
	private NodeDefinitionDao dao=null;
	private String  grandfather  ="test.definition.1";
	private String  father="test.definition.2";
	private String  son="test.definition.3";
	NodeDefinitionData data=new NodeDefinitionData();
	
	protected void setUp() throws Exception {
		super.setUp();
		dao=(NodeDefinitionDao)this.factory.getDao(NodeDefinitionDao.class);			
	}
   private void insert(String pkid,String parent){
	   data.setCreateTime(new Date());
		data.setDelete_mark(false);
		data.setDescription("test");
		data.setID(pkid);
		data.setName("test");
		data.setSuperID(parent);
		data.setSystem(false);
	   try{
		   dao.insert(data);
	   }catch(SQLException e){
		   factory.rollback();
	   }
   }
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	

	public final void testUpdate() throws SQLException {
		insert(grandfather, "");
		data.setID(grandfather);
		data.setName("test.error");
		try {
			dao.update(data);
		} catch (SQLException e) {
			factory.rollback();
		}
		NodeDefinitionData rs = dao.findByID(grandfather);
		Assert.assertEquals("passed", rs.getName(), "test.error");
		try {
			dao.delete(grandfather);
		} catch (SQLException e) {
			factory.rollback();
		}
		rs = dao.findByID(grandfather);
		Assert.assertNull("passed", rs);
	}

	public final void testFindBySuper() throws SQLException {
		insert(grandfather, "");
		insert(father, grandfather);
		insert(son, father);
		NodeDefinitionData[] rs=dao.findBySuper(father);
		Assert.assertEquals("passed", rs[0].getID(), son);
		rs = dao.findAllBySupser(grandfather);
		//3个NODEDEFINITION
		Assert.assertEquals("passed", rs.length,3);
		try{
		dao.delete(grandfather);
		rs = dao.findAllBySupser(grandfather);
		Assert.assertEquals("passed",rs.length,0);		//
		}catch(SQLException e){
			factory.rollback();
		}
	}	

	public final void testFindAll() throws SQLException {
		NodeDefinitionData[] rs = dao.findAll();
		Assert.assertNotSame("passed", rs.length, 0);
	}
}
