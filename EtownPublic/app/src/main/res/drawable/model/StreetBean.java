package com.ashok.etown2.model;

/**
 * Created by Admin on 8/6/2018.
 */

public class StreetBean {
   private String mStreetNum;
   private String mStreetName;

    public StreetBean(String mStreetNum, String mStreetName) {
        this.mStreetNum = mStreetNum;
        this.mStreetName = mStreetName;
    }

    public String getmStreetNum() {
        return mStreetNum;
    }

    public void setmStreetNum(String mStreetNum) {
        this.mStreetNum = mStreetNum;
    }

    public String getmStreetName() {
        return mStreetName;
    }

    public void setmStreetName(String mStreetName) {
        this.mStreetName = mStreetName;
    }
}
