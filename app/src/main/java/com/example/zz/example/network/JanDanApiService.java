package com.example.zz.example.network;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * desc:
 * author: wecent
 * date: 2018/9/27
 */
public interface JanDanApiService {

    @GET
    Call<BelleEntity> getDetailData(@Url String url,
                                    @Query("oxwlxojflwblxbsapi") String oxwlxojflwblxbsapi,
                                    @Query("page") int page
    );

}

