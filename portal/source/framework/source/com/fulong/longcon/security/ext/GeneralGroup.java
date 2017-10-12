package com.fulong.longcon.security.ext;

import java.util.Enumeration;

import com.fulong.longcon.security.Group;

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
public abstract class GeneralGroup implements Group {

	private static final long serialVersionUID = 6692709118763747967L;

    /**
     *
     * @return Enumeration
     */
    public Enumeration<?> organizations() {
        return members();
    }

    /**
     * Returns a string representation of this principal.
     *
     * @return a string representation of this principal.
     */
    public String toString() {
        return this.getName();
    }

    /**
     *
     * @return boolean
     */
    public boolean isUser() {
        return false;
    }

    /**
     *
     * @return boolean
     */
    public boolean isOrganization() {
        return false;
    }
}
