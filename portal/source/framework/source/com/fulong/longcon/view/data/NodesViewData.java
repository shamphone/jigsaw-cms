package com.fulong.longcon.view.data;

import java.io.Serializable;

/**
 * <p>Title: Coolink协同工作支撑平台</p>
 *
 * <p>Description: Coolink协同工作支撑平台</p>
 *
 * <p>Copyright: 北京中科辅龙计算机技术股份有限公司 2007</p>
 *
 * <p>Company: 北京中科辅龙计算机技术股份有限公司</p>
 *
 * @author <a href='mailto:lishaobo@fulong.com.cn'>lixf</a>
 * @version 1.0
 */
public class NodesViewData implements Serializable {
    
	private static final long serialVersionUID = 7549078204688582079L;
	
	private String proDefinitionID;
    private String nodeDefinitionID;
    private int width;
    private int orderNo;
    public NodesViewData() {
        super();
    }

    public void setProDefinitionID(String proDefinitionID) {

        this.proDefinitionID = proDefinitionID;
    }

    public String getProDefinitionID() {

        return proDefinitionID;
    }

    public void setNodeDefinitionID(String nodeDefinitionID) {

        this.nodeDefinitionID = nodeDefinitionID;
    }

    public void setWidth(int width) {

        this.width = width;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getNodeDefinitionID() {

        return nodeDefinitionID;
    }

    public int getWidth() {

        return width;
    }

    public int getOrderNo() {
        return orderNo;
    }

}
