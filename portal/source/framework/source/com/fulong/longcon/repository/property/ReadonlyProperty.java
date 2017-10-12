package com.fulong.longcon.repository.property;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Repository;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueFormatException;

/**
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 2.0
 */
public class ReadonlyProperty extends BasicProperty {
    private String beanPropertyName;
    private Property property;
    private Node node;
    private boolean hooked = false; //每个property只能被hook一次
    public ReadonlyProperty(Property property) {
        this(property, property.getParent());
    }

    public ReadonlyProperty(Property property, Node node) {
        this(property, node, property.getID());
    }

    public ReadonlyProperty(Property property, Node node,
                            String beanPropertyName) {
        super(null, node, property.getDefinition());
        this.property = property;
        this.beanPropertyName = beanPropertyName;
        this.node = node;
    }

    public void hook(Node node, String beanPropertyName) {
        if (hooked) {
            return;
        }
        this.node = node;
        this.beanPropertyName = beanPropertyName;
        this.hooked = true;
    }

    public void hook(Node node) {
        if (hooked) {
            return;
        }
        this.node = node;
        this.hooked = true;
    }

    /**
     * 设置 值的对应的Bean。
     * @param name String
     */
    public void setBeanPropertyName(String name) {
        this.beanPropertyName = name;
    }

    private Object getPropertyValue() {
        try {
            return PropertyUtils.getProperty(node, beanPropertyName);
        } catch (NoSuchMethodException ex) {
            throw new ValueFormatException(ex);
        } catch (InvocationTargetException ex) {
            throw new ValueFormatException(ex);
        } catch (IllegalAccessException ex) {
            throw new ValueFormatException(ex);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    /**
     *
     * @return Value[]
     */
    protected Value[] loadValues() {
        Object value = this.getPropertyValue();
        if (value == null) {
            return new Value[0];
        }
        if (value.getClass().isArray()) {
            return new Value[] {
                property.getRepository()
                .getValueFactory()
                .createValue(value, property.getDefinition().getType())};
        } else {
            Object[] array = (Object[]) value;
            Value[] values = new Value[array.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = property.getRepository().getValueFactory().
                    createValue(array[i], property.getDefinition().getType());
            }
            return values;
        }
    }

    /**
     *
     * @param values Value[]
     */
    protected void saveValues(Value[] values) {
    }

    /**
     *
     * @param values PropertyValue 数据格式转换错误
     * @throws ValueFormatException
     */
    public void setValue(Value[] values) throws ValueFormatException {

    }

    /**
     *
     * @return PropertyValue
     */
    public Value[] getValues() {
        Object value = this.getPropertyValue();
        if (value == null) {
            return new Value[0];
        }
        if (!value.getClass().isArray()) {
            return new Value[] {
                property.getRepository()
                .getValueFactory()
                .createValue(value, property.getDefinition().getType())};
        } else {
            Object[] array = (Object[]) value;
            Value[] values = new Value[array.length];
            for (int i = 0; i < values.length; i++) {
                values[i] = property.getRepository().getValueFactory().
                    createValue(array[i],
                                property.getDefinition().getType());
            }
            return values;
        }
    }

    public Repository getRepository() {
        return property.getRepository();
    }

    public String getID() {
        return property.getID();
    }

    public int getType() {
        return property.getType();
    }

    public Node getParent() {
        return property.getParent();
    }

    public PropertyDefinition getDefinition() {
        return property.getDefinition();
    }

}
