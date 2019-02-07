package com.example.arefin.dagger2loginlistapplication;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.arefin.dagger2loginlistapplication.Constants.SharedPreferenceName;
import static com.example.arefin.dagger2loginlistapplication.Constants.SharedPreferencesModePrivate;

@Module
 class SharedPreferenceModule {
    private Context context;

    SharedPreferenceModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    SharedPreferences.Editor getSharedPreferenceEditor(){
        return getSharedPreference().edit();
    }

    @Provides
    @Singleton
    SharedPreferences getSharedPreference(){
        return context.getSharedPreferences(SharedPreferenceName, SharedPreferencesModePrivate);
    }
}
