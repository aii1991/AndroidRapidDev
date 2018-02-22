package com.boildcoffee.base.network.interceptor;



import com.boildcoffee.base.network.exception.HttpException;
import com.boildcoffee.base.network.util.InterceptorUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import okhttp3.Interceptor;
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
        handleRspData(httpCode, httpBody);
        if (httpCode == 200){
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
        }

        return response;
    }

    protected abstract void handleRspData(int httpCode, String httpBody) throws IOException;
}
