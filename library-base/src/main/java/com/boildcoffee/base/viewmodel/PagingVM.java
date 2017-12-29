package com.boildcoffee.base.viewmodel;

import android.support.v4.widget.SwipeRefreshLayout;

import com.boildcoffee.base.BaseConfig;
import com.boildcoffee.base.bean.PagingBean;
import com.boildcoffee.base.network.exception.HttpException;
import com.boildcoffee.base.network.rx.HttpErrConsumer;
import com.boildcoffee.base.service.IPagingService;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author zjh
 *         2017/12/25
 */

public abstract class PagingVM<T> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener,IBFViewVM {
    private static final int START_PAGE = 1;
    private static final int PAGE_SIZE = BaseConfig.PAGE_SIZE;
    private int currentPage = START_PAGE;
    private int lastPage;
    private IPagingService<T> mDataService;
    private PagingBean<T> mPagingBean = new PagingBean<>();


    public void startGetData() {
        if (mDataService == null){
            mDataService = getPagingService();
        }
        startLoadData();
    }

    private void startLoadData() {
        mPagingBean.setIsRefreshing(true);
        mDataService.getData(currentPage, PAGE_SIZE,
                data -> {
                    mPagingBean.getData().clear();
                    if (!isCloseLoadMore(data)) {
                        mPagingBean.getData().addAll(data);
                    }
                },
                new HttpErrConsumer() {
                    @Override
                    public void error(HttpException e) {
                        Logger.d(e.getMessage());
                    }
                },
                () -> mPagingBean.setIsRefreshing(false));
    }


    @Override
    final public void onLoadMoreRequested() {
        lastPage = currentPage;
        currentPage++;
        mPagingBean.setIsLoadFail(false);
        mPagingBean.setIsLoadComplete(false);
        mDataService.getData(currentPage, PAGE_SIZE,
                data -> {
                    if (!isCloseLoadMore(data)) {
                        mPagingBean.getData().addAll(data);
                        mPagingBean.setIsLoadComplete(true);
                    }
                },
                new HttpErrConsumer() {
                    @Override
                    public void error(HttpException e) {
                        currentPage = lastPage;
                        mPagingBean.setIsLoadFail(true);
                    }
                },
                () -> {
                });
    }

    @Override
    final public void onRefresh() {
        lastPage = currentPage;
        currentPage = START_PAGE;
        mPagingBean.setIsRefreshing(true);
        mDataService.getData(currentPage, PAGE_SIZE, data -> {
            mPagingBean.getData().clear();
            if (!isCloseLoadMore(data)) {
                mPagingBean.getData().addAll(data);
            }
        }, new HttpErrConsumer() {
            @Override
            public void error(HttpException e) {
                Logger.d(e.getMessage());
            }
        }, () -> mPagingBean.setIsRefreshing(false));
    }

    private boolean isCloseLoadMore(List<T> t) {
        if (t.size() < PAGE_SIZE) {
            mPagingBean.getData().addAll(t);
            mPagingBean.setIsNoData(true);
            if (mPagingBean.isRefreshing()) {
                mPagingBean.setIsRefreshing(false);
            }
            return true;
        }
        return false;
    }

    final public PagingBean<T> getPagingBean() {
        return mPagingBean;
    }

    public abstract int getVariableId();
    public abstract IPagingService<T> getPagingService();

}
