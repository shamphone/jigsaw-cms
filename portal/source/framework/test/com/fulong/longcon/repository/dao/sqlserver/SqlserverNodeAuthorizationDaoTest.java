package com.fulong.longcon.repository.dao.sqlserver;

import java.sql.SQLException;

import com.fulong.longcon.repository.dao.NodeAuthorizationDao;
import com.fulong.longcon.repository.data.NodeAuthorizationData;
import com.fulong.longcon.security.data.PrincipalTypeData;

/**
 * 
 * @author songbo
 *
 */
public class SqlserverNodeAuthorizationDaoTest extends SqlserverDaoTestCase {

	private NodeAuthorizationDao dao = null;
	private NodeAuthorizationData data = new NodeAuthorizationData();
	private String PrincipalID= "100";
	private Integer PrincipalType= 0;
	private String NodeID= "test_id";
	private String Action= "read";
	private Integer Quota= 5;
	

	protected void setUp() throws Exception {
		super.setUp();
		dao = (NodeAuthorizationDao) factory.getDao(NodeAuthorizationDao.class);
		try {
			data.setPrincipalID(PrincipalID);
			data.setPrincipalType(PrincipalType);
			data.setNodeID(NodeID);
			data.setAction(Action);
			data.setQuota(Quota);
			dao.create(data);
			
			PrincipalID= "120";
			NodeID="test_id2";
			data.setPrincipalID(PrincipalID);
			data.setPrincipalType(PrincipalType);
			data.setNodeID(NodeID);
			data.setAction(Action);
			data.setQuota(Quota);
			dao.create(data);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(data);
			dao.deleteByNode("test_id");
			dao.create(data);
			dao.deleteByNodeAndAction(NodeID, Action);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}
	
	public final void testfindAuthorizationByPrincipal(){
		try{
			NodeAuthorizationData rs= dao.findAuthorizationByPrincipal(NodeID, Action, PrincipalID);
			System.out.println(rs.getNodeID()+" "+rs.getPrincipalID());
		}catch(SQLException e){}
	}
	
	public final void testfindByPrincipal(){
		try{
		NodeAuthorizationData[] rs=dao.findByPrincipal(PrincipalID);
		for(int i=0;i<rs.length;i++){
			System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getNodeID());
		}
		}catch(SQLException e){}
	}
	
	public final void testfindPrincipalByAuthorization(){
		try{
			NodeAuthorizationData[] rs = dao.findPrincipalByAuthorization(NodeID,Action);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getNodeID());
			}
		}catch(SQLException e){}
	}
	
	public final void testfindByNode(){
		try{
			NodeAuthorizationData[] rs= dao.findByNode(NodeID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipalID()+" "+rs[i].getPrincipalType()+" "+rs[i].getNodeID());
			}
		}catch(SQLException e){}
	}
	
	public final void testfindPrincsByNode(){
		try{
			PrincipalTypeData[] rs= dao.findPrincsByNode(NodeID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipal()+" "+rs[i].getType());
			}
		}catch(SQLException e){}
	}
	
	public final void testfindReadPrincsByNode(){
		try{
			PrincipalTypeData[] rs= dao.findPrincsByNode(NodeID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipal()+" "+rs[i].getType());
			}
		}catch(SQLException e){}
	}
	
	public final void testfindActions(){
		try{
			String[] rs= dao.findActions(NodeID, PrincipalID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].length());
			}
		}catch(SQLException e){}
	}
	
	public final void testclearActions(){
		try{
			dao.clearActions(NodeID,PrincipalID);
		}catch(SQLException e){}
	}
	
	public final void testupdataPrincipal(){
		try{
			dao.updataPrincipal(NodeID,PrincipalID,"180");
			this.testfindByNode();
		}catch (SQLException e) {
			factory.rollback();
		}
	}
	
	
}
