package com.boildcoffee.base.network.interceptor;



import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.BaseConfig;
import com.boildcoffee.base.network.exception.HttpException;
import com.boildcoffee.base.network.util.InterceptorUtils;
import com.boildcoffee.base.network.db.entity.ApiCacheEntity;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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
        if (BFConfig.INSTANCE.getConfig().getApiQueryCacheMode() == BaseConfig.CacheMode.NO_CACHE){
            return;
        }
        ApiCacheEntity apiCacheEntity = new ApiCacheEntity();
        apiCacheEntity.setKey(URLDecoder.decode(request.url().toString(),"utf-8").hashCode());
        apiCacheEntity.setUrl(request.url().toString());
        apiCacheEntity.setRspData(httpRsp);
        try {
            BaseApplication.mInstance.getAppDatabase().apiCacheDao().insert(apiCacheEntity);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    protected abstract void handleRspData(int httpCode, String httpBody) throws IOException;
}
