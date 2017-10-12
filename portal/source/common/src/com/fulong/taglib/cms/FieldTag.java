package com.fulong.taglib.cms;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.jsp.JspException;

import com.fulong.longcon.repository.Node;
import com.fulong.taglib.SpringTagSupport;

import org.apache.struts.taglib.TagUtils;

/**
 * 根据类别输出一个属性
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2008</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author jiangqi
 * @version 3.0
 */
public class FieldTag extends SpringTagSupport {
    
	private static final long serialVersionUID = 8787847970959237407L;
	
	private String node;
    private String property;
    private String type;
    private String customalValues;
    private String propertyName;
    private String name;
    private String scope;
    private String style;
    private String length;
    private String target;


    public int doStartTag() throws JspException {
        if(customalValues!=null&&!customalValues.equals("")){
            TagUtils.getInstance().write(pageContext,customalValues);
            return (SKIP_BODY);
        }
        pageContext.getRequest().setAttribute("style", style);
        pageContext.getRequest().setAttribute("type", type);
        pageContext.getRequest().setAttribute("target", target);
        pageContext.getRequest().setAttribute("length", length);
        pageContext.getRequest().setAttribute("propertyName", propertyName);
        Node node = (Node) TagUtils.getInstance().lookup(pageContext, name,
                property, scope);
        pageContext.getRequest().setAttribute("com.fulong.longcon.Content", node);
        String page = ".jsp";
        if (type.equals("item") || type.equals("radio") || type.equals("checkbox")) {
            page = type + page;
        } else {
            page = "node" + page;
        }
        try {
            pageContext.include(page);
        } catch (IOException ex) {
        } catch (ServletException ex) {
        }
        return (SKIP_BODY);
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCustomalValues(String customalValues) {
        this.customalValues = customalValues;
    }

    public void setNode(String node) {
        this.node = node;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setTarget(String target) {
        this.target = target;
    }


    public String getProperty() {
        return property;
    }

    public String getType() {
        return type;
    }

    public String getCustomalValues() {
        return customalValues;
    }

    public String getNode() {
        return node;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getName() {
        return name;
    }

    public String getScope() {
        return scope;
    }

    public String getStyle() {
        return style;
    }

    public String getLength() {
        return length;
    }

    public String getTarget() {
        return target;
    }
}
