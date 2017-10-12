package com.fulong.longcon.counter.dao;

import java.sql.SQLException;

import com.fulong.longcon.repository.dao.sqlserver.SqlserverDaoTestCase;
/**
 *  
 * Coolink协同工作框架模型
 * 
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 * 
 * Company: 北京中科辅龙计算机技术股份有限公司
 * 
 * @author songbo
 * 
 * @version 2.0
 * 
 */
public class TestClickCountDaoTest  extends SqlserverDaoTestCase {

	  private ClickCountDao oracleClickCountDao = null;

	    protected void setUp() throws Exception {
	        super.setUp();
	        oracleClickCountDao = (ClickCountDao)this.factory.getDao(ClickCountDao.class);
	    }

	    protected void tearDown() throws Exception {
	        oracleClickCountDao = null;
	        super.tearDown();
	    }

	    public void testInsertCount() {
	        String name = "";
	        try {
	            oracleClickCountDao.insertCount(name);
	        } catch (SQLException ex) {
	        }

	    }

	    public void testLoadCount() {
	        String name = "";
	        long expectedReturn = 0L;
	        long actualReturn = 0L;
	        try {
	            actualReturn = oracleClickCountDao.loadCount(name);
	        } catch (SQLException ex) {
	        }
	        assertEquals("return value", expectedReturn, actualReturn);

	    }

	    public void testSaveCount() {
	        String name = "";
	        long newValue = 0L;
	        try {
	            oracleClickCountDao.saveCount(name, newValue);
	        } catch (SQLException ex) {
	        }
	    }
}
