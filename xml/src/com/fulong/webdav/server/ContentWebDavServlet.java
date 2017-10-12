package com.fulong.webdav.server;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.longcon.repository.Repository;
/**
 * 针对内容的webDAV。处理类似：/xml/[nodeDefinitionID]和/xml/[nodeDefinitionID]/[nodeID].xml的请求。
 * 对于/xml/[nodeDefinitionID]请求，将返回该分类下的内容列表信息
 * 对于/xml/[nodeDefinitionID]/[nodeID].xml ，如果这个nodeID不存在，同时请求为PUT，则创建节点
 * 如果请求为GET,则将节点转化成XML形式返回。
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
public class ContentWebDavServlet
    extends WebDavServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5511309466681381898L;
	private Repository repository;

    public void init() throws ServletException {
        super.init();
        this.repository = (Repository)this.getBeanFactory().getBean(
            "repository");   
    }
    /**
     * 获取当前网站的所有者，新建的内容都放在这个所有者下。
     */
    private Node getNodeOwner(HttpServletRequest req){
    	return null;
    }

    protected ResourceInfo getRequestResourceInfo(HttpServletRequest req,
                                                  String path) throws ServletException {
        String[] pathes = path.split("[\\.\\/]");
        String baseURL = req.getRequestURL().toString();
        int index =  baseURL.indexOf("/xml");
        if(index<0)
        	return null;
        baseURL = baseURL.substring(0, index);
        URL url = null;
		try {
			url = new URL(baseURL);
		} catch (MalformedURLException e) {
			throw new ServletException(e);
		}
        //以.xml结尾的，是请求内容。否则是请求NodeDefinition信息，即每个NodeDefinition都对应一个文件夹
        if (path.indexOf(".xml")!=-1) {
            String id = pathes[pathes.length - 2];
            String categoryId = null;
       
            NodeDefinition category = null;
        	categoryId = pathes[pathes.length - 3];            	
            category = this.repository.getDefinitionManager().getDefinition(categoryId);
            if (category != null) {
                Node content = repository.getNode(id);
                NodeInfo info = new NodeInfo(url,repository);    
                if (content != null) 
                        info.setNode(content, category);
                else {
                	Node parent = this.getNodeOwner(req);                	
                	info.setParent(parent, category);
                }
                return info;
            }
            else
                return null;
        }else { //请求的是nodedefinition信息
        	// 路径等于 /xml，默认请求根节点信息
            if (pathes.length <= 1) {
            	NodeDefinition root =repository.getDefinitionManager().getDefinition(NodeDefinition.NO_PROPERTIES_SCHEME);
                return new NodeDefinitionInfo(url,repository,  root);
            }
            else{//请求的路径为 /xml/[nodeDefinition]，返回对应的NodeDefinition信息
            	String name = pathes[pathes.length - 1];
            	NodeDefinition definition = repository.getDefinitionManager().getDefinition(name);
            	if(definition == null){
            		this.log("Error to get definition for "+ name);
            		return null;
            	}            		
                return new NodeDefinitionInfo(url,repository,definition);
            }
        }
    }

}
