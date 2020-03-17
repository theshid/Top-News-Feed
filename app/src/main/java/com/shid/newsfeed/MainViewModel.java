package com.shid.newsfeed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.shid.newsfeed.Models.Article;

public class MainViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData<PagedList<Article>> articlePagedList;
    LiveData<PageKeyedDataSource<Integer, Article>> liveDataSource;

    //constructor
    public MainViewModel() {
        //getting our data source factory
        ArticleDataSourceFactory itemDataSourceFactory = new ArticleDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getArticleLiveDataSource();

        //Getting PagedList config
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ArticleDataSource.PAGE_SIZE).build();

        //Building the paged list
        articlePagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig))
                .build();
    }
}
