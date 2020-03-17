package com.shid.newsfeed;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.shid.newsfeed.Api.ApiClient;
import com.shid.newsfeed.Api.ApiInterface;
import com.shid.newsfeed.Models.Article;
import com.shid.newsfeed.Models.News;
import com.shid.newsfeed.Utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.shid.newsfeed.MainActivity.API_KEY;

public class ArticleDataSource extends PageKeyedDataSource<Integer, Article> {
    //the size of a page that we want
    public static final int PAGE_SIZE = 50;

    //we will start from the first page which is 1
    private static final int FIRST_PAGE = 1;

    private static  String country =  Utils.getCountry();
    private static String language = Utils.getLanguage();


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(country, API_KEY,PAGE_SIZE,FIRST_PAGE);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null){
                    callback.onResult(response.body().getArticle(),null,FIRST_PAGE + 1);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(country, API_KEY,PAGE_SIZE,params.key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (response.isSuccessful() && response.body().getArticle() != null){
                    callback.onResult(response.body().getArticle(),adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(country, API_KEY,PAGE_SIZE,params.key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                Integer adjacentKey = (params.key > 1) ? params.key + 1 : null;
                if (response.isSuccessful() && response.body().getArticle() != null){
                    callback.onResult(response.body().getArticle(),adjacentKey);
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {

            }
        });
    }
}
