package com.example.arefin.dagger2loginlistapplication;

import javax.inject.Singleton;
import dagger.Component;

//Declaring what modules will be using the component
@Component(modules = {SharedPreferenceModule.class})
@Singleton
public interface SharedPrefComponent {
    void inject(MainActivity mainActivity);
}
