package com.boildcoffee.rapiddev;

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
        BFConfig.getInstance().init(new BaseConfig.Builder()
                .setBaseUrl("http://gank.io/")
                .setDebug(true).build());
    }
}
