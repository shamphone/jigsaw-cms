/**
 * 
 */
package com.fulong.service;

import com.fulong.longcon.repository.Node;

/**
 *
 * <p>Title: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2006</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author lixf
 *
 * @version 2.0
 */
public class NodeObject extends ServiceObject {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 35032119780002L;

	
	public static final int Type = 1;
	
	public NodeObject(Node node) {
		super(node, Type);
	}

	public Node getNode() {
        return (Node)this.getSource();
    }

     
}

