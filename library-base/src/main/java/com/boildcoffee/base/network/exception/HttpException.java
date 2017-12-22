package com.boildcoffee.base.network.exception;

import java.io.IOException;

/**
 * @author zjh
 * 2017/3/1
 */
public class HttpException extends IOException{
    private int httpCode;  // http响应码
    private int businessCode; //业务状态码

    public HttpException(int httpCode, int businessCode, String msg){
        super(msg);
        this.httpCode = httpCode;
        this.businessCode = businessCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public int getBusinessCode() {
        return businessCode;
    }

    public void setBusinessCode(int businessCode) {
        this.businessCode = businessCode;
    }

    public boolean shouldRetry() {
        // 网络错误才应该重试
        return businessCode == 501 || businessCode == 502 || businessCode == 503;

    }

}
