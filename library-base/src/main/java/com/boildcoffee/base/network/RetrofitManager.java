package com.boildcoffee.base.network;


import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.network.interceptor.ReqCacheInterceptor;
import com.boildcoffee.base.network.interceptor.ReqAddTokenInterceptor;
import com.boildcoffee.base.network.util.SSLUtils;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author zjh
 *  2016/3/1
 */
public class RetrofitManager {
    private Retrofit mRetrofit;
    private ClearableCookieJar mCookieJar;
    private SharedPrefsCookiePersistor mPersistentCookieJar;
    private RetrofitManager(){
        initRetrofit();
    }

    private static class HandlerRetrofitManager {
        private static final RetrofitManager mRetrofitManager = new RetrofitManager();
    }


    public static RetrofitManager getInstance(){
        return HandlerRetrofitManager.mRetrofitManager;
    }

    /**
     * 初始化retorfit配置
     */
    private void initRetrofit() {
        HttpLoggingInterceptor LoginInterceptor = new HttpLoggingInterceptor();
        mPersistentCookieJar = new SharedPrefsCookiePersistor(BaseApplication.mInstance);
        mCookieJar = new PersistentCookieJar(new SetCookieCache(),mPersistentCookieJar);
        LoginInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.addInterceptor(BFConfig.INSTANCE.getConfig().getRspCheckInterceptor());
        if (BFConfig.INSTANCE.getConfig().isDebug()){
            builder.addInterceptor(LoginInterceptor);
        }
        builder.addInterceptor(new ReqCacheInterceptor());
        builder.addInterceptor(new ReqAddTokenInterceptor());

        builder.connectTimeout(BFConfig.INSTANCE.getConfig().getConnectTimeout(), TimeUnit.SECONDS);
        builder.readTimeout(BFConfig.INSTANCE.getConfig().getReadTimeout(), TimeUnit.SECONDS);
        builder.writeTimeout(BFConfig.INSTANCE.getConfig().getWriteTimeout(), TimeUnit.SECONDS);
        builder.retryOnConnectionFailure(true);
        builder.cookieJar(mCookieJar); //cookie配置
        builder.hostnameVerifier((hostname, session) -> true);

        SSLUtils.SSLParams sslParams = SSLUtils.getSslSocketFactory(null, null, null);//SSL配置
        builder.sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager);
        OkHttpClient client = builder.build();
        Retrofit.Builder retrofitBuilder =  new Retrofit.Builder()
                .baseUrl(BFConfig.INSTANCE.getConfig().getBaseUrl())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client);
        if (BFConfig.INSTANCE.getConfig().getConverter() == null){
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
        }
        mRetrofit = retrofitBuilder.build();
    }

    public void reLoad(){
        initRetrofit();
    }

    public <T> T createReq(Class<T> reqServer){
        return mRetrofit.create(reqServer);
    }

    public void removeCookie() {
        mCookieJar.clear();
    }

    /**
     * 持久化cookie
     */
    public void persistentCookie(){
        mPersistentCookieJar.saveAll(mCookieJar.loadForRequest(HttpUrl.get(URI.create(BFConfig.INSTANCE.getConfig().getBaseUrl()))));
    }

}
