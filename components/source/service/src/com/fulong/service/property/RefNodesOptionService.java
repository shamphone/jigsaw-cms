package com.fulong.service.property;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.NodeIterator;
import com.fulong.longcon.repository.Property;
import com.fulong.longcon.repository.Query;
import com.fulong.longcon.repository.Value;
import com.fulong.longcon.repository.ValueUtils;
import com.fulong.longcon.site.Site;
import com.fulong.longcon.site.SiteFactory;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 引用属性节点集的增删改服务
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
public class RefNodesOptionService extends NodeService {
	private SiteFactory siteFactory;
	
	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request)
			throws Exception {
		if (node != null) {
			String nodeSourceType = parameters.getValue("nodeSourceType");
			String CategoryID = parameters.getValue("CategoryID");
			String refPropID = parameters.getValue("refPropID");
			String optPattern = parameters.getValue("optPattern");
			Node sourceNode = null;
			if(nodeSourceType!=null&&!nodeSourceType.equals("")){
				if(nodeSourceType.equals("urlParam")){
					sourceNode = this.repository.getNode(request.getParameter("contentId"));
				}else if(nodeSourceType.equals("userParam")){
					sourceNode = (Node)request.getUserPrincipal();
				}else if(nodeSourceType.equals("siteParam")){
					sourceNode = this.getCurrentSite(request).getOwner();
				}
			}
			if(sourceNode!=null){
				Property refProp = sourceNode.getProperty(refPropID);
				if(refProp!=null){
					Value[] values = refProp.getValues();
					Value nodeValue = this.repository.getValueFactory().createValue(node);
					if(optPattern!=null&&!optPattern.equals("")){
						if(optPattern.equals("increase")){
							if(!ArrayUtils.contains(values, nodeValue)){
								values = this.add(values, nodeValue);
							}
						}else if(optPattern.equals("minus")){
							values = this.delete(values, nodeValue);
						}
					}
					sourceNode.setProperty(refPropID, values);
				}
			}
		}
	}
	private Site getCurrentSite(HttpServletRequest request) throws Exception {
		Site site = (Site) request.getAttribute(Site.class.getName());
		if (site == null) {
			String siteId = request.getParameter("siteId");
			if (siteId == null) {
				siteId = request.getServerName();
				if(request.getServerPort()!=80){
					siteId += (":"+80);
				}
			}
			site = this.siteFactory.getSite(siteId);
			if (site != null) {
				request.setAttribute(Site.class.getName(), site);
			}
		}
		return site;
	}
	
	private <Node> Value[] add(Value[] array,Value objectToAdd){
		Value[] ret = (Value[])Array.newInstance(array.getClass().getComponentType(), array.length+1);
		System.arraycopy(array, 0, ret, 0, array.length);
		ret[array.length] = objectToAdd;
		return ret;
	}
	
	private <Value> Value[] delete(Value[] array,Value objectToDelete){
		List<Value> list = new ArrayList<Value>();
		for(int i=0;i<array.length;i++){
			if(!array[i].equals(objectToDelete)){
				list.add(array[i]);
			}
		}
		return (Value[]) list.toArray((Value[])Array.newInstance(array.getClass().getComponentType(), 0));
	}

}
