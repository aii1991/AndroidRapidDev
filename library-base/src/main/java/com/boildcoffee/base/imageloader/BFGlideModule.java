package com.boildcoffee.base.imageloader;

import android.content.Context;
import android.util.Log;

import com.boildcoffee.base.BFConfig;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author zjh
 *         2017/12/25
 */

@GlideModule
public class BFGlideModule extends AppGlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .setBitmapPoolScreens(2)
                .build();
        builder.setMemoryCache(new LruResourceCache(calculator.getMemoryCacheSize()))
                .setBitmapPool(new LruBitmapPool(calculator.getBitmapPoolSize()))
                .setDiskCache(new InternalCacheDiskCacheFactory(context, BFConfig.INSTANCE.getConfig().getImageCacheFileName()
                        ,BFConfig.INSTANCE.getConfig().getImageCacheSize()))
                .setLogLevel(Log.DEBUG);

    }
}
