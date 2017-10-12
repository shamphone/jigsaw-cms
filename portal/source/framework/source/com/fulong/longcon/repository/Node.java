package com.fulong.longcon.repository;

import java.util.Iterator;
import java.io.InputStream;
import java.util.Calendar;

/**
 * 可以通过节点的properties()方法来遍历所有的属性，
 * 或者通过getProperty()方法来获取指定标识的属性。
 * 通过setProperty()方法来设置属性值。
 * 在设置时，系统将自动根据属性的类型做类型转换。
 * 例如将可以将字符串赋给一个日期属性，系统将尽可能对字符串进行转换，如果转换失败，则抛出异常。
 *
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
public interface Node extends Item {


    /**
     * 获取指定路径上的属性值。
     * 本节点下的属性以属性名称表示路径。
     * 对于子节点（复合属性）下的属性，以"子节点名称/子节点名称..../属性名称"的形式来表示路径
     * @param path String 属性路径，以"/"分隔。
     * @return com.fulong.longcon.content.Property
     * @todo 支持获取给定路径上的属性值
     */
    public Property getProperty(String path);

    /**
     * 遍历内容所有的属性
     * @return Iterator
     */
    public Iterator<Property> properties();

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */
    public Property setProperty(String name, Value value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @param type 属性转换类型
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */
    public Property setProperty(String name, Value value, int type) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param values 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */
    public Property setProperty(String name, Value[] values) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param values 属性值
     * @param type 属性对应的类型
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, Value[] values, int type) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param values 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, String[] values) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param values 属性值
     * @param type 属性对应的类型
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, String[] values, int type) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, String value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @param type 属性类型
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, String value, int type) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, InputStream value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */
    public Property setProperty(String name, boolean value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, double value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, long value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */

    public Property setProperty(String name, Calendar value) throws
        ValueFormatException;

    /**
     * 设置属性值。如果这个属性不存在，则抛出异常。
     * 属性的类型由结点定义确定，如果这个属性是其他类型，则系统作尽可能的类型转换。
     * 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @param name 属性标识符
     * @param value 属性值
     * @throws ValueFormatException 如果转换出错，则抛出ValueFormatException，其他错误，则抛出RepositoryException.
     * @return 由name指定的属性
     */
    public Property setProperty(String name, Node value) throws
        ValueFormatException;

    /**
     * 新增一个节点，节点id由系统自动生成
     * @param definition 大纲定义
     * @param name 节点名称
     * @return Node
     */
    public Node addNode(NodeDefinition definition, String name);
    
    /**
     * 新增一个节点
     * @param definition 大纲定义
     * @param name 节点名称
     * @param id :给定id,要求id不能重复。
     * @return Node
     */
    public Node addNode(NodeDefinition definition, String name, String id);
    

    /**
     * 新增一个子结点，使用本节点缺省的节点定义
     * @param name String
     * @return Node
     */
    public Node addNode(String name);

    /**
     * 设置父结点
     * @param parent 父节点
     */
    public void setParent(Node parent);

    /**
     * 获取指定标识符的子节点
     * @param relPath String 子节点的相对路径。如果这个路径下有多个子节点，则返回第一个节点
     * @return Node 子节点
     */
    public Node getNode(String path);
  
    /**
     * 获取指定标识符的子节点
     * @param relPath String 子节点的相对路径，如果这个名称下有多个节点，则返回第index个节点
     * @return Node 子节点
     */
    //public Node getNode(String name ,int index);

    /**
     * 遍历所有的子节点
     * @return Node
     */
    public NodeIterator<Node> getNodes();

    /**
     * 遍历所有的子节点(递归)
     * @return Node
     */
    public NodeIterator<Node> getAllNodes();

    /**
     * 遍历参数name属性的子节点
     * @param name String
     * @return NodeIterator
     */
    public NodeIterator<Node> getNodes(String name);
    
    
    /**
     * 遍历参数name属性的子节点（递归）
     * @param name String
     * @return NodeIterator
     */
    public NodeIterator<Node> getAllNodes(String name);

    /**
     * 获取所有引用这个节点的其他节点
     * @return PropertyIterator
     */
    @SuppressWarnings("rawtypes")
	public PropertyIterator getReferences();

    /**
     * 获得该节点在其兄弟节点的序号
     * @return int
     */
    public int getOrderNo();

    /**
     * 设置该节点在其兄弟节点的序号
     * @param orderNo int
     */
    public void setOrderNo(int orderNo);
    
    /**
     * 设置该节点在其兄弟节点的序号
     * @param orderNo int
     */
    public void setMaxOrderNo(int orderNo);
    

    /**
     * 当前node是否为parent的儿子/孙子节点
     * @param parent Node
     * @return boolean
     */
    public boolean isChild(Node parent);
    
	/**
	 * parentID和name组合下的最大序号
     * @param parentID String
     * @param name String
	 * @return int 
	 */
    public int getMaxOrderNo(String parentID,String name);

    /**
     * 判断在指定路径下是否有节点存在
     * @param path String 子节点的相对或者绝对路径
     * @return boolean
     */
    public boolean hasNode(String path);

    /**
     * 判断在指定路径下是否有属性存在
     * @param path String 子节点的相对或者绝对路径
     * @return boolean
     */
    public boolean hasProperty(String path);

    /**
     * 获取内容的主格式定义，即内容被创建时所使用的格式定义。
     * @return NodeDefinition
     */
    public NodeDefinition getDefinition();

    /**
     * 更改内容的主格式定义。
     * @param definition NodeDefinition
     */
    public void setDefinition(NodeDefinition definition);

    /**
     * 获取附加的格式定义，一个内容可以有多个格式定义
     * @return NodeDefinition[]
     */
    public NodeDefinition[] getMixinDefinitions();
    /**
     * 判断是否是nodeDefinition类型
     * @param nodeDefinitionName String
     * @return boolean
     */
    public boolean isNodeType(String nodeDefinitionName);
    /**
     * 添加附加类型，设置成功后，节点自动拥有指定类型下的所有属性，
     * 如果属性和现有的属性有重复的，则保持现有属性
     * @param definition NodeDefinition
     */
    public void addMixinDefinition(NodeDefinition definition);
    /**
     * 删除类型，删除后，这个类型对应的属性将被删除，如果某个属性在已有的类型中还在使用，则保留
     * @param nodeDefinitionName String
     */
    public boolean removeMixin(NodeDefinition definition);
    /**
     * 判断是否可以将nodeDefinitionName类型设置为这个节点的附加类型
     * @param definition NodeDefinition
     * @return boolean
     */
    public boolean canAddMixinDefinition(NodeDefinition definition);

    /**
     * 复制一个自身的拷贝，包括所有的属性，只有pkid是自动生成的，其他的信息和原件一样
     * 接口没有拷贝这个内容的分类信息。
     * @return Node
     */
    public Node clone();    
    
    /**
     * added by liulei in 2009-12-11
     */
    /**
     * 返回该节点的node_type。在有些情况下需要根据NODETYPE来确定该node是否应该被删除，
     * 比如删除推荐操作的结果。
     * @return int 0 or 1
     */
    public int getNodeType(String defid);
    
    /**
     * 修改Node的名称
     * @param name
     */
    public void setName(String name);
    

}
