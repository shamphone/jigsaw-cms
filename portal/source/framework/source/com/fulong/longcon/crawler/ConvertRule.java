package com.fulong.longcon.crawler;

import java.net.URL;

/**
 * 转换规则。定义动态页面地址和静态页面地址的转换关系。系统针对特定的系统提供转换规则：
 *
 * <p>Title: 龙驭网页爬虫系统</p>
 *
 * <p>Description: 龙驭网页爬虫系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 2.0
 */
public interface ConvertRule {
    /**
     * 判断地址是否需要转换
     * @param content String
     * @return boolean
     */
    public boolean needToChange(URL url);

    /**
     * 根据网页地址生成指定格式的路径，格式在config.properties中给出
     * @param url String 完整的页面地址
     * @return String 相对页面路径和地址。这个返回值一方面作为静态化后的文件的相对（主机+Context的）页面路径，一方面作为静态文件保存的相对（静态文件夹的）路径
     */
    public String convert(URL url);

}
