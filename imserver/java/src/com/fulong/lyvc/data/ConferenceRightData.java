package com.fulong.lyvc.data;
/**
 * 
 * ConferenceRightData
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-11
 */
public class ConferenceRightData {
    private long rightID;
    private String name;
    private String desc;
    
    public String getDesc() {
        return desc;
    }

    public String getName() {
        return name;
    }

    public long getRightID() {
        return rightID;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRightID(long rightID) {
        this.rightID = rightID;
    }
}
