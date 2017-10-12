package com.fulong.longcon.repository.core;

import java.util.Calendar;
import java.io.InputStream;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.Node;
/**
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
public interface InternalValue extends Value,Comparable<Object> {
    /**
     * 获取结点值
     * @return Node
     * @throws ValueFormatException
     */
    public Node getReference()  throws ValueFormatException;

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

    public void setValue(Value value)  throws ValueFormatException;


}
