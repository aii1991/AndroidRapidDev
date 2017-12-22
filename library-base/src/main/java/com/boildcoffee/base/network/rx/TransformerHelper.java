package com.boildcoffee.base.network.rx;


import android.app.ProgressDialog;

import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.util.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author zjh
 *  2017/3/1
 */

public class TransformerHelper {


    public static <T> ObservableTransformer<T,T> observableToMainThreadTransformer(){
        return observableToMainThreadTransformer(null);
    }

    public static <T> ObservableTransformer<T,T> observableToMainThreadTransformer(final ProgressDialog pd){
        return new ObservableTransformer<T,T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .doOnSubscribe(new Consumer<Disposable>() {
                            @Override
                            public void accept(@NonNull Disposable disposable) throws Exception {
                                if (NetworkUtils.isNetworkConnected(BaseApplication.mInstance)){
                                    if(pd != null && !pd.isShowing()){
                                        pd.show();
                                    }
                                }
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
