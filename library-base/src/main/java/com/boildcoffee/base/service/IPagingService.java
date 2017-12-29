package com.boildcoffee.base.service;

import com.boildcoffee.base.network.exception.HttpException;
import com.boildcoffee.base.network.rx.HttpErrConsumer;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author zjh
 *         2017/12/25
 *
 */

public interface IPagingService<T> {
    void getData(int page, int pageSize, Consumer<List<T>> onSuccess, HttpErrConsumer onError, Action onComplete);
}
