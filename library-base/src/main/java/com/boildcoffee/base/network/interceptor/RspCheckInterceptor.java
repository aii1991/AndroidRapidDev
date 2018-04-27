package com.boildcoffee.base.network.interceptor;



import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.BaseConfig;
import com.boildcoffee.base.network.exception.HttpException;
import com.boildcoffee.base.network.util.InterceptorUtils;
import com.boildcoffee.base.network.db.entity.ApiCacheEntity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpMethod;

/**
 * @author zjh
 *  2016/8/31
 */
public abstract class RspCheckInterceptor implements Interceptor{

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        try {
            response = chain.proceed(chain.request());
        }catch (Exception e){
            e.printStackTrace();
            if (e instanceof SocketTimeoutException || e instanceof ConnectException){
                throw new HttpException(0,0,"无法连接服务器");
            }
            throw new HttpException(0,0,e.getMessage());
        }
        int httpCode = response.code();
        ResponseBody rspBody = response.body();
        String httpBody = InterceptorUtils.getRspData(rspBody);
        toCacheRsp(response.request(),httpBody);
        handleRspData(httpCode, httpBody);
//        if (httpCode == 200 || httpCode == 3002){
//            try {
//                JSONObject jsonObject = new JSONObject(httpBody);
//                statusCode = jsonObject.getInt("res");
//                if (statusCode != 1){
//                    throw new HttpException(httpCode,statusCode,jsonObject.getString("msg"));
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//                throw new HttpException(httpCode,statusCode,"parase data error");
//            }catch (HttpException e){
//                throw e;
//            } catch (Exception e){
//                throw new HttpException(0,0,e.getMessage());
//            }
//        }

        return response;
    }

    private void toCacheRsp(Request request,String httpRsp) throws IOException{
        BaseConfig config = BFConfig.INSTANCE.getConfig();
        if (!request.method().equalsIgnoreCase("get")){
            return;
        }
        if (config.getApiQueryCacheMode() == BaseConfig.CacheMode.NO_CACHE || config.getRspCacheTime() <= 0){
            return;
        }else if ("0".equals(request.header("is-cache"))){
            return;
        }
        ApiCacheEntity apiCacheEntity = new ApiCacheEntity();
        apiCacheEntity.setKey(InterceptorUtils.getCacheKey(request));
        apiCacheEntity.setUrl(request.url().toString());
        apiCacheEntity.setRspData(httpRsp);
        apiCacheEntity.setCreateTime(System.currentTimeMillis());
        apiCacheEntity.setCacheTime(BFConfig.INSTANCE.getConfig().getRspCacheTime());
        try {
            BaseApplication.mInstance.getAppDatabase().apiCacheDao().insert(apiCacheEntity);
        }catch (Exception e){
            e.printStackTrace();
            BaseApplication.mInstance.getAppDatabase().apiCacheDao().update(apiCacheEntity);
        }

    }

    protected abstract void handleRspData(int httpCode, String httpBody) throws IOException;
}
