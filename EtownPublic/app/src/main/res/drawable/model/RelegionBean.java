package com.ashok.etown2.model;

/**
 * Created by Admin on 8/6/2018.
 */

public class RelegionBean {
    private int mReligioncode;
    private String mReligionName;

    public RelegionBean(int mReligioncode, String mReligionName) {
        this.mReligioncode = mReligioncode;
        this.mReligionName = mReligionName;
    }

    public int getmReligioncode() {
        return mReligioncode;
    }

    public void setmReligioncode(int mReligioncode) {
        this.mReligioncode = mReligioncode;
    }

    public String getmReligionName() {
        return mReligionName;
    }

    public void setmReligionName(String mReligionName) {
        this.mReligionName = mReligionName;
    }
}
