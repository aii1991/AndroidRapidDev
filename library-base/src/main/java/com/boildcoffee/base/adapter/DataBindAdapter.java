package com.boildcoffee.base.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.boildcoffee.base.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author zjh
 *         2017/12/27
 */

public class DataBindAdapter<T> extends BaseQuickAdapter<T,BFViewHolder>{
    private int mVariableId;

    public static <T> DataBindAdapter<T> create(int layoutResId, List<T> data, int variableId){
        return new DataBindAdapter<>(layoutResId,data,variableId);
    }

    public static <T> DataBindAdapter<T> create(int layoutResId, int variableId){
        return new DataBindAdapter<>(layoutResId,variableId);
    }

    private DataBindAdapter(int layoutResId, @Nullable List<T> data, int variableId) {
        super(layoutResId, data);
        init(variableId);
    }

    private DataBindAdapter(int layoutResId, int variableId) {
        super(layoutResId);
        init(variableId);
    }

    private void init(int variableId){
        mVariableId = variableId;
    }


    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(mLayoutInflater,layoutResId,parent,false);
        if (binding == null) {
            return super.getItemView(layoutResId, parent);
        }
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    @Override
    protected void convert(BFViewHolder helper, T item) {
        ViewDataBinding binding = helper.getBinding();
        binding.setVariable(mVariableId,item);
    }

}
