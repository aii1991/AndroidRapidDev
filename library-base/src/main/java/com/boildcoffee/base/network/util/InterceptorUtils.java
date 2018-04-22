package com.boildcoffee.base.network.util;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * @author zjh
 *  2016/9/2
 */
public class InterceptorUtils {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static String getRspData(ResponseBody responseBody){
        String rspData = null;
        try {
            long contentLength = responseBody.contentLength();
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }
            if (contentLength != 0) {
                rspData = buffer.clone().readString(charset);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return rspData;
    }

    public static String getReqData(RequestBody requestBody) throws IOException {
        Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return buffer.readString(UTF8);
    }

    public static Response changeRspData(Response response, String rspData) {
        MediaType contentType = response.body().contentType();
        ResponseBody body = ResponseBody.create(contentType,rspData);
        return response.newBuilder().body(body).build();
    }

    public static int getCacheUrlKey(Request request){
        String url = "";
        try {
            url = URLDecoder.decode(request.url().toString(),"utf-8");
            if (!url.substring(url.length() - 1).equals("/")){
                url += "/";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return url.hashCode();
    }
}
