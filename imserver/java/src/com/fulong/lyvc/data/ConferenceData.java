package com.fulong.lyvc.data;

import java.util.*;

public class ConferenceData {

    private String title;		//�������
    private String desc;                //��������
    private long conferecneModelID;     //����ģʽ
    private Date startTime;             //���鿪ʼʱ��
    private Date endTime;               //�������ʱ��
    private long id;
    private long conferenceCreatorId;
    
    
    public long getConferecneModelID() {
        return conferecneModelID;
    }

    public String getDesc() {
        return desc;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getTitle() {
        return title;
    }

    public long getId() {
        return id;
    }

    public long getConferenceCreatorId() {
        return conferenceCreatorId;
    }

    public void setConferecneModelID(long conferecneModelID) {
        this.conferecneModelID = conferecneModelID;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setConferenceCreatorId(long conferenceCreatorId) {
        this.conferenceCreatorId = conferenceCreatorId;
    }

}
