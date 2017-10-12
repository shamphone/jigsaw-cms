/**
 * 
 */
package com.fulong.portlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fulong.longcon.expression.FilterParser;
import com.fulong.longcon.expression.ServletFilterParser;
import com.fulong.longcon.repository.Node;
import com.fulong.service.NodeWorkItem;

/**
 *   
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
public class ServletNodeWorkItem  implements NodeWorkItem{
	private Node node;
	private HttpServletRequest request;
	private HttpServletResponse response;
	public ServletNodeWorkItem(Node node, HttpServletRequest request, HttpServletResponse response){
		this.node=node;
		this.request= request;
		this.response = response;
	}
	
	public Node getNode(){
		return this.node;
	}
	
	public HttpServletRequest getHttpRequest(){
		return this.request;
	}
	
	protected FilterParser createParser(){
		return new ServletFilterParser(request, response);
	}

	/* (non-Javadoc)
	 * @see com.fulong.longcon.workflow.WorkItem#validate(java.lang.String)
	 */
	public boolean validate(String conditions) {
		FilterParser parser = this.createParser();
		try {
			//conditions为多个条件拼接成的字符串，需要按照" And "或者" or "来解析，得到具体的各个条件(暂时只处理And条件)
			// String[] conditionArray = conditions.split("[ And | or ]");
			String[] conditionArray = conditions.split(" And ");
			for(int i=0;i<conditionArray.length;i++){
				parser.parser(conditionArray[i]);
				if(!parser.validate(node)){
					return false;
				}
			}
			
		} catch (Exception e) {
			 return false;
		}
		return true;
	}

}
