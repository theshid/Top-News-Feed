package com.shid.newsfeed;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.shid.newsfeed.Models.Article;

public class ArticleDataSourceFactory extends DataSource.Factory<Integer, Article> {

    //creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Article>> articleLiveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Article> create() {
        ArticleDataSource articleDataSource = new ArticleDataSource();
        articleLiveDataSource.postValue(articleDataSource);
        return articleDataSource;
    }

    //getter for articlelivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Article>> getArticleLiveDataSource() {
        return articleLiveDataSource;
    }
}
