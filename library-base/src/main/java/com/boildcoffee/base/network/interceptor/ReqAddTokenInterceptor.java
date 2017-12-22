package com.boildcoffee.base.network.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author zjh
 *  2016/9/13
 */
public class ReqAddTokenInterceptor implements Interceptor {
    private String token = "74029c766b33497fb0f6ec393e033704";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original.newBuilder()
                .header("token", token)
                .method(original.method(), original.body())
                .build();
        return chain.proceed(request);
    }

}
