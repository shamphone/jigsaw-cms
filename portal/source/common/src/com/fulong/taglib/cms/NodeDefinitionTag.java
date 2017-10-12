package com.fulong.taglib.cms;

import javax.servlet.jsp.JspException;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.taglib.SpringTagSupport;

/**
 *通过id获取NodeDefinition
 * <p>
 * Title: 龙驭门户引擎
 * </p>
 * 
 * <p>
 * Description: 龙驭网站内容管理系统核心引擎
 * </p>
 * 
 * <p>
 * Copyright: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * <p>
 * Company: 北京中科辅龙计算机技术股份有限公司
 * </p>
 * 
 * @author jiangqi
 * @version 1.0
 */

public class NodeDefinitionTag extends SpringTagSupport {
	
	private static final long serialVersionUID = -5237542056472160777L;
	private String ID;
	private String definitionID;

	public int doStartTag() throws JspException {
		NodeDefinition def = this.getRepository().getDefinitionManager()
				.getDefinition(definitionID);
		pageContext.setAttribute(ID, def);
		return (SKIP_BODY);

	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}

	public void setDefinitionID(String definitionID) {

		this.definitionID = definitionID;
	}

	public String getDefinitionID() {

		return definitionID;
	}

}
