package com.boildcoffee.base.viewmodel;

import android.databinding.ObservableBoolean;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.MutableBoolean;

import com.boildcoffee.base.BaseConfig;
import com.boildcoffee.base.repo.IPagingRepo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.orhanobut.logger.Logger;

import java.util.List;

/**
 * @author zjh
 *         2017/12/25
 */

public abstract class PagingMv<T> implements BaseQuickAdapter.RequestLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private static final int START_PAGE = 1;
    private static final int PAGE_SIZE = BaseConfig.PAGE_SIZE;
    private int currentPage = START_PAGE;
    private int lastPage; //是否最后一页
    private IPagingRepo<T> mDataRepo; //数据仓库
    private ObservableBoolean isRefreshing = new ObservableBoolean(true);
    private BaseQuickAdapter<T, ? extends BaseViewHolder> mQuickAdapter;


    public void startGetData(BaseQuickAdapter<T, ? extends BaseViewHolder> baseQuickAdapter, IPagingRepo dataRepo) {
        mQuickAdapter = baseQuickAdapter;
        mQuickAdapter.setOnLoadMoreListener(this);
        mDataRepo = dataRepo;
        startLoadData();
    }

    /**
     * 开始加载数据
     */
    private void startLoadData() {
        isRefreshing.set(true);
        mDataRepo.getData(currentPage, PAGE_SIZE,
                data -> {
                    mQuickAdapter.getData().clear();
                    if (!isCloseLoadMore(data)) {
                        mQuickAdapter.addData(data);
                    }
                },
                e -> Logger.d(e.getMessage()),
                () -> isRefreshing.set(false));
    }


    @Override
    public void onLoadMoreRequested() {
        lastPage = currentPage;
        currentPage++;
        mDataRepo.getData(currentPage, PAGE_SIZE,
                data -> {
                    if (!isCloseLoadMore(data)) {
                        mQuickAdapter.addData(data);
                        mQuickAdapter.loadMoreComplete();
                    }
                },
                e -> {
                    currentPage = lastPage;
                    mQuickAdapter.loadMoreFail();
                    Logger.d(e.getMessage());
                },
                () -> {
                });
    }

    @Override
    public void onRefresh() {
        lastPage = currentPage;
        currentPage = START_PAGE;
        mDataRepo.getData(currentPage, PAGE_SIZE, data -> {
            mQuickAdapter.getData().clear();
            if (!isCloseLoadMore(data)) {
                mQuickAdapter.addData(data);
                mQuickAdapter.loadMoreComplete();
            }
        }, e -> {

        }, () -> isRefreshing.set(false));
    }

    private boolean isCloseLoadMore(List<T> t) {
        if (t.size() < PAGE_SIZE) {
            mQuickAdapter.addData(t);
            mQuickAdapter.loadMoreEnd();
            if (isRefreshing.get()) {
                isRefreshing.set(false);
            }
            return true;
        }
        return false;
    }

    public ObservableBoolean isRefreshing() {
        return isRefreshing;
    }
}
