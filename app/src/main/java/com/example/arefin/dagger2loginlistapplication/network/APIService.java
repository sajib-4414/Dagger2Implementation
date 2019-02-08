package com.example.arefin.dagger2loginlistapplication.network;

import com.example.arefin.dagger2loginlistapplication.models.Todo;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface APIService {
    @GET("todos/")
    Observable<List<Todo>> getAllTodos();
}
