package com.boildcoffee.base.bindingadapter;

import android.app.Activity;
import android.databinding.BindingAdapter;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.boildcoffee.base.imageloader.ImageLoaderManager;

/**
 * @author zjh
 *         2017/12/28
 */

public class IvBindingAdapter {

    @BindingAdapter(value = {"activity","loadUrl","preLoadThumbnail"},requireAll = false)
    public static void loadUrl(ImageView v, Activity activity,String url, String thumbnailUrl){
        if (activity == null){
            ImageLoaderManager.getInstance().loadImg(v.getContext(),url,thumbnailUrl,v);
        }else {
            ImageLoaderManager.getInstance().loadImg(activity,url,thumbnailUrl,v);
        }
    }

    @BindingAdapter(value = {"fragment","loadUrl","preLoadThumbnail"},requireAll = false)
    public static void loadUrl(ImageView v, Fragment fragment, String url, String thumbnailUrl){
        if (fragment == null){
            ImageLoaderManager.getInstance().loadImg(v.getContext(),url,thumbnailUrl,v);
        }else {
            ImageLoaderManager.getInstance().loadImg(fragment,url,thumbnailUrl,v);
        }
    }
}
