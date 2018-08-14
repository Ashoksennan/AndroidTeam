package com.ashok.etown2.model;

/**
 * Created by Admin on 8/1/2018.
 */

public class BirthAbstractYearWiseBean {
    String year,liveBirth,stillBirth,total;

    public BirthAbstractYearWiseBean(String year, String liveBirth, String stillBirth, String total) {
        this.year = year;
        this.liveBirth = liveBirth;
        this.stillBirth = stillBirth;
        this.total = total;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLiveBirth() {
        return liveBirth;
    }

    public void setLiveBirth(String liveBirth) {
        this.liveBirth = liveBirth;
    }

    public String getStillBirth() {
        return stillBirth;
    }

    public void setStillBirth(String stillBirth) {
        this.stillBirth = stillBirth;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
