package com.ashok.etown2.model;

/**
 * Created by Admin on 8/9/2018.
 */

public class Disease {
   private int mDiseasecode;
   private String mDiseasename;

    public Disease(int mDiseasecode, String mDiseasename) {
        this.mDiseasecode = mDiseasecode;
        this.mDiseasename = mDiseasename;
    }

    public int getmDiseasecode() {
        return mDiseasecode;
    }

    public void setmDiseasecode(int mDiseasecode) {
        this.mDiseasecode = mDiseasecode;
    }

    public String getmDiseasename() {
        return mDiseasename;
    }

    public void setmDiseasename(String mDiseasename) {
        this.mDiseasename = mDiseasename;
    }
}
