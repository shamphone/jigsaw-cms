package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;
import java.util.ArrayList;

import com.fulong.longcon.repository.dao.NodeDefinitionSuperDao;

public class MysqlNodeDefinitionSuperDaoTest extends MysqlDaoTestCase{
	private NodeDefinitionSuperDao dao=null;
	private String  grandfather  ="test.definition.1";
	private String  father="test.definition.2";
	private String  son="test.definition.3";
	
	protected void setUp() throws Exception {
		super.setUp();
		dao=(NodeDefinitionSuperDao)this.factory.getDao(NodeDefinitionSuperDao.class);			
		dao.insert(father, grandfather);
		dao.insert(son, father);
	}
	protected void tearDown() throws Exception {
		this.delete();
		super.tearDown();
		
	}
	
	public final void delete(){
		try{
			dao.delete(son, father);
			dao.delete(father, grandfather);
		}catch (SQLException e) {
			factory.rollback();
		}
	}
	
	public final void testfindByNodeDefinitionID(){
		try{
			ArrayList<String> rs = dao.findByNodeDefinitionID(father);
			System.out.println(rs.get(0));
		}catch (SQLException e) {
		}
	}
	
	public final void testfindByNodeDefinitionIDAndSuperID(){
		try{
			String rs = dao.findByNodeDefinitionIDAndSuperID(father,grandfather);
			System.out.println(rs);
		}catch (SQLException e) {
		}
	}
}
