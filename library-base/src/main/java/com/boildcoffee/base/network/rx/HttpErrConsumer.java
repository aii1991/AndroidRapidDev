package com.boildcoffee.base.network.rx;

import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.R;
import com.boildcoffee.base.network.exception.HttpException;
import com.boildcoffee.base.util.NetworkUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * @author zjh
 *         2017/3/17
 */

public abstract class HttpErrConsumer implements Consumer<Throwable>{
    private static LoginTimeOut callback;

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        throwable.printStackTrace();
        if (!NetworkUtils.isNetworkConnected(BaseApplication.mInstance)){
            error(new HttpException(-1,-1, BaseApplication.mInstance.getString(R.string.network_disable)));
            return;
        }

        if(throwable instanceof HttpException){
            HttpException httpException = (HttpException) throwable;
            if (httpException.getBusinessCode() == -2){
                if (callback != null){
                    callback.onLoginTimeOut();
                }
            }
            error(httpException);
            return;
        }
        error(new HttpException(-1,-1, throwable.getMessage()));
    }

    public abstract void error(HttpException e);


    public static void setCallback(LoginTimeOut callback) {
        HttpErrConsumer.callback = callback;
    }

    public interface LoginTimeOut{
        void onLoginTimeOut();
    }
}
