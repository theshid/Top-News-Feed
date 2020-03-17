package com.shid.newsfeed.Api;

import com.shid.newsfeed.Models.News;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<News> getNews(

            @Query("country") String country ,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize,
            @Query("page") int page

    );

    @GET("everything")
    Call<News> getNewsSearch(


            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey,
            @Query("pageSize") int pageSize,
            @Query("page") int page

    );
}
