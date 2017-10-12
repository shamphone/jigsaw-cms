package com.fulong.longcon.repository;

import java.util.Calendar;
import java.io.InputStream;

/**
 * 用于构造符合
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
public interface ValueFactory {

    /**
     * 创建一个字符型的值对象
     * with the specified <code>value</code>.
     *
     * @param value a <code>String</code>
     * @return a <code>Value</code> of {@link PropertyType#STRING}
     */
    public Value createValue(String value);

    /**
     * 创建一个字符型的值对象，并转换成相应的类型
     * @param value 值
     * @param type 类型
     */
    public Value createValue(String value, int type) throws
            ValueFormatException;
    /**
      * 创建一个字符型的值对象，并转换成相应的类型
      * @param value 值
      * @param type 类型
      */
     public Value createValue(Object value, int type) throws
             ValueFormatException;

    /**
     * 创建一个整型值对象
     * @param value a <code>long</code>
     * @return a <code>Value</code> of {@link PropertyType#LONG}
     */
    public Value createValue(long value);

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#DOUBLE}
     * with the specified <code>value</code>.
     *
     * @param value a <code>double</code>
     * @return a <code>Value</code> of {@link PropertyType#DOUBLE}
     */
    public Value createValue(double value);

    /**
     * 创建一个布尔型值对象
     * @param value a <code>boolean</code>
     * @return a <code>Value</code> of {@link PropertyType#BOOLEAN}
     */
    public Value createValue(boolean value);

    /**
     * 创建一个日期值对象
     *
     * @param value a <code>Calendar</code>
     * @return a <code>Value</code> of {@link PropertyType#DATE}
     */
    public Value createValue(Calendar value);

    /**
     * 创建一个流值对象
     * @param value an <code>InputStream</code>
     * @return a <code>Value</code> of {@link PropertyType#BINARY}
     */
    public Value createValue(InputStream value);

    /**
     * 创建一个节点应用值对象
     * @param value a <code>Node</code>
     * @return a <code>Value</code> of {@link PropertyType#REFERENCE}
     * @throws RepositoryException if the specified <code>Node</code>
     * is not referencable, the current <code>Session</code> is no longer active, or another
     * error occurs.
     */
    public Value createValue(Node value);
}
