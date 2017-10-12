package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.SQLException;
import java.util.Date;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.NodeDefinitionDao;
import com.fulong.longcon.repository.dao.NodeTypeDao;
import com.fulong.longcon.repository.data.NodeDefinitionData;
import com.fulong.longcon.repository.data.NodeTypeData;

/**
 * 
 * 构造nodedefinition 树 
 *   test.definitionID3
 *     |___test.definitionID2
 *       |____test.definitionID1
 *测试本例  加com.fulong.longcon.repository.dao.NodeDefinitionDao=com.fulong.longcon.repository.dao.mysql.MysqlNodeDefinitionDao到impl.mysq.properties.       
 */

public class SqlserverNodeTypeDaoTest extends SqlserverDaoTestCase {

	
	private NodeTypeDao dao = null;
	private NodeTypeData data = new NodeTypeData();
	private String definitionID = "test.definitionID";
	private String definitionID2 = "test.definitionID2";
	private String ID = "test.ID";
	
	private NodeDefinitionDao def=null;

	protected void setUp() throws Exception {
		super.setUp();
		dao = (NodeTypeDao) factory.getDao(NodeTypeDao.class);
		def =(NodeDefinitionDao) factory.getDao(NodeDefinitionDao.class);

	}

	/**
	 * 构造测试数据
	 * @param main 是否是主类
	 */
	private void insert(boolean main,String definitionID) {
		try {
			data.setDefinitionID(definitionID);
			data.setID(ID);
			data.setMain(main);
			dao.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}

	}

	protected void tearDown() throws Exception {		
		super.tearDown();
	}


	public final void testDelete() throws SQLException {
		this.insert(false,this.definitionID);
		NodeTypeData rs=dao.findByDefinitionAndPKID(definitionID, ID);			
		Assert.assertEquals("passed", rs.getID(), ID);
		try {
			dao.delete(this.ID);
		} catch (SQLException e) {
			factory.rollback();
		}
		rs=dao.findByDefinitionAndPKID(definitionID, ID);
		Assert.assertNull("passed",rs);
	}

	public final void testDeletePrimary() throws SQLException {
		this.insert(true,definitionID);
		this.insert(false,definitionID2);
		String rs=dao.findPrimaryByPKID(ID);
		Assert.assertEquals("passed", rs,definitionID);
		String[] s=dao.findMixinNodeDefinitions(ID);
		Assert.assertEquals("passed", s[0],definitionID2);
		try{
			dao.deletePrimary(ID);
		}catch(SQLException e){
			factory.rollback();
		}
		rs=dao.findPrimaryByPKID(ID);
		Assert.assertNull("passed",rs);
		try{
			dao.deleteByDefinition(this.definitionID2);
		}catch(SQLException e){
			factory.rollback();
		}		
		s=dao.findMixinNodeDefinitions(ID);
		Assert.assertEquals("passed", s.length,0);
		
	}

	public final void testDeleteByDefinitionAndPKID() throws SQLException {		
		this.insert(false,this.definitionID);
		NodeTypeData rs=dao.findByDefinitionAndPKID(definitionID, ID);			
		Assert.assertEquals("passed", rs.getID(), ID);
		try {
			dao.deleteByDefinitionAndPKID(definitionID, ID);
		} catch (SQLException e) {
			factory.rollback();
		}
		rs=dao.findByDefinitionAndPKID(definitionID, ID);
		Assert.assertNull("passed",rs);
	}


	public final void testFindByDefinitionRecAndPKID() throws SQLException {
		NodeDefinitionData data=new NodeDefinitionData();
		data.setCreateTime(new Date());
		data.setDelete_mark(false);
		data.setDescription("test");
		data.setID("test.definitionID3");
		data.setName("test3");
		data.setSuperID("");
		data.setSystem(false);
		try {
			def.insert(data);
		} catch (SQLException E) {
			factory.rollback();
		}
		data.setID("test.definitionID2");
		data.setSuperID("test.definitionID3");
		try {
			def.insert(data);
		} catch (SQLException E) {
			factory.rollback();
		}
		data.setID("test.definitionID");
		data.setSuperID("test.definitionID2");
		try {
			def.insert(data);
		} catch (SQLException E) {
			factory.rollback();
		}
		//插入2个NODE
		this.insert(false, definitionID);
		this.insert(true, definitionID2);		
		NodeTypeData[] rs=dao.findByDefinitionRecAndPKID("test.definitionID3", ID);
		Assert.assertEquals("passed", rs.length, 2);
		try {
			dao.deleteByDefinitionRec("test.definitionID3");
			def.delete("test.definitionID3");
		} catch (SQLException E) {
			factory.rollback();
		}			
		rs=dao.findByDefinitionRecAndPKID("test.definitionID3", ID);
		Assert.assertEquals("passed", rs.length,0);
	}
}
