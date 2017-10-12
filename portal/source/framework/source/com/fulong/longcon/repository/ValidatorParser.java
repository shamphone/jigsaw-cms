package com.fulong.longcon.repository;

import java.util.Enumeration;



/**
 * 数据验证体系，这里定义的方法是为实现NodeDefinition.canSetProperty()方法提供基础。
 * 这里实现一个可扩展的验证框架。缺省的，系统提供必填、唯一、最大值、最小值等验证。
 * 实现一个验证类型需要完成如下操作:
 * 1. 继承ValidatorSupport，实现Validator接口。
 * 2. 修改property.editors.xml配置文件，注册验证器。
 * 验证框架实现如下功能;
 * 1. 服务器端验证，为实现NodeDefinition.canSetProperty()方法提供基础，这主要通过
 * public boolean validate(PropertyDefinition property, Value value);
 * 方法来实现。
 * 2. 客户端验证，为实现NodeValidatorTag标签提供支持即时验证的客户端脚本。脚本的方法由
 * ValidatorInfo.getJsFunction指定，实现由ValidatorInfo.getJavascript()提供。一般
 * 来说，这个脚本可以写在一个js文件中，在配置文件中，通过javascript-path来指定这个文件的路径。
 * 解析器在启动时，将这个文件内容读到内存中。NodeValidatorTag标签将这些脚本全部写到页面上。
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
public abstract class ValidatorParser {
    private static ValidatorParser _instance = null;
    public static ValidatorParser getParser() {
        if (_instance == null)
            throw new RepositoryException("Validator parser unready.");
        return _instance;
    }
    /**
     * singleton模式，全局仅保留一个实例。
     */
    public ValidatorParser() {
        _instance = this;
    }

    /**
     * 解析出constraint对应的validator;
     * @param type int
     * @param constraint String
     * @return Validator
     */
    public abstract Validator parser(String constraint);
    /**
     *
     * @return Enumeration
     */
    @SuppressWarnings("unchecked")
	public abstract Enumeration validatorInfos();

    /**
     *使用到new  ValidatorCollection( String[])地方变成对这个接口的调用。
     * @param constraint String[]
     * @return ValidatorCollection
     */
    public abstract ValidatorCollection parser(String[] constraint);

    /**
     *
     * @return ValidatorCollection
     */
    public abstract ValidatorCollection createCollection();


}
