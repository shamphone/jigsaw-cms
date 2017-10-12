package com.fulong.portal.model;

import java.util.Enumeration;

import java.util.Locale;
import org.apache.struts.util.MessageResources;

/**
 * 占位符定义
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author 李雄锋
 * @version 1.0
 */
public interface PortletDefinition {

    /**
     * Returns the identifier of this portlet as object id.
     * The return value cannot be NULL.
     *
     * @return the object identifier
     */
    public String getId();

    /**
     * Returns the class name
     *
     * @return the class name
     */
    public String getClassName();

    /**
     * Returns the administrative name
     * The return value cannot be NULL.
     *
     * @return the administrative name
     */
    public String getName();
    /**
     *
     * @param locale Locale
     * @return String
     */
    public String getDescription(Locale locale);

    /**
     * Returns all resource information of this portlet
     *
     * @return the portlet resources
     */
    @SuppressWarnings("unchecked")
	public Enumeration getLanguages();

    /**
     * Returns all parameters of this portlet
     * The return value cannot be NULL.
     *
     * @return the parameter set
     */
    @SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames();
    /**
     * 参数配置
     * @return ParameterConfig[]
     */
    public ParameterConfig[] getInitParameterConfigs();
    /**
     * 初始化参数
     * @param name String
     * @return ParameterConfig
     */
    public ParameterConfig getInitParameterConfig(String name);

    /**
     * Returns all SecurityRoleRefs of this portlet
     *
     * @return the SecurityRoleRef set
     */
    public SecurityRoleRefConfig[] getInitSecurityRoleRefs();
    /**
     * Preference配置
     * @return PreferenceSet
     */
    public PreferenceSet getPreferenceSet();

    /**
     * Returns the display name of this portlet for the given locale.
     * The return value may be NULL.
     *
     * @return display name for the given locale
     */
    public String getDisplayName(Locale locale);

    /**
     * Returns duration (in seconds) of expiration cache
     *
     * @return duration of expiration cache
     */
    public String getExpirationCache();
    /**
     * 资源文件配置
     * @return String
     */
    public String getResourceBundle();

    /**
     * 兼容struts所使用
     * @return MessageResources
     */
    public MessageResources getMessageResources();

}
