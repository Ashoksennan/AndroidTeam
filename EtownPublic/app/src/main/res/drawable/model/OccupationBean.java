package com.ashok.etown2.model;

/**
 * Created by Admin on 8/6/2018.
 */

public class OccupationBean {
    private int moccupationcode;
    private String moccupationName;

    public OccupationBean(int moccupationcode, String moccupationName) {
        this.moccupationcode = moccupationcode;
        this.moccupationName = moccupationName;
    }

    public int getMoccupationcode() {
        return moccupationcode;
    }

    public void setMoccupationcode(int moccupationcode) {
        this.moccupationcode = moccupationcode;
    }

    public String getMoccupationName() {
        return moccupationName;
    }

    public void setMoccupationName(String moccupationName) {
        this.moccupationName = moccupationName;
    }
}
