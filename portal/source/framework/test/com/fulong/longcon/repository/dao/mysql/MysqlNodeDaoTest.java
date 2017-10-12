/**
 * 
 */
package com.fulong.longcon.repository.dao.mysql;

import com.fulong.longcon.repository.dao.NodeDao;
import com.fulong.longcon.repository.data.NodeData;
import java.sql.*;

import junit.framework.Assert;

/**
 * @author LJY 构造的node的树形结构如下：以PKID显示
 * 即是 一个爷爷 一个爸爸  一个孙子一个孙女
 *        test.node.father (grandfather) 
 *           |______test.node.son (father)
 *                 |_____test.node.grandson 
 *                 |_____test.node.Granddaughter
 * 
 */
public class MysqlNodeDaoTest extends MysqlDaoTestCase {
	private NodeDao dao = null;
	private NodeData data = new NodeData();
	private String family="test.family.root";
	private String grandfather = "test.node.grandfather";
	private String father = "test.node.father";
	private NodeData son = new NodeData();
	private NodeData grandson = new NodeData();
	private NodeData granddaughter = new NodeData();
	private int ONE =1;
	private int TWO =2;
	private int THREE =3;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		dao = (NodeDao) factory.getDao(NodeDao.class);
		try {
			data.setID(grandfather);
			data.setName("test.node.name");
			data.setOrderNo(1);
			data.setParentID(family);
			dao.insert(data);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		try {
			dao.delete(grandfather);
			dao.delete(father);
			dao.delete("test.node.grandson");
			dao.delete("test.node.granddaughter");
		} catch (SQLException e) {
			factory.rollback();
		}
		super.tearDown();
	}

	private void setFather() {
		son.setID(father);
		son.setName("test.node.name");
		son.setOrderNo(ONE);
		son.setParentID(grandfather);
	}

	private void setGrandson() {
		grandson.setID("test.node.grandson");
		grandson.setName("test.node.name");
		grandson.setOrderNo(ONE);
		grandson.setParentID(father);
	}

	private void setGranddaughter() {
		granddaughter.setID("test.node.granddaughter");
		granddaughter.setName("test.node.name");
		granddaughter.setOrderNo(TWO);
		granddaughter.setParentID(father);
	}
	/**
	 * 测试修改grandfather这个NODE的序号 原来的序号是1 修改为11
	 */
	public final void testUpdate() {
		// 检查原来的序号是否为1
		NodeData rs = null;
		try {
			rs = dao.findByID(grandfather);
		} catch (SQLException e1) {

		}
		Assert.assertEquals("passed", rs.getOrderNo(), 1);
		data.setID(grandfather);
		data.setName("test.node.name");
		data.setOrderNo(11);
		data.setParentID(family);
		try {
			dao.update(data);
		} catch (SQLException e) {
			factory.rollback();
		}
		// 检查修改后的序号为11
		try {
			rs = dao.findByID(grandfather);
		} catch (SQLException e1) {

		}
		Assert.assertEquals("passed", rs.getOrderNo(), 11);

	}
     /**
	 * 测试grandfather的返回值是否为grandfather这个NODE
	 */
	public final void testFindByID() {
		NodeData rs = null;
		try {
			rs = dao.findByID(grandfather);
		} catch (SQLException e1) {
		}
		Assert.assertEquals("passed", rs.getID(), grandfather);
	}

	/**
	 * 检查grandfather下有几个NODE,插入father数据 ，结果应该为1个。
	 */
	public final void testCountByParentString() {
		this.setFather();
		try {
			dao.insert(son);
			long rs = dao.countByParent(grandfather);
			Assert.assertEquals("passed", rs,ONE);
		} catch (SQLException e) {
			//
		}
	}

	/**
	 * 创建儿子和2个孙子  递归查找家族树，期望结果为3 不包括爷爷本身
	 * 
	 * .
	 */
	public final void testCountRecByParentString() {	
		setFamily();
		try {
			long rs=dao.countRecByParent(grandfather);
			Assert.assertEquals("passed", rs, THREE);
		} catch (SQLException e) {
			
		}
	}

	/**
	 * 创建儿子和2个孙子  递归查找家族树，期望结果为3 不包括爷爷本身,注意与上面稍有不同
	 * 
	 * .
	 */
	public final void testCountRecByParentStringString() {
		setFamily();
		try {
			long rs=dao.countRecByParent(grandfather,"test.node.name");
			Assert.assertEquals("passed", rs, THREE);
		} catch (SQLException e) {			
		}
	}
	
   private void setFamily() {
		this.setFather();
		this.setGrandson();
		this.setGranddaughter();
		try {
			dao.insert(son);
			dao.insert(grandson);
			dao.insert(granddaughter);
		} catch (SQLException e) {
			factory.rollback();
		}
	}

	/**
	 * 
	 * 打印家族树
	 * .
	 * @throws SQLException 
	 */
	public final void testFindByParentRecStringLongLong() throws SQLException {
		System.out.println("---------------the family tree is : father  grandson granddaugther-----------------" );
		setFamily();
		NodeData[] rs = dao.findByParentRec(grandfather, 0, 4);
		String s = "__";
		for (int i = 0; i < rs.length; i++) {
			s += s;
			if (rs[i] != null)
				System.out.println("the tree is :" + s + rs[i].getID());
		}
	}

	/**
	 *  注意与上有所不同
	 * 
	 * .
	 * @throws SQLException 
	 */
	public final void testFindByParentRecStringStringLongLong() throws SQLException {
		System.out.println("---------------the family tree is : father  grandson granddaugther-----------------" );
		setFamily();
		NodeData[] rs = dao.findByParentRec(grandfather,"test.node.name", 0, 4);
		String s = "__";
		for (int i = 0; i < rs.length; i++) {
			s += s;
			if (rs[i] != null)
				System.out.println("the tree is :" + s + rs[i].getID());
		}
	}

	/**
	 * 自定义SQL 此处不测试 在业务逻辑collection测试该接口
	 * .
	 */
	public final void testSearch() {
	
	}

	/**
	 * 同上
	 * .
	 */
	public final void testCountResultNum() {
		
	}
		
        

	/**
	 * find grandfather
	 * @throws SQLException 
	 */
	public final void testFindByName() throws SQLException {
		NodeData rs=dao.findByName("test.node.name");
		Assert.assertEquals("passed", rs.getID(), grandfather);
		
		
	}

	/**
	 * 结果同上  但不同 
	 * @throws SQLException 
	 */
	public final void testFindByParentStringStringLongLong() throws SQLException {
		NodeData[] rs=dao.findByParent(family,"test.node.name",0,1);
		Assert.assertEquals("passed", rs[0].getID(), grandfather);
	}

	/**
	 * 
	 * father下有2个NODE
	 * .
	 */
	public final void testCountByParentStringString() {
		this.setFamily();
		try {
			dao.insert(son);
			long rs = dao.countByParent(father, "test.node.name");
			Assert.assertEquals("passed", rs,TWO);
		} catch (SQLException e) {
			//
		}
	}

	/**
	 * Test method for
	 * {@link com.fulong.longcon.repository.dao.mysql.MysqlNodeDao#deleteAll(java.lang.String)}
	 * .
	 */
	public final void testDeleteAll() {
		this.setFamily();
		try{
			dao.deleteAll(grandfather);
		}catch(SQLException e){
			factory.rollback();
		}
		try{
		long rs=dao.countRecByParent(grandfather, "test.node.name");
		Assert.assertEquals("passed", rs, 0);		
		}catch(SQLException e){			
		}
	}

	/**
	 * 
	 * 打印家族树的ID
	 * .
	 * @throws SQLException 
	 */
	public final void testChildrenIDs() throws SQLException {
		System.out.println("---------------the family tree is : father  grandson granddaugther-----------------" );
		setFamily();
		String[] rs = dao.childrenIDs(grandfather);
		String s = "__";
		for (int i = 0; i < rs.length; i++) {
			s += s;
			if (rs[i] != null)
				System.out.println("the tree is :" + s + rs[i]);
		}
	}

	/** 
	 * .
	 * @throws SQLException 
	 */
	public final void testGetMaxOrderNo() throws SQLException {
		this.setFamily();
		int rs=dao.getMaxOrderNo(father);
		Assert.assertEquals("passed", rs, TWO);
	}

	/**
	 * 复制一个NODE  NAME相同的NODE就会有2个
	 */
	public final void testCopyString() {
		this.setFamily();
		NodeData copyed=null;
		try{
			copyed=dao.copy(grandfather);
		}catch(SQLException e){
			factory.rollback();
		}
		try{
			long rs=dao.countRecByParent(family, "test.node.name");
			Assert.assertEquals("passed", rs, 5);
	    }catch(SQLException e){			
			}
	    if(copyed!=null)
	    try{
	    	dao.delete(copyed.getID());
	    }catch(SQLException e){
	    	factory.rollback();
	    }
	}
	
	/**
	 * 
	 * 打印输出结果查看
	 * .
	 * @throws SQLException 
	 */
	public final void testFindByParentStringLongLong() throws SQLException {
		setFamily();
		NodeData[] rs1=dao.findByParent(father, "test.node.name", 0, 1);
		NodeData[] rs2=dao.findByParent(father, "test.node.name", 1, 1);
		System.out.println("the first grandson is : "+ rs1[0].getID());
		System.out.println("the second grandson is : "+ rs2[0].getID());
	}

}
