package com.example.movieinfo.viewModel;

import android.arch.lifecycle.ViewModel;

import com.example.movieinfo.detail.SelectedMovieViewModel;
import com.example.movieinfo.list.MovieListViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public  abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieListViewModel.class)
    abstract ViewModel bindMovieListViewModel(MovieListViewModel movieListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SelectedMovieViewModel.class)
    abstract ViewModel bindSelectedMovieViewModel(SelectedMovieViewModel selectedMovieViewModel);
}
