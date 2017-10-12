package com.fulong.taglib.common;


import javax.servlet.jsp.JspException;

/**
 * 判断值不为空
 * <p>Title: 龙驭网站内容管理系统</p>
 *
 * <p>Description: 龙驭网站内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2008</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author Lixf
 * @version 3.0
 */
public class NotEmptyTag extends EmptyTag {


    // ------------------------------------------------------ Protected Methods


    /**
	 * 
	 */
	private static final long serialVersionUID = -3384704129259800301L;

	/**
     * Evaluate the condition that is being tested by this particular tag,
     * and return <code>true</code> if the nested body content of this tag
     * should be evaluated, or <code>false</code> if it should be skipped.
     * This method must be implemented by concrete subclasses.
     *
     * @exception JspException if a JSP exception occurs
     */
    protected boolean condition() throws JspException {

        return (condition(false));

    }


}
