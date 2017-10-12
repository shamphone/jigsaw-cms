/**
 * 
 */
package com.fulong.taglib.common;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.TagUtils;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.VariableManager;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.taglib.SpringTagSupport;

/**
 * 将Filter字符串显示成可显示文字  
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf
 *
 * @version 2.0
 *
 */
public class FilterTag   extends SpringTagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1746742796015544265L;
	
	private String name;
	private String property;
	private String value;
	private String definition;
	private String searchDef;
	private String scope;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getSearchDef() {
		return searchDef;
	}
	public void setSearchDef(String searchDef) {
		this.searchDef = searchDef;
	}
	
	public int doStartTag() throws JspException {
		TagUtils utils = TagUtils.getInstance();
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response = (HttpServletResponse)pageContext.getResponse();
		VariableManager manager =(VariableManager) this.getBeanFactory().getBean("variableManager");
		NodeDefinition definition = (NodeDefinition)utils.lookup(pageContext, this.definition,  scope);
		NodeDefinition searchDefinition = null;
		if(this.searchDef!=null){
			if(utils.lookup(pageContext, this.searchDef,  scope)!=null){
				searchDefinition = (NodeDefinition)utils.lookup(pageContext, this.searchDef,  scope);
			}
		}				
		if(definition==null)
			 throw new JspException("Unable to find definition for name :" + this.definition +".");
		String expression = (String)utils.lookup(pageContext, name, property, scope);
		if(expression == null)
			throw new  JspException("Unable to find expression for name :" + name +" property:"+ property+".");
		FilterParser parser = this.getFilterParser(request,response);
		try {
			parser.parser(expression);
		} catch (Exception e) {
			 throw new JspException(e);
		}
		ResourceBundle bundler= ResourceBundle.getBundle("Resources", request.getLocale());		
		PropertyDefinition property=definition.getPropertyDefinition(parser.getPropertyDefinition());
		if(!parser.getPropertyDefinition().equals("coolink_parentNode")&&property==null){
			if(!(parser.getPropertyDefinition().equals("theKeyword")&&parser.getOperation().equals("contains"))){
				return SKIP_BODY;
			}
		}			
		StringBuffer buffer = new StringBuffer();
		if(!(parser.getPropertyDefinition().equals("theKeyword")&&parser.getOperation().equals("contains"))){
			if(parser.getPropertyDefinition().equals("coolink_parentNode")){
				buffer.append(bundler.getString("coolink_parentNode"));
			}else{
				buffer.append(property.getName());
			}
		}else{
			buffer.append(bundler.getString("theKeyword"));
		}		
		buffer.append(" ");
		buffer.append(bundler.getString("filter."+parser.getOperation()));
		buffer.append(" ");		
		switch(parser.getValueType()){
		case FilterParser.NULL:
			buffer.append(utils.message(pageContext, null,request.getLocale().toString() , "filter.null"));
			break;
		case FilterParser.CONSTANT:
			buffer.append(parser.getValueExpression());
			break;
		case FilterParser.SYSVARIANT:
			buffer.append(manager.getDisplayName(parser.getValueExpression()));
			break;		
		case FilterParser.REFERENCE:
			String id  = parser.getValueExpression();
			Node node = this.getRepository().getNode(id);
			if(node!=null){
				if(node.getProperty("title")!=null&&node.getProperty("title").getString()!=null){
					buffer.append(node.getProperty("title").getString());
				}else if(node.getProperty("user-commonname")!=null&&node.getProperty("user-commonname").getString()!=null){
					buffer.append(node.getProperty("user-commonname").getString());
				}else if(node.getProperty("user-username")!=null&&node.getProperty("user-username").getString()!=null){
					buffer.append(node.getProperty("user-username").getString());
				}else{
					buffer.append(parser.getValueExpression());
				}
			}else{
				buffer.append(parser.getValueExpression());
			}
			
			break;
		case FilterParser.SEARCHDEFINITION:
			if(searchDefinition!=null){
				PropertyDefinition propertyDefinition = searchDefinition.getPropertyDefinition(parser.getValueExpression().trim());
				if(propertyDefinition!=null){
					buffer.append(propertyDefinition.getName()+" ("+searchDefinition.getName()+")");
				}
			}			
			break;
		}
		
		 utils.write(pageContext, buffer.toString());
		 return (SKIP_BODY);
     
    }	
	

}
