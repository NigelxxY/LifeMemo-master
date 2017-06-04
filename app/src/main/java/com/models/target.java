package com.models;

import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/13.
 */

public class target {
    private int targetid;
    private int userid;
    private int targetnum;
    private List<String> target;
    private String date;

    public int getTargetid() {
        return targetid;
    }

    public int getUserid() {
        return userid;
    }

    public int getTargetnum() {
        return targetnum;
    }

    public String getDate() {
        return date;
    }

    public List<String> getTarget() {
        return target;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTarget(List<String> target) {
        this.target = target;
    }

    public void setTargetid(int targetid) {
        this.targetid = targetid;
    }

    public void setTargetnum(int targetnum) {
        this.targetnum = targetnum;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
