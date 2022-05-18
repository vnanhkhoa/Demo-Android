package com.ownourome.musicmp3.data.network.response;

import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private Data data;

    public Result() {
    }

    public Result(String msg, Data data) {
        this.msg = msg;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
