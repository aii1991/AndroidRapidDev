package com.boildcoffee.base.repo;

import com.boildcoffee.base.network.exception.HttpException;

import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * @author zjh
 *         2017/12/25
 *
 */

public interface IPagingRepo<T> {
    void getData(int page, int pageSize, Consumer<List<T>> onSuccess, Consumer<HttpException> onError, Action onComplete);
}
