package com.fulong.portal.core;

/**
 *
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
public class NamespaceMapper {

    public static String encode(String portletName, String name) {
        return "pt."+portletName + "." + name;
    }

    public static String decode(String portletName, String name) {
        if (name.startsWith("pt."+portletName)) {
            return name.substring(portletName.length() + 4);
        }
        return null;
    }

    public static boolean isGlobal(String name) {
        return !(name.startsWith("pt."));
    }

    public static boolean isLocale(String name) {
        return name.startsWith("pt.");
    }

    public static boolean isReserved(String name) {
        return name.startsWith("portlet.");
    }
}
