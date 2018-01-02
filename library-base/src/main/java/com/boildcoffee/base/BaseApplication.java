package com.boildcoffee.base;

import android.app.Activity;
import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

import net.grandcentrix.tray.AppPreferences;

import java.util.Stack;

/**
 * @author zjh
 *         2017/12/22
 */

public class BaseApplication extends Application{
    public static BaseApplication mInstance;
    protected Stack<Activity> activityStack = new Stack<>();
    public AppPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mPreferences = new AppPreferences(this);
        initLogger();
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .tag("boiledCoffee")
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BFConfig.getInstance().getConfig().isDebug();
            }
        });
    }

    /**
     * 添加到activity堆栈中
     */
    public void addActivity(Activity activity) {
        if(activity != null){
            activityStack.push(activity);
        }
    }

    /**
     * 把Activity从堆栈中删除
     */
    public void removeActivity(Activity activity) {
        activityStack.remove(activity);
    }

    /**
     * 遍历堆栈中所有Activity并finish
     */
    public void exit() {
        for (int i = 0; i < activityStack.size(); i++) {
            Activity activity = activityStack.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
    }

    /**
     * 程序退出后台缓存
     */
    public void absolutelyExit() {
        clearActivity();
        System.exit(0);
    }

    /**
     * 清除堆栈中的activity
     */
    public void clearActivity() {
        exit();
        activityStack.clear();
    }


}
