package com.oumenreame.viewpager.data.model;

import java.io.Serializable;

public class Item implements Serializable {

    private String title;
    private String detail;

    public Item() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
