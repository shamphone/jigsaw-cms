package com.fulong.taglib.cms;

import javax.servlet.jsp.JspException;

import com.fulong.longcon.repository.NodeDefinition;
import com.fulong.taglib.SpringTagSupport;

/**
 *通过id获取ProperyDefinition
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

public class ProperyDefinitionTag extends SpringTagSupport {
	
	private static final long serialVersionUID = 8412261590535879322L;
	
	private String definitionID;
	private String ID;
	private String propertyID;

	public int doStartTag() throws JspException {

		NodeDefinition def = getRepository().getDefinitionManager().getDefinition(definitionID);
		pageContext.setAttribute(ID, def.getPropertyDefinition(propertyID));
		
		return (SKIP_BODY);
	}

	public void setDefinitionID(String definitionID) {
		this.definitionID = definitionID;
	}

	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public String getDefinitionID() {
		return definitionID;
	}

	public String getPropertyID() {
		return propertyID;
	}

	public String getID() {
		return ID;
	}

}
