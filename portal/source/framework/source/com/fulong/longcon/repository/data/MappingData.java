package com.fulong.longcon.repository.data;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lishaobo</a>
 * @version 1.0
 */
public class MappingData extends BasicData {
    /**
	 * 
	 */
	private static final long serialVersionUID = 8745256677307303795L;
	private String sPropertyID;
    private String sDefinitionID;
    private String remoteSite;
    private String dPropertyID;
    private String dPropertyName;
    private String dDefinitionID;
    private int type;
    public MappingData() {
        super();

    }

    public String getSDefinitionID() {
        return sDefinitionID;
    }

    public String getSPropertyID() {
        return sPropertyID;
    }

    public String getDDefinitionID() {
        return dDefinitionID;
    }

    public String getDPropertyID() {
        return dPropertyID;
    }

    public String getRemoteSite() {
        return remoteSite;
    }

    public String getDPropertyName() {
        return dPropertyName;
    }

    public int getType() {
        return type;
    }

    public void setSDefinitionID(String sDefinitionID) {
        this.sDefinitionID = sDefinitionID;
    }

    public void setSPropertyID(String sPropertyID) {

        this.sPropertyID = sPropertyID;
    }

    public void setDDefinitionID(String dDefinitionID) {
        this.dDefinitionID = dDefinitionID;
    }

    public void setDPropertyID(String dPropertyID) {
        this.dPropertyID = dPropertyID;
    }

    public void setRemoteSite(String remoteSite) {
        this.remoteSite = remoteSite;
    }

    public void setDPropertyName(String dPropertyName) {
        this.dPropertyName = dPropertyName;
    }

    public void setType(int type) {
        this.type = type;
    }

}
