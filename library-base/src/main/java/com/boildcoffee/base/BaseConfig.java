package com.boildcoffee.base;

/**
 * @author zjh
 *         2017/12/22
 */

final public class BaseConfig {
    public static final boolean DEBUG = BuildConfig.DEBUG;
    public static final int PAGE_SIZE = 15; //分页大小

    //------网络层配置--------
    public static  String BASE_URL = getBaseUrl();
    //retrofit
    public static final int CONNECT_TIMEOUT = 30; //网络连接超时时间(以秒为单位)
    public static final int READ_TIMEOUT = 60; //网络读取超时时间(以秒为单位)
    public static final int WRITE_TIMEOUT = 60; //网络写超时时间(以秒为单位)

    //glide
    public static final int IMG_CACHE_SIZE = 100 * 1024 * 1024; //图片磁盘缓存大小
    public static final String IMG_CACHE_NAME = "CACHE_IMG"; //图片缓存目录

    static String getBaseUrl(){
        String baseUrl;
        if (DEBUG){
            baseUrl = "http://gank.io/";
        }else {
            baseUrl = "";
        }
        return baseUrl;
    }
}
