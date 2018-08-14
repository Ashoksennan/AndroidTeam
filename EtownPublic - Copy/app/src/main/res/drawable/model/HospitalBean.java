package com.ashok.etown2.model;

/**
 * Created by Admin on 8/6/2018.
 */

public class HospitalBean {
    private int mHospitalCode;
    private String mHospitalName;

    public HospitalBean(int mHospitalCode, String mHospitalName) {
        this.mHospitalCode = mHospitalCode;
        this.mHospitalName = mHospitalName;
    }

    public int getmHospitalCode() {
        return mHospitalCode;
    }

    public void setmHospitalCode(int mHospitalCode) {
        this.mHospitalCode = mHospitalCode;
    }

    public String getmHospitalName() {
        return mHospitalName;
    }

    public void setmHospitalName(String mHospitalName) {
        this.mHospitalName = mHospitalName;
    }
}
