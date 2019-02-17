package com.example.movieinfo.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RepositoryApi {

    private RepositoryApi() {
    }

    private static final String BASE_URL = "https://yts.am/api/v2/";

    private static Retrofit retrofit;

    private static APIService apiService;

    public static APIService getInstance() {

        if (apiService != null) {
            return apiService;
        }
        if (retrofit == null) {
            initializeRetrofit();
        }
        apiService = retrofit.create(APIService.class);
        return apiService;
    }

    private static void initializeRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }
}
