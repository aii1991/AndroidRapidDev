# RapidDev

  致力于android快速开发,采用databind并集成目前流行的开发库如:retrofit,glid,rxjava等

## 使用

继承BaseApplication并在onCreate中初始化配置即可使用

```java
  public class MyApplication extends BaseApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        BFConfig.getInstance().init(new BaseConfig.Builder()
                .setBaseUrl("http://gank.io/")
                .setDebug(true).build());
    }
  }
```

## 配置选项

| 方法名                     | 作用 |
| -                         | :-:  |
| setDebug                  | 是否开启调试模式 |
| setPageSize               | 设置分页大小 |
| setBaseUrl                | 设置网络请求前缀地址 |
| setRetrofitConnectTimeout | 设置连接超时时间,默认30秒 |
| setRetrofitReadTimeout    | 设置读取超时时间,默认60秒 |
| setRetrofitWriteTimeout   | 设置写入超时时间,默认60秒 |
| setImageCacheSize         | 设置图片磁盘缓存大小,默认100M |
| setImageCacheFileName     | 设置缓存目录名称,默认CACHE_IMG |
| setLoadingImage           | 设置图片加载时图片 |
| setLoadFailImage          | 设置图片加载失败时图片 |
| setRspCheckInterceptor    | 设置处理网络请求响应数据方法 |

## 快速实现列表

![test.gif](
https://github.com/aii1991/AndroidRapidDev/blob/master/app/gif/test.gif)

```java
  public class MainVM extends PagingVM<ImageBean>{
      private RxAppCompatActivity mRxActivity;

      public MainVM(RxAppCompatActivity rxActivity){
          mRxActivity = rxActivity;
      }


      @Override
      public IPagingService<ImageBean> getPagingService() {
          return (page, pageSize, onSuccess, onError, onComplete) ->
                  RetrofitManager //获取列表数据
                          .getInstance()
                          .createReq(MainRepo.class)
                          .getListImage(page,pageSize)
                          .compose(mRxActivity.bindToLifecycle())
                          .compose(TransformerHelper.observableToMainThreadTransformer())
                          .map(new TransformHttpDataFunc<>())
                          .subscribe(onSuccess,onError,onComplete);
      }

      @Override
      public int getVariableId() {
          return BR.imageBean;
      }

  }
```

```java
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
```

activity_main.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="mainVm"
            type="com.boildcoffee.rapiddev.viewmodel.MainVM" />
        <import type="com.boildcoffee.base.bindingadapter.LayoutManagers" />
    </data>
    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v4.widget.SwipeRefreshLayout
            bind:pageVM="@{mainVm}"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
            <android.support.v7.widget.RecyclerView
                android:background="@android:color/holo_blue_dark"
                bind:layoutManager="@{LayoutManagers.linear()}"
                bind:pageVM="@{mainVm}"
                bind:itemViewId="@{@layout/main_item}"
                bind:BRName="@{mainVm.getVariableId()}"
                android:layout_width="match_parent"
                android:layout_height="match_parent" />

        </android.support.v4.widget.SwipeRefreshLayout>
    </LinearLayout>
</layout>
```

main_item.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:bind = "http://schemas.android.com/apk/res-auto">
    <data>
        <variable
            name="imageBean"
            type="com.boildcoffee.rapiddev.bean.ImageBean" />
    </data>
    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        <android.support.v7.widget.CardView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            bind:cardCornerRadius="10dp"
            bind:cardUseCompatPadding="true">
            <ImageView
                android:background="@android:color/black"
                bind:loadUrl="@{imageBean.getUrl()}"
                android:layout_width="match_parent"
                android:layout_height="250dp" />

        </android.support.v7.widget.CardView>
    </LinearLayout>
</layout>
```

## 日志打印

```java
    import com.orhanobut.logger.Logger;
    Logger.d("logger");
```
