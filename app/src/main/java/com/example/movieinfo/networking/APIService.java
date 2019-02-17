package com.example.movieinfo.networking;

import com.example.movieinfo.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("list_movies.json")
    Call<MovieResponse> getMovieList(@Query("limit") int limit,@Query("page") int page  );

}
