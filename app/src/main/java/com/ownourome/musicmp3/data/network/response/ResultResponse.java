package com.ownourome.musicmp3.data.network.response;

import com.google.gson.annotations.SerializedName;

public class ResultResponse {

    @SerializedName("msg")
    private String msg;

    @SerializedName("data")
    private DataResponse dataResponse;

    public ResultResponse() {
    }

    public ResultResponse(String msg, DataResponse dataResponse) {
        this.msg = msg;
        this.dataResponse = dataResponse;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataResponse getData() {
        return dataResponse;
    }

    public void setData(DataResponse dataResponse) {
        this.dataResponse = dataResponse;
    }
}
