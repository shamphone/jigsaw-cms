package com.fulong.lyvc.data;
/**
 * 
 * ConferenceModelRoleData
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-11
 */
public class ModeRoleData {
	private long id;
    private long modeId;		//����ģʽ��Ψһ��ʶ
    private String name;		//��ɫ���
    private String desc;		//��ɫ����
    private boolean isDefault;		//�Ƿ�ȱʡ��ɫ��ÿ��ģʽֻ����һ��ȱʡ��ɫ

    public String getDesc() {
        return desc;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public long getModeId() {
        return modeId;
    }

    public String getName() {
        return name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public boolean isDefault() {
		return isDefault;
	}

	public void setModeId(long modelID) {
        this.modeId = modelID;
    }

    public void setName(String name) {
        this.name = name;
    }
}
