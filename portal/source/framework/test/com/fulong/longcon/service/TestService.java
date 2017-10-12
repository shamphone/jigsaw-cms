package com.fulong.longcon.service;

/**
*
* <p>Title: 服务模型系统</p>
*
* <p>Description: 服务模型系统</p>
*
* <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
*
* <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
*
* @author liuzijun
* @version 1.0
*/

public class TestService extends ServiceTestCase {

	protected void setUp() throws Exception {
        super.setUp();
        //测试获取和设置服务的名称和描述
        String ID = "testCategory";
    	String name="testCategory1";
    	String serviceID = "testService";
    	String serviceName = "testServiceName";
   
    }

    protected void tearDown() throws Exception {
        super.tearDown();

    }
}
