/**
 * 
 */
package com.fulong.service.property;

import javax.servlet.http.HttpServletRequest;

import com.fulong.common.util.DesEncrypter;
import com.fulong.longcon.repository.Node;
import com.fulong.service.NodeService;
import com.fulong.service.ServiceParameters;

/**
 * 将属性加密保存，配置参数：
 * 1．待加密属性：可以多选。
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
public class EncryptService extends NodeService {
	protected static final String DES_KEY = "fulong";
	

	public void doProcess(Node node, ServiceParameters parameters, HttpServletRequest request) throws Exception{
		String property = parameters.getValue("property");
		if(property == null)
			property = "user-password";
		if(node.getProperty(property)==null)
			throw new IllegalArgumentException("No property for name :"+ property +" found in node with definition "+node.getDefinition().getName()+".");
		String password = node.getProperty(property).getString();// 用户密码
		DesEncrypter encrypter = new DesEncrypter(DES_KEY);
		String EN_password = encrypter.encrypt(password);
		node.setProperty(property, EN_password);
	}
	
}

