package com.shid.newsfeed;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.shid.newsfeed.Api.ApiClient;
import com.shid.newsfeed.Api.ApiInterface;
import com.shid.newsfeed.Models.Article;
import com.shid.newsfeed.Models.News;
import com.shid.newsfeed.Utils.Constant;
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

    private static String country = Utils.getCountry();
    private static String language = Utils.getLanguage();

    public static String keyword = "";
    public static String status = "";
    public static int error_response;
    private Call<News> call;
    public static String errorCode;


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Article> callback) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        if (keyword.length() > 0) {
            call = apiInterface.getNewsSearch(keyword, language, "publishedAt", API_KEY, PAGE_SIZE, FIRST_PAGE);
        } else {
            call = apiInterface.getNews(country, API_KEY, PAGE_SIZE, FIRST_PAGE);
        }

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    callback.onResult(response.body().getArticle(), null, FIRST_PAGE + 1);
                    keyword = "";
                    status = Constant.SUCCESS;

                } else {
                    keyword = "";
                    status = Constant.ERROR;
                    error_response = response.code();
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                keyword = "";
                status = Constant.FAILURE;
                errorCode = t.getMessage();
                Log.d("TAG","error is : " + t.getMessage());
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(country, API_KEY, PAGE_SIZE, params.key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    callback.onResult(response.body().getArticle(), adjacentKey);  keyword = "";
                    status = Constant.SUCCESS;

                } else {
                    keyword = "";
                    status = Constant.ERROR;
                    error_response = response.code();
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                keyword = "";
                status = Constant.FAILURE;
                errorCode = t.getMessage();
                Log.d("TAG","error is : " + t.getMessage());
            }
        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Article> callback) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<News> call = apiInterface.getNews(country, API_KEY, PAGE_SIZE, params.key);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                //if the current page is greater than one
                //we are decrementing the page number
                //else there is no previous page
                Integer adjacentKey = (params.key > 1) ? params.key + 1 : null;
                if (response.isSuccessful() && response.body().getArticle() != null) {
                    callback.onResult(response.body().getArticle(), adjacentKey);  keyword = "";
                    status = Constant.SUCCESS;

                } else {
                    keyword = "";
                    status = Constant.ERROR;
                    error_response = response.code();
                    switch (response.code()) {
                        case 404:
                            errorCode = "404 not found";
                            break;
                        case 500:
                            errorCode = "500 server broken";
                            break;
                        default:
                            errorCode = "unknown error";
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                keyword = "";
                status = Constant.FAILURE;
                errorCode = t.getMessage();
                Log.d("TAG","error is : " + t.getMessage());
            }
        });
    }
}
