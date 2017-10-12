package com.fulong.longcon.repository;

import java.util.*;

/**
 * Validator的注册信息。这个类是和property.editor.xml配置文件一起使用，
 * 为validator的实现类提供访问配置属性的接口。
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
public interface ValidatorInfo {
    /**
     * validator的名称
     * @return String
     */
    public String getName();
    /**
     * 校验模式，正则表达式
     * @return String
     */
    public String getPattern();

    public void setPattern(String pattern);
    /**
      * 显示名称
      * @return String
      */
     public String getDisplayName(Locale locale);

     /**
      * 描述
      * @return String
      */
     public String getDescription(Locale locale);
     /**
      * 编辑器的参数
      * @param name String
      * @return String
      */
    public String getParameter(String name);
    /**
     * validator对应的脚本，用来做验证
     * @return String
     */
    public String getJavascript();

    /**
     * 用来做数据验证的javascript方法的名称。
     * @return String
     */
    public String getJsFunction();
    /**
     * 验证器的实现类，必须是一个实现Validator接口的类
     * @return Class
     */
    @SuppressWarnings("unchecked")
	public Class getValidatorClass();

    public void setUrl(String url);

    public String getUrl();
}
