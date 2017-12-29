package com.boildcoffee.rapiddev.viewmodel;

import com.boildcoffee.base.network.RetrofitManager;
import com.boildcoffee.base.network.rx.TransformHttpDataFunc;
import com.boildcoffee.base.network.rx.TransformerHelper;
import com.boildcoffee.base.service.IPagingService;
import com.boildcoffee.base.viewmodel.PagingVM;
import com.boildcoffee.rapiddev.BR;
import com.boildcoffee.rapiddev.bean.ImageBean;
import com.boildcoffee.rapiddev.repo.MainRepo;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;


/**
 * @author zjh
 *         2017/12/26
 */

public class MainVM extends PagingVM<ImageBean>{
    private RxAppCompatActivity mRxActivity;

    public MainVM(RxAppCompatActivity rxActivity){
        mRxActivity = rxActivity;
    }


    @Override
    public IPagingService<ImageBean> getPagingService() {
        return (page, pageSize, onSuccess, onError, onComplete) ->
                RetrofitManager
                        .getInstance()
                        .createReq(MainRepo.class)
                        .getListImage(page,pageSize)
                        .compose(mRxActivity.bindToLifecycle())
                        .compose(TransformerHelper.observableToMainThreadTransformer())
                        .map(new TransformHttpDataFunc<>())
                        .subscribe(onSuccess,onError,onComplete);
    }

    @Override
    public int getVariableId() {
        return BR.imageBean;
    }


}
