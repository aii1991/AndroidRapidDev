package com.boildcoffee.base.network.interceptor;


import com.boildcoffee.base.BaseApplication;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zjh
 *  2016/9/13
 */
public class ReqAddTokenInterceptor implements Interceptor {
    private static String token = getToken();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("token", token)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }

    public static void setToken(String token){
        ReqAddTokenInterceptor.token = token;
        BaseApplication.mInstance.mPreferences.put("token",token);
    }

    private static String getToken(){
        return BaseApplication
                .mInstance
                .mPreferences
                .getString("token","");
    }
}
