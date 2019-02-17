package com.example.movieinfo.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.example.movieinfo.model.Movie;
import com.example.movieinfo.util.NetworkState;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class MovieListViewModel extends ViewModel {

//    private final MutableLiveData<MovieResponse> movieData = new MutableLiveData<>();
//    private final MutableLiveData<DataLoadError> dataLoadError = new MutableLiveData<>();
//    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

//    private Call<MovieResponse> movieDataCall;
//    private final APIService apiService;

//    @Inject
//    public MovieListViewModel(MovieDataSourceFactory movieDataSourceFactory) {
//        this.apiService = apiService;
////        fetchMovieData();
//    }

    private Executor executor;

    private LiveData<NetworkState> networkState;
    private LiveData<NetworkState> initialLoadingState;


    private LiveData<PagedList<Movie>> movieLiveData;


    @Inject
    public MovieListViewModel() {
        init();
    }

    public void init() {
        executor = Executors.newFixedThreadPool(5);

        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory();

        networkState = Transformations.switchMap(movieDataSourceFactory.getMutableMovieLiveDataSource(),
                dataSource -> dataSource.getNetworkState());

        initialLoadingState = Transformations.switchMap(movieDataSourceFactory.getMutableMovieLiveDataSource(),
                dataSource -> dataSource.getInitialLoading());




        PagedList.Config config = (new PagedList.Config.Builder())
                .setPageSize(20)
                .setEnablePlaceholders(false)
                .build();

        movieLiveData = (new LivePagedListBuilder<>(movieDataSourceFactory,config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<NetworkState> getInitialLoadingState() {
        return initialLoadingState;
    }

    public LiveData<PagedList<Movie>> getMovieLiveData() {
        return movieLiveData;
    }

    //    public LiveData<MovieResponse> getMovieData() {
//        return movieData;
//    }
//
//    public LiveData<DataLoadError> getDataLoadError() {
//        return dataLoadError;
//    }
//
//    public LiveData<Boolean> getLoading() {
//        return loading;
//    }

//    private void fetchMovieData() {
//        loading.setValue(true);
//
////        movieDataCall = RepositoryApi.getInstance().getMovieList();
//        movieDataCall = apiService.getMovieList();
//
//        movieDataCall.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(@NonNull Call<MovieResponse> call, @NonNull Response<MovieResponse> response) {
//
//                dataLoadError.setValue(new DataLoadError(false, null));
//                movieData.setValue(response.body());
//                loading.setValue(false);
//
//                movieDataCall = null;
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
//                Log.e(getClass().getSimpleName(), "Error loading " + t.getMessage());
//                dataLoadError.setValue(new DataLoadError(true, t));
//                loading.setValue(false);
//                movieDataCall = null;
//            }
//        });
//    }

    @Override
    protected void onCleared() {
        super.onCleared();

//        if (movieDataCall != null) {
//            movieDataCall.cancel();
//            movieDataCall = null;
//        }
    }
}
