package com.models;

import java.util.List;

/**
 * Created by Nigel_xxY on 2017/5/13.
 */

public class user extends Object {
    private int userid;
    private String username;
    private String password;
    private List<String> sleepk;
    private List<String> eatk;
    private List<String> studyk;
    private List<String> boringk;


    public int getUserid() {
        return userid;
    }
    public void setUserid(int userid){
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getBoringk() {
        return boringk;
    }

    public List<String> getEatk() {
        return eatk;
    }

    public List<String> getSleepk() {
        return sleepk;
    }

    public List<String> getStudyk() {
        return studyk;
    }

    public void setSleepk(List<String> str) {
        this.sleepk = str;
    }

    public void setStudyk(List<String> str) {
        this.studyk = str;
    }
    public void setBoringk(List<String>str) {
        this.boringk = str;
    }
    public void setEatk(List<String> str) {
        this.eatk = str;
    }
}
