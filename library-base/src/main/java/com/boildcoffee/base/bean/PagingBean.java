package com.boildcoffee.base.bean;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import java.util.List;

/**
 * @author zjh
 *         2017/12/29
 */

public class PagingBean<T> {
    private ObservableBoolean isRefreshing = new ObservableBoolean(false);
    private ObservableBoolean isNoData = new ObservableBoolean(false);
    private ObservableBoolean isLoadComplete = new ObservableBoolean(false);
    private ObservableBoolean isLoadFail = new ObservableBoolean(false);
    private ObservableList<T> mData = new ObservableArrayList<>();
    private ObservableField<int[]> swlColorRes = new ObservableField<>();

    public boolean isRefreshing() {
        return isRefreshing.get();
    }

    public void setIsRefreshing(boolean isRefreshing) {
        this.isRefreshing.set(isRefreshing);
    }

    public boolean isNoData() {
        return isNoData.get();
    }

    public void setIsNoData(boolean isNoData) {
        this.isNoData.set(isNoData);
    }

    public boolean isLoadComplete() {
        return isLoadComplete.get();
    }

    public void setIsLoadComplete(boolean isLoadComplete) {
        this.isLoadComplete.set(isLoadComplete);
    }

    public boolean isLoadFail() {
        return isLoadFail.get();
    }

    public void setIsLoadFail(boolean isLoadFail) {
        this.isLoadFail.set(isLoadFail);
    }

    public ObservableList<T> getData() {
        return mData;
    }

    public void addData(T data) {
        this.mData.add(data);
    }

    public void addAllData(List<T> datas){
        this.mData.addAll(datas);
    }

    public ObservableBoolean getIsRefreshing() {
        return isRefreshing;
    }

    public ObservableBoolean getIsNoData() {
        return isNoData;
    }

    public ObservableBoolean getIsLoadComplete() {
        return isLoadComplete;
    }

    public ObservableBoolean getIsLoadFail() {
        return isLoadFail;
    }

    public void setSwlColorRes(int[] colorRes){
        swlColorRes.set(colorRes);
    }

    public ObservableField<int[]> getSwlColorRes(){
        return swlColorRes;
    }
}
