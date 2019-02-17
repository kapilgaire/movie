package com.example.movieinfo.list;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.example.movieinfo.model.Movie;
import com.example.movieinfo.model.MovieResponse;
import com.example.movieinfo.networking.RepositoryApi;
import com.example.movieinfo.util.NetworkState;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {

    public static final int LIMIT = 20;
    public static final int PAGE_NUMBER = 1;


    private MutableLiveData networkState;
    private MutableLiveData initialLoading;

    public MovieDataSource() {


        networkState = new MutableLiveData();
        initialLoading = new MutableLiveData();

    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);

        RepositoryApi.getInstance().getMovieList(LIMIT, PAGE_NUMBER).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        List<Movie> movieList = response.body().getMovieData().getMovieList();
                        callback.onResult(movieList, null, PAGE_NUMBER + 1);

                        initialLoading.postValue(NetworkState.LOADED);
                        networkState.postValue(NetworkState.LOADED);
                    } else {
                        initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }

                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {


//        apiService.getMovieList(LIMIT,params.key).enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
//            }
//        });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {


        networkState.postValue(NetworkState.LOADING);

        RepositoryApi.getInstance().getMovieList(LIMIT, params.key).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {


                if (response.isSuccessful()) {

                    if (response.body() != null) {
                        int totalResults = response.body().getMovieData().getMovieCount() / LIMIT;

                        long nextkey = (params.key == totalResults) ? null : params.key + 1;

                        callback.onResult(response.body().getMovieData().getMovieList(), PAGE_NUMBER + 1);

                        networkState.postValue(NetworkState.LOADED);

                    } else {
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    }


                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });

    }
}
