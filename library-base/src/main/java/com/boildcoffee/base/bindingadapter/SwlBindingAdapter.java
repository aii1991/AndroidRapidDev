package com.boildcoffee.base.bindingadapter;

import android.databinding.BindingAdapter;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v4.widget.SwipeRefreshLayout;

import com.boildcoffee.base.viewmodel.PagingVM;

/**
 * @author zjh
 *         2017/12/29
 */

public class SwlBindingAdapter {
    @BindingAdapter("pageVM")
    public static <T> void bindPagingVM(SwipeRefreshLayout swipeRefreshLayout, PagingVM<T> pagingVM){
        swipeRefreshLayout.setRefreshing(pagingVM.getPagingBean().isRefreshing());
        swipeRefreshLayout.setColorSchemeResources(pagingVM.getPagingBean().getSwlColorRes().get());
        swipeRefreshLayout.setOnRefreshListener(pagingVM);
        pagingVM.getPagingBean().getIsRefreshing().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                swipeRefreshLayout.setRefreshing(((ObservableBoolean)sender).get());
            }
        });
        pagingVM.getPagingBean().getSwlColorRes().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                swipeRefreshLayout.setColorSchemeResources((int[]) ((ObservableField)sender).get());
            }
        });
    }
}
