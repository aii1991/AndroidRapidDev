package com.boildcoffee.base.bindingadapter;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * @author zjh
 *         2017/12/25
 */

public class RvViewBindingAdapter {

    @BindingAdapter(value = "bindAdapter")
    public static <T extends BaseQuickAdapter> void onBindAdapter(final RecyclerView recyclerView,final T baseQuickAdapter){
        baseQuickAdapter.openLoadAnimation();
        recyclerView.setAdapter(baseQuickAdapter);
    }

    @BindingAdapter(value = "onItemClickListener")
    public static void addOnItemClickListener(final RecyclerView recyclerView, SimpleClickListener onItemClickListener){
        recyclerView.addOnItemTouchListener(onItemClickListener);
    }

}
