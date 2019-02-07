package com.example.arefin.dagger2loginlistapplication;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import static com.example.arefin.dagger2loginlistapplication.Constants.SharedPreferenceName;
import static com.example.arefin.dagger2loginlistapplication.Constants.SharedPreferencesModePrivate;

@Module
public class SharedPreferenceModule {
    private Context context;


    public SharedPreferenceModule(Context context){
        this.context = context;
    }

    @Provides
    @Singleton
    public SharedPreferences.Editor getSharedPreferenceEditor(){
        return getSharedPreference().edit();
    }

    @Provides
    @Singleton
    public SharedPreferences getSharedPreference(){
        SharedPreferences pref = context.getSharedPreferences(SharedPreferenceName, SharedPreferencesModePrivate);
        return pref;
    }

}
