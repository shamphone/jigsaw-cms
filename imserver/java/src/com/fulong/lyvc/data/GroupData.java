package com.fulong.lyvc.data;
/**
 * 
 * GroupData
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-18
 */
public class GroupData {
    private long id;
    private String name;
    private String desc;
    private long creatorId;
    private long groupManagerId;
    
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public long getGroupManagerId() {
        return groupManagerId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }

    public void setGroupManagerId(long groupManagerId) {
        this.groupManagerId = groupManagerId;
    }
}
