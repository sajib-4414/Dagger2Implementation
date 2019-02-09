package com.example.arefin.dagger2loginlistapplication.network.injections;

import com.example.arefin.dagger2loginlistapplication.TodoDetailsActivity;
import com.example.arefin.dagger2loginlistapplication.TodoListActivity;


import javax.inject.Singleton;

import dagger.Component;

//Declaring what modules will be using the component
@Component(modules = {RetrofitNetworkModule.class})
@Singleton
public interface RetrofitNetworkComponent {
    void inject(TodoListActivity todoListActivity);
    void doInjections(TodoDetailsActivity activity);
}