package com.fulong.portlet.field.string;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.PropertyDefinition;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;
import com.fulong.portlet.PortletRender;
import com.fulong.portlet.field.PortletConfig;

/**
 * <p>
 * Title: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Description: Coolink平台协同管理系统
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司 2010
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author liuzijun
 * @version 1.0
 */
public class FinalRender extends PortletRender {

	public ActionForward render(ActionMapping mapping, ActionForm form,
			RenderRequest request, RenderResponse response) throws Exception {
		PortletPreferences preferences = request.getPreferences();
		PortletConfig config = new PortletConfig(preferences);
		Node node = lookupNode(request, response);
		if (node == null) {
			return mapping.findForward(NODONE);
		}
		String forwardChannel = preferences.getValue("channel", "");
		String forwardField = preferences.getValue("forwardField", "");
		String forwardURL = "";
		if(!forwardField.equals("")){
			if(node.getProperty(forwardField)!=null){
				forwardURL = node.getProperty(forwardField).getValue().getString();
			}
		}else if(!forwardChannel.equals("")){
			forwardURL = forwardChannel + "?contentId=" + node.getID();
		}
		request.setAttribute("forwardURL", forwardURL);
		String regExp = getRegExp(request, response);
		String customize = preferences.getValue("customize", null);
		if (customize != null) {
			request.setAttribute("preferences", request.getPreferences());
			request.setAttribute("value", processSensitive(regExp,request,response,customize));
			request.setAttribute("node", node);
			return mapping.findForward("success");
		}

		Property property = node.getProperty(config.getField());
		if (property != null) {
			Value[] values = property.getValues();
			if (values.length > 0) {
				String multiple = preferences.getValue("multiple", "yes");
				String separator = preferences.getValue("separator", " ");
				String cleanHtml = preferences.getValue("cleanHtml", "no");
				String suffix = preferences.getValue("suffix", "");
				int length = Integer.parseInt(preferences.getValue("length", "0"));
				if (multiple.equals("no")) {
					// 只显示一项
					String value = values[0].getString();
					if (value != null) {
						if (cleanHtml.equals("yes"))
							value = this.clearHtml(value);
						request.setAttribute("title", processSensitive(regExp,request,response,value));
						if (length != 0 && length*2 < this.getChineseLength(value)){
							/*if(length*2<value.length()){
								request.setAttribute("value", value.substring(0, length*2) + suffix);
							}else{
								request.setAttribute("value", value.substring(0, length) + suffix);
							}*/
							request.setAttribute("value", processSensitive(regExp,request,response,getSubstring(value, length*2) + suffix));
						}else{
							request.setAttribute("value", processSensitive(regExp,request,response,value));
						}
					}
				} else {
					StringBuilder value = new StringBuilder();
					if (cleanHtml.equals("no")) {
						for (int i = 0; i < values.length; i++) {
							String str = values[i].getString();
							if (str != null)
								value.append(str).append(separator);
						}
					} else {
						for (int i = 0; i < values.length; i++) {
							String str = values[i].getString();
							if (str != null) {
								str = this.clearHtml(str);
								value.append(str).append(separator);
							}
						}
					}
					if (value.length() > separator.length())
						value.delete(value.length() - separator.length(), value.length());
					request.setAttribute("title", processSensitive(regExp,request,response,value.toString()));
					if (length != 0 && length*2 < this.getChineseLength(value.toString()))
						request.setAttribute("value", processSensitive(regExp,request,response,getSubstring(value.toString(), (length - 1)*2) + suffix));
					else
						request.setAttribute("value", processSensitive(regExp,request,response,value.toString()));
				}
			}
		}
		
		
		request.setAttribute("preferences", request.getPreferences());
		
		request.setAttribute("node", node);
		return mapping.findForward("success");
	}
	
	protected String clearHtml(String html) throws ParserException {
		StringBuilder text = new StringBuilder();
		Parser parser = Parser.createParser(html, "utf-8");
		NodeFilter textFilter = new NodeClassFilter(TextNode.class);
		OrFilter lastFilter = new OrFilter();
		lastFilter.setPredicates(new NodeFilter[] { textFilter });
		NodeList nodelist = parser.parse(lastFilter);
		org.htmlparser.Node[] nodes = (org.htmlparser.Node[]) nodelist.toNodeArray();
		for (int i = 0; i < nodes.length; i++) {
			org.htmlparser.Node oNode = nodes[i];
			if (oNode instanceof TextNode)
				text.append(((TextNode) oNode).getText());
		}
		return text.toString();
	}
	protected int getChineseLength(String s){
		int l=0;
		char[]   c   =   s.toCharArray();   
		  for(int i=0;i<c.length;i++){   
			  int asc =(int)c[i]; 
			  if(asc<256){
				  l+=1;
			  }else{
				  l+=2;
			  }
		  }   
		  return l;
	}
	
	/**
	 * 根据敏感词库中的敏感词生成正则
	 * @param request
	 * @param response
	 * @return
	 */
	private String getRegExp(RenderRequest request, RenderResponse response){
		PortletPreferences preferences = request.getPreferences();
		//敏感词库样式处理
		String sensitiveCategoryID = preferences.getValue("sensitiveCategory", "");
		String sensitiveFieldID = preferences.getValue("sensitiveField", "");
		String regExp = "";
		if(!sensitiveCategoryID.equals("")&&!sensitiveFieldID.equals("")){
			NodeDefinition sensitiveCategory = this.getRepository().getDefinitionManager().getDefinition(sensitiveCategoryID);
			if(sensitiveCategory!=null){
				PropertyDefinition sensitiveField = sensitiveCategory.getPropertyDefinition(sensitiveFieldID);
				if(sensitiveField!=null){
					Query query = this.getRepository().getQueryManager().createQuery(sensitiveCategory, Query.SQL);
					NodeIterator<Node> nodes = query.nodes();
					while(nodes.hasNext()){
						Node sensitiveNode = nodes.nextNode();
						Value[] values = sensitiveNode.getProperty(sensitiveFieldID).getValues();
						if(values!=null){
							for(int i=0;i<values.length;i++){
								if(values[i].getString()!=null&&!values[i].getString().equals("")){
									if(regExp.length()!=0){
										regExp += "|";
									}
									regExp += ("("+values[i].getString()+")");
								}
							}
						}
					}
				}
			}
		}
		return regExp;
	}
	
	/**
	 * 处理敏感词
	 */
	private String processSensitive(String regExp,RenderRequest request, RenderResponse response,String value){
		if(regExp!=null&&regExp.length()!=0){
			value = value.replaceAll(regExp, "<span class=\""+request.getPreferences().getValue("sensitiveStyle", "")+"\">$0</span>");
		}
		return value;
	}
	
	protected String getSubstring(String output, int length) {
		int l=0;
		List list = new ArrayList();
		char[]   c   =   output.toCharArray();   
		  for(int i=0;i<c.length;i++){   
			  int asc =(int)c[i]; 
			  if(asc<256){
				  l+=1;
			  }else{
				  l+=2;
			  }
			  list.add(c[i]);
			  if(l>=length){
				  String ret="";
				  for(int j = 0; j<list.size(); j++){
					  ret+=list.get(j);
				  }
				  return ret;
			  }
		  }   
		 return output;
	}
}
