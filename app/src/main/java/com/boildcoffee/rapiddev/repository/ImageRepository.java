package com.boildcoffee.rapiddev.repository;

import android.app.Activity;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.base.network.RetrofitManager;
import com.boildcoffee.base.network.rx.TransformHttpDataFunc;
import com.boildcoffee.base.network.rx.TransformerHelper;
import com.boildcoffee.rapiddev.api.GirlImageApi;
import com.boildcoffee.rapiddev.bean.ImageBean;

import java.util.List;

import io.reactivex.Observable;

/**
 * @author zjh
 *         2018/3/14
 */

public class ImageRepository {

    BaseActivity mActivity;

    public ImageRepository(BaseActivity activity){
        mActivity = activity;
    }

    public static ImageRepository create(BaseActivity activity){
        return new ImageRepository(activity);
    }

    public Observable<List<ImageBean>> getImageList(int page, int pageSize){
        return RetrofitManager
                .getInstance()
                .createReq(GirlImageApi.class)
                .getListImage(page,pageSize)
                .compose(mActivity.bindToLifecycle())
                .compose(TransformerHelper.observableToMainThreadTransformer())
                .map(new TransformHttpDataFunc<>());
    }
}
