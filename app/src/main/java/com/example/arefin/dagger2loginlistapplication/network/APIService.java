package com.example.arefin.dagger2loginlistapplication.network;

import com.example.arefin.dagger2loginlistapplication.models.Todo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("todos/")
    Call<List<Todo>> getAllTodos();
}
