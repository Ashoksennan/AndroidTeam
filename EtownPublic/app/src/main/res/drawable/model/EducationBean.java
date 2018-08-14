package com.ashok.etown2.model;

/**
 * Created by Admin on 8/6/2018.
 */

public class EducationBean {
    private int mEducationcode;
    private String mEducationName;

    public EducationBean(int mEducationcode, String mEducationName) {
        this.mEducationcode = mEducationcode;
        this.mEducationName = mEducationName;
    }

    public int getmEducationcode() {
        return mEducationcode;
    }

    public void setmEducationcode(int mEducationcode) {
        this.mEducationcode = mEducationcode;
    }

    public String getmEducationName() {
        return mEducationName;
    }

    public void setmEducationName(String mEducationName) {
        this.mEducationName = mEducationName;
    }
}
