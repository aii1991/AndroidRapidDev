package com.boildcoffee.rapiddev.viewmodel;

import android.widget.Toast;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.base.bindingadapter.common.ReplyCommand;
import com.boildcoffee.base.paging.IPagingService;
import com.boildcoffee.base.viewmodel.PagingVM;
import com.boildcoffee.rapiddev.BR;
import com.boildcoffee.rapiddev.MyApplication;
import com.boildcoffee.rapiddev.bean.ImageBean;
import com.boildcoffee.rapiddev.repository.ImageRepository;


/**
 * @author zjh
 *         2017/12/26
 */

public class MainVM extends PagingVM<ImageBean>{
    private ImageRepository mImageRepository;

    public MainVM(BaseActivity rxActivity){
        mImageRepository = ImageRepository.create(rxActivity);
    }

    public ReplyCommand<Integer> mItemClickListener = new ReplyCommand<>((position) -> Toast.makeText(MyApplication.mInstance,"click position => "+ position,Toast.LENGTH_LONG).show());

    @Override
    public IPagingService<ImageBean> getPagingService() {
        return (page, pageSize, onSuccess, onError, onComplete) ->
                mImageRepository
                        .getImageList(page,pageSize)
                        .subscribe(onSuccess,onError,onComplete);
    }

    @Override
    public int getVariableId() {
        return BR.imageBean;
    }

}
