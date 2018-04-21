package com.boildcoffee.base;


import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

/**
 * @author zjh
 *         2017/12/27
 */

public class BaseActivity extends RxAppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.mInstance.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BaseApplication.mInstance.removeActivity(this);
    }
}
