package com.fulong.longcon.repository;

import java.io.InputStream;
import java.util.Calendar;

/**
 * 提供关于Node的一些常见方法的实现
 *
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
public abstract class GeneralNode implements Node {

    public GeneralNode() {
    }

 

    public Node addNode(String name) {
        return this.addNode(this.getDefinition(), name);
    }

    /**
     * 新增一个子结点，结点满足definition的定义,新增的节点将被持久化.
     *
     * @param definition NodeDefinition 结点定义
     * @return Node 新节点，按照节点定义中确定的初始值做初始化。
     */
    public Node addNode(NodeDefinition definition) {
        return this.addNode(definition, "");
    }

    /**
     *
     * @param name String
     * @return com.fulong.longcon.content.Property
     */
    public Property getNodeProperty(String name) {
        return this.getProperty(name);
    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value Node
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, Node value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;
    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param values Value[]
     * @param type int
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, Value[] values, int type) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        if (type == property.getType())
            this.setProperty(name, values);
        else
            throw new ValueFormatException(
                "Can't set property value, required is " + property.getType() +
                ", new type is " + type + " .");
        return property;
    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value long
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, long value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);

        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value String
     * @param type int
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, String value, int type) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        Value tvalue = this.getRepository().getValueFactory().createValue(value,
            type);
        property.setValue(tvalue);

        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value Value
     * @param type int
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, Value value, int type) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param values Value[]
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, Value[] values) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(values);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param values String[]
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, String[] values) throws
        ValueFormatException {
        Property property = this.getProperty(name);       
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        //增加了对资源节点的删除
        if(property.getType() == PropertyType.PATH){
        	Value[] vs = property.getValues();
        	if(vs.length != 0){
	            for(int i = 0;i < vs.length;i++){
	            	Boolean flag = true; //是否删除
	            	if(values.length == 0)
	            		flag = false;
	            	else{
		            	for(int n = 0;n < values.length;n++){
		            		if(vs[i].toString().equals(values[n])){
		            			flag = false;
		            			break;
		            		}
		            	}
	        		}
	            	if(flag){
		            	Query query = this.getRepository().getQueryManager().createQuery(this.getDefinition(), Query.SQL);
		        		query.filterByProperty(name, vs[i].toString());
		        		NodeIterator<?> nodes = query.nodes();
		        		if(nodes.getSize() != 1){
		        			flag = false;
		        		}
	            	}
	            	if(flag){
	            		Node parent = this;
	    	            while(!parent.getParent().getID().equals("root")){
	    	    			parent = parent.getParent();
	    	    		}
	    	            Node root = parent.getNode("resources");
			            String filename = vs[i].toString().substring(vs[i].toString().lastIndexOf("/") + 1);
			            Node resource = root.getNode(filename);
			            this.getRepository().delete(resource);
	            	}
	            }
        	}
        }
        property.setValue(values);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value double
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, double value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value Value
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, Value value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param values String[]
     * @param type int
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, String[] values, int type) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        if (values == null)
            property.setValue( (String)null);
        else{
            Value[] result = new Value[values.length];        	
            for (int i = 0; i < values.length; i++) {
                Value value = this.getRepository()
                    .getValueFactory()
                    .createValue(values[i], type);
                result[i]=value;                
            }
            property.setValue(result);
        }
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value Calendar
     * @throws ValueFormatException
     * @return Property
     */
    public Property setProperty(String name, Calendar value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value String
     * @throws ValueFormatException
     * @return Property

     */
    public Property setProperty(String name, String value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name + ".");
        property.setValue(value);
        return property;
    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value InputStream
     * @throws ValueFormatException
     * @return Property

     */
    public Property setProperty(String name, InputStream value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;

    }

    /**
     * 设置属性值。如果这个属性不存在，则创建这个属性。 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。 如果系统未定义该属性类型，则如果属性
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     *
     * @param name String
     * @param value boolean
     * @throws ValueFormatException
     * @return Property

     */
    public Property setProperty(String name, boolean value) throws
        ValueFormatException {
        Property property = this.getProperty(name);
        if (property == null)
            throw new IllegalArgumentException("Unknown property name: " + name +
                                               ".");
        property.setValue(value);
        return property;

    }

    /**
     * 当前node是否为parent的儿子/孙子节点
     * @param parent Node
     * @return boolean
     */
    public boolean isChild(Node parent) {
        Node temp = this.getParent();
        while (temp != null) {
            if (temp.getID().equals(parent.getID()))
                return true;
            temp = temp.getParent();
        }
        return false;
    }

    public boolean isChild(String parentID) {
        Node temp = this.getParent();
        while (temp != null) {
            if (temp.getID().equals(parentID))
                return true;
            temp = temp.getParent();
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj instanceof Node)
            return this.getID().equals( ( (Node) obj).getID());
        return false;
    }

    
    /**
	 * parentID和name组合下的最大序号
     * @param parentID String
     * @param name String
	 * @return int 
	 */
    public int getMaxOrderNo(String parentID,String name){
        return 0;
    }

    /**
     * 复制一个自身的拷贝，包括所有的属性，只有pkid是自动生成的，其他的信息和原件一样
     * @return Node
     */
    public Node clone() {
        return null;
    }

}
