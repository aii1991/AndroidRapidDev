package com.boildcoffee.base.network.bean;


import com.google.gson.annotations.SerializedName;

/**
 * @author zjh
 * 2017/3/1
 */

public class RspBean<T> {
    private int status;
    @SerializedName("results")
    private T data;
    private String msg; //errorMSG;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
