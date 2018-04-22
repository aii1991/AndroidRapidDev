package com.boildcoffee.rapiddev;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.BaseConfig;

/**
 * @author zjh
 *         2017/12/28
 */

public class MyApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        BFConfig.INSTANCE.init(new BaseConfig.Builder()
                .setApiQueryCacheMode(BaseConfig.CacheMode.CACHE_ELSE_NETWORK)
                .setBaseUrl("http://gank.io/")
                .setDebug(true).build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
