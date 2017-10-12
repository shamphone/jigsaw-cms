package com.fulong.longcon.repository.dao.mysql;

import java.sql.SQLException;

import junit.framework.Assert;

import com.fulong.longcon.repository.dao.NodeQuotaDao;
import com.fulong.longcon.repository.data.NodeQuotaData;
import com.fulong.longcon.security.data.PrincipalTypeData;

public class MysqlNodeQuotaDaoTest extends MysqlDaoTestCase{
	private NodeQuotaDao dao = null;
	private NodeQuotaData data = new NodeQuotaData();
	private String PrincipalID= "100";
	private Integer PrincipalType= 0;
	private String NodeID= "test_id";
	private String Unit= "long-term";
	private Integer Quota= 5;
	

	protected void setUp() throws Exception {
		super.setUp();
		dao = (NodeQuotaDao) factory.getDao(NodeQuotaDao.class);
		try {
			data.setPrincipalID(PrincipalID);
			data.setPrincipalType(PrincipalType);
			data.setNodeID(NodeID);
			data.setUnit(Unit);
			data.setQuota(Quota);
			dao.insert(data);
			
			PrincipalID= "120";
			data.setPrincipalID(PrincipalID);
			data.setPrincipalType(PrincipalType);
			data.setNodeID(NodeID);
			data.setUnit(Unit);
			data.setQuota(Quota);
			dao.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	protected void tearDown() throws Exception {
		try {
			dao.delete(NodeID, PrincipalID);
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}
	
	public final void testfindByCategory(){
		try{
			NodeQuotaData[] rs= dao.findByCategory(NodeID);
			Assert.assertEquals("passed", rs.length,2);
		}catch(SQLException e){}
	}
	
	public final void testfindByPrincipal(){
		try{
		NodeQuotaData rs=dao.findByPrincipal(PrincipalID, NodeID);
		System.out .println(rs.getNodeID()+" "+rs.getPrincipalID()+" "+rs.getQuota()+" "+rs.getUnit());
		}catch(SQLException e){}
	}
	
	public final void testfindPrincsByNode(){
		try{
			PrincipalTypeData[] rs = dao.findPrincsByNode(NodeID);
			for(int i=0;i<rs.length;i++){
				System.out .println(rs[i].getPrincipal()+" "+rs[i].getType());
			}
		}catch(SQLException e){}
	}
	
	public final void testfindQuota(){
		try{
			int rs= dao.findQuota(NodeID, PrincipalID);
			Assert.assertEquals("passed", rs,5);
		}catch(SQLException e){}
	}
	
	public final void testfindUnit(){
		try{
			String rs= dao.findUnit(NodeID, PrincipalID);
			Assert.assertEquals("passed", rs,"long-term");
		}catch(SQLException e){}
	}
	
	public final void testneedQuotaLimit(){
		try{
		
			NodeQuotaData rs = dao.needQuotaLimit(NodeID);
			System.out.println(rs.getNodeID()+" "+rs.getPrincipalID());
		}catch(SQLException e){}
	}
	
	public final void testupdateUnitOrQuota(){
		Unit="long";
		Quota=6;
		try{
			data.setPrincipalID(PrincipalID);
			data.setNodeID(NodeID);
			data.setUnit(Unit);
			data.setQuota(Quota);
			dao.updateUnitOrQuota(data);
			this.testfindByPrincipal();
		}catch (SQLException e) {
			factory.rollback();
		}
	}
}
