package com.example.movieinfo.networking;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class NetworkingModule {
    private static final String BASE_URL = "https://yts.am/api/v2/";

    @Provides
    @Singleton
    static Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
    }

    @Provides
    @Singleton
    static APIService provideApiService(Retrofit retrofit){
        return retrofit.create(APIService.class);
    }
}
