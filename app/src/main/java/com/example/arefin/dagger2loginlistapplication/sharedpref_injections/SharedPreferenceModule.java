package com.example.arefin.dagger2loginlistapplication.sharedpref_injections;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.arefin.dagger2loginlistapplication.DynamicToast;

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
    SharedPreferences.Editor getSharedPreferenceEditor(){
        return getSharedPreference().edit();
    }

    @Provides
    @Singleton
    SharedPreferences getSharedPreference(){
        return context.getSharedPreferences(SharedPreferenceName, SharedPreferencesModePrivate);
    }

    @Provides
    @Singleton
    DynamicToast getDynamicToast(){
        return new DynamicToast(context);
    }
}
