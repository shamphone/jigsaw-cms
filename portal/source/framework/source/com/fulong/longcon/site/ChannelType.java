package com.fulong.longcon.site;


/**
 * 栏目类型，主要用来定义地址的产生方式
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
public interface ChannelType {
    public static String PARAM_FILETERFIELD="fileterField";
    public static String PARAM_FILTERVALUE="filterValue";
    /**
     *
     * @return String
     */
    public String getName();
    
    public String getPrefix();

    /**
     * 类型的显示名称
     * @return String
     */
    public String getDisplayName();
    /**
     * 栏目类名
     * @return
     */
    public String getClassName();
    
    /**
     * 栏目文件名后缀
     * @return
     */
    public String getPostfix();
  
    
   
}
