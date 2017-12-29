package com.boildcoffee.rapiddev.ui.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import com.boildcoffee.rapiddev.R;
import com.boildcoffee.rapiddev.bean.ImageBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * @author zjh
 *         2017/12/27
 */

public class MainListAdapter extends BaseQuickAdapter<ImageBean,BaseViewHolder>{

    public MainListAdapter(@Nullable List<ImageBean> data) {
        super(R.layout.main_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ImageBean item) {

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
}
