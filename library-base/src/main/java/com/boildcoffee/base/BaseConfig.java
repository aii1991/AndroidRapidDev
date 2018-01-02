package com.boildcoffee.base;

import android.support.annotation.IdRes;

/**
 * @author zjh
 *         2017/12/22
 */

final public class BaseConfig {
    private final boolean DEBUG = BuildConfig.DEBUG;
    private final int PAGE_SIZE = 15; //分页大小
    //retrofit
    private String BASE_URL = "";
    private final int CONNECT_TIMEOUT = 30; //网络连接超时时间(以秒为单位)
    private final int READ_TIMEOUT = 60; //网络读取超时时间(以秒为单位)
    private final int WRITE_TIMEOUT = 60; //网络写超时时间(以秒为单位)
    //glide
    private  final int IMG_CACHE_SIZE = 100 * 1024 * 1024; //图片磁盘缓存大小
    private final String IMG_CACHE_NAME = "CACHE_IMG"; //图片缓存目录
    private final int LOADING_ERROR_IMG = R.mipmap.load_image_200;
    private final int LOAD_FAIL_IMG = R.mipmap.load_image_failed_200;

    private boolean mDebug = DEBUG;
    private int mPageSize = PAGE_SIZE;
    private String mBaseUrl = BASE_URL;
    private int mConnectTimeout = CONNECT_TIMEOUT;
    private int mReadTimeout = READ_TIMEOUT;
    private int mWriteTimeout = WRITE_TIMEOUT;
    private int mImageCacheSize = IMG_CACHE_SIZE;
    private String mImageCacheFileName = IMG_CACHE_NAME;
    private int mLoadingImage = LOADING_ERROR_IMG;
    private int mLoadFailImage = LOAD_FAIL_IMG;


    public static class Builder{
        BaseConfig mBaseConfig;
        public Builder(){
            mBaseConfig = new BaseConfig();
        }

        public Builder setDebug(boolean debug){
            mBaseConfig.mDebug = debug;
            return this;
        }

        public Builder setPageSize(int pageSize){
            mBaseConfig.mPageSize = pageSize;
            return this;
        }

        public Builder setBaseUrl(String baseUrl){
            mBaseConfig.mBaseUrl = baseUrl;
            return this;
        }

        public Builder setRetrofitConnectTimeout(int connectTimeout){
            mBaseConfig.mConnectTimeout = connectTimeout;
            return this;
        }

        public Builder setRetrofitReadTimeout(int readTimeout){
            mBaseConfig.mReadTimeout = readTimeout;
            return this;
        }

        public Builder setRetrofitWriteTimeout(int writeTimeout){
            mBaseConfig.mWriteTimeout = writeTimeout;
            return this;
        }

        public Builder setImageCacheSize(int imageCacheSize){
            mBaseConfig.mImageCacheSize = imageCacheSize;
            return this;
        }

        public Builder setImageCacheFileName(String fileName){
            mBaseConfig.mImageCacheFileName = fileName;
            return this;
        }

        public Builder setLoadingImage(@IdRes int loadingImage){
            mBaseConfig.mLoadingImage = loadingImage;
            return this;
        }

        public Builder setLoadFailImage(@IdRes int loadFailImage){
            mBaseConfig.mLoadFailImage = loadFailImage;
            return this;
        }

        public BaseConfig build(){
            return mBaseConfig;
        }
    }

    public int getPageSize() {
        return mPageSize;
    }

    public String getBaseUrl() {
        return mBaseUrl;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public int getWriteTimeout() {
        return mWriteTimeout;
    }

    public int getImageCacheSize() {
        return mImageCacheSize;
    }

    public String getImageCacheFileName() {
        return mImageCacheFileName;
    }

    public int getLoadingImage() {
        return mLoadingImage;
    }

    public int getLoadFailImage() {
        return mLoadFailImage;
    }

    public boolean isDebug() {
        return mDebug;
    }
}
