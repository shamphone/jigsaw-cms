package com.fulong.lyvc;

import java.sql.Timestamp;

/**
 * <p>Title: servermonitor</p>
 *
 * <p>Description: ��Ƶ��������ϵͳ</p>
 *
 * <p>Copyright: </p>
 *
 * <p>Company: �пƸ���</p>
 *
 * @author ��ΰɽ
 * @version 1.0
 */
public class ServerState {
	
    private Timestamp startTime;
    private Timestamp stopTime;
    private int state;
    
    public Timestamp getStartTime() {
        return startTime;
    }

    public Timestamp getStopTime() {
        return stopTime;
    }

    public int getState() {
        return state;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setStopTime(Timestamp stopTime) {
        this.stopTime = stopTime;
    }

    public void setState(int state) {
        this.state = state;
    }
}
