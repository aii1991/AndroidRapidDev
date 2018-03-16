package com.boildcoffee.base.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.boildcoffee.base.adapter.DataBindAdapter;
import com.boildcoffee.base.bindingadapter.common.ReplyCommand;
import com.boildcoffee.base.paging.viewmodel.PagingVM;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

/**
 * @author zjh
 *         2017/12/25
 */

public class RvBindingAdapter {

    @BindingAdapter(value = {"pageVM","itemViewId","BRName"})
    public static <T> void bindPagingVM(final RecyclerView recyclerView, final PagingVM<T> pagingVM,@IdRes int itemViewId,@IdRes int variableId){
        DataBindAdapter<T> dataBindAdapter = DataBindAdapter.create(itemViewId,pagingVM.getPagingBean().getData(),variableId);
        dataBindAdapter.openLoadAnimation();
        dataBindAdapter.setOnLoadMoreListener(pagingVM,recyclerView);
        pagingVM.getPagingBean().getData().addOnListChangedCallback(new BFOnListChangedCallback<>(dataBindAdapter,pagingVM));
        recyclerView.setAdapter(dataBindAdapter);
        pagingVM.getPagingBean().getIsLoadComplete().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableBoolean)sender).get()){
                    dataBindAdapter.loadMoreComplete();
                }
            }
        });
        pagingVM.getPagingBean().getIsLoadFail().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableBoolean)sender).get()){
                    dataBindAdapter.loadMoreFail();
                }
            }
        });
        pagingVM.getPagingBean().getIsNoData().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                if (((ObservableBoolean)sender).get()){
                    dataBindAdapter.loadMoreEnd();
                }
            }
        });
    }

    @BindingAdapter(value = "layoutManager")
    public static void setLayoutManager(final RecyclerView recyclerView,final LayoutManagers.LayoutManagerFactory layoutManagerFactory){
        recyclerView.setLayoutManager(layoutManagerFactory.create(recyclerView));
    }


    @BindingAdapter(value = "onItemClickListener")
    public static void addOnItemClickListener(final RecyclerView recyclerView, ReplyCommand<Integer> replyCommand){
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                replyCommand.execute(position);
            }
        });
    }


    private final static class BFOnListChangedCallback<T> extends ObservableList.OnListChangedCallback<ObservableList<T>> {
        DataBindAdapter<T> mDataBindAdapter;
        PagingVM<T> mPagingVM;

        BFOnListChangedCallback(DataBindAdapter<T> dataBindAdapter,PagingVM<T> pagingVM){
            mDataBindAdapter = dataBindAdapter;
            mPagingVM = pagingVM;
        }

        @Override
        public void onChanged(ObservableList<T> sender) {
            mDataBindAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
            mDataBindAdapter.notifyItemRangeChanged(positionStart,itemCount);
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
            if (mPagingVM.getPagingBean().isRefreshing()){
                mDataBindAdapter.notifyDataSetChanged();
            }else {
                mDataBindAdapter.notifyItemRangeInserted(positionStart,itemCount);
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
            mDataBindAdapter.notifyItemMoved(fromPosition,toPosition);
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
            mDataBindAdapter.notifyItemRangeRemoved(positionStart,itemCount);
        }
    }

}
