package com.example.movieinfo.base;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.create();

    }
    public static ApplicationComponent getApplicationComponent(Context context){
        return(( MyApplication) context.getApplicationContext()).applicationComponent;
    }
}
