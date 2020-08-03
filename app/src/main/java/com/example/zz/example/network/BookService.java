package com.example.zz.example.network;

import android.database.Observable;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    //https://api.douban.com/v2/book/search?q=金瓶梅&tag=&start=0&count=1

    /**
     * Retrofit网络请求测试
     * @param name
     * @param tag
     * @param start
     * @param count
     * @return
     */
    @GET("book/search")
    Call<Book> getSearchBook(@Query("q") String name,
                             @Query("tag") String tag,
                             @Query("start") int start,
                             @Query("count") int count);

    /**
     * Retrofit和Rxjava结合网络请求测试
     * @param name
     * @param tag
     * @param start
     * @param count
     * @return
     */
    @GET("book/search")
    Observable<Book> getSearchBook2(@Query("q") String name,
                                    @Query("tag") String tag, @Query("start") int start,
                                    @Query("count") int count);
}
