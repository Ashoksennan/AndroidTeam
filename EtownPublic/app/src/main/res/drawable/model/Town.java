package com.ashok.etown2.model;

/**
 * Created by Admin on 8/6/2018.
 */

public class Town {
    private int mTowncode;
    private String mTownName;

    public Town(int mTowncode, String mTownName) {
        this.mTowncode = mTowncode;
        this.mTownName = mTownName;
    }

    public int getmTowncode() {
        return mTowncode;
    }

    public void setmTowncode(int mTowncode) {
        this.mTowncode = mTowncode;
    }

    public String getmTownName() {
        return mTownName;
    }

    public void setmTownName(String mTownName) {
        this.mTownName = mTownName;
    }
}
