package com.boildcoffee.base.network.rx;



import com.boildcoffee.base.network.bean.RspBean;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * @author zjh
 * 2017/3/17
 */
public class TransformHttpDataFunc<E> implements Function<RspBean<E>,E> {
    @Override
    public E apply(@NonNull RspBean<E> eRspBean) throws Exception {
        return eRspBean.getData();
    }
}
