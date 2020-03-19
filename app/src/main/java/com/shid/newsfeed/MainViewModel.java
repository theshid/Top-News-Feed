package com.shid.newsfeed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.shid.newsfeed.Models.Article;

public class MainViewModel extends ViewModel {

    //creating livedata for PagedList  and PagedKeyedDataSource
    LiveData articlePagedList;
    LiveData<PageKeyedDataSource<Integer, Article>> liveDataSource;
    //getting our data source factory
    ArticleDataSourceFactory articleDataSourceFactory ;
    PagedList.Config pagedListConfig;

    //constructor
    public MainViewModel() {
        articleDataSourceFactory = new ArticleDataSourceFactory();

        //getting the live data source from data source factory
        liveDataSource = articleDataSourceFactory.getArticleLiveDataSource();

        //Getting PagedList config
         pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ArticleDataSource.PAGE_SIZE).build();

        //Building the paged list
        articlePagedList = (new LivePagedListBuilder(articleDataSourceFactory, pagedListConfig))
                .build();
    }

    public void refresh(){
        articleDataSourceFactory.getArticleLiveDataSource().getValue().invalidate();
        //Getting PagedList config
        pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(ArticleDataSource.PAGE_SIZE).build();
        articlePagedList = new LivePagedListBuilder(articleDataSourceFactory, pagedListConfig)
                .build();
    }
}
