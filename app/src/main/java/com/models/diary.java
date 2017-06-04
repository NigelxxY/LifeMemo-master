package com.models;

/**
 * Created by Nigel_xxY on 2017/5/13.
 */

public class diary extends Object {
    private int diaryid;
    private int userid;
    private String diarys;
    private String date;

    public String getDate() {
        return date;
    }

    public int getUserid() {
        return userid;
    }

    public int getDiaryid() {
        return diaryid;
    }

    public String getDiarys() {
        return diarys;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDiaryid(int diaryid) {
        this.diaryid = diaryid;
    }

    public void setDiarys(String diarys) {
        this.diarys = diarys;
    }
}
