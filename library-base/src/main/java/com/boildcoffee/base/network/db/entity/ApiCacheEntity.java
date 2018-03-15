package com.boildcoffee.base.network.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author zjh
 *         2018/3/13
 */
@Entity(indices = {@Index(value = {"key"},unique = true)},tableName = "t_api_cache")
public class ApiCacheEntity {
    @PrimaryKey
    @ColumnInfo(name = "key")
    private int key;
    @ColumnInfo(name = "rsp")
    private String rspData;
    @ColumnInfo(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getRspData() {
        return rspData;
    }

    public void setRspData(String rspData) {
        this.rspData = rspData;
    }
}
