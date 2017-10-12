package com.fulong.lyvc.data;

/**
 * 
 * ConferenceModelData
 * 
 * ��Ԧ��Ƶ����ϵͳ v3.0
 * 
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 * 
 * @author ���۷�
 * 
 *         ����޸�ʱ�䣺2009-3-11
 */
public class ModeData {
	private String name; // ����ģʽ���
	private String desc; // ����ģʽ����
	private boolean isPredefined;
	private long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public String getName() {
		return name;
	}

	public boolean isPredefined() {
		return isPredefined;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPredefined(boolean isPredefined) {
		this.isPredefined = isPredefined;
	}
}
