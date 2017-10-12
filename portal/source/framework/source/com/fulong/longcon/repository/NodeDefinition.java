package com.fulong.longcon.repository;


import java.util.Date;
import java.util.Iterator;

/**
 * 内容定义，即定义内容库的格式，确定内容库所拥有的属性。
 * 每个内容库都和指定的数据表相关联，这个数据表是由内容定义的ID来确定的。
 * 在使用节点前，必须对节点进行定义。Repository.createDefinition()方法可以创建一个节点定义。通过NodeDefinition.addPropertyDefinition（）、delete（）、getPropertyDefinition（）方法可以增、删、获取属性定义。
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
public interface NodeDefinition {
    public static final String USER_SCHEME = "user-scheme";
    public static final String ORG_SCHEME = "org-scheme";
    public static final String COUNTER_SCHEME = "counter-scheme";
    public static final String COMPOSITE_SCHEME_ROOT = "composite-scheme-root";
    public static final String NO_PROPERTIES_SCHEME="no-properties-scheme";
    public static final String DICT_SCHEME="dict-scheme";
    public static final String PRINCIPAL_SCHEME="principal-scheme";


    /**
     * 属性的标识符,和属性定义中的ID是相同的。
     * @return String 标识符
     */
    public String getID();

    /**
     * 名称，可以使用中文
     * @return java.lang.String
     */
    public String getName();

    /**
     * 重命名
     * @param name String
     */
    public void setName(String name);

    /**
     * 描述
     * @return String
     */
    public String getDescription();

    /**
     * 描述
     * @param description String
     */
    public void setDescription(String description);

    /**
     * 该节点定义/属性定义是不能删除的
     * @return boolean
     */
    public boolean isProtected();

    /**
     * 遍历所有属性，包括系统属性和用户自定义属性。
     * @return java.util.Iterator
     */
    public Iterator<PropertyDefinition> propertyDefinitions();

    /**
     * 返回除继承以外的属性集合，只包含自己这一级定义的属性。
     * @return Iterator
     */
    public Iterator<PropertyDefinition> getDeclaredPropertyDefinitions();


    /**
     * 根据readonly遍历所有属性，包括系统属性和用户自定义属性。
     * readonly=true 不可修改的属性(系统属性)
     * readonly=false 可修改的(用户自定义属性)
     * @return java.util.Iterator
     */
    public Iterator<PropertyDefinition> propertyDefinitions(boolean readonly);

    /**
     * 获取指定类型的属性定义
     * @param type String
     * @return java.util.Iterator
     */
    public Iterator<PropertyDefinition> propertyDefinitions(int type);

    /**
     * 获取指定ID的属性
     * @param property String
     * @return PropertyDescriptor
     */
    public PropertyDefinition getPropertyDefinition(String ID);

  

    /**
     * 创建指定类型的属性并添加到库定义中。
     * 当参数id表示的属性在本大纲或父大纲中存在时，抛出异常ItemExistsException。
     * @param type String 类型
     * @param id String 属性ID
     * @return PropertyDescriptor
     */
    public PropertyDefinition addPropertyDefinition(int type, String id);

    /**
     * 为当前NodeDefinition添加一个复合属性
     * @param type NodeDefinition，这个参数设置该复合属性到底是用的哪个大纲
     * @param id String
     * @return ChildNodeDefinition
     */
    public ChildNodeDefinition addChildNodeDefinition(NodeDefinition type,
        String id);

    /**
     * 删除属性
     * @param property PropertyDescriptor
     */
    public void delete(PropertyDefinition property);

    /**
     * 验证给定的值是否可以设置给当前属性
     * @param value Value
     * @return ValidatedResult
     */
    public boolean canSetProperty(String property, Value value);

    /**
     * 验证给定的值是否可以设置给当前属性
     * @param value Value[]
     * @return ValidatedResult
     * @deprecated
     * 使用canSetProperty(String property, Value[] value, Node node)方法代替
     * 可以得出当前库下内容是否唯一等校验
     */
    public boolean canSetProperty(String property, Value[] value);

    /**
     * 父定义。
     * @return NodeDefinition
     */
    public NodeDefinition getSuperDefinition();
    /**
     * 设置当前大纲的主父大纲
     * @param superDefinition NodeDefinition
     */
    public void setSuperDefinition(NodeDefinition superDefinition);
    
    /**
     * 父定义。
     * @return NodeDefinition
     */
    //public NodeDefinition[] getSuperDefinitions();
    /**
     * 设置当前大纲的父大纲
     * @param superDefinition NodeDefinition
     */
    //public void addSuperDefinition(NodeDefinition superDefinition);
    
    /**
     * 删除一个父大纲
     * @param superDefinition NodeDefinition
     */
    //public void removeSuperDefinition(NodeDefinition superDefinition);
    
    /**
     * 删除一个父大纲
     * @param superDefinition String
     */
    //public void removeSuperDefinition(String superDefinitionID);
    

    /**
     * 获得继承自当前大纲的大纲列表
     * @return NodeDefinitionIterator
     */
    public NodeDefinitionIterator getInheritDefinitions();

    /**
     * 如果这个节点类型是superDefinitionId所定义的节点定义的子节点，则返回true
     * @param superDefinitionId the name of a node type.
     * @return a boolean
     */
    public boolean isNodeType(String superDefinitionId);

    /**
     * 如果这个节点类型是superDefinition所定义的节点定义的子节点，则返回true
     * @param superDefinition the name of a node type.
     * @return a boolean
     */
    public boolean isNodeType(NodeDefinition superDefinition);

    /**
     * 是否递归遍历大纲
     * @param recursive boolean
     * @return NodeDefinitionIterator
     */
    public NodeDefinitionIterator getInheritDefinitions(boolean recursive);

    /**
     * 指定节点应用属性值的约束验证
     * @param property String
     * @param value Value[]
     * @param node Node
     * @return boolean
     * @deprecated 转移到Node中
     */
    public boolean canSetProperty(String property, Value[] value, Node node);

    /**
     *
     * @param property String
     * @param value Value[]
     * @param node Node 当前节点所在的父节点
     * @param self Node 当前节点
     * @return boolean
     * @deprecated 转移到Node中
     */
    public boolean canSetProperty(String property, Value[] value, Node node,
                                  Node self);

    /**
     *
     * @return Date
     */
    public Date getCreateDate();


  

    /**
     * 获取引用自己的属性定义集合。
     * @return Iterator
     */
    public Iterator<PropertyDefinition> getReferencedPropertyDefinitions();
    
    
    
    
}
