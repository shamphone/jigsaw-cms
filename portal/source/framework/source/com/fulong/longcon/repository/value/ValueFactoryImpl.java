package com.fulong.longcon.repository.value;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;
import com.fulong.longcon.repository.core.InternalValue;
import com.fulong.longcon.repository.core.InternalValueFactory;

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
public class ValueFactoryImpl implements InternalValueFactory {
    public ValueFactoryImpl() {
    }

    public void init() {

    }

    /**
     *
     * @param value 值
     * @param type 类型
     * @throws ValueFormatException
     * @return Value
     */
    public Value createValue(String value, int type) throws
        ValueFormatException {
        InternalValue ivalue;
        switch (type) {
            case PropertyType.BINARY:
                ivalue = new BinaryValue();
                break;
            case PropertyType.BOOLEAN:
                ivalue = new BooleanValue();
                break;
            case PropertyType.DATE:
                ivalue = new DateValue();
                break;
            case PropertyType.DOUBLE:
                ivalue = new DoubleValue();
                break;
            case PropertyType.LONG:
                ivalue = new LongValue();
                break;
            case PropertyType.PATH:
                ivalue = new PathValue();
                break;
            case PropertyType.REFERENCE:
                ivalue = new ReferenceValue();
                break;
            case PropertyType.TEXT:
                ivalue = new TextValue();
                break;
            default:
                ivalue = new StringValue();
                break;
        }
        	ivalue.setValue(value);
        return ivalue;

    }

    /**
     *
     * @param value an <code>InputStream</code>
     * @return a <code>Value</code> of {@link PropertyType#BINARY}
     */
    public Value createValue(InputStream value) {
        BinaryValue bvalue = new BinaryValue();
        bvalue.setValue(value);
        return bvalue;
    }

    /**
     * 创建一个字符型的值对象 with the specified <code>value</code>.
     *
     * @param value a <code>String</code>
     * @return a <code>Value</code> of {@link PropertyType#STRING}
     */
    public Value createValue(String value) {
        StringValue svalue = new StringValue();
        svalue.setValue(value);
        return svalue;
    }

    /**
     *
     * @param value a <code>long</code>
     * @return a <code>Value</code> of {@link PropertyType#LONG}
     */
    public Value createValue(long value) {
        LongValue lvalue = new LongValue();
        lvalue.setValue(value);
        return lvalue;
    }

    /**
     *
     * @param value a <code>Calendar</code>
     * @return a <code>Value</code> of {@link PropertyType#DATE}
     */
    public Value createValue(Calendar value) {
        DateValue dvalue = new DateValue();
        dvalue.setValue(value);
        return dvalue;
    }

    /**
     * @throws RepositoryException if the specified <code>Node</code> is not referencable, the current <code>Session</code> is no longer active, or another error occurs.
     *
     * @param value a <code>Node</code>
     * @return a <code>Value</code> of {@link PropertyType#REFERENCE}
     */
    public Value createValue(Node value) {
        ReferenceValue rvalue = new ReferenceValue();
        rvalue.setValue(value);
        return rvalue;
    }

    /**
     *
     * @param value a <code>boolean</code>
     * @return a <code>Value</code> of {@link PropertyType#BOOLEAN}
     */
    public Value createValue(boolean value) {
        BooleanValue bvalue = new BooleanValue();
        bvalue.setValue(value);
        return bvalue;
    }

    /**
     * Returns a <code>Value</code> object of {@link PropertyType#DOUBLE} with the specified <code>value</code>.
     *
     * @param value a <code>double</code>
     * @return a <code>Value</code> of {@link PropertyType#DOUBLE}
     */
    public Value createValue(double value) {
        DoubleValue dvalue = new DoubleValue();
        dvalue.setValue(value);
        return dvalue;
    }

    public Value createValue(Object value, int type) throws
        ValueFormatException {
        InternalValue ivalue;
        switch (type) {
            case PropertyType.BINARY:
                ivalue = new BinaryValue();
                break;
            case PropertyType.BOOLEAN:
                ivalue = new BooleanValue();
                break;
            case PropertyType.DATE:
                ivalue = new DateValue();
                break;
            case PropertyType.DOUBLE:
                ivalue = new DoubleValue();

                break;
            case PropertyType.LONG:
                ivalue = new LongValue();
                break;
            case PropertyType.PATH:
                ivalue = new PathValue();
                break;
            case PropertyType.REFERENCE:
                ivalue = new ReferenceValue();
                break;
            case PropertyType.TEXT:
                ivalue = new TextValue();
                break;
            default:
                ivalue = new StringValue();
                break;
        }
        if (value != null) {
            if (value instanceof String)
                ivalue.setValue( (String) value);
            else if (value instanceof Integer)
                ivalue.setValue( ( (Integer) value).longValue());
            else if (value instanceof Long)
                ivalue.setValue( ( (Long) value).longValue());
            else if (value instanceof Double)
                ivalue.setValue( ( (Double) value).doubleValue());
            else if (value instanceof Date)
                ivalue.setValue( ( (Date) value).getTime());
            else if (value instanceof Calendar)
                ivalue.setValue( (Calendar) value);
            else if (value instanceof Node)
                ivalue.setValue( (Node) value);
            else if (value instanceof Boolean)
                ivalue.setValue( ( (Boolean) value).booleanValue());
            else if (value instanceof Float)
                ivalue.setValue( ( (Float) value).doubleValue());
            else if (value instanceof InputStream)
                ivalue.setValue( (InputStream) value);
            else
                throw new ValueFormatException("Unable to convert data type.");
        }
        return ivalue;

    }
}
