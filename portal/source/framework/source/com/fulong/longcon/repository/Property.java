package com.fulong.longcon.repository;

import java.io.InputStream;
import java.util.Calendar;

/**
 * 内容的属性，代表内容的一个值。
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public interface Property extends Item{
    /**
         * 获取唯一标识符
         * @return String
         */
     public String getID();
    /**
     * 获取值类型,参见PropertyType枚举变量
     * @return int
     */
    public int getType();

    /**
     * 属性描述
     * @return PropertyDefinition
     */
    public PropertyDefinition getDefinition();

    /**
     * 设置日期值,如果无法转换，则抛出ValueFormatException
     * @param value Calendar
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(Calendar value) throws ValueFormatException;
    /**
     * 设置为内容引用,如果无法转换，则抛出ValueFormatException
     * @param value Content
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(Node value) throws ValueFormatException;
    /**
     * 设置为流,如果无法设置，则抛出ValueFormatException
     * @param value InputStream
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(InputStream value) throws ValueFormatException;
    /**
     * 设置为字符串,如果无法设置，则抛出ValueFormatException
     * @param value String
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(String value) throws ValueFormatException;
    /**
     * 设置为字符串数组,如果无法设置,则抛出ValueFormatException
     * @param values String[]
     * @throws ValueFormatException
     */
    public void setValue(String[] values) throws ValueFormatException;
    /**
     * 设置为布尔值,如果无法设置,则抛出ValueFormatException
     * @param value boolean
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(boolean value) throws ValueFormatException;
    /**
     * 设置为浮点值,如果无法设置,则抛出ValueFormatException
     * @param value double
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(double value) throws ValueFormatException;
    /**
     * 设置为长整型,如果无法设置,则抛出ValueFormatException
     * @param value long
     * @throws ValueFormatException 数据格式转换错误
     */
    public void setValue(long value) throws ValueFormatException;

    /**
     * 设置属性值
     * @param value PropertyValue 数据格式转换错误
     * @throws ValueFormatException
     */
    public void setValue(Value value) throws ValueFormatException;

    /**
     * 设置属性数组值
     * @param values PropertyValue 数据格式转换错误
     * @throws ValueFormatException
     */
    public void setValue(Value[] values) throws ValueFormatException;


    /**
     * 将属性值转换成字符串数组类型输出
     * @return String[]
     * @exception ValueFormatException 数据格式转换错误
     */
    public String[] getArray() throws ValueFormatException;

    /**
     * 将属性值转换成布尔值类型输出，等同于getValue().getBoolean();
     * @return boolean
     * @exception ValueFormatException 数据格式转换错误
     */
    public boolean getBoolean() throws ValueFormatException;

    /**
     * 将属性值转换成日期类型输出,等同于getValue().getDate();
     * @return Calendar
     * @exception ValueFormatException 数据格式转换错误
     */

    public Calendar getDate() throws ValueFormatException;

    /**
     * 将属性值转换成浮点类型输出，如果无法转换，则抛出ValueFormatException,
     * 等同于getValue().getDouble()
     * @return double
     * @exception ValueFormatException 数据格式转换错误
     */
    public double getDouble() throws ValueFormatException;

    /**
     * 将属性值转换成长整形输出，如果无法转换，则抛出ValueFormatException
     * 等同于getValue().getLong()
     * @return long
     * @throws ValueFormatException 数据格式转换错误
     */
    public long getLong() throws ValueFormatException;
    /**
     * 将属性值转换引用的内容输出，如果无法转换，则抛出ValueFormatException
     * 等同于getValue.getReference()
     * @return Content
     * @throws ValueFormatException 数据格式转换错误
     */
    public Node getReference() throws ValueFormatException;
    /**
     * 将属性值转换成流输出，如果无法转换，则抛出ValueFormatException
     * 等同于getValue.getStream();
     * @return InputStream
     * @throws ValueFormatException 数据格式转换错误
     */
    public InputStream getStream() throws ValueFormatException;
    /**
     * 将属性值转换成字符串输出，如果无法转换，则抛出ValueFormatException
     * 等同于getValue.getString();
     * @return String
     * @throws ValueFormatException 数据格式转换错误
     */
    public String getString() throws ValueFormatException;

    /**
     * 获取属性值
     * @return PropertyValue
     */
    public Value getValue();

    /**
     * 获取属性值数组
     * @return PropertyValue
     */
    public Value[] getValues();

    /**
     * 返回值得长度（以字节byte为单位），如果是PropertyValueType.BINARY类型，则返回字节数，其他类型返回其所占用的字节数。
     * 返回-1如果字节长度无法确定。
     * @return an <code>long</code>.
     */
    public long getLength();

    /**
     * 如果数值是个数组，则返回数组中每个值的大小。
     * @return an array of lengths
     */
    public long[] getLengths();


}
