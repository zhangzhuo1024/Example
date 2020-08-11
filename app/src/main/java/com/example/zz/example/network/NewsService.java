package com.example.zz.example.network;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NewsService {
    //https://api.apiopen.top/getJoke?page=1&count=2&type=text

    /**
     * Retrofit网络请求测试
     *
     * @param count
     * @param page
     * @param type
     * @return
     */
    @GET("getJoke")
    Call<ResponseBody> getJokeList(@Query("page") int page,
                                   @Query("count") int count,
                                   @Query("type") String type);

    /**
     * Retrofit和Rxjava结合网络请求测试
     *
     * @param count
     * @param page
     * @param type
     * @return
     */
    @GET("getJoke")
    Observable<ResponseBody> getJokeList2(@Query("page") int page,
                                          @Query("count") int count,
                                          @Query("type") String type);


    /**
     * Retrofit网络请求实战，测试网易新闻api
     *
     * @param count
     * @param page
     * @return
     */
    @POST("getWangYiNews")
    Call<ResponseBody> getWangYiNews(@Query("page") int page,
                                     @Query("count") int count);

}
