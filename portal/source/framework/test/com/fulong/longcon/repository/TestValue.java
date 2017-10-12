package com.fulong.longcon.repository;

import java.util.regex.Pattern;

/**
 * <p>Title: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Description: ��Ԧ��վ���ݹ���ϵͳ��������</p>
 *
 * <p>Copyright: �����пƸ������������޹�˾ 2006</p>
 *
 * <p>Company: �����пƸ������������޹�˾</p>
 *
 * @author not attributable
 * @version 2.0
 */
public class TestValue extends RepositoryTestCase {



    private Value value = null;

    protected void setUp() throws Exception {
        super.setUp();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }



    public void testGetValue()
    {




        value = this.repository.getValueFactory().createValue(true);
        this.assertEquals(value.getBoolean(), true);
        try{
            value.getDate();
        }
        catch(ValueFormatException ex){
            //ignore this exception;
            //            throw new RuntimeException(ex);
            System.out.println("boolean value can not get Date value");
        }
        String strValue="hello";
        value = this.repository.getValueFactory().createValue(strValue);
        this.assertEquals(value.getString(), strValue);
        this.assertEquals(value.getType(), PropertyType.STRING);
        try{
            value.getDate();
        }
        catch(ValueFormatException ex){
            //ignore this exception;
            //            throw new RuntimeException(ex);
            System.out.println("string value can not get Date value");
        }
        try{
            value.getLong();
        }
        catch(ValueFormatException ex){
            //ignore this exception;
            //            throw new RuntimeException(ex);
            System.out.println("string value can not get Date value");
        }
        try{
            value.getStream();
        }
        catch(ValueFormatException ex){
            //ignore this exception;
            //            throw new RuntimeException(ex);
            System.out.println("string value can not get Date value");
        }


    }


}
