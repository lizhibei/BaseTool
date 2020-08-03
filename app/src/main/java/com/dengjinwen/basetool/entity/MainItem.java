package com.dengjinwen.basetool.entity;

public class MainItem {
    private int id;
    private String title;
    private String desc;

    public MainItem(String title, String desc,int id) {
        this.title = title;
        this.desc = desc;
        this.id=id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
