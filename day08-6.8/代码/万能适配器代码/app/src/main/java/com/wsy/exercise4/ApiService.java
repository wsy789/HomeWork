package com.wsy.exercise4;



import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //https://www.wanandroid.com/project/list/1/json?cid=294
    String BaseUrl = "https://www.wanandroid.com/";

    @GET("project/list/{num}/json")
    Observable<WanBean> getNet(@Path("num") int num, @Query("cid") String cid);
}
