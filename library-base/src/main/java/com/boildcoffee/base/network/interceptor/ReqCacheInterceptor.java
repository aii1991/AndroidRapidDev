package com.boildcoffee.base.network.interceptor;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.BaseConfig;
import com.boildcoffee.base.network.db.entity.ApiCacheEntity;
import com.boildcoffee.base.network.util.InterceptorUtils;
import com.boildcoffee.base.util.NetworkUtils;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author zjh
 *         2018/3/15
 */

public class ReqCacheInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (request.method().equalsIgnoreCase("get")){
            int cacheMode = BFConfig.INSTANCE.getConfig().getApiQueryCacheMode();
            if (cacheMode == BaseConfig.CacheMode.CACHE_ELSE_NETWORK
                    || (cacheMode == BaseConfig.CacheMode.NETWORK_ELSE_CACHE && !NetworkUtils.isNetworkConnected(BaseApplication.mInstance))){
                ApiCacheEntity apiCacheEntity = BaseApplication.mInstance.getAppDatabase().apiCacheDao().findByKey(InterceptorUtils.getCacheUrlKey(request));
                if (apiCacheEntity != null){
                    return new Response
                            .Builder()
                            .code(200)
                            .protocol(Protocol.HTTP_1_1)
                            .message("data from cache")
                            .body(ResponseBody.create(MediaType.parse("text/plain"),apiCacheEntity.getRspData()))
                            .request(request)
                            .build();
                }
            }
        }
        return chain.proceed(request);
    }
}
