package com.fulong.longcon.repository.data;

import com.fulong.common.dao.*;

/**
 *
 * <p>Title: 龙驭门户引擎</p>
 *
 * <p>Description: 龙驭网站内容管理系统核心引擎</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author LJY
 * @version 1.0
 */
public class NodeDefinitionAuthorizationData
    implements Data {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6007485383817589756L;
	private String action;
    private String definitionID;
    private String principalID;
    private int principalType;
    public String getAction() {
        return action;
    }

    public String getDefinitionID() {

        return definitionID;
    }

    public String getPrincipalID() {
        return principalID;
    }

    public int getPrincipalType() {
        return principalType;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setDefinitionID(String definitionID) {

        this.definitionID = definitionID;
    }

    public void setPrincipalID(String principalID) {
        this.principalID = principalID;
    }

    public void setPrincipalType(int principalType) {
        this.principalType = principalType;
    }

}
