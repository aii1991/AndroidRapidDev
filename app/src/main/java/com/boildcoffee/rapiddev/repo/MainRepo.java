package com.boildcoffee.rapiddev.repo;

import com.boildcoffee.base.network.bean.RspBean;
import com.boildcoffee.rapiddev.bean.ImageBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author zjh
 *         2017/12/27
 */

public interface MainRepo {
    @GET("/api/data/福利/{pageSize}/{page}")
    Observable<RspBean<List<ImageBean>>> getListImage(@Path("page") int page,@Path("pageSize")int pageSize);
}
