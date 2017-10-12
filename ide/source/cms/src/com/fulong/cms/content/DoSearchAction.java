package com.fulong.cms.content;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.common.util.Pager;
import com.fulong.common.util.RangeIterator;
import com.fulong.common.util.Tree;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.PropertyType;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.NodeDefinitionTreeBuilder;

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
public class DoSearchAction extends ContentBaseAction {
    /**
     *
     * @param mapping ActionMapping
     * @param form ActionForm
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return ActionForward
     * @throws Exception
     */
    public ActionForward doExecute(ActionMapping mapping, ActionForm form,
                                       HttpServletRequest request,
                                       HttpServletResponse response) throws
            Exception {
        String definitionId = request.getParameter("definition");
        String keywords = request.getParameter("keywords");
        String propertyId=request.getParameter("sortproperty");
        String sortRule=request.getParameter("sorttype");
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        //modified by mali 
		String lucene = request.getParameter("lucene");
		String queryFlag = Query.SQL;
		if(lucene!=null && lucene.equals("on"))
			queryFlag = Query.LUCENE;
		
		if (pageNo == null) {
			pageNo = "0";
		}
		if (pageSize == null) {
			pageSize = "20";
		}
		Pager pager = new Pager();
		pager.setPageNo(Integer.parseInt(pageNo));
		pager.setPageSize(Integer.parseInt(pageSize));
        NodeDefinition definition = this.getRepository(request).getDefinitionManager().
                                    getDefinition(definitionId);
        Query query = this.getRepository(request).getQueryManager().createQuery(definition, queryFlag);
//        query.filterByAuthorization(request.getUserPrincipal());
        if ((keywords != null) && (keywords.length() > 0)) {
        	String temp = keywords.replaceAll("[(]", "").replaceAll("\\-", "").replaceAll("\\'", "").replaceAll("\\)", "");
        	request.setAttribute("keywords",keywords);
            query.filterByKeywords(temp);
        }
        //NodeIterator<Node> it=query.nodes();
        //this.setPager(request, it.getSize());
        if(propertyId!=null&&sortRule!=null){
            if(sortRule.equals("asc")==true){
                query.sortByProperty(propertyId, true);
            }
            else{
                query.sortByProperty(propertyId, false);

            }
        }
        RangeIterator<Node> contents = query.nodes();
        this.setPager(request, contents.getSize());
        contents.skip(pager.getFromIndex());
        request.setAttribute("nodes", contents);
        request.setAttribute("definition", definition);
        ArrayList<PropertyDefinition> simple= new ArrayList<PropertyDefinition>();
        for(Iterator<PropertyDefinition> definitions=definition.propertyDefinitions();definitions.hasNext();){
        	PropertyDefinition property=definitions.next();
        	switch(property.getType()){
        	case PropertyType.DATE:
        	case PropertyType.BOOLEAN:
        	case PropertyType.DOUBLE:
        	case PropertyType.LONG:
        	case PropertyType.NAME:
        	case PropertyType.PATH:
        	case PropertyType.STRING:
        		simple.add(property);
        		break;
        		default:;
        	}
        }
        request.setAttribute("propertyDefinitions", simple);
        NodeDefinitionTreeBuilder builder = new NodeDefinitionTreeBuilder(
                definition);
        Tree tree = builder.build();
        /*
        if (this.isAdministrator(request)) {
            tree = builder.build();
        } else {
            tree = builder.buildAuthorizationTree(request.getUserPrincipal());
        }
        */
        if (tree != null) {
            request.setAttribute("categoryTree", tree);
        }
        request.setAttribute("multiple",request.getParameter("multiple"));
        request.setAttribute("definition",request.getParameter("definition"));
        request.setAttribute("searchText",request.getParameter("searchText"));
        
		String str = request.getParameter("selectedProps");

		String[] properties = str.split("[*]");
		List<PropertyDefinition> listTemp = new ArrayList<PropertyDefinition>();
		for(int i=0;i<properties.length;i++){
			if(properties[i].equals("title")){
				PropertyDefinition prop = definition.getPropertyDefinition(properties[i]);
				if(prop!=null){
					listTemp.add(prop);
				}else{//兼容会员库数据，取会员库姓名字段
					prop = definition.getPropertyDefinition("user-commonname");
					if(prop!=null){
						listTemp.add(prop);
					}else{//如果没有姓名字段，取会员库用户名字段
						prop = definition.getPropertyDefinition("user-username");
						if(prop!=null){
							listTemp.add(prop);
						}
					}					
				}
			}else{
				PropertyDefinition prop = definition.getPropertyDefinition(properties[i]);
				if(prop!=null){
					listTemp.add(prop);
				}
			}
		}
		if(listTemp.size()>0){
			request.setAttribute("selectedProps", listTemp);
		}

        request.setAttribute("filterpatterns",request.getParameter("filterpatterns"));
        return mapping.findForward("success");
    }

}
