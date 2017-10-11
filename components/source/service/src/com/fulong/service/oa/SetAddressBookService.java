package com.fulong.service.oa;

import javax.servlet.ServletException;

import com.fulong.longcon.repository.Node;
import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.service.NodeObject;
import com.fulong.service.NodeService;

/**
 * 填写通讯簿属性（联系人姓名、部门、员工标示）
 * 
 * <p>
 * Title: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术有限公司 2006
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 2.0
 */
public class SetAddressBookService extends NodeService {
	private String property1;
	private String property2;
	private String property3;

	public void nodeCreated(NodeObject sv) throws ServletException {
		NodeObject event =(NodeObject)sv;
		Node node = event.getNode();		
		if(node!=null){
			NodeDefinition def = node.getDefinition();
			if(!def.isNodeType("2450189249358")){
				return;
			}else{
				String commonname = node.getProperty("user-commonname").getString();// 用户名字
				node.setProperty(this.property1,commonname);//设置联系人姓名
				node.setProperty(this.property2, node.getDefinition().getName());//部门
				node.setProperty(this.property3, "1");// 员工标示
			}
		}
	}
	
	public void setProperty1(String property1) {

		this.property1 = property1;
	}

	public String getProperty1() {
		return property1;
	}
	public void setProperty2(String property2) {

		this.property2 = property2;
	}

	public String getProperty2() {
		return property2;
	}
	public void setProperty3(String property3) {

		this.property3 = property3;
	}

	public String getProperty3() {
		return property3;
	}
}
