package com.models;

import java.io.Serializable;

/**
 * Created by Nigel_xxY on 2017/5/13.
 */

public class note extends Object implements Serializable{
    private int noteid;
    private int userid;
    private String notes;
    private String date;

    public int getNoteid() {
        return noteid;
    }

    public int getUserid() {
        return userid;
    }

    public String getDate() {
        return date;
    }

    public String getNotes() {
        return notes;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNoteid(int noteid) {
        this.noteid = noteid;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
