package com.boildcoffee.base.adapter;

import android.databinding.ViewDataBinding;
import android.view.View;

import com.boildcoffee.base.R;
import com.chad.library.adapter.base.BaseViewHolder;

public class BFViewHolder extends BaseViewHolder {
        public BFViewHolder(View view) {
            super(view);
        }

        public ViewDataBinding getBinding(){
            return (ViewDataBinding) itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        }
    }