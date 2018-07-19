package com.none.myapplication;

public class Values {
    int id,passno;
    String name;

    public Values(int id,String name,int passno) {
        this.id = id;
        this.name = name;
        this.passno = passno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpassno() {
        return passno;
    }

    public void setpassno(int passno) {
        this.passno = passno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}