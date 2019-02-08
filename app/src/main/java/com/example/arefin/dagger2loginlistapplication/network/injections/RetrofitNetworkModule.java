package com.example.arefin.dagger2loginlistapplication.network.injections;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arefin.dagger2loginlistapplication.DynamicToast;
import com.example.arefin.dagger2loginlistapplication.network.APIService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.arefin.dagger2loginlistapplication.Constants.BASE_URL;
import static com.example.arefin.dagger2loginlistapplication.Constants.SharedPreferenceName;
import static com.example.arefin.dagger2loginlistapplication.Constants.SharedPreferencesModePrivate;

@Module
public class RetrofitNetworkModule {

    @Singleton
    Gson getGsonInstance(){
        return new GsonBuilder()
                .setLenient()
                .create();
    }

    @Singleton
    Retrofit getRetrofitInstance(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(getGsonInstance()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    APIService getApiInstance(){
        return getRetrofitInstance().create(APIService.class);
    }

}
