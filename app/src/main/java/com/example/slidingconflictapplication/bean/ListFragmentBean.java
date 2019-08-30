package com.example.slidingconflictapplication.bean;

/**
 * Created by xwxwaa on 2019/8/30.
 */

public class ListFragmentBean {
    private String str;

    public String getStr() {
        return str == null ? "" : str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
