package com.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by Nigel_xxY on 2017/5/13.
 */

public class event extends Object implements Serializable {
    private int eventid;
    private int userid;
    private String eventclass;
    private String eventkeyword;
    private String sttime;
    private String endtime;
    private String eventdate;
    private String eventhing;

    public String getEventhing() { return eventhing; }
    public void setEventhing(String string ){this.eventhing = string;}
    public int getEventid() {
        return eventid;
    }

    public int getUserid() {
        return userid;
    }

    public  String getEndtime() {
        return endtime;
    }

    public String getEventclass() {
        return eventclass;
    }

    public String getEventdate() {
        return eventdate;
    }

    public String getEventkeyword() {
        return eventkeyword;
    }

    public String getSttime() {
        return sttime;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setEventclass(String eventclass) {
        this.eventclass = eventclass;
    }

    public void setEventdate(String eventdate) {
        this.eventdate = eventdate;
    }

    public void setEventid(int eventid) {
        this.eventid = eventid;
    }

    public void setEventkeyword(String eventkeyword) {
        this.eventkeyword = eventkeyword;
    }

    public void setSttime(String sttime) {
        this.sttime = sttime;
    }

    public int getTimeLength(){
        String[] sttimes = this.sttime.split(":");
        String[] endtimes = this.endtime.split(":");
        int stminute = Integer.valueOf(sttimes[0])*60+Integer.valueOf(sttimes[1]);
        int endminute = Integer.valueOf(endtimes[0])*60 + Integer.valueOf(endtimes[1]);
        return endminute-stminute;
    }
}
