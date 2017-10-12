package com.fulong.lyvc.data;
/**
 * 
 * ConferenceMember
 *
 * ��Ԧ��Ƶ����ϵͳ v3.0
 *
 * ��Ȩ���У������пƸ���������ɷ����޹�˾ 2009
 *
 * @author ���۷�
 *
 * ����޸�ʱ�䣺2009-3-11
 */
public class ConferenceMemberData {
    private long conferenceId;
    private long userId;
    private long roleId;
    private long id;
    
    public ConferenceMemberData() {
    }

    public void setConferenceId(long conferenceId) {
        this.conferenceId = conferenceId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConferenceId() {
        return conferenceId;
    }

    public long getUserId() {
        return userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public long getId() {
        return id;
    }
}
