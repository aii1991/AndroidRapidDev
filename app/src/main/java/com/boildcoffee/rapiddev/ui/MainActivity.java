package com.boildcoffee.rapiddev.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.boildcoffee.base.BaseActivity;
import com.boildcoffee.rapiddev.R;
import com.boildcoffee.rapiddev.databinding.ActivityMainBinding;
import com.boildcoffee.rapiddev.viewmodel.MainVM;

public class MainActivity extends BaseActivity {
    MainVM mainVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainVM = new MainVM(this);
        binding.setMainVm(mainVM);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainVM.startGetData();
    }
}
