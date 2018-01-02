package com.boildcoffee.base.imageloader;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.widget.ImageView;

import com.boildcoffee.base.BFConfig;
import com.boildcoffee.base.BaseApplication;
import com.boildcoffee.base.R;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.signature.ObjectKey;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * @author zjh
 *         2017/12/25
 */

public class ImageLoaderManager {
    private ImageLoaderManager(){}
    private static ImageLoaderManager mInstance;
    private int mLoadingImgRes = BFConfig.getInstance().getConfig().getLoadingImage();
    private int mErrorImgRes = BFConfig.getInstance().getConfig().getLoadFailImage();
//    private int mAvatarImgRes = R.mipmap.user_photo;
    private int mAvatarImgRes = BFConfig.getInstance().getConfig().getLoadingImage();

    private RequestOptions mRequestOptions;

    public static synchronized ImageLoaderManager getInstance(){
        if (mInstance == null){
            mInstance = new ImageLoaderManager();
        }
        return mInstance;
    }

    public ViewTarget loadImg(Activity activity, String url,String thumbnailUrl,ImageView imageView){
        return loadImg(GlideApp.with(activity),url,thumbnailUrl,null).into(imageView);
    }

    public ViewTarget loadImg(Fragment fragment, String url, String thumbnailUrl, ImageView imageView){
        return loadImg(GlideApp.with(fragment),url,thumbnailUrl,null).into(imageView);
    }

    public ViewTarget loadImg(Context context,String url, String thumbnailUrl, ImageView imageView){
         return loadImg(GlideApp.with(context),url,thumbnailUrl,null).into(imageView);
    }

    public RequestBuilder loadImg(GlideRequests glideRequests,String url,String thumbnailUrl,RequestListener requestListener){
        GlideRequest requestBuilder = glideRequests
                .load(url)
                .apply(getRequestOptions())
                .transition(withCrossFade());
        if (requestListener != null){
            requestBuilder.listener(requestListener);
        }
        if (TextUtils.isEmpty(thumbnailUrl)){
            requestBuilder.thumbnail(glideRequests.load(thumbnailUrl));
        }
        return requestBuilder;
    }


    /**
     * 加载头像
     * @param url
     * @param imageView
     * @return
     */
    public ViewTarget loadAvatar(String url,ImageView imageView){
        return GlideApp.with(BaseApplication.mInstance)
                .asBitmap()
                .signature(new ObjectKey(url + System.currentTimeMillis()))
                .placeholder(mAvatarImgRes)
                .error(mAvatarImgRes)
                .into(imageView);
    }


    private RequestOptions getRequestOptions(){
        if (mRequestOptions == null){
            mRequestOptions = new GlideOptions();
            mRequestOptions.fitCenter()
                    .placeholder(mLoadingImgRes)
                    .error(mErrorImgRes)
                    .autoClone();
        }
        return mRequestOptions;
    }


    public void setLoadingImg(@DrawableRes int imgRes){
        mLoadingImgRes = imgRes;
    }

    public void setErrorImg(@DrawableRes int imgRes){
        mErrorImgRes = imgRes;
    }

    public void setLoadingAvatarImg(@DrawableRes int imgRes){
        mAvatarImgRes = imgRes;
    }



}
