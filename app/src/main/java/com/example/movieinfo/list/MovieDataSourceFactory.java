package com.example.movieinfo.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

public class MovieDataSourceFactory extends DataSource.Factory {

    private MovieDataSource movieDataSource;

    private MutableLiveData<MovieDataSource> mutableMovieLiveDataSource;

    public MovieDataSourceFactory() {
        this.mutableMovieLiveDataSource = new MutableLiveData<MovieDataSource>();

    }

    @Override
    public DataSource create() {

        movieDataSource = new MovieDataSource();
        mutableMovieLiveDataSource.postValue(movieDataSource);
        return movieDataSource;
    }

    public MutableLiveData<MovieDataSource> getMutableMovieLiveDataSource() {
        return mutableMovieLiveDataSource;
    }
}
