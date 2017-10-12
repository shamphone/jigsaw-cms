package com.fulong.longcon.repository;

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
public interface Validator {
    /**
     * 解析参数
     * @param constraints String
     */
    public void parse(String constraints);

    /**
     * 验证值Value是否可以授予property。
     * @param property Property
     * @param value Value
     * @return String
     */
    public boolean validate(PropertyDefinition property, Value value);

    /**
     * 验证器的信息
     * @return ValidatorInfo
     */
    public ValidatorInfo getValidatorInfo();

    /**
     * 验证在当前节点应用中value值是否满足属性的约束
     * @param property PropertyDefinition
     * @param value Value
     * @param node String
     * @return boolean
     */
    public boolean validate(PropertyDefinition property, Value value,
                            Node node);

    /**
     * 验证在当前节点应用中value值是否满足属性的约束
     * @param property PropertyDefinition
     * @param value Value
     * @param parent Node 当前节点所在的库
     * @param self Node当前节点
     * @return boolean
     */
    public boolean validate(PropertyDefinition property, Value value,
                            Node parent, Node self);
}
