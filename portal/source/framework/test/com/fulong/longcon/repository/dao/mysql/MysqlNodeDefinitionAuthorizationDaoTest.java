package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;

import com.fulong.longcon.repository.dao.NodeDefinitionAuthorizationDao;
import com.fulong.longcon.repository.data.NodeDefinitionAuthorizationData;
import com.fulong.longcon.security.data.PrincipalTypeData;

public class MysqlNodeDefinitionAuthorizationDaoTest extends MysqlDaoTestCase{
	private NodeDefinitionAuthorizationDao dao = null;
	private NodeDefinitionAuthorizationData data = new NodeDefinitionAuthorizationData();
	private String PrincipalID= "2000000000000";
	private Integer PrincipalType= 1;
	private String DefinitionID= "test_id1";
	private String Action= "manage";
	

	protected void setUp() throws Exception {
		super.setUp();
		dao = (NodeDefinitionAuthorizationDao) factory.getDao(NodeDefinitionAuthorizationDao.class);
		try {
			data.setPrincipalID(PrincipalID);
			data.setPrincipalType(PrincipalType);
			data.setDefinitionID(DefinitionID);
			data.setAction(Action);
			dao.create(data);
			
			PrincipalID= "3000000000000";
			DefinitionID="test_id2";
			Action="read";
			data.setPrincipalID(PrincipalID);
			data.setPrincipalType(PrincipalType);
			data.setDefinitionID(DefinitionID);
			data.setAction(Action);
			dao.create(data);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(data);
			dao.deleteByNode("test_id1");
			dao.create(data);
			dao.deleteByNodeAndAction(DefinitionID, Action);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}
	
	public final void testfindAuthorizationByPrincipal(){
		try{
			NodeDefinitionAuthorizationData rs=dao.findAuthorizationByPrincipal(DefinitionID, Action, PrincipalID);
				System.out .println(rs.getPrincipalID()+" "+rs.getPrincipalType()+" "+rs.getDefinitionID());
			}catch(SQLException e){}
	}
	
	public final void testfindByPrincipal(){
		try{
			NodeDefinitionAuthorizationData[] rs=dao.findByPrincipal(PrincipalID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getDefinitionID());
			}
			}catch(SQLException e){}
	}
	
	public final void testfindPrincipalByAuthorization(){
		try{
			NodeDefinitionAuthorizationData[] rs=dao.findPrincipalByAuthorization(DefinitionID, Action);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getDefinitionID());
			}
			}catch(SQLException e){}
	}
	
	public final void testfindByPrincipalAndAction(){
		try{
			NodeDefinitionAuthorizationData[] rs=dao.findByPrincipalAndAction(PrincipalID, Action);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getDefinitionID());
			}
			}catch(SQLException e){}
	}
	
	public final void testfindByNode(){
		try{
			NodeDefinitionAuthorizationData[] rs=dao.findByNode(DefinitionID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getDefinitionID());
			}
			}catch(SQLException e){}
	}
	
	
	public final void testfindActions(){
		try{
			String[] rs=dao.findActions(DefinitionID,PrincipalID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i]);
			}
			}catch(SQLException e){}
	}
	
	public final void testfindPrincsByNode(){
		try{
			PrincipalTypeData[] rs=dao.findPrincsByNode(DefinitionID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipal()+" "+rs[i].getType());
			}
			}catch(SQLException e){}
	}
	
	public final void testfindReadPrincsByNode(){
		try{
			PrincipalTypeData[] rs=dao.findReadPrincsByNode(DefinitionID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipal()+" "+rs[i].getType());
			}
			}catch(SQLException e){}
	}
	
	public final void updataPrincipal(){
		try{
			dao.updataPrincipal(DefinitionID,PrincipalID,"8000000000000");
			this.testfindByNode();
		}catch (SQLException e) {
			factory.rollback();
		}
	}
	public final void testclearActions(){
		try{
			this.updataPrincipal();
			dao.clearActions(DefinitionID,"8000000000000");
			this.testfindByNode();
		}catch (SQLException e) {
			factory.rollback();
		}
	}
}
