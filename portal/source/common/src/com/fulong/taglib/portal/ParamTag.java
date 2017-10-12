package com.fulong.taglib.portal;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 *
 * <p>Title: WebMaster Core Library</p>
 *
 * <p>Description: Longcon WebMaster SV3</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: Beijing Zhongke Fulong Computer Tech. LTD</p>
 *
 * @author Lixf
 * @author ligang
 * @version 1.0
 */


public class ParamTag extends BodyTagSupport {
   
	private static final long serialVersionUID = 1843127003051026362L;
	
	private String name;
    private String value;
    private String body;

    public int doAfterBody() throws JspException {
          if (bodyContent != null) {
              body = bodyContent.getString();
              if (body != null) {
                  body = body.trim();
              }
              if (body.length() < 1) {
                  body = null;
              }
          }
          return (SKIP_BODY);

    }
     public int doEndTag() throws JspException{
         String content = this.body;
         if (content == null)
             content = this.getValue();
         URLTag urlTag = (URLTag) findAncestorWithClass(this, URLTag.class);
         if (urlTag == null) {
             throw new JspException(
                     "the 'param' Tag must have actionURL or renderURL as a parent");
         }
         urlTag.addParameter(name, content);
         this.name = null;
         this.value = null;
         this.body = null;

          return EVAL_PAGE;
     }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the value.
     * @return String
     */
    public String getValue() {
        if (value == null) {
            value = "";
        }
        return value;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the value.
     * @param value The value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    public void release(){
        super.release();
        this.name=null;
        this.value=null;
        this.body=null;
    }

}
