package com.boildcoffee.base;

/**
 * @author zjh
 *         2018/1/2
 */

public class BFConfig {
    private BFConfig (){}
    private static BFConfig instance;
    private BaseConfig mBaseConfig;

    public synchronized static BFConfig getInstance(){
        if (instance == null){
            instance = new BFConfig();
        }
        return instance;
    }

    public void init(BaseConfig baseConfig){
        mBaseConfig = baseConfig;
    }

    public BaseConfig getConfig(){
        return mBaseConfig;
    }

}
