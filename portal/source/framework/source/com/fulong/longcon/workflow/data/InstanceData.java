package com.fulong.longcon.workflow.data;

/**
 * <p>Title: 龙驭工作流系统</p>
 *
 * <p>Description: 龙驭工作流系统</p>
 *
 * <p>Copyright: Copyright (c)北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lixf@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class InstanceData extends BasicData{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7151639230035288130L;
	private String pkid;
    private String definitionID;
    private int state;
    public String getPkid() {
        return pkid;
    }

    public String getDefinitionID() {
        return definitionID;
    }

    public int getState() {
        return state;
    }

    public void setPkid(String pkid) {
        this.pkid = pkid;
    }

    public void setDefinitionID(String definitionID) {
        this.definitionID = definitionID;
    }

    public void setState(int state) {
        this.state = state;
    }

}
