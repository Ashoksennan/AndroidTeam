package com.ashok.etown2.model;

/**
 * Created by Admin on 8/7/2018.
 */

public class Delivery {
    int delcode;
    String delName;

    public Delivery(int delcode, String delName) {
        this.delcode = delcode;
        this.delName = delName;
    }

    public int getDelcode() {
        return delcode;
    }

    public void setDelcode(int delcode) {
        this.delcode = delcode;
    }

    public String getDelName() {
        return delName;
    }

    public void setDelName(String delName) {
        this.delName = delName;
    }
}
