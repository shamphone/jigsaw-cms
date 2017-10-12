package com.fulong.process.visual;

import java.io.Writer;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.expression.VariableManager;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.process.ProcessBaseAction;

/**
 * 
 * <p>Title: Coolink平台协同管理系统</p>
 *
 * <p>Description: Coolink平台协同管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2009</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class GetFilterPatternAction extends ProcessBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.process.ProcessBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean ok = false;
		String categoryID = request.getParameter("categoryID");
		String pattern = request.getParameter("pattern");
		String kongzhi = request.getParameter("kongzhi");
		StringBuffer buffer = new StringBuffer();
		try{
			VariableManager manager =(VariableManager) this.getBeanFactory().getBean("variableManager");
			NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryID);
			FilterParser parser = new ServletFilterParser(request,response);
			parser.parser(pattern);
			ResourceBundle bundler= ResourceBundle.getBundle("Resources", request.getLocale());		
			PropertyDefinition property=category.getPropertyDefinition(parser.getPropertyDefinition());			
			buffer.append(property.getName());
			buffer.append(" ");
			buffer.append(bundler.getString("filter."+parser.getOperation()));
			buffer.append(" ");		
			switch(parser.getValueType()){
			case FilterParser.NULL:
				buffer.append(kongzhi);
				break;
			case FilterParser.CONSTANT:
				buffer.append(parser.getValueExpression());
				break;
			case FilterParser.SYSVARIANT:
				buffer.append(manager.getDisplayName(parser.getValueExpression()));
				break;		
			case FilterParser.REFERENCE:
				buffer.append(parser.getValueExpression());
				break;		
			}
			ok = true;
		}catch(Exception e){
			ok = false;
		}
		
		response.setContentType("text/xml");
        response.setHeader("Content-Type", "text/xml; charset=utf-8");
		Writer writer = response.getWriter();
		
        if(ok){
        	writer.append(buffer.toString());
        }     
        writer.close();
		return null;
	}

}
