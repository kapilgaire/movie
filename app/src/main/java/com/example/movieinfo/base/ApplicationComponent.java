package com.example.movieinfo.base;

import com.example.movieinfo.detail.MovieDetailFragment;
import com.example.movieinfo.list.MovieListFragment;
import com.example.movieinfo.networking.NetworkingModule;
import com.example.movieinfo.viewModel.ViewModelModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {NetworkingModule.class, ViewModelModule.class,
})
public interface ApplicationComponent {

    void inject(MovieListFragment movieListFragment);

    void inject(MovieDetailFragment movieDetailFragment);
}
