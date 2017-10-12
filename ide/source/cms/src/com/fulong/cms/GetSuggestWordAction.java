package com.fulong.cms;

import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Query;

/**
 * 
 * <p>Title: Coolink骞冲彴鍗忓悓绠＄悊绯荤粺</p>
 *
 * <p>Description: Coolink骞冲彴鍗忓悓绠＄悊绯荤粺</p>
 *
 * <p>Copyright: 鍖椾含涓杈呴緳璁＄畻鏈烘妧鏈偂浠芥湁闄愬叕鍙� 2009</p>
 *
 * <p>Company: 鍖椾含涓杈呴緳璁＄畻鏈烘妧鏈偂浠芥湁闄愬叕鍙�</p>
 *
 * @author liuzijun
 * @version 3.1
 */
public class GetSuggestWordAction extends CMSBaseAction {

	/* (non-Javadoc)
	 * @see com.fulong.cms.CMSBaseAction#doPerform(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected ActionForward doPerform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String portletID = request.getParameter("portletID");
		request.setAttribute("portletID", portletID);
		String categoryID = request.getParameter("categoryID");
		String suggestPropId = request.getParameter("suggestPropID");
		//modified by mali 
		String lucene = request.getParameter("lucene");
		String queryFlag = Query.SQL;
		if(lucene!=null && lucene.equals("on"))
			queryFlag = Query.LUCENE;
		
		if(suggestPropId==null||suggestPropId.equals("")){
			suggestPropId = "title";
		}
		String keyword = request.getParameter("keyword");
		String getAll = request.getParameter("getAll");
		String isMutiple = request.getParameter("isMutiple");
		String[] conditions = request.getParameterValues("conditions");		
		Map contentMap = new LinkedHashMap();
		NodeIterator contents = null;
		if(categoryID!=null&&!categoryID.equals("")){
			NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryID);
			if(getAll!=null&&getAll.equals("getAll")){      //鍙栨墍鏈夌殑鍐呭杩斿洖椤甸潰
				Query query = this.getRepository(request).getQueryManager().createQuery(category, queryFlag);
				if(conditions!=null){
					for(int i=0;i<conditions.length;i++){
						FilterParser parser = this.getFilterParser(request, response);
						parser.parser(conditions[i]);
						parser.addToQuery(query);
					}
				}
				contents = query.nodes();
				contents.skip(this.getPager(request).getFromIndex());
				this.setPager(request, contents.getSize());
				request.setAttribute("suggestProp", category.getPropertyDefinition(suggestPropId));
				request.setAttribute("getAll", getAll);
				request.setAttribute("contents", contents);
				return mapping.findForward("success");
			}else{
				if(keyword!=null&&!keyword.equals("")){		//鏍规嵁鍏抽敭瀛楁悳		
					if(category!=null){
						Query query = this.getRepository(request).getQueryManager().createQuery(category, queryFlag);
						if(conditions!=null){
							for(int i=0;i<conditions.length;i++){
								FilterParser parser = this.getFilterParser(request, response);
								parser.parser(conditions[i]);
								parser.addToQuery(query);
							}
						}
						query.filterByProperty(suggestPropId, this.getRepository(request).getValueFactory().createValue(keyword));
						NodeIterator equalContents = query.nodes();
						while(equalContents.hasNext()){
							Node nodeTemp = equalContents.nextNode();
							contentMap.put(nodeTemp.getID(), nodeTemp);
						}
						Query query1 = this.getRepository(request).getQueryManager().createQuery(category, queryFlag);
						if(conditions!=null){
							for(int i=0;i<conditions.length;i++){
								FilterParser parser = this.getFilterParser(request, response);
								parser.parser(conditions[i]);
								parser.addToQuery(query1);
							}
						}
						query1.filterByKeywords(suggestPropId,keyword);
						NodeIterator likeContents = query1.nodes();
						while(likeContents.hasNext()){
							Node nodeTemp = likeContents.nextNode();
							contentMap.put(nodeTemp.getID(), nodeTemp);
						}
						if(contentMap.isEmpty()){
							Query query2 = this.getRepository(request).getQueryManager().createQuery(category, queryFlag);
							if(conditions!=null){
								for(int i=0;i<conditions.length;i++){
									FilterParser parser = this.getFilterParser(request, response);
									parser.parser(conditions[i]);
									parser.addToQuery(query2);
								}
							}
							try {
								query2.filterByKeywords("pinyin",keyword);
								NodeIterator pinyinContents = query2.nodes();
								while(pinyinContents.hasNext()){
									Node nodeTemp = pinyinContents.nextNode();
									contentMap.put(nodeTemp.getID(), nodeTemp);
								}
							} catch (IllegalArgumentException e) {
								//log.warn("unable find property:pinyin for definition ID:"+categoryID,e);
							}
						}
					}
				}
			}			
		}
		Iterator<Node> nodeValues = contentMap.values().iterator();
		Map contentMap1 = new LinkedHashMap();
		while(nodeValues.hasNext()){
			Node resultNode = nodeValues.next();
			contentMap1.put(resultNode.getProperty(suggestPropId).getString(), resultNode);
		}
		Iterator<Node> it = contentMap1.values().iterator();
		if(it!=null&&it.hasNext()){
			NodeDefinition category = this.getRepository(request).getDefinitionManager().getDefinition(categoryID);
			if(category!=null){
				request.setAttribute("suggestProp", category.getPropertyDefinition(suggestPropId));
			}
			request.setAttribute("contents", it);
			return mapping.findForward("success");
		}else{
			Writer writer = response.getWriter();
			response.setContentType("text/xml");
	        response.setHeader("Content-Type", "text/xml; charset=UTF-8");
	        writer.append("faild");
	        writer.close();
			return null;
		}		
	}

}
