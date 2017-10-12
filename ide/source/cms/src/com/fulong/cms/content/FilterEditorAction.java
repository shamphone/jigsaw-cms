package com.fulong.cms.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.cms.form.FilterForm;
import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;

/**
 * <p>Title: 龙驭内容管理系统</p>
 *
 * <p>Description: 龙驭内容管理系统</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 * @version 3.0
 */
public class FilterEditorAction extends ContentBaseAction {

    /**
     * definitionPerform
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward doExecute(ActionMapping mapping,
                                       ActionForm aform,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        //内容分类ID，不能为空
        NodeDefinition definition = this.getRepository(request).getDefinitionManager().
                                    getDefinition(request.getParameter(
                "definition"));
        
               
        
      //获取搜索大纲及其属性
        String SearchDefID = request.getParameter("searchDefID");
        if(SearchDefID!=null){
        	NodeDefinition searchDef = this.getRepository(request).getDefinitionManager().getDefinition(request.getParameter("searchDefID"));
        	if(searchDef!=null){
        		List<PropertyDefinition> simpleSearch = new ArrayList<PropertyDefinition>();
                List<PropertyDefinition> referenceSearch = new ArrayList<PropertyDefinition>();        
                addPropertyToList(searchDef,simpleSearch,referenceSearch);
                request.setAttribute("SearchProperties", simpleSearch);
                request.setAttribute("SearchReferece", referenceSearch);
                request.setAttribute("searchDef", searchDef);
        	}
        }
        
        //过滤器字符串，如果为空则表示新建        
        List<PropertyDefinition> simple = new ArrayList<PropertyDefinition>();
        List<PropertyDefinition> reference = new ArrayList<PropertyDefinition>();        
        addPropertyToList(definition,simple,reference);
        request.setAttribute("properties", simple);
        request.setAttribute("referece", reference);
        request.setAttribute("variables", this.getBeanFactory().getBean("variableManager"));
        
       

        String filter = request.getParameter("filter");
        if (filter != null && (filter = filter.trim()).length() > 0) {
            FilterForm form = (FilterForm) aform;
            FilterParser parser = this.getFilterParser(request, response);
            parser.parser(filter);
            form.setConstant(parser.getValueExpression());
            form.setOperation(parser.getOperation());
            form.setReference(parser.getValueExpression());
            form.setSysVariant(parser.getValueExpression());
            form.setValueType(parser.getValueType());
        }
        return mapping.findForward("success");
    }
    
    protected void addPropertyToList(NodeDefinition def,List<PropertyDefinition> simpleList,List<PropertyDefinition> refList){
    	for(Iterator<PropertyDefinition> propertyDefinitions = def.propertyDefinitions();
        propertyDefinitions.hasNext();) {
            PropertyDefinition propertyDefinition = (PropertyDefinition)
                    propertyDefinitions.next();
            switch (propertyDefinition.getType()){
            case PropertyType.BINARY:
            case PropertyType.FIX:
            case PropertyType.TEXT:
            //case PropertyType.PATH:
            case PropertyType.UNDEFINED:
            	break;
            case PropertyType.REFERENCE:
            	simpleList.add(propertyDefinition);
            	refList.add(propertyDefinition);
            	break;
            	default:
            		simpleList.add(propertyDefinition);
            }
        }
    }
}
