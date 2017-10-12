package com.fulong.longcon.security.dao;

import com.fulong.common.dao.DaoTestCase;

/**
 *
 * <p>Title: Longcon Passport System</p>
 *
 * <p>Description: Longcon Passport System</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lishaobo
 * @version 1.0
 * ServiceDao现在不用，所以，不做测试。
 */

public class TestServiceDao extends DaoTestCase {
    /*
    private ServiceDao jdbcServiceDao = null;

    protected void setUp() throws Exception {
        super.setUp();
        jdbcServiceDao = (ServiceDao)this.factory.getDao(ServiceDao.class);
    }

    protected void tearDown() throws Exception {
        jdbcServiceDao = null;
        super.tearDown();
    }

    public void testCountAll() {
        ServiceData data = new ServiceData();
        data.setName("testService20070323");

        jdbcServiceDao.create(data);
        String ID = data.getId();
        ServiceData returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData.getId(), data.getId());
        jdbcServiceDao.delete(ID);
        returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData, null);


        String type = "";
        long expectedReturn = 0L;
        long actualReturn = jdbcServiceDao.countAll(type);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testCreate() {
        ServiceData data = new ServiceData();
        data.setName("testService20070323");

        jdbcServiceDao.create(data);
        String ID = data.getId();
        ServiceData returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData.getId(), data.getId());
        jdbcServiceDao.delete(ID);
        returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData, null);
    }

    public void testDelete() {
        ServiceData data = new ServiceData();
         data.setName("testService20070323");

         jdbcServiceDao.create(data);
         String ID = data.getId();
         ServiceData returnData = jdbcServiceDao.findByPrimaryKey(ID);
         assertEquals("return value", returnData.getId(), data.getId());
         jdbcServiceDao.delete(ID);
         returnData = jdbcServiceDao.findByPrimaryKey(ID);
         assertEquals("return value", returnData, null);

    }

    public void testDeleteByProvider() {
        String orgID = "";
        jdbcServiceDao.deleteByProvider(orgID);

    }

    public void testFindAll() {
        String sort = "";
        String order = "";
        int fromIndex = 0;
        int number = 0;
        String type = "";
        ServiceData[] expectedReturn = null;
        ServiceData[] actualReturn = jdbcServiceDao.findAll(sort, order, fromIndex, number, type);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testFindAllId() {
        String sort = "";
        String order = "";
        int fromIndex = 0;
        int number = 0;
        String type = "";
        String[] expectedReturn = null;
        String[] actualReturn = jdbcServiceDao.findAllId(sort, order, fromIndex, number, type);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testFindByGroup() {
        String groupid = "";
        ServiceData[] expectedReturn = null;
        ServiceData[] actualReturn = jdbcServiceDao.findByGroup(groupid);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testFindByPrimaryKey() {
        ServiceData data = new ServiceData();
        data.setName("testService20070323");

        jdbcServiceDao.create(data);
        String ID = data.getId();
        ServiceData returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData.getId(), data.getId());
        jdbcServiceDao.delete(ID);
        returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData, null);

    }

    public void testFindByProvider() {
        String principalId = "";
        ServiceData[] expectedReturn = null;
        ServiceData[] actualReturn = jdbcServiceDao.findByProvider(principalId);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testFindByType() {
        String type = "";
        ServiceData[] expectedReturn = null;
        ServiceData[] actualReturn = jdbcServiceDao.findByType(type);
        assertEquals("return value", expectedReturn, actualReturn);

    }

    public void testFindByUser() {
        String principalId = "";
        ServiceData[] expectedReturn = null;
        ServiceData[] actualReturn = jdbcServiceDao.findByUser(principalId);
        assertEquals("return value", expectedReturn, actualReturn);

    }


    public void testUpdate() {
        ServiceData data = new ServiceData();
        data.setName("testService20070323");

        jdbcServiceDao.create(data);
        data.setName("nameAfterUpdated");
        jdbcServiceDao.update(data);
        String ID = data.getId();
        ServiceData returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData.getName(), "nameAfterUpdated");
        jdbcServiceDao.delete(ID);
        returnData = jdbcServiceDao.findByPrimaryKey(ID);
        assertEquals("return value", returnData, null);

    }
*/
}
