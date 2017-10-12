package com.fulong.portal.model;

import java.util.Collection;
import java.util.Locale;


/**
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
public interface PortletCategory {
        /**
         * 分类名称
         * @return String
         */
        public String getName();

        /**
         * 显示名称
         * @param locale Locale
         * @return String
         */
        public String getDisplayName(Locale locale);

        /**
         * 获取该分类下所有的占位符
         * @return Iterator
         */
        public  Collection<PortletDefinition> getPortletDeginitions();
}
