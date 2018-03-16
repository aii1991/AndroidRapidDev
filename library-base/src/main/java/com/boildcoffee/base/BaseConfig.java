package com.boildcoffee.base;

import android.support.annotation.IdRes;

import com.boildcoffee.base.network.interceptor.RspCheckInterceptor;

import java.io.IOException;

import retrofit2.Converter;

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
    private final int LOADING_ERROR_IMG = R.mipmap.load_image_200; //正在加载时显示的图片
    private final int LOAD_FAIL_IMG = R.mipmap.load_image_failed_200; //加载失败时显示的图片

    private final String DB_NAME = "bf";

    //room
    public static class CacheMode{
        public static int NO_CACHE = 0; //不缓存
        public static int NETWORK_ELSE_CACHE = 1; //网络数据优先
        public static int CACHE_ELSE_NETWORK = 2; //缓存数据优先
    }

    private boolean debug = DEBUG;
    private int pageSize = PAGE_SIZE;

    //retrofit
    private String baseUrl = BASE_URL;
    private int connectTimeout = CONNECT_TIMEOUT;
    private int readTimeout = READ_TIMEOUT;
    private int writeTimeout = WRITE_TIMEOUT;
    private int imageCacheSize = IMG_CACHE_SIZE;
    private String imageCacheFileName = IMG_CACHE_NAME;
    private int loadingImage = LOADING_ERROR_IMG;
    private int loadFailImage = LOAD_FAIL_IMG;
    private RspCheckInterceptor RspCheckInterceptor;
    private Converter.Factory converter;
    private int apiQueryCacheMode;
    private String dbName = DB_NAME;

    public static class Builder{
        BaseConfig mBaseConfig;
        public Builder(){
            mBaseConfig = new BaseConfig();
        }

        public Builder setDebug(boolean debug){
            mBaseConfig.debug = debug;
            return this;
        }

        public Builder setPageSize(int pageSize){
            mBaseConfig.pageSize = pageSize;
            return this;
        }

        public Builder setBaseUrl(String baseUrl){
            mBaseConfig.baseUrl = baseUrl;
            return this;
        }

        public Builder setRetrofitConnectTimeout(int connectTimeout){
            mBaseConfig.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setRetrofitReadTimeout(int readTimeout){
            mBaseConfig.readTimeout = readTimeout;
            return this;
        }

        public Builder setRetrofitWriteTimeout(int writeTimeout){
            mBaseConfig.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setImageCacheSize(int imageCacheSize){
            mBaseConfig.imageCacheSize = imageCacheSize;
            return this;
        }

        public Builder setImageCacheFileName(String fileName){
            mBaseConfig.imageCacheFileName = fileName;
            return this;
        }

        public Builder setLoadingImage(@IdRes int loadingImage){
            mBaseConfig.loadingImage = loadingImage;
            return this;
        }

        public Builder setLoadFailImage(@IdRes int loadFailImage){
            mBaseConfig.loadFailImage = loadFailImage;
            return this;
        }

        public Builder setRspCheckInterceptor(RspCheckInterceptor rspCheckInterceptor){
            mBaseConfig.RspCheckInterceptor = rspCheckInterceptor;
            return this;
        }

        public Builder setApiQueryCacheMode(int cacheMode){
            mBaseConfig.apiQueryCacheMode = cacheMode;
            return this;
        }

        public Builder setDbName(String dbName){
            mBaseConfig.dbName = dbName;
            return this;
        }

        public Builder setConverter(Converter.Factory converter){
            mBaseConfig.converter = converter;
            return this;
        }

        public BaseConfig build(){
            return mBaseConfig;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public int getImageCacheSize() {
        return imageCacheSize;
    }

    public String getImageCacheFileName() {
        return imageCacheFileName;
    }

    public int getLoadingImage() {
        return loadingImage;
    }

    public int getLoadFailImage() {
        return loadFailImage;
    }

    public boolean isDebug() {
        return debug;
    }

    public RspCheckInterceptor getRspCheckInterceptor() {
        if (RspCheckInterceptor == null){
            RspCheckInterceptor = new RspCheckInterceptor() {
                @Override
                protected void handleRspData(int httpCode, String httpBody) throws IOException {

                }
            };
        }
        return RspCheckInterceptor;
    }

    public int getApiQueryCacheMode() {
        return apiQueryCacheMode;
    }

    public String getDbName() {
        return dbName;
    }

    public Converter.Factory getConverter() {
        return converter;
    }
}
